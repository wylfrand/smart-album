<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../../common/headerLayerReinitMdp.jsp">
	<jsp:param
		value="Parcours/Securite/OubliMotDePasse/LayerConfirmationEnvoiMail"
		name="tag_pagename" />
	<jsp:param value="${userStatType}" name="userStatType" />
</jsp:include>
<body style="margin: 0px;">
	<div id="lsw">
		<div class="sfrDom layer">

	<h3>Cr&eacute;er votre nouveau mot de passe</h3>
	<h4>Un lien vous permettant de cr&eacute;er votre nouveau mot de
		passe vous a &eacute;t&eacute; envoy&eacute;.</h4>

	<form:form commandName="resetPwdForm" cssClass="information">
		Vous le recevrez dans quelques instants à votre adresse email :<br />
		<strong id="emailContactMasked">${resetPwdForm.emailContactMasked}</strong>
		<div class="bulle masque">
			<img class="fleche" src="//static.s-sfr.fr/media/fleche-bulle-1.png" />
			Votre adresse email est partiellement masqu&eacute;e pour
			pr&eacute;server la confidentialit&eacute; de vos informations
			personnelles
		</div>
		<br/>
		<p class="info-email">
			<em>Vous n'avez pas reçu d'email ? V&eacute;rifiez que
				celui-ci n'est pas dans votre dossier de « SPAM » ou de « courrier
				ind&eacute;sirable ».</em>
		</p>

	</form:form>
	</div></div>
	<script type="text/javascript"
		src="${cdnStaticContext}/stats/footer.js"></script>
</body>
</html>