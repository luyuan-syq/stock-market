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
<title>角色编辑</title>
<link type="text/css" rel="stylesheet" href="css/sso/sso.css" />
<script type="text/javascript"
	src="js/omui/development-bundle/ui/om-ajaxsubmit.js"></script>
<script type="text/javascript" src="js/util/security-utils.js"></script>
<script type="text/javascript"
	src="js/jqueryplugins/jstree_pre1.0_stable/jquery.jstree.js"></script>
<script type="text/javascript" src="js/role/roleEdit.js"></script>
<script type="text/javascript">
    var giveRightDialogTemp;
	function openSecurity() {
		giveRightDialogTemp.omDialog('open');
	}
	$(function() {
		// 获取树权限
		$.ajaxSetup({
			cache : false
		});
		giveRightDialogTemp = $("#rightTree123").jstree(
				{
					"plugins" : [ "json_data", "contextmenu", "types",
							"themes", "search", "ui", "checkbox" ],
					core : {
						strings : {
							loading : "加载中，请稍后……",
							new_node : "输入名称",
							initially_open : [ "utsnode" ]
						}
					},
					json_data : {
						"ajax" : {
							"url" : "./role/loadModuleTree",
							"data" : function(n) {
								return {
									id : $(n).attr("id") || ""
								};
							}
						}
					},
					search : {
						"case_insensitive" : true,
						"ajax" : {}
					},
					themes : {
						theme : "classic", // 设置theme主题，默认是"default"，可选值：default、apple、classic、default-rtl
						url : false, // 设置theme css文件的路径
						dots : true, // 是否显示虚线点
						icons : true
					// 是否显示节点前的图标

					},
					contextmenu : {
						select_node : true,
						show_at_node : false,
						items : function(nodeitem) {
						}
					}
				}).bind(
				"loaded.jstree",
				function(event, data) {
					var jsonrights = ${rights};

					for ( var i in jsonrights) {
						$.jstree._reference('rightTree123').check_node(
								$("#" + jsonrights[i].id))
					}
				});

		giveRightDialogTemp = $("#giveRight").omDialog({
			title : '功能权限',
			height : '390',
			width : '520',
			resizable : false,
			modal : true,
			autoOpen : false,
			buttons : [ {
				text : "确定",
				click : function() {
					giveRightDialogTemp.omDialog('close');
				}
			}, {
				text : "取消",
				click : function() {
					giveRightDialogTemp.omDialog('close');
				}
			} ]
		});

	})
</script>
</head>

<body>

	<script type="text/javascript">
		$(function() {
			var target = '.aps-ssomanager-container .aps-ssomanager-tabs';
			$(target).omTabs({
				border : false,
				active : 1,
				lazyLoad : true,
				onActivate : function(n, event) {
					if (n == 0) {
						window.location.href = "role/list";
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
				<li><a href="#role-list" class="role-listClass">角色列表</a></li>
				<li><a href="#role-edit" class="role-editClass">修改角色</a></li>
			</ul>
			<div id="role-list"></div>
			<div id="role-edit">
				<form id="role-info-form" class="aps-form"
					enctype="multipart/form-data" method="post">
					<input type="hidden" name="id" value="${role.id}"/>
					<table>
						<tr>
							<td class="first">角色名称：</td>
							<td class="second"><input name="name" value="${role.name }">
								<span class="require-mark">*</span></td>
							<td class="thrid"><label class="aps-form-prompt">字母数字或下划线组成</label>
								&nbsp;</td>
						</tr>
						<tr>
							<td class="first">描述：</td>
							<td class="second"><textarea name="description">${role.description } </textarea></td>
							<td class="thrid"></td>
						</tr>
						<tr>
							<td class="first">功能权限：</td>
							<td class="second"><input type="button"
								onclick="openSecurity()" value="功能权限"></td>
						</tr>
					</table>
					<div id="iconInfoTip" style="display: none;"></div>
					<div class="separator"></div>
					<div class="text_align_c pad ">
						<button type="button" class="button_u"
							onmousemove="this.className='button_f'"
							onmousedown="this.className='button_d'"
							onmouseout="this.className='button_u'"
							onclick="editSecurityRole()">提交表单</button>
						&nbsp;
						<button class="button_u" type="reset"
							onmousemove="this.className='button_f'"
							onmousedown="this.className='button_d'"
							onmouseout="this.className='button_u'">&nbsp;&nbsp;重置&nbsp;&nbsp;</button>
						&nbsp;
					</div>
				</form>
				<div id="giveRight" title="功能权限树">
					<div id="rightTree123"></div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>