package com.klst.icon;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.inject.Singleton;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.TranscodingHints;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.util.SVGConstants;

// @see https://xmlgraphics.apache.org/batik/

// @see https://stackoverflow.com/questions/2495501/swing-batik-create-an-imageicon-from-an-svg-file

@Singleton
public class Feather extends ImageTranscoder {

//	private static final String ICON_PATH = "../com.klst.client/bin/icons/feather/"; 
	private static final String ICON_PATH = "feather/"; // !!!!!!!!!!! edal was man hier schreibt!!!!!!!!!!!!!!!!
	private static final String ICON_SUFFIX = ".svg"; 
	
//	public 
	static ImageIcon getImageIcon(FeatherIcon icon, int size) {
		try {
			return getImageIcon(icon.getValue(), size);
		} catch (IOException e) {
			return null;
		}
	}
	private static ImageIcon getImageIcon(String iconName, int size) throws IOException {
		StringBuilder url = new StringBuilder(ICON_PATH).append(iconName).append(ICON_SUFFIX); 
		Float height = new Float(size);
		Feather transcoder = new Feather();

		TranscodingHints hints = new TranscodingHints();
		// hints.put(ImageTranscoder.KEY_WIDTH, width); // e.g. width=new Float(300)
		hints.put(ImageTranscoder.KEY_HEIGHT, height);
		hints.put(ImageTranscoder.KEY_DOM_IMPLEMENTATION, SVGDOMImplementation.getDOMImplementation());
		hints.put(ImageTranscoder.KEY_DOCUMENT_ELEMENT_NAMESPACE_URI, SVGConstants.SVG_NAMESPACE_URI);
		hints.put(ImageTranscoder.KEY_DOCUMENT_ELEMENT, SVGConstants.SVG_SVG_TAG);
		hints.put(ImageTranscoder.KEY_XML_PARSER_VALIDATING, false);

		// @@@@@@@@@ TEST TODO
//		hints.put(ImageTranscoder.KEY_BACKGROUND_COLOR, SVGConstants.SVG_BACKGROUND_ALPHA_VALUE);
		
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
	
	private BufferedImage image = null;
	
	@Override
	public BufferedImage createImage(int width, int height) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        return image;
	}

	@Override
	public void writeImage(BufferedImage arg0, TranscoderOutput arg1) throws TranscoderException {
		// TODO Auto-generated method stub
		
	}

	// BufferedImage extends java.awt.Image
    public BufferedImage getImage() {
        return image;
    }
    
	/*
	 * The call looks something like this:
	 */
//	public static void main(String[] args) {
//		Float width = new Float(16);
//		Float height = new Float(24);
//		String url = "src/main/resources/activity.svg";
//		Feather transcoder = new Feather();
//
//		TranscodingHints hints = new TranscodingHints();
//		// hints.put(ImageTranscoder.KEY_WIDTH, width); // e.g. width=new Float(300)
//		hints.put(ImageTranscoder.KEY_HEIGHT, height);// e.g. height=new Float(75)
//		hints.put(ImageTranscoder.KEY_DOM_IMPLEMENTATION, SVGDOMImplementation.getDOMImplementation());
//		hints.put(ImageTranscoder.KEY_DOCUMENT_ELEMENT_NAMESPACE_URI, SVGConstants.SVG_NAMESPACE_URI);
//		hints.put(ImageTranscoder.KEY_DOCUMENT_ELEMENT, SVGConstants.SVG_SVG_TAG);
//		hints.put(ImageTranscoder.KEY_XML_PARSER_VALIDATING, false);
//
//		transcoder.setTranscodingHints(hints);
//		try {
//			transcoder.transcode(new TranscoderInput(url), null); 
//		} catch (TranscoderException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		BufferedImage image = transcoder.getImage();
//		ImageIcon imageIcon = new ImageIcon(image); // Creates an ImageIcon from an image object.
//		// return imageIcon;
//		// JLabel label1 = new JLabel("Image and Text", imageIcon, JLabel.CENTER);
//	}
}
