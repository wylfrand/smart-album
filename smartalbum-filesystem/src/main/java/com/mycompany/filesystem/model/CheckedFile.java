/**
 * 
 */
package com.mycompany.filesystem.model;

import java.io.Serializable;

/**
 * @author aristidemvou
 *
 */
public class CheckedFile implements Serializable {
	
	

	private static final long serialVersionUID = 1L;
	
	public CheckedFile(){}
	
	private String imageName;
	private Long imageId;
	private String basUrl;
	
	public CheckedFile(String imageName, Long imageId)
	{
		this.imageId = imageId;
		this.imageName = imageName;
	}
	
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public Long getImageId() {
		return imageId;
	}
	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public String getBasUrl() {
		return basUrl;
	}

	public void setBasUrl(String basUrl) {
		this.basUrl = basUrl;
	}

}
