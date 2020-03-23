<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<c:if test="${none == 1 }">
	<meta name="decorator" content="none" />
	<jsp:include page="../base/headerResource.jsp" />
</c:if>
<link rel="stylesheet" type="text/css"
	href="css/omui/css/apusic/om-apusic.css" />
<link type="text/css" rel="stylesheet" href="css/sso/sso.css" />
<link type="text/css" rel="stylesheet" href="css/faff/visa.css" />
<script type="text/javascript" src="js/task/taskBaseInfo.js"></script>
</head>
<body>

	<div id="aps-console-account-changePassword"
		class="aps-console-account" style="padding-top: 30px">
			<input type="hidden" id="taskStatus" value="${task.status}" />
			<input type="hidden" id="taskId" value="${task.id}" />
			<table>
				<tr>
					<td class="first" height="30px">批件号：</td>
					<td class="second"><input name="instructionNo"
						value="${task.instructionNo}" disabled /></td>
					<td class="thrid"><label class="aps-form-prompt"></label>
						&nbsp;</td>
				</tr>
				<tr>
					<td class="first" height="30px">外事计划项目号：</td>
					<td class="second"><input name="faffPlanNo"
						value="${task.faffPlanNo}" disabled/></td>
					<td class="thrid"><label class="aps-form-prompt"></label>
						&nbsp;</td>
				</tr>
				<tr>
					<td class="first" height="30px">任务名称：</td>
					<td class="second"><input name="taskName"
						value="${task.taskName}" disabled/></td>
					<td class="thrid"><label class="aps-form-prompt"></label>
						&nbsp;</td>
				</tr>
				<tr>
					<td class="first" height="30px">任务状态：</td>
					<td class="second"><input name="statusStr" 
					value="${task.statusStr}" disabled/></td>
					<td class="thrid"><label class="aps-form-prompt"></label>
						&nbsp;</td>
				</tr>
				<tr>
				</tr>
				<tr>
					<td colspan="3">
						<div class="table-title">&nbsp;</div>
					</td>
				</tr>
				<tr>
				<td class="first" height="30px"></td>
					<td class="second">
					 <div id = "taskOpDiv">
					 </div>
					</td>
					<td class="thrid"><label class="aps-form-prompt"></label>
						&nbsp;</td>
				</tr>
			</table>
			<div id="iconInfoTip" style="display: none;"></div>
	</div>
	</body>
</html>