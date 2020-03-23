<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<link type="text/css" rel="stylesheet" href="css/sso/sso.css" />
<script type="text/javascript">
	$(function() {
		var buttonTd = $("#buttoTd");
		var taskStatus = $("#taskStatus").val();
		// 新建任务可分配
		if (-1 == taskStatus) {
			buttonTd
					.append("<input id = 'taskAssgin' type = 'button' value = '任务分配'></input>&nbsp;&nbsp;");
			$("#taskAssgin").click(function() {
				var taskId = $("#taskId").val();
				window.location.href = "task/beforeAssign?id=" + taskId;
			})
		}

		buttonTd
				.append("<input id = 'taskBack' type = 'button' value = '返回'></input>");
		$("#taskBack").click(function() {
			window.location.href = "task/list";
		})

		var assignUrl = "task/assign";
		var taskId = $("#taskId").val();
		var hasApplyPersonIds = $("#hasApplyIds").val();
		selectPersonListGrid = $('#selectPersonListGrid').omGrid({
			limit : 0,
			title : '添加出国人员',
			colModel : [ {
				header : '姓名',
				name : 'userName',
				width : 60,
				align : 'center',
				editor : {}
			}, {
				header : '性别',
				name : 'sexey',
				width : 30,
				align : 'center',
				renderer : function(colValue, rowData, rowIndex) {
					if (colValue == 0) {
						return "男";
					} else if (colValue == 1) {
						return "女"
					} else {
						return "未知性别";
					}
				}
			}, {
				header : '出生日期',
				name : 'birthday',
				width : 120,
				align : 'center',
				renderer : function(colValue, rowData, rowIndex) {
					if (colValue == "" || colValue == null) {
						return "";
					}
					return formattime(colValue);
				}
			}, {
				header : '出生地',
				name : 'placeBirth',
				width : 120,
				align : 'center',
				editor : {}
			}, {
				header : '工作单位',
				name : 'deptName',
				width : 200,
				align : 'center',
				editor : {}
			}, {
				header : '身份证号',
				name : 'idNumber',
				width : 160,
				align : 'center',
				editor : {}
			}, {
				header : '职务',
				name : 'business',
				align : 'center',
				width : 60,
				editor : {}
			}, {
				header : '对外身份',
				name : 'identity',
				align : 'left',
				width : 60,
				renderer : function(colValue, rowData, rowIndex) {
					if (colValue == 0) {
						return "组员";
					} else if (colValue == 1) {
						return "组长"
					} else if (colValue == 2) {
						return "团员"
					} else if (colValue == 3) {
						return "团长"
					} else {
						return "未知身份";
					}
				}
			}, {
				header : '是否有集团领导班子成员',
				name : 'isLeader',
				align : 'left',
				width : 140,
				renderer : function(colValue, rowData, rowIndex) {
					if (colValue == 0) {
						return "否";
					} else if (colValue == 1) {
						return "是"
					} else {
						return "未知";
					}
				}
			}, {
				header : '护照审核状态',
				name : 'flowStatus',
				width : '80',
				renderer : function(colValue, rowData, rowIndex) {
					if (colValue == 0) {
						return "新建";
					} else if (colValue == 1) {
						return "待审核";
					} else if (colValue == 2) {
						return "审核拒绝";
					} else if (colValue == 3) {
						return "办理中";
					} else if (colValue == 4) {
						return "已借出";
					} else if (colValue == 5) {
						return "已归档";
					} else {
						return "未知状态";
					}
				}
			}, {
				header : '护照审核信息',
				name : 'flowMsg',
				width : '120'

			} ],
			dataSource : "task/getPersonsByTaskId?taskId=" + taskId,
			onBeforeEdit : function() {
				$('#demo >:button').attr("disabled", true);
			},
			onAfterEdit : function() {
				$('#demo >:button').removeAttr("disabled");
			},
			onCancelEdit : function() {
				$('#demo >:button').removeAttr("disabled");
			}
		});
	})
</script>
</head>
<body>


	<div align="center">
		<div id="aps-console-account-changePassword"
			class="aps-console-account" style="padding-top: 30px">
			<form id="task-add-form" class="aps-form"
				enctype="multipart/form-data" method="post">
				<input id="taskId" type="hidden" name="id" value="${task.id}" /> <input
					id="taskStatus" type="hidden" name="status" value="${task.status}" />
				<table border="8" cellspacing="10">
					<tr>
						<td class="first">任务名称：</td>
						<td class="second"><input name="taskName"
							value="${task.taskName}" disabled /> <span class="require-mark"></span></td>
						<td class="thrid"><label class="aps-form-prompt"></label>
							&nbsp;</td>
					</tr>
					<tr>
						<td class="first">团长姓名：</td>
						<td class="second"><input name="headerName"
							value="${task.headerName}" disabled /> <span class="require-mark"></span></td>
						<td class="thrid"><label class="aps-form-prompt"></label>
							&nbsp;</td>
					</tr>
					<tr>
						<td class="first">团长身份证号：</td>
						<td class="second"><input name="headerIdCard"
							value="${task.headerIdCard}" disabled /> <span
							class="require-mark"></span></td>
						<td class="thrid"><label class="aps-form-prompt"></label>
							&nbsp;</td>
					</tr>
					<tr>
						<td class="first">任务开始日期：</td>
						<td class="second"><input id="container_start"
							name='taskBeginTime' value="${task.taskBeginTimeStr}" disabled /><span
							class="require-mark"></span></td>
						<td class="thrid"><label class="aps-form-prompt"></label>
							&nbsp;</td>
					</tr>
					<tr>
						<td class="first">任务结束日期：</td>
						<td class="second"><input id="container_end"
							name='taskEndTime' value="${task.taskEndTimeStr}" disabled /> <span
							class="require-mark"></span></td>
						<td class="thrid"><label class="aps-form-prompt"></label>
							&nbsp;</td>
					</tr>
					<tr>
						<td class="first">前往国家：</td>
						<td class="second"><input id="container_end"
							name='taskCountryName' value="${task.taskCountryName}" disabled />
							<span class="require-mark"></span></td>
						<td class="thrid"><label class="aps-form-prompt"></label>
							&nbsp;</td>
					</tr>
					<tr>
						<td class="first">已分配外事人员：</td>
						<td class="second"><input id="container_end"
							name='accountName' value="${task.accountName}" disabled />
							<span class="require-mark"></span></td>
						<td class="thrid"><label class="aps-form-prompt"></label>
							&nbsp;</td>
					</tr>
					<tr>
						<td class="first">任务描述：</td>
						<td class="second"><textarea rows="20" cols="5"
								name="taskDescription" disabled>${task.taskDescription}</textarea><span
							class="require-mark"></span></td>
						<td class="thrid"><label class="aps-form-prompt"></label>
							&nbsp;</td>
					</tr>
					<tr>
						<td class="first">添加出差人员：</td>
						<td class="second" colspan="2">
							<div id="selectPersonList"
								style="margin-left: 30px; margin-top: 20px">
								<table id="selectPersonListGrid"></table>
							</div>

						</td>
					</tr>
				</table>
				<div id="iconInfoTip" style="display: none;"></div>
			</form>
		</div>
	</div>
</body>
</html>