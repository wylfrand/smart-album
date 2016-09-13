package com.mycompany.services.smartalbum.infos;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.google.common.collect.Sets;

public class AlbumInfos implements MappingInfos<ShelfInfos>,  Serializable{

	private static final long serialVersionUID = -7042878411608396483L;

	private Long id = null;

	private ShelfInfos parent;
	
	private MessageHTMLInfos messageHTML;

	private ImageInfos coveringImage;

	private boolean showAfterCreate;

	private Date created;

	private String name;

	private String description;
	
	private Set<CommentInfos> comments = Sets.newHashSet();

	// ********************** Accessor Methods ********************** //

	/**
	 * Getter for property shelf
	 * 
	 * @return ShelfVO object, that contains this album
	 */
	public ShelfInfos getShelf() {
		return getParent();
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

	

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	

	/**
	 * @param coveringImage
	 *            - ImageInfos for covering album
	 */
	public void setCoveringImage(ImageInfos coveringImage) {
		this.coveringImage = coveringImage;
	}

	// ********************** Business Methods ********************** //
	/**
	 * Getter for property owner
	 * 
	 * @return UserVO object, owner of this album
	 */
	public UserInfos getOwner() {
		return getShelf() != null ? getShelf().getOwner() : null;
	}

	public boolean isOwner(UserInfos user) {
		return user != null && user.equals(getOwner());
	}
	
	/**
	 * Getter for property preDefined
	 * 
	 * @return is this shelf is predefined
	 */
	public boolean isPreDefined() {
		return getOwner().isPreDefined();
	}

	/**
	 * Return relative path of this album in file-system(relative to uploadRoot
	 * parameter)
	 */
	public String getPath() {
		if (getShelf() == null || getShelf().getPath() == null) {
			return null;
		}
		return getShelf().getPath() + this.getId() + File.separator;
	}

	@Override
	public String toString() {
		return "{id : " + getId() + ", name : " + getName() + "}";
	}

	public boolean isShowAfterCreate() {
		return showAfterCreate;
	}

	public void setShowAfterCreate(boolean showAfterCreate) {
		this.showAfterCreate = showAfterCreate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((getEtagere() == null) ? 0 : getEtagere().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AlbumInfos other = (AlbumInfos) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (getShelf() == null) {
			if (other.getShelf() != null)
				return false;
		} else if (!getShelf().equals(other.getShelf()))
			return false;
		return true;
	}

	/**
	 * @return the messageHTML
	 */
	public MessageHTMLInfos getMessageHTML() {
		return messageHTML;
	}

	/**
	 * @param messageHTML the messageHTML to set
	 */
	public void setMessageHTML(MessageHTMLInfos messageHTML) {
		this.messageHTML = messageHTML;
	}

	/**
	 * @return the comments
	 */
	public Set<CommentInfos> getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(Set<CommentInfos> comments) {
		this.comments = comments;
	}

	/**
	 * @return the etagere
	 */
	public ShelfInfos getEtagere() {
		return parent;
	}

	/**
	 * @param etagere the etagere to set
	 */
	public void setEtagere(ShelfInfos etagere) {
		this.parent = etagere;
	}

	/**
	 * @return the parent
	 */
	public ShelfInfos getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(ShelfInfos parent) {
		this.parent = parent;
	}

	/**
	 * @return the coveringImage
	 */
	public ImageInfos getCoveringImage() {
		return coveringImage;
	}

	@Override
	public void update(ShelfInfos entity, MappingOptions options) {
		parent = entity;
		if (options == null || options.isLoadingAlbumsImagesParent()) {
			for (ImageInfos image : entity.getImages()) {
				image.update(this, options);
			}
		}
	}
}
