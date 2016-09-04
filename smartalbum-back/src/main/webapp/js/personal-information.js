var ws_url = __host + 'ajax/check-address.html';
var list_url = __host + 'ajax/list-address.html';
var valid_url = __host + 'ajax/validation-infos.html';
                          
var Onglet_afficher = 1;

var rioAlreadyValidated = false;
var checkAdresseValidated = false;

function goToPageOffre1(){
	$("#coordonnees").submit();
}
function isBlank(str) {
    return (!str || /^\s*$/.test(str));
}
function Affiche(Nom){
	document.getElementById('affiche-contenu-'+Onglet_afficher).className = 'inactif onglet';
	document.getElementById('affiche-contenu-'+Nom).className = 'affiche-contenu-1 onglet';
	document.getElementById('contenu_'+Onglet_afficher).style.display = 'none'; 
	document.getElementById('contenu_'+Nom).style.display = 'block';
	Onglet_afficher = Nom;
}

$(function() {
	rioAlreadyValidated = true;
	
	$("#loading").hide();
	$('#optOut').val(false);
	
	$('#numero_identite').click(function(){
		var tab = parent.document.getElementsByName('newsletter');
		if (tab[0].checked) {
			$('#optOut').val(true);
		} else {				
			$('#optOut').val(false);
			
		}		
	 });

	$('#codePostal').click(function(){
		$("#check_zipcode").removeClass("champ_obligatoire_info");
		$("#check_zipcode").addClass("champ_obligatoire_legende");
		$("#codePostalInvalide").removeClass("fieldInteractionError");
		$("#codePostalInvalide").addClass("fieldInteraction");
		$("#msg-zipcode-obligatioire").removeClass("fieldInteractionError");
		$("#msg-zipcode-obligatioire").addClass("fieldInteraction");	
		$("#commentaires-cp").removeClass("fieldCom");
		$("#commentaires-cp").addClass("fieldComDisp");
		
	});
	$('#codePostal').blur(function(){
		$("#check_zipcode").removeClass("champ_obligatoire_legende");
		$("#check_zipcode").addClass("champ_obligatoire_info");
		$("#commentaires-cp").removeClass("fieldComDisp");
		$("#commentaires-cp").addClass("fieldCom");	
		checkNormAddress();
	});	
	
	$('#adresse').click(function(){
		$("#check_address").removeClass("champ_obligatoire_info");
		$("#check_address").addClass("champ_obligatoire_legende");	
		$("#msg-address-obligatioire").removeClass("fieldInteractionError");
		$("#msg-address-obligatioire").addClass("fieldInteraction");	
		$("#commentaires-adresse").removeClass("fieldCom");
		$("#commentaires-adresse").addClass("fieldComDisp");	
	});
	$('#adresse').blur(function(){
		$("#check_address").removeClass("champ_obligatoire_legende");
		$("#check_address").addClass("champ_obligatoire_info");
		$("#commentaires-adresse").removeClass("fieldComDisp");
		$("#commentaires-adresse").addClass("fieldCom");
		checkNormAddress();
	});	
	
	$('#telephone').click(function(){
		$("#check_phone").removeClass("champ_obligatoire_info");
		$("#check_phone").addClass("champ_obligatoire_legende");	
		$("#msg-phone-obligatioire").removeClass("fieldInteractionError");
		$("#msg-phone-obligatioire").addClass("fieldInteraction");	
		$("#commentaires-tel").removeClass("fieldCom");
		$("#commentaires-tel").addClass("fieldComDisp");
		$("#telephonErrone").removeClass("fieldInteractionError");
		$("#telephonErrone").addClass("fieldInteraction");
	});
	
	$('#telephone').blur(function(){
		$("#check_phone").removeClass("champ_obligatoire_legende");
     	$("#check_phone").addClass("champ_obligatoire_info"); 
		$("#commentaires-tel").removeClass("fieldComDisp");
		$("#commentaires-tel").addClass("fieldCom");	
	});	
	
	$('#ville').blur(function(){	 
		 checkNormAddress();
	});
	

	$('#date_mise_en_service').change(function(){
		var date_mise_en_service = $('#date_mise_en_service').val();
		if (date_mise_en_service.length <= 0){		
			$("#checkDate_service").removeClass("champ_obligatoire_info");
			$("#checkDate_service").addClass("champ_obligatoire");
			$("#msg-date-serv-obligatioire").removeClass("fieldInteraction");
			$("#msg-date-serv-obligatioire").addClass("fieldInteractionError");
		}else {
			$("#checkDate_service").removeClass("champ_obligatoire");
			$("#checkDate_service").addClass("champ_obligatoire_info");
			$("#msg-date-serv-obligatioire").removeClass("fieldInteractionError");
			$("#msg-date-serv-obligatioire").addClass("fieldInteraction");	
		}
	});	
	
	
	$('#rio').blur(function(){
		if(rioAlreadyValidated == true){
			$("#checkrio_msisdn").removeClass("champ_obligatoire");
			$("#checkrio_msisdn_msg").html("");
			$("#checkrio_msisdn_msg").removeClass("txt_checkrio_champ_obligatoire");
			
			$("#checkrio_rio").removeClass("champ_obligatoire");
			$("#checkrio_rio_msg").html("");
			$("#checkrio_rio_msg").removeClass("txt_checkrio_champ_obligatoire");
		}else{
			var msisdn= $('#numero_actuel').val();
			var rio = $('#rio').val();
			var reg = new RegExp(/^(06|07)[0-9]{8}/gi);
			if(rioAlreadyValidated == false && !isNaN(msisdn) && reg.test(msisdn) && rio.length == 12 && (rio.indexOf("02") != 0)){
				//doRioWsValidation(rio, msisdn);
			}
		}
	});
	
	$('#rio, #numero_actuel').change(function(){
		rioAlreadyValidated = false;
		var msisdn= $('#numero_actuel').val();
		var rio = $('#rio').val();
		
		var msisdnOk = doNumeroActuelValidation(msisdn); // Effectue en meme temps la coloration
		if (rio.length == 12 && (rio.indexOf("02") != 0) && msisdnOk && rioAlreadyValidated == false){
			doRioWsValidation(rio, msisdn); // Met à jour la coloration
		}else{
			if(rio.length == 0){
					$("#checkrio_rio").addClass("champ_obligatoire");
					$("#checkrio_rio_msg").html("Ce champ est obligatoire.Merci de le renseigner.");
					$("#checkrio_rio_msg").addClass("txt_checkrio_champ_obligatoire");
			}else{
				if(rio.length != 12 || rio.indexOf("02") == 0){
					$("#checkrio_rio").addClass("champ_obligatoire");
					$("#checkrio_rio_msg").addClass("txt_checkrio_champ_obligatoire");
					$("#checkrio_rio_msg").html("Le code RIO est invalide");	
				}else{
					if(!msisdnOk){
						// cas dejà effectué dans doNumeroActuelValidation
					}else{
						// RAF : RIO deja validé
					}
				}
			}
		}
	});	
	
	
	/*$('#numero_actuel').blur(function(){
		$("#checkrio_msisdn").removeClass("champ_obligatoire");
		$("#checkrio_msisdn_msg").html("");
		$("#checkrio_msisdn_msg").removeClass("txt_checkrio_champ_obligatoire");
		
		doNumeroActuelValidation();
	});	*/
	
	
	$('#listAddress').click(function(){
		
		var adresse = $('#adresse').val();
		var codePostal = $('#codePostal').val();
		var ville    = $('#ville').val();		
		$.ajax({
			type: 'POST',
			url: list_url,
			cache: false,
			data: "adresse=" + adresse + "&codePostal=" + codePostal + "&ville=" +ville,
			error: function (e, t){
				
			},
			success: function(data){	 
					showPopupNormAddress(data,adresse,codePostal,ville);		
			},
			complete: function(){
				$("#loading").hide();
				$("#content2").show();
			}
		});			
	});
	
	
	// On cache les sous-menus
    // sauf celui qui porte la classe "open_at_load" :
    $("ul.subMenu:not('.open_at_load')").hide();
    // On selectionne tous les items de liste portant la classe "toggleSubMenu"

    // et on remplace l'element span qu'ils contiennent par un lien :
    $("li.toggleSubMenu span").each( function () {
        // On stocke le contenu du span :
        var TexteSpan = $(this).text();
        $(this).replaceWith('<a href="" title="Afficher le sous-menu">' + TexteSpan + '</a>') ;
    } ) ;

    // On modifie l'evenement "click" sur les liens dans les items de liste
    // qui portent la classe "toggleSubMenu" :
    $("li.toggleSubMenu > a").click( function () {
        // Si le sous-menu etait deja ouvert, on le referme :
        if ($(this).next("ul.subMenu:visible").length != 0) {
            $(this).next("ul.subMenu").slideUp("normal", function () { $(this).parent().removeClass("open");});
        }
        // Si le sous-menu est cache, on ferme les autres et on l'affiche :
        else {
            $("ul.subMenu").slideUp("normal", function () { $(this).parent().removeClass("open");});
            $(this).next("ul.subMenu").slideDown("normal", function () { $(this).parent().addClass("open");});
        }
        // On empêche le navigateur de suivre le lien :
        return false;
    });
    
    
    $('#username').keypress(function (e) {
        var regex = new RegExp("^[a-zA-Z0-9-_]+$");
        var charCode = (e.which) ? e.which : e.keyCode;
        var str = String.fromCharCode(charCode);
        if (charCode == 192){
        	// grave accent 
        	e.preventDefault();
            return false;
        } 
        if (regex.test(str)) {
            return true;
        }
        if (e.which == 8 || e.which == 0 || (e.which == 97 && e.ctrlKey === true)
    			|| (e.which == 99 && e.ctrlKey === true) || (e.which == 118 && e.ctrlKey === true)) {
    			return true;
    		}
        
        e.preventDefault();
        return false;
    });
});


