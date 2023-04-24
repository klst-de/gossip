package io.homebeaver.gossip;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.table.DefaultTableModel;

import org.compiere.model.GridFieldVO;
import org.compiere.model.GridTab;
import org.compiere.util.DisplayType;
import org.jdesktop.swingx.table.DefaultTableColumnModelExt;
import org.jdesktop.swingx.table.TableColumnExt;
import org.jdesktop.swingx.table.TableColumnModelExt;

import com.klst.gossip.GridFieldBridge;
import com.klst.gossip.GridFields;

// TableModel bedeutet Java Swing Table, nicht DB Table!
// in AD: (base)GridTable extends AbstractTableModel mit Loader
public class GenericDataModel extends DefaultTableModel { // extends AbstractTableModel implements Serializable
// subclassed by InfoDataModel
	
	private static final long serialVersionUID = -8353798775903481429L;

	private static final Logger LOG = Logger.getLogger(GenericDataModel.class.getName());

//	protected List<Object[]> dataVector = new Vector<Object[]>();
//    protected Vector    dataVector; // so ist es in DefaultTableModel, <code>Vector</code> of <code>Vectors</code>

    private int windowNo;
    private GridTab gridTab;  // Tab Model - a combination of AD_Tab (the display attributes) and AD_Table information.
    private String name
    , tableName; // gridTab.get_TableName() oder InfoProduct : String s_sqlFrom
    
	//private GridFieldBridge[] fields = null; // oder noch besser:
    private DefaultTableColumnModelExt fields; // GridFields extends DefaultTableColumnModelExt implements TableColumnModelExt
//    TableColumnModelExt getTableColumnModelExt() { // existiert berets: public TableColumnModelExt getColumns()
//    	return fields; // TODO fields umbenennen in columnModel
//    }
	private int rowsToLoad = -1; // der Loader liefert es
	boolean m_virtual = false; // wie in GridTable TODO erklären
	
	// ctor
	public GenericDataModel(GridTab gridTab, int windowNo) {
		this.windowNo = windowNo;
		this.gridTab = gridTab;
		if(this.gridTab==null) {  // das kann null sein in Subclass InfoDataModel
			this.fields = new GridFields();
		} else {
			this.fields = new GridFields(this.gridTab.getFields());
			this.setName(gridTab.getName());
			this.setTableName(gridTab.get_TableName());
			LOG.warning("gridTab.Name="+name + " gridTab.TableName="+tableName + " fields:"+fields);
		}
	}

	public String toString() {
		return getClass().getName() +" windowNo="+windowNo + " gridTab:["+gridTab+"]" + " #fields="+fields.getColumnCount();		
	}
	
	void addColumn(GridFieldBridge gfb) {
		((GridFields)fields).addColumn(gfb);
	}
	
