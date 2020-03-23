<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<form id="country-info-form" class="aps-form" enctype="multipart/form-data"
		method="post">
		<table>
			<tr>
				<td class="first">国家简称：</td>
				<td class="second"><input name="code"> <span
					class="require-mark">*</span></td>
				<td class="thrid"><label class="aps-form-prompt">字母数字或下划线组成</label>
					&nbsp;</td>
			</tr>
			<tr>
				<td class="first">国家名称：</td>
				<td class="second"><input name="name"> <span
					class="require-mark">*</span></td>
				<td class="thrid"><label class="aps-form-prompt"></label>
					&nbsp;</td>
			</tr>
			
			<tr>
				<td class="first">是否签证：</td>
				<td class="second">是 ： <input name="needVisa" type="radio" value="1"  checked="checked"/> 否 : <input name="needVisa" type="radio" value="0"/></td>
				<td class="thrid"><label class="aps-form-prompt"></label>
					&nbsp;</td>
			</tr>
		</table>
	</form>
