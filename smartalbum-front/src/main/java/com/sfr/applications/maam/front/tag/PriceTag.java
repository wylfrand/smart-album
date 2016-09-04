package com.sfr.applications.maam.front.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mycompany.services.model.commun.Price;

/**
 * Tag Lib pour gérer les format du prix à afficher
 * 
 * @author fya
 */
public class PriceTag extends AMaamSpringTag{

	private static final transient Logger log = LoggerFactory.getLogger(PriceTag.class);
	
    private static final long serialVersionUID = 1L;
    
    private String value;
    
    /*
     * (non-Javadoc)
     * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
     */
    @Override
    public int doStartTag() throws JspException{
    	try{
    	    Price price = new Price(value);

    		pageContext.getOut().print(price.toString());
    	}catch(Exception ex){
    		log.error("Erreur: ", 
    				  ex);
    		throw new JspTagException(ex);
    	}

    	return SKIP_BODY;
    }

    
    /**
     * GETTER/SETTER
     */
	public String getValue(){
		return value;
	}

	public void setValue(String value){
		this.value = value;
	}
}