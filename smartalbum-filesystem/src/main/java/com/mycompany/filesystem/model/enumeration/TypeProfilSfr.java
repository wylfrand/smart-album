package com.mycompany.filesystem.model.enumeration;

import org.apache.commons.lang.StringUtils;

/**
 * Enumération des codes profil applicatif
 * 
 * @author amv
 */
public enum TypeProfilSfr {
    DEFAULT,
    SMART_ALBUM,
    OPIAM;
    
    public static TypeProfilSfr fromValue(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        
        for (TypeProfilSfr profile : TypeProfilSfr.values()) {
            if (profile.name().equals(value)) {
                return profile;
            }
        }
        return null;
    }
    
}
