package com.klst.gossip;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;
import javax.swing.plaf.metal.OceanTheme;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import org.compiere.plaf.CompiereTheme;
import org.compiere.plaf.CompiereThemeBlueMetal;

import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.SkyBluer;
import com.klst.client.LoginPanel;
import com.klst.gossip.demo.TabBank;

import gov.nasa.arc.mct.gui.impl.HidableTabbedPane;

public class RootFrame extends Window {  // Window extends JFrame
	
	private static final long serialVersionUID = -400920856924947621L;

	private static final Logger LOG = Logger.getLogger(RootFrame.class.getName());

	private static final String TITLE = "Gossip";
	private static final String METAL = "Metal";
	
	String crossPlatformLookAndFeelClassName = UIManager.getCrossPlatformLookAndFeelClassName();

	// frame mgt
	List<JFrame> frames;
	private static final int FRAMES_INITIAL_CAPACITY = 10;
	private Window makeFrame(int frameNumber, RootFrame rootFrame, int window_ID) {
		Window frame = new Window("Frame number " + frameNumber, rootFrame, window_ID);
		frame.setDefaultCloseOperation(frames.isEmpty() ? JFrame.EXIT_ON_CLOSE : JFrame.DISPOSE_ON_CLOSE);
		frames.add(frame);
		return frame;
	}
	private Window makeWindow(int window_ID) {
		int frameNumber = frames.size();
		return makeFrame(frameNumber, this, window_ID);
	}
	boolean remove(JFrame frame) {
		return frames.remove(frame);
	}
	// <<<

	JMenuItem miBank;
	JMenuItem miCountry;

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
	
	// Banken DEMO:
//	JPanel hiddenBankPanel;
	TabBank tabBank;
//	JPanel hiddenCountryPanel;
//	WindowCountry windowCountry;

	public RootFrame() {
		super(TITLE);
		LOG.config(TITLE + ", Component#:"+getContentPane().getComponentCount());
		this.rootFrame = this;
		
		frames = new ArrayList<JFrame>(RootFrame.FRAMES_INITIAL_CAPACITY);
		frames.add(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initMenuBar();

		// button & msg controlPanel
		JPanel controlPanel = (JPanel)getContentPane().getComponent(0);
		
		loginPanel = new LoginPanel();
		hidableTabbedPane = new HidableTabbedPane("HidableTabbedPane",loginPanel);
		controlPanel.add(hidableTabbedPane, BorderLayout.NORTH);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void initMenuBar() {
		//super.initMenuBar();
//		// File : JMenuItem's "Quit",  b,  c, ...
//		JMenu mFile = new JMenu();
//		menuBar.add(mFile);
//		mFile.setName("file");
//		mFile.setText("File");
//        if(!Env.isMac()) { 
//            JMenuItem quitItem = new JMenuItem("Quit"); // JMenuItem(String text, int mnemonic) | JMenuItem(String text, Icon icon)
//            quitItem.setName("quit");
//            quitItem.setActionCommand("quit");
//            quitItem.addActionListener(event -> {
//            	System.exit(0);
//            });
//            mFile.add(quitItem);
//        }
        
        mFile.addSeparator(); // -------------------------
        
//		hiddenPrimePanel = new JPanel();
//        miPrimeNumbers = new JMenuItem("Primzahlen (Demo)");
//        miPrimeNumbers.addActionListener(event -> {
//        	//hidableTabbedPane.getTabComponentAt(1); // liefert exception, wenn doch kein Demo da ist
//        	if( !hidableTabbedPane.isTabsShown() || (hidableTabbedPane.getTabComponentAt(1)!=hiddenPrimePanel && hidableTabbedPane.getTabCount()==2)) {
//        		hidableTabbedPane.addTab("Primzahlen", hiddenPrimePanel);
//        	} else {
//        		LOG.config("add tab Primzahlen");
//        	}
//        	msg.setText("start Primzahlen (Demo) in Tab");
//			this.pack();
//        	primeDemo(hiddenPrimePanel);
//        });
//		mFile.add(miPrimeNumbers);
		
//		hiddenBankPanel = new JPanel();
		miBank = new JMenuItem("zeige Banken (Demo)");
		miBank.addActionListener(event -> {
			LOG.config("new frame Banken");
			Window frame = makeWindow(158); // AD_Window_ID=158)
			LOG.config("windowframe components#:"+frame.getComponentCount() + " WindowNo:"+frame.getWindowNo());
			JRootPane rootPane = (JRootPane)frame.getComponent(0); // javax.swing.JRootPane cannot be cast to javax.swing.JPanel
			HidableTabbedPane tabPane = new HidableTabbedPane(); // ohne Komponenten
//			frame.setMenuBar(mb);
			frame.getContentPane().add(tabPane, BorderLayout.CENTER);
			//rootPane.setTabPane(tabPane);
			tabPane.addTab("", new JLabel("JLabel")); // TODO warum funktioniert es nur mit zwei Tabs? in dieser Reihenfolge???
			tabPane.addTab("bank stacker", tabBank);
			GenericDataLoader task = tabBank.showIn(rootPane, frame.getWindowNo());
			task.execute();
			
			frame.pack();
			frame.setLocationRelativeTo(null);; // oben links würde es sonst angezeigt
			frame.setVisible(true);
		});
		mFile.add(miBank);

		miCountry = new JMenuItem("zeige Länder (Demo)");
		miCountry.addActionListener(event -> {
			LOG.config("new frame Länder");
			Window frame = makeWindow(122); // AD_Window_ID=122;
			LOG.config("windowframe components#:"+frame.getComponentCount() + " WindowNo:"+frame.getWindowNo());
			GenericDataLoader task = frame.getDataLoader();
			task.execute();
//			JRootPane rootPane = (JRootPane)frame.getComponent(0); // javax.swing.JRootPane cannot be cast to javax.swing.JPanel
//			windowCountry.showIn(rootPane, frame); // .showIn(Container jPanel)
//			
//			frame.pack();
//			frame.setLocationRelativeTo(null);; // oben links würde es sonst angezeigt
//			frame.setVisible(true);
		});
		mFile.add(miCountry);

//		windowCountry = new WindowCountry(); // this);
		tabBank = new TabBank();
  
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
		// propagieren:
		Iterator<JFrame> i = frames.iterator();
		while(i.hasNext()) {
			JFrame f = i.next();
			SwingUtilities.updateComponentTreeUI(f);
			f.pack();
		}
	}

}
