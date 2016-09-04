package com.mycompany.filesystem.exception;

/**
 * Exception spécique couche service.
 * 
 * @author amv
 */
public class ServiceException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Code de l'erreur
     */
    private String codeErreur;
    
    /**
     * Libellé de l'erreur
     */
    private String libelleErreur;
    
    public ServiceException(String message) {
	super(message);
    }
    
    public ServiceException(Throwable cause) {
	super(cause);
    }
    
    public ServiceException(String message, Throwable cause) {
	super(message, cause);
    }
    

    public ServiceException(String codeErreur, String libelleErreur, Throwable cause) {
	super(cause);
	this.codeErreur = codeErreur;
	this.libelleErreur = libelleErreur;
    }
    
    public String getCodeErreur() {
	return codeErreur;
    }
    
    public void setCodeErreur(String codeErreur) {
	this.codeErreur = codeErreur;
    }
    
    public String getLibelleErreur() {
	return libelleErreur;
    }
    
    public void setLibelleErreur(String libelleErreur) {
	this.libelleErreur = libelleErreur;
    }
}