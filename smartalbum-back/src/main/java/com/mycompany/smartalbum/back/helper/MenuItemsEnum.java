package com.mycompany.smartalbum.back.helper;

import org.apache.commons.lang.StringUtils;

public enum MenuItemsEnum {
    
    ACCUEUIL,
    NOUVELLES_IMAGES,
    ETAGERES_PUBLIQUES,
    RECHERCHE,
    MES_ETAGERES;
    
    public static MenuItemsEnum fromValue(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        
        for (MenuItemsEnum profile : MenuItemsEnum.values()) {
            if (profile.name().equals(value)) {
                return profile;
            }
        }
        return null;
    }
    
}
