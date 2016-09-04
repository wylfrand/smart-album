package com.mycompany.services.smartalbum.vo;

import org.apache.commons.lang.StringUtils;

public enum DataTableEnumType {
	
	ALBUM("album"),
    IMAGE("image"),
    USER("user"),
    SHELF("shelf");
    
    String value;
    
    private DataTableEnumType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static DataTableEnumType fromValue(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        
        for (DataTableEnumType profile : DataTableEnumType.values()) {
            if (profile.name().equals(value)) {
                return profile;
            }
        }
        return null;
    }
	

}
