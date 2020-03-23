<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<base href="<%=basePath %>" />
<c:if test="${none == 1 }">
	<meta name="decorator" content="none" />
	<jsp:include page="../base/headerResource.jsp" />
</c:if>

<script type="text/javascript" src="js/task/taskAddPersons.js"></script>
</head>
<body>

	<div id="aps-console-account-changePassword"
		class="aps-console-account" style="padding-top: 30px">
		<input id = "hasApplyIds" type = "hidden"  value = "${hasApplyPersonIds}"/>
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
					<td class="second"><span>${task.taskCountryName}</span></td>
				</tr>
				<tr>
					<td class="first">添加出差人员：</td>
					<td class="second" colspan="2">
						<div id="selectPersonList"
							style="margin-left: 30px; margin-top: 20px">
							<input type="button" id="addAccount" value="新增" /> <input
								type="button" id="deleteAccount" value="删除" />
							<table id="selectPersonListGrid"></table>
						</div>
					</td>
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

	<!-- 创建出差人员 -->
	<div id="create-person-dialog">
		<form id="account-info-form" class="aps-form"
			enctype="multipart/form-data" method="post">
			<table>
				<tr>
					<td class="first">姓名：</td>
					<td class="second"><input name="userName"> <span
						class="require-mark">*</span></td>
					<td class="thrid"><label class="aps-form-prompt">字母数字或下划线组成</label>
						&nbsp;</td>
				</tr>
				<tr>
					<td class="first">身份证号：</td>
					<td class="second"><input name="idNumber"> <span
						class="require-mark">*</span></td>
					<td class="thrid"><label class="aps-form-prompt">字母数字或下划线组成</label>
						&nbsp;</td>
				</tr>
				<tr>
					<td class="first">性别：</td>
					<td class="second">
					    <label><input name="sexey" type="radio" value="0" checked="true">男</label>
					    <label><input name="sexey" type="radio" value="1">女</label>   
					    <span class="require-mark">*</span></td>
					<td class="thrid"><label class="aps-form-prompt">字母数字或下划线组成</label>
						&nbsp;</td>
				</tr>
				<tr>
					<td class="first">出生日期：</td>
					<td class="second">
                        <input id= "birthday" name="birthday" style="width: 138px">
					    <span class="require-mark">*</span></td>
					<td class="thrid"><label class="aps-form-prompt">字母数字或下划线组成</label>
						&nbsp;</td>
				</tr>
				<tr>
					<td class="first">出生地：</td>
					<td class="second">
                        <input name="placeBirth">
					    <span class="require-mark">*</span></td>
					<td class="thrid"><label class="aps-form-prompt">字母数字或下划线组成</label>
						&nbsp;</td>
				</tr>				
				<tr>
					<td class="first">工作单位：</td>
					<td class="second"><input name="deptName"> <span
						class="require-mark">*</span></td>
					<td class="thrid"><label class="aps-form-prompt">字母数字或下划线组成</label>
						&nbsp;</td>
				</tr>
				<tr>
					<td class="first">职务：</td>
					<td class="second"><input name="business"> <span
						class="require-mark">*</span></td>
					<td class="thrid"><label class="aps-form-prompt">字母数字或下划线组成</label>
						&nbsp;</td>
				</tr>				
				<tr>
					<td class="first">对外身份：</td>
					<td class="second">
					<select id = "identity" name = "identity" style="width:100px;">
                        <option value="2">团员</option>
                        <option value="3">团长</option>
                    </select>  
					<span class="require-mark">*</span></td>
					<td class="thrid"><label class="aps-form-prompt">例如：010-1234567</label>
						&nbsp;</td>
				</tr>
			</table>
			<div id="iconInfoTip" style="display: none;"></div>
		</form>

	</div>
	<script type="text/javascript">
		$(document).ready(function() {

			$('#container_start').omCalendar({
				dateFormat : "yy-mm-dd",
			});
			$('#container_end').omCalendar({
				dateFormat : "yy-mm-dd",
			});
		});
	</script>

</body>
</html>