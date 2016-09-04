package com.sfr.applications.maam.front.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycompany.service.exception.AmeManagementException;
import com.mycompany.service.exception.EliameSimulationException;
import com.mycompany.services.model.commun.SfrLine;
import com.sfr.applications.maam.front.model.RetourReponse;
import com.sfr.applications.maam.front.utils.Constant;
import com.sfr.applications.maam.front.utils.ViewName;
import com.sfr.applications.maam.model.eliame.SimulationInfos;
import com.sfr.applications.maam.model.restitution.AmesForSfrUser;
import com.sfr.applications.maam.model.restitution.AvailableGroupeAme;
import com.sfr.applications.maam.model.restitution.GroupeAme;
import com.sfr.applications.maam.model.restitution.SubscribedGroupeAme;

/**
 * Controller gérant la réstitution des AME
 * 
 * @author amv
 */
@Controller
@Component("restitutionAmeController")
public class RestitutionAmeController extends AMaamController {
    
    public static transient final Logger log = LoggerFactory.getLogger(RestitutionAmeController.class);
    
    /*
     * Affichage la page de résitution des groupes AME: disponibles et souscrit par le client SFR
     * 
     * @throws AmeManagementException
     */
    @RequestMapping(value = "/groupe/affichage/index", method = RequestMethod.GET)
    public String index(
                        @RequestParam(required = false, defaultValue = "true") boolean reload,
                        @RequestParam(required = false, defaultValue = "false") boolean reloadFromCache, ModelMap model,
                        @RequestParam(required = false) String ligne) throws AmeManagementException {
        
        SfrLine sfrLine = findSfrLine(getCurrentLineNumber());
        AmesForSfrUser ame = getAmesForSfrUser(reloadFromCache, sfrLine);
        
        String code = null;
        
        if (ame.getPictoByJoyaStatus(sfrLine.getJoyaStatus()) != null) {
        	sfrLine.setJoyaPictoUrl(ame.getPictoByJoyaStatus(sfrLine.getJoyaStatus()).getUrljoya());
        }
        
        List<SubscribedGroupeAme> subscribedGroups = ame.getSubscribedGroupeAmes();
        SubscribedGroupeAme ameGroup = null;
        String contentId = Constant.CONTENT_2;
        if (CollectionUtils.isNotEmpty(subscribedGroups)) {
            ameGroup = subscribedGroups.get(0); 
            code = ameGroup.getCode();
            if (ameGroup.getAmeMembers().size() == 3 && !ameGroup.isAddams() && !ameGroup.isComplete()) {
                contentId = Constant.CONTENT_3;
            } else if (ameGroup.getAmeMembers().size() > 3 && !ameGroup.isAddams() && !ameGroup.isComplete()) {
                contentId = Constant.CONTENT_4;
            }
            httpSessionCacheManager.putObjectInCache(Constant.IS_ADDAMS, ameGroup.isAddams());
        }
        model.addAttribute(Constant.CONTENT_ID, contentId);
        
        // Test des droits d'accès
        testAccesForRestitution(sfrLine.isVisionPersonne(),sfrLine.getNumber());
        
        if (ame.getSimulationInfos() == null ||
            StringUtils.isEmpty(ame.getSimulationInfos().getCurrentGroupeCode())) {
            //reload the simulation informations if they are not initialized
            reload = true;
        }
        
        boolean isCreation = true;
        if (reload) {
            AvailableGroupeAme availabelGroup = null;
            if (StringUtils.isEmpty(code)) {
                if (ame.getAvailableGroupeAmes() != null && ame.getAvailableGroupeAmes().size() > 0) {
                    availabelGroup = ame.getAvailableGroupeAmes().get(0);
                    code = availabelGroup.getCode();
                }
            }
            
            if (availabelGroup != null) {
                if (isDuoProfileEligibleForAvailableGroupe(availabelGroup, sfrLine)) {
                        if (Constant.CODESCS_08.equals(sfrLine.getCodeScs()) 
                                    || (StringUtils.isNotEmpty(sfrLine.getLibellePTA()) && sfrLine.getLibellePTA().toUpperCase().contains(Constant.CHAINE_RED)))
                        {
                        log.debug("code SCS egale a 08");
                        return Constant.REDIRECT + ViewName.ERROR_INELIGIBLE;
                        }
                    ame.setSimulationInfos(initSimulationInfosForCreation(sfrLine,code, ame));
                } else {
                    return Constant.REDIRECT +  ViewName.ERROR_INELIGIBLE;
                }
            } else {
                ame.setSimulationInfos(initSimulationInfosForModification(code, ame));
                isCreation = false;
            }
        }
        
        //TODO : faire le point sur le groupe récupéré
        List<GroupeAme> groups = ame.getGroupeAmes();
        GroupeAme groupe = null;
        if(groups != null && groups.size()>0){
            groupe =  groups.get(0);}
        
        model.put(Constant.IS_CREATION, isCreation);
        model.put(Constant.AME_GROUP, ameGroup);
        model.put(Constant.IMAGE_ACCUEIL, groupe == null ? null:groupe.getSmallImgUrl());
        model.put(Constant.MENTIONS_LEGALES, groupe.getMentionsLegales());
        model.put(Constant.SFR_USER_MODEL, ame.getCurrentLine());
        model.put(Constant.ACTIVE_SIMU, false);
        
        return ViewName.RESTITUTION_GROUPE_INDEX_VIEW;
    }
    
