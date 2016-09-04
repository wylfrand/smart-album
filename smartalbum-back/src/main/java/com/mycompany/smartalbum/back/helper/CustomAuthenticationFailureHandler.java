package com.mycompany.smartalbum.back.helper;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class CustomAuthenticationFailureHandler extends
		SimpleUrlAuthenticationFailureHandler {

	protected static final Logger LOG = LoggerFactory.getLogger("SMARTALBUM_AUTHENTIFICATION");

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		super.onAuthenticationFailure(request, response, exception);
		if (exception.getClass().isAssignableFrom(UsernameNotFoundException.class)) {
			// On redirige vers l'url de sélection d'une ligne
		} else if (exception.getClass().isAssignableFrom(DisabledException.class)) {
			LOG.error(exception.getMessage(), exception);
		}
		else if (exception.getClass().isAssignableFrom(BadCredentialsException.class)) {
			//request.setAttribute("statusMessage", "Authentication Failed: " + exception.getMessage()); 
		}
		//request.setAttribute("statusMessage", "Authentication Failed: " + exception.getMessage()); 
		LOG.error(exception.getMessage(), exception);
	}

}
