package com.mycompany.smartalbum.back.form;

import java.io.Serializable;

import com.mycompany.services.utils.SelectedPicture;

public class FileUploadForm implements Serializable{

    private static final long serialVersionUID = -7061186190694485881L;
    
    private String selectedCoverAlbum;
    
    private int selectedFilesSize;
    
    // La photo sélectionné par défaut
    private SelectedPicture defaultSelectedPicture;
    
    // Le chemin relatif de l'image par défaut (lorsqu'une image n'est pas trouvé)
    private String defaultPicturePath;
    
    private String selectedPicturesOptions;
    
    

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
    public SelectedPicture getDefaultSelectedPicture() {
        return defaultSelectedPicture;
    }

    /**
     * @param defaultSelectedPicture the defaultSelectedPicture to set
     */
    public void setDefaultSelectedPicture(SelectedPicture defaultSelectedPicture) {
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
    
}
