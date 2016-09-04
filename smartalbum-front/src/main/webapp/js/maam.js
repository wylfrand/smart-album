$(document).ready(function() {
	//initialize the tooltip
	$('.aa').tooltip();
	
	$(".addLine, .addLineMPVX").keypress(function (e) {
	     //allow only numbers, backspace, delete, home, end, left, right, Ctrl + A, Ctrl + C, Ctrl + V
		if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57) && !(e.which == 97 && e.ctrlKey === true)
			&& !(e.which == 99 && e.ctrlKey === true) && !(e.which == 118 && e.ctrlKey === true)) {
			return false;
		}
	});
	
	
	$('.en-savoir-plus').fancybox({
		'hideOnOverlayClick' : true,
		'autoDimensions' : false,
		'scrolling' : 'yes',
		'showCloseButton' : true,
		'padding' : 0,
		'overlayOpacity' : 0,
		'shadow' : 0,
		'type':'iframe',
		'width': 950,
		'height' : 500,
		'centerOnScroll' : true,
		'shadowCssClass' : 'popin-bg',
		});
	
	$('#btn-aide').fancybox({
		'hideOnOverlayClick' : true,
		'autoDimensions' : false,
		'scrolling' : 'yes',
		'showCloseButton' : true,
		'padding' : 0,
		'overlayOpacity' : 0,
		'shadow' : 0,
		'type':'iframe',
		'width': 950,
		'height' : 500,
		'centerOnScroll' : true,
		'shadowCssClass' : 'popin-bg',
		});
	
	$(".deleteLineIcon").click(function() {
		//initialize the status of links and buttons
		$("#new-reduction-button").remove();
		$("#deleteLineLink").show();
		$("#deleteLineLink").removeClass("deleteGroup");
		$("#deleteLineLink").removeClass("seeNewReduction");
		
		var jLink = $(this);
		var number = jLink.attr("data-number");
		var numberToDisplay = jLink.attr("data-number-with-point");
		var minReduction = jLink.attr("data-valueMinPerMonth");
		var maxReduction = jLink.attr("data-valueMaxPerMonth");
		var totalMinContribution = jLink.attr("data-totalMinContribution");
		var totalMaxContribution = jLink.attr("data-totalMaxContribution");
		var totalMinContributionMPVX = jLink.attr("data-totalMinContributionMPVX");
		var totalMaxContributionMPVX = jLink.attr("data-totalMaxContributionMPVX");
		var isDuo = (jLink.attr("data-isDuo") == "true");
		var isCurrentLineNonContribute = (jLink.attr("data-free") == "true");
		var isBoxFree = (jLink.attr("data-boxFree") == "true");
		var hasNonContributedLine = (jLink.attr("data-hasNonContributedLine") == "true");
		var hasMoreThanTwoLines = (jLink.attr("data-hasMoreThanTwoLines") == "true");
		var isAddams = (jLink.attr("data-addams") == "true");
		
		var deleteLineSimulationURL = $("#deleteLineLink").attr("data-check-simu-delation-url");
		var url = deleteLineSimulationURL + number + ".html";
		var serviceIndisponibleURL = $("#deleteLineLink").attr("data-service-indisponible-url");
		
		var text = "";
		var reduction = "";
		if (minReduction == maxReduction) {
			reduction = maxReduction + "&euro;";
		} else {
			reduction = "de " + minReduction + " &agrave; " + maxReduction + "&euro;";
		}
		
			$.post(url, function(data) {
				//if the line is deleted successfully from SimulationInfos, we launch the simulation and confirmation
				if (!data.result) {
					
					if(data.error == "ELIAME_INELIGIBLE"){
					
					//5.B Retrait d’une ligne DUO (MPVX) ou d’un groupe de 2 lignes(ADDAMS ou MPVX)
						reduction = getReduction(isBoxFree,isAddams,totalMinContribution,totalMaxContribution,totalMinContributionMPVX,totalMaxContributionMPVX);
						text = '<strong>Si vous retirez la ligne ' + numberToDisplay
							+ ', votre Multi-Packs sera supprim&eacute;.</strong><br/>'
							+ 'Vous perdrez le b&eacute;n&eacute;fice de votre remise soit <span class="price-layer">' + reduction + '</span>/mois.';
					
					$("#deleteLineLink").after("<input type='button' value='Confirmer' class='new-delete-groupe' id='new-reduction-button' onclick='javascript:deleteGroupe();'/>");
					$("#deleteLineLink").hide();
					$("#deleteLineLink").addClass("seeNewReduction");
					
					$("#contenu-layer").html(text);
					showDeleteLineLayer();
					}
					else
					{
						window.location.href = serviceIndisponibleURL;
					}
				}
				else
				{
					var reductionGroup = getReduction(isBoxFree,isAddams,totalMinContribution,totalMaxContribution,totalMinContributionMPVX,totalMaxContributionMPVX);
					text = getPopupText(hasMoreThanTwoLines,isDuo,numberToDisplay,isCurrentLineNonContribute,isAddams,hasNonContributedLine,reduction,reductionGroup);
					
					$("#deleteLineLayer").attr("data-number", number);
					$("#contenu-layer").html(text);
					showDeleteLineLayer();
					
				}
			});
		
	});
	
	//initialize the placeholder for IE
	$('[placeholder]').focus(function() {
	  var input = $(this);
	  if (input.val() == input.attr('placeholder')) {
	    input.val('');
	    input.removeClass('placeholder');
	  }
	}).blur(function() {
	  var input = $(this);
	  if (input.val() == '' || input.val() == input.attr('placeholder')) {
	    input.addClass('placeholder');
	    input.val(input.attr('placeholder'));
	  }
	}).blur();
	
	$('[placeholder]').parents('form').submit(function() {
	  $(this).find('[placeholder]').each(function() {
	    var input = $(this);
	    if (input.val() == input.attr('placeholder')) {
	      input.val('');
	    }
	  });
	});
});

