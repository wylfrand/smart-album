package com.sfr.applications.maam.front.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.mycompany.services.model.commun.enumeration.NatureLine;
import com.mycompany.services.model.commun.enumeration.TypeProfilSfr;

/**
 * Répresente les informations liees a la ligne FIXE ou MOBILE d'un utilisateur
 * 
 * @author amv
 */
public class NewAddLine implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String name;
    
    
    private String firstname;
    
	/**
	 * Civilité
	 */
	private String civility;
	
	private String etatLine;
	
	 /**
     * Le numéro de téléphone
     */
    private String number;
    
    
    /**
     * Libellé PTA de la ligne
     */
    private String libellePTA;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getCivility() {
		return civility;
	}

	public void setCivility(String civility) {
		this.civility = civility;
	}

	public String getEtatLine() {
		return etatLine;
	}

	public void setEtatLine(String etatLine) {
		this.etatLine = etatLine;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getLibellePTA() {
		return libellePTA;
	}

	public void setLibellePTA(String libellePTA) {
		this.libellePTA = libellePTA;
	}
	
	
}
