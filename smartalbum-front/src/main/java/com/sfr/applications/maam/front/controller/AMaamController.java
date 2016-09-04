package com.sfr.applications.maam.front.controller;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;

import com.google.common.collect.Lists;
import com.mycompany.service.exception.AmeManagementException;
import com.mycompany.services.model.commun.RecursiveToStringStyle;
import com.mycompany.services.model.commun.SfrLine;
import com.mycompany.services.model.commun.enumeration.NatureLine;
import com.mycompany.services.model.commun.enumeration.TypeProfilSfr;
import com.sfr.applications.maam.database.model.Profil;
import com.sfr.applications.maam.database.model.Transcodification;
import com.sfr.applications.maam.database.model.enumeration.ProfilLineTypeSfr;
import com.sfr.applications.maam.database.model.enumeration.TranscodificationType;
import com.sfr.applications.maam.database.service.ProfilDatabaseService;
import com.sfr.applications.maam.database.service.TranscodificationDatabaseService;
import com.sfr.applications.maam.front.exception.NotEnoughRoleException;
import com.sfr.applications.maam.front.exception.UserProfileException;
import com.sfr.applications.maam.front.service.UserProfileHelper;
import com.sfr.applications.maam.front.utils.AutenticationTools;
import com.sfr.applications.maam.front.utils.Constant;
import com.sfr.applications.maam.front.utils.HttpSessionCacheManager;
import com.sfr.applications.maam.model.eliame.SimuLigne;
import com.sfr.applications.maam.model.eliame.SimulationInfos;
import com.sfr.applications.maam.model.restitution.AmeMember;
import com.sfr.applications.maam.model.restitution.AmePerson;
import com.sfr.applications.maam.model.restitution.AmesForSfrUser;
import com.sfr.applications.maam.model.restitution.AvailableGroupeAme;
import com.sfr.applications.maam.model.restitution.SubscribedGroupeAme;
import com.sfr.applications.maam.service.ClarifyService;
import com.sfr.applications.maam.service.ListeDonneesService;
import com.sfr.applications.maam.service.MaamRestitutionService;
import com.sfr.springframework.security.helper.AuthenticationHelper;
import com.sfr.ws.psw.profile.v4_1.UserProfileFault_Exception;
import com.sfr.xml.psw.profile.v4_1.FicheUtilisateur;
import com.sfr.xml.psw.profile.v4_1.GammeLigneFixe;
import com.sfr.xml.psw.profile.v4_1.Ligne;
import com.sfr.xml.psw.profile.v4_1.LigneFixe;
import com.sfr.xml.psw.profile.v4_1.LigneMobile;
import com.sfr.xml.psw.profile.v4_1.Role;
import com.sfr.xml.psw.profile.v4_1.SegmentContratFixe;

/**
 * La base de tous les controllers liés au projet maam
 * 
 * @author amv
 */
public abstract class AMaamController {
    
    private static final transient Logger log = LoggerFactory.getLogger(AMaamController.class);
    
    @Resource(name = "clarifyService")
    protected ClarifyService clarifyService;
    
    @Resource(name = "listerDonneesService")
    protected ListeDonneesService listeDonneesService;
    
    @Resource(name = "maamRestitutionService")
    protected MaamRestitutionService maamRestitutionService;
    
    @Resource(name = "profilDatabaseService")
    protected ProfilDatabaseService profilDatabaseService;
    
    @Resource(name = "transcodificationDatabaseService")
    protected TranscodificationDatabaseService transcodificationDatabaseService;
    
    @Resource
    protected HttpSessionCacheManager httpSessionCacheManager;
    
    @Resource
    protected AutenticationTools autenticationTools;
    
    @Resource(name = "userProfileHelper")
    protected UserProfileHelper userProfileHelper;
    
	@Resource(name = "httpSessionCacheManager")
	protected HttpSessionCacheManager cacheManager;
    
    @Value("${maam.roles.restitution}")
    private String restitutionRolesList;
    
    @Value("${maam.roles.management}")
    private String managementOptionsRolesList;
    
    /**
     * Indique si l'utilisateur en cours de connexion est identifié en tant que vision personne
     */
    protected boolean isVisionPersonne(final FicheUtilisateur fiche) {
        try {
            return userProfileHelper.isVisionPersonne(fiche);
        } catch (UserProfileFault_Exception e) {
            throw new UserProfileException();
        }
    }
    
    /**
     * Génére un tableau de role à partir d'une liste de valeurs
     */
    private List<Role> generateTableOfRole(String value) {
        String[] tblValue = value.split(Constant.COMA);
        List<Role> result = Lists.newArrayList();
        
        if (tblValue != null) {
            for (String aValue : tblValue) {
                try {
                    result.add(Role.valueOf(aValue));
                } catch (Exception e) {
                    // Mauvais rôle
                        log.debug("Role inconnue : {}",aValue);
                }
            }
        }
        
        return result;
    }
    
    /**
     * La liste des droits nécéssaire pour accéder à la page de restitution des groupes
     */
    protected List<Role> getRolesForRestitution() {
        // Il faut être titulaire ou utilisateur principal/délégué N0 ou N1
        return generateTableOfRole(restitutionRolesList);
    }
    
    /**
     * Vérifie si l'utilisatuer connecté à accès à la page de restitution des groupes
     */
    protected void testAccesForRestitution(boolean isVisionPersonne,String numero) throws NotEnoughRoleException {
        // L'utilisateur SFR n'est pas en vision personne ==> Pas besoin de faire ces tests
        if (!isVisionPersonne) {
            log.info("L'utilisateur [{}] n'a pas un acces Vision Personne, il n'y a pas de test d'acces vis a vis de son role.",
                        AuthenticationHelper.getLogin());
            return;
        }
        
        if (Collections.disjoint(getCurrentRoles(), getRolesForRestitution())) {
            throw new NotEnoughRoleException();
        } else {
            log.debug("L'utilisateur SFR {} a le droit d'acceder à la restitution des groupes.", AuthenticationHelper.getLogin());
        }
    }
    
