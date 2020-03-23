<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="wzax-breadcrumbs-nav">
	<c:if test="${!empty adminBreadcrumbsMap.elements}">
		<ul>
			<%-- 从索引1开始迭代, 目的是过滤掉根目录的显示 --%>
			<c:forEach items="${adminBreadcrumbsMap.elements }" var="menuElement" begin="1" varStatus="status">
				<c:if test="${menuElement.elementType eq 'option' }">
					<%-- 如果该<li>是最后一个, 就标记为class="last", 用于控制不显示分隔图标 --%>
					<c:choose>
						<c:when test="${status.last }">
							<li class="last">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<%-- 判断是folder还是page, 分别标记上不同的class --%>
					<c:if test="${menuElement.type eq 'folder' }">
						<span>
							<a class="wzax-folder" href="${menuElement.url }" hidefocus="true">${menuElement.title }</a>
						</span>
					</c:if>
					<c:if test="${menuElement.type eq 'page' }">
						<span>
							<a class="wzax-psml" href="${menuElement.url }" hidefocus="true">${menuElement.title }</a>
						</span>
					</c:if>
					</li>
				</c:if>
			</c:forEach>
		</ul>
	</c:if>
</div>
