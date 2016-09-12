package com.mycompany.services.smartalbum.infos;

import java.io.Serializable;
import java.util.Date;

public class CommentInfos implements Serializable{
	
private static final long serialVersionUID = 3429270322123226071L;
    
    private Long id;
    
    private ImageInfos imageParent;
    
    private AlbumInfos albumParent;
    
    private UserInfos authorParent;
    
    private Date date;
    
    private String message;
    
    /**
     * Getter for property preDefined
     * 
     * @return is this shelf is predefined
     */
    public boolean isPreDefined() {
        return getImage().isPreDefined();
    }
    
    // ---------------------------------------Getters, Setters
    public Long getId() {
        return id;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public UserInfos getAuthor() {
        return authorParent;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public ImageInfos getImage() {
        return imageParent;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((imageParent == null) ? 0 : imageParent.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CommentInfos other = (CommentInfos) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (imageParent == null) {
            if (other.imageParent != null)
                return false;
        } else if (!imageParent.equals(other.imageParent))
            return false;
        return true;
    }

	/**
	 * @return the album
	 */
	public AlbumInfos getAlbum() {
		return albumParent;
	}

}
