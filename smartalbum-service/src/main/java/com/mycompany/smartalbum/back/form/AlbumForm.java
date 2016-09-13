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
import com.mycompany.database.smartalbum.model.User;
import com.mycompany.services.smartalbum.infos.ShelfInfos;
import com.mycompany.services.smartalbum.vo.ImageVO;
import com.mycompany.services.smartalbum.vo.ShelfVO;

/**
 * Class for representing Album Entity form
 *
 * @author Aristide M'vou
 */
public class AlbumForm implements Serializable {

	private static final long serialVersionUID = -7042878411608396483L;

	private Long id = null;
	
	private User user;

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
	
	private List<ShelfInfos> publicShelves;
	
	private List<ShelfInfos> userShelves;
	
	private Set<ShelfInfos> userShelvesInfos;
	
	private int selectedFilesSize;
	
	List<ImageVO> selectedPictures = Lists.newArrayList();
	
	// La photo sélectionné par défaut
	private ImageVO defaultSelectedPicture;
	
	// Le chemin relatif de l'image par défaut (lorsqu'une image n'est pas trouvé)
	private String defaultPicturePath;
	
	private String selectedPicturesOptions;
	
	private Boolean creation;
	
	private String path;
	
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
	public List<ShelfInfos> getPublicShelves() {
	    if(publicShelves == null)
	    {
	        publicShelves = Lists.newArrayList();
	    }
		return publicShelves;
	}

	/**
	 * @param publicShelves the publicShelves to set
	 */
	public void setPublicShelves(List<ShelfInfos> publicShelves) {
		this.publicShelves = publicShelves;
	}

	/**
	 * @return the userShelves
	 */
	public List<ShelfInfos> getUserShelves() {
	    if(userShelves == null)
            {
	        userShelves = new ArrayList<>();
            }
		return userShelves;
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
	 * @param userShelves the userShelves to set
	 */
	public void setUserShelves(List<ShelfInfos> userShelves) {
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
    public ImageVO getDefaultSelectedPicture() {
        return defaultSelectedPicture;
    }

    /**
     * @param defaultSelectedPicture the defaultSelectedPicture to set
     */
    public void setDefaultSelectedPicture(ImageVO defaultSelectedPicture) {
        this.defaultSelectedPicture = defaultSelectedPicture;
    }

    /**
     * @return the selectedPictures
     */
    public List<ImageVO> getSelectedPictures() {
        if(selectedPictures == null)
        {
            selectedPictures = new ArrayList<ImageVO>();
        }
        return selectedPictures;
    }

    
    
    public void initSelectedPictures()
    {
        selectedPictures = new ArrayList<ImageVO>();
    }

	public Boolean getCreation() {
		return creation;
	}

	public void setCreation(Boolean creation) {
		this.creation = creation;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setSelectedPictures(List<ImageVO> selectedPictures) {
		this.selectedPictures = selectedPictures;
	}

}
