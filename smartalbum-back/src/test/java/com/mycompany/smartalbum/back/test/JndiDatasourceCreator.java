package com.mycompany.smartalbum.back.test;
import javax.naming.NamingException;

import oracle.jdbc.pool.OracleConnectionPoolDataSource;

import org.springframework.mock.jndi.SimpleNamingContextBuilder;

    /**
     * Class to create a mock JNDI object in which we add a direct connection to the 
     * Oracle jdbc. Needed so that the standalone Junit tests run and don't fail on the 
     * creation of the dataSource bean within applicationContext.xml. 
     * This class acts as if a datasource would be added to an application/web server's
     * JNDI tree, under the name  java:comp/env/jdbc/XXXX.
     * Has to be invoked from within each one of the Junit tests. 
     * 
     * @author amvou
     */
    public class JndiDatasourceCreator{
    	private static final String url = "jdbc:oracle:thin:@192.168.14.140:1521:orcl";
    	private static final String username = "smart";
    	private static final String password = "smart";
    	private static final String jndiName = "smartalbum";

    	public static void create() throws Exception{
    		try{
    			final SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
    			OracleConnectionPoolDataSource ds = new OracleConnectionPoolDataSource();
    			ds.setURL(url);
    			ds.setUser(username);
    			ds.setPassword(password);
    			builder.bind("java:comp/env/jdbc/" + jndiName, ds);
    			builder.activate();
    		} catch(NamingException ex){
    			ex.printStackTrace();
    		}
    	}
    }