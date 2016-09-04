<head>	
	<title>Smart Album</title>
	<meta charset="utf-8" />
	<link rel="shortcut icon" type="image/x-icon" href="<c:url value='css/images/favicon.ico'/>" />
	<link rel="stylesheet" href="<c:url value='/templates/green/css/green.css'/>" type="text/css" media="all" />
<%-- 	<link rel="stylesheet" href="<c:url value='/templates/green/css/fancybox.css'/>" type="text/css" media="all" /> --%>
	<script src="<c:url value='/templates/green/js/selectivizr.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/templates/green/js/modernizr.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/templates/green/js/simpleFade.js'/>" type="text/javascript"></script>
<%-- 	<script src="<c:url value='/templates/green/js/fancybox.js'/>" type="text/javascript"></script> --%>
	<script src="<c:url value='/templates/green/js/tweet.js'/>" type="text/javascript"></script>
	
			<link rel="stylesheet" type="text/css" href="<c:url value='/css/infos_persos.css' />" media="screen" />
		<!-- jquery simple modal -->
		<!-- Intégration stickyfloat -->
		<script type="text/javascript" src="<c:url value='/js/personal-information.js' />" type="text/javascript"></script>
	<!-- jquery simple modal -->
		<script type="text/javascript" src="<c:url value='/js/jquery/modal/jquery.simplemodal.js'/>"></script>
		<script type="text/javascript">
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
</script>
</head>