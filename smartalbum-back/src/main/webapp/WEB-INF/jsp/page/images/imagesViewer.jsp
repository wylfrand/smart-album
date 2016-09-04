<%@ include file="/WEB-INF/jsp/common/include.jsp" %>

<style>
<!--

-->
select#custom-headers{
	width: 268px;
}

select#images_dest_list{
	width: 268px;
}
</style>
<!-- multi-select -->
<link rel="stylesheet" type="text/css" href="<c:url value='/js/lou-multi-select-8712b02/css/multi-select.css'/>"></link>
<c:if test="${actionSuccess}">
    <font color='red'>Album enregistré avec succ&egrave;s</font>
</c:if>

<!-- Formulaire -->
<form:form id="imagesForm" name="imagesForm" method="POST" modelAttribute="imagesForm">

<div id="orgnizeCategory">
	<div class="gallery">
		<div class="sorting">
			<div id="galery_thumbnail_categories">
				<span>Filter photos by</span> <a class="sortLink selected"
					data-keyword="all" href="#">Toutes les pages</a>
					<c:forEach items="${categories}" var="categorie" varStatus="compteur">
						<a class="sortLink" data-keyword="${categorie}" href="#">${categorie}</a>
					</c:forEach>
				<div class="clear_both"></div>
			</div>
		</div>
		<div class="photos">
			<div class="thumbnail_container">
				<c:forEach items="${albumSource.images}" var="image" varStatus="compteur">
					<a target="_blank" class="thumbnail showMe fancybox" id="thumbnailShowMe${compteur.index}"
						href="<c:url value='/imagesController/paintImage${image.fullPath}/fullImage.html'/>"
						title="${image.name}/${image.size}" data-keywords="${image.category}" rel="group">
						<img id='galery_thumbnail${compteur.index}' src="<c:url value='/imagesController/paintImage${image.fullPath}/_small120.html'/>"/>
					</a>
				</c:forEach>
			</div>
		</div>
	</div>
</div>
    <div class="borderedDiv" style="margin-left:50px; margin-top:20px;">
    <span class="leftTop"></span>
    <span class="rightTop"></span>
    <br />
    <!-- GESTION DES IMAGES -->
    <table width="500px" cellspacing="0" cellpadding="0" border="0" class="selectable">
        <tr>
            <td width="200px">
                <div class="selectMultiTop">Images à déplacer ${albumSource.name}</div>
                <div>
                <span>Déplacez les images dans :</span>
                <select id="myShelfTochoose">
                    <option>-- choose album --</option>
                    <c:forEach items="${albumSource.shelf.albums}" var="album" varStatus="sta">
                        <option value="${album.id}">
                        	${album.name}
                        </option>
                    </c:forEach>
                </select>
                </div>
                <select id="public-methods" size="15" multiple="multiple">
                    <c:forEach items="${albumSource.images}" var="image" varStatus="sta">
                        <option class="searchable" value="${image.id}">
                        	${image.name}
                        </option>
                    </c:forEach>
                </select>
                <br/>
                <a href='#' id='select-all'>select all</a>
				<a href='#' id='deselect-all'>deselect all</a>
                <div class="selectMultiBottom"></div>
            </td>
        </tr>
    </table>
    
    <div class="spacer5"></div>
    <span class="leftBottom"></span>
    <span class="rightBottom"></span>
</div><!-- fin borderedDiv -->

<!-- ##### Boutons ##### -->
<div id="save_zone">
    <div class="hubo_confirmBtnPanel" id="ame_create_btn" style="display: none;">
        <a href="#" class="huboBtnLink100" id="ame_create_link" onclick="javascript:createAme();">
            <span>Enregistrer</span>
        </a>
    </div>
    <div class="hubo_confirmBtnPanel" id="ame_update_btn" style="display: none;">   
        <a href="#" class="huboBtnLink100" id="ame_update_link" onclick="javascript:updateAme();">
            <span>Enregistrer</span>
        </a>
    </div>
