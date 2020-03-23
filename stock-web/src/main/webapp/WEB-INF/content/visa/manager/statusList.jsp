<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>状态查询</title>
<link type="text/css" rel="stylesheet" href="css/sso/sso.css" />
<script type="text/javascript" src="js/util/security-utils.js"></script>
<script type="text/javascript" src="js/visa/statusList.js"></script>
</head>

<body>

<script type="text/javascript">
$(function() {
	var target = '.aps-ssomanager-container .aps-ssomanager-tabs';
	new SYQ.VisaStatus({
		namespace : '',
		ENGINE_CONTEXT_PATH : '',
		CONTEXT_PATH : '',
		urls : {
			getVisaStatusURL : 'visa/ajaxStatusList?taskId=${task.id}',
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

	<p desc="aps-portlet-desc" style="display: none">SSO设置用于对与门户进行单点登录的应用以及相关账号映射的管理。</p>

	<div class="aps-ssomanager-container">
		<div class="aps-ssomanager-tabs">
			<ul>
				<li><a href="#status-visaList">办理状态查询</a></li>
			</ul>
			<div id="status-visaList">
				<div id="aps-console-app-view-wrap"
					class="aps-console-app-view-wrap">
					<div id="tab-app" class="tab-app">
						<div class="buttonbar default-bar-space"></div>
						<div class="grid">
							<table class="tb-grid visaStatus-grid"></table>
						</div>

						<div id="<portlet:namespace/>aps-console-app-view-dialog-wrap"
							class="aps-console-app-view-dialog-wrap">
							<div id="selector-dialog"></div>
							<div id="create-app-dialog"></div>
							<div id="edit-app-dialog"></div>
							<div id="view-app-dialog"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>	
	
</body>
</html>