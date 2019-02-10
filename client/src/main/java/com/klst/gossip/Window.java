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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
 - es ist ein Top Level Component 
 - enthält JPanel als ContentPane im Borderlayout
 - das wiederum besteht aus tabPane : HidableTabbedPane ; // hierin die AD tabs
 */

public class Window extends JFrame implements WindowListener {
	
	private static final long serialVersionUID = 5098403364836474988L;

	private static final Logger LOG = Logger.getLogger(Window.class.getName());
	
	public static final String P_SHOW_TRL = "#"+Ini.P_SHOW_TRL;

	RootFrame rootFrame;
	JMenuBar menuBar = new JMenuBar();
	JMenu mFile = new JMenu(); // File : JMenuItem's "Quit",  b,  c, ...
	
	private static int mindowCouter = 0; // wird pro ctor hochgezählt
	private int windowNo;
	
	private int window_ID;
	private Properties ctx = null;
	private String trxName;
	protected MWindow mWindow;
	
	List<MTab> tabs;
	protected HidableTabbedPane tabPane; // TODO protected raus
	
	// ctor
	/* super ctors
	 * 	   JFrame() throws HeadlessException 
	 *     JFrame(GraphicsConfiguration gc)
	 *     JFrame(String title) throws HeadlessException
	 *     JFrame(String title, GraphicsConfiguration gc)
	 */
	protected Window() {
		LOG.warning("implizit ctor");
		mindowCouter++;
		this.windowNo = mindowCouter-1;
	}
	Window(String title) { // RootFrame
		this(title, null, -1);
	}
	Window(String title, RootFrame rootFrame, int window_ID) {
		super(title);
		mindowCouter++;
		this.windowNo = mindowCouter-1;
		
		this.rootFrame = rootFrame;
		this.window_ID = window_ID;
		
		initMenuBar();

		this.ctx = Env.getCtx();
		this.trxName =  Trx.createTrxName(Window.class.getName());
		if(this.window_ID == -1) {
			setTitle(title); 
		} else {
			mWindow = new MWindow(ctx, this.window_ID, trxName);
			LOG.config("mWindow:"+mWindow);
			setTitle(); 
		}
		getContentPane().add(new JPanel(new BorderLayout()));
		addWindowListener(this); // wg. - JFrame.DISPOSE_ON_CLOSE
	}

	private void initMenuBar() {
		LOG.config(menuBar.toString());
		this.setJMenuBar(menuBar);
		menuBar.add(mFile);
		mFile.setName("file");
		mFile.setText("File");
        if(!Env.isMac()) { 
            JMenuItem quitItem = new JMenuItem("Quit"); // JMenuItem(String text, int mnemonic) | JMenuItem(String text, Icon icon)
            quitItem.setName("quit");
            quitItem.setActionCommand("quit");
            quitItem.addActionListener(event -> {
            	System.exit(0);
            });
            mFile.add(quitItem);
        }

	}

	public int getWindowNo() {
		return this.windowNo;
	}
	protected List<MTab> getTabs(boolean reload) {
		// macht eigentlich setTabs TODO
		if(ctx==null) {
			LOG.warning("ctx==null");
		} else {
			ctx.forEach((key,value) -> { // zum Test
				LOG.info("key:"+key + " : " + value.toString());
			});
		}
		this.tabs = Arrays.asList(mWindow.getTabs(reload, trxName));
		return tabs;
	}
	
	public void setTitle(String title) {
		super.setTitle(title);
	}
	void setTitle() {
		setTitle("["+this.windowNo+"] " + this.mWindow.getName());
	}
	
	void setTabPane(HidableTabbedPane hidableTabbedPane) { // TODO raus
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
		JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(rootContainer);
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
		LOG.config("remove windowNo:"+this.windowNo);
		this.rootFrame.remove(this);
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
