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
<title>文件模板管理</title>
<link type="text/css" rel="stylesheet" href="css/faff/account.css" />
<script type="text/javascript"
	src="js/omui/development-bundle/ui/om-ajaxsubmit.js"></script>
<script type="text/javascript" src="js/util/security-utils.js"></script>
<script type="text/javascript" src="js/account.js"></script>
<script type="text/javascript" src="js/visa/newFileTemplateManager.js"></script>
</head>
<body>

	<script type="text/javascript">
		$(function() {
			var target = '.aps-ssomanager-container .aps-ssomanager-tabs';
			new SYQ.FileTemplateManager({
				namespace : '',
				ENGINE_CONTEXT_PATH : '',
				CONTEXT_PATH : '<%=basePath %>',
				urls : {
					getFileTemplates : 'newVisa/ajaxListToFT',
					deleteFileTemplateUrl : 'newVisa/deleteFT'
				}
			});

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

	<p desc="aps-portlet-desc" style="display: none">签证办理文件模板管理。</p>

	<div class="aps-ssomanager-container">
		<div>
			<input id="file_upload" name="file" type="file" />
			<button value="上传" onclick="$('#file_upload').omFileUpload('upload')">上传</button>
		</div>
		<div class="aps-ssomanager-tabs">
			<ul>
				<li><a href="#file-template-list">签证办理文件模板管理</a></li>
			</ul>
			<div id="file-template-list">
				<div id="aps-console-app-view-wrap"
					class="aps-console-app-view-wrap">
					<div id="tab-app" class="tab-app">
						<div class="buttonbar default-bar-space"></div>
						<div class="grid">
							<table class="tb-grid file-template-grid"></table>
						</div>

					</div>
				</div>
			</div>
		</div>

	</div>
</body>
</html>