package com.klst.gossip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.swing.table.AbstractTableModel;

import org.compiere.model.MColumn;
import org.compiere.model.MField;
import org.compiere.model.MTab;
import org.compiere.model.MTable;
import org.compiere.util.Env;
import org.compiere.util.Trx;

// aka GridTable
//public GridTable(Properties ctx, int AD_Table_ID, String TableName, int WindowNo, int TabNo,
//		boolean withAccessControl)
//	{
//		this(ctx, AD_Table_ID, TableName, WindowNo, TabNo, withAccessControl, false);
//	}

// Table bedeutet Java Swing Table, nicht DB Table!
public class GenericTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -8353798775903481429L;

	private static final Logger LOG = Logger.getLogger(GenericTableModel.class.getName());

	// die Zeilen von TableModel
    private final List<Object[]> tableRows = new ArrayList<Object[]>();

	private int tab_ID; // hieraus kann man table_ID ermitteln, für Country (AD_Table - AD_Table_ID=170)
	private MTab mTab;
	private List<MField> fields;
	
	private int table_ID;
	private MTable mTable;
	private String dbTableName; // ist in mTable 
	private List<MColumn> columns;

	private Properties ctx;
	private String trxName;

	// ctor
	public GenericTableModel(int tab_ID) {
		this.tab_ID = tab_ID;
		this.table_ID = getTable_ID(tab_ID);
		this.mTable = MTable.get(Env.getCtx(), table_ID);
		LOG.config(mTab.toString() + " " + mTable.toString());
		
		this.columns = mTable.getColumnsAsList();
		this.fields = Arrays.asList(mTab.getFields(false, trxName)); // reload
		int firstField = 0;
		for (int f = 0; f < fields.size(); f++) {
			if(fields.get(f).getSeqNo()==0) {
				firstField++;
			} else {
				break;
			}
		}
		this.fields = this.fields.subList(firstField, fields.size());
	}

	private int getTable_ID(int tab_ID) {
		LOG.config("aus AD_Tab holen tab_ID:"+tab_ID);
		this.ctx = Env.getCtx();
		this.trxName =  Trx.createTrxName(GenericTableModel.class.getName()); 
		this.mTab = new MTab(ctx, tab_ID, trxName);
		int table_ID = mTab.getAD_Table_ID();
		return table_ID;
	}

    /*
     * (non-Javadoc)
     * @see javax.swing.table.TableModel#getRowCount()
     */
	@Override
	public int getRowCount() {
		return tableRows.size();
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		// es werden columns gelesen, aber fields dargestellt!
		return fields.size();
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(rowIndex >= getRowCount()) {
			return new Object();
		}
		if(columnIndex < getColumnCount()) {
			return getRow(rowIndex)[columnIndex];
		}
        return null;
	}
	
    public Object[] getRow(int rowIndex) {
        return tableRows.get(rowIndex);
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
     */
    @Override
    public Class<?> getColumnClass(int column) {
    	return getValueAt(0, column).getClass();
    }

    @Override
    public String getColumnName(int column) {
    	return fields.get(column).getName();
    }


    // wird im Loader.process benötigt : bankTableModel.add(chunks); chunks == rows(der JTable)
    public void add(List<Object[]> chunks) {
        int first = tableRows.size();
        int last = first + chunks.size() - 1;
        tableRows.addAll(chunks);
        fireTableRowsInserted(first, last);
    }

    public void add(Object[] row) {
        int index = tableRows.size();
        tableRows.add(row);
        fireTableRowsInserted(index, index);
    }
    
    public String getDbTableName() {
//    	LOG.warning("Table_ID:"+this.mTable.getAD_Table_ID() + " Name:"+this.mTable.getName()  + " TableName:"+this.mTable.getTableName());
    	return this.mTable.getTableName();
    }
    
    public List<MField> getColumns() {
    	return this.fields;
    }
    
}
