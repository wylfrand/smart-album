<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>

<input type="button" class="btn classname" value="Voir/Masquer" id="myButtonAnimate${shelf.id}" onclick="javascript: $('#userAlbums-'+${shelf.id}).toggle();"/>

<div id="userAlbums-${shelf.id}" class="draggable" data-droppableid="${shelf.id}">
	<c:forEach items="${albums}" var="album" varStatus="compteurAlbum">
		<c:set var="coveringUrl"><c:url value='/imagesController/paintImage${album.coveringImage.fullPath}/_medium.html'/></c:set>
		<div class="preview_box_album_120" data-albumid="${album.id}" data-shelfid="${shelf.id}" id="${shelf.id}-${album.id}">
			<div class="backgroundImage200">
				<span class="imageTitle">
				<a href="#" onclick="javascript: showPopupWarning('popupLayer${album.id}');"> 
					<img src="<c:url value='/img/p_supprimer.gif'/>" width="12"
						 border="0" alt="Delete Album" title="Supprimer l'album"/></a>|
						 <a href="#" onclick="javascript: editAlbum(${album.id});"> 
					<img src="<c:url value='/img/images_modif.png'/>" width="15"
						height="35" border="0" alt="Edit Album" title="Modifier l'album"/></a>|
						<a href="#" onclick='javascript: selectAlbum("${coveringUrl}","${shelf.name}","${album.name}","${fn:escapeXml(album.description)}","${fn:length(album.images)}","${album.id}");'> 
					<img src="<c:url value='/img/Cursor-Select-icon.png'/>" width="15"
						height="35" border="0" alt="Edit Album" title="Sélectionnez cet album"/></a>
				</span>
				<a href="<c:url value='/albumsController/showAlbum/${album.id}.html'/>">
					<img id='album_${compteurAlbum.index}' src="<c:url value='/imagesController/paintImage${album.coveringImage.fullPath}/_medium.html'/>" />
				</a>
			</div>
			<div style="display: block" class="album_name">${album.name} <br/>${album.created}</div>
		</div>
	<c:set var="elementId" value="${album.id}" />
	<c:set var="confirmfunction" value="deleteAlbum(${album.id},'${shelf.id}-${album.id}');" />
	<c:set var="messageHeader" value="Suppression de l'album ${album.name} - ${album.id}" />
	<c:set var="messageBody" value="Attention, Vous allez suprimer ${fn:length(album.images)} photo(s).<br/>Cette suppression sera <b>irréversible!</b>" />
	<%@ include file="/WEB-INF/jsp/common/popup.jsp"%>		
		
	</c:forEach>
</div>
