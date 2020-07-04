package org.compiere.apps.form;

import java.util.logging.Logger;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

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
public class WorkflowActivities extends GenericFormPanel {

	private static final Logger LOG = Logger.getLogger(WorkflowActivities.class.getName());
	
	private static int WF_ACTIVITIES_AD_Window_ID = 298;
	// TableName=AD_WF_Activity AD_Table_ID=644 / AD_Tab_ID=576 / AD_Window_ID=298
	
	public WorkflowActivities() {
		super(WF_ACTIVITIES_AD_Window_ID, true); // showWhere=true : shows default where selection
	}

	private int column_index_AD_Table_ID = -1;
	private int column_index_Record_ID = -1;
	private int column_index_IsSOTrx = -1;
	private int column_index_DocumentNo = -1;
	private int column_index_AD_User_ID = -1;
	protected void registerTableSelectionListener() throws Exception {
		MXTable miniTable = (MXTable)getTable(); // returns JTable, but instance is 
		LOG.config("ColumnCount="+miniTable.getColumnCount());
		for (int i = 0; i < miniTable.getColumnCount(); i++) {
			String columnName = miniTable.getColumnName(i);
			if ("AD_Table_ID".equals(columnName)) {
				column_index_AD_Table_ID = i;
			}
			if ("Record_ID".equals(columnName)) {
				column_index_Record_ID = i;
			}
			if ("DocumentNo".equals(columnName)) {
				column_index_DocumentNo = i;
			}
			if ("IsSOTrx".equals(columnName)) {
				column_index_IsSOTrx = i;
			}
			if ("AD_User_ID".equals(columnName)) {
				column_index_AD_User_ID = i;
			}
		}
		miniTable.addMiniTableSelectionListener(event -> {
			Object source = event.getSource();
			if(source instanceof JTable) {
				JTable jTable = (JTable)source;
				LOG.config("TableSelectionListener event.Source:"+(JTable)source);
				ListSelectionModel columnSM = jTable.getColumnModel().getSelectionModel();
				if(columnSM.getAnchorSelectionIndex()==column_index_AD_User_ID) {
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
