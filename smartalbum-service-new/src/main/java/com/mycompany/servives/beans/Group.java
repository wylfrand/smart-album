/*
 * OPIAM Suite
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */

package com.mycompany.servives.beans;

import opiam.admin.faare.persistence.javabeans.JBTop;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Mapping class for the group object.<br>
 * The definition of the object is given in the faare_mapping.xml file.
 * This file defines the mapping between the group object and the
 *  corresponding LDAP entries.
 */
public class Group extends JBTop implements Serializable
{
    /** ObjectClasses list. */
    private List objectClass = new ArrayList();

    /** Description. */
    private List description;

    /** CommonName. */
    private String commonName;

    /** Ou. */
    private String ou;

    /** UniqueMember. */
    private Collection uniqueMember;

    
    
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

    /**
     * Gets the organizational unit.
     *
     * @return the organizational unit.
     */
    public String getOu()
    {
        return ou;
    }

    /**
     * Sets the organizational unit.
     *
     * @param ou  The organizational unit to set.
     */
    public void setOu(String ou)
    {
        this.ou = ou;
    }

    /**
     * Gets the uniquemember (multi-valued attribute)
     *
     * @return the uniquemember.
     */
    public Collection getUniqueMember()
    {
        return uniqueMember;
    }

    /**
     * Sets the uniquemember (multi-valued attribute)
     *
     * @param uniquemember The uniquemember to set.
     */
    public void setUniqueMember(Collection uniquemember)
    {
        this.uniqueMember = uniquemember;
    }

    /**
     * Gets the description.
     *
     * @return the description.
     */
    public List getDescription()
    {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description The description to set.
     */
    public void setDescription(List description)
    {
        this.description = description;
    }
}
