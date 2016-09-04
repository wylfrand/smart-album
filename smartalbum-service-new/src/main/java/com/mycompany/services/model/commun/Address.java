package com.mycompany.services.model.commun;

import java.io.Serializable;

public class Address implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = -8062322333214705767L;
    
    private String address;
    private String additionalAddress;
    private String zipCode;
    private String city;
    
    private String SSAEmetteur;
    private String dateEnvoiRequete;
    private String idTransaction;
    private String numeroFlux;
    private String numeroPA;
    private String versionFlux;
    
    private String volatil;
    
    /**
     * @return the additionalAddress
     */
    public String getAdditionalAddress() {
        return additionalAddress;
    }

    /**
     * @param additionalAddress the additionalAddress to set
     */
    public void setAdditionalAddress(String additionalAddress) {
        this.additionalAddress = additionalAddress;
    }

    /**
     * @return the zipCode
     */
    public String getZipCode() {
        return zipCode;
    }
    
    /**
     * @param zipCode the zipCode to set
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    @Override
    public String toString() {
        return "Address [address=" + address + ", postalCode=" + zipCode + ", city=" + city + "]";
    }
    
    public String getSSAEmetteur() {
        return SSAEmetteur;
    }
    
    public void setSSAEmetteur(String sSAEmetteur) {
        SSAEmetteur = sSAEmetteur;
    }
    
    public String getDateEnvoiRequete() {
        return dateEnvoiRequete;
    }
    
    public void setDateEnvoiRequete(String dateEnvoiRequete) {
        this.dateEnvoiRequete = dateEnvoiRequete;
    }
    
    public String getIdTransaction() {
        return idTransaction;
    }
    
    public void setIdTransaction(String idTransaction) {
        this.idTransaction = idTransaction;
    }
    
    public String getNumeroFlux() {
        return numeroFlux;
    }
    
    public void setNumeroFlux(String numeroFlux) {
        this.numeroFlux = numeroFlux;
    }
    
    public String getNumeroPA() {
        return numeroPA;
    }
    
    public void setNumeroPA(String numeroPA) {
        this.numeroPA = numeroPA;
    }
    
    public String getVersionFlux() {
        return versionFlux;
    }
    
    public void setVersionFlux(String versionFlux) {
        this.versionFlux = versionFlux;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the volatil
     */
    public String getVolatil() {
        return volatil;
    }

    /**
     * @param volatil the volatil to set
     */
    public void setVolatil(String volatil) {
        this.volatil = volatil;
    }
    
    
    
}
