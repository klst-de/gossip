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
		this.loader = getDataLoader(this.tableModel);
		this.loader.execute();
	}
	
	/* setRowSelectionInterval(index0, index1)
	 * Selects the rows from <code>index0</code> to <code>index1</code>, inclusive.
	 */
	public void first() {
		// includeSpacing if false, return the true cell bounds -computed by subtracting 
		//  the intercellspacing from the height and widths ofthe column and row models
		jXTable.scrollRectToVisible(jXTable.getCellRect(0, 0, true)); // includeSpacing:true
		jXTable.setRowSelectionInterval(0, 0);
	}
	
	public void previous() {
		// diese methode hat Sinn im SingleRowModus
		// welches ist die currentRow im MultiRowModus?
		// - die kleinste selektierte!
		int currentRow = -1;
		int[] selected = jXTable.getSelectedRows(); // can be empty
		if(selected.length==0) {
			// und nun? TODO
		} else {
			currentRow = selected[0];
			currentRow--;
		}
		LOG.config("new currentRow:"+currentRow);
		if(currentRow<0) return; // nix selektiert oder bereits bei first
		jXTable.scrollRectToVisible(jXTable.getCellRect(currentRow, 0, true));
		jXTable.setRowSelectionInterval(currentRow, currentRow);
	}
	
	public void last() {
		LOG.config("VisibleRowCount:"+jXTable.getVisibleRowCount() + " RowCount:"+jXTable.getRowCount());
		jXTable.scrollRectToVisible(jXTable.getCellRect(jXTable.getRowCount()-1, 0, true));
		LOG.config("VisibleRowCount:"+jXTable.getVisibleRowCount() + " RowCount:"+jXTable.getRowCount());
		jXTable.setRowSelectionInterval(jXTable.getRowCount()-1, jXTable.getRowCount()-1);
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
        	t.loader = getDataLoader(gt, t);
        }
        frame.jPanel.add(frame.tabPane, BorderLayout.CENTER);
        frame.pack();
        
        return this.loader;
	}
	public GenericDataLoader getDataLoader(JComponent vPanel) { // TEST TODO
		frame.jPanel.add(vPanel, BorderLayout.CENTER);
		
		GridTab gridTab = getGridTabs().get(0); // first Tab
		Tab tab = getTabs().get(0); 
        frame.tabPane = new HidableTabbedPane(gridTab.getName(), tab);
        for (int i = 1; i < getGridTabs().size(); i++) { // ohne first
        	GridTab gt = getGridTabs().get(i);
        	Tab t = getTabs().get(i); 
        	frame.tabPane.addTab(gt.getName(), t);
        	t.loader = getDataLoader(gt, t);
        }
        frame.pack();
//		setLocationRelativeTo(null);; // im caller! oben links würde es sonst angezeigt
        tab.loader = getDataLoader(gridTab, tab);
        return tab.loader;
	}
	public GenericDataLoader getDataLoader() { // TODO nicht nur first ==> this
		GridTab gridTab = getGridTabs().get(0); // first Tab
		Tab tab = getTabs().get(0); 
        frame.tabPane = new HidableTabbedPane(gridTab.getName(), tab);
        for (int i = 1; i < getGridTabs().size(); i++) { // ohne first
        	GridTab gt = getGridTabs().get(i);
        	Tab t = getTabs().get(i); 
        	frame.tabPane.addTab(gt.getName(), t);
        	t.loader = getDataLoader(gt, t);
        }
        frame.jPanel.add(frame.tabPane, BorderLayout.CENTER);
        frame.pack();
//		setLocationRelativeTo(null);; // im caller! oben links würde es sonst angezeigt
/*
public int getSelectedRow() existiert in JTable

Updates the selection models of the table, depending on the state of thetwo flags: toggle and extend. 
Most changesto the selection that are the result of keyboard or mouse events receivedby the UI are channeled through this method so that the behavior may beoverridden by a subclass. Some UIs may need more functionality thanthis method provides, such as when manipulating the lead for discontiguousselection, and may not call into this method for some selection changes. 

This implementation uses the following conventions: 
• toggle: false, extend: false.Clear the previous selection and ensure the new cell is selected. 
• toggle: false, extend: true.Extend the previous selection from the anchor to the specified cell,clearing all other selections. 
• toggle: true, extend: false.If the specified cell is selected, deselect it. If it is not selected, select it. 
• toggle: true, extend: true.Apply the selection state of the anchor to all cells between it and thespecified cell. 
Parameters:
rowIndex affects the selection at row
columnIndex affects the selection at columntoggle see description aboveextend if true, extend the current selection

this.jXTable.changeSelection(rowIndex, columnIndex, toggle, extend);

 *
    * Selects the rows from <code>index0</code> to <code>index1</code>,
     * inclusive.
        this.jXTable.setRowSelectionInterval(index0, index1);
 
 */
        tab.loader = getDataLoader(gridTab, tab);
        return tab.loader;
	}

	private GenericDataLoader getDataLoader(GridTab gridTab, Tab tab) {
		tab.tableModel = new GenericTableModel(gridTab, tab.getWindowNo());
        JScrollPane scrollpane = new JScrollPane(tab.jXTable);
        Stacker stacker = new Stacker(scrollpane);
        tab.jXTable.setName(gridTab.getName());
        tab.add(stacker, BorderLayout.CENTER);

        tab.jXTable.setColumnControlVisible(true);
        // replace grid lines with striping 
        tab.jXTable.setShowGrid(false, false);
        tab.jXTable.addHighlighter(HighlighterFactory.createSimpleStriping());
        // initialize preferred size for table's viewable area
        tab.jXTable.setVisibleRowCount(10); // TODO

//        CustomColumnFactory factory = new CustomColumnFactory();

        tab.jXTable.setModel(tab.tableModel);
        return getDataLoader(tab.tableModel);
	}
	
	private GenericDataLoader getDataLoader(GenericTableModel tableModel) {
 		GenericDataLoader task = new GenericDataLoader(tableModel);
 		
		JLabel status = new JLabel();
        BindingGroup group = new BindingGroup();
        group.addBinding(Bindings.createAutoBinding(READ, task, 
        		BeanProperty.create("progress"),
        		frame.progressBar, BeanProperty.create("value")));
        group.addBinding(Bindings.createAutoBinding(READ, task, 
        		BeanProperty.create("state"),
        		this, BeanProperty.create("loadState"))); // call setLoadState 
        group.bind();

//		setVisible(true); // in setLoadState
		return task;		
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
