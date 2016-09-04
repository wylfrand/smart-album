package com.mycompany.smartalbum.back.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mycompany.database.smartalbum.model.Image;
import com.mycompany.database.smartalbum.model.Shelf;
import com.mycompany.services.utils.SelectedPicture;

/**
 * Class for representing Album Entity form
 *
 * @author Aristide M'vou
 */
public class AlbumForm implements Serializable {

	private static final long serialVersionUID = -7042878411608396483L;

	private Long id = null;

	private List<Image> images;

	// Etagère dans lequel l'album est rangé
	private Shelf shelf;

	private Image coveringImage;
	
	private boolean showAfterCreate;
	
	private Date created;

	private String name;
	
	private String selectedOwnShelf;

	private String selectedPubShelf;
	
	private String selectedCoverAlbum;
	
	private String description;
	
	private Set<Shelf> publicShelves;
	
	private Set<Shelf> userShelves;
	
	private int selectedFilesSize;
	
	List<SelectedPicture> selectedPictures = Lists.newArrayList();
	
	// La photo sélectionné par défaut
	private SelectedPicture defaultSelectedPicture;
	
	// Le chemin relatif de l'image par défaut (lorsqu'une image n'est pas trouvé)
	private String defaultPicturePath;
	
	private String selectedPicturesOptions;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the images
	 */
	public List<Image> getImages() {
	    if(images == null)
	    {
	        images = Lists.newArrayList();
	    }
		return images;
	}

	/**
	 * @param images the images to set
	 */
	public void setImages(List<Image> images) {
		this.images = images;
	}

	/**
	 * @return the shelf
	 */
	public Shelf getShelf() {
		return shelf;
	}

	/**
	 * @param shelf the shelf to set
	 */
	public void setShelf(Shelf shelf) {
		this.shelf = shelf;
	}

	/**
	 * @return the coveringImage
	 */
	public Image getCoveringImage() {
		return coveringImage;
	}

	/**
	 * @param coveringImage the coveringImage to set
	 */
	public void setCoveringImage(Image coveringImage) {
		this.coveringImage = coveringImage;
	}

	/**
	 * @return the showAfterCreate
	 */
	public boolean isShowAfterCreate() {
		return showAfterCreate;
	}

	/**
	 * @param showAfterCreate the showAfterCreate to set
	 */
	public void setShowAfterCreate(boolean showAfterCreate) {
		this.showAfterCreate = showAfterCreate;
	}

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the publicShelves
	 */
	public Set<Shelf> getPublicShelves() {
	    if(publicShelves == null)
	    {
	        publicShelves = Sets.newHashSet();
	    }
		return publicShelves;
	}

	/**
	 * @param publicShelves the publicShelves to set
	 */
	public void setPublicShelves(Set<Shelf> publicShelves) {
		this.publicShelves = publicShelves;
	}

	/**
	 * @return the userShelves
	 */
	public Set<Shelf> getUserShelves() {
	    if(userShelves == null)
            {
	        userShelves = Sets.newHashSet();
            }
		return userShelves;
	}

	/**
	 * @param userShelves the userShelves to set
	 */
	public void setUserShelves(Set<Shelf> userShelves) {
		this.userShelves = userShelves;
	}

	public String getSelectedOwnShelf() {
		return selectedOwnShelf;
	}

	public void setSelectedOwnShelf(String selectedOwnShelf) {
		this.selectedOwnShelf = selectedOwnShelf;
	}

	public String getSelectedPubShelf() {
		return selectedPubShelf;
	}

	public void setSelectedPubShelf(String selectedPubShelf) {
		this.selectedPubShelf = selectedPubShelf;
	}

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
     * @return the selectedPictures
     */
    public List<SelectedPicture> getSelectedPictures() {
        if(selectedPictures == null)
        {
            selectedPictures = new ArrayList<SelectedPicture>();
        }
        return selectedPictures;
    }

    
    
    public void initSelectedPictures()
    {
        selectedPictures = new ArrayList<SelectedPicture>();
    }

}
