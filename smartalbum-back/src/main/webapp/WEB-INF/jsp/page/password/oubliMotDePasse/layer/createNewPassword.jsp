<%-- <jsp:include page="../../common/headerLayerReinitMdp.jsp"> --%>
<%-- 	<jsp:param value="Parcours/Securite/OubliMotDePasse/LayerChoixEnvoi" name="tag_pagename"/> --%>
<%-- </jsp:include> --%>
  <script type="text/javascript">
	$(document).ready(function()  {
     $('#resetPwdFormQS').submit(function()
		{
			$errors = $(this).find('label.error:not(.validate)');
			$errorBox = $('div.errorForm');
			$('div.errorForm > ul').remove();
			if ($errors.length > 0)
			{
				$errors.each(function(i, e)
				{
					if (i == 0)
					{
						$errorList = $('<ul/>');
						$errorBox.find('ul').remove().end().append($errorList);
					}
					
					$errorList.append(
						$('<li/>').append
						(
							$('<strong/>').text('Erreur ' + (i+1) + ' : ')
							).append(
								$(e).text()
									)
						);
				});
				$errorBox.show();
			}
			else
			{
				$errorBox.hide();
			}
	    });
		
   });
  </script>
  <div id="createNewPassword" style="display: none;">
		   <h3>Cr&eacute;er votre nouveau mot de passe</h3>
		   <h4>Comment voulez-vous recevoir votre nouveau mot de passe ?</h4>
           <form:form id="resetPwdFormQS" name="resetPwdFormQS" commandName="resetPwdForm" action="confirmation.action?layerMode=true">
              	    <c:set var="errorsBinding"><form:errors path="*"/></c:set>
              		<div class="errorForm" style="<c:if test='${empty (errorsBinding)}'>display:none;</c:if>">
					    <spring:hasBindErrors name="resetPwdFormQS">
							<ul>
								<c:forEach var="error" items="${errors.allErrors}">
									<li>
										<c:if test='${empty (error.defaultMessage)}'>
											<spring:message code="${error.code}"></spring:message>
										</c:if>
										<c:if test='${!empty (error.defaultMessage)}'>
											${error.defaultMessage}
										</c:if>
									</li>
								</c:forEach>
							</ul>
						</spring:hasBindErrors>
              		</div>

					<form:hidden path="identifiant" />
					<form:hidden path="utilisateurFixe" />
					<form:hidden path="msisdn" />
					<form:hidden path="ascUser"/>
					<form:hidden path="secretQuestion" />
					<form:hidden path="answer" />
					<form:hidden path="notificationView" />
					<form:hidden path="emailContactMasked" />

					<c:if test="${!empty (resetPwdForm.secretQuestion)}">
		                  <fieldset>
											<p>
                        						<label> <c:if test="${!empty (resetPwdForm.notificationView)}"><span class="lbI">1.</span></c:if>
                        							Veuillez r&eacute;pondre &agrave; votre question secr&egrave;te<img src="${cdn1RootContext}/elements/includes/fusion/css/ico/ico_helper.png" class="tip" alt="aide" />
                        						</label>
                     						</p>
											<p>
												<strong>${resetPwdForm.secretQuestion}</strong> <br/>
												<form:input path="answer" cssClass="type_text" maxlength="25" size="25"  />
											</p>
											<p>
											<a href="oubliQuestionSecrete.action" class="arrowO" >Je ne me souviens pas de la r&eacute;ponse de ma question secr&egrave;te</a>
                      						</p>
                      	</fieldset>
	                </c:if>

					<c:if test="${!empty (resetPwdForm.notificationView)}">

					<c:if test='${!(resetPwdForm.notificationView eq "none")}'>

							<c:if test='${(resetPwdForm.notificationView eq "email")}'>
									<label for="emailContactMaskedBubbleQS">Par email sur votre adresse<br/>
									<strong>${resetPwdForm.emailContactMasked}</strong></label>
								    <div class="bulle moyen">
										<img class="fleche" src="//static.s-sfr.fr/media/fleche-bulle-1.png" />
										Votre adresse email est  partiellement masqu&eacute;e pour pr&eacute;server la confidentialit&eacute; de vos informations personnelles
									</div>					
									
							</c:if>
							<c:if test='${(resetPwdForm.notificationView eq "all")}'>
										<form:radiobutton id="notification" name="moyen" path="notification" value="EMAIL" checked="true" />
  										<label for="notification">
  											Par email sur votre adresse<br>
											<strong id="emailContactMaskedBubbleQS">${resetPwdForm.emailContactMasked}</strong>
										</label>
										<div class="sep"></div>
                        				<form:radiobutton id="notification" name="moyen" path="notification" value="SMS"  />
  										<label for="notification">
  											Par SMS au num&eacute;ro<br/>
											<strong>${resetPwdForm.msisdnMasked}</strong>
										</label>
							<div class="bulle moyen">
								<img class="fleche" src="//static.s-sfr.fr/media/fleche-bulle-1.png" />
								Votre adresse email et votre num&eacute;ro de mobile sont partiellement masqu&eacute;s pour pr&eacute;server la confidentialit&eacute; de vos informations personnelles
							</div>					
							</c:if>
					</c:if>
					</c:if>
					<br><button href="#" class="myosotis-btn myosotis-btn-simple submit">Continuer</button>

         </form:form>
         </div>
