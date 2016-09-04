package com.mycompany.services.smartalbum.infos;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ShelfInfos implements Serializable {

	private static final long serialVersionUID = -7042878411608396483L;

	private Long id;

	private String name;

	private String description;

	private UserInfos owner = new UserInfos();

	private Set<AlbumInfos> albums = new HashSet<AlbumInfos>();

	private boolean shared;

	private Date created;

	/**
	 * La description longue d'une étagère
	 */
	private String longValue;

	/**
	 * Getter for property preDefined
	 * 
	 * @return is this shelf is predefined
	 */
	public boolean isPreDefined() {
		return getOwner() != null && getOwner().isPreDefined();
	}

	public Long getId() {
		return id;
	}

	/**
	 * Getter for property name
	 * 
	 * @return name of album
	 */
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

	public Set<AlbumInfos> getAlbums() {
		return albums;
	}

	public UserInfos getOwner() {
		return owner;
	}

	public void setOwner(UserInfos owner) {
		this.owner = owner;
	}

	public boolean isOwner(UserInfos user) {
		return getOwner() != null && getOwner().equals(user);
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * Getter for property shared. If true - all users can view this shelves and
	 * albums and images, contained in this shelf, otherwise this shelf can view
	 * only owner.
	 * 
	 * @return shared
	 */
	public boolean isShared() {
		return shared;
	}

	/**
	 * Setter for property shared
	 * 
	 * @param shared
	 *            - determine is this shelf will be accessible by other users.
	 */
	public void setShared(boolean shared) {
		this.shared = shared;
	}

	// ********************** Business Methods ********************** //

	/**
	 * @return List of unvisited images
	 */
	public List<ImageInfos> getUnvisitedImages() {
		final List<ImageInfos> unvisitedImages = new ArrayList<ImageInfos>();
		for (AlbumInfos a : getAlbums()) {
			unvisitedImages.addAll(a.getUnvisitedImages());
		}
		return unvisitedImages;
	}

	/**
	 * @return List of images, belongs to this shelf
	 */
	public List<ImageInfos> getImages() {
		final List<ImageInfos> images = new ArrayList<ImageInfos>();
		for (AlbumInfos a : getAlbums()) {
			images.addAll(a.getImages());
		}
		return images;
	}

	/**
	 * This method add album to collection of albums of current shelf
	 * 
	 * @param album
	 *            - album to add
	 */
	public void addAlbum(AlbumInfos album) {
		if (album == null) {
			throw new IllegalArgumentException("Null album!");
		}

		if (album.getShelf() != null
				&& !album.getShelf().getAlbums().contains(this)) {
			// remove from previous shelf
			album.getShelf().removeAlbum(album);
			album.setShelf(this);
			albums.add(album);
		}
	}

	/**
	 * This method remove album from collection of albums of album
	 * 
	 * @param album
	 *            - album to remove
	 */
	public void removeAlbum(AlbumInfos album) {
		if (album == null) {
			throw new IllegalArgumentException("Null album!");
		}

		if (!album.getShelf().equals(this)) {
			throw new IllegalArgumentException(
					"This ShelfVO not contain this album!");
		}

		albums.remove(album);
		album.setShelf(null);
	}

	/**
	 * This method return first album of current shelf or null if shelf haven't
	 * albums.
	 * 
	 * @return first album of shelf or null
	 */
	public AlbumInfos getFirstAlbum() {
		if (this.albums.isEmpty()) {
			return null;
		}

		Iterator<AlbumInfos> it = albums.iterator();
		if(it.hasNext())
		{
			return (AlbumInfos)it.next();
		}
		
		return null;
	}

	/**
	 * Return relative path of this shelf in file-system(relative to uploadRoot
	 * parameter)
	 */
	public String getPath() {
		if (getOwner().getPath() == null) {
			return null;
		}
		return getOwner().getPath() + this.getId().toString() + File.separator;
	}

	@Override
	public String toString() {
		return "{id : " + getId() + ", name : " + getName() + "}";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		return result;
	}

	
	public void setId(Long id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

	/**
	 * @return the longValue
	 */
	public String getLongValue() {
		return longValue;
	}

	/**
	 * @param longValue
	 *            the longValue to set
	 */
	public void setLongValue(String longValue) {
		this.longValue = longValue;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShelfInfos other = (ShelfInfos) obj;
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
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		return true;
	}
}