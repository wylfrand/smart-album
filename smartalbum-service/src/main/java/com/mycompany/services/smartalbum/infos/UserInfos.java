package com.mycompany.services.smartalbum.infos;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mycompany.database.smartalbum.model.Sex;

public class UserInfos implements MappingInfos<UserInfos>, Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;

	private String passwordHash;

	private String firstName;

	private String secondName;

	private String email;

	private String login;

	private Date birthDate;

	private Sex sex = Sex.MALE;

	private Boolean hasAvatar;

	private List<ShelfInfos> shelves = new ArrayList<ShelfInfos>();

	private boolean preDefined;

	public boolean isPreDefined() {
		return preDefined;
	}

	public void setPreDefined(boolean preDefined) {
		this.preDefined = preDefined;
	}

	//----------------Getters, Setters
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Long getId() {
		return id;
	}

	public List<ShelfInfos> getShelves() {
		return shelves;
	}
	
	public void setShelves(List<ShelfInfos> shelves) {
		this.shelves = shelves;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public Boolean getHasAvatar() {
		return hasAvatar;
	}

	public void setHasAvatar(Boolean hasAvatar) {
		this.hasAvatar = hasAvatar;
	}

	//---------------------------Business methods

	/**
	 * This method add shelf to collection of shelves, belongs to user
	 *
	 * @param shelf -
	 *              shelf to add
	 */
	public void addShelf(ShelfInfos shelf) {
		if (shelf == null) {
			throw new IllegalArgumentException("Null shelf!");
		}
		if (!shelves.contains(shelf)) {
			shelf.setParent(this);
			shelves.add(shelf);
		}
	}

	/**
	 * This method remove shelf from collection of shelves, belongs to user
	 *
	 * @param shelf -
	 *              shelf to remove
	 */
	public void removeShelf(ShelfInfos shelf) {
		if (shelf == null) {
			throw new IllegalArgumentException("Null shelf");
		}
		if (shelf.getOwner().getLogin().equals(this.getLogin())) {
			shelf.setParent(null);
			shelves.remove(shelf);
		} else {
			throw new IllegalArgumentException("ShelfVO not belongs to this user!");
		}
	}

	/**
	 * Return relative path of folder with user's images in file-system(relative to uploadRoot parameter)
	 */
	public String getPath() {
		if (this.getId() == null) {
			return null;
		}
		return File.separator + this.getLogin() + File.separator;
	}

	/**
	 * This method return all images, belongs to user
	 *
	 * @return images, belongs to user
	 */
	public List<ImageInfos> getImages() {
		final List<ImageInfos> images = new ArrayList<ImageInfos>();
		for (ShelfInfos s : getShelves()) {
			images.addAll(s.getImages());
		}
		return images;
	}

	/**
	 * This method return all albums, belongs to user
	 *
	 * @return albums, belongs to user
	 */
	public List<AlbumInfos> getAlbums() {
		final List<AlbumInfos> albums = new ArrayList<AlbumInfos>();
		for (ShelfInfos s : getShelves()) {
			albums.addAll(s.getAlbums());
		}
		return albums;
	}

	/**
	 * This method return all images, belongs to user
	 *
	 * @return images, belongs to user
	 */
	public List<ImageInfos> getSharedImages() {
		final List<ImageInfos> images = new ArrayList<ImageInfos>();
		for (ShelfInfos s : getShelves()) {
			if (!s.isShared()) {
				continue;
			}
			for (AlbumInfos a : s.getAlbums()) {
				//images.addAll(a.getImages());
			}
		}
		return images;
	}

	/**
	 * This method return all albums, belongs to user
	 *
	 * @return albums, belongs to user
	 */
	public List<AlbumInfos> getSharedAlbums() {
		final List<AlbumInfos> albums = new ArrayList<AlbumInfos>();
		for (ShelfInfos s : getShelves()) {
			if (!s.isShared()) {
				continue;
			}
			albums.addAll(s.getAlbums());
		}
		return albums;
	}

	/**
	 * This method check, if user already have shelf with given name
	 * 
	 * @param shelf - shelf to check
	 * @return boolean value, that indicated if shelf with the same name exist
	 */
	public boolean hasShelfWithName(ShelfInfos shelf) {
		for(ShelfInfos s : getShelves()){
			if(!s.equals(shelf) && s.getName().equals(shelf.getName())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method check, if parent shelf contain album with the same name as given album
	 * 
	 * @param album - album to check
	 * @return boolean value, that indicate if album with the same name exist
	 */
	public boolean hasAlbumWithName(AlbumInfos album) {
		for(AlbumInfos a : album.getShelf().getAlbums()){
			if(!a.equals(album) && a.getName().equals(album.getName())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method check, if parent shelf contain album with the same name as given album
	 * 
	 * @param album - album to check
	 * @return boolean value, that indicate if album with the same name exist
	 */
	public ShelfInfos getShelfWithId(Long shelfId) {
		ShelfInfos result = null;
		for(ShelfInfos aShelf : getShelves()){
			if(aShelf.getId().equals(shelfId)){
				result =  aShelf;
			}
		}
		return result;
	}
	
	/**
	 * This method check, if containing album already have image with the same name
	 * 
	 * @param image - image to check
	 * @return boolean value, that indicate if image with the same name exist
	 */
	public boolean hasImageWithName(ImageInfos image) {
//		for(ImageInfos i : image.getAlbum().getImages()){
//			if(!i.equals(image) &&  i.getName().equals(image.getName())){
//				return true;
//			}
//		}
		return false;
	}
	
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final UserInfos user = (UserInfos) o;

		if (id != null ? !id.equals(user.id) : user.id != null) return false;
		if (login != null ? !login.equals(user.login) : user.login != null)
			return false;

		return true;
	}

	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (login != null ? login.hashCode() : 0);
		return result;
	}

	public void setId(Long id) {
		this.id = id;
	}
	


	@Override
	public void update(UserInfos entity, MappingOptions options) {
		if (options.isLoadingShelvesParent()) {
			for (ShelfInfos shelf : shelves) {
				shelf.update(this, options);
			}
		}
	}
	
	public void updateUser(final MappingOptions options){
		update(this, options);
	}

}
