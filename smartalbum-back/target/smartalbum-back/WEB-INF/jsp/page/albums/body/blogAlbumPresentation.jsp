<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
<div class="blog-item">
	<div class="entry">
		<div class="post-head">
			<div class="post-date">${file.image.uploaded}</div>
			<c:set var="commentSize" value="${fn:length(file.image.comments)}" />
			<a href="#" class="comments">${commentSize}
				Commentaire${commentSize>1 ? 's' : ''}</a>
			<div class="cl">&nbsp;</div>
		</div>
		<c:set var="relativeUrl" value="/imagesController/paintImage${file.image.fullPath}/fullImage.html"/>
		<a href="#" id="parent${file.image.id}" width="1440"
			onclick="javascript:printFullImage('${file.image.id}','${relativeUrl}', '${compteur.index}')" class="images"> <img
			src="<c:url value='/imagesController/paintImage${file.image.fullPath}/_small200.html'/>"
			alt="" class="blogImg" />
		</a>
		<c:forEach items="${file.image.messagesHTML}" var="messageHTML"
			varStatus="compteurMESSAGE">

			<c:if test="${messageHTML.message_type eq 'SHORTDESCRIPTION'}">
				<c:set var="messageToPrint" value="${messageHTML}" />
			</c:if>
		</c:forEach>

		<div id="messageToPrintPositionId${messageToPrint.id}">
			${messageToPrint.htmlDescription}</div>
		<div class="cl">&nbsp;</div>
		<div class="default-links" style="display: none">
			<a id="${messageToPrint.id}"
				href="messageToPrintPositionId${messageToPrint.id}"
				class="modifierDescription">Modifier le message</a> <span>|</span><strong>Posted
				in:</strong> <a href="#">${currentAlbum.name}</a><span>|</span> <strong>Tags:</strong>
			<c:forEach items="${file.image.imageTags}" var="metatag"
 				varStatus="compteurTag">
				<a href="#">${metatag.tag}</a>
 			</c:forEach>
			<a
				href="<c:url value='/imagesController/showImageDetails/${currentAlbum.id}/${file.id}.html?pageNumber=${pagination.activePageNumber}'/>"
				class="read-more">Continue Reading</a>
		</div>
	</div>
</div>
<c:set var="messageHTML" value="${messageToPrint}" />
<%@ include file="/WEB-INF/jsp/page/tiny/editShortDescription.jsp"%>

<c:set var="layerImage" value="${file.image}" />
<%@ include file="/WEB-INF/jsp/page/images/imageLayer.jsp"%>
