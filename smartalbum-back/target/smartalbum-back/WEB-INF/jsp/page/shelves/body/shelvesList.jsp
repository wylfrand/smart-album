<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>

<div class="shelf-header-table">
	<h1 class="h1-style">${myHeader}</h1>
	<a href="<c:url value='/shelvesController/synchronyseAll.html'/>" onclick="javascript: showActivity();">
		Synchroniser
	</a>
	<span>|</span>
	<a href="#" onclick="javascript: showBlockInLayer('createShelfLayer');" onclick="javascript: showActivity();">
		Nouvelle étagère
	</a>
	
</div>
<c:forEach items="${shelves}" var="shelf" varStatus="compteurShelf">
	<div id="shelfInfo-${compteurShelf.count}" class="shelfBlock">
		<%@ include file="/WEB-INF/jsp/page/shelves/body/shelfInfo.jsp" %>
	</div>
	<%@ include file="/WEB-INF/jsp/page/shelves/body/modifyShelfLayer.jsp"%>
</c:forEach>
<%@ include file="/WEB-INF/jsp/page/shelves/body/createShelfLayer.jsp"%>
<%@ include file="/WEB-INF/jsp/common/popupShelf.jsp"%>