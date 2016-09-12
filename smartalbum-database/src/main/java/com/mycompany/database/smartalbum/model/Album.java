/**
 * License Agreement.
 *
 *  JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007  Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */
package com.mycompany.database.smartalbum.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.OrderBy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.google.common.collect.Sets;
import com.mycompany.database.smartalbum.search.enums.MessageHTMLTypes;

/**
 * Class for representing Album Entity.
 * 
 * // * @author Aristide M'vou
 */
@Entity
@Table(name = "Album")
public class Album extends ABuisnessObject<Long> implements Serializable, Cloneable {

	private static final long serialVersionUID = -7042878411608396483L;

	@Id
	@GeneratedValue
	private Long id = null;

	@OneToMany(mappedBy = "album", cascade = {CascadeType.PERSIST,CascadeType.REMOVE,CascadeType.REFRESH}, orphanRemoval = true)
	private List<Image> images = new ArrayList<Image>();

	@NotNull
	@ManyToOne
	@JoinColumn(nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Shelf shelf;

	@OneToMany(mappedBy = "album", cascade = {CascadeType.PERSIST,CascadeType.REMOVE,CascadeType.REFRESH})
	@Fetch(FetchMode.SUBSELECT)
	private List<MessageHTML> messagesHTML = new ArrayList<>();

	@OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE,CascadeType.REFRESH})
	@Fetch(FetchMode.SELECT)
	private Image coveringImage;

	@Transient
	private boolean showAfterCreate;

	@Temporal(TemporalType.DATE)
	private Date created;

	@Column(length = 255, nullable = false)
	@NotNull
	@NotEmpty
	@Length(min = 3, max = 50)
	private String name;

	@Column(length = 1024)
	private String description;

	@OrderBy(clause = "date asc")
	@OneToMany(cascade = {CascadeType.PERSIST,CascadeType.REMOVE }, mappedBy = "album")
	@Fetch(FetchMode.SUBSELECT)
	private Set<Comment> comments = Sets.newHashSet();

	// ********************** Accessor Methods ********************** //

	/**
	 * Getter for property shelf
	 * 
	 * @return Shelf object, that contains this album
	 */
	public Shelf getShelf() {
		return shelf;
	}

	public void setShelf(Shelf parent) {
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
	public List<Image> getImages() {
		if (images == null) {
			images = new ArrayList<Image>();
		}
		return images;
	}

	public void setImages(List<Image> newImages) {
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
	public List<Image> getUnvisitedImages() {
		final List<Image> unvisitedImages = new ArrayList<Image>(this.getImages().size());
		for (Image i : this.getImages()) {
			if (i.isNew()) {
				unvisitedImages.add(i);
			}
		}
		return unvisitedImages;
	}

	/**
	 * @param coveringImage
	 *            - Image for covering album
	 */
	public void setCoveringImage(Image coveringImage) {
		this.coveringImage = coveringImage;
	}

	// ********************** Business Methods ********************** //

	/**
	 * This method add image to collection of images of current album
	 * 
	 * @param image
	 *            - image to add
	 */
	public void addImage(Image image) { // TODO
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
		image.setUser(null);
		images.add(image);
	}

	/**
	 * This method remove image from collection of images of album
	 * 
	 * @param image
	 *            - image to remove
	 */
	public void removeImage(Image image) {
		if (image == null) {
			throw new IllegalArgumentException("Null image");
		}
		if (!image.getAlbum().equals(this)) {
			throw new IllegalArgumentException("This album not contain this image!");
		}

		if (getCoveringImage().equals(image)) {
			setCoveringImage(null);
		}
		image.setAlbum(null);
		images.remove(image);
	}

	/**
	 * Getter for property owner
	 * 
	 * @return User object, owner of this album
	 */
	public User getOwner() {
		return getShelf() != null ? getShelf().getOwner() : null;
	}

	public boolean isOwner(User user) {
		return user != null && user.equals(getOwner());
	}

	/**
	 * This method determine index of specified image in collection of images,
	 * belongs to this album. Used in slideshow etc...
	 * 
	 * @return index of specified image
	 */
	public int getIndex(Image image) {
		if (isEmpty()) {
			return -1;
		}
		int index = 0;
		for (Iterator<Image> it = images.iterator(); it.hasNext();) {
			index++;
			Image currentImage = it.next();
			if (currentImage.equals(image)) {
				break;
			}
		}
		return index;
	}

	/**
	 * This method determine index of specified image in collection of images,
	 * belongs to this album. Used in slideshow etc...
	 * 
	 * @return index of specified image
	 */
	public Image getImageByName(String imageName) {
		Image result = null;
		List<Image> myImages = getImages();
		for (Image currentImage : myImages) {
			if (currentImage.getName().equals(imageName)) {
				result = currentImage;
				break;
			}
		}
		return result;
	}

	/**
	 * This method determine index of specified image in collection of images,
	 * belongs to this album. Used in slideshow etc...
	 * 
	 * @return index of specified image
	 */
	public Image getImageById(Long imageId) {
		Image result = null;
		List<Image> myImages = getImages();
		for (Image currentImage : myImages) {
			if (currentImage.getId().equals(imageId)) {
				result = currentImage;
				break;
			}
		}
		return result;
	}

	/**
	 * This method determine covering image of this album
	 * 
	 * @return covering image
	 */
	public Image getCoveringImage() {
		if (coveringImage == null && !isEmpty()) {

			for (Iterator<Image> it = images.iterator(); it.hasNext();) {
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
	public void addComment(Comment comment) {
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
	public void removeComment(Comment comment) {
		if (comment == null) {
			throw new IllegalArgumentException("Null comment");
		}
		if (comment.getAlbum().equals(this)) {
			comment.setAlbum(null);
			comments.remove(comment);
		} else {
			throw new IllegalArgumentException("Comment not belongs to this image");
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

	@Override
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
		Album other = (Album) obj;
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
	public MessageHTML getMessageHTMLByType(MessageHTMLTypes typeDeMessage) {
		MessageHTML result = null;
		for (MessageHTML message : getMessagesHTML()) {
			if (message.getMessage_type().equals(typeDeMessage.name())) {
				result = message;
				break;
			}
		}
		return result;
	}

	/**
	 * @return the messageHTML
	 */
	public List<MessageHTML> getMessagesHTML() {
		return messagesHTML;
	}

	/**
	 * @param messageHTML
	 *            the messageHTML to set
	 */
	public void setMessagesHTML(List<MessageHTML> messageHTML) {
		this.messagesHTML = messageHTML;
	}

	/**
	 * @return the comments
	 */
	public Set<Comment> getComments() {
		return comments;
	}

	/**
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
}
