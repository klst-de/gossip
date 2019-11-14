package com.klst.gossip;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.table.AbstractTableModel;

import org.compiere.model.GridTab;
import org.compiere.util.DisplayType;

// TableModel bedeutet Java Swing Table, nicht DB Table!
public class GenericDataModel extends AbstractTableModel {

	private static final long serialVersionUID = -8353798775903481429L;

	private static final Logger LOG = Logger.getLogger(GenericDataModel.class.getName());

	// die Zeilen von TableModel
    private final List<Object[]> tableRows = new ArrayList<Object[]>();

    private int windowNo;
    private GridTab gridTab;
	//private GridFieldBridge[] fields = null; // oder noch besser:
	private GridFields fields;
	private int rowsToLoad = -1; // der Loader liefert es
	
	// ctor
	public GenericDataModel(GridTab gridTab, int windowNo) {
		this.windowNo = windowNo;
		this.gridTab = gridTab;
		this.fields = new GridFields(this.gridTab.getFields());
	}

	public String toString() {
		return getClass().getName() +" windowNo "+windowNo + " gridTab:["+gridTab+"]";		
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
		return fields.getColumnCount(true); // includeHidden
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
	
	// wird gerufen wenn celle angeckickt
    public boolean isCellEditable(int rowIndex, int columnIndex) {
    	GridFieldBridge field = (GridFieldBridge)this.fields.getColumn(columnIndex);
    	boolean isEditable = field.isEditable();
//    	GridField field = this.fields[columnIndex];
//    	boolean isEditable = field.isEditable(false); // checkContext
    	if(field.isEditable()) {
    		LOG.config(""+rowIndex+" "+field + "isEditable no context, checkContext:"+field.isEditable(true));
    	} else {
    		LOG.config(""+rowIndex+" "+field + "isNOTEditable no context");
    	}
    	return isEditable;
    }

    // TODO das muss in Tab impementiert werden!
    // ist in JTable und auch in VTable extends CTable extends JTable
    // aber wir leiten von AbstractTableModel implements TableModel ab
//	public boolean editCellAt(int row, int column, EventObject e) {
//		return false;
//		
//	}
	
	// if data model is editable:
	// TODO public void setValueAt(Object aValue, int rowIndex, int columnIndex);
	
    public Object[] getRow(int rowIndex) {
        return tableRows.get(rowIndex);
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
    	GridFieldBridge field = (GridFieldBridge)this.fields.getColumn(columnIndex);
//    	GridField field = this.fields[column];
    	int displayType = field.getDisplayType();
    	if(logDisplayType.containsKey(field)) {
    		// schon geloggt
    	} else {
        	LOG.config(field + " displayType:"+displayType);
        	logDisplayType.put(field, displayType);
    	}
    	// Return Storage Class - ist aber nicht die DisplayClass
    	// die ist nämlich VEditor oder so!
    	return DisplayType.getClass(displayType, true); // true == Boolean as CheckBox TODO, die anderen displayType
    	// TODO 
    }
    // wg. LOG
    private Map<GridFieldBridge, Integer> logDisplayType = new Hashtable<GridFieldBridge, Integer>();
    // <<<

    @Override
    public String getColumnName(int columnIndex) {
    	GridFieldBridge field = (GridFieldBridge)this.fields.getColumn(columnIndex);
    	LOG.config("column:"+columnIndex + " ColumnName/Identifier:"+field.getIdentifier() + " Header:"+field.getHeaderValue());
    	return field.getColumnName();
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
    
    public String getName() {
    	return this.gridTab.getName();
    }
    
    public String getDbTableName() {
    	return this.gridTab.get_TableName();
    }
    
	public int getWindowNo() {
		return this.windowNo;
	}
	
    public GridFields getColumns() { // ===> this.columnModel
    	return this.fields;
    }
    
    public int getRowsToLoad() {
    	return this.rowsToLoad;
    }
    
    public void setRowsToLoad(int rowsToLoad) {
    	this.rowsToLoad = rowsToLoad;
    }
    
    public void clear() {
    	this.tableRows.clear();
    }
    
}
