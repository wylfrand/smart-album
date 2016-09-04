<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@ include file="/WEB-INF/jsp/common/include.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><decorator:title default="Smart Album - Back Office"></decorator:title></title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta id="viewport" name="viewport" content="width=device-width, maximum-scale=1.0, minimum-scale=1.0, inital-scale=1.0" />
		<meta name="keywords" content="album,photos, photo, ldap, identity" />
		<meta name="description" content="" />
		<meta name="robots" content="index, follow" />
		<meta charset="utf-8" />
        <!-- Add jQuery library -->
<%--         <script language="javascript" type="text/javascript" src="<c:url value='/js/jquery/jquery-1.9.1.min.js'/>"></script> --%>
	<!-- Login decorator -->
		<title>Login decorator</title>
		<script src="<c:url value='/templates/login/js/jquery.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/templates/login/js/jquery.query-2.1.7.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/templates/login/js/rainbows.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/js/personal-information.js' />" type="text/javascript"></script>
		<link rel="stylesheet" href="<c:url value='/templates/login/css/style.css'/>" type="text/css" media="all" />
		<link rel="stylesheet" type="text/css" href="<c:url value='/css/infos_persos.css' />" media="screen" />
		
<!-- jquery simple modal -->
<script type="text/javascript"
	src="<c:url value='/js/jquery/modal/jquery.simplemodal.js'/>"></script>
		
		<script language="javascript" type="text/javascript"
	src="<c:url value='/js/jquery/myuploadfunction.js'/>"></script>
	
		<!-- jquery simple modal -->
		<!-- Intégration stickyfloat -->
		 <script language="javascript" type="text/javascript">			
			function logOut(){
				document.location.href = "<c:url value='/authentification/logout.html' />";
			}
		</script>
		<decorator:head />
	</head>
	<body>
		<!-- ##### Footer (bas de page) ##### -->
		<decorator:body/>
	</body>
</html>