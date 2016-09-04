<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>

<c:set var="shelves" value="${predefinedShelves}"></c:set>
<c:set var="pluriel" value="${fn:length(shelves)>1 ? 's' : ''}"></c:set>
<c:set var="myHeader" value="${fn:length(shelves)} étagère${pluriel} publique${pluriel}"></c:set>

<div>
<!-- 	<div class="clearfix"> -->
<!-- 			<div class="shelves"> -->
<%-- 				<%@ include file="/WEB-INF/jsp/page/shelves/body/shelvesList.jsp"%> --%>
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
<!-- Page -->
	<div id="page">
<!-- Gallery -->
			<div id="gallery">
				<div class="shell">	
					<div class="nav">
						<ul>
							<li class="active"><a href="#">All</a></li>
							<c:forEach items="${shelves}" var="shelf" varStatus="compteurShelf">
								<li><a href="#">${shelf.name}</a></li>
							</c:forEach>
						</ul>
						<span class="colors"></span>
					</div>
					<div class="container">
						<div class="list-thumbs">
							<ul>
								<c:forEach items="${shelves}" var="shelf" varStatus="compteurShelf">
										<c:forEach items="${shelf.albums}" var="album" varStatus="compteurAlbum">
											<li class="${shelf.name}" id="${shelf.id}-${album.id}">
												<div class="box">
													<a href="<c:url value='/albumsController/showAlbum/${album.id}.html'/>" class="fancybox">
														<img id='album_${compteurShelf.index}-${compteurAlbum.index}' src="<c:url value='/imagesController/paintImage${album.coveringImage.fullPath}/_medium.html'/>" width="269" height="198"/>
													</a>
													<div class="info">
														<span class="category">${shelf.name}</span>
														<span class="album_short_desc">${shelf.description}</span>
													</div>
												</div>
											</li>
										</c:forEach>
								</c:forEach>
							</ul>
							<div class="cl">&nbsp;</div>
						</div>
					</div>
				</div>
			</div>
			<!-- END Gallery -->
<!-- 					<div class="footer-push"></div> -->
	</div>
	<!-- END Page -->
</div>