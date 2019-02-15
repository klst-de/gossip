package com.klst.gossip;

import static org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ;

import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker.StateValue;
import javax.swing.table.JTableHeader;

import org.compiere.model.GridTab;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTableHeader;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import gov.nasa.arc.mct.gui.impl.HidableTabbedPane;

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

	private WindowFrame frame;
	private GridTab gridTab;
	private GenericTableModel tableModel;
	private GenericDataLoader loader;

	// ui
	JXTable jXTable = createXTable(); // JXTable extends JTable implements TableColumnModelExtListener
	
	// ctor
	/* super ctors
	 * 	   public JPanel(LayoutManager layout, boolean isDoubleBuffered) 
	 *     public JPanel(LayoutManager layout) 
	 *     public JPanel(boolean isDoubleBuffered) 
	 */
	public Tab(GridTab gridTab, WindowFrame frame) {
		super(new BorderLayout());
		LOG.config("gridTab "+gridTab + ", WindowFrame frame:"+frame);
		this.frame = frame;
		this.gridTab = gridTab;
		this.addComponentListener(this);
		
		// in GridTab gibt es ein GridTable m_mTable // GridTable extends AbstractTableModel
		// wir wollen unser eigenes Model haben GenericTableModel extends AbstractTableModel
		// und org.jdesktop.swingx.JXTable nutzen, darin 
		// - GenericEditor
		// - NumberEditor
		// - BooleanEditor
		this.jXTable = createXTable();
	}

	public void setLoadState(StateValue state) {
		LOG.config("StateValue:"+state);
		if(state.equals(StateValue.STARTED)) {
			frame.setVisible(true);
		}
	}

	public GridTab getGridTab() {
		return this.gridTab;
	}
	
	public void refresh() {
//		die einzigen setter:
//		this.jXTable.getModel().setValueAt(aValue, rowIndex, columnIndex);
//		this.jXTable.getModel().isCellEditable(rowIndex, columnIndex)
		this.loader = initDataLoader();
		this.loader.execute();
	}
	
	/* setRowSelectionInterval(index0, index1)
	 * Selects the rows from <code>index0</code> to <code>index1</code>, inclusive.
	 */
	private void setRowSelection(int rowIndex) {
		// includeSpacing if false, return the true cell bounds -computed by subtracting 
		//  the intercellspacing from the height and widths ofthe column and row models
		jXTable.scrollRectToVisible(jXTable.getCellRect(rowIndex, 0, true)); // includeSpacing:true
		jXTable.setRowSelectionInterval(rowIndex, rowIndex);
	}
	
	public void first() {
		setRowSelection(0);
	}
	
	public void previous() {
		// diese methode hat Sinn im SingleRowModus
		// welches ist die currentRow im MultiRowModus?
		// - die kleinste selektierte!
		int currentRow = -1;
		int[] selected = jXTable.getSelectedRows(); // can be empty
		if(selected.length==0) {
			// und nun? wrap around
			currentRow = jXTable.getRowCount();
		} else {
			currentRow = selected[0];
		}
		LOG.config("currentRow:"+currentRow);
		currentRow--;
		if(currentRow<0) {
			last(); // wrap around
			return; // nix selektiert oder bereits bei first
		}
		setRowSelection(currentRow);
	}
	
	public void next() {
		int[] selected = jXTable.getSelectedRows(); // can be empty
		int currentRow = selected.length==0 ? -1 : selected[selected.length-1];
		LOG.config("currentRow:"+currentRow);
		currentRow++;
		setRowSelection( currentRow<jXTable.getRowCount() ? currentRow : 0 );
	}

	public void last() {
//		LOG.config("VisibleRowCount:"+jXTable.getVisibleRowCount() + " RowCount:"+jXTable.getRowCount());
//		jXTable.scrollRectToVisible(jXTable.getCellRect(jXTable.getRowCount()-1, 0, true));
//		LOG.config("VisibleRowCount:"+jXTable.getVisibleRowCount() + " RowCount:"+jXTable.getRowCount());
//		jXTable.setRowSelectionInterval(jXTable.getRowCount()-1, jXTable.getRowCount()-1);
		setRowSelection(jXTable.getRowCount()-1);
	}
	
	private int getWindowNo() {
		return frame.getWindowNo();
	}
	private List<GridTab> getGridTabs() {
		return frame.getGridTabs();
	}
	private List<Tab> getTabs() {
		return frame.getTabs();
	}
	
	private GenericDataLoader getDataLoaderBUGGY() {
        frame.tabPane = new HidableTabbedPane(); // BUG. so geht es nicht
        for (int i = 0; i < getGridTabs().size(); i++) { // ohne first
        	GridTab gt = getGridTabs().get(i);
        	Tab t = getTabs().get(i); 
        	frame.tabPane.addTab(gt.getName(), t);
        	t.initModelAndTable();
        	t.initDataLoader();
        }
        frame.jPanel.add(frame.tabPane, BorderLayout.CENTER);
        frame.pack();
        
        return this.loader;
	}
	public GenericDataLoader getDataLoader(JComponent vPanel) { // TEST TODO
		frame.jPanel.add(vPanel, BorderLayout.CENTER);
		
//		GridTab gridTab = getGridTabs().get(0); // first Tab
//		Tab tab = getTabs().get(0); 
        frame.tabPane = new HidableTabbedPane(gridTab.getName(), vPanel);
//        for (int i = 1; i < getGridTabs().size(); i++) { // ohne first
//        	GridTab gt = getGridTabs().get(i);
//        	Tab t = getTabs().get(i); 
//        	frame.tabPane.addTab(gt.getName(), t);
//        	t.loader = getDataLoader(gt, t);
//        }
        frame.jPanel.add(frame.tabPane, BorderLayout.CENTER);
        frame.pack();
//		setLocationRelativeTo(null);; // im caller! oben links würde es sonst angezeigt
        
        initModelAndTable();
        return initDataLoader();
	}
	public GenericDataLoader getDataLoader() { // TODO nicht nur first ==> this
		GridTab gridTab = getGridTabs().get(0); // first Tab
		Tab tab = getTabs().get(0); 
        frame.tabPane = new HidableTabbedPane(gridTab.getName(), tab);
        for (int i = 1; i < getGridTabs().size(); i++) { // ohne first
        	GridTab gt = getGridTabs().get(i);
        	Tab t = getTabs().get(i); 
        	frame.tabPane.addTab(gt.getName(), t);
        	t.initModelAndTable();
        	t.initDataLoader();
        }
        frame.jPanel.add(frame.tabPane, BorderLayout.CENTER);
        frame.pack();
//		setLocationRelativeTo(null);; // im caller! oben links würde es sonst angezeigt
        
        tab.initModelAndTable();    
        return tab.initDataLoader();
	}

	private void initModelAndTable() {
		this.tableModel = new GenericTableModel(this.gridTab, getWindowNo());
        JScrollPane scrollpane = new JScrollPane(this.jXTable);
        Stacker stacker = new Stacker(scrollpane);
        jXTable.setName(gridTab.getName());
        add(stacker, BorderLayout.CENTER);

        jXTable.setColumnControlVisible(true);
        // replace grid lines with striping 
        jXTable.setShowGrid(false, false);
        jXTable.addHighlighter(HighlighterFactory.createSimpleStriping());
        // initialize preferred size for table's viewable area
        jXTable.setVisibleRowCount(10); // TODO

//        CustomColumnFactory factory = new CustomColumnFactory();

        jXTable.setModel(tableModel);
//        return getDataLoader(tableModel);
		
	}
	
	private GenericDataLoader initDataLoader() {
 		this.loader = new GenericDataLoader(this.tableModel);
 		
		JLabel status = new JLabel();
        BindingGroup group = new BindingGroup();
        group.addBinding(Bindings.createAutoBinding(READ, loader, 
        		BeanProperty.create("progress"),
        		frame.progressBar, BeanProperty.create("value")));
        group.addBinding(Bindings.createAutoBinding(READ, loader, 
        		BeanProperty.create("state"),
        		this, BeanProperty.create("loadState"))); // call setLoadState 
        group.bind();

//		setVisible(true); // in setLoadState
		return loader;		
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

}
