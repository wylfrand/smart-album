package com.mycompany.smartalbum.back.resetPassword;

import org.apache.commons.lang.StringUtils;

public class ResetPwdForm {
    
    private String identifiant;
    private String msisdn;
    private String msisdnMasked;
    private String secretQuestion;
    private String answer;
    private String notification;
    private String notificationView;
    private String captchaResponse;
    private String emailContact;
    private String emailContactMasked;
    private String finalNotification;
    private String finalAddress;
    private String finalAddressMasked;
    private String validerBouton;
    private String validerQSBouton;
    private String noTemplate;
    private String ascUser;
    private boolean utilisateurFixe;
    
    public String getSecretQuestion() {
        return this.secretQuestion;
    }
    
    public void setSecretQuestion(final String secretQuestion) {
        this.secretQuestion = secretQuestion;
    }
    
    public String getAnswer() {
        return this.answer;
    }
    
    public void setAnswer(final String answer) {
        this.answer = answer;
    }
    
    public String getNotification() {
        return this.notification;
    }
    
    public void setNotification(final String notification) {
        this.notification = notification;
    }
    
    public String getNotificationView() {
        return this.notificationView;
    }
    
    public void setNotificationView(final String notificationView) {
        this.notificationView = notificationView;
    }
    
    public String getCaptchaResponse() {
        return this.captchaResponse;
    }
    
    public void setCaptchaResponse(final String captchaResponse) {
        this.captchaResponse = captchaResponse;
    }
    
    public String getEmailContact() {
        return this.emailContact;
    }
    
    public void setEmailContact(final String emailContact) {
        this.emailContact = emailContact;
    }
    
    public String getEmailContactMasked() {
        return this.emailContactMasked;
    }
    
    public void setEmailContactMasked(final String emailContactMasked) {
        this.emailContactMasked = emailContactMasked;
    }
    
    public String getFinalNotification() {
        return this.finalNotification;
    }
    
    public void setFinalNotification(final String finalNotification) {
        this.finalNotification = finalNotification;
    }
    
    public String getFinalAddress() {
        return this.finalAddress;
    }
    
    public void setFinalAddress(final String finalAddress) {
        this.finalAddress = finalAddress;
    }
    
    public String getIdentifiant() {
        return this.identifiant;
    }
    
    public void setIdentifiant(final String identifiant) {
        this.identifiant = identifiant;
    }
    
    public String getValiderBouton() {
        return this.validerBouton;
    }
    
    public void setValiderBouton(final String validerBouton) {
        this.validerBouton = validerBouton;
    }
    
    public String getValiderQSBouton() {
        return this.validerQSBouton;
    }
    
    public void setValiderQSBouton(final String validerQSBouton) {
        this.validerQSBouton = validerQSBouton;
    }
    
    public String getNoTemplate() {
        return this.noTemplate;
    }
    
    public void setNoTemplate(final String noTemplate) {
        this.noTemplate = noTemplate;
    }
    
    public String getAscUser() {
        return this.ascUser;
    }
    
    public void setAscUser(final String ascUser) {
        this.ascUser = ascUser;
    }
    
    public String getFinalAddressMasked() {
        if (StringUtils.isNotBlank(this.finalAddress)) {
            this.finalAddressMasked = StringUtils.substring(this.finalAddress, 0, 6) + "****";
        }
        return this.finalAddressMasked;
    }
    
    public void setFinalAddressMasked(final String finalAddressMasked) {
        this.finalAddressMasked = finalAddressMasked;
    }
    
    public String getMsisdn() {
        return this.msisdn;
    }
    
    public void setMsisdn(final String msisdn) {
        this.msisdn = msisdn;
    }
    
    public String getMsisdnMasked() {
        if (StringUtils.isNotBlank(this.msisdn)) {
            this.msisdnMasked = StringUtils.substring(this.msisdn, 0, 6) + "****";
        }
        return this.msisdnMasked;
    }
    
    public void setMsisdnMasked(final String msisdnMasked) {
        this.msisdnMasked = msisdnMasked;
    }
    
    public boolean isUtilisateurFixe() {
        return this.utilisateurFixe;
    }
    
    public void setUtilisateurFixe(final boolean utilisateurFixe) {
        this.utilisateurFixe = utilisateurFixe;
    }
    
}
