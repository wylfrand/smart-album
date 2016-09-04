<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<!-- multi-select -->
<link rel="stylesheet" type="text/css" href="<c:url value='/js/select2-3.5.1/select2.css'/>"></link>
<script src="<c:url value='/js/select2-3.5.1/select2.min.js'/>" type="text/javascript"></script>

<link rel="stylesheet" type="text/css" href="<c:url value='/js/lou-multi-select-8712b02/css/multi-select.css'/>"></link>
<script language="javascript" type="text/javascript" src="<c:url value='/js/lou-multi-select-8712b02/js/jquery.multi-select.js'/>"></script>


<script type="text/javascript">

function format(state) {
    if (!state.id) return state.text; // optgroup
    var originalOption = state.element;
    return "<img class='flag' src='"+$(originalOption).data('src')+ "' /><br/>" + state.text;
    }
    
	function updateAlbum()
	{
		var selected = new Array();
		var albumId = $("#myAlbumTochoose").val();
		var selectionSearchStringIds = '#ms-public-methods .ms-elem-selection.ms-selected';
		 $(selectionSearchStringIds).each(function() {
			 selected[selected.length] = $(this).text().trim();
		 });
		 var jsonImageForm = {
				 imageNames :  selected,
				 albumId : albumId
		 }

		 $.ajax({
		        url: "<c:url value='/imagesControllerAjax/ajax/moveImages.json' />",
				data : JSON.stringify(jsonImageForm),
		        success: function(data){    
		        	window.location.href = "<c:url value='/albumsController/editAlbum/"+albumId + ".html'/>";
		        }
		    ,
		    dataType: "json",
		    cache: false,
		    type: "POST",
		    contentType: "application/json"
		  });
	}
    
    function getSelectedImageTab(selectionSearchStringIds)
    {
    	var selectionSearchTab = [];
    	$(selectionSearchStringIds).each(function() {
			selectionSearchTab.push($(this).text().trim());
		});
    	$("#image-picker").val(selectionSearchTab).trigger("change");
    	return selectionSearchTab;
    }

    $(document).ready(function(){
    	$("#image-picker").select2({
    		minimumInputLength: 2,
    		placeholder: "Chercher une image dans l'album",
    	    formatResult: format,
    	    formatSelection: format,
    	    escapeMarkup: function(m) { return m; }
    	    });
    	
    	$("#myAlbumTochoose").select2();
    	
		$("#image-picker").on("change", function(e) {
			console.log("change " + JSON.stringify({
								val : e.val,
								added : e.added,
								removed : e.removed
							}));
						}).on("select2-opening", function() {
							//alert("ouverture!");
						}).on("select2-selecting", function(e) { 
						var selectionSearchStringIds = '#public-methods .ms-elem-selection.ms-selected';
							getSelectedImageTab(selectionSearchStringIds);
							$('#public-methods').multiSelect('select', e.val);
						}).on("select2-open", function() {
							//alert("ouvert");
						}).on("select2-close", function() {
						}).on("select2-removing", function(e) { 
							$('#public-methods').multiSelect('deselect', e.val);
						
						}).on("select2-highlight",
								function(e) {
									console.log("highlighted val=" + e.val + " choice="
											+ JSON.stringify(e.choice));
								});

						var selectableSearchString = [];

						$('#public-methods')
								.multiSelect(
										{
											selectableHeader : "<div class='custom-header'>Photos de l'abum</div>",
											selectionHeader : "<div class='custom-header'>Photos à déplacer</div>",
											afterInit : function(ms) {
												var that = this, $selectableSearch = that.$selectableUl
														.prev(), $selectionSearch = that.$selectionUl
														.prev(), selectableSearchString = '#'
														+ that.$container
																.attr('id')
														+ ' .ms-elem-selectable:not(.ms-selected)', selectionSearchString = '#'
														+ that.$container
																.attr('id')
														+ ' .ms-elem-selection.ms-selected';
											},
											afterSelect : function(values) {
												var that = this;
												var selectionSearchStringIds = '#'
														+ that.$container
																.attr('id')
														+ ' .ms-elem-selection.ms-selected';
												
												getSelectedImageTab(selectionSearchStringIds);
											},
											afterDeselect : function(values) {
												var that = this;
												var selectionSearchStringIds = '#'
														+ that.$container
																.attr('id')
														+ ' .ms-elem-selection.ms-selected';
												
												var selectionSearchTab = getSelectedImageTab(selectionSearchStringIds);
												var object = selectionSearchTab
														.indexOf(values);
												if (object != -1) {
													selectionSearchTab.splice(
															object, 1);
												}
												$("#image-picker").val(
														selectionSearchTab)
														.trigger("change");
											}
										});

						$('#select-all').click(function() {
							$('#public-methods').multiSelect('select_all');
							return false;
						});
						$('#deselect-all').click(function() {
							$('#public-methods').multiSelect('deselect_all');
							return false;
						});

						$('#refresh').on('click', function() {
							$('#public-methods').multiSelect('refresh');
							return false;
						});
						$('#add-option').on('click', function() {
							$('#public-methods').multiSelect('addOption', {
								value : 42,
								text : 'test 42',
								index : 0
							});
							return false;
						});

					});
