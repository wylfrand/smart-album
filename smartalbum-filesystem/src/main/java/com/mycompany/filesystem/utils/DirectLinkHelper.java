///**
// * License Agreement.
// *
// * Rich Faces - Natural Ajax for Java Server Faces (JSF)
// *
// * Copyright (C) 2007 Exadel, Inc.
// *
// * This library is free software; you can redistribute it and/or
// * modify it under the terms of the GNU Lesser General Public
// * License version 2.1 as published by the Free Software Foundation.
// *
// * This library is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// * Lesser General Public License for more details.
// *
// * You should have received a copy of the GNU Lesser General Public
// * License along with this library; if not, write to the Free Software
// * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
// */
//package com.mycompany.filesystem.utils;
//
//import java.io.IOException;
//import java.io.OutputStream;
//import java.security.Identity;
//
//import sun.security.krb5.Credentials;
//
//import com.mycompany.database.smartalbum.model.Image;
//import com.mycompany.filesystem.service.ImageLoader;
///**
// * Convenience UI class for 'directLink' functionality.
// *
// * @author Andrey Markhel
// */
//public class DirectLinkHelper {
// 
//	
//	ImageLoader imageLoader;
//	
//	Identity identity;
//	
//	Credentials credentials;
//	/**
//	 * Convenience method to paint full-sized image in new tab or window
//	 *
//	 * @param out - OutputStream to write image
//	 * @param data - relative path of the image
//	 */
//	public void paintImage(OutputStream out, Object data)
//			throws IOException {
//		Long id = Long.valueOf(data.toString());
//		Image im = null;//em.find(Image.class, id);
//		if(isImageRecentlyRemoved(im)){
//			imageLoader.paintImage(out, Constants.DEFAULT_PICTURE);
//			return;
//		}
//		if(isImageSharedOrBelongsToUser(im)){
//			imageLoader.paintImage(out, im.getFullPath());
//		}else{
//			return;
//		}
//	}
//
//	private boolean isImageSharedOrBelongsToUser(Image im) {
//		//return im.getAlbum().getShelf().isShared() || (identity.hasRole(Constants.ADMIN_ROLE) && im.getAlbum().getOwner().getLogin().equals(credentials.getUsername()));
//		return im.getAlbum().getShelf().isShared();
//	}
//
//	private boolean isImageRecentlyRemoved(Image im) {
//		return im == null || im.getAlbum() == null || im.getAlbum().getShelf() == null;
//	}
//}