</div>
</form:form>

<%-- <script language="javascript" type="text/javascript" src="<c:url value='/js/lou-multi-select-8712b02/js/quick-search.js'/>"></script> --%>
<script type="text/javascript">
 
	    $(document).ready(function(){
	    	// Construction de la liste des images en AJAX
	    	//$("#images_dest_list").imagepicker();
	    	$('a.sortLink').on('click', function(e) {
				e.preventDefault();
				$('a.sortLink').removeClass('selected');
				$(this).addClass('selected');
				var keyword = $(this).attr('data-keyword');
				sortThumbnails(keyword);
			});
			if ($(".rotableImg").exists()) {
				$(".rotableImg").rotate({
					bind : {
						mouseover : function() {
							$(this).rotate({
								animateTo : 15
							});
						},
						mouseout : function() {
							$(this).rotate({
								animateTo : -15
							});
						}
					}
				});
			}

			$('.gallery .sorting').css('margin-bottom',
					window.thumbnailSpacing + 'px');
			$('.thumbnail_container a.thumbnail').addClass(
					'showMe').addClass('fancybox').attr(
					'rel', 'group');

			positionThumbnails();

			setInterval('checkViewport()', 75);
			
			
			/////////////////////////////////////
			// Search item //////////////////////
			/////////////////////////////////////
			$('#public-methods').multiSelect();
			
			$('#select-all').click(function(){
			  $('#public-methods').multiSelect('select_all');
			  return false;
			});
			$('#deselect-all').click(function(){
			  $('#public-methods').multiSelect('deselect_all');
			  return false;
			});
			
			$('#refresh').on('click', function(){
			  $('#public-methods').multiSelect('refresh');
			  return false;
			});
			$('#add-option').on('click', function(){
			  $('#public-methods').multiSelect('addOption', { value: 42, text: 'test 42', index: 0 });
			  return false;
			});
	    });
	    
		/**
		 * Déplace toutes les images disponibles vers les images de l'Album
		 */
	    function addAllImagesToAlbum(){
			var listeAlbumsSources =  document.getElementById("custom-headers");
			for(var i = 0; i < listeAlbumsSources.length; i++){
				$("#images_dest_list").addOption(listeAlbumsSources.options[i].value, 
												 listeAlbumsSources.options[i].text);								
			}
			$("#custom-headers").removeOption(/./);
			$("#images_dest_list").sortOptions();
	    }
		
	    
		/**
		 * Ajoute aux images de l'Album toutes les images séléctionnés parmi les profils dispo
		 */
	    function addImagesToAlbum(){
			var elements = $("#custom-headers").selectedOptions();
			// Confirmation
			if(elements.length > 0){
				for(var i = 0; i < elements.length; i++){
					$("#custom-headers").removeOption(elements[i].value, true);
					$("#images_dest_list").addOption(elements[i].value, elements[i].text);								
				}
				$("#images_dest_list").sortOptions();
			}
	    }
		
		
	    /**
		 * Enl&egrave;ve tous les images de l'Album
		 */
	    function removeAllImagesFromAlbum(){
			var listeAlbumDest =  document.getElementById("images_dest_list");
			
			for(var i = 0; i < listeAlbumDest.length; i++){
				$("#custom-headers").addOption(listeAlbumDest.options[i].value, 
												 listeAlbumDest.options[i].text);								
			}
			$("#images_dest_list").removeOption(/./);
			$("#custom-headers").sortOptions();
	    }
	    
		/**
		 * Enl&egrave;ve toutes les images séléctionnées parmi les images de l'Album
		 */
	    function removeImagesFromAlbum(){
			var elements = $("#images_dest_list").selectedOptions();

			// Confirmation
			if(elements.length > 0){
				for(var i = 0; i < elements.length; i++){
					$("#images_dest_list").removeOption(elements[i].value, true);
					$("#custom-headers").addOption(elements[i].value, elements[i].text);								
				}
				$("#custom-headers").sortOptions(); 
			}
	    }
</script>
