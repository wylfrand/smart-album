<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>

<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
<!-- Wrapper -->
<!-- End Header -->
<!-- Main -->
<div id="main">
	<c:set var="simpleQuote">'</c:set>
	<c:set var="simpleReplace">\'</c:set>
	<c:set var="messageToPrint">${fn:replace(messageHTML.htmlDescription,simpleQuote,simpleReplace)}</c:set>
	<div class="center">
		<!-- Content -->
		<div id="content" class="col col-2">
			<div class="entry" style="border: none">
				<!-- End Image and its options -->
				<!-- Image html description -->
				<div class="project-information">
					<div id="printedMessage">${messageHTML.htmlDescription}</div>
					<a href="<c:url value='/albumsController/showAlbum/${album.id}.html'/>">Retour à la liste</a> <span>|</span> <a id="${messageHTML.id}" href="#modifierDescriptionImage" class="modifierDescriptionImage">Modifier le message</a>
				</div>
				<!-- End Image html description -->
			</div>
			<c:set var="entityType">ALBUM</c:set>
			<%@ include file="/WEB-INF/jsp/page/tiny/messages.jsp" %>
		</div>
		<!-- End Content -->
		<div class="cl">&nbsp;</div>
	</div>
</div>
<!-- Image comment -->
<div class="center">
	<!-- Content -->
	<c:set var="comments" value="${album.comments}"/>
	<c:set var="albumId" value="${album.id}"/>
	<c:set var="imageId" value="-1"/>
	<div id="commentsContent">
		<%@ include file="/WEB-INF/jsp/page/images/body/comments.jsp" %>
	</div>
	<!-- End Content -->
	<div class="cl">&nbsp;</div>
</div>
<!-- End Image comment -->

<!-- End Wrapper -->