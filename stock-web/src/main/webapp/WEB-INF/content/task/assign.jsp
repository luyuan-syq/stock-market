<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<script type="text/javascript" src="js/task/taskAssign.js"></script>
</head>
<body>

	<div id="aps-console-account-changePassword"
		class="aps-console-account" style="padding-top: 30px">
		<form id="task-assign-form" class="aps-form"
			enctype="multipart/form-data" method="post">
			<input type="hidden" value="${task.id }" id="taskId"/>
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
					<td class="first">添加外事人员：</td>
					<td class="second" colspan="2">
						<div id="selectAccountList" >
							<%-- <input type="button" id="addAccount" value="新增" />--%> <input
								type="button" id="deleteAccount" value="删除" />
							<table id="selectAccountListGrid"></table>
						</div>

						<div id="accountList" style="margin-top: 20px">
							<table id="accountListGrid"></table>
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