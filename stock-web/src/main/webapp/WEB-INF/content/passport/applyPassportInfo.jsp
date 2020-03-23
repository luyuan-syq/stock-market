<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link type="text/css" rel="stylesheet" href="css/validate/validate.css" />
<script type="text/javascript" src="js/passport/applyPassportInfo.js"></script>

</head>


<body>

	<div class="right clear">
		<div id="nav_cont">
			<h1 id="tag_1" class="cont_tag_show"></h1>
			
				<table width="100%" border="0" class="grid_layout" cellspacing="0">
				  <c:if test="${passport.flowStatus == 2 }">
					<tr>
						<td align="right">拒绝原因：</td>
						<td colspan="3"><input value="${passport.flowMsg}"
							class="input_text" disabled="disabled"/> <span class="errorImg"></span><span
							class="errorMsg"></span></td>
					</tr>
				</c:if>
					<tr>
						<td align="right">用户名称：</td>
						<td colspan="3"><input value="${person.userName}"
							class="input_text" disabled="disabled"/> <span class="errorImg"></span><span
							class="errorMsg"></span></td>
					</tr>
					<tr>
						<td align="right">籍贯住址：</td>
						<td colspan="3"><input name="placeBirth" id="placeBirth" value="${applyPassportInfo.placeBirth}"
							class="input_text" /> <span class="errorImg"></span><span
							class="errorMsg"></span></td>
					</tr>
					<tr>
						<td align="right">因公护照照片编码：</td>
						<td colspan="3"><input name="pictureNo" id="pictureNo" value="${applyPassportInfo.pictureNo}"
							class="input_text" /> <span class="errorImg"></span><span
							class="errorMsg"></span></td>
					</tr>
					<tr class="style1">
						<td align="right" width="15%"><span class="color_red">*</span>身份证照片：</td>
						<td colspan="2" width="15%"><input type="file"  name="file"
							id="idCardImg" class="input_text" /> <span class="errorImg"></span><span
							class="errorMsg"></span>
							<button value="上传" id="uploadIdCardImg">上传</button>
							</td>
						<td width="40%">
						   <div id="showIdCardImg" imgName="${applyPassportInfo.idCardImg}"></div>
						</td>
					</tr>
					<tr>
						<td align="right" width="15%"><span class="color_red">*</span>户口本复印件照片：</td>
						<td colspan="2" width="15%"><input type="file"  name="file"
							id="familyBookImg" class="input_text" /> <span class="errorImg"></span><span
							class="errorMsg"></span>
							<button value="上传" onclick="$('#familyBookImg').omFileUpload('upload');retur">上传</button>
							</td>
						<td width="40%">
						   <div id="showFamilyBookImg" imgName="${applyPassportInfo.familyBookImg}"></div>
						</td>
					</tr>
					
				</table>
			<form id="form1" method="post" action="passport/applyPassportInfo">
				<input type="hidden" id="personId" name="personId"
					value="${person.id}" />
				<input type="hidden" name="idCardImg"/>
				<input type="hidden" name="familyBookImg"/>
				<div class="separator"></div>
				<div class="text_align_c pad ">
					<button type="button" id="buttonApplyPassportInfo" class="button_u"
						onmousemove="this.className='button_f'"
						onmousedown="this.className='button_d'"
						onmouseout="this.className='button_u'">提交表单</button>
					&nbsp;
					<button class="button_u" type="reset"
						onmousemove="this.className='button_f'"
						onmousedown="this.className='button_d'"
						onmouseout="this.className='button_u'">&nbsp;&nbsp;重置&nbsp;&nbsp;</button>
					&nbsp;
				</div>
			</form>
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