<%@ include file="/WEB-INF/jsp/common/include.jsp" %>

<div id="popupShelfLayer" class="notVisible">
	<div id="conteneur-layer">
		<div id="btn-fermer">
		</div>
		<div id="contenu-layer">
			<strong>#messageHeader</strong><br />
			#messageBody
			<br />
		</div>
		<div id="confirmer-layer">
			<a href="javascript:parent.$.fancybox.close();">
				<img src='<c:url value="/img/btn-annuler-layer.png" />' />
			</a>
			<a href="javascript:void(0);" >
				<img onclick="javascript:#confirmfunction" src='<c:url value="/img/btn-confirmer-layer.png" />' />
			</a>
		</div>
	</div>
</div>
