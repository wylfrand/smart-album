package com.mycompany.smartalbum.back.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * Classe dédiée à la gestion du mailing.
 */
@Component
public class SendMailService {
    
    /*
     * Logger
     */
    private final Logger logger = LoggerFactory.getLogger(SendMailService.class);
    
    /*
     * Spring beans injection
     */
    private JavaMailSender mailSender;
    private VelocityEngine velocityEngine;
    private String akamaiHost;
    private String sendIdentRecoveryTemplate;
    private String sendPassword9cTemplate;
    private String sendChangeMailContactTemplate;
    
    public final String sender = "wylfranda@yahoo.fr";
    public final String sendIdSubjectRecupIdent = "[SMARTALBUM.FR] - Envoi de votre identifiant";
    public final String sendPasswordSubject = "[SMARTALBUM.FR] - Envoi de votre nouveau mot de passe";
    public final String sendChangeMailContactSubject = "[SMARTALBUM.FR] - Modification de votre adresse e-mail de contact";
    
    /**
     * Méthode dédiée à l'envoi de l'identifiant unique par mail pour le parcours de recuperation des identifiants
     */
    public void sendIdentifiantConfirmationMailForIdentRecovery(final String emailContact, final String numLigne,
                                                                final String numContrat, final String civilite,
                                                                final String nom, final String identifiant) {
        logger.trace("Entrée - Envoi de l'identifiant par mail pour parcours de recuperation de l'identifiant");
        final MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(final MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = null;
                message = new MimeMessageHelper(mimeMessage);
                message.setFrom(sender);
                message.setTo(emailContact);
                message.setSubject(sendIdSubjectRecupIdent);
                final Map<String, Object> model = new HashMap<String, Object>();
                model.put("akamaiHost", SendMailService.this.akamaiHost);
                model.put("numLigne", numLigne);
                model.put("numContrat", numContrat);
                model.put("civilite", civilite);
                String formPolitesse = "Ch&egrave;re";
                if (StringUtils.equalsIgnoreCase("M.", civilite)) {
                    formPolitesse = "Cher";
                }
                model.put("cher", formPolitesse);
                model.put("nom", nom);
                final String[] identifiants = StringUtils.split(identifiant, ",");
                model.put("identifiant", identifiants);
                model.put("sizeIdentifiant", identifiants == null ? 0 : identifiants.length);
                logger.debug("sendIdentifiantConfirmationMailForIdentRecovery : {} {} {} {} {} {} ", new Object[] {
                    numLigne, numContrat, civilite, formPolitesse, nom, identifiants
                });
                final String text = VelocityEngineUtils
                    .mergeTemplateIntoString(SendMailService.this.velocityEngine,
                                             SendMailService.this.sendIdentRecoveryTemplate,"iso-8859-1", model);
                message.setText(text, true);
            }
        };
        mailSender.send(preparator);
        logger.trace("Sortie - Envoi de l'identifiant par mail pour parcours de recuperation de l'identifiant");
    }
    
    
    /**
     * Sends e-mail using Velocity template for the body and 
     * the properties passed in as Velocity variables.
     * 
     * @param   msg                 The e-mail message to be sent, except for the body.
     * @param   hTemplateVariables  Variables to use when processing the template. 
     */
    public void send(final SimpleMailMessage msg, 
                     final Map<String, Object> hTemplateVariables) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
               MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
               message.setTo(msg.getTo());
               message.setFrom(msg.getFrom());
               message.setSubject(msg.getSubject());
               String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/emailBody.vm","iso-8859-1", hTemplateVariables);
               logger.info("body={}", body);
               message.setText(body, true);
            }
         };
         mailSender.send(preparator);
        logger.info("Sent e-mail to '{}'.", msg.getTo());
    }
    
    
    
    
    
    /**
     * Méthode dédiée à l'envoi du mot de passe par mail pour les clients 9c
     */
    public void sendPassword9cConfirmationMail(final String emailContact, final String civilite, final String prenom,
                                               final String nom, final String identifiant, final String password) throws MailException{
        logger.debug("Entrée - Envoi du mot de passe par mail client 9c");
        final MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(final MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = null;
                message = new MimeMessageHelper(mimeMessage);
                message.setFrom(emailContact);
                message.setTo(emailContact);
                message.setSubject(sendPasswordSubject);
                final Map<String, Object> model = new HashMap<String, Object>();
                model.put("akamaiHost", SendMailService.this.akamaiHost);
                model.put("civilite", civilite);
                model.put("prenom", prenom);
                model.put("nom", nom);
                model.put("identifiant", identifiant);
                model.put("password", password);
                final String text = VelocityEngineUtils
                    .mergeTemplateIntoString(SendMailService.this.velocityEngine,
                                             SendMailService.this.sendPassword9cTemplate,"iso-8859-1", model);
                message.setText(text, true);
            }
        };
        mailSender.send(preparator);
        logger.debug("Sortie - Envoi du mot de passe par mail client 9c");
    }
    
    /**
     * Méthode dédiée à l'envoi de la notification de mise à jour de l'email de contact.
     * 
     * @param emailContact
     * @param civilite
     */
    public void sendChangeMailContactMail(final String emailContact, final String civilite) {
        logger.debug("Entree dans la methode sendChangeMailContactMail");
        final MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(final MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = null;
                message = new MimeMessageHelper(mimeMessage);
                message.setFrom(sender);
                message.setTo(emailContact);
                message.setSubject(sendChangeMailContactSubject);
                final Map<String, Object> model = new HashMap<String, Object>();
                model.put("akamaiHost", SendMailService.this.akamaiHost);
                model.put("email", emailContact);
                model.put("user.comptePrincipal.civilite", civilite);
                final String text = VelocityEngineUtils
                    .mergeTemplateIntoString(SendMailService.this.velocityEngine,
                                             SendMailService.this.sendChangeMailContactTemplate,"iso-8859-1", model);
                message.setText(text, true);
            }
        };
        mailSender.send(preparator);
        logger.debug("Sortie de la methode sendChangeMailContactMail");
    }
    
    @Required
    public void setMailSender(final JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    @Required
    public void setVelocityEngine(final VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }
    
    @Required
    public void setSendPassword9cTemplate(final String sendPassword9cTemplate) {
        this.sendPassword9cTemplate = sendPassword9cTemplate;
    }
    
//    @Required
//    public void setSendIdentRecoveryTemplate(final String sendIdentRecoveryTemplate) {
//        this.sendIdentRecoveryTemplate = sendIdentRecoveryTemplate;
//    }
    
    @Required
    public void setAkamaiHost(final String akamaiHost) {
        this.akamaiHost = akamaiHost;
    }
    
//    @Required
//    public void setSendChangeMailContactTemplate(final String sendChangeMailContactTemplate) {
//        this.sendChangeMailContactTemplate = sendChangeMailContactTemplate;
//    }
}
