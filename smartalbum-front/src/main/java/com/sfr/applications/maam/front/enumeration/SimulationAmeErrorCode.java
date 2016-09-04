package com.sfr.applications.maam.front.enumeration;

/**
 * La liste des codes erreurs pour la simulation
 * 
 * @author mgning
 */
public enum SimulationAmeErrorCode {
    
    /**
     * Pas assez de membre pour créer le groupe
     */
    NOT_ENOUGH_MEMBER,
    
    /**
     * Trop de membre pour créer le groupe
     */
    MAXIMUM_NUMBER_MEMBER,
    
    /**
     * Le numero saisi n'est pas un numéro mobile reconnu par SFR
     */
    NOT_VALID_SFR_NUMBER_MOBILE,
    
    /**
     * Le numero saisi n'est pas un numéro fixe reconnu par SFR
     */
    NOT_VALID_SFR_NUMBER_FIXE,
    
    /**
     * Le numero saisi n'est pas un numéro au bon format
     */
    NOT_VALID_NUMBER,
    
    /**
     * Le membre existe déja dans le groupe
     */
    ALREADY_MEMBER_EXIST,
    
    /**
     * Non eligible par eliame: aucun resultat
     */
    ELIAME_INELIGIBLE_NO_RESULT,
    
    /**
     * Non eligible par eliame
     */
    ELIAME_INELIGIBLE,
    
    /**
     * Vous devez être administrateur du groupe pour cette opération
     */
    MUST_BE_ADMIN,
    
    /**
     * Erreur liée à l'expiration du cache
     */
    CACHE_ERROR,
    
    /**
     * Erreur lors de la suppression de groupe : erreur web service
     */
    GROUP_DELETION_WS_ERROR,
    
    /**
     * Erreur lors de la suppression de groupe :groupe inconnu ou utilisateur non admin ou nombre mini de lignes correct
     */
    GROUP_DELETION_BUSINESS_ERROR,
    
    /**
     * Simulation déjà effectuée
     */
    ALREADY_SIMULATION_DONE,
    
    /**
     * Code d'erreur en cas d'ajout de ligne à un groupe existant KO
     */
    GROUPE_MODIFICATION_AJOUT_ERROR,
    
    /**
     * Code d'erreur en cas d'ajout de ligne à un groupe existant KO
     */
    ELIAME_TECHNICAL_ERROR,
    
    /**
     * Code d'erreur en cas de souscription de groupe KO
     */
    GROUPE_SOUSCRIPTION_ERROR,
    /**
     * Code d'erreur en cas de souscription de groupe KO
     */
    TYPE_ALREADY_ADDED_ERROR,
    /**
     * Code d'erreur en cas de timeout du ws simulationAME
     */
    ELIAME_TIMEOUT_ERROR,
    /**
     * Code d'erreur en cas de timeout du ws simulationAME
     */
    NO_ERROR,
    
    /**
     * The member already exist in an existing AME
     */
    ALREADY_IN_A_GROUP;
}