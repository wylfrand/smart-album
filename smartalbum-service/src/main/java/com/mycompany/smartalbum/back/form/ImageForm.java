package com.mycompany.smartalbum.back.form;

import java.io.Serializable;
import java.util.List;



public class ImageForm implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7913680835245562287L;
	
	private List<String> imageNames;
	
	private Long albumId = -1L;

	public Long getAlbumId() {
		return albumId;
	}

	public void setAlbumId(Long albumId) {
		this.albumId = albumId;
	}

	public List<String> getImageNames() {
		return imageNames;
	}

	public void setImageNames(List<String> imageNames) {
		this.imageNames = imageNames;
	}
	
	


}