<ui:composition xmlns="http://www.w3.org/1999/xhtml"
                xmlns:ui="http://java.sun.com/jsf/facelets"
                xmlns:h="http://java.sun.com/jsf/html"
                xmlns:f="http://java.sun.com/jsf/core"
                xmlns:s="http://jboss.com/products/seam/taglib"
                xmlns:a4j="http://richfaces.org/a4j"
                xmlns:rich="http://richfaces.org/rich"
                xmlns:richx="http://richfaces.org/richx">
                <ui:include src="/includes/image/imageHeaderInfo.xhtml" />
		
    <h:panelGroup layout="block" styleClass="image-edit-div">
		<rich:graphValidator>
			<table class="image-edit-div-table" cellpadding="5" border="0">
				<tr>
					<td valign="top" nowrap="true" class="image-edit-label">
						#{messages['photo_name']}
					</td>
					<td valign="top" colspan="2">
						<h:inputText id="image_name"
									 value="#{model.selectedImage.name}"
									 styleClass="image-edit-photo-input">
							<rich:ajaxValidator event="onblur"/>
						</h:inputText>
					</td>
				</tr>
				<tr>
					<td style="padding:0; margin:0;" />
					<td style="padding:0; margin:0;" colspan="2">
						<rich:message for="image_name" styleClass="required"/>
					</td>
				</tr>
				<tr>
					<td valign="top" nowrap="true" class="image-edit-label">
						Captured at
					</td>
					<td valign="top">
						<a4j:outputPanel id="calendar" layout="block">
							<rich:calendar id="image_uploaded"
										   styleClass="image-edit-calendar"
										   enableManualInput="true"
										   value="#{model.selectedImage.created}"
										   cellWidth="24px" cellHeight="22px">
								<rich:ajaxValidator event="oninputblur"/>
							</rich:calendar>
						</a4j:outputPanel>
					</td>
					<td width="200" valign="top">
						<h:selectBooleanCheckbox style="margin : 0px" value="#{model.selectedImage.showMetaInfo}"/>#{messages['show_meta_information']}
					</td>
				</tr>
				<tr>
					<td style="padding:0; margin:0;"/>
					<td style="padding:0; margin:0;">
						<rich:message for="image_uploaded" styleClass="required"/>
					</td>
					<td style="padding:0; margin:0;"/>
				</tr>
				<tr>
					<td valign="top" nowrap="true" class="image-edit-label">
						#{messages['description']}
					</td>
					<td valign="top" colspan="2">
						<h:inputTextarea value="#{model.selectedImage.description}" styleClass="image-edit-description-input"/>
					</td>
				</tr>
				<tr>
					<td valign="top" nowrap="true" class="image-edit-label">
						#{messages['popular_tags']}
					</td>
					<td valign="top" colspan="2">
						<h:inputText value="#{model.selectedImage.meta}" id="text" styleClass="image-edit-photo-input"/>
						<rich:suggestionbox height="100" id="suggestionBoxId" for="text" tokens=",[]"
						suggestionAction="#{imageManager.autoComplete}" var="result"
						fetchValue="#{result.tag}"
						minChars="1"
						columnClasses="center"

						>
						<h:column>
							<h:outputText value="#{result.tag}" />
						</h:column>
						</rich:suggestionbox>
						<br/>
						<h:panelGroup style="color : #666666">
							<a4j:repeat value="#{imageManager.popularTags()}" var="tag" rowKeyVar="row">
								<h:outputText value=", " rendered="#{!(row == 0)}"/>
								<a4j:outputPanel style="cursor:pointer" onclick="selectPopularTag('#{tag.tag}', #{rich:element('text')});">
									#{tag.tag}
								</a4j:outputPanel>
							</a4j:repeat>
						</h:panelGroup>
					</td>
				</tr>
				<tr>
					<td valign="top" nowrap="true" class="image-edit-label">
						#{messages['direct_link']}
					</td>
					<td valign="top" colspan="2">
						<h:outputText styleClass="image-edit-direct-link "
									  value="#{imageManager.getImageDirectLink(model.selectedImage)}"/>
					</td>
				</tr>
				<tr>
					<td valign="top" nowrap="true" class="image-edit-label">

					</td>
					<td valign="top" colspan="2">
						<h:selectBooleanCheckbox value="#{model.selectedImage.covering}" style="margin : 0px"/>
						#{messages['use_this_photo_like_current_album_cover']}
					</td>
				</tr>
				<tr>
					<td valign="top" nowrap="true" class="image-edit-label">

					</td>
					<td valign="top" colspan="2">
						<h:selectBooleanCheckbox value="#{model.selectedImage.allowComments}" style="margin : 0px"/>
						#{messages['allow_commennts']}
					</td>
				</tr>
				<tr>
					<td valign="top">


					</td>
					<td valign="top" align="right" colspan="2" style="padding : 10px;">
						<richx:commandButton value="#{messages['image.save']}" style="float: left" 
											 actionListener="#{imageManager.editImage(model.selectedImage, false)}" reRender="mainArea"/>
						<richx:commandButton value="#{messages['image.cancel']}" immediate="true"
											 actionListener="#{controller.cancelEditImage(model.selectedImage)}" reRender="mainArea"/>
					</td>
				</tr>
			</table>
		</rich:graphValidator>
	</h:panelGroup>
	<ui:include src="/includes/image/imageGeneralInfo.xhtml" />
</ui:composition>