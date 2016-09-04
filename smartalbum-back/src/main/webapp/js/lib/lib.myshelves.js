jQuery(document).ready(function() {  	
	myshelves.init();
	
});

$.fn.exists = function() {
	return this.length > 0;
};

var myshelves = (function() {

	init = function(){
		if ($(".draggable .preview_box_album_120").exists()) {

			$('.draggable .preview_box_album_120').draggable({
				containment : '.shelves',
				stack : '.draggable .preview_box_album_120',
				cancel : "a.ui-icon", // clicking an icon won't initiate dragging
				cursor : 'move',
				revert : true
			});

			$('.shelfBlock').droppable({
				accept : '.preview_box_album_120, .test',
				hoverClass : 'hovered',
				activeClass: "ui-state-hover",
				hoverClass: "ui-state-active",
				drop : function(event, ui) {
					dropAlbum(ui.draggable, $(this).find(".albumList .draggable"));
				}
			});
		}
		
		if ($(".draggableIndirectObj .test").exists()) {
			$('.draggableIndirectObj .test').draggable({
				containment : '.shelves',
				stack : '.draggableIndirectObj test',
				cancel : "a.ui-icon", // clicking an icon won't initiate dragging
				cursor : 'move',
				revert : true
			});
		}
		
        return true;
    };
    
    dropAlbum = function($item, $shelfBlock) {
	$.fancybox.showLoading();
	var url = common.getDocumentRootURL() + "/albumsControllerRest/ajax/dropAlbumToShelf.json?albumId="+$item.attr("data-albumid")+"&shelfId="+$shelfBlock.attr("data-droppableid");
	$.fancybox.showLoading();
	$.ajax({
		type : "POST",
		dataType : "json",
		data : {
			'albumId' : $item.attr("data-albumid"),
			'shelfId' : $shelfBlock.attr("data-droppableid")
		},
		url : url,
		success : function(data) {
			if (data.result == true) {
				$item.fadeOut(function() {
					$item.appendTo($shelfBlock);
					$item.fadeIn(function() {
					});
				});
			} else {
				alert("Impossible de déplacer l'album...");
			}
			$.fancybox.hideLoading();

		},
		error : function(request, status, err) {
			$.fancybox.hideLoading();
			alert("Assurez-vous d'être connecté, sinon merci de contater votre administrateur");
		}
	}); // Fin ajax

}

selectAlbum = function (imgSrc, shelfName, albumName,albumDescription,albumNbImg,albumId)
{
	var imageSource = $('#imgFactToCopy img');
	imageSource.attr("src",imgSrc);
	
	$('#selectedShelfName').text(shelfName);
	$('#selectedAlbumName').text(albumName);
	$('#selectedAlbumDescription').text(albumDescription);
	$('#selectedNbImage').text(albumNbImg);
}

	return {
		init: init,
		dropAlbum : dropAlbum,
		selectAlbum : selectAlbum
	};
})();
