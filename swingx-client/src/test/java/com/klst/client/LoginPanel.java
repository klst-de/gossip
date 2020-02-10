package com.klst.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.Connection;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.compiere.Adempiere;
import org.compiere.db.CConnection;
import org.compiere.model.MLanguage;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.Language;
import org.jdesktop.swingx.JXLoginPane;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.auth.JDBCLoginService;
import org.jdesktop.swingx.auth.LoginService;
import org.jdesktop.swingx.auth.PasswordStore;
import org.jdesktop.swingx.auth.UserNameStore;

import com.klst.icon.AbstractImageTranscoder;

public class LoginPanel extends JXPanel {

	private static final long serialVersionUID = -2401589870773958688L;
	
	private static final Logger LOG = Logger.getLogger(LoginPanel.class.getName());

	private static final boolean isDoubleBuffered = true;
	
	AbstractImageTranscoder AIT = AbstractImageTranscoder.getInstance();
	private JDBCLoginService jdbcLoginService;
	Connection dbConnection;
	CConnection cConnection = null;
	public CConnection getCConnection() {
		return cConnection;
	}
	
	JXLoginPane loginPane; // user login
	JXLoginPane serverPane; // server login
	JButton connect;
	JButton connectionTest;

    public LoginPanel() {
        this(null);
    }

    public LoginPanel(LoginService service) {
        this(service, null, null);
    }

    public LoginPanel(LoginService service, PasswordStore passwordStore, UserNameStore userStore) {
        this(service, passwordStore, userStore, null);
    }

    public LoginPanel(LoginService service, PasswordStore passwordStore, UserNameStore userStore, List<String> servers) {
    	super(isDoubleBuffered);
    	LOG.info("service:"+service + " passwordStore:"+passwordStore + " userStore:"+userStore + " servers:"+servers);
    	serverPane = new JXLoginPane(null, null, null, servers);
    	loginPane = new JXLoginPane(null, passwordStore, userStore, null);
    	initComponents();
    }

	private void initComponents() {
		setLayout(new BorderLayout());
		add(loginPane, BorderLayout.PAGE_START); // aka NORTH
		
		// damit wird zuerst loginPane angezeigt, dann serverPane
//    	Status status = JXLoginPane.showLoginDialog(this, loginPane);
//    	LOG.info("status:"+status.name());
		
		// ohne serverPane
//		List<String> servers = new ArrayList<String>( Arrays.asList("localhost", "bionic-beaver") );
//		serverPane.setServers(servers);
//	    String driver = "org.postgresql.Driver";
//	    String url = "jdbc:postgresql://localhost:5432/miad001";
//	    jdbcLoginService = new JDBCLoginService(driver, url); // JDBCLoginService(String driver, String url) | JDBCLoginService(String driver, String url, Properties props)
//	    serverPane.setBanner(null); // no Banner
//	    serverPane.setBannerText("Server"); // implziert Banner
//		add(serverPane, BorderLayout.CENTER);
		connect = new JButton("connect",AIT.getImageIcon(AIT.SERVER, 24));
		add(connect, BorderLayout.CENTER);
		connect.addActionListener(event -> {
			//LOG.warning("**** TODO ****");
			connectToDatabase();
		});
	    
		connectionTest = new JButton("connectionTest",AIT.getImageIcon(AIT.DATABASE, 24));
		add(connectionTest, BorderLayout.PAGE_END); // aka SOUTH
		connectionTest.addActionListener(event -> {
			initLogin();
		});
/*
        add(banner, BorderLayout.NORTH);
        contentCardPane = new JPanel(new CardLayout());
        contentCardPane.setOpaque(false);
        contentCardPane.add(contentPanel, "0");
        contentCardPane.add(progressPanel, "1");
        add(contentCardPane, BorderLayout.CENTER);

 */
	}

