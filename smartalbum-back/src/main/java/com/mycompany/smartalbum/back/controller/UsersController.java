package com.mycompany.smartalbum.back.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.common.collect.Lists;
import com.mycompany.database.smartalbum.exception.PhotoAlbumException;
import com.mycompany.database.smartalbum.model.Sex;
import com.mycompany.database.smartalbum.model.User;
import com.mycompany.database.smartalbum.utils.Constants;
import com.mycompany.filesystem.model.FileMeta;
import com.mycompany.filesystem.utils.HashUtils;
import com.mycompany.services.model.commun.Address;
import com.mycompany.services.model.commun.InfosBeanForm;
import com.mycompany.services.utils.Constant;
import com.mycompany.services.utils.RetourReponse;
import com.mycompany.smartalbum.back.form.validator.PhotoAvatarValidator;
import com.mycompany.smartalbum.back.helper.LdapHelper;
import com.mycompany.smartalbum.back.helper.SmartAlbumAuthProvider;
import com.mycompany.smartalbum.back.utils.ViewEnum;

import opiam.admin.applis.demo.beans.Person;

@Controller
@RequestMapping("/usersController")
@SessionAttributes(value = { Constant.SMARTALBUM_INFOSPERSO_FORM })
public class UsersController extends ABaseController {

	@Resource
	private SmartAlbumAuthProvider smartAlbumAuthProvider;
	
	@Autowired
	PhotoAvatarValidator fileValidator;

	@Resource
	private LdapHelper ldapHelper;
	
	
	
	private final static transient Logger log = LoggerFactory
	.getLogger(UsersController.class);

	@RequestMapping(value = "/paintAvatarImage", method = RequestMethod.GET)
	public void paintAvatarImage(final HttpServletResponse response,
			final HttpServletRequest request) throws IOException {

		InfosBeanForm aInfosPersoForm = (InfosBeanForm) request.getSession()
		.getAttribute(Constant.SMARTALBUM_INFOSPERSO_FORM);
		if (aInfosPersoForm == null) {
			// request.getSession().setAttribute(Constant.SMARTALBUM_INFOSPERSO_FORM,
			// new InfosBeanForm());
			aInfosPersoForm = new InfosBeanForm();

		}
		FileMeta data = aInfosPersoForm.getAvatarData();
		try {
			response.setContentType(data.getFileType());
			response.setHeader("Content-disposition", "attachment; filename=\""
					+ data.getFileName() + "\"");
			FileCopyUtils.copy(aInfosPersoForm.getAvatarData().getBytes(),
					response.getOutputStream());

		} catch (IOException e) {
			log.error("impossible d'afficher l'image demandée", e);
		}
	}

