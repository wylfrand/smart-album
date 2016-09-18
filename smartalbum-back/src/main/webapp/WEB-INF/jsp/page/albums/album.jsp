<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>

<c:set var="album" value="${currentAlbum}" />
<c:set var="selectedImages" value="${model.selectedAlbum.images}" />
<c:set var="filesToPrint"
	value="${showPictureDetailInAlbum ? pictureDetailInAlbum : files}" />
<div id="currentAlbumId" data-albumid="${currentAlbum.id}"
	data-albumtype="${ currentAlbum.isSliderAlbum}"></div>
<div class="bd margin-left" id="tabs">
	<div id="infoLayerMultipack" class="infoLayer">
		<ul class="js-blockTabs ajustSliderTabs">

			<c:choose>
				<c:when test="${currentAlbum.isSliderAlbum}">
					<li class="tabs"><a href="#sliders"
						class="js-tabLink closeState"><span>Regarder les
								sliders</span> </a></li>
				</c:when>
				<c:otherwise>
					<li class="tabs"><a href="#imagesBlog"
				class="js-tabLink openState"><span>Blog des images</span> </a></li>
				</c:otherwise>
			</c:choose>
			<li class="tabs"><a href="#smartslider"
						class="js-tabLink closeState"><span>Album - Fullscreen
								Slider</span> </a></li>
		</ul>
		<div class="separator ajustSeparator"></div>

		<div id="sliders">
			<div id="userAlbums">
				<%@ include file="/WEB-INF/jsp/page/albums/body/albumInfo.jsp"%>
			</div>
		</div>
		<c:choose>
			<c:when test="${currentAlbum.isSliderAlbum}">
				<div id="smartslider">
					<%@ include file="/WEB-INF/jsp/page/albums/body/imageSet.jsp"%>
				</div>
			</c:when>
			<c:otherwise>
				<div id="imagesBlog">
					<%@ include file="/WEB-INF/jsp/page/albums/body/blog.jsp"%>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</div>


