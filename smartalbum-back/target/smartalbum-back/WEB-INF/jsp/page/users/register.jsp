<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>


<link rel="stylesheet" type="text/css"
	href="<c:url value='/css/infos_persos.css' />" media="screen" />
<script type="text/javascript">
	var __host = "<c:url value='/' />";
	if (__host.indexOf(";") != -1) {
		__host = __host.substr(0, __host.indexOf(";"));
	}
</script>
<script type="text/javascript"
	src="<c:url value='/js/personal-information.js' />"></script>
<div id="col_gauche">

	<div class="clearer"></div>
	<div class="infos_persos">
		<div class="titre">Renseignez vos informations personnelles</div>

		<div class="form">

			<form
				action="${pageContext.request.contextPath}/usersController/upload.html"
				method="post" enctype="multipart/form-data">
				<h4>Téléchargez un avatar :</h4>
				<br />
				<div>
					<span><strong>Photo à télécharger</strong></span> <span><input
						type="file" name="file" /><span> <span><input
								type="submit" name="submit" value="valider cette photo" /><span>
				</div>
			</form>

			<form:form modelAttribute="infosPersoForm" id="coordonnees"
				name="coordonnees" method="post"
				action="${pageContext.request.contextPath}/usersController/create-account.html">

				<div style="border: 1px solid green; background-color: white;">
					<c:choose>
						<c:when test="${infosPersoForm.avatarUploaded}">
							<a href="#" style="float: right; margin-top: 8px;"onclick="javascript:return false;">
								<img id='userAvatar' src="<c:url value='/usersController/paintAvatarImage.html'/>" alt="" width="320px" height="340px" title="Grande image" alt="Image non trouvée" align="absmiddle" class="rotableImg"/>		
							</a>
						</c:when>
						<c:otherwise>
							<a href="#" style="float: right; margin-top: 8px;" onclick="javascript:return false;">
								<img src="<c:url value='/img/defaultavatar.jpg'/>">
							</a>
						</c:otherwise>
					</c:choose>

					<p id="check_login" class="champ_obligatoire_info">
						<label>Login* : </label><br />
						<form:input path="username" name="username" id="username"
							class="medium_text" maxlength="20" />
						<form:errors path="username" cssClass="error" />
						<span id="msg-login-obligatioire" class="fieldInteraction">
							Ce champ est obligatoire.<br />Merci de le renseigner.
						</span>
					</p>
					<p id="check_passwd" class="champ_obligatoire_info">
						<label>Mot de pass* : </label><br />
						
						<form:password path="password" class="medium_text" maxlength="20"/>
						<form:errors path="password" cssClass="error" />
						<span id="msg-passwd-obligatioire" class="fieldInteraction">
							Ce champ est obligatoire.<br />Merci de le renseigner.
						</span>
					</p>
					<p id="check_confirmpasswd" class="champ_obligatoire_info">
						<label>Confirmation du mot de pass* : </label><br />
						
						<form:password path="confirmPassword" class="medium_text" maxlength="20"/>
						<form:errors path="confirmPassword" cssClass="error" />
						<span id="msg-confirmPassword-obligatioire"
							class="fieldInteraction"> Ce champ est obligatoire.<br />Merci
							de le renseigner.
						</span>
					</p>
					<FIELDSET id="passwordInfos" style="width: 100%; border-color: white; border-style: outset">
          		      <LEGEND><font style="TEXT-DECORATION: none; COLOR: #cc3300;">&nbsp;ATTENTION&nbsp;!&nbsp;</font></LEGEND> 
                         &nbsp;&nbsp;Le&nbsp;mot&nbsp;de&nbsp;passe&nbsp;doit&nbsp;contenir&nbsp;au&nbsp;moins&nbsp;:<BR/>
                         &nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;6&nbsp;charactères<BR/>
                         &nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;une&nbsp;lettre&nbsp;majuscule<BR/>
                         &nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;un&nbsp;caractère&nbsp;spéciale&nbsp;(&nbsp;!@#$%^&*(){}:;<>,.?/\\&nbp;)<BR/>
                         &nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;un&nbsp;nombre&nbsp;
                        </FIELDSET>
                        <br/>
				</div>
				
				<p id="check_civility" class="champ_obligatoire_civ">
					<label> Civilit&eacute;* :</label> <br>
					<form:radiobutton value="M" path="civ" name="civ" id="monsieur" />
					<label for="monsieur">Monsieur</label>
					<form:radiobutton value="MME" path="civ" name="civ" id="madame" />
					<label for="madame"> Madame</label>
					<form:radiobutton value="MLLE" path="civ" name="civ"
						id="mademoiselle" />
					<label for="mademoiselle">Mademoiselle</label> <span
						id="msg-civ-obligatioire" class="fieldInteraction">Ce champ
						est obligatoire.<br />Merci de le renseigner.
					</span>
				</p>
				<p id="check_name" class="champ_obligatoire_info">
					<label>Nom* : </label><br />
					<form:input path="nom" name="nom" id="nom" class="medium_text"
						maxlength="20" />
					<form:errors path="nom" cssClass="error" />
					<span id="msg-name-obligatioire" class="fieldInteraction">
						Ce champ est obligatoire.<br />Merci de le renseigner.
					</span>
				</p>
				<p id="check_fistname" class="champ_obligatoire_info">
					<label>Pr&eacute;nom* :</label><br />
					<form:input path="prenom" name="prenom" id="prenom"
						class="medium_text" maxlength="20" />
					<form:errors path="prenom" cssClass="error" />
					<span id="msg-fistname-obligatioire" class="fieldInteraction">
						Ce champ est obligatoire.<br />Merci de le renseigner.
					</span>
				</p>
				<div id="champ_obligatoire_ville">
					<div id="affichagecommentaire">
						<p id="check_address" class="champ_obligatoire_info">
							<label>Adresse* :</label><br />
							<form:input path="adresse" name="adresse" maxlength="25"
								id="adresse" class="large_text" />
							<form:errors path="adresse" cssClass="error" />
							<span id="msg-address-obligatioire" class="fieldInteraction">
								L'adresse doit comporter entre 5 et 25 caractères.<br />Merci
								de le renseigner.
							</span> <span id="commentaires-adresse" class="fieldCom">Num&eacute;ro,
								type de voie, nom de la voie.</span>
						</p>
					</div>
					<div id="affichagecommentaire">
						<p id="complement_address" class="champ_obligatoire_info">
							<label>Adresse (Complément) :</label><br />
							<form:input name="complementAdresse" path="complementAdresse"
								id="complementadresse" class="large_text" maxlength="25" />
							<form:errors path="complementAdresse" cssClass="error" />
						</p>
					</div>
					<div id="affichagecommentaire">
						<p id="check_zipcode" class="champ_obligatoire_info">
							<label>Code Postal* :</label><br />
							<form:input path="codePostal" name="codePostal" maxlength="5"
								id="codePostal" class="small_text" />
							<form:errors path="codePostal" cssClass="error" />
							<span id="msg-zipcode-obligatioire" class="fieldInteraction">
								Ce champ est obligatoire.<br />Merci de le renseigner.
							</span> <span id="commentaires-cp" class="fieldCom">5 chiffres.
								Exemple : 44000.</span> <span id="codePostalInvalide"
								class="fieldInteraction"> Votre code postal n'est pas
								reconnu.<br />Veuillez entrer un code postal valide.
							</span>
						</p>
					</div>

					<div id="affichagecommentaire">
						<p id="check_city" class="champ_obligatoire_info">
							<label>Ville* :</label><br>
							<form:input path="ville" name="ville" id="ville"
								class="large_text" maxlength="20" />
							<form:errors path="ville" cssClass="error" />
							<span id="msg-city-obligatioire" class="fieldInteraction">
								Ce champ est obligatoire.<br />Merci de le renseigner.
							</span>
						</p>
						<p id="txt_champ_obligatoire-ville"
							class="txt_champ_obligatoire-ville">
							Après vérification votre adresse semble comporter une erreur.<br>
							Merci de la valider<a id="listAddress" href="javascript:void(0)">
								en cliquant ici.</a>
						</p>
					</div>
				</div>

				<div id="affichagecommentaire">
					<p id="check_phone" class="champ_obligatoire_info">
						<label>T&eacute;l&eacute;phone* :</label><br />
						<form:input path="telephone" name="telephone" id="telephone"
							maxlength="10" class="small_text" />
						<form:errors path="telephone" cssClass="error" />
						<span id="msg-phone-obligatioire" class="fieldInteraction">
							Ce champ est obligatoire.<br />Merci de le renseigner.
						</span> <span id="commentaires-tel" class="fieldCom">10 chiffres.
							Exemple : 0123456789.</span> <span id="telephonErrone"
							class="fieldInteraction">Cette information semble erronée.
							Merci de la modifier</span>
					</p>
				</div>
				<p id="check_email" class="champ_obligatoire_info">
					<label>E-mail* :</label><br />
					<form:input path="email" name="email" id="email"
						class="medium_text" maxlength="80" />
					<form:errors path="email" cssClass="error" />
					<span id="msg-email-obligatioire" class="fieldInteraction">
						Ce champ est obligatoire.<br />Merci de le renseigner.
					</span> <span id="mailErrone" class="fieldInteraction">merci de
						saisir une adresse mail valide(exemple@exemple.com)</span>
				</p>

				<div class="txt_gris">Cet e-mail sera utilis&eacute; afin de
					confirmer votre inscription sur SMARTALBUM</div>
				<p class="champ_obligatoire_info" style="padding-left: 0px;">
					<form:checkbox value="newsletter" path="newsletter"
						name="newsletter" id="newsletter" />
					<label for="">Je ne souhaite pas recevoir des informations
						sur les offres commerciales de SMARTALBUM par e-mail </label>
				</p>

				<p id="check_date_naissance" class="champ_obligatoire_info">
					<label>Date de naissance* :</label><br />
					<form:select name="jour" path="jour" id="jour">
						<form:option value="jour">Jour</form:option>
						<c:forEach begin="1" end="31" step="1" varStatus="compteurDate">
							<c:choose>
								<c:when test="${compteurDate.index eq infosPersoForm.jour}">
									<option selected='selected' value="${compteurDate.index}">${compteurDate.index}</option>
								</c:when>
								<c:otherwise>
									<option value="${compteurDate.index}">${compteurDate.index}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</form:select>
					<form:select path="mois" name="mois" id="mois">
						<c:forEach items="${infosPersoForm.months}" var="month"
							varStatus="compteurMonths">
							<c:choose>
								<c:when test="${infosPersoForm.departement eq month}">
									<option selected='selected' value="${month}">${month}</option>
								</c:when>
								<c:otherwise>
									<option value="${month}">${month}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</form:select>
					<form:select name="annee" path="annee" id="annee">
						<form:option value="annee">Année</form:option>
						<c:forEach begin="1909" end="2014" step="1"
							varStatus="compteurAnnee">
							<c:choose>
								<c:when test="${compteurAnnee.index eq infosPersoForm.annee}">
									<option selected='selected' value="${compteurAnnee.index}">${compteurAnnee.index}</option>
								</c:when>
								<c:otherwise>
									<option value="${compteurAnnee.index}">${compteurAnnee.index}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</form:select>
					<span id="birthdate_error_msg" class="fieldInteraction"></span>
					<form:hidden id="dateNaissance" name="dateNaissance"
						path="dateNaissance" />
				</p>
				<p id="check_departement" class="champ_obligatoire_info">
					<label>D&eacute;partement de naissance * : </label><br />
					<form:select path="departement" name="departement" id="departement"
						class="select_dep">
						<form:option value="selection">- - - - - - S&eacute;lectionner - - - - - - </form:option>
						<c:forEach items="${infosPersoForm.allDepartment}"
							var="departement" varStatus="compteurMonths">
							<c:choose>
								<c:when test="${infosPersoForm.departement eq departement}">
									<option selected='selected' value="${departement}">${departement}</option>
								</c:when>
								<c:otherwise>
									<option value="${departement}">${departement}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<form:option value="97">97 Dom Tom</form:option>
						<form:option value="99">99 étranger</form:option>
					</form:select>
					<span id="msg-departement-obligatioire" class="fieldInteraction">
						Ce champ est obligatoire.<br />Merci de le renseigner.
					</span>
				</p>

				<!-- 				<div class="btn_valider_infos_persos"> -->
				<input type="submit" name="valider_infos_persos"
					id="valider_infos_persos" value="Valider" />
				<!-- 				</div> -->
				<p class="champs_obligatoire">
					<br /> <br />* Champs obligatoires
				</p>

			</form:form>
		</div>
	</div>
</div>