    /**
     * La liste des droits nécéssaire pour accéder aux parcours de modification des AMES
     */
    protected List<Role> getRolesForManagement() {
        // Il faut être titulaire ou utilisateur principal/délégué N0 ou N1
        return generateTableOfRole(managementOptionsRolesList);
    }
    
   
    /**
     * Indique si l'utilisateur connecté est titulaire de la ligne
     */
    protected boolean isTituOfCurrentLine() {
        List<Role> listOfRoles = getCurrentRoles();
        
        return (listOfRoles.contains(Role.UTILISATEUR_PRINCIPAL_COMPLET)
                || listOfRoles.contains(Role.UTILISATEUR_DELEGUE_COMPLET) || listOfRoles.contains(Role.TITULAIRE));
    }
    
    /**
     * Indique si l'utilisateur connecté est titulaire de la ligne
     */
    protected List<SfrLine> getListOfTituLines() {
        List<SfrLine> listOfTituLines = Lists.newArrayList();
        
        Object obj = httpSessionCacheManager.getObjectFromCache(Constant.CURRENT_SFR_FICHE);
        FicheUtilisateur fu = null;
        if (obj != null) {
            fu = (FicheUtilisateur) obj;
        }
        
        if(fu==null)
        {
        	return listOfTituLines;
        }
        
        // La liste des lignes ratachées au compte connecté
        List<Ligne> listOfLine = userProfileHelper.getListOfLignes();
        
       
        
        for (Ligne ligne : listOfLine) {
            
            // Si cette ligne est UPP pour l'utilisateur connecté
            if (!isVisionPersonne(fu) || ligne.getRoles().contains(Role.UTILISATEUR_PRINCIPAL_COMPLET)
                || ligne.getRoles().contains(Role.UTILISATEUR_DELEGUE_COMPLET)
                || ligne.getRoles().contains(Role.TITULAIRE)) {
                SfrLine sfrLine = findSfrLine(ligne.getLabel().substring(0, 10));
                
                // On ajoute cette ligne à la liste des lignes titu de l'utilisateur connecté
                if (sfrLine != null)
                    listOfTituLines.add(sfrLine);
            }
        }
        
        return listOfTituLines;
    }
    
    /**
     * Renvoie les lignes dont l'utilisateur que l'on souhaite rajouté est titulaire
     */
    protected List<SfrLine> getListOfTituLines(String login) {
        List<SfrLine> listOfTituLines = Lists.newArrayList();
        
        // La liste des lignes ratachées au compte connecté
        List<Ligne> listOfLine = userProfileHelper.getListOfLignes();
        
        for (Ligne ligne : listOfLine) {
            
            // Si cette ligne est UPP pour l'utilisateur connecté
            if (ligne.getRoles().contains(Role.UTILISATEUR_PRINCIPAL_COMPLET)
                || ligne.getRoles().contains(Role.UTILISATEUR_DELEGUE_COMPLET)
                || ligne.getRoles().contains(Role.TITULAIRE)) {
                SfrLine sfrLine = findSfrLine(ligne.getLabel().substring(0, 10));
                
                // On ajoute cette ligne à la liste des lignes titu de l'utilisateur que l'on souhaite ajouté
                if (sfrLine != null)
                    listOfTituLines.add(sfrLine);
            }
        }
        return listOfTituLines;
    }
    
    /**
     * Retourne les rôle de la personne actuellement connecté
     */
    protected List<Role> getCurrentRoles() {
        return getCurrentLigneByLabel().getRoles();
    }
    
    /**
     * @return la ligne courrante par son label
     */
    protected Ligne getCurrentLigneByLabel() {
        Ligne ligne = null;
        try {
            Object result =  httpSessionCacheManager.getObjectFromCache(Constant.CURRENT_SFR_LINE_KEY);
            if (result == null) {
                String label = (String) httpSessionCacheManager.getObjectFromCache(Constant.CURRENT_SFR_LABEL);
                ligne = userProfileHelper.getLigneByLabel(label);
                httpSessionCacheManager.putObjectInCache(Constant.CURRENT_SFR_LINE_KEY, ligne);
            }
            else
            {
            	ligne=(Ligne)result;
            }
        } catch (Exception e) {
            throw new UserProfileException();
        }
        return ligne;
    }
    
    /**
     * @return le numéro de téléphone de l'utilisateur connecté
     */
    protected String getCurrentLineNumber() {
        String number = null;
        try {
                number = ((String) httpSessionCacheManager.getObjectFromCache(Constant.CURRENT_SFR_LABEL));
                if(StringUtils.isNotBlank(number) && number.length()>=10)
                {
                        number=number.substring(0,10);
                        log.debug("Le numéro correspondant à la ligne connectée est {}",number);
                }
                else
                {
                        log.debug("Numéro de la ligne connectée vide! Merci de contacter votre administrateur.",number);
                }
        } catch (Exception e) {
            throw new UserProfileException(e);
        }
        return number;
    }
    
    protected String getLibellePTA(Ligne ligne) {
        if (ligne instanceof LigneMobile) {
            return ((LigneMobile) ligne).getLibelleForfait();
        } else if (ligne instanceof LigneFixe) {
            return ((LigneFixe) ligne).getLibelleOffreSouscrite();
        }
        return "";
    }
    
    protected String getTypeGamme(Ligne ligne) {
        if (ligne instanceof LigneMobile) {
            return ((LigneMobile) ligne).getGammeTarifaire();
        } else if (ligne instanceof LigneFixe) {
            return ((LigneFixe) ligne).getGamme().name();
        }
        return "";
    }
    
