<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>

<c:set var="shelves" value="${currentShelfList}"></c:set>
<c:set var="messageHTML" value="${welcomeMessage}"></c:set>
<c:set var="pluriel" value="${fn:length(shelves)>1 ? 's' : ''}"></c:set>
<c:set var="myHeader" value="étagère${pluriel} choisie${pluriel}"></c:set>

<div>
	<div class="clearfix">
		<div class="shelves">
			<%@ include file="/WEB-INF/jsp/page/shelves/body/shelvesList.jsp"%>
		</div>
	</div>
</div>

