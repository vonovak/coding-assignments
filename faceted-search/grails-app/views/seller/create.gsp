<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'seller.label', default: 'Seller')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>

	<body>

	<div class="container-fluid">
	<div class="row">

		<div id="create-seller" class="col-md-10 col-md-offset-1 main" role="main">
			<div class="row">
				<div class="col-xs-12">
					<h1 class="page-header"><g:message code="default.create.label" args="[entityName]" /></h1>
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
			<g:form url="[resource:sellerInstance, action:'save']" >
			<fieldset class="form">
				<g:render template="form"/>
			</fieldset>
			<fieldset class="buttons">
				<g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
			</fieldset>
			</g:form>
		</div>
		</div>
	</div>

	</body>
</html>
