package com.mycompany.smartalbum.back.helper.velosity;

import org.apache.commons.lang.StringUtils;

import com.mycompany.services.model.commun.enumeration.TypeProfilSfr;

public enum TemplateEnum {
	
	DATATABLE_CB_IMG_VM("DATATABLE_CB_IMG_VM"),
    DATATABLE_ACTIONS_VM("DATATABLE_ACTIONS_VM"),
    DATATABLE_BLOG_ENTRY("DATATABLE_BLOG_ENTRY");
    
    String value;
    
    private TemplateEnum(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
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
