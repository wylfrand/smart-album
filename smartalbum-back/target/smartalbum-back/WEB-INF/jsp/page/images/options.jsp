<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<div id="animate${fileMetaId}">
		<input onclick="javascript: rotate('${fileMetaId}');" id="rotation${fileMetaId}" type="button" class="btn" value="Rotation 90°" data-current-angle="0" data-idImg="${fileMetaId}"/>
		<input onclick="javascript: zoomOut('${fileMetaId}');" id="zoomOut${fileMetaId}" type="button" class="btn" value="Agrandir" data-agrandissement="1"/>
		<input onclick="javascript: zoomIn('${fileMetaId}');" id="zoomIn${fileMetaId}" type="button" class="btn" value="Réduire" data-reduction="1"/>
</div>

<script>
     function rotate(fileMetaId){ 
    	  var newAngle = parseInt($("#rotation"+fileMetaId).attr("data-current-angle"))+90;
    	  if(newAngle>350)
    		{
    		  newAngle=0;
    		}
          $('#imageInfosLayer'+fileMetaId+' img').css("transform","rotateZ("+newAngle+"deg)");
          $('#rotation'+fileMetaId).attr("data-current-angle",newAngle);
      };
      
     function zoomOut(fileMetaId) {
    	  var aggrandissement = parseInt($("#zoomOut"+fileMetaId).attr("data-agrandissement"))+1;
    	  if(aggrandissement>10)
    		{
    		  aggrandissement=10;
    		}
    	  
    	  $('#zoomOut'+fileMetaId).attr("data-agrandissement",aggrandissement);
    	  $('#zoomIn'+fileMetaId).attr("data-reduction",aggrandissement);
          $('#imageInfosLayer'+fileMetaId+' img').css("transform","scale(1."+aggrandissement+")");
          
      };
      
      function zoomIn(fileMetaId) {
    	  var reduction = parseInt($("#zoomIn"+fileMetaId).attr("data-reduction"))-1;
    	  if(reduction<0)
    		{
    		  reduction=0;
    		}
    	  
    	  $('#zoomIn'+fileMetaId).attr("data-reduction",reduction);
    	  $('#zoomOut'+fileMetaId).attr("data-agrandissement",reduction);
          $('#imageInfosLayer'+fileMetaId+' img').css("transform","scale(1."+reduction+")");
          
      };
  	</script>
  	<style>
       #box {
           width: 50px;
           height: 50px;
           border: 1px solid grey;
       }
   </style>