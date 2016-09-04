<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jsp/common/include.jsp" %>

<head>
      
    <!-- ##### Custom css/js ##### -->
    <link rel="STYLESHEET" type="text/css" href="<c:url value='/css/maam.css' />" />
    <!-- ##### Fin Custom css/js ##### -->
        
    <script type="text/javascript"> 
    function doSelectLigne(){
    	var label = $("input[name='label']:checked").val();
    	if($("input[name='label']:checked").length > 0){
	    	$.ajax({
	            type    : "POST",
	            cache   : false,
	            url     : "<c:url value='/ajax/choose-ligne.html' />",
	            data    : {'msisdn': label},
	            success : function(data){
	               $.fancybox.close();
	               document.location.href = "<c:url value='/'/>";
	            }
	        });
    	}
    }
    </script>
</head>

<body style="text-align: left">
    <div style="border-style:solid; border-width:4px; border-color: #d8d8d8;">
    	<c:if test="${not empty multi_ligne_mode_select and multi_ligne_mode_select}">
    		<a href="" onClick="javascript:$.fancybox.close();return false;"><img alt="fermer" src="<c:url value='/img/btn_fermer.gif'/>" style="float: right; border-style: solid; height: 37px;"/></a>
    		<div class="clearFix"></div>
        </c:if>
        
        <div style="text-align: left; padding: 20px 30px;">
	        <b>Veuillez s&eacute;l&eacute;ctionner la ligne sur laquelle vous souhaitez r&eacute;aliser cet acte:</b> <br />
	        <form name="select_form" id="select_form" method="POST">
	            <c:forEach var="ligne" items="${sfr_user_lignes}" varStatus="sta">
	              <input type="radio" id="label" name="label" value="${ligne}" ${sta.index eq '0'? '': ''}> <maam:telephone label="${ligne}"/><br />
	            </c:forEach>
	        </form>
	        
	        <div class="confirmBtnPanel">
	            <a href="#" onClick="javascript:doSelectLigne();" class="btnLink128" style="margin-left: 10px; text-decoration: none; padding-right: 20px; padding-bottom: 10px">Continuer</a>
	        </div>
	    </div>
	</div>
</body>