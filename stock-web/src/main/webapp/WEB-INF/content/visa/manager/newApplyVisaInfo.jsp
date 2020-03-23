<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<script type="text/javascript" src="js/visa/newApplyVisaInfo.js"></script>
<script type="text/javascript">
$(function() {
	new SYQ.TextListManager({
		namespace : '',
		ENGINE_CONTEXT_PATH : '',
		CONTEXT_PATH : '',
		urls : {
			getFileTemplates : 'newVisa/ajaxListForSubmit',
			addTextListUrl : 'newVisa/addTL'
		}
	});
});

</script>
</head>
<body>
    
    <input type="hidden" name="personId" id="personId" value="${person.id }" /> 
    <input type="hidden" name="taskId" id="taskId" value="${task.id }"/>
    <input type="hidden" name="countryId" id="countryId" value="${country.id }"/>
    <input type="hidden" name="countryCode" id="countryCode" value="${country.code }"/>
    <input type="hidden" name="principalId" id="principalId" value="${principal.id }"/>
	<div id="country-accordion">
		<ul>
			 <c:if test="${country.needVisa == 1 }">
				<li><a href="#accordion-${country.code}">${country.name}</a></li>
			 </c:if>
		</ul>
		  <c:if test="${country.needVisa == 1 }">
			<div id="accordion-${country.code}"  name="countryApplyVisaInfo" code="${country.code}">
			    <%@include file="./newVisaInfo.jsp"%>
			</div>
		  </c:if>

	</div>
	
	<!-- 上传文件 -->
	<div id="upload-visaFileTemplate-dialog">
		<input id="file_upload" name="file" type="file" />
		<button value="上传" onclick="$('#file_upload').omFileUpload('upload')">上传</button>
	</div>
</body>
</html>