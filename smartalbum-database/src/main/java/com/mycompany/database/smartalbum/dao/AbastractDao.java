package com.mycompany.database.smartalbum.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.slf4j.Logger;

import com.mycompany.database.smartalbum.model.ABuisnessObject;

public abstract class AbastractDao<T extends ABuisnessObject<?>, PK extends Object>{

    @PersistenceContext(unitName="jpaUnit", type=PersistenceContextType.EXTENDED)
    protected EntityManager em;

    public abstract List<T> findAll();
  
    public void print(){
    	List<T> objets = findAll();
    	getLog().debug("On a {} objets pour l'entité de type [{}]", objets.size(), getBoClass());
    	
    	if(getLog().isDebugEnabled()){
	    	for(T obj: objets){
	    		getLog().debug("{}", obj);
	    	}
    	}
    }
    
    
    /**
	 * Renvoie une durée au format: mm:HH:ss
	 * 
	 * @param startMs La date de début en ms
	 * @param endMs La date de fin en ms
	 * @return Le temps écoulé
	 */
	protected static String getTime(long startMs,
			                      long endMs){
		long lastMs = endMs - startMs;
		
		long timeInS = lastMs / 1000;
		long timeInMs = lastMs % 1000;
		long timeInMin = timeInS / 60;
		timeInS = timeInS % 60;
		long timeInH = timeInMin / 60;
		timeInMin = timeInMin % 60;
		
		return new StringBuilder().append(timeInH < 10? "0": "")
		                          .append(timeInH)
		                          .append("H")
		                          .append(timeInMin < 10? "0": "")
		                          .append(timeInMin)
		                          .append("mn")
		                          .append(timeInS < 10? "0": "")
		                          .append(timeInS)
		                          .append("s")
		                          .append(timeInMs < 10? "00": timeInMs < 100? "0": "")
		                          .append(timeInMs)
		                          .append("ms")
		                          .toString();
	}
	
	
    // GETTER / SETTER
    protected abstract Class<T> getBoClass();
    
    protected abstract Logger getLog();

    
}