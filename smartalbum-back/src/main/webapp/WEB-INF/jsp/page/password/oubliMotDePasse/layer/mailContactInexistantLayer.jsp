<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../../common/headerLayerReinitMdp.jsp">
	<jsp:param
		value="Parcours/Securite/OubliMotDePasse/LayerSans Adresse Secours"
		name="tag_pagename" />
	<jsp:param value="${userStatType}" name="userStatType" />
</jsp:include>

<body style="margin: 0px;">
	<div id="lsw">
		<div class="sfrDom layer">

			<h3>Cr&eacute;er votre nouveau mot de passe</h3>

			<div class="information" style="border: 0">

				<p>Nous ne pouvons pas vous envoyer votre nouveau mot de passe
					car vous n'avez jamais communiqu&eacute; &agrave; SFR d'adresse
					email ou de t&eacute;l&eacute;phone de secours ou de contact.</p>
				<p>Pour obtenir votre nouveau mot de passe veuillez contacter le
					service client SFR.</p>

				<a class="myosotis-link tiny"
					href="http://assistance.sfr.fr/contacter/accueil/contact-accueil/fc-3269-72681">Tous
					les moyens de contacter le Service Client</a>

			</div>
		</div>
	</div>
	<script type="text/javascript"
		src="${cdnStaticContext}/stats/footer.js"></script>
</body>
</html>