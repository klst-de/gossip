package com.klst.gossip;

import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Properties;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.compiere.model.MTab;
import org.compiere.util.Env;
import org.compiere.util.Trx;

public class Tab extends JPanel implements ComponentListener {

	private static final long serialVersionUID = -2597982525624660612L;
	
	private static final Logger LOG = Logger.getLogger(Tab.class.getName());

	// AD Komponenten:
	// MTab mit den Metadaten
	// swing Komponenten:
	// JPanel parentTab
	// HidableTabbedPane parentContainer
	// window / bzw JFrame
	// rootFrame

	private int tab_ID;
	protected MTab mTab;
	private Properties ctx;
	private String trxName;

	// ui
	protected JProgressBar progressBar;
	
	// ctor
	/* super ctors
	 * 	   public JPanel(LayoutManager layout, boolean isDoubleBuffered) 
	 *     public JPanel(LayoutManager layout) 
	 *     public JPanel(boolean isDoubleBuffered) 
	 */
	protected Tab(int tab_ID) {
		super(new BorderLayout());
		LOG.config("tab_ID "+tab_ID);
		this.tab_ID = tab_ID;
		this.ctx = Env.getCtx();
		this.trxName = Trx.createTrxName(Tab.class.getName());
		this.mTab = new MTab(ctx, this.tab_ID, trxName);
		this.addComponentListener(this);
		
		progressBar = new JProgressBar(0, 100);
		progressBar.setStringPainted(true);
		this.add(progressBar, BorderLayout.PAGE_END);
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
