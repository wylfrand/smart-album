package com.mycompany.services.smartalbum.vo;

import java.io.Serializable;

public class Column implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public Column(String data){
		this.data = data;
	}
	
	private String data;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
