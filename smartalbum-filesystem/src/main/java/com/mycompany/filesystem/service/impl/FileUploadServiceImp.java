/**
 * License Agreement.
 *
 * Rich Faces - Natural Ajax for Java Server Faces (JSF)
 *
 * Copyright (C) 2007 Exadel, Inc.
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

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.Normalizer;
import java.util.Date;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;
import com.drew.metadata.jpeg.JpegDirectory;
import com.mycompany.database.smartalbum.exception.PhotoAlbumException;
import com.mycompany.database.smartalbum.model.Image;
import com.mycompany.database.smartalbum.services.IImageDao;
import com.mycompany.filesystem.model.FileMeta;
import com.mycompany.filesystem.model.FileWrapper;
import com.mycompany.filesystem.model.Model;
import com.mycompany.filesystem.service.FileService;
import com.mycompany.filesystem.service.FileUploadService;
import com.mycompany.filesystem.service.ImageDimension;
import com.mycompany.filesystem.utils.Constants;
import com.mycompany.filesystem.utils.FileFilter;
import com.mycompany.filesystem.utils.ImageUtils;
import com.mycompany.filesystem.utils.PictureMetaData;
import com.mycompany.filesystem.utils.UploadedFile;

/**
 * Class encapsulated all functionality, related to file-upload process.
 * 
 * @author amv
 */
@Component
public class FileUploadServiceImp implements FileUploadService {

	private final static transient Logger LOG = LoggerFactory
			.getLogger(FileUploadServiceImp.class);

	@Resource
	private IImageDao imageDBService;

	FileWrapper fileWrapper;

	Model model;

	@Resource
	private FileService fileManager;

	
	public String generateNewPath(Image image) throws PhotoAlbumException {
		String path = image.getPath().substring(0,
				image.getPath().lastIndexOf(Constants.DOT));
		Long countCopies = imageDBService.getCountIdenticalImages(
				image.getAlbum(), path) + 1;
		String newPath = fileManager.transformPath(image.getPath(), "_"
				+ countCopies);
		return newPath;
	}

