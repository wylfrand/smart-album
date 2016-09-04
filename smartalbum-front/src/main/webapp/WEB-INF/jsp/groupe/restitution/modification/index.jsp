<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>

<head>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
</head>
<body>

<div id="back-top">
	<div>
		<p class="${(not empty ameGroup.numbersWaitingCompensation['MIGRATION']) || (not empty ameGroup.numbersWaitingCompensation['PJ']) || (not empty ameGroup.numbersWaitingCompensation['ACTIVATION'])?'titre':'titre_sans_contrepartie'}">
		<strong>VOTRE MULTI-PACKS</strong><br/><br/>
		<c:if test="${not ameGroup.complete and ameGroup.addams}">
	<c:choose>
		<c:when test="${(fn:length(ameGroup.ameMembers) - 3) < 0}">
			<span class="sous-titre-mp"> Ajoutez des lignes et augmentez
			vos remises sur la box. <a class="en-savoir-plus"
				href="${enSavoirPlus}">En savoir plus</a> </span>
		</c:when>
		<c:otherwise>
			<span class="sous-titre-mp"> Ajoutez une ligne et augmentez
			vos remises sur la box. <a class="en-savoir-plus"
				href="${enSavoirPlus}">En savoir plus</a> </span>
		</c:otherwise>
	</c:choose>

</c:if>
		</p>
	</div>
</div>
<div id="contenu2">
	
	<%@include file="/WEB-INF/jsp/groupe/restitution/modification/body/zone_message.jsp" %>
	
	<table class="table table-condensed">
	    <thead>
		    <tr>
			    <th class="line_multi-pack">Les lignes de votre Multi-Packs</th>
			    <th class="remise_multi-pack">Remises</th>
		    	<th></th>
		    </tr>
	    </thead>
	    <tbody>
	    	<c:forEach items="${ameGroup.ameMembers}" var="member" varStatus="status">
			    <%@include file="/WEB-INF/jsp/groupe/restitution/new_line.jsp" %>
	    	</c:forEach>
	    	
	    	<!-- content for the case where the packpage is not complete -->
			<c:if test="${not ameGroup.complete and ameGroup.addams}">
				<c:forEach begin="1" end="${(4 - fn:length(ameGroup.ameMembers)) > 0 ? 4 - fn:length(ameGroup.ameMembers) : 0}" var="counter">
					<%@include file="/WEB-INF/jsp/groupe/restitution/new_empty_line.jsp" %>
				</c:forEach>
				
			</c:if>
	    </tbody>
	</table>
	
	<c:if test="${not ameGroup.complete and not ameGroup.addams}">
		<%@include file="/WEB-INF/jsp/groupe/restitution/modification/body/new_line_mpvx.jsp" %>
	</c:if>
	
	<c:if test="${not ameGroup.complete and ameGroup.addams}">
		<div class="clear"></div>
		<div class="box-tooltip-remise">
			<a id="imgRemiseActive" rel="tooltip" data-placement="left" class="notVisible" onclick="javascript:simulerModificationDeGroupe();" href="javascript:void(0);">
				<img alt="" src='<c:url value="/img/bouton-voir-remise.png" />' border="0" style="margin-right: -1px;" />
			</a>
		
			<div id="forbiddenActionDiv" class="notVisible">
				<div class="forbiddenActionContent">
					<div class="forbiddenText">Vous devez ajouter au moins une ligne mobile pour voir vos remises</div>
				</div>
				<img alt="interdit" src='<c:url value="/img/interdit.png" />'>
			</div>
			<a id="imgRemise" class="noCreatedLine" href="javascript:void(0);">
				<img alt="" src='<c:url value="/img/bouton-voir-remise.png" />' class="img-remise" border="0" style="margin-right: -1px;" />
			</a>
		</div>
		<div class="clear"></div>
	</c:if>
	
	<div class='box-suppression-remise'>
		<div class="deleteGroupLink ${ameGroup.addams or (ameGroup.complete and  not ameGroup.addams)? 'ajusteSuppressionMp' : ''}">
			<a onclick="javascript:showDeleteGroupLayer();" href="javascript:void(0);">
				<img alt="" src='<c:url value="/img/suppression-multipack.png" />' />
			</a>
		</div>
		<c:set var="printMessageIdentiteProtegee" value="${not empty ameGroup.numbersWaitingCompensation['PJ']}" />
		<div id="${printMessageIdentiteProtegee?'textSameNameComp':'textSameName'}" class="${printMessageIdentiteProtegee?'textSameNameCompClass':'notVisible'}">
			<%@ include file="/WEB-INF/jsp/groupe/restitution/identite_protege.jsp" %>
		</div>
	</div>
	
	<div class="clear"></div>
	<input type="hidden" name="groupCode" id="groupCode" value="${ameGroup.code}" />
	<input type="hidden" name="lastAddedNumber" id="lastAddedNumber" value="" />
	<input type="hidden" name="lastDeletedNumber" id="lastDeletedNumber" value="" />
</div>

<c:if test="${not ameGroup.complete}">
	<%@ include file="/WEB-INF/jsp/common/aides.jsp" %>
</c:if>

<div class="mlegales"><a class="mentionsLegales" href="javascript:void(0);" onclick="javascript:displayMentionsLegales();">Mentions légales</a></div>
<div class="notVisible mentionsLegales" id="legalBox"> 
	<p>${ameGroup.mentionsLegales}</p>
</div>

<%@include file="/WEB-INF/jsp/groupe/restitution/modification/body/popup.jsp" %>
</body>