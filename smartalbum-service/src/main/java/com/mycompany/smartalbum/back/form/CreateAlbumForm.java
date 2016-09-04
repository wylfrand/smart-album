package com.mycompany.smartalbum.back.form;

import java.io.Serializable;

public class CreateAlbumForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private String action;

	private String page;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

}
