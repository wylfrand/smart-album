<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:richx="http://richfaces.org/richx"
	xmlns:rich="http://richfaces.org/rich">
	<ui:decorate
		template="#{controller.isProfileEditable(model.selectedUser) ? 'userPrefs/userPrefsEdit.xhtml' : 'userPrefs/userPrefs.xhtml'}">
		<ui:param name="user" value="#{model.selectedUser}" />
		<ui:define name="header">
			<h:outputText value="#{messages['user_profile_']}"
				styleClass="h1-style" />
		</ui:define>
		<ui:define name="controls">
			<h:panelGrid columns="2"
				rendered="#{controller.isProfileEditable(model.selectedUser)}"
				styleClass="user-prefs-button">
				<h:panelGroup layout="block">
					<richx:commandButton actionListener="#{controller.editUser()}"
						value="#{messages['user.save']}" reRender="mainArea" />
				</h:panelGroup>
				<h:panelGroup layout="block">
					<richx:commandButton immediate="true"
						actionListener="#{controller.cancelEditUser()}"
						value="#{messages['user.cancel']}" reRender="mainArea" />
				</h:panelGroup>
			</h:panelGrid>

			<h:panelGrid
				rendered="#{!controller.isProfileEditable(model.selectedUser)}"
				columns="2" style="margin-top : 10px">
				<richx:commandButton actionListener="#{controller.cancelEditUser()}"
					reRender="mainArea" value="#{messages['ok']}" />
			</h:panelGrid>
		</ui:define>
	</ui:decorate>
</ui:composition>