function doNumeroActuelValidation(msisdn){
	if (msisdn.length <= 0 ){
		$("#checkrio_msisdn").addClass("champ_obligatoire");
		$("#checkrio_msisdn_msg").html("Ce champ est obligatoire.Merci de le renseigner.");
		$("#checkrio_msisdn_msg").addClass("txt_checkrio_champ_obligatoire");
		
		return false;
	} else {
		// Nombre de chiffres
		var reg = new RegExp(/^(06|07)[0-9]{8}/gi);
		if(isNaN(msisdn) || !reg.test(msisdn)){
			$("#checkrio_msisdn").addClass("champ_obligatoire");
			$("#checkrio_msisdn_msg").html("Merci de saisir un numéro de téléphone mobile valide.");
			$("#checkrio_msisdn_msg").addClass("txt_checkrio_champ_obligatoire");
			return false;
		}else{
			$("#checkrio_msisdn").removeClass("champ_obligatoire");
			$("#checkrio_msisdn_msg").html("");
			$("#checkrio_msisdn_msg").removeClass("txt_checkrio_champ_obligatoire");		
			return true;
		}
	}
}

function showPopupNormAddress(data,adresse,codePostal,ville) {	
	 var choix =''; 
	 var i ; 
	 for(i= 0; i < data.length; i++){
		 choix = choix + '<div class="champ-radio"><input type="radio" value="'+data[i].address+'-'+data[i].zipCode+'-'+data[i].city+'" name="cb" class="bt_radio"> <span class="label-adresse">'+data[i].address+'</br>'+data[i].zipCode+' '+data[i].city+'</span></div>';                     
	 }
	var html = '<div class="popinLayerRed">'+
				    '<div class="popinContentRed">'+
				    '<div class="popinTopRed">'+
				        '<a href="" onclick="javascript:parent.$.fancybox.close();return false;" class="popinClose3Red tbleu">Fermer</a>'+
				    '</div>'+
				    '<div>'+ 'Après vérification, votre adresse semble comporter une erreur ou ne pas être aux normes postales.<br>'+
				         'Veuillez choisir une des options suivantes :'+
					'</div>'+
				    ' <br/><p><b> Choisir une adresse mise aux normes postales : </b> </p>'+
						'<div class="bloc-form">'+
					        choix  +                                                                                                                                            
					        '<div class="champ-radio"><input type="radio" value="m" name="cb" class="bt_radio" checked="checked"> <span class="label-adresseModifier"><strong>Modifier votre adresse et valider &agrave; nouveau le formulaire</strong></span> </div>'+
							'<div class="champ-radio"><input type="radio" value="c" name="cb" class="bt_radio"> <span class="label-adresse"><strong>Conserver l adresse de saisie :</strong><br/>'+adresse+'</br>'+codePostal+' '+ ville+'</span></div>'+
					        '<br>'+
							'<div class="btn_valider_popinAdr">'+
							'<input id="activer-kit" type="submit" name="activer-kit" value="">'+
							'</div>'+
						'</div>'+
					'</div>'+
			  '</div>';
	$.fancybox({
	      'autoScale'         : false,
	      'centerOnScroll'    : true,
	      'hideOnOverlayClick': true,
	      'autoDimensions'    : true,
	      'showCloseButton'   : false,
	      'padding'           : 0,
	      'overlayOpacity'    : 0,
	      'shadow' : 0, 
	      'shadowCssClass' : 'popin-bg',
	      'content' : html 	     
	  }); 
	$("#activer-kit").click( function() {
		//$.fancybox.showActivity();	
		var cb="m";
		var j ; 
		var tab = parent.document.getElementsByName('cb');
		for(j=0; j < tab.length; j++){			
			if (tab[j].checked){				
				cb  = tab[j].value;
				break;
			}
		}		
		if ( cb == "m" ) {
			parent.document.forms["coordonnees"].elements["adresse"].value="";
			parent.document.forms["coordonnees"].elements["codePostal"].value="";
			parent.document.forms["coordonnees"].elements["ville"].value="";
			
		} else {
			if ( cb =="c"){
				$("#champ_obligatoire_ville").removeClass("champ_obligatoire_ville_error");
				$("#champ_obligatoire_ville").addClass("champ_obligatoire_ville");		
				$("#txt_champ_obligatoire-ville").removeClass("txt_champ_obligatoire-ville-error");	
				$("#txt_champ_obligatoire-ville").addClass("txt_champ_obligatoire-ville");
				checkAdresseValidated = true;
				parent.$.fancybox.close();
				return false;
			} else {
				var n=cb.split("-"); 				
				parent.document.forms["coordonnees"].elements["adresse"].value=n[0];
				parent.document.forms["coordonnees"].elements["codePostal"].value=n[1];
				parent.document.forms["coordonnees"].elements["ville"].value=n[2];			
			}
		}		
		$("#champ_obligatoire_ville").removeClass("champ_obligatoire_ville_error");
		$("#champ_obligatoire_ville").addClass("champ_obligatoire_ville");		
		$("#txt_champ_obligatoire-ville").removeClass("txt_champ_obligatoire-ville-error");	
		$("#txt_champ_obligatoire-ville").addClass("txt_champ_obligatoire-ville");
		checkAdresseValidated = true;
		parent.$.fancybox.close();
		return false;
	});	
}

