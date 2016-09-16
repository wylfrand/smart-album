<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<c:set var="pattern" value="dd/MM/yyyy hh:mm:ss" scope="application" />
<html>
<head>
<script type="text/javascript">
	function doAuth() {
		var login = $("#j_username").val();
		var password = $("#j_password").val();
		if(!isBlanck(login) && !isBlanck(password)){
		$.ajax({
					type : "POST",
					cache : false,
					url : "<c:url value="/authentification/action/login.html" />",
					data : "j_username=" + $("#j_username").val()
							+ "&j_password=" + $("#j_password").val(),
					beforeSend: setHeader,
					dataType: "text",
					success : function(data) {
						if (data == "true") {
							document.location.href = "<c:url value='/shelvesController/publicShelves.html' />";
						} else {
							var error = JSON.parse(data);
							alert(error.resultObject);
						}
					}
				});
		}

		return false;
	}
	
	function setHeader(xhr) {
	    xhr.setRequestHeader("Accept", "application/json");
	}
	
	function isBlanck(obj){
		return(!obj || $.trim(obj) === "");
	};
</script>
</head>

<body>
	<div id="wrapper">
		<div id="wrappertop"></div>
		<div id="wrappermiddle">
			<h2>Entrez vos identifiants</h2>
			<form id="loginForm">
				<div id="username_input">
					<div id="username_inputleft"></div>
					
					<div id="username_inputmiddle">
						 <input
							placeholder="Identifiant" type="text"
							id="j_username" name="j_username" maxlength="20"
							onkeypress="javascript:if (event.keyCode == 13){doAuth();};"
							class="credentials" /> <img id="url_user"
							src="<c:url value='/templates/login/images/mailicon.png'/>"
							alt="">
					</div>
					<div id="username_inputright"></div>
				</div>
				<div id="password_input">
					<div id="password_inputleft"></div>
					
					<div id="password_inputmiddle">
						 <input
							placeholder="Mot de passe" type="password"
							id="j_password" name="j_password" maxlength="20"
							autocomplete="off"
							onkeypress="javascript:if (event.keyCode == 13){doAuth();};"
							class="credentials" /> <img id="url_password"
							src="<c:url value='/templates/login/images/passicon.png'/>"
							alt="">
					</div>
					<div id="password_inputright"></div>
				</div>
				<div id="submit">
					<a href="#" class="huboBtnLink100" onclick="javascript:doAuth()">
						<img id="url_password" src="<c:url value='/templates/login/images/submit_hover.png'/>" alt="" id="submit1">
						</a>
				</div>
				<div id="links_left">
					<a href="#" onclick="showBlockInLayer('createNewPassword');">Forgot your Password?</a>
				</div>
				<div id="links_right">
					<a href="<c:url value='/usersController/open-creation.html'/>">Register</a>
				</div>
				<div id="links_right">
				<a href="<c:url value='/shelvesController/publicShelves.html'/>">Anonymous access</a>
				</div>
			</form>
		</div>
		<div id="wrapperbottom"></div>

		<div id="powered">
			<p>
				Powered by <a href="http://wylfrand.ddns.net:8080/opiam_demo/">OPIAM Admin Control Panel</a>
			</p>
		</div>
		<%@ include file="/WEB-INF/jsp/page/password/oubliMotDePasse/layer/createNewPassword.jsp"%>
	</div>
	
</body>
</html>