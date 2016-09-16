<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><decorator:title default="Smart Album - Back Office"></decorator:title></title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta id="viewport" name="viewport" content="width=device-width, maximum-scale=1.0, minimum-scale=1.0, inital-scale=1.0" />
		<c:set var="user" value="${SPRING_SECURITY_CONTEXT.authentication.principal}" scope="request"/>
		
		<script src="<c:url value='/templates/green/js/jquery.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/templates/green/js/functions.js'/>" type="text/javascript"></script>
		
		<link rel="stylesheet" href="<c:url value='/templates/green/css/base.css'/>" type="text/css" media="all" />
	<link rel="stylesheet" href="<c:url value='/templates/green/css/green.css'/>" type="text/css" media="all" />
		<!-- ##### css ##### -->
		<link rel="stylesheet" type="text/css" href="<c:url value='/css/smartAlbum.css' />" />
		<link rel="stylesheet" type="text/css" href="<c:url value='/css/photoalbum.css' />" />
		<link rel="shortcut icon" href="<c:url value='/templates/netbased/css/images/favicon.ico?cb=1'/>" />
		<link rel="stylesheet" href="<c:url value='/templates/netbased/css/style.css'/>" type="text/css" media="all" />
        
	<script type="text/javascript">
			var __host = "<c:url value='/' />";
			if (__host.indexOf(";") != -1) {
				__host = __host.substr(0, __host.indexOf(";"));
			}
		</script>
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
		
		<!-- ##### Footer (bas de page) ##### -->
		<decorator:body/>
<!-- 		<div class="footer-push"></div> -->
		<!-- Footer -->
		<div class="center">
			<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
			</div>
			</section>
	</body>
</html>