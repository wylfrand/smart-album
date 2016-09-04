<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<c:set var="prixTotalPourOfrirBox" value="30"/>

<c:choose>
	<c:when test="${not ameGroup.addams}">
		<strong>
			<c:choose>
				<c:when test="${ameGroup.maxReductionPerMonth.price eq ameGroup.minReductionPerMonth.price}">
					<c:choose>
						<c:when test="${ameGroup.maxReductionPerMonth.gratuit}">
							-
						</c:when>
						<c:otherwise>
							-${ameGroup.maxReductionPerMonth}&euro;
					    </c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
				de <c:if test="${not ameGroup.minReductionPerMonth.gratuit}"></c:if>${ameGroup.minReductionPerMonth}&euro;
				 &agrave; -${ameGroup.maxReductionPerMonth}&euro;
			          </c:otherwise>
			</c:choose>
		</strong>
	</c:when>
	<c:otherwise>
		<strong>
			<c:choose>
				<c:when test="${ameGroup.fixLine.valueMinPerMonth eq ameGroup.fixLine.valueMaxPerMonth}">
					<c:choose>
						<c:when test="${ameGroup.fixLine.valueMaxPerMonth.gratuit}">
							-
						</c:when>
						<c:otherwise>
						  <c:choose>
							  <c:when test="${ameGroup.fixLine.valueMaxPerMonth eq prixTotalPourOfrirBox}">
							  -29.99
							  </c:when>
							  <c:otherwise>
							  -${ameGroup.fixLine.valueMaxPerMonth}&euro;
							  </c:otherwise>
						  </c:choose>
					    </c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
				de <c:if test="${not ameGroup.fixLine.valueMinPerMonth.gratuit}"></c:if>${ameGroup.fixLine.valueMinPerMonth}&euro;
				 &agrave; -${ameGroup.fixLine.valueMaxPerMonth}&euro;
			          </c:otherwise>
			</c:choose>
		</strong>
	</c:otherwise>
</c:choose>
