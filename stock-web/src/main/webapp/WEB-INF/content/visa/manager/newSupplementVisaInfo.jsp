<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<script type="text/javascript" src="js/visa/newSupplementVisaInfo.js"></script>
<script type="text/javascript">
	$(function() {
		new SYQ.TextListManager({
			namespace : '',
			ENGINE_CONTEXT_PATH : '<%=basePath%>',
			CONTEXT_PATH : '',
			urls : {
				getFileTemplates : 'newVisa/ajaxListVisaFileFlow',
				addTextListUrl : 'newVisa/addTL'
			}
		});
	});
</script>
</head>
<body>

	<input type="hidden" name="visaPrincipalId" id="visaPrincipalId"
		value="${visaPrincipal.id }" />

	<div id="country-accordion">
		<ul>
			<li><a href="#accordion-${country.code}">${country.name}</a></li>
		</ul>
		<div id="accordion-${country.code}" name="countryApplyVisaInfo"
			code="${country.code}">
			<%@include file="./newVisaInfo.jsp"%>
		</div>

	</div>

	<div id="reject-file-flow-dialog">
		<table>
			<tr>
				<td class="first">补充材料原因：</td>
				<td class="second"><textarea id="rejectFlowMsg" name="rejectFlowMsg"> </textarea><span
					class="require-mark">*</span></td>
				<td class="thrid"></td>
			</tr>
		</table>
	</div>
</body>
</html>