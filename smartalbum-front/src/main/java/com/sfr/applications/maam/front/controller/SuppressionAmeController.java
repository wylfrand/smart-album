package com.sfr.applications.maam.front.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mycompany.service.exception.AmeManagementException;
import com.mycompany.services.model.commun.SfrLine;
import com.sfr.applications.maam.front.utils.Constant;
import com.sfr.applications.maam.front.utils.ViewName;
import com.sfr.applications.maam.model.restitution.AmesForSfrUser;
import com.sfr.applications.maam.model.restitution.SubscribedGroupeAme;
import com.sfr.applications.maam.service.MaamManagementService;

/**
 * Controller gérant la suppression des AME
 * 
 * @author amv
 */
@Controller
public class SuppressionAmeController extends AMaamController {
    
    private static final transient Logger log = LoggerFactory.getLogger(SuppressionAmeController.class);
    
    @Resource(name = "maamManagementService")
    private MaamManagementService maamManagementService;
    
    /**
     * Action pour supprimer un groupe AME
     * 
     * @throws AmeManagementException
     */
    @RequestMapping(value = "/action/groupe/suppression/confirmation", method = {RequestMethod.GET})
    public String deleteGroupeAction() throws AmeManagementException {
	
    SfrLine sfrUser = findSfrLine(getCurrentLineNumber());
    AmesForSfrUser ameUser = getAmesForSfrUser(true,sfrUser);

    if (ameUser == null || ameUser.getSubscribedGroupeAmes().size()==0) {
	    log.info("Expiration du cache, redirection vers : {}", ViewName.HOME);
	    return Constant.REDIRECT + ViewName.ERROR_SERVICE_INDISPONIBLE;
	}
    
    // A ce niveau il y a forcément au moins un groupe dans la liste des groupes souscrit, on prend le premier groupe!
    SubscribedGroupeAme subscribedGroupeAme = ameUser.getSubscribedGroupeAmes().get(0);
    
    log.debug("Le client [{}] ayant pour numéro {} tente de supprimer le groupe {}", new Object[] {sfrUser.getIdPersonne(),
	    sfrUser.getNumber(), subscribedGroupeAme});
    try {
    		maamManagementService.deleteAmeForExtranet(sfrUser.getIdPersonne(), subscribedGroupeAme.getId(), 
    				null, autenticationTools.getTypeLoginSuperUser(), autenticationTools.getLoginSuperUser());
	
    } catch (AmeManagementException e) {
    	log.warn("Erreur lors de la suppression du groupe : ", e);
    	 return Constant.REDIRECT + ViewName.ERROR_SERVICE_INDISPONIBLE;
		
    }
	
    return Constant.REDIRECT + ViewName.GROUPE_CONFIRMATION_REDIRECT;
    }
    
}