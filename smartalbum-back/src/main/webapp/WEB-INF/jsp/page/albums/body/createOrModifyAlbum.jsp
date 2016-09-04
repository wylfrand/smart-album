<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>

<div id="albumContent" class="albumContent">
<script type="text/javascript">
function changeCoveringImage(controllerName,albumRelativePath)
{
	   var actionValue = $('select#selectedCoverAlbum option:selected').val();
	   var coveringImgUrl = "<c:url value='/imagesController/paintImage/"+albumRelativePath+"/"+actionValue+"/_small200.html'/>";
	   $('#coveringImage').replaceWith('<img id="coveringImage" src="'+coveringImgUrl+'"/>');
}


$(function() {
	
	$( "#name" ).change(function() {
		$('#name').css('border','0');
	});
	
	$("#selectedPubShel").change(function() {
		$('#selectedOwnShelf').css('border','0');
		$('#selectedPubShelf').css('border','0');
	});
	
	$("#selectedOwnShelf").change(function() {
		$('#selectedOwnShelf').css('border','0');
		$('#selectedPubShelf').css('border','0');
	});
	
$( "#valider_infos_album" ).click(function( event ) {
	event.stopPropagation();
	
	if(!$("#name").val() || (!$("#selectedOwnShelf").val() && !$("#selectedPubShelf").val()))
	{
		if(!$("#name").val())
		{
			//$("#name").
			alert("Vous devez renseigner un nom pour l'album!");
			$('#name').css('border','1px solid red');
		}
		else if(!$("#selectedOwnShelf").val() && !$("#selectedPubShelf").val())
		{
				alert("Vous devez préciser un thème de rangement");
				$('#selectedOwnShelf').css('border','1px solid red');
				$('#selectedPubShelf').css('border','1px solid red');
		}
		
		event.stopPropagation();
		return false;
	}
	
	$("#albumId").submit();
	
	});
	
});
</script>
	<div id='container'>
		<div id='logo' class="albumhead">
			<h1>
				 ${controller eq 'controller' ? 'Creéation' : 'Modification'} d'un album <span id="imageSize"/></span>
			</h1>
		</div>
		<div id='content'>
			<!-- modal content -->
			<form:form modelAttribute="albumForm" id="albumId" name="albumId" method="post" action="${pageContext.request.contextPath}/albumsController/createOrModifyAlbum.html">
				<form:hidden path="creation" value="${controller eq 'controller' ? 'true' : 'false' }"/>
						<p class="shelfTitle">Veuillez préciser les informations de rangement</p>
						<div class="albumCoveringImgBlock">
							<div id="backgroundAlbumImage">
								<img id='coveringImage' src=""/>
							</div>
							${albumForm.id}
							<div class="albumInfosBlock">
								<table>
									<tr>
										<td><span class="albumTitle">Couverture</span></td>
										<td>
											<div id="selectContainer">
												<form:select path="selectedCoverAlbum" onchange="javascript:changeCoveringImage('${controller}','${albumForm.path}');" class='selectClass' id='selectedCoverAlbum'>
													<form:option value='' selected='selected'>-CHOISIR UNE COUVERTURE-</form:option>
													${albumForm.selectedPicturesOptions}
											</form:select>
											</div>
										</td>
									</tr>
									<tr>
										<td><div><span class="albumTitle">Nom de l'album</span></td>
										<td><form:input path="name" cssClass="inputClass"/></div></td>
									</tr>
									<tr>
										<td><span class="albumTitle">Mes thèmes : </span></td>
										<td><form:select path="selectedOwnShelf" cssClass="selectClass" disabled="${empty albumForm.userShelvesInfos}">
											<form:option value="" selected="selected">-CHOISIR UN THEME-</form:option>
											<c:forEach var="aShelf" items="${albumForm.userShelvesInfos}">
					                			<form:option value="${aShelf.id}">${aShelf.name}</form:option>
					        				</c:forEach>
										</form:select></td>
									</tr>
<!-- 									<tr> -->
<!-- 										<td><span class="albumTitle">Thèmes publics : </span></td> -->
<%-- 										<td><form:select path="selectedPubShelf" cssClass="selectClass" disabled="${empty albumForm.publicShelves}"> --%>
<%-- 											<form:option value="" selected="selected">-CHOISIR UN THEME-</form:option> --%>
<%-- 											<c:forEach var="aShelf" items="${albumForm.publicShelves}"> --%>
<%-- 				                				<form:option value="${aShelf.id}">${aShelf.name}</form:option> --%>
<%-- 				        					</c:forEach> --%>
<%-- 										</form:select></td> --%>
<!-- 									</tr> -->
<!-- 									<tr> -->
<!-- 									<td colspan="2"> -->
<!-- 										<div class="actionApresCreation"> -->
<%-- 											<form:radiobutton path="showAfterCreate" value="true" /> --%>
<!-- 											&nbsp;&nbsp;Afficher tous les thèmes après la création de -->
<!-- 											l'album -->
<%-- 											<form:radiobutton path="showAfterCreate" value="false" /> --%>
<!-- 											&nbsp;&nbsp;Fermer cette fenêtre après la création de l'album -->
<!-- 										</div> -->
<!-- 									</td> -->
<!-- 								</tr> -->
								</table>
								<div class="additionnalBlock"><span class="albumTitle">Description de l'album : </span><br><br>
									<div>
										<form:textarea id="descriptionAlbum" path="description" cssStyle="width:449px" maxlength="840" cssErrorClass="error" cssClass="inlineBlock" />
									</div>
								</div>
							</div>
						
						<div>
							<input type="submit" name="valider_infos_album" id="valider_infos_album" value="Enregistrer"></input>
						<input type="submit" name="valider_infos_album" id="fermerr_infos_album" onclick="javascript: closeSimpleModal();return false;" value="Annuler"></input>
						</div>
					
				</div>
		</form:form>
		</div>
	</div>
</div>
