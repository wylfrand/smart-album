<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
    <head>
		<title>Les Multi-Packs de SFR - Erreur</title>
		<meta http-equiv="Content-Type" content= "text/html; charset=iso-8859-15" />
	
		<script>
			var _stats_univers = "espace_client";
			var _stats_pagename = "Mes Avantages/Multipack/Erreurs/Service indisponible";
		</script>
    </head>

<div id="back-top">
  		<div>
		<p class="titre-confirmation">
			<strong>SERVICE INDISPONIBLE</strong><br><br>
		</p>
	</div>
</div>

<div id="contenu-ineligibilite-totale">
	<div>
    		<div class="bloc-non-valide-confirm">
			<span class="mesg-confirmation">Le service est momentan&eacute;ment indisponible, merci de vous reconnecter ult&eacute;rieurement.</span><br><br>
    		</div>	
	</div>
</div>

<div class="retour-conf">
	<a href="<c:url value='${espaceClientUrl}'/>">
		<img src="<c:url value='/img/retour-etape.png' />"> Retour &agrave; l&acute;accueil de mon Espace Client
	</a>
</div>
