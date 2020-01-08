package com.klst.gossip;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.compiere.model.GridTab;
import org.compiere.model.GridWindow;
import org.compiere.model.GridWindowVO;
import org.compiere.model.MWindow;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.Trx;
import org.jdesktop.swingx.JXStatusBar;

import com.klst.icon.AbstractImageTranscoder;

import gov.nasa.arc.mct.gui.impl.HidableTabbedPane;

/*
 - visualisiert MWindow mWindow
 - es ist ein Top Level Component 
 - enthält JPanel im Borderlayout im ContentPane 
 - das wiederum besteht aus tabPane : HidableTabbedPane ; // hierin die AD tabs
 */
// ersetzt (AD client) APanel
public class WindowFrame extends JFrame implements WindowListener {
	
	private static final long serialVersionUID = 5098403364836474988L;

	private static final Logger LOG = Logger.getLogger(WindowFrame.class.getName());
	
	public static final String P_SHOW_TRL = "#"+Ini.P_SHOW_TRL;
	
	static final int SMALL_ICON_SIZE = 16;
	static final int LARGE_ICON_SIZE = 24;
	AbstractImageTranscoder AIT = AbstractImageTranscoder.getInstance();
	
	private static int windowCounter = 0; // für windowNo, wird pro ctor hochgezählt
	private int windowNo;
	
	private int window_ID;
	private Properties ctx = null;
	private String trxName;
	private MWindow mWindow; // eigentlich sollten allen mWindow Daten aus gridWindow kommen
	// GridWindow is ein wrapper für MWindow
	protected GridWindow gridWindow; // GridWindow implements Serializable, contains GridWindowVO ArrayList<GridTab> Set<GridTab>
	// in List sind alle / in Set die initalisierten!!!
	// TODO Set<GridTab> initTabs =/= List<GridTab> gridTabs , List<Tab> tabs
	private List<GridTab> gridTabs; // TODO verschieben nach WindowPame - wieso List statt Set
	private List<Tab> tabs;
//	Tab currentTab; // nur eine Tab kann es sein, die bekommen wir aus tabPane => getSelectedTab
	
	/* ui:
	 * WindowFrame aka (swing) Frames:
	 *   rootFrame/windowNo=0, window/windowNo=1, ... window/windowNo=n(this), window/windowNo=n+1, ...
	 * this frame:
	 * - menuBar
	 *   - mFile, ...
	 *     - quitItem, ...
	 * this ContentPane contains:
	 * - jPanel
	 *   - at PAGE_START: toolBar
	 *   - at CENTER    : tabPane
	 *   - at PAGE_END  : statusBar mit progressBar
	 */
	RootFrame rootFrame; // mit FrameManager
	JMenuBar menuBar = new JMenuBar();
	JMenu mFile = new JMenu(); // File : JMenuItem's "Quit",  b,  c, ...
	JMenu mEdit = new JMenu(); // Edit : JMenuItem's "New Record", ... and Navigation goto first, next, last Record etc
	JMenu mInfo = new JMenu(); // Infofenster
	JMenu mSetup = new JMenu(); // ...
	JMenu mHelp = new JMenu(); // ...
	JPanel jPanel = new JPanel(new BorderLayout());
	HidableTabbedPane tabPane;
	// statusBar:
	JComponent statusBarLeft;
	JLabel actionStatus;
	JProgressBar progressBar;
    JLabel tableStatus;
    JLabel tableRows;

