package com.mycompany.services.smartalbum.infos;

public class MappingOptions {
	
	private boolean loadingShelvesParent = false;
	private boolean loadingAlbumsParent = false;
	private boolean loadingUserImagesParent = false;
	private boolean loadingAlbumsImagesParent = false;
	private boolean loadingMessagesHtmlParent = false;
	/**
	 * @return the loadingShelves
	 */
	public boolean isLoadingShelvesParent() {
		return loadingShelvesParent;
	}
	/**
	 * @param loadingShelves the loadingShelves to set
	 */
	public void setLoadingShelvesParent(boolean loadingShelves) {
		this.loadingShelvesParent = loadingShelves;
	}
	/**
	 * @return the loadingAlbums
	 */
	public boolean isLoadingAlbumsParent() {
		return loadingAlbumsParent;
	}
	/**
	 * @param loadingAlbums the loadingAlbums to set
	 */
	public void setLoadingAlbumsParent(boolean loadingAlbums) {
		this.loadingAlbumsParent = loadingAlbums;
	}
	/**
	 * @return the loadingUserImages
	 */
	public boolean isLoadingUserImagesParent() {
		return loadingUserImagesParent;
	}
	/**
	 * @param loadingUserImages the loadingUserImages to set
	 */
	public void setLoadingUserImagesParent(boolean loadingUserImages) {
		this.loadingUserImagesParent = loadingUserImages;
	}
	/**
	 * @return the loadingAlbumsImages
	 */
	public boolean isLoadingAlbumsImagesParent() {
		return loadingAlbumsImagesParent;
	}
	/**
	 * @param loadingAlbumsImages the loadingAlbumsImages to set
	 */
	public void setLoadingAlbumsImagesParent(boolean loadingAlbumsImages) {
		this.loadingAlbumsImagesParent = loadingAlbumsImages;
	}
	/**
	 * @return the loadingMessagesHtml
	 */
	public boolean isLoadingMessagesHtmlParent() {
		return loadingMessagesHtmlParent;
	}
	/**
	 * @param loadingMessagesHtml the loadingMessagesHtml to set
	 */
	public void setLoadingMessagesHtmlParent(boolean loadingMessagesHtml) {
		this.loadingMessagesHtmlParent = loadingMessagesHtml;
	}
}
