<security:authorize access="isAuthenticated()">
<%-- <span style="float : left"><strong>Salut</strong> [${user.username}]!</span> --%>
<li <c:if test="${currentPage == 'ACCUEUIL'}">class="active"</c:if>>
	<a href="<c:url value='/controller/initUploadedFiles.html' />"
	onclick="javascript: showActivity();"> Nouveaux documents </a>
</li>
</security:authorize>
<li
	<c:if test="${currentPage == 'ETAGERES_PUBLIQUES' || currentPage == 'ACCUEUIL'}">class="active"</c:if>>
	<a onclick="javascript: showActivity();"
	href="<c:url value='/shelvesController/publicShelves.html' />">
		Publique </a>
</li>
<security:authorize access="isAuthenticated()">

<li <c:if test="${currentPage == 'MES_ETAGERES'}">class="active"</c:if>>
	<a href="<c:url value='/shelvesController/myShelves.html' />"
	onclick="javascript: showActivity();"> Mon espace </a>
</li>
<li <c:if test="${currentPage == 'RECHERCHE'}">class="active"</c:if>>
	<a href="<c:url value='/searchController/index.html' />"
	onclick="javascript: showActivity();"> Recherche </a>
</li>
<li><a onclick="javascript:logOut();">Se déconnecter</a></li>
</security:authorize>
<security:authorize access="!isAuthenticated()">
	<li><a href="<c:url value='/usersController/open-creation.html'/>">S'enregistrer</a></li>
	<li><a onclick="javascript:logOut();">Se connecter</a></li>
</security:authorize>
<!-- <li><input type="button" value="Déconnexion" -->
<!-- 	onclick="javascript:logOut();" /></li> -->