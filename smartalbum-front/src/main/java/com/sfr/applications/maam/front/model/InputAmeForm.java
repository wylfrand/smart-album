package com.sfr.applications.maam.front.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InputAmeForm implements Serializable{
	
	/**
	 * Formulaire permettant de recuperer en une fois toutes les lignes à rajouter
	 */
	private static final long serialVersionUID = 1L;
	
	private String codeAme;

	public String getCodeAme() {
		return codeAme;
	}

	public void setCodeAme(String codeAme) {
		this.codeAme = codeAme;
	}

	private List<String> newLines = new ArrayList<String>();

	public List<String> getNewLines() {
		return newLines;
	}

	public void setNewLines(List<String> newLines) {
		this.newLines = newLines;
	}
}
