<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>

<div class="bloc-ineligibilite">
	<div class="bloc-non-valide">
		<strong>
		<c:choose>
	 			<c:when test="${fn:length(lignesHorsProposition)==1}">
	 				La ligne suivante ne peut pas faire partie de votre Multi-Packs
	 			</c:when>
	 			<c:otherwise>
	 				Les lignes suivantes ne peuvent pas faire partie de votre Multi-Packs
	 			</c:otherwise>
	 		</c:choose>
		</strong>
		<br/><br/>
		<c:forEach var="simuligne" items="${lignesHorsProposition}">
			<img src="<c:url value='/img/ineligibilite.png'/>">
				<strong><maam:telephone label="${simuligne.number}" separator="." />
				</strong> 
				<span class="deuxPoint">:</span>
			<div class="mesg-ineligibilite-confirmation">
			<c:choose>
	 			<c:when test="${empty simuligne.libelleIneligibilite}">
	 				Cette ligne n'est pas &eacute;ligible Multi-Packs
	 			</c:when>
	 			<c:otherwise>
	 				${simuligne.libelleIneligibilite}
	 			</c:otherwise>
	 		</c:choose>
				
			</div>
		</c:forEach>
	</div>
</div>
