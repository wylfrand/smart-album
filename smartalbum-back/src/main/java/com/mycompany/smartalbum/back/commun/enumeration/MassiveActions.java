package com.mycompany.smartalbum.back.commun.enumeration;

import org.apache.commons.lang.StringUtils;

/**
 * Enumeration des actions en masse sur des fichiers
 * 
 * @author amv
 */
public enum MassiveActions {
	REMOVE_SELECTED,
	REMOVE_ALL,
	TOOGLE_SELECT_ALL,
	MODIFY_ALBUM,
	CREATE_ALBUM;
    
    public static MassiveActions fromValue(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        
        for (MassiveActions action : MassiveActions.values()) {
            if (action.name().equals(value)) {
                return action;
            }
        }
        return null;
    }
    
}