<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>

<table class="entete">
	<tr>
		<td>
				<c:choose>
		             <c:when test="${hasPJCompensation}">
		             <div class="orange">
		             	<p style="margin-top: -3px;color: red;">Votre pack est en attente d’éléments</p>
		             	<p style="margin-top: -25px;color: red;">pour être totalement finalisé</p>
		             </div>
		             </c:when>
		             <c:otherwise>
		             <div class="green">
		                 <p>Créé le : <strong><fmt:formatDate pattern="dd/MM/yyyy" value="${ame.createDate}" /></strong></p>
		                 </div>
		             </c:otherwise>
		        </c:choose> 
		</td>
		
		<td>
		<div class="euro">Economie réalisée : 
			<strong style="font-weight: normal;font-size: 12px;color: black;">
				 <c:choose>
                    		<c:when test="${(ame.maxReductionPerMonth.price eq ame.minReductionPerMonth.price)}">
                             <c:choose>
                    				<c:when test="${ame.maxReductionPerMonth.gratuit}">
                                 	<span class="prix trouge" style="font-size: 16px;"></span>
                         		 </c:when>
                        			<c:otherwise>
                                 <span class="prix trouge" style="font-size: 16px;"><maam:price value="${ame.maxReductionPerMonth.price}"></maam:price>&euro;</span>/mois
                         		</c:otherwise>
                     		</c:choose> 
                          </c:when>
                        	<c:otherwise>
                                 de <strong class="prix trouge" style="font-size: 16px;"><maam:price value="${ame.minReductionPerMonth.price}"/>&euro;</strong> &agrave; <strong class="prix trouge" style="font-size: 16px;"><maam:price value="${ame.maxReductionPerMonth.price}"/>&euro;</strong>/mois
                         </c:otherwise>
                     </c:choose>       
			</strong>
		</div></td>
		<td>
			<div class="supp">
				<a id="suppressionGroupePanel_${ame.code}_link" href="#" onClick="javascript:showSuppressionGroupePanel('${ame.code}', this.id);return false;">Supprimer</a>
	            <a id="suppressionGroupePanel_${ame.code}" href="#suppressionGroupeBloc_${ame.code}"></a>
			</div></td>
	</tr>
</table>

<div style="display:none">
            <div id="suppressionGroupeBloc_${ame.code}">
               <div class="popinLayer3 png_bg">
                    <div class="popinContent" style="margin-bottom: 35px;">
                        <div class="popinTop">
                            <a href="#" onclick="javascript:$.fancybox.close();return false;" class="popinClose3 tbleu">Fermer</a>
                        </div>
                        <p style="margin-bottom: 15px;">
                            Vous avez demand&eacute; la <strong>suppression</strong> de votre Multi-Packs.<br/>
                            <c:if test="${not ame.maxReductionPerMonth.gratuit}">
                            	<br/><br/> Si vous confirmez vous perdrez le b&eacute;n&eacute;fice de vos remises, soit 
                            	<c:choose>
                    		<c:when test="${(ame.maxReductionPerMonth.price eq ame.minReductionPerMonth.price)}">
                             <c:choose>
                    				<c:when test="${ame.maxReductionPerMonth.gratuit}">
                                 	<span class="prix trouge" style="font-size: 16px;"></span>
                         		 </c:when>
                        			<c:otherwise>
                                 <span class="prix trouge" style="font-size: 16px;"><maam:price value="${ame.maxReductionPerMonth.price}"></maam:price>&euro;</span>/mois.
                         		</c:otherwise>
                     		</c:choose> 
                          </c:when>
                        	<c:otherwise>
                                 de <strong class="prix trouge" style="font-size: 16px;"><maam:price value="${ame.minReductionPerMonth.price}"/>&euro;</strong> &agrave; <strong class="prix trouge" style="font-size: 16px;"><maam:price value="${ame.maxReductionPerMonth.price}"/>&euro;</strong>/mois
                         </c:otherwise>
                     </c:choose>    
                            </c:if>
                        </p>
                        <div style="padding:10px 0 10px 20px;">
                            <input type="checkbox" id="confirm_suppression_${ame.code}" onclick="javascript:changeSelectedButton('${ame.code}');"/>Je souhaite supprimer mon Multi-Packs.
                        </div>
                       	<div id="confirmGreyButton" style="padding:10px 0 20px 20px;">
                           	<a href="#" style="text-decoration:none;" onclick="javascript:$.fancybox.close();return false;" class="btn-grey png_bg popinBtn">Annuler</a>
                           	<a id="suppressionGroupe_btn_${ame.id}" href="#" style="text-decoration:none;padding-left: 18px;" onclick="javascript:if(!$('#confirm_suppression_${ame.code}').is(':checked'))return false;_stat_pagename='Gestion Groupe/Layer Suppression Groupe';stat_evt('scRemove',[';GROUPE;1;0']);stats();deleteAme('${ame.code}', '${ame.id}', false);return false;" class="btn-grey png_bg no-margin">Confirmer</a>
                        </div>
                        <div id="confirmRedButton" style="display: none;padding:10px 0 20px 20px;">
                        		<a href="#" style="text-decoration:none;" onclick="javascript:$.fancybox.close();return false;" class="btn-grey png_bg popinBtn">Annuler</a>
                            	<a id="suppressionGroupe_btn_${ame.id}" href="#" style="text-decoration:none;padding-left: 18px;" onclick="javascript:if(!$('#confirm_suppression_${ame.code}').is(':checked'))return false;_stat_pagename='Gestion Groupe/Layer Suppression Groupe';stat_evt('scRemove',[';GROUPE;1;0']);stats();deleteAme('${ame.code}', '${ame.id}', false);return false;" class="btn-red png_bg no-margin">Confirmer</a>
                        </div>
                        <div id="groupe_delete_loading_${ame.id}" style="display: none; float: right;margin-right: 99px;top:-45px" class="maam-loading-small">
                        	<div></div>
                        </div>
                 </div>
           </div>
      </div>
 </div>