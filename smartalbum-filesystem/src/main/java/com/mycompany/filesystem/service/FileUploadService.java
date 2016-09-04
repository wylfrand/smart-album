package com.mycompany.filesystem.service;

import org.springframework.web.multipart.MultipartFile;

import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.mycompany.database.smartalbum.exception.PhotoAlbumException;
import com.mycompany.database.smartalbum.model.Image;
import com.mycompany.filesystem.model.FileMeta;
import com.mycompany.filesystem.utils.UploadedFile;

public interface FileUploadService {
	
	public String generateNewPath(Image image) throws PhotoAlbumException;

	public void addError(UploadedFile item, Image image, String error); 
	
	public void addError(Image image, String error);
	
	public Image constructImage(FileMeta item) ;
	
	public String extractMetadata(FileMeta item, Image image);

	public void setupCreatedDate(Image image, Directory exifDirectory,final Metadata metadata) throws MetadataException ;

	public void setupDimensions(Image image, Directory exifDirectory, Directory jpgDirectory);

	public void setupCameraModel(Image image, Directory exifDirectory) ;
	
	public FileMeta computeFileMetaBeforeSavingOriginal(MultipartFile mpf, String userLogin, boolean bufferImg);

	public FileMeta getFsFileByPath(String absoluteFilePath, String relativePath);
	
	public Image getImageFileByPath(String absoluteFilePath, String relativePath);
	
	public FileMeta getFsFile(final String absoluteFilePath,final String substitute, String relativePath);
	
	
	
	/**
	 * Permet de récupérer une image connaissant sa page et l'index dans la page
	 * L'emplacement cible cible correspond à tmp.list[k] k étant la position absolue calculée.
	 * @param directory
	 * @param fileName La taille d'une page
	 * @param dimension TODO
	 * @return Méta données du fichier recherché ou null si le fichier n'existe pas
	 */
	public FileMeta getFileFromFs(final String directory, String fileName, ImageDimension dimension);
}
