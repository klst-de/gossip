package de.javabuch;
/* Beispiel3501.java - Kapitel 35 - Swing: Grundlagen
Quelle:
Handbuch der Java-Programmierung, 3. Auflage, Addison Wesley, Version 3.0.1
© 1998-2003 Guido Krüger, http://www.javabuch.de
 */

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.basic.BasicIconFactory;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

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

	Icon iconLandschaft = new ImageIcon(BasicIconFactory.class.getResource(imageDelayed));

	public Beispiel3501() {
		super("Beispiel3501 - Kapitel 35 - Swing: Grundlagen, Handbuch der Java-Programmierung");
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
		
		// Monatsliste
		JList<String> list = new JList<String>(MONTHS);
		list.setToolTipText("W�hlen Sie ihren Geburtsmonat aus");
		getContentPane().add(new JScrollPane(list), BorderLayout.CENTER);
		
		// Panel mit den Buttons
		JPanel buttonPanel = new JPanel();
		
		JButton button1 = new JButton(METAL);
		String crossPlatformLookAndFeelClassName = UIManager.getCrossPlatformLookAndFeelClassName();
		// Set cross-platform Java L&F (also called "Metal")
		button1.addActionListener(event -> updateLaF(crossPlatformLookAndFeelClassName, new DefaultMetalTheme()));
		button1.setToolTipText("Metal-Look-and-Feel aktivieren");
		buttonPanel.add(button1);
		
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
		addWindowListener(new WindowClosingAdapter(true));
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

	public static void main(String[] args) {
		Beispiel3501 frame = new Beispiel3501();
		frame.setLocation(100, 100);
		frame.pack();
		frame.setVisible(true);
	}
}