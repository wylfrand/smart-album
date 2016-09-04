<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<div id="resultGroup">
	<div class="userInfo">
<!-- Info du groupe -->
		<p><strong>Id : </strong>${userResult.id} </p>
		<p><strong>FirstName : </strong>${userResult.firstName} </p>
		<p><strong>SecondName : </strong>${userResult.secondName} </p>
		<p><strong>Email : </strong>${userResult.email}  </p>
		<p><strong>Login : </strong>${userResult.login} </p>
		<p><strong>Sex : </strong>${userResult.sex} </p>
		<p><strong>Date création : </strong><fmt:formatDate value="${userResult.birthDate}" pattern="${pattern}" /> </p>
		<p><strong>Possède un avatar : </strong>${userResult.hasAvatar} </p>
		<p><strong>Nombre d'étagères : </strong>${fn:length(userResult.shelves)} </p>
		<p><strong>Utilisateur prédéfinie : </strong>${userResult.preDefined} </p>
		<!-- Info des membres -->
		<c:if test="${! empty userResult.shelves }">
			<c:forEach items="${userResult.shelves}" var="shelfResult">
				<p><span id="plusShelf${shelfResult.owner.id}-${shelfResult.id}">+</span>
				<a href="javascript:display('shelfSearch${shelfResult.owner.id}-',${shelfResult.id},'plusShelf${shelfResult.owner.id}-','<c:url value='/searchController/ajax/search/shelf.html' />');">${shelfResult.name} - ${shelfResult.id} (étagère)</a></p>
				<div id="shelfSearch${shelfResult.owner.id}-${shelfResult.id}" style="display: none;">
					<%@ include file="/WEB-INF/jsp/page/search/result/shelfResult.jsp" %> 
				</div>
			</c:forEach>
		</c:if>
	</div>
</div>