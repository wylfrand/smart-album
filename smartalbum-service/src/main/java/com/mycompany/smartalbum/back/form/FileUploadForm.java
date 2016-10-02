package com.mycompany.smartalbum.back.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import com.mycompany.database.smartalbum.search.vo.ModifyShelfForm;
import com.mycompany.filesystem.model.CheckedFile;
import com.mycompany.services.smartalbum.infos.ShelfInfos;

public class FileUploadForm implements Serializable{

    private static final long serialVersionUID = -7061186190694485881L;
    
    private String selectedCoverAlbum;
    
    private int selectedFilesSize;
    
    // La photo sélectionné par défaut
    private CheckedFile defaultSelectedPicture;
    
    // Le chemin relatif de l'image par défaut (lorsqu'une image n'est pas trouvé)
    private String defaultPicturePath;
    
    private String selectedPicturesOptions;
    
    private String selectedAlbumName;
    
    private String selectedAlbumDescription;
    
    private String selectedAlbumShelf;
    
    private Set<ShelfInfos> userShelvesInfos;
    
    List<ModifyShelfForm> userShelfResume = new ArrayList<>();

    /**
     * @return the selectedCoverAlbum
     */
    public String getSelectedCoverAlbum() {
        return selectedCoverAlbum;
    }

    /**
     * @param selectedCoverAlbum the selectedCoverAlbum to set
     */
    public void setSelectedCoverAlbum(String selectedCoverAlbum) {
        this.selectedCoverAlbum = selectedCoverAlbum;
    }

    /**
     * @return the selectedFilesSize
     */
    public int getSelectedFilesSize() {
        return selectedFilesSize;
    }

    /**
     * @param selectedFilesSize the selectedFilesSize to set
     */
    public void setSelectedFilesSize(int selectedFilesSize) {
        this.selectedFilesSize = selectedFilesSize;
    }

    /**
     * @return the defaultSelectedPicture
     */
    public CheckedFile getDefaultSelectedPicture() {
        return defaultSelectedPicture;
    }

    /**
     * @param defaultSelectedPicture the defaultSelectedPicture to set
     */
    public void setDefaultSelectedPicture(CheckedFile defaultSelectedPicture) {
        this.defaultSelectedPicture = defaultSelectedPicture;
    }

    /**
     * @return the defaultPicturePath
     */
    public String getDefaultPicturePath() {
        return defaultPicturePath;
    }

    /**
     * @param defaultPicturePath the defaultPicturePath to set
     */
    public void setDefaultPicturePath(String defaultPicturePath) {
        this.defaultPicturePath = defaultPicturePath;
    }

    /**
     * @return the selectedPicturesOptions
     */
    public String getSelectedPicturesOptions() {
        return selectedPicturesOptions;
    }

    /**
     * @param selectedPicturesOptions the selectedPicturesOptions to set
     */
    public void setSelectedPicturesOptions(String selectedPicturesOptions) {
        this.selectedPicturesOptions = selectedPicturesOptions;
    }

	public String getSelectedAlbumName() {
		return selectedAlbumName;
	}

	public void setSelectedAlbumName(String selectedAlbumName) {
		this.selectedAlbumName = selectedAlbumName;
	}

	public String getSelectedAlbumDescription() {
		return selectedAlbumDescription;
	}

	public void setSelectedAlbumDescription(String selectedAlbumDescription) {
		this.selectedAlbumDescription = selectedAlbumDescription;
	}

	public String getSelectedAlbumShelf() {
		return selectedAlbumShelf;
	}

	public void setSelectedAlbumShelf(String selectedAlbumShelf) {
		this.selectedAlbumShelf = selectedAlbumShelf;
	}
	
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

	/**
	 * @return the userShelfResume
	 */
	public List<ModifyShelfForm> getUserShelfResume() {
		return userShelfResume;
	}

	/**
	 * @param userShelfResume the userShelfResume to set
	 */
	public void setUserShelfResume(List<ModifyShelfForm> userShelfResume) {
		this.userShelfResume = userShelfResume;
	}
}
