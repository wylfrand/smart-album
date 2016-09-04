<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jsp/common/include.jsp" %>

<div class="contain-aide">
		<div class="souscription">
			<c:choose>
				<c:when test="${sfr_user.natureLine.mobile}">
					<h2>Créer votre Multi-Packs</h2>
				<p>Abonnez-vous à la box de SFR et bénéficiez tous les mois d'une remise sur votre box!</p>
				<div class="btn-aide"><a href="${souscrireLigneFixe}">Souscrire la box de SFR&nbsp;<img alt="" src='<c:url value="/img/bouton-aide.png" />'></a></div>
			</c:when>
			<c:otherwise>
				<h2> Bénéficiez de Multi-Packs pour toute la famille </h2>
				<p>Abonnez-vous à un forfait Carré et bénéficiez d'une remise Multi-Packs.</p>
				<div class="btn-aide"><a href="${souscrireLigneMobile}">Souscrire un forfait suppplémentaire&nbsp;<img alt="" src='<c:url value="/img/bouton-aide.png" />'></a></div>
			</c:otherwise>
			</c:choose>
		</div>
		<div class="aide">
			<h2>Les Multi-Packs, comment ça marche ?</h2>
			<p>Des réponses à toutes vos questions.</p>	<br/>
			<div class="btn-aide"><a id="btn-aide" href="${enSavoirPlus}">En savoir plus <img alt="" src='<c:url value="/img/bouton-aide.png" />'></a>
			</div>
		</div>
</div>