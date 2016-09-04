<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@ include file="/WEB-INF/jsp/common/include.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><decorator:title default="Smart Album - Back Office"></decorator:title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta id="viewport" name="viewport"
	content="width=device-width, maximum-scale=1.0, minimum-scale=1.0, inital-scale=1.0" />
<c:set var="user" value="${SPRING_SECURITY_CONTEXT.authentication.principal}" scope="request" />

<!-- Add jQuery library -->
<script language="javascript" type="text/javascript" src="<c:url value='/js/jquery/jquery-1.9.1.min.js'/>"></script>

<!-- ##### custom js ##### -->
<script src="<c:url value='/js/default.js' />" type="text/javascript"></script>

<!-- <file upload && for good look and feelbootstrap -->
<script language="javascript" type="text/javascript"
	src="<c:url value='/js/jquery/jquery-ui-1.10.3/ui/jquery.ui.widget.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<c:url value='/js/jquery/jquery.iframe-transport.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<c:url value='/js/jquery/jquery.fileupload.js'/>"></script>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/js/jquery/bootstrap/css/bootstrap.css' />" />
<script language="javascript" type="text/javascript"
	src="<c:url value='/js/jquery/bootstrap/js/bootstrap.min.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<c:url value='/js/validate/jquery.validate.js'/>"></script>

<!-- ##### css ##### -->
<link rel="stylesheet" type="text/css"
	href="<c:url value='/css/smartAlbum.css' />" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/css/photoalbum.css' />" />
<link rel="stylesheet" href="<c:url value='/templates/green/css/base.css'/>" type="text/css" media="all" />

<!-- Blog -->
<link rel="shortcut icon"
	href="<c:url value='/templates/netbased/css/images/favicon.ico?cb=1'/>" />
<link rel="stylesheet"
	href="<c:url value='/templates/netbased/css/style.css'/>"
	type="text/css" media="all" />

<!--[if IE 6]>
		<link rel="stylesheet" href="<c:url value='/templates/netbased/css/ie6.css'/>" type="text/css" media="all" />
	<![endif] -->
<script type="text/javascript">
	var __host = "<c:url value='/' />";
	if (__host.indexOf(";") != -1) {
		__host = __host.substr(0, __host.indexOf(";"));
	}
</script>
<script type="text/javascript"
	src="<c:url value='/js/personal-information.js' />"></script>
<script language="javascript" type="text/javascript">
	function logOut() {
		document.location.href = "<c:url value='/authentification/logout.html' />";
	}
</script>
<decorator:head />
<style >

</style>
</head>
<body>
	<!-- header -->
	<div class="wrapper">
	<c:set var="currentURL" value="<c:url value='/'/>" />
	<%@ include file="/WEB-INF/jsp/page/home/header_green.jsp"%>
	<section id="page"> <!-- Header --> <header>
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
	</header> <%-- 	    	<a href="<c:url value='/home/index.html' />"> --%> <%-- 	    		<%@ include file="/WEB-INF/jsp/common/navigation.jsp" %> --%>
	<!-- 	    		<div class="firstColumn"> --> <!-- 	    	</a> --> <!-- Menu gauche -->
	<!-- Menu gauche --> <c:if test="${not fn:contains(pageContext.request.requestURL,'/create-account')}">
		<%@ include file="/WEB-INF/jsp/common/left-menu.jsp"%>
	</c:if> <!-- ##### Footer (bas de page) ##### --> 
	<decorator:body /> 
<!-- 	<div id="footer-push" class="notext">&nbsp;</div> -->
	<!-- Footer -->
	<div class="center">
		<%@ include file="/WEB-INF/jsp/common/footer.jsp"%>
	</div></section>
	</div>
	
</body>
</html>