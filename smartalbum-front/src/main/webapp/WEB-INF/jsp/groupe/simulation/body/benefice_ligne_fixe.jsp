<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<strong class="box-offerte">
<c:choose>
	<c:when test="${ligneSimulation.minReductionTotalPerMonth eq ligneSimulation.maxReductionTotalPerMonth and ligneSimulation.maxReductionTotalPerMonth.price eq 30}">
		Box offerte<span class="price"></span><br/>
		<span class="box-max">soit -29,99&euro;</span>
	</c:when>
	<c:otherwise>
		<c:choose>
	<c:when test="${ligneSimulation.minReductionTotalPerMonth eq ligneSimulation.maxReductionTotalPerMonth}">
		<c:choose>
			<c:when test="${ligneSimulation.minReductionTotalPerMonth.gratuit}">
			</c:when>
			<c:otherwise>
				-${ligneSimulation.maxReductionTotalPerMonth}&euro;
		          </c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
	de <c:if test="${not ligneSimulation.minReductionTotalPerMonth.gratuit}"></c:if>${ligneSimulation.minReductionTotalPerMonth}&euro;
	 &agrave; -${ligneSimulation.maxReductionTotalPerMonth}&euro; 
          </c:otherwise>
</c:choose>
	</c:otherwise>
</c:choose>
</strong>
<c:if test="${not ligneSimulation.minReductionTotalPerMonth.gratuit}">
	<span class="parmois">/mois</span>
</c:if>
