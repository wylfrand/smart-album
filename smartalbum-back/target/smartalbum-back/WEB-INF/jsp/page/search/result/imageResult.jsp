<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>

<div id="resultImage">
	<div class="imageInfo">
	<c:choose>
		<c:when test="${DEVICE_IS_MOBILE }">
			<div>
				<c:set var="imageURLAlbum"><c:url value="/imagesController/paintImage${imageResult.getFullPath()}/_medium.html"/></c:set>
			</div>
		</c:when>
		<c:otherwise>
			<c:set var="imageURLAlbum"><c:url value="/imagesController/paintImage${imageResult.getFullPath()}/fullImage.html"/></c:set>
		</c:otherwise>
		</c:choose>
	
	<img id='album_${compteurAlbum.index}' onclick="javascript:printFullImageInLayerFactWithSrc('${imageURLAlbum}')" src="<c:url value='/imagesController/paintImage${imageResult.fullPath}/_small120.html'/>"/>
	<p><strong>id : </strong>${imageResult.id}</p>
	<p><strong>Nom : </strong>${imageResult.name} </p>
	<p><strong>Est en couverture d'album : </strong>${imageResult.covering}</p>
	<p><strong>Emplacement de l'image : </strong>${imageResult.fullPath}</p>
	<p><strong>Model d'appareil photo : </strong>${imageResult.cameraModel}</p>
	<p><strong>Date de création : </strong><fmt:formatDate value="${imageResult.created}" pattern="${pattern}" /> --${imageResult.created}++</p>
	<p><strong>Date de téléchargement : </strong><fmt:formatDate value="${imageResult.uploaded}" pattern="${pattern}" /></p>
	<p><strong>Dimension (largeur X hauteur) : </strong>${imageResult.width} X ${imageResult.height}</p>
	<p><strong>Description : </strong>${imageResult.description}</p>
	<p><strong>Taille : </strong>${imageResult.size}</p>
	<p><strong>Peut-on rajouter un commentaire : </strong>${imageResult.allowComments}</p>
	<p><strong>Peut-on voir les méta-données : </strong>${imageResult.showMetaInfo}</p>
	<p><strong>Nombre de commentaires : </strong>${fn:length(imageResult.comments)}</p>
	<p><strong>Nombre de tags : </strong>${fn:length(imageResult.imageTags)}</p>
	<p><strong>Meta : </strong>${imageResult.meta}</p>
	<p><strong>Déjà visité : </strong>${imageResult.visited}</p>
	<p><strong>Album parent : </strong>${imageResult.album.name} - ${imageResult.album.created}</p>
	<p><strong>Nombre de message HTML : </strong>${fn:length(imageResult.messagesHTML)} </p>
	</div>
	</div>
</div>