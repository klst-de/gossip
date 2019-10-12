package de.javabuch;
/* Beispiel3501.java - Kapitel 35 - Swing: Grundlagen
Quelle:
Handbuch der Java-Programmierung, 3. Auflage, Addison Wesley, Version 3.0.1
© 1998-2003 Guido Krüger, http://www.javabuch.de
 */

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.basic.BasicIconFactory;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;
import javax.swing.plaf.metal.OceanTheme;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import org.jdesktop.swingx.JXLoginPane;
import org.jdesktop.swingx.JXLoginPane.Status;
import org.jdesktop.swingx.auth.JDBCLoginService;

import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.SkyBluer;
import com.klst.icon.AbstractImageTranscoder;

import gov.nasa.arc.mct.gui.impl.HidableTabbedPane;

public class Beispiel3501 extends JFrame {
//	implements ActionListener ist mit Lambda nicht mehr notwendig!
	
	private static final long serialVersionUID = -4238082996703714212L;
	
	private static final String[] MONTHS = { 
			"Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember" 
			};

	private static final String METAL = "Metal";
	private static final String NIMBUS = "Nimbus";	// NimbusLookAndFeel
	private static final String SYNTH = "Synth";	// SynthLookAndFeel does not directly provide a look, all painting is delegated.
	                                                // MultiLookAndFeel
	JLabel msg = new JLabel();

	private static final String JavaCup16 = "icons/JavaCup16.png";
	private static final String imageDelayed = "icons/image-delayed.png"; // Landschaft
	private static final String imageFailed = "icons/image-failed.png";
	private static final String imageJTabbedPane = "JTabbedPane.gif";

	WindowClosingAdapter windowClosingAdapter = new WindowClosingAdapter(true);
	
	Icon iconJavaCup = new ImageIcon(BasicIconFactory.class.getResource(JavaCup16));
	Icon iconLandschaft = new ImageIcon(BasicIconFactory.class.getResource(imageDelayed));
	HidableTabbedPane hidableTabbedPane;
	JButton buttonToggle;
	JButton buttonTabPlace;
	
	// dafür gossip client notwendig:
	AbstractImageTranscoder AIT = AbstractImageTranscoder.getInstance();
	
	// File Menu Items : 
	// wo ist der Reiter bei hidableTabbedPane, der Reiter ist nur sichtbar, wenn mindestens zwei Tabs vorhanden sind
	// bei einem Tab sieht HidableTabbedPane wie ein JPanel aus
	JMenuItem miTabTop;
	JMenuItem miTabLeft;
	JMenuItem miTabBottom;
	JMenuItem miTabRight;
	
	String crossPlatformLookAndFeelClassName = UIManager.getCrossPlatformLookAndFeelClassName();
	
	// Look & Feel Menu Items
	// crossPlatform: 
	JRadioButtonMenuItem miNimbusLaF;
	JRadioButtonMenuItem miSteelLaF;
	JRadioButtonMenuItem miOceanLaF; // the Java default Metal theme
	JRadioButtonMenuItem miAquaLaF;
	JRadioButtonMenuItem miPlastikLaF; // jgoodies
	// Platform dependent:
	JRadioButtonMenuItem miMotifLaF;
	JRadioButtonMenuItem miWindowsLaF;
	JRadioButtonMenuItem miGimpLaF; // GTK GIMP-Toolkit

