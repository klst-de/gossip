package com.klst.gossip.wrapper;

import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.table.DefaultTableModel;

import org.compiere.model.GridField;
import org.compiere.model.GridTable;
import org.jdesktop.swingx.table.TableColumnModelExt;

import com.klst.gossip.FieldsModelExt;

// wrapper für GridTable, GridTable extends AbstractTableModel
// GTM === GenericDataModel
/* in super (swing)DefaultTableModel gibt es zwei member:

    protected Vector    dataVector;
    protected Vector    columnIdentifiers; // The <code>Vector</code> of column identifiers.
    
Constructors:
	public DefaultTableModel()
	public DefaultTableModel(int rowCount, int columnCount)
	public DefaultTableModel(Vector columnNames, int rowCount)
	public DefaultTableModel(Object[] columnNames, int rowCount)
	public DefaultTableModel(Vector data, Vector columnNames)
	public DefaultTableModel(Object[][] data, Object[] columnNames)

 */
public class GridTableModel extends DefaultTableModel { // extends AbstractTableModel implements Serializable

	private static final long serialVersionUID = -1195711198935289642L;
	
	private static final Logger LOG = Logger.getLogger(GridTableModel.class.getName());
	
	private int rowsToLoad = -1; // der Loader liefert es per setter
	private String 		        m_tableName = "";
	public int getRowsToLoad() {
		return this.rowsToLoad;
	}

	public void setRowsToLoad(int rowsToLoad) {
		this.rowsToLoad = rowsToLoad;
	}

	public String getTableName() {
		return m_tableName;
	}
	
    public TableColumnModelExt getColumnModelExt(int columnIndex) {
    	return (TableColumnModelExt)fields.getColumnExt(columnIndex);
    }

    // wird im Loader.process benötigt : bankTableModel.add(chunks); chunks == rows(der JTable)
    public void add(List<Object[]> chunks) {
        int first = getDataVector().size();
        int last = first + chunks.size() - 1;
        LOG.config("DataVector().size() is "+first + ", chunks.size()="+chunks.size());
        //getDataVector().addAll(chunks);
        for (int i = 0; i < chunks.size(); i++) {
        	Object[] rowData = chunks.get(i);
//        	Vector<Vector> rows = new Vector<Vector>(chunks.size());
        	Vector<Object> row = new Vector<Object>(rowData.length);
        	for (int column = 0; column < rowData.length; column++) {
        		Object o = rowData[column];
        		row.add(rowData[column]);
        		LOG.config("Object:"+o + " R/C="+first+i+"/"+column);
        		//setValueAt(rowData[column], first+i, column);      		
        	}
        	getDataVector().add(row);
        }
//        chunks.get(index)
//        chunks.forEach((Object[] row) -> {
//        	add(row); // TODO
//        });
        fireTableRowsInserted(first, last); // implementiert in AbstractTableModel
    }
    public void add(Object[] rowData) {
    	LOG.config("DataVector().size() is "+getDataVector().size() + ", row whith #cols:"+rowData.length);
        int row = getDataVector().size();
        //getDataVector().add(row);
        for (int column = 0; column < rowData.length; column++) {
        	Object aValue = rowData[column];
        	setValueAt(aValue, row, column);
        }
        fireTableRowsInserted(row, row);
    }

    public void fireTableRowsInserted(int firstRow, int lastRow) {
    	LOG.config("firstRow="+firstRow + ", lastRow="+lastRow);
    	super.fireTableRowsInserted(firstRow, lastRow);
    }

//    public void setValueAt(Object aValue, int row, int column) {
//    	LOG.config("Object:"+aValue + ", row="+ row + ",column="+column);
//    	LOG.config("dataVector.size:"+((Vector)dataVector).size());
//    	//super.setValueAt(aValue, row, column);
////        Vector rowVector = (Vector)dataVector.elementAt(row);
////        rowVector.setElementAt(aValue, column);
////        fireTableCellUpdated(row, column);
//    	Vector<Vector> rows = ((Vector)dataVector);
//    	Vector<Object> rowCells = ((Vector)dataVector);
//    }
    public Object getValueAt(int row, int column) {
    	Object o = super.getValueAt(row, column);
    	LOG.config("row="+row + ", column="+column + ":"+o);
       return o;
    }
    
    

//	GridTable gridTable;
	TableColumnModelExt fields; // GridFields extends DefaultTableColumnModelExt implements TableColumnModelExt
	// UI: TableColumnExt extends TableColumn
	
/* interface TableColumnModelExt methods: // TableColumnModelExt extends TableColumnModel

    public int getColumnCount(boolean includeHidden);
    public List<TableColumn> getColumns(boolean includeHidden);
    public TableColumnExt getColumnExt(Object identifier);
    public TableColumnExt getColumnExt(int columnIndex);
    public void addColumnModelListener(TableColumnModelListener x);

 */
//	public GridTableModel(Properties ctx, int AD_Table_ID, String TableName, int WindowNo, int TabNo, boolean withAccessControl, boolean virtual) {
//		
//	}
//	in super:
//	@SuppressWarnings("rawtypes")
//	protected static Vector convertToVector(Object[] anArray) {
//		return null;
//	}
	// tatsächlich Vector<TableColumnModelExt>
//	protected static Vector<Object> convertToTableColumnModelExt(Object[] anArray) {
//		DefaultTableColumnModelExt dtcme;
//        if (anArray == null) {
//            return null;
//        }
//        Vector<Object> v = new Vector<Object>(anArray.length);
//        for(int c = 0; c < anArray.length; c++) {
//        	Object o = anArray[c];
//        	if(o instanceof GridField) {
//        		GridField gridField = (GridField)o;
////        		TableColumnExt aColumn = new TableColumnExt(gridField.g);    		
////        		dtcme.addColumn(aColumn);
//        	} else {
//                v.addElement(o);
//        	}
//        }
//        return v;
//	}
	public GridTableModel(GridTable gridTable) {
// ich brauche gridTable, um die final Methoden setValueAt , ... dranzukommen
		// super DefaultTableModel(Object[] columnNames, int rowCount)
		// super(gridTable.getFields(), 0); // GridField[] gridTable.getFields()
		// super(int rowCount, int columnCount)
		super(0, gridTable.getFields().length); // (int rowCount, int columnCount)
		m_tableName = gridTable.getTableName();
		this.fields = new FieldsModelExt(gridTable);
		LOG.config("m_tableName="+m_tableName + " gridTable.getFields().length="+gridTable.getFields().length +" TableColumnModelExt.ColumnCount:"+fields.getColumnCount()+"/"+fields.getColumnCount(true)); // includeHidden
		super.setColumnIdentifiers(gridTable.getFields()); // array of column identifiers
/* Standard Fields werden erst in GridTab.loadFields "nachinitialisiert" und sind daher in gridTable.getFields() nicht dabei



 */
		for(int c = 0; c < getColumnCount(); c++) {
			LOG.config("ColumnName["+c+"]="+super.getColumnName(c));
		}
		LOG.config("ctor fertig, ColumnCount="+super.getColumnCount() + " " + gridTable);		
	}

