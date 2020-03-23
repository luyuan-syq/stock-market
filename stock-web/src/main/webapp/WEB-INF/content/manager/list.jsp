<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>管理员管理</title>
<link type="text/css" rel="stylesheet" href="css/sso/sso.css" />
<script type="text/javascript"
	src="js/omui/development-bundle/ui/om-ajaxsubmit.js"></script>
<script type="text/javascript" src="js/util/security-utils.js"></script>
<script type="text/javascript" src="js/manager/manager.js"></script>
<script type="text/javascript"
	src="js/jqueryplugins/jstree_pre1.0_stable/jquery.jstree.js"></script>
<script type="text/javascript" src="js/manager/managerAdd.js"></script>
</head>

<body>

	<script type="text/javascript">
		$(function() {
			var target = '.aps-ssomanager-container .aps-ssomanager-tabs';
			new SYQ.Role({
				namespace : '',
				ENGINE_CONTEXT_PATH : '',
				CONTEXT_PATH : '',
				urls : {
					getManagerURL : 'manager/ajaxList',
					addManagerBeforeURL : 'manager/addBeforeURL',
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
				<li><a href="#manager-list">管理员列表</a></li>
				<li><a href="#manager-add" class="account-listClass">添加管理员</a></li>
			</ul>
			<div id="manager-list">
				<div id="aps-console-app-view-wrap"
					class="aps-console-app-view-wrap">
					<div id="tab-app" class="tab-app">
						<div class="buttonbar default-bar-space"></div>
						<div class="grid">
							<table class="tb-grid manager-grid"></table>
						</div>

						<div id="<portlet:namespace/>aps-console-app-view-dialog-wrap"
							class="aps-console-app-view-dialog-wrap">
							<div id="selector-dialog"></div>
							<div id="create-role-dialog"></div>
							<div id="edit-app-dialog"></div>
							<div id="view-app-dialog"></div>
						</div>
					</div>
				</div>
			</div>
			<div id="manager-add">
				<form id="manager-info-form" class="aps-form"
					enctype="multipart/form-data" method="post">
					<table>
						<tr>
							<td class="first">管理员名称：</td>
							<td class="second"><input name="name"> <span
								class="require-mark">*</span></td>
							<td class="thrid"><label class="aps-form-prompt">字母数字或下划线组成</label>
								&nbsp;</td>
						</tr>
						<tr>
							<td class="first">密码：</td>
							<td class="second"><input name="password" /></td>
							<td class="thrid"></td>
						</tr>
						<tr>
							<td class="first">确认密码：</td>
							<td class="second"><input name="repassword" /></td>
							<td class="thrid"></td>
						</tr>
						<tr>
							<td class="first">手机：</td>
							<td class="second"><input name="contactPhone" /></td>
							<td class="thrid"></td>
						</tr>
						<tr>
							<td class="first">电话：</td>
							<td class="second"><input name="mobile" /></td>
							<td class="thrid"></td>
						</tr>
						<tr>
							<td class="first">邮箱：</td>
							<td class="second"><input name="contactEmail" /></td>
							<td class="thrid"></td>
						</tr>
						<tr>
							<td class="first">管理员状态：</td>
							<td class="second">
							<select name="status">
							   <option value="1">有效</option>
							   <option value="0">无效</option>
							</select>
							</td>
							<td class="thrid"></td>
						</tr>
						<tr>
							<td class="first">角色：</td>
							<td class="second"><input type="button"
								onclick="openRole()" value="角色配置"></td>
						</tr>
					</table>
					<div id="iconInfoTip" style="display: none;"></div>
					<div class="separator"></div>
					<div class="text_align_c pad ">
						<button type="button" class="button_u"
							onmousemove="this.className='button_f'"
							onmousedown="this.className='button_d'"
							onmouseout="this.className='button_u'"  onclick="addManager()">提交表单</button>
						&nbsp;
						<button class="button_u" type="reset"
							onmousemove="this.className='button_f'"
							onmousedown="this.className='button_d'"
							onmouseout="this.className='button_u'">&nbsp;&nbsp;重置&nbsp;&nbsp;</button>
						&nbsp;
					</div>
				</form>
				<div id="giveRole" title="角色树">
					<div id="roleTree123"></div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>