package com.sfr.applications.maam.front.model;

import java.io.Serializable;

import com.sfr.applications.maam.front.enumeration.SimulationAmeErrorCode;

/**
 * Définit le retour à une reponse http maam
 * 
 * @author sco
 */
public class RetourReponse implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Résultat du retour
     */
    private Boolean result = Boolean.TRUE;
    
    /**
     * Code d'erreur si présent
     */
    private SimulationAmeErrorCode error = null;
    
    /**
     * Objet résultat du retour (numéro de téléphone par exemple)
     */
    private Object resultObject = null;
    
    
    public static RetourReponse ok() {
	return new RetourReponse();
    }
    
    public RetourReponse() {
	
    }
    
    public RetourReponse(SimulationAmeErrorCode error) {
	this.error = error;
	result = Boolean.FALSE;
    }
    

    // GETTER / SETTER
    public Boolean getResult() {
	return result;
    }
    
    public void setResult(Boolean result) {
	this.result = result;
    }
    
    public SimulationAmeErrorCode getError() {
	return error;
    }
    
    public void setError(SimulationAmeErrorCode error) {
	this.error = error;
    }
    
    public Object getResultObject() {
	return resultObject;
    }
    
    public void setResultObject(Object resultObject) {
	this.resultObject = resultObject;
    }
}
