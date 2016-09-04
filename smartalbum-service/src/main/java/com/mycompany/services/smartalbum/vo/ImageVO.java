package com.mycompany.services.smartalbum.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import com.mycompany.database.smartalbum.model.Comment;
import com.mycompany.database.smartalbum.utils.ActionTools;

public class ImageVO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;

	private List<MetaTagVO> imageTags = new ArrayList<MetaTagVO>();

	private Set<CommentVO> comments = Sets.newHashSet();

	private AlbumVO album;

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
	
	private List<MessageHTMLVO> messagesHTML = new ArrayList<>();

	/**
	 * Getter for property preDefined
	 * 
	 * @return is this shelf is predefined
	 */
	public boolean isPreDefined() {
		return getAlbum().isPreDefined();
	}

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

	public AlbumVO getAlbum() {
		return album;
	}

	public void setAlbum(AlbumVO album) {
		this.album = album;
	}

	public List<MetaTagVO> getImageTags() {
		return imageTags;
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

	public void setImageTags(final List<MetaTagVO> imageTags) {
		this.imageTags = imageTags;
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

	// ---------------------------Business methods

	/**
	 * Add comment to this image.
	 * 
	 * @param comment
	 *            - comment to add
	 */
	public void addComment(CommentVO comment) {
		if (comment == null) {
			throw new IllegalArgumentException("Null comment!");
		}
		comment.setImage(this);
		comments.add(comment);
	}

	/**
	 * Remove comment from list of comments, belongs to that image.
	 * 
	 * @param comment
	 *            - comment to delete
	 */
	public void removeComment(Comment comment) {
		if (comment == null) {
			throw new IllegalArgumentException("Null comment");
		}
		if (comment.getImage().equals(this)) {
			comment.setImage(null);
			comments.remove(comment);
		} else {
			throw new IllegalArgumentException(
					"Comment not belongs to this image");
		}
	}

	/**
	 * Add metatag to this image.
	 * 
	 * @param metatag
	 *            - metatag to add
	 */
	public void addMetaTag(MetaTagVO metatag) {
		if (metatag == null) {
			throw new IllegalArgumentException("Null metatag!");
		}
		if (!imageTags.contains(metatag)) {
			metatag.addImage(this);
			imageTags.add(metatag);
		}
	}

	/**
	 * Remove metatag from list of metatag, associated to that image.
	 * 
	 * @param metatag
	 *            - metatag to delete
	 */
	public void removeMetaTag(MetaTagVO metatag) {
		if (metatag == null) {
			throw new IllegalArgumentException("Null metatag!");
		}
		if (imageTags.contains(metatag)) {
			metatag.removeImage(this);
			imageTags.remove(metatag);
		}
	}

	/**
	 * Return MetaTag object by string representation
	 * 
	 * @param s
	 *            - string representation of metatag
	 */
	public MetaTagVO getTagByName(String s) {
		for (MetaTagVO t : imageTags) {
			if (t.getTag().equals(s)) {
				return t;
			}
		}
		return null;
	}

	/**
	 * Return Comma separated tag value for presentation in view
	 */
	public String getMeta() {
		final StringBuilder s = new StringBuilder();
		for (MetaTagVO tag : this.imageTags) {
			s.append(tag.getTag()).append(", ");
		}
		// Remove ',' from end
		if (s.length() >= 2) {
			s.delete(s.length() - 2, s.length());
		}
		return s.toString();
	}

	/**
	 * Return relative path of this image in file-system(relative to uploadRoot
	 * parameter)
	 */
	public String getFullPath() {
		if (getAlbum().getPath() == null) {
			return null;
		}
		return getAlbum().getPath() + this.path;
	}

	public UserVO getOwner() {
		return getAlbum().getOwner();
	}

	public boolean isOwner(UserVO user) {
		return user != null && user.equals(getOwner());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImageVO other = (ImageVO) obj;
		if (album == null) {
			if (other.album != null)
				return false;
		} else if (!album.equals(other.album))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (Double.doubleToLongBits(size) != Double
				.doubleToLongBits(other.size))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((album == null) ? 0 : album.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		long temp;
		temp = Double.doubleToLongBits(size);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public String toString() {
		return "{id : " + getId() + ", name : " + getName() + "}";
	}

	public void setId(Long id) {
		this.id = id;
	}


	/**
	 * @return the comments
	 */
	public Set<CommentVO> getComments() {
		return comments;
	}

	/**
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(Set<CommentVO> comments) {
		this.comments = comments;
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

	/**
	 * @return the messagesHTML
	 */
	public List<MessageHTMLVO> getMessagesHTML() {
		if(messagesHTML == null)
		{
			messagesHTML = new ArrayList<>();
		}
		return messagesHTML;
	}

	/**
	 * @param messagesHTML the messagesHTML to set
	 */
	public void setMessagesHTML(List<MessageHTMLVO> messagesHTML) {
		this.messagesHTML = messagesHTML;
	}

}
