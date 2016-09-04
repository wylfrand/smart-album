<%@ include file="/WEB-INF/jsp/common/include.jsp"%>

<c:choose>
	<c:when test="${isAddams}">
		<c:choose>
			<c:when test="${hasError or isDelete}">
				<%@include file="/WEB-INF/jsp/groupe/restitution/new_empty_line.jsp"%>
			</c:when>
			<c:otherwise>
				<%@include file="/WEB-INF/jsp/groupe/restitution/new_line.jsp"%>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<%@include
			file="/WEB-INF/jsp/groupe/restitution/modification/body/new_line_mpvx.jsp"%>
	</c:otherwise>
</c:choose>
