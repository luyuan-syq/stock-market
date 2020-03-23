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
<title>护照信息列表</title>
<link type="text/css" rel="stylesheet" href="css/sso/sso.css" />
<script type="text/javascript" src="js/util/security-utils.js"></script>
<script type="text/javascript" src="js/task/task.js"></script>
</head>

<body>

<script type="text/javascript">
var taskBaseList;
$(function() {
	taskBaseList = new SYQ.Task({
		namespace : '',
		ENGINE_CONTEXT_PATH : '',
		CONTEXT_PATH : '',
		urls : {
			addtaskURL : 'task/add',
			addtaskBeforeURL : 'task/addBeforeURL',
			editTaskBeforeURL : 'task/editBeforeURL',
			deleteTaskURL : 'task/delete',
			getTaskURL : 'task/ajaxList',
			updatetaskURL : 'task/update',
			addCodeForTaskURL : 'taskCode/addForTask'
		}
	});
	
});
</script>

	<p desc="aps-portlet-desc" style="display: none">SSO设置用于对与门户进行单点登录的应用以及相关账号映射的管理。</p>
			
	<div class="aps-ssomanager-container">
		<div id="aps-task-tabs" class="aps-ssomanager-tabs">
			<ul>
				<li><a href="#task-list-search" class="account-listClass">任务检索</a></li>
				<%--
				<li><a href="<%=basePath%>task/addNewBeforeURL" class="account-listClass">添加任务</a></li>
				<li><a href="<%=basePath%>task/beforeAssign?id=1" class="account-listClass">任务分配</a></li>
				<li><a href="#account-list">导入任务</a></li>
				 --%>
			</ul>
			<div id="task-list-search">
				<div id="aps-console-app-view-wrap" class="aps-console-app-view-wrap">
					<div id="tab-app" class="tab-app">
					    <div id="search-panel">
				   			<div>
				   				<span class="label">批件号：</span>
					   			<input type="text" id="instructionNo" class="input-text" name="instructionNo"/>
					   			<span class="label">任务名称：</span>
					   			<input type="text" id="taskName" class="input-text" name="taskName"/>
					   			<span class="label">任务编码：</span>
					   			<input type="text" class="input-text" id="taskCode" name="taskCode"/>
					   			<span class="label">国家：</span>
					   			<input type="text" class="input-text" id="taskCountry" name="taskCountry"/>
					   			<span class="label">团长名称：</span>
					   			<input type="text" class="input-text" id="headerName" name="headerName"/>
					   			<br/><br />
					   			<span class="label">外事人员名称：</span>
					   			<input type="text" id="accountName" class="input-text" name="accountName"/>
					   			<span class="label">开始时间：</span>
					   			<input id="taskBeginTime" style="width: 118px" name="taskBeginTime"/>
					   			<span class="label">结束时间：</span>
					   			<input id="taskEndTime"  style="width: 118px" name="taskEndTime"/>
					   			<span class="label">状态：</span>
					   			<select id="searchStatus" class="input-text" style="height:25px">
					   			   <option value="">所有</option>
					   			   <option value="-1">新建</option>
					   			   <option value="1">办理中</option>
					   			   <option value="9">待接收</option>
					   			   <option value="10">已终止</option>
					   			   <option value="2">完成</option>
					   			   <option value="3">中止</option>
					   			   
					   			</select>
					   			<br /><br />
					   			<span id="button-search">搜索</span>
					   			<span id="button-clear">重置</span>
				   			</div>
				   		</div>
				   		
				   		<div id="task-list">
							<div id="aps-console-app-view-wrap"
								class="aps-console-app-view-wrap">
								<div id="tab-app" class="tab-app">
									<div class="buttonbar default-bar-space"></div>
									<div class="grid">
										<table class="tb-grid task-grid"></table>
									</div>
									<div id="<portlet:namespace/>aps-console-app-view-dialog-wrap"
										class="aps-console-app-view-dialog-wrap">
										<div id="selector-dialog"></div>
										<div id="create-task-dialog"></div>
										<div id="edit-task-dialog"></div>
										<div id="view-app-dialog"></div>
									</div>
								</div>
							</div>
						</div>
						
					</div>
				</div>
			</div>
			
		</div>
	</div>	
</body>
</html>