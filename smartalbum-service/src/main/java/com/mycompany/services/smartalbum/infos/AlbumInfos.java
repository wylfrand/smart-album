package com.mycompany.services.smartalbum.infos;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.google.common.collect.Sets;
import com.mycompany.database.smartalbum.transaction.CommitTransaction;

public class AlbumInfos implements Serializable{

	private static final long serialVersionUID = -7042878411608396483L;

	private Long id = null;

	private Set<ImageInfos> images = new HashSet<ImageInfos>();

	private ShelfInfos shelf;
	
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
		return shelf;
	}

	public void setShelf(ShelfInfos parent) {
		this.shelf = parent;
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
	 * Getter for property images
	 * 
	 * @return List if images, belongs to this album
	 */
	public Set<ImageInfos> getImages() {
		if (images == null) {
			images = Sets.newHashSet();
		}
		return images;
	}

	public void setImages(Set<ImageInfos> newImages) {
		images = newImages;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return List of unvisited images
	 */
	public Set<ImageInfos> getUnvisitedImages() {
		final Set<ImageInfos> unvisitedImages = new HashSet<ImageInfos>(this
				.getImages().size());
		for (ImageInfos i : this.getImages()) {
			if (i.isNew()) {
				unvisitedImages.add(i);
			}
		}
		return unvisitedImages;
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
	 * This method add image to collection of images of current album
	 * 
	 * @param image
	 *            - image to add
	 */
	@CommitTransaction
	public void addImage(ImageInfos image) { // TODO
		if (image == null) {
			throw new IllegalArgumentException("Null image!");
		}
		if (this.getImages().contains(image)) {
			// If album contain this image already
			return;
		}
		if (image.getAlbum() != null && !this.equals(image.getAlbum())) {
			// Remove from previous album
			image.getAlbum().removeImage(image);
		}
		image.setAlbum(this);
		images.add(image);
	}

	/**
	 * This method remove image from collection of images of album
	 * 
	 * @param image
	 *            - image to remove
	 */
	public void removeImage(ImageInfos image) {
		if (image == null) {
			throw new IllegalArgumentException("Null image");
		}
		if (!image.getAlbum().equals(this)) {
			throw new IllegalArgumentException(
					"This album not contain this image!");
		}

		if (getCoveringImage().equals(image)) {
			setCoveringImage(null);
		}
		//image.setAlbum(null);
		images.remove(image);
	}

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
	 * This method determine index of specified image in collection of images,
	 * belongs to this album. Used in slideshow etc...
	 * 
	 * @return index of specified image
	 */
	public int getIndex(ImageInfos image) {
		if (isEmpty()) {
			return -1;
		}
		 int index = 0;
		for (Iterator<ImageInfos> it = images.iterator(); it.hasNext(); ) {
		    index++;
		    ImageInfos currentImage = it.next();
		        if (currentImage.equals(image))
		        {
		            break;
		        }
		    }
		return index;
	}

	/**
	 * This method determine covering image of this album
	 * 
	 * @return covering image
	 */
    public ImageInfos getCoveringImage() {
        if (coveringImage == null && !isEmpty()) {
            
            for (Iterator<ImageInfos> it = images.iterator(); it.hasNext();) {
                coveringImage = it.next();
            }
        }
        
        return coveringImage;
    }
    
	/**
	 * Add comment to this image.
	 * 
	 * @param comment
	 *            - comment to add
	 */
	public void addComment(CommentInfos comment) {
		if (comment == null) {
			throw new IllegalArgumentException("Null comment!");
		}
		comment.setAlbum(this);
		comments.add(comment);
	}
	
	/**
	 * Remove comment from list of comments, belongs to that image.
	 * 
	 * @param comment
	 *            - comment to delete
	 */
	public void removeComment(CommentInfos comment) {
		if (comment == null) {
			throw new IllegalArgumentException("Null comment");
		}
		if (comment.getAlbum().equals(this)) {
			comment.setAlbum(null);
			comments.remove(comment);
		} else {
			throw new IllegalArgumentException(
					"CommentVO not belongs to this image");
		}
	}

	/**
	 * This method determine is album empty or not
	 */
	public boolean isEmpty() {
		return images == null || images.isEmpty();
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
		result = prime * result + ((shelf == null) ? 0 : shelf.hashCode());
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
		if (shelf == null) {
			if (other.shelf != null)
				return false;
		} else if (!shelf.equals(other.shelf))
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

}