	// ctor
	/* super ctors
	 * 	   JFrame() throws HeadlessException 
	 *     JFrame(GraphicsConfiguration gc)
	 *     JFrame(String title) throws HeadlessException
	 *     JFrame(String title, GraphicsConfiguration gc)
	 */
	WindowFrame(String title) { // für RootFrame
		this(title, null, -1, null);
	}
	WindowFrame(String title, RootFrame rootFrame, int window_ID, GridWindow gridWindow) {
		super(title);
		windowCounter++;
		this.windowNo = windowCounter-1;
		
		this.rootFrame = rootFrame;
		this.window_ID = window_ID;
		
		initMenuBar();

		this.ctx = Env.getCtx();
		this.trxName = Trx.createTrxName(WindowFrame.class.getName());
		if(this.window_ID == -1) {
			setTitle(title); 
		} else {
			initGridWindow(gridWindow);
			// mWindow ==> gridWindow
			mWindow = new MWindow(ctx, this.window_ID, trxName);
			LOG.config("mWindow:"+mWindow);
			setTitle(); 
		}
		getContentPane().add(jPanel);
		
//		jPanel.add(progressBar, BorderLayout.PAGE_END);
		jPanel.add(createStatusBar(), BorderLayout.PAGE_END);
		
		addWindowListener(this); // wg. - JFrame.DISPOSE_ON_CLOSE
		LOG.config("<<<<<<<<<<<<<<<<<<<<<<<< ctor fertig");
	}

	private void initMenuBar() {
		LOG.config(menuBar.toString());
		this.setJMenuBar(menuBar);
		menuBar.add(mFile);
		menuBar.add(mEdit);
		
		mFile.setName("file");
		mFile.setText("File");
		mEdit.setName("edit");
		mEdit.setText("Edit");
        if(!Env.isMac()) { 
            // JMenuItem(String text) | JMenuItem(String text, int mnemonic) | JMenuItem(String text, Icon icon)
            JMenuItem quitItem = new JMenuItem("Quit", AIT.getImageIcon(AIT.EXIT, SMALL_ICON_SIZE));
            quitItem.setName("quit");
            quitItem.setActionCommand("quit");
            quitItem.addActionListener(event -> {
            	System.exit(0);
            });
            mFile.add(quitItem);
            
            JMenuItem logoutItem = new JMenuItem("Logout", AIT.getImageIcon(AIT.LOGOUT, SMALL_ICON_SIZE));
            logoutItem.setName("logout");
            logoutItem.setActionCommand("logout");
            logoutItem.addActionListener(event -> {
            	LOG.config("logout");
            	rootFrame.login();
            });
            mFile.add(logoutItem);
            
            JMenuItem cancelItem = new JMenuItem("Cancel", AIT.getImageIcon(AIT.CANCEL, SMALL_ICON_SIZE));
            cancelItem.setName("cancel");
            cancelItem.setActionCommand("cancel");
            cancelItem.addActionListener(event -> {
            	Tab tab = this.getSelectedTab();
            	tab.cancel(); // Exception tab kann null sein, zB im RootFrame TODO
            });
            mEdit.add(cancelItem);
            
            JMenuItem refreshItem = new JMenuItem("Refresh", AIT.getImageIcon(AIT.REFRESH, SMALL_ICON_SIZE));
            refreshItem.setName("refresh");
            refreshItem.setActionCommand("refresh");
            refreshItem.addActionListener(event -> {
            	Tab tab = this.getSelectedTab();
            	if(tab==null) { // tab kann null sein, zB im RootFrame TODO
            		LOG.config("refresh for "+this + "\nLaF:"+UIManager.getLookAndFeel());
            		this.refresh();
             	} else {
            		tab.refresh();
            	}
            });
            mEdit.add(refreshItem);

            JMenuItem firstItem = new JMenuItem("First", AIT.getImageIcon(AIT.FIRST, SMALL_ICON_SIZE));
            firstItem.setName("first");
            firstItem.setActionCommand("first");
            firstItem.addActionListener(event -> {
            	Tab tab = this.getSelectedTab();
            	tab.first();
            });
            mEdit.add(firstItem);

            JMenuItem previousItem = new JMenuItem("Previous", AIT.getImageIcon(AIT.PREVIOUS, SMALL_ICON_SIZE));
            previousItem.setName("previous");
            previousItem.setActionCommand("previous");
            previousItem.addActionListener(event -> {
            	Tab tab = this.getSelectedTab();
            	tab.previous();
            });
            mEdit.add(previousItem);

            JMenuItem nextItem = new JMenuItem("Next", AIT.getImageIcon(AIT.NEXT, SMALL_ICON_SIZE));
            nextItem.setName("next");
            nextItem.setActionCommand("next");
            nextItem.addActionListener(event -> {
            	Tab tab = this.getSelectedTab();
            	tab.next();
            });
            mEdit.add(nextItem);

            JMenuItem lastItem = new JMenuItem("Last", AIT.getImageIcon(AIT.LAST, SMALL_ICON_SIZE));
            lastItem.setName("last");
            lastItem.setActionCommand("last");
            lastItem.addActionListener(event -> {
            	Tab tab = this.getSelectedTab();
            	tab.last();
            });
            mEdit.add(lastItem);
        }

	}

