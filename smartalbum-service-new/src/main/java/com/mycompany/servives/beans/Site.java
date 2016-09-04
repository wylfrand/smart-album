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
import java.util.List;


/**
 * Mapping class for the site object.<br>
 * The definition of the object is given in the faare_mapping.xml file.
 * This file defines the mapping between the site object and the
 *  corresponding LDAP entries.
 */
public class Site extends JBTop implements Serializable
{
    /** ObjectClasses list. */
    private List objectClass = new ArrayList();

    /** Description. */
    private List description;

    /** Street. */
    private String street;

    /** State. */
    private String state;

    /** Locality. */
    private String locality;

    /**
     * Gets the description(multi-valued attribute).
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
     * @param description  The description to set.
     */
    public void setDescription(List description)
    {
        this.description = description;
    }

    /**
     * Gets the locality.
     *
     * @return the locality.
     */
    public String getLocality()
    {
        return locality;
    }

    /**
     * Sets the locality.
     *
     * @param locality  The locality to set.
     */
    public void setLocality(String locality)
    {
        this.locality = locality;
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
     * Sets the objectClasses list.
     *
     * @param objectClass  The objectClasses list to set.
     */
    public void setObjectClass(List objectClass)
    {
        this.objectClass = objectClass;
    }

    /**
     * Gets the state.
     *
     * @return the state.
     */
    public String getState()
    {
        return state;
    }

    /**
     * Sets the state.
     *
     * @param state The state to set.
     */
    public void setState(String state)
    {
        this.state = state;
    }

    /**
     * Gets the street.
     *
     * @return the street.
     */
    public String getStreet()
    {
        return street;
    }

    /**
     * Sets the street.
     *
     * @param street The street to set.
     */
    public void setStreet(String street)
    {
        this.street = street;
    }
}
