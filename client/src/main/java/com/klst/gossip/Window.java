package com.klst.gossip;

import java.awt.Container;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.compiere.model.MTab;
import org.compiere.model.MWindow;
import org.compiere.util.Env;
import org.compiere.util.Trx;

import gov.nasa.arc.mct.gui.impl.HidableTabbedPane;

/*
 - besteht aus Tabs : HidableTabbedPane hidableTabbedPane; // hierin die AD tabs
 */

public class Window implements WindowListener {
	
	private static final Logger LOG = Logger.getLogger(Window.class.getName());
	
	private int window_ID;
	protected MWindow mWindow;
	
	Gossip rootFrame;
	JFrame currentFrame; 
	protected HidableTabbedPane hidableTabbedPane; // TODO protected raus
	
	// ctor
	protected Window(Gossip rootFrame, int window_ID) {
		this.rootFrame = rootFrame;
		this.window_ID = window_ID;
		
		Properties ctx = Env.getCtx();
		String trxName =  Trx.createTrxName(Window.class.getName());
		mWindow = new MWindow(ctx, window_ID, trxName);
	}

	protected List<MTab> getTabs(boolean reload, String trxName) {
		 List<MTab> tabs = Arrays.asList(mWindow.getTabs(reload, trxName));
		 return tabs;
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
