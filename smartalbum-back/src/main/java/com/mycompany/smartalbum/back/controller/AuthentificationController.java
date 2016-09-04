package com.mycompany.smartalbum.back.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.google.gson.Gson;
import com.mycompany.services.model.commun.InfosBeanForm;
import com.mycompany.services.utils.Constant;
import com.mycompany.services.utils.RetourReponse;
import com.mycompany.smartalbum.back.resetPassword.ResetPwdForm;

/**
 * Controlleur pour afficher la page d'accueil / Controler for the welcome page
 * of smartalbum
 * 
 * @author amvou
 */
@Controller
@SessionAttributes(value = { Constant.SMARTALBUM_INFOSPERSO_FORM })
public class AuthentificationController extends ABaseController {

	private final static String LOGIN_PAGE = "page/login/index";
	private final static transient Logger LOG = LoggerFactory
	.getLogger(AuthentificationController.class);

	/**
	 * Affiche la page d'authentification de SMARTALBUM / Print the
	 * authentication page of smartalbum
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ajax/authentification/action/login", method = RequestMethod.GET)
	public @ResponseBody RetourReponse isAuthenticated(
			final HttpServletRequest request, final ModelMap model) {

		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		RetourReponse response = new RetourReponse();

		if (!authentication.isAuthenticated()) {
			response.setResult(false);
			response.setResultObject(request.getAttribute("statusMessage"));
		}
		return response;
	}

	@RequestMapping(value = "/authentification/login/ok", method = RequestMethod.GET)
	public @ResponseBody String ok() {
		// RetourReponse response = new RetourReponse();
		// response.setResult(true);
		// response.setResultObject(StringUtils.EMPTY);
		// return response;
		Gson gson = new Gson();

		return gson.toJson(Boolean.TRUE);
	}

	@RequestMapping(value = "/authentification/login/error", method = RequestMethod.GET)
	public @ResponseBody String error(final ModelMap model) {
		model.put(Constant.ERROR_DETAIL_ATTR, backService.getCacheManager()
				.getObjectFromCache(Constant.ERROR_DETAIL_ATTR));
		RetourReponse response = new RetourReponse();
		response.setResult(false);
		Gson gson = new Gson();
		response.setResultObject(backService.getCacheManager()
				.getObjectFromCache(Constant.ERROR_DETAIL_ATTR));
		return gson.toJson(response);
	}

	/**
	 * Affiche la page d'authentification de SMARTALBUM / Print the
	 * authentication page of smartalbum
	 * 
	 * @return
	 */
	@RequestMapping(value = "/authentification/login", method = RequestMethod.GET)
	public String homeIndex(
			@ModelAttribute("resetPwdForm") final ResetPwdForm resetPwdForm,
			final HttpServletRequest request, final ModelMap model) {

		InfosBeanForm aInfosPersoForm = (InfosBeanForm) request.getSession()
		.getAttribute(Constant.SMARTALBUM_INFOSPERSO_FORM);
		if (aInfosPersoForm == null) {
			request.getSession().setAttribute(
					Constant.SMARTALBUM_INFOSPERSO_FORM, new InfosBeanForm());
			model.put(Constant.SMARTALBUM_INFOSPERSO_FORM, new InfosBeanForm());
		} else {
			model.put(Constant.SMARTALBUM_INFOSPERSO_FORM, aInfosPersoForm);
		}
		model.put("resetPwdForm", new ResetPwdForm());
		return LOGIN_PAGE;
	}

	@RequestMapping(value = "/authentification/logout.html", method = RequestMethod.GET)
	public String logout(final HttpServletRequest request, final ModelMap model) {

		// DO something
		return LOGIN_PAGE;
	}

	@Override
	protected Logger getLoger() {
		return LOG;
	}
}