<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<div class="comment-item">
	<div class="author-info">
		<c:choose>
<%-- 			<c:when test="${comment.author.hasAvatar}"> --%>
<c:when test="false">
				<img
					src="<c:url value='/imagesController/paintAvatarImage/${comment.author.id}.html'/>"
					alt="" class="blogImg-comment" width="75" />
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${comment.author.sex.key eq 1}">
						<img src="<c:url value='/img/shell/avatar_default.png'/>" alt=""
							class="blogImg-comment" width="75" />
					</c:when>
					<c:otherwise>
						<img src="<c:url value='/img/shell/avatar_w_default.png'/>" alt=""
							class="blogImg-comment" width="75" height="80" />
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
		<p>
			<a href="#">${comment.author.firstName}
				${comment.author.secondName}</a>
		</p>
		<p>${comment.date}</p>
	</div>
	<div class="comment-text">
		<p>${comment.message}</p>
		<!-- 							<a href="#">Replay</a> -->
	</div>
	<div class="cl">&nbsp;</div>
</div>