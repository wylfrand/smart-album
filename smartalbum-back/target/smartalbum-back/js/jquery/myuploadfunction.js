
var thumbnailSpacing = 15;
var documentRootURL = getContextRoot();

$.fn.exists = function() {
	return this.length > 0;
};


$(function() {
	
	if ($("#image-pickerFromFact").exists()) {
	$("#image-pickerFromFact").select2({
		minimumInputLength: 2,
		placeholder: "Chercher une image dans cet album",
	    formatResult: format,
	    formatSelection: format,
	    escapeMarkup: function(m) { return m; }
	    });
	$('#select-allFromFact').click(function(){
		  $('#image-pickerFromFact').multiSelect('select_all');
		  return false;
		});
		$('#deselect-allFromFact').click(function(){
		  $('#image-pickerFromFact').multiSelect('deselect_all');
		  return false;
		});
};

	$(".fancybox")
			.attr('rel', 'gallery')
			.fancybox(
					{
						beforeShow : function() {
							if (this.title) {
								// New line
								this.title += '<br />';

								// Add tweet button
								this.title += '<a href="https://twitter.com/share" class="twitter-share-button" data-count="none" data-url="'
										+ this.href + '">Tweet</a> ';

								// Add FaceBook like button
								this.title += '<iframe src="//www.facebook.com/plugins/like.php?href='
										+ this.href
										+ '&amp;layout=button_count&amp;show_faces=true&amp;width=500&amp;action=like&amp;font&amp;colorscheme=light&amp;height=23" scrolling="no" frameborder="0" style="border:none; overflow:hidden; width:110px; height:23px;" allowTransparency="true"></iframe>';
							}
						},
						afterShow : function() {
							// Render tweet button
							twttr.widgets.load();
						},
						helpers : {
							title : {
								type : 'inside'
							}
						}
					});

	$("#submit1").hover(function() {
		$(this).animate({
			"opacity" : "0"
		}, "slow");
	}, function() {
		$(this).animate({
			"opacity" : "1"
		}, "slow");
	});

	if ($("#tabs").exists()) {
		$("#tabs").tabs();
	}

	$(document).ajaxStart(function() {
		$("#loading").show();
	});

	$(".modifierDescriptionImage").click(function() {
		var id = $(this).attr("id");
		$("#messageHTMLLayer" + id).toggle('slow');
	});

	$(".blogAjustHTMLCCC").toggle('slow');

	$(".modifierDescription").click(function() {
		var id = $(this).attr("id");
		$("#messageHTMLLayer" + id).toggle('slow');

		return false;
	});

	// Toggle gallery thumb info box
	$('.entry').mouseenter(function() { 
		$('.default-links', this).animate({
			'height' : 20
		}, 300, function() {
			$(this).css({
				'display' : 'block'
			});
		});
	}).mouseleave(function() {
		$('.default-links', this).animate({
			'height' : 0
		}, 600, function() {
			$(this).css({
				'display' : 'none'
			});
		});
	});

	var images = [];
	if ($(".ws_thumbs a").length > 0) {
		$(".ws_thumbs a").each(function() {
			var $this = $(this);
			images.push({
				src : $this.attr('data-src'),
				title : $this.attr('data-title') % window.thumbnailSpacing
			});
		});
	}

	jQuery('#selectedPubShelf').change(function() {
		jQuery('#selectedOwnShelf').val("");
	});

	jQuery('#selectedOwnShelf').change(function() {
		jQuery('#selectedPubShelf').val("");
	});

	jQuery('.preview_box_album_120').click(function() {

	});

	var controllerName = "";
	var relativeUrl = "";

	if ($("#galleryPluralSight").exists()) {
		controllerName = $("#galleryPluralSight").attr("data-controller");
		relativeUrl = $("#galleryPluralSight").attr("relativePath");
		initPSGalery(controllerName,relativeUrl);
	} else {
		controllerName = $("#importFiles").attr("data-controller");
	}

	if ($('#fileupload').exists()) {
		$('#fileupload')
				.fileupload({
					dataType : 'json',
					done : function(e, data) {
						$("a:has(.thumbnail)").remove();
						$("tr:has(td)").remove();
						$.fancybox.showLoading();
						$.each(data.result,
							function(index, file) {
								var img_modif = documentRootURL+ '/img/images_modif.jpg';
								var img_suppr = documentRootURL+ '/img/p_supprimer.gif';
								var thumbFileNames = [];
								$(".thumbnail_container a").each(function() {
									thumbFileNames.push($(this).attr("title"));
								});
								var existingFileName = file.fileName+ "/"+ file.fileSize;
								// On ne traite pas les fichier déjà traité ie ceux qui le couple nom/taille déjà traité
								var url = documentRootURL+ '/imagesController/paintTmpImage/'+file.fileName+'/'+controllerName+'/_medium.html';
								$("#uploaded-files tbody")
										.append($('<tr id="pictoId_'+ index+ '"/>')
										.append($('<td/>').html("<a id='downloadImage"+ index+ "'>"
																	+ (index + 1)
																	+ "&nbsp;&nbsp;&nbsp;<img id='big_img_url_"
																	+ index
																	+ "'/></a>"))
										.append($('<td class="first center"/>').text(file.fileName))
										.append($('<td class="center"/>').text(file.fileSize+ '/'+ file.fileType))
										.append($('<td class="center"/>').text(file.dimension))
										.append($('<td id="lastCol"'+ index+ '" class="last center"/>')
										));

								var currentImg = $("#big_img_url_"+ index);
								currentImg.attr("src", url)
										.attr("width","100px")
										.attr("height","100px")
										.attr("title","Grande image")
										.attr("alt","Loading ...")
										.attr("align","absmiddle");

								// ON met a jour les
								// catégories
								// de trie
								var anChorWithSearchValue = [];
								$("#galery_thumbnail_categories a").each(
									function() {
										anChorWithSearchValue.push($(this).attr("data-keyword"));
								});

								if ($.inArray(file.fileSize,anChorWithSearchValue) == -1) {
									$("#galery_thumbnail_categories")
											.append($('<a/>').attr("class","sortLink")
															.attr("data-keyword",file.fileSize)
															.attr("href","#")
															.append(file.fileSize));
								}

								if ($.inArray(existingFileName,thumbFileNames) == -1) {
									$(".thumbnail_container")
											.append($('<a/>').attr("class","thumbnail")
															.attr("href",url)
															.attr("title",file.fileName+ "/"+ file.fileSize)
															.attr("data-keywords",file.fileSize)
															.append('<img id="galery_thumbnail'+ index+ '" />'));
									$("#galery_thumbnail"+ index).attr("src",url);
									$("#downloadImage"+ index).attr("href",url);
									$("#file" + index).click(function() {
														downloadPhoto(e,index);
									});
								}
							});
					$.fancybox.hideLoading();
				},
				progressall : function(e, data) {
					var progress = parseInt((data.loaded/ data.total) * 100, 10);
					$('#progress .bar').css('width', progress + '%');
				},
				stop :function(e)
				{
				    $("#mainContainer").after( "<p class='ajustUploadButton'><a onclick='javascript: window.location.reload();' class='upload-validation'>OK</a></p>" );
				},
				dropZone : $('#dropzone')
			});
	}

	var anChorWithSearchValue = [];
	$("#thumbnail_container a").each(
			function() {
				if ($.inArray($(this).attr("data-keywords"),
						anChorWithSearchValue) == -1) {
					anChorWithSearchValue.push($(this).attr("data-keywords"));
				}
			});

	for (var i = 0; i < anChorWithSearchValue.length; i++) {
		$("#galery_thumbnail_categories").append(
				$('<a/>').attr("class", "sortLink").attr("data-keyword",
						anChorWithSearchValue[i]).attr("href", "#").append(
						anChorWithSearchValue[i]));
	}

	$('a.sortLink').on('click', function(e) {
		e.preventDefault();
		$('a.sortLink').removeClass('selected');
		$(this).addClass('selected');
		var keyword = $(this).attr('data-keyword');
		sortThumbnails(keyword);
	});

	$('.gallery .sorting').css('margin-bottom', window.thumbnailSpacing + 'px');
	$('.thumbnail_container a.thumbnail').addClass('showMe').addClass(
			'fancybox').attr('rel', 'group');

	positionThumbnails();

	setInterval('checkViewport()', 750);

});

