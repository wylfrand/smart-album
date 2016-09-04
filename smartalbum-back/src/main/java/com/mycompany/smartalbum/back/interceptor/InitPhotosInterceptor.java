package com.mycompany.smartalbum.back.interceptor;

import java.util.LinkedHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mycompany.filesystem.model.CheckedFile;
import com.mycompany.services.model.commun.InfosBeanForm;
import com.mycompany.services.utils.Constant;
import com.mycompany.services.utils.HttpSessionCacheManager;
import com.mycompany.smartalbum.back.helper.SmartAlbumAuthProvider;
import com.mycompany.smartalbum.back.service.SmartAlbumBackService;

/**
 * Intercepteur gérant l'importation des images de l'utilisateur connecté
 */
public class InitPhotosInterceptor extends HandlerInterceptorAdapter {
    
    private final static transient Logger log = LoggerFactory.getLogger(InitPhotosInterceptor.class);
    
    @Resource
    protected SmartAlbumBackService backService;
    
    @Resource
	private SmartAlbumAuthProvider smartAlbumAuthProvider;

    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	
    	log.debug(Constant.MESSAGE_INIT_PHOTOS);
    	
    	Object obj = null;
    	Object objInfosPerso = null;
    	
    	HttpSessionCacheManager objCache = backService.getCacheManager();
    	
    	if(objCache !=null)
    	{
    		obj = objCache.getObjectFromCache(Constant.SMARTALBUM_CHECKED_PICTURES);
    		objInfosPerso = objCache.getObjectFromCache(Constant.SMARTALBUM_INFOSPERSO_FORM);
    	}
    	
    	if(obj == null)
    	{
    		backService.getCacheManager().putObjectInCache(Constant.SMARTALBUM_CHECKED_PICTURES,new LinkedHashMap<String, CheckedFile>());
    	}
    	
    	if(objInfosPerso == null)
    	{
    		backService.getCacheManager().putObjectInCache(Constant.SMARTALBUM_INFOSPERSO_FORM,new InfosBeanForm());
    	}
    	
        return true;
    }
    
    
    
}
