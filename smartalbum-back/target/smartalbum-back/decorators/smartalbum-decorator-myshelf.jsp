<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jsp/common/include.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><decorator:title default="Smart Album - Back Office"></decorator:title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta id="viewport" name="viewport"
	content="width=device-width, maximum-scale=1.0, minimum-scale=1.0, inital-scale=1.0" />
<c:set var="user"
	value="${SPRING_SECURITY_CONTEXT.authentication.principal}"
	scope="request" />
<!-- Add jQuery library -->
<script language="javascript" type="text/javascript" src="<c:url value='/js/jquery/jquery-1.9.1.min.js'/>"></script>

	<!-- WOW Slider -->
<link rel="stylesheet" type="text/css"
	href="<c:url value='/js/wow_slider_engine/style.css' />" />
<!-- ##### custom js ##### -->
<script src="<c:url value='/js/default.js' />" type="text/javascript"></script>
<!-- <file upload && for good look and feelbootstrap -->
	<script language="javascript" type="text/javascript" src="<c:url value='/js/jquery/jquery-ui-1.10.3/ui/jquery.ui.core.js'/>"></script>
	<script language="javascript" type="text/javascript" src="<c:url value='/js/jquery/jquery-ui-1.10.3/ui/jquery.ui.widget.js'/>"></script>
	<script language="javascript" type="text/javascript" src="<c:url value='/js/jquery/jquery-ui-1.10.3/ui/jquery.ui.mouse.js'/>"></script>
	<script language="javascript" type="text/javascript" src="<c:url value='/js/jquery/jquery-ui-1.10.3/ui/jquery.ui.draggable.js'/>"></script>
	<script language="javascript" type="text/javascript" src="<c:url value='/js/jquery/jquery-ui-1.10.3/ui/jquery.ui.droppable.js'/>"></script>

<script language="javascript" type="text/javascript"
	src="<c:url value='/js/jquery/jquery.iframe-transport.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<c:url value='/js/jquery/jquery.fileupload.js'/>"></script>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/js/jquery/bootstrap/css/bootstrap.css' />" />
<script language="javascript" type="text/javascript"
	src="<c:url value='/js/jquery/bootstrap/js/bootstrap.min.js'/>"></script>

<!-- Photo Galery -->
<link rel="stylesheet" type="text/css"
	href="<c:url value='/js/jquery/fancyapps-fancyBox-18d1712/source/jquery.fancybox.css' />" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/css/sortable_gallery.css' />" />
<script type="text/javascript"
	src="<c:url value='/js/jquery/fancyapps-fancyBox-18d1712/source/jquery.fancybox.js'/>"></script>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/css/pagination.css' />" />

<!-- we code these -->
<link rel="stylesheet" type="text/css"
	href="<c:url value='/css/dropzone.css' />" />
	
<!-- <script language="javascript" type="text/javascript" -->
<%-- 	src="<c:url value='/js/jquery/myuploadfunction.js'/>"></script> --%>
<script language="javascript" type="text/javascript"
	src="<c:url value='/js/lib/lib.common.js'/>"></script>
	<script language="javascript" type="text/javascript"
	src="<c:url value='/js/lib/lib.myshelves.js'/>"></script>

<!-- jquery simple modal -->
<script type="text/javascript"
	src="<c:url value='/js/jquery/modal/jquery.simplemodal.js'/>"></script>

<!-- ##### css ##### -->
<link rel="stylesheet" type="text/css"
	href="<c:url value='/css/smartAlbum.css' />" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/css/photoalbum.css' />" />

<!-- Blog -->
<link rel="shortcut icon"
	href="<c:url value='/templates/netbased/css/images/favicon.ico?cb=1'/>" />
<link rel="stylesheet"
	href="<c:url value='/templates/netbased/css/style.css'/>"
	type="text/css" media="all" />
<link rel="stylesheet"
	href="<c:url value='/templates/green/css/base.css'/>" type="text/css"
	media="all" />