	public void addError(UploadedFile item, Image image, String error) {
		fileWrapper.onFileUploadError(image, error);
		try {
			item.delete();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addError(Image image, String error) {
		fileWrapper.onFileUploadError(image, error);
	}

	public Image constructImage(FileMeta item) {

		Image image = null;
		try {
			image = new Image();
			image.setUploaded(new Date());
			image.setCreated(new Date());
			image.setDescription(item.getDescription() == null ? "Image en construction"
					: item.getDescription());
			image.setName(item.getFileName());
			if(item.getBytes() != null){
				image.setSize(item.getBytes().length/ 1024);
			}
			image.setPath(item.getFileName());
			image.setAllowComments(true);
			image.setHeight(item.getHeight());
			image.setWidth(item.getWidth());
		} catch (Exception e) {
			if (item != null) {
				LOG.error("Impossible de construire l'image BDD "
						+ item.getFileName());
			} else {
				LOG.error("Impossible de construire l'image BDD null");
			}
		}
		return image;
	}

	public String extractMetadata(FileMeta item, Image image) {
		InputStream in = null;
		try {
			in = new ByteArrayInputStream(item.getBytes());
			Metadata metadata = JpegMetadataReader.readMetadata(in);
			Directory exifIFD0Directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
	        Directory exifSubIFDDirectory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
	        Directory jpgDirectory = metadata.getFirstDirectoryOfType(JpegDirectory.class);
	        if (exifIFD0Directory != null) {
	            setupCameraModel(image, exifIFD0Directory);
	        }
	        if (exifSubIFDDirectory != null) {
	            setupCreatedDate(image, exifSubIFDDirectory,metadata);
	        }
	        else{
	        	image.setCreated(new Date());
	        }
	        if (jpgDirectory != null || exifSubIFDDirectory!=null) {
	            setupDimensions(image, exifSubIFDDirectory, jpgDirectory);
	        }
	        if(item.getBytes()!=null){
	        	GeoLocation geoLoc = calculateGeolocationFromFile(item.getBytes());
	        	if(geoLoc!=null){
	        		LOG.debug(geoLoc.toDMSString());
	        	}
	        }
			return Constants.OK;
		} catch (Exception e) {
			return Constants.IMAGE_SAVING_ERROR;
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				return Constants.IMAGE_SAVING_ERROR;
			}
		}
	}

	public void setupCreatedDate(final Image image, Directory exifDirectory,final Metadata metadata)
			throws MetadataException {
		Date date = null;
		
		if (date == null) {
			date = exifDirectory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
		}

		if (date == null) {
			date = exifDirectory.getDate(ExifSubIFDDirectory.TAG_DATETIME_DIGITIZED);
		}

		if (date == null) {
			final ExifIFD0Directory exifDir = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
			date = exifDir.getDate(ExifIFD0Directory.TAG_DATETIME);
		}

		if (date == null) {
			date = new Date();
		} 
		image.setCreated(date);
	}

	public void setupDimensions(Image image, Directory exifDirectory,
			Directory jpgDirectory) {
		try {
			if (exifDirectory != null && exifDirectory.containsTag(ExifSubIFDDirectory.TAG_EXIF_IMAGE_WIDTH)
					&& exifDirectory
							.containsTag(ExifSubIFDDirectory.TAG_EXIF_IMAGE_HEIGHT)) {
				int width = exifDirectory
						.getInt(ExifSubIFDDirectory.TAG_EXIF_IMAGE_WIDTH);
				image.setWidth(width);
				int height = exifDirectory
						.getInt(ExifSubIFDDirectory.TAG_EXIF_IMAGE_HEIGHT);
				image.setHeight(height);
			} else {
				if (jpgDirectory
						.containsTag(JpegDirectory.TAG_IMAGE_HEIGHT)) {
					int width = jpgDirectory
							.getInt(JpegDirectory.TAG_IMAGE_WIDTH);
					image.setWidth(width);
					int height = jpgDirectory
							.getInt(JpegDirectory.TAG_IMAGE_HEIGHT);
					image.setHeight(height);
				}
			}
		} catch (MetadataException e) {
			addError(image, Constants.IMAGE_SAVING_ERROR);
		}
	}
	
	public void setupCameraModel(Image image, Directory exifDirectory) {
		if (exifDirectory.containsTag(ExifDirectoryBase.TAG_MODEL)) {
			String cameraModel = exifDirectory
					.getString(ExifDirectoryBase.TAG_MODEL);
			image.setCameraModel(cameraModel);
		} else {
			image.setCameraModel("");
		}
	}

	public synchronized FileMeta computeFileMetaBeforeSavingOriginal(MultipartFile mpf, String userLogin,
			boolean bufferImg) {
		FileMeta fileMeta = new FileMeta();
		fileMeta.setFileSize(mpf.getSize() / 1024 + " Kb");
		fileMeta.setFileType(mpf.getContentType());
		try {
			PictureMetaData pictMeta = ImageUtils.getMetaData(mpf);
			BufferedImage reducedImg = ImageUtils.shrink(
					ImageUtils.getImage(mpf), pictMeta.getWidth(),
					pictMeta.getHeight());
			String fileName = ImageUtils.writeJpeg(reducedImg, new File(Constants.TMP_DIR
					+ File.separator + userLogin + File.separator
					+ normalize(mpf.getOriginalFilename())), 350, pictMeta);
			// On ecrit le nom du fichier tel qu'il a ete normalisé et on le renomme suivant qu'un fichier ayant le même non existe ou pas.
			fileMeta.setFileName(fileName);
			
			// On va afficher le fichier reduit
  			fileMeta.setDimension(pictMeta.getWidth() + "X"+ pictMeta.getHeight());
  			fileMeta.setHeight(pictMeta.getHeight());
  			fileMeta.setWidth(pictMeta.getWidth());
			// On rajoute l'image avec sa taille réduite pour les fichiers d'une
			// seule page sinon ça devient trop lourd
				if(bufferImg){
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ImageIO.write(reducedImg, "jpg", baos);
					fileMeta.setBytes(baos.toByteArray());
					baos.flush();
					baos.close();
				}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOG.error("Impossible de sauvegarder le fichier sur le fileSystem",
					e);
		} finally {
		}
		return fileMeta;
	}
	
	private String normalize(String input) {
		String convertedString = Normalizer
				.normalize(input, Normalizer.Form.NFD)
				.replaceAll("[^\\p{ASCII}]", "").replaceAll(" ", "-");
		return convertedString;
	}

	public FileMeta getFsFileByPath(String absoluteFilePath, String relativePath) {
		File currentFile = new File(absoluteFilePath);
		FileMeta fileMeta = new FileMeta();
		fileMeta.setAbsoluteFilePath(absoluteFilePath);
		fileMeta.setRelativePath(relativePath);

		if (currentFile.isFile()) {
			FileFilter filter = new FileFilter();
			fileMeta.setFileName(filter.getNameWithoutExtension(currentFile.getName()));
			fileMeta.setFileSize(currentFile.length() / 1024 + " Kb");
			fileMeta.setFullPath(fileMeta.getFileName());

			InputStream is;
			String mimeType;
			try {
				is = new BufferedInputStream(new FileInputStream(currentFile));
				mimeType = URLConnection.guessContentTypeFromStream(is);
				is.close();
				fileMeta.setFileType(mimeType);
				byte[] bFile = new byte[(int) currentFile.length()];

				// convert file into array of bytes
				FileInputStream fileInputStream = new FileInputStream(
						currentFile);
				fileInputStream.read(bFile);
				fileInputStream.close();
				//fileMeta.setBytes(bFile);

				PictureMetaData pictMeta = ImageUtils.getMetaData(bFile);

				fileMeta.setDimension(pictMeta.getWidth() + " X "
						+ pictMeta.getHeight());
				fileMeta.setWidth(pictMeta.getWidth());
				fileMeta.setHeight(pictMeta.getHeight());
				fileMeta.setFileSize(pictMeta.getSize()+"");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fileMeta;
	}

	public FileMeta getFsFile(final String absoluteFilePath,
			final String substitute, String relativePath) {
		FileFilter filter = new FileFilter();
		String original_absoluteFilePath = filter.getNameWithoutExtension(absoluteFilePath);
		String path = fileManager.transformPath(original_absoluteFilePath, substitute);
		File currentFile = new File(path);
		String fileName =  StringUtils.isNotBlank(original_absoluteFilePath)  ?original_absoluteFilePath.substring(original_absoluteFilePath.lastIndexOf(File.separator)+1):StringUtils.EMPTY;
		
		FileMeta fileMeta = new FileMeta();
		fileMeta.setAbsoluteFilePath(path);
		fileMeta.setRelativePath(fileName);
		fileMeta.setFileName(fileName);
		if (currentFile.isFile()) {
			updateFileMeta(fileMeta, currentFile);
		}
		return fileMeta;
	}

	private void updateFileMeta(FileMeta fileMeta, File currentFile) {
		InputStream is;
		String mimeType;

		fileMeta.setFileName(currentFile.getName());
		fileMeta.setFullPath(currentFile.getName());
		fileMeta.setFileSize(currentFile.length() / 1024 + " Kb");

		try {
			is = new BufferedInputStream(new FileInputStream(currentFile));
			mimeType = URLConnection.guessContentTypeFromStream(is);
			is.close();
			fileMeta.setFileType(mimeType);
			byte[] bFile = new byte[(int) currentFile.length()];

			// convert file into array of bytes
			FileInputStream fileInputStream = new FileInputStream(currentFile);
			fileInputStream.read(bFile);
			fileInputStream.close();
			fileMeta.setBytes(bFile);

			PictureMetaData pictMeta = ImageUtils.getMetaData(bFile);

			fileMeta.setDimension(pictMeta.getWidth() + "X"
					+ pictMeta.getHeight());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Permet de récupérer une image connaissant sa page et l'index dans la page
	 * L'emplacement cible cible correspond à tmp.list[k] k étant la position
	 * absolue calculée.
	 * 
	 * @param directory
	 * @param fileName
	 *            La taille d'une page
	 * @return Méta données du fichier recherché ou null si le fichier n'existe
	 *         pas
	 */
	public FileMeta getFileFromFs(final String directory, String fileName,
			ImageDimension dimension) {
		
		File tmp = new File(directory);
		FileMeta fileMeta = null;

		if (tmp != null) {
			fileMeta = new FileMeta();
			String fileAbsolutePath = directory + File.separator + fileName;
			fileAbsolutePath = fileAbsolutePath.replaceAll("//", File.separator);
			fileMeta.setAbsoluteFilePath(fileAbsolutePath);
			File currentFile = new File(fileAbsolutePath);
			if (currentFile.isFile()) {
				updateFileMeta(fileMeta, currentFile);
			}
		}
		return fileMeta;
	}

	public Image getImageFileByPath(String absoluteFilePath, String relativePath) {
		return constructImage(getFsFileByPath(absoluteFilePath, relativePath));
	}
	
	protected static void print(com.drew.metadata.Metadata metadata) {
		LOG.debug("-------------------------------------");
		// A Metadata object contains multiple Directory objects
		//
		for (Directory directory : metadata.getDirectories()) {
			//
			// Each Directory stores values in Tag objects
			//
			for (Tag tag : directory.getTags()) {
				LOG.debug(tag.toString());
			}

			//
			// Each Directory may also contain error messages
			//
			if (directory.hasErrors()) {
				for (String error : directory.getErrors()) {
					LOG.error("ERROR: " + error);
				}
			}
		}
	}
	
	public GeoLocation calculateGeolocationFromFile(final byte[] decodedBytes) {
		GeoLocation location = null;
		try {
			InputStream is = new ByteArrayInputStream(decodedBytes);
			Metadata metadata = ImageMetadataReader.readMetadata(is);
			GpsDirectory gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory.class);
			if(gpsDirectory!=null){
				location = gpsDirectory.getGeoLocation();
			}
		} catch (ImageProcessingException e) {
			LOG.error("Imposible de lire la géolocalisation de l'image",e);
		} catch (IOException e) {
			LOG.error("Imposible de lire la géolocalisation de l'image",e);
		}
		return location;
	}
}
