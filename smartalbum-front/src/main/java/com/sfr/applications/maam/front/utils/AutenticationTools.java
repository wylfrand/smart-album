package com.sfr.applications.maam.front.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mycompany.services.model.commun.enumeration.TypeCanalExtranet;
import com.mycompany.services.model.commun.enumeration.TypeLoginExtranet;
import com.sfr.applications.maam.front.exception.UserProfileException;
import com.sfr.authentication.common.AuthenticationId;
import com.sfr.authentication.common.CanalSU;
import com.sfr.authentication.common.TypeLoginSU;
import com.sfr.springframework.security.helper.AuthenticationHelper;

/**
 * Methode utilitaire liée à l'authentification SFR
 * 
 * @author sco
 */
@Component
public class AutenticationTools {
    
    public static transient final Logger log = LoggerFactory.getLogger(AutenticationTools.class);
    
    @Value("${maam.authentification.superuser.mock.active:false}")
    private boolean isMockedSuperUser = false;
    
    /**
     * Indique si l'utilisateur est un utilisateur usurpé ou non
     */
    public boolean isSuperUser() {
	return isMockedSuperUser ? true : AuthenticationHelper.isSuperUser();
    }
    

    /**
     * Retourne l'Id d'authentification SFR
     */
    public AuthenticationId getAuthId() {
	return AuthenticationHelper.getAuthenticationId();
    }
    
    /**
     * Retourne le login d'utilisateur
     * 
     * @return
     */
    public String getLogin() {
	return AuthenticationHelper.getLogin();
    }
    

    /**
     * Retourne le login de l'utilisateur usurpé si il existe
     */
    public String getLoginSuperUser() {
	if (!isSuperUser()) {
	    return null;
	} else if (isMockedSuperUser) {
	    return "mockedSU";
	}
	
	return getAuthId().getLoginSu();
    }
    

    /**
     * Retourne le role de l'utilisateur usurpé s'il existe
     */
    public String getRoleSuperUser() {
	if (!isSuperUser()) {
	    return null;
	} else if (isMockedSuperUser) {
	    return "EXTRANET_SFC_ADG_MOBILE_RET";
	}
	
	return AuthenticationHelper.getRoleSu();
    }
    

    /**
     * Retourne le type de login de l'utilisateur usurpé si il existe
     */
    public TypeLoginExtranet getTypeLoginSuperUser() {
	if (!isSuperUser()) {
	    return null;
	} else if (isMockedSuperUser) {
	    return TypeLoginExtranet.CC;
	}
	
	TypeLoginSU type = getAuthId().getTypeLoginSu();
	
	switch (type) {
	    case DIS:
		return TypeLoginExtranet.DIS;
	    case CC:
		return TypeLoginExtranet.CC;
	    case SFC:
		return TypeLoginExtranet.SFC;
	    case SUPPORT:
		throw new UserProfileException("L'utilisateur de type SUPPORT n'a pas accès au parcours");
	    default:
		return null;
	}
    }
    

    /**
     * Retourne le type de canal de l'utilisateur usurpé si il existe
     */
    public TypeCanalExtranet getTypeCanalSuperUser() {
	if (!isSuperUser()) {
	    return null;
	} else if (isMockedSuperUser) {
	    return TypeCanalExtranet.RC;
	}
	
	CanalSU type = getAuthId().getCanalSu();
	switch (type) {
	    case DIS:
		return TypeCanalExtranet.DIS;
	    case RC:
		return TypeCanalExtranet.RC;
	    case SFC:
		return TypeCanalExtranet.SFC;
	    default:
		return null;
	}
    }
    
    /**
     * Retourne le type de sub canal de l'utilisateur usurpé si il existe
     */
    public String getTypeSubCanalSuperUser() {
	String role = getAuthId().getRoleSu();
	
	if ("EXTRANET_SFC_ADG_MOBILE_RET".equals(role)) {
	    return "RET";
	} else if ("EXTRANET_SFC_ADG_MOBILE_TEL".equals(role)) {
	    return "TLV";
	}
	
	return null;
    }
}