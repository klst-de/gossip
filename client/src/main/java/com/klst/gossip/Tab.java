package com.klst.gossip;

import static org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.EnumMap;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
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

	private static EnumMap<StateValue, Icon> statusToTrafficlights = new EnumMap<>(StateValue.class);
	
	// AD Komponenten:
	// MTab mit den Metadaten
	// swing Komponenten:
	// JPanel parentTab
	// HidableTabbedPane parentContainer
	// window / bzw JFrame
	// rootFrame

	private WindowFrame frame;
	private GridTab gridTab;
//	private List<GridField> fields;
	private GenericTableModel tableModel;
	private GenericDataLoader loader;
	int currentRow = -1;

	// ui
	JXTable jXTable; // JXTable extends JTable implements TableColumnModelExtListener
	ListSelectionModel listSelectionModel; // the ListSelectionModel that is used to maintain rowselection state
	SingleRowPanel singleRowPanel = null; // kapselt VPanel 
	
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
//		this.fields = Arrays.asList(this.gridTab.getFields());
		this.addComponentListener(this);
		this.setName(this.gridTab.getName());
		
		// in GridTab gibt es ein GridTable m_mTable // GridTable extends AbstractTableModel
		// wir wollen unser eigenes Model haben GenericTableModel extends AbstractTableModel
		// und org.jdesktop.swingx.JXTable nutzen, darin 
		// - GenericEditor
		// - NumberEditor
		// - BooleanEditor
		this.jXTable = createXTable();
		this.listSelectionModel = jXTable.getSelectionModel();
		
		if(statusToTrafficlights.isEmpty()) {
			statusToTrafficlights.put(StateValue.PENDING, frame.AIT.getImageIcon(frame.AIT.RLI, WindowFrame.SMALL_ICON_SIZE));
			statusToTrafficlights.put(StateValue.STARTED, frame.AIT.getImageIcon(frame.AIT.YLI, WindowFrame.SMALL_ICON_SIZE));
			statusToTrafficlights.put(StateValue.DONE   , frame.AIT.getImageIcon(frame.AIT.GLI, WindowFrame.SMALL_ICON_SIZE));
		}
	}

	public void setLoadState(StateValue state) {
		LOG.config(this.getName()+" StateValue:"+state);
//		frame.actionStatus.setText(state.name()); // sieht nicht gut aus => Ampel:
		frame.actionStatus.setIcon(statusToTrafficlights.get(state));
		if(state.equals(StateValue.STARTED)) {
			frame.setVisible(true);		
			updateStatusBar();
		} else if(state.equals(StateValue.DONE)) {
			updateStatusBar();
		}
	}

	public GridTab getGridTab() {
		return this.gridTab;
	}
	
	void cancel() {
		if(this.loader==null) {
			return;
		}
		LOG.config("mayInterruptIfRunning");
		loader.cancel(true); // true == mayInterruptIfRunning 
	}
	
	public void refresh() {
		this.tableModel.clear(); // remove all elements
		frame.tableStatus.setText(""); // cancelled Status
		this.loader = initDataLoader();
		this.loader.execute();
	}
	
	private void updateStatusBar() {
		StringBuilder text = new StringBuilder("").append(currentRow+1).append("/").append(tableModel.getRowCount());
		if(tableModel.getRowCount()==tableModel.getRowsToLoad()) {
			// OK alles geladen
		} else {
			text.append("/").append(tableModel.getRowsToLoad());
		}
		frame.tableRows.setText(text.toString());
	}

	/* setRowSelectionInterval(index0, index1)
	 * Selects the rows from <code>index0</code> to <code>index1</code>, inclusive.
	 */
	private void setRowSelection(int rowIndex) {
		// includeSpacing if false, return the true cell bounds -computed by subtracting 
		//  the intercellspacing from the height and widths ofthe column and row models
		jXTable.scrollRectToVisible(jXTable.getCellRect(rowIndex, 0, true)); // includeSpacing:true
		jXTable.setRowSelectionInterval(rowIndex, rowIndex);
		currentRow = rowIndex;
		updateStatusBar();	
	}
	
	public void first() {
		setRowSelection(0);
		singleRowPanel.removeAll();
		singleRowPanel.showSingleRowPanelSize(0);
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
	
	// nur zur Doku
//	private GenericDataLoader getDataLoaderBUGGY() {
//        frame.tabPane = new HidableTabbedPane(); // BUG. so geht es nicht
//        for (int i = 0; i < getGridTabs().size(); i++) { // ohne first
//        	GridTab gt = getGridTabs().get(i);
//        	Tab t = getTabs().get(i); 
//        	frame.tabPane.addTab(gt.getName(), t);
//        	t.initModelAndTable(???);
//        	t.initDataLoader();
//        }
//        frame.jPanel.add(frame.tabPane, BorderLayout.CENTER);
//        frame.pack();
//        
//        return this.loader;
//	}
	public GenericDataLoader getDataLoader() { // TODO nicht nur first ==> this
		GridTab gridTab = getGridTabs().get(0); // first Tab
		Tab tab = getTabs().get(0); 
		Dimension preferredDim = tab.initModelAndTable(null); // null == calculate preferredDim 
		LOG.config(this.getName()+" preferredDim:"+preferredDim);
        frame.tabPane = new HidableTabbedPane(gridTab.getName(), tab);
        for (int i = 1; i < getGridTabs().size(); i++) { // ohne first
        	GridTab gt = getGridTabs().get(i);
        	Tab t = getTabs().get(i); 
        	frame.tabPane.addTab(gt.getName(), t);
        	t.initModelAndTable(preferredDim);
        	t.initDataLoader();
        }
        frame.jPanel.add(frame.tabPane, BorderLayout.CENTER);
        frame.pack();
//		setLocationRelativeTo(null);; // im caller! oben links würde es sonst angezeigt
        
        return tab.initDataLoader();
	}

	private Dimension getSingleRowPanelSize() {
		singleRowPanel = new SingleRowPanel(this.tableModel); // darin VPanel gekapselt!
		return singleRowPanel.getSingleRowPanelSize();
	}
	
	private Dimension initModelAndTable(Dimension useDim) {
		this.tableModel = new GenericTableModel(this.gridTab, getWindowNo());
		tableModel.addTableModelListener(event -> {
			LOG.warning("getFirstRow:"+event.getFirstRow());
			if(event.getFirstRow()==0 && this.currentRow<0) {
				first();
			}
		});

		LOG.config(this.getName()+" isSingleRow:"+gridTab.isSingleRow());
		Dimension preferredDim = useDim;
		if(preferredDim==null) {
			preferredDim = getSingleRowPanelSize();
			// TODO die Berechnung der preferredDim ist ohne included Tab!!!!
			LOG.warning("dimension: H/W "+preferredDim.getHeight()+"/"+preferredDim.getWidth());
		} else {
			LOG.config("preferredDim set to useDim:"+useDim);
		}
		if(!gridTab.isSingleRow()) { // isSingleRow aka Single Row Panel in MigLayout für dieses Tab !!!!!!!!!!!!!!! NOT raus
			if(this.singleRowPanel==null) {
				add(new JLabel("Platzhalter"), BorderLayout.CENTER); // diesen lazy berechnen
			} else {
				add(singleRowPanel, BorderLayout.CENTER);	
			} 
		} else {
			this.setPreferredSize(preferredDim);
	        JScrollPane scrollpane = new JScrollPane(this.jXTable);
	        Stacker stacker = new Stacker(scrollpane);
	        jXTable.setName(gridTab.getName());
	        add(stacker, BorderLayout.CENTER);			
		}

        jXTable.setColumnControlVisible(true);
        // replace grid lines with striping 
        jXTable.setShowGrid(false, false);
        jXTable.addHighlighter(HighlighterFactory.createSimpleStriping());

//        CustomColumnFactory factory = new CustomColumnFactory();

        jXTable.setModel(tableModel);
        listSelectionModel.addListSelectionListener(event -> {
        	currentRow = event.getFirstIndex();
            updateStatusBar();
        });
        
        return preferredDim;
	}
	

	private GenericDataLoader initDataLoader() {
 		this.loader = new GenericDataLoader(this.tableModel);
 		
        BindingGroup group = new BindingGroup();
        group.addBinding(Bindings.createAutoBinding(READ, loader, 
        		BeanProperty.create("progress"),
        		frame.progressBar, BeanProperty.create("value")));
        group.addBinding(Bindings.createAutoBinding(READ, loader, 
        		BeanProperty.create("state"),
        		this, BeanProperty.create("loadState"))); // call setLoadState 
//        group.addBinding(Bindings.createAutoBinding(READ, loader, 
//        		BeanProperty.create("cancelled"), // liefert Boolean.TRUE
//        		frame.tableStatus, BeanProperty.create("text"))); // schreibt true, wie frame.tableStatus.setText(text)
        group.bind();

//		setVisible(true); // in setLoadState
        
        loader.addPropertyChangeListener(event -> {
        	if ("cancelled".equals(event.getPropertyName())) {
        		if(loader.isCancelled()) {
        			frame.tableStatus.setText("cancelled ");
        			setLoadState(StateValue.PENDING);
        		}
        	}
        });
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
