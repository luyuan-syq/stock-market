<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.syq.web.utils.LoginConstants"%>

<%
  if(request.getSession().getAttribute(LoginConstants.SESSION_MANAGER) != null) {
    response.sendRedirect("index");
    return;
  }
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="username" value="<%=LoginConstants.USERNAME%>" scope="page" />
<c:set var="password" value="<%=LoginConstants.PASSWORD%>" scope="page" />
<c:set var="errorCode"
	value="<%=request.getSession().getAttribute(
					LoginConstants.ERRORCODE)%>"
	scope="page" />
<c:set var="loginType"
	value="<%=request.getSession().getAttribute(
					LoginConstants.LOGIN_TYPE)%>"
	scope="page" />
<%-- 为避免刷新页面显示上次的登录响应信息 --%>
<%
  request.getSession().removeAttribute(LoginConstants.ERRORCODE);
%>
<html>
<head>
<meta name="decorator" content="logint" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>中检外事管理系统</title>
<link rel="icon" href="images/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon" />

<link href="css/apsl/core/base.css" rel="stylesheet" type="text/css" />
<link href="css/login/login-screen.css" rel="stylesheet" type="text/css" />
<link href="css/apsl/core/content.css" rel="stylesheet" type="text/css" />
<!--[if lte IE 7]>
	<link href="css/login/login_hacks.css" rel="stylesheet" type="text/css" />
<![endif]-->

<script type="text/javascript" src="js/core/jquery-1.7.2.min.js">
	
</script>
<script type="text/javascript"
	src="js/omui/development-bundle/ui/minified/om-validate.min.js">
	
</script>
<script type="text/javascript" src="js/aps/aps.util.js">
	
</script>
<script type="text/javascript" src="js/aps/login/aps-login.js">
	
</script>

<script type="text/javascript">
	$(function() {
		new APS.Login({
			username : '${username}',
			password : '${password}',
			errorCode : '${errorCode}'
		});
		
	});
</script>
</head>
<body class="aps">
	<div class="aps-page_margins">
		<div class="aps-page">
			<div id="aps-header">
				<div id="aps-logo"></div>
				<div id="aps-topnav"></div>
			</div>
			<div id="aps-nav">
				<div class="aps-hlist"></div>
			</div>
			<div id="aps-login_main">
				<div class="login_box_bg">
					<div class="login_box">
						<div class="title">
						
							<a href="javascript:void(0);" <c:if test="${loginType == null || (loginType !=null && loginType!=2) }">class="def"</c:if> id="type1"
								onclick="swithtap(1)">用户登录</a><a href="javascript:void(0);"
								id="type2" onclick="swithtap(2)" <c:if test="${loginType !=null && loginType==2 }">class="def"</c:if>>任务编码</a>
						</div>
						<div class="login_cont" id="passwordLogin" <c:if test="${(loginType !=null && loginType==2) }">style="display: none"</c:if>>
							<span name="respMsg" style="color: red"></span>
							<form method="post"
								action="manager/login">

								<input id="username" class="text name" type="text"
									name="username" class="focus" value="" /> <input id="password"
									type="password" class="text pw" name="password" value="" />
								<div class="btn_box">
									<div class="left">
										<input type="checkbox" name="remember" id="remember" /> <label
											for="remember"> 记住密码 </label>
									</div>
									<input id="submit" type="submit" name="regsubmit" value="登 录"
										class="btn" />
								</div>
								<div class="help">
									<a href="javascript:downLoadHelp();" style="color: #2d2c2d;">登录帮助</a>
								</div>
							</form>
						</div>
						<div class="login_cont" <c:if test="${loginType == null || (loginType !=null && loginType!=2) }">style="display: none"</c:if> id="certLogin">
						    <span name="respMsg" style="color: red"></span>
						   	<form action="manager/codeLogin" id="softkey_form" name="softkey_form"
								method="post">
								<input id="code" class="text name" type="text"
									name="code" class="focus" value="" />
								<div class="btn_box">
									<input type="button" name="regsubmit" id="button" value="登 录"
										class="btn" onclick="checkFormForCode();" />
								</div>
								<div class="help">
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
			<div id="aps-ie_clearing">&nbsp;</div>
		</div>
	</div>
	<div id="aps-footer">
		<div id="aps-footer_content">北京网众安信科技有限公司版权所有</div>
	</div>
</body>
</html>