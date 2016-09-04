<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<c:choose>
	<c:when test="${layerImage.id gt -1}">
		<c:set var="fileMetaId" value="${layerImage.id}" />
	</c:when>
	<c:otherwise>
		<c:set var="fileMetaId" value="${compteur.index}" />
	</c:otherwise>
</c:choose>

<div style="text-align: left; display: none;" id="layer${fileMetaId}">
	<input type="submit" name="valider_infos_album"
		id="fermerr_infos_album"
		onclick="javascript: closeSimpleModal();return false;" value="Fermer"></input>
	<form action="#" id="imageDetail${fileMetaId}">
		<p class="double">
		<span id="layer-image${fileMetaId}"></span>
		</p>

	</form>

</div>