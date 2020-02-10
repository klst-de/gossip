package com.klst.icon;

import java.io.IOException;

import javax.swing.ImageIcon;

public class FeatherImageTranscoder extends AbstractImageTranscoder {

	// ctor
	public FeatherImageTranscoder() {
		super("../../gossip/swingx-client/bin/icons/feather/");
//getResource("../../gossip/swingx-client/bin/icons/feather/"));
		
		AppIcon todo = FeatherIcon.feather; // TODO diese müssen noch zugeordnet werden
		RLI = FeatherIcon.RLI;
		YLI = FeatherIcon.YLI;
		GLI = FeatherIcon.GLI;
		// Menu File:
		PRINTSCREEN = FeatherIcon.PRINTSCREEN;
		SCREENSHOT = FeatherIcon.SCREENSHOT;
		REPORT = FeatherIcon.REPORT;
		PRINT = FeatherIcon.PRINT;
		PREVIEW = FeatherIcon.PREVIEW;
		EXPORT = FeatherIcon.EXPORT;
		END = FeatherIcon.END;
		LOGOUT = FeatherIcon.LOGOUT;
		EXIT = FeatherIcon.EXIT;
		// Menu Edit:
		NEW = FeatherIcon.NEW;
		SAVE = FeatherIcon.SAVE;
		COPY = FeatherIcon.COPY;
		DELETE = FeatherIcon.DELETE;
		DELETE_SEL = FeatherIcon.DELETE_SEL;
		IGNORE = FeatherIcon.IGNORE;
		REFRESH = FeatherIcon.REFRESH;
		FIND = FeatherIcon.FIND; FIND_TOGGLED = FeatherIcon.FIND_TOGGLED;
		LOCK = FeatherIcon.LOCK;; LOCK_TOGGLED = FeatherIcon.LOCK_TOGGLED;
		// Menu View / Info:
		PRODUCT = FeatherIcon.PRODUCT;
		BPARTNER = FeatherIcon.BPARTNER;
		ACCOUNT = FeatherIcon.ACCOUNT;
		SCHEDULE = FeatherIcon.SCHEDULE;
		MRP = FeatherIcon.feather;
		CRP = FeatherIcon.feather;
		ORDER = FeatherIcon.ORDER;
		INVOICE = FeatherIcon.INVOICE;
		SHIPMENT = FeatherIcon.SHIPMENT;
		PAYMENT = FeatherIcon.PAYMENT;
		CASHLINE = FeatherIcon.CASHLINE; // "book"
		ASSIGNMENT = FeatherIcon.feather; // Resource
		ASSET = FeatherIcon.ASSET;
		ATTACH = FeatherIcon.ATTACH; ATTACH_TOGGLED = FeatherIcon.ATTACH_TOGGLED;
		CHAT = FeatherIcon.CHAT; CHAT_TOGGLED = FeatherIcon.CHAT_TOGGLED;
		HISTORY = FeatherIcon.HISTORY; HISTORY_TOGGLED = FeatherIcon.HISTORY_TOGGLED;
		GRID = FeatherIcon.GRID; GRID_TOGGLED = FeatherIcon.GRID_TOGGLED;
		// Menu Go
		FIRST = FeatherIcon.FIRST;
		PREVIOUS = FeatherIcon.PREVIOUS;
		NEXT = FeatherIcon.NEXT;
		LAST = FeatherIcon.LAST;
		PARENT = FeatherIcon.PARENT;
		DETAIL = FeatherIcon.DETAIL;
		ZOOM = FeatherIcon.ZOOM;
		REQUEST = FeatherIcon.REQUEST;
		ARCHIVE = FeatherIcon.ARCHIVE;
		HOME = FeatherIcon.HOME;
		// Menu Tools:
		CALCULATOR = FeatherIcon.feather;
		CALENDAR = FeatherIcon.CALENAR;
		EDITOR = FeatherIcon.EDITOR;
		SCRIPT = FeatherIcon.SCRIPT;
		WINSIZE = FeatherIcon.feather;
		WORKFLOW = FeatherIcon.WORKFLOW;
		PREFERENCE = FeatherIcon.PREFERENCE;
		// Menu Help:
		HELP = FeatherIcon.HELP;
		ONLINE = FeatherIcon.ONLINE;
		SENDMAIL = FeatherIcon.SENDMAIL;
		ABOUT = FeatherIcon.ABOUT;

		SERVER = FeatherIcon.SERVER;
		DATABASE = FeatherIcon.DATABASE;
		MENU = FeatherIcon.MENU;
		MENU_WINDOW = FeatherIcon.MENU_WINDOW;
		MENU_X = FeatherIcon.MENU_X;
		MENU_V = FeatherIcon.MENU_V;
		MENU_C = FeatherIcon.MENU_C;
		MENU_D = FeatherIcon.MENU_D;
		MENU_Z = FeatherIcon.MENU_Z;
		MENU_TD = FeatherIcon.MENU_TD;
		MAIL = FeatherIcon.MAIL;
		TASK = FeatherIcon.TASK;
		PROCESS = FeatherIcon.PROCESS;
		INFO = FeatherIcon.INFO;
		WARN = FeatherIcon.WARN;
		ERROR = FeatherIcon.ERROR;
		QUESTION = FeatherIcon.QUESTION;
		CANCEL = FeatherIcon.CANCEL;
		OK = FeatherIcon.OK;
		FOLDER = FeatherIcon.FOLDER;
		TABLE_COLUMN_CONTROL = FeatherIcon.TABLE_COLUMN_CONTROL;
	}

	@Override
	public ImageIcon getImageIcon(AppIcon icon, int size) {
		try {
			return getImageIcon(icon.toString(), size);
		} catch (IOException e) {
			return null;
		}
	}

}
