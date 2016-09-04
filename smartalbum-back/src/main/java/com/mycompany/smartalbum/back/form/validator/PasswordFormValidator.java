package com.mycompany.smartalbum.back.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mycompany.services.model.commun.InfosBeanForm;

@Component
public class PasswordFormValidator implements Validator {
	
    /**
     * Effectue la validation du mot de passe d'une personne
     * 
     * @param object, Le formulaire a valider
     * @param errors, Le conteneur spring des erreurs eventuelles
     */
    @Override
    public void validate(Object form, Errors errors) {
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "oldPassword", "erreur.smartalbum.user.password.invalide");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "erreur.smartalbum.user.password.invalide");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "erreur.smartalbum.user.password.invalide");
        
        Object oldPasswordObj = errors.getFieldValue("oldPassword");
        String oldPassword = null;
        if(oldPasswordObj != null){
        	oldPassword = oldPasswordObj.toString();
        }
        
        // A ce niveau, on sait que l'ancien mot de passe est correct
        Object passwordObj = errors.getFieldValue("password");
        Object confirmPasswordObj = errors.getFieldValue("confirmPassword");
        if(passwordObj !=null && confirmPasswordObj!=null){
        	String password = passwordObj.toString();
        	boolean hasMinus6cars = passwordObj.toString().length()<6;
        	if(hasMinus6cars){
        		errors.rejectValue("password", "erreur.smartalbum.user.pasword.six.caracteres", "Le mot de passe doit contenir au moins 6 caractères");
        	}
        	boolean hasUppercase = !password.equals(password.toLowerCase());
        	if(!hasUppercase){
        		errors.rejectValue("password", "erreur.smartalbum.user.pasword.majuscule.caractere", "Le mot de passe doit avoir au moins une majuscule");
        	}
        	boolean hasSpecial   = !password.matches("[A-Za-z0-9 ]*");
        	//hasSpecial = password.matches(".*[!@#$%^&*(){}:;,.?/\\&nbp;].*");
        	if(!hasSpecial){
        		errors.rejectValue("password", "erreur.smartalbum.user.pasword.special.caractere", "Un caracère spéciale est requis pour le mot de passe");
        	}
        	// le mdp doit conyenir un chiffre
        	if(!password.matches(".*[0-9].*")) { 
        		errors.rejectValue("password", "erreur.smartalbum.user.pasword.nombre.caracter", "Le mot de passe doit contenir un chiffre");
        	}
        	// Les mots de passe saisie doivent être identiques
        	String confirmPassword = confirmPasswordObj.toString();
        	if(!password.equals(confirmPassword)){
        		errors.rejectValue("confirmPassword", "erreur.smartalbum.user.password.same.password", "Le mot de passe saisie doit être identique au précédent!");
        	}
        	
        	if(password.equals(oldPassword)){
        		errors.rejectValue("password", "erreur.smartalbum.user.password.not.same.password", "Le mot de passe saisie doit être identique au précédent!");
        	}
        }
        
    }
    
    @Override
    public boolean supports(Class<?> clazz) {
        return InfosBeanForm.class.getClass().isAssignableFrom(clazz);
    }
    
}
