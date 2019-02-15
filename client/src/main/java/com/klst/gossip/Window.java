package com.klst.gossip;

import static org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker.StateValue;

import org.compiere.model.GridTab;
import org.compiere.model.GridWindow;
import org.compiere.model.MWindow;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.Trx;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import com.klst.icon.AbstractImageTranscoder;

import gov.nasa.arc.mct.gui.impl.HidableTabbedPane;

/*
 - visualisiert MWindow mWindow
 - es ist ein Top Level Component 
 - enthält JPanel im Borderlayout im ContentPane 
 - das wiederum besteht aus tabPane : HidableTabbedPane ; // hierin die AD tabs
 */

public class Window extends JFrame implements WindowListener {
	
	private static final long serialVersionUID = 5098403364836474988L;

	private static final Logger LOG = Logger.getLogger(Window.class.getName());
	
	public static final String P_SHOW_TRL = "#"+Ini.P_SHOW_TRL;
	
	static final int SMALL_ICON_SIZE = 16;
	static final int LARGE_ICON_SIZE = 24;
	AbstractImageTranscoder AIT = AbstractImageTranscoder.getInstance();
	
	private static int mindowCounter = 0; // für windowNo wird pro ctor hochgezählt
	private int windowNo;
	
	private int window_ID;
	private Properties ctx = null;
	private String trxName;
	protected MWindow mWindow;
	protected GridWindow gridWindow;
	List<GridTab> gridTabs; // TODO verschieben nach Tab
	List<Tab> tabs;
	
