package com.mycompany.database.smartalbum.model;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entité de base du modèle de données SMARTALBUM
 * 
 * @author wmvou
 */
public abstract class ABuisnessObject<T extends Object> implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
	private static transient final Logger log = LoggerFactory.getLogger("SMARTALBUM_MODEL");
	
	public final static String TABLE_PREFIX = "";
	
    
    // GETTER / SETTER
    abstract public T getId();

    abstract public void setId(final T id);

	protected Logger getLog(){
		return log;
	}
}