<%@ include file="/WEB-INF/jsp/common/include.jsp" %>

<tr id="add_new_line_row_${counter}">
   <td class="firstColumn">
   	<c:choose>
		<c:when test='${member.mobile}'>
			<img alt="" src='<c:url value="/img/mobile.png" />' class='adjustement-line' />
		</c:when>
		<c:otherwise>
			<img alt="" src='<c:url value="/img/box.png" />'/>
		</c:otherwise>
	</c:choose>
	
   	
   	<c:choose>
			<c:when
				test="${member.serviceType ne 'CMD-MOB' && member.serviceType ne 'CMD-FIX'}">
				<span class="texte-td">
					<maam:telephone label="${member.person.line.number}" separator="." />
				</span>
			</c:when>
			<c:otherwise>
				<span class="texte-td newline"> Nouvelle ligne</span>
			</c:otherwise>
		</c:choose> 
   		<c:choose>
   			<c:when test='${isAdding}'>
   			   <c:choose>
   			   		<c:when test='${sameName}'>
   			   		<span class="texte-td">
   						<maam:fullName id="fullName${status.index}" withToolTip="true" civilite="${member.person.civilite}" firstName="${member.person.firstname}" name="${member.person.name}" taille="${longueurFullName}"/>		
   				   	</span>
   				   	</c:when>
   				   	<c:otherwise>
   				   		<span class="protectedName texte-td">
							Identité du titulaire<br/> protégée*
						</span>
   				   	</c:otherwise>
   			   </c:choose>
   			</c:when>
   			<c:otherwise>
   				 <c:choose>
   			   		<c:when test='${member.waitingForPJ}'>
   						<span class="protectedName texte-td">
							Identité du titulaire<br/> protégée*
						</span>
   					</c:when>
					<c:otherwise>
						<span class="texte-td">
							<maam:fullName id="fullName${status.index}" withToolTip="true" civilite="${member.person.civilite}" firstName="${member.person.firstname}" name="${member.person.name}" taille="${longueurFullName}"/>
						</span>
					</c:otherwise>
				</c:choose> 
   			
   			</c:otherwise>
   		</c:choose>
   	
   	<c:choose>
   		<c:when test="${member.mobile}">
	    	<div class='${member.migrating ? "texte-td-gris-promo twoLines" : "texte-td-gris-promo"}'>
	    		<div class="promo">
		    		<maam:libelleForfait id="libelleForfait${status.index}" withToolTip="true" libelle='${member.person.line.libellePTA}' taille="${longueurTitreForfaitMobile}"></maam:libelleForfait>
	    			<c:if test="${member.migrating}">
		    			<br><span class="loader">changement en cours</span>
	    			</c:if>
	    		</div>
	    		<div class="carre-promo">
	    			<img src="${member.person.line.joyaPictoUrl}" />
	    		</div>
	    	</div>
   		</c:when>
   		<c:otherwise>
   			<div class="texte-td-gris-box">
   				<maam:libelleForfait id="libelleForfait${status.index}" withToolTip="true" libelle='${member.person.line.libellePTA}' taille="${longueurTitreForfaitBox}"></maam:libelleForfait>
   			</div>
   		</c:otherwise>
   	</c:choose>
   </td>
   <td class="separationPrice">
   		<%@ include file="/WEB-INF/jsp/groupe/restitution/reduction_column.jsp" %>
	</td>
	<td class="separationAction">
	   	<c:choose>
	   		<c:when test="${isAdding}">
	   			<c:set var="modifyLineURL"><c:url value='/action/groupe/creation/modify/'/></c:set>
	   			<a href="javascript:modifyLine('${counter}','${member.person.line.number}','${modifyLineURL}');" class="disabledAction">Modifier</a>
	   		</c:when>
	   		<c:otherwise>
			   	<c:if test='${member.mobile}'>
			   		<a class="deleteLineIcon" href="javascript:void(0);" data-number="${member.person.line.number}"
			   			data-number-with-point='<maam:telephone label="${member.person.line.number}" separator="." />'
			   			data-valueMinPerMonth="${member.valueMinPerMonth}" data-valueMaxPerMonth="${member.valueMaxPerMonth}"
			   			data-free="${member.valueMaxPerMonth.gratuit}" data-hasNonContributedLine="${ameGroup.hasNonContributedLine}"
			   			data-isDuo="${member.duoMember}" data-hasMoreThanTwoLines="${fn:length(ameGroup.ameMembers) > 2}"
			   			data-totalMinContribution="${ameGroup.minReductionPerMonthAddams}" data-totalMaxContribution="${ameGroup.maxReductionPerMonthAddams}"
			   			data-totalMinContributionMPVX="${ameGroup.minReductionPerMonth}" data-totalMaxContributionMPVX="${ameGroup.maxReductionPerMonth}"
			   			data-boxFree='${ameGroup.boxFree}' data-addams="${ameGroup.addams}">
				    	<img alt="" src='<c:url value="/img/retirer-off.png" />' class="img-delete" border="0" />
			    	</a>
			   	</c:if>
	   		</c:otherwise>
	   	</c:choose>
	   	<input type="hidden" name="activeSimu_${counter}" value="${activeSimu}" id="activeSimu_${counter}" />
	   	<input type="hidden" name="sameName_${counter}" value="${sameName}" id="sameName_${counter}" />
	<!--    	pour chaque  numéro je cache son id -->
	   	<input type="hidden" name="id_${member.person.line.number}" value="add_new_line_row_${counter}" id="id_${member.person.line.number}" />
   </td>
</tr>