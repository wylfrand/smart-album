<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<div id="resultShelf">
	<div class="shelfInfo">
<!-- Info du groupe -->
		<p><strong>Id : </strong>${shelfResult.id} </p>
		<p><strong>Nom : </strong>${shelfResult.name} </p>
		<p><strong>Description : </strong>${shelfResult.description} </p>
		<p><strong>Propriétaire: </strong>${shelfResult.owner.firstName}  </p>
		<p><strong>Etagère partagée? : </strong>${shelfResult.shared} </p>
		<p><strong>Description longue : </strong>${shelfResult.longValue} </p>
		<p><strong>Nombre d'albums : </strong>${fn:length(shelfResult.albums)} </p>
		<p><strong>Date création : </strong><fmt:formatDate value="${shelfResult.created}" pattern="${pattern}" /> </p>
<!-- Info des membres -->
		<c:if test="${! empty shelfResult.albums}">
			<c:forEach items="${shelfResult.albums}" var="albumResult">
				<p><span id="plusAlbum${albumResult.shelf.id}-${albumResult.id}">+</span>
				<a href="javascript:display('albumSearch${albumResult.shelf.id}-',${albumResult.id},'plusAlbum${albumResult.shelf.id}-','<c:url value='/searchController/ajax/search/album.html' />');">${albumResult.name} - ${albumResult.id} (album)</a></p>
				<div id="albumSearch${albumResult.shelf.id}-${albumResult.id}" style="display: none;">
				</div>
			</c:forEach>
			
		</c:if>
	</div>
</div>