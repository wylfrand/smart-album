package com.mycompany.database.smartalbum.utils;

import org.apache.commons.lang.StringUtils;

/**
 * Enumeration des actions en masse sur des fichiers
 * 
 * @author amv
 */
public enum Entities {
	ALBUM,
	IMAGE,
	COMMENT,
	SHELF,
	USER,
	MESSAGE_HTML;
    
    public static Entities fromValue(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        
        for (Entities action : Entities.values()) {
            if (action.name().equals(value)) {
                return action;
            }
        }
        return null;
    }
    
}