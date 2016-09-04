package com.sfr.applications.maam.front.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Component("httpSessionCacheManager")
public class HttpSessionCacheManager{

	private static final transient Logger log = LoggerFactory.getLogger(HttpSessionCacheManager.class);

	/**
	 * Récupère un objet du cache HTTP (Session) via sa clé
	 * 
	 * @param key La clé de l'objet en cache
	 * @return L'objet en cache
	 */
	public Object getObjectFromCache(String key){
		log.debug("On récupère du cache l'objet qui a pour clé [{}]", key);
		
		return RequestContextHolder.currentRequestAttributes().getAttribute(key, 
				                                                            RequestAttributes.SCOPE_SESSION);
	}

	/**
	 * Met un objet en cache HTTP (Session) via sa clé
	 * 
	 * @param key La clé de l'objet à mettre en cache
	 * @param object L'objet à mettre e ncache
	 */
	public void putObjectInCache(String key, 
			                     Object object){
		log.debug("On met dans le cache l'objet {} avec la clé [{}].", 
				  new Object[]{object, key});
		RequestContextHolder.currentRequestAttributes().setAttribute(key, 
				                                                     object, 
													                 RequestAttributes.SCOPE_SESSION);
	}

	
	/**
	 * Enlève un objet du cache HTTP (Session) via sa clé
	 * 
	 * @param key La clé de l'objet à supprimer
	 */
	public void removeObjectInCache(String key){
		log.debug("On supprime du cache l'objet qui a la clé [{}].", key);
		RequestContextHolder.currentRequestAttributes().removeAttribute(key, 
				                                                        RequestAttributes.SCOPE_SESSION);
	}
}