 package com.klst.gossip.demo;

import java.util.logging.Logger;

import com.klst.gossip.Tab;

// extends Tab (generisch) extends JPanel
public class TabRegion extends Tab {

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
		super(136); // (AD_Tab - AD_Tab_ID=136)
		LOG.config("");
		this.addComponentListener(this);
	}
	
}
