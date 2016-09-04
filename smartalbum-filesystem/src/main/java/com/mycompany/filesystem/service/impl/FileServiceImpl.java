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
package com.mycompany.filesystem.service.impl;

/**
 * Class encapsulated all functionality, related to working with the file system.
 *
 * @author Aristide M'vou
 */
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mycompany.database.smartalbum.model.Album;
import com.mycompany.database.smartalbum.model.Image;
import com.mycompany.database.smartalbum.model.Shelf;
import com.mycompany.database.smartalbum.model.User;
import com.mycompany.filesystem.service.FileService;
import com.mycompany.filesystem.service.ImageDimension;
import com.mycompany.filesystem.utils.Constants;
import com.mycompany.filesystem.utils.FileUtils;

@Component
public class FileServiceImpl implements FileService {

	private File uploadRoot;
	
	@Value("${smartalbum.filesystem.upload.root.path}")
	private String uploadRootPath;

	@Value("${smartalbum.filesystem.upload.global.root.path}")
	private String tmpFile;
	
	/**
	 * Method, that invoked at startup application. Used to determine where
	 * application will be write new images. This method set uploadRoot field -
	 * it is reference to the file where images will be copied.
	 */
	@PostConstruct
	public void create() {
		uploadRoot = new File(this.uploadRootPath);
	}

	/**
	 * This method used to get reference to the file with the specified relative
	 * path to the uploadRoot field
	 * 
	 * @param path
	 *            - relative path of file
	 * @return File reference
	 */
	public File getFileByPathFromUploadRoot(String path) {
		
		if (this.uploadRoot != null && StringUtils.isNotBlank(path)) {
			File result = null;
			try {
				result = new File(this.uploadRoot, path);
				final String resultCanonicalPath = result.getCanonicalPath();
				if (!resultCanonicalPath.startsWith(this.uploadRootPath)) {
					result = null;
				}
				return result;
			} catch (IOException e) {
				result = null;
			}
			return result;
		}
		return null;
	}

	public File getFileByPathFromUploadRootTmp(String path) {
		if (this.tmpFile != null) {
			File result = null;
			try {
				result = new File(this.tmpFile, path);
				final String resultCanonicalPath = result.getCanonicalPath();
				if (!resultCanonicalPath.startsWith(this.tmpFile)) {
					result = null;
				}
				return result;
			} catch (IOException e) {
				result = null;
			}
			return result;
		}
		return null;
	}
	public File getFileByPathFromPlace(String path) {
		if (path != null) {
			File result = null;
				result = new File(path);
				return result;
		}
		return null;
	}

	/**
	 * This method observes <code>Constants.ALBUM_DELETED_EVENT</code> and
	 * invoked after the user delete album. This method delete album directory
	 * from the disk
	 * 
	 * @param album
	 *            - deleted album
	 * @param path
	 *            - relative path of the album directory
	 */
	public void onAlbumDeleted(Album album, String path) {
		deleteDirectory(path);
	}

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
	public void onShelfDeleted(Shelf shelf, String path) {
		deleteDirectory(path);
	}

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
	public void onUserDeleted(User user) {
		deleteDirectory(user.getPath());
	}

	/**
	 * This method observes <code>SHELF_ADDED_EVENT</code> and invoked after the
	 * user add new shelf This method add shelf directory to the disk
	 * 
	 * @param shelf
	 *            - added shelf
	 */
	public void onShelfAdded(Shelf shelf) {
		File directory = getFileByPathFromUploadRoot(shelf.getPath());
		FileUtils.addDirectory(directory);
	}

	/**
	 * This method observes <code>ALBUM_ADDED_EVENT</code> and invoked after the
	 * user add new album This method add album directory to the disk
	 * 
	 * @param album
	 *            - added album
	 */
	public void onAlbumAdded(Album album) {
		File directory = getFileByPathFromUploadRoot(album.getPath());
		if (directory!=null && !directory.exists()) {
			FileUtils.addDirectory(directory);
		}

	}