</script>

<!-- <input type="submit" name="valider_infos_album" -->
<!-- 		id="fermerr_infos_album" -->
<!-- 		onclick="javascript: closeSimpleModal();return false;" value="Fermer"></input> -->

<!-- Formulaire -->
<form:form id="imagesForm" name="imagesForm" method="POST" modelAttribute="imagesForm" style="margin-right : 10px">
<c:set var='nbImage' value="${fn:length(albumSource.images)}"/>
<c:set var='expresseionAccordee' value="${nbImage > 1 ? 'les images' : 'l image'}"/>
<!-- GESTION DES IMAGES -->
<h4>Déplacez  ${expresseionAccordee} de l'album - ${albumSource.name} - <br/>vers d'autres albums</h4>
<div class="photos">
			<select id="image-picker" class="image-picker show-labels show-html" data-limit="${fn:length(albumSource.images)}"
				multiple="multiple" show-html>
				<c:forEach items="${albumSource.images}" var="image" varStatus="compteur">
					<option data-src="<c:url value='/imagesController/paintImage${image.fullPath}/_small80.html'/>" data-label="${image.name}">
						${image.name}</option>
				</c:forEach>
				</select>
			</div>
    <table cellspacing="0" cellpadding="0" border="0" class="selectable">
        <tr>
            <td>
                <select id="public-methods" size="15" multiple="multiple">
                    <c:forEach items="${albumSource.images}" var="image" varStatus="sta">
                        <option class="searchable" value="${image.name}">
                        	${image.name}
                        </option>
                    </c:forEach>
                </select>
                <br/>
                <a href='#' id='select-all'>Sélectionner tout</a>
				<a href='#' id='deselect-all'>Déselectionner tout</a>
				<a href='#' id='refresh'>Refresh</a>
            </td>
        </tr>
    </table>
    <!-- ##### Boutons ##### -->
                <div>
                <span>Déplacez les images sélectionnées dans l'album:</span>
                
                <select id="myAlbumTochoose">
                    <option value="-1">-- choose album --</option>
                    <c:forEach items="${albumSource.owner.shelves}" var="ownerShelf" varStatus="comp">
                    	<optgroup label="${ownerShelf.name}">
		                    <c:forEach items="${ownerShelf.albums}" var="album" varStatus="sta">
		                        <option value="${album.id}">
		                        	${album.name}
		                        </option>
		                    </c:forEach>
	                    </optgroup>
                    </c:forEach>
                </select>
                <a href="#" class="album_update_btn" id="album_update_link" onclick="javascript:updateAlbum();">
            <span>Enregistrer</span>
        </a>
                </div>
</div><!-- fin borderedDiv -->
    <div class="hubo_confirmBtnPanel" id="album_update_btn" style="display: inline;">   
        
    </div>
</form:form>
<style>

.select2-container-multi .select2-choices {
    width: 450px!important;
}

#image-picker {
    width: 179px;
}

/* #s2id_myShelfTochoose { */
/*     width: 279px; */
/* } */
</style>

