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
import com.mycompany.database.smartalbum.search.vo.ImageResume;
import com.mycompany.database.smartalbum.search.vo.SearchDataTableRequest;
import com.mycompany.database.smartalbum.vo.DataTableEntity;

/**
 * Interface for manipulating with image entity
 *
 * @author Aristide M'vou
 */

public interface IImageDao {

	public void deleteImage(Image image) throws PhotoAlbumException;

	public void addImage(Image image) throws PhotoAlbumException;
	
	public boolean isImageWithThisPathExist(Album album, String path);
	
	public Long getCountIdenticalImages(Album album, String path);

	public  Image findImageById(final Long id);
	
	public void print();
	
	 public List<Image> findAll();

	public Image findImageByNameAndUser(String imageName, Long id);

	public Image findImageByNameAndAlbumId(String imageName, Long id);

	public DataTableEntity<Image> findImagesBySearchRequest(SearchDataTableRequest request, boolean shouldSearchInUser);

	public void addImageInUserTmp(Image image);

	public void updateImage(Image image) throws PhotoAlbumException;
	
	void deleteAllImages(List<Image> imagesToDelete);
	
	public List<String> findAllImageNamesByAlbumId(final Long id);
	
	public List<ImageResume> findAllImageResumeByAlbumId(final Long id);

}