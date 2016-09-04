package com.sfr.applications.maam.front.controller;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.cxf.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.sfr.applications.maam.front.enumeration.SimulationAmeErrorCode;
import com.sfr.applications.maam.front.exception.UserProfileException;
import com.sfr.applications.maam.front.model.RetourReponse;
import com.sfr.applications.maam.front.utils.Constant;
import com.sfr.applications.maam.front.utils.ViewName;
import com.sfr.applications.maam.model.eliame.ReponseSimulationEliameExtented;
import com.sfr.applications.maam.model.eliame.SimuLigne;
import com.sfr.applications.maam.model.eliame.SimulationInfos;
import com.sfr.applications.maam.model.eliame.SimulationProposition;
import com.sfr.applications.maam.model.restitution.AmeMember;
import com.sfr.applications.maam.model.restitution.AmesForSfrUser;
import com.sfr.applications.maam.model.restitution.SubscribedGroupeAme;
import com.sfr.applications.maam.service.MaamManagementService;
import com.sfr.applications.maam.service.MaamSimulationService;
import com.sfr.composants.eliamegestioname.exception.EliameGestionAMEWSCommunicationException;
import com.sfr.composants.eliamegestioname.exception.EliameGestionAMEWSTechnicalException;
import com.sfr.ws.psw.profile.v4_1.UserProfileService;



/**
 * Controller gérant la modification des AME
 * 
 * @author amv
 */
@Controller
public class ModificationAmeController extends AMaamController {
    
    public static transient final Logger log = LoggerFactory.getLogger(ModificationAmeController.class);
    
    @Resource(name = "maamManagementService")
    private MaamManagementService maamManagementService;
    
	@Resource(name = "maamSimulationService")
	private MaamSimulationService maamSimulationService;
    
    @Resource(name = "${userProfileService}")
    protected UserProfileService userProfileService;
    
    /**
     * Affiche le container des erreurs
     */
    @RequestMapping(value = "/groupe/modification/error", method = RequestMethod.GET)
    public String error(@RequestParam String error, ModelMap model) {
        
        model.put(Constant.ERROR_MODEL, error);
        
        return ViewName.MODIFICATION_GROUPE_ERROR_VIEW;
    }
    
    /**
     * Valide la proposition d'ajout de lignes
     */
    @RequestMapping(value = "/action/groupe/modification/ajout/confirmation", method = {
        RequestMethod.POST
    })
    public @ResponseBody
    RetourReponse executeConfirmationGroupeModificationAdd() throws AmeManagementException {
        
    	SfrLine aSfrLine = findSfrLine(getCurrentLineNumber());
    	AmesForSfrUser ameUser = getAmesForSfrUser(true,aSfrLine);
        if (ameUser == null || ameUser.getSimulationInfos() == null) { // Cache expiré
            log.info("simulationInfos est null : expiration du cache; on redirige vers la page d'acceuil");
            return new RetourReponse(SimulationAmeErrorCode.CACHE_ERROR);
        }
        
        SimulationInfos simulationInfos = ameUser.getSimulationInfos();
        
        if (simulationInfos.isSimulationDone() || simulationInfos.isHasNotExistingLines()) {
            log.info("Simulation déjà effectuée; on redirige vers la page d'acceuil");
            return new RetourReponse(SimulationAmeErrorCode.ALREADY_SIMULATION_DONE);
        }
        
        // Proposition choisie
        SimulationProposition chosenProposition = simulationInfos.getBestPropoition();
        
        String propositionId = chosenProposition==null?null:chosenProposition.getPropositionId();
        if (chosenProposition == null) {
            log.error("Erreur technique dans l'application : Proposition d'id [{}] non trouvée.", propositionId);
            
            throw new AmeManagementException(
                                             "Erreur technique dans l'application : impossible de récupérer la proposition d'id ["
                                                 + propositionId + "].");
        }
        simulationInfos.setChosenProposition(chosenProposition);
        
        log.debug("simulationInfos à utiliser dans confirmationAdd() : {}", simulationInfos);
        
        SubscribedGroupeAme subscribedGroupeAme = ameUser
            .getSubscribedGroupeAme(simulationInfos.getCurrentGroupeCode());
        
        // On va modifier l'AME
        try {
            maamManagementService.modifyAmeForExtranet(aSfrLine.getIdCSU(),subscribedGroupeAme.getShortName(),
                                                       propositionId, null, null, autenticationTools
                                                           .getTypeCanalSuperUser(), autenticationTools
                                                           .getTypeLoginSuperUser(), autenticationTools
                                                           .getLoginSuperUser());
            
            // Echec de l'ajout de la ligne
        } catch (AmeManagementException e) {
            List<SimuLigne> simuLignes = simulationInfos.getSimuLignesAAjouter();
            StringBuffer numerosErreurs = new StringBuffer();
            for (int i = 0; i < simuLignes.size(); i++) {
                numerosErreurs.append(simuLignes.get(i).getNumber());
                if (i < simuLignes.size() - 1) {
                    numerosErreurs.append(",");
                }
            }
            
            if (simuLignes.size() == 1) {
                String numero = simuLignes.get(0).getNumber();
                numerosErreurs.append(numero);
                log.error("L'ajout de la ligne {} au groupe {} a échoué.", numero, subscribedGroupeAme.getFinalName());
            } else {
                log.error("L'ajout des lignes {} au groupe {} a échoué.", numerosErreurs.toString(),
                          subscribedGroupeAme.getFinalName());
            }
            
            log.error("Erreur : ",e);
            RetourReponse retour = new RetourReponse(SimulationAmeErrorCode.GROUPE_MODIFICATION_AJOUT_ERROR);
            retour.setResultObject(numerosErreurs.toString());
            return retour;
        }
        
        return RetourReponse.ok();
    }
    
