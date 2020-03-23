<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
	<meta name="decorator" content="none" />
</head>
<body>
	<form id="account-info-form" class="aps-form" enctype="multipart/form-data"
		method="post">
		<input type="hidden" name="id" value="${account.id}"/>
		<table>
			<tr>
				<td class="first">姓名：</td>
				<td class="second"><input name="userName" value="${account.userName }"> <span
					class="require-mark">*</span></td>
				<td class="thrid"><label class="aps-form-prompt">字母数字或下划线组成</label>
					&nbsp;</td>
			</tr>
			
			<tr>
				<td class="first">工作单位：</td>
				<td class="second"><input name="deptName"  value="${account.deptName }"> <span
					class="require-mark">*</span></td>
				<td class="thrid"><label class="aps-form-prompt"></label>
					&nbsp;</td>
			</tr>
			<tr>
				<td class="first">电话：</td>
				<td class="second"><input name="contactPhone" value="${account.contactPhone }"> <span
					class="require-mark"></span></td>
				<td class="thrid"><label class="aps-form-prompt">例如：010-1234567</label>
					&nbsp;</td>
			</tr>
			<tr>
				<td class="first">手机：</td>
				<td class="second"><input name="mobile" value="${account.mobile }"> <span
					class="require-mark"></span></td>
				<td class="thrid"><label class="aps-form-prompt"></label>
					&nbsp;</td>
			</tr>
			<tr>
				<td class="first">邮箱：</td>
				<td class="second"><input name="contactEmail" value="${account.contactEmail }"> <span
					class="require-mark"></span></td>
				<td class="thrid"><label class="aps-form-prompt"></label>
					&nbsp;</td>
			</tr>
		</table>
		<div id="iconInfoTip" style="display: none;"></div>
	</form>

</body>
</html>