    /**
     * Récupère l'id CSU de la ligne, quelque soit la nature de la ligne et du client
     */
    protected String getFinalIdCsu(Ligne ligne) {
        String codeSegment = StringUtils.EMPTY;
        String numeroCSU = StringUtils.EMPTY;
        
        // L'id CSU d'une ligne mobile est le champ numéro contrat
        if (ligne instanceof LigneMobile) {
            codeSegment = ((LigneMobile) ligne).getSegment();
            numeroCSU = ((LigneMobile) ligne).getNumeroContrat();
            log.debug("Le numéro CSU (ou numéro contrat) de la ligne mobile [{}] est: [{}].", ligne.getLabel(),
                      numeroCSU);
        } else if (ligne instanceof LigneFixe) {
            codeSegment = (((LigneFixe) ligne).getSegment() == null) ? StringUtils.EMPTY : ((LigneFixe) ligne)
                .getSegment().toString();
            numeroCSU = ((LigneFixe) ligne).getNumeroCSU();
            log.debug("Le numéro CSU (ou numéro contrat) de la ligne fixe [{}] est: [{}].", ligne.getLabel(), numeroCSU);
        }
        
        if (codeSegment == null || numeroCSU == null) {
            throw new UserProfileException("Aucun id CSU de trouve pour la ligne: " + ligne.getLabel());
        }
        
        log.debug("On est en vision ligne, l'idCSU de la ligne [{}] récupérée est: [{}].", ligne.getLabel(),
                  codeSegment + "-" + numeroCSU);
        
        return codeSegment + "-" + numeroCSU;
    }
    