	/**
	 * This method invoked after user set new avatar icon
	 * 
	 * @param avatarData
	 *            - avatar file
	 * @param user
	 *            - user, that add avatar
	 */
	public boolean saveAvatar(File avatarData, User user) {
		String avatarPath = File.separator + user.getLogin() + File.separator
				+ Constants.AVATAR_JPG;
		createDirectoryIfNotExist(avatarPath);
		return writeFile(avatarPath, avatarData.getPath(), "",
				Constants.AVATAR_SIZE, true);
	}

	/**
	 * This method observes <code>Constants.IMAGE_DELETED_EVENT</code> and
	 * invoked after the user delete her image This method delete image and all
	 * thumbnails of this image from the disk
	 * @param path
	 *            - relative path of the image file
	 */
	public void deleteImage(String path, boolean existingAlbum) {
		if(existingAlbum)
		{
			for (ImageDimension d : ImageDimension.values()) {
				FileUtils.deleteFile(getFileByPathFromUploadRoot(transformPath(
						path, d.getFilePostfix())));
			}
		}
		else
		{
			for (ImageDimension d : ImageDimension.values()) {
				FileUtils.deleteFile(getFileByPathFromUploadRootTmp(transformPath(path, d.getFilePostfix())));
			}
		}
		
	}

	public void deleteImageFromTmp(String path) {
		FileUtils.deleteFile(getFileByPathFromUploadRootTmp(path));
	}

	/**
	 * This method invoked after user upload new image
	 * 
	 * @param fileName
	 *            - new relative path to the image file
	 * @param tempFilePath
	 *            - absolute path to uploaded image
	 */
	public boolean addImage(String fileName, String tempFilePath,
			boolean includeUploadRoot) {
		createDirectoryIfNotExist(tempFilePath);
		String normalizedImageName = normalize(fileName);
		for (ImageDimension d : ImageDimension.values()) {
			if (includeUploadRoot || !ImageDimension.ORIGINAL.equals(d)) {
				if (!writeFile(normalizedImageName, tempFilePath, d.getFilePostfix(),
						d.getX(), includeUploadRoot)) {
					return false;
				}
			}
		}
		
		// On vérifie si le fichier originale existe on le déplace le copie sinon
		final File file = new File(fileName);
		if (file!= null && !file.exists()) {
			if (!writeFile(normalizedImageName, tempFilePath, ImageDimension.ORIGINAL.getFilePostfix(),
					ImageDimension.ORIGINAL.getX(), includeUploadRoot)) {
				return false;
			}
		}
		
		return true;
	}
	
	private String normalize(String input) {
		String convertedString = Normalizer
				.normalize(input, Normalizer.Form.NFD)
				.replaceAll("[^\\p{ASCII}]", "").replaceAll(" ", "-");
		return convertedString;
	}

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
	public String transformPath(String target, String substitute) {
		if (target.length() < 2 || target.lastIndexOf(Constants.DOT) == -1) {
			return "";
		}

		if (StringUtils.isBlank(substitute)) {
			return target;
		} else {
			if (!substitute.startsWith("_")) {
				return target;
			}
		}

		final String begin = target.substring(0,
				target.lastIndexOf(Constants.DOT));
		final String end = target.substring(target.lastIndexOf(Constants.DOT));
		return begin + substitute + end;
	}

	/**
	 * This method used to get reference to the file with the absolute path
	 * 
	 * @param path
	 *            - absolute path of file
	 * @return File reference
	 */
	public File getFileByAbsolutePath(String path) {
		return new File(path);
	}

	/**
	 * This utility method used to determine if the directory with specified
	 * relative path exist
	 * 
	 * @param path
	 *            - absolute path of directory
	 * @return File reference
	 */
	public boolean isDirectoryPresent(String path) {
		final File file = getFileByPathFromUploadRoot(path);
		return file!=null && file.exists() && file.isDirectory();
	}

