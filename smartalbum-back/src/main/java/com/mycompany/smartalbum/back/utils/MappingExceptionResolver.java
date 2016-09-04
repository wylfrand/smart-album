package com.mycompany.smartalbum.back.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * Intercepte les erreurs de la couche présentation
 * 
 * @author amv
 */
public class MappingExceptionResolver extends SimpleMappingExceptionResolver{

	private final static transient Logger log = LoggerFactory.getLogger(MappingExceptionResolver.class);

	
	@Override
	public ModelAndView doResolveException(HttpServletRequest request,
										   HttpServletResponse response,
										   Object handler,
										   Exception ex){
		log.warn("Erreur non prevu par l'application Smart Album: ", ex);
		
		return super.doResolveException(request, 
				                        response, 
				                        handler, 
				                        ex);
	}
}