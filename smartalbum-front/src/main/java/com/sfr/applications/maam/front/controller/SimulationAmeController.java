package com.sfr.applications.maam.front.controller;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycompany.service.exception.AmeManagementException;
import com.mycompany.service.exception.EliameSimulationException;
import com.mycompany.services.model.commun.SfrLine;
import com.sfr.applications.maam.front.enumeration.SimulationAmeErrorCode;
import com.sfr.applications.maam.front.exception.UserProfileException;
import com.sfr.applications.maam.front.model.RetourReponse;
import com.sfr.applications.maam.front.utils.Constant;
import com.sfr.applications.maam.front.utils.ViewName;
import com.sfr.applications.maam.model.eliame.ReponseSimulationEliameExtented;
import com.sfr.applications.maam.model.eliame.SimulationInfos;
import com.sfr.applications.maam.model.eliame.SimulationProposition;
import com.sfr.applications.maam.model.restitution.AmesForSfrUser;
import com.sfr.applications.maam.model.restitution.SubscribedGroupeAme;
import com.sfr.applications.maam.service.MaamSimulationService;
import com.sfr.composants.eliamegestioname.exception.EliameGestionAMEWSCommunicationException;
import com.sfr.composants.eliamegestioname.exception.EliameGestionAMEWSTechnicalException;
import com.sfr.eliame.TypeActeDeGestion;
import com.sfr.ws.psw.profile.v4_1.UserProfileService;

/**
 * Controller gérant la simulation des AME
 * 
 * @author amv
 */
@Controller
public class SimulationAmeController extends AMaamController {
    
    private static final transient Logger log = LoggerFactory.getLogger(SimulationAmeController.class);
    
    @Resource(name = "maamSimulationService")
    private MaamSimulationService maamSimulationService;
    
    @Resource(name = "${userProfileService}")
    protected UserProfileService userProfileService;
    
