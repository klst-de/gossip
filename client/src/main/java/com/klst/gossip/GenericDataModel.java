package com.klst.gossip;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.table.DefaultTableModel;

import org.compiere.model.GridTab;
import org.compiere.util.DisplayType;

// TableModel bedeutet Java Swing Table, nicht DB Table!
public class GenericDataModel extends DefaultTableModel { // extends AbstractTableModel implements Serializable

	private static final long serialVersionUID = -8353798775903481429L;

	private static final Logger LOG = Logger.getLogger(GenericDataModel.class.getName());

//	protected List<Object[]> dataVector = new Vector<Object[]>();
//    protected Vector    dataVector; // so ist es in DefaultTableModel, <code>Vector</code> of <code>Vectors</code>

    private int windowNo;
    private GridTab gridTab;  // Tab Model - a combination of AD_Tab (the display attributes) and AD_Table information.

	//private GridFieldBridge[] fields = null; // oder noch besser:
	private GridFields fields;
	private int rowsToLoad = -1; // der Loader liefert es
	boolean m_virtual = false; // wie in GridTable TODO erklären
	
	// ctor
	public GenericDataModel(GridTab gridTab, int windowNo) {
		this.windowNo = windowNo;
		this.gridTab = gridTab;
		this.fields = new GridFields(this.gridTab.getFields());
	}

	public String toString() {
		return getClass().getName() +" windowNo="+windowNo + " gridTab:["+gridTab+"]" + " #fields="+fields.getColumnCount();		
	}
	
	// name wie in DefaultTableModel, dort: public Vector getDataVector()
	@Override
	public Vector<Object[]> getDataVector() {
		return (Vector<Object[]>) dataVector;
	}

    /*
     * (non-Javadoc)
     * @see javax.swing.table.TableModel#getRowCount()
     */
	@Override
	public int getRowCount() {
		return getDataVector().size();
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
		// in DefaultTableModel
//        Vector rowVector = (Vector)dataVector.elementAt(row);
//        return rowVector.elementAt(column);

		if(rowIndex >= getRowCount()) {
			return null;
//			return new Object();
		}
		if(columnIndex < getColumnCount()) {
			return getRow(rowIndex)[columnIndex];
		}
        return null;
	}
	
    public boolean isCellEditable(int rowIndex, int columnIndex) {
    	GridFieldBridge field = (GridFieldBridge)this.fields.getColumn(columnIndex);
    	final boolean checkContext = true;
    	boolean isEditable = field.isEditable(checkContext);
//    	GridField field = this.fields[columnIndex];
//    	boolean isEditable = field.isEditable(false); // checkContext
//    	if(field.isEditable()) {
//    		LOG.config(""+rowIndex+" "+field + "isEditable no context, checkContext:"+field.isEditable(true));
//    	} else {
//    		LOG.config(""+rowIndex+" "+field + "isNOTEditable no context");
//    	}
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
	
    Object[] getRow(int rowIndex) {
        return getDataVector().get(rowIndex);
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
    	GridFieldBridge field = (GridFieldBridge)this.fields.getColumn(columnIndex);
    	int displayType = field.getDisplayType();
    	if(logDisplayType.containsKey(field)) {
    		// schon geloggt
    	} else {
        	LOG.config(field + " Storage Class:"+DisplayType.getClass(displayType, true));
        	logDisplayType.put(field, displayType);
    	}
    	// Return Storage Class, (used for MiniTable) - ist aber nicht die DisplayClass
    	// die ist nämlich VEditor oder so!
    	return DisplayType.getClass(displayType, true); // true == nur für Boolean as CheckBox
    	// TODO 
    }
    // wg. LOG
    private Map<GridFieldBridge, Integer> logDisplayType = new Hashtable<GridFieldBridge, Integer>();
    // <<<

    @Override
    public String getColumnName(int columnIndex) {
    	GridFieldBridge field = (GridFieldBridge)this.fields.getColumn(columnIndex);
//    	LOG.config("column:"+columnIndex + " ColumnName/Identifier:"+field.getIdentifier() + " Header:"+field.getHeaderValue());
    	return field.getColumnName();
    }

    GridFieldBridge getFieldModel(int columnIndex) {
//		Object fieldModel = columnIdentifiers.get(columnIndex);
//		return(GridFieldBridge)fieldModel;
    	return (GridFieldBridge)this.fields.getColumn(columnIndex);
    }
    
    public Object getHeaderValue(int columnIndex) {
    	GridFieldBridge field = (GridFieldBridge)this.fields.getColumn(columnIndex);
    	return field.getHeaderValue();
    }
    
    // wird im Loader.process benötigt : bankTableModel.add(chunks); chunks == rows(der JTable)
    public void add(List<Object[]> chunks) {
        int first = getDataVector().size();
        int last = first + chunks.size() - 1;
        getDataVector().addAll(chunks);
        fireTableRowsInserted(first, last);
    }

    public void add(Object[] row) {
        int index = getDataVector().size();
        getDataVector().add(row);
        fireTableRowsInserted(index, index);
    }
    
	public String getName() {
		return this.gridTab.getName();
	}

	public String getTableName() {
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
		getDataVector().clear();
	}
    
}