function switchDiv(n){ 
var id ="";
  switch(n){
    case 0:
         id = "";
         break;
    case 1:
         id = "signature";
         break;
    case 2:
         id = "envoiMP";
         break;
  } 
  document.getElementById('signature').style.display = "none";
  document.getElementById('envoiMP').style.display = "none";

  if (id!="")
     document.getElementById(id).style.display = "block";
}

function ToggleDiv(elementId){

	var el = document.getElementById(elementId);
	
	if (el.style.display = 'none')
		{ el.style.display = 'block'; }
	else if(el.style.display = 'block')
		{ el.style.display = 'none'; }
}		

function toggleDiv(element){
  if(document.getElementById(element).style.display = 'none')
  {
    document.getElementById(element).style.display = 'block';
  }
  else if(document.getElementById(element).style.display = 'block')
  {
    document.getElementById(element).style.display = 'none';
  }
}

function ToggleDiv2(elementId){
	var el = document.getElementById(elementId);
	if (el.style.display != 'none')
		{ el.style.display = 'none'; }
	else
		{ el.style.display = 'none'; }
		
}

function basculeElement(_this){
	var onglet_li = document.getElementById('ul_onglets').getElementsByTagName('li');
	for(var i = 0; i < onglet_li.length; i++){
		if(onglet_li[i].id){
			if(onglet_li[i].id == _this.id){
				if(_this.id == "2"){
					if(!wsAlredayCalled){ // variable globale
						rioAlreadyValidated = false;
						$("#rio").val("");
						$("#numero_actuel").val("");
						$("#date_mise_en_service").val("");
						
						// Appel de date range
						appelWsActivationDateRange();
					}
				}
				
				onglet_li[i].className = 'onglet_selectionner';
				document.getElementById('pnm' + _this.id).style.display = 'block';
			}else{
				onglet_li[i].className = 'onglet';
				document.getElementById('pnm' + onglet_li[i].id).style.display = 'none';
			}
		}
	}           
}


