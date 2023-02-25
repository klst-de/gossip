package io.homebeaver.gossip.icon;

import org.jdesktop.swingx.demos.svg.FeatheRactivity;
import org.jdesktop.swingx.demos.svg.FeatheRcalendar;
import org.jdesktop.swingx.demos.svg.FeatheRcheck;
import org.jdesktop.swingx.demos.svg.FeatheRcrosshair;
import org.jdesktop.swingx.demos.svg.FeatheRdatabase;
import org.jdesktop.swingx.demos.svg.FeatheRfeather;
import org.jdesktop.swingx.demos.svg.FeatheRfile_text;
import org.jdesktop.swingx.demos.svg.FeatheRfolder;
import org.jdesktop.swingx.demos.svg.FeatheRgit_pull_request;
import org.jdesktop.swingx.demos.svg.FeatheRserver;
import org.jdesktop.swingx.demos.svg.FeatheRsettings;
import org.jdesktop.swingx.demos.svg.FeatheRsidebar;
import org.jdesktop.swingx.demos.svg.FeatheRx;
import org.jdesktop.swingx.demos.svg.FeatheRx_circle;
import org.jdesktop.swingx.icon.JXIcon;
import org.jdesktop.swingx.icon.TrafficLightGreenIcon;
import org.jdesktop.swingx.icon.TrafficLightRedIcon;
import org.jdesktop.swingx.icon.TrafficLightYellowIcon;

