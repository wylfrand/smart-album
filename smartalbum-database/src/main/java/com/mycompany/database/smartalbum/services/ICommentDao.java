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
import com.mycompany.database.smartalbum.model.Comment;
import com.mycompany.database.smartalbum.model.User;

/**
 * Interface for manipulating with album entity
 *
 * @author Aristide M'vou
 */

public interface ICommentDao {

	void addImageComment(Comment comment) throws PhotoAlbumException;
	
	public void deleteImageComment(Comment comment) throws PhotoAlbumException;
	
	public void addAlbumComment(Comment comment) throws PhotoAlbumException;
	
	public List<Comment> findAllUserComments(User user) throws PhotoAlbumException;

	void deleteAlbumComment(Comment comment) throws PhotoAlbumException;

}