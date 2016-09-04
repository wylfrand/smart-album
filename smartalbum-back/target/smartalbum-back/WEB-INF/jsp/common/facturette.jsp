<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>

<style>
.undelined{
text-decoration: underline;
}
</style>

<div class="draggableIndirectObj">
<c:if test="${not empty currentAlbum}">
<div id="facturette" class="test" data-albumid="${currentAlbum.id}" data-shelfid="${currentAlbum.shelf.id}">
	<c:set var="offer"  value="true" />
	<c:set var="isUploadPage" value="${fn:contains(pageContext.request.requestURL,'controller/initUploadedFiles')}"/>
	<c:set var="isModifyAlbumPage" value="${fn:contains(pageContext.request.requestURL,'albumsController/editAlbum')}"/>
	<c:choose>
		<c:when test="${not empty currentAlbum and not isUploadPage and not isModifyAlbumPage}">
		<h4><img src="<c:url value='/img/Cursor-Select-icon.png'/>" width="35"
						height="55" border="0" alt="Edit Album" title="Modifier l'album"/>Album sélectionné<hr size="1" color="#DDDDDD"></h4>
		<div id="imgFactToCopy">
			<a href="#"  width="1440" onclick="javascript:printFullImageInLayerFact();" class="images"> 
				<img src="<c:url value='/imagesController/paintImage${currentAlbum.coveringImage.fullPath}/_medium.html'/>" />
			</a>
		</div>
				
		<br/>
		<ul style="text-align: left; text-align: left;">
			<li>
				<span class="undelined">Etagère</span> : <span id="selectedShelfName">${currentAlbum.shelf.name}</span><br>
				<span class="undelined">Album</span> : <span id="selectedAlbumName">${currentAlbum.name}</span><br>
				<span class="undelined">Description</span> : <span id="selectedAlbumDescription">${currentAlbum.description}</span><br>
			</li>
		</ul>
		</c:when>
		<c:when test="${isUploadPage}">
			<p class="tuto">Les images importées dans cette page restent enregistrées dans votre entrepot d'images!<br/>
			Vous pourrez les rangez dans vos albums quand bon vous semblera!<br/><br/>
			Pour créer un nouvel album suivez les étapes suivantes:<br/></p>
			<dl class="faq">
				<dt>- "Importez vos photos!"</dt>
				<dd>Importez simultanément une ou plusieurs photos (<b>Parcourir ...</b>)</dd>
				<dd>Vous pouvez aussi glissez-déposer vos photos depuis votre périphérique!</dd>
				<dt>- Valider en cliquant sur le bouton OK </dt>
				<dd>Ce bouton se trouve au bas de la page. Attendez que la barre de téléchargement ait atteint son maximum!</dd>
				<dt>-  Cochez les images de votre album</dt>
				<dd>Toutes les images qui sont destinées au même album doivent d'abort être sélectionnées. Vous pouvez effectuer cette action sur plusieurs pages sans perdre la sélection.</dd>
				<dt>- Choisissez "créer un album"</dt>
				<dd> Cette option est disponible dans la liste déroulante au bas de la page.</dd>
				<dt>- Laissez vous guider pour la création de l'album!</dt>
			</dl>
		</c:when>
		<c:when test="${isModifyAlbumPage}">
			<p class="tuto">
				Modifiez les propriétés de votre album en toute simplicité! <br/>
				Pour rajouter des photos ou modifier des emplacements de photos:<br/></p>
				<dl class="faq">
					<dt>- "Importez encore des photos dans l'album!"</dt>
					<dd>Importez simultanément une ou plusieurs photos (<b>Parcourir ...</b>) dans votre album</dd>
					<dt>- Validez  (bouton OK) </dt>
					<dd>Les photos importées sont directement enregistrées dans votre album en toute sécurité!</dd>
					<dt>- Choisissez "Modifiez l'album"</dt>
					<dd> Cette option est disponible dans la liste déroulante au bas de la page.</dd>
					<dt>- Laissez vous guider pour la modification de l'album!</dt>
					<dt>- Déplacer l'album </dt>
					<dd>Si vous modifiez le thème d'un album, tout l'album sera déplacé vers le nouveau thème</dt>
					<dt>- Déplacez des images!</dt>
					<dd>Vous pouvez déplacer uniquement des images en cliquand sur l'onglet "Déplacez des images". Laissez-vous guider!</dd>
				</dl>
		</c:when>
		<c:otherwise>
			<strong>Importez des images et créez des albums pour enrichir vos articles</strong>
		</c:otherwise>
	</c:choose>
<%-- 		<%@ include file="/WEB-INF/jsp/common/chat.jsp"%> --%>
</div>
</c:if>
</div>
