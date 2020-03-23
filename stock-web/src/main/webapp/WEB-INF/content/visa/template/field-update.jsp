<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta name="decorator" content="none" />

<c:set var="updateFieldUrl" value="./visaTemplate/updateField"></c:set>

<script type="text/javascript">
	var fieldUpdate;
	$(function() {
		 fieldUpdate = new SYQ.FieldUpdate({
			el : $('#field-update')
		});
	});
</script>

<div id="field-update">
	<form action="${updateFieldUrl }" id="fieldUpdateForm"
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
				<td class="second"><input name="fieldId" value="${field.id }" /></td>
				<td class="third">&nbsp;</td>
			</tr>
			<tr>
				<td class="first"><span>名称：</span></td>
				<td class="second"><input name="fieldName"
					value="${field.name}" /> <span class="require-mark">*</span></td>
				<td class="third">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="3">
					<div class="table-title closed-top-title">类型配置</div>
				</td>
			</tr>
			<tr>
				<td class="first"><span>文本类型：</span></td>
				<td class="second"><c:if test="${'textInput' == field.type}">
						<span>文本输入</span>
					</c:if> <c:if test="${'textSelect' == field.type}">
						<span>文本选择</span>
					</c:if> <input type="hidden" name="fieldType" value="${field.type}" /></td>
				<td class="third">&nbsp;</td>
			</tr>
			<tr>
				<td class="first"><span>显示类型：</span></td>
				<td class="second"><c:if test="${'textInput' == field.type}">

						<select name="displayType"
							onchange="changeTextInputDefaultValueDom(event);">
							<c:forEach items="text:单行文本,textarea:多行文本" var="item">
								<c:if test="${item.split(':')[0] == field.displayType }">
									<option value="${item.split(':')[0]}" selected="selected">${item.split(':')[1]}</option>
								</c:if>
								<c:if test="${item.split(':')[0] != field.displayType }">
									<option value="${item.split(':')[0]}">${item.split(':')[1]}</option>
								</c:if>
							</c:forEach>
						</select>
					</c:if> 
					<c:if test="${'textSelect' == field.type}">

						<select name="displayType">
							<c:forEach items="radio:单选项,checkbox:多选项,select:选择列表" var="item">
								<c:if test="${item.split(':')[0] == field.displayType }">
									<option value="${item.split(':')[0]}" selected="selected">${item.split(':')[1]}</option>
								</c:if>
								<c:if test="${item.split(':')[0] != field.displayType }">
									<option value="${item.split(':')[0]}">${item.split(':')[1]}</option>
								</c:if>
							</c:forEach>
						</select>
					</c:if></td>
				<td class="third">&nbsp;</td>
			</tr>
			<tr>
				<td class="first"><span>默认值：</span></td>
				<td class="second" colspan="2">
					<div template="default-value">
						<c:if test="${'textInput' == field.type}">
							<div class="default-value-content">
								<c:if test="${'text' == field.displayType}">
									<input name="defaultValues" value="${field.values[0].value}" >
								</c:if>
								<c:if test="${'textarea' ==field.displayType}">
									<textarea rows="30" cols="30" name="defaultValues">${field.values[0].value}</textarea>
								</c:if>
								<select name="checkeds"  style="display: none">
									<c:forEach items="false:不勾选,true:勾选" var="item">
										<option value="${item.split(':')[0]}">${item.split(':')[1]}</option>
								   </c:forEach>
								 </select>
							</div>
						</c:if>
						<c:if test="${'textSelect' == field.type}">
							<div class="default-value-content">
							    <c:forEach items="${field.values }" var="defaultValue">
							    	<div class="default-value-item">
										<button type="button" class="default-value-delete-btn"
											onclick="deleteDefaultValue(event);"></button>
										<input name="defaultValues" value="${defaultValue.value }">
										<select name="checkeds"  style="display: none">
											<c:forEach items="false:不勾选,true:勾选" var="item">
											    <c:if test="${item.split(':')[0] == defaultValue.checked }">
											        <option value="${item.split(':')[0]}" selected="selected">${item.split(':')[1]}</option>
											    </c:if>
											    <c:if test="">
													<option value="${item.split(':')[0]}">${item.split(':')[1]}</option>
											    </c:if>
										   </c:forEach>
										 </select>
									</div>
							    </c:forEach>
							</div>
							<div class="default-value-action">
								<button type="button" class="default-value-create-btn"
									onclick="createDefaultValue(event);">添加</button>
							</div>
						</c:if>
					</div>
				</td>
			</tr>
			<c:if test="${'textInput' == field.type}">
				<tr>
					<td class="first"><span>校验器：</span></td>
					<td class="second">
						<div template="validate-rule">
							<select name="validateRule">
								<option value="" <c:if test="${field.validate.value == '' }">selected="selected" </c:if>>任意字符</option>
								<option value="^[A-Za-z0-9_]{6,30}$" <c:if test="${field.validate.value == '^[A-Za-z0-9_]{6,30}$' }">selected="selected" </c:if> >数字字母下划线组成长度为6至30</option>
								<option value="^1[0-9]{10}$" <c:if test="${field.validate.value == '^1[0-9]{10}$' }">selected="selected" </c:if>>手机号码格式:13800138000</option>
								<option value="^[0-9]{3}-[0-9]{7,8}|[0-9]{4}-[0-9]{7,8}$" <c:if test="${field.validate.value == '^[0-9]{3}-[0-9]{7,8}|[0-9]{4}-[0-9]{7,8}$' }">selected="selected" </c:if>>固话格式:0755-22228888</option>
								<option value=".+@.+\\\..+" <c:if test="${field.validate.value == '.+@.+\\\..+' }">selected="selected" </c:if>>邮箱格式:example@example.com</option>
								<option value="^[1-9]d{5}$" <c:if test="${field.validate.value == '^[1-9]d{5}$' }">selected="selected" </c:if>>邮编格式:523000</option>
							</select>
						</div>
						<div class="validate-description display-none">
							<textarea rows="50" cols="50" name="validateDescription">${field.validate.description }</textarea>
						</div>
					</td>
					<td class="third">&nbsp;</td>
				</tr>
			</c:if>
			<tr>
				<td colspan="3">
					<div class="table-title closed-top-title">属性配置</div>
				</td>
			</tr>

			<tr>
				<td class="first"><span>启用：</span></td>
				<td class="second">
				 <input type="checkbox" name="visible"  <c:if test="${field.visible }">checked="checked"</c:if>/>
				</td>
				<td class="third">&nbsp;</td>
			</tr>
			<tr>
				<td class="first"><span>必填：</span></td>
				<td class="second">
				   <input type="checkbox" name="required"  <c:if test="${field.required }">checked="checked"</c:if>/>		
				</td>
				<td class="third">&nbsp;</td>
			</tr>
			<tr>
				<td class="first"><span>可编辑：</span></td>
				<td class="second">
				  <input type="checkbox" name="editable"  <c:if test="${field.editable }">checked="checked"</c:if>/>
				</td>
				<td class="third">&nbsp;</td>
			</tr>
			<tr>
				<td class="first"><span>列可见：</span></td>
				<td class="second">
				    <input type="checkbox" name="columnVisible"  <c:if test="${field.columnVisible }">checked="checked"</c:if>/>		
				</td>
				<td class="third">&nbsp;</td>
			</tr>
		</table>
	</form>

</div>