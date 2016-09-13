package com.mycompany.services.smartalbum.vo.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import com.mycompany.filesystem.model.CheckedFile;
import com.mycompany.services.smartalbum.infos.ShelfInfos;

public class AlbumVOForm implements Serializable{

	private static final long serialVersionUID = -7042878411608396483L;

	private Long id = null;

	private Set<ImageVOForm> images = new HashSet<ImageVOForm>();

	private ImageVOForm coveringImage;

	private boolean showAfterCreate;

	private Date created;

	private String name;

	private String description;
	
	private String path;
	
	private List<CheckedFile> filesToCreateOrModify = new ArrayList<>();
	
	private CheckedFile albumCoverFile = null;
	
	private int selectedFilesSize;
	
	private String selectedPicturesOptions;
	
	private Set<ShelfInfos> userShelvesInfos;
	
	/**
	 * @return the userShelves
	 */
	public Set<ShelfInfos> getUserShelvesInfos() {
	    if(userShelvesInfos == null)
            {
	    	userShelvesInfos = Sets.newHashSet();
            }
		return userShelvesInfos;
	}
	
	// ********************** Accessor Methods ********************** //

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Getter for property images
	 * 
	 * @return List if images, belongs to this album
	 */
	public Set<ImageVOForm> getImages() {
		if (images == null) {
			images = Sets.newHashSet();
		}
		return images;
	}

	public void setImages(Set<ImageVOForm> newImages) {
		images = newImages;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return List of unvisited images
	 */
	public Set<ImageVOForm> getUnvisitedImages() {
		final Set<ImageVOForm> unvisitedImages = new HashSet<ImageVOForm>(this
				.getImages().size());
		for (ImageVOForm i : this.getImages()) {
			if (i.isNew()) {
				unvisitedImages.add(i);
			}
		}
		return unvisitedImages;
	}

	/**
	 * @param coveringImage
	 *            - ImageVO for covering album
	 */
	public void setCoveringImage(ImageVOForm coveringImage) {
		this.coveringImage = coveringImage;
	}

	// ********************** Business Methods ********************** //
	/**
	 * This method determine covering image of this album
	 * 
	 * @return covering image
	 */
    public ImageVOForm getCoveringImage() {
        if (coveringImage == null && !isEmpty()) {
            
            for (Iterator<ImageVOForm> it = images.iterator(); it.hasNext();) {
                coveringImage = it.next();
            }
        }
        return coveringImage;
    }
    
	/**
	 * This method determine is album empty or not
	 */
	public boolean isEmpty() {
		return images == null || images.isEmpty();
	}

	/**
	 * Return relative path of this album in file-system(relative to uploadRoot
	 * parameter)
	 */
	public String getPath() {
		return path;
	}

	@Override
	public String toString() {
		return "{id : " + getId() + ", name : " + getName() + "}";
	}

	public boolean isShowAfterCreate() {
		return showAfterCreate;
	}

	public void setShowAfterCreate(boolean showAfterCreate) {
		this.showAfterCreate = showAfterCreate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<CheckedFile> getFilesToCreateOrModify() {
		return filesToCreateOrModify;
	}

	public void setFilesToCreateOrModify(List<CheckedFile> filesToCreateOrModify) {
		this.filesToCreateOrModify = filesToCreateOrModify;
	}

	public CheckedFile getAlbumCoverFile() {
		return albumCoverFile;
	}

	public void setAlbumCoverFile(CheckedFile albumCoverFile) {
		this.albumCoverFile = albumCoverFile;
	}

	public int getSelectedFilesSize() {
		return selectedFilesSize;
	}

	public void setSelectedFilesSize(int selectedFilesSize) {
		this.selectedFilesSize = selectedFilesSize;
	}

	public String getSelectedPicturesOptions() {
		return selectedPicturesOptions;
	}

	public void setSelectedPicturesOptions(String selectedPicturesOptions) {
		this.selectedPicturesOptions = selectedPicturesOptions;
	}

}
