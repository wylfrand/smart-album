<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page session="false" %>
<%@ page import="java.io.InputStream, java.util.Properties" %>
<%
request.setAttribute("dont-log-supervision-page", Boolean.TRUE);

InputStream mf=application.getResourceAsStream("/META-INF/MANIFEST.MF");
Properties prop= new Properties();

if (mf!=null){
	prop.load(mf);
}
%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="fr" lang="fr">
	<head>
		<title>Supervision <%=prop.getProperty("Implementation-Title","")%></title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	</head>
	<body>
		supervision - V <%=prop.getProperty("Implementation-Version","")%>

        <!-- DEBUT AUTRES INFORMATIONS -->
        Build-Date: <%=prop.getProperty("Build-Date","")%> <br/>
        
        Scm-Branch: <%=prop.getProperty("Scm-Branch","")%> <br/>
        
        Svn-Revision : <%=prop.getProperty("Svn-Revision","")%> <br/>
        <!-- FIN AUTRES INFORMATIONS -->
	</body>
</html>