    /**
     * Récupère une ligne SFR
     */
    protected SfrLine findSfrLine(String newLigne) {
        SfrLine sfrLine = null;
        
        // Lorsqu'on doit récupérer la fiche client associée à une ligne fixe alors on doit appeler directement CLY (et
        // plus UP) => voir commentaire jira MAAM-172
        // Dans tous les cas on auras besoin de la ligne retrouvée via UPV4!
        FicheUtilisateur fiche = null;
        if(StringUtils.isNotBlank(newLigne) && newLigne.equals(getCurrentLineNumber()))
        {
        	Object result = cacheManager.getObjectFromCache(Constant.CURRENT_SFR_FICHE);
    		if(result!=null)
    		{
    			fiche = (FicheUtilisateur)result;
    		}
        	
        }
        
       if(fiche == null)  {
    	   log.debug("Appel à UP dans findSfrLine ...");
    	   fiche = userProfileHelper.recupFicheUtilisateurFromLogin(newLigne);
       }
        
        Ligne ligne = null;
            try {
            	
            	ligne = userProfileHelper.getLigneFromFiche(fiche,newLigne);
                // remplacement des services listerVision360
                sfrLine = listeDonneesService.findSFRLineFromLineNumber(newLigne);
                
                if (sfrLine != null) {
                    log.debug("La ligne reconnue par clarify est: [{}].", sfrLine);
                    
                    if (ligne != null) {
                        
                        log.debug("On va compléter la ligne [{}] avec le type de profile et le libellé PTA en effectuant un appel à UPV4!.",
                                sfrLine);
                         // Ligne trouvé par UPV4
                        sfrLine.setTypeProfilSfr(TypeProfilSfr.valueOf(ligne.getProfilPsw().name()));
                        String upLibellePTA = getLibellePTA(ligne);
                        if(StringUtils.isBlank(sfrLine.getCivility()))
                        {
                                sfrLine.setCivility(fiche.getCivilite().name());
                        }
                        if(StringUtils.isBlank(sfrLine.getName()))
                        {
                                sfrLine.setName(fiche.getNom());
                        }
                        if(StringUtils.isBlank(sfrLine.getFirstname()))
                        {
                                sfrLine.setFirstname(fiche.getPrenom());
                        }
                        if(StringUtils.isNotBlank(upLibellePTA))
                        {
                                sfrLine.setLibellePTA(upLibellePTA);
                        }
                        if(StringUtils.isBlank(sfrLine.getIdPersonne()))
                        {
                                sfrLine.setIdPersonne(fiche.getIdPersonne());
                        }
                        String codeSCS = "";
                        if (StringUtils.isBlank(sfrLine.getCodeScs())) {
                            if (sfrLine.getNatureLine().isMobile()) {
                                codeSCS = ((LigneMobile) ligne).getSegment();
                                if(StringUtils.isBlank(sfrLine.getCodeScs())){
                                    sfrLine.setCodeScs(codeSCS);}
                            } else {
                                
                                String segmentContratFixe = (((LigneFixe) ligne).getSegment() == null) ? StringUtils.EMPTY : ((LigneFixe) ligne)
                                .getSegment().toString();
                                
                                if (segmentContratFixe != null) {
                                    if(StringUtils.isBlank(sfrLine.getCodeScs())){
                                      sfrLine.setCodeScs(codeSCS);}
                                }
                            }
                                                
                        }
                        if(StringUtils.isNotBlank(getCurrentLineNumber()) && getCurrentLineNumber().equals(newLigne))
                        {
                                sfrLine.setVisionPersonne(isVisionPersonne(fiche));
                        }
                        String numContrat = "";
                        
                        if (sfrLine.getNatureLine().isMobile()) 
                        {
                                numContrat = ((LigneMobile) ligne).getNumeroContrat();
                        }
                        else
                        {
                                numContrat = ((LigneFixe) ligne).getNumeroCSU();
                        }
                        if (StringUtils.isBlank(sfrLine.getNumContrat())) {
                            sfrLine.setNumContrat(numContrat);
                            if(StringUtils.isBlank(numContrat)){
                                    log.debug("On est en vision ligne, le numéro CSU de la ligne [{}] n'a pas pu être récupéré via le web service Clarify et via userProfile!",
                                ligne.getLabel());
                            }
                        }
                        if(StringUtils.isBlank(sfrLine.getIdCSU()))
                        {
                            if(StringUtils.isBlank(sfrLine.getCodeScs()) || StringUtils.isBlank(sfrLine.getNumContrat()))
                            {
                                sfrLine.setIdCSU(getFinalIdCsu(ligne));
                            }
                            else
                            {
                                sfrLine.setIdCSU(sfrLine.getCodeScs()+"-"+sfrLine.getNumContrat());
                            }
                        }

                        // Autres infos
                        if (StringUtils.isEmpty(sfrLine.getIdCSU())) {
                                log.debug("On est en vision ligne, le numéro CSU de la ligne [{}] n'a pas pu être récupéré via le web service Clarify.",
                                    ligne.getLabel());
                        }
                    }
                    log.debug("@@@ Après la mise à jour de la ligne SfrLine {} les données utilisées sont : {}]",
                             newLigne, ReflectionToStringBuilder.toString(sfrLine, new RecursiveToStringStyle(-1)));
                }
                else
                {
                        log.debug("La ligne {} n'a pas été reconnue par clarify.",newLigne);
                    // Ligne trouvé par UPV4
                    if (fiche != null && ligne != null) {
                        sfrLine = new SfrLine(newLigne, findNatureLine(ligne));
                        sfrLine.setCivility(fiche.getCivilite() != null ? fiche.getCivilite().name() : "");
                        sfrLine.setName(fiche.getNom());
                        sfrLine.setFirstname(fiche.getPrenom());
                        
                        try
                        {
                        	sfrLine.setIdCSU(getFinalIdCsu(ligne));
                        }
                        catch(Exception e)
                        {
                        	log.error("La ligne {} ne possède pas d'Id Csu",newLigne);
                        }
                        
                        if(sfrLine.getNatureLine().isMobile()){
                                String codeSCS = ((LigneMobile) ligne).getSegment();
                                sfrLine.setCodeScs(codeSCS);
                        }else {
                                SegmentContratFixe segmentContratFixe = ((LigneFixe) ligne).getSegment();
                                if(segmentContratFixe != null){
                                String codeSCS = segmentContratFixe.toString();
                                sfrLine.setCodeScs(codeSCS);
                                }
                        }
                        
                        sfrLine.setEmail(fiche.getEmailDeContact1());
                        sfrLine.setStopEmailPub(fiche.isStopMailEmailDeContact1());
                        sfrLine.setBirthday(fiche.getDateNaissance());
                        if (ligne != null) {
                            String numero = ligne.getLabel().substring(0, 10);
                            sfrLine.setNumber(numero);
                            sfrLine.setIdPersonne(fiche.getIdPersonne());
                            sfrLine.setTypeProfilSfr(TypeProfilSfr.valueOf(ligne.getProfilPsw().name()));
                            // Adresse
                            if (ligne.getAdresseClient() != null) {
                                sfrLine.setCurrentAdresse(ligne.getAdresseClient().getAdresse());
                                sfrLine.setCurrentZipCode(ligne.getAdresseClient().getCodePostal());
                                sfrLine.setCurrentCity(ligne.getAdresseClient().getLocalite());
                            }
                        }
                        if(StringUtils.isNotBlank(getCurrentLineNumber()) && getCurrentLineNumber().equals(newLigne))
                        {
                                sfrLine.setVisionPersonne(isVisionPersonne(fiche));
                        }
                        String upLibellePTA = getLibellePTA(ligne);
                        //Le libelle PTA est prioritairement lu dans UPV4, s'il n'y est pas renseigne, on va le lire dans clarify
                        if(StringUtils.isNotBlank(upLibellePTA))
                        {
                                sfrLine.setLibellePTA(upLibellePTA);
                        }
                        sfrLine.setTypeGamme(getTypeGamme(ligne));
                        // Profil inconu
                        try {
                            sfrLine.setTypeProfilSfr(TypeProfilSfr.valueOf(ligne.getProfilPsw().name()));
                        } catch (Exception e) {
                            log.error("Profil PSW non gere: {} " + ligne.getProfilPsw(), e);
                        }
                        // Ligne inconnu
                    } else {
                        
                        log.error("Impossible de récupérer la ligne [{}] : ligne non reconnue via UPV4 : .", newLigne);
                    }
                    log.debug("@@@ Après la mise à jour de la ligne SfrLine {} les données utilisées sont : {}]",
                             newLigne, ReflectionToStringBuilder.toString(sfrLine, new RecursiveToStringStyle(-1)));
                }
            }catch (Exception e) {
                
                if (ligne != null) {
                    
                    ToStringBuilder roles = new ToStringBuilder("-");
                    for (Role role : ligne.getRoles()) {
                        roles.append(role.name()).append("-");
                    }
                    ;
                    ToStringBuilder tsb = new ToStringBuilder(this).append("CodeSCS", ligne.getCodeSCS())
                        .append("DateActivation", ligne.getDateActivation())
                        .append("IdCompteClient", ligne.getIdCompteClient()).append("Label", ligne.getLabel())
                        .append("Roles", roles.toString());
                    
                    if (ligne.getLabel() == null) {
                        log.error("L'utilisateur connecté n'a pas de ligne: ligne.getLabel = null!!");
                    }
                    if (ligne != null && ligne.getProfilPsw() != null) {
                        log.error("Profil PSW non gere: {} " + ligne.getProfilPsw().name(), e);
                    } else {
                        log.error("Profil PSW null ! ", e);
                    }
                    log.error("UserContext:", tsb.toString());
                } else if (ligne == null) {
                    log.error("PB avec userProfile, currentLigne = null!");
                }
                log.error("Impossible de récupérer la ligne non reconnue par UPV4 : [{}].", newLigne);
                log.error("Erreur: ", e);
            }
        return sfrLine;
    }
    
    /**
     * Retourne les ame de l'utilisateur attaché au parcours MAAM
     */
    protected AmesForSfrUser getAmesForSfrUser(boolean withCache,SfrLine sfrLine) throws AmeManagementException {
        
        AmesForSfrUser ameSfr = maamRestitutionService
            .recupAmesConfigurationForSfrUserForExtranet(sfrLine, getListOfTituLines(),
                                                         withCache, // avec
                                                         // cache
                                                         autenticationTools.getTypeLoginSuperUser(),
                                                         autenticationTools.getTypeCanalSuperUser());
        
        log.debug("ame : " + ameSfr);
        log.info("@@@ La configuration de l'AME est : {}]",
                 ReflectionToStringBuilder.toString(ameSfr, new RecursiveToStringStyle(-1)));
        
        return ameSfr;
    }
    
