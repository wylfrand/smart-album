<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<html>
<body>
	<!-- Page -->
		<!-- END Header -->
		<section id="content">
			<!-- Featured Content -->
			<section id="featured-content">
				<div class="shell">
					<div class="container">
						<!-- Item -->
<!-- 						<div class="item item-1"> -->
<!-- 							<h2>Qui sommes-nous?</h2> -->
<!-- 							<p>Nous sommes des développeurs qui cherchons à vulgariser le développement web. Nous recherchons actuellement des personnes passionnées d'informatique et qui souhaitent, comme nous, rendre le web accessible à tous (dans toute sa complexité)...</p> -->
<%-- 							<img src="<c:url value='/templates/green/css/images/featured-image-1.png'/>" width="437" height="370" alt="" data-hover="<c:url value='/templates/green/css/images/featured-image-1-hover.png'/>" /> --%>
<!-- 						</div> -->
						<!-- END Item -->
						<!-- Item -->
						
						<div class="item item-2">
							<h2>Crééz votre page!</h2>
							<p>Rajoutez des photos dans l'onglet "Nouvelles images". Créez des blocs de rangement d'images, créez des albums, puis rajoutez une description courte et une description logue plus élaborée. Divulguez vos articles! Mieu, demandez-nous de nouvelles fonctionalités!...</p>
							<c:if test="${ not DEVICE_IS_MOBILE }">
							<div class="video-holder">
								<iframe width="469" height="269" src="//www.youtube.com/embed/7TvepUn5PrM?wmode=transparent" frameborder="0" allowfullscreen></iframe>
							</div>
							</c:if>
						</div>
						<!-- END Item -->
						<!-- Item -->
						<div class="item item-3">
							<h2>SMART ALBUM</h2>
							<p>L'application SMART ALBUM permet de manipuler intelligemment des images et des videos afin de présenter vos projets en vous basant essentiellement sur l'image et la video. Vous pourrez aussi juste ranger toutes vos photos, vos documents perso scannés et les retrouver en quelques cliques! Vérifiez par vous même! </p>
							<c:if test="${ not DEVICE_IS_MOBILE }">
							<img src="<c:url value='/templates/green/css/images/featured-image-1.png'/>" width="437" height="370" alt="" data-hover="<c:url value='/templates/green/css/images/featured-image-1-hover.png'/>" />
							</c:if>
						</div>
						<!-- END Item -->
					</div>
					<div class="nav">
						<ul>
							<li><a href="#"></a></li>
							<li><a href="#"></a></li>
<!-- 							<li><a href="#"></a></li> -->
						</ul>
					</div>
				</div>
			</section>
			<!-- END Featured Content -->
		</section>
	<!-- END Page -->
	<div id="register" style="display:none;">
				<%@ include file="/WEB-INF/jsp/page/users/register.jsp"%>
			</div>
</body>
</html>