function getReduction(isBoxFree,isAddams,totalMinContribution,totalMaxContribution,totalMinContributionMPVX,totalMaxContributionMPVX)
{
	var reduction="0";
	if (isBoxFree) {
		reduction = "29,99&euro;";
	} else {
		if (isAddams) {
			if (totalMinContribution == totalMaxContribution) {
				reduction = totalMaxContribution + "&euro;";
			} else {
				reduction = "de " + totalMinContribution + " &agrave; " + totalMaxContribution + "&euro;";
			}
		} else {
			if (totalMinContributionMPVX == totalMaxContributionMPVX) {
				reduction = totalMaxContributionMPVX + "&euro;";
			} else {
				reduction = "de " + totalMinContributionMPVX + " &agrave; " + totalMaxContributionMPVX + "&euro;";
			}
		}
	}
	return reduction;
}

function getPopupText(hasMoreThanTwoLines,isDuo,numberToDisplay,isCurrentLineNonContribute,isAddams,hasNonContributedLine,reduction,reductionGroup)
{
	var text = "";
	//5.A Retrait d’une ligne non DUO (MPVX) et d’un groupe de plus de 2 lignes (ADDAMS ou MPVX)
	if (hasMoreThanTwoLines && !isDuo) {
		text = '<strong>Si vous retirez la ligne ' + numberToDisplay + ',</strong><br/>'
			+ 'vous perdrez la remise associ&eacute;e soit <span class="price-layer">' + reduction + '</span>/mois.';
	}
	
	//5.B Retrait d’une ligne DUO (MPVX) ou d’un groupe de 2 lignes(ADDAMS ou MPVX)
	if (!hasMoreThanTwoLines || isDuo) {
		reduction=reductionGroup;
		text = '<strong>Si vous retirez la ligne ' + numberToDisplay
			+ ', votre Multi-Packs sera supprim&eacute;.</strong><br/>'
			+ 'Vous perdrez le b&eacute;n&eacute;fice de votre remise soit <span class="price-layer">' + reduction + '</span>/mois.';
		
		//add a class to the link for later use : to delete the group instead of a line
		$("#deleteLineLink").addClass("deleteGroup");
	} else if (hasMoreThanTwoLines && isCurrentLineNonContribute) {
		//5.C Retrait d’une ligne  non-contributrice d’un groupe ADDAMS de plus de 2 lignes
		text = '<strong>Si vous retirez la ligne <span id="numberToDelete"></span></strong> vos remises restent inchang&eacute;es.';
	}
	
	
	//5.D : Retrait d’une ligne contributrice d’un groupe ADDAMS avec des ligne non-contributrice
	if (hasNonContributedLine && !isCurrentLineNonContribute && isAddams && hasMoreThanTwoLines) {
		text = '<strong>Si vous retirez la ligne ' + numberToDisplay + ',</strong><br/>'
			+ 'vous perdrez la remise associ&eacute;e soit <span class="price-layer">' + reduction + '</span>/mois.<br/>'
		 + 'Une autre ligne du groupe pourra vous faire b&eacute;n&eacute;fice de remises.';
			
		$("#deleteLineLink").after("<input type='button' value='Voir vos nouvelles remises' class='new-reduction' id='new-reduction-button' onclick='javascript:deleteLine();' />");
		$("#deleteLineLink").hide();
		$("#deleteLineLink").addClass("seeNewReduction");
	}
	else if(hasNonContributedLine && !isCurrentLineNonContribute && !isAddams)
	{
		
	}
	return text;
}

function displayMentionsLegales(){
    $("#legalBox").slideToggle(350);
}

function clearErrorMessage(id) {
	$("#add_new_line_input_" + id).removeAttr('placeholder');
	$("#erreurFormeAddams_" + id).hide();
	if ($("#add_new_line_input_" + id).hasClass("hasError")) {
 		var selector = "#add_new_line_row_" + id + " .texte-erreur";
		$("#add_new_line_input_" + id).removeClass("hasError");
		$(selector).text("");
		$("#add_new_line_input_" + id).val($("#add_new_line_input_" + id).attr("data-number"));
		document.getElementById("add_new_line_input_" + id).selectionStart = $("#add_new_line_input_" + id).val().length;
		document.getElementById("lastAddedNumber").value = 0;
	}
}