	/**
	 * upload (permet d'importer un avatar)
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(
			@ModelAttribute(Constant.SMARTALBUM_INFOSPERSO_FORM) final InfosBeanForm infoPerso,
			final MultipartHttpServletRequest request, final HttpServletResponse response,
			final ModelMap model) throws Exception {

		// 1. build an iterator
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = null;
		User connectedUser = backService.getCurrentUser(true);
		String userLogin = null;
		if(connectedUser != null){
		 userLogin = backService.getCurrentUser(true).getLogin();
		}
		else{
			userLogin = Constant.DEFAULT_USER;
		}
		int nbFiles = 0;
		// 2. get each file
		while (itr.hasNext()) {
			// 2.1 get next MultipartFile
			String next = itr.next();
			mpf = request.getFile(next);
			if (mpf.getSize() > 0) {
				log.debug(mpf.getOriginalFilename() + " uploaded! "
						+ mpf.getSize());
				// 2.2 if files > 10 remove the first from the list
				// 2.3 create new fileMeta
				FileMeta fileMeta = backService.getFileUploadService()
						.computeFileMetaBeforeSavingOriginal(mpf, userLogin, true);
				// 2.4 add to files
				fileMeta.setInputStream(mpf.getInputStream());
				fileMeta.setIndexInMemory(new Long(nbFiles++));
				infoPerso.setAvatarUploaded(true);
				infoPerso.setAvatarData(fileMeta);
			} else {
				infoPerso.setAvatarUploaded(false);
				infoPerso.setAvatarData(null);
			}
			
		}
		backService.getCacheManager().putObjectInCache(Constant.SMARTALBUM_INFOSPERSO_FORM, infoPerso);
		return Constant.PAGE_INFOS_PERSO;
	}

	@RequestMapping(value = "/operation-succeed", method = RequestMethod.POST)
	public String operation_succeed(@ModelAttribute(Constant.SMARTALBUM_INFOSPERSO_FORM) final InfosBeanForm infoPerso,final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {
		infoPerso.setErrorMessage(StringUtils.EMPTY);
		model.put("newEmail", infoPerso.getEmail());
		updateInfoPerso(infoPerso);
		if(infoPerso.getLogin() != null){
			try{
				mailService.sendPassword9cConfirmationMail(infoPerso.getEmail(),infoPerso.getCiv(), infoPerso.getPrenom(), infoPerso.getNom(),infoPerso.getLogin(), infoPerso.getPassword());
			}
			catch(MailException e)
			{
				log.error("Impossible d'envoyer un mail de confirmation!", e);
				return ViewEnum.SMARTALBUM_MAIL_ERROR_VIEW.getView();
			}
		return ViewEnum.SMARTALBUM_SUCCEED_VIEW.getView();
		}
		return openCreation(response,infoPerso, model);
		
	}

	@RequestMapping(value = "/registration-failed", method = RequestMethod.POST)
	public String registration_failed(@ModelAttribute(Constant.SMARTALBUM_INFOSPERSO_FORM) final InfosBeanForm infoPerso,final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response,final PhotoAlbumException e) throws IOException {
		infoPerso.setErrorMessage(e.getMessage());
		return openCreation(response,infoPerso, model);
	}

	@RequestMapping(value = "/create-account", method = RequestMethod.POST)
	public String validate(
			@ModelAttribute(Constant.SMARTALBUM_INFOSPERSO_FORM) final InfosBeanForm infoPerso,
			final BindingResult errors, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {
		

		userFormValidator.validate(infoPerso, errors);
		//return registration_failed(infoPerso,model, request, response,e);
		// The login should be alpha numeric
		if(infoPerso.getUsername() == null || !StringUtils.isAlphanumeric(infoPerso.getUsername().replaceAll("_", "").replaceAll("-", "")))
		{
			return registration_failed(infoPerso,model, request, response,new PhotoAlbumException("Le login doit être alpha numérique"));
		}

		if (errors.hasErrors()) {
			model.put(Constant.SMARTALBUM_USER_CREATE_SUCCESS, false);

			RetourReponse retour = new RetourReponse();
			retour.setResult(Boolean.FALSE);
			return Constant.PAGE_INFOS_PERSO;
		}

		InfosBeanForm aInfosPersoForm = (InfosBeanForm) request.getSession()
		.getAttribute(Constant.SMARTALBUM_INFOSPERSO_FORM);
		if (aInfosPersoForm == null) {
			// request.getSession().setAttribute(Constant.SMARTALBUM_INFOSPERSO_FORM,
			// new InfosBeanForm());
			aInfosPersoForm = new InfosBeanForm();
			model.put(Constant.SMARTALBUM_INFOSPERSO_FORM, aInfosPersoForm);

		} else {
			model.put(Constant.SMARTALBUM_INFOSPERSO_FORM, aInfosPersoForm);
		}

		// Enregistrement de l'utilisateur dans l'annuaire
		Person ldapPerson = null;
		try {
			ldapPerson = ldapHelper.createLdapUser(request, aInfosPersoForm);
		} catch (PhotoAlbumException e) {
			return registration_failed(infoPerso,model, request, response,e);
		}
		// Enregistrement de l'utilisateur en BDD
		try
		{
			if(!dbRegister(getDbUser(aInfosPersoForm)))
			{
				ldapHelper.deleteLdapUser(request,ldapPerson);
				ldapPerson = null;
				return registration_failed(infoPerso,model, request, response,new PhotoAlbumException("Un utilisateur ayant l'email "+
						aInfosPersoForm.getEmail()+" ou le login "+aInfosPersoForm.getLogin()+ "existe déjà!"));
			}
		}
		catch(Exception e)
		{
			try {
				if(ldapPerson!=null)
				{
					ldapHelper.deleteLdapUser(request,ldapPerson);
				}
			} catch (PhotoAlbumException e1) {
				log.error("erreur technique : ",e1);
			}
			return registration_failed(infoPerso,model, request, response,new PhotoAlbumException(e.getMessage()));
		}
		model.put(Constant.SMARTALBUM_USER_CREATE_SUCCESS, true);
		return operation_succeed(infoPerso,model, request, response);
	}

	@RequestMapping(value = "/open-creation", method = RequestMethod.GET)
	public String openCreation(
			final HttpServletResponse response,
			@ModelAttribute(Constant.SMARTALBUM_INFOSPERSO_FORM) final InfosBeanForm infoPerso, final ModelMap model) throws IOException {
		if(infoPerso == null){
			model.put(Constant.SMARTALBUM_INFOSPERSO_FORM, new InfosBeanForm());
		}
		model.put(Constant.SMARTALBUM_INFOSPERSO_FORM, infoPerso);
		backService.getCacheManager().putObjectInCache(Constant.SMARTALBUM_INFOSPERSO_FORM, infoPerso);
		return Constant.PAGE_INFOS_PERSO;

	}

	@RequestMapping(value = "/ajax/check-address", method = RequestMethod.POST)
	public @ResponseBody
	ArrayList<Address> normAddress(
			@RequestParam final String adresse,
			@RequestParam final String codePostal,
			@RequestParam final String ville,
			@ModelAttribute(Constant.SMARTALBUM_INFOSPERSO_FORM) final InfosBeanForm infoPerso,
			final ModelMap model) throws IOException {
		return Lists.newArrayList();
	}

	@RequestMapping(value = "/create-user", method = RequestMethod.POST)
	public String create(
			@ModelAttribute(Constant.SMARTALBUM_INFOSPERSO_FORM) final InfosBeanForm infoPerso,
			final BindingResult errors, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {
		return ViewEnum.SMARTALBUM_VIEW_CREATE_USER.getView();
	}
	
	@RequestMapping(value = "/ajax/password/editNewPassword", method = RequestMethod.POST)
	public String resetPassword(@ModelAttribute(Constant.SMARTALBUM_INFOSPERSO_FORM) final InfosBeanForm infoPerso, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {
		model.put("email", infoPerso.getEmail());
		return ViewEnum.SMARTALBUM_VIEW_RESET_PASSWORD.getView();
	}
	
	
	@RequestMapping(value = "/password/modifyPassword", method = RequestMethod.POST)
	public String processResetPassword(
			@ModelAttribute(Constant.SMARTALBUM_INFOSPERSO_FORM) final InfosBeanForm infoPerso,
			final BindingResult errors, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {
		// Traitement avec plusieurs reponses possibles
		passwordFormValidator.validate(infoPerso, errors);
		if (errors.hasErrors()) {
			return ViewEnum.SMARTALBUM_VIEW_RESET_PASSWORD.getView();
		}
		Person person = null;
		try {
			person = ldapHelper.searchPerson(request, infoPerso.getLogin());
		} catch (PhotoAlbumException e) {
			errors.rejectValue("oldPassword", "erreur.smartalbum.user.tech.password",
					"Pour des raisons techniques votre mot de passe ne pas être mis à jour, veuillez reéssayer ultérieurement.");
			log.error("Impossible de charger l'utilisateur technique ldap", e);
			return ViewEnum.SMARTALBUM_VIEW_RESET_PASSWORD.getView();
		}
		
		if(!person.getPassword().equals(infoPerso.getOldPassword()))
		{
			errors.rejectValue("oldPassword", "erreur.smartalbum.user.old.password",
					"Votre mot de passe actuelle est incorrecte. Merci de le ressaisir ...");
			return ViewEnum.SMARTALBUM_VIEW_RESET_PASSWORD.getView();
		}
		
		// Now everything is OK we can reset the password !
		if (person != null) {
			infoPerso.setPassword(infoPerso.getConfirmPassword());
			// Modify Password in ldap directory
			try {
				if (ldapHelper.modifyPerson(request, person, infoPerso.getConfirmPassword())) {
					// Mise à jour de la base de donnée
					User user = backService.getUserDBService().findUserByEmail(infoPerso.getEmail());
					user.setPasswordHash(HashUtils.encodePasswd(infoPerso.getConfirmPassword()));
					backService.getUserDBService().updateUser(user);
				} else {
					errors.rejectValue("oldPassword", "erreur.smartalbum.user.update.password",
							"Le mot de passe actuelle est incorrect, merci de le corriger");
					log.error("Impossible de mettre à jour le mot de passe dans l'annuaire");
					return ViewEnum.SMARTALBUM_VIEW_RESET_PASSWORD.getView();
				}
			} catch (PhotoAlbumException e) {
				errors.rejectValue("oldPassword", "erreur.smartalbum.user.tech.password",
						"Le mot de passe actuelle est incorrect, merci de le corriger");
				log.error("Impossible de mettre à jour le mot de passe dans l'annuaire", e);
				return ViewEnum.SMARTALBUM_VIEW_RESET_PASSWORD.getView();
			}

		}
		return operation_succeed(infoPerso, model, request, response);
	}
	
	@RequestMapping(value = "/ajax/password/checkMailContact/{email}", method = RequestMethod.POST)
	public String checkContactMail(@ModelAttribute(Constant.SMARTALBUM_INFOSPERSO_FORM) final InfosBeanForm infoPerso,final @PathVariable("email") String email,final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {
		
		infoPerso.setEmail(email);
		switch (infoPerso.getAction()) {
		case Constant.CHANGE_PASSWORD_VAR:
			updateInfoPerso(infoPerso);
			return resetPassword(infoPerso, model, request,response);
		case Constant.SEND_PASSWORD_VAR:
			return operation_succeed(infoPerso,model, request, response);
		default:
			break;
		}
		return printEmailCheckForm(infoPerso,infoPerso.getAction(), model, request, response);
	}
	
	@RequestMapping(value = "/password/printEmailCheckForm/{action}", method = RequestMethod.GET)
	public String printEmailCheckForm(@ModelAttribute(Constant.SMARTALBUM_INFOSPERSO_FORM) final InfosBeanForm infoPerso,@PathVariable("action") String action,final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {
		infoPerso.setAction(action);
		return ViewEnum.SMARTALBUM_VIEW_CONFIRM_MAIL_PASSWORD.getView();
	}

	/***
	 * URL: /shelvesController/publicShelves
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ajax/userShelves", method = RequestMethod.POST)
	public String getUserShelves(final ModelMap model) {

		User currentUser = backService.getCurrentUser(false);

		model.put(Constant.SMARTALBUM_SELECTED_USER, currentUser);

		return ViewEnum.ALBUM_CREATION_VIEW.getView();
	}

	
	/**
	 * Method, that invoked when user try to register in the application. If
	 * registration was successfull, user immediately will be loginned to the
	 * system.
	 * 
	 * @param user
	 *            - user object, that will be passed to registration procedure.
	 */
	public boolean dbRegister(final User user) {
		user.setPasswordHash(HashUtils.encodePasswd(user.getPassword()));
		// This check is actual only on livedemo server to prevent hacks.
		// Only admins can mark user as pre-defined
		user.setPreDefined(true);
		// if(!handleAvatar(user)){
		// return false;
		// }
		try {
			backService.getUserDBService().register(user);
		} catch (Exception e) {
			log.error("Erreur lors de l'enregistrement de l'utilisateur :", e);
			backService.getErrorHandler().addToErrors(
					Constants.REGISTRATION_ERROR);
			return false;
		}

		return true;
	}
	
	private void updateInfoPerso(final InfosBeanForm infoPerso)
	{
		if(infoPerso.getLogin() == null){
			User user = backService.getUserDBService().findUserByEmail(infoPerso.getEmail());
			if(user!=null){
				infoPerso.setCiv(user.getSex() == Sex.MALE ? "M" : "Mme");
				infoPerso.setPrenom(user.getFirstName());
				infoPerso.setNom(user.getSecondName());
				infoPerso.setLogin(user.getLogin());
				infoPerso.setPassword(user.getPassword());
			}
		}
	}

	@Override
	protected Logger getLoger() {
		return log;
	}
}
