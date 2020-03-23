<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<meta name="decorator" content="none" />

<META HTTP-EQUIV="Pragma" CONTENT="no-cache"/>

<c:set var="SPLITER" value="_"></c:set>

<script type="text/javascript"
	src="js/visa/visaInfo.js"></script>

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
			<%@include file="./visaInfoGetBasicFields.jsp"%>

			<c:set var="fieldCategory" value="${customMsg }"></c:set>
			<c:set var="isOutPutCategory" value="true"></c:set>
			<%@include file="./visaInfoGetCustomFields.jsp"%>
			<tr>
				<td colspan="3">
					<div class="table-title">&nbsp;</div>
				</td>
			</tr>
		</table>
		<c:choose>
		
		<c:when test="${principal.flowStatus == 0 || principal.flowStatus == 2 }">
			<div class="text_align_c pad ">
				<button type="button" id="buttonApplyVisaInfo" class="button_u"
					onmousemove="this.className='button_f'"
					onmousedown="this.className='button_d'"
					onmouseout="this.className='button_u'" code="${code}">提交表单</button>
				&nbsp;
				<button class="button_u" type="reset"
					onmousemove="this.className='button_f'"
					onmousedown="this.className='button_d'"
					onmouseout="this.className='button_u'">&nbsp;&nbsp;重置&nbsp;&nbsp;</button>
				&nbsp;
			</div>
		</c:when>
		<c:when test="${principal.flowStatus == 1 }">
		 	 待审核
		</c:when>
		<c:when test="${principal.flowStatus == 3 }">
		 	 办理中
		</c:when>
		<c:when test="${principal.flowStatus == 4 }">
		 	 已归档
		</c:when>
		</c:choose>
	</form>
</div>
<!--[if gt IE 6 ]> </div> <![endif]-->