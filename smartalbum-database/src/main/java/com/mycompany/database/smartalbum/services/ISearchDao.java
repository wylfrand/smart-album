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
import com.mycompany.database.smartalbum.model.Image;
import com.mycompany.database.smartalbum.model.MetaTag;
import com.mycompany.database.smartalbum.model.Shelf;
import com.mycompany.database.smartalbum.model.User;

/**
 * Interface for search actions
 *
 * @author Aristide M'vou
 */
public interface ISearchDao {
	public List<Image> searchByImage(String query, boolean searchInMyAlbums, boolean searchInShared,User user) throws PhotoAlbumException;
	
	public List<MetaTag> searchByTags(String query, boolean searchInMyAlbums, boolean searchInShared,User user) throws PhotoAlbumException;
	
	public List<Album> searchByAlbum(String query, boolean searchInMyAlbums, boolean searchInShared,User user) throws PhotoAlbumException;
	
	public List<User> searchByUsers(String query, boolean searchInMyAlbums, boolean searchInShared,User user) throws PhotoAlbumException;
	
	public List<Shelf> searchByShelves(String query,boolean searchInMyAlbums, boolean searchInShared,User user) throws PhotoAlbumException;
	
}
