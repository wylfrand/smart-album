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
<%-- 		<link rel="stylesheet" type="text/css" href="<c:url value='/js/jquery/fancyapps-fancyBox-18d1712/source/jquery.fancybox.css' />" /> --%>
		<!-- Tabs -->
<%-- 		<script src="<c:url value='/js/jquery/jquery-ui-1.10.3/jquery-ui-1.10.3.custom.min.js'/>"></script> --%>
		<!-- jquery simple modal -->
<%-- 		<script type="text/javascript" src="<c:url value='/js/jquery/modal/jquery.simplemodal.js'/>"></script> --%>
        <!-- ##### css ##### -->
	<!-- Autres Progress -->
		<title>Progress</title>
		<link rel="shortcut icon" type="image/x-icon" href="<c:url value='/templates/progress/css/images/favicon.ico?cb=1'/>" />
			<link rel="stylesheet" href="<c:url value='/templates/progress/css/style.css'/>" type="text/css" media="all" />
			<link rel="stylesheet" href="<c:url value='/templates/progress/css/featureCarousel.css'/>" type="text/css" media="all" />
			<link rel="stylesheet" href="<c:url value='/templates/progress/js/fancybox/jquery.fancybox-1.3.1.css'/>" type="text/css" media="all" />
			<!--[if IE 6]>
				<link rel="stylesheet" href="<c:url value='/templates/progress/css/ie6.css'/>" type="text/css" media="all" />
				<script src="<c:url value='/templates/progress/js/png-fix.js'/>" type="text/javascript"></script>
			<![endif]-->
		
		<script src="<c:url value='/templates/progress/js/cufon-yui.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/templates/netbased/js/functions.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/templates/progress/js/Helvetica.font.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/templates/progress/js/jquery.easing.1.3.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/templates/progress/js/fancybox/jquery.fancybox-1.3.1.js'/>" type="text/javascript" charset="utf-8"></script>
		<script src="<c:url value='/templates/progress/js/fancybox/jquery.mousewheel-3.0.2.pack.js'/>" type="text/javascript" charset="utf-8"></script>
		<script src="<c:url value='/templates/progress/js/jquery.featureCarousel.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/templates/progress/js/jquery.jcarousel.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/templates/progress/js/jquery.tweet.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/templates/progress/js/Myriad_Pro.font.js'/>" type="text/javascript"></script>
        
		 <script language="javascript" type="text/javascript">			
			function logOut(){
				document.location.href = "<c:url value='/authentification/logout.html' />";
			}
		</script>
		<decorator:head />
	</head>

	<body>
		<div id="wrapper">
	
	<!-- Header -->
	<div id="header">
		
		<div class="center">
			
			<!-- Logo -->
			<h1 id="logo"><span>PREMIUM CSS TEMPLATE</span><a href="#" class="notext">Progress - Premium CSS Template</a></h1>
			<!-- End Logo -->

			<!-- Navigation -->
			<div id="navigation">
				<ul>
				    <li><a href="index.html">Home</a></li>
				    <li><a href="about.html">About</a></li>
				    <li><a href="services.html">Services</a>
				    	<div class="dd-holder">
				    		<div class="pointer"></div>
				    		<ul>
				    		    <li><a href="service-page.html">Service Page 1</a></li>
				    		    <li><a href="service-page.html">Service Page 2</a>
		    		    			<div class="dd-holder">
				    		    		<div class="pointer"></div>
				    		    		<ul>
				    		    			<li><a href="service-page.html">Service Page 2.1</a></li>
				    		    			<li><a href="service-page.html">Service Page 2.2</a></li>
				    		    			<li><a href="service-page.html">Service Page 2.3</a></li>
				    		    			<li><a href="service-page.html">Service Page 2.4</a></li>
				    		    			<li><a href="service-page.html">Service Page 2.5</a></li>
				    		    			<li class="last"><a href="service-page.html">Service Page 2.6</a></li>
				    		    		</ul>
			    		    		</div>
				    		    </li>
				    		    <li><a href="service-page.html">Service Page 3</a></li>
				    		    <li class="last"><a href="service-page.html">Service Page 4</a>
		    		    			<div class="dd-holder">
				    		    		<div class="pointer"></div>
				    		    		<ul>
				    		    			<li><a href="service-page.html">Service Page 4.1</a></li>
				    		    			<li><a href="service-page.html">Service Page 4.2</a></li>
				    		    			<li><a href="service-page.html">Service Page 4.3</a></li>
				    		    			<li><a href="service-page.html">Service Page 4.4</a></li>
				    		    			<li><a href="service-page.html">Service Page 4.5</a></li>
				    		    			<li class="last"><a href="service-page.html">Service Page 4.6</a></li>
				    		    		</ul>
			    		    		</div>
				    		    </li>
				    		</ul>
				    	</div>
				    </li>
				    <li><a href="projects.html">Projects</a></li>
				    <li><a href="blog.html">Blog</a></li>
				    <li><a href="contact.html">Contact</a></li>
				    <li class="last"><a href="#">Bonus Pages</a>
				    	<div class="dd-holder">
				    		<div class="pointer"></div>
				    		<ul>
				    		    <li><a href="email/email.html">Email Template</a></li>
				    		    <li><a href="index.html">Home Style 1</a></li>
				    		    <li><a href="home2.html">Home Style 2</a></li>
				    		    <li><a href="gallery.html">Gallery Classic Style</a></li>
				    		    <li><a href="gallery-projects.html">Gallery Project Style</a></li>
				    		    <li><a href="#" class="login-link">Login</a></li>
				    		    <li><a href="error.html">Error Page</a></li>
				    		    <li><a href="under-construction.html">Under Construction</a></li>
				    		    <li><a href="full-page.html">Full Page</a></li>
				    		    <li><a href="left-sidebar.html">Left Sidebar</a></li>
				    		    <li><a href="right-sidebar.html">Right Sidebar</a></li>
				    		    <li><a href="sitemap.html">Sitemap</a></li>
				    		    <li class="last"><a href="styles.html">Styles</a></li>
				    		</ul>
				    	</div>
				    </li>
				</ul>
			</div>
			<!-- End Navigation -->
			
			<div class="cl">&nbsp;</div>
			
			<!-- Social-links -->
			<div class="social-links">
				<ul>
				    <li><a href="#" title="Follow us on twitter" class="twitter tooltip">twitter</a></li>
				    <li><a href="http://fancyapps.com/fancybox/demo/2_b.jpg" title="Like us on facebook" class="fancybox facebook tooltip">facebook</a></li>
				    <li><a href="#" title="Share on Flickr" class="flickr tooltip">flickr</a></li>
				    <li><a href="#" title="RSS Feed" class="rss tooltip">rss</a></li>
				</ul>
			</div>
		</div>
		
	</div>
	<!-- End Header -->
	
	<!-- Location -->
	<div id="location">
		<div class="center">
			<h2>Projects - Blog</h2>
			<p>Intro message goes here.</p>
		</div>
	</div>
	<!-- End Location -->
	
	<!-- Main -->
	<div id="main">
		
		<div class="center">
		<decorator:body/>
		</div>
	</div>
	</div>
	
		<c:if test="${not fn:contains(pageContext.request.requestURL,'/home')}">
<!-- 			<div id="footer-push" class="notext">&nbsp;</div> -->
		</c:if>
		<!-- Footer -->
		<div class="center">
			<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
			</div>
	</body>
</html>