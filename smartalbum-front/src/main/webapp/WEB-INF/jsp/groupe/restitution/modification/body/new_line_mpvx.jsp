<%@ include file="/WEB-INF/jsp/common/include.jsp" %>

<div class="bloc-confirmation mobileRow" id="add_new_line_row_1">
	<c:set var="createLineURL"><c:url value='/action/groupe/creation/ajout/'/></c:set>
	<c:set var="className" value="addLineMPVX" />
	<c:set var="placeHolder">Saisir une ligne mobile</c:set>
	<c:if test="${hasError}">
		<c:set var="className" value="addLineMPVX hasError" />
	</c:if>
	<c:if test="${not empty member.person.line.number}">
		<c:set var="className" value="addLineMPVX addSuccess" />
	</c:if>
	<div class="bloc-non-valide">
		<strong>Multi-Packs évolue ! Maintenant bénéficiez de remises sur votre box grâce à vos lignes mobiles.</strong>
		<a href="${enSavoirPlus}"><span class="savoir-plus"> En savoir plus</span></a>
	</div>
	<div class="controls">
		<div class="control-label">Ajouter une ligne mobile et voir ce qui change</div>
		<input id="add_new_line_input_1"
			type="text" class="${className}" data-number="${msisdn}"
			onkeyup="javascript:countCaracters(1, '${createLineURL}', false);"
			onblur="javascript:addNewLine(1, '${createLineURL}', false);"
			onclick="javascript:clearErrorMessageMPVX();" maxlength="10"
			name="add_new_line_input_1" title="Saisir une ligne mobile"
			placeholder="Saisir une ligne mobile" value='<maam:telephone label="${msisdn}" separator="." />'>
		<span class="loadingImage_1"></span>
		<div id="erreurFormeMpvx" class="msg-erreur-mpvx" style="display: none;">
				Veuillez saisir un num&eacute;ro SFR &agrave; 10 chiffres.
		</div>
		<c:if test="${hasError}">
			<div class="msg-erreur">
				<c:choose>
					<c:when test='${errorCode eq "errorLineNotExist"}'>
						La ligne saisie n'a pas &eacute;t&eacute; reconnue ou n'est pas une ligne SFR. Merci d'en saisir une nouvelle.
					</c:when>
					<c:when test='${errorCode eq "errorExistingLine"}'>
						Cette ligne est d&eacute;j&agrave; renseign&eacute;e, merci d'en saisir une nouvelle.
					</c:when>
					<c:when test='${errorCode eq "errorCodeScs"}'>
						Les offres SFR La carte ne sont pas compatibles avec Multi-Packs. Merci de saisir une autre ligne.
					</c:when>
					<c:when test='${errorCode eq "errorCodeLibellePTA"}'>
						Les s&eacute;ries RED ne sont pas compatibles  avec Multi-Packs. Merci de saisir une autre ligne.
					</c:when>
					<c:when test='${errorCode eq "errorCodeHasAmeSubscrib"}'>
						Cette ligne fait d&eacute;j&agrave; partie d'un Multi-Packs. Merci d'en saisir une autre.
					</c:when>
					<c:when test='${errorCode eq "errorStartWith06or07"}'>
						Vous devez saisir une ligne fixe.
					</c:when>
					<c:when test='${errorCode eq "errorNotStartWithO6Or07"}'>
						Vous devez saisir une ligne mobile.
					</c:when>
				</c:choose>
			</div>
		</c:if>
		<c:if test="${not empty member.person.line.number}">
			<div class="msg-success">
				<img alt="" src='<c:url value="/img/check-vert.png" />' />
			</div>
		</c:if>
	</div>
	<div class="confirmer">
		<input type="hidden" name="activeSimu_1" value="${activeSimu}" id="activeSimu_1" />
		<input type="hidden" name="modifyLineURL" value="<c:url value='/action/groupe/creation/modify/'/>" id="modifyLineURL" />
			<a id="imgRemiseActive" class="notVisible" onclick="javascript:simulerModificationDeGroupe();" href="javascript:void(0);">
				<img alt="" src='<c:url value="/img/btn_confirmer.png" />' border="0" style="padding-right: 1px;"/>
			</a>
			
			<div id="forbiddenActionDivMPVX" class="notVisible">
				<div class="forbiddenActionContent">
					<div class="forbiddenText">Vous devez ajouter une  ligne  mobile pour voir si vous pouvez bénéficier de nouvelles remises</div>
				</div>
				<img alt="interdit" src='<c:url value="/img/interdit.png" />'>
			</div>
			<a id="imgRemise" class="noCreatedLine" href="javascript:void(0);">
				<img alt="" src='<c:url value="/img/btn_confirmer.png" />' border="0" style="padding-right: 1px;"/>
			</a>
	</div>
</div>

<script type="text/javascript">
$(document).ready(function() {
	$("a#imgRemise.noCreatedLine").hover(function() {
		$("#forbiddenActionDivMPVX").show();
	});
	
	$("a#imgRemise.noCreatedLine").mouseout(function() {
		$("#forbiddenActionDivMPVX").hide();
	});

	$(".addLineMPVX").keypress(function (e) {
	     //allow only numbers, backspace, delete, home, end, left, right, Ctrl + A, Ctrl + C, Ctrl + V
		if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57) && !(e.which == 97 && e.ctrlKey === true)
			&& !(e.which == 99 && e.ctrlKey === true) && !(e.which == 118 && e.ctrlKey === true)) {
			return false;
		}
	});
});
</script>