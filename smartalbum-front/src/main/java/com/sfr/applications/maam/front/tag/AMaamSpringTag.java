package com.sfr.applications.maam.front.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.springframework.context.ApplicationContext;

import com.sfr.applications.maam.front.utils.SpringContextHolder;

/**
 * Tag Lib de base pour Maam, gérant le context Spring et l'utilisation des tooltip
 * 
 * @author amv
 */
public abstract class AMaamSpringTag extends BodyTagSupport{

    private static final long serialVersionUID = 1L;
    
    private boolean withToolTip;
    // This attribute become mandatory if withToolTip=true
    private String id;
    private String ttclass;
	private String dataPlacement;
	private String href;
    
    /*
     * (non-Javadoc)
     * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
     */
    @Override
    public abstract int doStartTag() throws JspException;
    
    
    /**
     * Retourne le contexte spring
     */
    protected ApplicationContext getApplicationContext(){
    	return SpringContextHolder.getApplicationContext();
    }


	public boolean isWithToolTip() {
		return withToolTip;
	}


	public void setWithToolTip(boolean withToolTip) {
		this.withToolTip = withToolTip;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getDataPlacement() {
		return dataPlacement;
	}


	public void setDataPlacement(String dataPlacement) {
		this.dataPlacement = dataPlacement;
	}


	public String getHref() {
		return href;
	}


	public void setHref(String href) {
		this.href = href;
	}


	public String getTtclass() {
		return ttclass;
	}


	public void setTtclass(String ttclass) {
		this.ttclass = ttclass;
	}
}