	/**
	 * This utility method used to determine if the file with specified relative
	 * path exist
	 * 
	 * @param path
	 *            - absolute path of file
	 * @return File reference
	 */
	public boolean isFilePresent(String path) {
		final File file = getFileByPathFromUploadRoot(path);
		return file!=null && file.exists();
	}

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
	public void renameAlbumDirectory(Album album, String pathOld) {
		File file = getFileByPathFromUploadRoot(pathOld);
		File file2 = getFileByPathFromUploadRoot(album.getPath());
		if (file2!= null && file2.exists()) {
			if (file2.isDirectory()) {
				FileUtils.deleteDirectory(file2, false);
			} else {
				FileUtils.deleteFile(file2);
			}
		}
		if(file!=null){
		file.renameTo(file2);
		}
	}
	
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
			final String pathOld) {
		File file = getFileByPathFromUploadRoot(pathOld);
		File file2 = getFileByPathFromUploadRoot(targetPath);
		if (file2!=null &&file2.exists()) {
			if (file2.isDirectory()) {
				FileUtils.deleteDirectory(file2, false);
			} else {
				FileUtils.deleteFile(file2);
			}
		}
		if(file!=null){
		file.renameTo(file2);
		}
	}

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
	@Override
	public void moveToUserDir(final String targetPath, final String pathOld) {
		File file = getFileByPathFromUploadRootTmp(pathOld);
		File file2 = getFileByPathFromUploadRootTmp(targetPath);
		if (file2.exists()) {
			if (file2.isDirectory()) {
				FileUtils.deleteDirectory(file2, false);
			} else {
				FileUtils.deleteFile(file2);
			}
		}
		file.renameTo(file2);
	}
	
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
	@Override
	public void moveFile(final String targetPath, final String pathOld) {
		File file = null;
		File file2 = null;
		for (ImageDimension dimension : ImageDimension.values()) {
			file = getFileByPathFromPlace(transformPath(pathOld,
					dimension.getFilePostfix()));
			file2 = getFileByPathFromPlace(transformPath(
					targetPath, dimension.getFilePostfix()));
		if (file2.exists()) {
			if (file2.isDirectory()) {
				FileUtils.deleteDirectory(file2, false);
			} else {
				FileUtils.deleteFile(file2);
			}
		}
		file.renameTo(file2);
		}
	}

	/**
	 * This method observes <code>Constants.IMAGE_DRAGGED_EVENT</code> and
	 * invoked after the user dragged image form one album to the another. This
	 * method rename image file and all thumbnails to the new name
	 * 
	 * @param image
	 *            - dragged image
	 * @param pathOld
	 *            - old path of image file
	 */
	public boolean renameImageFile(Image image, String pathOld) {
		File file = null;
		File file2 = null;
		boolean result = false;
		for (ImageDimension dimension : ImageDimension.values()) {
			file = getFileByPathFromUploadRoot(transformPath(pathOld,
					dimension.getFilePostfix()));
			file2 = getFileByPathFromUploadRoot(transformPath(
					image.getFullPath(), dimension.getFilePostfix()));
			if (file2!=null && file2.exists()) {
				if (file2.isDirectory()) {
					FileUtils.deleteDirectory(file2, false);
				} else {
					FileUtils.deleteFile(file2);
				}
			}
			File parent = file2.getParentFile();
			
			if(!parent.exists() && !parent.mkdirs())
			{
				throw new IllegalStateException("Impossible de créer le répertoire : "+parent);
			}
			if(file!=null){
			result = file.renameTo(file2);
			}
		}
		
		return result;
	}
	
	/**
	 * This method observes <code>Constants.IMAGE_DRAGGED_EVENT</code> and
	 * invoked after the user dragged image form one album to the another. This
	 * method rename image file and all thumbnails to the new name
	 * 
	 * @param image
	 *            - dragged image
	 * @param pathOld
	 *            - old path of image file
	 */
	public boolean renameImageFile(String newPath, String pathOld) {
		File file = null;
		File file2 = null;
		boolean result = false;
		for (ImageDimension dimension : ImageDimension.values()) {
			file = getFileByPathFromUploadRootTmp(transformPath(pathOld,
					dimension.getFilePostfix()));
			file2 = getFileByPathFromUploadRootTmp(transformPath(
					newPath, dimension.getFilePostfix()));
			if (file2.exists()) {
				if (file2.isDirectory()) {
					FileUtils.deleteDirectory(file2, false);
				} else {
					FileUtils.deleteFile(file2);
				}
			}
			File parent = file2.getParentFile();
			
			if(!parent.exists() && !parent.mkdirs())
			{
				throw new IllegalStateException("Impossible de créer le répertoire : "+parent);
			}
			result = file.renameTo(file2);
		}
		
		return result;
	}

	public synchronized boolean writeFile(String newFileName, String fileName,
			String pattern, int size, boolean includeUploadRoot) {
		BufferedImage bsrc = null;
		try {
			// Read file form disk
			bsrc = FileUtils.bitmapToImage(normalize(fileName), Constants.JPG);
		} catch (IOException e1) {
			return false;
		}
		int resizedParam = bsrc.getWidth() > bsrc.getHeight() ? bsrc.getWidth()
				: bsrc.getHeight();
		double scale = (double) size / resizedParam;
		Double widthInDouble = ((Double) scale * bsrc.getWidth());
		int width = widthInDouble.intValue();
		Double heightInDouble = ((Double) scale * bsrc.getHeight());
		int height = heightInDouble.intValue();
		// Too small picture or original size
		if (width > bsrc.getWidth() || height > bsrc.getHeight() || size == 0) {
			width = bsrc.getWidth();
			height = bsrc.getHeight();
		}
		// scale image if need
		BufferedImage bdest = FileUtils.getScaledInstance(bsrc, width, height,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC, true);
		// Determine new path of image file
		String dest =  transformPath(newFileName, pattern);
		String newFileAbsolutePath = computeNewFileAbsolutePath(dest,pattern);
		
		try {
			// save to disk
			FileUtils.imageToBitmap(bdest, newFileAbsolutePath, Constants.JPG);
		} catch (IOException ex) {
			return false;
		}
		return true;
	}

	public void deleteDirectory(String directory) {
		final File file = getFileByPathFromUploadRoot(directory);
		if(file!= null){
			FileUtils.deleteDirectory(file, false);
		}
	}

	@Override
	public void createDirectoryIfNotExist(String fileNameNew) {
		final int lastIndexOf = fileNameNew.lastIndexOf(File.separator);
		if (lastIndexOf > 0) {
			final String directory = fileNameNew.substring(0, lastIndexOf);
			final File file = getFileByPathFromUploadRoot(directory);
			if (file!= null && !file.exists()) {
				file.mkdirs();
			}
		}
	}

	@Override
	public boolean createTmpDirectoryForUser(String userlogin) {
		File result = null;
		try {
			// Create the tmp file
			result = new File(this.tmpFile, userlogin);
			final String resultCanonicalPath = result.getCanonicalPath();
			if (!resultCanonicalPath.startsWith(this.tmpFile)) {
				result = null;
			}
			// Create the light tmp file
		} catch (IOException e) {
			result = null;
		}
		if (result == null)
			return false;
		if (!result.exists()) {
			result.mkdirs();
			return true;
		}
		return false;
	}

	/**
	 * This method finds all the albums of a given shelf This method add shelf
	 * directory to the disk
	 * 
	 * @param shelf
	 *            - added shelf
	 */
	public List<String> findAllDirectoryNames(String absolutePath) {
		File directory = getFileByPathFromUploadRoot(absolutePath);
		List<String> result = new ArrayList<>();
		if (directory!= null && directory.isDirectory()) {
			result = Arrays.asList(directory.list(FileUtils.directoryFilter));
		}
		return result;

	}

	public boolean isDirectoryEmpty(String directory) {
		final File file = getFileByPathFromUploadRoot(directory);
		boolean result = true;
		if (file!= null && file.exists() && file.isDirectory()) {
			String[] names = file.list(FileUtils.imageFilter);
			if (names.length > 2) {
				result = false;
			} else {
				if (names.length >= 1) {
					if (!names[0].equals(".DS_Store")
							&& !names[0].equals(".svn")) {
						result = false;
					}
				}
			}
		}
		return result;
	}
	
	protected String computeNewFileAbsolutePath(final String absoluteFilePath, final String pattern)
	{
		int count = 1;
		File newFullPath = new File(absoluteFilePath);
		String fileNameOnly = FilenameUtils.removeExtension(newFullPath.getName());
		fileNameOnly = fileNameOnly.replace(pattern, "");
		String extension = FilenameUtils.getExtension(newFullPath.getName());
		
		while(newFullPath.exists()) 
		{
			String newFileName = fileNameOnly+"-"+count+++pattern;
		    newFullPath = new File(newFullPath.getParent()+File.separator+newFileName+"."+extension);
		}
		return newFullPath.getAbsolutePath();
	}
}
