<div style="text-align: left;" id="messageHTMLLayer${messageHTML.id}" class="blogAjustShortHTML">
<H2>
		<b>Mise à jour de la description de ${messageHTML.image.name}</b>
	</H2>
<form action="#" id="shortDescriptionForm${messageHTML.id}">
	<input type="hidden"  id="id${messageHTML.id}" name="name${messageHTML.id}"  value="${messageHTML.id}">
	<p class="double">
		<label for="f1-telephone">Titre:</label>
		<input type="text" id="title${messageHTML.id}" name="title"/>
	</p>
	<p class="double">
		<label for="f1-infos">Description:
		<span class="info">(Saisissez ici la description de l'image)</span></label>
		<textarea id="description${messageHTML.id}" name="description" rows="5" cols="30"></textarea>
	</p>
	<p class="simple">
		<input type="button" value="Mettre à jour" onClick="saveShortDescription(${messageHTML.id});" class="row submit-btn"/>
	</p>
</form>

</div>
