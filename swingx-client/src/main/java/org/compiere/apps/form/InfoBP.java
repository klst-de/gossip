package org.compiere.apps.form;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.compiere.model.GridTab;
import org.compiere.model.GridTable;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.WindowModel;
import org.compiere.swing.CTabbedPane;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTaskPane;

import com.klst.gossip.GenericFormPanel;
import com.klst.gossip.MXTable;

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
//		super(BPartnerInfo_AD_Window_ID, true); // showWhere=true : shows default where selection
	}

	private JXPanel bpPanel = new JXPanel(new BorderLayout()); // private JXPanel mainPanel = new JXPanel();
	private JXTaskPane subPane = new JXTaskPane();
	private CTabbedPane tabbedPane  = new CTabbedPane();
	
	// super.miniTable contains BPartner info is managed by superclass
	private MXTable contactTbl; // = new MXTable();
	private MXTable addressTbl; // = new MXTable(); // TODO rename to Locations
	private GridTable contactTableModel; // renamed to contactTableModel
	private GridTable locationTableModel;
	
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
		
		WindowModel gridWindow = super.getGridWindow();
		assert(gridWindow.getTabCount()==2);
		GridTab contactTab = gridWindow.getTab(1); // es gibt 3 Tabs : 0==>BP, 1==>Contact, 2==>Location
		contactTableModel = contactTab.getTableModel(); // getTableModel() macht boolean initTab(boolean synchron)
		GridTab locationTab = gridWindow.getTab(2);
		locationTableModel = locationTab.getTableModel();
		
		LOG.config("contactTableModel:"+contactTableModel);
		LOG.config("locationTableModel:"+locationTableModel);
		setSelectWhereClause();
		contactTbl = MXTable.createXTable(contactTableModel);
		addressTbl = MXTable.createXTable(locationTableModel);
		tabbedPane.addTab(Msg.translate(Env.getCtx(), "Contact"), new JScrollPane(contactTbl));
		tabbedPane.addTab(Msg.translate(Env.getCtx(), "Location"), new JScrollPane(addressTbl));
		subPane.setLayout(new BorderLayout());
		subPane.setTitle(Msg.translate(Env.getCtx(), "ContactAndAddress"));
		//subPanel.setVisible(true);
		//subPanel.setCollapsed(false);
		subPane.add(tabbedPane, BorderLayout.CENTER);
		mainPanel.add(subPane, BorderLayout.PAGE_END);
	}
	
	private final static boolean finalCall = false;
	private final static boolean isOnlyCurrentRows = false;
	private final static int onlyCurrentDays = 0;
	private Object setSelectWhereClause() {	
		setSelectWhereClause(contactTableModel);	
		return setSelectWhereClause(locationTableModel);
	}

	// returns bp valueAtName
	private Object setSelectWhereClause(GridTable gridTableModel) {
		int bpSelectedRow = super.miniTable.getSelectedRow();
		LOG.config("TableName="+gridTableModel.getTableName() + " WhereClause:"+gridTableModel.getSelectWhereClause() 
			+ " BP SelectedRow:"+bpSelectedRow
//			+ " BP LeadRowKey:"+super.miniTable.getLeadRowKey()
			);
		String newWhereClause = "1=2"; // default for bpSelectedRow<0 aka no bp selected
		Object valueAtName = null;
		if(bpSelectedRow>=0) {
			Object valueAt = miniTable.getValueAt(bpSelectedRow, column_index_Record_ID);
			valueAtName = miniTable.getValueAt(bpSelectedRow, column_index_NAME);
			LOG.config("col="+column_index_Record_ID + " valueAt:"+valueAt);
			newWhereClause = I_C_BPartner.COLUMNNAME_C_BPartner_ID+"="+valueAt;
		}
		gridTableModel.close(finalCall);
		gridTableModel.setSelectWhereClause(newWhereClause , isOnlyCurrentRows, onlyCurrentDays);
		return valueAtName;
	}

	private int column_index_Record_ID = -1;
	private int column_index_C_BP_Group_ID = -1;
	private int column_index_NAME = -1;
	// Location
	private int column_index_C_Location_ID = 10;

	protected void registerTableSelectionListener() throws Exception {
		MXTable miniTable = (MXTable)getTable(); // returns JTable, but instance is 
		LOG.config("ColumnCount="+miniTable.getColumnCount());
		for (int i = 0; i < miniTable.getColumnCount(); i++) {
			String columnName = miniTable.getColumnName(i);
			if(I_C_BPartner.COLUMNNAME_C_BPartner_ID.equals(columnName)) {
				column_index_Record_ID = i;
			}
			if(I_C_BPartner.COLUMNNAME_C_BP_Group_ID.equals(columnName)) {
				column_index_C_BP_Group_ID = i;
			}
			if(I_C_BPartner.COLUMNNAME_Name.equals(columnName)) {
				column_index_NAME = i;
			}
		}
		
		miniTable.addMiniTableSelectionListener(event -> {
			Object source = event.getSource();
			if(source instanceof JTable) {
				JTable jTable = (JTable)source;
				LOG.config("TableSelectionListener event.Source:"+(JTable)source);
				ListSelectionModel columnSM = jTable.getColumnModel().getSelectionModel();
				if(columnSM.getAnchorSelectionIndex()==column_index_Record_ID) {
					// TODO
				} else {
				}
					
			} else {
				LOG.config("source NOT JTable:"+source);		
			}
		});
		
//		miniTable.addMiniTableSelectionListener(event -> {
//			Object source = event.getSource();
//			if(source instanceof CTable) {
////				log.config("TableSelectionListener event.Source:"+(CTable)source);
//				MiniTable miniTable = (MiniTable)source;
//				ListSelectionModel columnSM = miniTable.getColumnModel().getSelectionModel();
////				log.config("columnSM.getAnchorSelectionIndex():"+columnSM.getAnchorSelectionIndex());
//				if(columnSM.getAnchorSelectionIndex()==column_index_AD_User_ID) {
//					// zoom to AD_User_ID
//					zoom(I_AD_User.Table_ID, miniTable, column_index_AD_User_ID );
//				} else if(columnSM.getAnchorSelectionIndex()==column_index_AD_Table_ID) {
//					// zoom to AD_Table_ID.Record_ID when klick on AD_Table_ID
//					columnSM.setAnchorSelectionIndex(column_index_Record_ID);
//					columnSM.setLeadSelectionIndex(column_index_Record_ID);
//					zoom(miniTable, column_index_AD_Table_ID, column_index_Record_ID);
//				} else {
//					// zoom to AD_Table_ID.Record_ID when klick on Record_ID
//					zoom(miniTable, column_index_AD_Table_ID, column_index_Record_ID);
//				}
//
//			} else {
//				log.config("source NOT CTable:"+source);
//			}
//		});
	}

}
