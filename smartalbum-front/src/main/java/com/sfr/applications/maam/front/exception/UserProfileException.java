package com.sfr.applications.maam.front.exception;

/**
 * Exception User Profile
 * 
 * @author sco
 */
public class UserProfileException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public UserProfileException(){
	}

	
	public UserProfileException(String message){
		super(message);

	}

	public UserProfileException(Throwable exception){
		super(exception);

	}

	public UserProfileException(String message, 
			                    Throwable exception){
		super(message, 
		      exception);
	}
}