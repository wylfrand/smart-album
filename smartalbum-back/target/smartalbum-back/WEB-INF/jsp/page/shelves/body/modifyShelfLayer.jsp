<div style="text-align: left; display: none" id="modifyShelfLayer${shelf.id}" class="modifyShelfLayerClass">
	<div id='logo' class="albumhead" style="background: #fff;">
		<h4>Modification d'une �tag�re</h4>
	</div>
	<form:form modelAttribute="shelfForm" id="shelfId" name="shelfId"
		method="post" action="${pageContext.request.contextPath}/shelvesController/modifyShelf.html">
		<form:hidden path="id" value="${shelf.id}"/>
		
		<p class="double">
			<label for="f1-infos">Nom de l'�tag�re:</label><br/>
			<form:input autocomplete="off" path="name" cssClass="inputClass" value="${shelf.name}"/>
		</p>
		<p class="double">
			<label for="f1-infos">Description: <span class="info">(Saisissez ici la description de l'�tag�re)</span></label><br/>
			<form:textarea id="descriptionShelf" path="description"  maxlength="840"/>
		</p>
		<p class="simple">
			<form:checkbox path="shared" id="etagere_partagee" value="${shelf.shared}" style="width:20px;height:20px;"/>
			&nbsp;&nbsp;Cette �tag�re <b>est</b> publique<br/>
		</p>
		<p class="simple">
			<input type="submit" value="Mettre � jour" class="row submit-btn" />
		</p>
	</form:form>
</div>
