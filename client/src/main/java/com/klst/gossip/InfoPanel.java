package com.klst.gossip;

import static org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ;

import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker.StateValue;

import org.compiere.model.I_AD_Role;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;

import gov.nasa.arc.mct.gui.impl.HidableTabbedPane;

// ? InfoPanel ist eine R/O-Variante von MuliRowPanel extends JXTable, bzw Tab extends JPanel, das MuliRowPanel enthält 
public class InfoPanel extends JPanel implements ComponentListener {

	private static final long serialVersionUID = -8886435209941973618L;

	private static final Logger LOG = Logger.getLogger(InfoPanel.class.getName());
	
	private static EnumMap<StateValue, Icon> statusToTrafficlights = new EnumMap<>(StateValue.class);
	
	// AD Komponenten:
	// 
	// swing Komponenten:
	// JPanel 
	// HidableTabbedPane parentContainer
	// window / bzw JFrame
	// rootFrame

	private WindowFrame frame;
//	private GridTab gridTab;
	private GenericDataModel dataModel;
	private GenericDataLoader dataLoader;
	int currentRow = -1;

	// ui
	MuliRowPanel mrp; // MuliRowPanel extendsJXTable extends JTable implements TableColumnModelExtListener
	ListSelectionModel listSelectionModel; // the ListSelectionModel that is used to maintain rowselection state
//	SingleRowPanel srp = null; // SingleRowPanel extends JPanel , kapselt VPanel 

	// ctor ref in Tab.getSingleRowPanelSize() : srp = new SingleRowPanel(this.dataModel); // darin VPanel gekapselt!
	// in Tab wird der tm und dataloader initialisiert: this.dataLoader = new GenericDataLoader(this.dataModel);
	// für ctor public GenericDataModel(GridTab gridTab, int windowNo) brauche ich (base) GridTab und windowNo
	//  ABER GridTab gibt es für Info Tabellen nicht, ERGO für Info GenericDataModel ohne GridTab
	public InfoPanel(WindowFrame frame, GenericDataModel tm) {
		super(new BorderLayout());
		LOG.config("GenericDataModel:"+tm + ", WindowFrame frame:"+frame);
		this.frame = frame;
		this.dataModel = tm;

		this.addComponentListener(this);
		this.setName(tm.getName());
		
//		srp = new SingleRowPanel(this.dataModel); // darin VPanel gekapselt!

		if(statusToTrafficlights.isEmpty()) {
			statusToTrafficlights.put(StateValue.PENDING, frame.AIT.getImageIcon(frame.AIT.RLI, WindowFrame.SMALL_ICON_SIZE));
			statusToTrafficlights.put(StateValue.STARTED, frame.AIT.getImageIcon(frame.AIT.YLI, WindowFrame.SMALL_ICON_SIZE));
			statusToTrafficlights.put(StateValue.DONE   , frame.AIT.getImageIcon(frame.AIT.GLI, WindowFrame.SMALL_ICON_SIZE));
		}
	}
	
