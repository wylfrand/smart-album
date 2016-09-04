/*
 * OPIAM Suite
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */

package com.mycompany.servives.beans;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import opiam.admin.faare.persistence.javabeans.JBUser;

import com.mycompany.services.utils.StringUtil;


/**
 * Mapping class for the person object.<br>
 * The definition of the object is given in the faare_mapping.xml file.
 * This file defines the mapping between the person object and the
 *  corresponding LDAP entries.
 *
 * This class defines the setter and getter methods for all the attributes of
 * a person entry as named in the faare_mapping.xml file.
 *
 * All the classes that describe a mapped object representing users must extend
 *  the JBUser class which inherits from the JBTop class.
 * The JBUser class represents the connected user.
 */
public class Person extends JBUser implements Serializable
{
    /** ObjectClasses list. */
    private List objectClass = new ArrayList();

    /** User identifier. */
    private String id = null;

    /** First name and last name. */
    private String commonName = null;

    /** Dsiplay name. */
    private String displayName = null;

    /** Last name. */
    private String name = null;

    /** First name. */
    private String givenName = null;

    /** Password. */
    private String password = null;

    /** Phone number. */
    private String phone = null;

    /** Mail. */
    private String mail = null;

    /** Photo. */
    private Collection photo = null;

    /** Fax. */
    private String fax = null;

    /** Title. */
    private String title = null;

    /** RoomNumber. */
    private String roomNumber = null;

    /** Site. */
    private String site = null;

    /** Site. */
    private String department = null;

    /** Manager. */
    private Collection manager;

    /** Current profile. */
    private String profile = null;

    /** Certificate. */
    private Collection certificate = null;

    /** Application authorization. */
    private String appAuth = "No";

    /** Description. */
    private Collection description;

    /**
     * Creates a new Person object.
     */
    public Person()
    {
    }

    /**
     * Gets the authorization value.
     *
     * @return the authorization value..
     */
    public String getAppAuth()
    {
        return appAuth;
    }

    /**
     * Gets the authorization value.
     * Used by the jsp page.
     *
     * @return Returns the authorization value.
     */
    public String getVisibleAppAuth()
    {
        if ((appAuth != null) && (appAuth.equals("on")))
        {
            return "Yes";
        }
        else
        {
            return "No";
        }
    }

    /**
     * Sets the authorization value.
     *
     * @param appAuth  The authorization value to set.
     */
    public void setAppAuth(String appAuth)
    {
        this.appAuth = appAuth;
    }

    /**
     * Gets the profile.
     *
     * @return the profile.
     */
    public String getProfile()
    {
        return profile;
    }

    /**
     * Sets the profile.
     *
     * @param profile The profile to set.
     */
    public void setProfile(String profile)
    {
        this.profile = profile;
    }

    /**
     * Gets the fax.
     *
     * @return the fax.
     */
    public String getFax()
    {
        return fax;
    }

    /**
     * Sets the fax.
     *
     * @param fax The fax to set.
     */
    public void setFax(String fax)
    {
        this.fax = fax;
    }

    /**
     * Gets the maanger.
     *
     * @return the manager.
     */
    public Collection getManager()
    {
        return manager;
    }

    /**
     * Sets the manager.
     *
     * @param manager The manager to set.
     */
    public void setManager(Collection manager)
    {
        this.manager = manager;
    }

    /**
     * Gets the room number.
     *
     * @return the roomNumber.
     */
    public String getRoomNumber()
    {
        return roomNumber;
    }

    /**
     * Sets the room number.
     *
     * @param roomNumber The roomNumber to set.
     */
    public void setRoomNumber(String roomNumber)
    {
        this.roomNumber = roomNumber;
    }

    /**
     * Gets the site.
     *
     * @return the site.
     */
    public String getSite()
    {
        return site;
    }

    /**
     * Sets the site.
     *
     * @param site The site to set.
     */
    public void setSite(String site)
    {
        this.site = site;
    }

    /**
     * Gets the title.
     *
     * @return the title.
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Sets the title.
     *
     * @param title The title to set.
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * Gets the set of objectclasses.
     *
     * @return The objectClasses list.
     */
    public List getObjectClass()
    {
        return objectClass;
    }

    /**
     * Initializes the set of objectclasses.
     *
     * @param objectClasses The objectClass list to set.
     */
    public void setObjectClass(List objectClasses)
    {
        this.objectClass = objectClasses;
    }

    /**
     * Gets the user identifier.
     *
     * @return The identifier.
     */
    public String getId()
    {
        return id;
    }