$.validator.addMethod("validate_civ", function(value) {  
	
	var civ = "";
	var j ; 
	var tab = parent.document.getElementsByName('civ');
	for(j=0; j < tab.length; j++){			
		if (tab[j].checked){				
			civ  = tab[j].value;
			break;
		}
	}			
	if (civ == ""){
		$("#check_civility").removeClass("champ_obligatoire_civ");	
		$("#check_civility").addClass("champ_obligatoire_civ-error");	
		$("#msg-civ-obligatioire").removeClass("fieldInteraction");
		$("#msg-civ-obligatioire").addClass("fieldInteractionError");
		return false;
	}
	else {
		$("#check_civility").removeClass("champ_obligatoire_civ-error");
		$("#check_civility").addClass("champ_obligatoire_civ");	
		$("#msg-civ-obligatioire").removeClass("fieldInteractionError");
		$("#msg-civ-obligatioire").addClass("fieldInteraction");	
		return true;
	}
});


$.validator.addMethod("validate_nom", function(value) {   
	var nom = $('#nom').val();
	if (nom.length <= 0 || isBlank(nom) ){
		$("#check_name").addClass("champ_obligatoire");
		$("#msg-name-obligatioire").removeClass("fieldInteraction");
		$("#msg-name-obligatioire").addClass("fieldInteractionError");
		return false;
	}
	else {
		$("#check_name").removeClass("champ_obligatoire");	
		$("#msg-name-obligatioire").removeClass("fieldInteractionError");
		$("#msg-name-obligatioire").addClass("fieldInteraction");
		return true;
     }	
});

$.validator.addMethod("validate_prenom", function(value) {   
	var prenom = $('#prenom').val();
	if (prenom.length <= 0 || isBlank(prenom)){
		$("#check_fistname").addClass("champ_obligatoire");	
		$("#msg-fistname-obligatioire").removeClass("fieldInteraction");
		$("#msg-fistname-obligatioire").addClass("fieldInteractionError");
		return false;
	}
	else {
		$("#check_fistname").removeClass("champ_obligatoire");
		$("#msg-fistname-obligatioire").removeClass("fieldInteractionError");
		$("#msg-fistname-obligatioire").addClass("fieldInteraction");
		return true;
	}
});

$.validator.addMethod("validate_adresse", function(value) {   
	var adresse = $('#adresse').val();		
	if ((adresse.length <= 4 )|| (adresse.length > 25 )){
		$("#check_address").removeClass("champ_obligatoire_info");
		$("#check_address").removeClass("champ_obligatoire_legende");
		$("#check_address").addClass("champ_obligatoire");	
		$("#commentaires-adresse").removeClass("fieldComDisp");
		$("#commentaires-adresse").addClass("fieldCom");
		$("#msg-address-obligatioire").removeClass("fieldInteraction");
		$("#msg-address-obligatioire").addClass("fieldInteractionError");
		return false;
	}
	else {
		$("#check_address").removeClass("champ_obligatoire");
		$("#check_address").removeClass("champ_obligatoire_legende");
		$("#check_address").addClass("champ_obligatoire_info");
		$("#msg-address-obligatioire").removeClass("fieldInteractionError");
		$("#msg-address-obligatioire").addClass("fieldInteraction");
		return true;
	}
});

$.validator.addMethod("validate_codePostal", function(value) {   
	var codePostal = $('#codePostal').val();
	
	if (codePostal.length <= 0 || isBlank(codePostal)){
		/*Le code postal est vide*/
		$("#check_zipcode").removeClass("champ_obligatoire_info");
		$("#check_zipcode").removeClass("champ_obligatoire_legende");
		$("#check_zipcode").addClass("champ_obligatoire");	
		$("#commentaires-cp").removeClass("fieldComDisp");
		$("#commentaires-cp").addClass("fieldCom");
		$("#msg-zipcode-obligatioire").removeClass("fieldInteraction");
		$("#msg-zipcode-obligatioire").addClass("fieldInteractionError");
		$("#codePostalInvalide").removeClass("fieldInteractionError");
		$("#codePostalInvalide").addClass("fieldInteraction");

		return false;
	}else {
		if (codePostal.match(/^[0-9]{5}$/)){
			/*Le code postal est valide*/
			$("#check_zipcode").removeClass("champ_obligatoire");
			$("#check_zipcode").addClass("champ_obligatoire_info");
			$("#msg-zipcode-obligatioire").removeClass("fieldInteractionError");
			$("#msg-zipcode-obligatioire").addClass("fieldInteraction");
			$("#codePostalInvalide").removeClass("fieldInteractionError");
			$("#codePostalInvalide").addClass("fieldInteraction");
			return true;		
		} else {
			/*Le code postal est invalide*/
			$("#check_zipcode").removeClass("champ_obligatoire_info");
			$("#check_zipcode").removeClass("champ_obligatoire_legende");
			$("#check_zipcode").addClass("champ_obligatoire");	
			$("#commentaires-cp").removeClass("fieldComDisp");
			$("#commentaires-cp").addClass("fieldCom");
			$("#msg-zipcode-obligatioire").removeClass("fieldInteractionError");
			$("#msg-zipcode-obligatioire").addClass("fieldInteraction");			
			$("#codePostalInvalide").removeClass("fieldInteraction");
			$("#codePostalInvalide").addClass("fieldInteractionError");
				
		}		
	}
});

