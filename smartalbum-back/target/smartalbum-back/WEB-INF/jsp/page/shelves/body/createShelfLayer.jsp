<div style="text-align: left; display: none" id="createShelfLayer" class="createShelfLayerClass">
	<div id='logo' class="albumhead" style="background: #fff;">
		<h4>Cre�ation d'une �tag�re</h4>
	</div>
	<form:form modelAttribute="shelfForm" id="shelfId" name="shelfId"
		method="post" action="${pageContext.request.contextPath}/shelvesController/createShelf.html">
		<p class="double">
			<label for="f1-infos">Nom de l'�tag�re:</label>
			<form:input autocomplete="off" path="name" cssClass="inputClass" />
		</p>
		<p class="double">
			<label for="f1-infos">Description: <span class="info">(Saisissez ici la description de l'�tag�re)</span></label><br>
			<form:textarea id="descriptionShelf" path="description" cssStyle="width:449px" maxlength="840" cssErrorClass="error" cssClass="inlineBlock" value="test"/>
		</p>
		<p class="simple">
			<form:checkbox path="shared" id="etagere_partagee" value="false" style="width:20px;height:20px;"/>
			&nbsp;&nbsp;Cette �tag�re <b>est</b> publique<br/>
		</p>
		<p class="simple">
			<input type="submit" value="Mettre � jour" class="row submit-btn" />
		</p>
	</form:form>
</div>
