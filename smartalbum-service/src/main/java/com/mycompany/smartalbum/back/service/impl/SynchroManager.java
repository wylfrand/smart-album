package com.mycompany.smartalbum.back.service.impl;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mycompany.database.smartalbum.exception.PhotoAlbumException;
import com.mycompany.database.smartalbum.model.User;
import com.mycompany.smartalbum.back.service.SmartAlbumBackService;

public class SynchroManager implements Runnable {
	private SmartAlbumBackService backService;
	private final static transient Logger LOG = LoggerFactory.getLogger(SynchroManager.class);
	private User user;
	
	@Override
	public void run() {
		try {
			synchronizeShelvesWithDatabase();
		} catch (PhotoAlbumException e) {
			LOG.error("Une erreur s'est produite pendant la synchronisation ...",e);
		}
	}
	
	private void synchronizeShelvesWithDatabase() throws PhotoAlbumException {
		// Supprime les albums vides enregistrés en base
		backService.deleteEmptyAlbums(user.getLogin());
		// Cré les albums correspondant à des répertoires de photos les supprime
		// s'ils sont vides
		List<String> userNames = backService.getFileSystemService().findAllDirectoryNames(File.separator);
		for (String userDirectory : userNames) {
			// On synchronise uniquement les albums de l'utilisateur connecté ou
			// ceux de l'étagère publique
			if (user.getLogin()!= null) {
				if (userDirectory.equals(user.getLogin())) {
					List<String> shelfNames = backService.getFileSystemService().findAllDirectoryNames(File.separator + userDirectory);
					for (String shelfName : shelfNames) {
							backService.synchronizeShelfWithDatabase(File.separator + userDirectory + File.separator + shelfName,
									shelfName, user);
					}
				}
			} else {
				break;
			}
		}

	}

	public void setBackService(SmartAlbumBackService backService) {
		this.backService = backService;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

}
