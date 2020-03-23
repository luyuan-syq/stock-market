<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta name="decorator" content="none" />

<c:set var="createFieldUrl" value="./visaTemplate/createField">

</c:set>


<c:set var="textInputDisplayTypeTemplate">
	<select name="displayType"
		onchange="changeTextInputDefaultValueDom(event);">
		<c:forEach items="text:单行文本,textarea:多行文本" var="item">
			<option value="${item.split(':')[0]}">${item.split(':')[1]}</option>
		</c:forEach>
	</select>
</c:set>
<c:set var="textInputDefaultValueTemplate">
	<div class="default-value-content">
		<input name="defaultValues" /> <select name="checkeds"
			style="display: none">
			<c:forEach items="false:不勾选,true:勾选" var="item">
				<option value="${item.split(':')[0]}">${item.split(':')[1]}</option>
			</c:forEach>
		</select>
	</div>
</c:set>

<c:set var="textInputValidateRuleTemplate">
	<select name="validateRule">
		<option value="">任意字符</option>
		<option value="^[A-Za-z0-9_]{6,30}$">数字字母下划线组成长度为6至30</option>
		<option value="^1[0-9]{10}$">手机号码格式:13800138000</option>
		<option value="^[0-9]{3}-[0-9]{7,8}|[0-9]{4}-[0-9]{7,8}$">固话格式:0755-22228888</option>
		<option value=".+@.+\\\..+">邮箱格式:example@example.com</option>
		<option value="^[1-9]d{5}$">邮编格式:523000</option>
	</select>
</c:set>
<%-- 

--%>

<script type="text/javascript">
   var fieldCreate;
	$(function() {
		fieldCreate = new SYQ.FieldCreate({
			el : $('#field-create'),
			isFieldExistedUrl : '${isFieldExistedUrl}'
		});
	});
</script>

<div id="field-create">
	<%-- 这里定义了字段新增时用到的dom模板 --%>
	<div class="field-templates display-none">
		<%-- 文本类型模板 --%>
		<div class="textInput-display-type-template">
			${textInputDisplayTypeTemplate }</div>
		<div class="textSelect-display-type-template">
			<select name="displayType">
				<c:forEach items="radio:单选项,checkbox:多选项,select:选择列表" var="item">
					<option value="${item.split(':')[0]}">${item.split(':')[1]}</option>
				</c:forEach>
			</select>
		</div>
		<%-- 默认值模板 --%>
		<div class="textInput-default-value-template">
			${textInputDefaultValueTemplate}</div>
		<div class="textSelect-default-value-template">
			<div class="default-value-content">
				<div class="default-value-item">
					<button type="button" class="default-value-delete-btn"
						onclick="deleteDefaultValue(event);"></button>
					<input name="defaultValues" maxlength="1000" />
					<select name="checkeds" >
						<c:forEach items="false:不勾选,true:勾选" var="item">
							<option value="${item.split(':')[0]}">${item.split(':')[1]}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="default-value-action">
				<button type="button" class="default-value-create-btn"
					onclick="createDefaultValue(event);">添加</button>
			</div>
		</div>
		<%-- 校验器模板 --%>
		<div class="textInput-validate-rule-template">
			${textInputValidateRuleTemplate }</div>
		<div class="textSelect-validate-rule-template">
			<select name="validateRule"></select>
		</div>
	</div>
	<%-- 通过form表单提交新增字段 --%>
	<form action="${createFieldUrl }" id="fieldCreateForm"
		class="aps-form aps-form-min user-field-form" method="post">
		<input type="hidden" name="categoryId" value="${categoryId }"/>
		<input type="hidden" name="code" value="${code }"/>
		<table>
			<tr>
				<td colspan="3">
					<div class="table-title closed-top-title">基本信息</div>
				</td>
			</tr>
			<tr>
				<td class="first"><span>简称：</span></td>
				<td class="second"><input name="fieldId" /> <span
					class="require-mark">*</span></td>
				<td class="third"><label class="aps-form-prompt">字母数字或下划线组成</label>
				</td>
			</tr>
			<tr>
				<td class="first"><span>名称：</span></td>
				<td class="second"><input name="fieldName" /> <span
					class="require-mark">*</span></td>
				<td class="third">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="3">
					<div class="table-title closed-top-title">类型配置</div>
				</td>
			</tr>
			<tr>
				<td class="first"><span>文本类型：</span></td>
				<td class="second"><select name="fieldType">
						<c:forEach items="textInput:文本输入,textSelect:文本选择"
							var="item">
							<option value="${item.split(':')[0]}">${item.split(':')[1]}</option>
						</c:forEach>
				</select></td>
				<td class="third"></td>
			</tr>
			<tr>
				<td class="first"><span>显示类型：</span></td>
				<td class="second">
					<div template="display-type">${textInputDisplayTypeTemplate }
					</div>
				</td>
				<td class="third">&nbsp;</td>
			</tr>
			<tr>
				<td class="first"><span>默认值：</span></td>
				<td class="second" colspan="2">
					<div template="default-value">
						${textInputDefaultValueTemplate}</div>
				</td>
			</tr>
			<tr>
				<td class="first"><span>校验器：</span></td>
				<td class="second">
					<div template="validate-rule">
						${textInputValidateRuleTemplate }</div>
					<div class="validate-description display-none">
						<input name="validateDescription" value="任意字符" />
					</div>
				</td>
				<td class="third">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="3">
					<div class="table-title closed-top-title">属性配置</div>
				</td>
			</tr>
			<tr>
				<td class="first"><span>启用：</span></td>
				<td class="second"><input type="checkbox" name="visible" /></td>
				<td class="third">&nbsp;</td>
			</tr>
			<tr>
				<td class="first"><span>必填：</span></td>
				<td class="second"><input type="checkbox" name="required" /></td>
				<td class="third">&nbsp;</td>
			</tr>
			<tr>
				<td class="first"><span>可编辑：</span></td>
				<td class="second"><input type="checkbox" name="editable" /></td>
				<td class="third">&nbsp;</td>
			</tr>
			<tr>
				<td class="first"><span>列可见：</span></td>
				<td class="second"><input type="checkbox" name="columnVisible" />
				</td>
				<td class="third">&nbsp;</td>
			</tr>
		</table>
	</form>

</div>