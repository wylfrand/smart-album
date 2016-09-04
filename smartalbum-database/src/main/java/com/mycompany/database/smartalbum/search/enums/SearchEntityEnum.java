/**
 * License Agreement.
 *
 *  JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007  Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */
package com.mycompany.database.smartalbum.search.enums;

import org.apache.commons.lang.StringUtils;

/**
 * This enumeration represent all possible entities for search
 *
 * @author Aristide M'vou
 */
public enum SearchEntityEnum {
	USER,SHELF,ALBUM,IMAGE,METATAG;
	
	public static SearchEntityEnum fromValue(String value) {
	        if (StringUtils.isBlank(value)) {
	            return null;
	        }
	        
	        for (SearchEntityEnum action : SearchEntityEnum.values()) {
	            if (action.name().equals(value)) {
	                return action;
	            }
	        }
	        return null;
	    }
}
