<%@ include file="/WEB-INF/jsp/common/include.jsp" %>

	<c:if test='${(not empty ameGroup.numbersWaitingCompensation["MIGRATION"]) || (not empty ameGroup.numbersWaitingCompensation["PJ"]) || (not empty ameGroup.numbersWaitingCompensation["ACTIVATION"]) }'>
		<div class="info-asavoir-validation">
			<div class="img-asavoir"><img alt="" src='<c:url value="/img/point-info.png" />' /></div>
			
			<div class="asavoir">
					<c:set var="title">Important, ce que vous devez savoir</c:set>
					<c:if test='${not empty ameGroup.numbersWaitingCompensation["PJ"] }'>
						<c:set var="title">Important, votre dossier sera validé sous quelques conditions.</c:set>
					</c:if>
				
				<strong>${title}</strong><br/>
				<c:if test='${not empty ameGroup.numbersWaitingCompensation["PJ"] }'>
					<p>
						<c:choose>
						<c:when test='${fn:contains(ameGroup.numbersWaitingCompensation["PJ"], ",") }'>							
								<c:set var="nbCompAff" value="0"/>
                                <c:set var="listNum" value="${fn:split(ameGroup.numbersWaitingCompensation['PJ'], ',')}"/>
                                <c:set var="lengthListNum" value="${fn:length(listNum)}"/>
                                &bull; Les lignes 
                                <c:forEach var="ligneSimulation" items="${listNum}" varStatus="varLigne"><c:set var="nbCompAff" value="${nbCompAff+1}" /><strong><maam:telephone label="${ligneSimulation}" separator="." /></strong><c:if test="${nbCompAff lt (lengthListNum - 1)}">,&nbsp;</c:if><c:if test="${nbCompAff eq (lengthListNum - 1)}">&nbsp;et&nbsp;</c:if></c:forEach> 
                                doivent être détenues par des titulaires de même nom ou même adresse que les autres lignes du Multi-Packs. Merci de télécharger le formulaire permettant de justifier votre situation.								
						  </c:when>
							<c:otherwise>
								&bull; La ligne <strong>${ameGroup.numbersWaitingCompensation["PJ"]}</strong> doit être détenue par un titulaire de même nom ou même adresse que les autres lignes du Multi-Packs.
								Merci de télécharger le formulaire permettant de justifier votre situation.
							</c:otherwise>
						</c:choose>
						<br /> <br /><a href="javascript:void(0);"
					onclick="javascript:downloadPdf(event,'<c:url value="/doc/formulaire/downloadManagerPiecesJustificatives.html?isRestitution=true"/>');return false;">
					<u><img src="<c:url value='/img/download.png' />"> T&eacute;l&eacute;chargez le formulaire (PDF).</u></a> <br /> <br />
					</p>
				</c:if>
				
				<c:if test='${not empty ameGroup.numbersWaitingCompensation["MIGRATION"] }'>
					<p>
						<c:choose>
							<c:when test='${fn:contains(ameGroup.numbersWaitingCompensation["MIGRATION"], ",") }'>
								&bull; Vous avez demandé à  changer de forfait sur vos lignes 
								<c:set var="nbCompAff" value="0"/>
                                <c:set var="listNum" value="${fn:split(ameGroup.numbersWaitingCompensation['MIGRATION'], ',')}"/>
                                <c:set var="lengthListNum" value="${fn:length(listNum)}"/>
                                <c:forEach var="ligneSimulation" items="${listNum}" varStatus="varLigne"><c:set var="nbCompAff" value="${nbCompAff+1}" /><strong><maam:telephone label="${ligneSimulation}" separator="." /></strong><c:if test="${nbCompAff lt (lengthListNum - 1)}">,&nbsp;</c:if><c:if test="${nbCompAff eq (lengthListNum - 1)}">&nbsp;et&nbsp;</c:if></c:forEach>. La remise affich&eacute;e sur votre box en tient d&eacutej&agrave; compte.
							</c:when>
							<c:otherwise>
								&bull; Vous avez demandé à  changer de forfait sur votre ligne <strong>${ameGroup.numbersWaitingCompensation["MIGRATION"]}</strong>. La remise affich&eacute;e sur votre box en tient d&eacutej&agrave; compte.
							</c:otherwise>
						</c:choose>
					</p>
				</c:if>
				
				<c:if test='${not empty ameGroup.numbersWaitingCompensation["ACTIVATION"] }'>
					<p>
						<c:choose>
							<c:when test='${fn:contains(ameGroup.numbersWaitingCompensation["ACTIVATION"], ",") }'>
								&bull; Vous devez activer vos lignes 
								<c:set var="nbCompAff" value="0"/>
                                <c:set var="listNum" value="${fn:split(ameGroup.numbersWaitingCompensation['ACTIVATION'], ',')}"/>
                                <c:set var="lengthListNum" value="${fn:length(listNum)}"/>
                                <c:forEach var="ligneSimulation" items="${listNum}" varStatus="varLigne"><c:set var="nbCompAff" value="${nbCompAff+1}" /><strong><maam:telephone label="${ligneSimulation}" separator="." /></strong><c:if test="${nbCompAff lt (lengthListNum - 1)}">,&nbsp;</c:if><c:if test="${nbCompAff eq (lengthListNum - 1)}">&nbsp;et&nbsp;</c:if></c:forEach> pour bÃ©nÃ©ficier de la remise sur votre box.
							</c:when>
							<c:otherwise>
								<c:if test="${fn:length(ameGroup.ameMembers) ge 3}">
							&bull; Une des lignes de votre groupe Multi-Packs est en cours dâ€™activation.<br/>
												Si cette nouvelle ligne n'est pas activée dans un délai de deux mois, 
												votre remise sera automatiquement ajustée Ã  la baisse.
								</c:if>
								<c:if test="${fn:length(ameGroup.ameMembers) eq 2}">
							&bull; Une des lignes de votre groupe Multi-Packs est en cours dâ€™activation.<br/>
												Si cette nouvelle ligne n'est pas activée dans un délai de deux mois, 
												votre groupe sera automatiquement supprimé.
								</c:if>
							</c:otherwise>
						</c:choose>
					</p>
				</c:if>
			</div>
		</div>
	</c:if>
	
