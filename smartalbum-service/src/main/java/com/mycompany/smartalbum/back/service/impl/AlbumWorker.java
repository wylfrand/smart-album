package com.mycompany.smartalbum.back.service.impl;

import com.mycompany.database.smartalbum.exception.PhotoAlbumException;
import com.mycompany.smartalbum.back.form.AlbumForm;
import com.mycompany.smartalbum.back.service.SmartAlbumBackService;

public class AlbumWorker implements Runnable {

	protected SmartAlbumBackService backService;
	
	protected AlbumForm currentAlbumForm = null;

	@Override
	public void run() {
		createOrodifyAlbum();
	}
	
	private void createOrodifyAlbum() {
		if(currentAlbumForm!=null){
			if(currentAlbumForm.getCreation().booleanValue()){
				backService.addAlbum(currentAlbumForm);
			}
			else
			{
				try {
					backService.modifyCurrentAlbum(currentAlbumForm);
				} catch (PhotoAlbumException e) {
					if(Thread.currentThread().isAlive())
					{
						Thread.currentThread().interrupt();
					}
				}
			}
		}
	}

	public AlbumForm getCurrentAlbumForm() {
		return currentAlbumForm;
	}

	public void setCurrentAlbumForm(AlbumForm currentAlbumForm) {
		this.currentAlbumForm = currentAlbumForm;
	}

	public SmartAlbumBackService getBackService() {
		return backService;
	}

	public void setBackService(SmartAlbumBackService backService) {
		this.backService = backService;
	}

}
