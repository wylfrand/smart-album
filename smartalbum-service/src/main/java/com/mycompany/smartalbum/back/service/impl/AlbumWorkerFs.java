package com.mycompany.smartalbum.back.service.impl;

import com.mycompany.database.smartalbum.model.Album;
import com.mycompany.database.smartalbum.model.User;
import com.mycompany.services.utils.RetourReponse;
import com.mycompany.smartalbum.back.service.SmartAlbumBackService;

public class AlbumWorkerFs implements Runnable {

	protected SmartAlbumBackService backService;
	private Album album;
	private User user;
	private int coverPageIndex, coverPhotoIndex;
	private boolean isCreation;
	RetourReponse response = new RetourReponse();
	
	@Override
	public void run() {
		saveAlbum();
	}
	
	private void saveAlbum() {
		if(isCreation){
			response = backService.saveAlbum(album, isCreation, user, null);
			}
			else
			{
				
			}
		}

	public SmartAlbumBackService getBackService() {
		return backService;
	}

	public void setBackService(SmartAlbumBackService backService) {
		this.backService = backService;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public int getCoverPageIndex() {
		return coverPageIndex;
	}

	public void setCoverPageIndex(int coverPageIndex) {
		this.coverPageIndex = coverPageIndex;
	}

	public int getCoverPhotoIndex() {
		return coverPhotoIndex;
	}

	public void setCoverPhotoIndex(int coverPhotoIndex) {
		this.coverPhotoIndex = coverPhotoIndex;
	}

	public boolean isCreation() {
		return isCreation;
	}

	public void setCreation(boolean isCreation) {
		this.isCreation = isCreation;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
