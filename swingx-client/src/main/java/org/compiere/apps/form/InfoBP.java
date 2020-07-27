package org.compiere.apps.form;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.compiere.model.GridField;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Location;
import org.compiere.model.MLocation;
import org.compiere.swing.CTabbedPane;
import org.compiere.util.DefaultContextProvider;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTaskPane;

import com.klst.gossip.GenericDataLoader;
import com.klst.gossip.GenericFormPanel;
import com.klst.gossip.MXTable;
import com.klst.gossip.wrapper.GridTableModel;
import com.klst.gossip.wrapper.TabModel;
import com.klst.gossip.wrapper.WindowModel;

/**
 *	subclasses Generic Form Panel
 *	
 *  The ContentPane in BorderLayout contains
 *  <br> at PAGE_START a selectionPanel
 *  <br> at CENTER     a mainPanel with scrollable miniTable
 *  <br> at PAGE_END   a statusBar
 *  
 *  <p> in subclass the mainPanel can contain additional miniTables, 
 *  expl InfoBP.mainPanel contains bpPanel + subPane with Contacts and Locations
 *	
 *  @author https://github.com/homebeaver
 */
public class InfoBP extends GenericFormPanel {

	private static final Logger LOG = Logger.getLogger(InfoBP.class.getName());
	
	private static int BPartnerInfo_AD_Window_ID=1000000;
	
	public InfoBP() {
		super(BPartnerInfo_AD_Window_ID);
//		super(BPartnerInfo_AD_Window_ID, true); // showWhere=true : shows defined where selection
	}

	private JXPanel bpPanel = new JXPanel(new BorderLayout()); // private JXPanel mainPanel = new JXPanel();
	private JXTaskPane subPane = new JXTaskPane();
	private CTabbedPane tabbedPane  = new CTabbedPane();
	
	// super.miniTable contains BPartner info is managed by superclass
	private MXTable contactTbl; // = new MXTable();
	private MXTable locationTbl; // = new MXTable(); // renamed address to location
	private GridTableModel contactTableModel; // renamed to contactTableModel
	private GridTableModel locationTableModel;
	
	@Override // dimension calculated for mainPanel, use 50% height for bpPanel
	protected void setPreferredSize(Dimension dimension) {
		Dimension dim = new Dimension(dimension.width, dimension.height/2);
		bpPanel.setPreferredSize(dim);
		subPane.setPreferredSize(dim); 
	}

