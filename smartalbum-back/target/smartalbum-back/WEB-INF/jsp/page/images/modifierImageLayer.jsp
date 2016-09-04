<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<c:choose>
	<c:when test="${layerImage.id gt -1}">
		<c:set var="fileMetaId" value="${layerImage.id}" />
		<c:set var="isTmpImage" value="false" />
	</c:when>
	<c:otherwise>
		<c:set var="fileMetaId" value="${compteur.index}" />
		<c:set var="isTmpImage" value="true" />
	</c:otherwise>
</c:choose>
<div style="background: lavender none repeat scroll 0 0;color: midnightblue;border-radius: 8px;text-align: center;display:none;" id="imageInfosLayer${fileMetaId}">

<form action="#" id="imageDetail2${fileMetaId}">
	<img src="<c:url value='/${controller}/get/${compteur.index}.html'/>" alt="" height="100%"/>
	<p class="double" >
	<span id="layer-image2${fileMetaId}"></span>
	<span>Nom de l'image :</span><br>
		<input type="text" id="imageSelectionnee${fileMetaId}" value="${fileMeta.fileName}"/>
	</p>
	<%@ include file="/WEB-INF/jsp/page/images/options.jsp" %><br>
		<input onclick="javascript: saveNewImageName('${fileMetaId}','${isTmpImage}','${fileMeta.fileName}');return false;" type="submit" id="valider_infos_image${fileMetaId}"  value="Enregistrer"></input>
		<input type="submit" id="fermerr_infos_image${fileMetaId}" onclick="javascript: closeSimpleModal();return false;" value="Annuler"></input>
						
</form>
</div>