    /**
     * Initialise les infos de la simulation
     */
    protected SimulationInfos initSimulationInfosForCreation(SfrLine sfrLine, final String code, AmesForSfrUser ame) {
        log.debug("Initialisation des informations de simulation de création de groupe.");
        
        AvailableGroupeAme availableGroupeAme = ame.getAvailableGroupeAme(code);
        SimulationInfos simulationInfos = new SimulationInfos(code, availableGroupeAme.getMaxLine(),
                                                              availableGroupeAme.getMinLine());
        simulationInfos.setConnectedLine(ame.getCurrentLine());
        
        //gere le back navigateur
        simulationInfos.setMustReloadPage(false);
        
        SimuLigne simuLigne = new SimuLigne();
        simuLigne.setConnectedLine(true);
        simuLigne.setNumber(sfrLine.getNumber());
        simuLigne.setExistingLigne(true);
        simuLigne.setSameNameAsCurrentLine(true);
        simuLigne.setCivilite(sfrLine.getCivility());
        simuLigne.setName(sfrLine.getName());
        simuLigne.setFirstname(sfrLine.getFirstname());
        simuLigne.setUserTituLigne(true);
        simuLigne.setNatureLine(sfrLine.getNatureLine());
        simuLigne.setNumeroCSU(sfrLine.getIdCSU());
        simuLigne.setTypeProfilSfr((sfrLine.getTypeProfilSfr() != null) ? sfrLine.getTypeProfilSfr().name() : null);
        simuLigne.setLibellePTA(sfrLine.getLibellePTA());
        simuLigne.setTypeGamme(sfrLine.getTypeGamme());
        simuLigne.setAdded(true);
        simuLigne.setAdditionnal(false);
        simulationInfos.addSimuLigne(simuLigne);
        simulationInfos.setAddams(false);
        simulationInfos.setTranscodifications(getEliameTranscodifications());
        ame.setSimulationInfos(simulationInfos);
        
        return simulationInfos;
    }
    
    /**
     * Initialise les infos de la simulation
     */
    protected SimulationInfos initSimulationInfosForModification(final String code, AmesForSfrUser ame) {
        log.debug("Initialisation des informations de simulation de modification de groupe.");
        
        SubscribedGroupeAme subscribedGroupeAme = ame.getSubscribedGroupeAme(code);
        
        if(subscribedGroupeAme == null)
        {
                return null;
        }
        
        SimulationInfos simulationInfos = new SimulationInfos(code, subscribedGroupeAme.getNbTotalLines(),
                                                              subscribedGroupeAme.getMinimumNbLine());
        simulationInfos.setConnectedLine(ame.getCurrentLine());
        simulationInfos.setSimuLignes(getInitSimuLigne(ame, true, code, false));
        simulationInfos.setSimuForModification(true);
        simulationInfos.setMustReloadPage(false);
        simulationInfos.setTranscodifications(getEliameTranscodifications());
        ame.setSimulationInfos(simulationInfos);
        
        simulationInfos.setAddams(subscribedGroupeAme.isAddams());
        
        
        
        // On tri
        Collections.sort(simulationInfos.getSimuLignes());
        
        return simulationInfos;
    }
    
    /**
     * Recupère la liste des lignes titulaire représenté par leur posseceur
     */
    protected List<SimuLigne> getInitSimuLigne(AmesForSfrUser user, boolean isForModification, String codeAme,
                                               final boolean back) {
        
        if (!isForModification) {
            log.info("On initialise les lignes de simulation d'une création de groupe {} pour l'utilisateur {}",
                     codeAme, user);
            return getInitSimuLigneForCreation(user, codeAme, back);
        } else {
            log.info("On initialise les lignes de simulation d'une modification du groupe {} pour l'utilisateur {}",
                     codeAme, user);
            return getInitSimuLigneForModification(user, codeAme);
        }
    }
    
    
    /**
     * Recupère la liste des lignes titulaires représenté par leur posseceur pour une création d'AME
     */
    private List<SimuLigne> getInitSimuLigneForCreation(AmesForSfrUser user, String availableAmeCode, final boolean back) {
        AvailableGroupeAme availableGroupeAme = user.getAvailableGroupeAme(availableAmeCode);
        List<SimuLigne> simuLignes = Lists.newArrayList();
        
        if (availableGroupeAme.getAmesForSfrUser().getSimulationInfos() != null) {
            if (availableGroupeAme.getAmesForSfrUser().getSimulationInfos().getSimuLignes() != null && back
                && availableGroupeAme.getAmesForSfrUser().getSimulationInfos().getSimuLignes().size() > 1) {
                simuLignes = availableGroupeAme.getAmesForSfrUser().getSimulationInfos().getSimuLignes();
                return simuLignes;
            }
        }
        
        for (SfrLine sfrLine : user.getSfrLinesTitus()) {
            if (sfrLine != null) {
                if (sfrLine.equals(user.getCurrentLine())) { // La ligne connectée est automatiquement ajoutée au groupe
                    SimuLigne simuLigne = createSimuLigneFromSfrLine(sfrLine);
                    simuLigne.setAdded(true);
                    simuLigne.setAdditionnal(false);
                    simuLigne.setLinePosition(1);
                    simuLigne.setInitiallyAdded(true);
                    simuLigne.setConnectedLine(true);
                }
            }
        }
        
        return simuLignes;
    }
    
    /**
     * Initialise une ligne de simulation (sans préciser si elle est ajoutée ou non) à partir des infos d'une ligne sfr
     * 
     * @param sfrLine
     * @return
     */
    private SimuLigne createSimuLigneFromSfrLine(SfrLine sfrLine) {
        SimuLigne simuLigne = new SimuLigne();
        simuLigne.setNumber(sfrLine.getNumber());
        simuLigne.setExistingLigne(true);
        simuLigne.setCivilite(sfrLine.getCivility());
        simuLigne.setName(sfrLine.getName());
        simuLigne.setFirstname(sfrLine.getFirstname());
        simuLigne.setUserTituLigne(true);
        simuLigne.setNatureLine(sfrLine.getNatureLine());
        simuLigne.setNumeroCSU(sfrLine.getIdCSU());
        simuLigne.setTypeProfilSfr((sfrLine.getTypeProfilSfr() != null) ? sfrLine.getTypeProfilSfr().name() : null);
        simuLigne.setLibellePTA(sfrLine.getLibellePTA());
        simuLigne.setTypeGamme(sfrLine.getTypeGamme());
        
        return simuLigne;
    }
    
