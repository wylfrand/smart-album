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
package com.mycompany.smartalbum.back.service.impl;
/**
 * Class encapsulated all functionality, related to drag'n'drop process.
 *
 * @author Aristide M'vou
 */

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mycompany.database.smartalbum.model.Album;
import com.mycompany.database.smartalbum.model.Image;
import com.mycompany.database.smartalbum.model.Shelf;
import com.mycompany.database.smartalbum.model.User;
import com.mycompany.database.smartalbum.utils.Constants;
import com.mycompany.smartalbum.back.service.SmartAlbumBackService;

@Component
public class DnDHelper {
    
    private final static transient Logger LOG = LoggerFactory
        .getLogger(DnDHelper.class);
    
    @Resource
    protected SmartAlbumBackService backService;

	
	/**
	 * Listenet, that invoked  during drag'n'drop process. Only registered users can drag images.
	 *
	 * @param event - event, indicated that drag'n'drop started
	 */
	public boolean processDrop(Object dragValue, Object dropValue) {
	    
	    User user = backService.getCurrentUser(false);
		
		if(dragValue instanceof Image){
			//If user drag image
			if(!((Album)dropValue).getOwner().getLogin().equals(user.getLogin())){
				//Drag in the album, that not belongs to user
				backService.getErrorHandler().addToErrors(Constants.ADD_ERROR_EVENT+"-"+Constants.DND_PHOTO_ERROR);
				LOG.error(Constants.ADD_ERROR_EVENT+"-"+Constants.DND_PHOTO_ERROR);
				return false;
			}
			return handleImage((Image)dragValue, (Album)dropValue);	
		}else if(dragValue instanceof Album){
			//If user drag album
			if(!((Shelf)dropValue).getOwner().getLogin().equals(user.getLogin())){
				//Drag in the shelf, that not belongs to user
				backService.getErrorHandler().addToErrors(Constants.ADD_ERROR_EVENT+"-"+Constants.DND_ALBUM_ERROR);
				LOG.error(Constants.ADD_ERROR_EVENT+"-"+Constants.DND_ALBUM_ERROR);
				return false;
			}
			return handleAlbum((Album)dragValue, (Shelf)dropValue);
		}
		return false;
	}

	private boolean handleAlbum(Album dragValue, Shelf dropValue) {
		String pathOld = dragValue.getPath();
		dropValue.addAlbum(dragValue);
		try{
		    backService.getAlbumDBService().editAlbum(dragValue);
		}catch(Exception e){
		    backService.getErrorHandler().addToErrors(Constants.ADD_ERROR_EVENT+"-"+Constants.ERROR_IN_DB);
		    LOG.error(Constants.ADD_ERROR_EVENT+"-"+Constants.ERROR_IN_DB, e);
			return false;
		}
		 LOG.debug(Constants.ALBUM_DRAGGED_EVENT+"-"+ dragValue +" : "+ pathOld);
		 
		 return true;
	}

	private boolean handleImage(Image dragValue, Album dropValue) {
		if(dragValue.getAlbum().equals(dropValue)){
		    LOG.warn(Constants.ADD_ERROR_EVENT+"-"+"Tentative de déplacer une image dans l'album qui la contient");
			return false;
		}
		String pathOld = dragValue.getFullPath();
		dropValue.addImage(dragValue);
		try{
		    backService.getAlbumDBService().editAlbum(dropValue);
		}catch(Exception e){
			backService.getErrorHandler().addToErrors(Constants.ADD_ERROR_EVENT+"-"+Constants.ERROR_IN_DB);
			LOG.error(Constants.ADD_ERROR_EVENT+"-"+Constants.ERROR_IN_DB,e);
			return false;
		}
		LOG.debug(Constants.IMAGE_DRAGGED_EVENT+"-"+ dragValue +" : "+ pathOld);
		return true;
	}
}
