package com.klst.icon;

public enum FeatherIcon implements AppIcon {

//	Name	Wert		// Beschreibung siehe http://wiki.idempiere.org/de/Toolbar
	// originalnamen:
	feather("feather"),
	// ... viele andere
	zoom_out("zoom-out"),

	RLI		("Red_Light_Icon"),
	YLI		("Yellow_Light_Icon"),
	GLI		("Green_Light_Icon"),
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
	CANCEL	("x"),
	OK	("check"),
	SENDMAIL("send"), // EMailSupport
	ONLINE("globe"), // Online Help oder "book-opem" 
	ABOUT("star");
	
	FeatherIcon(String name) {
		this.value = name;
	}
	private FeatherIcon(FeatherIcon icon) {
		this.value = icon.getValue();
	}

	private final String value;
	
	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return this.value;
	}

}