	// name wie in DefaultTableModel, dort: public Vector<Vector> getDataVector() ( return dataVector; }
	@Override // DefaultTableModel
	public Vector<Vector> getDataVector() {
		LOG.config("dataVector.size="+dataVector.size() + " " + (dataVector.isEmpty() ? "empty" : dataVector.get(0)));
		return (Vector<Vector>) dataVector;
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
	public Object getValueAt(int row, int column) {
		// in DefaultTableModel
//        Vector<Object> rowVector = dataVector.elementAt(row);
//        return rowVector.elementAt(column);
		if(row >= getRowCount()) {
			return null;
//			return new Object();
		}
		if(column < getColumnCount()) {
			Vector<Vector> dv = this.getDataVector();
			Vector v = dv.get(column);
			LOG.config("TEST ret:"+getRow(row)[column] + "================ dv["+column+"]:"+v.get(row));
			return getRow(row)[column];
		}
        return null;
	}
	
	@Override // DefaultTableModel liefert immer true
    public boolean isCellEditable(int row, int column) {
		if(this.fields instanceof GridFields) {
	    	GridFieldBridge field = (GridFieldBridge)fields.getColumn(column);
	    	final boolean checkContext = true;
	    	boolean isEditable = field.isEditable(checkContext);
	    	return isEditable;
		}
		return super.isCellEditable(row, column);
    }

    // TODO das muss in Tab impementiert werden!
    // ist in JTable und auch in VTable extends CTable extends JTable
    // aber wir leiten von AbstractTableModel implements TableModel ab
//	public boolean editCellAt(int row, int column, EventObject e) {
//		return false;
//		
//	}
	
	@Override // if data model is editable:
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// aus super:
//        Vector rowVector = (Vector)dataVector.elementAt(row); // hier exception, weil noch keine editoren implementiert sind
//        rowVector.setElementAt(aValue, column);
//        fireTableCellUpdated(row, column);
/*

Exception in thread "AWT-EventQueue-0" java.lang.ClassCastException: [Ljava.lang.Object; cannot be cast to java.util.Vector
	at javax.swing.table.DefaultTableModel.setValueAt(DefaultTableModel.java:664)
	at javax.swing.JTable.setValueAt(JTable.java:2744)
	at org.jdesktop.swingx.JXTable.setValueAt(JXTable.java:1473)
	at javax.swing.JTable.editingStopped(JTable.java:4729)
	at javax.swing.AbstractCellEditor.fireEditingStopped(AbstractCellEditor.java:141)
	at javax.swing.DefaultCellEditor$EditorDelegate.stopCellEditing(DefaultCellEditor.java:368)
	at javax.swing.DefaultCellEditor.stopCellEditing(DefaultCellEditor.java:233)
	at javax.swing.DefaultCellEditor$EditorDelegate.actionPerformed(DefaultCellEditor.java:385)
	at javax.swing.AbstractButton.fireActionPerformed(AbstractButton.java:2022)
	at javax.swing.AbstractButton$Handler.actionPerformed(AbstractButton.java:2348)
	at javax.swing.DefaultButtonModel.fireActionPerformed(DefaultButtonModel.java:402)
	at javax.swing.JToggleButton$ToggleButtonModel.setPressed(JToggleButton.java:308)
	at javax.swing.plaf.basic.BasicButtonListener.mouseReleased(BasicButtonListener.java:252)
	at java.awt.Component.processMouseEvent(Component.java:6539)
	at javax.swing.JComponent.processMouseEvent(JComponent.java:3324)
	at java.awt.Component.processEvent(Component.java:6304)
	at java.awt.Container.processEvent(Container.java:2239)
	at java.awt.Component.dispatchEventImpl(Component.java:4889)
	at java.awt.Container.dispatchEventImpl(Container.java:2297)
	at java.awt.Component.dispatchEvent(Component.java:4711)
	at javax.swing.plaf.basic.BasicTableUI$Handler.repostEvent(BasicTableUI.java:948)
	at javax.swing.plaf.basic.BasicTableUI$Handler.mouseReleased(BasicTableUI.java:1164)
	at java.awt.AWTEventMulticaster.mouseReleased(AWTEventMulticaster.java:290)
	at java.awt.AWTEventMulticaster.mouseReleased(AWTEventMulticaster.java:289)
	at java.awt.Component.processMouseEvent(Component.java:6539)
	at javax.swing.JComponent.processMouseEvent(JComponent.java:3324)
	at java.awt.Component.processEvent(Component.java:6304)
	at java.awt.Container.processEvent(Container.java:2239)
	at java.awt.Component.dispatchEventImpl(Component.java:4889)
	at java.awt.Container.dispatchEventImpl(Container.java:2297)
	at java.awt.Component.dispatchEvent(Component.java:4711)
	at java.awt.LightweightDispatcher.retargetMouseEvent(Container.java:4904)
	at java.awt.LightweightDispatcher.processMouseEvent(Container.java:4535)
	at java.awt.LightweightDispatcher.dispatchEvent(Container.java:4476)
	at java.awt.Container.dispatchEventImpl(Container.java:2283)
	at java.awt.Window.dispatchEventImpl(Window.java:2746)
	at java.awt.Component.dispatchEvent(Component.java:4711)
	at java.awt.EventQueue.dispatchEventImpl(EventQueue.java:760)
	at java.awt.EventQueue.access$500(EventQueue.java:97)
	at java.awt.EventQueue$3.run(EventQueue.java:709)
	at java.awt.EventQueue$3.run(EventQueue.java:703)
	at java.security.AccessController.doPrivileged(Native Method)
	at java.security.ProtectionDomain$JavaSecurityAccessImpl.doIntersectionPrivilege(ProtectionDomain.java:74)
	at java.security.ProtectionDomain$JavaSecurityAccessImpl.doIntersectionPrivilege(ProtectionDomain.java:84)
	at java.awt.EventQueue$4.run(EventQueue.java:733)
	at java.awt.EventQueue$4.run(EventQueue.java:731)
	at java.security.AccessController.doPrivileged(Native Method)
	at java.security.ProtectionDomain$JavaSecurityAccessImpl.doIntersectionPrivilege(ProtectionDomain.java:74)
	at java.awt.EventQueue.dispatchEvent(EventQueue.java:730)
	at java.awt.EventDispatchThread.pumpOneEventForFilters(EventDispatchThread.java:205)
	at java.awt.EventDispatchThread.pumpEventsForFilter(EventDispatchThread.java:116)
	at java.awt.EventDispatchThread.pumpEventsForHierarchy(EventDispatchThread.java:105)
	at java.awt.EventDispatchThread.pumpEvents(EventDispatchThread.java:101)
	at java.awt.EventDispatchThread.pumpEvents(EventDispatchThread.java:93)
	at java.awt.EventDispatchThread.run(EventDispatchThread.java:82)


 */
		LOG.warning("TODO not implemented row/colum "+rowIndex+"/"+columnIndex + " Object value="+aValue); //TODO
//		if(this.fields instanceof GridFields) {
//	    	GridFieldBridge field = (GridFieldBridge)fields.getColumn(columnIndex);
//	    	field.setValue(aValue, false); // inserting==false
//		}
//		//super.setValueAt(aValue, rowIndex, columnIndex);
	}
	