$.validator.addMethod("validate_ville", function(value) {   
	
	var ville = $('#ville').val();
	if (ville.length <= 0 || isBlank(ville)) {
		$("#check_city").removeClass("info");
		$("#check_city").addClass("champ_obligatoire");
		$("#msg-city-obligatioire").removeClass("fieldInteraction");
		$("#msg-city-obligatioire").addClass("fieldInteractionError");
		return false;
	}
	else {
		$("#check_city").removeClass("champ_obligatoire");
		$("#msg-city-obligatioire").removeClass("fieldInteractionError");
		$("#msg-city-obligatioire").addClass("fieldInteraction");
		return true;
}
});


$.validator.addMethod("validate_msisdn", function(value) {
	var msisdn = $('#numero_actuel').val();
	if($("#pnm2").is(":visible")){
		return doNumeroActuelValidation(msisdn);
	}else{
		$("#checkrio_rio").removeClass("champ_obligatoire");
		$("#checkrio_rio_msg").html("");
		$("#checkrio_rio_msg").removeClass("txt_checkrio_champ_obligatoire");	
		return true;
	}
});

$.validator.addMethod("validate_rio", function(value) {
	var rio = $('#rio').val();
	var msisdn= $('#numero_actuel').val();
	if($("#pnm2").is(":hidden")){
		$("#checkrio_rio").removeClass("champ_obligatoire");
		$("#checkrio_rio_msg").html("");
		$("#checkrio_rio_msg").removeClass("txt_checkrio_champ_obligatoire");	
		return true;
	}else{
		if (rio.length <= 0){
			$("#checkrio_rio").addClass("champ_obligatoire");
			$("#checkrio_rio_msg").html("Ce champ est obligatoire.Merci de le renseigner.");
			$("#checkrio_rio_msg").addClass("txt_checkrio_champ_obligatoire");				
			return false;
		}else{
			if(rio.length != 12 || rio.indexOf("02") == 0){
				$("#checkrio_rio").addClass("champ_obligatoire");
				$("#checkrio_rio_msg").html("Le code rio est invalide.");
				$("#checkrio_rio_msg").addClass("txt_checkrio_champ_obligatoire");	
				return false;
			}else{
				return true;
				/*if(rioAlreadyValidated == true){
					return true;
				}else{
					var validationResult =  doRioWsValidation(rio, msisdn);
					return validationResult;
				}*/
			}
		}
	}
});

$.validator.addMethod("validate_date_mise_en_service", function(value) {   
	var date_mise_en_service = $('#date_mise_en_service').val();
	
	if($("#pnm2").css("display")=="none"){
		$("#checkDate_service").removeClass("champ_obligatoire");
		$("#checkDate_service").addClass("champ_obligatoire_info");
		$("#msg-date-serv-obligatioire").removeClass("fieldInteractionError");
		$("#msg-date-serv-obligatioire").addClass("fieldInteraction");	
		return true;}
	else{
		if (date_mise_en_service.length <= 0){		
			$("#checkDate_service").removeClass("champ_obligatoire_info");
			$("#checkDate_service").addClass("champ_obligatoire");
			$("#msg-date-serv-obligatioire").removeClass("fieldInteraction");
			$("#msg-date-serv-obligatioire").addClass("fieldInteractionError");
					
			return false;
		}
		else {
			$("#checkDate_service").removeClass("champ_obligatoire");
			$("#checkDate_service").addClass("champ_obligatoire_info");
			$("#msg-date-serv-obligatioire").removeClass("fieldInteractionError");
			$("#msg-date-serv-obligatioire").addClass("fieldInteraction");	
			return true;
		}
	}
});


$.validator.addMethod("validate_resiliation", function(value) {
	var resil = parent.document.getElementsByName('resiliation');
	if($("#pnm2").css("display")=="none"){
		$("#check_resiliation").removeClass("champ_obligatoire_resiliation");
		$("#check_resiliation").addClass("champ_info_resiliation");
    	$("#msg-check-obligatioire").removeClass("fieldInteractionError");
		$("#msg-check-obligatioire").addClass("fieldInteraction");
		return true;
	}else{
		if (resil[0].checked) {
			$("#check_resiliation").removeClass("champ_obligatoire_resiliation");
			$("#check_resiliation").addClass("champ_info_resiliation");
	    	$("#msg-check-obligatioire").removeClass("fieldInteractionError");
			$("#msg-check-obligatioire").addClass("fieldInteraction");
			return true;
		} else {
			$("#check_resiliation").removeClass("champ_info_resiliation");
			$("#check_resiliation").addClass("champ_obligatoire_resiliation");
	    	$("#msg-check-obligatioire").removeClass("fieldInteraction");
			$("#msg-check-obligatioire").addClass("fieldInteractionError");
			return false;		
		}	
	}	
});


