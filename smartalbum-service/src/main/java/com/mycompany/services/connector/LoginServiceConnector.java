package com.mycompany.services.connector;


/**
 * Interface de service gérant les interactions avec le client web service Login Clarify
 * 
 * @author amv
 */
public interface LoginServiceConnector{


	/**
	 * Récupère un identifiant de session à clarify
	 * 
	 * @param userName Le login
	 * @param password Le password
	 * @return La clé de session clarify
	 * @throws LoginException Erreur Web Service
	 */
    public String login(final String username,
    		            final String password);
}