    /**
     * Valide la proposition de suppression de lignes
     */
    @RequestMapping(value = "/action/groupe/modification/suppression/confirmation", method = { RequestMethod.POST })
    @ResponseBody
    public RetourReponse executeConfirmationRemove()
        throws AmeManagementException {
        
    	SfrLine aSfrLine = findSfrLine(getCurrentLineNumber());
    	AmesForSfrUser ame = getAmesForSfrUser(true, aSfrLine);
        if (ame == null) { // Cache expiré
            log.info("ameUser est null : expiration du cache ou problème restitution; on redirige vers la page d'acceuil");
            return new RetourReponse(SimulationAmeErrorCode.CACHE_ERROR);
        }
        
        SimulationInfos simulationInfos = ame.getSimulationInfos();
        if (simulationInfos == null) { // Cache expiré
            log.info("Problème lors de la récupération des infos de simulations : simulationInfos est null; on redirige vers la page d'acceuil");
            return new RetourReponse(SimulationAmeErrorCode.CACHE_ERROR);
            
            // Impossible de modifier 2 fois !
        }
        // Proposition choisie
        SimulationProposition chosenProposition = simulationInfos.getBestPropoition();
        if (chosenProposition == null) {
            log.error("Erreur technique dans l'application : aucune proposition eliame trouvée.");
            
            return new RetourReponse(SimulationAmeErrorCode.ELIAME_INELIGIBLE);
        }
        
        simulationInfos.setChosenProposition(chosenProposition);
        
        log.debug("simulationInfos à utiliser dans confirmation() : {}", simulationInfos);
        
        SubscribedGroupeAme subscribedGroupeAme = ame.getSubscribedGroupeAme(simulationInfos.getCurrentGroupeCode());
        
        // On va modifier l'ame
        maamManagementService.modifyAmeForExtranet(aSfrLine.getIdCSU(), subscribedGroupeAme.getShortName(), chosenProposition.getPropositionId(),
                              null, null, autenticationTools.getTypeCanalSuperUser(), autenticationTools
                                      .getTypeLoginSuperUser(), autenticationTools.getLoginSuperUser());
        
        return RetourReponse.ok();
    }
    
