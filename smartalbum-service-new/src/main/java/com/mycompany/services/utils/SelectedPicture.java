package com.mycompany.services.utils;

import java.io.Serializable;

public class SelectedPicture implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pageIndex;
	private String photoIndex;
	/**
	 * @return the pageIndex
	 */
	public String getPageIndex() {
		return pageIndex;
	}
	/**
	 * @param pageIndex the pageIndex to set
	 */
	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}
	/**
	 * @return the photoIndex
	 */
	public String getPhotoIndex() {
		return photoIndex;
	}
	/**
	 * @param photoIndex the photoIndex to set
	 */
	public void setPhotoIndex(String photoIndex) {
		this.photoIndex = photoIndex;
	}
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SelectedPicture [pageIndex=" + pageIndex + ", photoIndex=" + photoIndex + "]";
    }

}
