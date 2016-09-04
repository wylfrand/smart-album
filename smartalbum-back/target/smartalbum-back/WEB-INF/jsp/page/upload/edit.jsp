<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jsp/common/include.jsp" %>

<div class="maam_line"></div>

<c:set var="title" value="Création d'un picto :" />
<c:if test="${ isEdit }">
	<c:set var="title" value="Modification d'un picto :" />
</c:if>
<h2 class="maam_form_title">${title}</h2>

<!-- Formulaire -->
<form:form method="POST" modelAttribute="pictoForm">
	<spring:bind path="id">
		<form:hidden path="id" />
	</spring:bind>
	<table class="simple2lines2cols">
		<tr>
			<spring:bind path="status">
				<td class="border" width="15%">
					<label title="code de statut">Code de statut : </label>
				</td>
				<td class="border" width="15%">
					<form:input path="status" cssClass="inputText2" cssErrorClass="error-input" size="20" maxlength="75" />
				</td>
			</spring:bind>
			<spring:bind path="urljoya">
				<td class="border" width="10%">
					<label title="URL de picto">URL : </label>
				</td>
				<td class="border" width="60%">
					<form:input path="urljoya" cssClass="inputText2" cssErrorClass="error-input" size="80" maxlength="200" />
				</td>
			</spring:bind>
		</tr>
	</table>
</form:form>

<!-- ##### Boutons ##### -->
<div id="save_zone">
	<div class="hubo_confirmBtnPanel">
		<a href="javascript:savePicto(${isEdit});" class="huboBtnLink100">
			<span>Enregistrer</span>
		</a>
	</div>
</div>