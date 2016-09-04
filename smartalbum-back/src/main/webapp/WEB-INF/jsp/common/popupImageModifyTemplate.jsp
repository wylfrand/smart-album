<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<div style="background: lavender none repeat scroll 0 0;color: midnightblue;border-radius: 8px;text-align: center;display:none;" id="modifyImageTemplateId">

<form action="#">
	<img src="<c:url value='/imagesController/paintImage/_small80.html'/>" alt="" height="100%"/>
	
	<p class="double" >
	<span id="layer-image2#compteurIndex"></span>
	<span>Nouveau nom :</span><br>
		<input type="text" id="imageSelectionnee#imageId" value="#fileName"/>
	</p>
<%-- 	<%@ include file="/WEB-INF/jsp/page/images/options.jsp" %><br> --%>
		<input onclick="javascript: saveNewImageName('#imageId','#fileName', '#isTmpImage');return false;" type="submit" id="valider_infos_image#compteurIndex"  value="Enregistrer"></input>
		<input type="submit" onclick="javascript: closeSimpleModal();return false;" value="Annuler"></input>
						
</form>
</div>