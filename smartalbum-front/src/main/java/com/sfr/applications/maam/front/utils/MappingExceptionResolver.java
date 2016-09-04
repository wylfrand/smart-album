package com.sfr.applications.maam.front.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.AccessDeniedException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * Intercepte les erreurs de la couche présentation
 * 
 * @author sco
 */
public class MappingExceptionResolver extends SimpleMappingExceptionResolver{

	private final static transient Logger log = LoggerFactory.getLogger(MappingExceptionResolver.class);

	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.SimpleMappingExceptionResolver#doResolveException(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	public ModelAndView doResolveException(HttpServletRequest request,
										   HttpServletResponse response,
										   Object handler,
										   Exception ex){
		
		if(ex instanceof AccessDeniedException){
			throw (AccessDeniedException)ex;
		}
		
		log.warn("Erreur non prévu par l'application MAAM: ", ex);
		
		return super.doResolveException(request, 
				                        response, 
				                        handler, 
				                        ex);
	}
}