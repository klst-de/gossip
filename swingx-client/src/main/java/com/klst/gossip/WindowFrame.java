package com.klst.gossip;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.SwingWorker.StateValue;

import org.compiere.apps.form.FormPanel;
import org.compiere.model.GridWindow;
import org.compiere.model.GridWindowVO;
import org.compiere.model.MProcess;
import org.compiere.model.MWindow;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.Trx;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXStatusBar;

import com.klst.gossip.wrapper.TabModel;
import com.klst.gossip.wrapper.WindowModel;
import com.klst.icon.AbstractImageTranscoder;

import gov.nasa.arc.mct.gui.impl.HidableTabbedPane;

/*
 - visualisiert MWindow mWindow
 - es ist ein Top Level Component 
 - enthält JPanel im Borderlayout im ContentPane, das wiederum besteht aus tabPane : HidableTabbedPane ; // hierin die AD tabs
   - also List<Tab> Tab aka JPanel
   - oder InfoPanel aka JPanel 
   - oder ProcessPanel aka JPanel
   - auch MenuPanel ist ein JPanel -> extends JXPanel
 */
// ersetzt (AD client) APanel
public class WindowFrame extends JXFrame implements WindowListener {
	
	private static final long serialVersionUID = 5098403364836474988L;

	private static final Logger LOG = Logger.getLogger(WindowFrame.class.getName());
	
	static final int INFO_WINDOW_ID = 385; // "Info Window" == AD_Window_ID=385
	
	public static final String P_SHOW_TRL = "#"+Ini.P_SHOW_TRL;
	
	static final int SMALL_ICON_SIZE = 16;
	static final int LARGE_ICON_SIZE = 24;
	AbstractImageTranscoder AIT = AbstractImageTranscoder.getInstance();
	
	private static int windowCounter = 0; // für windowNo, wird pro ctor hochgezählt
	private int windowNo;
	
