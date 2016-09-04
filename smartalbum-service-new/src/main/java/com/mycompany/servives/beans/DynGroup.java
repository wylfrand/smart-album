/*
 * OPIAM Suite
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */

package com.mycompany.servives.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import opiam.admin.faare.persistence.javabeans.JBDynamicGroup;


/**
 * Mapping class for the dynGroup object.<br>
 * The definition of the object is given in the faare_mapping.xml file.
 * This file defines the mapping between the group object and the
 *  corresponding LDAP entries.
 */
public class DynGroup extends JBDynamicGroup implements Serializable
{
    /** ObjectClasses list. */
    private List objectClass = new ArrayList();

    /** CommonName. */
    private String commonName;

    /** Member. */
    private Collection member;
 

    
    
    public Collection getMember() {
		return member;
	}

	public void setMember(Collection member) {
		this.member = member;
	}

	/**
     * Gets the commonName.
     *
     * @return the commonName.
     */
    public String getCommonName()
    {
        return commonName;
    }

    /**
     * Sets the commonName.
     *
     * @param commonName The commonName to set.
     */
    public void setCommonName(String commonName)
    {
        this.commonName = commonName;
    }

    /**
     * Gets the objectclasses list.
     *
     * @return the objectClasses list.
     */
    public List getObjectClass()
    {
        return objectClass;
    }

    /**
     * Sets the objectclasses list.
     *
     * @param objectClass The objectClasses list to set.
     */
    public void setObjectClass(List objectClass)
    {
        this.objectClass = objectClass;
    }
}