    /**
     * Recupère la liste des lignes titulaire représentées par leur posseceur pour une modification d'AME
     */
    private List<SimuLigne> getInitSimuLigneForModification(AmesForSfrUser user, String subscribedAmeCode) {
        List<SimuLigne> simuLignes = Lists.newArrayList();
        
       
        
        // Lignes existantes
        SubscribedGroupeAme subscribedGroupeAme = user.getSubscribedGroupeAme(subscribedAmeCode);
        SfrLine sfrCurrentLine = user.getCurrentLine();
        
        for (AmeMember member : subscribedGroupeAme.getAmeMembers()) {
                
            
            SimuLigne simuLigne = new SimuLigne();
            simuLigne.setNumber(member.getPerson().getLine().getNumber());
            simuLigne.setCivilite(member.getPerson().getCivilite());
            simuLigne.setName(member.getPerson().getName());
            simuLigne.setFirstname(member.getPerson().getFirstname());
            simuLigne.setLibellePTA(member.getPerson().getLine().getLibellePTA());
                        
            simuLigne.setExistingLigne(true);
            simuLigne.setAdded(true);
            simuLigne.setInitiallyAdded(true);
            simuLigne.setAdditionnal(false);
            
            if(sfrCurrentLine.getCivility().equalsIgnoreCase(simuLigne.getCivilite())
                        && sfrCurrentLine.getName().equalsIgnoreCase(simuLigne.getName())
                        && sfrCurrentLine.getFirstname().equalsIgnoreCase(simuLigne.getFirstname()))
            {
                simuLigne.setSameNameAsCurrentLine(true);
            }
            simuLigne.setUserTituLigne(false);
            simuLigne.setNatureLine(member.getPerson().getLine().getNatureLine());
            simuLigne.setNumeroCSU(member.getPerson().getLine().getIdCSU());
            simuLigne.setTypeProfilSfr((member.getPerson().getLine().getTypeProfilSfr() != null) ? member.getPerson()
                .getLine().getTypeProfilSfr().name() : null);
            if (user.getCurrentLine().getNumber().equals(member.getPerson().getLine().getNumber())) {
                simuLigne.setConnectedLine(true);
            }
            
            
            try
            {
            // Mise à jour de SFRLine
            SfrLine ligne = findSfrLine(member.getPerson().getLine().getNumber());
            if(ligne != null)
            {
                if(StringUtils.isNotBlank(ligne.getFirstname()))
                {
                        member.getPerson().setFirstname(ligne.getFirstname());
                        simuLigne.setFirstname(ligne.getFirstname());
                }
                if(StringUtils.isNotBlank(ligne.getName()))
                {
                        member.getPerson().setName(ligne.getName());
                        simuLigne.setName(ligne.getName());
                }
                if(StringUtils.isNotBlank(ligne.getCivility()))
                {
                        member.getPerson().setCivilite(ligne.getCivility());
                        simuLigne.setCivilite(ligne.getCivility());
                }
                
                if(StringUtils.isNotBlank(ligne.getLibellePTA()))
                {
                     member.getPerson().getLine().setLibellePTA(ligne.getLibellePTA());
                     simuLigne.setLibellePTA(ligne.getLibellePTA());
                }
              
              //update the status JOYA
                if (user.getPictoByJoyaStatus(ligne.getJoyaStatus()) != null) {
                    String joyaPicto = user.getPictoByJoyaStatus(ligne.getJoyaStatus()).getUrljoya();
                    ligne.setJoyaPictoUrl(joyaPicto);
                    simuLigne.setJoyaStatus(ligne.getJoyaStatus());
                    simuLigne.setJoyaPictoUrl(ligne.getJoyaPictoUrl());
                }
                // Mise à jour de la ligne
                member.getPerson().setLine(ligne);
            }
            
            log.debug("@@@ initialisation de la simuLigne {} avec les données suivantes : {}]",
                      simuLigne.getNumber(),  ReflectionToStringBuilder.toString(simuLigne, new RecursiveToStringStyle(-1)));
            log.debug("@@@ initialisation de member.getPerson() {} avec les données suivantes : {}]",
                      simuLigne.getNumber(),  ReflectionToStringBuilder.toString(member.getPerson(), new RecursiveToStringStyle(-1)));
           
        }
        catch (Exception e) {
            log.debug(" Impossible de trouver la ligne {}...",simuLigne.getNumber(), e);
        }
            simuLignes.add(simuLigne);
        }
        
        // Lignes disponibles de l'utilisateur connectée
        List<SfrLine> tituLines = user.getSfrLinesTitus();
        for (SfrLine sfrLine : tituLines) {
            for (SimuLigne simuLigne : simuLignes) {
                if (sfrLine != null) {
                    if (sfrLine.getNumber().equals(simuLigne.getNumber())) {
                        simuLigne.setUserTituLigne(true);
                        simuLigne.setLibellePTA(sfrLine.getLibellePTA());
                        simuLigne.setTypeGamme(sfrLine.getTypeGamme());
                    }
                }
            }

        }
        
        
        return simuLignes;
    }
    
    /**
     * Récupère la nature de la ligne à partir de la ligne
     */
    protected NatureLine findNatureLine(final Ligne ligne) {
        if (ligne instanceof LigneMobile) {
            return NatureLine.MOB;
        } else if (ligne instanceof LigneFixe) {
            // fixe THD
            if (GammeLigneFixe.THD.equals(((LigneFixe) ligne).getGamme())) {
                return NatureLine.THD;
                
                // fixe DSL
            } else {
                return NatureLine.DSL;
            }
        }
        
        // Mobile par défaut
        return NatureLine.MOB;
    }
    
