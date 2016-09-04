<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<strong>
<c:choose>
	<c:when test="${ligneSimulation.minReductionTotalPerMonth eq ligneSimulation.maxReductionTotalPerMonth}">
		<c:choose>
			<c:when test="${ligneSimulation.minReductionTotalPerMonth.gratuit}">
			</c:when>
			<c:otherwise>
				-${ligneSimulation.maxReductionTotalPerMonth}&euro;<span class="parmois"> /mois</span>
		    </c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
	de <c:if test="${not ligneSimulation.minReductionTotalPerMonth.gratuit}"></c:if>${ligneSimulation.minReductionTotalPerMonth}&euro;
	 &agrave; -${ligneSimulation.maxReductionTotalPerMonth}&euro;<span class="parmois"> /mois</span>
          </c:otherwise>
</c:choose>
</strong> 
