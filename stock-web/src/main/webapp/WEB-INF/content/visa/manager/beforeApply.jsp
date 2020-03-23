<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="noNeedVisa" value="0"></c:set>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<base href="<%=basePath %>" />
<c:if test="${none == 1 }">
	<meta name="decorator" content="none" />
	<jsp:include page="../../base/headerResource.jsp" />
</c:if>

<script type="text/javascript" src="js/visa/taskApplyVisa.js"></script>
</head>
<body>

	<div id="aps-console-account-changePassword"
		class="aps-console-account" style="padding-top: 30px">
		<form id="task-assign-form" class="aps-form"
			enctype="multipart/form-data" method="post">
			<input type="hidden" value="${task.id }" id="taskId" />
			<table>
				<tr>
					<td class="first">任务编码：</td>
					<td class="second"><span>${task.taskCode}</span></td>
				</tr>
				<tr>
					<td class="first">任务名称：</td>
					<td class="second"><span>${task.taskName}</span></td>
				</tr>
				<tr>
					<td class="first">团长姓名：</td>
					<td class="second"><span>${task.headerName}</span></td>
				</tr>
				<tr>
					<td class="first">任务开始日期：</td>
					<td class="second"><span>${task.taskBeginTimeStr}</span></td>
				</tr>
				<tr>
					<td class="first">任务结束日期：</td>
					<td class="second"><span>${task.taskEndTimeStr }</span></td>
				</tr>
				<tr>
					<td class="first">前往国家：</td>
					<td class="second"><span>
					 <c:forEach items="${task.countrys }" var="country">
					    ${country.name }<c:if test="${country.needVisa == 1}">(签证)</c:if> <c:if test="${country.needVisa == 0}"> <c:set var="noNeedVisa" value="${noNeedVisa+1}"></c:set> </c:if>&nbsp;&nbsp;
					 </c:forEach>
					</span></td>
				</tr>
				<c:if test="${noNeedVisa !=  task.countrys.size()}">
				<tr>
					<td class="first">出差人员：</td>
					<td class="second" colspan="2">
						<div id="selectVisaPersonList"
							style="margin-left: 30px; margin-top: 20px">
							<table id="selectVisaPersonListGrid"></table>
						</div>

					</td>
				</tr>
				</c:if>
				<c:if test="${noNeedVisa ==  task.countrys.size()}">
				<tr>
					<td class="first" colspan="3">无需签证</td>
				</tr>
				</c:if >
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

</body>
</html>