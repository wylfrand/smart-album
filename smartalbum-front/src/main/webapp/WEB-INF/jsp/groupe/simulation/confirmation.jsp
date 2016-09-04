<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<head>
    <script type="text/javascript">
        products = [];
        products.push(stat_get_product("", "GROUPE", "1", "0"));
        products.push(stat_get_product("", "LIGNE", "12", "0"));
        stat_evt("event25",products);
    </script>
    
    <script type="text/javascript">
    function downloadPdf(e,url){
    	
    	if (e.preventDefault) {
    		e.preventDefault();
    		window.location.href = url;
    		return false;
    		}else {
    		e.returnValue = false;
    		window.location.href = url;
    		} 
    }
    </script>
    
    <c:set var="PJrequired" value="${ame_configuration.simulationInfos.chosenProposition.hasADMContrepartie}" scope="page" />	
    <c:set var="isSuppressionDeGroup" value="${ame_configuration.simulationInfos.chosenProposition==null ? true : false}" />
    <c:set var="remiseTotaleSuppressionGroupe" value="${ame_configuration.subscribedGroupeAmes[0].maxReductionPerMonth}" />
    <c:set var="remiseTotaleMPVX" value="${ame_configuration.simulationInfos.chosenProposition.groupeSimulations[0].maxReductionTotalPerMonth}" />
    <c:set var="remiseTotaleADDAMS" value="${ame_configuration.simulationInfos.chosenProposition.fixBenefit}" />
    <c:set var="remiseTotale" value="${(ame_configuration.simulationInfos.simulationEliame.typeActe eq 'AMS') or ame_configuration.simulationInfos.addams  ? remiseTotaleADDAMS : (isSuppressionDeGroup ? remiseTotaleSuppressionGroupe : remiseTotaleMPVX)}" />
	<c:set var="sfr_user" value="${ame_configuration.simulationInfos.connectedLine}" />
	<c:set var="prixTotalPourOfrirBox" value="30"/>
</head>
		<div id="back-top">
			<div>
				<p class="titre-confirmation">
					<strong>CONFIRMATION</strong><br/><br/></p>					
			</div>
		</div>
		<div id="contenu-confirmation">
				<div class="mesg-confirmation2">
				<c:choose>

					<c:when test="${ame_configuration.simulationInfos.simulationEliame.typeActe eq 'AMS'}">
						<c:choose>
		             		<c:when test="${remiseTotale.price lt prixTotalPourOfrirBox}">
		              		 &bull; <strong>Votre demande a &eacute;t&eacute; prise en compte et sera effective sous 1h.</strong><br/>
					     		&bull; Pass&eacute; ce d&eacute;lai, votre Multi-Pack sera cr&eacute;&eacute;. Vous b&eacute;n&eacute;ficierez d'une remise <br/>
					     		de <strong>-${remiseTotale}&euro; </strong>/ mois sur votre prochaine facture box ou la suivante si votre facture<br/> du mois en cours a d&eacute;j&agrave; &eacute;t&eacute; &eacute;dit&eacute;e.
		              			<br/><br/>
		              		</c:when>
		              		<c:otherwise>
		              		&bull; <strong>Votre demande a &eacute;t&eacute; prise en compte et sera effective sous 1h.</strong><br/>
				     		&bull; Pass&eacute; ce d&eacute;lai, votre Multi-Pack sera cr&eacute;&eacute;. Vous b&eacute;n&eacute;ficierez de la box <br/>
				     		offerte (dans la limite de <strong>29.99&euro;</strong>/ mois) sur votre prochaine facture box ou la suivante si votre facture<br/> du mois en cours a d&eacute;j&agrave; &eacute;t&eacute; &eacute;dit&eacute;e.
		             		<br/><br/>
		             		</c:otherwise>
		             	</c:choose>
						
					</c:when>
					<c:otherwise>
						&bull; <strong>Votre demande a &eacute;t&eacute; prise en compte et sera effective sous 1h.</strong><br/><br/>
             			<c:choose>
             				<c:when test="${isSuppressionDeGroup}">
              		    &bull; Pass&eacute; ce d&eacute;lai, votre Multi-Packs sera supprim&eacute; et vous perdrez vos remises, soit 
              		    <span class="price-layer"> <%@ include
								file="/WEB-INF/jsp/groupe/restitution/modification/body/benefice_suppression_groupe.jsp"%>
						</span>/mois
              		     sur votre prochaine facture ou sur la suivante si votre facture du mois en cours a d&eacute;j&agrave; &eacute;t&eacute; &eacute;dit&eacutee.
              			<br/><br/>
              				</c:when>
              				<c:otherwise>
<!--               				//supression line -->
									<c:choose>
             							<c:when test="${remiseTotale.price lt prixTotalPourOfrirBox}">
             								Pass&eacute; ce d&eacute;lai, votre Multi-Packs sera mis &agrave; jour. D&eacute;sormais vous &eacute;conomiserez 
             								<strong>-${remiseTotale}&euro; </strong>/mois sur votre prochaine facture <br /> ou sur la suivante si votre facture du mois en cours a d&eacute;j&agrave; &eacute;t&eacute; &eacute;dit&eacutee.
             							</c:when>
             							<c:otherwise>
             							Pass&eacute; ce d&eacute;lai, votre Multi-Packs sera mis &agrave; jour. Vous b&eacute;n&eacute;ficierez de la box <br/>
				     		offerte (dans la limite de <strong>29.99&euro;</strong>/ mois) sur votre prochaine facture box ou la suivante si votre facture<br/> du mois en cours a d&eacute;j&agrave; &eacute;t&eacute; &eacute;dit&eacute;e.
             							</c:otherwise>
             						</c:choose>
              				</c:otherwise>
              			</c:choose>
					</c:otherwise>
				</c:choose>
             	</div>
             	<c:if test="${PJrequired && not isSuppressionDeGroup}">
							<div class="info-asavoir-validation">
								<div class="img-asavoir"><img alt="" src="<c:url value='/img/point-info.png' />"></div>
								<div class="asavoir">
								<strong>Important</strong><br />
								&bull; Nous attendons des pièces justificatives de votre part. Pensez-y !<br>
								<a class="downloadLink" href="javascript:void(0);" onclick="javascript:downloadPdf(event,'<c:url value="/doc/formulaire/downloadManagerPiecesJustificatives.html"/>');return false;"><img src="<c:url value='/img/download.png' />"> T&eacute;l&eacute;chargez le formulaire (PDF).</a>
								</div>
							</div>
						</c:if>
			</div><!-- #contenu -->
			<c:if test="${remiseTotale lt prixTotalPourOfrirBox}">
			<div class="box-incomplete"> <strong>Votre groupe n'est pas complet. Et vous n'avez pas la box offerte?</strong></div>										
			<%@ include file="/WEB-INF/jsp/common/aides.jsp" %>		
			</c:if>
			<div class="retour">
				<a href="<c:url value='${espaceClientUrl}'/>"><img src="<c:url value='/img/retour-etape.png' />"> Retour &agrave; l&acute;accueil de mon Espace Client</a>
			</div>

   <script type="text/javascript">
		var stat_pvars = [];
		if (typeof zvars == 'undefined'){
			zvars = [];
		}
		zvars["events"] ="event8";
		zvars["eVar46"] ="SC:Avantages:Multipack";
	</script>