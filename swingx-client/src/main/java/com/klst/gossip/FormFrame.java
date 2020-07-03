package com.klst.gossip;

// ich will nicht (client)org.compiere.apps.form.FormFrame kopieren
// wird für interface FormPanel benötigt
public class FormFrame extends WindowFrame {

	private static final long serialVersionUID = 2208388587280573311L;

//	FormFrame() {
//		this("NoName");
//	}
	FormFrame(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	FormFrame(String title, RootFrame rootFrame, int window_ID, Object object) {
		super(title, rootFrame, window_ID, object);
		// exception in super com.klst.gossip.WindowFrame.<init>(WindowFrame.java:156)
		// TODO Auto-generated constructor stub
	}

}
