package com.mycompany.services.utils;

import java.io.Serializable;

public class CommentForm implements Serializable{
    
    private static final long serialVersionUID = 1316440715501471570L;
    private long id;
    private long imageId;
    private String message;
    private String email;
    
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }
    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
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
    /**
     * @return the imageId
     */
    public long getImageId() {
        return imageId;
    }
    /**
     * @param imageId the imageId to set
     */
    public void setImageId(long imageId) {
        this.imageId = imageId;
    }
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }
    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
   
    
}
