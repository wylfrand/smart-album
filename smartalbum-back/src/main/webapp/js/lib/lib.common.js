jQuery(document).ready(function() {  	
    common.init();
});

var common = (function() {

    var thumbnailSpacing = 15;
    var documentRootURL = null;

    init = function(){
	documentRootURL = getContextRoot();
    };
    getThumbnailSpacing = function(){
	
        return thumbnailSpacing;
    };
    getDocumentRootURL = function(){
        return getContextRoot();
    };
    exists = function(){
	return this.length > 0;
    };
    
    setHeader = function(xhr) {
	    xhr.setRequestHeader("Accept", "application/json");
	}
    
    showPopupWarning = function (messageId) {

	// $("#iframe").fancybox({
	$.fancybox({
		'autoScale' : false,
		'centerOnScroll' : true,
		'hideOnOverlayClick' : false,
		'autoDimensions' : true,
		'height' : 'auto',
		'scrolling' : 'no',
		'width' : '480',
		'showCloseButton' : true,
		'padding' : 0,
		'overlayOpacity' : 0,
		'shadow' : 0,
		'transitionIn' : 'elastic',
		'transitionOut' : 'elastic',
		'shadowCssClass' : 'popin-bg',
		'content' : $("#" + messageId).html()
	});
    }
    
    showPopupWarningContent = function (pupupContent) {

	// $("#iframe").fancybox({
	$.fancybox({
		'autoScale' : false,
		'centerOnScroll' : true,
		'hideOnOverlayClick' : true,
		'autoDimensions' : false,
		'height' : 'auto',
		'scrolling' : 'no',
		'width' : '480',
		'showCloseButton' : false,
		'padding' : 0,
		'overlayOpacity' : 0,
		'shadow' : 0,
		'transitionIn' : 'elastic',
		'transitionOut' : 'elastic',
		'shadowCssClass' : 'popin-bg',
		'content' : pupupContent
	});
    }
    
    closeSimpleModal = function () {
	$.modal.close();
    }
    
    deleteAlbum = function(index, objectToDeleteId) {
	url = getContextRoot() + '/albumsControllerRest/ajax/deleteAlbum.json?albumId='+index;
	$.fancybox.showLoading();
	$.ajax({
		type : "POST",
		dataType : "json",
		contentType : "application/json",
		data : {
			albumId : index
		},
		url : url,
		success : function(data) {
			$.fancybox.hideLoading();
//			window.location.href = getContextRoot()
//					+ '/shelvesController/publicShelves.html';
			$("#"+objectToDeleteId).fadeOut();
			$.fancybox.close();
		},
		error : function(request, status, err) {
			$.fancybox.hideLoading();
			alert("Impossible de supprimer l'album...");
		}
	});// Fin ajax
}
    
    deleteShelf = function (index, objectToDeleteId) {
	alert("1-"+objectToDeleteId);
	url = getContextRoot() + '/shelvesController/ajax/deleteShelf.json?shelfId='+index;
	$.fancybox.showLoading();
	$.ajax({
		type : "POST",
		data : {
			shelfId : index
		},
		dataType : "json",
		contentType : "application/json",
		url : url,
		success : function(data) {
			$.fancybox.hideLoading();
//			window.location.href = getContextRoot()
//					+ '/shelvesController/myShelves.html';
			$("#"+objectToDeleteId).fadeOut();
			$.fancybox.close();
			;
		},
		error : function(request, status, err) {
			$.fancybox.hideLoading();
			alert("Impossible de supprimer l'album...");
		}
	});// Fin ajax
}
    
    showBlockInLayer = function(blockId) {
	$('#' + blockId).modal({
		opacity : 80,
		overlayCss : {
			backgroundColor : "#000"
		},
		persist : true,
		overlayClose : true,
		close : true,
		onOpen : function(dialog) {
			dialog.overlay.fadeIn('fast', function() {
				dialog.data.hide();
				dialog.container.fadeIn('fast', function() {
					dialog.data.slideDown('fast');
				});
			});
		},
		// Closing animations
		onClose : function(dialog) {
			dialog.data.fadeOut('fast', function() {
				dialog.container.hide('fast', function() {
					dialog.overlay.slideUp('fast', function() {
						$.modal.close();
					});
				});
			});
		}
	});
}
    
    getContextRoot = function() {
	var protocol = window.location.protocol;
	var url = window.location.href;
	var host = window.location.host;
	var lastPart = url.split(host);
	return protocol + "//" + window.location.host + "/"
			+ lastPart[1].split('/')[1];
    };
    
    showStandardWarning = function(popupId,confirmfunction, messageHeader,messageBody){
	var target = $("#"+popupId);
	var popupContent = target.html();
	popupContent = popupContent.replace("#confirmfunction", confirmfunction).replace("#messageHeader", messageHeader).replace("#messageBody", messageBody);
	showPopupWarningContent(popupContent);
    }
    
    printFullImageInLayerFact = function()
    {
    	$.fancybox.showLoading();
    	var img = new Image();
    	var imageSource = $('#imgFactToCopy img');
    	var imgSrc = imageSource.attr("src").replace("_medium","fullImage");
    	img.onload = function()
        {
        	$.fancybox.hideLoading();
         	$("#fullImgToPrint").html(img);
         	showBlockInLayer("fullImgToPrint");
        };
        img.height='100%';
        img.src = imgSrc;
        img.onclick=function() {closeSimpleModal(); }
        return false;
    }
    
    editAlbum = function (albumId) {
	window.location.href = getContextRoot() + '/albumsController/editAlbum/'
			+ albumId + '.html';
	;
}

      printDataTableStandardPopup = function(messageHeader, messageBody, confirmfunction, compteurIndex) {
	var htmlContent = $("#standardPupupTemplateId").html();
	htmlContent = htmlContent.replace("#messageHeader",messageHeader);
	htmlContent = htmlContent.replace("#messageBody",messageBody);
	htmlContent = htmlContent.replace("#serverBaseUrl",getDocumentRootURL());
	htmlContent = htmlContent.replace("#confirmfunction",confirmfunction);
	htmlContent = htmlContent.replace("#compteurIndex",compteurIndex);
	showPopupWarningContent(htmlContent);
       }
      
      buildRichTextEditor = function (elementId){

		 $('textarea#'+elementId).tinymce({
	         // Location of TinyMCE script
		 //${pageContext.request.contextPath}
		 //documentRootURL
	         script_url : documentRootURL+"/js/jquery/tiny_mce/tiny_mce.js",
	          
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
	    };
      
	    // Enregistre le nouveau contenu
	    saveRichText = function (messageId, entityType){
		var messageHTML = $('textarea#' + messageId).html();
                var urlMessage = documentRootURL
		+ '/messageHTMLController/ajax/update.json';
                
                var longDescriptionFormObj = {
			id : messageId,
			description : messageHTML,
			entityType : entityType
                };
	
                var longDescriptionFormObjStr = JSON.stringify(longDescriptionFormObj);
                
                $.fancybox.showActivity();
                $.ajax({
                    type: 'POST',
                    url: urlMessage,
                    cache: false,
                    data: longDescriptionFormObjStr,
                    contentType : "application/json",
                    success: function(data){
                    	alert("Sauvegarde effectué avec succès");
                         $.fancybox.hideActivity();
                    }
                });
            };
	return {
		init : init,
		exists : exists,
		getThumbnailSpacing : getThumbnailSpacing,
		getDocumentRootURL : getDocumentRootURL,
		getContextRoot : getContextRoot,
		showBlockInLayer : showBlockInLayer,
		showPopupWarning : showPopupWarning,
		deleteAlbum : deleteAlbum,
		deleteShelf : deleteShelf,
		closeSimpleModal : closeSimpleModal,
		showStandardWarning : showStandardWarning,
		showPopupWarningContent : showPopupWarningContent,
		printFullImageInLayerFact : printFullImageInLayerFact,
		editAlbum : editAlbum,
		setHeader : setHeader,
		buildRichTextEditor : buildRichTextEditor,
		saveRichText : saveRichText,
		printDataTableStandardPopup : printDataTableStandardPopup,
	};
})();
