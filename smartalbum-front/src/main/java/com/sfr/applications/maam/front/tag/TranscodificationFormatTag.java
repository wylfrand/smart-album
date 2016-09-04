package com.sfr.applications.maam.front.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sfr.applications.maam.database.model.Transcodification;
import com.sfr.applications.maam.database.model.enumeration.TranscodificationType;
import com.sfr.applications.maam.database.service.TranscodificationDatabaseService;

/**
 * Tag Lib pour gérer les transcodifications à afficher à partir du code
 * 
 * @author sco
 */
public class TranscodificationFormatTag extends AMaamSpringTag{

	private static final transient Logger log = LoggerFactory.getLogger(TranscodificationFormatTag.class);
	
    private static final long serialVersionUID = 1L;
    
    private final static String TRANSCODIFICATION_SERVICE_BEAN_NAME = "transcodificationDatabaseService";
    
    /**
     * Le code de la transcodification
     */
    private String code;
    
    /**
     * Le type de la transcodification
     */
    private String transcodificationType;
    
    /*
     * (non-Javadoc)
     * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
     */
    @Override
    public int doStartTag() throws JspException{
    	try{
    		TranscodificationDatabaseService service = (TranscodificationDatabaseService)getApplicationContext().getBean(TRANSCODIFICATION_SERVICE_BEAN_NAME); 
    		Transcodification transcodification = service.findTranscodification(code, 
    				                                                            TranscodificationType.valueOf(transcodificationType));
    		if(transcodification != null){
    			pageContext.getOut().print(transcodification.getLibelle());
    		}else{
    			pageContext.getOut().print(code);
    		}
    	}catch(Exception ex){
    		log.error("Erreur: ", 
    				  ex);
    		throw new JspTagException(ex);
    	}

    	return SKIP_BODY;
    }
    

    // GETTER / SETTER
	public String getCode(){
		return code;
	}

	public void setCode(String code){
		this.code = code;
	}

	public String getTranscodificationType(){
		return transcodificationType;
	}

	public void setTranscodificationType(String transcodificationType){
		this.transcodificationType = transcodificationType;
	}
}