<%@ page import="phonedb.Seller" %>



<div class="row ${hasErrors(bean: sellerInstance, field: 'name', 'error')} required">
	<div class="col-lg-3">
		<label for="name">
			<g:message code="seller.name.label" default="Name" />
			<span class="required-indicator">*</span>
		</label>
	</div>
	<div class="div-lg-4"><g:textField name="name" required="" value="${sellerInstance?.name}"/>
</div>
</div>

<div class="row ${hasErrors(bean: sellerInstance, field: 'phones', 'error')} ">
	<div class="col-lg-3">
		<label for="phones">
			<g:message code="seller.phones.label" default="Phones" />
			
		</label>
	</div>
	<div class="div-lg-4"><g:select name="phones" from="${phonedb.Phone.list()}" multiple="multiple" optionKey="id" size="5" value="${sellerInstance?.phones*.id}" class="many-to-many"/>
</div>
</div>