	public GenericDataLoader getDataLoader() {
//		Dimension preferredDim = srp.getSingleRowPanelSize();
//		LOG.config("'"+this.getName()+"' preferredDim:"+preferredDim);

		this.mrp = MuliRowPanel.createXTable(dataModel);
//		this.setPreferredSize(preferredDim);
		
		JScrollPane scrollpane = new JScrollPane(this.mrp);
		add(scrollpane, BorderLayout.CENTER);
		LOG.config("CellSelectionEnabled:"+mrp.getCellSelectionEnabled()); // sollte true sein!? TODO ist aber false
		this.listSelectionModel = mrp.getSelectionModel();
        listSelectionModel.addListSelectionListener(event -> {
        	currentRow = event.getFirstIndex();
            updateStatusBar();
        });
        	
        frame.tabPane = new HidableTabbedPane(this.getName(), scrollpane);
        frame.jPanel.add(frame.tabPane, BorderLayout.CENTER);
//        frame.jPanel.add(scrollpane);
        frame.pack();
		initDataLoader();
		return this.dataLoader;
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
//		srp.removeAll();
//		srp.showSingleRowPanelSize(0);
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

	GenericDataLoader initDataLoader() { // ähnlich zu Tab.initDataLoader()
		this.dataLoader = new GenericDataLoader(this.dataModel);

		BindingGroup group = new BindingGroup();
        group.addBinding(Bindings.createAutoBinding(READ, dataLoader, BeanProperty.create("progress"), frame.progressBar, BeanProperty.create("value")));
        group.addBinding(Bindings.createAutoBinding(READ, dataLoader, BeanProperty.create("state"), this, BeanProperty.create("loadState"))); // call setLoadState 
        group.bind();
        
        dataLoader.addPropertyChangeListener(event -> {
        	if ("state".equals(event.getPropertyName())) {
        		setLoadState((StateValue)event.getNewValue());
        	}
        });
        
		return dataLoader;	
	}
	
	public void setLoadState(StateValue state) { // ähnlich zu Tab.setLoadState()
		LOG.config(this.getName()+" StateValue:"+state);
		frame.actionStatus.setIcon(statusToTrafficlights.get(state));
		if(state.equals(StateValue.STARTED)) {
			frame.setVisible(true);		
			updateStatusBar();
		} else if(state.equals(StateValue.DONE)) {
			frame.setVisible(true);	
			updateStatusBar();
		}
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

	private static final Map<String, String> ALLOWANCE = createMap();
    private static Map<String, String> createMap() {
        Map<String, String> result = new HashMap<String, String>();
        result.put("Account", I_AD_Role.COLUMNNAME_Allow_Info_Account);
        result.put("Asset", I_AD_Role.COLUMNNAME_Allow_Info_Asset);
        result.put("BPartner", I_AD_Role.COLUMNNAME_Allow_Info_BPartner);
        result.put("CashJournal", I_AD_Role.COLUMNNAME_Allow_Info_CashJournal);
        result.put("CRP", I_AD_Role.COLUMNNAME_Allow_Info_CRP);
        result.put("InOut", I_AD_Role.COLUMNNAME_Allow_Info_InOut);
        result.put("Invoice", I_AD_Role.COLUMNNAME_Allow_Info_Invoice);
        result.put("MRP", I_AD_Role.COLUMNNAME_Allow_Info_MRP);
        result.put("Order", I_AD_Role.COLUMNNAME_Allow_Info_Order);
        result.put("Payment", I_AD_Role.COLUMNNAME_Allow_Info_Payment);
        result.put("Product", I_AD_Role.COLUMNNAME_Allow_Info_Product);
        result.put("Resource", I_AD_Role.COLUMNNAME_Allow_Info_Resource);
        result.put("Schedule", I_AD_Role.COLUMNNAME_Allow_Info_Schedule);
        return Collections.unmodifiableMap(result);
    }

	// aus (client) AEnv
	/**
	 * 
	 * @param infoWindowName Urspünglich suffix zu ALLOW_INFO_ bildet spalte in AD_ROLE, 
	 *  besser aus AD_ROLE (AD_Table_ID=156) holen
	 *  oder aus I_AD_Role.COLUMNNAME_Allow_Info_Account
	 *                    .COLUMNNAME_Allow_Info_Asset
	 *                    .COLUMNNAME_Allow_Info_BPartner
	 *                    ... statisch
	 * @return
	 */
	public static boolean canAccessInfo(String infoWindowName) {
		boolean result=false;
		int roleid= Env.getAD_Role_ID(Env.getCtx());
		String sqlRolePermission="Select COUNT(AD_ROLE_ID) AS ROWCOUNT FROM AD_ROLE WHERE AD_ROLE_ID=" + roleid  
	                              + " AND " + ALLOWANCE.get(infoWindowName) + "='Y'"; 

		LOG.config(sqlRolePermission); 
		PreparedStatement prolestmt = null; 
		ResultSet rs = null;
		try 
		{ 
			prolestmt = DB.prepareStatement (sqlRolePermission, null); 
	 
			rs = prolestmt.executeQuery ();  
	 
			rs.next(); 
	 
			if (rs.getInt("ROWCOUNT")>0)
			{
				result=true;
			}
			else 
			{
				return false;
			}
		} 
		catch (Exception e) 
		{
				System.out.println(e); 
				LOG.log(Level.SEVERE, "(1)", e); 
		} 
		finally
		{
			DB.close(rs, prolestmt);
			rs = null; prolestmt = null;
		}
	
		return result; 
		
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
		if(e.getComponent() instanceof InfoPanel) {
			this.dataLoader.execute();
		}
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		LOG.warning(""+e.getComponent());
	}
}