    /**
     * Effectue la simulation pour une suppression de lignes
     * 
     * @return le résultat de la suppression d'une ligne
     * @throws AmeManagementException
     * @throws EliameGestionAMEWSTechnicalException
     * @throws EliameGestionAMEWSCommunicationException
     */
    @RequestMapping(value = "/action/groupe/modification/suppression/simulation/{number}", method = {RequestMethod.POST})
    @ResponseBody
    public RetourReponse dosimulationModificationDelete(@PathVariable("number") String number, ModelMap model)
        throws EliameSimulationException, AmeManagementException, EliameGestionAMEWSTechnicalException {
        
        SfrLine sfrUser = findSfrLine(getCurrentLineNumber());
        AmesForSfrUser ame = getAmesForSfrUser(true, sfrUser);
        // Cache expire
        if (ame == null || ame.getSimulationInfos() == null) {
            log.info("Expiration du cache; ameUser est null, redirection vers : {}", ViewName.HOME);
            return new RetourReponse(SimulationAmeErrorCode.CACHE_ERROR);
        }
        
        // Infos spécifiques à la simulation
        SimulationInfos simulationInfos = ame.getSimulationInfos();
        Collections.sort(simulationInfos.getSimuLignes());
        
        SubscribedGroupeAme subscribedGroupeAme = ame.getSubscribedGroupeAme(simulationInfos.getCurrentGroupeCode());
        
        log.debug("Début de la suppression du numéro depuis les information de simulation.");
        // On enlève la ligne -->
        simulationInfos.removeSimuLigne(number);
        log.debug("Fin de la suppression du numéro : {}", number);
        log.debug("simulationInfos après suppression du numéro {} : {}", number, simulationInfos);
        
        AmeMember ameMember = null;
        for (AmeMember member : subscribedGroupeAme.getAmeMembers()) {
            if (number.equals(member.getPerson().getLine().getNumber())) {
                ameMember = member;
                break;
            }
        }
        
        ReponseSimulationEliameExtented result = null;
        // si la suppression de ligne n'entraine pas la suppression du groupe
        if (simulationInfos.getSimuLignesAdded().size() >= subscribedGroupeAme.getMinimumNbLine()) {
            log.debug("On va effectuer la simulation après suppression de la ligne {}", number);
            
            try {
                if (sfrUser.getIdPersonne() == null) {
                    throw new UserProfileException("Aucun id Personne de trouvee pour la ligne: " + sfrUser.getNumber()
                          + " alors que ce champs est obligatoire pour une simulation de mise a jour de groupe.");
                }
                result = maamSimulationService.simulationGroupeAmeRetraitMembreForExtranet(autenticationTools.getTypeLoginSuperUser(),
                         autenticationTools.getRoleSuperUser(), autenticationTools.getLoginSuperUser(), sfrUser.getIdPersonne(),
                         simulationInfos.getCurrentGroupeCode(), subscribedGroupeAme.getId(), simulationInfos);
            } catch (EliameGestionAMEWSCommunicationException e) {
                // Gestion du timeout
                log.error("Impossible d'effectuer la suppression! Timeout simulationAME",e);
                return new RetourReponse(SimulationAmeErrorCode.ELIAME_TIMEOUT_ERROR);
            }
        } else {
            log.debug("Pas de simulation à effectuer : le nombre de lignes dans le groupe est inférieur au nombre minimum de lignes requis.", number);
            log.info("La suppression de cette ligne entraine la suppression du groupe {}", subscribedGroupeAme);
        }
        
        return saveSimulationInfo(result, ame, ameMember, simulationInfos);
    }

