<div style="text-align: left; display: none" id="deleteShelfLayer" class="deleteShelfLayerClass">
	<div id='logo' class="albumhead" style="background: #fff;">
		<h4>Suppresion d'une �tag�re</h4>
	</div>
	<form:form modelAttribute="shelfForm" id="shelfId" name="shelfId"
		method="post" action="${pageContext.request.contextPath}/shelvesController/createShelf.html">
		<p class="double">
			Pour Supprimer une �tag�re il faut que touys les albums de l'�tag�re soient supprim�s dans un premier temps.
		</p>
		
		<p class="simple">
			<input type="submit" value="Mettre � jour" class="row submit-btn" />
		</p>
	</form:form>
</div>
