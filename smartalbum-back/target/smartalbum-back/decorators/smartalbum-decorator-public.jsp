<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@ include file="/WEB-INF/jsp/common/include.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><decorator:title default="Smart Album - Back Office"></decorator:title></title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta id="viewport" name="viewport" content="width=device-width, maximum-scale=1.0, minimum-scale=1.0, inital-scale=1.0" />
		<c:set var="user" value="${SPRING_SECURITY_CONTEXT.authentication.principal}" scope="request"/>
        <!-- Add jQuery library -->
        <script language="javascript" type="text/javascript" src="<c:url value='/js/jquery/jquery-1.9.1.min.js'/>"></script>
        <!-- ##### custom js ##### -->
		<script src="<c:url value='/js/default.js' />" type="text/javascript"></script>
		
		<!-- <file upload && for good look and feelbootstrap -->
		<link rel="stylesheet" type="text/css" href="<c:url value='/js/jquery/fancyapps-fancyBox-18d1712/source/jquery.fancybox.css' />" />
		<script type="text/javascript" src="<c:url value='/js/jquery/fancyapps-fancyBox-18d1712/source/jquery.fancybox.js'/>"></script>
		<!-- Tabs -->
		<script src="<c:url value='/js/jquery/jquery-ui-1.10.3/jquery-ui-1.10.3.custom.min.js'/>"></script>
		<!-- jquery simple modal -->
		<script type="text/javascript" src="<c:url value='/js/jquery/modal/jquery.simplemodal.js'/>"></script>
        <!-- ##### css ##### -->
		<link rel="stylesheet" type="text/css" href="<c:url value='/css/smartAlbum.css' />" />
		<link rel="stylesheet" type="text/css" href="<c:url value='/css/photoalbum.css' />" />
	<!-- Autres UX FAbric -->
		<title>UX Fabric</title>
		<link rel="shortcut icon" type="image/x-icon" href="<c:url value='/templates/uxfabric/css/images/favicon.ico'/>" />
		<link rel="stylesheet" href="<c:url value='/templates/uxfabric/css/base.css'/>" type="text/css" media="all" />
		<link rel="stylesheet" href="<c:url value='/templates/uxfabric/css/default.css'/>" type="text/css" media="all" />
		<link rel="stylesheet" href="<c:url value='/templates/netbased/css/style.css'/>" type="text/css" media="all" />
	<!-- 	<link rel="stylesheet" href="css/fancybox.css" type="text/css" media="all" /> -->
	<!-- 	<script src="js/jquery.js" type="text/javascript"></script> -->
		<script src="<c:url value='/templates/uxfabric/js/selectivizr.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/templates/uxfabric/js/modernizr.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/templates/uxfabric/js/simpleFade.js'/>" type="text/javascript"></script>
	<!-- 	<script src="js/fancybox.js" type="text/javascript"></script> -->
		<script src="<c:url value='/templates/uxfabric/js/livevalidation.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/templates/uxfabric/js/tweet.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/templates/uxfabric/js/functions.js'/>" type="text/javascript"></script>
		
		 <script language="javascript" type="text/javascript">			
			function logOut(){
				document.location.href = "<c:url value='/authentification/logout.html' />";
			}
		</script>
		<decorator:head />
		
	</head>
	<body>
		<!-- header -->
		<c:set var="currentURL" value="<c:url value='/'/>"/>
		<%@ include file="/WEB-INF/jsp/page/home/header_green.jsp"%>
		<section id="page">
		<!-- Header -->
		<header>
			<div class="shell">
				<!-- Logo -->
<%-- 				<h1 id="logo"><a href="#"><img src="<c:url value='/templates/green/css/images/logo.png'/>" width="148" height="52" alt="" /></a></h1> --%>
				<!-- END Logo -->
				<!-- Navigation-->
				<nav>
					<ul>
						<%@ include file="/WEB-INF/jsp/common/nav_option.jsp"%>
					</ul>
				</nav>
				<!-- Navigation -->
			</div>
		</header>
		<!-- Menu gauche -->
		<%@ include file="/WEB-INF/jsp/common/left-menu.jsp" %>
		<!-- ##### Footer (bas de page) ##### -->
		<decorator:body/>
		<c:if test="${not fn:contains(pageContext.request.requestURL,'/home')}">
<!-- 			<div id="footer-push" class="notext">&nbsp;</div> -->
		</c:if>
		<!-- Footer -->
		<div class="center">
			<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
			</div>
	</body>
</html>