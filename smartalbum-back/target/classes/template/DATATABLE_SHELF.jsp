<div class="shelf-header-table-col2">
	<h1>
			<a href="#serverBaseUrl/shelvesController/shelfDetails/${shelf.getId()}.html?'/>"><span class="shelfHeader">${shelf.getName()}</span></a>
			<a href="#" onclick='javascript:common.printDataTableStandardPopup("$messageHeaderSuppression)", "$messageBodySuppression)", "$confirmfunctionSuppression", "$compteurIndex");'>
				<img src="#serverBaseUrl/img/p_supprimer.gif" width="13" height="16"
				border="0" alt="Supprimer" />
			</a>
			<span style="border-right: 2px">|</span>
			<a href="#" onclick="javascript: showBlockInLayer('modifyShelfLayer${shelf.getId()}');"> 
				<img src="<c:url value='/img/images_modif.jpg'/>" width="13" height="16" border="0" alt="Modifier" title="Modifier l'étagère" />
			</a>
		</h1>
	<div class="additional-info-text">
		<span>Created : ${shelf.created} </span>
		<span>contains : ${shelf.getImages().size())} </span>
		<span>images into : ${shelf.getAlbums().size())}</span>
		<span>albums and ${shelf.getUnvisitedImages().size())} unvisited images</span>
		<span></span>
		<p class="shelfdescription">${shelf.getDescription()}</p>
	</div>
	<div></div>
</div>