<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%--遍历字段 --%>



<c:forEach items="${fieldCategory}" var="oneFieldCategory"
	varStatus="cateStatus">

	<c:if test="${isOutPutCategory}">
		<tr>
			<td colspan="3">
				<div class="table-title">${oneFieldCategory.name}</div>
			</td>
		</tr>
	</c:if>
	<c:forEach items="${oneFieldCategory.fields}" var="onePrincipalField"
		varStatus="status">
		<%--如果字段可见 --%>
		<c:if test="${onePrincipalField.visible}">
			<tr>
				<c:choose>
					<%--如果为多行文本框 --%>
					<c:when test="${onePrincipalField.displayType=='textarea'}">
						<td class="first">
							<div class="inputLable">
								<label
									for="${PORTLET_NAMESPACE}${oneFieldCategory.id}${SPLITER}${onePrincipalField.id}">
									${onePrincipalField.name }: </label>
							</div>
						</td>
						<td class="seond">
							<div class="inputField">
								<textarea
									name="${ oneFieldCategory.id}${SPLITER}${onePrincipalField.id}"
									class="requiredRule1 regXp-apsSysUserInfo_principal.label"
									id="${PORTLET_NAMESPACE}${onePrincipalField.id}"
									<c:if test="${!onePrincipalField.editable}">
				          readonly="readonly" disabled="true"
				        </c:if>>
				        <c:if test="${onePrincipalField.values[0].value != null}">${onePrincipalField.values[0].value}</c:if>
					</textarea>
								<span class="require-mark"><c:if
										test="${onePrincipalField.required}">*</c:if></span>
							</div>
						</td>
						<td class="third"></td>
					</c:when>
					<%--如果为text --%>
					<c:when test="${onePrincipalField.displayType=='text'}">
						<td class="first">
							<div class="inputLable">
								<label
									for="${PORTLET_NAMESPACE}${oneFieldCategory.id}${SPLITER}${onePrincipalField.id}">
									${onePrincipalField.name }: </label>
							</div>
						</td>
						<td class="seond">
							<div class="inputField">
								<input type="text"
									name="${oneFieldCategory.id}${SPLITER}${onePrincipalField.id}"
									id="${PORTLET_NAMESPACE}${onePrincipalField.id}"
									<c:if test="${!onePrincipalField.editable}">
				          readonly="readonly" disabled="true"
				        </c:if>
									<c:if test="${onePrincipalField.values[0].value != null}"> value="${onePrincipalField.values[0].value}"</c:if> />
								<span class="require-mark"><c:if
										test="${onePrincipalField.required}">*</c:if></span>
							</div>
						</td>
						<td class="third"></td>
					</c:when>
					<%--如果为选择框 --%>
					<c:when test="${onePrincipalField.displayType=='select'}">
						<td class="first">
							<div class="inputLable">
								<label
									for="${PORTLET_NAMESPACE}${oneFieldCategory.id}${SPLITER}${onePrincipalField.id}">
									${onePrincipalField.name }: </label>
							</div>
						</td>
						<td class="seond">
							<div class="inputField">
								<select
									name="${oneFieldCategory.id}${SPLITER}${onePrincipalField.id}"
									id="${PORTLET_NAMESPACE}${onePrincipalField.id}"
									<c:if test="${!onePrincipalField.editable}">
				          readonly="readonly" disabled="true" onfocus="this.defaultIndex=this.selectedIndex;" onchange="this.selectedIndex=this.defaultIndex;"
				        </c:if>
									<c:if test="${onePrincipalField.values[0].value != null}"> value="${onePrincipalField.values[0].value}"</c:if>>
									<c:forEach items="${oneFieldCategory.values}"
										varStatus="substatus" var="subField">
										<option value="${subField.value}"
											<c:if test="${subField.checked}">selected="selected"</c:if>>
											${subField.value }</option>
									</c:forEach>
								</select> <span class="require-mark"><c:if
										test="${onePrincipalField.required}">*</c:if></span>
							</div>
						</td>
						<td class="third"></td>
					</c:when>
					<%--如果为单选框或者多选框--%>
					<c:when
						test="${onePrincipalField.displayType=='radio' || onePrincipalField.displayType=='checkbox'}">
						<td class="first">
							<div class="inputLable">
								<label
									for="${PORTLET_NAMESPACE}${oneFieldCategory.id}${SPLITER}${onePrincipalField.id}">
									${onePrincipalField.name }: </label>
							</div>
						</td>
						<td class="seond" colspan="2">
							<div class="inputField">
								<c:forEach items="${onePrincipalField.values}" var="subField"
									varStatus="substatus">
									<input
										<c:if test="${onePrincipalField.displayType=='radio'}">type="radio"</c:if>
										<c:if test="${onePrincipalField.displayType!='radio'}">type="checkbox"</c:if>
										name="${oneFieldCategory.id}${SPLITER}${onePrincipalField.id}"
										id="${PORTLET_NAMESPACE}subField-${substatus.index}-${oneFieldCategory.id}${SPLITER}${onePrincipalField.id}"
										value="${subField.value}"
										<c:if test="${!onePrincipalField.editable}">
					     readonly="readonly" onclick="javascript:return false;" disabled="true"
					   </c:if>
										<c:if test="${subField.checked}">
					     checked="true"
					   </c:if> />
									<label
										for="${PORTLET_NAMESPACE}subField-${substatus.index}-${oneFieldCategory.id}${SPLITER}${onePrincipalField.id}">
										${subField.value }
									</label>
								</c:forEach>
								<span class="require-mark"><c:if
										test="${onePrincipalField.required}">*</c:if></span>
							</div>
						</td>
						<%--
			<td class="third">
			</td>
			 --%>
					</c:when>
				</c:choose>
			</tr>
		</c:if>
	</c:forEach>
</c:forEach>
