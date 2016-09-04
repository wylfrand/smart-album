<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>

<div id="galleryPluralSight">
<c:forEach items="${files}" var="file" varStatus="compteur">
		<img id='galleryPluralSight${compteur.index}' class="galleryImage" width="350" height="350" src="<c:url value='/imagesController/paintImage${file.image.fullPath}/_medium.html'/>" alt="${file.image.name}"/>
		</c:forEach>
</div>
