package com.mycompany.database.smartalbum.search.vo;

import org.apache.commons.lang.StringUtils;

public enum DataTableColumnEnum {

	FILEDIMENSION("FILEDIMENSION","width"), 
	FILESIZE("FILESIZE","size"), 
	FILENAME("FILENAME","name"), 
	IMAGE("IMAGE","path");

	private String name;
	
	private String value;

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	DataTableColumnEnum(String name, String value) {
		this.name = name;
		this.value = value;
		
	}

	public static DataTableColumnEnum fromValue(String name) {
		if (StringUtils.isBlank(name)) {
			return FILENAME;
		}
		for (DataTableColumnEnum action : DataTableColumnEnum.values()) {
			if (action.name.equals(name.toUpperCase())) {
				return action;
			}
		}
		return FILENAME;
	}
	
	
}
