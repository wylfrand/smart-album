<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<div id="resultAlbum">
	<div class="albumInfo">
<!-- Info du groupe -->
		<p><strong>Nom : </strong>${albumResult.name} </p>
		<p><strong>Date de création : </strong>${albumResult.created} </p>
		<p><strong>description : </strong>${albumResult.description} </p>
		<p><strong>Nom de couverture : </strong>${albumResult.coveringImage.name} </p>
		<p><strong>Nombre d'images: </strong>${fn:length(albumResult.images)}</p>
<!-- Info des images -->
		<c:if test="${! empty albumResult.images}">
			<c:forEach items="${albumResult.images}" var="imageResult">
				<p><span id="plusImage${albumResult.id}-${imageResult.id}">+</span>
				<a href="javascript:display('imageSearch${albumResult.id}-',${imageResult.id},'plusImage${albumResult.id}-','<c:url value='/searchController/ajax/search/image.html' />');">${imageResult.name} - ${imageResult.size} (image)</a>
				</p>
				<div id="imageSearch${albumResult.id}-${imageResult.id}" style="display: none;">
<%-- 					<%@ include file="/WEB-INF/jsp/page/search/result/imageResult.jsp" %>  --%>
				</div>
			</c:forEach>
			
		</c:if>
	</div>
</div>