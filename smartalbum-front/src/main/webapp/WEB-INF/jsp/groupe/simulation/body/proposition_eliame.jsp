<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>

	<table class="table table-condensed">
		<thead>
			<tr>
				<th class="line_multi-pack">Les lignes de votre Multi-Packs</th>
				<th class="remise_multi-pack">Remises</th>
				<th></th>
			</tr>
		</thead>
	<tbody>
	
	<c:forEach var="ligneSimulation" items="${chosenProposition.groupeSimulations[0].ligneSimulations}" varStatus="stat">
		<c:set var="ligne" value="${null}" />
		<c:forEach var="simuligne" items="${ame_configuration.simulationInfos.simuLignes}" varStatus="loopstatus">
			<c:set var="numberToCompare" value="${ligneSimulation.ligne.natureLigne eq 'THD'? ligneSimulation.ligne.numLigneVOIP : ligneSimulation.ligne.numLigne}" />
			<c:if test="${numberToCompare eq simuligne.number}">
				<c:set var="ligne" value="${simuligne}" />
			</c:if>
			<!-- simuGroupe = groupe envoye par ELIAME -- > simuLigne = lignes envoyee par Eliame -->
			</c:forEach>
			
			<c:if test="${ligne.existingLigne}">
			<c:choose>
				<c:when test="${not (ligne.natureLine eq 'MOB')}">
					<tr>
						<td class="firstColumn">
							<img alt="" src='<c:url value="/img/box.png" />' class="box-off" />
							<c:choose>
									<c:when test="${ligneSimulation.underConstruction}">
										<span class="texte-td newline"> Nouvelle ligne</span>
									</c:when>
									<c:otherwise>
										<span class="texte-td"> <maam:telephone
												label="${ligneSimulation.ligne.numLigne}" separator="." />
										</span>
									</c:otherwise>
								</c:choose> 
							<c:choose>
								<c:when test="${ligne.initiallyAdded}">
								<span class="texte-td">
									<maam:fullName id="fullName${stat.index}" withToolTip="true" civilite="${ligne.civilite}" firstName="${ligne.firstname}" name="${ligne.name}" taille="${longueurFullName}"/>
								</span>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${ligne.sameNameAsCurrentLine or ligne.connectedLine}">
											<span class="texte-td">
												<maam:fullName id="fullName${stat.index}" withToolTip="true" civilite="${ligne.civilite}" firstName="${ligne.firstname}" name="${ligne.name}" taille="${longueurFullName}"/>
											</span>
										</c:when>
										<c:otherwise>
											<span class="protectedName texte-td" style="margin-left: 17px;">
												Identit&eacute; du titulaire<br/> prot&eacute;g&eacute;e*
											</span>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
							<span class="texte-td-gris-box">
								<maam:libelleForfait id="libelleForfait${stat.index}" withToolTip="true" taille="${longueurTitreForfaitBox}" libelle="${ligne.libellePTA}"/>
							</span>
						</td>
						
						<td class="separationPrice">
							<div class="price-remise-euro">
								<%@ include file="/WEB-INF/jsp/groupe/simulation/body/benefice_ligne_fixe.jsp" %>
							</div>
						</td>
						<td class="separationAction"></td>
					</tr>
					</c:when>
				<c:otherwise>
					<tr>
						<td>
							<img alt="" src='<c:url value="/img/mobile.png" />' class='adjustement-line'/>
							<span class="texte-td">
								<maam:telephone label="${ligneSimulation.ligne.numLigne}" separator="." />
							</span>
							
								<c:choose>
								<c:when test="${ligne.initiallyAdded or ligne.connectedLine}">
								<span class="texte-td">
									<maam:fullName id="fullName${stat.index}" withToolTip="true" civilite="${ligne.civilite}" firstName="${ligne.firstname}" name="${ligne.name}" taille="${longueurFullName}"/>
								</span>
								</c:when>
								<c:otherwise>
									<c:set var="differentNamesExists" value="true" />
									<p class="protectedName texte-td" style="margin-left: 17px;">
										Identit&eacute; du titulaire<br/>prot&eacute;g&eacute;e*
									</p>
								</c:otherwise>
							</c:choose>
							<div class='${ligneSimulation.waitingForPTACompensation ? "texte-td-gris-promo twoLines" : "texte-td-gris-promo"}'>
								<div class="promo">
									<maam:libelleForfait id="libelleForfait${stat.index}" withToolTip="true" taille="${longueurTitreForfaitMobile}" libelle="${ligne.libellePTA}"/>
									<c:if test="${ligneSimulation.waitingForPTACompensation}">
										<br /><span class="loader">changement en cours</span>
									</c:if>
								</div>
								
 								<div class="carre-promo"> 
									<img src="${ligne.joyaPictoUrl}" />
 								</div> 
							</div>
						</td>
						<td class="separationPrice">
							<c:set var="message_contributrice">
								<c:choose>
	   								<c:when test="${chosenProposition.groupeSimulations[0].minReductionFixTotalPerMonth eq chosenProposition.groupeSimulations[0].maxReductionFixTotalPerMonth and chosenProposition.groupeSimulations[0].maxReductionFixTotalPerMonth.price ge 30}">
	   									Cette ligne contribue à la Box offerte
	   								</c:when>
	   								<c:otherwise>
	   								Cette ligne vous fait b&eacute;n&eacute;ficier d'une remise de -${ligneSimulation.maxReductionTotalPerMonth}&euro; sur votre box
	   								</c:otherwise>
	   							</c:choose>   
							</c:set>
							<c:set var="message_non_contributrice" value="Cette ligne ne contribue pas aux remises sur la box"/>
								<c:choose>
									<c:when test="${simulationInfos.addams}">
										<c:choose>
											<c:when test="${ligneSimulation.maxReductionTotalPerMonth.gratuit}">
												<a  rel="tooltip"  data-placement="right" class="aa" href="javascript:void(0);" data-original-title="${message_non_contributrice}">
												<img alt="" src="<c:url value='/img/remise-info.png'/>" class="img-remise" border="0"></a>
											</c:when>
											<c:otherwise>
												<a  rel="tooltip"  data-placement="right" class="aa" href="javascript:void(0);" data-original-title="${message_contributrice}">
												<img alt="" src="<c:url value='/img/remise-euro.png'/>" class="img-remise" border="0"></a>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<%@ include file="/WEB-INF/jsp/groupe/simulation/body/benefice_ligne_mobile.jsp" %>
									</c:otherwise>
								</c:choose>
						</td>
						<td class="separationAction disabledAction"></td>
					</tr>
			</c:otherwise>
		</c:choose>
	</c:if>
</c:forEach>
	</tbody>
</table>