import phonedb.common.FilterItem
import phonedb.common.FilterCommand
import phonedb.Phone
import phonedb.Seller
import groovy.xml.MarkupBuilder
import org.apache.commons.logging.LogFactory
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.search.SearchType
import org.elasticsearch.index.query.FilterBuilder
import org.elasticsearch.index.query.FilterBuilders
import org.elasticsearch.index.query.QueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.aggregations.AggregationBuilders
import org.elasticsearch.search.aggregations.bucket.terms.Terms
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.springframework.context.i18n.LocaleContextHolder

class AggregationService {
    static transactional = false

    public static final String ALLFILTERS = "allfilters"
    private static final log = LogFactory.getLog(this)

    def elasticSearchService
    def messageSource

    static final LinkedHashSet<String> stringTermFilters =
            ["brand", "color", "storage", "ram", "screenSize"] as LinkedHashSet

    static final LinkedHashSet<String> rangeFilters = ["screenSize"] as LinkedHashSet

    static final Set<String> allFilterHeadings = rangeFilters + stringTermFilters

    def getFiltersAndPhones(FilterCommand cmd) {
        def params = cmd.properties
        def results = runSearch(params)

        final Collection<Phone> phones = results.searchResults
        def aggregations = results.aggregations
        def buf = new StringBuffer();

        for (String filterName : stringTermFilters) {
            List<String> strings = []
            List<Integer> counts = []
            List<String> zeroStrings = []
            List<Boolean> selected = []

            Terms terms = (Terms) aggregations[filterName]
            for (Terms.Bucket bucket : terms?.getBuckets()) {
                def count = bucket.getAggregations().get(ALLFILTERS).getDocCount() // Doc count
                if (count == 0) {
                    zeroStrings << bucket.getKey()
                } else {
                    strings << bucket.getKey()      // Term
                    counts << count
                    selected << (bucket.getKey() in params[filterName]) ? true : false
                }
            }
            //append those filters that have zero count to the end
            strings.addAll(zeroStrings)
            counts.addAll([0] * zeroStrings.size())

            if (terms) {
                FilterItem filterItem = new FilterItem(filterHeading: filterName, selected: selected, filterLabels: strings,
                        dataValues: strings, matchingCounts: counts, dataNames: [filterName] * counts.size())

                String html = getFilterHTML(filterItem, [open: true], cmd)
                buf.append(html)
            }
        }


        return [filter: buf.toString(), phones: phones, minmaxBlue: [], total: results.total]
    }

    private Object runSearch(final Map<String, Object> params) {
        SearchRequest request = new SearchRequest()
        request.searchType SearchType.DFS_QUERY_THEN_FETCH
        SearchSourceBuilder source = new SearchSourceBuilder()

        /*
        * the set of aggregations is CONSTANT, the set of filters CHANGES
        * */
        HashMap<String, FilterBuilder> allSelectedFilters = new HashMap<String, FilterBuilder>(Math.min(params.size(), 15))
        params.each { directParamKey, directParamValues ->
            if (directParamValues == null || !allFilterHeadings.contains(directParamKey)) {
                //just double checking that we're not getting some bogus from the url
                return
            }
            String elasticPath = getAggregationPath(directParamKey)

            allSelectedFilters.put(directParamKey, FilterBuilders.termsFilter(elasticPath, directParamValues))
        }

        /*
        The post_filter is applied to the search hits at the very end of a search request,
        after aggregations have already been calculated.
        */
        FilterBuilder postFilter = FilterBuilders.andFilter()
        allSelectedFilters.values().each { postFilter.add(it) }

        source.postFilter(postFilter)

        //and finally take care of all aggregations
        prepareAggregations(allSelectedFilters, source)

        Integer offset = (params.offset as Integer) ?: 0
        Integer max = Math.min((params.max as Integer) ?: 20, 25)
        source.from(offset).size(max)

        QueryBuilder query = QueryBuilders.matchAllQuery()

        Long start = System.currentTimeMillis()
        Object search = elasticSearchService.search(request.source(source.query(query)), [indices: [Phone], types: [Phone]])
        log.debug("querying, fetching and re-building results from ES took ${System.currentTimeMillis() - start}ms")

        return search
    }

