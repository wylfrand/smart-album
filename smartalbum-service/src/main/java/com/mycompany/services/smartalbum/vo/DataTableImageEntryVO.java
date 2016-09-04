package com.mycompany.services.smartalbum.vo;

import java.io.Serializable;

public class DataTableImageEntryVO extends DataTableEntryVO implements Serializable{
	private static final long serialVersionUID = -7042878411608396483L;

	private String image;

	private String fileName;

	private String fileSize;

	private String fileDimension;
	
	private String action;
	
	private int imageIndex;
	
	private boolean checkedInDataTable;
	
	private String baseUrl;
	
	private String blogEntry;
	
	private String sliderEntry;
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileDimension() {
		return fileDimension;
	}

	public void setFileDimension(String fileDimension) {
		this.fileDimension = fileDimension;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public DataTableEnumType getDataTableType() {
		return DataTableEnumType.IMAGE;
	}

	/**
	 * @return the imageIndex
	 */
	public int getImageIndex() {
		return imageIndex;
	}

	/**
	 * @param imageIndex the imageIndex to set
	 */
	public void setImageIndex(int imageIndex) {
		this.imageIndex = imageIndex;
	}

	/**
	 * @return the checkedInDataTable
	 */
	public boolean isCheckedInDataTable() {
		return checkedInDataTable;
	}

	/**
	 * @param checkedInDataTable the checkedInDataTable to set
	 */
	public void setCheckedInDataTable(boolean checkedInDataTable) {
		this.checkedInDataTable = checkedInDataTable;
	}

	/**
	 * @return the baseUrl
	 */
	public String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * @param baseUrl the baseUrl to set
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	/**
	 * @return the blogEntry
	 */
	public String getBlogEntry() {
		return blogEntry;
	}

	/**
	 * @param blogEntry the blogEntry to set
	 */
	public void setBlogEntry(String blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * @return the sliderEntry
	 */
	public String getSliderEntry() {
		return sliderEntry;
	}

	/**
	 * @param sliderEntry the sliderEntry to set
	 */
	public void setSliderEntry(String sliderEntry) {
		this.sliderEntry = sliderEntry;
	}
	

}
