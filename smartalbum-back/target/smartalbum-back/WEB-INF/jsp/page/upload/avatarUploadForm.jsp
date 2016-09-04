<div>
<center>
	<form:form method="post" enctype="multipart/form-data"
		modelAttribute="uploadedFile" action="fileUpload.htm">
		<table>
			<tr>
				<td>Upload File:</td>
				<td><input type="file" name="file" /></td>
				<td style="color: red; font-style: italic;">
				<form:errors path="file" /></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="Upload" /></td>
				<td></td>
			</tr>
		</table>
	</form:form>
</center>
</div>