    private static void prepareAggregations(allSelectedFilters, source) {
        FilterBuilder removed
        def sortKey = "_term"


        String filterPath
        def agg
        for (String filterName : allFilterHeadings) {
            removed = allSelectedFilters.remove(filterName)
            filterPath = getAggregationPath(filterName)

            FilterBuilder tempFilters = FilterBuilders.andFilter()
            allSelectedFilters.values().each { tempFilters.add(it) }

            if (filterName in stringTermFilters) {
                //sorting alphabetically!!
                agg = AggregationBuilders.terms(filterName).field(filterPath).order(Terms.Order.aggregation(sortKey, true)).minDocCount(0).size(0)
                source.aggregation(agg.subAggregation(AggregationBuilders.filter(ALLFILTERS).filter(tempFilters)))
            }
            if (removed) {
                allSelectedFilters.put(filterName, removed)
            }
        }

    }

    private String getFilterHTML(FilterItem filterItem, Map map, FilterCommand cmd) {
        String filterHeading = filterItem.filterHeading
        List<String> filterLabels = filterItem.filterLabels
        List<String> dataNames = filterItem.dataNames
        List<?> dataValues = filterItem.dataValues
        List<Integer> matchingCounts = filterItem.matchingCounts
        List<Boolean> selected = filterItem.selected


        // if you remove this, things will get fucked up.
        // It would cause nesting the filters' html into one another and it would make the filter page look ugly
        if (filterLabels.size() == 0) {
            return ""
        }


        final String queryString = cmd.createFilterUrl()

        def w = new StringWriter()
        def builder = new MarkupBuilder(new IndentPrinter(new PrintWriter(w), "", false))

        builder.setDoubleQuotes(true)
        builder.omitNullAttributes = true
        builder.div(class: "panel panel-default") {
            div(class: "panel-heading", role: "tab", id: "heading$filterHeading") {
                h4(class: "panel-title") {
                    a(href: "#collapse$filterHeading", 'data-toggle': "collapse", 'aria-expanded': map?.open ? "true" : "false",
                            'aria-controls': "collapse$filterHeading",
                            messageSource.getMessage("filter.${filterHeading}.label", null, filterHeading, LocaleContextHolder.getLocale()),
                            class: map?.open ? "text-capitalize filterfontweight" : "filterfontweight text-capitalize collapsed")
                    //span(class: "count", "0", id: "collapse${filterHeading}Cnt")
                }
            }

            def op = map?.open ? " in" : "";
            String elemId
            Integer count
            div(id: "collapse$filterHeading", class: "panel-collapse collapse$op", role: "tabpanel", 'aria-labelledby': "heading$filterHeading") {
                div(class: "panel-body") {
                    if (map?.range) {
                        dataValues.eachWithIndex { dataValue, i ->
                            count = matchingCounts[i]
                            elemId = filterHeading + dataNames[i] + dataValue.toString().replaceAll(" ", "_")
                            input(type: "number", id: "slider-low-${elemId}", '')
                            input(type: "number", id: "slider-hi-${elemId}", '')
                            div(class: "sliderwrapper") {
                                div(id: elemId, '')
                            }
                        }
                    } else {
                        def content, hrefString, disabled, checked
                        dataValues.eachWithIndex { dv, i ->
                            String dataValue = dv.toString()
                            count = matchingCounts[i]
                            disabled = count == 0 ? "disabled" : null
                            checked = selected[i] ? "checked" : "data-noinfo"

                            // this removes brand xxx from link if that checkbox is selected, so that next click on the checkbox unselects it
                            // I made sure the '&' will match, i.e. that i'm not replacing params that belong to the '?' in url
                            hrefString = selected[i] ? queryString?.replace("&${dataNames[i]}=${dataValue.encodeURL()}", "") : "${queryString}&${dataNames[i]}=${dataValue.encodeURL()}"

                            elemId = filterHeading + dataNames[i] + dataValue.toString().replaceAll(" ", "_")
                            div(class: "checkbox") {
                                input(type: "checkbox", class: "filterCheckbox", "data-name": dataNames[i],
                                        "data-value": dataValue, id: elemId,
                                        disabled: disabled, "${checked}": '')
                                label(for: elemId, class: (!count) ? "filterCheckbox-disabled" : null) {
                                    content = messageSource.getMessage("phone.filter.${filterLabels[i]}.label", null, filterLabels[i], LocaleContextHolder.getLocale())
                                    content += " ($count)"

                                    if (count) {
                                        a(href: hrefString, class: "filterhref filterCheckbox", content)
                                    } else {
                                        span(content)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        w.toString()
    }

    private static String getAggregationPath(String paramKey) {
        if (paramKey in ["ram", "storage"]) {
            return "phone.$paramKey"
        }
        if (paramKey == "brand") {
            return "phone.brand.brand.untouched"
        }
        return "phone.$paramKey.$paramKey"
    }

}