	void refresh() {
		LOG.config("to be implemented by subclass");
	}
	
	public int getWindowNo() {
		return this.windowNo;
	}
	
	// can make window?
	// GridWindow.get wirft keine exception, das Ergebnis kann aber null sein!
	static GridWindow getGridWindow(int window_ID) {
		// das statische GridWindow.get erstellte eine window value object instanz GridWindowVO.create (Env.getCtx(), WindowNo, AD_Window_ID)
		// static GridWindowVO create (Properties ctx, int WindowNo, int AD_Window_ID, int AD_Menu_ID = 0)
		// GridWindowVO.create: #1 - AD_Window_ID=304; AD_Menu_ID=0
/* statt
		return GridWindow.get(Env.getCtx(), windowCounter, window_ID);
	überschreibe ich es hier
	                     get (Properties ctx, int WindowNo, int AD_Window_ID, boolean virtual)
*/
		LOG.config("windowCounter=" + windowCounter + ", AD_Window_ID=" + window_ID);
		GridWindowVO mWindowVO = create(Env.getCtx(), windowCounter, window_ID, 0); // in APanel Zeile 731
		if (mWindowVO == null)
			return null;
		
		LOG.config("Name=" + mWindowVO.Name
				+ ", Description=" + mWindowVO.Description
				+ ", Help=" + mWindowVO.Help
				+ ", WindowType=" + mWindowVO.WindowType
				+ ", AD_Color_ID=" + mWindowVO.AD_Color_ID
				+ ", AD_Image_ID=" + mWindowVO.AD_Image_ID
				+ ", WinHeight=" + mWindowVO.WinHeight
				+ ", WinWidth=" + mWindowVO.WinWidth
				+ ", IsSOTrx=" + mWindowVO.IsSOTrx
				+ ", IsReadWrite=" + mWindowVO.IsReadWrite // anhand role = MRole.getDefault(ctx, false); windowAccess = role.getWindowAccess(vo.AD_Window_ID);
				);
		Properties ctx = Env.getCtx();
		ctx.forEach((key,value) -> { // zum Test
			LOG.info("ctx key:"+key + " : " + value.toString());
		});
		boolean virtual = false;
		return new GridWindow(mWindowVO, virtual); // in APanel Zeile 738

	}
	// == org.compiere.model.GridWindowVO ersetzen, dmit das sql angezeigt werden kann:
	public static GridWindowVO create (Properties ctx, int WindowNo, int AD_Window_ID, int AD_Menu_ID) {
		StringBuffer sql = new StringBuffer("SELECT Name,Description,Help,WindowType, "
			+ "AD_Color_ID,AD_Image_ID,WinHeight,WinWidth, "
			+ "IsSOTrx ");
		if (Env.isBaseLanguage(ctx, "AD_Window"))
			sql.append("\nFROM AD_Window w \nWHERE w.AD_Window_ID=? AND w.IsActive='Y'");
		else
			sql.append("\nFROM AD_Window_vt w \nWHERE w.AD_Window_ID=?")
				.append(" AND AD_Language='")
				.append(Env.getAD_Language(ctx)).append("'");
		LOG.config("AD_Window_ID="+AD_Window_ID+" sql=\n"+sql
				+ "\n !!! in GridWindowVO.create(..) call createTabs (GridWindowVO mWindowVO) !");
/* z.B.
SELECT Name,Description,Help,WindowType, AD_Color_ID,AD_Image_ID,WinHeight,WinWidth, IsSOTrx 
FROM AD_Window w 
WHERE w.AD_Window_ID=304 AND w.IsActive='Y'
 */
		// GridWindowVO.create auch private static boolean createTabs (GridWindowVO mWindowVO)
		return GridWindowVO.create(ctx, WindowNo, AD_Window_ID, AD_Menu_ID);
	}

