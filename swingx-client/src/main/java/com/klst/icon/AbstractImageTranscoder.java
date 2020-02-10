package com.klst.icon;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;

import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.TranscodingHints;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.util.SVGConstants;

/* Notice 
 * that there are two @Singleton annotations, 
 * one in javax.inject and the other in the javax.ebj package. 
 * I'm referring to them by their fully-qualified names to avoid confusion.
 * @see https://stackoverflow.com/questions/26832051/singleton-vs-applicationscope
 * @see https://github.com/javax-inject/javax-inject
 */
@javax.inject.Singleton
public abstract class AbstractImageTranscoder extends ImageTranscoder {

//	private static final String ICON_PATH = "../com.klst.client/bin/icons/feather/"; 
	private static final String ICON_SUFFIX = ".svg"; 
	
	public static AbstractImageTranscoder SINGLETON = null;

	public static AbstractImageTranscoder getInstance() {
		return SINGLETON;
	}
	
	static {
//		new AppIcon8ImageTranscoder(); // default
		new FeatherImageTranscoder();
//		new AppGlyphImageTranscoder();
	}
	
	public AppIcon 
	RLI, YLI, GLI, // traffic light icons
	FOLDER,
	TABLE_COLUMN_CONTROL, // der kleine Controler ColumnControlButton rechts bei den Tabellenüberschriften, durch org.jdesktop.swingx.icon.ColumnControlIcon implementiert
	SERVER, DATABASE,
	MENU, MENU_WINDOW, 
	MENU_X, // UserForm
	MENU_V, // SetVariable
	MENU_C, // UserChoice
	MENU_D, // DocumentAction
	MENU_Z, // WaitSleep
	MENU_TD, // ???
	MAIL,
	TASK,
	PROCESS,
	INFO,
	WARN, // warnschilder sind dreiecke! ("alert-circle"),
	ERROR,	// Stopp-Schild
	QUESTION,	// ?-Zeichen
	CANCEL, 
	OK,
	PRINTSCREEN, SCREENSHOT, REPORT, PRINT, PREVIEW, EXPORT, END, LOGOUT, EXIT, // Menu File
	NEW, SAVE, COPY, DELETE, DELETE_SEL, IGNORE, REFRESH, FIND, FIND_TOGGLED, LOCK, LOCK_TOGGLED, // Menu Edit
	PRODUCT, BPARTNER, ACCOUNT, SCHEDULE, MRP, CRP, ORDER, INVOICE, SHIPMENT, PAYMENT
	, CASHLINE, ASSIGNMENT, ASSET, ATTACH, ATTACH_TOGGLED, CHAT, CHAT_TOGGLED, HISTORY, HISTORY_TOGGLED, GRID, GRID_TOGGLED, // Menu View / Info
	FIRST, PREVIOUS, NEXT, LAST, PARENT, DETAIL, ZOOM, REQUEST, ARCHIVE, HOME, // Menu Go
	CALCULATOR, CALENDAR, EDITOR, SCRIPT, WINSIZE, WORKFLOW, PREFERENCE, // Menu Tools
	HELP, ONLINE, SENDMAIL, ABOUT; // Menu Help
	
	private BufferedImage image = null;	
	private URI iconPath = null;
	
	// ctor
//	private AbstractImageTranscoder() {
//		this(ICON_PATH);
//	}
	protected AbstractImageTranscoder(String iconPath) {
		try {
//			String baseName = iconPath;
//			IconSets.class.getResource(baseName);
//			IconSets.class.getPackage().
//			int index = baseName.lastIndexOf('/');
//            if(index != -1) {
//            	iconPath = IconSets.class.getPackage().getName()+"/"+baseName.substring(0, index+1);
//            }
//			System.out.println("GUT !!!!!!!!!!!!! "+iconPath);
			this.iconPath = new URI(iconPath);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		SINGLETON = this;
	}
	
	// benutzung in AppAction::AbstractButton : Feather.getImageIcon(iconName, 16); FeatherIcon iconName
	// => AbstractImageTranscoder..getImageIcon(iconName, 16);
	public abstract ImageIcon getImageIcon(AppIcon icon, int size);

	@Override
	public BufferedImage createImage(int width, int height) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        return image;
	}

	@Override
	public void writeImage(BufferedImage arg0, TranscoderOutput arg1) throws TranscoderException {
		// TODO Auto-generated method stub
		throw new TranscoderException("not implemented");
	}

	protected ImageIcon getImageIcon(String iconName, int size) throws IOException {
		StringBuilder url = new StringBuilder(iconPath.toString()).append(iconName).append(ICON_SUFFIX); 
		Float width = new Float(size), height = new Float(size); // quadratisch
		Feather transcoder = new Feather();

		TranscodingHints hints = new TranscodingHints();
		hints.put(ImageTranscoder.KEY_WIDTH, width); // e.g. width=new Float(300)
		hints.put(ImageTranscoder.KEY_HEIGHT, height);
		hints.put(ImageTranscoder.KEY_DOM_IMPLEMENTATION, SVGDOMImplementation.getDOMImplementation());
		hints.put(ImageTranscoder.KEY_DOCUMENT_ELEMENT_NAMESPACE_URI, SVGConstants.SVG_NAMESPACE_URI);
		hints.put(ImageTranscoder.KEY_DOCUMENT_ELEMENT, SVGConstants.SVG_SVG_TAG);
		hints.put(ImageTranscoder.KEY_XML_PARSER_VALIDATING, false);

		transcoder.setTranscodingHints(hints);
		try {
			transcoder.transcode(new TranscoderInput(url.toString()), null); 
		} catch (TranscoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IOException("Error parsing SVG file " + e);
		}
		BufferedImage image = transcoder.getImage();
		ImageIcon imageIcon = new ImageIcon(image); // Creates an ImageIcon from an image object.
		return imageIcon;
	}

}
