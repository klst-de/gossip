package com.klst.gossip;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.table.AbstractTableModel;

import org.compiere.model.GridField;
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
	private GridField[] fields = null;
	private int rowsToLoad = -1; // der Loader liefert es
	
	// ctor
	public GenericDataModel(GridTab gridTab, int windowNo) {
		this.windowNo = windowNo;
		this.gridTab = gridTab;
		this.fields = this.gridTab.getFields();
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
		return fields.length;
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
    	GridField field = this.fields[columnIndex];
    	boolean isEditable = field.isEditable(false); // checkContext
    	if(isEditable) {
    		LOG.config(""+rowIndex+" "+field + "isEditable no context, checkContext:"+field.isEditable(true));
    	} else {
    		LOG.config(""+rowIndex+" "+field + "isNOTEditable no context");
    		return isEditable; // false
    	}
    	return field.isEditable(true); // checkContext
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
    public Class<?> getColumnClass(int column) {
    	GridField field = this.fields[column];
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
    private Map<GridField, Integer> logDisplayType = new Hashtable<GridField, Integer>();
    // <<<

    @Override
    public String getColumnName(int column) {
    	return fields[column].getColumnName();
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
	
    public GridField[] getColumns() {
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
