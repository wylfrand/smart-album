<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
<!-- Wrapper -->
<!-- tinymce -->
       <script type="text/javascript" src="<c:url value='/js/jquery/tiny_mce/jquery.tinymce.js' />"></script>
        <script type="text/javascript">
        $(document).ready(function() 
            { 
                    $('textarea.tinymce').tinymce({
                        // Location of TinyMCE script
                        script_url : '<c:url value='/js/jquery/tiny_mce/tiny_mce.js' />',
                         
                        // General options
                        theme : "advanced",
                        plugins : "safari,style,layer,table,advhr,advimage,advlink,iespell,inlinepopups,insertdatetime,contextmenu,paste,directionality,noneditable,visualchars,nonbreaking,xhtmlxtras",
                        theme_advanced_buttons1 : "newdocument,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,styleselect,formatselect,fontselect,fontsizeselect",
                        theme_advanced_buttons2 : "cut,copy,paste|,search,replace,|,bullist,numlist,|,outdent,indent,blockquote,|,undo,redo,|,link,unlink,image,code,|,insertdate,inserttime,|,forecolor,backcolor",
                        theme_advanced_buttons3 : "tablecontrols,|sub,sup,|,charmap,iespell,advhr,|,ltr,rtl,|insertlayer,moveforward,movebackward,absolute|visualchars,nonbreaking",
                        theme_advanced_toolbar_location : "top",
                        theme_advanced_toolbar_align : "left",
                        theme_advanced_resizing : true,

                        // Drop lists for link/image/media/template dialogs
                        template_external_list_url : "lists/template_list.js",
                        external_link_list_url : "lists/link_list.js",
                        external_image_list_url : "lists/image_list.js"
                    });
                    
                    $(".modifierDescriptionImage").click(function() {
                    	$(".blogAjustHTMLCCC").toggle('slow');
                	});

                	$(".blogAjustHTMLCCC").toggle('slow');

                	$(".modifierDescription").click(function() {
                		$(".blogAjustHTMLCCC").toggle('slow');
                	});
            });
        </script>
<!-- End Header -->
<!-- Main -->

<div id="main">
	<c:forEach items="${pictureDetail.image.messagesHTML}" var="messageHTML" varStatus="compteurMESSAGE">
		<c:if test="${messageHTML.message_type eq 'LONGDESCRIPTION'}">
			<c:set var="blogMessageHTML" value="${messageHTML}"/>
		</c:if>
	</c:forEach>
	<div class="center">
		<!-- Content -->
		<div id="content" class="col col-2">
			<div class="entry" style="border: none">

				<!-- Image and its options -->
				<div>
				<table>
					<tr>
					<td><img src="<c:url value='/imagesController/paintImage${pictureDetail.image.fullPath}/_medium.html'/>" alt="" style="max-width: 772px"/></td>
						<td><%@ include file="/WEB-INF/jsp/page/images/options.jsp" %></td>
					</tr>
				</table>
				</div>
				<!-- End Image and its options -->
				<!-- Image html description -->
				<div class="project-information">
					<div id="printedMessage">${blogMessageHTML.htmlDescription}</div>
					<a href="<c:url value='/albumsController/showAlbum/${pictureDetail.image.album.id}.html'/>">Retour à la liste</a> <span>|</span> <a id="${blogMessageHTML.id}" href="#modifierDescriptionImage" class="modifierDescriptionImage">Modifier le message</a>
				</div>
				<!-- End Image html description -->
			</div>
			<c:set var="messageHTML" value="${blogMessageHTML}"/>
			<%@ include file="/WEB-INF/jsp/page/tiny/messages.jsp" %>
		</div>
		<!-- End Content -->
		<div class="cl">&nbsp;</div>
	</div>
</div>
<!-- Image comment -->
<div class="center">
	<!-- Content -->
	<c:set var="comments" value="${pictureDetail.image.comments}"/>
	<%@ include file="/WEB-INF/jsp/page/images/body/comments.jsp" %>
	<!-- End Content -->
	<div class="cl">&nbsp;</div>
</div>
<!-- End Image comment -->

</div>

<!-- End Wrapper -->