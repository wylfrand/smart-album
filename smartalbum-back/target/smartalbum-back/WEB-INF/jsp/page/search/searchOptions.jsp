<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<form:form id="searchForm">
<c:forEach items="${searchImageHelper.options}" var="option" varStatus="compteurSearchImg">
	<form:label path="optionLabel">${option.name}</form:label>
	<form:checkbox path="selectedOption" value="${option.selected}" onchange="javascript:processSlection()"/>
	<br />
</c:forEach>
</form:form>

