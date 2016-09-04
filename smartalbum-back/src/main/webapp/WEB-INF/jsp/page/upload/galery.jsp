<%@page contentType="text/html;charset=UTF-8"%><%@page pageEncoding="UTF-8"%><%@ page session="false"%><%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%><%@ include file="/WEB-INF/jsp/common/include.jsp"%><script language="JavaScript">	function ValidateChanges() {		var image = document.getElementById("image").value;		if (image != '') {			var checkimg = image.toLowerCase();			if (!checkimg.match(/(\.jpg|\.png|\.JPG|\.PNG|\.jpeg|\.JPEG)$/)) {				alert("Please enter  Image  File Extensions .jpg,.png,.jpeg");				document.getElementById("image").focus();				return false;			}		}		return true;	}</script><div class="uploadTitle">		<c:choose>						<c:when test="${controller eq 'albumsController'}">							Mise à jour de l'album <span>${currentAlbum.name}</span>						</c:when>						<c:otherwise>							Créer un album photos						</c:otherwise>					</c:choose></div><div class="tabContainer_item">	<div class="infoLayer_toggler" style="padding-top: 15px;padding-bottom: 10px;">		<div style="display: inline;"><a href="#" data-content="customer_sfr"			class="togglerMultipack toggler-a closeState">			<img src="<c:url value='/img/database-icon.png'/>" width="16" height="23" border="0" alt="Editer les propriétés" />			Importez vos photos!</a>			<c:if test="${controller eq 'albumsController'}">				</c:if>		</div>	</div>	<div id="customer_sfr" class="toggleHide">		<input id="fileupload" type="file" name="files[]"			data-url="<c:url value='/${controller}Rest/upload.json' />" multiple><!-- 		<div id="dropzone" class="fade well">Drop files here</div> -->		<div id="progress" class="progress">			<div class="bar" style="width: 0%;"></div>		</div>	</div></div><table id="uploaded-files" class="display" width="100%">  	<thead>		<tr>			<th align="center"> Image</th>			<th class="first">File Name</th>			<c:if test="${not DEVICE_IS_MOBILE }">			<th>File Size</th>			<th>File Dimention</th>			</c:if>						<th class="last">Actions</th>		</tr>	</thead>	<tbody>	</tbody>	<tfoot>		<tr style="clear : both">			<td colspan="5">				<ul id="buttons-list">					<li><a onclick="javascript:massiveAction('REMOVE_SELECTED');">REMOVE_SELECTED</a></li>					<li><a						onclick="javascript:showPopupWarning('popupLayerSupprimerTout');">REMOVE_ALL</a></li>					<li><a onclick="javascript:massiveAction('TOOGLE_SELECT_ALL');">TOOGLE_SELECT_ALL</a></li>					<c:choose>						<c:when test="${controller eq 'albumsController'}">							<li><a onclick="javascript:massiveAction('MODIFY_ALBUM');">MODIFY_ALBUM</a></li>						</c:when>						<c:otherwise>							<li><a onclick="javascript:massiveAction('CREATE_ALBUM');">CREATE_ALBUM</a></li>						</c:otherwise>					</c:choose>				</ul>			</td>		</tr>	</tfoot></table><div id="currentAlbumId" data-albumid="${currentAlbum.id}"></div><%@ include file="/WEB-INF/jsp/page/albums/body/createOrModifyAlbum.jsp"%><c:set var="elementId" value="SupprimerTout" /><c:set var="confirmfunction" value="massiveAction('REMOVE_ALL');" /><c:set var="messageHeader" value="Suppression de toutes les photos de l'album ${currentAlbum.name}" /><c:set var="messageBody" value="Attention, cette suppression sera <b>irréversible</b><br/> <b>Toutes les pages</b> de l'album seront supprimés!!" /><%@ include file="/WEB-INF/jsp/common/popup.jsp"%><%@ include file="/WEB-INF/jsp/common/popupImageTemplate.jsp"%><%@ include file="/WEB-INF/jsp/common/popupImageModifyTemplate.jsp"%>