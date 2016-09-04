<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:choose>
	<c:when test='${resetPwdForm.finalNotification eq "SMS" }'>
		<c:set var="tag_pagename_value" value="Parcours/Securite/OubliMotDePasse/LayerConfirmationEnvoiSMS"/>
	</c:when>
	<c:otherwise>
		<c:set var="tag_pagename_value" value="Parcours/Securite/OubliMotDePasse/LayerConfirmationEnvoiMailDirect"/>
	</c:otherwise>
</c:choose>
<jsp:include page="../../common/headerLayerReinitMdp.jsp">
	<jsp:param value="${tag_pagename_value}" name="tag_pagename"/>
</jsp:include>

<body style="margin: 0px;">
	<div id="lsw">
		<div class="sfrDom layer">
		<h3 style="margin-bottom: 20px;">Votre nouveau mot de passe a bien &eacute;t&eacute; envoy&eacute;</h3>

		Vous le recevrez dans quelques instants
		  <c:choose>
            	<c:when test='${resetPwdForm.finalNotification eq "SMS" }'>
					 par SMS au numéro <strong>${resetPwdForm.finalAddressMasked}</strong><br>
            	</c:when>
            	<c:otherwise>
                     &agrave; votre adresse email <strong>${resetPwdForm.emailContactMasked}</strong>.
                </c:otherwise>
		  </c:choose>
		  		 	
		<form:form  id="resetPwdForm" cssClass="information" cssStyle="margin-top: 20px;" >
				<img class="fanion" src="../img/fanion.png" style="margin:0">
				Si vous consultez vos emails SFR depuis votre mobile, ou votre PC via un logiciel (Outlook, Thunderbird...), <strong>remplacez l'ancien mot de passe par celui-ci.</strong><br/>
				Si vous utilisez une application SFR sur smartphone, vous pouvez &ecirc;tre amen&eacute; &agrave; remplacer l'ancien mot de passe par celui-ci.
		  				
		</form:form>
	<script type="text/javascript" src="${cdnStaticContext}/stats/footer.js"></script>
	</div></div>
</body>
</html>
