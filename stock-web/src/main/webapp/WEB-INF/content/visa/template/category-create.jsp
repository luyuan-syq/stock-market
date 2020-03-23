<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form action="./visaTemplate/createFieldCategory" method="post" class="aps-form">
     <table>
         <tr>
            <td class="first">
               <span>类别简称</span>
            </td>
            <td class="second">
               <input name="categoryId" />
               <span class="require-mark">*</span>
            </td>
            <td class="third">
               <label class="aps-form-prompt">字母数字或下划线组成</label>
               &nbsp;
            </td>
         </tr>
         <tr>
            <td class="first">
               <span>类别名称</span>
            </td>
            <td class="second">
               <input name="categoryName" />
               <span class="require-mark">*</span>
            </td>
            <td class="third">
               &nbsp;
            </td>
         </tr>
     </table>
</form>