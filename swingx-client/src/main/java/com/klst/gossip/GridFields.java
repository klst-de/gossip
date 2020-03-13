package com.klst.gossip;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.event.TableColumnModelListener;
import javax.swing.table.TableColumn;

import org.compiere.model.GridField;
import org.jdesktop.swingx.table.DefaultTableColumnModelExt;
import org.jdesktop.swingx.table.TableColumnExt;
import org.jdesktop.swingx.table.TableColumnModelExt;

// GridField aka TableColumn
// diese Klasse kapselt GridField[] fields

// durch das Ableiten von org.jdesktop.swingx.table.DefaultTableColumnModelExt sind alle notwendigen Methoden wg implements TableColumnModelExt vorimplementiert
// in super sind zwei Listen
// - List<TableColumn> initialColumns : all columns, in the order in which were added to the model.
// - List<TableColumn> currentColumns : all columns, in the order they would appear if all were visible.

public class GridFields extends DefaultTableColumnModelExt // extends DefaultTableColumnModel implements TableColumnModel, ropertyChangeListener, ListSelectionListener, Serializable
                        implements TableColumnModelExt {

	private static final long serialVersionUID = 5218395949102734661L;
	
	private static final Logger LOG = Logger.getLogger(GridFields.class.getName());

	public GridFields() {
		super();
		LOG.warning("empty - use addColumn");
	}
	public GridFields(GridField[] gfields) {
		super();
		LOG.config("GridField[].length="+gfields.length); // Land:33
		for(int c = 0; c < gfields.length; c++) {
			GridField gfield = gfields[c];
			TableColumnExt aColumn = new GridFieldBridge(gfield);
			GridFieldBridge gColumn = (GridFieldBridge)aColumn;
			if(gColumn.isDisplayed() && gColumn.isDisplayedGrid()) {
				// display column
				aColumn.setToolTipText(gColumn.getDescription()); // TODO funktioniert nicht
//				aColumn.setCellRenderer(cellRenderer);
				this.addColumn(aColumn); 
				LOG.config(c+" add visible: "+aColumn + " Description:"+gColumn.getDescription()+ " CellRenderer=CellRenderer"+aColumn.getCellRenderer());
			} else {
				LOG.config(c+" not visible: "+aColumn);
//				this.isRemovedToInvisibleEvent(c); // bewirkt nichts ?????
			}
		}
		// TODO: die Breite ist nicht angepasst!
		
	}
	
	
// wg. implements TableColumnModelExt

	@Override
	public int getColumnCount(boolean includeHidden) {
//		LOG.config("includeHidden="+includeHidden + " (false/true)="+super.getColumnCount(false)+"/"+super.getColumnCount(true));
		return super.getColumnCount(includeHidden);
	}

	@Override
	public List<TableColumn> getColumns(boolean includeHidden) {
		LOG.config("return super...");
		return super.getColumns(includeHidden);
	}

	@Override
	public TableColumnExt getColumnExt(Object identifier) {
		LOG.config("return super...");
		return super.getColumnExt(identifier);
	}

	@Override
	public TableColumnExt getColumnExt(int columnIndex) {
		LOG.config("return super...");
		return super.getColumnExt(columnIndex);
	}
		
	@Override
	public void addColumnModelListener(TableColumnModelListener x) {
		LOG.config("TableColumnModelListener "+x);
		super.addColumnModelListener(x);
	}


// wg. implements TableColumnModel
	
	@Override
	public void addColumn(TableColumn aColumn) {
		LOG.config("TableColumn "+aColumn);
		if (aColumn instanceof GridFieldBridge) {
			TableColumnExt eColumn = (TableColumnExt)aColumn;
			GridFieldBridge gColumn = (GridFieldBridge)aColumn;
			eColumn.setToolTipText(gColumn.getDescription()); // TODO funktioniert nicht
			super.addColumn(eColumn);
			return;
		} 
		super.addColumn(aColumn);
	}

//	@Override
//	public void removeColumn(TableColumn column) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void moveColumn(int columnIndex, int newIndex) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void setColumnMargin(int newMargin) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public int getColumnCount() {
//		return fields.length;
//	}
//
//	@Override
//	public Enumeration<TableColumn> getColumns() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public int getColumnIndex(Object columnIdentifier) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public TableColumn getColumn(int columnIndex) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public int getColumnMargin() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public int getColumnIndexAtX(int xPosition) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public int getTotalColumnWidth() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public void setColumnSelectionAllowed(boolean flag) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public boolean getColumnSelectionAllowed() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public int[] getSelectedColumns() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public int getSelectedColumnCount() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public void setSelectionModel(ListSelectionModel newModel) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public ListSelectionModel getSelectionModel() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void removeColumnModelListener(TableColumnModelListener x) {
//		// TODO Auto-generated method stub
//		
//	}
}
