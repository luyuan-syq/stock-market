<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
	<meta name="decorator" content="none" />
</head>
<body>
	<form id="role-info-form" class="aps-form" enctype="multipart/form-data"
		method="post">
		<table>
			<tr>
				<td class="first">角色名称：</td>
				<td class="second"><input name="name"> <span
					class="require-mark">*</span></td>
				<td class="thrid"><label class="aps-form-prompt">字母数字或下划线组成</label>
					&nbsp;</td>
			</tr>
			<tr>
				<td class="first">描述：</td>
				<td class="second"><textarea name="description"> </textarea></td>
				<td class="thrid"></td>
			</tr>
			<tr>
				<td class="first">功能权限：</td>
				<td class="second"><input name="mobile"> <span
					class="require-mark"></span></td>
				<td class="thrid"><label class="aps-form-prompt"></label>
					&nbsp;</td>
			</tr>
		</table>
		<div id="iconInfoTip" style="display: none;"></div>
	</form>

</body>
</html>