<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="com.syq.web.utils.LoginConstants"%>
<%-- 获得 当前站点 --%>
<c:set var="doAsSitePath"
	value="<%=request.getParameter(\"doAsSitePath\")%>" />
<c:set var="closedPanels"
	value="<%=request.getParameter(\"closedPanels\")%>" />

<c:set var="loginType"
	value="<%=request.getSession().getAttribute(LoginConstants.LOGIN_TYPE)%>" />

<div id="wzax-leftnav" class="om-accordion" style="position: relative;">
	<c:if test="${loginType != null && loginType==1 }">
		<div class="om-panel-header">
			<div class="om-icon om-panel-icon panel-0"></div>
			<div class="om-panel-title">待办任务</div>
			<div class="om-panel-tool">
				<div class="om-icon om-panel-tool-collapse"></div>
			</div>
		</div>
		<div id="nav-panel-1"
			class="nav-panel om-panel-body om-widget-content"
			nodename="sitemanager" iconcls="panel-1" itemtitle="外事人员">
			<div class="nav-item">
				<div class="item-pic personalInfo"></div>
				<a nid="waitingCodeTask"
					href="/faff-web/passport/todoList?needCountAboutExprie=true"
					hidefocus="true">代办任务</a>
			</div>
		</div>
	</c:if>
	<c:if test="${loginType != null && loginType==2 }">
		<div class="om-panel-header">
			<div class="om-icon om-panel-icon panel-0"></div>
			<div class="om-panel-title">待办任务</div>
			<div class="om-panel-tool">
				<div class="om-icon om-panel-tool-collapse"></div>
			</div>
		</div>
		<div id="nav-panel-1"
			class="nav-panel om-panel-body om-widget-content"
			nodename="sitemanager" iconcls="panel-1" itemtitle="外事人员">
			<div class="nav-item">
				<div class="item-pic personalInfo"></div>
				<a nid="waitingTask" href="/faff-web/passport/todoFaffList"
					hidefocus="true">待办任务</a>
			</div>
		</div>
		
	</c:if>
	<c:if test="${loginType != null && loginType==1 }">
		<div class="om-panel-header">
			<div class="om-icon om-panel-icon panel-0"></div>
			<div class="om-panel-title">外事人员管理</div>
			<div class="om-panel-tool">
				<div class="om-icon om-panel-tool-collapse"></div>
			</div>
		</div>
		<div id="nav-panel-1"
			class="nav-panel om-panel-body om-widget-content"
			nodename="sitemanager" iconcls="panel-1" itemtitle="外事人员">

			<%--

			<div class="nav-item nav-item-selected">
				<div class="item-left"></div>
				<div class="item-middle">
					<div class="item-pic personalInfo"></div>
					<a nid="accountManager" href="/faff-web/account/list"
						hidefocus="true">外事人员</a>
				</div>
				<div class="item-right"></div>
			</div>
 			--%>

			<div class="nav-item">
				<div class="item-pic personalInfo"></div>
				<a nid="accountManager" href="/faff-web/account/list"
					hidefocus="true">外事人员</a>
			</div>
		</div>
	</c:if>
	<c:if test="${loginType != null && loginType==1 }">
		<div class="om-panel-header">
			<div class="om-icon om-panel-icon panel-0"></div>
			<div class="om-panel-title">编码管理</div>
			<div class="om-panel-tool">
				<div class="om-icon om-panel-tool-collapse"></div>
			</div>
		</div>
		<div id="nav-panel-1"
			class="nav-panel om-panel-body om-widget-content"
			nodename="sitemanager" iconcls="panel-1" itemtitle="站点管理">

			<div class="nav-item">
				<div class="item-pic sitesetting"></div>
				<a nid="TaskCodeManager" href="/faff-web/taskCode/list"
					hidefocus="true">编码管理</a>
			</div>
		</div>
	</c:if>
	<c:if test="${loginType != null && loginType==1 }">
		<div class="om-panel-header">
			<div class="om-icon om-panel-icon panel-0"></div>
			<div class="om-panel-title">外事任务管理</div>
			<div class="om-panel-tool">
				<div class="om-icon om-panel-tool-collapse"></div>
			</div>
		</div>

		<div id="nav-panel-1"
			class="nav-panel om-panel-body om-widget-content"
			nodename="sitemanager" iconcls="panel-1" itemtitle="外事任务管理">

			<div class="nav-item">
				<div class="item-pic membermanager"></div>
				<a nid="TaskManager" href="/faff-web/task/list" hidefocus="true">任务检索</a>
			</div>
			<div class="nav-item">
				<div class="item-pic membermanager"></div>
				<a nid="TaskManager" href="/faff-web/task/addNewBeforeURL"
					hidefocus="true">添加任务</a>
			</div>
			<!--  
			<div class="nav-item">
				<div class="item-pic membermanager"></div>
				<a nid="TaskManager" href="/faff-web/task/beforeAssign?id=1"
					hidefocus="true">任务分配</a>
			</div>
			-->
			<div class="nav-item">
				<div class="item-pic membermanager"></div>
				<a nid="TaskManager" href="/faff-web/task/importTask"
					hidefocus="true">导入任务</a>
			</div>

			<%-- 
		<c:if test="${loginType != null && loginType==2 }">
			<div class="nav-item">
				<div class="item-pic membermanager"></div>
				<a nid="TaskManager" href="/faff-web/task/beforeAddAccounts?none=0"
					hidefocus="true">添加出差人员</a>
			</div>
		</c:if>
		--%>
		</div>
	</c:if>
	<div class="om-panel-header">
		<div class="om-icon om-panel-icon panel-0"></div>
		<div class="om-panel-title">护照管理</div>
		<div class="om-panel-tool">
			<div class="om-icon om-panel-tool-collapse"></div>
		</div>
	</div>
	<div id="nav-panel-1" class="nav-panel om-panel-body om-widget-content"
		nodename="sitemanager" iconcls="panel-1" itemtitle="站点管理">
		<c:if test="${loginType != null && loginType==1 }">
			<div class="nav-item">
				<div class="item-pic sitesetting"></div>
				<a nid="TaskCodeManager" href="/faff-web/passport/list"
					hidefocus="true">护照管理</a>
			</div>
			<div class="nav-item">
				<div class="item-pic sitesetting"></div>
				<a nid="TaskCodeManager" href="/faff-web/passport/addBefore"
					hidefocus="true">护照录入</a>
			</div>
			<div class="nav-item">
				<div class="item-pic membermanager"></div>
				<a nid="TaskManager" href="/faff-web/task/auditList"
					hidefocus="true">信息审核</a>
			</div>
		</c:if>
		<div id="nav-panel-1"
			class="nav-panel om-panel-body om-widget-content"
			nodename="sitemanager" iconcls="panel-1" itemtitle="站点管理">
			<%-- 
			<c:if test="${loginType != null && loginType==2 }">
				<div class="nav-item">
					<div class="item-pic membermanager"></div>
					<a nid="TaskManager" href="/faff-web/renewalPassport/list"
						hidefocus="true">换发状态查询</a>
				</div>
			</c:if>
			--%>
			<c:if test="${loginType != null && loginType==1 }">
				<div class="nav-item">
					<div class="item-pic membermanager"></div>
					<a nid="TaskManager" href="/faff-web/renewalPassport/list"
						hidefocus="true">换发管理</a>
				</div>
			</c:if>
		</div>
		<c:if test="${loginType != null && loginType==2 }">
			<div class="nav-item">
				<div class="item-pic membermanager"></div>
				<a nid="TaskManager" href="/faff-web/passport/statusList"
					hidefocus="true">护照办理状态查询</a>
			</div>
		</c:if>
	</div>
	<c:if test="${loginType != null && loginType==1 }">
		<div class="om-panel-header">
			<div class="om-icon om-panel-icon panel-0"></div>
			<div class="om-panel-title">签证管理</div>
			<div class="om-panel-tool">
				<div class="om-icon om-panel-tool-collapse"></div>
			</div>
		</div>
		<div id="nav-panel-1"
			class="nav-panel om-panel-body om-widget-content"
			nodename="sitemanager" iconcls="panel-1" itemtitle="站点管理">
			<%-- 
			<div class="nav-item">
				<div class="item-pic sitesetting"></div>
				<a nid="TaskCodeManager" href="/faff-web/visa/index"
					hidefocus="true">模板管理</a>
			</div>
			--%>
			<div class="nav-item">
				<div class="item-pic sitesetting"></div>
				<a nid="TaskCodeManager" href="/faff-web/newVisa/ftManager"
					hidefocus="true">文件模板管理</a>
			</div>
			<div class="nav-item">
				<div class="item-pic sitesetting"></div>
				<a nid="TaskCodeManager" href="/faff-web/newVisa/tlManager"
					hidefocus="true">资料清单管理</a>
			</div>

			<div class="nav-item">
				<div class="item-pic sitesetting"></div>
				<a nid="TaskCodeManager" href="/faff-web/visa/taskListForSigned"
					hidefocus="true">签证管理</a>
			</div>


		</div>
	</c:if>

	<c:if test="${loginType != null && loginType==2 }">
		<div class="om-panel-header">
			<div class="om-icon om-panel-icon panel-0"></div>
			<div class="om-panel-title">签证管理</div>
			<div class="om-panel-tool">
				<div class="om-icon om-panel-tool-collapse"></div>
			</div>
		</div>
		<div id="nav-panel-1"
			class="nav-panel om-panel-body om-widget-content"
			nodename="sitemanager" iconcls="panel-1" itemtitle="站点管理">
			<div class="nav-item">
				<div class="item-pic membermanager"></div>
				<a nid="TaskManager" href="/faff-web/visa/statusList"
					hidefocus="true">签证办理状态查询</a>
			</div>
