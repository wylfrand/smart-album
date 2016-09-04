package com.mycompany.filesystem.exception;

/**
 * Exception caractérisant les services Clarify
 * 
 * @author amv
 */
public class LoginException extends ServiceException{

	private static final long serialVersionUID = 1L;

	public LoginException(String message){
        super(message);
    }
    
    public LoginException(Throwable cause){
        super(cause);
    }
    
    public LoginException(String message, 
    		                Throwable cause){
        super(message,
              cause);
    }
}