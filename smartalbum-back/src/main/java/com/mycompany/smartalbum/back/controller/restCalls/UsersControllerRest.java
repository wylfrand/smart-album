package com.mycompany.smartalbum.back.controller.restCalls;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mycompany.database.smartalbum.model.User;
import com.mycompany.database.smartalbum.utils.Constants;
import com.mycompany.filesystem.utils.HashUtils;
import com.mycompany.services.utils.Constant;
import com.mycompany.services.utils.RetourReponse;
import com.mycompany.smartalbum.back.controller.ABaseController;
import com.mycompany.smartalbum.back.form.validator.PhotoAvatarValidator;
import com.mycompany.smartalbum.back.helper.SmartAlbumAuthProvider;
import com.mycompany.smartalbum.back.utils.ViewEnum;

@RestController
@RequestMapping("/usersControllerRest")
@SessionAttributes(value = { Constant.SMARTALBUM_INFOSPERSO_FORM })
public class UsersControllerRest extends ABaseController {

	@Resource
	private SmartAlbumAuthProvider smartAlbumAuthProvider;
	
	private final static transient Logger log = LoggerFactory
	.getLogger(UsersControllerRest.class);
	
	@RequestMapping(value = "/ajax/password/checkMailContact", method = RequestMethod.POST)
	public @ResponseBody RetourReponse checkContactMail(final String email,final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {
		RetourReponse reponse = new RetourReponse();
		return reponse;
	}

	@Override
	protected Logger getLoger() {
		// TODO Auto-generated method stub
		return log;
	}
}
