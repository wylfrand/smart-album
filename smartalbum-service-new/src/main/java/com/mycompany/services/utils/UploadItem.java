package com.mycompany.services.utils;

import java.io.Serializable;

import org.springframework.web.multipart.commons.CommonsMultipartFile;


public class UploadItem implements Serializable{

	private static final long serialVersionUID = -6936434414564507865L;
	private String filename;
	private CommonsMultipartFile fileData;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public CommonsMultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}

}