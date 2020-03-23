<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>编码管理 </title>
<link type="text/css" rel="stylesheet" href="css/faff/account.css" />
<script type="text/javascript" src="js/omui/development-bundle/ui/om-ajaxsubmit.js"></script>
<script type="text/javascript" src="js/util/security-utils.js"></script>
<script type="text/javascript" src="js/task/taskCode.js"></script>

</head>

<body>

	<script type="text/javascript">
		$(function() {
			var target = '.aps-ssomanager-container .aps-ssomanager-tabs';
			new SYQ.TaskCode({
				namespace : '',
				ENGINE_CONTEXT_PATH : '',
				CONTEXT_PATH : '',
				urls : {
					addTaskCodeURL : 'taskCode/add',
					addAccountBeforeURL : 'account/addBeforeURL',
					getTaskCodeURL : 'taskCode/ajaxList'
				}
			});

		});
	</script>
	
	<p desc="aps-portlet-desc" style="display: none">SSO设置用于对与门户进行单点登录的应用以及相关账号映射的管理。</p>

	<div class="aps-ssomanager-container">
		<div id="taskCode-manager-tabs" class="taskCode-manager-tabs">
			<ul>
				<li><a href="#taskCode-list">编码查询</a></li>
				<li><a href="#taskCode-create">创建编码</a></li>
			</ul>
			<div id="taskCode-list">
				<div id="aps-console-app-view-wrap"
					class="aps-console-app-view-wrap">
					<div id="tab-app" class="tab-app">
					    <div id="search-panel">
				   			<div>
					   			<span class="label">任务编码：</span>
					   			<input type="text" id="searchTaskCode" class="input-text" />
					   			<span class="label">开始时间：</span>
					   			<input id="createBeginTime" style="width: 118px" />
					   			<span class="label">结束时间：</span>
					   			<input id="createEndTime"  style="width: 118px"/>
					   			<br /><br />
					   			<span class="label">外事人员：</span>
					   			<input type="text" id="searchUserName" class="input-text" />
					   			<span class="label">编码状态：</span>
					   			<select id="searchStatus" class="input-text" style="height:25px">
					   			   <option value="">所有</option>
					   			   <option value="1">未分配</option>
					   			   <option value="2">在用</option>
					   			   <option value="3">关闭</option>
					   			</select>
					   			<!--  
					   			<span class="label">所绑定的任务编码：</span>
					   			<input type="text" class="input-text" />
					   			<span class="label">所分配的外事人员：</span>
					   			<input type="text" class="input-text" />
					   			-->
					   			<br /><br />
					   			<span id="button-search">搜索</span>
					   			<span id="button-clear">重置</span>
				   			</div>
				   		</div>
				   		<br/>
						<div class="buttonbar default-bar-space"></div>
						<div class="grid">
							<table class="tb-grid taskCode-grid"></table>
						</div>
						
						<br/>

						<div id="<portlet:namespace/>aps-console-app-view-dialog-wrap"
							class="aps-console-app-view-dialog-wrap">
							<div id="selector-dialog"></div>
							<div id="create-account-dialog"></div>
							<div id="edit-account-dialog"></div>
							<div id="view-app-dialog"></div>
						</div>
					</div>
				</div>
			</div>
			<!-- 创建编码 -->
			<div id="taskCode-create">
				<div id="create-taskCode-dialog">
				   <table>
						<tr>
							<td class="first">编码数量：</td>
							<td class="second"><input name="codeNum"> <span
								class="require-mark">*</span></td>
							<td class="thrid"><label class="aps-form-prompt">只能输入数字</label>
								&nbsp;</td>
						</tr>
					</table>
				</div>
			</div>
		</div>

	</div>
</body>
</html>