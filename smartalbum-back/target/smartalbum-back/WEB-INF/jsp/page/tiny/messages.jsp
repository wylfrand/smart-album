<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>

<div style="text-align: center;" id="messageHTMLLayer${messageHTML.id}" class="blogAjustHTMLCCC">
	<c:set var="simpleQuote">'</c:set>
	<c:set var="simpleReplace">\'</c:set>
	<c:set var="theMessageToPrint">${fn:replace(messageHTML.htmlDescription,simpleQuote,simpleReplace)}</c:set>
	
    <form method="post" action="somepage" id="form${messageHTML.id}">
	    <input type="hidden" name="text"> 
        <table border="0" width="20%" cellpadding="0" cellspacing="0" id="content-table">
<!--             <tr> -->
<!--                 <th rowspan="3" class="sized"> -->
<%--                 <img src="<c:url value='/img/shared/side_shadowleft.jpg' />" width="20" height="300" alt="" /> --%>
<!--                 </th> -->
<!--                 <th class="topleft"></th> -->
<!--                 <td id="tbl-border-top">&nbsp;</td> -->
<!--                 <th class="topright"></th> -->
<%--                 <th rowspan="3" class="sized"><img src="<c:url value='/img/shared/side_shadowright.jpg' />" width="20" height="300" alt="" /></th> --%>
<!--             </tr> -->
            <tr>
				<td id="TextArea${messageHTML.id}" class="tinyMce"> 
					<script language="JavaScript">
						initRTE('${theMessageToPrint}', '<c:url value="/templates/netbased/css/style.css"/>');	
					</script>					
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