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

import org.compiere.model.GridTab;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;

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
	private GenericDataModel dataModel;
	private GenericDataLoader dataLoader;
	int currentRow = -1;

	// ui
	MuliRowPanel mrp; // MuliRowPanel extendsJXTable extends JTable implements TableColumnModelExtListener
	ListSelectionModel listSelectionModel; // the ListSelectionModel that is used to maintain rowselection state
	SingleRowPanel srp = null; // SingleRowPanel extends JPanel , kapselt VPanel 
	
	// ctor
	/* super ctors
	 * 	   public JPanel(LayoutManager layout, boolean isDoubleBuffered) 
	 *     public JPanel(LayoutManager layout) 
	 *     public JPanel(boolean isDoubleBuffered) 
	 */
	public Tab(WindowFrame frame, int tabIndex) {
		super(new BorderLayout());
		LOG.config("tabIndex:"+tabIndex + ", WindowFrame frame:"+frame);
		this.frame = frame;
		if(frame.gridWindow.isTabInitialized(tabIndex)==false) {
			LOG.config("gridTab "+tabIndex+" not initialized. Do it now ...");
			frame.gridWindow.initTab(tabIndex);
		}
		this.gridTab = frame.gridWindow.getTab(tabIndex);
		this.addComponentListener(this);
		this.setName(this.gridTab.getName());
		
/* TODO GridTab Bridge
 in GridTab gibt es ein GridTable m_mTable // GridTable extends AbstractTableModel
 wir wollen unser eigenes Model haben GenericDataModel extends AbstractTableModel
 und org.jdesktop.swingx.JXTable nutzen, darin 
 - GenericEditor
 - NumberEditor
 - BooleanEditor
 */	
		
		if(statusToTrafficlights.isEmpty()) {
			statusToTrafficlights.put(StateValue.PENDING, frame.AIT.getImageIcon(frame.AIT.RLI, WindowFrame.SMALL_ICON_SIZE));
			statusToTrafficlights.put(StateValue.STARTED, frame.AIT.getImageIcon(frame.AIT.YLI, WindowFrame.SMALL_ICON_SIZE));
			statusToTrafficlights.put(StateValue.DONE   , frame.AIT.getImageIcon(frame.AIT.GLI, WindowFrame.SMALL_ICON_SIZE));
		}
		LOG.config("<<<<<<<<<<<<<<<<<<<<<<<< ctor fertig");
	}

	public GridTab getGridTab() {
		return this.gridTab;
	}
	
	void cancel() {
		if(this.dataLoader==null) {
			return;
		}
		LOG.fine("mayInterrupt dataLoader IfRunning");
		dataLoader.cancel(true); // true == mayInterruptIfRunning
	}
	
	public void refresh() {
		this.dataModel.clear(); // remove all elements
		frame.tableStatus.setText(""); // cancelled Status
		this.dataLoader = initDataLoader();
		this.dataLoader.execute();
	}
	
	private void updateStatusBar() {
		StringBuilder text = new StringBuilder("").append(currentRow+1).append("/").append(dataModel.getRowCount());
		if(dataModel.getRowCount()==dataModel.getRowsToLoad()) {
			// OK alles geladen
		} else {
			text.append("/").append(dataModel.getRowsToLoad());
		}
		frame.tableRows.setText(text.toString());
	}

	/* setRowSelectionInterval(index0, index1)
	 * Selects the rows from <code>index0</code> to <code>index1</code>, inclusive.
	 */
	private void setRowSelection(int rowIndex) {
		// includeSpacing if false, return the true cell bounds -computed by subtracting 
		//  the intercellspacing from the height and widths ofthe column and row models
		mrp.scrollRectToVisible(mrp.getCellRect(rowIndex, 0, true)); // includeSpacing:true
		mrp.setRowSelectionInterval(rowIndex, rowIndex);
		currentRow = rowIndex;
		updateStatusBar();	
	}
	
	public void first() {
		setRowSelection(0);
		srp.removeAll();
		srp.showSingleRowPanelSize(0);
	}
	
	public void previous() {
		// diese methode hat Sinn im SingleRowModus
		// welches ist die currentRow im MultiRowModus?
		// - die kleinste selektierte!
		int currentRow = -1;
		int[] selected = mrp.getSelectedRows(); // can be empty
		if(selected.length==0) {
			// und nun? wrap around
			currentRow = mrp.getRowCount();
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
		int[] selected = mrp.getSelectedRows(); // can be empty
		int currentRow = selected.length==0 ? -1 : selected[selected.length-1];
		LOG.config("currentRow:"+currentRow);
		currentRow++;
		setRowSelection( currentRow<mrp.getRowCount() ? currentRow : 0 );
	}

	public void last() {
		setRowSelection(mrp.getRowCount()-1);
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
//		GridTab gridTab = getGridTabs().get(0); // first Tab
//		Tab tab = getTabs().get(0); 
		Dimension preferredDim = initModelAndTable(null); // null == calculate preferredDim 
		LOG.config("'"+this.getName()+"' preferredDim:"+preferredDim);
        frame.tabPane = new HidableTabbedPane(gridTab.getName(), this);
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
        
        return initDataLoader();
	}

	private Dimension getSingleRowPanelSize() {
//		if(dataModel.getDbTableName().equals("AD_OrgType")) {
//			LOG.warning("----------------const Patch für AD_OrgType == "+dataModel.getDbTableName());
//			return new Dimension(523, 125);
//		}
		srp = new SingleRowPanel(this.dataModel); // darin VPanel gekapselt!
		return srp.getSingleRowPanelSize();
	}
	
	private Dimension initModelAndTable(Dimension useDim) {
		this.dataModel = new GenericDataModel(this.gridTab, getWindowNo());
//		dataModel.addTableModelListener(event -> {
//			LOG.warning("event Rows "+event.getFirstRow()+":"+event.getLastRow() + ", RowCount:"+dataModel.getRowCount()+"/"+dataModel.getRowsToLoad());
//			if(event.getFirstRow()==TableModelEvent.HEADER_ROW && this.currentRow<0) {
//				first();
//			}
//		});

		LOG.config("Tab.Name=:'"+this.getName()+"' isSingleRow:"+gridTab.isSingleRow());
		Dimension preferredDim = useDim;
		if(preferredDim==null) {
			preferredDim = getSingleRowPanelSize();
			// TODO die Berechnung der preferredDim ist ohne included Tab!!!!
			LOG.warning("dimension: H/W "+preferredDim.getHeight()+"/"+preferredDim.getWidth());
		} else {
			LOG.config("preferredDim set to useDim:"+useDim);
		}
		
		// init
		this.mrp = MuliRowPanel.createXTable(dataModel);
		
//		if(!gridTab.isSingleRow()) { // isSingleRow aka Single Row Panel in MigLayout für dieses Tab !!!!!!!!!!!!!!! TODO NOT raus - ist nur zum Test
		if(gridTab.isSingleRow()) {	
			if(this.srp==null) {
				add(new JLabel("Platzhalter"), BorderLayout.CENTER); // diesen lazy berechnen
			} else {
				add(srp, BorderLayout.CENTER);	
			} 
		} else {
			this.setPreferredSize(preferredDim);
	        JScrollPane scrollpane = new JScrollPane(this.mrp);
//	        Stacker stacker = new Stacker(scrollpane);
	        mrp.setName(gridTab.getName());
//	        add(stacker, BorderLayout.CENTER);
	        add(scrollpane, BorderLayout.CENTER);
	        
//	        CustomColumnFactory factory = new CustomColumnFactory();
	        
	        LOG.config("CellSelectionEnabled:"+mrp.getCellSelectionEnabled()); // sollte true sein!? TODO ist aber false
			this.listSelectionModel = mrp.getSelectionModel();
	        listSelectionModel.addListSelectionListener(event -> {
	        	currentRow = event.getFirstIndex();
	            updateStatusBar();
	        });
		}


        
        return preferredDim;
	}
	

	private GenericDataLoader initDataLoader() {
 		this.dataLoader = new GenericDataLoader(this.dataModel);
 		
        BindingGroup group = new BindingGroup();
        group.addBinding(Bindings.createAutoBinding(READ, dataLoader, BeanProperty.create("progress"), frame.progressBar, BeanProperty.create("value")));
        group.addBinding(Bindings.createAutoBinding(READ, dataLoader, BeanProperty.create("state"), this, BeanProperty.create("loadState"))); // call setLoadState 
//      group.addBinding(Bindings.createAutoBinding(READ, dataLoader, BeanProperty.create("cancelled"), frame.tableStatus, BeanProperty.create("text"))); // schreibt true, wie frame.tableStatus.setText(text)
        group.bind();

//		setVisible(true); // in setLoadState
        
        dataLoader.addPropertyChangeListener(event -> {
        	//LOG.config("event.getPropertyName() =============== event:"+event);
//        	if ("cancelled".equals(event.getPropertyName())) { // ist gar nicht gebunden ==> also raus/auskommentieren
//        		if(dataLoader.isCancelled()) {
//        			frame.tableStatus.setText("cancelled ");
//        			setLoadState(StateValue.PENDING);
//        		}
//        	}
        	if ("state".equals(event.getPropertyName())) {
//        		if(event.getNewValue()==StateValue.DONE) ...
        		setLoadState((StateValue)event.getNewValue());
        	}
        });
		return dataLoader;		
	}

	public void setLoadState(StateValue state) {
		LOG.config(this.getName()+" StateValue:"+state);
//		frame.actionStatus.setText(state.name()); // sieht nicht gut aus => Ampel:
		frame.actionStatus.setIcon(statusToTrafficlights.get(state));
		if(state.equals(StateValue.STARTED)) {
			frame.setVisible(true);		
			updateStatusBar();
		} else if(state.equals(StateValue.DONE)) {
			frame.setVisible(true);	
			updateStatusBar();
		}
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
			this.dataLoader.execute();
		}
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		LOG.warning(""+e.getComponent());
	}

}