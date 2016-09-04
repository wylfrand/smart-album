package com.sfr.applications.maam.front.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tag Lib pour gérer les format des numéros à afficher
 * 
 * @author sco
 */
public class TelephoneNumberFormatTag extends AMaamSpringTag {
    
    private static final transient Logger log = LoggerFactory.getLogger(TelephoneNumberFormatTag.class);
    
    private static final long serialVersionUID = 1L;
    
    private String label;
    
    private String separator;
    
    private final static String SPACE_NUMER = " ";
    
    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
     */
    @Override
    public int doStartTag() throws JspException {
	try {
	    label = label.trim();
	    
	    String currentSeparator = StringUtils.isBlank(separator) ? SPACE_NUMER : separator;
	    
	    StringBuilder result = new StringBuilder();
	    if(StringUtils.isBlank(label) || label.length()!=10)
	    {
	    	result.append(label);
	    }
	    else
	    {
		    for (int i = 0; i < label.length(); i += 2) {
			if ((i + 2) < label.length()) {
			    result.append(label.subSequence(i, i + 2) + currentSeparator);
			} else {
			    result.append(label.subSequence(i, i + 2));
			}
		    }
	    }
	    
	    pageContext.getOut().print(result.toString());
	} catch (Exception ex) {
	    log.error("Erreur: ", ex);
	    throw new JspTagException(ex);
	}
	
	return SKIP_BODY;
    }
    

    /**
     * GETTER/SETTER
     */
    public String getLabel() {
	return label;
    }
    
    public void setLabel(String label) {
	this.label = label;
    }
    
    public String getSeparator() {
	return separator;
    }
    
    public void setSeparator(String separator) {
	this.separator = separator;
    }
}