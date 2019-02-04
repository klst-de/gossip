 package com.klst.gossip.demo;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.logging.Logger;

import javax.swing.JPanel;

// extends Tab (generisch) extends JPanel
public class TabRegion extends JPanel implements ComponentListener {

	private static final long serialVersionUID = 6863202380722356842L;
	
	private static final Logger LOG = Logger.getLogger(TabRegion.class.getName());

	// AD Komponenten:
	// MTab mit den Metadaten
	// swing Komponenten:
	// JPanel parentTab
	// HidableTabbedPane parentContainer
	// window / bzw JFrame
	// rootFrame
	
	public TabRegion() {
		LOG.config("");
		this.addComponentListener(this);
	}
	
	// wg. ComponentListener
	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		LOG.warning("");
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		LOG.warning("");
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		LOG.warning("");
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		LOG.warning("");
	}
	
}
