package com.mycompany.services.smartalbum.infos;

import java.io.Serializable;
import java.util.Date;

public class CommentBaseInfos implements Serializable{
	
private static final long serialVersionUID = 3429270322123226071L;
    
    private Long id;
    
    private UserInfos authorMessage;
    
    private Date date;
    
    private String message;
    
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
        return authorMessage;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authorMessage == null) ? 0 : authorMessage.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
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
		CommentBaseInfos other = (CommentBaseInfos) obj;
		if (authorMessage == null) {
			if (other.authorMessage != null)
				return false;
		} else if (!authorMessage.equals(other.authorMessage))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		return true;
	}

}
