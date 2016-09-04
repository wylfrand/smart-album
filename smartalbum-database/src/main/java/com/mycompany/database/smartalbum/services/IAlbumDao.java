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
package com.mycompany.database.smartalbum.services;

import java.util.List;

import com.mycompany.database.smartalbum.exception.PhotoAlbumException;
import com.mycompany.database.smartalbum.model.Album;

/**
 * Interface for manipulating with album entity
 *
 * @author Aristide M'vou
 */

public interface IAlbumDao {

	void addAlbum(Album album) throws PhotoAlbumException;
	
	void deleteAlbum(Album album) throws PhotoAlbumException;
	
	void editAlbum(Album album) throws PhotoAlbumException;
	
	public void print();
	
	public Album findAlbumById(Long albumId);
	
	public List<Album> findAll();

}