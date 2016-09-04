package com.mycompany.smartalbum.back.form.validator;

import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mycompany.database.smartalbum.model.User;
import com.mycompany.database.smartalbum.utils.Constants;
import com.mycompany.services.model.commun.InfosBeanForm;
import com.mycompany.smartalbum.back.service.SmartAlbumBackService;

@Component
public class UserFormValidator implements Validator {
	
	// ----------- SERVICES ---------------------------//
	@Resource
	protected SmartAlbumBackService backService;
    
    /**
     * Effectue la validation d'un formulaire representant une personne
     * 
     * @param object, Le formulaire a valider
     * @param errors, Le conteneur spring des erreurs eventuelles
     */
    @Override
    public void validate(Object form, Errors errors) {
        InfosBeanForm userForm = (InfosBeanForm) form;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nom", "erreur.smartalbum.user.nom.invalide");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "prenom", "erreur.smartalbum.user.prenom.invalide");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "erreur.smartalbum.user.username.invalide");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "erreur.smartalbum.user.password.invalide");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "erreur.smartalbum.user.password.invalide");
        Object passwordObj = errors.getFieldValue("password");
        Object confirmPasswordObj = errors.getFieldValue("confirmPassword");
        if(passwordObj !=null && confirmPasswordObj!=null){
        	
        	String password = passwordObj.toString();
        	String confirmPassword = confirmPasswordObj.toString();
        	
        	boolean hasMinus6cars = passwordObj.toString().length()<6;
        	if(hasMinus6cars){
        		errors.rejectValue("password", "erreur.smartalbum.user.pasword.six.caracteres", "Mot de passe incorrect");
        	}
        	
        	boolean hasUppercase = !password.equals(password.toLowerCase());
        	if(!hasUppercase && !hasMinus6cars){
        		errors.rejectValue("password", "erreur.smartalbum.user.pasword.majuscule.caractere", "Mot de passe incorrect");
        	}
        	
        	boolean hasSpecial   = !password.matches("[A-Za-z0-9 ]*");
        	//hasSpecial = password.matches(".*[!@#$%^&*(){}:;,.?/\\&nbp;].*");
        	if(!hasSpecial){
        		errors.rejectValue("password", "erreur.smartalbum.user.pasword.special.caractere", "Mot de passe incorrect");
        	}
        	
        	// le mdp doit conyenir un chiffre
        	if(!password.matches(".*[0-9].*")) { 
        		errors.rejectValue("password", "erreur.smartalbum.user.pasword.nombre.caracter", "Mot de passe incorrect");
        	}
        	
        	// Les mots de passe saisie doivent être identiques
        	if(!password.equals(confirmPassword)){
        		errors.rejectValue("confirmPassword", "erreur.smartalbum.user.password.invalide", "Ce mot passe ne correspond pas celui du champs password");
        	}
        	
        	Object usernameObj = errors.getFieldValue("username");        	
        	if(checkUserExist(usernameObj.toString()))
        	{
        		errors.rejectValue("username", "erreur.smartalbum.user.pasword.login.existe", "Login déjà existant en base de données");
        	}
        	
        	Object emailObj = errors.getFieldValue("email");        	
        	if(checkEmailExist(emailObj.toString()))
        	{
        		errors.rejectValue("email", "erreur.smartalbum.user.mail.invalide", "Mail déjà existant en base de données");
        	}
        }
        // Le passwor et le confirmPassword doivent être les même
        //ValidationUtils.
        if (!Pattern.matches("^((06)|(07))[0-9]{8}$", userForm.getTelephone())) {
            errors.rejectValue("telephone", "erreur.smartalbum.user.telephone.invalide");
        }
    }
    
    private boolean checkUserExist(final String login) {
		if (backService.getUserDBService().isUserExist(login)) {
			backService.getErrorHandler().addToErrors(
					Constants.USER_WITH_THIS_LOGIN_ALREADY_EXIST);
			return true;
		}

		return false;
	}

	private boolean checkEmailExist(final String email) {
		if (backService.getUserDBService().isEmailExist(email)) {
			backService.getErrorHandler().addToErrors(
					Constants.USER_WITH_THIS_EMAIL_ALREADY_EXIST);
			return true;
		}
		return false;
	}

	private boolean checkPassword(final User user) {
		if (!user.getPassword().equals(user.getConfirmPassword())) {
			backService.getErrorHandler().addToErrors(
					Constants.CONFIRM_PASSWORD_NOT_EQUALS_PASSWORD);
			return false;
		}
		return true;
	}
    
    @Override
    public boolean supports(Class<?> clazz) {
        return InfosBeanForm.class.getClass().isAssignableFrom(clazz);
    }
    
}