	private void useCConnectionDialog() {
		//CConnectionDialog startet schon bei der statischen initialisierung ==> wg initLogin() : verschoben
		AConnectionDialog cConnectionDialog = new AConnectionDialog(cConnection);
		cConnection = cConnectionDialog.getConnection();
		LOG.info(CConnection.get().toStringLong()); // ? TODO warum false?
		LOG.info("cConnection.isDatabaseOK():"+cConnection.isDatabaseOK());
		Ini.setProperty(Ini.P_CONNECTION, CConnection.get().toStringLong());
//		if(cConnection!=null && cConnection.isDatabaseOK()) {
//			Ini.setProperty(Ini.P_CONNECTION, CConnection.get().toStringLong());
//			connectionTest.setBackground(Color.GREEN);
//		} else {
//			connectionTest.setBackground(Color.RED);
//		}
		LOG.info(Ini.P_CONNECTION+":"+Ini.getProperty(Ini.P_CONNECTION));
	}

	private boolean authenticate(String name, char[] password, String server) {
    	LOG.info("name:"+name + " server:"+server);
    	boolean connected = false;
    	try {
    		connected = jdbcLoginService.authenticate(name, password, server);
    	} catch (Exception e) {
    		LOG.warning(e.getMessage());
    	}
    	if(connected) {
    		connectionTest.setBackground(Color.GREEN);
    		dbConnection = jdbcLoginService.getConnection();
    	} else {
    		connectionTest.setBackground(Color.RED);
    		dbConnection = null;
    	}
        return connected;
	}
	
	/** aus com.klst.apps.AConnectionDialog bzw org.compiere.db.CConnectionDialog (base)
	 *  Test Database connection
	 */
//	private void cmd_testDB()
//	{
//		setBusy (true);
//		Exception e = m_cc.testDatabase(true);
//		if (e != null)
//		{
//			JOptionPane.showMessageDialog(this,
//				e,		//	message
//				res.getString("ConnectionError") + ": " + m_cc.getConnectionURL(),
//				JOptionPane.ERROR_MESSAGE);
//		}
//		setBusy (false);
//	}   //  cmd_testDB

	/** aus ALogin (client)
	 *	Set Initial & Ini Parameters
	 *	Optional Automatic login
	 *	@return true, if connected & parameters set
	 */
//	public boolean initLogin()
//	{
//		m_cc = CConnection.get(Adempiere.getCodeBaseHost());
//		hostField.setValue(m_cc);
//		
//		if ( Ini.isPropertyBool(Ini.P_VALIDATE_CONNECTION_ON_STARTUP)) {
//			validateConnection();
//		} 
//
//		//  Application/PWD
//		userTextField.setText(Ini.getProperty(Ini.P_UID));
//		if (Ini.isPropertyBool(Ini.P_STORE_PWD))
//			passwordField.setText(Ini.getProperty(Ini.P_PWD));
//		else
//			passwordField.setText("");
//		//
//		languageCombo.setSelectedItem(Ini.getProperty(Ini.P_LANGUAGE));
//
//		//	AutoLogin - assumes that connection is OK
//		if (Ini.isPropertyBool(Ini.P_A_LOGIN))
//		{
//			connectionOK ();
//			defaultsOK ();
//			if (m_connectionOK)		//	simulate
//				m_okPressed = true;
//			return m_connectionOK;
//		}
//		return false;
//	}	//	initLogin

