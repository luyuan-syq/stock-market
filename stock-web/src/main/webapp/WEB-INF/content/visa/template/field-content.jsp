<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta name="decorator" content="none" />


<script type="text/javascript">
	$(function() {
		new SYQ.FieldContent({
			el : $("#field-category"),
			categoryId : '${categoryId}',
			getCategoryFieldUrl : './visaTemplate/getCategoryFieldInfo?code=${code}&categoryId=${categoryId}',
			deleteFieldsUrl : './visaTemplate/deleteFields?code=${code}&categoryId=${categoryId}',
			getFieldCreatePageUrl : './visaTemplate/getFieldCreatePage?code=${code}&categoryId=${categoryId}',
			getFieldUpdatePageUrl : './visaTemplate/getFieldUpdatePage?code=${code}&categoryId=${categoryId}'
		});
	});
</script>

<div id="field-category">
	<div id="field-content">
		<div class="field-content-action default-bar-space"></div>
		<table></table>
	</div>
</div>
<div>

</div>
<div class="default-value-item-template display-none">
	<div class="default-value-item">
		<button type="button" class="default-value-delete-btn"
			onclick="deleteDefaultValue(event)"></button>
		<input type="text" value="" name="defaultValues" theme="simple">
		<select name="checkeds" theme="simple">
		  <option value="false">不勾选</option>
		  <option value="true">勾选</option>
		</select>
	</div>
</div>