    /**
     * Affichage la page d'error...
     * 
     * @throws AmeManagementException
     */
    @RequestMapping(value = "/error/service_indisponible", method = RequestMethod.GET)
    public String errorPageServiceIndisponible() throws AmeManagementException {
        
        return ViewName.ERROR_SERVICE_INDISPONIBLE_VIEW;
    }
    
    @RequestMapping(value = "/error/ineligible", method = RequestMethod.GET)
    public String errorPageIneligible() throws AmeManagementException {
        
        return ViewName.ERROR_INELIGIBLE_VIEW;
    }
    
    /**
     * Action de suppression d'une ligne
     * 
     * @throws AmeManagementException
     */
    @RequestMapping(value = "/action/groupe/line/delete/{number}", method = {RequestMethod.POST})
    public @ResponseBody
    RetourReponse deleteLine(@PathVariable("number") String number, ModelMap model)
        throws AmeManagementException {
        
        AmesForSfrUser ame = getAmesForSfrUser(true,findSfrLine(getCurrentLineNumber()));
        SimulationInfos simulationInfos = ame.getSimulationInfos();
        
        // On enlève la ligne
        simulationInfos.removeSimuLigne(number);
        
        return RetourReponse.ok();
    }
    
    /**
     * ajout d'une new line
     * 
     * @param msisdn
     * @param counter : ID of the row
     * @param model
     * @param response
     * @return
     * @throws Exception 
     * @throws AmeManagementException
     * @throws EliameSimulationException
     */
    @RequestMapping(value = "/action/groupe/creation/ajout/{msisdn}", method = {
        RequestMethod.POST
    })
    public String addNewSfrLigne(@RequestParam(required = false, defaultValue = "false") boolean back,
                                 @PathVariable("msisdn") String msisdn, @RequestParam("counter") String counter,
                                 @RequestParam("isCreation") boolean isCreation,
                                 @RequestParam("isLigneMobile") boolean isligneMobile, ModelMap model,
                                 HttpServletResponse response){
        log.debug("test");
        try {
        	
        	
            SfrLine sfrCurrentLine = findSfrLine(getCurrentLineNumber());
            AmesForSfrUser ame = getAmesForSfrUser(true, sfrCurrentLine);
            
            String code = ame.getGroupeAmes().get(0).getCode();
            
            // gere le back navigateur
            SimulationInfos simulationInfos = ame.getSimulationInfos();
            if(simulationInfos==null || simulationInfos.isMustReloadPage())
            {
                if(isCreation)
                {
                        initSimulationInfosForCreation(sfrCurrentLine, code, ame);
                }
                else
                {
                        initSimulationInfosForModification(code, ame);  
                }
            }
            simulationInfos = ame.getSimulationInfos();
            // Cache expire
            if (ame != null && ame.getSimulationInfos() != null) {
                boolean hasError = false;
                
                // la ligne du tableau est pour une ligne mobile
                if (isligneMobile) {
                    if (!msisdn.startsWith(Constant.NUM_06) && !msisdn.startsWith(Constant.NUM_07)
                        && !msisdn.startsWith("00")) {
                        hasError = true;
                        model.addAttribute("errorCode", "errorNotStartWithO6Or07");
                    }
                } else {
                    // la ligne du tableau est pour une ligne fix
                    if (msisdn.startsWith(Constant.NUM_06) || msisdn.startsWith(Constant.NUM_07) || msisdn.startsWith("00")) {
                        hasError = true;
                        model.addAttribute("errorCode", "errorStartWith06or07");
                    }
                }
                // appel au ResititutionAMEV41
                SfrLine newSfrLine = null;
                // check le reste des regles de gestion de la partie 3 controle saisie
                if (!hasError) {
                    newSfrLine = ajoutNewLigne(ame, msisdn, sfrCurrentLine, isCreation, model);
                    hasError = newSfrLine==null?true:false;
                }
                
                if (isCreation) {
                        if (isValidateGroup(simulationInfos.getSimuLignesAAjouter())) {
                                model.put(Constant.ACTIVE_SIMU, true);
                        } else {
                                model.put(Constant.ACTIVE_SIMU, false);
                        }
                } else {
                        if (simulationInfos.getSimuLignesAAjouter().size() == 0) {
                        model.put(Constant.ACTIVE_SIMU, false);
                    } else {
                        model.put(Constant.ACTIVE_SIMU, true);
                    }
                }
                
                
                boolean isAddams = true;
                
                if (httpSessionCacheManager.getObjectFromCache(Constant.IS_ADDAMS) != null) {
                    isAddams = (Boolean) httpSessionCacheManager.getObjectFromCache(Constant.IS_ADDAMS);
                }
                
                if (newSfrLine != null && sfrCurrentLine!=null) {
                    if (sfrCurrentLine.getFirstname().equals(newSfrLine.getFirstname())
                        && sfrCurrentLine.getName().equals(newSfrLine.getName())) {
                        model.addAttribute(Constant.SAMENAME, true);
                    } else {
                        model.addAttribute(Constant.SAMENAME, false);
                    }
                }
                
                if(sfrCurrentLine == null)
                {
                	 return ViewName.ERROR_KO_VIEW;
                }
                
                model.addAttribute("hasError", hasError);
                model.addAttribute(Constant.COUNTER, counter);
                model.addAttribute(Constant.MSISDN, msisdn);
                model.addAttribute(Constant.IS_ADDAMS, isAddams);
                model.addAttribute(Constant.IS_CREATION, isCreation);
                model.addAttribute(Constant.IS_ADDING, true);
                model.addAttribute(Constant.SFR_USER_MODEL, sfrCurrentLine);
                
                if (!hasError) {
                    log.debug("Libelle PTA de la ligne ajoutee: " + newSfrLine.getLibellePTA());
                    ajoutNewLineInJsp(ame, newSfrLine, isAddams, model);
                }
                
                // ligne ajouter
                return ViewName.RESULT_ADD_LINE;
            } else {
                log.info("Expiration du cache; simulationInfos est null, redirection vers : {}", ViewName.HOME);
                return ViewName.ERROR_KO_VIEW;
            }
        } catch (Exception e) {
            // TODO: handle exception
                log.error("erreur",e);
                return ViewName.ERROR_KO_VIEW;
        }
    }
        
        
    @RequestMapping(value = "/action/groupe/creation/modify/{msisdn}", method = {RequestMethod.POST})
    public String deleteSfrLigne(@PathVariable("msisdn") String msisdn, @RequestParam("counter") String counter,
                                 @RequestParam("isCreation") boolean isCreation, ModelMap model, HttpServletResponse response) {
        log.debug("suprimer une ligne");
        try {
            SfrLine sfrCurrentLine = findSfrLine(getCurrentLineNumber());
            AmesForSfrUser ame = getAmesForSfrUser(true, sfrCurrentLine);
            // Cache expire
            if (ame != null && ame.getSimulationInfos() != null) {
                SimulationInfos simulationInfos = ame.getSimulationInfos();
                
                simulationInfos.removeSimuLigne(msisdn);
                
                boolean isAddams = true;
                
                if (httpSessionCacheManager.getObjectFromCache(Constant.IS_ADDAMS) != null) {
                    isAddams = (Boolean) httpSessionCacheManager.getObjectFromCache(Constant.IS_ADDAMS);
                }
                
                if (isCreation) {
                        if (isValidateGroup(simulationInfos.getSimuLignesAAjouter())) {
                                model.put(Constant.ACTIVE_SIMU, true);
                        } else {
                                model.put(Constant.ACTIVE_SIMU, false);
                        }
                } else {
                        if (simulationInfos.getSimuLignesAAjouter().size() == 0) {
                        model.put(Constant.ACTIVE_SIMU, false);
                    } else {
                        model.put(Constant.ACTIVE_SIMU, true);
                    }
                }
                
                model.addAttribute(Constant.COUNTER, counter);
                model.addAttribute(Constant.IS_ADDAMS, isAddams);
                model.addAttribute(Constant.SFR_USER_MODEL, sfrCurrentLine);
                model.addAttribute(Constant.IS_CREATION, isCreation);
                model.addAttribute(Constant.IS_DELETE, true);
                
                // ligne suprimer
                return ViewName.RESULT_ADD_LINE;
            } else {
                log.info("Expiration du cache; simulationInfos est null, redirection vers : {}", ViewName.HOME);
                return Constant.REDIRECT + ViewName.HOME;
            }
        } catch (Exception e) {
            return Constant.REDIRECT + ViewName.HOME;
            // TODO: handle exception
        }
    }
    
    /**
     * Affichage la page de résitution des groupes AME: disponibles et souscrit par le client SFR
     * 
     * @throws AmeManagementException
     * */
//     @RequestMapping(value = "/test", method = RequestMethod.GET) public String test() throws AmeManagementException 
//     {
//    	return "/groupe/test-intranet/test";
//     }
     

}
