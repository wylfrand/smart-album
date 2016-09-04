<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<script language="JavaScript">
	function Validate() {
		var image = document.getElementById("image").value;
		if (image != '') {
			var checkimg = image.toLowerCase();
			if (!checkimg.match(/(\.jpg|\.png|\.JPG|\.PNG|\.jpeg|\.JPEG)$/)) {
				alert("Please enter  Image  File Extensions .jpg,.png,.jpeg");
				document.getElementById("image").focus();
				return false;
			}
		}
		return true;
	}
</script>
	<div id="mainContainer">
		<div class="bd" id="tabs">
			<div id="infoLayerMultipack" class="infoLayer">
				<ul class="js-blockTabs">
<!-- 				<li class="tabs"><a href="#orgnizeCategory" -->
<!-- 						class="js-tabLink openState"><span>Créer des catégories</span> -->
<!-- 					</a></li> -->
					<li class="tabs"><a href="#importFiles"
						class="js-tabLink openState"><span>Importer images</span>
					</a></li>
					<li class="tabs"><a href="#sliderId" class="js-tabLink closeState"><span>Mise à jour par catégorie</span>
					</a></li>
					
				</ul>
				<div class="separator"></div>
				<div id="importFiles" data-images-size="${fn:length(files)}" data-controller="${controller}" data-images="${files}">
					<div class="contentColumns">
						<div class="mainContent">
							<div class="leftContent">
								<%@ include file="/WEB-INF/jsp/page/upload/galery.jsp" %>
							</div>
							<div class="rightContent">
								<div class="slidingContent">
								</div>
							</div>
						</div>
					</div>
				</div>
				
<!-- 				<div id="sliderId"> -->
<%-- 					<%@ include file="/WEB-INF/jsp/page/images/slider.jsp" %> --%>
<!-- 				</div> -->
			</div>
		</div>
	</div>
<%@ include file="/WEB-INF/jsp/common/popup.jsp" %>
