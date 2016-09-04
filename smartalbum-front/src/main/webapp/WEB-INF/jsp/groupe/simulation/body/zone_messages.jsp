<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>

<div class="info-asavoir-validation ajustBonASavoir">
	<div class="img-asavoir"><img alt="" src='<c:url value="/img/point-info.png" />' /></div>


	<div class="asavoir">
		<c:choose>
			<c:when test="${groupe_ame.hasADMContrepartie}">
				<strong>Important, votre dossier sera valid&eacute; sous quelques conditions.</strong><br />
			</c:when>
			<c:otherwise>
				<strong>Important, ce que vous devez savoir</strong>.<br />
			</c:otherwise>
		</c:choose>
		<br />

		<c:if test="${groupe_ame.hasADMContrepartie}">
			<p>
				<c:choose>
					<c:when test="${groupe_ame.nbPJComp>1}">
					<c:set var="nbCompAff" value="0"/>
						&bull; Les lignes 
						<c:forEach var="ligneSimulation" items="${groupe_ame.lignesWithCompensations}" varStatus="varLigne"><c:if test="${ligneSimulation.waitingForADMCompensation}"><c:set var="nbCompAff" value="${nbCompAff+1}"/><strong><maam:telephone label="${ligneSimulation.ligne.numLigne}" separator="." /></strong><c:if test="${nbCompAff lt groupe_ame.nbPJComp-1}">,&nbsp;</c:if><c:if test="${nbCompAff eq groupe_ame.nbPJComp-1}">&nbsp;et&nbsp;</c:if></c:if></c:forEach>
						doivent &ecirc;tre d&eacute;tenues par des titulaires de m&ecirc;me nom ou de m&ecirc;me adresse que les autres lignes du Multi-Packs. Merci de t&eacute;l&eacute;charger le formulaire permettant de justifier votre situation.<br />
					</c:when>
					<c:otherwise>
						<c:forEach var="ligneSimulation" items="${groupe_ame.lignesWithCompensations}" varStatus="varLigne">
							<c:if test="${ligneSimulation.waitingForADMCompensation}">
								&bull; La ligne <strong><maam:telephone label="${ligneSimulation.ligne.numLigne}" separator="." /></strong>
								doit &ecirc;tre d&eacute;tenue par un titulaire de m&ecirc;me nom ou de m&ecirc;me adresse que les autres lignes du Multi-Packs. Merci de t&eacute;l&eacute;charger le formulaire permettant de justifier votre situation.<br />
							</c:if>
						</c:forEach>
					</c:otherwise>
				</c:choose>
				<br /> <a href="javascript:void(0);"
					onclick="javascript:downloadPdf(event,'<c:url value="/doc/formulaire/downloadManagerPiecesJustificatives.html"/>');return false;">
					<u><img src="<c:url value='/img/download.png' />"> T&eacute;l&eacute;chargez le formulaire (PDF).</u></a> <br /> <br />
			</p>
		</c:if>

		<c:if test='${groupe_ame.hasPTAContrepartie}'>
			<p>
				<c:choose>
					<c:when test="${groupe_ame.nbMIGComp>1}">
					<c:set var="nbCompAff" value="0"/>
							&bull; Vous avez demandé à changer de forfait sur vos lignes 
				    <c:forEach var="ligneSimulation" items="${groupe_ame.lignesWithCompensations}" varStatus="varLigne"><c:if test="${ligneSimulation.waitingForPTACompensation}"><c:set var="nbCompAff" value="${nbCompAff+1}"/><strong><maam:telephone label="${ligneSimulation.ligne.numLigne}" separator="." /></strong><c:if test="${nbCompAff lt groupe_ame.nbMIGComp-1}">,&nbsp;</c:if><c:if test="${nbCompAff eq groupe_ame.nbMIGComp-1}">&nbsp;et&nbsp;</c:if></c:if></c:forEach>
						. La remise affich&eacute;e sur votre box en tient d&eacutej&agrave; compte.
					</c:when>
					<c:otherwise>
						<c:forEach var="ligneSimulation" items="${groupe_ame.lignesWithCompensations}" varStatus="varLigne">
							<c:if test="${ligneSimulation.waitingForPTACompensation}">
								&bull; Vous avez demandé à changer de forfait sur votre ligne <strong><maam:telephone label="${ligneSimulation.ligne.numLigne}" separator="." /></strong>. La remise affich&eacute;e sur votre box en tient d&eacutej&agrave; compte.
							</c:if>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</p>
		</c:if>
		<c:if test='${groupe_ame.hasACTContrepartie}'>
			<p>
				<c:choose>
					<c:when test="${groupe_ame.nbACTComp>1}">
					&bull; Vous devez activer vos lignes 
					<c:set var="nbCompAff" value="0"/>
					<c:forEach var="ligneSimulation" items="${groupe_ame.lignesWithCompensations}" varStatus="varLigne"><c:if test="${ligneSimulation.waitingForACTCompensation}"><c:set var="nbCompAff" value="${nbCompAff+1}"/><strong><maam:telephone label="${ligneSimulation.ligne.numLigne}" separator="." /></strong><c:if test="${nbCompAff lt groupe_ame.nbACTComp-1}">,&nbsp;</c:if><c:if test="${nbCompAff eq groupe_ame.nbACTComp-1}">&nbsp;et&nbsp;</c:if></c:if></c:forEach>
						pour bénéficier de la remise sur votre box.
					</c:when>
					<c:otherwise>
					<c:forEach var="ligneSimulation" items="${groupe_ame.lignesWithCompensations}" varStatus="varLigne">
						<c:if test="${ligneSimulation.waitingForACTCompensation}">
						&bull; Vous devez activer votre ligne <strong><maam:telephone label="${ligneSimulation.ligne.numLigne}" separator="." /></strong> pour bénéficier de la remise sur votre box.
						</c:if>
					</c:forEach>
					</c:otherwise>
				</c:choose>
			</p>
		</c:if>
		
	</div>

</div>