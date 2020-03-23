<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link type="text/css" rel="stylesheet" href="css/validate/validate.css" />
<script type="text/javascript" src="js/visa/expressDelivery.js"></script>

<script type="text/javascript">
	$(function() {
		var target = '.aps-ssomanager-container .aps-ssomanager-tabs';
		$(target).omTabs({
			border : false,
			onActivate : function(n, event) {
				if (n == 0) {
				} else {
				}
			}
		});
	})
</script>
</head>
<body>


	<div class="aps-ssomanager-container">
		<div class="aps-ssomanager-tabs">
			<ul>
				<li><a href="#before-delivery">录入护照资料</a></li>
			</ul>
			<div id="before-delivery">
				<div id="aps-console-app-view-wrap"
					class="aps-console-account">

					<div id="nav_cont">
						<h1 id="tag_1" class="cont_tag_show"></h1>
						<form id="task-add-form" class="aps-form"
							enctype="multipart/form-data" method="post"
							onsubmit="return false;">
							<input type="hidden" id="method" name="method"
								value="formValidateSubmit"> <input type="hidden" id="id"
								name="id" value="${delivery.id}"> <input type="hidden"
								id="taskId" name="taskId" value="${task.id}">
							<table>
								<tr>
									<td class="first" ><span class="color_red"></span>任务名称：</td>
									<td class="second">${task.taskName}<span class="errorImg"></span><span
										class="errorMsg"></span></td>
									<td class="thrid" ></td>
								</tr>
								<tr>
									<td class="first"><span class="color_red">*</span>快递单号：</td>
									<td class="second"><input type="text" name="number"
										id="number" class="input_text" value="${delivery.number}" />
										<span class="errorImg"></span><span class="errorMsg"></span></td>
									<td class="thrid" ></td>
								</tr>
								<tr>
									<td class="first"><span class="color_red">*</span>快递公司：</td>
									<td class="second"><input type="text" name="company"
										id="company" class="input_text" value="${delivery.company}" />
										<span class="errorImg"></span> <span class="errorMsg"></span></td>
									<td class="thrid" ></td>
								</tr>
								<tr>
									<td class="first">收件人：</td>
									<td class="second"><input type="text" name="receiver"
										id="receiver" class="input_text" value="${delivery.receiver}" />
										<span class="errorImg"></span><span class="errorMsg"></span></td>
									<td class="thrid" ></td>
								</tr>
								<tr>
									<td class="first">收件地址：</td>
									<td class="second"><input type="text" name="address"
										id="address" class="input_text" value="${delivery.address}" />
										<span class="errorImg"></span><span class="errorMsg"></span></td>
										<td class="thrid" ></td>
								</tr>

							</table>
							<div class="separator"></div>
							<table>
								<tr>
									<td class="first">
										<div class="text_align_c pad ">
											<button type="submit" class="button_u" id="button_submit"
												onmousemove="this.className='button_f'"
												onmousedown="this.className='button_d'"
												onmouseout="this.className='button_u'">提交表单</button>
											&nbsp;
											<button class="button_u" type="reset" id="button_clear"
												onmousemove="this.className='button_f'"
												onmousedown="this.className='button_d'"
												onmouseout="this.className='button_u'">&nbsp;&nbsp;重置&nbsp;&nbsp;</button>
											&nbsp;
										</div>
									
									</td>
									<td class="thrid" ></td>
								</tr>
							</table>

						</form>
					</div>
				</div>
			</div>
		</div>
	</div>


</body>
</html>