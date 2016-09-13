package com.mycompany.services.smartalbum.vo.form;

import java.io.Serializable;
import java.util.Date;

import com.mycompany.database.smartalbum.utils.ActionTools;

public class ImageVOForm implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private boolean covering;
	
	private String category;

	private String path;

	private String cameraModel;

	private int height;

	private double size;

	private int width;

	private Date uploaded;

	private String description;

	private Date created;

	private boolean allowComments;

	private Boolean showMetaInfo = true;

	private boolean visited;

	private String meta = "";
	
	private boolean checked;

	// ********************** Accessor Methods ********************** //

	public Boolean getShowMetaInfo() {
		return showMetaInfo;
	}

	public void setShowMetaInfo(final Boolean showMetaInfo) {
		this.showMetaInfo = showMetaInfo;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Getter for property path. Represent file-system structure, relative at
	 * uploadRoot dir(determined at startup, by default is system temp dir)
	 * Usually is user.GetLogin() + SLASH + image.getAlbum().getId() + SLASH +
	 * fileName, for example "amarkhel/15/coolPicture.jpg"
	 * 
	 * @return relative path of image
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Setter for property path
	 * 
	 * @param path
	 *            - relative path to image
	 */
	public void setPath(String path) {
		this.path = path;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * Setter for property meta
	 * 
	 * @param meta
	 *            - string representation of metatags, associated to image. Used
	 *            at jsf page.
	 */
	public void setMeta(String meta) {
		this.meta = meta;
	}

	/**
	 * Getter for property meta
	 * 
	 * @return string representation of metatags, associated to image. Used at
	 *         jsp page.
	 */
	public String getMetaString() {
		return meta;
	}

	public String getCameraModel() {
		return cameraModel;
	}

	public void setCameraModel(String cameraModel) {
		this.cameraModel = cameraModel;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Getter for property size
	 * 
	 * @return size of image in KB
	 */
	public double getSize() {
		return size;
	}

	/**
	 * setter for property size
	 * 
	 * @param size
	 *            - size of image in KB
	 */
	public void setSize(double size) {
		this.size = size;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Getter for property uploaded
	 * 
	 * @return date of upload to site of this image
	 */
	public Date getUploaded() {
		return uploaded;
	}

	/**
	 * setter for property uploaded
	 * 
	 * @param uploaded
	 *            - date of upload
	 */
	public void setUploaded(Date uploaded) {
		this.uploaded = uploaded;
	}

	/**
	 * Getter for property allowComments. If true, other user may comment this
	 * image.
	 * 
	 * @return is other users may comment this image
	 */
	public boolean isAllowComments() {
		return allowComments;
	}

	/**
	 * @param allowComments
	 *            the allowComments to set
	 */
	public void setAllowComments(boolean allowComments) {
		this.allowComments = allowComments;
	}

	/**
	 * @return if this image is covering for containing album
	 */
	public boolean isCovering() {
		return covering;
	}

	/**
	 * @param covering
	 *            - determine if this image is covering for containing album
	 */
	public void setCovering(boolean covering) {
		this.covering = covering;
	}

	/**
	 * Getter for property visited
	 * 
	 * @return boolean value, that indicated is user visit this image already
	 */
	public boolean isVisited() {
		return visited;
	}

	/**
	 * Setter for property visited
	 * 
	 * @param visited
	 *            - boolean value, that indicated is user visit this image
	 *            already
	 */
	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	/**
	 * Determine if this image should be marked as new(on jsf page, for example
	 * in tree)
	 * 
	 * @return boolean value, that indicated is this image should be marked as
	 *         new
	 */
	public boolean isNew() {
		if (!visited) {
			return this.getUploaded().after(ActionTools.getRecentlyDate());
		}
		return false;
	}

	@Override
	public String toString() {
		return "{id : " + getId() + ", name : " + getName() + "}";
	}

	public void setId(Long id) {
		this.id = id;
	}

	


	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
