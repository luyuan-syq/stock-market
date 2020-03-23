<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<script type="text/javascript" src="js/visa/newDaibanAuditVisaInfo.js"></script>
</head>
<body>
    
    <input type="hidden" name="visaPrincipalId" id="visaPrincipalId" value="${visaPrincipal.id }" /> 
    
	<div id="country-accordion">
		<ul>
				<li><a href="#accordion-${country.code}">${country.name}</a></li>
		</ul>
			<div id="accordion-${country.code}"  name="countryApplyVisaInfo" code="${country.code}">
			
			   
			</div>

	</div>
</body>
</html>