package com.mycompany.database.smartalbum.utils;

import org.apache.commons.lang.StringUtils;


/**
 * Enumération des différentes vues de l'application
 * 
 * @author <b>amvou</b>
 */
public enum HTMLMessageTypes {
    

    WELCOME,
    
    ALBUM_DESC,
    
    IMAGE_DESC,
    
    SHELF_DESC,
    
    UNKNOWN;
    
    public static HTMLMessageTypes fromValue(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        
        for (HTMLMessageTypes action : HTMLMessageTypes.values()) {
            if (action.name().equals(value)) {
                return action;
            }
        }
        return HTMLMessageTypes.UNKNOWN;
    }
}
