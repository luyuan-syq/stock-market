<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<input type="hidden" name="visaPrincipalId" value="${principal.id}" />
<input type="hidden" name="code" value="${code }" />
<%-- 
<tr>
	<td colspan="3">
		<div class="table-title closed-top-title">
			基本信息
		</div>
	</td>
</tr>
<tr>
	<td class="first">
		<div class="inputLabel">
			<label for="${PORTLET_NAMESPACE}username">
				编号:
			</label>
		</div>
	</td>
	<td class="second" colspan="2">
		<div class="inputField">
			<span id="${PORTLET_NAMESPACE}username">  </span>
		</div>
	</td>
</tr>
--%>
<c:if test="${principal.flowStatus == 2 }">
<tr>
	<td class="first">
		<div class="inputLabel">
			<label>
				拒绝原因:
			</label>
		</div>
	</td>
	<td class="second" colspan="2">
		<div class="inputField">
			<span> <textarea id="refusedMsg-${principal.id}" rows="50" cols="50">${principal.flowMsg }</textarea> </span>
		</div>
	</td>
</tr>
</c:if>
