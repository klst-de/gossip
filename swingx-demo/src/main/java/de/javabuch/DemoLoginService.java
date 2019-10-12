package de.javabuch;

import java.util.logging.Logger;

import org.compiere.db.CConnection;
import org.compiere.db.CConnectionEditor;
import org.compiere.util.DB;
import org.jdesktop.swingx.auth.LoginService;

/**
 * A {@code LoginService} that can be modified to allow or disallow logins. Only useful for
 * demonstration purposes.
 * 
 * @author Karl George Schaefer
 */
// aus package org.jdesktop.swingx.demos.loginpane;
public class DemoLoginService extends LoginService { // abstract class LoginService extends AbstractBean
	
	private Logger LOG = Logger.getLogger(DemoLoginService.class.getName());
	
    private boolean validLogin;
    
    /**
     * Constructs the default service.
     */
    public DemoLoginService() {
        setSynchronous(true);
        LOG.info("validLogin:"+validLogin);
    }
    
    /**
     * @return the validLogin
     */
    public boolean isValidLogin() {
        return validLogin;
    }

    /**
     * @param validLogin the validLogin to set
     */
    public void setValidLogin(boolean validLogin) {
        this.validLogin = validLogin;
    }

    /**
     * {@inheritDoc}
     */
    /*
                      public boolean authenticate(String name, char[] password,
                                      String server) throws Exception {
                              // perform authentication and return true on success.
                              return false;

     */
    @Override
    public boolean authenticate(String name, char[] password, String server) throws Exception {
    	LOG.info("name:"+name + " server:"+server);
    	try {
        	this.startAuthentication(name, password, server==null ? "localhost" : server);
        	setValidLogin(true);
    	} catch (Exception e) {
    		LOG.warning(e.getMessage());
    	}
        return isValidLogin();
    }

    // aus org.compiere.apps.ALogin:
    private CConnection 	m_cc;
    private boolean		    m_connectionOK = false;
    private CConnectionEditor hostField = new CConnectionEditor();
	private void validateConnection()
	{
		m_connectionOK = false;
		validateAppServer();
		
		//make sure connecting to new database
		DB.closeTarget();
		connectToDatabase();
		//
		hostField.setDisplay();
	}   //  validateConnection

	private void validateAppServer() {
		if (!CConnection.isServerEmbedded()) {
			m_cc.testAppsServer();
		}
	}

	private void connectToDatabase() {
		//Check connection
		DB.setDBTarget(m_cc);
		
		//direct
		DB.connect();
	}

}
