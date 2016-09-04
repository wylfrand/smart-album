package com.mycompany.filesystem.model;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import com.mycompany.filesystem.utils.Constants;
import com.mycompany.filesystem.utils.FileUtils;



/**
 * Utility class, that perform copying images from uploadRootPath  file to temp folder at startup application
 * 
 * @author Aristide Mvou
 * 
 **/

public class CopyImageStuff {

	private File uploadRoot;

	private String uploadRootPath;
	
	private String imageSrc;
	
	/**
	 * Method, that perform copying images from ear file to temp folder at startup application
	 *
	 */

public void create() throws IOException {
	resolveImageFolder();
	resolveUploadRoot();
	copyImages();
}
	
	/**
	 * Method, that perform deleting images from temp folder during destroy application
	 *
	 */
	public void destroy()throws IOException {
		FileUtils.deleteDirectory(uploadRoot, true);
	}
	
	private void resolveImageFolder() throws MalformedURLException {
		
		this.imageSrc = getClass().getClassLoader().getResource(Constants.IMAGE_FOLDER).getPath();
		if(this.imageSrc == null)
		{
			throw new IllegalStateException(Constants.UPLOAD_FOLDER_PATH_ERROR);
		}

	}
	
	private void resolveUploadRoot() throws IOException {
		uploadRoot = new File(FileUtils.joinFiles(
				System.getProperty(Constants.TEMP_DIR), Constants.PHOTOALBUM_FOLDER));

		if (uploadRoot.exists()) {
			FileUtils.deleteDirectory(uploadRoot, true);
		}

		uploadRoot.mkdir();

		uploadRootPath = uploadRoot.getCanonicalPath();
	}
	
	private void copyImages() {
		try {
			FileUtils.copyDirectory(new File(imageSrc), uploadRoot);
		} catch (IOException e) {
			System.out.println("ERROR on copy '"+imageSrc+"' to '"+uploadRoot+ '\'');
		}
	}
}