$.validator.addMethod("validate_telephone", function(value) {   
	var telephone = $('#telephone').val();
	// Enlever tous les charactères sauf les chiffres
	//chiffres = chiffres.replace(/^0/g, '');
	// Le champs est vide
	
	if ( telephone == "" ){
		$("#check_phone").removeClass("champ_obligatoire_info");
		$("#check_phone").removeClass("champ_obligatoire_legende");	
		$("#check_phone").addClass("champ_obligatoire");
		$("#commentaires-tel").removeClass("fieldComDisp");
		$("#commentaires-tel").addClass("fieldCom");
		$("#msg-phone-obligatioire").removeClass("fieldInteraction");
		$("#msg-phone-obligatioire").addClass("fieldInteractionError");
		$("#telephonErrone").removeClass("fieldInteractionError");
		$("#telephonErrone").addClass("fieldInteraction");
		return false;
	}
	// Nombre de chiffres
	var reg = new RegExp(/^(01|02|03|04|05|06|07|08|09)[0-9]{8}/gi);
	if (isNaN(telephone) || !reg.test(telephone)){
		$("#check_phone").removeClass("champ_obligatoire_info");
		$("#check_phone").removeClass("champ_obligatoire_legende");	
		$("#check_phone").addClass("champ_obligatoire");
		$("#commentaires-tel").removeClass("fieldComDisp");
		$("#commentaires-tel").addClass("fieldCom");	
		$("#msg-phone-obligatioire").removeClass("fieldInteractionError");
		$("#msg-phone-obligatioire").addClass("fieldInteraction");			
		$("#telephonErrone").removeClass("fieldInteraction");
		$("#telephonErrone").addClass("fieldInteractionError");
		return false;		
	
	}else{		
		$("#check_phone").removeClass("champ_obligatoire");
		$("#check_phone").addClass("champ_obligatoire_info");
		$("#msg-phone-obligatioire").removeClass("fieldInteractionError");
		$("#msg-phone-obligatioire").addClass("fieldInteraction");
		$("#telephonErrone").removeClass("fieldInteractionError");
		$("#telephonErrone").addClass("fieldInteraction");
		return true;

	}
	
});
$.validator.addMethod("validate_email", function(value) {   
	var email = $('#email').val();	
	
	if (email.length <= 0 || isBlank(email)){
		/*Le code postal est vide*/
		$("#check_email").removeClass("champ_obligatoire_info");
		$("#check_email").removeClass("champ_obligatoire_legende");
		$("#check_email").addClass("champ_obligatoire");	
		$("#msg-email-obligatioire").removeClass("fieldInteraction");
		$("#msg-email-obligatioire").addClass("fieldInteractionError");
		$("#mailErrone").removeClass("fieldInteractionError");
		$("#mailErrone").addClass("fieldInteraction");		
		return false;
	}else {		
		if (validEmail(email)) {
			/*est valide*/
			$("#check_email").removeClass("champ_obligatoire");
			$("#check_email").addClass("champ_obligatoire_info");
			$("#msg-email-obligatioire").removeClass("fieldInteractionError");
			$("#msg-email-obligatioire").addClass("fieldInteraction");
			$("#mailErrone").removeClass("fieldInteractionError");
			$("#mailErrone").addClass("fieldInteraction");
			return true;		
		} else {
			
			/*est invalide*/
			$("#check_email").removeClass("champ_obligatoire_info");
			$("#check_email").removeClass("champ_obligatoire_legende");
			$("#check_email").addClass("champ_obligatoire");	
			$("#msg-email-obligatioire").removeClass("fieldInteractionError");
			$("#msg-email-obligatioire").addClass("fieldInteraction");			
			$("#mailErrone").removeClass("fieldInteraction");
			$("#mailErrone").addClass("fieldInteractionError");					
		}	
	}
	
});

$.validator.addMethod("validate_tpi", function(value) {   
	var j=0;
	var champs = document.getElementsByName("tpi");	
	for (var i=0;i<champs.length;i++){
		if (champs[i].checked)
			j++;
		else
			j;
	}
	if (j==0){
		$("#check_type_identite").addClass("champ_obligatoire");
		$("#msg-tpi-obligatioire").removeClass("fieldInteraction");
		$("#msg-tpi-obligatioire").addClass("fieldInteractionError");
		return false;
	}
	else{
		$("#check_type_identite").removeClass("champ_obligatoire");
		$("#msg-tpi-obligatioire").removeClass("fieldInteractionError");
		$("#msg-tpi-obligatioire").addClass("fieldInteraction");
		return true;
	}
});

$.validator.addMethod("validate_identite", function(value) {   
	var numero_identite = $('#numero_identite').val();
	if (numero_identite.length <=0 || isBlank(numero_identite)){
    	$("#check_num_identite").addClass("champ_obligatoire");
    	$("#msg-numi-obligatioire").removeClass("fieldInteraction");
		$("#msg-numi-obligatioire").addClass("fieldInteractionError");
		return false;
	}else{
		$("#check_num_identite").removeClass("champ_obligatoire");
    	$("#msg-numi-obligatioire").addClass("fieldInteraction");
		$("#msg-numi-obligatioire").removeClass("fieldInteractionError");
		return true;
	}
	
});

$.validator.addMethod("validate_birth_date", function(value) {   
	var jour = $('#jour').val();
	var mois = $('#mois').val();
	var annee = $('#annee').val();
	
	//isNaN($("#dateNaissance").val())
	
	if (jour=="Jour" || mois =="Mois" || annee== "Année" ){
		/*La date est obligatoire */
		$("#check_date_naissance").addClass("champ_obligatoire");
		$("#birthdate_error_msg").html("Ce champ est obligatoire.<br/>Merci de le renseigner.");
		$("#birthdate_error_msg").removeClass("fieldInteraction");
		$("#birthdate_error_msg").addClass("fieldInteractionError");

		return false;
	} else{
		//verify if the date is a valid date
		var isDateValid = false;
		var leapYear = (((annee % 4) == 0) && ((annee % 100) != 0) || ((annee % 400) == 0));
		//the month begin with "0" : (january)
		if (mois == 1) {
			isDateValid = leapYear ? jour <= 29 : jour <= 28;
        } else {
        	if ((mois == 3) || (mois == 5) || (mois == 8) || (mois == 10)) {
        		isDateValid = jour <= 30;
            } else {
            	isDateValid = jour <= 31;
            }
        }
		
		if (!isDateValid) {
			$("#check_date_naissance").addClass("champ_obligatoire");
			$("#birthdate_error_msg").html("Cette date est invalide");
			$("#birthdate_error_msg").removeClass("fieldInteraction");
			$("#birthdate_error_msg").addClass("fieldInteractionError");
			return false;
		}
		
		/** La date selectionnée*/
		var maDate = new Date();
		maDate.setDate(jour);
		maDate.setMonth(mois);
		maDate.setYear(annee);
		/** Date du jour */
		var today = new Date();
		//Calcul de différence
		var diff = ((today.getTime() - maDate.getTime())/(1000*60*60*24*365.242199));
		if (diff < 18){
			
			$("#check_date_naissance").addClass("champ_obligatoire");
			$("#birthdate_error_msg").html("Vous devez être majeur pour pouvoir poursuivre votre commande.");
			$("#birthdate_error_msg").removeClass("fieldInteraction");
			$("#birthdate_error_msg").addClass("fieldInteractionError");
			return false;
		}
	}
	
	$("#check_date_naissance").removeClass("champ_obligatoire");
	$("#birthdate_error_msg").html("");
	$("#birthdate_error_msg").removeClass("fieldInteractionError");
	$("#birthdate_error_msg").addClass("fieldInteraction");
	return true;
});

