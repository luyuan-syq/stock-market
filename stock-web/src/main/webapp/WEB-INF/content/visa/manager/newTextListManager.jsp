<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
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
<base href="<%=basePath%>" />
<title>签证模板列表</title>
<link type="text/css" rel="stylesheet" href="css/sso/sso.css" />
<link type="text/css" rel="stylesheet" href="css/faff/common.css" />
<link type="text/css" rel="stylesheet" href="css/faff/visa.css" />
<link type="text/css" rel="stylesheet"
	href="css/faff/visa/user-config.css" />
<script type="text/javascript"
	src="js/jqueryplugins/jstree_pre1.0_stable/jquery.jstree.js"></script>
<script type="text/javascript" src="js/util/security-utils.js"></script>
<script type="text/javascript" src="js/visa/visaCountry.js"></script>
<script type="text/javascript" src="js/visa/newTextListManager.js"></script>

</head>

<body>

	<script type="text/javascript">
		var textListManager;
		$(function() {
			var target = '#countryVisaDetail';
			textListManager = new SYQ.TextListManager({
				namespace : '',
				ENGINE_CONTEXT_PATH : '',
				CONTEXT_PATH : '',
				urls : {
					getFileTemplates : 'newVisa/ajaxList',
					addTextListUrl : 'newVisa/addTL',
					deleteTextValueUrl : 'newVisa/deleteTV'
				}
			});

			$(target).omTabs({
				border : false,
				onActivate : function(n, event) {

				}
			});
		});
	</script>

	<div id="overall">
		<input type="hidden" id="selectedCountry" />
		<div class="aps-security-tree-container aps-tree-container">
			<div id="visaCountryTree">
				<img src="images/throbber.gif" alt="" /> 加载中，请稍候……
			</div>
		</div>
		<div id="group-separator" class="aps-console-column-exspand">
			<div class="aps-collapse-separator"></div>
		</div>
		<div id="countryVisaDetail" class="detail-expand-width ">
			<ul>
				<li><a href="#text-list">资料清单管理</a></li>
			</ul>

			<div id="text-list">
				<div id="aps-console-app-view-wrap"
					class="aps-console-app-view-wrap">
					<div id="tab-app" class="tab-app">
						<div>
							<table>
								<tr>
									<td><input type="radio" value="1" name="visaFileTemplateType"
										checked="checked">非标准文件</input></td>
									<td><input type="text" name="visaFileTemplateValue" style="width: 800px" />
									</td>
									<td></td>
								</tr>
								<tr>
									<td><input type="radio" value="0" name="visaFileTemplateType">标准文件</input>
									</td>
									<td><select name="visaFileTemplateValue">
 											<c:forEach items="${vfts}" var="vft">
 											   <option value="${vft.id}">${vft.name} </option>
 											</c:forEach>
									    </select>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<button onclick="addVisaFileTemplate()">添加</button>
									</td>
								</tr>
							</table>


						</div>
						<div class="grid">
							<table class="tb-grid text-list-grid"></table>
						</div>

					</div>
				</div>
			</div>

		</div>
	</div>
	<div>
		<div id="create-country-dialog">
			<%@include file="../country/add.jsp"%>
		</div>
		<div id="edit-country-dialog"></div>
	</div>
</body>
</html>