<!--[if IE 6]>
		<link rel="stylesheet" href="<c:url value='/templates/netbased/css/ie6.css'/>" type="text/css" media="all" />
	<![endif] -->
<script language="javascript" type="text/javascript"
	src="<c:url value='/js/validate/jquery.validate.js'/>"></script>

<script src="<c:url value='/templates/netbased/js/jquery.jcarousel.js'/>"
	type="text/javascript" charset="utf-8"></script>

<script
	src="<c:url value='/templates/netbased/js/jquery.easing.1.3.js'/>"
	type="text/javascript"></script>
<script src="<c:url value='/templates/netbased/js/swfobject.js'/>"
	type="text/javascript" charset="utf-8"></script>
<script src="<c:url value='/templates/netbased/js/functions.js'/>"
	type="text/javascript" charset="utf-8"></script>

<!-- Intégration stickyfloat -->
<script type="text/javascript"
	src="<c:url value='/js/stickyfloat.js' />"></script>
<%-- 	<link href="<c:url value='/js/responsiveTabs/template1/responsive-tabs.css' />" rel="stylesheet" type="text/css" /> --%>
<%--     <script src="<c:url value='/js/responsiveTabs/responsive-tabs.js' />" type="text/javascript"></script> --%>


<script type="text/javascript">
	var __host = "<c:url value='/' />";
	if (__host.indexOf(";") != -1) {
		__host = __host.substr(0, __host.indexOf(";"));
	}
</script>
<script language="javascript" type="text/javascript">
	function logOut() {
		document.location.href = "<c:url value='/authentification/logout.html' />";
	}
</script>
<decorator:head />
</head>
<body>
	<!-- header -->
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
<%-- 		<c:if test="${not DEVICE_IS_MOBILE}"> --%>
<%-- "<c:url value='/chatController/showChatRoom.html' /> --%>
<!-- onclick="javascript:showCurrentChatRoom();" -->
<%-- href="<c:url value='/chatController/showChatRoom.html' />" --%>
			<li
				<c:if test="${currentPage == 'ACCUEUIL'}">class="active"</c:if>>
				<a id="chatRoom"  onclick="javascript:showCurrentChatRoom();"> Chat room </a>
			</li>
<%-- 			</c:if> --%>
			<%@ include file="/WEB-INF/jsp/common/nav_option.jsp"%>
		</ul>
		</nav>
		<!-- Navigation -->
	</div>
	</header> <!-- Menu gauche --> 
	<div style="display : none" id="fullImgToPrint"></div>
	<c:if test="${not fn:contains(pageContext.request.requestURL,'/create-account') and not DEVICE_IS_MOBILE}">
		<%@ include file="/WEB-INF/jsp/common/left-menu.jsp"%>
	</c:if> <!-- ##### Footer (bas de page) ##### --> <!-- Titre commun à toutes les pages -->
	<div id="titre"></div>
	<c:choose>
		<c:when test="${DEVICE_IS_MOBILE }">
			<div>
				<decorator:body />
				<div id="hr"></div>
			</div>
		</c:when>
		<c:otherwise>
			
				<div class="wrap">
				<div class="menu">
						<c:if test="${not fn:contains(pageContext.request.requestURL,'/imagesController/showImageDetails')}">
							<%@include file="/WEB-INF/jsp/common/facturette.jsp"%>
						</c:if>
						<script type="text/javascript">
						    $(document).ready(function() {
							$('.menu').stickyfloat({
							    duration : 300});
						    });
						</script>
					</div>
					<decorator:body>
					<div class="scroller_content">
					</div>
					</decorator:body>
					
				
				<div id="hr"></div>
			</div>
		</c:otherwise>
	</c:choose>
	<div class="clearer"></div>
		<div style="float: right"></div>
	<!-- Footer -->
	<div class="center">
		<%@ include file="/WEB-INF/jsp/common/footer.jsp"%>
	</div>
	</section>
</body>
</html>