    /**
     * Save the result into simulationInfos and prepare the response
     * 
     * @param result
     * @param ame
     * @param aDeletedAmeMember
     * @param simulationInfos
     * @return
     * @throws EliameSimulationException
     * @throws AmeManagementException
     */
    private RetourReponse saveSimulationInfo(ReponseSimulationEliameExtented result, AmesForSfrUser ame,
                                                       AmeMember aDeletedAmeMember, SimulationInfos simulationInfos)
        throws EliameSimulationException, AmeManagementException {
        // Aucune proposition
        if (result != null && !(result.getListeProposition() != null && !result.getListeProposition().getPropositions().isEmpty())) {
            
            // On sauvegarde la simulation
            simulationInfos.setSimulationEliame(result);
            simulationInfos.setLastDeletedLine(aDeletedAmeMember);
            ame.setSimulationInfos(simulationInfos);
            
            return RetourReponse.ok();
        } else if (result != null && result.getListeProposition() != null
                   && !StringUtils.isEmpty(result.getListeProposition().getCodeIneligibilite())) {
            // Erreur d'éligilité Eliame
            simulationInfos = initSimulationInfosForModification(simulationInfos.getCurrentGroupeCode(), ame);
            ame.setSimulationInfos(simulationInfos);
            log.info("Le client ayant pour numéro {} n'est pas éligible au retrait de ligne du groupe {}. Code d'inéligibilité : {}",
                     new Object[] {
                         ame.getCurrentLine().getNumber(), simulationInfos.getCurrentGroupeCode(),
                         result.getListeProposition().getCodeIneligibilite()
                     });
            return new RetourReponse(SimulationAmeErrorCode.ELIAME_INELIGIBLE);
            
        } else if (result == null) {
            return RetourReponse.ok();
            
            // Ok !
        } else {
            // On sauvegarde la simulation
            simulationInfos.setSimulationEliame(result);
            ame.setSimulationInfos(simulationInfos);
            simulationInfos.setLastDeletedLine(aDeletedAmeMember);
            return RetourReponse.ok();
        }
    }
    
    
    /**
     * Effectue la simulation pour une suppression de lignes
     * 
     * @return le résultat de la suppression d'une ligne
     * @throws AmeManagementException
     * @throws EliameGestionAMEWSTechnicalException
     * @throws EliameGestionAMEWSCommunicationException
     */
    @RequestMapping(value = "/action/groupe/verification/suppression/simulation/{number}", method = {RequestMethod.POST})
    @ResponseBody
    public RetourReponse checkSimulationDelationResult(@PathVariable("number") String number, ModelMap model)
        throws EliameSimulationException, AmeManagementException, EliameGestionAMEWSTechnicalException {
        
        SfrLine sfrUser = findSfrLine(getCurrentLineNumber());
        AmesForSfrUser ame = getAmesForSfrUser(true, sfrUser);
        // Cache expire
        if (ame == null || ame.getSimulationInfos() == null) {
            log.info("Expiration du cache; ameUser est null, redirection vers : {}", ViewName.HOME);
            return new RetourReponse(SimulationAmeErrorCode.CACHE_ERROR);
        }
        
        // Infos spécifiques à la simulation
        SimulationInfos simulationInfos = ame.getSimulationInfos();
        
        SubscribedGroupeAme subscribedGroupeAme = ame.getSubscribedGroupeAme(simulationInfos.getCurrentGroupeCode());
        
        ReponseSimulationEliameExtented result = null;
        // si la suppression de ligne n'entraine pas la suppression du groupe
            log.debug("On va effectuer une simulation avec la ligne {}", number);
         
            
            // On enlève la ligne de simuligne
            simulationInfos.removeTemporarily(number);
            
            try {
                if (sfrUser.getIdPersonne() == null) {
                    throw new UserProfileException("Aucun id Personne de trouvee pour la ligne: " + sfrUser.getNumber()
                          + " alors que ce champs est obligatoire pour une simulation de mise a jour de groupe.");
                }
                result = maamSimulationService.simulationGroupeAmeRetraitMembreForExtranet(autenticationTools.getTypeLoginSuperUser(),
                         autenticationTools.getRoleSuperUser(), autenticationTools.getLoginSuperUser(), sfrUser.getIdPersonne(),
                         simulationInfos.getCurrentGroupeCode(), subscribedGroupeAme.getId(), simulationInfos);
              //On remet la ligne dans simulationInfos car l'on n'est pas sûr que la ligne sera effectivement supprimée
                simulationInfos.resetTemporarilyRemoved(number);
                
            } catch (Exception e) {
                // Gestion du timeout
                log.error("Impossible d'effectuer la suppression en raison d'un problème de communication avec le serveur! ",e);
                return new RetourReponse(SimulationAmeErrorCode.ELIAME_TECHNICAL_ERROR);
            }
            
            if (result != null && result.getListeProposition() != null && result.getListeProposition().getPropositions().isEmpty()) {
                
                // On sauvegarde la simulation
            	return new RetourReponse(SimulationAmeErrorCode.ELIAME_INELIGIBLE);
                
            }
            
            return RetourReponse.ok();
        } 
}
