package com.sfr.applications.maam.front.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sfr.applications.maam.front.utils.ViewName;
import com.sfr.ws.psw.profile.v4_1.UserProfileFault_Exception;

/**
 * Le controlleur pour l'affichage des options du client
 * 
   * @author amv
 */
@Controller
public class MultiLigneController extends AMaamController {
    
    public static transient final Logger log = LoggerFactory.getLogger(MultiLigneController.class);
    
    private final static String MODEL_SFR_USER_LIGNES = "sfr_user_lignes";
    
    private final static String MODEL_MULTI_LIGNE_MODE_SELECT = "multi_ligne_mode_select";
    
    /**
     * Le cas où le client n'a jamais choisi de ligne
     */
    @RequestMapping(value = "/select-ligne-first", method = {RequestMethod.GET})
    public String selectLigneFirst(ModelMap model) {
	model.put(MODEL_MULTI_LIGNE_MODE_SELECT, true);
	return ViewName.VIEW_SELECT_LIGNE_FIRST;
    }
    

    /**
     * La page pour gérer le multi-lignes
     */
    @RequestMapping(value = "/select-ligne", method = {RequestMethod.GET})
    public String selectLigne(ModelMap model) throws UserProfileFault_Exception {
	model.put(MODEL_SFR_USER_LIGNES, userProfileHelper.getListLabels());
	return ViewName.VIEW_SELECT_LIGNE;
    }
    

    /**
     * L'action pour chosisir une ligne
     */
    @RequestMapping(value = "/ajax/choose-ligne", method = {RequestMethod.POST})
    public @ResponseBody
    boolean chooseLigne(@RequestParam(required = true) String msisdn, ModelMap model, HttpServletResponse response) throws UserProfileFault_Exception {
	
	userProfileHelper.selectionneLigne(response, msisdn);
	
	return true;
    }
}