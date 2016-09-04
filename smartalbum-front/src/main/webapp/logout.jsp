<%@ page pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>D&eacute;connexion</title>
		<META http-equiv="Cache-Control" content="no-cache">
	</head>
	<body>
		<% 	
			if (session != null) {
				session.invalidate();
			}
		%>
	</body>
</html>