/*
//	Name	Wert		// Beschreibung siehe http://wiki.idempiere.org/de/Toolbar
	// originalnamen:
	feather("feather"),
	// ... viele andere
	zoom_out("zoom-out"),

	RLI		("Red_Light_Icon"), TLRED
	YLI		("Yellow_Light_Icon"),
	GLI		("Green_Light_Icon"), TLGREEN
	SERVER	("server"),
	DATABASE("database"),
	MENU	("menu"),
	MENU_X	("layout"), // UserForm
	MENU_V	("toggle-left"), // SetVariable
	MENU_C	("toggle-right"), // UserChoice
	MENU_D	("command"), // DocumentAction , == SCRIPT
	MENU_Z	("moon"), // WaitSleep
	MENU_TD	("loader"), // WaitSleep
	MAIL	("mail"),
	TASK	("activity"),
	IGNORE	("rotate-ccw"),		// Änderung rückgängig machen   | <Escape>
	HELP	("help-circle"),	// Hilfe                        | <F1>
	NEW		("square"),			// Neuer Eintrag                | <F2>
	COPY	("copy"),			// Eintrag kopieren             | <Shift><F2>
	DELETE	("trash"),			// Eintrag löschen              | <F3>
	DELETE_SEL("trash-2"),		// Ausgewählte Einträge löschen | <Ctrl><D>
	SAVE	("save"),			// Änderungen speichern         | <F4>
	REFRESH	("refresh-cw"),		// Erneut abfragen              | <F5>
	FIND	("search"),			// Eintrag suchen               | <F6>
	FIND_TOGGLED("filter"), // "zoom-in"
	ATTACH	("paperclip"),		// Anhang                       | <F7>
	ATTACH_TOGGLED("link"),
	GRID	("grid"),			// Transaktionsfenster          | <F8>
	GRID_TOGGLED("sidebar"), MENU_WINDOW(GRID_TOGGLED),
	HISTORY	("layers"),			// History Records              | <F9>
	HISTORY_TOGGLED("layers"),
	F10	(feather),	// ?? nicht belegt                      | <F10>
	REPORT	("file-text"),		// Report                       | <F11>
	PRINT	("printer"),		// Drucken                      | <F12>
	// JMenu mFile:
	PRINTSCREEN("airplay"),
	SCREENSHOT("clipboard"),	//  
	EXPORT	("share-2"),
	END		("x-circle"),		// Fenster verlassen            | ALT+X
	LOGOUT	("log-out"),		// Abmelden                     | SHIFT+ALT+L
	EXIT	("power"),			// Anwendung beenden
	LOCK	("lock"),
	LOCK_TOGGLED("unlock"),
	CALENAR	("calendar"),
	EDITOR	("edit"),
	CASHLINE("book"), // besser book , da Cash Journal / Kassenbuch
	// Info: 
	PRODUCT	("package"),
	BPARTNER("users"),
	ACCOUNT	("hash"), // Konten
	SCHEDULE("clock"),
	ORDER	("inbox"),
	INVOICE	("file-plus"), // "plus-square"
	SHIPMENT("truck"),
	ASSET	("briefcase"),  // Vermögenswerte
	// ... TODO weitere INFOs
	PAYMENT("dollar-sign"), // "credit-card"

	CHAT	("message-circle"),
	CHAT_TOGGLED("message-circle"),
	// JMenu mGo:
	FIRST	("chevrons-up"),
	PREVIOUS("chevron-up"),
	NEXT	("chevron-down"),
	LAST	("chevrons-down"),
	PARENT	("chevron-left"),
	DETAIL	("chevron-right"),
//	aFirst =	addAction("First", 			mGo, 	KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP, Event.ALT_MASK),	false);
//	aPrevious = addAction("Previous", 		mGo, 	KeyStroke.getKeyStroke(KeyEvent.VK_UP, Event.ALT_MASK),	false);
//	aNext = 	addAction("Next", 			mGo, 	KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, Event.ALT_MASK),	false);
//	aLast =		addAction("Last",	 		mGo, 	KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN, Event.ALT_MASK),	false);
//	aParent =	addAction("Parent", 		mGo, 	KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, Event.ALT_MASK),	false);
//	aDetail =	addAction("Detail", 		mGo,	KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, Event.ALT_MASK),	false);
	ZOOM	("crosshair"), // ZoomAcross 
	REQUEST	("message-square"),
	ARCHIVE	("archive"),
	FOLDER	("folder"),
	TABLE_COLUMN_CONTROL("list"),
	HOME	("home"),
	WORKFLOW("git-pull-request"),
	SCRIPT	("command"),
	PREFERENCE("sliders"),
	PREVIEW	("file-minus"),
	PROCESS	("settings"),
	INFO	("info"),
	WARN	("alert-triangle"), // warnschilder sind dreiecke! ("alert-circle"),
	ERROR	("alert-octagon"),	// Stopp-Schild
	QUESTION(HELP),	// ?-Zeichen
	CANCEL	("x"), TangoRDialog_error_round
	OK	("check"), TangoRDialog_accept
	SENDMAIL("send"), // EMailSupport
	ONLINE("globe"), // Online Help oder "book-opem" 
	ABOUT("star");
	
	FeatheRdatabase.of(JXIcon.SMALL_ICON, JXIcon.SMALL_ICON
	FeatheRserver.of(JXIcon.SMALL_ICON, JXIcon.SMALL_ICON));

		RadianceIcon base = TangoRDialog_accept.factory().createNewIcon();
		base.setDimension(new Dimension(JXIcon.ACTION_ICON, JXIcon.ACTION_ICON));
	oder
		RadianceIcon icon = TangoRDialog_accept.of(JXIcon.ACTION_ICON, JXIcon.ACTION_ICON);

 */
public class IconFactory {

	// vorerst alle nur monochrom feather und tango! TODO mit map
	private static boolean monochrom = false;
	
