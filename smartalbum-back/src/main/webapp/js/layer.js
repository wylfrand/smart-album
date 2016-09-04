
function closeLayer(layerElementId) {
	jQuery("#" + layerElementId).dialog("close");
	return false;
}

function initPageQuit(){
	if(pageQuitEnabled){
		pageQuitPopUpShowed = false;
		jQuery(document).ready(function(){
			jQuery(window).mouseleave(openLayerPageQuit);
		});
	}
}

function openLayerPageQuit(evt) {
	if(!pageQuitPopUpShowed && (evt.layerX <= 0 || evt.layerX > evt.pageX || evt.layerY <= 0 || evt.layerY > evt.pageY)){
	pageQuitPopUpShowed = true;
	return openLayerIframe('//www.sfr.fr/export/bloc/richtext/popin.sortie.signup/default.html', 650, 300);
	}
	
}

function openLayerIframe(iframeUrl, iframeWidth, iframeHeight, title) {
	
	var layer = jQuery('<div id=\"layer-iframe\" class=\"modalLayer\"><br/><p style=\"text-align:center;\"><iframe frameborder="0" border="0" cellspacing="0" marginwidth="0" marginheight="0" src=\"' + iframeUrl + '" width=\"' + iframeWidth + '\" height=\"' + iframeHeight + '\"></iframe><p></div>');
	jQuery(".ecomfixe").prepend(layer);
	return openLayer('layer-iframe', iframeWidth + 65, true, title);
}

function openLayer(layerElementId, layerWidth, layerClosable, title) {
 	jQuery("#" + layerElementId).dialog({
				modal : true,
				width : layerWidth,
				minHeight : 50,
				resizable : false,
				draggable : true,
				closeOnEscape : false,
				title:title,
				opacity:1,
				close : function() {
				},
				open : function(event, ui) {
					var currentDialog = jQuery(this).dialog("widget");
					// If enable, new close button, get old remove mouseover bind and chage look and feel
					var closeLink = currentDialog.find(".ui-dialog-titlebar-close");
					if (layerClosable) {
						closeLink.unbind('mouseover');
					} else {
						closeLink.hide();
					}
					currentDialog.css("z-index", 10);
					
					//un affichage uniforme (sur fond noir) sur tous le parcours
					var overlay = currentDialog.parent().find(".ui-widget-overlay");
					overlay.css("position", "absolute");
					overlay.css("left", "0px");
					overlay.css("top", "0px");
					overlay.css("background", "#000000");
					var content = currentDialog.find(".ui-dialog-content");
					content.css("padding", "0px 0px 0px 0px");
					var title = currentDialog.find(".ui-dialog-title");
					title.css("font-size", "18px");
					title.css("font-family", "sfrbold");
					title.css("margin-left", "18px");
				}
			});
	return false;
}
