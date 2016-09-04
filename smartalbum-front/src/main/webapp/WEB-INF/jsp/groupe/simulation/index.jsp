<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>

<c:set var="lignesHorsProposition"
	value="${ame_configuration.simulationInfos.simuLignesHorsProposition}" />
<c:set var="chosenProposition"
	value="${ame_configuration.simulationInfos.chosenProposition}" />
<c:set var="groupe_ame"
	value="${ame_configuration.simulationInfos.chosenProposition.groupeSimulations[0]}" />
<c:set var="simulationInfos"
	value="${ame_configuration.simulationInfos}" />
<c:set var="totalInegibilite"
	value="${fn:length(groupe_ame.ligneSimulations)>0 ? false : true}" />

<script type="text/javascript">
 
 function validerSimulation(typeAct){

	var actionUrl = "";

	if(typeAct == 'AMS')
	{
		actionUrl = "<c:url value='/action/groupe/souscription/confirmation.html'/>?sfrintid=EC_mp_creation";
	}

	if(typeAct == 'AMA')
	{
		actionUrl = "<c:url value='/action/groupe/modification/ajout/confirmation.html'/>?sfrintid=EC_mp_modification";
	}
	
	if(typeAct == 'AMD')
	{
		actionUrl = "<c:url value='/action/groupe/modification/suppression/confirmation.html'/>?sfrintid=EC_mp_suppression";
	}

    $.ajax({
    	type	 : "POST",
    	cache	: false,
    	url	 : actionUrl,
    	success: function(data) {

           if(data.result == true)
           {
        		document.location.href="<c:url value='/groupe/confirmation.html'/>";
           }
           else if(data.error == "GROUPE_MODIFICATION_AJOUT_ERROR" || data.error == "GROUPE_SOUSCRIPTION_ERROR")
           {
        		document.location.href="<c:url value='/error/service_indisponible.html'/>";
           }
           else if (data.error == "ELIAME_INELIGIBLE") {
        	   document.location.href="<c:url value='/error/ineligible.html'/>";
           }
           else
           {
        	   document.location.href = "<c:url value='/error/service_indisponible.html'/>";
           }
       },
		error : function(request, status, err) {
			 document.location.href = "<c:url value='/error/service_indisponible.html'/>";
		}
    });
 } 
 </script>

<div id="back-top"
	class='${totalInegibilite ? "totalInegibilite" : "" }'>
<div>
<p class="titre"><strong>VOTRE PROPOSITION MULTI-PACKS</strong><br />
<br />
<c:choose>
	<c:when
		test="${chosenProposition.groupeSimulations[0].oneFixAndManyMobiles}">
		<span class="mesg-confirmation">Bravo, vous pouvez faire des
		&eacute;conomies gr&acirc;ce &agrave; vos lignes mobiles. <br />
		Voici notre meilleure proposition ! <br />
		<br />
		Alors vous confirmez ?</span>
	</c:when>
	<c:otherwise>
		<c:if
			test="${fn:length(chosenProposition.groupeSimulations[0].ligneSimulations)==2}">
			<span class="mesg-confirmation">Bravo, vous pouvez faire des
			&eacute;conomies gr&acirc;ce &agrave; votre ligne mobile. <br />
			Voici notre meilleure proposition ! <br />
			<br />
			Alors vous confirmez ?</span>
		</c:if>

		<c:if
			test="${fn:length(chosenProposition.groupeSimulations[0].ligneSimulations)==0}">
			<c:choose>
				<c:when test="${simulationInfos.simulationEliame.typeActe eq 'AMS'}">
					<span class="mesg-confirmation">Malheureusement, aucune de
					vos lignes ne vous permet <br />
					de bénéficier de remises Multi-Packs</span>
				</c:when>
				<c:otherwise>
							Rien ne change pour votre Multi-Packs. Vous conservez vos remises actuelles
							</c:otherwise>
			</c:choose>
		</c:if>
	</c:otherwise>
</c:choose> <br />
<br />
</p>
</div>
</div>

<div id="contenu2"
	class='${totalInegibilite ? "totalInegibilite" : "" }'><c:if
	test="${groupe_ame.hasADMContrepartie or groupe_ame.hasPTAContrepartie or groupe_ame.hasACTContrepartie}">
	<%@ include
		file="/WEB-INF/jsp/groupe/simulation/body/zone_messages.jsp"%>
</c:if> <c:if test="${not totalInegibilite}">
	<%@ include
		file="/WEB-INF/jsp/groupe/simulation/body/proposition_eliame.jsp"%>
</c:if> 

<c:if
	test="${(fn:length(lignesHorsProposition)>0 && fn:length(chosenProposition.groupeSimulations[0].ligneSimulations)!=0) && (simulationInfos.simulationEliame.typeActe ne 'AMD')}">
	<%@ include
		file="/WEB-INF/jsp/groupe/simulation/body/lignes_hors_groupe.jsp"%>
	</c:if> <c:if test="${not totalInegibilite}">
	<div class="confirmer-2"><c:set var="message_ama_mpvx"
		value="En confirmant, vos remises Multi-Packs seront désormais sur votre facture box" />
	<c:choose>
		<c:when
			test="${( not simulationInfos.addams) && simulationInfos.simulationEliame.typeActe eq 'AMA'}">

			<div class="message-ama-mpvx">${message_ama_mpvx}</div>
			<a id="imgRemise" class="noCreatedLine" href="javascript:void(0);"
				onclick="javascript:validerSimulation('${simulationInfos.simulationEliame.typeActe}');">
			<img alt="" src='<c:url value="/img/btn_confirmer.png" />' border="0" />
			</a>
		</c:when>
		<c:otherwise>
			<a href="javascript:void(0);"
				onclick="javascript:validerSimulation('${simulationInfos.simulationEliame.typeActe}');">
			<img alt="" src="<c:url value='/img/btn_confirmer_2.png'/>"
				border="0"></a>
		</c:otherwise>
	</c:choose></div>

	<div class="clear"></div>
	<c:choose>
		<c:when test="${ligne.connectedLine}">
		<div id="textSameName" style="display: none;"><%@ include
				file="/WEB-INF/jsp/groupe/restitution/identite_protege.jsp"%>
			</div>
		</c:when>
		<c:otherwise>
		<div id="textSameName" style="margin-top: 4px"><%@ include
				file="/WEB-INF/jsp/groupe/restitution/identite_protege.jsp"%>
			</div>
		</c:otherwise>
	</c:choose>


</c:if></div>
<!-- #contenu -->
<%-- <%@ include file="/WEB-INF/jsp/common/aides.jsp" %> MAAM-447 Ce bloc ne doit pas etre affiche --%>
<div class="clearer"><!-- --></div>
<div class="retour"><a
	href="<c:url value='/groupe/affichage/index.html'/>"><img
	src="<c:url value='/img/retour-etape.png' />"> Retour &agrave;
l'&eacute;tape pr&eacute;c&eacute;dente</a></div>