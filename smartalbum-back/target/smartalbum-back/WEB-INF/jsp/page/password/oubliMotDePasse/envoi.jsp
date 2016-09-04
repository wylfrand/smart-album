<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:choose>
	<c:when test='${resetPwdForm.finalNotification eq "SMS" }'>
		<c:set var="tag_pagename_value"
			value="Parcours/Securite/OubliMotDePasse/ConfirmationEnvoiSMS" />
	</c:when>
	<c:otherwise>
		<c:set var="tag_pagename_value"
			value="Parcours/Securite/OubliMotDePasse/ConfirmationEnvoiMailDirect" />
	</c:otherwise>
</c:choose>
<jsp:include page="../common/headerReinitMdp.jsp">
	<jsp:param value="${tag_pagename_value}" name="tag_pagename" />
</jsp:include>
<script type="text/javascript">
	$(function() {
		$('#decodeReturnUrl').live(
				'click',
				function() {
					document.location.href = '${urlRetour}'.replace(/\%3F/g, '?').replace(/\%26/g, '&');
					return false;
				});
		$('img.tip').bubbles({'contentType' : 'alt', width : 220});
		var isSubmit = false;
	});
</script>
<div id="lsw">
	<h1>Votre nouveau mot de passe a bien été envoyé</h1>

	<div class="large-border">
		<form:form commandName="resetPwdForm">
			<p>
				Vous le recevrez dans quelques instants
				<c:choose>
					<c:when test='${resetPwdForm.finalNotification eq "SMS" }'>
				par SMS au numéro <strong>${resetPwdForm.finalAddressMasked}</strong>
					</c:when>
					<c:otherwise>
	    	    à votre adresse email <strong>${resetPwdForm.emailContactMasked}</strong>
					</c:otherwise>
				</c:choose>
			</p>

			<div class="information" style="margin-top: 2em;">
				<img src="../img/fanion.png" /> Si vous consultez vos emails SFR
				depuis votre mobile, ou votre PC via un logiciel (Outlook,
				Thunderbird...), <strong>remplacez l'ancien mot de passe par
					celui-ci</strong>.<br> Si vous utilisez une application SFR sur
				smartphone, vous pouvez être amené à remplacer l'ancien mot de passe
				par celui-ci.
			</div>

			<div class="links">
				<c:choose>
					<c:when test="${not empty sboSpecificLeaveLink}">
						<a id="sboSpecificLeaveLink" class="myosotis-link tiny"
							href="${bizteamUsercare}">Acc&eacute;der &agrave; votre Espace
							Client</a>
					</c:when>
					<c:otherwise>
						<a id="decodeReturnUrl" class="myosotis-link tiny" href="">Acc&eacute;der
							&agrave; votre Espace Client</a>
					</c:otherwise>
				</c:choose>
			</div>
		</form:form>
	</div>
</div>
<jsp:include page="../common/footerReinitMdp.jsp" />