<%@ page import="phonedb.Phone" %>



<div class="row ${hasErrors(bean: phoneInstance, field: 'name', 'error')} required">
	<div class="col-lg-3">
		<label for="name">
			<g:message code="phone.name.label" default="Name" />
			<span class="required-indicator">*</span>
		</label>
	</div>
	<div class="div-lg-4"><g:textField name="name" required="" value="${phoneInstance?.name}"/>
</div>
</div>

<div class="row ${hasErrors(bean: phoneInstance, field: 'brand', 'error')} required">
	<div class="col-lg-3">
		<label for="brand">
			<g:message code="phone.brand.label" default="Brand" />
			<span class="required-indicator">*</span>
		</label>
	</div>
	<div class="div-lg-4"><g:select id="brand" name="brand.id" from="${phonedb.Brand.list()}" optionKey="id" required="" value="${phoneInstance?.brand?.id}" class="many-to-one"/>
</div>
</div>

<div class="row ${hasErrors(bean: phoneInstance, field: 'color', 'error')} required">
	<div class="col-lg-3">
		<label for="color">
			<g:message code="phone.color.label" default="Color" />
			<span class="required-indicator">*</span>
		</label>
	</div>
	<div class="div-lg-4"><g:select id="color" name="color.id" from="${phonedb.Color.list()}" optionKey="id" required="" value="${phoneInstance?.color?.id}" class="many-to-one"/>
</div>
</div>

<div class="row ${hasErrors(bean: phoneInstance, field: 'ram', 'error')} required">
	<div class="col-lg-3">
		<label for="ram">
			<g:message code="phone.ram.label" default="Ram" />
			<span class="required-indicator">*</span>
		</label>
	</div>
	<div class="div-lg-4"><g:field name="ram" type="number" value="${phoneInstance.ram}" required=""/>
</div>
</div>

<div class="row ${hasErrors(bean: phoneInstance, field: 'screenSize', 'error')} required">
	<div class="col-lg-3">
		<label for="screenSize">
			<g:message code="phone.screenSize.label" default="Screen Size" />
			<span class="required-indicator">*</span>
		</label>
	</div>
	<div class="div-lg-4"><g:field name="screenSize" value="${fieldValue(bean: phoneInstance, field: 'screenSize')}" required=""/>
</div>
</div>

<div class="row ${hasErrors(bean: phoneInstance, field: 'sellers', 'error')} ">
	<div class="col-lg-3">
		<label for="sellers">
			<g:message code="phone.sellers.label" default="Sellers" />
			
		</label>
	</div>
	<div class="div-lg-4">
		<g:select name="sellers" from="${phonedb.Seller.list()}" multiple="multiple" optionKey="id" size="5" value="${phoneInstance?.sellers*.id}" class="many-to-many"/>
</div>
</div>

<div class="row ${hasErrors(bean: phoneInstance, field: 'storage', 'error')} required">
	<div class="col-lg-3">
		<label for="storage">
			<g:message code="phone.storage.label" default="Storage" />
			<span class="required-indicator">*</span>
		</label>
	</div>
	<div class="div-lg-4"><g:field name="storage" type="number" value="${phoneInstance.storage}" required=""/>
</div>
</div>

