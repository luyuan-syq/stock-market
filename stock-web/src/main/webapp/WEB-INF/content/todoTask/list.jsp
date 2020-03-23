<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.syq.web.utils.LoginConstants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>护照管理</title>
<link type="text/css" rel="stylesheet" href="css/sso/sso.css" />
<script type="text/javascript" src="js/util/security-utils.js"></script>
<script type="text/javascript" src="js/todolist/passport.js"></script>
<script type="text/javascript" src="js/omui/development-bundle/ui/om-ajaxsubmit.js"></script>
<script type="text/javascript" src="js/jqueryplugins/jstree_pre1.0_stable/jquery.jstree.js"></script>
</head>

<body>

<script type="text/javascript">
var passport;
$(function() {
	var target = '.aps-ssomanager-container .aps-ssomanager-tabs';
	var needCountExprie = ${countExpire};
	var needCountNotReturn = ${countNotReturn};
	var needCountAboutExprie = ${aboutExpire};
	passport = new SYQ.Passport({
		namespace : '',
		ENGINE_CONTEXT_PATH : '',
		CONTEXT_PATH : '',
		urls : {
			addAppURL : '${addAppURL}',
			addAppBeforeURL : '${addAppBeforeURL}',
			deleteAppURL : '${deleteAppURL}',
			getAboutExprieURL : 'passport/ajaxListTodo?needCountAboutExprie=true&status=4,5',
			getNotReutrnURL : 'passport/ajaxListTodo?status=4',
			getWaitAuditURL : 'passport/ajaxListTodo?status=1',
			getDoingURL : 'passport/ajaxListTodo?status=3',
			getWaitAuditVisaURL : 'newVisa/ajaxListToVisa/?searchStatus=1&flowStatus=1',
			getDoingVisaURL : 'visa/ajaxListToVisa/?searchStatus=3',
			updateAppURL : '${updateAppURL}',
			appIsExitURL : '${appIsExitURL}',
			getRenewalPassportURL : 'renewalPassport/ajaxList?needCountAboutExprie=true'
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
				<li><a href="#aboutexprie-list">将过期护照列表</a></li>
				<li><a href="#notreturn-list">未归还护照列表</a></li>
				<li><a href="#waitaudit-list">待审核护照列表</a></li>
				<li><a href="#doing-list">办理中护照列表</a></li>
				<li><a href="#waitaudit-visa-list">待审核人员签证列表</a></li>
				<li><a href="#doing-visa-list">办理中人员签证列表</a></li>
				<li><a href="#renewal-passport-list">换发护照列表</a></li>
			</ul>
			<div id="aboutexprie-list">
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
			
			<div id="notreturn-list">
				<div id="aps-console-app-view-wrap"
					class="aps-console-app-view-wrap">
					<div id="tab-app-notreturn" class="tab-app">
						<div class="buttonbar default-bar-space"></div>
						<div class="grid">
							<table class="tb-grid notreturn-grid"></table>
						</div>

						<div id="<portlet:namespace/>aps-console-app-view-dialog-wrap"
							class="aps-console-app-view-dialog-wrap">
							<div id="view-app-dialog-notreturn"></div>
						</div>
					</div>
				</div>
			</div>
			
			<div id="waitaudit-list">
				<div id="aps-console-app-view-wrap"
					class="aps-console-app-view-wrap">
					<div id="tab-app-waitaudit" class="tab-app">
						<div class="buttonbar default-bar-space"></div>
						<div class="grid">
							<table class="tb-grid waitaudit-grid"></table>
						</div>

						<div id="<portlet:namespace/>aps-console-app-view-dialog-wrap"
							class="aps-console-app-view-dialog-wrap">
							<div id="view-app-dialog-waitaudit"></div>
						</div>
					</div>
				</div>
			</div>
			
			<div id="doing-list">
				<div id="aps-console-app-view-wrap"
					class="aps-console-app-view-wrap">
					<div id="tab-app-doing" class="tab-app">
						<div class="buttonbar default-bar-space"></div>
						<div class="grid">
							<table class="tb-grid doing-grid"></table>
						</div>

						<div id="<portlet:namespace/>aps-console-app-view-dialog-wrap"
							class="aps-console-app-view-dialog-wrap">
							<div id="view-app-dialog-doing"></div>
						</div>
					</div>
				</div>
			</div>
			
            <div id="waitaudit-visa-list">
				<div id="aps-console-app-view-wrap"
					class="aps-console-app-view-wrap">
					<div id="tab-app-doing" class="tab-app">
						<div class="buttonbar default-bar-space"></div>
						<div class="grid">
							<table class="tb-grid waitaudit-visa-grid"></table>
						</div>

						<div id="<portlet:namespace/>aps-console-app-view-dialog-wrap"
							class="aps-console-app-view-dialog-wrap">
							<div id="view-app-dialog-doing"></div>
						</div>
					</div>
				</div>
			</div>
			
            <div id="doing-visa-list">
				<div id="aps-console-app-view-wrap"
					class="aps-console-app-view-wrap">
					<div id="tab-app-doing" class="tab-app">
						<div class="buttonbar default-bar-space"></div>
						<div class="grid">
							<table class="tb-grid doing-visa-grid"></table>
						</div>

						<div id="<portlet:namespace/>aps-console-app-view-dialog-wrap"
							class="aps-console-app-view-dialog-wrap">
							<div id="view-app-dialog-doing"></div>
						</div>
					</div>
				</div>
			</div>
			
			
			<div id="renewal-passport-list">
				<div id="aps-console-app-view-wrap"
					class="aps-console-app-view-wrap">
					<div id="tab-app-doing" class="tab-app">
						<div class="buttonbar default-bar-space"></div>
						<div class="grid">
							<table class="tb-grid renewal-passport-grid"></table>
						</div>

						<div id="<portlet:namespace/>aps-console-app-view-dialog-wrap"
							class="aps-console-app-view-dialog-wrap">
							<div id="view-app-dialog-doing"></div>
						</div>
					</div>
				</div>
			</div>
			
		</div>
	</div>	
</body>
</html>