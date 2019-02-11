package com.klst.gossip;

import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker.StateValue;
import javax.swing.table.JTableHeader;

import org.compiere.model.GridTab;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTableHeader;

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

	private GridTab gridTab;
	GenericDataLoader loader;
//	private int tab_ID;
////	protected MTab mTab;
//	private Properties ctx;
//	private String trxName;

	// ui
//	protected JProgressBar progressBar;
	JXTable jXTable = createXTable();
	
	// ctor
	/* super ctors
	 * 	   public JPanel(LayoutManager layout, boolean isDoubleBuffered) 
	 *     public JPanel(LayoutManager layout) 
	 *     public JPanel(boolean isDoubleBuffered) 
	 */
	public Tab(int tab_ID) {
		super(new BorderLayout());
		LOG.warning("deprecated tab_ID "+tab_ID);
//		this.tab_ID = tab_ID;
//		this.ctx = Env.getCtx();
//		this.trxName = Trx.createTrxName(Tab.class.getName());
////		this.mTab = new MTab(ctx, this.tab_ID, trxName); // TODO blocker!!!
//		this.addComponentListener(this);
//		
//		progressBar = new JProgressBar(0, 100);
//		progressBar.setStringPainted(true);
//		this.add(progressBar, BorderLayout.PAGE_END);
	}
	public Tab(GridTab gridTab) {
		super(new BorderLayout());
		LOG.config("gridTab "+gridTab);
		this.gridTab = gridTab;
		this.addComponentListener(this);
		
		this.jXTable = createXTable();
		
//		progressBar = new JProgressBar(0, 100);
//		progressBar.setForeground(Color.PINK);
//		progressBar.setStringPainted(true);
//		this.add(progressBar, BorderLayout.PAGE_END);
	}

	public void setLoadState(StateValue state) {
		LOG.config("StateValue:"+state);
	}

	// aus org.jdesktop.swingx.demos.table.XTableDemo
	protected JXTable createXTable() {
		JXTable table = new JXTable() {

			private static final long serialVersionUID = -2974517519415177299L;

			@Override
			protected JTableHeader createDefaultTableHeader() {
				return new JXTableHeader(columnModel) {
					private static final long serialVersionUID = -4124370542563896297L;

					@Override
					public void updateUI() {
						super.updateUI();
						// need to do in updateUI to survive toggling of LAF
						if (getDefaultRenderer() instanceof JLabel) {
							((JLabel) getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

						}
					}

				};
			}

		};
		return table;
	}

	// wg. ComponentListener
	@Override
	public void componentResized(ComponentEvent e) {
		LOG.warning(""+e.getComponent());
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		LOG.warning(""+e.getComponent());
	}

	@Override
	public void componentShown(ComponentEvent e) {
		LOG.warning(""+e.getComponent());
		if(e.getComponent() instanceof Tab) {
			Tab tab = (Tab)e.getComponent();
			LOG.config("ParentTab:"+gridTab.getParentTab());
			this.loader.execute();
		}
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		LOG.warning(""+e.getComponent());
	}

/*
	private void showXX(Tab parent) {  // TODO gescheiten Namen finden Component.show() ist deprecated
		GenericTableModel tableModel = new GenericTableModel(gridTab, gridTab.getWindowNo());
		JXTable jXTable = createXTable();
		JScrollPane scrollpane = new JScrollPane(jXTable); 
		Stacker stacker = new Stacker(scrollpane);
		jXTable.setName(gridTab.getName());
		
		jXTable.setColumnControlVisible(true);
        // replace grid lines with striping 
		jXTable.setShowGrid(false, false);
		jXTable.addHighlighter(HighlighterFactory.createSimpleStriping());
        // initialize preferred size for table's viewable area
		jXTable.setVisibleRowCount(10);

        CustomColumnFactory factory = new CustomColumnFactory();
//        OscarRendering.configureColumnFactory(factory, getClass()); // TODO
        // set the factory before setting the table model
//        bankTable.setColumnFactory(factory);
//        </snip>

        jXTable.setModel(tableModel);
 		GenericDataLoader task = new GenericDataLoader(tableModel);
        
		JLabel status = new JLabel(); // TODO 
        BindingGroup group = new BindingGroup();
        group.addBinding(Bindings.createAutoBinding(READ, task, 
        		BeanProperty.create("progress"),
                progressBar, BeanProperty.create("value")));
        group.addBinding(Bindings.createAutoBinding(READ, task, 
        		BeanProperty.create("state"),
        		this, BeanProperty.create("loadState"))); // call setLoadState 
        group.bind();


		JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		currentFrame.pack();
//		currentFrame.setLocationRelativeTo(null);; // oben links würde es sonst angezeigt
//		currentFrame.setVisible(true);
		
		validate();
		SwingUtilities.updateComponentTreeUI(this);
		
		task.execute();
	}
*/
}
