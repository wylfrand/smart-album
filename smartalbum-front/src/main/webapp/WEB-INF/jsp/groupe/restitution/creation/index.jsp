<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jsp/common/include.jsp" %>

<head>
<script type="text/javascript">

    
</script>
</head>
<body>

	<div id="contenu">
		<p class="titre">
			CR&Eacute;EZ VOTRE MULTI-PACKS <br /> <strong>ET B&Eacute;N&Eacute;FICIEZ DE
				REMISES SUR VOTRE <br> BOX TOUS LES MOIS !
			</strong>
		</p>

		<div class="parcours">
			<img alt="" src='${smallImage}' />
		</div>

		<table class="table table-condensed creation">
			<thead>
				<tr>
					<th class="line_multi-pack">Les lignes de votre Multi-Packs</th>
					<th class="remise_multi-pack">Remises</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<tr id="add_new_line_row_0">
					<td>
						<c:choose>
							<c:when test="${sfr_user.natureLine.mobile}">
								<img alt="" src='<c:url value="/img/mobile.png" />' class='adjustement-line'/>
							</c:when>
							<c:otherwise>
								<img alt="" src="<c:url value='/img/box.png'/>"/>
							</c:otherwise>
						</c:choose> 
						<span class="texte-td"><maam:telephone label="${sfr_user.number}" separator="." /></span>
						<span class="texte-td"><maam:fullName id="fullName0" withToolTip="true" civilite="${sfr_user.civility}" firstName="${sfr_user.firstname}" name="${sfr_user.name}" taille="${longueurFullName}"/></span>

						<c:choose>
							<c:when test="${sfr_user.natureLine.mobile}">
								<div class="texte-td-gris-promo">
									<div class="promo">
										<maam:libelleForfait id="libelleForfait0" withToolTip="true"
											libelle='${sfr_user.libellePTA}'
											taille="${longueurTitreForfaitMobile}"></maam:libelleForfait>
									</div>
									<div class="carre-promo">
										<img alt="" src='${sfr_user.joyaPictoUrl}'>
									</div>
								</div>
							</c:when>
							<c:otherwise>
								<div class="texte-td-gris-box">
									<maam:libelleForfait libelle='${sfr_user.libellePTA}' id="libelleForfait0" withToolTip="true"
										taille="${longueurTitreForfaitBox}"></maam:libelleForfait>
								</div>
							</c:otherwise>
						</c:choose> 
						
					</td>
					<td class="separationPrice">
						<a rel="tooltip" data-placement="right"	class="aa adding" href="#"
						data-original-title="Compl&eacute;tez votre Multi-Packs pour connaître les remises sur votre box">
							<img alt="" src="<c:url value = "/img/remise-info.png"/>" class="img-remise adding" border="0">
						</a>
					</td>
					<td class="separationAction"></td>
				</tr>
				
				<c:forEach begin="1" end="3" var="counter"> 
					<%@include file="/WEB-INF/jsp/groupe/restitution/new_empty_line.jsp" %>		
				</c:forEach>
			</tbody>
					
		</table>
		<div class="box-tooltip-remise">
			<a id="imgRemiseActive" rel="tooltip" data-placement="left" class="aa notVisible" onclick="javascript:simulerCreationDeGoupe();" href="javascript:void(0);">
				<img alt="" src='<c:url value="/img/bouton-voir-remise.png" />' border="0" style="margin-right: 3px;" />
			</a>
		
			<div id="forbiddenActionDiv" class="notVisible">
				<div class="forbiddenActionContent">
					<div class="forbiddenText">Vous devez ajouter au moins une ligne ${sfr_user.natureLine.mobile ? "fixe" : "mobile"} pour voir vos remises</div>
				</div>
				<img alt="interdit" src='<c:url value="/img/interdit.png" />'>
			</div>
			<a id="imgRemise" class="noCreatedLine" href="javascript:void(0);" >
				<img alt="" src='<c:url value="/img/bouton-voir-remise.png" />' border="0" style="margin-right: 3px;" />
			</a>
		</div>
		<div class="clear"></div>
		<div id="textSameName" style="display: none;">
			<%@ include file="/WEB-INF/jsp/groupe/restitution/identite_protege.jsp" %>
		</div>
		<input type="hidden" name="lastAddedNumber" id="lastAddedNumber" value="" />
		<input type="hidden" name="lastDeletedNumber" id="lastDeletedNumber" value="" />
	</div>
	<%@ include file="/WEB-INF/jsp/common/aides.jsp" %>
	<div class="mlegales"><a class="mentionsLegales" href="javascript:void(0);" onclick="javascript:displayMentionsLegales();">Mentions légales</a></div>
<div class="notVisible mentionsLegales" id="legalBox"> 
	<p>${mentionsLegales}</p>
</div>
</body>