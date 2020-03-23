<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<link type="text/css" rel="stylesheet" href="css/validate/validate.css" />
<script type="text/javascript" src="js/passport/passportAdd.js"></script>

</head>


<body>
	<div class="right clear">
		<div id="nav_cont">
			<h1 id="tag_1" class="cont_tag_show"></h1>
				<table width="100%" border="0" class="grid_layout" cellspacing="0">
					<tr class="style1">
						<td align="right" width="15%"><span class="color_red">*</span>护照编码：</td>
						<td colspan="3" width="65%"><input type="text" name="passportNo"
							id="fileNo" class="input_text" value="${passport.passportNo}" readonly/> <span class="errorImg"></span><span
							class="errorMsg"></span></td>
					</tr>
					<tr>
						<td align="right"><span class="color_red">*</span>姓名：</td>
						<td colspan="3"><input type="text" id="userNo" name="surname"
							class="input_text" value="${passport.name }" readonly /> <span class="errorImg"></span><span
							class="errorMsg"></span></td> 
					</tr>
					<tr>
						<td align="right"><span class="color_red">*</span>性别：</td>
						<td><input name="sex" value="${passport.sex }" readonly /> <span class="errorImg"></span><span
							class="errorMsg"></span></td>
					</tr>
					<tr>
						<td align="right">身份证号：</td>
						<td colspan="3"><input type="text" name="idNumber" id="idNumber"
							class="input_text" value="${passport.idNumber }" readonly /> <span class="errorImg"></span><span
							class="errorMsg"></span></td>
					</tr>
					<tr>
						<td align="right">颁发日期：</td>
						<td colspan="3"><input type="text" id="dateIssue"
							name="dateIssue" style="color: #aaa;" value="${passport.dateIssue}" readonly/> <span class="errorImg"></span><span
							class="errorMsg"></span></td>
					</tr>
					<tr>
						<td align="right">过期时间：</td>
						<td colspan="3"><input type="text" name="dateExpire"
							id="dateExpire" style="color: #aaa;" value="${passport.dateExpire}" readonly/> <span class="errorImg"></span><span
							class="errorMsg"></span></td>
					</tr>
					<tr>
						<td align="right">籍贯住址：</td>
						<td colspan="3"><input name="placeBirth" id="placeBirth"
							class="input_text" value="${passport.placeBirth }" readonly/> <span class="errorImg"></span><span
							class="errorMsg"></span></td>
					</tr>
					<tr>
						<td align="right">注册地址：</td>
						<td colspan="3"><input type="text" name="placeIssue"
							id="placeIssue" class="input_text" value="${passport.placeIssue}" readonly/> <span class="errorImg"></span><span
							class="errorMsg"></span></td>
					</tr>
				</table>
				<div class="separator"></div>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {

			$('#dateIssue').omCalendar({
				dateFormat : "yy-mm-dd H:i:s",
				showTime : true
			});
			$('#dateExpire').omCalendar({
				dateFormat : "yy-mm-dd H:i:s",
				showTime : true
			});
		});
	</script>

</body>
</html>