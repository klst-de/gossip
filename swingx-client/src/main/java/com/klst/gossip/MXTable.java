package com.klst.gossip;

import java.beans.PropertyChangeEvent;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.compiere.model.GridField;
import org.compiere.model.GridTable;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTableHeader;
import org.jdesktop.swingx.renderer.DefaultTableRenderer;
import org.jdesktop.swingx.table.ColumnFactory;
import org.jdesktop.swingx.table.DefaultTableColumnModelExt;
import org.jdesktop.swingx.table.TableColumnExt;

public class MXTable extends JXTable { // JXTable extends JTable implements TableColumnModelExtListener

	private static final long serialVersionUID = 8107876965534675938L;
	private static final Logger LOG = Logger.getLogger(MXTable.class.getName());
	
	// factory method aus org.jdesktop.swingx.demos.table.XTableDemo , erweitert, wird in Tab gebraucht
	// dataModel ist GenericDataModel
	// ohne gridTab - es ist in GenericDataModel gekapselt
	protected static MXTable createXTable(TableModel dataModel) {
		if(dataModel instanceof GridTable) {
			return new MXTable((GridTable)dataModel);
		}
		return new MXTable(dataModel);
	}
	
    /**
     * {@inheritDoc}
     * 
     */
	@Override // implemeted in JXTable
	public void columnPropertyChange(PropertyChangeEvent event) {
		LOG.config("event:"+event);
		super.columnPropertyChange(event);
	}
	
	private MXTable() {
		super();
		LOG.config("ctor");
	}

//	ColumnFactory columnFactory = GridFieldFactory.getInstance(); // a singleton
	ColumnFactory columnFactory = ColumnFactory.getInstance(); 
	// public TableColumnExt createAndConfigureTableColumn(TableModel model, int modelIndex)
	DefaultTableColumnModelExt tcme; // = new DefaultTableColumnModelExt(); // der einzige ctor
	
//	static private TableColumnModel initTableColumnModel(GridField[] fields) {
	static private TableColumnModel initTableColumnModel(GridTable dataModel) {
		DefaultTableColumnModelExt tcme = new DefaultTableColumnModelExt();
		GridField[] fields = dataModel.getFields();
		boolean readOnly = true; // alle sind RO
		for (int f = 0; f < fields.length; f++) {	
			GridField field = fields[f];
			boolean isDisplayed = field.isDisplayed() & field.isDisplayedGrid(); // nur fields anzeigen, die isDisplayed UND isDisplayedGrid sind
			int displayType = field.getDisplayType();
			String header = field.getHeader();
			String columnName = field.getColumnName();
			Class<?> columnClass = dataModel.getColumnClass(f);
			LOG.config("displayType="+displayType + " isKey="+field.isKey() + " isDisplayed="+isDisplayed + " isSelectionColumn="+field.isSelectionColumn() + 
					": fields["+f+"].ColumnName="+columnName + "/" + columnClass);
			
			switch(displayType) {
//			case DisplayType.Date: // 15 Date
//				field.setDisplayType(DisplayType.Date); // ohne Time
//				minitable.setColumnClass(f, field);
//				break;
//			case DisplayType.DateTime: // 16 DateDoc
//				field.setDisplayType(DisplayType.DateTime);
//				minitable.setColumnClass(f, field);
//				break;
//			case DisplayType.String:   // 10 DocumentNo
//			case DisplayType.ID:       // 13 C_BPartner.C_BPartner_ID
//			case DisplayType.List:     // 17 DocStatus
//			case DisplayType.Table:    // 18 CreatedBy, UpdatedBy
//			case DisplayType.TableDir: // 19 AD_Client_ID, ...
//				minitable.setColumnClass(f, field);
//				break;
//			case DisplayType.Location: // 21 Location TODO
//				field.setDisplayType(DisplayType.TableDir);
//				minitable.setColumnClass(f, field);
//				break;
			default:
//				minitable.setColumnClass(f, columnClass, displayType, readOnly, header);
			}
			TableCellRenderer cellRenderer = new DefaultTableRenderer();
			TableCellEditor cellEditor = null;
			//TableColumnExt aColumn = new TableColumnExt(f);
			// TODO: 
			int width = 75; // default
			TableColumnExt aColumn = new TableColumnExt(f, width, cellRenderer, cellEditor);
			
			if(isDisplayed) {
				// Both isDisplayed() and isDisplayedGrid() should be true
			} else {
				// TODO
//				minitable.setColumnVisibility(minitable.getColumn(f), false);
				aColumn.setVisible(false);
			}	
			tcme.addColumn(aColumn);
		}
		return tcme;
	}
	private MXTable(GridTable dataModel) {
		super(dataModel, initTableColumnModel(dataModel));
		tcme = (DefaultTableColumnModelExt)columnModel; // protected TableModel in super.columnModel
		LOG.config("columnModel ColumnCount="+tcme.getColumnCount());
		
//		List<TableColumn> cols = tcme.getColumns(true); // includeHidden
//		for (int f = 0; f < cols.size(); f++) {
//			TableColumnExt cole = (TableColumnExt)(cols.get(f));
//			LOG.config("col "+f + ": isVisible="+cole.isVisible());
//		}
//		cols.forEach((TableColumn col, int i) -> {
//			TableColumnExt cole = (TableColumnExt)col;
//			cole.isVisible();
//		});
		
		GridField[] fields = dataModel.getFields();
		for (int f = 0; f < tcme.getColumnCount(); f++) {
			GridField field = fields[f];
			TableColumn tc = tcme.getColumn(f);
			tc.setHeaderValue(field.getHeader());
		}
		
		// public class JXTableHeader extends JTableHeader implements TableColumnModelExtListener
//		tableHeader = new JXTableHeader(tcme);
//		GridField[] fields = dataModel.getFields();
//		for (int f = 0; f < fields.length; f++) {
//			GridField field = fields[f];
//			boolean isDisplayed = field.isDisplayed() & field.isDisplayedGrid();
//			if(isDisplayed) {
//				tableHeader.add(new JXLabel(field.getHeader()), f); // IllegalArgumentException: illegal component position
//			} else {
//				LOG.config("field "+f + " isDisplayed="+isDisplayed);
//			}
//		}
//		tableHeader.setTable(this);
		LOG.config("tableHeader ColumnCount="+tableHeader.getComponentCount());
//		super.tableHeader
		
	}
	private MXTable(TableModel dataModel) {
		super(dataModel); // es gibt noch ctor in super mit TableColumnModel: 
		//        (TableModel dm, (interface)TableColumnModel cm) und 4 andere:
		// JXTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm)
		// an dataModel komme ich immer dran: in JTable: protected TableModel dataModel;
		/*
(interface)TableColumnModel wird erweitert in (swingx)interface TableColumnModelExt

in (swingx)public class DefaultTableColumnModelExt extends DefaultTableColumnModel implements TableColumnModelExt

		 */
		//TableColumn aColumn;
		TableColumnExt aColumn = null; //new TableColumnExt(int modelIndex);
		// new TableColumnExt(int modelIndex, int width)
		// new TableColumnExt(int modelIndex, int width, TableCellRenderer cellRenderer, TableCellEditor cellEditor)
		// new TableColumnExt(TableColumnExt columnExt)
//		tcme.addColumn(aColumn);
	}
	
	public void setModel(TableModel dataModel) {
		LOG.config("dataModel:"+dataModel);
		super.setModel(dataModel);
	}
}
