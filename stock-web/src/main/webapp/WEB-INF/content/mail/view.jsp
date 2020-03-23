<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<link type="text/css" rel="stylesheet" href="css/validate/validate.css" />
<script type="text/javascript" src="js/passport/passportAdd.js"></script>

</head>


<body>

	<div class="right clear">
		<div id="nav_cont">
			<h1 id="tag_1" class="cont_tag_show"></h1>
			<form id="form1" method="post" action="../../omButtonServlet">
				<input type="hidden" name="id"
					value="${mailInfo.id }">
				<table width="100%" border="0" class="grid_layout" cellspacing="0">
					<tr class="style1">
						<td align="right" width="15%"><span class="color_red">*</span>邮件服务名称：</td>
						<td colspan="3" width="65%"><input type="text" name="name"
							id="name" class="input_text" value="${mailInfo.name }"/> <span class="errorImg"></span><span
							class="errorMsg"></span></td>
					</tr>
					<tr>
						<td align="right"><span class="color_red">*</span>发送者邮箱：</td>
						<td colspan="3"><input type="text" id="sender" name="sender"
							class="input_text" value="${mailInfo.sender }"/> <span class="errorImg"></span><span
							class="errorMsg"></span></td>
					</tr>
					<tr>
						<td align="right"><span class="color_red">*</span>邮件服务地址：</td>
						<td colspan="3"><input type="text" name="serverAddr"
							id="serverAddr" class="input_text" value="${mailInfo.serverAddr }"/> <span class="errorImg"></span><span
							class="errorMsg"></span></td>
					</tr>
					<tr>
						<td align="right"><span class="color_red">*</span>邮件服务端口：</td>
						<td><input name="port" value="${mailInfo.port }"/> <span class="errorImg"></span><span
							class="errorMsg"></span></td>
					</tr>
					<tr>
						<td align="right">账户：</td>
						<td colspan="3"><input type="text" name="userName" id="userName"
							class="input_text" value="${mailInfo.userName }"/> <span class="errorImg"></span><span
							class="errorMsg"></span></td>
					</tr>
					<tr>
						<td align="right">密码：</td>
						<td colspan="3"><input type="password" id="password"
							name="password"  value="${mailInfo.password }"/> <span class="errorImg"></span><span
							class="errorMsg"></span></td>
					</tr>
					<tr>
						<td align="right">是否验证：</td>
						<td colspan="3"><input id="isauth" name="isauth" type="checkbox"
                               <c:if test="${mailInfo.auth}">checked="checked"</c:if>  /><span
							class="errorMsg"></span></td>
					</tr>
					<tr>
						<td align="right">验证：</td>
						<td colspan="3"><a href="javascript:void(0);" onclick="validateConnection()">验证链接</a></td>
					</tr>
					
				</table>
				<div class="separator"></div>
				<div class="text_align_c pad ">
					<button type="submit" class="button_u"
						onmousemove="this.className='button_f'"
						onmousedown="this.className='button_d'"
						onmouseout="this.className='button_u'">提交</button>
					&nbsp;
				</div>
			</form>
		</div>
	</div>

</body>
</html>