	private int window_ID;
	private Properties ctx = null;
	private String trxName;
	private MWindow mWindow; // eigentlich sollten alle mWindow Daten aus gridWindow kommen
	// GridWindow is ein wrapper für MWindow
	protected GridWindow windowModel; // WindowModel extends GridWindow
//	protected GridWindow gridWindow; //                 (base)GridWindow implements Serializable, contains GridWindowVO ArrayList<GridTab> Set<GridTab>
	protected InfoPanel infoWindow; 
	// in List sind alle / in Set die initalisierten!!!
	// TODO Set<GridTab> initTabs =/= List<GridTab> gridTabs , List<Tab> tabs
	private List<TabModel> tabModels; // TODO verschieben nach WindowPane - wieso List statt Set
	private List<Tab> tabs;
//	Tab currentTab; // nur eine Tab kann es sein, die bekommen wir aus tabPane => getSelectedTab
	protected ProcessPanel processWindow; 
	
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
	 *   - at CENTER    : tabPane              or processPane
	 *   - at PAGE_END  : statusBar mit progressBar
	 */
	RootFrame rootFrame; // mit FrameManager
	JMenuBar menuBar = new JMenuBar();
	JMenu mFile = new JMenu(); // File : JMenuItem's "Quit",  b,  c, ...
	JMenu mEdit = new JMenu(); // Edit : JMenuItem's "New Record", ... and Navigation goto first, next, last Record etc
	JMenu mInfo = new JMenu(); // Infofenster
	JMenu mSetup = new JMenu(); // ...
	JMenu mHelp = new JMenu(); // ...
	JPanel jPanel = new JPanel(new BorderLayout()); // TODO JXPanel
	HidableTabbedPane tabPane;
	JXButton runProcessButton;
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
	WindowFrame(String title, RootFrame rootFrame, int window_ID, Object object) {
		super(title);
		windowCounter++;
		this.windowNo = windowCounter-1;
		
		this.rootFrame = rootFrame;
		this.window_ID = window_ID;
		
		initMenuBar();
		Container statusBar = createStatusBar();

		this.ctx = Env.getCtx();
		this.trxName = Trx.createTrxName(WindowFrame.class.getName());
		if(object instanceof GridWindow  && object.getClass() != GridWindow.class) {
//		if(object instanceof GridWindow  && object.getClass() != GridWindow.class) {
			initWindow((GridWindow)object);
			mWindow = new MWindow(ctx, this.window_ID, trxName);
			LOG.config("mWindow:"+mWindow);
			setTitle("["+this.windowNo+"] " + this.windowModel.getName());
//		} else if(object instanceof WindowModel) {
		} else if(object instanceof GridWindow) {
			LOG.warning("DAS SOLL NICHT SEIN GridWindow object:"+object);
			initWindow((WindowModel)object);
			mWindow = new MWindow(ctx, this.window_ID, trxName);
			LOG.warning("DAS SOLL NICHT SEIN mWindow:"+mWindow);
			setTitle("["+this.windowNo+"] " + this.windowModel.getName());
		} else if(object instanceof GenericDataModel) {
			initInfoWindow((GenericDataModel)object);
			setTitle("["+this.windowNo+"] Info " + this.infoWindow.getName());
		} else if(object instanceof GenericFormPanel && object.getClass() != GenericFormPanel.class) {
			LOG.config(">>>>>>>>>>>>>>>>>>> object isSUBCLASSof GenericFormPanel "+object);
			GenericFormPanel formPanel = (GenericFormPanel)object;
			setTitle("["+this.windowNo+"] formPanel WindowId=" + formPanel.getWindowId());
			formPanel.setStatusBar(statusBar);
		} else if(object instanceof GenericFormPanel) {
			LOG.config(">>>>>>>>>>>>>>>>>>> object instanceof GenericFormPanel "+object);
		} else if(object instanceof MProcess) {
/* Allgemein: MProcess extends X_AD_Process extends PO implements I_AD_Process, I_Persistent
                                                                  I_AD_Process.Table_Name = "AD_Process" 
in AD 3.9.3 gibt es #580 (#11 inaktive #569 aktive) Berichte (#156 aktive) und Prozesse
- von den #413 aktiven Prozessen haben 71 keine Klassenimplementiertung, 
-- die #71 sind entweder
--     #02 DB Procedure : AD_Process.procedurename <>null
--     #38 workflows : AD_Process.ad_workflow_id <>null
--     #05 special form : AD_Process.ad_form_id <>null
--     #25 smart browse : AD_Process.ad_browse_id <>null
--     #01 nix von den oberen: AD_Workflow DocValue / Auf Belegwert basierende Workflows
select * from ad_process 
where isactive='Y' and isreport='N' 
and classname is null 
order by procedurename
        ,ad_workflow_id
        ,ad_form_id
        ,ad_browse_id
-- für #342 gibt es eine klasse z.B. Classname=org.compiere.process.CacheReset die den Prozess implementiert
(base) public class org.compiere.process.CacheReset extends SvrProcess / abstract class SvrProcess implements ProcessCall.startProcess(Properties ctx, ProcessInfo pi, Trx trx)
implements ClientProcess

Zum Ausführen einer Prozessklasse procCla gibt es in AD ProcessCtl implements Runnable mit run(). Das ist sehr allgemein, 
aber since 1.6 gibt es SwingWorker, die besser wären. Besonders für die Prozesse die im swing client ausgeführt werden.
- es gibt nur zwei Prozesse, die NUR im swing laufen, dh "implements ClientProcess" haben:
-- [205-Cache Reset] Classname=org.compiere.process.CacheReset
-- und 53008 org.eevolution.process.CompletePrintOrder


am Beispiel von MProcess:MProcess[205-Cache Reset] Classname=org.compiere.process.CacheReset

          abstract class SvrProcess implements ProcessCall // Server Process Template
                                               ProcessCall.startProcess(Properties ctx, ProcessInfo pi, Trx trx);
class CacheReset extends SvrProcess implements ClientProcess // process can only be run on the client
d.h. nirgends wird die swing worker funktionalotät erwartet:
 - protected abstract T doInBackground() throws Exception
 - protected void process(List<V> chunks) 
 - protected void done()

 */
			initProcessWindow((MProcess)object);
			// TODO process icon statt java + this.processWindow.getName() liefert null
			setTitle("["+this.windowNo+"] Process " + this.processWindow.getName());
			pack();
			
/*			MProcess mProcess = (MProcess)object;
			LOG.config("object/MProcess:"+mProcess + " Classname="+mProcess.getClassname());
			ProcessInfo pi = new ProcessInfo(null, mProcess.getAD_Process_ID());
			pi.setAD_User_ID (Env.getAD_User_ID(Env.getCtx()));
			pi.setAD_Client_ID(Env.getAD_Client_ID(Env.getCtx()));
			pi.setInterfaceType(ProcessInfo.INTERFACE_TYPE_SWING);
			// this ist der parameterPanel bzw. processDialog, zum Prozessstart braucht es einen Start-Buttom und den Listener dazu
			m_textArea = new JTextArea(); // Results (wg. Demo PrimeNumbersTask), sichtbar erst nach dem Start 
//			m_textArea.setRows(30);
			m_textArea.setVisible(false);
			jPanel.add( new JScrollPane(m_textArea), BorderLayout.CENTER );
			//jPanel.add(m_textArea, BorderLayout.CENTER);
			runProcessButton = new JXButton("Start Prozess", AIT.getImageIcon(AIT.PROCESS, SMALL_ICON_SIZE));
			runProcessButton.setName("runProcessButton");
	        runProcessButton.addActionListener(event -> {
	        	// TODO ....  ein Objekt/Task bereitstellen ähnlich zu GenericDataLoader, das man starten kann
	        	//ProcessCtl xxx; //.process(parent.getParentProcess(), getWindowNo(), this, getProcessInfo(), null);
	        	SwingWorker task = new PrimeNumbersTask(10,1000); // nur zur Demo
	    		BindingGroup group = new BindingGroup();
	            group.addBinding(Bindings.createAutoBinding(READ, task, BeanProperty.create("progress"), this.progressBar, BeanProperty.create("value")));
	            group.addBinding(Bindings.createAutoBinding(READ, task, BeanProperty.create("state"), this, BeanProperty.create("loadState"))); // call setLoadState 
	            group.bind();
	            m_textArea.setRows(30);
	            m_textArea.setVisible(true);
//	            task.addPropertyChangeListener(event2 -> {
//	            	if ("state".equals(event2.getPropertyName())) {
//	            		setLoadState((StateValue)event2.getNewValue());
//	            	}
//	            });
	        	task.execute();		
	        });
			jPanel.add(runProcessButton, BorderLayout.LINE_END); // für Demo EAST
			if(statusToTrafficlights.isEmpty()) {
				statusToTrafficlights.put(StateValue.PENDING, AIT.getImageIcon(AIT.RLI, WindowFrame.SMALL_ICON_SIZE));
				statusToTrafficlights.put(StateValue.STARTED, AIT.getImageIcon(AIT.YLI, WindowFrame.SMALL_ICON_SIZE));
				statusToTrafficlights.put(StateValue.DONE   , AIT.getImageIcon(AIT.GLI, WindowFrame.SMALL_ICON_SIZE));
			}
------------- */
		} else {
			LOG.config("object:"+object);
			assert(window_ID==-1);
			setTitle(title); 
		}
		getContentPane().add(jPanel);
		jPanel.add(statusBar, BorderLayout.PAGE_END);
		
		addWindowListener(this); // wg. - JFrame.DISPOSE_ON_CLOSE
	}

//	public MenuBar getMenuBar() {
	// Ergebnis: sollte immer JMenuBar sein 
//		return super.getMenuBar();
//	}
	
