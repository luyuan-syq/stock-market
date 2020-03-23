<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>护照管理</title>
<link type="text/css" rel="stylesheet" href="css/sso/sso.css" />
<script type="text/javascript" src="js/util/security-utils.js"></script>
<script type="text/javascript" src="js/passport/renewalPassport.js"></script>
</head>

<body>

<script type="text/javascript">
var passport;
var globleTaskCode = '${taskCode}';
$(function() {
	var target = '.aps-ssomanager-container .aps-ssomanager-tabs';
	passport = new SYQ.Passport({
		namespace : '',
		ENGINE_CONTEXT_PATH : '',
		CONTEXT_PATH : '',
		urls : {
			addAppURL : '${addAppURL}',
			addAppBeforeURL : '${addAppBeforeURL}',
			deleteAppURL : '${deleteAppURL}',
			getPassportURL : 'renewalPassport/ajaxList?needCountAboutExprie=true',
			updateAppURL : '${updateAppURL}',
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
				<li><a href="#passport-list">护照检索</a></li>
				<%-- 
				<li><a href="#account-list" class="account-listClass">信息录入</a></li>
				<li><a href="#account-list">信息检索</a></li>--%>
			</ul>
								    <div id="search-panel">
				   			<div>
					   			<span class="label">姓名：</span>
					   			<input type="text" id="name" class="input-text" name="name"/>
					   			<span class="label">有效期开始时间：</span>
					   			<input id="expireBeginTime" style="width: 118px" name="expireBeginTime"/>
					   			<span class="label">有效期结束时间：</span>
					   			<input id="expireEndTime"  style="width: 118px" name="expireEndTime"/>
					   			<br/><br />
					   			<!-- 
					   			<span class="label">状态：</span>
					   			<select id="searchStatus" class="input-text" style="height:25px">
					   			   <option value="">所有</option>
					   			   <option value="1">换发函已提交</option>
					   			   <option value="2">待登记</option>
					   			</select>
					   			 -->
					   			<span class="label">任务批号：</span>
					   			<input type="text" class="input-text" id="instructionNo" name="instructionNo"/>
					   			<span class="label">身份证号：</span>
					   			<input type="text" class="input-text" id="idNumber" name="idNumber"/>
					   			<br /><br />
					   			<span id="button-search">搜索</span>
					   			<span id="button-clear">重置</span>
				   			</div>
				   		</div>
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