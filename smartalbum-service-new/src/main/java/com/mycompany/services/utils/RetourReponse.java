package com.mycompany.services.utils;

import java.io.Serializable;

/**
 * Définit le retour à une reponse http smartAlbum
 * 
 * @author amv
 */
public class RetourReponse implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Résultat du retour
     */
    private Boolean result = Boolean.TRUE;
    

    /**
     * Objet résultat du retour
     */
    private Object resultObject = null;
    
    
    public static RetourReponse ok() {
        
	return new RetourReponse();
    }
    
    public RetourReponse() {
	
    }
    
    // GETTER / SETTER
    public Boolean getResult() {
	return result;
    }
    
    public void setResult(Boolean result) {
	this.result = result;
    }
    
    public Object getResultObject() {
	return resultObject;
    }
    
    public void setResultObject(Object resultObject) {
	this.resultObject = resultObject;
    }
}
