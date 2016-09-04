<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<c:set var="createLineURL"><c:url value='/action/groupe/creation/ajout/'/></c:set>
<c:set var="className" value="addLine texte-td" />
<c:if test="${hasError}">
	<c:set var="className" value="addLine texte-td hasError" />
</c:if>

<tr id="add_new_line_row_${counter}" class='${ (sfr_user.natureLine.mobile and counter == 1 and isCreation) ? "boxRow" : "mobileRow" }'>
	<td>
		<table class="lineToAdd">
			<tr>
				<td>
					<c:choose>
						<c:when test="${sfr_user.natureLine.mobile and counter == 1 and isCreation}">
						
							<img alt="" src='<c:url value="/img/box-off.png" />' class='${ hasError ? "hasError box-off-creation" : "box-off-creation" }' />
							
						</c:when>
						<c:otherwise>
							<img alt="" src='<c:url value="/img/mobile-off.png" />' class='${ hasError ? "hasError mobile-off" : "mobile-off" }' " />
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					<input type="hidden" name="activeSimu_${counter}" value="${activeSimu}" id="activeSimu_${counter}" />
					<input id="add_new_line_input_${counter}"
						type="text" class="${className}" data-number="${msisdn}"
						onkeyup="javascript:countCaracters(${counter}, '${createLineURL}', true);"
						onblur="javascript:addNewLine(${counter}, '${createLineURL}', true);"
						onclick="javascript:clearErrorMessage(${counter});"  maxlength="10"
						name="add_new_line_input_${counter}" title="Ajouter une ligne"
						placeholder="Ajouter une ligne" value='<maam:telephone label="${msisdn}" separator="." />'>
				</td>
				<td class="tdError" id="erreurFormeAddams_${counter}" style="display: none;">
				<span class="loadingImage_${counter}"></span>
				 	<span class="texte-erreur-addams">
				 	Veuillez saisir un num&eacute;ro SFR &agrave; 10 chiffres.
				 	</span>
				</td>
				<td class="tdError" id="tdError_${counter}">
					<span class="loadingImage_${counter}"></span>
				 	<span class="texte-erreur">
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
					</span>
				</td>
			</tr>
		</table>
	</td>
	<td class="separationPrice"></td>
	<td class="separationAction"></td>
</tr>