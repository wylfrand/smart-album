<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<spring:bind path="resetPwdForm">
	<c:choose>
		<c:when test="${empty status.errors.allErrors}">
			<c:set var="tag_pagename_value"
				value="Parcours/Securite/OubliMotDePasse/ChoixEnvoi" />
		</c:when>
		<c:otherwise>
			<c:set var="tag_pagename_value"
				value="Parcours/Securite/OubliMotDePasse/Erreur" />
		</c:otherwise>
	</c:choose>
	<jsp:include page="../common/headerReinitMdp.jsp">
		<jsp:param value="${tag_pagename_value}" name="tag_pagename" />
	</jsp:include>
</spring:bind>

<div id="lsw">
	<h1>Créer votre nouveau mot de passe</h1>
	<!-- contenu custom -->
	<form:form id="resetPwdForm" commandName="resetPwdForm" method="post"
		action="confirmation.action?urlRetour=${urlRetour}"
		cssClass="large-border">
		<c:set var="errorsBinding">
			<form:errors path="*" element="li" />
		</c:set>
		<div class=" <c:if test='${empty (errorsBinding)}'>hidden </c:if>">
			<spring:hasBindErrors name="resetPwdForm">
				<ul>
					<c:forEach var="error" items="${errors.allErrors}">
						<li><c:if test='${empty (error.defaultMessage)}'>
								<spring:message code="${error.code}"></spring:message>
							</c:if> <c:if test='${!empty (error.defaultMessage)}'>${error.defaultMessage}</c:if>
						</li>
					</c:forEach>
				</ul>
			</spring:hasBindErrors>
		</div>

		<form:hidden path="identifiant" />
		<form:hidden path="utilisateurFixe" />
		<form:hidden path="msisdn" />
		<form:hidden path="ascUser" />
		<c:if test="${!empty (resetPwdForm.secretQuestion)}">
			<!-- TODO: Qu'est-ce qu'on en fait ? Non maquetté... -->
			<fieldset>
				<p>
				<h2>
					<c:if test="${!empty (resetPwdForm.notificationView)}">
						<span>1.</span>
					</c:if>
					Veuillez r&eacute;pondre &agrave; votre question secr&egrave;te<img
						src="${cdn1RootContext}/elements/includes/fusion/css/ico/ico_helper.png"
						class="tip" alt="aide" />
				</h2>
				</p>
				<p>
					<form:hidden path="secretQuestion" />
					<strong>${resetPwdForm.secretQuestion}</strong> <br />
					<form:input path="answer" cssClass="text" maxlength="25" size="25" />
				</p>
				<p>
					<a href="oubliQuestionSecrete.action?urlRetour=${urlRetour}"
						class="arrowO">Je ne me souviens pas de la r&eacute;ponse de
						ma question secr&egrave;te</a>
				</p>
				<c:if test='${!(resetPwdForm.notificationView eq "none")}'>
			</fieldset>
		</c:if>
		</c:if>

		<c:if test="${!empty (resetPwdForm.notificationView)}">
			<form:hidden path="notificationView" />
			<form:hidden path="emailContactMasked" />

			<c:if test='${!(resetPwdForm.notificationView eq "none")}'>
					<h2>
						<c:if test="${!empty (resetPwdForm.secretQuestion)}">
							<span>2.</span>
						</c:if>
						Comment voulez-vous recevoir votre nouveau mot de passe ?
					</h2>
					<div class="cadre">
						<img src="../img/fleche.png" /> Votre adresse email et votre
						numéro de mobile<br> sont partiellement masqués pour
						préserver la<br> confidentialité de vos informations
						personnelles
					</div>
					<c:if test='${(resetPwdForm.notificationView eq "email")}'>
						<form:hidden path="notification"></form:hidden>
								Par email sur votre adresse<label
							id="emailContactMaskedLabel">${resetPwdForm.emailContactMasked}</label>
					</c:if>
					<c:if test='${(resetPwdForm.notificationView eq "all")}'>
							<form:radiobutton id="email" path="notification" value="EMAIL" checked="true" cssStyle="float:left" />
							<label for="email">Par email sur votre adresse<br/>
									<strong>${resetPwdForm.emailContactMasked}</strong>
							</label><br/><br/>
							<form:radiobutton id="sms" path="notification" value="SMS" cssStyle="float:left" />
							<label for="sms">Par SMS au numéro<br/>
								<strong>${resetPwdForm.msisdnMasked}</strong>	
							</label>
					</c:if>
			</c:if>
		</c:if>

		<c:if test='${resetPwdForm.notificationView eq "none"}'>
			<form:hidden path="notification" />
		</c:if>

		<div class="buttons">
			<a href="identifiant.action" id="retourBouton" name="retourBouton"
				class="myosotis-btn myosotis-btn-inverse myosotis-btn-simple">Annuler</a>
			<button type="submit" name="validerQSBouton" id="validerQSBouton"
				class="myosotis-btn myosotis-btn-simple">
				<c:out value="${param['BTN_MDP4_7']}" default="Continuer" />
			</button>
		</div>

		<c:if test='${resetPwdForm.notificationView eq "none"}'>
			</fieldset>
		</c:if>
	</form:form>

	<!-- /contenu custom -->
</div>

<jsp:include page="../common/footerReinitMdp.jsp" />