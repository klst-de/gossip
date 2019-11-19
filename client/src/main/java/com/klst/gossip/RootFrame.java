package com.klst.gossip;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;
import javax.swing.plaf.metal.OceanTheme;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import org.compiere.Adempiere;
import org.compiere.model.GridWindow;
import org.compiere.plaf.CompiereTheme;
import org.compiere.plaf.CompiereThemeBlueMetal;
import org.compiere.util.Env;

import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.SkyBluer;
import com.klst.client.LoginPanel;

import gov.nasa.arc.mct.gui.impl.HidableTabbedPane;

public class RootFrame extends WindowFrame {  // WindowFrame extends JFrame
	
	private static final long serialVersionUID = -400920856924947621L;

	private static final Logger LOG = Logger.getLogger(RootFrame.class.getName());

	private static final String TITLE = "Gossip";
	private static final String METAL = "Metal";
	
	String crossPlatformLookAndFeelClassName = UIManager.getCrossPlatformLookAndFeelClassName();

	// frame mgt
	List<JFrame> frames;
	private static final int FRAMES_INITIAL_CAPACITY = 10;
	private WindowFrame makeFrame(int frameNumber, RootFrame rootFrame, int window_ID, GridWindow gridWindow) {
		WindowFrame frame = new WindowFrame("Frame number " + frameNumber, rootFrame, window_ID, gridWindow);
		frame.setDefaultCloseOperation(frames.isEmpty() ? JFrame.EXIT_ON_CLOSE : JFrame.DISPOSE_ON_CLOSE);
		frames.add(frame);
		return frame;
	}
	private WindowFrame makeWindow(int window_ID, GridWindow gridWindow) {
		int frameNumber = frames.size();
		return makeFrame(frameNumber, this, window_ID, gridWindow);
	}
	boolean remove(JFrame frame) {
		return frames.remove(frame);
	}
	// <<<

	JMenuItem miBank;
	JMenuItem miCountry;
	JMenuItem miDocument;

	// Look & Feel Menu Items
	// crossPlatform: 
	JRadioButtonMenuItem miNimbusLaF;
	JRadioButtonMenuItem miSteelLaF;
	JRadioButtonMenuItem miOceanLaF; // the Java default Metal theme
	JRadioButtonMenuItem miPlastikLaF; // jgoodies
	JRadioButtonMenuItem miCompiereLaF; // compiere
	JRadioButtonMenuItem miCompiereIceLaF;
	// Platform dependent:
	JRadioButtonMenuItem miMotifLaF;
	JRadioButtonMenuItem miWindowsLaF;
	JRadioButtonMenuItem miGimpLaF; // GTK GIMP-Toolkit

