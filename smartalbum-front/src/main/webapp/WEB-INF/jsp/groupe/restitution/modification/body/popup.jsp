<%@ include file="/WEB-INF/jsp/common/include.jsp" %>

<div id="deleteLineLayer" class="notVisible deleteLayer">
	<c:set var="deleteLineSimulationURL"><c:url value='/action/groupe/modification/suppression/simulation/' /></c:set>
	<c:set var="validDeleteLineURL"><c:url value='/action/groupe/modification/suppression/confirmation.html' /></c:set>
	<c:set var="validDeleteLineURL"><c:url value='/action/groupe/modification/suppression/confirmation.html' /></c:set>
	<c:set var="confirmationURL"><c:url value='/groupe/confirmation.html' /></c:set>
	<c:set var="deleteGroupURL"><c:url value='/action/groupe/suppression/confirmation.html' /></c:set>
	<c:set var="silumationURL"><c:url value='/groupe/simulation.html'/></c:set>
	<c:set var="serviceIndisponibleURL"><c:url value='/error/service_indisponible.html'/></c:set>
	<c:set var="checkSimulationDelationResultURL"><c:url value='/action/groupe/verification/suppression/simulation/'/></c:set>
	
	
	<div id="conteneur-layer">
		<div id="btn-fermer">
			<a href="javascript:parent.$.fancybox.close();">
				<img src='<c:url value="/img/btn-fermer.png" />'>
			</a>
		</div>
		<div id="contenu-layer">
		
		</div>
		<div id="confirmer-layer">
		
			<a href="javascript:parent.$.fancybox.close();">
				<img src='<c:url value="/img/btn-annuler-layer.png" />' />
			</a>
			<a id="deleteLineLink" href="javascript:deleteLine();"
				data-delete-group-url="${deleteGroupURL}" data-simulation-url="${silumationURL}"
				data-delete-line-url="${deleteLineSimulationURL}" data-validate-delete-line-url="${validDeleteLineURL}"
				data-confirmation-url="${confirmationURL}" data-service-indisponible-url="${serviceIndisponibleURL}"
				data-check-simu-delation-url="${checkSimulationDelationResultURL}">
				<img src='<c:url value="/img/btn-confirmer-layer.png" />' />
			</a>
			
		</div>
	</div>
</div>

<div id="deleteGroupLayer" class="notVisible deleteLayer">

	<c:set var="deleteGroupURL"><c:url value='/action/groupe/suppression/confirmation.html' /></c:set>
	<c:set var="totalContribution" value="${ameGroup.maxReductionPerMonth}" />
	<c:if test="${ameGroup.boxFree}">
		<c:set var="totalContribution" value="29,99" />
	</c:if>

	<div id="conteneur-layer">
		<div id="btn-fermer">
			<a href="javascript:parent.$.fancybox.close();">
				<img src='<c:url value="/img/btn-fermer.png" />'>
			</a>
		</div>
		<div id="contenu-layer">
			<strong>Vous avez demand&eacute; la suppression de votre Multi-Packs.</strong><br />
			Si vous confirmez, vous perdrez le b&eacute;n&eacute;fice de vos remises,<br />
			soit <span class="price-layer">
				<%@ include file="/WEB-INF/jsp/groupe/restitution/modification/body/benefice_suppression_groupe.jsp" %>
			</span>/mois<br /><br />
			<input id="checkboxConfirmeDeleteGroupe" type="checkbox" onclick="javascript:activeImgBouton();"/>Confirmer la suppression<br>
		</div>
		<div id="confirmer-layer">
		
			<a href="javascript:parent.$.fancybox.close();">
				<img src='<c:url value="/img/btn-annuler-layer.png" />' />
			</a>
		  
			<a id="imgRemiseActive" style="display: none;" href="javascript:void(0);" >
				<img onclick="javascript:goToConfirmPage('${deleteGroupURL}');" src='<c:url value="/img/btn-confirmer-layer.png" />' />
			</a>
			
			<img id="imgRemiseInactive" src='<c:url value="/img/btn-gris-confirmer-layer.png" />' />
			
		
			
		</div>
	</div>
</div>