    /**
     * Effectue la simulation pour une souscription de groupe
     * 
     * @param code Le code de l'AME sur lequel on effectue la simulation
     * @return Le statut de l'opération de création : ok tout s'est bien passé
     * @throws AmeManagementException Exception levé en cas d'erreur de simulation
     */
    @RequestMapping(value = "/action/groupe/souscription/simulation", method = {RequestMethod.GET})
    public @ResponseBody
    RetourReponse doSimulerCreate() throws EliameSimulationException, AmeManagementException {
        
        log.debug("Debut de SIMULATION de la creation du groupe.");
        
        SfrLine sfrUser = findSfrLine(getCurrentLineNumber());
        AmesForSfrUser ameUser = getAmesForSfrUser(true, sfrUser);
        
        String code = ameUser.getGroupeAmes().get(0).getCode();
        
        // Cache expiré
        if (ameUser == null || ameUser.getSimulationInfos() == null) {
            log.info("Expiration du cache; simulationInfos est null, redirection vers : {}", ViewName.HOME);
            return new RetourReponse(SimulationAmeErrorCode.CACHE_ERROR);
        }
        
        
        SimulationInfos simulationInfos = ameUser.getSimulationInfos();
      //gere le back navigateur
        if(simulationInfos.isMustReloadPage()){
        	initSimulationInfosForCreation(sfrUser, code, ameUser);
        }
        simulationInfos.setMustReloadPage(true);
        log.debug("simulationInfos dans simulerCreate() : {}", simulationInfos);
        
        // Pas assez de lignes dans le groupe
        if (!simulationInfos.isHadEnoughNewLigne()) {
            log.info("Pas assez de lignes dans simulationInfos, redirection vers : {}", ViewName.HOME);
            return new RetourReponse(SimulationAmeErrorCode.NOT_ENOUGH_MEMBER);
        }
        
        ReponseSimulationEliameExtented result = null;
        try {
            
            if (sfrUser.getIdPersonne() == null) {
                throw new UserProfileException(
                                               "Aucun id Personne de trouvee pour la ligne: "
                                                   + sfrUser.getNumber()
                                                   + " alors que ce champs est obligatoire pour une simulation de creation de groupe.");
            }
            
            result = maamSimulationService
                .simulationGroupeAmeCreationForExtranet(autenticationTools.getTypeLoginSuperUser(),
                                                        autenticationTools.getRoleSuperUser(),
                                                        autenticationTools.getLoginSuperUser(),
                                                        sfrUser.getIdPersonne(),
                                                        simulationInfos.getCurrentGroupeCode(), simulationInfos);
            
            
        } catch (EliameSimulationException e) {
            log.error("La souscription au groupe [{}] pour le client [{}] connecté a échoué", ameUser
                .getAvailableGroupeAme(code).getName(), simulationInfos.getConnectedLine().getNumber());
            log.error("Erreur : ",e);
            return new RetourReponse(SimulationAmeErrorCode.GROUPE_SOUSCRIPTION_ERROR);
        } catch (EliameGestionAMEWSTechnicalException e) {
            log.error("La souscription au groupe [{}] pour le client [{}] connecté a échoué", ameUser
                .getAvailableGroupeAme(code).getName(), simulationInfos.getConnectedLine().getNumber());
            log.error("Erreur : ",e);
            return new RetourReponse(SimulationAmeErrorCode.ELIAME_TECHNICAL_ERROR);
        } catch (EliameGestionAMEWSCommunicationException e) {
            // Gestion du timeout
            log.error("La souscription au groupe [{}] pour le client [{}] connecté a échoué, Timeout simulationAME",
                      ameUser.getAvailableGroupeAme(code).getName(), simulationInfos.getConnectedLine().getNumber());
            log.error("Erreur : ",e);
            return new RetourReponse(SimulationAmeErrorCode.ELIAME_TIMEOUT_ERROR);
        }
        // Aucune proposition
        if (result == null || result.getListeProposition() == null
            || result.getListeProposition().getPropositions().isEmpty()) {
            log.debug("erreur simulation eliame - no result");
            
            // On sauvegarde la simulation
            simulationInfos.setSimulationEliame(result);
            ameUser.setSimulationInfos(simulationInfos);
            
            log.error("Impossible de créer le groupe, problème d'éligibilité");
            return new RetourReponse(SimulationAmeErrorCode.ELIAME_INELIGIBLE);
            
            // Erreur d'éligilité Eliame
        } else if (result != null && result.getListeProposition() != null
                   && !StringUtils.isEmpty(result.getListeProposition().getCodeIneligibilite())) {
            log.debug("erreur simulation eliame - ineligible");
            
            return new RetourReponse(SimulationAmeErrorCode.ELIAME_INELIGIBLE);
            
            // Ok !
        } else {
            log.debug("simulation ok, MAJ de simulationInfos, ReponseSimulationEliameExtented : {}", result);
            
            // On sauvegarde la simulation
            simulationInfos.setSimulationEliame(result);
            ameUser.setSimulationInfos(simulationInfos);
            
            log.debug("retour de la vue GROUPE_SIMULATION_VIEW : {}", ViewName.GROUPE_SIMULATION_VIEW);
            resetCache(true);
            return RetourReponse.ok();
        }
    }
   
    
    /**
     * Effectue la simulation pour un ajout de ligne lors de la modification d'un groupe
     * 
     * @param code
     * @param number
     * @param newLineId
     * @param model
     * @return
     * @throws EliameSimulationException
     * @throws AmeManagementException
     */
    @RequestMapping(value = "/action/groupe/modification/simulation", method = {
        RequestMethod.GET
    })
    public @ResponseBody
    RetourReponse doSimulationModificationAdd(ModelMap model)
        throws EliameSimulationException, AmeManagementException {
        
    	SfrLine sfrLine = findSfrLine(getCurrentLineNumber());
    	AmesForSfrUser ameUser = getAmesForSfrUser(true,sfrLine);
        
        // Cache expiré
        if (ameUser == null) {
            log.info("Expiration du cache; ameUser est null, redirection vers : {}", ViewName.HOME);
            return new RetourReponse(SimulationAmeErrorCode.CACHE_ERROR);
        }
        
        String code = ameUser.getGroupeAmes().get(0).getCode();
        
        //gere le back navigateur
        SimulationInfos simulationInfos = ameUser.getSimulationInfos();
        
        if(simulationInfos.isMustReloadPage()){
        	initSimulationInfosForModification(code, ameUser);
        }
        simulationInfos.setMustReloadPage(true);
        
        SubscribedGroupeAme subscribedGroupeAme = ameUser
            .getSubscribedGroupeAme(simulationInfos.getCurrentGroupeCode());
        
        ReponseSimulationEliameExtented result = null;
        try {
        	
            if (sfrLine.getIdPersonne() == null) {
                throw new UserProfileException(
                                               "Aucun id Personne de trouvee pour la ligne: "
                                                   + sfrLine.getNumber()
                                                   + " alors que ce champs est obligatoire pour une simulation de suppression de groupe.");
            }
            
            result = maamSimulationService.simulationGroupeAmeAjoutMembreForExtranet(autenticationTools.getTypeLoginSuperUser(),
                                                           autenticationTools.getRoleSuperUser(),
                                                           autenticationTools.getLoginSuperUser(), sfrLine.getIdPersonne(),
                                                           simulationInfos.getCurrentGroupeCode(),
                                                           subscribedGroupeAme.getId(), simulationInfos);
            
            // On reinitialise la simulation eliame
            simulationInfos.setSimulationEliame(null);
            
        } catch (EliameSimulationException e) {
            log.error("L'ajout de la ligne {} au groupe {} a échoué.", simulationInfos.getSimuLignesAAjouter().toString(), subscribedGroupeAme.getFinalName());
            RetourReponse retour = new RetourReponse(SimulationAmeErrorCode.GROUPE_MODIFICATION_AJOUT_ERROR);
            retour.setResultObject(simulationInfos.getSimuLignesAAjouter().toString());
            log.error("Erreur : ",e);
            return retour;
        } catch (EliameGestionAMEWSTechnicalException e) {
            
            log.error("L'ajout de la ligne {} au groupe {} a échoué.", simulationInfos.getSimuLignesAAjouter().toString(), subscribedGroupeAme.getFinalName());
            RetourReponse retour = new RetourReponse(SimulationAmeErrorCode.ELIAME_TECHNICAL_ERROR);
            retour.setResultObject(simulationInfos.getSimuLignesAAjouter().toString());
            log.error("Erreur : ",e);
            return retour;
            
        } catch (EliameGestionAMEWSCommunicationException e) {
            // Gestion du timeout
            log.error("Impossible d'effectuer l'ajout de ligne! Timeout simulationAME");
            log.error("Erreur : ",e);
            return new RetourReponse(SimulationAmeErrorCode.ELIAME_TIMEOUT_ERROR);
        }
        
     // Aucune proposition
        if (result == null || result.getListeProposition() == null
            || result.getListeProposition().getPropositions().isEmpty()) {
        	
            log.debug("erreur simulation eliame - no result");
            
            // On sauvegarde la simulation
            simulationInfos.setSimulationEliame(result);
            ameUser.setSimulationInfos(simulationInfos);
            
            log.error("Impossible de créer le groupe, problème d'éligibilité");
            return new RetourReponse(SimulationAmeErrorCode.ELIAME_INELIGIBLE);
            
            // Erreur d'éligilité Eliame
        } else if (result != null && result.getListeProposition() != null
                   && !StringUtils.isEmpty(result.getListeProposition().getCodeIneligibilite())) {
            log.debug("erreur simulation eliame - ineligible");
            initSimulationInfosForModification(simulationInfos.getCurrentGroupeCode(), ameUser);
            
            return new RetourReponse(SimulationAmeErrorCode.ELIAME_INELIGIBLE);
            
            // Ok !
        } else {
            log.debug("simulation ok, MAJ de simulationInfos, ReponseSimulationEliameExtented : {}", result);
            
            // On sauvegarde la simulation
            simulationInfos.setSimulationEliame(result);
            ameUser.setSimulationInfos(simulationInfos);
            
            log.debug("retour de la vue GROUPE_SIMULATION_VIEW : {}", ViewName.GROUPE_SIMULATION_VIEW);
            resetCache(true);
            return RetourReponse.ok();
        }
    }
    
