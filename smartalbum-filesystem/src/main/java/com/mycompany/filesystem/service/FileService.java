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
package com.mycompany.filesystem.service;

/**
 * Class encapsulated all functionality, related to working with the file system.
 *
 * @author Aristide M'vou
 */
import java.io.File;
import java.util.List;

import com.mycompany.database.smartalbum.model.Album;
import com.mycompany.database.smartalbum.model.Image;
import com.mycompany.database.smartalbum.model.Shelf;
import com.mycompany.database.smartalbum.model.User;

public interface FileService {

	/**
	 * Method, that invoked at startup application. Used to determine where
	 * application will be write new images. This method set uploadRoot field -
	 * it is reference to the file where images will be copied.
	 */
	public void create();

	/**
	 * This method used to get reference to the file with the specified relative
	 * path to the uploadRoot field
	 * 
	 * @param path
	 *            - relative path of file
	 * @param existingAlbum TODO
	 * @return File reference
	 */
	public File getFileByPathFromUploadRoot(String path);

	/**
	 * This method observes <code>Constants.ALBUM_DELETED_EVENT</code> and
	 * invoked after the user delete album. This method delete album directory
	 * from the disk
	 * 
	 * @param album
	 *            - deleted album
	 * @param path
	 *            - relative path of the album directory
	 * 
	 */
	public void onAlbumDeleted(Album album, String path);

	/**
	 * This method observes <code>Constants.SHELF_DELETED_EVENT</code> and
	 * invoked after the user delete her shelf This method delete shelf
	 * directory from the disk
	 * 
	 * @param shelf
	 *            - deleted shelf
	 * @param path
	 *            - relative path of the shelf directory
	 */
	public void onShelfDeleted(Shelf shelf, String path);

	/**
	 * This method observes <code>Constants.USER_DELETED_EVENT</code> and
	 * invoked after the user was deleted(used in livedemo to prevent flooding)
	 * This method delete user directory from the disk
	 * 
	 * @param user
	 *            - deleted user
	 * @param path
	 *            - relative path of the user directory
	 */
	public void onUserDeleted(User user);

	/**
	 * This method observes <code>SHELF_ADDED_EVENT</code> and invoked after the
	 * user add new shelf This method add shelf directory to the disk
	 * 
	 * @param shelf
	 *            - added shelf
	 */
	public void onShelfAdded(Shelf shelf);

	/**
	 * This method observes <code>ALBUM_ADDED_EVENT</code> and invoked after the
	 * user add new album This method add album directory to the disk
	 * 
	 * @param album
	 *            - added album
	 */
	public void onAlbumAdded(Album album);

	/**
	 * This method invoked after user set new avatar icon
	 * 
	 * @param avatarData
	 *            - avatar file
	 * @param user
	 *            - user, that add avatar
	 */
	public boolean saveAvatar(File avatarData, User user);

	/**
	 * This method observes <code>Constants.IMAGE_DELETED_EVENT</code> and
	 * invoked after the user delete her image This method delete image and all
	 * thumbnails of this image from the disk
	 * @param path
	 *            - relative path of the image file
	 * @param existingAlbum TODO
	 */
	public void deleteImage(String path, boolean existingAlbum);
	
	public void deleteImageFromTmp(String path);

	/**
	 * This method invoked after user upload new image
	 * 
	 * @param fileName
	 *            - new relative path to the image file
	 * @param tempFilePath
	 *            - absolute path to uploaded image
	 * @param includeUploadRoot TODO
	 */
	public boolean addImage(final String fileName, final String tempFilePath, boolean includeUploadRoot);

	/**
	 * This method used to transform one path to another. For example you want
	 * get path of the file with dimensioms 80 of image with path
	 * /user/1/2/image.jpg, this method return /user/1/2/image_substitute.jpg
	 * 
	 * @param target
	 *            - path to transform
	 * @param substitute
	 *            - new 'addon' to the path
	 */
	public String transformPath(String target, String substitute);

	/**
	 * This method used to get reference to the file with the absolute path
	 * 
	 * @param path
	 *            - absolute path of file
	 * @return File reference
	 */
	public File getFileByAbsolutePath(String path);

	/**
	 * This utility method used to determine if the directory with specified
	 * relative path exist
	 * 
	 * @param path
	 *            - absolute path of directory
	 * @return File reference
	 */
	public boolean isDirectoryPresent(String path);

	/**
	 * This utility method used to determine if the file with specified relative
	 * path exist
	 * 
	 * @param path
	 *            - absolute path of file
	 * @return File reference
	 */
	public boolean isFilePresent(String path);

	/**
	 * This method observes <code>Constants.ALBUM_DRAGGED_EVENT</code> and
	 * invoked after the user dragged album form one shelf to the another. This
	 * method rename album directory to the new directory
	 * 
	 * @param album
	 *            - dragged album
	 * @param pathOld
	 *            - old path of album directory
	 */
	public void renameAlbumDirectory(Album album, String pathOld);

	/**
	 * This method observes <code>Constants.ALBUM_RENAME_EVENT</code> and
	 * invoked after the user dragged album form one shelf to the another. This
	 * method rename album directory to the new directory
	 * 
	 * @param targetPath
	 *            - dragged album
	 * @param pathOld
	 *            - old path of album directory
	 */
	public void renameAlbumDirectory(final String targetPath,
			final String pathOld);

	/**
	 * This method observes <code>Constants.IMAGE_DRAGGED_EVENT</code> and
	 * invoked after the user dragged image form one album to the another. This
	 * method rename image file and all thumbnails to the new name
	 * 
	 * @param image
	 *            - dragged image
	 * @param pathOld
	 *            - old path of image file
	 * @return 
	 */
	public boolean renameImageFile(Image image, String pathOld);
	
	public boolean renameImageFile(String newPath, String pathOld);

	public boolean writeFile(String newFileName, String fileName,
			String pattern, int size, boolean includeUploadRoot);

	public void deleteDirectory(String directory);

	public void createDirectoryIfNotExist(String fileNameNew);

	public List<String> findAllDirectoryNames(String shelfAbsolutePath);

	public boolean isDirectoryEmpty(String directory);

	public boolean createTmpDirectoryForUser(String userlogin);

	public void moveToUserDir(final String targetPath, final String pathOld);
	
	public File getFileByPathFromUploadRootTmp(String path);

	void moveFile(String targetPath, String pathOld);
}