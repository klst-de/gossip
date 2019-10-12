package de.javabuch;

import java.util.List;
import java.util.logging.Logger;

import org.jdesktop.swingx.JXLoginPane;
import org.jdesktop.swingx.auth.LoginEvent;
import org.jdesktop.swingx.auth.LoginListener;
import org.jdesktop.swingx.auth.LoginService;
import org.jdesktop.swingx.auth.PasswordStore;
import org.jdesktop.swingx.auth.UserNameStore;

public class DemoLoginPane extends JXLoginPane
	implements LoginListener {

	private static final long serialVersionUID = 7062675183300461498L;
	
	private Logger LOG = Logger.getLogger(DemoLoginPane.class.getName());
	
    public DemoLoginPane() {
        this(null);
    }

    public DemoLoginPane(LoginService service) {
        this(service, null, null);
    }

    public DemoLoginPane(LoginService service, PasswordStore passwordStore, UserNameStore userStore) {
        this(service, passwordStore, userStore, null);
    }

    public DemoLoginPane(LoginService service, PasswordStore passwordStore, UserNameStore userStore, List<String> servers) {
    	super(service, passwordStore, userStore, servers);
    	LOG.info("service:"+service + " passwordStore:"+passwordStore + " userStore:"+userStore + " servers:"+servers);
    }

// in JXLoginPane sind diese Methoden implementiert und public!
	@Override
	public void loginFailed(LoginEvent source) {
		// TODO Auto-generated method stub
		LOG.info(source.toString());
		LOG.info("userName:"+this.getUserName());
		this.setErrorMessage("userName '"+this.getUserName() + "' war wohl nix!");
	}

	@Override
	public void loginStarted(LoginEvent source) {
		// TODO Auto-generated method stub
		LOG.info(source.toString());
	}

	@Override
	public void loginCanceled(LoginEvent source) {
		// TODO Auto-generated method stub
		LOG.info(source.toString());
	}

	@Override
	public void loginSucceeded(LoginEvent source) {
		// TODO Auto-generated method stub
		LOG.info(source.toString());
	}

}