	@Override // additional subPane for Contacts + Addresses
	protected void initMainPanel(JPanel mainPanel, Component miniTable) {
		bpPanel.add(new JScrollPane(miniTable), BorderLayout.CENTER);
		mainPanel.add(bpPanel, BorderLayout.CENTER);
		
		WindowModel gridWindow = super.getWindowModel();

		TabModel contactTab = gridWindow.getTab(1); // es gibt 3 Tabs : 0==>BP, 1==>Contact, 2==>Location
		contactTableModel = contactTab.getGridTableModel(); // getTableModel() macht boolean initTab(boolean synchron)
		TabModel locationTab = gridWindow.getTab(2);
		locationTableModel = locationTab.getGridTableModel();
		
		if(miniTable instanceof MXTable) {
			MXTable mxTable = (MXTable)miniTable;
			LOG.config("miniTable.ColumnCount="+mxTable.getColumnCount() + ", SelectedRow="+mxTable.getSelectedRow());
			for (int i = 0; i < mxTable.getColumnCount(); i++) {
				// getColumnExt(i) : The return value is null, if the column atposition columnIndex is not of type TableColumnExt.
				// mxTable.getColumnExt(i).getIdentifier() liefert Object GridField
//				LOG.config("i="+i + ":"+mxTable.getColumn(i) + "/"+mxTable.getColumnExt(i) + " - "+mxTable.getColumnExt(i).getIdentifier()); 
				Object identifier = mxTable.getColumnExt(i).getIdentifier();
				GridField gridField = ((GridField)identifier);
				LOG.config("i="+i + ": ColumnName/ColumnSQL="+gridField.getColumnName() + "/"+gridField.getColumnSQL(false) + " - "+identifier); 
				String columnName = gridField.getColumnName();
				if(I_C_BPartner.COLUMNNAME_C_BPartner_ID.equals(columnName)) {
					column_index_Record_ID = i;
				}
				if(I_C_BPartner.COLUMNNAME_C_BP_Group_ID.equals(columnName)) {
					column_index_C_BP_Group_ID = i;
				}
				// BPName ist virtuell und nicht in I_C_BPartner
				if("BPName".equals(columnName)) {
					column_index_BPNAME = i;
				}
			}
			// alle indizes sollten>=0 sein
			LOG.config("column_index_Record_ID="+column_index_Record_ID 
				+ ", column_index_C_BP_Group_ID="+column_index_C_BP_Group_ID
				+ ", column_index_BPNAME="+column_index_BPNAME);
		}
		
		LOG.config("contactTableModel:" +contactTableModel);  // expected MTable[AD_User,WindowNo=1,Tab=1]
		LOG.config("locationTableModel:"+locationTableModel); // expected MTable[C_BPartner_Location,WindowNo=1,Tab=2]
		setSelectWhereClause();
		contactTbl = MXTable.createXTable(contactTableModel);
		locationTbl = MXTable.createXTable(locationTableModel);
		tabbedPane.addTab(Msg.translate(Env.getCtx(), "Contact"), new JScrollPane(contactTbl));
		tabbedPane.addTab(Msg.translate(Env.getCtx(), "Location"), new JScrollPane(locationTbl));
		subPane.setLayout(new BorderLayout());
		subPane.setTitle(Msg.translate(Env.getCtx(), "ContactAndAddress"));

		subPane.add(tabbedPane, BorderLayout.CENTER);
		mainPanel.add(subPane, BorderLayout.PAGE_END);
		
		registerTableSelectionListener(contactTbl);
		
		LOG.config("addressTbl.ColumnCount="+locationTbl.getColumnCount() + ", SelectedRow="+locationTbl.getSelectedRow());
		for (int i = 0; i < locationTbl.getColumnCount(); i++) {
			String columnName = locationTbl.getColumnName(i);
			if(I_C_Location.COLUMNNAME_C_Location_ID.equals(columnName)) {
				column_index_C_Location_ID = i;
			}
		}	
		LOG.config("column_index_C_Location_ID="+column_index_C_Location_ID); 
		registerTableSelectionListener(locationTbl);
	}
	
	private final static boolean finalCall = false;
	private final static boolean isOnlyCurrentRows = false;
	private final static int onlyCurrentDays = 0;
	// returns bp valueAtName
	private Object setSelectWhereClause() {	
		setSelectWhereClause(contactTableModel);	
		return setSelectWhereClause(locationTableModel);
	}

	// returns bp valueAtName
	private Object setSelectWhereClause(GridTableModel gridTableModel) {
		int bpSelectedRow = super.miniTable.getSelectedRow();
		LOG.config("TableName="+gridTableModel.getTableName() + " WhereClause:"+gridTableModel.getSelectWhereClause() 
			+ " BP SelectedRow:"+bpSelectedRow
//			+ " BP LeadRowKey:"+super.miniTable.getLeadRowKey()
			);
		String newWhereClause = "1=2"; // default for bpSelectedRow<0 aka no bp selected
		Object valueAtName = null;
		if(bpSelectedRow>=0) {
			Object valueAt = miniTable.getValueAt(bpSelectedRow, column_index_Record_ID);
			valueAtName = miniTable.getValueAt(bpSelectedRow, column_index_BPNAME);
			LOG.config("col="+column_index_Record_ID + " valueAt:"+valueAt);
			newWhereClause = I_C_BPartner.COLUMNNAME_C_BPartner_ID+"="+valueAt;
		}
		gridTableModel.close(finalCall);
		gridTableModel.setSelectWhereClause(newWhereClause , isOnlyCurrentRows, onlyCurrentDays);
		LOG.config("WhereClause:"+gridTableModel.getSelectWhereClause());
		return valueAtName;
	}

	private int column_index_Record_ID = -1;
	private int column_index_C_BP_Group_ID = -1;
	private int column_index_BPNAME = -1;
	// Location
	private int column_index_C_Location_ID = -1;

