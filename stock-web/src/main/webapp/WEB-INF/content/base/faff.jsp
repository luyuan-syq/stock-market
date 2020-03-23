<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="com.syq.web.utils.LoginConstants"%>

<c:set var="loginType" value="<%=request.getSession().getAttribute(LoginConstants.LOGIN_TYPE)%>" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath %>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> <decorator:title /></title>
<jsp:include page="headerResource.jsp" />
<decorator:head />
<script type="text/javascript" src="js/faff.js"></script>
</head>

<body>
	<%-- 跳转到页面某块布局的锚点 --%>
	<ul id="wzax-skiplinks">
		<li><a class="wzax-skip" href="#wzax-nav">锚点1</a></li>
		<li><a class="wzax-skip" href="#wzax-col3">锚点2</a></li>
	</ul>
	
	<%-- 顶级导航栏 --%>
	<div id="wzax-topnav">
		<div id="wzax-logo-wrapper" style="top:0px">
			  <ul>
				<li class="item-guide">
					<div class="site-nav nav-hover" style="background-color: transparent;">
					<img src="images/logo.png" width="200" height="60" >
					</div>
				</li>
				<li class="item-guide" style="padding-left: 30px;top:5px">
					<div style="background-color: transparent;">
					     <strong style="font-size: 20px;color:black;">外事管理系统</strong>
					</div>
				</li>
				
			  </ul>
		</div>
		<div id="wzax-guidebar">
			<ul>
				<li class="item-guide ">
					<div class="left"></div>
					<div class="middle site-nav nav-hover" style="background-color: transparent;">
					
					</div>
					<div class="right "></div>
				</li>
				<li class="item-guide">
					<div class="separator"></div>
				</li>
				<li class="item-guide ">
					<div class="left"></div>
					<div class="middle site-nav nav-hover" style="background-color: transparent;">
					<a class="principal" href="javascript:void(0)" style="display: inline;">${sessionScope.session_manager.name}</a>
					<span class="drop-down" style="background-image: url(&quot;./images/header_arrow.png&quot;);">&nbsp;</span>
					</div>
					<div class="right "></div>
				</li>
 
				<li class="item-guide">
					<div class="separator"></div>
				</li>
				<li class="item-guide">
					<div class="left"></div>
					<c:if test="${loginType != null && loginType==1 }">
					<div class="middle nav-hover">
						<a id = "toListUrl" class="principal" href="passport/todoList?needCountAboutExprie=true"><b>待办任务</b>：<font color="#FF0000"><b id = "todoCount"></b></font></a>
					</div>
					</c:if>
					<c:if test="${loginType != null && loginType==2 }">
					<div class="middle nav-hover">
						<a id = "toListUrl" class="principal" href="passport/todoFaffList"><b>待办任务</b>：<font color="#FF0000"><b id = "todoCount"></b></font></a>
					</div>
					</c:if>
					<div class="right"></div>
				</li>
				<li class="item-guide">
					<div class="separator"></div>
				</li>
				
				<li class="item-guide">
					<div class="left"></div>
					<div class="middle nav-hover">
						<a class="wzax-logout-href" href="manager/logout">注销</a>
					</div>
					<div class="right"></div>
				</li>
					
				<li class="item-guide">
					<div id="wzax-nail"></div>
				</li>
			</ul>
		</div>
	</div>
	
	<%-- 全部主体内容含边框 --%>
	<div class="wzax-page_margins">
		<%-- 主体内容 --%>
		<div class="wzax-page">
			<div id="wzax-head"></div>
			<div id="wzax-main">
				<div id="wzax-col1">
					<div id="wzax-col1_content" class="wzax-clearfix">
						<%@ include file="./leftnav.jsp" %>
					</div>
				</div>
				<div id="wzax-col2">
				   <div id="wzax-col2_content" class="wzax-clearfix">
						<div class="wzax-console-column-switch wzax-console-column-exspand wzax-console-column-exspand-layout-console wzax-console-column-switch-layout-console">
							<div></div>
						</div>
					</div>
				</div>
				<div id="wzax-col3">
				      <decorator:body />
				</div>
			</div>
		</div>
	</div>
	<div id="wzax-scrolltop"></div>
</body>
</html>