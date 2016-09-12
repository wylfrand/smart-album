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
import com.mycompany.database.smartalbum.model.Shelf;
/**
 * Interface for manipulating with shelf entity
 *
 * @author Aristide M'vou
 */

public interface IShelfDao {

	void addShelf(Shelf shelf) throws PhotoAlbumException;
	
	void deleteShelf(Shelf shelf) throws PhotoAlbumException;
	
	void editShelf(Shelf shelf) throws PhotoAlbumException;
	
	List<Shelf> getPredefinedShelves();
	
	List<Shelf> getUserShelves(final Long userId);

	List<Shelf> findAll();
	
	Shelf findShelfById(Long shelfId);

	Shelf findShelfByName(String string);
}