	public boolean initLogin() {
		LOG.info("***** PropertyFileName:"+Ini.getPropertyFileName());
		Ini.loadProperties(true); // boolean reload
		LOG.info("***** PropertyFileName:"+Ini.getPropertyFileName());
		
		cConnection = CConnection.get(Adempiere.getCodeBaseHost());
		// private CConnectionEditor hostField = new CConnectionEditor(); hostField.setValue(m_cc);
		if ( Ini.isPropertyBool(Ini.P_VALIDATE_CONNECTION_ON_STARTUP)) {
			LOG.warning("TODO validateConnection()"); // TODO validateConnection();
		}
		
		LOG.info(Ini.P_UID+":"+Ini.getProperty(Ini.P_UID) + " +++ "+Ini.P_PWD+":"+Ini.getProperty(Ini.P_PWD));
		loginPane.setUserName(Ini.getProperty(Ini.P_UID));
		loginPane.setPassword(Ini.getProperty(Ini.P_PWD).toCharArray());
		if (Ini.isPropertyBool(Ini.P_A_LOGIN)) {
			LOG.warning("TODO autoconnection"); // TODO 
		}
		
		LOG.info("Ini  Connection:"+Ini.getProperty("Connection"));
		LOG.info("<<< "+Ini.P_CONNECTION+":"+Ini.getProperty(Ini.P_CONNECTION));
// Connection:CConnection[name=mintest02x{localhost-ad391-adempiere},AppsHost=mintest02x,AppsPort=1099,type=PostgreSQL,DBhost=localhost,DBport=5432,DBname=ad391,BQ=false,FW=false,FWhost=,FWport=0,UID=adempiere,PWD=adempiere]
		//loginPane.setPassword(Ini.getProperty(Ini.P_PWD)); es brauch String to char[]
		
		return false;
	}
	