<%-- 
			<div class="nav-item">
				<div class="item-pic sitesetting"></div>
				<a nid="TaskCodeManager"
					href="/faff-web/visa/beforeApplyVisa?none=0" hidefocus="true">签证材料录入</a>
			</div>
--%>

		</div>
	</c:if>

	<c:if test="${loginType != null && loginType==1 }">
		<div class="om-panel-header">
			<div class="om-icon om-panel-icon panel-0"></div>
			<div class="om-panel-title">系统管理</div>
			<div class="om-panel-tool">
				<div class="om-icon om-panel-tool-collapse"></div>
			</div>
		</div>
		<div id="nav-panel-1"
			class="nav-panel om-panel-body om-widget-content"
			nodename="sitemanager" iconcls="panel-1" itemtitle="日志管理">

			<!--  
			<div class="nav-item">
				<div class="item-pic sitesetting"></div>
				<a nid="accountManager" href="log/list" hidefocus="true">日志管理</a>
			</div>
            -->

			<div class="nav-item">
				<div class="item-pic sitesetting"></div>
				<a id="accountManager" href="mail/view" hidefocus="true">邮件服务</a>
			</div>

			<div class="nav-item">
				<div class="item-pic sitesetting"></div>
				<a nid="accountManager" href="/faff-web/manager/list"
					hidefocus="true">管理员管理</a>
			</div>

			<div class="nav-item">
				<div class="item-pic sitesetting"></div>
				<a nid="accountManager" href="/faff-web/role/list" hidefocus="true">角色管理</a>
			</div>

		</div>
	</c:if>

</div>