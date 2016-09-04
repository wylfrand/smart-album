<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<HTML>
<HEAD>
<TITLE>Application menu and body</TITLE>
<style type="text/css">
form {
  background: none repeat scroll 0 0 lavender;
    border-radius: 8px;
    color: midnightblue;
    height: 82px;
    margin-bottom: 124px;
    margin-left: 256px;
    margin-top: 122px;
    padding: 2px 20px;
    width: 647px;
}
</style>
<script type="text/javascript">
$(document).ready(function() {
	
	setTimeout(function() {
	       window.location.href = "<c:url value='/authentification/logout.html' />"
	      }, 5000);
});
</script>
</HEAD>
<BODY style="margin: 0; overflow: auto;" class="body_background">
	<form>
		<TABLE cellpadding="0" cellspacing="20" border="0" align="center" width="100%" summary="Body" class="body">
			<tr>
				<TD valign="top" class="background">
					<TABLE cellpadding="0" cellspacing="0" border="0" width="80%" align="center" summary="Body" class="body">
						<TR style="margin-left: 15;">
							<TD class="body">L'opération a été effectuée avec succès !<BR> Vous serez automatiquement redirigé vers la page d'authentification dans 5 secondes.<BR>
								<A href="<c:url value='/authentification/logout.html' />"><b>Cliquez ici</b></A> pour être redirigé maintenant.
							</TD>
						</TR>
					</TABLE>
				</TD>
			</TR>
		</TABLE>
	</form>
</BODY>