package com.mycompany.smartalbum.back.helper;

import java.util.List;

import opiam.admin.faare.service.UserContext;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * La classe définissant un utilisateur au sens spring security. 
 * C'est cet objet qui sera utilisé coté client pour représenter un employé correctement authentifié.*
 * 
 * This class is a spring security user. It reprensent a client user who is really authenticated.
 * @author amvou
 */
public class SmartAlbumUser extends User{

    private static final long serialVersionUID = 1L;
    
    private com.mycompany.database.smartalbum.model.User dbUser;
    
    private String login;
    
    private String name;
    
    private String firstName;
    
    private String entityName;
    
    private UserContext userContext;
    
    /**
     * Constructeur de l'objet.
     * Cet objet, une fois construit, servira à l'authentification.
     * 
     * @param username Le login de l'utilisateur.
     * @param password Le password de l'utilisateur. /
     * @param enabled Compte activé ou non. / activated or not activated account
     * @param accountNonExpired Compte expiré ou non. / Expired or non expired account
     * @param credentialsNonExpired Role expiré ou non./ Expired or non expired role
     * @param accountNonLocked Compte bloqué ou non. / Blocked account.
     * @param authorities La liste des rôles. / List of the roles.
     * @throws IllegalArgumentException Mauvais paramètrage. / Bad configuration.
     */
    public SmartAlbumUser(String username, 
	                String password, 
	                boolean enabled,
	                boolean accountNonExpired,
	                boolean credentialsNonExpired,
	                boolean accountNonLocked, 
	                List<GrantedAuthority> authorities) throws IllegalArgumentException{
        super(username, 
              password, 
              enabled, 
              accountNonExpired, 
              credentialsNonExpired,
              accountNonLocked, 
              authorities);
        
        userContext = new UserContext();
        userContext.setLogin(username);
        userContext.setPwd(password);
    }
    
    
    // GETTER/SETTER
   
    /**
     * @return the dbUser
     */
    public com.mycompany.database.smartalbum.model.User getDbUser() {
        return dbUser;
    }


    /**
     * @param dbUser the dbUser to set
     */
    public void setDbUser(com.mycompany.database.smartalbum.model.User dbUser) {
        this.dbUser = dbUser;
    }


    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }


    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }


    /**
     * @return the name
     */
    public String getName() {
        return name;
    }


    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }


    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SmartAlbumUser [dbUser=" + dbUser + ", login=" + login + ", name=" + name + ", firstName=" + firstName
               + "]";
    }


    /**
     * @return the entityName
     */
    public String getEntityName() {
        return entityName;
    }


    /**
     * @param entityName the entityName to set
     */
    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }


    /**
     * @return the userContext
     */
    public UserContext getUserContext() {
        return userContext;
    }


    /**
     * @param userContext the userContext to set
     */
    public void setUserContext(UserContext userContext) {
        this.userContext = userContext;
    }
}