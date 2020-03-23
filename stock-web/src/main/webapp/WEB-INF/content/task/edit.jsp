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
<script type="text/javascript" src="js/task/taskEdit.js"></script>
</head>
<body>



	<div id="aps-console-account-changePassword"
		class="aps-console-account" style="padding-top: 30px">
		<form id="task-add-form" class="aps-form"
			enctype="multipart/form-data" method="post">
			<input type="hidden" name="id" value="${task.id}" /> <input
				type="hidden" name="taskCountry" value="${task.taskCountry}" /> <input
				type="hidden" name="status" value="1" /> <input type="hidden"
				id="hasGroupMemberHidden" name="hasGroupMemberHidden"
				value="${task.hasGroupMember}" /> <input type="hidden"
				id="costSourceHidden" name="costSourceHidden"
				value="${task.costSource}" /> <input type="hidden"
				id="taskStatusHidden" value="${task.status}" />
			<table>
				<tr>
					<td class="first">批件号：</td>
					<td class="second"><input name="instructionNo"
						value="${task.instructionNo}" /> <span class="require-mark">*</span></td>
					<td class="thrid"><label class="aps-form-prompt"></label>
						&nbsp;</td>
				</tr>
				<tr>
					<td class="first">任务名称：</td>
					<td class="second"><input name="taskName"
						value="${task.taskName}" /> <span class="require-mark">*</span></td>
					<td class="thrid"><label class="aps-form-prompt"></label>
						&nbsp;</td>
				</tr>
				<tr>
					<td class="first">外事计划项目号：</td>
					<td class="second"><input name="faffPlanNo"
						value="${task.faffPlanNo}" /> <span class="require-mark">*</span></td>
					<td class="thrid"><label class="aps-form-prompt"></label>
						&nbsp;</td>
				</tr>
				<tr>
					<td class="first">经费来源：</td>
					<td class="second"><select id="costSource" name="costSource"
						style="width: 100px;">
							<option value="">请选择</option>
							<option value="派员单位">派员单位</option>
							<option value="邀请方">邀请方</option>
							<option value="其他">其他</option>
					</select></td>
					<td class="thrid"><label class="aps-form-prompt"></label>
						&nbsp;</td>
				</tr>
				<tr>
					<td class="first">是否有集团领导班子成员：</td>
					<td class="second"><label><input name="hasGroupMember"
							type="radio" value="0" checked="true">否</label> <label><input
							name="hasGroupMember" type="radio" value="1">是</label> <span
						class="require-mark">*</span></td>
					<td class="thrid"><label class="aps-form-prompt"></label>
						&nbsp;</td>
				</tr>
				<tr>
					<td class="first">任务开始日期：</td>
					<td class="second"><input id="container_start"
						name='taskBeginTime' value="${task.taskBeginTime}" /><span
						class="require-mark"></span></td>
					<td class="thrid"><label class="aps-form-prompt"></label>
						&nbsp;</td>
				</tr>
				<tr>
					<td class="first">任务结束日期：</td>
					<td class="second"><input id="container_end"
						name='taskEndTime' value="${task.taskEndTime}" /> <span
						class="require-mark"></span></td>
					<td class="thrid"><label class="aps-form-prompt"></label>
						&nbsp;</td>
				</tr>
				<tr>
					<td class="first">前往国家：</td>
					<td class="second" colspan="2">
						<div template="default-value">
							<div class="field-templates">
								<div class="textSelect-default-value-template">
									<div id="default-value-content" class="default-value-content" style="margin-left: 70px;margin-top:20px">
										<c:forEach items="${taskCountrys }" var="taskCountry">
											<div class="default-value-item">

												<button type="button" class="default-value-delete-btn"
													onclick="deleteDefaultValue(event);"></button>

												<button type="button" class="default-value-view-btn"
													onclick="selectCountry(event);">选择国家&nbsp;&nbsp;&nbsp;&nbsp;</button>
												<span class="taskCountry" style="color: red"></span><input
													type="hidden" name="selectTaskCountry" value="${taskCountry.taskCountry }"/> <span>出差天数:
												</span><input name="expectStickDay" maxlength="1000" value="${taskCountry.expectStickDay }"/> <span>备注:
												</span><input name="comment" style="width: 200px" value="${taskCountry.comment }"/>
											</div>
										</c:forEach>

									</div>
									<div class="default-value-action" style="margin-left: 0px">
										<button type="button"
											class="default-value-create-btn om-btn-txt om-btn-icon"
											onclick="createDefaultValue(event);"
											style="background-image: url('css/images/16/add.png'); background-position: 0% 50%; background-repeat: no-repeat;">添加</button>
									</div>
								</div>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<tr>
					<td class="first">任务描述：</td>
					<td class="second"><textarea rows="20" cols="5"
							id="taskDescription" name="taskDescription">${task.taskDescription}</textarea><span
						class="require-mark"></span></td>
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
	<div id="default-value-item-clone" class="display-none">
		<div class="default-value-item ">
			<button type="button" class="default-value-delete-btn"
				onclick="deleteDefaultValue(event);"></button>

			<button type="button" class="default-value-view-btn"
				onclick="selectCountry(event);">选择国家&nbsp;&nbsp;&nbsp;&nbsp;</button>
			<span style="color:red" class="taskCountry"></span><input
				type="hidden" name="selectTaskCountry" /> <span>出差天数:
			</span><input name="expectStickDay" maxlength="1000" /> <span>备注: </span><input
				name="comment" style="width: 200px" />
		</div>
	</div>

	<!-- 选择国家 -->
	<div id="select-country">
		<div id="select-country-dialog">
			
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			var taskBeginTime = '${task.taskBeginTime}';
			var taskEndTime = '${task.taskEndTime}';
			var taskStatus = '${task.status}';
			if (taskBeginTime != null && '' != taskBeginTime) {
				taskBeginTime = formattime(FormatDate(taskBeginTime))
				$('#container_start').val(taskBeginTime);
			}
			if (taskEndTime != null && '' != taskEndTime) {
				taskEndTime = formattime(FormatDate(taskEndTime))
				$('#container_end').val(taskEndTime);
			}
			//formattime(colValue)
			$('#container_start').omCalendar({
				dateFormat : "yy-mm-dd",
			});
			$('#container_end').omCalendar({
				dateFormat : "yy-mm-dd",
			});
		});
		function FormatDate(t) {
			var dateStr = t.trim().split(" ");
			// 就是这一行代码和上面的字符串分隔，然后拼接而成的GMT
			var strGMT = dateStr[0] + " " + dateStr[1] + " " + dateStr[2] + " "
					+ dateStr[5] + " " + dateStr[3] + " GMT+0800";
			var time = new Date(Date.parse(strGMT));
			return time
		}
	</script>
	</body>
</html>