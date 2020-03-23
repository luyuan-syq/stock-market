<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
	<meta name="decorator" content="none" />
	<form id="country-info-form" class="aps-form" enctype="multipart/form-data"
		method="post">
		<input type="hidden" name="id" value="${country.id}">
		<table>
			<tr>
				<td class="first">国家简称：</td>
				<td class="second"><input name="code" value="${country.code}" disabled="disabled"> <span
					class="require-mark">*</span></td>
				<td class="thrid"><label class="aps-form-prompt">字母数字或下划线组成</label>
					&nbsp;</td>
			</tr>
			<tr>
				<td class="first">国家名称：</td>
				<td class="second"><input name="name" value="${country.name}"> <span
					class="require-mark">*</span></td>
				<td class="thrid"><label class="aps-form-prompt"></label>
					&nbsp;</td>
			</tr>
			
			<tr>
				<td class="first">是否签证：</td>
				<td class="second">是 ： <input name="needVisa" type="radio" value="1"  <c:if test="${country.needVisa == 1 }" >checked="checked"</c:if>/> 否 : <input name="needVisa" type="radio" value="0" <c:if test="${country.needVisa == 0 }" >checked="checked"</c:if>/></td>
				<td class="thrid"><label class="aps-form-prompt"></label>
					&nbsp;</td>
			</tr>
		</table>
	</form>
