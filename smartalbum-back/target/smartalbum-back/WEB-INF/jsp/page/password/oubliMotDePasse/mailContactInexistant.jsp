<jsp:include page="../common/headerReinitMdp.jsp">
	<jsp:param
		value="Parcours/Securite/OubliMotDePasse/Sans Adresse Secours"
		name="tag_pagename" />
</jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="lsw">
	<h1>Créer votre nouveau mot de passe</h1>
	<form class="large-border f5">

		<c:choose>
			<c:when test="${sessionScope.srr}">
				<p>Nous ne pouvons pas vous envoyer votre nouveau mot de passe
					car vous n'avez jamais communiqué à SFR d'adresse email ou de
					téléphone de secours ou de contact.</p>
				<p>Pour obtenir votre nouveau mot de passe veuillez contacter le
					service client SFR au 1097.</p>
			</c:when>
			<c:otherwise>
				<p>Nous ne pouvons pas vous envoyer votre nouveau mot de passe
					car vous n'avez jamais communiqué à SFR d'adresse email ou de
					téléphone de secours ou de contact</p>
				<p>Pour obtenir votre nouveau mot de passe veuillez contacter le
					service client SFR.</p>
				<a class="myosotis-link tiny" id="assistanceLink"
					href="${urlAssistanceContact}">Tous les moyens de contacter le
					Service Client</a>
			</c:otherwise>
		</c:choose>
	</form>
</div>

<jsp:include page="../common/footerReinitMdp.jsp" />