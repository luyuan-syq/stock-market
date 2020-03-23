<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>" />
<title>签证模板列表</title>
<link type="text/css" rel="stylesheet" href="css/sso/sso.css" />
<link type="text/css" rel="stylesheet" href="css/faff/common.css" />
<link type="text/css" rel="stylesheet" href="css/faff/visa.css" />
<link type="text/css" rel="stylesheet"
	href="css/faff/visa/user-config.css" />
<script type="text/javascript"
	src="js/jqueryplugins/jstree_pre1.0_stable/jquery.jstree.js"></script>
<script type="text/javascript" src="js/util/security-utils.js"></script>
<script type="text/javascript" src="js/visa/visa.js"></script>
<script type="text/javascript" src="js/visa/visaCountry.js"></script>

<script type="text/javascript" src="js/visa/field-custom.js"></script>
<script type="text/javascript" src="js/visa/field-content.js"></script>
<script type="text/javascript" src="js/visa/field-create-update.js"></script>

</head>

<body>

	<script type="text/javascript">
		$(function() {
			SYQ.Admin = {
				imageDir : 'css/images/16/'
			};
			SYQ.PortalMananger = {
				addImage : SYQ.Admin.imageDir + 'add.png',
				addImage2 : SYQ.Admin.imageDir + 'add2.png',
				deleteImage : SYQ.Admin.imageDir + 'delete.png',
				saveImage : SYQ.Admin.imageDir + 'save.png',
				editImage : SYQ.Admin.imageDir + 'edit.png',
				passwordImage : SYQ.Admin.imageDir + 'password.png'
			};
		});
		$(function() {
			var target = '#countryVisaDetail';
			new SYQ.Visa({
				namespace : '',
				ENGINE_CONTEXT_PATH : '',
				CONTEXT_PATH : '',
				urls : {
					addAccountURL : 'account/add',
					addAccountBeforeURL : 'account/addBeforeURL',
					editAccountBeforeURL : 'account/editBeforeURL',
					deleteAppURL : 'account/delete',
					getAccountURL : 'account/ajaxList',
					updateAccountURL : 'account/update',
					appIsExitURL : '${appIsExitURL}'
				}
			});

			$(target).omTabs({
				border : false,
				onActivate : function(n, event) {
					
				}
			});
		});
	</script>

	<div id="overall">
		<div class="aps-security-tree-container aps-tree-container">
			<div id="visaCountryTree">
				<img src="images/throbber.gif" alt="" /> 加载中，请稍候……
			</div>
		</div>
		<div id="group-separator" class="aps-console-column-exspand">
			<div class="aps-collapse-separator"></div>
		</div>
		<div id="countryVisaDetail" class="detail-expand-width ">
			<ul>
				<li><a href="<%=basePath%>visaTemplate/template">模板设置</a></li>
			</ul>

		</div>
	</div>

	<div>
		<div id="create-country-dialog">
			<%@include file="./country/add.jsp"%>
		</div>
		<div id="edit-country-dialog"></div>
	</div>
	<%-- 字段类型使用到的对话框 --%>
	<div id="category-create-dlg" class="display-none">
		<%@include file="./template/category-create.jsp"%>
	</div>
	<div id="category-delete-dlg" class="display-none">
		<span>确定删除该字段类别吗?</span>
	</div>

	<%-- 字段使用到的对话框 --%>
	<div id="field-create-dlg" class="display-none"></div>
	<div id="field-delete-dlg" class="display-none">
		<span>确定删除字段吗?</span>
	</div>
	<div id="field-update-dlg" class="display-none"></div>
</body>
</html>