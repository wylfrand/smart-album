<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../common/headerReinitMdp.jsp">
	<jsp:param
		value="Parcours/Securite/OubliMotDePasse/ConfirmationEnvoiMail"
		name="tag_pagename" />
</jsp:include>

<script type="text/javascript">
	$(function() {
		$('#decodeReturnUrl').live(
				'click',
				function() {
					document.location.href = '${urlRetour}'.replace(/\%3F/g, '?').replace(/\%26/g, '&');
					return false;
				});
	});
</script>

<div id="lsw">
	<h1>Créer votre nouveau mot de passe</h1>
	<form:form commandName="resetPwdForm" cssClass="large-border">
		<p><strong>Un lien vous permettant de cr&eacute;er votre nouveau mot de
			passe vous a &eacute;t&eacute; envoy&eacute;.</strong></p>
						Vous le recevrez dans quelques instants &agrave; votre adresse
						email<br />
		<strong>${resetPwdForm.emailContactMasked}</strong>
		<br>
		<br>
		<em>Vous n'avez pas reçu d'email ? Vérifiez que celui-ci n'est
			pas dans votre dossier de « SPAM »<br> ou de « courrier
			indésirable ».
		</em>
		<div class="links">
			<c:choose>
				<c:when test="${sessionScope.srr}">
					<a class="myosotis-link tiny" href="http://moncompte-adsl.sfr.re/">Acc&eacute;der
						&agrave; votre Espace Client</a>
					<br />
				</c:when>
				<c:otherwise>
					<a id="decodeReturnUrl" class="myosotis-link tiny" href="">Acc&eacute;der
							&agrave; votre Espace Client</a>
					<br />
				</c:otherwise>
			</c:choose>
		</div>
	</form:form>
</div>

<jsp:include page="../common/footerReinitMdp.jsp" />