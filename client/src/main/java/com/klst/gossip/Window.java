package com.klst.gossip;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.compiere.model.MTab;
import org.compiere.model.MWindow;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.Trx;

import gov.nasa.arc.mct.gui.impl.HidableTabbedPane;

/*
 - visualisiert MWindow mWindow
 - dieses JPanel ist das ContentPane im Borderlayout
 - besteht aus tabPane : HidableTabbedPane ; // hierin die AD tabs
 */

public class Window extends JPanel implements WindowListener {
	
	private static final long serialVersionUID = 5098403364836474988L;

	private static final Logger LOG = Logger.getLogger(Window.class.getName());
	
	public static final String P_SHOW_TRL = "#"+Ini.P_SHOW_TRL;
	
	Gossip rootFrame;
	private int window_ID;
	private Properties ctx;
	private String trxName;
	protected MWindow mWindow;
	
	JFrame currentFrame; // TODO ist null
	List<MTab> tabs;
	protected HidableTabbedPane tabPane; // TODO protected raus
	
	// ctor
	protected Window() {
		super(new BorderLayout());
	}
	protected Window(Gossip rootFrame, int window_ID) {
		this();
		this.rootFrame = rootFrame;
		this.window_ID = window_ID;
		
		this.ctx = Env.getCtx();
		this.trxName =  Trx.createTrxName(Window.class.getName());
		mWindow = new MWindow(ctx, this.window_ID, trxName);
//		this.setTitle(this.mWindow.getName()); // TODO funktioniert nicht im ctor !?
	}

	protected List<MTab> getTabs(boolean reload) {
		ctx.forEach((key,value) -> { // zum Test
			LOG.info("key:"+key + " : " + value.toString());
		});
		this.tabs = Arrays.asList(mWindow.getTabs(reload, trxName));
		return tabs;
	}
	
	void setTitle() {
		setTitle(this.mWindow.getName());
	}
	void setTitle(String title) {
		JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		if(jFrame==null) {
			LOG.warning("currentFrame (aka WindowAncestor) is null for "+this + " - Cannot set title to "+title);
		} else {
			jFrame.setTitle(title);
		}
	}
	
	void setTabPane(HidableTabbedPane hidableTabbedPane) {
		this.tabPane = hidableTabbedPane; 
	}
	
	/* Obtain Window/JFrame from inside a JPanel
	 * see https://stackoverflow.com/questions/9650874/java-swing-obtain-window-jframe-from-inside-a-jpanel

There are 2 direct, different methods for this in SwingUtilities which provide the same functionality (as noted in their Javadoc). 
They return java.awt.Window but if you added your panel to a JFrame, you can safely cast it to JFrame.

The 2 direct and most simple ways:

JFrame f1 = (JFrame) SwingUtilities.windowForComponent(comp);
JFrame f2 = (JFrame) SwingUtilities.getWindowAncestor(comp);

For completeness some other ways:

JFrame f3 = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, comp);
JFrame f4 = (JFrame) SwingUtilities.getRoot(comp);
JFrame f5 = (JFrame) SwingUtilities.getRootPane(comp).getParent();

	 */
	protected void setWindowListenerFor(Container rootContainer) {
		currentFrame = (JFrame) SwingUtilities.getWindowAncestor(rootContainer);
		if(currentFrame==null) {
			LOG.warning("currentFrame (aka WindowAncestor) is null for "+rootContainer);
		} else {
			currentFrame.addWindowListener(this);
		}
		
	}
	
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		LOG.config("TODO");
	}

	@Override
	public void windowClosed(WindowEvent e) {
		LOG.config("remove "+this.currentFrame);
		this.rootFrame.remove(this.currentFrame);
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
