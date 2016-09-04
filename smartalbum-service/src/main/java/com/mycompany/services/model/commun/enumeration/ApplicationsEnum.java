package com.mycompany.services.model.commun.enumeration;

import org.apache.commons.lang.StringUtils;

public enum ApplicationsEnum {
    
    SMARTALBUM_BACK,
    SMARTALBUM_FRONT,
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
