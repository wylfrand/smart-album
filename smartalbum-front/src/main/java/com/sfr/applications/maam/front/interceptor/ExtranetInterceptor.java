package com.sfr.applications.maam.front.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sfr.applications.maam.front.utils.HttpSessionCacheManager;
import com.sfr.springframework.security.helper.AuthenticationHelper;

/**
 * Intercepteur gérant le header d'accès d'internet ou d'extranet
 */
public class ExtranetInterceptor extends HandlerInterceptorAdapter {
    
    private static final long serialVersionUID = 1L;
    
    private final static transient Logger log = LoggerFactory.getLogger(ExtranetInterceptor.class);
    
    private final static String MODE_EXTRANET = "MODE_EXTRANET";
    
    @Resource(name = "httpSessionCacheManager")
    protected HttpSessionCacheManager cacheManager;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(cacheManager.getObjectFromCache(MODE_EXTRANET) == null){
            if( AuthenticationHelper.isSuperUser() ){
                log.debug("Information SuperUser from Authtification [true]");
                cacheManager.putObjectInCache(MODE_EXTRANET, true);
            } else {
                cacheManager.putObjectInCache(MODE_EXTRANET, false);
            }
        }
        return true;
    }
}
