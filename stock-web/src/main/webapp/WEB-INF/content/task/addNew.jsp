<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<html>
<head>
	<link type="text/css" rel="stylesheet" href="css/sso/sso.css" />
 	<script type="text/javascript" src="js/task/taskAdd.js"></script>
</head>
<body>

   
  
	<div id="aps-console-account-changePassword"
		class="aps-console-account" style="padding-top: 30px">
		<form id="task-add-form" class="aps-form"
			enctype="multipart/form-data" method="post">
			<table>
				<tr>
					<td class="first">任务编码：</td>
					<td class="second"  ><input name="taskCode" value="${code}" readonly="readonly" style="width:80%"/> <span
						class="require-mark">*</span>&nbsp;&nbsp;<input type="button"
						class="input-button" value="生成" id="generateTaskCode" /></td>
					<td class="thrid"><label class="aps-form-prompt"></label>
						&nbsp;</td>
				</tr>
				<tr>
					<td class="first">批件号：</td>
					<td class="second"><input name="instructionNo" style="width:95%"/> <span
						class="require-mark">*</span></td>
					<td class="thrid"><label class="aps-form-prompt"></label>
						&nbsp;</td>
				</tr>
				<tr>
					<td class="first">任务名称：</td>
					<td class="second"><input name="taskName" style="width:95%"/> <span
						class="require-mark">*</span></td>
					<td class="thrid"><label class="aps-form-prompt"></label>
						&nbsp;</td>
				</tr>
				<tr>
					<td class="first">团长姓名：</td>
					<td class="second"><input name="headerName" style="width:95%"/> <span
						class="require-mark">*</span></td>
					<td class="thrid"><label class="aps-form-prompt"></label>
						&nbsp;</td>
				</tr>
				<tr>
					<td class="first">团长身份证号：</td>
					<td class="second"><input name="headerIdCard" style="width:95%"/> <span
						class="require-mark">*</span></td>
					<td class="thrid"><label class="aps-form-prompt"></label>
						&nbsp;</td>
				</tr>
				<tr>
					<td class="first">任务开始日期：</td>
					<td class="second"><input id="container_start"
						name='taskBeginTime' /><span class="require-mark">*</span></td>
					<td class="thrid"><label class="aps-form-prompt"></label>
						&nbsp;</td>
				</tr>
				<tr>
					<td class="first">任务结束日期：</td>
					<td class="second"><input id="container_end"
						name='taskEndTime' /> <span class="require-mark">*</span></td>
					<td class="thrid"><label class="aps-form-prompt"></label>
						&nbsp;</td>
				</tr>
				<tr>
					<td class="first">前往国家：</td>
					<td class="second"> <div id="taskCountry" ></div></td>
					<td class="thrid"><label class="aps-form-prompt"></label>
						&nbsp;</td>
				</tr>
				<tr></tr>
				<tr >
					<td class="first">任务描述：</td>
					<td class="second"><textarea rows="20" cols="5"
							name="taskDescription" style="width:95%"></textarea></td>
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