function clearErrorMessageMPVX() {
	$("#add_new_line_input_1").removeAttr('placeholder');
	$('#erreurFormeMpvx').hide();
	if ($("#add_new_line_input_1").hasClass("hasError")) {
		$(".msg-erreur").html("");
		$("#add_new_line_input_1").removeClass("hasError");
		$("#add_new_line_input_1").val($("#add_new_line_input_1").attr("data-number"));
		document.getElementById("add_new_line_input_1").selectionStart = $("#add_new_line_input_1").val().length;
	} else if ($("#add_new_line_input_1").hasClass("addSuccess")) {
		$("#add_new_line_input_1").val($("#add_new_line_input_1").attr("data-number"));
		document.getElementById("add_new_line_input_1").selectionStart = $("#add_new_line_input_1").val().length;
	}
}

function showDeleteLineLayer() {
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
		'shadowCssClass' : 'popin-bg',
		'content' : $("#deleteLineLayer").html(),
		'onStart'		: function() {
        	$("#fancybox-outer").css('margin-left', '130px');
        	
        			}
	});
}

function deleteLine() {
	if ($("#deleteLineLink").hasClass("deleteGroup")) {
		//delete group 
		var deleteGroupURL = $("#deleteLineLink").attr("data-delete-group-url");
		
		window.location.href = deleteGroupURL;
	} else {
		var deleteLineSimulationURL = $("#deleteLineLink").attr("data-delete-line-url");
		var validDeleteLineURL = $("#deleteLineLink").attr("data-validate-delete-line-url");
		var confirmDeleteLineURL = $("#deleteLineLink").attr("data-confirmation-url");
		var simulationURL = $("#deleteLineLink").attr("data-simulation-url");
		var deleteGroupURL = $("#deleteLineLink").attr("data-delete-group-url");
		var serviceIndisponibleURL = $("#deleteLineLink").attr("data-service-indisponible-url");
		
		//delete line
		var number = $("#deleteLineLayer").attr("data-number");
		var url = deleteLineSimulationURL + number + ".html";
		
		$.post(url, function(data) {
			//if the line is deleted successfully from SimulationInfos, we launch the simulation and confirmation
			
			if (data.result) {
				//display the page Simulation
				if ($("#deleteLineLink").hasClass("seeNewReduction")) {
					window.location.href = simulationURL;
				} else {
					//validate the delete and go to the page of confirmation
					$.post(validDeleteLineURL, function(data) {
						if (data.result) {
							//5.D : see new reduction
							window.location.href = confirmDeleteLineURL;
						} else {
							
							if (data.error == "ELIAME_INELIGIBLE") {
								// MAAM-476 Eliame ne renvoit aucune proposition lors de la Simulation
								// alors on supprime le groupe !
								window.location.href = deleteGroupURL;
							}
							
						}
					});
				}
			} else if (data.error == "CACHE_ERROR") {
				// On ecrit cette condition pour plus de lisibilite
				window.location.href = serviceIndisponibleURL;
				
			} else if (data.error == "ELIAME_TIMEOUT_ERROR") {
				// On ecrit cette condition pour plus de lisibilite
				window.location.href = serviceIndisponibleURL;
				
			} else if (data.error == "ELIAME_INELIGIBLE") {
			// On ecrit cette condition pour plus de lisibilite
				window.location.href = serviceIndisponibleURL;
			}
			else{
				window.location.href = serviceIndisponibleURL;
				
			}
		});
	}
}

function showDeleteGroupLayer() {
	$.fancybox({
		'autoScale' : false,
		'centerOnScroll' : true,
		'hideOnOverlayClick' : true,
		'autoDimensions' : false,
		'height' : '230',
		'scrolling' : 'no',
		'width' : '480',
		'showCloseButton' : false,
		'padding' : 0,
		'overlayOpacity' : 0,
		'shadow' : 0,
		'shadowCssClass' : 'popin-bg',
		'content' : $("#deleteGroupLayer").html(),
		'onStart'		: function() {
        	$("#fancybox-outer").css('margin-left', '130px');
        	
        			}
	});
	
	return false;
}

function goToConfirmPage(deleteGroupURL) {
	if( $("#fancybox-content #checkboxConfirmeDeleteGroupe").is(":checked")){
		window.location.href =deleteGroupURL;
	}
	return false;
}

function deleteGroupe() {
	window.location.href=$("#deleteLineLink").attr("data-delete-group-url");
	return false;
}

function activeImgBouton(){
	if( $("#fancybox-content #checkboxConfirmeDeleteGroupe").is(":checked")){
		$("#fancybox-content #imgRemiseActive").show();
		$("#fancybox-content #imgRemiseInactive").hide();
	}else {
		$("#fancybox-content #imgRemiseActive").hide();
		$("#fancybox-content #imgRemiseInactive").show();
	}
}

function downloadPdf(e,url){
 	
 	if (e.preventDefault) {
 		e.preventDefault();
 		window.location.href = url;
 		return false;
 		}else {
 		e.returnValue = false;
 		window.location.href = url;
 		} 
 }


