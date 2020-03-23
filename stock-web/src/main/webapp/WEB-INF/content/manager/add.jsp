<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<script type="text/javascript"
	src="js/jqueryplugins/jstree_pre1.0_stable/jquery.jstree.js"></script>
<script type="text/javascript" src="js/role/roleAdd.js"></script>
</head>
<body>
	<form id="role-info-form" class="aps-form"
		enctype="multipart/form-data" method="post">
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
				<td class="second"><input type="button"
					onclick="openSecurity()" value="功能权限"></td>
			</tr>
		</table>
		<div id="iconInfoTip" style="display: none;"></div>
		<div class="separator"></div>
		<div class="text_align_c pad ">
			<button type="button" class="button_u"
				onmousemove="this.className='button_f'"
				onmousedown="this.className='button_d'"
				onmouseout="this.className='button_u'" onclick="addSecurityRole()">提交表单</button>
			&nbsp;
			<button class="button_u" type="reset"
				onmousemove="this.className='button_f'"
				onmousedown="this.className='button_d'"
				onmouseout="this.className='button_u'">&nbsp;&nbsp;重置&nbsp;&nbsp;</button>
			&nbsp;
		</div>
	</form>
	<div id="giveRight" title="功能权限树">
		<div id="rightTree123"></div>
	</div>

</body>
</html>