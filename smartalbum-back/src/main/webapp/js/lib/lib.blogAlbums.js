jQuery(document).ready(
	function() {
	    blogAlbums.init();
	    var currentAlbumId = $("#currentAlbumId").attr("data-albumid");
	    blogAlbums.printBlogDataTableSearchResult("blog-files",
		    currentAlbumId);
	    blogAlbums.printSliderDataTableSearchResult("slider-files",
		    currentAlbumId);
	    //blogAlbums.printGenericTiny("textAreaId");
	    
	    // blogAlbums.loadWowSliderImageList(currentAlbumId);
	    // alert("Pause");
	    // blogAlbums.initWowSlider("wowslider-container1",0);
	});

$(window).load(function() {

});

$.fn.exists = function() {
    return this.length > 0;
};

var blogAlbums = (function() {

    init = function() {
	return true;
    };
    
    printGenericTiny = function(textAreaId){
	common.buildRichTextEditor(textAreaId);
    }

    printBlogDataTableSearchResult = function(dataTableId, currentAlbumId) {
	var userUrl = common.getDocumentRootURL()
		+ "/albumsControllerRest/ajax/printsImages.json?albumId="
		+ currentAlbumId + "&dataTableType=BLOG";
	initBlogDatatable(dataTableId, userUrl);
    }

    printSliderDataTableSearchResult = function(dataTableId, currentAlbumId) {
	var userUrl = common.getDocumentRootURL()
		+ "/albumsControllerRest/ajax/printsImages.json?albumId="
		+ currentAlbumId + "&dataTableType=SLIDER";
	initSliderDatatable(dataTableId, userUrl, currentAlbumId);
    }

    modifyShortImageDescription = function(messageId) {
	$('#' + messageId).show();
	common.showBlockInLayer(messageId);
    }

    initBlogDatatable = function(idTable, urlAjax) {
	var table = $('#' + idTable).DataTable(
		{
		    "paging" : true,
		    "pagingType" : "full_numbers",
		    "searching" : true,
		    "sort" : true,
		    "info" : true,
		    "stateSave" : true,
		    "autoWidth" : true,
		    "processing" : true,
		    "serverSide" : true,
		    "lengthChange" : true,
		    "columns" : [ {
			"width" : "100%",
			"orderable" : false,
			"data" : "blogEntry"
		    } ],
		    "ajax" : {
			"url" : urlAjax,
			"type" : "POST",
			"dataSrc" : 'data'
		    },
		    "language" : {
			"url" : common.getDocumentRootURL()
				+ '/js/jquery/datatables/Fr.txt'
		    }
		});
    }

    initSliderDatatable = function(idTable, urlAjax, currentAlbumId) {
	var table = $('#' + idTable).on('draw.dt', function() {
	    initWowSlider("wowslider-container1", 0);
	}).DataTable(
		{
		    "paging" : true,
		    "pagingType" : "full_numbers",
		    "searching" : false,
		    "sort" : true,
		    "info" : true,
		    "stateSave" : true,
		    "autoWidth" : true,
		    "destroy" : true,
		    "processing" : true,
		    "serverSide" : true,
		    "lengthChange" : true,
		    "columns" : [ {
			"width" : "100%",
			"orderable" : false,
			"data" : "sliderEntry"
		    } ],
		    "ajax" : {
			"url" : urlAjax,
			"type" : "POST",
			"dataSrc" : 'data'
		    },
		    "language" : {
			"url" : common.getDocumentRootURL()
				+ '/js/jquery/datatables/Fr.txt'
		    }
		})
    }

    // Enregistre le nouveau contenu
    saveShortDescription = function(shortDescriptionFormId) {
	$.fancybox.showLoading();
	var urlMessage = documentRootURL
		+ '/messageHTMLController/ajax/updateShortDescription.json';
	var shortDescriptionFormObj = {
	    id : shortDescriptionFormId,
	    title : $("#popupTitle" + shortDescriptionFormId).val(),
	    entityType : "image",
	    description : $("#popupDescription" + shortDescriptionFormId).val()
	};
	var shortDescriptionFormObjStr = JSON
		.stringify(shortDescriptionFormObj);
	$.ajax({
	    type : 'POST',
	    url : urlMessage,
	    data : shortDescriptionFormObjStr,
	    dataType : "json",
	    contentType : "application/json",
	    success : function(response) {
		$.fancybox.hideLoading();
		$("#title" + shortDescriptionFormId).text(
			shortDescriptionFormObj.title);
		$("#descriptionMessage" + shortDescriptionFormId).text(
			shortDescriptionFormObj.description);
		$('#messageHTMLLayer' + shortDescriptionFormId).close();
	    },
	    error : function(request, status, err) {
		$.fancybox.hideLoading();
		alert("Erreur lors de la sauvegarde..." + status + " *** "
			+ err);
	    }
	});
    }

    // Enregistre le nouveau contenu
    loadWowSliderImageList = function(currentAlbumId) {
	$.fancybox.showLoading();
	var urlWowImages = common.getDocumentRootURL()
		+ "/albumsControllerRest/ajax/wowSliderImages.json?albumId="
		+ currentAlbumId;
	$.ajax({
		    type : 'POST',
		    url : urlWowImages,
		    dataType : "json",
		    contentType : "application/json",
		    success : function(response) {
			$.fancybox.hideLoading();
			if (response.result) {
			    printSliderDataTableSearchResult("slider-files",
				    currentAlbumId);
			    alert(response.resultObject);
			    initWowSlider("wowslider-container1",
				    response.resultObject);
			}
		    },
		    error : function(request, status, err) {
			$.fancybox.hideLoading();
			alert("Erreur lors de la récupération des images du slider"
				+ status + " *** " + err);
		    }
		});
    }

    initWowSlider = (function(containerId, imageList) {

	var options = {
	    effect : "basic",
	    prev : "",
	    next : "",
	    duration : 20 * 100,
	    delay : 20 * 100,
	    width : 1024,
	    height : 768,
	    autoPlay : true,
	    autoPlayVideo : false,
	    playPause : true,
	    stopOnHover : false,
	    loop : false,
	    bullets : 1,
	    caption : true,
	    captionEffect : "parallax",
	    controls : true,
	    controlsThumb : true,
	    responsive : 1,
	    fullScreen : true,
	    gestures : 2,
	    onBeforeStep : 0,
	    images : imageList
	}

	$("#" + containerId).wowSlider(options);
    });
    

    return {
	init : init,
	printGenericTiny : printGenericTiny,
	initBlogDatatable : initBlogDatatable,
	printBlogDataTableSearchResult : printBlogDataTableSearchResult,
	saveShortDescription : saveShortDescription,
	initWowSlider : initWowSlider,
	initSliderDatatable : initSliderDatatable,
	loadWowSliderImageList : loadWowSliderImageList,
	printSliderDataTableSearchResult : printSliderDataTableSearchResult,
	modifyShortImageDescription : modifyShortImageDescription
    };
})();
