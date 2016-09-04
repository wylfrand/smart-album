<%@ include file="/WEB-INF/jsp/common/include.jsp" %>

<head>
<script type="text/javascript">
$(document).ready(function() {
	$(".img-delete").hover(function() {
		var src = $(this).attr("src").replace("retirer-off.png", "retirer-on.png");
		$(this).attr("src", src);
	});
	$(".img-delete").mouseout(function() {
		var src = $(this).attr("src").replace("retirer-on.png", "retirer-off.png");
		$(this).attr("src", src);
	});
});

function modifyLine(id,msisdn,modifyLineURL){
	msisdn = msisdn.replace(/\./g, '');
	if ($.trim(msisdn) != "") {
		var url = modifyLineURL + msisdn + ".html";
		$(".loadingImage_" + id).before('<img class="loadingImage_class" src="<c:url value='/img/moulinette.png' />" />');
		$(".loadingImage_" + id).remove();
		$.post(url, {counter: id, isCreation: "${isCreation}"}, function(data) {
			if($("#sameName_" + id).val() != null && $("#sameName_"+id).val() == "false"){
				if ($(".sameNameWarning").length < 2) {
					$("#textSameName").hide();
				}
			}
			$("#add_new_line_row_" + id).replaceWith(data);
			
			if ($("#activeSimu_" + id).val() != null && $("#activeSimu_" + id).val() == "true") {
				$("#imgRemiseActive").show();
				$("#imgRemise").hide();
				$(".separationPrice a.aa.adding").attr("data-original-title","");
				$(".separationPrice img.adding").css("cursor","default");
			} else {
				$("#imgRemiseActive").hide();
				$("#imgRemise").show();
				$(".separationPrice a.aa.adding").attr("data-original-title","Complétez votre Multi-Packs pour connaître les remises sur votre box");
				$(".separationPrice img.adding").css("cursor","pointer");
			}
			
			$("#lastAddedNumber").val("");
		});
	}
}

function countCaracters(id, createLineURL, isAddams) {
	var selector = "#add_new_line_input_" + id;
	var number = $(selector).val();
	number = $.trim(number.replace(/\./g, ''));
	var currentInput = document.getElementById("add_new_line_input_" + id);
	if(currentInput.className.indexOf("boldTelephone")==-1)
	{
		$("#add_new_line_input_" + id).addClass("boldTelephone");
	}
		
	if (number.length == 10) {
		document.getElementById("add_new_line_input_" + id).blur();
	} else if (number.length < 10 && !isAddams) {
		modifyLineMPVX(id, number);
	}
}

function addNewLine(id, createLineURL, isAddams) {
	var selector = "#add_new_line_input_" + id;
	var number = $(selector).val();
	number = $.trim(number.replace(/\./g, ''));
	if(number.length == 0){
		var currentInput = document.getElementById("add_new_line_input_" + id);
		
		if(currentInput.className.indexOf("boldTelephone")!=-1)
		{
			$("#add_new_line_input_" + id).removeClass("boldTelephone");
		}
		if(isAddams)
		{
			$("#add_new_line_input_" + id).attr("placeholder","Ajouter une ligne");
			$("#add_new_line_input_" + id).removeClass();
			$("#add_new_line_input_" + id).addClass("addLine texte-td");
		}else {
			$("#add_new_line_input_1").attr("placeholder","Saisir une ligne mobile");
			$("#add_new_line_input_" + id).removeClass();
			$("#add_new_line_input_" + id).addClass("addLineMPVX");
		}
		
	}
	if (number.length == 10) {
		var isMobile = $("#add_new_line_row_" + id).hasClass("mobileRow");
		var urlCreateLine = createLineURL + number + ".html";
		$(".loadingImage_" + id).before('<img class="loadingImage_class" src="<c:url value='/img/moulinette.png' />" />');
		$(".loadingImage_" + id).remove();
		$.ajax({
			type : "POST",
			url: urlCreateLine,
			data : {counter: id, isCreation: "${isCreation}", isLigneMobile: isMobile},
			dataType: "html",
			timeout: 60000,
			success : function(data) {

		   		if (data != null && $.trim(data) != "KO") {
					//replace the current empty line with the a new line with data filled
					$("#add_new_line_row_" + id).replaceWith(data);
					
					//active or desactivate the action button according to the value of "activeSimu"
					if ($("#activeSimu_" + id).val() != null && $("#activeSimu_" + id).val() == "true") {
						$("#imgRemiseActive").show();
						$("#imgRemise").hide();
						$(".separationPrice a.aa.adding").attr("data-original-title","");
						$(".separationPrice img.adding").css("cursor","default");
					} else {
						$("#imgRemiseActive").hide();
						$("#imgRemise").show();
						$(".separationPrice a.aa.adding").attr("data-original-title","Complétez votre Multi-Packs pour connaître les remises sur votre box");
						$(".separationPrice img.adding").css("cursor","pointer");
					}
					
					//verify if the new line has the same name of the current connected line or not.
					//If not, it should display a message to tell the user the name is protected
					if($("#sameName_"+id).val() != null && $("#sameName_"+id).val()== "false"){
						$("#textSameName").show();
					}
				}
		   		else
			   	{
		   			document.location.href = "<c:url value='/error/service_indisponible.html'/>";
				}
				
				//update the hidden input for "lastAddedNumber"
				$("#lastAddedNumber").val(number);
				if(!isAddams)
				{
					$("#add_new_line_input_" + id).addClass("boldTelephone");
				}
			},
			error : function(request, status, err) {
	            if(status == "timeout") {
	            	document.location.href = "<c:url value='/groupe/affichage/index.html'/>";
	            }
	            else
		        {
	            	document.location.href = "<c:url value='/error/service_indisponible.html'/>";
			    }
			}
		});
	}else {
		if(number != "Saisir une ligne mobile" && number != "" && number !="Ajouter une ligne"){
			if(!isAddams){
				$('#erreurFormeMpvx').show();
				$("#add_new_line_input_1").attr("data-number", number);
				var input = document.getElementById("add_new_line_input_1");
					input.className= "addLineMPVX hasError"; 	
			}
			else {
				$('#erreurFormeAddams_'+id).show();
				$('#tdError_'+id).hide();
				
				$("#add_new_line_input_"+id).attr("data-number", number);
				var input = document.getElementById("add_new_line_input_"+id);
				input.className= "addLine texte-td hasError";
			}
		}
	}
}



