<% import grails.persistence.Event %>
<%=packageName%>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>

<div class="container-fluid">
    <div class="row">

        <div class="col-sm-3 col-md-2 sidebar">
            <div class="panel-group nav nav-sidebar" id="accordion" role="tablist" aria-multiselectable="true">

            </div>
        </div>

        <div class="col-md-10 col-md-offset-1 main">
            <div class="row">
                <div class="col-xs-12">
                    <h1 class="page-header">${domainClass.propertyName}</h1>
                </div>
            </div>

            <div id="list-${domainClass.propertyName}" class="table-responsive">
                <g:if test="\${flash.message}">
                    <div class="message" role="status">\${flash.message}</div>
                </g:if>
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <% excludedProps = Event.allEvents.toList() << 'id' << 'version'
                        allowedNames = domainClass.persistentProperties*.name << 'dateCreated' << 'lastUpdated'
                        props = domainClass.properties.findAll {
                            allowedNames.contains(it.name) && !excludedProps.contains(it.name) && it.type != null && !Collection.isAssignableFrom(it.type) && (domainClass.constrainedProperties[it.name] ? domainClass.constrainedProperties[it.name]?.display : true)
                        }
                        Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))
                        props.eachWithIndex { p, i -> %>
                        <th><g:message code="${domainClass.propertyName}.${p.name}.label" default="${p.naturalName}" /></th>
                        <% } %>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="\${${propertyName}List}" status="i" var="${propertyName}">
                        <tr class="\${(i % 2) == 0 ? 'even' : 'odd'}">
                            <% props.eachWithIndex { p, i ->
                                if (i == 0) { %>
                            <td><g:link action="show"
                                        id="\${${propertyName}.id}">\${fieldValue(bean: ${propertyName}, field: "${p.name}")}</g:link></td>
                            <% } else if (1==1 || i < 6) {
                                if (p.type == Boolean || p.type == boolean) { %>
                            <td><g:formatBoolean boolean="\${${propertyName}.${p.name}}"/></td>
                            <%
                                } else if (p.type == Date || p.type == java.sql.Date || p.type == java.sql.Time || p.type == Calendar) { %>
                            <td><g:formatDate date="\${${propertyName}.${p.name}}"/></td>
                            <% } else { %>
                            <td>\${fieldValue(bean: ${propertyName}, field: "${p.name}")}</td>
                            <% }
                            }
                            } %>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="col-sm-2 col-sm-offset-5">
            <div class="pagination">
                <g:paginate total="${0}"/>
            </div>
        </div>
    </div>
</div>

</body>
</html>
