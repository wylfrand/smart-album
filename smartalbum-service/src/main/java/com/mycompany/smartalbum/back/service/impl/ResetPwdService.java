//package com.mycompany.smartalbum.back.service.impl;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Random;
//
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Required;
//
//import com.sfr.composants.asc.exception.AscException;
//import com.sfr.composants.asc.exception.DisabledAccountException;
//import com.sfr.composants.asc.exception.LoginNotFoundException;
//import com.sfr.composants.asc.gestionsecurite.NotificationType;
//import com.sfr.composants.asc.service.AscService;
//import com.sfr.psw.selfcare.password.exception.common.AdslServiceException;
//import com.sfr.psw.selfcare.password.exception.common.CommonServiceException;
//import com.sfr.psw.selfcare.password.exception.common.MobileServiceException;
//import com.sfr.psw.selfcare.password.exception.common.TechnicalServiceException;
//import com.sfr.psw.selfcare.password.exception.common.validation.DisabledUserServiceException;
//import com.sfr.psw.selfcare.password.exception.common.validation.LoginIsMailContactServiceException;
//import com.sfr.psw.selfcare.password.exception.common.validation.NoContactEMailServiceException;
//import com.sfr.psw.selfcare.password.exception.common.validation.UserNotFoundServiceException;
//import com.sfr.psw.selfcare.password.exception.resetpwd.adsl.BalMissingInformationServiceException;
//import com.sfr.psw.selfcare.password.exception.resetpwd.adsl.InvalidEmailServiceException;
//import com.sfr.psw.selfcare.password.exception.resetpwd.adsl.InvalidPasswordServiceException;
//import com.sfr.psw.selfcare.password.exception.resetpwd.adsl.NotAdslUserServiceException;
//import com.sfr.psw.selfcare.password.exception.resetpwd.adsl.PasswordsNotMatchServiceException;
//import com.sfr.psw.selfcare.password.exception.resetpwd.adsl.UnknownEmailServiceException;
//import com.sfr.psw.selfcare.password.exception.resetpwd.mobile.InvalidInputServiceException;
//import com.sfr.psw.selfcare.password.exception.resetpwd.mobile.NotActiveContractServiceException;
//import com.sfr.psw.selfcare.password.exception.resetpwd.mobile.NotMobileUserServiceException;
//import com.sfr.psw.selfcare.password.exception.resetpwd.mobile.SecondaryAdslProfilServiceException;
//import com.sfr.psw.selfcare.password.exception.resetpwd.mobile.UnknownLoginServiceException;
//import com.sfr.psw.selfcare.password.model.FichePasswordSelfcare;
//import com.sfr.psw.selfcare.password.model.LigneFixe;
//import com.sfr.psw.selfcare.password.model.LigneMobile;
//import com.sfr.psw.selfcare.password.service.util.CommonServiceUtils;
//import com.sfr.psw.selfcare.password.userprofile.light.UPSLightClientParsed;
//
//public class ResetPwdService {
//    
//    private static Logger logger = LoggerFactory.getLogger(ResetPwdService.class);
//    private final static String USER_BLOQUE = "BLOQUE";
//    private final static String USER_SUSP = "SUSPENDU";
//    
//    public final static String sfrClientUsernamePattern = "sfr.fr";
//    
//    /*
//     * Generation de password
//     */
//    public final static String ALPHANUMERIC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
//    public final static Random RND = new Random();
//    /*
//     * Types de notification
//     */
//    public final static String NOTIFICATION_EMAIL = "EMAIL";
//    public final static String NOTIFICATION_SMS = "SMS";
//    
//    /*
//     * PROFIL AVEC MOBILE
//     */
//    public final static String AVEC_MOBILE = "A";
//    
//    // Constants
//    public final static String OPERATEUR_SRR = "SRR";
//
//    private CommonServiceUtils commonServiceUtils;
//    
//    private AscService ascService;
//    
//    
//    /**
//     * Common method for adsl users password reinitialisation
//     * 
//     * @param login
//     * @return String : user email contact where pwd is sent
//     * @throws LoginIsMailContactServiceException
//     * @throws InvalidEmailServiceException
//     * @throws InvalidPasswordServiceException
//     * @throws BalMissingInformationServiceException
//     * @throws PasswordsNotMatchServiceException
//     * @throws UnknownEmailServiceException
//     * @throws AdslServiceException
//     * @throws NoContactEMailServiceException
//     * @throws NotAdslUserServiceException
//     * @throws UserNotFoundServiceException
//     * @throws DisabledUserServiceException
//     * @throws TechnicalServiceException
//     */
//    public String resetAdslPwd(String login) throws LoginIsMailContactServiceExcetion, InvalidEmailServiceException,
//
//        logger.debug("Sortie de la méthode ResetPwdService.resetAdslPwd()");
//        return emailContact;
//    }
//    
//    /**
//     * 
//     * @param login
//     * @param msisdn
//     * @return
//     * @throws MobileServiceException
//     * @throws DisabledUserServiceException
//     */
//	public Notification resetPwdByNotificationForFixe(String login, String msisdn ) throws MobileServiceException, DisabledUserServiceException{
//		logger.debug("Entrée dans la méthode ResetPwdService.resetPwdByNotification()");
//
//		try {
//			// tentative d'appel de l'ASC pour ré-initialiser le mot de passe
//			logger.debug("Parametres d'appel reinitialisation via asc : [login="
//					+ login + "] [notification=" + NotificationType.SMS + "] ");
//			com.sfr.composants.asc.gestionsecurite.Notification notificationASC = ascService
//					.resetPassword(login, NotificationType.SMS, msisdn, true);
//			return new Notification(EnumMedia.SMS,
//					notificationASC.getNotificationAdresseMedia());
//		} catch (LoginNotFoundException e) {
//			logger.debug(
//					"Utilisateur non present dans l'asc LOGIN : {}",
//					login);
//			throw new MobileServiceException(e);
//		} catch (DisabledAccountException e) {
//			throw new DisabledUserServiceException(e);
//		} catch (AscException e) {
//			logger.debug(
//					"Erreur technique de l'opération asc:resetPassword [login="
//							+ login + "] : " + e.getMessage(), e);
//			throw new MobileServiceException(e);
//		}
//
//	}
//	
//	/**
//     * Common method for mobile users password
//     * 
//     * @param login
//     * @param notification mandatory (SMS|EMAIL)
//     * @param restCall determine if call from rest application
//     * @return Notification : The informations of notification
//     * @throws UserNotFoundServiceException
//     * @throws UnknownLoginServiceException
//     * @throws InvalidInputServiceException
//     * @throws NotMobileUserServiceException
//     * @throws SecondaryAdslProfilServiceException
//     * @throws NotActiveContractServiceException
//     * @throws MobileServiceException
//     * @throws DisabledUserServiceException
//     * @throws NoContactEMailServiceException
//     * @throws LoginIsMailContactServiceException
//     * @throws CommonServiceException
//     */
//    public Notification resetPwdByNotification(String login, EnumMedia notificationMedia, boolean restCall)
//        throws UserNotFoundServiceException, UnknownLoginServiceException, InvalidInputServiceException,
//        NotMobileUserServiceException, SecondaryAdslProfilServiceException, NotActiveContractServiceException,
//        MobileServiceException, DisabledUserServiceException, NoContactEMailServiceException,
//        LoginIsMailContactServiceException, CommonServiceException, Exception {
//        logger.debug("Entrée dans la méthode ResetPwdService.resetPwdByNotification()");
//        Notification finalNotification = null;
//        FichePasswordSelfcare utilisateur = upsLightClientParsed.findByLogin(login, null);
//        LigneMobile ligneMobile = !utilisateur.getLignesMobiles().isEmpty()
//            ? utilisateur.getLignesMobiles().get(0) : null;
//            
//        if (ligneMobile != null) {
//            // verification statut contrat client
//            if (ligneMobile.getStatutCsu() != null) {
//                
//                if (Arrays.asList(USER_BLOQUE, USER_SUSP).contains(ligneMobile.getStatutCsu().toString())) {
//                    throw new DisabledUserServiceException(
//                                                           "Contrat de l'utilisateur desactive. Reinitialisation impossible.");
//                }
//            }
//            String media = notificationMedia.getMedia();
//            if (StringUtils.equals(media, NOTIFICATION_SMS)) {// notification par SMS
//                try {
//                    // Asc issue cf support-413 / PWDSELF-141
//                    login = ligneMobile.getMsisdn();
//                    // tentative d'appel de l'ASC pour ré-initialiser le mot de passe
//                    logger.debug("Parametres d'appel reinitialisation via asc : [login=" + login + "] [notification="
//                                 + NotificationType.SMS + "] ");
//                    com.sfr.composants.asc.gestionsecurite.Notification notificationASC = ascService
//                        .resetPassword(login, NotificationType.SMS, login, true);
//                    finalNotification = new Notification(EnumMedia.SMS, notificationASC.getNotificationAdresseMedia());
//                } catch (LoginNotFoundException e) {
//                    logger.debug("Utilisateur mobile non present dans l'asc LOGIN : {}", login);
//                    throw new MobileServiceException(e);
//                } catch (DisabledAccountException e) {
//                    throw new DisabledUserServiceException(e);
//                } catch (AscException e) {
//                    logger.debug("Erreur technique de l'opération asc:resetPassword [login=" + login + "] : "
//                                     + e.getMessage(), e);
//                    throw new MobileServiceException(e);
//                }
//            } else {
//                // NB : cette reinitialisation par mail n'est plus d'actualité coté UI. Cependant toujours utilisé côté WS jusqu'à
//                // refonte des
//                // applis Iphone pour utilisation du token Asc
//                if (StringUtils.equals(media, NOTIFICATION_EMAIL)) {// notification par email
//                    try {
//                        String emailContact = getValidContactEmail(utilisateur);
//                        // tentative d'appel de l'ASC pour ré-initialiser le mot de passe
//                        logger.debug("Parametres d'appel reinitialisation via asc : [login=" + login
//                                     + "] [notification=" + NotificationType.EMAIL + "] [email de contact="
//                                     + emailContact + "] ");
//                        com.sfr.composants.asc.gestionsecurite.Notification notificationASC = ascService
//                            .resetPassword(login, NotificationType.EMAIL, emailContact, true);
//                        finalNotification = new Notification(EnumMedia.EMAIL,
//                                                             notificationASC.getNotificationAdresseMedia());
//                        if (notificationASC.getNotificationMedia().equals(NotificationType.SMS))
//                            finalNotification.setNotificationMedia(EnumMedia.SMS);
//                    } catch (LoginNotFoundException e) {// utilisateur non trouvé dans l'ASC :
//                        logger.debug("Utilisateur mobile non present dans l'asc apres migration LOGIN : {}", login);
//                        // Remonter une erreur technique dans ce cas precis car migration doit etre effectuee.
//                        throw new MobileServiceException(e);
//                        
//                    } catch (DisabledAccountException e) {
//                        throw new DisabledUserServiceException(e);
//                    } catch (AscException e) {
//                        logger.debug("Erreur technique lors de l'appel d'une opération asc [login=" + login + "] : "
//                                     + e.getMessage(), e);
//                        throw new MobileServiceException(e);
//                    } catch (CommonServiceException e) {
//                        throw new CommonServiceException(e);
//                        
//                    }
//                }
//            }
//        } else {
//            throw new NotMobileUserServiceException("User is not mobile client");
//        }
//        logger.debug("Sortie de la méthode ResetPwdService.resetPwdByNotification()");
//        return finalNotification;
//        
//    }
//    
//    /**
//     * Utilise l'asc pour recuperer la liste de tous les logins. Retourne un mail de contact different de la liste des logins.
//     * 
//     * @param utilisateur
//     * @return
//     * @throws NoContactEMailServiceException
//     * @throws LoginIsMailContactServiceException
//     * @throws CommonServiceException
//     */
//    public String getValidContactEmail(FichePasswordSelfcare utilisateur) throws NoContactEMailServiceException,
//        LoginIsMailContactServiceException, CommonServiceException {
//        List<String> listLogins = utilisateur.getLogins();
//        List<String> listEmails = new ArrayList<String>();
//        if (StringUtils.isNotBlank(utilisateur.getEmailDeSecours())) {// on doit prendre l'email de secours en priorité ; on
//            // l'ajoute donc en premier dans la liste
//            listEmails.add(utilisateur.getEmailDeSecours());
//        }
//        // recuperation email de contact.
//        String emailContactMobile = utilisateur.getEmailDeContact1();
//        if (StringUtils.isNotBlank(emailContactMobile)) {
//            listEmails.add(emailContactMobile);
//        }
//        if (listEmails.isEmpty()) {
//            throw new NoContactEMailServiceException("User without any contact email");
//        }
//        
//        String validEmail = StringUtils.EMPTY;
//        if(utilisateur.isInternaute()){
//            validEmail = listEmails.get(0);
//        }
//        else
//        {
//            for (String email : listEmails) {
//                if (!listLogins.contains(email)) {
//                    validEmail = email;
//                    logger.debug("Selected contact email is : {}", validEmail);
//                    break;
//                }
//            }
//        }
//        if (StringUtils.isBlank(validEmail)) {
//            throw new LoginIsMailContactServiceException("User login is mail contact. One of user login is :"
//                                                         + utilisateur.getLogins().get(0));
//        }
//        return validEmail;
//    }
//    
//    /**
//     * Recuperer un mobile utilise pour contacter le client.
//     * 
//     * @param utilisateur
//     * @return
//     */
//    public String getValidMobileContact(FichePasswordSelfcare utilisateur){
//    	String mobileContact = StringUtils.EMPTY;
//    	if ( StringUtils.isNotBlank(utilisateur.getTelephoneMobileDeSecours())){
//    		mobileContact = utilisateur.getTelephoneMobileDeSecours();
//    	}else if (StringUtils.isNotBlank(utilisateur.getTelephoneMobileDeContact())){
//    		mobileContact = utilisateur.getTelephoneMobileDeContact();
//    	}
//    	return mobileContact;
//    }
//    
//    /**
//     * Methode de creation du token via Asc
//     * 
//     * @param login
//     * @param url
//     * @param notificationType
//     * @param notificationAdresse
//     * @throws Exception
//     */
//    public void createAscToken(String login, String url, NotificationType notificationType, String notificationAdresse)
//        throws Exception {
//        String message = "Entree dans la methode createAscToken. Params : [login : {}], [notificationAdresse : {}], [notificationType : {}], [url : {}]";
//        logger.debug(message, new Object[] {
//            login, notificationAdresse, notificationType, url
//        });
//        ascService.createToken(login, url, true, notificationType, notificationAdresse);
//        logger.debug("Sortie de la methode createAscToken");
//    }
//    
//    /**
//     * Réinitialisation mdp des clients SBO via l'asc.La notificationMedia est à "FIXE".L'adresse de notification n'est pas renseignée.
//     * 
//     * @param login
//     * @throws MobileServiceException
//     * @throws DisabledUserServiceException
//     * @throws CommonServiceException
//     */
//    public String resetPwdForSBO(String login) throws MobileServiceException, DisabledUserServiceException,
//        CommonServiceException {
//        logger.debug("Entree dans la methode resetPwdForSBO. [Login : {}]", login);
//        try {
//            FichePasswordSelfcare utilisateur = upsLightClientParsed.findByLogin(login, null);
//            String ndi = commonServiceUtils.retrieveSboNdi(utilisateur);
//            ascService.resetPassword(ndi, NotificationType.FIXE, null, true);
//            logger.debug("Sortie de la methode resetPwdForSBO");
//            return ndi;
//        } catch (LoginNotFoundException e) {// utilisateur non trouvé dans l'ASC :
//            logger.debug("Utilisateur mobile non present dans l'asc apres migration LOGIN : {}", login);
//            // Remonter une erreur technique dans ce cas precis car migration doit etre effectuee.
//            throw new MobileServiceException(e);
//        } catch (DisabledAccountException e) {
//            throw new DisabledUserServiceException(e);
//        } catch (AscException e) {
//            logger.debug("Erreur technique lors de l'appel d'une opération asc [login=" + login + "] : "
//                             + e.getMessage(), e);
//            throw new MobileServiceException(e);
//        } catch (Exception e) {
//            throw new CommonServiceException(e);
//        }
//    }
//    
//    @Required
//    public void setCommonServiceUtils(CommonServiceUtils commonServiceUtils) {
//        this.commonServiceUtils = commonServiceUtils;
//    }
//    
//    @Required
//    public void setUpsLightClientParsed(final UPSLightClientParsed upsLightClientParsed) {
//        this.upsLightClientParsed = upsLightClientParsed;
//    }
//    
//    @Required
//    public void setAscService(AscService ascService) {
//        this.ascService = ascService;
//    }
//    
//}