	protected void registerTableSelectionListener(MXTable miniTable) {
		// miniTable ist contactTbl oder locationTbl
		LOG.config("TableSelectionListener for MXTable with "+miniTable.getColumnModel().getColumnCount()+" columns.");		
		miniTable.addMiniTableSelectionListener(event -> {
			Object source = event.getSource();
			if(source instanceof JTable) {
				JTable jTable = (JTable)source;
				ListSelectionModel columnSM = miniTable.getColumnModel().getSelectionModel();
				LOG.config("TableSelectionListener event.Source:"+(JTable)source 
					+ "\n SelectedRow="+miniTable.getSelectedRow()
					+ ", ID (erwartet ACTION_PERFORMED : 1001)="+event.getID()
					+ ", ActionCommand="+event.getActionCommand()
					+ ", SelectionMode="+miniTable.getSelectionMode()
					+ ", AnchorSelectionIndex="+columnSM.getAnchorSelectionIndex()
					+ ", LeadSelectionIndex="+columnSM.getLeadSelectionIndex()
					+ ", ColumnSelectionAllowed="+miniTable.getColumnModel().getColumnSelectionAllowed()
					);
				
				if(column_index_C_Location_ID==columnSM.getAnchorSelectionIndex()) {
					int selectedRow = miniTable.getSelectedRow();
					Object value = miniTable.getValueAt(selectedRow, column_index_C_Location_ID);
					if(value!=null) {					
						Integer key = (Integer) value;
						MLocation mLocation = new MLocation(Env.getCtx(), key.intValue(), null); // String trxName
						// Das Öffnen von Window kann ich nocht nciht implementieren, aber GOOGLE_MAPS
						LOG.config("MapsLocation:"+mLocation.getMapsLocation());
						Env.startBrowser(DefaultContextProvider.GOOGLE_MAPS_URL_PREFIX + mLocation.getMapsLocation());
					}
				} else {
					LOG.warning("AnchorSelectionIndex="+columnSM.getAnchorSelectionIndex() + " NOT (expected) "+column_index_C_Location_ID);
				}
			} else {
				LOG.config("source NOT JTable:"+source);		
			}
		});
	}
	
	@Override
	protected void registerTableSelectionListener() { // für Component miniTable, also BP
		MXTable miniTable = (MXTable)getTable(); // getTable() returns JTable, but instance is MXTable
		
//		LOG.config("TableSelectionListener registriert!!!!!");
		miniTable.addMiniTableSelectionListener(event -> {
			LOG.config("TableSelectionListener event:"+event);
			Object source = event.getSource();
			if(source instanceof JTable) {
				JTable jTable = (JTable)source;
				// Object source == MXTable miniTable
				ListSelectionModel columnSM = miniTable.getColumnModel().getSelectionModel();
				LOG.config("TableSelectionListener event.Source:"+(JTable)source 
					+ "\n SelectedRow="+miniTable.getSelectedRow()
					+ ", ID (erwartet ACTION_PERFORMED : 1001)="+event.getID()
					+ ", ActionCommand="+event.getActionCommand()
					+ ", SelectionMode="+miniTable.getSelectionMode()
					+ ", columnSM.AnchorSelectionIndex="+columnSM.getAnchorSelectionIndex()
					+ ", columnSM.LeadSelectionIndex="+columnSM.getLeadSelectionIndex()
					+ ", ColumnSelectionAllowed="+miniTable.getColumnModel().getColumnSelectionAllowed()
					);
				Object bpValueAtName = setSelectWhereClause(); // for contactTableModel and locationTableModel
				subPane.setTitle(Msg.translate(Env.getCtx(), "ContactAndAddress") + (bpValueAtName==null ? ":" : " for "+bpValueAtName));

				// reload data
				contactTableModel.setRowCount(0); // deleting-all-the-rows-in-a-jtable
				GenericDataLoader dataLoader = new GenericDataLoader(contactTableModel);
				dataLoader.execute();
				
				// oder kürzer:
				locationTableModel.setRowCount(0);
				new GenericDataLoader(locationTableModel).execute();
				
			} else {
				LOG.config("source NOT JTable:"+source);		
			}
		});
		
	}

}
