<div style="text-align: left; display: none" id="deleteShelfLayer" class="deleteShelfLayerClass">
	<div id='logo' class="albumhead" style="background: #fff;">
		<h4>Suppresion d'une étagère</h4>
	</div>
	<form:form modelAttribute="shelfForm" id="shelfId" name="shelfId"
		method="post" action="${pageContext.request.contextPath}/shelvesController/createShelf.html">
		<p class="double">
			Pour Supprimer une étagère il faut que touys les albums de l'étagère soient supprimés dans un premier temps.
		</p>
		
		<p class="simple">
			<input type="submit" value="Mettre à jour" class="row submit-btn" />
		</p>
	</form:form>
</div>
