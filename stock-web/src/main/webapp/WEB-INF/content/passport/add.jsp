<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link type="text/css" rel="stylesheet" href="css/validate/validate.css" />
<script type="text/javascript" src="js/passport/passportAdd.js"></script>
</head>
<body>
	<div class="right clear">
		<div id="nav_cont">
			<h1 id="tag_1" class="cont_tag_show"></h1>
			<form id="task-add-form" class="aps-form"
				enctype="multipart/form-data" method="post" onsubmit="return false;">
				<input type="hidden" id="method" name="method"
					value="formValidateSubmit"> <input type="hidden" id="id"
					name="id" value="${passport.id}"> <input type="hidden"
					id="taskId" name="taskId" value="${passport.taskId}">
				<table width="100%" border="0" class="grid_layout" cellspacing="0">
					<tr class="style1">
						<td align="right" width="15%"><span class="color_red">*</span>护照编码：</td>
						<td colspan="3" width="65%"><input type="text"
							id="passportNo" name="passportNo" id="fileNo" class="input_text"
							value="" /> <span class="errorImg"></span><span class="errorMsg"></span></td>
					</tr>
					<tr>
						<td align="right"><span class="color_red">*</span>姓名：</td>
						<td colspan="3"><input type="text" id="surname"
							name="surname" class="input_text" value="${passport.name}" />
							<span class="errorImg"></span><span class="errorMsg"></span></td>
					</tr>
					<tr>
						<td align="right"><span class="color_red">*</span>性别：</td>
						<td><select id="sex" name="sex" class="input-text"
							style="height: 25px">
								<option value="0"
									<c:if test="${passport.sex == 0}"> selected="selected" </c:if>>男</option>
								<option value="1"
									<c:if test="${passport.sex == 1}"> selected="selected" </c:if>>女</option>
						</select> <span class="errorImg"></span> <span class="errorMsg"></span></td>
					</tr>
					<tr>
						<td align="right"><span class="color_red">*</span>身份证号：</td>
						<td colspan="3"><input type="text" name="idNumber"
							id="idNumber" class="input_text" value="${passport.idNumber}" />
							<span class="errorImg"></span><span class="errorMsg"></span></td>
					</tr>
					<tr>
						<td align="right"><span class="color_red">*</span>颁发日期：</td>
						<td colspan="3"><input type="text" id="dateIssue"
							name="dateIssue" style="color: #aaa;" value="" /> <span
							class="errorImg"></span><span class="errorMsg"></span></td>
					</tr>
					<tr>
						<td align="right"><span class="color_red">*</span>过期时间：</td>
						<td colspan="3"><input type="text" name="dateExpire"
							id="dateExpire" value="" /> <span class="errorImg"></span><span
							class="errorMsg"></span></td>
					</tr>
					<tr>
						<td align="right">籍贯住址：</td>
						<td colspan="3"><input name="placeBirth" id="placeBirth"
							class="input_text" value="${passport.placeBirth}" /> <span
							class="errorImg"></span><span class="errorMsg"></span></td>
					</tr>
					<tr>
						<td align="right">注册地址：</td>
						<td colspan="3"><input type="text" name="placeIssue"
							id="placeIssue" class="input_text" value="${passport.placeIssue}" />
							<span class="errorImg"></span><span class="errorMsg"></span></td>
					</tr>
				</table>
				<div class="separator"></div>
				<div class="text_align_c pad ">
					<button type="submit" class="button_u" id="button_submit"
						onmousemove="this.className='button_f'"
						onmousedown="this.className='button_d'"
						onmouseout="this.className='button_u'">提交表单</button>
					&nbsp;
					<button class="button_u" type="reset" id="button_clear"
						onmousemove="this.className='button_f'"
						onmousedown="this.className='button_d'"
						onmouseout="this.className='button_u'">&nbsp;&nbsp;重置&nbsp;&nbsp;</button>
					&nbsp;
				</div>
			</form>
		</div>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {

			$('#dateIssue').omCalendar({
				dateFormat : "yy-mm-dd",
				showTime : false
			});
			$('#dateExpire').omCalendar({
				dateFormat : "yy-mm-dd",
				showTime : false
			});
		});
	</script>
</body>
</html>