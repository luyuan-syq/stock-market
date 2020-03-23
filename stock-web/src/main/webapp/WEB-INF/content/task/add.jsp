<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
	<meta name="decorator" content="none" />
</head>
<body>
	<form id="task-info-form" class="aps-form" enctype="multipart/form-data"
		method="post">
		<table>
			<tr>
				<td class="first">任务名称：</td>
				<td class="second"><input name="taskName"/> <span
					class="require-mark">*</span></td>
				<td class="thrid"><label class="aps-form-prompt"></label>
					&nbsp;</td>
			</tr>
			<tr>
				<td class="first">团长姓名：</td>
				<td class="second"><input name="headerName"/> <span
					class="require-mark">*</span></td>
				<td class="thrid"><label class="aps-form-prompt"></label>
					&nbsp;</td>
			</tr>
			<tr>
				<td class="first">团长身份证号：</td>
				<td class="second"><input name="headerIdCard"/> <span
					class="require-mark">*</span></td>
				<td class="thrid"><label class="aps-form-prompt"></label>
					&nbsp;</td>
			</tr>
			<tr>
				<td class="first">任务开始日期：</td>
				<td class="second"><input id="container_start" name='taskBeginTime'/><span
					class="require-mark"></span></td>
				<td class="thrid"><label class="aps-form-prompt"></label>
					&nbsp;</td>
			</tr>
			<tr>
				<td class="first">任务结束日期：</td>
				<td class="second"><input id="container_end" name='taskEndTime'/> <span
					class="require-mark"></span></td>
				<td class="thrid"><label class="aps-form-prompt"></label>
					&nbsp;</td>
			</tr>
			<tr>
				<td class="first">任务描述：</td>
				<td class="second"><textarea rows="20" cols="5" name="taskDescription"></textarea><span
					class="require-mark">*</span></td>
				<td class="thrid"><label class="aps-form-prompt"></label>
					&nbsp;</td>
			</tr>
		</table>
		<div id="iconInfoTip" style="display: none;"></div>
	</form>
    <script type="text/javascript">

        $(document).ready(function() {

            $('#container_start').omCalendar({dateFormat : "yy-mm-dd H:i:s",showTime : true});
            $('#container_end').omCalendar({dateFormat : "yy-mm-dd H:i:s",showTime : true});
        });

    </script>

</body>
</html>