	private JMenuBar menuBar = new JMenuBar();
/* aus: /swingx-demos/swingx-demos-swingxset/src/main/java/org/jdesktop/swingxset/SwingXSet.java
    protected JMenuBar createMenuBar() {
    
        JMenuBar menubar = new JMenuBar();
        menubar.setName("menubar");
        
        // File menu
        JMenu fileMenu = new JMenu();
        fileMenu.setName("file");
        menubar.add(fileMenu);
        
        // File -> Quit
        if (!runningOnMac()) {
            JMenuItem quitItem = new JMenuItem();
            quitItem.setName("quit");
            quitItem.setAction(getAction("quit"));
            fileMenu.add(quitItem);
        }
	...
 */
//    private javax.swing.Action getAction(String actionName) {
//        return getContext().getActionMap().get(actionName);
//    }
	private void initMenuBar() {
		// File : JMenuItem's "Quit",  b,  c, ...
		JMenu mFile = new JMenu();
		menuBar.add(mFile);
		mFile.setName("file");
		mFile.setText("File");
        if(!runningOnMac()) {
            JMenuItem quitItem = new JMenuItem("Quit"); // JMenuItem(String text, int mnemonic) | JMenuItem(String text, Icon icon)
            quitItem.setName("quit");
            quitItem.setActionCommand("quit");
            quitItem.addActionListener(event -> {
            	System.exit(0);
            });
            mFile.add(quitItem);
        }
        
        mFile.addSeparator(); // -------------------------
        
        miTabTop = new JMenuItem("Tab on Top", AIT.getImageIcon(AIT.PREVIOUS, 24));
        miTabTop.addActionListener(event -> {
			hidableTabbedPane.setTabPlacement(JTabbedPane.TOP);
		});
        mFile.add(miTabTop);
        
        miTabLeft = new JMenuItem("Tab on Left", AIT.getImageIcon(AIT.PARENT, 24));
        miTabLeft.addActionListener(event -> {
			hidableTabbedPane.setTabPlacement(JTabbedPane.LEFT);
		});
        mFile.add(miTabLeft);
            
        miTabBottom = new JMenuItem("Tab on Bottom", AIT.getImageIcon(AIT.NEXT, 24));
        miTabBottom.addActionListener(event -> {
			hidableTabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
		});
        mFile.add(miTabBottom);
        
        miTabRight = new JMenuItem("Tab on Right", AIT.getImageIcon(AIT.DETAIL, 24));
        miTabRight.addActionListener(event -> {
			hidableTabbedPane.setTabPlacement(JTabbedPane.RIGHT);
		});
        mFile.add(miTabRight);
        
        // Look & Feel : 
		JMenu mLuf = new JMenu(); // extends AbstractButton
		menuBar.add(mLuf);
		mLuf.setText("Look & Feel");
		mLuf.setMnemonic(KeyEvent.VK_L); // Alt+L
		mLuf.setActionCommand("LaF"); // in AbstractButton	    
        ButtonGroup group = new ButtonGroup();
        
        miSteelLaF = new JRadioButtonMenuItem(METAL+": Steel");
        System.out.println("current L&F is "+UIManager.getLookAndFeel().getName());
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
        
        miAquaLaF = new JRadioButtonMenuItem(METAL+": Aqua");
        miAquaLaF.setMnemonic(KeyEvent.VK_A);
        miAquaLaF.addActionListener(event -> {
        	updateLaF(crossPlatformLookAndFeelClassName, new AquaTheme());
        	miAquaLaF.setSelected(true);
        });
        group.add(miAquaLaF);
        mLuf.add(miAquaLaF);
        
        miPlastikLaF = new JRadioButtonMenuItem("Plastic: SkyBluer");
        miPlastikLaF.setMnemonic(KeyEvent.VK_P);
        miPlastikLaF.addActionListener(event -> {
        	updateLaF(PlasticLookAndFeel.class.getName(), new SkyBluer());
        	miPlastikLaF.setSelected(true);
        });
        group.add(miPlastikLaF);
        mLuf.add(miPlastikLaF);
       
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
	
	public Beispiel3501() {
		super("Beispiel3501 - Kapitel 35 - Swing: Grundlagen, Handbuch der Java-Programmierung");
		
		this.setJMenuBar(menuBar);
		initMenuBar();
		
		// Panel zur Namenseingabe
		JPanel namePanel = new JPanel();
		// statt ImageIcon("triblue.gif") :
		JLabel label = new JLabel("Name:", iconLandschaft, SwingConstants.LEFT);		
		namePanel.add(label);

		JTextField tf = new JTextField(30);
		tf.setToolTipText("Geben Sie ihren Namen ein");
		namePanel.add(tf);
		namePanel.setBorder(BorderFactory.createEtchedBorder());
		getContentPane().add(namePanel, BorderLayout.NORTH);
		
//		buttonTabPlace = new JButton("tabPlace",new ImageIcon(BasicIconFactory.class.getResource(imageFailed)));
		// dafür gossip client notwendig:
		//AbstractImageTranscoder AIT = AbstractImageTranscoder.getInstance();
//		AIT.getImageIcon(AIT.FIRST, 24); // LARGE_ICON_SIZE
		buttonTabPlace = new JButton("tabPlace",AIT.getImageIcon(AIT.IGNORE, 24));
		
		buttonTabPlace.addActionListener(event -> {
			if(hidableTabbedPane.getTabPlacement()==JTabbedPane.TOP) {
				hidableTabbedPane.setTabPlacement(JTabbedPane.LEFT);
			} else {
				hidableTabbedPane.setTabPlacement(JTabbedPane.TOP);
			}
		});
		buttonToggle = new JButton("toggle Tabs", new ImageIcon(getClass().getResource(imageJTabbedPane)));
		buttonToggle.setText(null); // button ohne text: icon only
		buttonToggle.setMargin(new  Insets(0, 0, 0, 0)); // no insets: Insets(int top, int left, int bottom, int right)
//		buttonToggle.setMargin(new  Insets(-5, -5, -5, -5)); // no insets: Insets(int top, int left, int bottom, int right)
		buttonToggle.setBorderPainted(false);
		JPanel hiddenPanel = new JPanel();
		hiddenPanel.setLayout(new BorderLayout());
		hiddenPanel.add(buttonToggle, BorderLayout.NORTH);
		namePanel.add(hiddenPanel);
		buttonToggle.setToolTipText("ein- /ausschalten HidableTabbedPane");
		buttonToggle.addActionListener(event -> {
			if(hidableTabbedPane.isTabsShown()) {
				hidableTabbedPane.removeTabAt(1);
			} else {
				hidableTabbedPane.addTab("sichtbar gemacht", buttonTabPlace);
			}
			this.pack();
		});
		hidableTabbedPane = new HidableTabbedPane("HidableTabbedPane",namePanel);
		getContentPane().add(hidableTabbedPane, BorderLayout.NORTH);
		
		// Monatsliste
		JList<String> list = new JList<String>(MONTHS);
		list.setToolTipText("W�hlen Sie ihren Geburtsmonat aus");
		getContentPane().add(new JScrollPane(list), BorderLayout.CENTER);
		
		// Panel mit den Buttons
		JPanel buttonPanel = new JPanel();
		
		// Set cross-platform Java L&F (also called "Metal")
		// statt Button eine JComboBox für METAL themes
//		JButton button1 = new JButton(METAL);
//		button1.addActionListener(event -> updateLaF(crossPlatformLookAndFeelClassName, new DefaultMetalTheme()));
//		button1.setToolTipText("Metal-Look-and-Feel aktivieren");
//		buttonPanel.add(button1);

/* die L&F Umschalter jetzt im Menu

		lafCB = this.createLaFComboBox();
		buttonPanel.add(lafCB);
		lafCB.addActionListener(event -> {
			System.out.println(event.toString());
			System.out.println(event.getSource()); // == ??? buttonSynth.getText()
			System.out.println(lafCB.getSelectedItem());
			if(lafCB.getSelectedIndex()==0) {
				updateLaF(crossPlatformLookAndFeelClassName, new DefaultMetalTheme());
			} else if(lafCB.getSelectedIndex()==1) {
				updateLaF(crossPlatformLookAndFeelClassName, new AquaTheme());
			} else if(lafCB.getSelectedIndex()==2) {
				updateLaF(PlasticLookAndFeel.class.getName(), new SkyBluer());
			}
		});
		
		// Platform spezifische L&F
		JButton button2 = new JButton("Motif");
		button2.addActionListener(event -> updateLaF("com.sun.java.swing.plaf.motif.MotifLookAndFeel"));
		button2.setToolTipText("Motif-Look-and-Feel aktivieren");
		buttonPanel.add(button2);
		
		JButton button3 = new JButton("Windows");
		button3.addActionListener(event -> updateLaF("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"));
		button3.setToolTipText("Windows-Look-and-Feel aktivieren");
		buttonPanel.add(button3);
		
		JButton button4 = new JButton("GTK");
		button4.addActionListener(event -> updateLaF("com.sun.java.swing.plaf.gtk.GTKLookAndFeel"));
		button4.setToolTipText("GTK-Look-and-Feel aktivieren");
		buttonPanel.add(button4);

		// @see https://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/_nimbusDefaults.html
		JButton btnNimbus = new JButton(NIMBUS);
		btnNimbus.addActionListener(event -> updateLaF(NimbusLookAndFeel.class.getName()));
		btnNimbus.setToolTipText("Nimbus-Look-and-Feel aktivieren");
		buttonPanel.add(btnNimbus);
 */
		createLoginPaneDemo(buttonPanel);

		JButton buttonSynth = new JButton(SYNTH);
/* klassisch, mit ActionListener:
 		button5.addActionListener(this);
// lambda: */
		buttonSynth.addActionListener(event -> {
			System.out.println(event.toString());
			System.out.println(event.getActionCommand()); // == buttonSynth.getText()
			updateLaF("javax.swing.plaf.synth.SynthLookAndFeel");
		});
		buttonSynth.setToolTipText("Synth-Look-and-Feel aktivieren");
		buttonPanel.add(buttonSynth);

		// button & msg controlPanel
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BorderLayout());
		controlPanel.add(buttonPanel, BorderLayout.CENTER);
		controlPanel.add(msg, BorderLayout.SOUTH);
		getContentPane().add(controlPanel, BorderLayout.SOUTH);
		
		// Windows-Listener
/* public interface WindowListener extends EventListener hat mehrere Methoden, daher geht es nicht mit Lambda, so etwa:

		this.addWindowListener( event -> {
				event.getWindow().setVisible(false);
				event.getWindow().dispose();
				System.exit(0);
		});


https://stackoverflow.com/questions/30259812/can-we-use-the-lambda-expression-for-windowlistener-if-yes-how-if-no-why-can

A lambda expression can substitute a functional interface (i.e. an interface with a single non default method). 
Therefore WindowAdapter, which has multiple methods (windowActivated(WindowEvent e), 
windowClosed(WindowEvent e), windowClosing(WindowEvent e), ...), can't be substituted by a lambda expression.

 */
		addWindowListener(windowClosingAdapter);
	}

/*
         final JXLoginPane panel = new JXLoginPane(new LoginService() {
                      public boolean authenticate(String name, char[] password,
                                      String server) throws Exception {
                              // perform authentication and return true on success.
                              return false;
                      }});
      final JFrame frame = JXLoginPane.showLoginFrame(panel);

 */
    private JButton loginLauncher;
	private DemoLoginService loginService;
	private JDBCLoginService jdbcLoginService;
	private DemoLoginPane loginPane;
	private void createLoginPaneDemo(Container container) {
	    loginService = new DemoLoginService();
	    String driver = "org.postgresql.Driver";
	    String url = "jdbc:postgresql://localhost:5432/miad001";
	    jdbcLoginService = new JDBCLoginService(driver, url); // JDBCLoginService(String driver, String url) | JDBCLoginService(String driver, String url, Properties props)
	    loginPane = new DemoLoginPane(jdbcLoginService);
        loginLauncher = new JButton("Login");
        loginLauncher.setName("launcher");
//        getContentPane().add(loginLauncher, BorderLayout.EAST);
        container.add(loginLauncher);
        bind();
	}
    private void bind() {
        loginLauncher.addActionListener(event -> {
//        		new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                JXLoginPane.showLoginDialog(LoginPaneDemo.this, loginPane);
//            }
        	Status status = JXLoginPane.showLoginDialog(this, loginPane);
        	System.out.println("status:"+status.name());
        });
//        Bindings.createAutoBinding(READ,
//                allowLogin, BeanProperty.create("selected"),
//                service, BeanProperty.create("validLogin")).bind();
        // LoginListener hat mehrere methoden, daher nicht mit Lambda
//        loginService.addLoginListener(event -> {
//        	
//        });
        jdbcLoginService.addLoginListener(loginPane);
    }

