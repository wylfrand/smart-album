<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
    <head>
		<title>Les Multi-Packs de SFR - Erreur</title>
		<script>
			var _stats_univers = "espace_client";
			var _stats_pagename = "Mes Avantages/Multipack/Erreurs/Ligne non eligible ";
		</script>
    </head>

	<div id="back-top">
		<div>
			<p class="titre-confirmation">
				<strong>VOUS N'&Ecirc;TES PAS &Eacute;LIGIBLE</strong><br><br>
				<span class="mesg-confirmation">Votre offre actuelle ne vous permet pas de cr&eacute;er un Multi-Packs.</span><br><br>
			</p>
		</div>
	</div>
	
	<div id="contenu-ineligibilite-totale">
		<div>
     		<div class="bloc-non-valide-confirm">Pour en b&eacute;n&eacute;ficier passez aux formules Carr&eacute;es et b&eacute;n&eacute;ficiez de l'avantage tremplin!<br>
     			<a href="${offreMobile}" class="no-color-text"><img src="<c:url value='/img/arrow.png' />"> Changer d'offre mobile<br/></a>
     			<a href="${toutesLesOffres}" class="no-color-text"><img src="<c:url value='/img/arrow.png' />"> Consulter toutes les offres Multi-Packs</a> 
     		</div>	
		</div>					
	</div>

	<div class="retour-conf">
		<a href="<c:url value='${espaceClientUrl}'/>">
			<img src="<c:url value='/img/retour-etape.png' />"> Retour &agrave; l&acute;accueil de mon Espace Client
		</a>
	</div>