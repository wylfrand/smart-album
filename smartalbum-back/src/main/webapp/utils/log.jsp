<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.util.Properties"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jsp/common/include.jsp" %>


<%@page import="java.util.Date"%>
<%@page import="java.io.BufferedReader" %>
<%@page import="java.lang.StringBuffer" %>
<%@page import="java.text.SimpleDateFormat"%>

<%
    // L'env a gérer
    String env = request.getParameter("env");
    out.println("ENV: " + env + "<br />");
    
    // Le fichier log4j.properties de l'environnement
	InputStream logProperties = application.getResourceAsStream("/WEB-INF/config/" + env + "/log4j.properties");
	Properties prop = new Properties();
	
	if(logProperties != null){
	    prop.load(logProperties);
	}
	
    boolean validation = true;
    int nbLigneDisplay = 100;
    try{
    	nbLigneDisplay = Integer.valueOf(request.getParameter("nbd"));
    }catch(Exception e){
    }
    
    out.println("NB lignes &agrave; afficher: " + nbLigneDisplay + "<br />");
    
    BufferedReader fic = null;
    
    String logFile = prop.getProperty("log4j.appender.debugfile.File");
    
    logFile = StringUtils.replace(logFile,
					              "${catalina.base}",
					              System.getProperty("catalina.base"));
    out.println("L'URI du fichier de log: " + logFile + "<br />");
    
    int nbLigne = 0;
    
    StringBuilder logBuffer = new StringBuilder();
    
    if(logFile != null){
        try{
            fic = new BufferedReader(new FileReader(logFile));
         
            nbLigne = 0;
             
	         while((fic.readLine()) != null){
	             nbLigne++;
	         }
	         System.gc();
	         
	         if(fic != null){
	             try {
	                 fic.close();
	             }catch(Exception e){
	             }
	         }
	         
	         fic = new BufferedReader(new FileReader(logFile));

	    }catch(Exception e){
	        out.println("Erreur: " + e);
	    }
	
	    out.println("Nombre de lignes dans le fichier de logs: " + nbLigne + "<br />");
	    
	    int ligneCourrante = 0;
	    String line = null;
	    try{
	        if(validation){
	            while((line = fic.readLine()) != null){
	                if((nbLigne - ligneCourrante) <= nbLigneDisplay){
	                    logBuffer.append(line);
	                    logBuffer.append("\n");
	                }
	                
	                ligneCourrante++;
	            }
	        }
	    }catch(Exception e){
	    	out.println("Erreur: " + e);
	        validation = false;
	    }finally{
	        if (fic != null){
	            try{
	                fic.close();
	            }catch(Exception e){
	            	out.println("Erreur: " + e);
	                validation = false;
	            }
	        }
	    }
    }else{
    	out.println("Aucun fichier de log trouvé");
    }
%>
<% 
    if(validation){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy '-' HH:mm:ss");
        String today = sdf.format(new Date());
%>
        <% request.setAttribute("date", sdf.format(new Date())); %>
        Le fichier de log du back office maam avec ${nbLigneDisplay} lignes &agrave; la date du ${date}"<br/><br/>
<%
        if(logBuffer.toString().length() > 0){
%>
            <textarea rows="30" cols="160" readonly="readonly" style="font-size: 11px">
                <%=logBuffer.toString()%>
            </textarea><br /><br />
<%      }else{ %>
            Aucun fichier de trouver
<%      }
    }
%>