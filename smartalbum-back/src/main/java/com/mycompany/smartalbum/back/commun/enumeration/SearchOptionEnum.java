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
package com.mycompany.smartalbum.back.commun.enumeration;

import org.apache.commons.lang.StringUtils;

/**
 * This enumeration represent all possible entities for search
 *
 * @author Aristide M'vou
 */
public enum SearchOptionEnum {
	USERS("Users"),
	SHELVES("Shelves"),
	ALBUMS("Albums"),
	IMAGES("Images"),
	TAGS("Tags");
	
	private String name;
	
	SearchOptionEnum(String nom)
	{
	    this.name = nom;
	}
	
	public static SearchOptionEnum fromValue(String value) {
	        if (StringUtils.isBlank(value)) {
	            return null;
	        }
	        
	        for (SearchOptionEnum action : SearchOptionEnum.values()) {
	            if (action.name.equals(value)) {
	                return action;
	            }
	        }
	        return null;
	    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
