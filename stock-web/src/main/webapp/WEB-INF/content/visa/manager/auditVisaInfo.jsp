<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<script type="text/javascript" src="js/visa/auditVisaInfo.js"></script>
</head>
<body>
    
    <input type="hidden" name="personId" id="personId" value="${person.id }" /> 
    <input type="hidden" name="taskId" id="taskId" value="${task.id }"/>
    
	<div id="country-accordion">
		<ul>
			<c:forEach items="${countrys}" var="country">
				<li><a href="#accordion-${country.code}">${country.name}</a></li>
			</c:forEach>
		</ul>
		<c:forEach items="${countrys}" var="country">
			<div id="accordion-${country.code}"  name="countryApplyVisaInfo" code="${country.code}">
			
			   
			</div>
		</c:forEach>

	</div>
</body>
</html>