<div class="setContainer">
<div>
<a href='#' id='select-allFromFact'>Sélectionner tout</a>
&nbsp;|&nbsp;<a href='#' id='deselect-allFromFact'>Déselectionner tout</a>
&nbsp;|&nbsp;<a onclick="javascript:startSlider();return false;">Full screen slider de la sélection !</a>
</div>
<select id="image-pickerFromFact" class="image-picker show-labels show-html searchWidth" data-limit="${fn:length(currentAlbum.images)}"
			multiple="multiple" show-html>
	<c:forEach items="${currentAlbum.images}" var="image" varStatus="compteur">
		<option data-src="<c:url value='/imagesController/paintImage${image.fullPath}/_small80.html'/>" data-label="${image.name}">
			${image.name}</option>
	</c:forEach>
</select>
</div>
<div></div>