    Object[] getRow(int rowIndex) {
    	Object[] a = new Object[getRowCount()];
    	getDataVector().toArray(a);
        return a;
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
    	LOG.config(""+columnIndex);
		if(this.fields instanceof GridFields) {
			GridFieldBridge field = (GridFieldBridge)fields.getColumn(columnIndex);
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
		}
		return super.getColumnClass(columnIndex);
    }
    // wg. LOG
    private Map<GridFieldBridge, Integer> logDisplayType = new Hashtable<GridFieldBridge, Integer>();
    // <<<

    @Override
    public String getColumnName(int columnIndex) {
    	GridFieldBridge field = getFieldModel(columnIndex);
//    	LOG.config("column:"+columnIndex + " ColumnName/Identifier:"+field.getIdentifier() + " Header:"+field.getHeaderValue());
    	return field.getColumnName();
    }

    // getFieldModel(int columnIndex).seqno ist nicht zu bekommen, aber die List Reihenfolge entspricht seqno
    List<GridFieldVO> getGridFieldVOList() {
    	return gridTab.getM_vo().getFields(); 	
    }
    
    TableColumnExt getColumnExt(int columnIndex) {
    	return (GridFieldBridge)this.fields.getColumn(columnIndex);
    }
    @Deprecated
    GridFieldBridge getFieldModel(int columnIndex) { // TODO raus, dafür getColumnExt
    	return (GridFieldBridge)this.fields.getColumn(columnIndex);
    }
    
    public Object getHeaderValue(int columnIndex) {
    	GridFieldBridge field = (GridFieldBridge)this.fields.getColumn(columnIndex);
    	return field.getHeaderValue();
    }
    
    // wird im Loader.process benötigt : bankTableModel.add(chunks); chunks == rows(der JTable)
//    public void add(List<Object[]> chunks) {
//        int first = getDataVector().size();
//        int last = first + chunks.size() - 1;
//        getDataVector().addAll(chunks);
//        fireTableRowsInserted(first, last);
//    }
//
//    public void add(Object[] row) {
//        int index = getDataVector().size();
//        getDataVector().add(row);
//        fireTableRowsInserted(index, index);
//    }
    
	void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
//		return this.gridTab.getName();
	}

	void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableName() {
		return this.tableName;
//		return this.gridTab.get_TableName();
	}

	public int getWindowNo() {
		return this.windowNo;
	}

	public TableColumnModelExt getColumns() { // ===> this.columnModel
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