	private void initGridWindow(GridWindow gridWindow) {
		LOG.config(">>>>GridWindow.get ...");
		this.gridWindow = gridWindow; //GridWindow.get(ctx, this.windowNo, this.window_ID); 
		LOG.config("gridWindow:"+gridWindow.toString() + " getWindowType:"+gridWindow.getWindowType() + " with Tab#:"+gridWindow.getTabCount());
		LOG.config("<<<<");
		this.gridTabs = new ArrayList<GridTab>(5); // initialCapacity : 5
/* WINDOWTYPEs: aus GridWindowVO		
		public static final String	WINDOWTYPE_QUERY = "Q";
		public static final String	WINDOWTYPE_TRX = "T";
		public static final String	WINDOWTYPE_MMAINTAIN = "M";
TODO Demo für jeden Typ
*/
		this.gridTabs = new ArrayList<GridTab>(gridWindow.getTabCount());
		this.tabs = new ArrayList<Tab>(gridWindow.getTabCount());
		for (int i = 0; i < gridWindow.getTabCount(); i++) {
			Tab tab = new Tab(this, i);
			this.tabs.add(tab); // gridTabs und tabs korrespondieren
			this.gridTabs.add(tab.getGridTab());
		}
	}
	
	public Tab getSelectedTab() {
		if(this.tabPane==null) {
			LOG.warning("tabPane is null");
			return (Tab)null;
		}
		int index = tabPane.getSelectedIndex();
		LOG.config("tabPane selected/tabs:"+index + "/" + tabPane.getTabCount());
		return (Tab)tabPane.getComponentAt(index);
	}
	
	List<GridTab> getGridTabs() {
		return this.gridTabs;
	}
	
	List<Tab> getTabs() {
		return this.tabs;
	}
	
	// aus org.jdesktop.swingx.demos.table.XTableDemo
    protected Container createStatusBar() {
        JXStatusBar statusBar = new JXStatusBar(); // aus jdesktop
        statusBar.putClientProperty("auto-add-separator", Boolean.FALSE);
        // Left status area
        statusBar.add(Box.createRigidArea(new Dimension(10, 22)));
        statusBarLeft = Box.createHorizontalBox();
        statusBar.add(statusBarLeft, JXStatusBar.Constraint.ResizeBehavior.FILL);
        actionStatus = new JLabel();
        actionStatus.setName("loadingStatusLabel");
        actionStatus.setHorizontalAlignment(JLabel.LEADING);
        statusBarLeft.add(actionStatus);

        // Middle (should stretch)
//        statusBar.add(Box.createHorizontalGlue());
//        statusBar.add(Box.createHorizontalGlue());
        statusBar.add(Box.createVerticalGlue());
        statusBar.add(Box.createRigidArea(new Dimension(50, 0)));

        // Right status area
        tableStatus = new JLabel(); 
        tableStatus.setName("rowCountLabel");
        JComponent bar = Box.createHorizontalBox();
        bar.add(tableStatus);
        tableRows = new JLabel("0");
        bar.add(tableRows);
        
        statusBar.add(bar);
        statusBar.add(Box.createHorizontalStrut(12));
        
		this.progressBar = new JProgressBar(0, 100);
		progressBar.setStringPainted(true);
        statusBarLeft.add(progressBar); // in XTableDemo wird sie ein/ausgeblendet, wie actionStatus:
        // in setLoadState:
        // statusBarLeft.remove(progressBar);
        // statusBarLeft.remove(actionStatus);
        // revalidate(); repaint();
        return statusBar;
    }

    // überschreiben, damit beim DISPOSE_ON_CLOSE der Loader worker gestoppt wird : cancel
    public void dispose() {
    	tabs.forEach( (tab) -> {
    		tab.cancel();
		});
    	super.dispose();
    }
    
	public void setTitle(String title) {
		super.setTitle(title);
	}
	void setTitle() {
		setTitle("["+this.windowNo+"] " + this.gridWindow.getName());
	}
	
	public void setTabPane(HidableTabbedPane hidableTabbedPane) { // TODO raus
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
