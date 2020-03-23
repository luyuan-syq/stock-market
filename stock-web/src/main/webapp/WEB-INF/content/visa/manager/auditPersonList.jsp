<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>出差人员列表</title>
<link type="text/css" rel="stylesheet" href="css/faff/account.css" />
<script type="text/javascript" src="js/omui/development-bundle/ui/om-ajaxsubmit.js"></script>
<script type="text/javascript" src="js/util/security-utils.js"></script>
<script type="text/javascript" src="js/visa/auditPersonList.js"></script>

</head>

<body>

	<script type="text/javascript">
		$(function() {
			var target = '.aps-ssomanager-container .aps-ssomanager-tabs';
			new SYQ.Account({
				namespace : '',
				ENGINE_CONTEXT_PATH : '',
				CONTEXT_PATH : '',
				TASK_ID : '${taskId}',
				urls : {
					addAccountURL : 'account/add',
					addAccountBeforeURL : 'account/addBeforeURL',
					editAccountBeforeURL : 'account/editBeforeURL',
					deleteAppURL : 'account/delete',
					getAccountURL : 'person/ajaxListToTask/?taskId=${taskId}',
					updateAccountURL : 'account/update',
					appIsExitURL : '${appIsExitURL}'
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
				<li><a href="#account-list">出差人员审核</a></li>
			</ul>
			<div id="account-list">
				<div id="aps-console-app-view-wrap"
					class="aps-console-app-view-wrap">
					<div id="tab-app" class="tab-app">
						<div class="buttonbar default-bar-space"></div>
						<div class="grid">
							<table class="tb-grid account-grid"></table>
						</div>

						<div id="<portlet:namespace/>aps-console-app-view-dialog-wrap"
							class="aps-console-app-view-dialog-wrap">
							<div id="view-app-dialog"></div>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
</body>
</html>