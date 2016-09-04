<%@ include file="/WEB-INF/jsp/common/include.jsp" %>

<script type="text/javascript">
$(document).ready(function() {
	//initialize the tooltip
	$('.aa').tooltip();
});
</script>
	
<c:choose>
	<c:when test="${isAdding}">
		<a rel="tooltip" data-placement="right" class="aa adding" href="javascript:void(0);" data-original-title="Compl&eacute;tez votre Multi-Packs pour connaître les remises sur votre box">
			<img alt="" src='<c:url value="/img/remise-info.png" />' class="img-remise adding" border="0" />
		</a>
  		</c:when>
  		<c:otherwise>
	   	<c:choose>
	   		<c:when test="${ameGroup.addams}">
	   			<c:choose>
	   				<c:when test="${member.mobile}">
	   					<c:choose>
	   						<c:when test="${member.valueMaxPerMonth.gratuit}">
	   							<a rel="tooltip" data-placement="right" class="aa" href="javascript:void(0);" data-original-title="Cette ligne ne contribue pas aux remises sur la box">
		    						<img alt="" src='<c:url value="/img/euro-off.png" />' class="img-remise" border="0" />
		    					</a>
	   						</c:when>
	   						<c:otherwise>
	   							<c:set var="originalTitle">
	   							
	   							<c:choose>
	   								<c:when test="${ameGroup.minReductionPerMonthAddams eq ameGroup.maxReductionPerMonthAddams && ameGroup.maxReductionPerMonthAddams.price eq 30}">
	   									Cette ligne contribue à la Box offerte
	   								</c:when>
	   								<c:otherwise>
	   									Cette ligne vous fait bénéficier d'une remise de -${member.valueMaxPerMonth}&euro; sur votre box
	   								</c:otherwise>
	   							</c:choose>   
	   							</c:set>
	   							<c:if test="${ member.valueMaxPerMonth ne member.valueMinPerMonth }">
	   								<c:set var="originalTitle">Cette ligne vous fait bénéficier d'une remise de -${member.valueMinPerMonth}&euro; &agrave; -${member.valueMaxPerMonth}&euro; sur votre box</c:set>
	   							</c:if>
	   							<a rel="tooltip" data-placement="right" class="aa" href="javascript:void(0);" data-original-title="${originalTitle}">
		    						<img alt="" src='<c:url value="/img/euro-on.png" />' class="img-remise" border="0" />
		    					</a>
	   						</c:otherwise>
	   					</c:choose>
	   				</c:when>
	   				<c:otherwise>
	   					<c:choose>
	   						<c:when test="${ameGroup.maxReductionPerMonthAddams.price eq 0}">
						    	<div class="price-remise-euro"><strong></strong></div>
	   						</c:when>
	   						<c:when test="${ameGroup.minReductionPerMonthAddams eq ameGroup.maxReductionPerMonthAddams && ameGroup.maxReductionPerMonthAddams.price eq 30}">
						    	<div class="price-remise-euro"><strong>Box offerte</strong><br/><span class="price">soit -29,99&euro;/mois</span></div>
	   						</c:when>
	   						<c:when test="${ameGroup.minReductionPerMonthAddams ne ameGroup.maxReductionPerMonthAddams}">
						    	<div class="price-remise-euro">
						    		<strong>de -${ameGroup.minReductionPerMonthAddams}&euro; &agrave; -${ameGroup.maxReductionPerMonthAddams}&euro;</strong>
						    		<span class="price">/mois</span>
						    	</div>
	   						</c:when>
	   						<c:otherwise>
	   							<div class="price-remise-euro">
					   				<strong>-${ameGroup.maxReductionPerMonthAddams}&euro;</strong><span class="price">/mois</span>
					   			</div>
	   						</c:otherwise>
	   					</c:choose>
	   				</c:otherwise>
	   			</c:choose>
	   		</c:when>
	   		<c:otherwise>
	   			<div class="price-remise-euro">
	   				<c:choose>
	   					<c:when test="${member.valueMaxPerMonth.gratuit}">
	   						<strong></strong>
	   					</c:when>
	   					<c:when test="${(member.valueMinPerMonth eq member.valueMaxPerMonth) and !member.valueMaxPerMonth.gratuit }">
	   						<strong>-${member.valueMaxPerMonth}&euro;</strong>
	   						<span class="price">/mois</span>
	   					</c:when>
	   					<c:when test="${member.valueMinPerMonth ne member.valueMaxPerMonth}">
	   						<strong>de -${member.valueMinPerMonth}&euro; &agrave; -${member.valueMaxPerMonth}&euro;</strong>
	   						<span class="price">/mois</span>
	   					</c:when>
	   				</c:choose>
	   			</div>
	   		</c:otherwise>
	   	</c:choose>
	</c:otherwise>
</c:choose>