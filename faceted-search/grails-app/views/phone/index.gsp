<%@ page import="phonedb.Phone" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'phone.label', default: 'Phone')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>

<div class="container-fluid">
    <div class="row">

        <div class="col-sm-3 col-md-2 sidebar">
            <div class="panel-group nav nav-sidebar" id="leftpanel" role="tablist" aria-multiselectable="true">
                <div id="accordion">
                    <g:renderFilter filterHtml="${filterHtml}"/>
                </div>
            </div>
        </div>

        <div class="col-md-10 main">
            <div class="row">
                <div class="col-xs-12">
                    <h1>phone</h1>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12">
                   <g:link controller="phone" action="create">create new</g:link>
                </div>
            </div>

            <div id="list-phone" class="table-responsive">
                <g:if test="${flash.message}">
                    <div class="message" role="status">${flash.message}</div>
                </g:if>
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th><g:message code="phone.name.label" default="Name"/></th>

                        <th><g:message code="phone.brand.label" default="Brand"/></th>

                        <th><g:message code="phone.color.label" default="Color"/></th>

                        <th><g:message code="phone.ram.label" default="Ram"/></th>

                        <th><g:message code="phone.screenSize.label" default="Screen Size"/></th>

                        <th><g:message code="phone.storage.label" default="Storage"/></th>

                        <th><g:message code="phone.storage.label" default="Sellers"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${phoneInstanceList}" status="i" var="phoneInstance">
                        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                            <td><g:link action="show" id="${phoneInstance.id}">${phoneInstance}</g:link></td>

                            <td>${fieldValue(bean: phoneInstance, field: "brand")}</td>

                            <td>${fieldValue(bean: phoneInstance, field: "color")}</td>

                            <td>${fieldValue(bean: phoneInstance, field: "ram")}</td>

                            <td>${fieldValue(bean: phoneInstance, field: "screenSize")}</td>

                            <td>${fieldValue(bean: phoneInstance, field: "storage")}</td>

                            <td>
                                <ul class="list-unstyled">
                                    <g:each in="${phoneInstance.sellers}" var="s">
                                        <li><g:link
                                                controller="seller" action="show"
                                                id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
                                    </g:each>
                                </ul>
                            </td>

                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="col-sm-2 col-sm-offset-5">
            <div class="pagination">
                <g:paginate total="0"/>
            </div>
        </div>
    </div>
</div>

</body>
</html>
