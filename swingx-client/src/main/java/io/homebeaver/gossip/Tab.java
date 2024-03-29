package io.homebeaver.gossip;

import static org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.EnumMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker.StateValue;

import org.compiere.model.GridTab;
import org.compiere.util.Env;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.swingx.icon.JXIcon;

import com.klst.gossip.MXTable;
import com.klst.gossip.SingleRowPanel;
import com.klst.gossip.wrapper.GridTableModel;
import com.klst.gossip.wrapper.TabModel;

import gov.nasa.arc.mct.gui.impl.HidableTabbedPane;
import io.homebeaver.gossip.icon.IconFactory;

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
	private TabModel tabModel;
	private GridTableModel gridTableModel;
	private GenericDataLoader dataLoader;
	int currentRow = -1;

	// ui
//	MuliRowPanel mrp; // MuliRowPanel extends     JXTable extends JTable implements TableColumnModelExtListener
	MXTable mXTable; // MXTable extends JXTable , JXTable extends JTable implements TableColumnModelExtListener
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
		if(frame.windowModel.isTabInitialized(tabIndex)==false) {
			LOG.config("tabModel "+tabIndex+" not initialized. Do it now ...");
			frame.windowModel.initTab(tabIndex); // (base)GridWindow.initTab
		}
//		this.tabModel = frame.windowModel.getTab(tabIndex);
//		GridTabVO vo = this.tabModel.getM_vo();
		this.tabModel = new TabModel(frame.windowModel.getTab(tabIndex).getM_vo(), frame.windowModel);
		this.tabModel.initTab(false);
		
//		String tableName = this.tabModel.get_TableName(); 
//		GridField[] gFields = this.tabModel.getFields(); 
//		LOG.config(" tableName="+tableName+" gFields.length="+gFields.length);
//		
//		GridTable gridTable = this.tabModel.getTableModel();
//		LOG.warning("GridTable gridTable.Class="+gridTable.getClass());
		
//		Properties props = Env.getCtx();
//		for(Object key: props.keySet()) {
//			LOG.config("Ctx "+key + " : " + props.getProperty(key.toString()));
//		}
		this.gridTableModel = this.tabModel.getGridTableModel();
		
		this.addComponentListener(this);
		this.setName(this.tabModel.getName());
		