$.validator.addMethod("validate_departement", function(value) {   
	var departement = $('#departement').val();
	if (departement.length <=0 || isBlank(departement) || departement =="selection"){
    	$("#check_departement").addClass("champ_obligatoire");
    	$("#msg-departement-obligatioire").removeClass("fieldInteraction");
		$("#msg-departement-obligatioire").addClass("fieldInteractionError");
		return false;
	}else{
		$("#check_departement").removeClass("champ_obligatoire");
    	$("#msg-departement-obligatioire").addClass("fieldInteraction");
		$("#msg-departement-obligatioire").removeClass("fieldInteractionError");
		return true;
	}
	
});


//validator
$(document).ready(function() {
    
    // validate signup form on keyup and submit
    $("#coordonnees").validate({
    	ignore: "",
        rules: {
        	date_mise_en_service: {
          		validate_date_mise_en_service: true
            },
        	resiliation: {
        		validate_resiliation: true
        	},
        	civ: {
        		validate_civ: true
        	},
        	nom: {
				validate_nom: true
			},
			prenom: {
				validate_prenom: true
			},
			adresse: {
				validate_adresse:true
			},
			codePostal: {
				validate_codePostal: true
			},
			ville: {
				validate_ville: true
			},
			telephone: {
				validate_telephone: true
			},
			email: {
				validate_email: true
			},
			tpi: {
        		validate_tpi: true
        	},
        	numero_identite: {
        		validate_identite: true
        	},
        	rio:{ 
        		validate_rio:true
        	},
        	numero_actuel:{ 
        		validate_msisdn:true
        	},
        	dateNaissance: {
        			validate_birth_date: true
        	},
        	departement: {
    			validate_departement: true
        	}	
        },
            
        messages: {
        	date_mise_en_service: {
          		validate_date_mise_en_service: ""
            },
        	resiliation: {
        		validate_resiliation: " "
            }, 
        	civ: {
        		validate_civ: " "
            },            	
            nom: {  
            	validate_nom: " "
            },
            prenom: {
            	validate_prenom: " "
            },
	        adresse: {
	        	validate_adresse: " "
	        },
	        codePostal: {
	        	validate_codePostal: " "
	        },
	        ville: {
	        	validate_ville: " "
	        },
	        telephone: {
	        	validate_telephone: " "
            },
            email: {
            	validate_email: " "
            },
            tpi: {
            	validate_tpi: " "
            },
            numero_identite: {
            	validate_identite:" "
            },
            rio:{ 
        		validate_rio:" "
        	},
        	numero_actuel:{ 
        		validate_msisdn:" "
          	},
            dateNaissance: {
            	validate_birth_date: " "
            },
            departement: {
    			validate_departement: true
        	}	
        }
	});
	
	$('#jour,#mois,#annee').change(function(){	 

		var jour = $('#jour').val();
		var mois = $('#mois').val();
		var annee = $('#annee').val();
		$("#dateNaissance").val($("#jour").val().concat($("#mois").val()).concat($("#annee").val()));
		
		if ( jour !="jour" && mois != "mois" && annee != "annee" ) {
			//isNaN($("#dateNaissance").val())
			if (jour=="jour" || mois =="Mois" || annee== "Année" ){
				/*La date est obligatoire */
				$("#check_date_naissance").addClass("champ_obligatoire");
				$("#birthdate_error_msg").html("Ce champ est obligatoire.<br/>Merci de le renseigner.");
				$("#birthdate_error_msg").removeClass("fieldInteraction");
				$("#birthdate_error_msg").addClass("fieldInteractionError");
				return false;
			} else{
				//verify if the date is a valid date
				var isDateValid = false;
				var leapYear = (((annee % 4) == 0) && ((annee % 100) != 0) || ((annee % 400) == 0));
				//the month begin with "0" : (january)
				if (mois == 1) {
					isDateValid = leapYear ? jour <= 29 : jour <= 28;
                } else {
                	if ((mois == 3) || (mois == 5) || (mois == 8) || (mois == 10)) {
                		isDateValid = jour <= 30;
                    } else {
                    	isDateValid = jour <= 31;
                    }
                }
				
				if (!isDateValid) {
					$("#check_date_naissance").addClass("champ_obligatoire");
					$("#birthdate_error_msg").html("Cette date est invalide");
					$("#birthdate_error_msg").removeClass("fieldInteraction");
					$("#birthdate_error_msg").addClass("fieldInteractionError");
					return false;
				}
				
				/** La date selectionnée*/
				var maDate = new Date();
				maDate.setDate(jour);
				maDate.setMonth(mois);
				maDate.setYear(annee);
				/** Date du jour */
				var today = new Date();
				//Calcul de différence
				var diff = ((today.getTime() - maDate.getTime())/(1000*60*60*24*365.242199));
				if (diff < 18){
					$("#check_date_naissance").addClass("champ_obligatoire");
					$("#birthdate_error_msg").html("Vous devez être majeur pour pouvoir poursuivre votre commande.");
					$("#birthdate_error_msg").removeClass("fieldInteraction");
					$("#birthdate_error_msg").addClass("fieldInteractionError");
					return false;
				}
			}
			
			$("#check_date_naissance").removeClass("champ_obligatoire");
			$("#birthdate_error_msg").html("");
			$("#birthdate_error_msg").removeClass("fieldInteractionError");
			$("#birthdate_error_msg").addClass("fieldInteraction");
			return true;

			
		}
	 
		 
	});	
});