	/**************************************************************************
	 *	Connection OK pressed - aus ALogin (client)
	 */
//	private void connectionOK ()
//	{
//		log.info("");
//		//
//		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//		confirmPanel.getOKButton().setEnabled(false);
//
//		m_connectionOK = tryConnection();
//
//		if (m_connectionOK)			
//		{
//			//  Verify Language & Load Msg
//			Language language = Language.getLoginLanguage();
//			Env.verifyLanguage (m_ctx, language);
//			Env.setContext(m_ctx, Env.LANGUAGE, language.getAD_Language());
//			Msg.getMsg(m_ctx, "0");
//			
//			//	Migration
//			MSystem system = MSystem.get(m_ctx);
//			if (system.isJustMigrated())
//			{
//				statusBar.setStatusLine("Running: After Migration ....", true);
//				ADialog.info (m_WindowNo, this, "AfterMigration");
//				Thread.yield();
//				DB.afterMigration(m_ctx);
//			}
//			//	Set Defaults
//			printerField.setValue(Ini.getProperty(Ini.P_PRINTER));
//			//	Change Tab to Default
//			loginTabPane.setSelectedIndex(1);
//		}
//
//		confirmPanel.getOKButton().setEnabled(true);
//		setCursor(Cursor.getDefaultCursor());
//	}	//	connectionOK
	private void connectToDatabase() {
		//Check connection
		DB.setDBTarget(cConnection);
		
		//direct
		if(DB.connect()) {
			connect.setBackground(Color.GREEN);
			Properties ctx = Env.getCtx();

			Language language = Language.getLoginLanguage();
			LOG.info("Locale:"+this.getLocale() + " Language:"+language + " default:"+Locale.getDefault(Locale.Category.DISPLAY));
			MLanguage lang = MLanguage.get(ctx, this.getLocale().toLanguageTag());
			LOG.info("lang:"+lang);
			Env.setContext(ctx, Env.LANGUAGE, this.getLocale().toString());
			Env.setContext(ctx, Env.LANGUAGE, language.getLocale().toString()); // aus props
			Env.setContext(ctx, "#ShowTrl", "N");
			 
//			ctx.list(System.out);
/* liefert
-- listing properties --
#AD_Language=de-DE // this.getLocale().toLanguageTag()
#AD_Language=de_DE // this.getLocale().toString() - so erwartet es ADempiere

		connect.addActionListener(event -> {
			//LOG.warning("**** TODO ****");
			connectToDatabase();
		});

 */
			ctx.forEach((key,value) -> {
				LOG.info("key:"+key + " : " + value.toString());
			});
		} else {
			connect.setBackground(Color.RED);
		}	
	}
/* in ALogin wird Locale aus Language gesetzt
 * in Gossip umgekehrt Language aus Locale ermittelt 
	private void languageComboChanged ()
	{
		String langName = (String)languageCombo.getSelectedItem();
	//	log.info( "Language: " + langName);
		Language language = Language.getLanguage(langName);
		Language.setLoginLanguage(language);
		Env.setContext(m_ctx, Env.LANGUAGE, language.getAD_Language());

		//	Locales
		Locale loc = language.getLocale();
		Locale.setDefault(loc);
		this.setLocale(loc);
		res = ResourceBundle.getBundle(RESOURCE, loc);
		//

 */
//	/**************************************************************************
//	 *	Try to connect.
//	 *  - Get Connection
//	 *  - Compare User info
//	 *  @return true if connected
//	 */
//	private boolean tryConnection()
//	{
//		m_user = userTextField.getText();
//		m_pwd = new String (passwordField.getPassword());
//
//		//	Establish connection
//		if (!DB.isConnected(false))
//			validateConnection();
//		
//		if (!DB.isConnected(false))
//		{
//			statusBar.setStatusLine(txt_NoDatabase, true);
//			hostField.setBackground(AdempierePLAF.getFieldBackground_Error());
//			return false;
//		}
//		
//		//	Reference check
//		Ini.setProperty(Ini.P_ADEMPIERESYS, "Reference".equalsIgnoreCase(CConnection.get().getDbUid()));
//
//		//	Reference check
//		Ini.setProperty(Ini.P_LOGMIGRATIONSCRIPT, "Reference".equalsIgnoreCase(CConnection.get().getDbUid()));
//
//		//  Get Roles
//		m_login = new Login(m_ctx);
//		KeyNamePair[] roles = null;
//		try 
//		{
//			roles = m_login.getRoles(m_user, m_pwd);
//			if (roles == null || roles.length == 0)
//			{
//				statusBar.setStatusLine(txt_UserPwdError, true);
//				userTextField.setBackground(AdempierePLAF.getFieldBackground_Error());
//				passwordField.setBackground(AdempierePLAF.getFieldBackground_Error());
//				return false;
//			}
//		}
//		catch (Throwable e)
//		{
//			if (e.getCause() instanceof AccessException)
//			{
//				statusBar.setStatusLine(txt_UserPwdError, true);
//				userTextField.setBackground(AdempierePLAF.getFieldBackground_Error());
//				passwordField.setBackground(AdempierePLAF.getFieldBackground_Error());
//				return false;
//			}
//			else
//			{
//				log.severe(CLogger.getRootCause(e).getLocalizedMessage());
//				statusBar.setStatusLine(CLogger.getRootCause(e).getLocalizedMessage(), true);
//				return false;
//			}
//		}
//		
//		
//		//	Delete existing role items
//		m_comboActive = true;
//		if (roleCombo.getItemCount() > 0)
//			roleCombo.removeAllItems();
//
//		//  Initial role
//		KeyNamePair iniValue = null;
//		String iniDefault = Ini.getProperty(Ini.P_ROLE);
//
//		//  fill roles
//		for (int i = 0; i < roles.length; i++)
//		{
//			roleCombo.addItem(roles[i]);
//			if (roles[i].getName().equals(iniDefault))
//				iniValue = roles[i];
//		}
//		if (iniValue != null)
//			roleCombo.setSelectedItem(iniValue);
//		
//		// If we have only one role, we can hide the combobox - metas-2009_0021_AP1_G94
//		if (roleCombo.getItemCount() == 1 && ! MSysConfig.getBooleanValue("ALogin_ShowOneRole", true))
//		{
//			roleCombo.setSelectedIndex(0);
//			roleLabel.setVisible(false);
//			roleCombo.setVisible(false);
//		}
//		else
//		{
//			roleLabel.setVisible(true);
//			roleCombo.setVisible(true);
//		}
//
//		userTextField.setBackground(AdempierePLAF.getFieldBackground_Normal());
//		passwordField.setBackground(AdempierePLAF.getFieldBackground_Normal());
//		//
//		this.setTitle(hostField.getDisplay());
//		statusBar.setStatusLine(txt_LoggedIn);
//		m_comboActive = false;
//		roleComboChanged();
//		return true;
//	}	//	tryConnection

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Login Panel");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(new LoginPanel());
//                frame.setPreferredSize(new Dimension(800, 600));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

}
