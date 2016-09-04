<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
<!-- Wrapper -->
<!-- End Header -->
<!-- Main -->

<div id="main">
	<c:forEach items="${image.messagesHTML}" var="messageHTML" varStatus="compteurMESSAGE">
		<c:if test="${messageHTML.message_type eq 'LONGDESCRIPTION'}">
			<c:set var="blogMessageHTML" value="${messageHTML}"/>
		</c:if>
	</c:forEach> 
	<c:set var="simpleQuote">'</c:set>
	<c:set var="simpleReplace">\'</c:set>
	<c:set var="messageToPrint">${fn:replace(blogMessageHTML.htmlDescription,simpleQuote,simpleReplace)}</c:set>
	<div class="center">
		<!-- Content -->
<!-- 		<div id="content" class="col col-2"> -->
		<div id="content">
		
			<div class="entry" style="border: none">
				<!-- Image and its options -->
				<div>
				<table>
					<tr>
					<td><img src="<c:url value='/imagesController/paintImage${image.fullPath}/fullImage.html'/>" alt="" style="width: 100%"/></td>
<%-- 						<td><%@ include file="/WEB-INF/jsp/page/images/options.jsp" %></td> --%>
					</tr>
				</table>
				</div>
				<!-- End Image and its options -->
				<!-- Image html description -->
				<div class="project-information">
					<div id="printedMessage">${blogMessageHTML.htmlDescription}</div>
					<a href="<c:url value='/albumsController/showAlbum/${image.album.id}.html'/>">Retour à la liste</a> <span>|</span> <a id="${blogMessageHTML.id}" href="#modifierDescriptionImage" class="modifierDescriptionImage">Modifier le message</a>
				</div>
				<!-- End Image html description -->
			</div>
			<c:set var="messageHTML" value="${blogMessageHTML}"/>
			<c:set var="entityType">IMAGE</c:set>
			<%@ include file="/WEB-INF/jsp/page/tiny/messages.jsp" %>
		</div>
		<!-- End Content -->
		<div class="cl">&nbsp;</div>
	</div>
</div>
<!-- Image comment -->
<div class="center">
	<!-- Content -->
	<c:set var="comments" value="${image.comments}"/>
	<c:set var="imageId" value="${image.id}"/>
	<c:set var="albumId" value="-1"/>
	<div id="commentsContent">
		<%@ include file="/WEB-INF/jsp/page/images/body/comments.jsp" %>
	</div>
	<!-- End Content -->
	<div class="cl">&nbsp;</div>
</div>
<!-- End Image comment -->

</div>

<!-- End Wrapper -->