package com.mycompany.services.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Component("httpSessionCacheManager")
public class HttpSessionCacheManager{

	/**
	 * Récupère un objet du cache HTTP Session
	 * 
	 * @param key La clé associée à l'objet en cache
	 */
	public Object getObjectFromCache(String key){
		
		return RequestContextHolder.currentRequestAttributes().getAttribute(key, 
				                                                            RequestAttributes.SCOPE_SESSION);
	}


	/**
	 * Met un objet en cache HTTP Session
	 * 
	 * @param key La clé associée à l'objet en cache
	 * @param object L'objet à mettre en cache
	 */
	public void putObjectInCache(String key, 
			                     Object object){
		RequestContextHolder.currentRequestAttributes().setAttribute(key,
				                                                     object,
													                 RequestAttributes.SCOPE_SESSION);
	}


	/**
	 * Enlève un objet du cache
	 * 
	 * @param key La clé associée à l'objet en cache
	 */
	public void removeObjectInCache(String key){
		RequestContextHolder.currentRequestAttributes().removeAttribute(key, 
				                                                        RequestAttributes.SCOPE_SESSION);
	}
}