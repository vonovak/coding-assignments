
<%@ page import="phonedb.Seller" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'seller.label', default: 'Seller')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>

<body>

<div class="container-fluid">
	<div class="row">

		<div id="show-seller" class="col-md-10 col-md-offset-1 main" role="main">
			<div class="row">
				<div class="col-xs-12">
					<h1 class="page-header"><g:message code="default.show.label" args="[entityName]" /></h1>
				</div>
			</div>

			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
					<ol class="property-list seller">
					
						<g:if test="${sellerInstance?.name}">
						<li class="fieldcontain">
							<span id="name-label" class="property-label"><g:message code="seller.name.label" default="Name" /></span>
							
								<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${sellerInstance}" field="name"/></span>
							
						</li>
						</g:if>
					
						<g:if test="${sellerInstance?.phones}">
						<li class="fieldcontain">
							<span id="phones-label" class="property-label"><g:message code="seller.phones.label" default="Phones" /></span>

							<ul>
								<g:each in="${sellerInstance.phones}" var="p">
								<li><g:link controller="phone" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
								</g:each>
							</ul>
						</li>
						</g:if>
					
					</ol>
				%{--<g:form url="[resource:sellerInstance, action:'delete']" method="DELETE">--}%
					%{--<fieldset class="buttons">--}%
						%{--<g:link class="edit" action="edit" resource="${sellerInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>--}%
						%{--<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />--}%
					%{--</fieldset>--}%
				%{--</g:form>--}%
		</div>
	</div>
</div>


</body>
</html>