    /**
     * Récupère la nature de la ligne à partir de son numéro
     */
    protected NatureLine findNatureLine(final String number) {
        if (number.startsWith(Constant.NUM_06) || number.startsWith(Constant.NUM_07)) {
            return NatureLine.MOB;
        } else {
            return NatureLine.DSL;
        }
    }
    
    
    
    protected String getUserLabelInSession() {
        String label = (String) httpSessionCacheManager.getObjectFromCache(Constant.CURRENT_SFR_LABEL);
        
        if (label == null) {
            label = StringUtils.EMPTY;
        }
        
        return label;
    }
    
    public boolean canResetCache() {
        Boolean reset = (Boolean) httpSessionCacheManager.getObjectFromCache(Constant.MUST_RELOAD_CACHE);
        if (reset != null) {
            return reset.booleanValue();
        } else {
            return false;
        }
    }
    
    public void resetCache(boolean reset) {
        httpSessionCacheManager.putObjectInCache(Constant.MUST_RELOAD_CACHE, new Boolean(reset));
    }
    /**
     * Permet de savoir si l'on autorise l'ajout d'une ligne (DUO ou ADDITIONNELLE) à un groupe si elle n'est pas éligible
     * Pour cela, on vérifie que le profile de la ligne ne fait pas partie des profils existants.
     * @param typeProfilSfr Profil à vérifier dans la table des profils
     * @param isEligible Indique que le profil est éligible ou non (existe parmis les profils de l'AME)
     * @return true si le profile n'existe pas en base de donnée, false sinon.
     */
    private boolean isGrantedAnyWay(String typeProfilSfr, boolean isEligible)
    {
        if(isEligible) {return true;}
        
        boolean granted = true;
        if(StringUtils.isBlank(typeProfilSfr)) return false;
        
        List<Profil> profils = profilDatabaseService.findAllProfils();
        for(Profil aProfil : profils)
        {
                if(aProfil.getCode().equals(typeProfilSfr))
                {
                        granted = false;
                        break;
                }
        }
        if(granted)
        {log.debug("Autorisation du profil non existant ["+typeProfilSfr+"]!");}
        
        return granted;
    }
    
    /**
     * Cette méthode renvoit true si le profile de la ligne à ajouter correspond à un profile additionnel du groupe <br>
     * Autrement dit : Permet de vérifier qu'une ligne ADDITIONNELLE est éligible pour l'ajout à un groupe existant
     * 
     * @param subscribedGroupeAme, Le groupe souscrit à modifier
     * @param sfrLine, La ligne additionnelle à ajouter au groupe
     * @param typeLigne, Le type de ligne à tester
     * @return
     */
    protected boolean isAdditionnelProfileEligibleForSubscribedGroupe(SubscribedGroupeAme subscribedGroupeAme,
                                                                      SfrLine sfrLine) {
        if (sfrLine.getTypeProfilSfr() == null) {
            return false;
        }
        
        String sfrlineProfile = sfrLine.getTypeProfilSfr().name();
        
        for (Profil profil : subscribedGroupeAme.getProfils()) {
            if (profil.getProfilLineType().equals(ProfilLineTypeSfr.ADDITIONNELLE)
                && profil.getCode().equals(sfrlineProfile)) {
                return true;
            }
        }
        
        return isGrantedAnyWay(sfrlineProfile,false);
    }
    
    /**
     * Cette méthode renvoit true si le profile de la ligne à ajouter correspond à un profile DUO du groupe <br>
     * Autrement dit : Permet de vérifier qu'une ligne DUO est éligible pour la création d'un groupe
     * 
     * @param availableGroupeAme, Le groupe à souscrire
     * @param sfrLine, La ligne additionnelle à ajouter au groupe
     * @return
     */
    protected boolean isDuoProfileEligibleForAvailableGroupe(AvailableGroupeAme availableGroupeAme, SfrLine sfrLine) {
        
        // MAAM-344 - AMV
        if(!sfrLine.getNatureLine().isMobile())
        {
                log.debug("La ligne fixe "+sfrLine.getNumber()+" est éligible pour la création d'un duo.");
                return true;
        }
        
        if (sfrLine.getTypeProfilSfr() == null) {
            return false;
        }
        
        String sfrlineProfile = sfrLine.getTypeProfilSfr().name();
        
        for (Profil profil : availableGroupeAme.getProfils()) {
            if (profil.getProfilLineType().equals(ProfilLineTypeSfr.DUO) && profil.getCode().equals(sfrlineProfile)) {
                return true;
            }
        }
        return isGrantedAnyWay(sfrlineProfile,false);
    }
    
        /**
         * Renvoie la liste des transcodifications eliame configurées dans le BO
         */
        public List<Transcodification> getEliameTranscodifications(){
                
                List<Transcodification> transcodifications = Lists.newArrayList();
                try{
                        transcodifications = transcodificationDatabaseService.findAllTranscodificationByType(TranscodificationType.INELIGIBILITE_ELIAME);
                }catch (Exception e) {
                        // Erreur dans la récupération des transcodifications
                        log.error("Erreur dans la récupération des transcodifications : ", e);
                }
                return transcodifications;
        }
        public void resetCache(AmesForSfrUser ameUser)
        {
        	if(ameUser==null)
        	{
        		return;
        	}
            maamRestitutionService.removeAmesConfigurationForSfrUserFromCache(ameUser.getCurrentLine());
        }
        
        protected boolean isValidateGroup(List<SimuLigne> lines) {
            boolean hasFixLine = false;
            boolean hasMobileLine = false;
            if (lines != null) {
                    for (SimuLigne line : lines) {
                            if (line.getNatureLine().isMobile()) {
                                    hasMobileLine = true;
                            } else {
                                    hasFixLine = true;
                            }
                    }
            }
            
            return hasFixLine && hasMobileLine;
        }