	// A component that combines a button or editable field and a drop-down list. 
	// Type Parameters:<E> the type of the elements of this combo box
	JComboBox<String> lafCB;
	
	// hairCB = (JComboBox) comboBoxPanel.add(createHairComboBox());
    private JComboBox<String> createLaFComboBox() {
        JComboBox<String> cb = new JComboBox<String>();
        //fillComboBox(cb);
//        cb.addItem(getString("ComboBoxDemo.brent")); // resources/swingset.properties: ComboBoxDemo.brent=Brent
//        cb.addItem(getString("ComboBoxDemo.georges"));
//        cb.addItem(getString("ComboBoxDemo.hans"));
//        cb.addItem(getString("ComboBoxDemo.howard"));
        MetalTheme metalTheme = new DefaultMetalTheme();
        cb.addItem(METAL+" : "+metalTheme.getName());
        MetalTheme aquaTheme = new AquaTheme();
        cb.addItem(METAL+" : "+aquaTheme.getName());
        
        MetalTheme plastikTheme = new SkyBluer();
        //PlasticTheme pt = new SkyBluer();
        cb.addItem("Plastic"+" : "+plastikTheme.getName());
        return cb;
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
			msg.setText(plaf);
		} catch (UnsupportedLookAndFeelException e) {
			System.err.println(e.toString());
		} catch (ClassNotFoundException e) {
			System.err.println(e.toString());
			msg.setText(e.toString());
		} catch (InstantiationException e) {
			System.err.println(e.toString());
		} catch (IllegalAccessException e) {
			System.err.println(e.toString());
		}
		SwingUtilities.updateComponentTreeUI(this);
		this.pack();
	}

    public static boolean runningOnMac() {
        return System.getProperty("os.name").equals("Mac OS X");
    } 

	public static void main(String[] args) {
		Beispiel3501 frame = new Beispiel3501();
		frame.setLocation(100, 100);
		frame.pack();
		frame.setVisible(true);
	}
}