	/* ui:
	 * Windows aka (swing) Frames:
	 *   rootFrame/windowNo=0, window/windowNo=1, ... window/windowNo=n(this), window/windowNo=n+1, ...
	 * this frame:
	 * - menuBar
	 *   - mFile, ...
	 *     - quitItem, ...
	 * this ContentPane contains:
	 * - jPanel
	 *   - at PAGE_START: toolBar
	 *   - at CENTER    : tabPane
	 *   - at PAGE_END  : progressBar
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
	JProgressBar progressBar;

	// ctor
	/* super ctors
	 * 	   JFrame() throws HeadlessException 
	 *     JFrame(GraphicsConfiguration gc)
	 *     JFrame(String title) throws HeadlessException
	 *     JFrame(String title, GraphicsConfiguration gc)
	 */
	Window(String title) { // für RootFrame
		this(title, null, -1);
	}
	Window(String title, RootFrame rootFrame, int window_ID) {
		super(title);
		mindowCounter++;
		this.windowNo = mindowCounter-1;
		
		this.rootFrame = rootFrame;
		this.window_ID = window_ID;
		
		initMenuBar();

		this.ctx = Env.getCtx();
		this.trxName = Trx.createTrxName(Window.class.getName());
		if(this.window_ID == -1) {
			setTitle(title); 
		} else {
			initGridWindow();
			// mWindow ==> gridWindow
			mWindow = new MWindow(ctx, this.window_ID, trxName);
			LOG.config("mWindow:"+mWindow);
			setTitle(); 
		}
		getContentPane().add(jPanel);
		addWindowListener(this); // wg. - JFrame.DISPOSE_ON_CLOSE
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
            
            JMenuItem previousItem = new JMenuItem("Previous", AIT.getImageIcon(AIT.PREVIOUS, SMALL_ICON_SIZE));
            previousItem.setName("previous");
            previousItem.setActionCommand("previous");
            previousItem.addActionListener(event -> {
/*   aus Apanel m_curGC == currentGridControler
			m_curGC.getTable().removeEditor();
			m_curGC.acceptEditorChanges();
			if ((actionEvent.getModifiers() & ActionEvent.SHIFT_MASK) != 0) {
				currentTab.switchRows(currentTab.getCurrentRow(), currentTab.getCurrentRow() - 1, m_curGC.getTable().getSortColumn(), m_curGC.getTable().isSortAscending());
			} else {
				currentTab.navigateRelative(-1); <==== die tatsächliche Navogation:
	// in (base) GridTab			
	public int navigateRelative (int rowChange)
	{
		return navigate (m_currentRow + rowChange);
	}   //  navigateRelative
				
				
			}

 */
            });
            mEdit.add(previousItem);
        }

	}

	public int getWindowNo() {
		return this.windowNo;
	}
	
	private void initGridWindow() {
		this.gridWindow = GridWindow.get(ctx, this.windowNo, this.window_ID); 
		this.gridTabs = new ArrayList<GridTab>(5); // initialCapacity : 5
		LOG.config("gridWindow:"+gridWindow.toString() + " with Tab#:"+gridWindow.getTabCount());
		this.gridTabs = new ArrayList<GridTab>(gridWindow.getTabCount());
		this.tabs = new ArrayList<Tab>(gridWindow.getTabCount());
		for (int i = 0; i < gridWindow.getTabCount(); i++) {
			if(gridWindow.isTabInitialized(i)==false) {
				LOG.config("gridTab "+i+" not initialized. Do it now ...");
				gridWindow.initTab(i);
			}
			GridTab gridTab = gridWindow.getTab(i);
			this.gridTabs.add(gridTab);
			this.tabs.add(new Tab(gridTab)); // gridTabs und tabs korrespondieren
		}
	}
	
	public GenericDataLoader getDataLoader(JComponent vPanel) { // TEST TODO
		jPanel.add(vPanel, BorderLayout.CENTER);
		
		this.progressBar = new JProgressBar(0, 100);
		progressBar.setStringPainted(true);
		jPanel.add(progressBar, BorderLayout.PAGE_END);
		
		GridTab gridTab = gridTabs.get(0); // first Tab
		GenericTableModel tableModel = new GenericTableModel(gridTab, getWindowNo());
 		GenericDataLoader task = new GenericDataLoader(tableModel);
		
		pack();
		setLocationRelativeTo(null);; // oben links würde es sonst angezeigt
		setVisible(true); 
		return task;	
	}
	
	public GenericDataLoader getDataLoader() {
		this.progressBar = new JProgressBar(0, 100);
		progressBar.setStringPainted(true);
		jPanel.add(progressBar, BorderLayout.PAGE_END);
		
		GridTab gridTab = gridTabs.get(0); // first Tab
		Tab tab = tabs.get(0); 
        this.tabPane = new HidableTabbedPane(gridTab.getName(), tab);
        for (int i = 1; i < gridTabs.size(); i++) { // ohne first
        	GridTab gt = gridTabs.get(i);
        	Tab t = tabs.get(i); 
        	tabPane.addTab(gridTabs.get(i).getName(), tabs.get(i));
        	t.loader = getDataLoader(gt, t);
        }
        jPanel.add(tabPane, BorderLayout.CENTER);
        
        return getDataLoader(gridTab, tab);
	}
	
	private GenericDataLoader getDataLoader(GridTab gridTab, Tab tab) {
		GenericTableModel tableModel = new GenericTableModel(gridTab, getWindowNo());
        JScrollPane scrollpane = new JScrollPane(tab.jXTable);
        Stacker stacker = new Stacker(scrollpane);
        tab.jXTable.setName(gridTab.getName());
        tab.add(stacker, BorderLayout.CENTER);

        tab.jXTable.setColumnControlVisible(true);
        // replace grid lines with striping 
        tab.jXTable.setShowGrid(false, false);
        tab.jXTable.addHighlighter(HighlighterFactory.createSimpleStriping());
        // initialize preferred size for table's viewable area
        tab.jXTable.setVisibleRowCount(10); // TODO

//        CustomColumnFactory factory = new CustomColumnFactory();

        tab.jXTable.setModel(tableModel);
 		GenericDataLoader task = new GenericDataLoader(tableModel);
 		
		JLabel status = new JLabel();
        BindingGroup group = new BindingGroup();
        group.addBinding(Bindings.createAutoBinding(READ, task, 
        		BeanProperty.create("progress"),
                progressBar, BeanProperty.create("value")));
        group.addBinding(Bindings.createAutoBinding(READ, task, 
        		BeanProperty.create("state"),
        		this, BeanProperty.create("loadState"))); // call setLoadState 
        group.bind();

		pack();
		setLocationRelativeTo(null);; // oben links würde es sonst angezeigt
//		setVisible(true); // in setLoadState
		
		return task;
	}
	
	public void setLoadState(StateValue state) {
		LOG.config("StateValue:"+state);
		if(state.equals(StateValue.STARTED)) {
			setVisible(true);
		}
	}

	public List<GridTab> getGridTabs() {
		return this.gridTabs;
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
