<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>

	<script type="text/javascript"
	src="<c:url value='/js/tiny_mce/config.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/js/tiny_mce/richtext.js' />"></script>	

<div style="text-align: center;" id="messageHTMLLayer${messageHTML.id}" class="blogAjustHTMLCCC">
	<c:set var="simpleQuote">'</c:set>
	<c:set var="simpleReplace">\'</c:set>
	<c:set var="theMessageToPrint">${fn:replace(messageHTML.htmlDescription,simpleQuote,simpleReplace)}</c:set>
	
    <form method="post" action="somepage" id="form${messageHTML.id}">
	    <input type="hidden" name="text"> 
        <table border="0" width="20%" cellpadding="0" cellspacing="0" id="content-table">
            <tr>
				<td id="TextArea${messageHTML.id}" class="tinyMce"> 
				
					<script language="JavaScript">
						initRTE('${theMessageToPrint}', '');	
					</script>	
<%-- 					<textarea id="textAreaId" name="detailedDescription" class="tinymce" >${theMessageToPrint}</textarea> --%>
									
				</td>
			</tr>
            <tr>
                <th class="sized bottomleft"></th>
                <td id="tbl-border-bottom">&nbsp;</td>
                <th class="sized bottomright"></th>
            </tr>
        </table>
        <a href="#" onclick="javascript:submitRichText('${messageHTML.id}','${entityType}');" class="classname">Enregistrer</a>
        <div class="clear">&nbsp;</div>
        </form>
</div>

