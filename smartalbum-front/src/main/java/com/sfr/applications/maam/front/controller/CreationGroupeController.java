package com.sfr.applications.maam.front.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycompany.service.exception.AmeManagementException;
import com.mycompany.services.model.commun.SfrLine;
import com.sfr.applications.maam.front.enumeration.SimulationAmeErrorCode;
import com.sfr.applications.maam.front.model.RetourReponse;
import com.sfr.applications.maam.model.eliame.SimulationInfos;
import com.sfr.applications.maam.model.eliame.SimulationProposition;
import com.sfr.applications.maam.model.restitution.AmesForSfrUser;
import com.sfr.applications.maam.model.restitution.AvailableGroupeAme;
import com.sfr.applications.maam.service.MaamManagementService;
import com.sfr.ws.psw.profile.v4_1.UserProfileService;

/**
 * Le controlleur de la creation de groupe
 * 
 * @author amv
 */
@Controller
public class CreationGroupeController extends AMaamController {

        public static transient final Logger log = LoggerFactory
                        .getLogger(CreationGroupeController.class);

        @Resource(name = "${userProfileService}")
        protected UserProfileService userProfileService;
        
        @Resource(name = "maamManagementService")
        private MaamManagementService maamManagementService;
        
        
        
        /**
         * Valide la proposition
         */
        @RequestMapping(value = "/action/groupe/souscription/confirmation", method = {
            RequestMethod.POST
        })
        public @ResponseBody
        RetourReponse executeGroupeSouscription()
            throws AmeManagementException {
            
            log.debug("Debut de CONFIRMATION de la creation du groupe.");
            
            SfrLine sfrCurrentLine = findSfrLine(getCurrentLineNumber());
            AmesForSfrUser ameUser = getAmesForSfrUser(true, sfrCurrentLine);
            
            if (ameUser == null) { // Cache expiré
                log.info("ameUser est null : expiration du cache; on redirige vers la page d'acceuil");
                return new RetourReponse(SimulationAmeErrorCode.CACHE_ERROR);
            }
            
            SimulationInfos simulationInfos = ameUser.getSimulationInfos();
            
            if (simulationInfos == null) { // Cache expiré
                log.info("Expiration du cache : simulationInfos est null; on redirige vers la page d'acceuil");
                return new RetourReponse(SimulationAmeErrorCode.CACHE_ERROR);
            }
            
            String[] idCsu = null;
            String[] evidenceTypeCodes = null;
            String propositionId = ameUser.getSimulationInfos().getBestPropoition().getPropositionId();
            
            // Proposition choisie
            SimulationProposition chosenProposition = simulationInfos.getProposition(propositionId);
            if (chosenProposition == null) {
                log.error("Erreur technique dans l'application : Proposition d'id [{}] non trouvée.", propositionId);
                
                throw new AmeManagementException(
                                                 "Erreur technique dans l'application : impossible de récupérer la proposition d'id ["
                                                     + propositionId + "].");
            }
            simulationInfos.setChosenProposition(chosenProposition);
            
            log.debug("simulationInfos à utiliser dans confirmation() : {}", simulationInfos);
            
            AvailableGroupeAme availableGroupeAme = ameUser.getAvailableGroupeAme(simulationInfos.getCurrentGroupeCode());
            
            // On va souscrire l'ame
            log.debug("appel du webService managementAME pour souscrire le groupe");
            
            try {
                maamManagementService.subscribeAmeForExtranet(sfrCurrentLine.getIdPersonne(),
                		availableGroupeAme.getName(), propositionId, idCsu,
                                                              evidenceTypeCodes,
                                                              autenticationTools.getTypeCanalSuperUser(),
                                                              autenticationTools.getTypeLoginSuperUser(),
                                                              autenticationTools.getLoginSuperUser());
                
                // Echec de la souscription de groupe
            } catch (AmeManagementException e) {
                log.error("La souscription au groupe [{}] pour le client [{}] connecté a échoué",
                          availableGroupeAme.getName(), simulationInfos.getConnectedLine().getNumber());
                
                return new RetourReponse(SimulationAmeErrorCode.GROUPE_SOUSCRIPTION_ERROR);
            }
            
            return RetourReponse.ok();
        }
       
}