function modifyLineMPVX(id, number) {
	//test if the number is already deleted from simulationInfo
	var savedNumber = $("#add_new_line_input_1").attr("data-number");
	if (savedNumber != null && savedNumber.trim() != "" && $("#lastDeletedNumber").val() != savedNumber) {
		//remove the line from simulationInfo
		var modifyLineURL = $("#modifyLineURL").val();
		var url = modifyLineURL + savedNumber + ".html";
		$.post(url, {counter: id, isCreation: "${isCreation}"}, function(data) {
			$(".msg-success").empty();
			$("#add_new_line_input_1").removeClass("addSuccess");
			$("#imgRemiseActive").hide();
			$("#imgRemise").show();
			$("#add_new_line_input_1").attr("data-number", number);
			$("#lastDeletedNumber").val(number);
			$("#lastAddedNumber").val("");
		});
	}
}

function checkNumber(number, id, isAddams) {
	var isCorrect = true;
	var lastAddedNumber = $("#lastAddedNumber").val();
	if (lastAddedNumber == number || $.trim(number) == "") {
		isCorrect = false;
	}
	return isCorrect;
}

function simulerCreationDeGoupe(){
    var actionUrl = "<c:url value='/action/groupe/souscription/simulation.html'/>?sfrintid=EC_mp_creer";
    $.ajax({
    	type	 : "GET",
    	cache	: false,
    	url	 : actionUrl,
    	success: function(data) {
            	
                if(data.result == true){
                	document.location.href="<c:url value='/groupe/simulation.html'/>";
            	}
        		else if(data.error == "GROUPE_SOUSCRIPTION_ERROR" || data.error == "GROUPE_MODIFICATION_ERROR" || data.error == "ELIAME_TIMEOUT_ERROR")
            	{
					document.location.href = "<c:url value='/error/service_indisponible.html'/>";
				}
				else if(data.error == "ELIAME_INELIGIBLE")
        		{
        			document.location.href = "<c:url value='/error/ineligible.html'/>";
        		}
				else{
					document.location.href = "<c:url value='/error/service_indisponible.html'/>";
        			
        		}
    		}
    	});
} 

function simulerModificationDeGroupe(){
    var actionUrl = "<c:url value='/action/groupe/modification/simulation.html'/>?sfrintid=EC_mp_modifier";
    $.ajax({
    	type	 : "GET",
    	cache	: false,
    	url	 : actionUrl,
    	success: function(data) {
            	
                if(data.result == true){
                	document.location.href="<c:url value='/groupe/simulation.html'/>";
            	}
        		else if(data.error == "GROUPE_SOUSCRIPTION_ERROR" || data.error == "GROUPE_MODIFICATION_ERROR" 
        				|| data.error == "ELIAME_TIMEOUT_ERROR" || data.error == "GROUPE_MODIFICATION_AJOUT_ERROR")
            	{
					document.location.href = "<c:url value='/error/service_indisponible.html'/>";
				}
				else if(data.error == "ELIAME_INELIGIBLE")
        		{
        			document.location.href = "<c:url value='/error/ineligible.html'/>";
        		}
				else{        			
        			document.location.href = "<c:url value='/error/service_indisponible.html'/>";
        		}
    		}
    	});
}
</script>
</head>
<body>
<c:choose>
	<c:when test="${isCreation}">
		<%@ include file="/WEB-INF/jsp/groupe/restitution/creation/index.jsp" %>		
	</c:when>
	<c:otherwise>
		<%@ include file="/WEB-INF/jsp/groupe/restitution/modification/index.jsp" %>
	</c:otherwise>
</c:choose>
</body>