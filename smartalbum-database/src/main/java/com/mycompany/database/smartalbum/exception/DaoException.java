package com.mycompany.database.smartalbum.exception;

/**
 * Exception couche dao
 * 
 * @author amv
 */
public class DaoException extends RuntimeException{
    
	private static final long serialVersionUID = 1L;

	public DaoException(final String message){
        super(message);
    }
    
    public DaoException(Throwable cause){
        super(cause);
    }
    

    public DaoException(final String message, 
    		            final Throwable cause){
        super(message,
              cause);
    }
}