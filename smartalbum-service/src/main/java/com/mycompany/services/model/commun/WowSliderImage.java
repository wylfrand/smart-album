package com.mycompany.services.model.commun;

import java.io.Serializable;

public class WowSliderImage implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String src;
	private String title;
	/**
	 * @return the src
	 */
	public String getSrc() {
		return src;
	}
	/**
	 * @param src the src to set
	 */
	public void setSrc(String src) {
		this.src = src;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

}
