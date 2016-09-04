<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
	<div class="search-block">
<form:form modelAttribute="searchForm" id="appliSearchForm" name="appliSearchForm" class="ajust-search-form"  method="post" action="${pageContext.request.contextPath}/searchController/search.html">
		<div class="search-div">
			<form:input path="searchQuery" value="${searchForm.searchQuery}" styleClass="search-input"></form:input>
			<a styleClass="search-find-button"  onclick="javascript: searchImages()"><img height="20" border="0" width="20" alt="" src="<c:url value='/images/b_search.png'/>"/></a>
		</div>
		<div id="searchOptions" class="search-options">
				<div class="search-options-div1">
				    <strong>Critères de recherche&nbsp;:&nbsp;</strong><br/>
				    <form:checkbox path="seachInMyAlbums" value="${searchForm.seachInMyAlbums}"></form:checkbox>
				    Dans mes albums<br/>
				    <form:checkbox path="searchInShared" value="${searchForm.searchInShared}"></form:checkbox>
				     Dans les albums partagés
					</div>
				<div class="search-options-div2">
					<div>
						<c:forEach items="${searchForm.options}" var="option"
						varStatus="compteurSearchImg">
						<form:label path="options[${compteurSearchImg.index}].name">${option.label}</form:label>
						<form:checkbox path="options[${compteurSearchImg.index}].selected" value="${option.selected}"/>
						|
					</c:forEach>
				</div>
				</div>
		</div>
		<div class="searchResult">
			<%@ include file="/WEB-INF/jsp/page/search/result/result.jsp"%>
		</div>
</form:form>
	</div>
