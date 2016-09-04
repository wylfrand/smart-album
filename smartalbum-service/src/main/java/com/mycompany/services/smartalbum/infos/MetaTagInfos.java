package com.mycompany.services.smartalbum.infos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.database.smartalbum.model.MetaTag;

public class MetaTagInfos implements Serializable{
	
	private static final long serialVersionUID = -9065024051468971330L;

	private Long id;

	private String tag;

	private List<ImageInfos> images = new ArrayList<ImageInfos>();

	public MetaTagInfos() {
	}

	public MetaTagInfos(Long id, String tag) {
		this.id = id;
		this.tag = tag;
	}

	//---------------------------------Getters, Setters..
	public Long getId() {
		return id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public List<ImageInfos> getImages() {
		return images;
	}

	public void setImages(List<ImageInfos> images) {
		this.images = images;
	}

	public void addImage(ImageInfos image) {
		images.add(image);
	}

	public void removeImage(ImageInfos image) {
		images.remove(image);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		final MetaTag metaTag = (MetaTag) obj;

		return (id == null ? metaTag.getId() == null : id.equals(metaTag.getId()))
				&& tag.equalsIgnoreCase(metaTag.getTag());
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + tag.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "{id : "+getId()+", tag : "+getTag()+"}"; 
	}

}
