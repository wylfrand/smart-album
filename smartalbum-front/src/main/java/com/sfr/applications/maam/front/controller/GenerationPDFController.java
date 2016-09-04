package com.sfr.applications.maam.front.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.SFRITextRenderer;

import com.mycompany.services.model.commun.SfrLine;
import com.sfr.applications.maam.front.utils.Constant;
import com.sfr.applications.maam.front.utils.ViewName;
import com.sfr.applications.maam.model.eliame.LigneSimulation;
import com.sfr.applications.maam.model.eliame.SimulationInfos;
import com.sfr.applications.maam.model.restitution.AmeMember;
import com.sfr.applications.maam.model.restitution.AmesForSfrUser;
import com.sfr.applications.maam.model.restitution.Group;
import com.sfr.applications.maam.model.restitution.SubscribedGroupeAme;

@Controller
public class GenerationPDFController extends AMaamController {
    
    private static final Logger log = LoggerFactory.getLogger(GenerationPDFController.class);
    
    private static final SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_PDF);
    
    public final String codesPJ = "PJMP01";
    
    @Value("${pdf.piecesjustificatives.mentionslegales}")
    private String mentionsLegales;
    
    @Resource(name = "maamVelocityEngine")
    private VelocityEngine velocityEngine;
    
    /**
     * Méthode de test
     * 
     * @param code
     * @param os
     */
    public void testPdf(String code, OutputStream os) {
        try {
            String templateString = StringUtils.EMPTY;
            // Convert info to map
            
            List<String> numeros = new ArrayList<String>();
            for (int i = 0; i < 2; i++) {
                numeros.add("062030409" + i);
            }
            
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("code", "PJMPF");
            param.put("nom", "Nom00");
            param.put("prenom", "Prenom");
            param.put("codePDV", "aaaaa");
            param.put("mentionsLegales", mentionsLegales.split("<br/>"));
            param.put("premierNumero", "0620304056");
            param.put("deuxiemeNumero", "");
            param.put("numeroADSL", "0120304056");
            param.put("numeros", numeros);
            param.put("date", sdf.format(new Date()));
            
            long time1 = System.currentTimeMillis();
            templateString = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, ViewName.TEMPLATE_PDF_DIR + code
                                                                                         + Constant.EXT_VM, Constant.ENCODING, param);
            
            ITextRenderer renderer = new ITextRenderer();
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(templateString.getBytes(Constant.ENCODING)));
            renderer.setDocument(doc, ViewName.TEMPLATE_PDF_DIR);
            renderer.layout();
            renderer.createPDF(os);
            log.debug("Pdf a été correctement généré en :" + (System.currentTimeMillis() - time1) + "ms.");
            
            os.flush();
            os.close();
        } catch (Exception e) {
            log.error("Erreur de la génération PDF", e);
        }
    }
    
    /**
     * Méthode qui génère le fichier pdf pour les pièces justificatives, il faut créer un fichier template .vm portant le code du
     * groupe s'il s'agit d'un nouveau groupe AME
     * 
     * @param ameName
     * @param propositionId
     * @param evidenceTypeCode
     * @param request
     * @return
     */
    @RequestMapping(value = "/doc/formulaire/downloadPiecesJustificatives")
    public ResponseEntity<byte[]> downloadPdf(@RequestParam(required = false) String ameName,
                                              @RequestParam(required = false) String code,
                                              @RequestParam(required = false) String propositionId,
                                              @RequestParam(required = false) String evidenceTypeCode,
                                              @RequestParam String numeroLigne, HttpServletRequest request) {
        if (StringUtils.isEmpty(ameName)) {
            ameName = "PiecesJustificatives";
        }
        // définir les paramètres dans le http header afin de pouvoir lancer un téléchargement côté navigateur
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        responseHeaders.add(Constant.ATTACHMENT_HEADER_NAME, Constant.HEADER_ATTACHMENT + ameName.replaceAll(" ", "_") + ".pdf");
        responseHeaders.add("Expires", "0");
        responseHeaders.add("Cache-Control", "no-cache, must-revalidate");
        try {
        	List<String> list = new ArrayList<String>();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            generatePdf(code, propositionId, evidenceTypeCode, numeroLigne, os,list);
            os.flush();
            byte[] body = os.toByteArray();
            os.close();
            
            return new ResponseEntity<byte[]>(body, responseHeaders, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Erreur de la génération PDF", e);
        }
        return null;
    }
    
    /**
     * Méthode qui génère le fichier pdf pour les pièces justificatives, il faut créer un fichier template .vm portant le code du
     * groupe s'il s'agit d'un nouveau groupe AME
     * 
     * @param ameName
     * @param propositionId
     * @param evidenceTypeCode
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/doc/formulaire/downloadManagerPiecesJustificatives")
    public ResponseEntity<byte[]> downloadPdfForConnectedLine(HttpServletRequest request,@RequestParam(required = false) Boolean isRestitution) throws Exception {
        
        SfrLine sfrUser = findSfrLine(getCurrentLineNumber());
        
        AmesForSfrUser ameUser = null;
        ameUser = getAmesForSfrUser(true, sfrUser);
        
        
        Group currentGroup = null;
        String numeroLigne = null;
        
        
   	 	List<SubscribedGroupeAme> subscribedGroups = ameUser.getSubscribedGroupeAmes();
   	 	List<String> listLineWaitingForPJ = new ArrayList<String>();
   	 	if(subscribedGroups != null && subscribedGroups.size()>0 && isRestitution!=null && isRestitution.booleanValue())
   	 	{
   	 		currentGroup = (Group) subscribedGroups.get(0);
   	 		
   	 		//MAAM-472
   	 	    numeroLigne = currentGroup.getManager();
   	 	    //MAAM-472  Gestion des PDF dans ADDAMS
   	        //Les lignes suivantes seront pré-rempli avec les numéros de lignes en attente de PJ
   	 	    for(AmeMember member : subscribedGroups.get(0).getAmeMembers()){
   	 	    	if(member.isWaitingForPJ() && !member.getPerson().getLine().getNumber().equals(numeroLigne)){
   	 	    		listLineWaitingForPJ.add(member.getPerson().getLine().getNumber());
   	 			}
   	 	    }
   	 	    	
   	 	}
		 else 
		 {
			 	SimulationInfos simulationInfos = ameUser.getSimulationInfos();
			 	currentGroup = (Group)simulationInfos.getChosenProposition().getGroupeSimulations().get(0);

				//MAAM-472
				numeroLigne = simulationInfos.getChosenProposition().getGroupeSimulations().get(0).getAdministrateur().getLigne().getNumLigne();
				List<LigneSimulation> members = simulationInfos.getChosenProposition().getGroupeSimulations().get(0).getLigneSimulations();
				  
				//MAAM-472  Gestion des PDF dans ADDAMS
		        //Les lignes suivantes seront pré-rempli avec les numéros de lignes en attente de PJ
				for(LigneSimulation member : members){
					if (member.isWaitingForADMCompensation() && !member.getLigne().getNumLigne().equals(numeroLigne)){
							listLineWaitingForPJ.add(member.getLigne().getNumLigne());
					}
				}
			}
   	 	
   	 	
   	 	
        
        if(currentGroup == null)
        {
        	return null;
        }
        
        String code = null;
        if(ameUser.getSimulationInfos()!=null)
        {
        	code = ameUser.getSimulationInfos().getCurrentGroupeCode();
        }
        	
        String ameName = currentGroup.getName();
        String propositionId = "";
        if(ameUser.getSimulationInfos()!=null && ameUser.getSimulationInfos().getChosenProposition()!=null)
        {
        	propositionId = ameUser.getSimulationInfos().getChosenProposition().getPropositionId();
        }
        String evidenceTypeCode = null;
        
        if (currentGroup.getAdminRelations() != null && currentGroup.getAdminRelations().size() == 1) {
            evidenceTypeCode = "PJMPFP";
        }
        
       
        
        if(StringUtils.isBlank(numeroLigne))
        {
        	numeroLigne =  ameUser.getSimulationInfos().getConnectedLine().getNumber();
        }
        
        if (StringUtils.isEmpty(ameName)) {
            ameName = "PiecesJustificatives";
        }
        // définir les paramètres dans le http header afin de pouvoir lancer un téléchargement côté navigateur
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        responseHeaders.add(Constant.ATTACHMENT_HEADER_NAME, Constant.HEADER_ATTACHMENT + ameName.replaceAll(" ", "_") + Constant.EXT_PDF);
        responseHeaders.add("Expires", "0");
        responseHeaders.add("Cache-Control", "no-cache, must-revalidate");
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            generatePdf(code, propositionId, evidenceTypeCode, numeroLigne, os,listLineWaitingForPJ);
            os.flush();
            byte[] body = os.toByteArray();
            os.close();
            
            return new ResponseEntity<byte[]>(body, responseHeaders, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Erreur de la génération PDF", e);
        }
        return null;
    }
    
    /**
     * Méthode pour générer le pdf
     * 
     * @param code code d'AME, facultatif si propositionId est présent
     * @param propositionId propositionId, facultatif si code est présent
     * @param evidenceTypeCode code de la relation pour la pièce justificative
     * @param numeroLigne numéro de ligne pour laquelle la pièce justificative est demandée
     * @param os output stream pour écrire le fichier
     * @throws Exception
     */
    private void generatePdf(String code, String propositionId, String evidenceTypeCode, String numeroLigne,
                             OutputStream os, List<String> listLineWaitingForPJ) throws Exception {
        String templateString = StringUtils.EMPTY;
        // Convert info to map
        String name = StringUtils.EMPTY;
        String firstName = StringUtils.EMPTY;
        
        Map<String, Object> param = new HashMap<String, Object>(); 
        param.put("premierNumero", numeroLigne);
        
        //MAAM-472  Gestion des PDF dans ADDAMS
        //Les lignes suivantes seront pré-rempli avec les numéros de lignes en attente de PJ
        param.put("listLineWaitingForPJ", listLineWaitingForPJ);
        //
        param.put("code", StringUtils.isEmpty(evidenceTypeCode) ? "" : evidenceTypeCode);
        param.put("nom", name);
        param.put("prenom", firstName);
        param.put("mentionsLegales", mentionsLegales.split("<br/>"));
        param.put("numeroLigne", numeroLigne);
        
        long time1 = System.currentTimeMillis();
        templateString = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, ViewName.TEMPLATE_PDF_DIR + code + Constant.EXT_VM,Constant.ENCODING, param);
        
        // générer le pdf
        ITextRenderer renderer = new SFRITextRenderer();
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(templateString.getBytes(Constant.ENCODING)));
        renderer.setDocument(doc, ViewName.TEMPLATE_PDF_DIR);
        renderer.layout();
        renderer.createPDF(os);
        log.debug("Pdf a été correctement généré en :" + (System.currentTimeMillis() - time1)
                  + "ms. with template : " + ViewName.TEMPLATE_PDF_DIR + code + Constant.EXT_VM);
    }
}