function validEmail(mailteste){
	var reg = new RegExp('^[a-z0-9]+([_|\.|-]{1}[a-z0-9]+)*@[a-z0-9]+([_|\.|-]{1}[a-z0-9]+)*[\.]{1}[a-z]{2,6}$', 'i');

	if(reg.test(mailteste))
	{
		return(true);
	}
	else
	{
		return(false);
	}
}


function showPopupServiceIndispo() {
	var html = '<div class="popinLayerServiceIndispo">'
			+ '<div class="popinContent">'
			+ '<div class="popinTop">'
			+ '<a href="#" onclick="javascript:parent.$.fancybox.close();return false;" class="popinCloseR tbleu">Fermer</a>'
			+ '</div>'
			+

			'<div class="popinServiceIndispo">'
			+ '<h4> Le service demandé est momentanément indisponible,'+'<br>'+' merci de réessayer ultérieurement.'+'<br>'+'Veuillez nous excuser pour la gêne occasionnée.</h4>'
			+ '</div>';

	$.fancybox({
		'autoScale' : false,
		'centerOnScroll' : true,
		'hideOnOverlayClick' : true,
		'autoDimensions' : true,
		'showCloseButton' : false,
		'padding' : 0,
		'overlayOpacity' : 0,
		'shadow' : 0,
		'shadowCssClass' : 'popin-bg',
		'content' : html
	});
}


function showBlockInLayer(blockId) {
	$('#'+blockId).modal({
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

function closeSimpleModal()
{
	$.modal.close();
}


function checkNormAddress(){
	var adresse = $('#adresse').val();
	var codePostal = $('#codePostal').val();
	var ville    = $('#ville').val();
	if ( (ville != "" && adresse != "")  &&  codePostal != ""){			
		$.ajax({				
			type: 'POST',
			url: ws_url,
			cache: false,
			data: "adresse=" + adresse + "&codePostal=" + codePostal + "&ville=" +ville,
			error: function (e, t){
				//alert("CONNEXION FAIL");			
			},
			success: function(data){
				
				if ( data != "") {
					if ( data.length > 0){						
						if ( data[0].volatil.length == 8 ){
							parent.document.forms["coordonnees"].elements["adresse"].value=data[0].address;
							parent.document.forms["coordonnees"].elements["codePostal"].value=data[0].zipCode;
							parent.document.forms["coordonnees"].elements["ville"].value=data[0].city;
							$("#champ_obligatoire_ville").removeClass("champ_obligatoire_ville_error");
							$("#champ_obligatoire_ville").addClass("champ_obligatoire_ville");		
							$("#txt_champ_obligatoire-ville").removeClass("txt_champ_obligatoire-ville-error");	
							$("#txt_champ_obligatoire-ville").addClass("txt_champ_obligatoire-ville");
							
							$("#check_city").removeClass("champ_obligatoire");
							$("#msg-city-obligatioire").removeClass("fieldInteractionError");
							$("#msg-city-obligatioire").addClass("fieldInteraction");
							checkAdresseValidated = true;
							return false;
						} else {
							$("#champ_obligatoire_ville").removeClass("champ_obligatoire_ville");
							$("#champ_obligatoire_ville").addClass("champ_obligatoire_ville_error");
							$("#txt_champ_obligatoire-ville").removeClass("txt_champ_obligatoire-ville");
							$("#txt_champ_obligatoire-ville").addClass("txt_champ_obligatoire-ville-error");
							
							$("#check_city").removeClass("champ_obligatoire");
							$("#msg-city-obligatioire").removeClass("fieldInteractionError");
							$("#msg-city-obligatioire").addClass("fieldInteraction");
							checkAdresseValidated = false;
							 return false;	
						}
					} 
				}else {

					$("#champ_obligatoire_ville").removeClass("champ_obligatoire_ville_error");
					$("#champ_obligatoire_ville").addClass("champ_obligatoire_ville");		
					$("#txt_champ_obligatoire-ville").removeClass("txt_champ_obligatoire-ville-error");	
					$("#txt_champ_obligatoire-ville").addClass("txt_champ_obligatoire-ville");
					
					$("#check_city").removeClass("champ_obligatoire");
					$("#msg-city-obligatioire").removeClass("fieldInteractionError");
					$("#msg-city-obligatioire").addClass("fieldInteraction");
					checkAdresseValidated = true;
					 return false;	
				}					
			},
			complete: function(){
				$("#loading").hide();
				$("#content2").show();
			}
		});	
		
		function saveUser() {
//			$.fancybox.showLoading();
			var urlValidaion = getContextRoot() +'/usersController/ajax/validate-user.html';
			$.ajax({
				type : 'POST',
				url : urlValidaion,
				cache : false,
				data : $("#coordonnees").serializeArray(),
				success : function(response) {
//					$.fancybox.hideLoading();
					$("#col_gauche").html(response.resultObject);
					//$("#messageHTMLLayer"+shortDescriptionFormId).toggle('slow');
				},
				error : function(request, status, err) {
					$.fancybox.hideLoading();
					alert("Erreur lors de la sauvegarde...");
				}
			});
		}
	}
}