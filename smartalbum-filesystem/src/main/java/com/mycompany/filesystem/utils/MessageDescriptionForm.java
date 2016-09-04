package com.mycompany.filesystem.utils;

import java.io.Serializable;

public class MessageDescriptionForm implements Serializable {
    
    private static final long serialVersionUID = 9211458101491675455L;
    
    private String title;
    private String description;
    private long id = -1L;
    private String entityType;
    
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
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
     * @return the id
     */
    public long getId() {
        return id;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
}