    /**
     * Sets the user identifier.
     *
     * @param anid  The identifier to set.
     */
    public void setId(String anid)
    {
        this.id = anid;
    }

    /**
     * Gets the fisrt name and the last name.
     *
     * @return The concatenation of the first name and the last name.
     */
    public String getCommonName()
    {
        return commonName;
    }

    /**
     * Sets the fisrt name and the last name.
     *
     * @param aCN  The fisrt name and the last name to set.
     */
    public void setCommonName(String aCN)
    {
        this.commonName = aCN;
    }

    /**
     * Gets the display name.
     *
     * @return The concatenation of the last name and the first name.
     */
    public String getDisplayName()
    {
        StringBuffer displayname = new StringBuffer();
        displayname.append(name);
        displayname.append(" ");
        displayname.append(givenName);

        return displayname.toString();
    }

    /**
     * Sets the display name.
     *
     * @param aDisplay  The last name and the first name to set.
     */
    public void setDisplayName(String aDisplay)
    {
        this.displayName = aDisplay;
    }

    /**
     * Gets the last name.
     *
     * @return The last name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the last name.
     *
     * @param anom  The last name to set.
     */
    public void setName(String aname)
    {
        this.name = aname;
    }

    /**
     * Gets the first name.
     *
     * @return The first name.
     */
    public String getGivenName()
    {
        return givenName;
    }

    /**
     * Sets the first name.
     *
     * @param aprenom  The first name to set.
     */
    public void setGivenName(String aGN)
    {
        this.givenName = aGN;
    }

    /**
     * Gets the password.
     *
     * @return The password.
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param apassword The password to set.
     */
    public void setPassword(String apassword)
    {
        this.password = apassword;
    }

    /**
     * Gets the mail.
     *
     * @return The mail.
     */
    public String getMail()
    {
        return mail;
    }

    /**
     * Sets the mail.
     *
     * @param amail  The mail to set.
     */
    public void setMail(String amail)
    {
        this.mail = amail;
    }

    /**
     * Gets the phone number.
     *
     * @return The phone number.
     */
    public String getPhone()
    {
        return phone;
    }

    /**
     * Sets the phone number.
     *
     * @param aphone  The phone number to set.
     */
    public void setPhone(String aphone)
    {
        this.phone = aphone;
    }

    /**
     * Gets the photo.
     *
     * @return The photo as a bytes collection.
     */
    public Collection getPhoto()
    {
        return photo;
    }

    /**
     * Sets the photo.
     *
     * @param value  The bytes collection to set as a photo.
     */
    public void setPhoto(Collection value)
    {
        this.photo = value;
    }

    /**
     * Gets the department.
     *
     * @return the department.
     */
    public String getDepartment()
    {
        return department;
    }

    /**
     * Sets the department.
     *
     * @param department The department to set.
     */
    public void setDepartment(String department)
    {
        this.department = department;
    }

    /**
     * Sets the certificate.
     *
     * @param certificate The certificate to set.
     */
    public void setCertificate(Collection certificate)
    {
        this.certificate = certificate;
    }

    /**
     * Gets the certificate.
     *
     * @return the certificate.
     */
    public Collection getCertificate()
    {
        return certificate;
    }

    /**
     * Gets the certificate.
     * Used by the jsp page.
     *
     * @return the certificate.
     */
    public ArrayList getVisibleCertificate()
    {
        try
        {
            ByteArrayInputStream bais =
                new ByteArrayInputStream((byte[]) certificate.iterator().next());
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate cert =
                (X509Certificate) cf.generateCertificate(bais);

            ArrayList infosList = new ArrayList();
            infosList.add("Version: " + cert.getVersion());
            infosList.add("Subject: " + cert.getSubjectDN());
            infosList.add("Signature Algorithm: " + StringUtil.displayString(cert.getSigAlgName(), 80, null) +
            		", OID = " + StringUtil.displayString(cert.getSigAlgOID(), 80, null));
            infosList.add("");
            infosList.add("Key: " + cert.getPublicKey());
            infosList.add("");
            infosList.add("Validity: From: " + cert.getNotBefore());
            infosList.add("            To: " + cert.getNotAfter());
            infosList.add("Issuer: " + cert.getIssuerDN());
            infosList.add("SerialNumber: " + cert.getSerialNumber());

            return infosList;
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    /**
     * Gets the person object as a String.
     *
     * @return the commonName.
     */
    @Override
	public String toString()
    {
        return commonName;
    }

    /**
     * Gets the description.
     *
     * @return the description.
     */
    public Collection getDescription()
    {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param aDescription  The description to set.
     */
    public void setDescription(Collection aDescription)
    {
        this.description = aDescription;
    }
}
