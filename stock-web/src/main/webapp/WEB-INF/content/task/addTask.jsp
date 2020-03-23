<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<html>
<head>
<link type="text/css" rel="stylesheet" href="css/sso/sso.css" />
<script type="text/javascript" src="js/task/taskAddNew.js"></script>
</head>
<body>


    <input type="hidden" id="basePath" value="<%=basePath %>" />
	<div id="aps-console-account-changePassword"
		class="aps-console-account" style="padding-top: 30px">
		<form id="task-add-form" class="aps-form"
			enctype="multipart/form-data" method="post">
			<table>
				<tr>
					<td class="first">任务编码：</td>
					<td class="second"><input name="taskCode" value="${code}"
						readonly="readonly" style="width: 50%" /> <span
						class="require-mark">*</span>&nbsp;&nbsp;<input type="button"
						class="input-button" value="生成" id="generateTaskCode" /></td>
					<td class="thrid"><label class="aps-form-prompt"></label>
						&nbsp;</td>
				</tr>
				<tr>
					<td class="first">批件号：</td>
					<td class="second"><input name="instructionNo"
						style="width: 90%" /> <span class="require-mark">*</span></td>
					<td class="thrid"><label class="aps-form-prompt"></label>
						&nbsp;</td>
				</tr>
				<tr>
					<td colspan="3">
						<div class="table-title">&nbsp;</div>
					</td>
				</tr>
				<%@include file="./saveButton.jsp"%>
			</table>
			<div id="iconInfoTip" style="display: none;"></div>
		</form>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {

			$('#container_start').omCalendar({
				dateFormat : "yy-mm-dd H:i:s",
				showTime : true
			});
			$('#container_end').omCalendar({
				dateFormat : "yy-mm-dd H:i:s",
				showTime : true
			});
		});
	</script>

</body>
</html>