function format(state) {
    if (!state.id) return state.text; // optgroup
    var originalOption = state.element;
    var imgSrc = $(originalOption).data('src');
    var result = "<img  onclick=\"javascript:printMediumImage('"+imgSrc+"');\" class='flag' src='"+imgSrc+ "' /><br/>" + state.text;
    return result;
    }
    
function getPathContextRoot() {
	var protocol = window.location.protocol;
	var url = window.location.href;
	var host = window.location.host;
	return protocol + "//" + window.location.host + "/";
}

function stopSlider()
{
	$("#image-pickerFromFact").addClass("stop");
	$.modal.close();
}

function startSlider()
{
	$("#image-pickerFromFact").removeClass("stop");
	getSelectedFullImages();
}
    
function getPathContextRoot() {
	var protocol = window.location.protocol;
	var url = window.location.href;
	var host = window.location.host;
	return protocol + "//" + window.location.host + "/";
}

function initPSGalery(controllerName,relativePath) {
	var loadCount = 0;
	var g = new PS.Gallery('galleryPluralSight');

}

function showAlbumLayer() {
	$('#albumContent').modal({
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

function showBlockInLayer(blockId) {
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

function getContextRoot() {
	var protocol = window.location.protocol;
	var url = window.location.href;
	var host = window.location.host;
	var lastPart = url.split(host);
	return protocol + "//" + window.location.host + "/"
			+ lastPart[1].split('/')[1];
}

function downloadPhoto(e, index) {
	$.fancybox.showLoading();
	var url = documentRootURL + '/controller/get/' + index + '.html';
	// window.location.href="http://localhost:8080/smartalbum-back/controller/get/1.html";
	if (e.preventDefault) {
		e.preventDefault();
		window.location.href = url;
		return false;
	} else {
		e.returnValue = false;
		window.location.href = url;
	}
	$.fancybox.hideLoading();
}



/**
 * Met &agrave; jour l'affichage de la grande image
 */
function updateBigImageUrl(index) {
	var imageUrl = $("#big_img_url").val();
	if (imageUrl != null && imageUrl != "") {
		$("#big_img_url_fixe").attr("src", imageUrl);
		$("#big_img_url_fixe").show();
	} else {
		$("#big_img_url_fixe").hide();
	}
}

function checkViewport() {
	var photosWidth = $('.photos').width();
	var thumbnailContainerWidth = $('.thumbnail_container').width();
	var thumbnailWidth = $('.thumbnail_container a.thumbnail:first-child')
			.outerWidth();

	if (photosWidth < thumbnailContainerWidth) {
		positionThumbnails();
	}

	if ((photosWidth - thumbnailWidth) > thumbnailContainerWidth) {
		positionThumbnails();
	}
}

function sortThumbnails(keyword) {
	$('.thumbnail_container a.thumbnail').each(
			function() {
				var thumbnailKeywords = $(this).attr('data-keywords');

				if (keyword == 'all') {
					$(this).addClass('showMe').removeClass('hideMe').attr(
							'rel', 'group');
				} else {
					if (thumbnailKeywords.indexOf(keyword) != -1) {
						$(this).addClass('showMe').removeClass('hideMe').attr(
								'rel', 'group');
					} else {
						$(this).addClass('hideMe').removeClass('showMe').attr(
								'rel', 'none');
					}
				}
			});
	positionThumbnails();
}

function positionThumbnails() {
	// $('.debug-remainder').html('');
	$.fancybox.showLoading();
	$('.thumbnail_container a.thumbnail.hideMe').animate({
		opacity : 0
	}, 500, function() {
		$(this).css({
			'display' : 'none',
			'top' : '0px',
			'left' : '0px'
		});
	});

	var containerWidth = $('.photos').width();
	var thumbnail_R = 0;
	var thumbnail_C = 0;
	var thumbnailWidth = $('a.thumbnail img:first-child').outerWidth()
			+ window.thumbnailSpacing;
	var thumbnailHeight = $('a.thumbnail img:first-child').outerHeight()
			+ window.thumbnailSpacing;
	var max_C = Math.floor(containerWidth / thumbnailWidth);

	$('.thumbnail_container a.thumbnail.showMe').each(function(index) {
		var remainder = (index % max_C) / 100;
		var maxIndex = 0;
		// $('.debug-remainder').append(remainder+' - ');

		if (remainder == 0) {
			if (index != 0) {
				thumbnail_R += thumbnailHeight;
			}
			thumbnail_C = 0;
		} else {
			thumbnail_C += thumbnailWidth;
		}

		$(this).css('display', 'block').animate({
			'opacity' : 1,
			'top' : thumbnail_R + 'px',
			'left' : thumbnail_C + 'px'
		}, 500);

		var newWidth = max_C * thumbnailWidth;
		var newHeight = thumbnail_R + thumbnailHeight;
		$('.thumbnail_container').css({
			'width' : newWidth + 'px',
			'height' : newHeight + 'px'
		});
	});
	detectFancyboxLinks();

	var sortingWidth = $('.thumbnail_container').width() / thumbnailWidth;
	var newWidth = sortingWidth * thumbnailWidth - window.thumbnailSpacing;
	$('.sorting').css('width', newWidth + 'px');
	$.fancybox.hideLoading();
}

function detectFancyboxLinks() {

	$('a.fancybox').unbind('click.fb');
	if ($(window).width() < 550) {
		$('.thumbnail_container a.thumbnail').removeClass('fancybox').attr(
				'target', '_blank');
	} else {
		$('.thumbnail_container a.thumbnail').removeAttr('target');
	}
}

function toggleselectPicture(imageName, imageId) {
	$.fancybox.showLoading();
	var urlToggleSelect = documentRootURL + '/controller/toggleSelect.json';
	$.ajax({
		type : "POST",
		cache : false,
		url : urlToggleSelect,
		data : {
			'imageName' : imageName,
			'imageId' : imageId
		},
		success : function(data) {
			$.fancybox.hideLoading();
		},
		error : function(request, status, err) {
		    alert("Impossible de sélectionner une image, merci de contacter votre administrateur!");
			$.fancybox.hideLoading();
		}
	});
}

function massiveAction(actionValue) {
    var existCheckedCheckboxes = $('.filecheckboxes:checked').size() > 0;
    if(existCheckedCheckboxes || actionValue != 'NONE'){
		$.fancybox.showLoading();
		var baseURL = documentRootURL;
		var actionUrl = baseURL + '/controller/massiveAction.json?action='+actionValue;
		var masiveActionFormFormObj = 
		{'action' : actionValue
		};
		
		var masiveActionFormObjStr = JSON.stringify(masiveActionFormFormObj);
		$.ajax({
					type : "POST",
					cache : false,
					url : actionUrl,
					dataType: "json",
					contentType : "application/json",
					data : masiveActionFormObjStr,
					success : function(data) {
					    
						if (actionValue == 'TOOGLE_SELECT_ALL') {
							$('.filecheckboxes')
									.prop(
											'checked',
											$('.filecheckboxes:not(:checked)')
													.size() > 0);
						} else if (actionValue == 'CREATE_ALBUM' || actionValue == 'MODIFY_ALBUM') {
							
							var controllerName = "controller";
							if(actionValue == 'MODIFY_ALBUM')
							{
								controllerName = "albumsController";
								// SelectedAlbumName
								$("#name").val(data.resultObject.selectedAlbumName);
								$("#descriptionAlbum").val(data.resultObject.selectedAlbumDescription);
								$("#selectedOwnShelf").val(data.resultObject.selectedAlbumShelf);
								// 
							}
							
							$("#selectedCoverAlbum").html(
									data.resultObject.selectedPicturesOptions);
							$('#imageSize').text(
									"(" + data.resultObject.selectedFilesSize
											+ " images)");
							if (data.resultObject.selectedFilesSize > 0) {
								$("#valider_infos_album").show();
								
								var coveringImgUrl = baseURL
								+ "/imagesController/paintImage/"
								+ data.resultObject.defaultSelectedPicture.basUrl+data.resultObject.defaultSelectedPicture.imageName
								+ "/_small200.html";
								$('#coveringImage').replaceWith(
										'<img id="coveringImage" src="'
												+ coveringImgUrl + '"/>');
								
							} else {
								$("#valider_infos_album").hide();
								$("#selectedCoverAlbum").val("");
								var coveringImgUrl = baseURL
										+ "/imagesController/paintDefaultImage/default/noimage_small200.jpg.html";
								$('#coveringImage').replaceWith(
										'<img id="coveringImage" src="'
												+ coveringImgUrl + '"/>');
							}
							showAlbumLayer();
						} else if (actionValue == 'REMOVE_SELECTED') {
						    
						    $('.filecheckboxes:checked').closest("tr").remove();
						}
						else if (actionValue == 'REMOVE_ALL') {
						    window.location.reload();
						}
						$.fancybox.hideLoading();
						$('#massiveAction').val("NONE");
					},
					error : function(request, status, err) {
						$.fancybox.hideLoading();
						alert(err);
					}
				});
    }
}

function showAlbum(index) {
	url = documentRootURL + '/albumsController/show/' + index + '.html';
	$.fancybox.showLoading();
	$.ajax({
		type : "POST",
		url : url,
		success : function(data) {
			$.fancybox.hideLoading();
		},
		error : function(request, status, err) {
			$.fancybox.hideLoading();
		}
	});// Fin ajax
}

function validateChanges(albumId) {

	url = documentRootURL + '/albumsController/ajax/editAlbum.html';
	if (typeof albumId !== "undefined") {

		$.fancybox.showLoading();

		$.ajax({
			type : "POST",
			url : url,
			data : {
				'albumId' : albumId
			},
			success : function(data) {
				$.fancybox.hideLoading();
				document.location.href = documentRootURL
						+ "/albumsController/editAlbum/" + albumId + ".html";
			},
			error : function(request, status, err) {
				$.fancybox.hideLoading();
			}
		});// Fin ajax
	}
}

function submitRichText(messageId, entityType) {
	document.forms[0].method = "POST";
	document.forms[0].text.value = getXHTML(trim(document
			.getElementById(rteName).contentWindow.document.body.innerHTML));
	save(messageId, document.forms[0].text.value, entityType);
}

// Enregistre le nouveau contenu
function save(messageId, messageHTML, entityType) {
	$.fancybox.showLoading();
	var urlMessage = documentRootURL
			+ '/messageHTMLController/ajax/update.json';

	if (entityType == 'undefined' || entityType == '') {
		entityType = 'IMAGE';
	}
	
	var longDescriptionFormObj = {
			id : messageId,
			description : messageHTML,
			entityType : entityType
	};
	
	var longDescriptionFormObjStr = JSON.stringify(longDescriptionFormObj);
	$.ajax({
		type : 'POST',
		url : urlMessage,
		cache : false,
		contentType : "application/json",
		data : longDescriptionFormObjStr,
		success : function(response) {
			$(".blogAjustHTMLCCC").toggle("slow");
			$("#printedMessage").html(response.resultObject);
			$.fancybox.hideLoading();
		},
		error : function(request, status, err) {
			$.fancybox.hideLoading();
			alert("Erreur lors de la sauvegarde..."+status+" *** "+err);
		}
	});
}

function setHeader(xhr) {
    xhr.setRequestHeader("Accept", "application/json");
}

function saveComment(url) {
	$.fancybox.showLoading();
	var urlMessage = documentRootURL + url;
	
	var newComment = {
			imageId : $("#imageId1").val(),
			message : $("#messageId").val(),
			albumId : $("#albumId").val()
	};
	var newCommentObjStr = JSON.stringify(newComment);
	
	$.ajax({
		type : 'POST',
		url : urlMessage,
		cache : false,
		contentType : "application/json",
		data : newCommentObjStr,
		success : function(response) {
			if ($(".comments-holder .comment-item:last-child").exists()) {
				$(".comments-holder .comment-item:last-child").after(response);
				$("#messageId").val("");
			} else {
				$(".comments-holder h5").after(response);
			}

			var nbComment = $(".comments-holder .comment-item").length;

			if (nbComment > 1) {
				$(".comments-holder h5").text(nbComment + " Commentaires");
			} else {
				$(".comments-holder h5").text(nbComment + " Commentaire");
			}

			$.fancybox.hideLoading();
			// window.location.reload();
			$("#msgThanks").toggle('slow');

		},
		error : function(request, status, err) {
			$.fancybox.hideLoading();
			$("#msgAlert").toggle('slow');
		}
	});
}

function showActivity() {
	// clearInterval(loadingTimer);

	$.fancybox.showLoading();
	// loadingTimer = setInterval(_animate_loading, 66);
}

function hideActivity() {
	// clearInterval(loadingTimer);

	$.fancybox.hideLoading();
	// loadingTimer = setInterval(_animate_loading, 66);
}

function showPopupWarning(messageId) {

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
		'content' : $("#" + messageId).html()
	});
}

function editAlbum(albumId) {
	window.location.href = getContextRoot() + '/albumsController/editAlbum/'
			+ albumId + '.html';
	;
}

function deleteAlbum(index) {
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
			window.location.href = getContextRoot()
					+ '/shelvesController/publicShelves.html';
			;
		},
		error : function(request, status, err) {
			$.fancybox.hideLoading();
			alert("Impossible de supprimer l'album...");
		}
	});// Fin ajax
}

