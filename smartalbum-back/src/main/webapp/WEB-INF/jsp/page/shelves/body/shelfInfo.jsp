<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>

<c:set var="albums" value="${shelf.albums}"></c:set>

<div class="shelf-header-table-col2">

	<h1>
			<a href="<c:url value='/shelvesController/shelfDetails/${shelf.id}.html?'/>"><span class="shelfHeader">${shelf.name}</span></a>
			<c:set var="elementId" value="${shelf.id}" />
			<c:set var="confirmfunction" value="deleteShelf(${shelf.id},shelfInfo-${shelf.id});" />
			<c:set var="messageHeader" value="Suppression de l étagère ${shelf.name}" />
			<c:set var="messageBody" value="Attention, cette suppression sera <b>irréversible!</b><br/>Vous allez supprimer <b>${fn:length(shelf.albums)} album(s)</b> avec <b>${fn:length(shelf.images)} image(s)!</b>" />
						
			<a href="#" onclick="javascript:showStandardWarning('popupShelfLayer','${confirmfunction}','${messageHeader}','${messageBody}');"> 
			<img src="<c:url value='/img/p_supprimer.gif'/>" width="13" height="16"
						border="0" alt="Supprimer" title="Supprimer l'étagère"/>
			</a>
			<span style="border-right: 2px">|</span>
			<a href="#" onclick="javascript: showBlockInLayer('modifyShelfLayer${shelf.id}');"> 
				<img src="<c:url value='/img/images_modif.jpg'/>" width="13" height="16" border="0" alt="Modifier" title="Modifier l'étagère" />
			</a>
		</h1>
	<div class="additional-info-text">
		<span>Created : ${shelf.created} </span>
		<span>contains : ${fn:length(shelf.images)} </span>
		<span>images into : ${fn:length(shelf.albums)}</span>
		<span>albums and ${fn:length(shelf.unvisitedImages)} unvisited images</span>
		<span></span>
		<p class="shelfdescription">${shelf.description}</p>
	</div>
	<div></div>
</div>
<div id="shelfInfoDetail-${shelf.id}" class="albumList">
		<%@ include file="/WEB-INF/jsp/page/albums/body/albumsList.jsp" %>
</div>