	public static JXIcon get(String name, int size) {
		if("SERVER".equalsIgnoreCase(name))
			return getSERVER(size);
		else if("DATABASE".equalsIgnoreCase(name))
			return getDATABASE(size);
		else if("CALENDAR".equalsIgnoreCase(name))
			return getCALENDAR(size);
		else if("CANCEL".equalsIgnoreCase(name))
			return getCANCEL(size);
		else if("OK".equalsIgnoreCase(name))
			return getOK(size);
		/* zur INFO aus X_AD_Menu : 
 TODO besser MTreeNode.getImageIcon(MTreeNode.getImageIndex(imageIndicator), int size)
		public static final String ACTION_Form        = "X"; z.B. Tree Maintenance ==> icon wie W
		public static final String ACTION_Process     = "P";
		public static final String ACTION_Report      = "R";
		public static final String ACTION_SmartBrowse = "S"; z.B. User Browser, wie MWFNode.ACTION_SmartBrowse
		public static final String ACTION_Task        = "T";                       ==> icon wie P
		public static final String ACTION_Window      = "W";
		public static final String ACTION_WorkFlow    = "F";
		public static final String ACTION_Workbench   = "B"; ===> obsolet
		                MWFNode.ACTION_SetVariable      "V" 
		                MWFNode.ACTION_UserChoice       "C"
		                MWFNode.ACTION_DocumentAction   "D" MTreeNode.TYPE_DOCACTION
		                MWFNode.ACTION_SmartBrowse      "S" MTreeNode.TYPE_DOCACTION
		                MWFNode.ACTION_WaitSleep        "Z" ====> keine Abbildung
	 */
//		else if("X".equalsIgnoreCase(name))
//			return getFORM(size);
		else if("P".equalsIgnoreCase(name))
			return getPROCESS(size);
		else if("R".equalsIgnoreCase(name))
			return getREPORT(size);
//		else if("S".equalsIgnoreCase(name))
//			return getSMARTBROWSE(size);
		else if("T".equalsIgnoreCase(name))
			return getTASK(size);
		else if("W".equalsIgnoreCase(name))
			return getMENU_WINDOW(size);
		else if("F".equalsIgnoreCase(name))
			return getWORKFLOW(size);
//		else if("B".equalsIgnoreCase(name))
//			return getWORKBENCH(size);
		else return FeatheRfeather.of(size, size);
			
	}
	
	public static JXIcon getTLRED(int size) {
		return TrafficLightRedIcon.of(size, size);
	}
	public static JXIcon getTLYELLOW(int size) {
		return TrafficLightYellowIcon.of(size, size);
	}
	public static JXIcon getTLGREEN(int size) {
		return TrafficLightGreenIcon.of(size, size);
	}

	public static JXIcon getSERVER(int size) {
		return monochrom ? FeatheRserver.of(size, size) : TangoRNetwork_server.of(size, size);
	}
	public static JXIcon getDATABASE(int size) {
		return monochrom ? FeatheRdatabase.of(size, size) : TangoRApplications_database.of(size, size);
	}
	public static JXIcon getCALENDAR(int size) {
		return monochrom ? FeatheRcalendar.of(size, size) : TangoROffice_calendar.of(size, size);
	}
	public static JXIcon getZOOM(int size) {
		return monochrom ? FeatheRcrosshair.of(size, size) : 
			TangoRDialog_error_round.of(size, size); // TODO
	}

	public static JXIcon getMENU_WINDOW(int size) {
		return monochrom ? FeatheRsidebar.of(size, size) : 
			TangoRPreferences_system_windows.of(size, size);
	}
	public static JXIcon getREPORT(int size) {
		return monochrom ? FeatheRfile_text.of(size, size) : 
			TangoRText_x_generic.of(size, size);
	}
	public static JXIcon getPROCESS(int size) {
		return monochrom ? FeatheRsettings.of(size, size) : 
			TangoRApplications_system.of(size, size);
	}
	public static JXIcon getTASK(int size) {
		return monochrom ? FeatheRactivity.of(size, size) : 
			TangoRUtilities_system_monitor.of(size, size);
	}
	public static JXIcon getWORKFLOW(int size) {
		return monochrom ? FeatheRgit_pull_request.of(size, size) : 
			TangoRDialog_error_round.of(size, size); // TODO
	}
	public static JXIcon getEND(int size) {
		return monochrom ? FeatheRx_circle.of(size, size) :
			TangoRDialog_error_round.of(size, size); // TODO
	}
	public static JXIcon getFOLDER(int size) {
		return monochrom ? FeatheRfolder.of(size, size) : 
			TangoRDialog_error_round.of(size, size); // TODO
	}
	
	public static JXIcon getCANCEL(int size) {
		return monochrom ? FeatheRx.of(size, size) : TangoRDialog_error_round.of(size, size);
	}
	public static JXIcon getOK(int size) {
		return monochrom ? FeatheRcheck.of(size, size) : TangoRDialog_accept.of(size, size);
	}
}
