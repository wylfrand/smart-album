package com.mycompany.database.smartalbum.search.vo;

import java.io.Serializable;
import java.math.BigInteger;

public class ImageResume implements Serializable {
	
	private static final long serialVersionUID = 1L;
	BigInteger id;
	String name;
	
	public ImageResume(BigInteger id, String name)
	{
		this.name = name;
		this.id = id;
	}
	/**
	 * @return the id
	 */
	public BigInteger getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(BigInteger id) {
		this.id = id;
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
