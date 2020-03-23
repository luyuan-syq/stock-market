<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<meta name="decorator" content="none" />

<META HTTP-EQUIV="Pragma" CONTENT="no-cache"/>

<c:set var="SPLITER" value="_"></c:set>

<script type="text/javascript"
	src="js/visa/getAuditInfo.js"></script>

<%--下面的条件编译，千万要注意空格，只有IE会解析 --%>
<!--[if lt IE 7 ]> <div class="ie6"> <![endif]-->
<!--[if IE 7 ]><div class="ie7"> <![endif]-->
<!--[if IE 8 ]><div class="ie8"> <![endif]-->
<!--[if IE 9 ]><div class="ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><div><![endif]-->
<div id="${PORTLET_NAMESPACE}aps-console-account-personalInfo"
	class="aps-console-account">
	<span></span>
	<form action="${setPersoanlInfoAJAXURL}"
		id="visa-apply-info-form-${code}" method="post" class="aps-form person-info-form" >
		<table>
			<%@include file="./aduitVisaInfoGetBasicFields.jsp"%>

			<c:set var="fieldCategory" value="${customMsg }"></c:set>
			<c:set var="isOutPutCategory" value="true"></c:set>
			<%@include file="./visaInfoGetCustomFields.jsp"%>
			<tr>
				<td colspan="3">
					<div class="table-title">&nbsp;</div>
				</td>
			</tr>
			
			<!-- 定义 -->
			<%@include file="./auditApplyVisaInfo.jsp"%>
		</table>
	</form>
</div>
<!--[if gt IE 6 ]> </div> <![endif]-->