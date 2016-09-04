package com.sfr.applications.maam.front.utils;

public interface ViewName {

    /**         pages restitution       **/
    String RESTITUTION_GROUPE_INDEX_VIEW = "/groupe/restitution/index";
    String RESULT_ADD_LINE= "/groupe/restitution/result_add_line";
    String RESULT_SUP_LINE= "/groupe/restitution/new_empty_line";
    
    /** pages ineligibilite **/
    String ERROR_INELIGIBLE = "/error/ineligible.html";
    
    /** pages service indisponible **/
    String ERROR_SERVICE_INDISPONIBLE = "/error/service_indisponible.html";
    
    /** pages ineligibilite **/
    String ERROR_INELIGIBLE_VIEW = "/error/ineligible";
    
    /** pages ineligibilite **/
    String ERROR_KO_VIEW = "/error/ko";
    
    /** pages service indisponible **/
    String ERROR_SERVICE_INDISPONIBLE_VIEW = "/error/service_indisponible";
    
    /** pages confirmation simulation**/
    String CONFIRMATION_GROUPE_VIEW = "/groupe/simulation/confirmation";
    
    /** pages confirmation suppression**/
    String CONFIRMATION_GROUPE_SUPPRESSION_VIEW = "/groupe/suppression/confirmation";
    
    /** pages confirmation suppression**/
    String CONFIRMATION_GROUPE_SUPPRESSION = "/groupe/suppression/confirmation.html";
    
    /** Page de simulation **/
    String GROUPE_SIMULATION_VIEW = "/groupe/simulation/index";
    
    String HOME = "/groupe/affichage/index.html";
    
    String MODIFICATION_GROUPE_CONFIRMATION_VIEW = "/groupe/modification/confirmation";
    
    String MODIFICATION_GROUPE_ERROR_VIEW = "/groupe/simulation/body/detail-error_modification";
    
    String MODIFICATION_INELIGIBLE_ERROR_VIEW = "/groupe/creation/errorIneligible";
    
    String MODIFICATION_GROUPE_NEW_BIZZ_VIEW = "/groupe/modification/body/new_bizz_modification_bloc";
    
    String VIEW_SELECT_LIGNE = "/select-ligne/select";
    
    String VIEW_SELECT_LIGNE_FIRST = "/select-ligne/first";
    
    String GROUPE_CONFIRMATION_REDIRECT = "/groupe/confirmation.html";
    
    String TEMPLATE_PDF_DIR = "/config/template/pdf/";
}
