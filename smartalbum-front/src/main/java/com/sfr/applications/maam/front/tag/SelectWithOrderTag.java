package com.sfr.applications.maam.front.tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sfr.applications.maam.database.model.NewLineConfiguration;

/**
 * Tag Lib pour gérer l'affichage du libellé forfait
 * 
 * @author amvou
 */
public class SelectWithOrderTag extends AMaamSpringTag {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private static final transient Logger log = LoggerFactory.getLogger(SelectWithOrderTag.class);
    
  
    /**
     * Les lignes configurées en base
     */
    private Set<NewLineConfiguration> newLineConfigurations;
    
    /**
     * Les lignes configurées sur le serveur MAAM
     */
    private SortedMap<Integer,String> mapOrder = new TreeMap<Integer,String>();
    
    /**
     * La configuration initiale
     */
    private String newBizzOrder;
    
    private String nature;
    
    private static final String ORDERID_SEPARATOR = ";";
    
  
    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
     */
    @Override
    public int doStartTag() throws JspException {
	try {		
	    pageContext.getOut().print(getOrderedOptions());
	} catch (Exception ex) {
	    log.error("Erreur: ", ex);
	    throw new JspTagException(ex);
	}
	
	return SKIP_BODY;
    }
    
    private void updateNewBizzMapOrder() {    	
   	    
    	    String[] entry = newBizzOrder.split(ORDERID_SEPARATOR);
    	    for(int i=0; i< entry.length;i++)
    	    {
    	    	mapOrder.put(i, entry[i].trim());
    	    }
	}

	private String getOrderedOptions() {

		updateNewBizzMapOrder();
		
		boolean isFirstElement = true;
    	StringBuilder optionsSelect = new StringBuilder();
    	List<String> addedTypeId = new ArrayList<String>();
   	
    	for(int i = 0;i < getMapOrder().size();i++)
    	{
    		NewLineConfiguration currentLineConf = getNewLineConf(getMapOrder().get(i));
    		if(currentLineConf != null){
    			addedTypeId.add(currentLineConf.getTypeId());
	    		if(nature.equals(currentLineConf.getNature().name()))
	    		{
		    		if(isFirstElement)
		    		{
		    			optionsSelect.append("<option value='"+currentLineConf.getId()+"' selected=selected>"+currentLineConf.getLibelle()+"</option>");
		    			isFirstElement = false;
		    		}
		    		else
		    		{    			
		    			optionsSelect.append("<option value='"+currentLineConf.getId()+"'>"+currentLineConf.getLibelle()+"</option>");
		    		}
	    		}
    		}

    	}
    	
    	// Si l'on n'a pas une map complète, on la complète avec le reste des éléments de newLineConfigurations
		if(getMapOrder() != null && getNewLineConfigurations() != null && getMapOrder().size() != getNewLineConfigurations().size())
		{			
			for(NewLineConfiguration newConf: getNewLineConfigurations())
			{
				if(nature.equals(newConf.getNature().name()))
	    		{
					if(!addedTypeId.contains(newConf.getTypeId()))
					{
						optionsSelect.append("<option value='"+newConf.getId()+"'>"+newConf.getLibelle()+"</option>");
					}
	    		}
					
			}
		}
    	return optionsSelect.toString();
	}

	public static Logger getLog() {
		return log;
	}
	
	private NewLineConfiguration getNewLineConf(final String newLineTypeId)
	{
		if(newLineTypeId == null) return null;
		NewLineConfiguration result = null;
		for (NewLineConfiguration currentLineConf : getNewLineConfigurations()) {
			
			if(currentLineConf.getTypeId().equals(newLineTypeId.trim()))
			{
				result = currentLineConf;
				break;
			}			
		}
		return result;
	}

	public Set<NewLineConfiguration> getNewLineConfigurations() {
		return newLineConfigurations;
	}

	public Map<Integer, String> getMapOrder() {
		return mapOrder;
	}

	public void setNewLineConfigurations(
			Set<NewLineConfiguration> newLineConfigurations) {
		this.newLineConfigurations = newLineConfigurations;
	}
	
	public void setMapOrder(TreeMap<Integer, String> mapOrder) {
		this.mapOrder = mapOrder;
	}

	public String getNewBizzOrder() {
		return newBizzOrder;
	}

	public void setNewBizzOrder(String newBizzOrder) {
		this.newBizzOrder = newBizzOrder;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

}