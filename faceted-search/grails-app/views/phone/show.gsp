
<%@ page import="phonedb.Phone" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'phone.label', default: 'Phone')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>

<body>

<div class="container-fluid">
	<div class="row">

		<div id="show-phone" class="col-md-10 col-md-offset-1 main" role="main">
			<div class="row">
				<div class="col-xs-12">
					<h1 class="page-header"><g:message code="default.show.label" args="[entityName]" /></h1>
				</div>
			</div>

			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
					<ol class="property-list phone">
					
						<g:if test="${phoneInstance?.name}">
						<li class="fieldcontain">
							<span id="name-label" class="property-label"><g:message code="phone.name.label" default="Name" /></span>
							
								<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${phoneInstance}" field="name"/></span>
							
						</li>
						</g:if>
					
						<g:if test="${phoneInstance?.brand}">
						<li class="fieldcontain">
							<span id="brand-label" class="property-label"><g:message code="phone.brand.label" default="Brand" /></span>
							
								<span class="property-value" aria-labelledby="brand-label"><g:link controller="brand" action="show" id="${phoneInstance?.brand?.id}">${phoneInstance?.brand?.encodeAsHTML()}</g:link></span>
							
						</li>
						</g:if>
					
						<g:if test="${phoneInstance?.color}">
						<li class="fieldcontain">
							<span id="color-label" class="property-label"><g:message code="phone.color.label" default="Color" /></span>
							
								<span class="property-value" aria-labelledby="color-label"><g:link controller="color" action="show" id="${phoneInstance?.color?.id}">${phoneInstance?.color?.encodeAsHTML()}</g:link></span>
							
						</li>
						</g:if>
					
						<g:if test="${phoneInstance?.ram}">
						<li class="fieldcontain">
							<span id="ram-label" class="property-label"><g:message code="phone.ram.label" default="Ram" /></span>
							
								<span class="property-value" aria-labelledby="ram-label"><g:fieldValue bean="${phoneInstance}" field="ram"/></span>
							
						</li>
						</g:if>
					
						<g:if test="${phoneInstance?.screenSize}">
						<li class="fieldcontain">
							<span id="screenSize-label" class="property-label"><g:message code="phone.screenSize.label" default="Screen Size" /></span>
							
								<span class="property-value" aria-labelledby="screenSize-label"><g:fieldValue bean="${phoneInstance}" field="screenSize"/></span>
							
						</li>
						</g:if>
					
						<g:if test="${phoneInstance?.sellers}">
						<li class="fieldcontain">
							<span id="sellers-label" class="property-label"><g:message code="phone.sellers.label" default="Sellers" /></span>
							
								<g:each in="${phoneInstance.sellers}" var="s">
								<span class="property-value" aria-labelledby="sellers-label"><g:link controller="seller" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></span>
								</g:each>
							
						</li>
						</g:if>
					
						<g:if test="${phoneInstance?.storage}">
						<li class="fieldcontain">
							<span id="storage-label" class="property-label"><g:message code="phone.storage.label" default="Storage" /></span>
							
								<span class="property-value" aria-labelledby="storage-label"><g:fieldValue bean="${phoneInstance}" field="storage"/></span>
							
						</li>
						</g:if>
					
					</ol>
				%{--<g:form url="[resource:phoneInstance, action:'delete']" method="DELETE">--}%
					%{--<fieldset class="buttons">--}%
						%{--<g:link class="edit" action="edit" resource="${phoneInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>--}%
						%{--<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />--}%
					%{--</fieldset>--}%
				%{--</g:form>--}%
		</div>
	</div>
</div>


</body>
</html>
