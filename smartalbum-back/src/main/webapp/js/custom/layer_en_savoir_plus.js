$(function() {
	// Initialisation des onglets du layer
//	$(function() {
//		$( "#tabs" ).tabs();
//	});
	
//	$("#layer_multipack_ctn").dialog({
//		dialogClass: "no-close",
//		autoOpen: false,
//		height: 600,
//		width: 350,
//		modal: true,
//        open: function(event, ui) {
//    			// Bouton Fermer
//	        	$('.popinClose').click(function(){
//	                $('#layer_multipack_ctn').dialog('close');
//	                return false;
//	            });
//	        	
//	        	// Hide the modal dialog when someone clicks outside of it.
//	        	$("body").mouseup(function (e){
//    			    var container = $("#layer_multipack_ctn");
//    			    if (!container.is(e.target) // if the target of the click isn't the container...
//    			        && container.has(e.target).length === 0) // ... nor a descendant of the container
//    			    {
//    			    	$("#layer_multipack_ctn").dialog( "close" );
//    			    }
//	        	});
//    		}
//	});
//	
//	$(".open-layer-en-savoir-plus").click(function() {
//		$("#layer_multipack_ctn").dialog("open", { closeOnEscape: true});
//		return false;
//	});
//	
//	$(".mentionsLegalesLayer").click(function() {
//		$("#legals").toggle('drop');
//		return false;
//	});
//	
	$('.togglerMultipack').click(function(e){
		var current_element = $(this);
		var content_id = current_element.attr("data-content");
		if($("#"+content_id).hasClass("openState")){
			$("#"+content_id).toggle('drop').removeClass("openState").addClass("closeState");
			current_element.removeClass("openState").addClass("closeState");
			if(current_element.parents().is("#howtoget")){
				current_element.closest("div[class='tabContainer_item']").find("a.infoLayer_togglerLink").removeClass("openState").addClass("closeState");
			}
		}else {
			$("#"+content_id).toggle('drop').removeClass("closeState").addClass("openState");
			current_element.removeClass("closeState").addClass("openState");
			if(current_element.parents().is("#howtoget")){
				current_element.closest("div[class='tabContainer_item']").find("a.infoLayer_togglerLink").removeClass("closeState").addClass("openState");
			}
		}
		
		return false;
	});
	
	$('a.infoLayer_togglerLink').click(function(e){
		var current_element = $(this);
		var content_id = current_element.attr("data-content");
		if($("#"+content_id).hasClass("openState")){
			$("#"+content_id).toggle('drop').removeClass("openState").addClass("closeState");
			current_element.removeClass("openState").addClass("closeState");
		}else {
			$("#"+content_id).toggle('drop').removeClass("closeState").addClass("openState");
			current_element.removeClass("closeState").addClass("openState");
		}
		
		return false;
	});
});