<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath %>" />
<title>护照管理</title>
<link type="text/css" rel="stylesheet" href="css/sso/sso.css" />
<script type="text/javascript" src="js/util/security-utils.js"></script>
<script type="text/javascript" src="js/todolist/toDoFaff.js"></script>
<script type="text/javascript" src="js/omui/development-bundle/ui/om-ajaxsubmit.js"></script>
<script type="text/javascript" src="js/jqueryplugins/jstree_pre1.0_stable/jquery.jstree.js"></script>
</head>

<body>

<script type="text/javascript">
$(function() {
	var target = '.aps-ssomanager-container .aps-ssomanager-tabs';
	
	
	toDoFaff = new SYQ.ToDoFaff({
		namespace : '',
		ENGINE_CONTEXT_PATH : '',
		CONTEXT_PATH : '',
		urls : {
			getDaiBanPassportURL : 'passport/daiBanAjaxList?taskId=${task.id}',
			getDaiBanVisaURL : 'visa/daiBanAjaxList?taskId=${task.id}'
		}
	});
	
	var select = '${select}';
	$(target).omTabs({
		border : false,
		onActivate : function(n, event) {
			
		}
	});
	var selectIndex = parseInt(select);
	$(target).omTabs('activate', selectIndex);
});
</script>

	<p desc="aps-portlet-desc" style="display: none">SSO设置用于对与门户进行单点登录的应用以及相关账号映射的管理。</p>

	<div class="aps-ssomanager-container">
		<div class="aps-ssomanager-tabs">
			<ul>
			    <li><a href="<%=basePath %>task/infoByCode?none=1">外事任务</a></li>
			 	<li><a href="<%=basePath %>task/editInfoURL?none=1">任务录入</a></li>
				<li><a href="<%=basePath %>task/beforeAddAccounts?none=1">添加出差人员</a></li>
				<li><a href="#passport-list">待办护照</a></li>
				<li><a href="#visa-list">待办签证</a></li>
			</ul>
			<div id="passport-list">
				<div id="aps-console-app-view-wrap"
					class="aps-console-app-view-wrap">
					<div id="tab-app" class="tab-app">
						<div class="buttonbar default-bar-space"></div>
						<div class="grid">
							<table class="tb-grid passport-grid"></table>
						</div>

						<div id="<portlet:namespace/>aps-console-app-view-dialog-wrap"
							class="aps-console-app-view-dialog-wrap">
							<div id="view-app-dialog"></div>
						</div>
					</div>
				</div>
			</div>
			
			<div id="visa-list">
				<div id="aps-console-app-view-wrap"
					class="aps-console-app-view-wrap">
					<div id="tab-app-notreturn" class="tab-app">
						<div class="buttonbar default-bar-space"></div>
						<div class="grid">
							<table class="tb-grid visa-grid"></table>
						</div>

						<div id="<portlet:namespace/>aps-console-app-view-dialog-wrap"
							class="aps-console-app-view-dialog-wrap">
							<div id="view-app-dialog-notreturn"></div>
						</div>
					</div>
				</div>
			</div>
			
		</div>
	</div>		
</body>
</html>