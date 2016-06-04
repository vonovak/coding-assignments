package phonedb.common

@grails.validation.Validateable(nullable = true)
class FilterCommand {
    List<String> screenSize
    List<String> brand
    List<String> color
    List<String> ram
    List<String> storage

    Integer offset
    Integer max


    public String createFilterUrl() {

        def paramz = ['screenSize', 'brand', 'color', 'ram', 'storage']
        def url = paramz.findAll { this[it] }.collect { filterName ->
            if (this[filterName] instanceof List) {
                this[filterName].collect { listItem ->
                    filterName + "=" + URLEncoder.encode(listItem.toString(), "UTF-8")
                }.join('&')
            } else {
                filterName + "=" + URLEncoder.encode(this[filterName].toString(), "UTF-8")
            }
        }.join('&')

        if (url.length() > 0) {
            url = "&${url}"
        }
        "/phone/index?filter$url"
    }

}