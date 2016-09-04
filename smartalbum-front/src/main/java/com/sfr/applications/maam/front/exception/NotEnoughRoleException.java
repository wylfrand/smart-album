package com.sfr.applications.maam.front.exception;

/**
 * L'utilisateur SFR n'a pas le role pour accéder à cette page
 * 
 * @author sco
 */
public class NotEnoughRoleException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public NotEnoughRoleException(){
	}

	
	public NotEnoughRoleException(String message){
		super(message);

	}

	public NotEnoughRoleException(Throwable exception){
		super(exception);

	}

	public NotEnoughRoleException(String message, 
			                      Throwable exception){
		super(message, 
		      exception);
	}
}