	@Override // javax.swing.JFrame wg.LOG
	public Container getContentPane() {
		// Ergebnis: Container sollte immer JXPanel sein 
		Container contentPane = super.getContentPane();
		LOG.config("contentPane:"+contentPane);
		return contentPane;
	}
	
	private void initMenuBar() {
		LOG.config(menuBar.toString());
		this.setJMenuBar(menuBar);
		menuBar.add(mFile);
		menuBar.add(mInfo);
		menuBar.add(mEdit);
		
		mFile.setName("file");
		mFile.setText("File");
		mInfo.setName("info");
		mInfo.setText("Info");
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
            
            JMenuItem infoProductlItem = new JMenuItem("Product Info", AIT.getImageIcon(AIT.PRODUCT, SMALL_ICON_SIZE));
            infoProductlItem.setName("infoProduct");
            infoProductlItem.setActionCommand("infoProduct");
            infoProductlItem.addActionListener(event -> {
            	LOG.config("item:"+infoProductlItem);
				LOG.config("canAccessInfo:"+InfoDataModel.canAccessInfo("Product"));
				// INFO_WINDOW_ID "Info Window" AD_Window_ID=385
				GenericDataModel tm = new InfoDataModel("Product", INFO_WINDOW_ID);
				rootFrame.openNewFrame(INFO_WINDOW_ID, tm);
            });
            mInfo.add(infoProductlItem);
            
            JMenuItem infoBPartnerItem = new JMenuItem("Business Partner Info", AIT.getImageIcon(AIT.BPARTNER, SMALL_ICON_SIZE));
            infoBPartnerItem.setName("infoBPartner");
            infoBPartnerItem.setActionCommand("infoBPartner");
            infoBPartnerItem.addActionListener(event -> {
            	LOG.config("item:"+infoBPartnerItem);
//				LOG.config("canAccessInfo:"+InfoDataModel.canAccessInfo("BPartner"));
//				GenericDataModel tm = new InfoDataModel("BPartner", INFO_WINDOW_ID);
//				rootFrame.openNewFrame(INFO_WINDOW_ID, tm);
            	int AD_Form_ID = 1000001; 
            	String className = "org.compiere.apps.form.InfoBP";
            	LOG.info("AD_Form_ID=" + AD_Form_ID + " - Class=" + className);
            	FormPanel m_panel;
				try {
					// Create instance w/o parameters
					m_panel = (FormPanel) Class.forName(className).newInstance(); // === new className()
					FormFrame frame = this.rootFrame.makeFormFrame(AD_Form_ID, m_panel);
					m_panel.init(frame.getWindowNo(), frame);
				} catch (Exception e) {
					LOG.log(Level.SEVERE, "Class=" + className + ", AD_Form_ID=" + AD_Form_ID, e);
				}
            });
            mInfo.add(infoBPartnerItem);
// TEST TODO muss raus            
            JMenuItem infoWFActivItem = new JMenuItem("WorkflowActivities TEST Info", AIT.getImageIcon(AIT.TASK, SMALL_ICON_SIZE));
            infoWFActivItem.setName("infoWFActiv");
            infoWFActivItem.setActionCommand("infoWFActiv");
            infoWFActivItem.addActionListener(event -> {
            	LOG.config("item:"+infoWFActivItem);
            	// aus FormFrame.openForm: AD_Form_ID=1000000 - Class=org.compiere.apps.form.UnprocessedDocuments
            	int AD_Form_ID = 1000000; 
            	String className = "org.compiere.apps.form.WorkflowActivities";
//            	String name;
            	LOG.info("AD_Form_ID=" + AD_Form_ID + " - Class=" + className);
            	FormPanel m_panel;
				try {
					// Create instance w/o parameters
					m_panel = (FormPanel) Class.forName(className).newInstance(); // === new WorkflowActivities()
					FormFrame frame = this.rootFrame.makeFormFrame(AD_Form_ID, m_panel);
					m_panel.init(frame.getWindowNo(), frame);
				} catch (Exception e) {
					LOG.log(Level.SEVERE, "Class=" + className + ", AD_Form_ID=" + AD_Form_ID, e);
				}
            });
            mInfo.add(infoWFActivItem);
            
            JMenuItem cancelItem = new JMenuItem("Cancel", AIT.getImageIcon(AIT.CANCEL, SMALL_ICON_SIZE));
            cancelItem.setName("cancel");
            cancelItem.setActionCommand("cancel");
            cancelItem.addActionListener(event -> {
            	LOG.config("eventSource:"+event.getSource());
            	Component c = this.getSelectedTab();
            	if(c instanceof Tab) {
            		this.dispose();
            		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            	} else if(c instanceof InfoPanel) {
            		InfoPanel i = (InfoPanel)c;
            		i.cancel();
            	} else {
            		LOG.warning("cancel on Component "+c);
            	}
            });
            mEdit.add(cancelItem);
            
            JMenuItem refreshItem = new JMenuItem("Refresh", AIT.getImageIcon(AIT.REFRESH, SMALL_ICON_SIZE));
            refreshItem.setName("refresh");
            refreshItem.setActionCommand("refresh");
            refreshItem.addActionListener(event -> {
            	Component c = this.getSelectedTab();
            	if(c instanceof Tab) {
            		Tab tab = (Tab)c;
            		tab.refresh();
            	} else if(c instanceof InfoPanel) {
            		InfoPanel i = (InfoPanel)c;
            		i.refresh();
            	} else if(c==null && this instanceof RootFrame) {
            		this.refresh();
            	} else {
            		LOG.warning("refresh on SelectedTab Component "+c+ " , "+this);
            	}
            });
            mEdit.add(refreshItem);

            JMenuItem firstItem = new JMenuItem("First", AIT.getImageIcon(AIT.FIRST, SMALL_ICON_SIZE));
            firstItem.setName("first");
            firstItem.setActionCommand("first");
            firstItem.addActionListener(event -> {
            	Component c = this.getSelectedTab();
            	if(c instanceof Tab) {
            		Tab tab = (Tab)c;
            		tab.first();
            	} else if(c instanceof InfoPanel) {
            		InfoPanel i = (InfoPanel)c;
            		i.first();
            	} else {
            		LOG.warning("go to first on Component "+c);
            	}
            });
            mEdit.add(firstItem);

            JMenuItem previousItem = new JMenuItem("Previous", AIT.getImageIcon(AIT.PREVIOUS, SMALL_ICON_SIZE));
            previousItem.setName("previous");
            previousItem.setActionCommand("previous");
            previousItem.addActionListener(event -> {
            	Component c = this.getSelectedTab();
            	if(c instanceof Tab) {
            		Tab tab = (Tab)c;
            		tab.previous();
            	} else if(c instanceof InfoPanel) {
            		InfoPanel i = (InfoPanel)c;
            		i.previous();
            	} else {
            		LOG.warning("previous item on Component "+c);
            	}
            });
            mEdit.add(previousItem);

            JMenuItem nextItem = new JMenuItem("Next", AIT.getImageIcon(AIT.NEXT, SMALL_ICON_SIZE));
            nextItem.setName("next");
            nextItem.setActionCommand("next");
            nextItem.addActionListener(event -> {
            	Component c = this.getSelectedTab();
            	if(c instanceof Tab) {
            		Tab tab = (Tab)c;
            		tab.next();
            	} else if(c instanceof InfoPanel) {
            		InfoPanel i = (InfoPanel)c;
            		i.next();
            	} else {
            		LOG.warning("next item on Component "+c);
            	}
            });
            mEdit.add(nextItem);

            JMenuItem lastItem = new JMenuItem("Last", AIT.getImageIcon(AIT.LAST, SMALL_ICON_SIZE));
            lastItem.setName("last");
            lastItem.setActionCommand("last");
            lastItem.addActionListener(event -> {
            	Component c = this.getSelectedTab();
            	if(c instanceof Tab) {
            		Tab tab = (Tab)c;
            		tab.last();
            	} else if(c instanceof InfoPanel) {
            		InfoPanel i = (InfoPanel)c;
            		i.last();
            	} else {
            		LOG.warning("next item on Component "+c);
            	}
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
	// GridWindow.get/WindowModel.get wirft keine exception, das Ergebnis kann aber null sein!
	static GridWindow getWindowModel(int window_ID) {
		// das statische GridWindow.get erstellt eine window value object instanz GridWindowVO.create (Env.getCtx(), WindowNo, AD_Window_ID)
		// static GridWindowVO create (Properties ctx, int WindowNo, int AD_Window_ID, int AD_Menu_ID = 0)
		// GridWindowVO.create: #1 - AD_Window_ID=304; AD_Menu_ID=0
/* statt
		return WindowModel.get(Env.getCtx(), windowCounter, window_ID);
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
		return new WindowModel(mWindowVO, virtual); // in APanel Zeile 738

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
				+ "\n !!! in (base)GridWindowVO.create(..) call createTabs (GridWindowVO mWindowVO) !");
/* z.B.
SELECT Name,Description,Help,WindowType, AD_Color_ID,AD_Image_ID,WinHeight,WinWidth, IsSOTrx 
FROM AD_Window w 
WHERE w.AD_Window_ID=304 AND w.IsActive='Y'
 */
		if(AD_Window_ID==INFO_WINDOW_ID) { // Für INFO_WINDOW_ID liefert GridWindowVO.create "Not found" - sollte aber NoAccess sein
			//GridWindowVO vo = new GridWindowVO(ctx, WindowNo); // private ctor!
			//LOG.config("Add R/O acces to WindowNo "+INFO_WINDOW_ID); // auch das bringt nix
// -----------> MRole.saveWarning: AccessTableNoView - Required=4(System Data) != UserLevel= CO [16]
			return null;
		}
		// GridWindowVO.create auch private static boolean createTabs (GridWindowVO mWindowVO)
		return GridWindowVO.create(ctx, WindowNo, AD_Window_ID, AD_Menu_ID);
	}

	private void initProcessWindow(MProcess process) {
		this.processWindow = new ProcessPanel(this, process);	
	}
	private void initInfoWindow(GenericDataModel gdm) {
		this.infoWindow = new InfoPanel(this, gdm);
	}
	private void initWindow(GridWindow gridWindow) {
		LOG.config(">>>>GridWindow.get ...");
		this.windowModel = (WindowModel)gridWindow; //GridWindow.get(ctx, this.windowNo, this.window_ID); 
		LOG.config("windowModel:"+windowModel.toString() + " WindowType:"+windowModel.getWindowType() + " with Tab#:"+windowModel.getTabCount());
		LOG.config("<<<<");
		this.tabModels = new ArrayList<TabModel>(windowModel.getTabCount());
		this.tabs = new ArrayList<Tab>(windowModel.getTabCount());
		for (int i = 0; i < windowModel.getTabCount(); i++) {
			Tab tab = new Tab(this, i);
			this.tabs.add(tab); // gridTabs und tabs korrespondieren
			this.tabModels.add(tab.getTabModel());
		}
	}
//	private void initWindow(WindowModel gridWindow) {
//		LOG.config(">>>>GridWindow.get ...");
//		this.windowModel = gridWindow; //GridWindow.get(ctx, this.windowNo, this.window_ID); 
//		LOG.config("windowModel:"+windowModel.toString() + " WindowType:"+windowModel.getWindowType() + " with Tab#:"+windowModel.getTabCount());
//		LOG.config("<<<<");
//		this.tabModels = new ArrayList<TabModel>(windowModel.getTabCount());
//		this.tabs = new ArrayList<Tab>(windowModel.getTabCount());
//		for (int i = 0; i < windowModel.getTabCount(); i++) {
//			Tab tab = new Tab(this, i);
//			this.tabs.add(tab); // gridTabs und tabs korrespondieren
//			this.tabModels.add(tab.getTabModel());
//		}
//	}
	
	public Component getSelectedTab() { // Component can be Tab
		if(this.tabPane==null) {
			LOG.warning("tabPane is null");
			return null;
		}
		int index = tabPane.getSelectedIndex();
		Component c = tabPane.getComponentAt(index);
		LOG.config("tabPane selected/tabs:"+index + "/" + tabPane.getTabCount() + " Component:"+c);
		return c;
	}
	
	List<TabModel> getTabModels() {
		return this.tabModels;
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
    	if(infoWindow!=null) infoWindow.cancel();
    	if(tabs!=null) {
    		tabs.forEach( (tab) -> {
    			tab.cancel();
    		});
    		tabs = null;
		} else {
			
		}
    	this.rootFrame.remove(this);
    	super.dispose();
    }
    
	public void setTitle(String title) {
		super.setTitle(title);
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
	
// inner
	private JTextArea m_textArea;
	private static EnumMap<StateValue, Icon> statusToTrafficlights = new EnumMap<>(StateValue.class);
	public void setLoadState(StateValue state) { // ähnlich zu Tab.setLoadState()
		LOG.config(this.getName()+" StateValue:"+state);
		this.actionStatus.setIcon(statusToTrafficlights.get(state));
		if(state.equals(StateValue.STARTED)) {
			this.setVisible(true);		
			updateStatusBar();
		} else if(state.equals(StateValue.DONE)) {
			this.setVisible(true);	
			updateStatusBar();
		}
	}
	private void updateStatusBar() {
//		StringBuilder text = new StringBuilder("").append(currentRow+1).append("/").append(dataModel.getRowCount());
//		if(dataModel.getRowCount()==dataModel.getRowsToLoad()) {
//			// OK alles geladen
//		} else {
//			text.append("/").append(dataModel.getRowsToLoad());
//		}
//		this.tableRows.setText(text.toString());
	}
	class PrimeNumbersTask extends SwingWorker<List<BigInteger>, BigInteger> {
		
	    private final int m_bitCount;
	    private final int m_primeCount;
	    private ArrayList<BigInteger> m_numbers = new ArrayList<BigInteger>();
	    private Random m_randomGenerator = new Random(0);
	    
	    PrimeNumbersTask(int bitCount, int primeCount)
	    {
	      m_bitCount = bitCount;
	      m_primeCount = primeCount;
	    }

	    @Override
		public List<BigInteger> doInBackground() {
	    	LOG.config("m_bitCount="+m_bitCount + " m_primeCount="+m_primeCount);
			while ((m_numbers.size() < m_primeCount) && !isCancelled()) {
				BigInteger number = nextPrimeNumber();
				m_numbers.add(number);
				super.publish(number);
				setProgress(100 * m_numbers.size() / m_primeCount);
			}
			return m_numbers;
		}

		private BigInteger nextPrimeNumber() {
			return BigInteger.probablePrime(100, m_randomGenerator);
		}

		@Override
		protected void process(List<BigInteger> chunks) {
			LOG.config("chunks#:"+chunks.size());
			for (BigInteger number : chunks) {
				m_textArea.append(number + "\n");
			}
		}

		@Override
		protected void done() {
			m_textArea.append("Generated total of " + m_numbers.size() + " primes of size " + m_bitCount + " bits.\n");
			// Reset state of buttons.
//			m_startButton.setEnabled(true);
//			m_cancelButton.setEnabled(false);
		}

}

}
