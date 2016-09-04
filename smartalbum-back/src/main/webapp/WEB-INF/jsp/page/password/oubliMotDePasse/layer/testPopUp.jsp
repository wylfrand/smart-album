<jsp:include page="../../common/headerLayerReinitMdp.jsp" />
<script language="javascript" type='text/javascript' src="../js/jquery.bubbles.js"></script>
<script src="../js/jquery.reinitMdp.layer.js" type="text/javascript"></script>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<script type="text/javascript">
      $(function(){
        $('a.layerReinitMdp').reinitMdpLayer({
          ajaxPath : '<%=request.getAttribute("layerResetPwdPath")%>'
        });
      });
	</script>
    <div id="ctn_custom" class="clear">
               <div class="boxTitle">
                  <h1>Saisir votre identifiant pour recevoir votre nouveau mot de passe </h1>
               </div>
               <div id="colR" class="wMin"></div>
               <div id="colL"><div class="box boxGrey">
                  <span class="tlcs"></span>
                  <span class="trcs"></span>
                  <span class="blcs"></span>
                  <span class="brcs"></span>
                  <div class="box boxGreyB">
                  <form  id="testPopUpForm" >
 						<a href="" class="arrowO layerReinitMdp">Afficher PopUp</a>
 						<br/>
                  </form>
					 <span class="tlcb"></span>
                     <span class="trcb"></span>
                     <span class="blcb"></span>
                     <span class="brcb"></span>
                  </div></div>
               </div>
            </div>
