<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link href="${contextPath }/account/css/account.css" rel="stylesheet" type="text/css" />
<%-- but for the sake of performance, it would make better sense to include the CSS first then this script.
http://code.google.com/p/html5shiv/ --%>
    <!--[if lt IE 9]>
    <script  type="text/javascript" src="${contextPath}/account/js/html5.js"></script>
    <![endif]-->
<script type="text/javascript" src="js/customFieldValidate.js"></script>
<script type="text/javascript" src="js/accountBase.js"></script>
<script type="text/javascript">
<!--
 $(function(){
	 SYQ.Account.Base.SPLITER_FOR_CATEGORY_AND_FIELD = "${SPLITER}"; 
 });
//-->
</script>