	HidableTabbedPane hidableTabbedPane; // hierin loginPanel und die (hidden) Demopanels
	LoginPanel loginPanel;
	MenuPanel menuPanel;

/* Gegenüberstellung start gossip vs AD-client

   (base) org.compiere.Adempiere.main ist jeweils die start methode mit programm argument
   gossip: "com.klst.gossip.RootFrame"       | AD-client: <nix> - dauault ist className = "org.compiere.apps.AMenu"
                        main:625:  startClass.newInstance();
                                             |
                                             | log.info("CodeBase=" + Adempiere.getCodeBase());	// Get JNLP CodeBase aka Java Web Start
                                             | ... splash ...
                                             | initSystem mit new ALogin(...
                                             | private VTreePanel ... treePanel = new VTreePanel (m_WindowNo, true, false)
wie org.jdesktop.swingx.demos.tree.XTreeDemo | (client) org.compiere.grid.tree.VTreePanel extends CPanel implements ActionListener
              bzw TreeTableDemo              |
                                             | AD_Tree_ID = per SQL: (default 10)
SELECT COALESCE(r.AD_Tree_Menu_ID, ci.AD_Tree_Menu_ID)
  FROM AD_ClientInfo ci 
  INNER JOIN AD_Role r ON (ci.AD_Client_ID=r.AD_Client_ID) 
 WHERE AD_Role_ID=? -- 102
                                             | treePanel.initTree(AD_Tree_ID);
                                             | MTree vTree = new MTree (Env.getCtx(), treeId, editable, allNodes=false, whereClause, trxName=null);

 */
	public RootFrame() {
		super(TITLE);
		LOG.info("Java Web Start/JNLP CodeBase=" + Adempiere.getCodeBase());		
		LOG.config(TITLE + ", Component#:"+getContentPane().getComponentCount());
		this.rootFrame = this;
		
		frames = new ArrayList<JFrame>(RootFrame.FRAMES_INITIAL_CAPACITY);
		frames.add(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initMenuBar();

		// button & msg controlPanel
		JPanel controlPanel = (JPanel)getContentPane().getComponent(0);
		
		menuPanel = new MenuPanel(this); // MenuPanel extends JXPanel
		loginPanel = new LoginPanel();
		hidableTabbedPane = new HidableTabbedPane("HidableTabbedPane/menu",menuPanel);
		controlPanel.add(hidableTabbedPane, BorderLayout.CENTER); //.PAGE_START); // aka NORTH
//		hidableTabbedPane.addTab("login", loginPanel);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	void login() {
		LOG.info(""+loginPanel);
		hidableTabbedPane.addTab("login", loginPanel);
		pack();
		loginPanel.setVisible(true);
	}
	
	void openNewFrame(int window_ID) {
		LOG.config("new frame aka Window with window_ID="+window_ID);
		Properties ctx = Env.getCtx();
		ctx.forEach((key,value) -> { // zum Test
			LOG.info("ctx key:"+key + " : " + value.toString());
		});
		
		GridWindow gridWindow = getGridWindow(window_ID);
		if(gridWindow==null) {
			LOG.warning("window mit AD_Window_ID="+window_ID+"nicht gefunden");
		} else {
			WindowFrame frame = makeWindow(window_ID, gridWindow);
			LOG.config("windowframe components#:"+frame.getComponentCount() + " WindowNo:"+frame.getWindowNo());
			GenericDataLoader task = frame.getTabs().get(0).getDataLoader(); // first Tab direkt laden, das nur testweise, also ohne Auswahl 
			// denn es gibt Window mit AuswahlSpalten, zB Business Partner
			frame.setLocationRelativeTo(null); // oben links würde es sonst angezeigt
			task.execute();
		}
		
	}

	private void initMenuBar() {
		// gemeinsame JMenuItem's z.B mFile."Quit" in Window
        
        mFile.addSeparator(); // -------------------------
		miBank = new JMenuItem("zeige Banken (Demo)", AIT.getImageIcon(AIT.PAYMENT, SMALL_ICON_SIZE));
		miBank.addActionListener(event -> {
			LOG.config("new frame Banken");
			openNewFrame(158);
		});
		mFile.add(miBank);

		miCountry = new JMenuItem("zeige Länder (Demo)", AIT.getImageIcon(AIT.ONLINE, SMALL_ICON_SIZE));
		miCountry.addActionListener(event -> {
			LOG.config("new frame Länder");
			openNewFrame(122);
		});
		mFile.add(miCountry);
		
		miDocument = new JMenuItem("zeige Belege (Demo)", AIT.getImageIcon(AIT.ARCHIVE, SMALL_ICON_SIZE));
		miDocument.addActionListener(event -> {
			LOG.config("new frame Belege");
			openNewFrame(135); // Document Type 
		});
		mFile.add(miDocument);
		
		// oder AD_Window_ID=140 Product
		// oder AD_Window_ID=143 Sales Order

        // Look & Feel : 
		JMenu mLuf = new JMenu(); // extends AbstractButton
		menuBar.add(mLuf);
		mLuf.setText("Look & Feel");
		mLuf.setMnemonic(KeyEvent.VK_L); // Alt+L
		mLuf.setActionCommand("LaF"); // in AbstractButton	    
        ButtonGroup group = new ButtonGroup();
        
        miSteelLaF = new JRadioButtonMenuItem(METAL+": Steel");
        miSteelLaF.setMnemonic(KeyEvent.VK_S);
        miSteelLaF.addActionListener(event -> {
        	updateLaF(crossPlatformLookAndFeelClassName, new DefaultMetalTheme());
        	miSteelLaF.setSelected(true);
        });
        group.add(miSteelLaF);
        mLuf.add(miSteelLaF);
        
        miOceanLaF = new JRadioButtonMenuItem(METAL+": Ocean");
        miOceanLaF.setSelected(true); // this is default
        miOceanLaF.setMnemonic(KeyEvent.VK_O);
        miOceanLaF.addActionListener(event -> {
        	updateLaF(crossPlatformLookAndFeelClassName, new OceanTheme());
        	miOceanLaF.setSelected(true);
        });
        group.add(miOceanLaF);
        mLuf.add(miOceanLaF);
        
        miPlastikLaF = new JRadioButtonMenuItem("Plastic: SkyBluer");
        miPlastikLaF.setMnemonic(KeyEvent.VK_P);
        miPlastikLaF.addActionListener(event -> {
        	updateLaF(PlasticLookAndFeel.class.getName(), new SkyBluer());
        	miPlastikLaF.setSelected(true);
        });
        group.add(miPlastikLaF);
        mLuf.add(miPlastikLaF);
       
        mLuf.addSeparator(); // ------------------------- 
        
        miCompiereLaF = new JRadioButtonMenuItem("Compiere: BlueMetal");
        miCompiereLaF.setMnemonic(KeyEvent.VK_A);
        miCompiereLaF.addActionListener(event -> {
        	updateLaF(crossPlatformLookAndFeelClassName, new CompiereTheme());
        	miCompiereLaF.setSelected(true);
        });
        group.add(miCompiereLaF);
        mLuf.add(miCompiereLaF);
      
        miCompiereIceLaF = new JRadioButtonMenuItem("Compiere: Ice");
        miCompiereIceLaF.setMnemonic(KeyEvent.VK_A);
        miCompiereIceLaF.addActionListener(event -> {
        	updateLaF(crossPlatformLookAndFeelClassName, new CompiereThemeBlueMetal());
        	miCompiereIceLaF.setSelected(true);
        });
        group.add(miCompiereIceLaF);
        mLuf.add(miCompiereIceLaF);
      
       mLuf.addSeparator(); // ------------------------- 

        miMotifLaF = new JRadioButtonMenuItem("Motif");
        miMotifLaF.setMnemonic(KeyEvent.VK_M);
        miMotifLaF.addActionListener(event -> {
        	updateLaF("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            miMotifLaF.setSelected(true);
        });
        group.add(miMotifLaF);
        mLuf.add(miMotifLaF);

        miWindowsLaF = new JRadioButtonMenuItem("Windows");
        miWindowsLaF.setMnemonic(KeyEvent.VK_W);
        miWindowsLaF.addActionListener(event -> {
        	miWindowsLaF.setSelected(true);
        	updateLaF("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        });
        group.add(miWindowsLaF);
        mLuf.add(miWindowsLaF);
        
        miGimpLaF = new JRadioButtonMenuItem("GTK / GIMP-Toolkit");
        miGimpLaF.setMnemonic(KeyEvent.VK_G);
        miGimpLaF.addActionListener(event -> {
        	miGimpLaF.setSelected(true);
        	updateLaF("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        });
        group.add(miGimpLaF);
        mLuf.add(miGimpLaF);
        
        mLuf.addSeparator(); // -------------------------
        
        miNimbusLaF = new JRadioButtonMenuItem("Nimbus");
        miNimbusLaF.setMnemonic(KeyEvent.VK_N);
        miNimbusLaF.addActionListener(event -> {
        	miNimbusLaF.setSelected(true);
        	updateLaF(NimbusLookAndFeel.class.getName());
        });
        group.add(miNimbusLaF);
        mLuf.add(miNimbusLaF);
	}

	// wie  VEditorFactory.getEditor(m_mTab, mField, false); // TODO
//	private static VEditor factoryGetEditor(GridTab gridTab, GridField mField, boolean b) {
/*
	public static final int String     = 10;
	public static final int Integer    = 11;
	public static final int Amount     = 12;
	public static final int ID         = 13;
	public static final int Text       = 14;
	public static final int Date       = 15;
	public static final int DateTime   = 16;
	public static final int List       = 17;
	public static final int Table      = 18;
	public static final int TableDir   = 19;
	public static final int YesNo      = 20;
	public static final int Location   = 21;
	public static final int Number     = 22;
	public static final int Binary     = 23;
	public static final int Time       = 24;
	public static final int Account    = 25;
	public static final int RowID      = 26;
	public static final int Color      = 27;
	public static final int Button	   = 28;
	public static final int Quantity   = 29;
	public static final int Search     = 30;
	public static final int Locator    = 31;
	public static final int Image      = 32;
	public static final int Assignment = 33;
	public static final int Memo       = 34;
	public static final int PAttribute = 35;
	public static final int TextLong   = 36;
	public static final int CostPrice  = 37;
	public static final int FilePath   = 38;
	public static final int FileName   = 39;
	public static final int URL        = 40;
	public static final int PrinterName= 42;
	public static final int Chart           = 53370;
	public static final int FilePathOrName  = 53670;

 */
//	private static VEditor factoryGetEditor(GridTab mTab, GridField mField, boolean tableEditor) {
//		if (mField == null)
//			return null;
//		
//		VEditor editor = null;
//		int displayType = mField.getDisplayType();
//		String columnName = mField.getColumnName();
//		boolean mandatory = mField.isMandatory(false);      //  no context check
//		boolean readOnly = mField.isReadOnly();
//		boolean updateable = mField.isUpdateable();
//		int WindowNo = mField.getWindowNo();
//
//		//  Not a Field
//		if (mField.isHeading())
//			return null;
//
//		//	String (clear/password)
//		if (displayType == DisplayType.String
//			|| displayType == DisplayType.PrinterName
//			|| (tableEditor && (displayType == DisplayType.Text || displayType == DisplayType.TextLong)) )
//		{
//			if (mField.isEncryptedField())
//			{	LOG.warning("TODO VPassword"); return null;
////				VPassword vs = new VPassword (columnName, mandatory, readOnly, updateable,
////					mField.getDisplayLength(), mField.getFieldLength(), mField.getVFormat());
////				vs.setName (columnName);
////				vs.setField (mField);
////				editor = vs;
//			}
//			else
//			{	LOG.warning("TODO VString"); return null;
////				VString vs = new VString (columnName, mandatory, readOnly, updateable,
////					mField.getDisplayLength(), mField.getFieldLength(), 
////					mField.getVFormat(), mField.getObscureType());
////				vs.setName (columnName);
////				vs.setField (mField);
////				if (mField.isAutocomplete()) {
////					ADempiereAutoCompleteDecorator.decorate(vs, mField.getEntries(), false);
////				}
////				editor = vs;
//			}
//		}
//
//		//	Lookup
//		else if (DisplayType.isLookup(displayType) || displayType == DisplayType.ID)
//		{
//			VLookup vl = new VLookup(columnName, mandatory, readOnly, updateable,
//				mField.getLookup());
//			vl.setName(columnName);
//			vl.setField (mField);
//			editor = vl;
//		}
//
//		//	YesNo
//		else if (displayType == DisplayType.YesNo)
//		{
//			VCheckBox vc = new VCheckBox(columnName, mandatory, readOnly, updateable,
//				mField.getHeader(), mField.getDescription(), tableEditor);
//			vc.setName(columnName);
//			vc.setField (mField);
//			editor = vc;
//		}
//
//		//	Date
//		else if (DisplayType.isDate(displayType))
//		{
//			if (displayType == DisplayType.DateTime)
//				readOnly = true;
//			VDate vd = new VDate(columnName, mandatory, readOnly, updateable,
//				displayType, mField.getHeader());
//			vd.setName(columnName);
//			vd.setField (mField);
//			editor = vd;
//		}
//
//		else
//			LOG.log(Level.WARNING, columnName + " - Unknown Type: " + displayType);
//		
//		return editor;
//	}
//--------------------------- */	

	/*
	 * initialisiert auch MetalLookAndFeel theme,
	 */
	private void updateLaF(String plaf, MetalTheme theme) {
        MetalLookAndFeel.setCurrentTheme(theme);
        updateLaF(plaf);
	}
	
	private void updateLaF(String plaf) {
		try {
			UIManager.setLookAndFeel(plaf);
//			msg.setText(plaf);
		} catch (UnsupportedLookAndFeelException e) {
			LOG.warning(e.toString());
		} catch (ClassNotFoundException e) {
			LOG.warning(e.toString());
//			msg.setText(e.toString());
		} catch (InstantiationException e) {
			LOG.warning(e.toString());
		} catch (IllegalAccessException e) {
			LOG.warning(e.toString());
		}
		SwingUtilities.updateComponentTreeUI(this);
		this.pack();
		// propagieren:
		Iterator<JFrame> i = frames.iterator();
		while(i.hasNext()) {
			JFrame f = i.next();
			SwingUtilities.updateComponentTreeUI(f);
			f.revalidate();
		}
	}

}
