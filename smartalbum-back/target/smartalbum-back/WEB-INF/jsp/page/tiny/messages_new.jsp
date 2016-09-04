<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>

<div style="text-align: center;" id="messageHTMLLayer${messageHTML.id}" class="blogAjustHTMLCCC">
    <form method="post" action="somepage" id="form${messageHTML.id}">
	    <span class="info">(Saisissez ici la description de l'image)</span></label>
        	<textarea id="textAreaMessage${messageHTML.id}" name="textAreaMessage" class="tinymce">${messageHTML.htmlDescription}</textarea>
        <a href="#" onclick="javascript:submitRichText('${messageHTML.id}');" class="classname">Enregistrer</a>
        <div class="clear">&nbsp;</div>
        </form>
</div>