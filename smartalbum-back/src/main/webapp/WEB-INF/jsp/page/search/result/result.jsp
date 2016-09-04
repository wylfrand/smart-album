<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<c:choose>
	<c:when test="${not empty searchForm.errors}">
	<h4 class="errors">Erreur dans les critères de recherche : </h4>
		<c:forEach items="${searchForm.errors}" var="error" varStatus="searchError">
			${searchError.index+1} - ${error} <br/>
		</c:forEach>
	</c:when>
	<c:otherwise>
	<h4>Resultat de la recherche : </h4><br/>
		<c:forEach items="${searchForm.options}" var="option" varStatus="compteurSearchImg">
			<c:choose>
			<c:when test="${empty option.searchResult}">
				<p>Aucun ${option.label}</p>
			</c:when>
			<c:when test="${fn:length(option.searchResult) > searchForm.resultLimit}">
				<p>Trop de résultats (${fn:length(option.searchResult)}) pour l'option ${option.label}, merci de bien vouloir préciser votre recherche </p>
			</c:when>
			<c:otherwise>
				<p>Nombre de résultats (${option.label}): ${fn:length(option.searchResult)} ${option.name}</p>
				<c:forEach items="${option.searchResult}" var="result">
					<c:choose>
						<c:when test="${option.name eq 'Shelves' and option.selected}">
							<p><span id="plusResultShelf${result.id}">+</span>
							<a href="javascript:display('shelfResultSearch',${result.id},'plusResultShelf','<c:url value='/searchController/ajax/search/shelf.html' />');">${result.name} - ${result.id} (étagère)</a></p>
							<div id="shelfResultSearch${result.id}" style="display: none;"></div>
						</c:when>
						<c:when test="${option.name eq 'Albums' and option.selected}">
							<p><span id="plusResultAlbum${result.id}">+</span>
							<a href="javascript:display('albumResultSearch',${result.id},'plusResultAlbum','<c:url value='/searchController/ajax/search/album.html' />');">${result.name} - ${result.id} (album)</a></p>
							<div id="albumResultSearch${result.id}" style="display: none;"></div>
						</c:when>
						<c:when test="${option.name eq 'Images' and option.selected}">
							<p><span id="plusResultImage${result.id}">+</span>
							<a href="javascript:display('imageResultSearch',${result.id},'plusResultImage','<c:url value='/searchController/ajax/search/image.html' />');">${result.name} - ${result.id} (image)</a></p>
							<div id="imageResultSearch${result.id}" style="display: none;"></div>
						</c:when>
						<c:when test="${option.name eq 'Tags' and option.selected}">
							<p><a href="#">${result.tag} - ${result.id} </a></p>
						</c:when>
						<c:when test="${option.name eq 'Users' and option.selected}">
							<p><span id="plusResultUser${result.id}">+</span>
							<a href="javascript:display('userResultSearch',${result.id},'plusResultUser','<c:url value='/searchController/ajax/search/user.html' />');">${result.login} - ${result.id} (utilisateur)</a></p>
							<div id="userResultSearch${result.id}" style="display: none;"></div>
						</c:when>
					</c:choose>
				</c:forEach>
			</c:otherwise>
		</c:choose>
		</c:forEach>
</c:otherwise>
</c:choose>
<script type="text/javascript">
	//fonction d'affichage le contenu
	function display(name, id, divPlus, url){
		if ($("#"+name+id).is(':visible')){
			$("#"+name+id).hide();
			$("#"+divPlus+id).html("+");
		} else{
			if (url != null && url != '' && url != undefined){
				displayDetail(name,id, url);
			}
			$("#"+name+id).show();
			$("#"+divPlus+id).html("-");
		}
	}
	
	function displayDetail(name,id, url){
		$.fancybox.showLoading();
		$.ajax({
            url: url,
            data: "id="+id,
            success: function(data){
            	$("#"+name+id).html(data);
            	$.fancybox.hideLoading();
            },
            error : function(request,error){
	 			if (error == 'timeout'){
	 				$.fancybox.hideLoading();
	 				$("#"+name+id).html("Temps d'exécution est dépassé (60s), merci de bien vouloir relancer votre recherche").show();
	 			}
 		  	},
            dataType: "html",
            timeout: 60000,
            type: "POST",
            cache: false
          });
	}
</script>