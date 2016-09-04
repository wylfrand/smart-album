package com.sfr.applications.maam.front;

import javax.annotation.Resource;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.sfr.applications.maam.front.controller.AMaamController;
import com.sfr.ws.psw.profile.v4_1.UserProfileService;
import com.sfr.xml.psw.profile.v4_1.FicheUtilisateur;

public abstract class TestController extends AbstractJUnit4SpringContextTests{
	
	private static final transient Logger log = LoggerFactory.getLogger(AMaamController.class);
	
	@Resource(name = "${userProfileService}")
	protected UserProfileService userProfileService;
	
	@Before
	public void loadSecurityContext(){
		try{
	    	FicheUtilisateur user = userProfileService.findExtendedByAuthenticationString("LOGIN=0620304059");
	    	UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, 
	    			                                                                                          null,
	    			                                                                                          new GrantedAuthority[]{new GrantedAuthorityImpl("ROLE_USER")}); 
	    	SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}catch(Exception e){
			log.warn("Erreur: ", e);
		}
	}
}