         protected void ajoutNewLineInJsp(AmesForSfrUser ame, SfrLine newSfrLine, boolean isAddams, ModelMap model) {

        	 AmeMember member = new AmeMember();
             AmePerson person = new AmePerson();

             person.setCivilite(newSfrLine.getCivility());
             person.setFirstname(newSfrLine.getFirstname());
             person.setName(newSfrLine.getName());
             person.setLine(newSfrLine);
             member.setPerson(person);
             
             
             List<AvailableGroupeAme> availableAmeGroups = ame.getAvailableGroupeAmes();
        	 List<SubscribedGroupeAme> subscribedGroups = ame.getSubscribedGroupeAmes();
        	 
        	 if(subscribedGroups != null && subscribedGroups.size()>0)
        	 {
                 model.put(Constant.AME_GROUP, subscribedGroups.get(0));
        	 }
        	 else
        	 {
        		 if(availableAmeGroups!=null && availableAmeGroups.size()>0)
        		 {
        			 availableAmeGroups.get(0).setAmeMembers(member);
            		 availableAmeGroups.get(0).setAddams(isAddams);
                     model.put(Constant.AME_GROUP, availableAmeGroups.get(0));
        		 }
        		 
        	 }
        	 model.put(Constant.AME_MEMBER, member);
            }
            
          public SfrLine ajoutNewLigne(AmesForSfrUser ame, String lineNumberToAdd, SfrLine sfrCurrentLine, boolean isCreation,
                                         ModelMap model)  {
                 boolean hasError = false;
                 SfrLine newSfrLine = null;
                 List<SimuLigne> addedLines = ame.getSimulationInfos().getSimuLignes();
                 if (addedLines != null) {
                         for (SimuLigne line : addedLines) {
                                 if (line.getNumber().equals(lineNumberToAdd)) {
                                         // error RG103 Si la ligne est saisie 2 fois ou
                                         // qu’elle fait partie du groupe existant alors on
                                         // affiche le message suivant
                                         hasError = true;
                                         model.addAttribute("errorCode", "errorExistingLine");
                                         break;
                                 }
                         }//end for
                         if (!hasError) {
                             newSfrLine = findSfrLine(lineNumberToAdd);
                             if (newSfrLine == null) {
                                 // le msisdn n'existe pas
                                 // RG 101 Si la ligne mobile saisie n'est pas trouve dans
                                 // Clarify
                                 // alors on affiche le message suivant :
                                 hasError = true;
                                 model.addAttribute("errorCode", "errorLineNotExist");

                             } 
                             if (!hasError) {
                               
                                 if (Constant.CODESCS_08.equals(newSfrLine.getCodeScs())) {
                                         // RG99 Si la ligne mobile saisie est une ligne
                                         // prepaye (scs 08 dans UP ou Clarify) alors on
                                         // affiche le message suivant :
                                         hasError = true;
                                         model.addAttribute("errorCode", "errorCodeScs");
                                 } else {
                                     if (StringUtils.isNotEmpty(newSfrLine.getLibellePTA()) && newSfrLine.getLibellePTA().toUpperCase().contains(Constant.CHAINE_RED)) {
                                                 // RG100 Si la ligne mobile saisie est une ligne
                                                 // RED (le nom du forfait comporte la chaine
                                                 // « RED ») alors on affiche le message
                                                 // suivant :
                                                 hasError = true;
                                                 model.addAttribute("errorCode", "errorCodeLibellePTA");
                                         } else {
                                                 if (maamRestitutionService.isLineBelongToGroup(
                                                                 null, newSfrLine.getIdCSU())) {
                                                         // RG 102 Si la ligne saisie est deja dans
                                                         // un groupe (source restituionAME)
                                                         hasError = true;
                                                         model.addAttribute("errorCode",
                                                         "errorCodeHasAmeSubscrib");
                                                 } else {
                                                         // ajout de la ligne
                                                         SimuLigne simuNewLigne = new SimuLigne();
                                                         
                                                         // On va mettre à jour le status joya de la ligne
                                                         if (ame.getPictoByJoyaStatus(newSfrLine.getJoyaStatus()) != null) {
                                                             newSfrLine.setJoyaPictoUrl(ame.getPictoByJoyaStatus(newSfrLine.getJoyaStatus()).getUrljoya());
                                                         }
                                                         simuNewLigne.setCivilite(newSfrLine.getCivility());
                                                         simuNewLigne.setName(newSfrLine.getName());
                                                         simuNewLigne.setFirstname(newSfrLine.getFirstname());
                                                         simuNewLigne.setTypeGamme(newSfrLine.getTypeGamme());
                                                         simuNewLigne.setLibellePTA(newSfrLine.getLibellePTA());
                                                         simuNewLigne.setNumeroCSU(newSfrLine.getIdCSU());
                                                         simuNewLigne.setNumber(newSfrLine.getNumber());
                                                         simuNewLigne.setExistingLigne(true);
                                                         simuNewLigne.setInitiallyAdded(false);
                                                         simuNewLigne.setJoyaPictoUrl(newSfrLine.getJoyaPictoUrl());
                                                         simuNewLigne.setJoyaStatus(newSfrLine.getJoyaStatus());
                                                         if(sfrCurrentLine.getCivility().equalsIgnoreCase(simuNewLigne.getCivilite())
                                                                         && sfrCurrentLine.getName().equalsIgnoreCase(simuNewLigne.getName())
                                                                         && sfrCurrentLine.getFirstname().equalsIgnoreCase(simuNewLigne.getFirstname()))
                                                         {
                                                                 simuNewLigne.setSameNameAsCurrentLine(true);
                                                         }
                                                         simuNewLigne.setAdded(true);
                                                         simuNewLigne.setAdditionnal(true);
                                                         simuNewLigne.setUserTituLigne(false);
                                                         simuNewLigne.setNatureLine(newSfrLine.getNatureLine());
                                                         simuNewLigne.setTypeProfilSfr((newSfrLine.getTypeProfilSfr() != null) ? newSfrLine.getTypeProfilSfr().name(): null);
                                                         //add the new line into "SimulationInfos"
                                                         ame.getSimulationInfos().addSimuLigne(simuNewLigne);
                                                 }
                                         }
                                 }//end else
                         }//end if "hasError"
                 }//end if "addedLines != null"
             }
             // On renvoie la ligne uniquement s'il n'y a pas d'erreur!
             return hasError?null:newSfrLine;
        }
}