function setHeader(xhr) {
	xhr.setRequestHeader("Accept", "application/json");
}



function selectAlbum(imgSrc, shelfName, albumName,albumDescription,albumNbImg,albumId)
{
	var imageSource = $('#imgFactToCopy img');
	imageSource.attr("src",imgSrc);
	
	$('#selectedShelfName').text(shelfName);
	$('#selectedAlbumName').text(albumName);
	$('#selectedAlbumDescription').text(albumDescription);
	$('#selectedNbImage').text(albumNbImg);
}

function getSelectedFullImages()
{
	var imagesSRC = [];
	if ($("#s2id_image-pickerFromFact .flag").length > 0) {
		$("#s2id_image-pickerFromFact .flag").each(function() {
			var $this = $(this);
			var imgToPush = $this.attr('src').replace("_small80","fullImage");
			imagesSRC.push(imgToPush);
		});
	};

    	$.each(imagesSRC, function(index, value) {
	var stop = false;
	setTimeout(function() {
	    if ($('#image-pickerFromFact').hasClass("stop")) {
		stop = true;
		return false;
	    } else {
		printFullImageInLayerFactWithSrc(value);
	    }
	}, index * 4000);
	if (stop) {
	    return false;
	}
    });
	
}
function printFullImageInLayerFactWithSrc(imgSrc)
{
	var result = "<a onclick=\"javascript:showBlockInLayer('fullImgToPrint');\"> <img  onclick=\"javascript:stopSlider();\"  src='"+imgSrc+"' height='100%'/></a><br/>";
	$.fancybox.showLoading();
	var img = new Image();
	img.onload = function()
    {
    	
     	$("#fullImgToPrint").html(img);
     	$.fancybox.hideLoading();
     	showBlockInLayer("fullImgToPrint");
    };
    img.height='100%';
    img.src = imgSrc;
    img.onclick = function() { stopSlider(); };
    return false;
}
function printFullImageInLayerFact()
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
function printFullImage(layerImageId,imageReltivePath, compteurId) {
	var fileMetaId = -1;
	if (layerImageId > -1) {
		fileMetaId = layerImageId;
	} else {
		fileMetaId = compteurId;
	}
	$.fancybox.showLoading();
    var img = new Image();
    img.onload = function()
    {
    	$("#layer-image"+fileMetaId).html(img);
    	$.fancybox.hideLoading();
    	showBlockInLayer('layer'+fileMetaId);
    };
    img.height='100%';
    img.src = getContextRoot()+imageReltivePath;
	return false;
}

function printMediumImage(imgSrc)
{
	var imageToprint = imgSrc.replace("small80","medium");
	$('#imgFactToCopy img').replaceWith('<img id="coveringImage" src="'+imageToprint+'"/>');
	printFullImageInLayerFact();
}

function showImageManager(albumId)
{
	var albumToOrganizeObj = {
			albumId : albumId
	};
	var albumToOrganizeObjStr = JSON.stringify(albumToOrganizeObj);
	
	url = getContextRoot() + '/albumsController/ajax/organizeImages.json?albumId='+albumId;
	$.fancybox.showLoading();
	$.ajax({
		type : "POST",
		data : albumToOrganizeObjStr,
		dataType : "text",
		url : url,
		success : function(data) {
			$.fancybox.hideLoading();
			$("#imagesManager").html(data);
			showBlockInLayer("imagesManager");
		},
		error : function(request, status, err) {
			$.fancybox.hideLoading();
			alert("Impossible déplacer les images dans l'album."+ status+" - "+err);
		}
	});// Fin ajax
}
function closeSimpleModal() {
	$.modal.close();
}
