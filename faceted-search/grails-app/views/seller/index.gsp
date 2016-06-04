
<%@ page import="phonedb.Seller" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'seller.label', default: 'Seller')}"/>
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
                    <h1 class="page-header">seller</h1>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12">
                    <g:link controller="seller" action="create">create new</g:link>
                </div>
            </div>

            <div id="list-seller" class="table-responsive">
                <g:if test="${flash.message}">
                    <div class="message" role="status">${flash.message}</div>
                </g:if>
                <table class="table table-striped">
                    <thead>
                    <tr>
                        
                        <th><g:message code="seller.name.label" default="Name" /></th>
                        
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${sellerInstanceList}" status="i" var="sellerInstance">
                        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                            
                            <td><g:link action="show"
                                        id="${sellerInstance.id}">${fieldValue(bean: sellerInstance, field: "name")}</g:link></td>
                            
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
