jQuery(document).ready(function() {  	
    uploadAlbums.init();
    var currentAlbumId = $("#currentAlbumId").attr("data-albumid");
    uploadAlbums.printUploadDataTableSearchResult("uploaded-files",currentAlbumId);
});

$.fn.exists = function() {
	return this.length > 0;
};

var uploadAlbums = (function() {

    init = function(){
        return true;
    };
    
    printUploadDataTableSearchResult = function(dataTableId, currentAlbumId) {
	var userUrl = common.getDocumentRootURL() + '/albumsControllerRest/ajax/printsImages.json?albumId='+currentAlbumId+'&dataTableType=UPLOAD';
	initUploadDatatable(dataTableId, userUrl);
    }
    
    printDataTableModifyImageNamePopup = function(fileName, isTmpImage , controllerName, relativeUrl,compteurIndex,imageId) {
	var htmlContent = $("#modifyImageTemplateId").html();
	htmlContent = htmlContent.replace(new RegExp("#fileName", 'g'),fileName);
	htmlContent = htmlContent.replace(new RegExp("#serverBaseUrl", 'g'),getDocumentRootURL()); 
	htmlContent = htmlContent.replace(new RegExp("#isTmpImage", 'g'),isTmpImage); 
	htmlContent = htmlContent.replace(new RegExp("#controller", 'g'),controllerName); 
	htmlContent = htmlContent.replace(new RegExp("#compteurIndex", 'g'),compteurIndex); 
	htmlContent = htmlContent.replace(new RegExp("#relativeUrl", 'g'),relativeUrl); 
	htmlContent = htmlContent.replace(new RegExp("#imageId", 'g'),imageId); 
	htmlContent = htmlContent.replace("paintImage/_small80","paintImage"+relativeUrl+"/_medium");
	htmlContent = htmlContent.replace(new RegExp("#currentImgId", 'g'),imageId);
	
	common.showPopupWarningContent(htmlContent);
       }
    
    deletePicto = function (index, imageId,fromTmp) {
    	var currentImageId = parseInt(imageId);
    	var url = documentRootURL + '/imagesControllerAjax/delete/' + imageId + '.json?fromTmp='+fromTmp;
    	$.fancybox.showLoading();
    	$.ajax({
    		type : "POST",
    		url : url,
    		beforeSend : setHeader,
    		success : function(data) {
    			if (data.result) {
    				$("#big_img_url_"+index).closest("tr").remove();
    				$.fancybox.close();
    			} else {
    				alert("Impossible de supprimer l'image..."+imageId);
    			}
    			$.fancybox.hideLoading();
    		},
    		error : function(request, status, err) {
    			$.fancybox.hideLoading();
    			alert(status + " : " + err);
    		}
    	}); // Fin ajax
    }
    
    saveNewImageName = function (currentImgId,imageOldName,isTmpImage)
    {
    	var imageNewName = $("#imageSelectionnee"+currentImgId).val();
    	var currentImageId = parseInt(currentImgId);
    	var url = "";
    	if (currentImageId < 0) {
    		url = documentRootURL + '/controllerName/ajax/renameImage.json?imageId='+currentImageId+'&imageNewName='+imageNewName+'&isTmpImage='+isTmpImage+"&imageOldName="+imageOldName;
    	} else {
    		url = documentRootURL + '/imagesControllerAjax/ajax/renameImage.json?imageId='+currentImageId+'&imageNewName='+imageNewName+'&isTmpImage='+isTmpImage+"&imageOldName="+imageOldName;
    	}
    	
    	$.fancybox.showLoading();
    	$.ajax({
    		type : "POST",
    		url : url,
    		beforeSend : setHeader,
    		success : function(data) {

    			if (data.result) {
    				$.fancybox.close();
    				window.location.reload();

    			} else {
    				alert("Impossible de modifier l'image...");
    			}
    			$.fancybox.hideLoading();

    		},
    		error : function(request, status, err) {
    			$.fancybox.hideLoading();
    			alert(status + " : " + err);
    		}
    	}); 
    	// Fin ajax
    }
    initUploadDatatable = function(idTable, urlAjax) {
    	var table = $('#' + idTable).DataTable({
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
    			"width" : "20%",
    			"orderable" : false,
    			"data": "image"
    		}, {
    			"width" : "20%",
    			"orderable" : true,
    			"data": "fileName"
    		}, {
    			"width" : "20%",
    			"orderable" : true,
    			"data": "fileSize"
    			
    		}, {
    			"width" : "20%",
    			"orderable" : true,
    			"data": "fileDimension"
    		}, {
    			"width" : "20%",
    			"orderable" : false,
    			"data": "action"
    		}],
    		"ajax" : {
    			"url" : urlAjax,
    			"type" : "POST",
    			"dataSrc" : 'data'
    		}
            	,
            	"language" : {
            		"url" : common.getDocumentRootURL()+'/js/jquery/datatables/Fr.txt'
            	}
    	});
     }
	return {
		init: init,
		printUploadDataTableSearchResult : printUploadDataTableSearchResult,
		printDataTableModifyImageNamePopup : printDataTableModifyImageNamePopup,
		deletePicto : deletePicto,
		initUploadDatatable : initUploadDatatable,
		saveNewImageName : saveNewImageName
	};
})();