/* 
 in GridTab gibt es ein GridTable m_mTable // GridTable extends AbstractTableModel
 wir wollen unser eigenes Model haben GenericDataModel/GridTableModel extends AbstractTableModel
 und org.jdesktop.swingx.JXTable nutzen, darin 
 - GenericEditor
 - NumberEditor
 - BooleanEditor
 */	
		
		if(statusToTrafficlights.isEmpty()) {
			statusToTrafficlights.put(StateValue.PENDING, IconFactory.getTLRED(JXIcon.SMALL_ICON));
			statusToTrafficlights.put(StateValue.STARTED, IconFactory.getTLYELLOW(JXIcon.SMALL_ICON));
			statusToTrafficlights.put(StateValue.DONE   , IconFactory.getTLGREEN(JXIcon.SMALL_ICON));
		}
		LOG.config("<<<<<<<<<<<<<<<<<<<<<<<< ctor fertig");
	}

	public TabModel getTabModel() {
		return this.tabModel;
	}
	
	void cancel() {
		if(this.dataLoader==null) {
			return;
		}
		LOG.fine("mayInterrupt dataLoader IfRunning");
		dataLoader.cancel(true); // true == mayInterruptIfRunning
	}
	
	public void refresh() {
//		gtm.getDataVector().removeAllElements(); // leeren aber nicht entfernen == clear
//		gtm.getDataVector().clear();
		gridTableModel.setRowCount(0); // @see https://stackoverflow.com/questions/6232355/deleting-all-the-rows-in-a-jtable
		frame.tableStatus.setText(""); // cancelled Status
		this.dataLoader = initDataLoader();
		this.dataLoader.execute();
	}
	
	private void updateStatusBar() {
//		StringBuilder text = new StringBuilder("").append(currentRow+1).append("/").append(dataModel.getRowCount());
		StringBuilder text = new StringBuilder("").append(currentRow+1).append("/").append(gridTableModel.getRowCount());
		if(gridTableModel.getRowCount()==gridTableModel.getRowsToLoad()) {
			// OK alles geladen
		} else {
			text.append("/").append(gridTableModel.getRowsToLoad());
		}
		frame.tableRows.setText(text.toString());
	}

	/* setRowSelectionInterval(index0, index1)
	 * Selects the rows from <code>index0</code> to <code>index1</code>, inclusive.
	 */
	private void setRowSelection(int rowIndex) {
		// includeSpacing if false, return the true cell bounds -computed by subtracting 
		//  the intercellspacing from the height and widths ofthe column and row models
		mXTable.scrollRectToVisible(mXTable.getCellRect(rowIndex, 0, true)); // includeSpacing:true
		mXTable.setRowSelectionInterval(rowIndex, rowIndex);
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
		int[] selected = mXTable.getSelectedRows(); // can be empty
		if(selected.length==0) {
			// und nun? wrap around
			currentRow = mXTable.getRowCount();
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
		int[] selected = mXTable.getSelectedRows(); // can be empty
		int currentRow = selected.length==0 ? -1 : selected[selected.length-1];
		LOG.config("currentRow:"+currentRow);
		currentRow++;
		setRowSelection( currentRow<mXTable.getRowCount() ? currentRow : 0 );
	}

	public void last() {
		setRowSelection(mXTable.getRowCount()-1);
	}
	
	private int getWindowNo() {
		return frame.getWindowNo();
	}
	private List<TabModel> getTabModels() {
		return frame.getTabModels();
	}
	private List<Tab> getTabs() {
		return frame.getTabs();
	}
	
	public GenericDataLoader getDataLoader() { // TODO nicht nur first ==> this
//		GridTab gridTab = getGridTabs().get(0); // first Tab
//		Tab tab = getTabs().get(0); 
		Dimension preferredDim = initModelAndTable(null); // null == calculate preferredDim 
		LOG.config("'"+this.getName()+"' preferredDim:"+preferredDim);
        frame.tabPane = new HidableTabbedPane(tabModel.getName(), this);
        for (int i = 1; i < getTabModels().size(); i++) { // ohne first
        	GridTab gt = getTabModels().get(i);
        	Tab t = getTabs().get(i); 
        	frame.tabPane.addTab(gt.getName(), t);
        	t.initModelAndTable(preferredDim);
        	t.initDataLoader(); // ?kann ich DataLoader für tab>0 jetzt schon initialisieren?
        }
        frame.jPanel.add(frame.tabPane, BorderLayout.CENTER);
        frame.pack();
//		setLocationRelativeTo(null);; // im caller! oben links würde es sonst angezeigt
        
        return initDataLoader();
	}

	private Dimension getSingleRowPanelSize() {
//		srp = new SingleRowPanel(this.gridTableModel); // darin VPanel gekapselt!
//		return srp.getSingleRowPanelSize();
		return new Dimension(200,200);
	}
	
	private Dimension initModelAndTable(Dimension useDim) {
		LOG.config("Tab.Name=:'"+this.getName()+"' isSingleRow:"+tabModel.isSingleRow());
		Dimension preferredDim = useDim;
		if(preferredDim==null && tabModel.isSingleRow()) {
			preferredDim = getSingleRowPanelSize();
			// TODO die Berechnung der preferredDim ist ohne included Tab!!!!
			LOG.warning("dimension: H/W "+preferredDim.getHeight()+"/"+preferredDim.getWidth());
		} else {
			LOG.config("preferredDim set to useDim:"+useDim);
		}
		
		// init
		mXTable = MXTable.createXTable(this.gridTableModel); 
		
//		if(!gridTab.isSingleRow()) { // isSingleRow aka Single Row Panel in MigLayout für dieses Tab !!!!!!!!!!!!!!! TODO NOT raus - ist nur zum Test
		if(tabModel.isSingleRow()) {	
			if(this.srp==null) {
				add(new JLabel("Platzhalter"), BorderLayout.CENTER); // diesen lazy berechnen
			} else {
				add(srp, BorderLayout.CENTER);	
			} 
		} else {
			this.setPreferredSize(preferredDim);
	        JScrollPane scrollpane = new JScrollPane(mXTable);
//	        Stacker stacker = new Stacker(scrollpane);
	        mXTable.setName(tabModel.getName());
//	        add(stacker, BorderLayout.CENTER);
	        add(scrollpane, BorderLayout.CENTER);
	        
//	        CustomColumnFactory factory = new CustomColumnFactory();
//-----------
	        //aus GenericFormPanel:
//			setSelectWhereClause(); // where clause für den Loader
			
			// javax.swing.table.DefaultTableModel erwartet raw type Vector data
//TODO:	        
//			Vector<Vector<Object>> data = getData(gridTableModel); // Vector data wird für worker benötigt
//			DefaultTableModel dataModel = new DefaultTableModel(data, getFieldsNames(gridTableModel));
			
			//miniTable.setModel(dataModel);
			mXTable.setModel(gridTableModel); // bereits in MXTable.createXTable(gtm)
			
//			GridField[] fields = gridTableModel.getFields();
//			assert(gridTableModel.getRowCount()==miniTable.getRowCount());
//			for(int f = 0; f < fields.length; f++) {
//				GridField field = fields[f];
//				if(field.isSelectionColumn()) {
//					addSelection(field); 
//				}								
//			}
			//			addSelection(fields); // additional selectionFields
//			if(gridTableModel.getRowCount()>0) {
//				// JTable.changeSelection(rowIndex, columnIndex, toggle, extend);
//				miniTable.changeSelection(0, -1, false, false);
//			}
//			
//			setPreferredSize();

//------------
	        LOG.config("CellSelectionEnabled:"+mXTable.getCellSelectionEnabled()); // sollte true sein!? TODO ist aber false
			this.listSelectionModel = mXTable.getSelectionModel();
	        listSelectionModel.addListSelectionListener(event -> {
	        	currentRow = event.getFirstIndex();
	            updateStatusBar();
	        });
		}


        
        return preferredDim;
	}
	

	private GenericDataLoader initDataLoader() {
		this.dataLoader = new GenericDataLoader(this.gridTableModel);
        BindingGroup group = new BindingGroup();
        group.addBinding(Bindings.createAutoBinding(READ, dataLoader, BeanProperty.create("progress"), frame.progressBar, BeanProperty.create("value")));
        group.addBinding(Bindings.createAutoBinding(READ, dataLoader, BeanProperty.create("state"), this, BeanProperty.create("loadState"))); // call setLoadState 
// TODO Tab.setLoadState: Unposted Documents StateValue:DONE wird ZWEI mal gerufen ??????????
        group.bind();
        dataLoader.addPropertyChangeListener(event -> {
        	if ("state".equals(event.getPropertyName())) {
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
			LOG.config("ParentTab:"+tabModel.getParentTab());
			this.dataLoader.execute();
		}
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		LOG.warning(""+e.getComponent());
	}

}
