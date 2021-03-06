<%@ page import="phonedb.Seller" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'seller.label', default: 'Seller')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>

	<div class="container-fluid">
		<div class="row">

		<div id="edit-seller" class="col-md-10 col-md-offset-1 main" role="main">
			<div class="row">
				<div class="col-xs-12">
					<h1 class="page-header"><g:message code="default.edit.label" args="[entityName]" /></h1>
				</div>
			</div>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${sellerInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${sellerInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form url="[resource:sellerInstance, action:'update']" method="PUT" >
				<g:hiddenField name="version" value="${sellerInstance?.version}" />
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				<fieldset class="buttons">
					<g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
				</fieldset>
			</g:form>
		</div>

	</div>
		</div>


	</body>
</html>
