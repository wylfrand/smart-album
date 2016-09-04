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

import opiam.admin.faare.persistence.javabeans.JBTop;


/**
 * Mapping class for the department object.<br>
 * The definition of the object is given in the faare_mapping.xml file.
 * This file defines the mapping between the department object and the
 *  corresponding LDAP entries.
 */
public class Department extends JBTop implements Serializable
{
    /** ObjectClasses list. */
    private List objectClass = new ArrayList();

    /** Description. */
    private Collection description = null;

    /** Ou. */
    private String ou;

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
     * @param objectClass The objectClass to set.
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
     * Sets the organizational unit
     *
     * @param ou  The organizational unit to set.
     */
    public void setOu(String ou)
    {
        this.ou = ou;
    }

    /**
     * Gets the description (multi-valued attribute).
     *
     * @return the description.
     */
    public Collection getDescription()
    {
        return description;
    }

    /**
     * Sets the description (multi-valued attribute).
     *
     * @param ldescription  The description to set.
     */
    public void setDescription(Collection ldescription)
    {
        this.description = ldescription;
    }
}