	// stubs:
	private String m_whereClause;
	private String m_orderClause;
	public boolean setSelectWhereClause(String newWhereClause, boolean onlyCurrentRows, int onlyCurrentDays) {
		LOG.warning("STUB newWhereClause="+newWhereClause);
		m_whereClause = newWhereClause;
		// TODO
		return false;	
	}
	public String getSelectWhereClause() {
		return m_whereClause;
	}
	public String getOrderClause() {
		return m_orderClause;
	}
	
	public TableColumnModelExt getFields() {
		return fields;
	}
	public void close (boolean finalCall) {
		LOG.warning("nur Kompatibilität zu GridTable");
	}
// ------------------------------- stubs TODO
	
//    /**
//     * {@inheritDoc}
//     * 
//     */
//	@Override // implemeted in DefaultTableModel
//    public void setValueAt(Object aValue, int row, int column) {
//        Vector rowVector = (Vector)dataVector.elementAt(row);
//        rowVector.setElementAt(aValue, column);
//        fireTableCellUpdated(row, column); // macht AbstractTableModel.fireTableChanged(new TableModelEvent(this, row, row, column));
//    }

	public GridField getGridField(int column) {
		Object id = null;
        if (column < columnIdentifiers.size() && (column >= 0)) {
            id = columnIdentifiers.elementAt(column);
        }
		if(id == null) {
			LOG.warning("id NOT GridField:"+id);
		}
		return (GridField)id;
	}
	public int getDisplayType(int column) {
		Object id = null;
        if (column < columnIdentifiers.size() && (column >= 0)) {
            id = columnIdentifiers.elementAt(column);
        }
		if(id == null) return -1;
		if(id instanceof GridField) return ((GridField)id).getDisplayType();
		LOG.warning("id NOT GridField !!!!!!!!!!!! :"+id);
		return -2;		
	}
	/* wie in GridField
	 *  Get Column Name or SQL .. with/without AS
	 *  @param withAS include AS ColumnName for virtual columns in select statements
	 *  @return column name
	 */
	public String getColumnSQL(int column, boolean withAS) {
		Object id = null;
        if (column < columnIdentifiers.size() && (column >= 0)) {
            id = columnIdentifiers.elementAt(column);
        }
		if(id == null) super.getColumnName(column);
		if(id instanceof GridField) return ((GridField)id).getColumnSQL(withAS);
		LOG.warning("id NOT GridField !!!!!!!!!!!! :"+id);
		return "!!!!!";		
	}
    /**
     * {@inheritDoc}
     * 
     */
	@Override // implemeted in DefaultTableModel
    public String getColumnName(int column) {
		return getColumnSQL(column, false);
//		Object id = null;
//        if (column < columnIdentifiers.size() && (column >= 0)) {
//            id = columnIdentifiers.elementAt(column);
//        }
////		TableColumnExt tce = this.fields.getColumnExt(column); // alle
////		id = tce.getIdentifier();
//		if(id == null) super.getColumnName(column);
//		if(id instanceof GridField) return ((GridField)id).getColumnName();
//		LOG.warning("id NOT GridField !!!!!!!!!!!! :"+id);
//		return "!!!!!";
////        return (id == null) ? super.getColumnName(column)
////                : ((GridField)id).getColumnName();
////                : ((GridField)id).getHeader();
    }

//	public boolean open (int maxRows) {
//		return gridTable.open(maxRows);
//	}
}
