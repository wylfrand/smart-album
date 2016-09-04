package com.mycompany.database.smartalbum.search.enums;

import org.apache.commons.lang.StringUtils;

public enum MessageHTMLTypes {
    
    SHORTDESCRIPTION,
    LONGDESCRIPTION;
    public static MessageHTMLTypes fromValue(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        
        for (MessageHTMLTypes message : MessageHTMLTypes.values()) {
            if (message.name().equals(value)) {
                return message;
            }
        }
        return null;
    }
    
}
