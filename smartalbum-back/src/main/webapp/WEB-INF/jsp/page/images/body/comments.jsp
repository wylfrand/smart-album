<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
<!-- <div id="content" class="col col-2"> -->
<div id="content">
	<div class="comments-holder"> 
		<c:set var="commentSize" value="${fn:length(comments)}" />
		<h5 class="count">${commentSize} Commentaire${commentSize>1 ? 's' : ''}</h5>
		<c:forEach items="${comments}" var="comment"
			varStatus="compteurComment">
			<%@ include file="/WEB-INF/jsp/page/images/body/comment-item.jsp"%>
		</c:forEach>
	</div>

	<div class="comment-form">
		<h4>Post a Comment</h4>
		<form action="#" id="commentFormId" class="validate-form">
			<div class="msg-thanks" id="msgThanks">
				<p>
					<strong>Thank You!</strong> Your comment was posted!
				</p>
			</div>
			<div class="msg-alert" id="msgAlert">
				<p>
					<strong>Error!</strong> Some field's are required!
				</p>
			</div>

<!-- 			<div class="row"> -->
<!-- 				<label>Email<span> (required) Will not be published</span></label> <input -->
<!-- 					type="text" id="emailId" name="email" disabled="disabled" -->
<%-- 					class="field required valid-email" value="${album.owner.email}"/> --%>
<!-- 				<div class="cl">&nbsp;</div> -->
<!-- 			</div> -->
			
			<div class="row">
				<label></label>
				<textarea id="messageId" name="messageId"
					class="field message required" cols="100" rows="5"></textarea>
				<div class="cl">&nbsp;</div>
			</div>
			<input type="hidden" id="imageId1" name="imageId" value="${imageId}" />
			<input type="hidden" id="albumId" name="albumId" value="${albumId}" />
			<div class="row">
			<security:authorize access="isAuthenticated()">
				<input type="button" class="submit-btn" value="Send the Message" onclick="javascript: saveComment('/imagesController/ajax/addComment.html');" />
				<div class="cl">&nbsp;</div>
			</security:authorize>
			<security:authorize access="!isAuthenticated()">
				<span class="required"><a href="<c:url value='/usersController/open-creation.html'/>">Enregistrez-vous</a> ou 
				<a onclick="javascript:logOut();">Connectez-vous</a> pour laisser un commentaire</span>
				<div class="cl">&nbsp;</div>
			</security:authorize>
			</div>
		</form>
	</div>

</div>