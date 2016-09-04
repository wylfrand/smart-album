<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>

<script type="text/javascript">

function toogleSlider()
{
	if($("#slider1").hasClass("selected"))
	{
		$("#slider1").addClass("unselected");
		$("#slider1").removeClass("selected");
		
		$("#slider2").addClass("selected");
		$("#slider2").removeClass("unselected");
	}
	else
	{
		$("#slider2").addClass("unselected");
		$("#slider2").removeClass("selected");
		
		$("#slider1").addClass("selected");
		$("#slider1").removeClass("unselected");
	}
}
	
</script>

<style type="text/css">
	.selected{ display: blok;margin-top: 80px;}
	.unselected{ display: none;}
</style>

<div class="album-header-table">
	<div class="block-album-infos">
		</div>
		<div class="secondColumn">
<!-- 			<a href="#" onclick="javascript: toogleSlider();">  -->
<%-- 					<img src="<c:url value='/images/power_switch_user.png'/>" width="26" --%>
<!-- 						height="35" border="0" alt="Switch slider"/></a> -->
						
			<a href="#" onclick="javascript: editAlbum(${album.id});"> 
					<img src="<c:url value='/img/images_modif.jpg'/>" width="26"
						height="35" border="0" alt="Edit Album"/></a>
			
			<a href="<c:url value='/albumsController/download/${album.id}.html'/>" target="_blanc">
			<img src="<c:url value='/img/disquette.png'/>" width="24"
						 border="0" alt="Download Album"/>
			</a>
			<a href="#" onclick="javascript: showPopupWarning('popupLayerDelateAlbum');"> 
					<img src="<c:url value='/img/p_supprimer.gif'/>" width="18"
						 border="0" alt="Delete Album"/></a>
			
			<a href="#" onclick="javascript: showImageManager('${album.id}');"> 
					<img src="<c:url value='/images/insertrowabove.gif'/>" width="24"
						 border="0" alt="Images manager"/></a>
		</div>
	</div>
	<div id="slider1">
		<%@ include file="/WEB-INF/jsp/page/images/slider.jsp"%>
	</div>
	
	<c:forEach items="${album.messagesHTML}" var="messageHTMLToSet" varStatus="compteurMESSAGE">
		<c:if test="${messageHTMLToSet.message_type eq 'LONGDESCRIPTION'}">
			<c:set var="messageHTML" value="${messageHTMLToSet}"/>
		</c:if>
	</c:forEach>
	
	<%@ include file="/WEB-INF/jsp/page/albums/albumMessagesHTML.jsp" %>
	
	<c:set var="elementId" value="DelateAlbum" />
	<c:set var="confirmfunction" value="deleteAlbum(${album.id});" />
	<c:set var="messageHeader" value="Suppression de l'album ${album.name} - ${album.id}" />
	<c:set var="messageBody" value="Attention, Vous allez suprimer ${fn:length(album.images)} photo(s).<br/>Cette suppression sera irréversible!" />
	<%@ include file="/WEB-INF/jsp/common/popup.jsp"%>
	<div id="imagesManager" style="display:none"></div>
</div>