    /**
     * Effectue l'affichage de de la page de simulation
     * 
     * @throws AmeManagementException
     */
    @RequestMapping(value = "/groupe/simulation", method = {RequestMethod.GET})
    public String showSimulationGroupe(ModelMap model) throws EliameSimulationException, AmeManagementException {
        
        AmesForSfrUser ameUser = getAmesForSfrUser(true, findSfrLine(getCurrentLineNumber()));
        SimulationInfos simulationInfos = ameUser.getSimulationInfos();
        
        if (ameUser == null || simulationInfos == null) {
            // Cache expiré
            log.info("Expiration du cache; simulationInfos est null, redirection vers : {}", ViewName.HOME);
            return Constant.REDIRECT + ViewName.HOME;
        }
        
        SimulationProposition bestProposition = ameUser.getSimulationInfos().getBestPropoition();
        simulationInfos.updateLignesHorsProposition(bestProposition);
        
        // Dans le cas d'une création on met à jour le type de groupe
        if(bestProposition!=null && bestProposition.getGroupeSimulations()!=null && bestProposition.getGroupeSimulations().size()>0)
        {
        	ameUser.getSimulationInfos().setAddams(bestProposition.getGroupeSimulations().get(0).isAddams());
        }
        
        model.put(Constant.GROUPE_AME_MODEL,(simulationInfos.getSimulationEliame().getTypeActe().equals(TypeActeDeGestion.AMS)) ? ameUser
		                      .getAvailableGroupeAme(simulationInfos.getCurrentGroupeCode()) : ameUser
		                      .getSubscribedGroupeAme(simulationInfos.getCurrentGroupeCode()));
        
        model.put(Constant.AME_MODEL, ameUser);
        return ViewName.GROUPE_SIMULATION_VIEW;
    }
    
    @RequestMapping(value = "/groupe/confirmation", method = RequestMethod.GET)
	public String affichagePageConfirmation(ModelMap model)
			throws AmeManagementException {
		AmesForSfrUser ameUser = getAmesForSfrUser(true,
				findSfrLine(getCurrentLineNumber()));
		if (ameUser.getSubscribedGroupeAmes().size() > 0) {
			SubscribedGroupeAme subscribedGroupeAme = ameUser
					.getSubscribedGroupeAmes().get(0);
			model.put(Constant.AME_GROUP, subscribedGroupeAme);
		}

		// Cache expiré
		if (ameUser == null || ameUser.getSimulationInfos() == null || ameUser.getSimulationInfos().isSimulationDone()) {
			// On vide le cache
			resetCache(ameUser);
			log.info("Expiration du cache; simulationInfos est null, redirection vers : {}",ViewName.HOME);
			return Constant.REDIRECT + ViewName.HOME;
		}

		// On vide le cache
		model.addAttribute("ame_configuration", ameUser);

		ameUser.getSimulationInfos().setSimulationDone(true);
		return ViewName.CONFIRMATION_GROUPE_VIEW;
	}

}


