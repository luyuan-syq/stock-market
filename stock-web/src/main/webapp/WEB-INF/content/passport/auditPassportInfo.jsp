<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<link type="text/css" rel="stylesheet" href="css/validate/validate.css" />
<script type="text/javascript" src="js/passport/auditPassportInfo.js"></script>
</head>

<body>

	<div class="aps-ssomanager-container">
		<div class="aps-ssomanager-tabs">
			<ul>
				<li><a href="#audit-passportInfo">信息审核</a></li>
			</ul>
			<div id="audit-passportInfo">
				<div id="aps-console-app-view-wrap"
					class="aps-console-app-view-wrap">

					<div class="right clear">
						<div id="nav_cont">
							<h1 id="tag_1" class="cont_tag_show"></h1>

							<table class="grid_layout">
								<tr>
									<td align="left">用户Id：</td>
									<td colspan="3"><input
										value="${applyPassportInfo.personId}" class="input_text"
										disabled="disabled" /> <span class="errorImg"></span><span
										class="errorMsg"></span></td>
								</tr>
								<tr>
									<td align="left">籍贯住址：</td>
									<td colspan="3"><input
										value="${applyPassportInfo.placeBirth}" class="input_text"
										disabled="disabled" /> <span class="errorImg"></span><span
										class="errorMsg"></span></td>
								</tr>
								<tr>
									<td align="left">因公护照照片编码：</td>
									<td colspan="3"><input
										value="${applyPassportInfo.pictureNo}" class="input_text"
										disabled="disabled" /> <span class="errorImg"></span><span
										class="errorMsg"></span></td>
								</tr>
								<tr class="style1">
									<td align="left" width="15%">
									    <span class="color_red">*</span>身份证照片：
									    <button type='button' id = 'idCardDownLoad' >图片下载</button>
									</td>
									<td colspan="3" width="55%">
										<div id="showIdCardImg"
											imgName="${applyPassportInfo.idCardImg}"></div>
									</td>
								</tr>
								<tr>
									<td align="left" width="15%"><span class="color_red">*</span>户口本复印件照片：
									    <button type='button' id = 'familyBookDownLoad' >图片下载</button></td>
									<td colspan="3" width="55%">
										<div id="showFamilyBookImg"
											imgName="${applyPassportInfo.familyBookImg}"></div>
									</td>
								</tr>

							</table>
							<table>
								<tr>
									<td colspan="4" width="70%">

										<div id="edit-task-dialog" style="display: none">
											<form id="form1" method="post"
												action="passport/auditPassportInfo">
												<input type="hidden" id="personId" name="personId"
													value="${applyPassportInfo.personId}" /> <input
													type="hidden" id="dealFlag" name="dealFlag"
													value="${dealFlag}" /> <input type="hidden"
													name="familyBookImg" />
													
												<div style="text-align: center;">
													理由：<input type="text" name="rejectMsg" />
												</div>
												
												<div class="text_align_c pad ">
													<button type="button" id="buttonPass" class="button_u"
														onmousemove="this.className='button_f'"
														onmousedown="this.className='button_d'"
														onmouseout="this.className='button_u'">通过</button>
													&nbsp;
													<button class="button_u" id="buttonReject" type="button"
														onmousemove="this.className='button_f'"
														onmousedown="this.className='button_d'"
														onmouseout="this.className='button_u'">&nbsp;&nbsp;拒绝&nbsp;&nbsp;</button>
													&nbsp;
												</div>
												
											</form>
										</div>
									</td>
								</tr>
							</table>

						</div>
					</div>



				</div>
			</div>
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

			var target = '.aps-ssomanager-container .aps-ssomanager-tabs';

			$(target).omTabs({
				border : false,
				onActivate : function(n, event) {
					if (n == 0) {
					} else {
					}
				}
			});
		});
	</script>

</body>
</html>