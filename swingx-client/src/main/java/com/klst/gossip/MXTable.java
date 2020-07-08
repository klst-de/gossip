package com.klst.gossip;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.logging.Logger;

import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.compiere.model.GridField;
import org.compiere.model.GridTable;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.table.ColumnFactory;
import org.jdesktop.swingx.table.DefaultTableColumnModelExt;
import org.jdesktop.swingx.table.TableColumnExt;
import org.jdesktop.swingx.table.TableColumnModelExt;

import com.klst.gossip.render.MXTableRenderer;
import com.klst.icon.TableColumnControlButton;

public class MXTable extends JXTable { // JXTable extends JTable implements TableColumnModelExtListener

	private static final long serialVersionUID = 8107876965534675938L;
	private static final Logger LOG = Logger.getLogger(MXTable.class.getName());
	
	// Layout:
	// - ColumnControlButton - a control to the trailing corner above the scroll pane
	private static final boolean isColumnControlVisible = true;
	// - Grid lines
	private static final boolean showHorizontalLines = true;
	private static final boolean showVerticalLines = true;
	// - Highlighter aka Decorator, die org.jdesktop.swingx.decorator.HighlighterFactory bietet mehrere an
	//   SimpleStriping, AlternateStriping, ... siehe dort
	private static Highlighter highlighter = HighlighterFactory.createAlternateStriping();
			
	// factory method aus org.jdesktop.swingx.demos.table.XTableDemo, erweitert, wird in GenericFormPanel verwendet
	public static MXTable createXTable(TableModel dataModel) {
		if(dataModel instanceof GridTable) {
			// Ansatz 1:
			// - OK Header und Spalten synchron
			// - OK Splatenbreite berechnet, aber nicht aus den Zellen
			// - OK einige Spalten ausgeblendet
			// - NOK Zellen nur teilweise gerendert TODO
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
		if("preferredWidth".equals(event.getPropertyName())
		|| "width".equals(event.getPropertyName())
		) {
			Object o = event.getSource();
			if(o instanceof TableColumnExt) {
				TableColumnExt tce = (TableColumnExt)o;
				LOG.config("preferredWidth OldValue="+event.getOldValue() + " for "+tce.getHeaderValue() + " not changed to "+event.getNewValue());
				return;
			}
		}
		super.columnPropertyChange(event);
	}
	
	private MXTable() {
		super();
		LOG.config("ctor");
	}

//	ColumnFactory columnFactory = GridFieldFactory.getInstance(); // a singleton
	ColumnFactory columnFactory = ColumnFactory.getInstance(); // Creates and configures (swingx)TableColumnExt extends (swing)TableColumn
	// public TableColumnExt createAndConfigureTableColumn(TableModel model, int modelIndex)
	TableColumnModelExt tcme; // = new DefaultTableColumnModelExt(); // der einzige ctor
	TableColumnModelExt getTableColumnModelExt() {
		return tcme;
	}
	
	/* calculate width: min of
	 * 75	// default width taken from javax.swing.table.TableColumn
	 * field.getDisplayLength()
	 * field.getFieldLength()
	 * field.MAXDISPLAY_LENGTH
	 * result is max(min, header.length())
	 */
	static public int calculateWidth(GridField field) {
		String header = field.getHeader();
		LOG.config("field "+field.getColumnName() + "/"+header+": minOf 75, DisplayLength()="+field.getDisplayLength() + ", FieldLength()="+field.getFieldLength() + ", MAXDISPLAY_LENGTH="+GridField.MAXDISPLAY_LENGTH);
		int min = Math.min(Math.min(Math.min(75, field.getDisplayLength()), field.getFieldLength()), GridField.MAXDISPLAY_LENGTH);
		return Math.max(min, header.length());
	}
	static private TableColumnModel initTableColumnModel(GridTable dataModel) {
		TableColumnModelExt tcme = new DefaultTableColumnModelExt();
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
			
			int width = calculateWidth(field);
			TableCellRenderer cellRenderer = new MXTableRenderer(dataModel);
			TableCellEditor cellEditor = null;
			TableColumnExt aColumn = new TableColumnExt(f, width, cellRenderer, cellEditor);
			aColumn.setHeaderValue(field.getHeader()); // TODO es gibt TableColumn.sizeWidthToFit()
			aColumn.setIdentifier(field); // Object Identifier
						
			if(isDisplayed) {
				// Both isDisplayed() and isDisplayedGrid() should be true
			} else {
				aColumn.setVisible(false);
			}	
			tcme.addColumn(aColumn);
		}
		LOG.config("GridField.length="+fields.length + " TableColumnModelExt="+tcme.getColumnCount(true));
		return tcme;
	}
	
	private MXTable(GridTable dataModel) {
		super(dataModel, initTableColumnModel(dataModel)); // TableModel dm, TableColumnModel cm
		tcme = (TableColumnModelExt)columnModel; // protected TableModel in super.columnModel
		LOG.config("columnModel ColumnCount="+tcme.getColumnCount());
		
		setColumnControl(new TableColumnControlButton(this)); // TableColumnControlButton tauscht das Icon
		setColumnControlVisible(isColumnControlVisible); // column control to the trailing corner of the scroll pane 

		// replace grid lines with striping 
		setShowGrid(showHorizontalLines, showVerticalLines);
		addHighlighter(highlighter);

		// JXTable uses instances of this as per-class default renderers:
		setDefaultRenderer(Object.class, new MXTableRenderer(dataModel));
		LOG.config("tableHeader ColumnCount="+tableHeader.getColumnModel().getColumnCount());
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
	
	void setModel(GridTable dataModel) {
		LOG.config("GridTable dataModel:"+dataModel);
		//TableColumnModel tableColumnModel = initTableColumnModel(dataModel);
		setModel((TableModel)dataModel);
	}
	public void setModel(TableModel dataModel) {
		LOG.config("dataModel:"+dataModel);
		super.setModel(dataModel);
	}
	
	GridTable getGridModel() {
		TableModel tm = super.getModel();
		if(tm instanceof GridTable) {
			return (GridTable)tm;
		}
		return null;
	}
	
    /**
     * {@inheritDoc}
     * 
     */
//	@Override // implemeted in JTable
//	public Object getValueAt(int row, int column) {
//		Object o = super.getValueAt(row, column);
//		LOG.config("(row "+row+", column "+column+"):"+o);
//		return o;
//	}
	
    /**
     * {@inheritDoc}
     * 
     */
	@Override // implemeted in JTable
	public int getSelectedRow() {
		return super.getSelectedRow();
	}
	
    /**
     * {@inheritDoc}
     * 
     */
	@Override // implemeted in JTable
	public void valueChanged(ListSelectionEvent e) {
		super.valueChanged(e);
		// es gibt zwei events : mouse down (e.getValueIsAdjusting()==true) + mouse up (e.getValueIsAdjusting()==false)
		// @see void javax.swing.ListSelectionModel.setValueIsAdjusting(boolean valueIsAdjusting)
		fireRowSelectionEvent(e);
	}
	
    // A list of event listeners for this component
	private EventListenerList listenerList = new EventListenerList();

	private void fireRowSelectionEvent(ListSelectionEvent e) {
		if(e.getValueIsAdjusting()) {
			// TODD bei SINGLE_SELECTION ignorieren
			return;
		}
		
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();

        // Lazily create the event:
//        RowSelectionEvent rowSelectionEvent = new RowSelectionEvent(this, RowSelectionEvent.ROW_TOGGLED);
        // ctor            (Object source, int id, String command)
        // oder ActionEvent(Object source, int id, String command, int modifiers)
        ActionEvent rowSelectionEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, Integer.toString(e.getLastIndex()));


        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==TableSelectionListener.class) {
                ((TableSelectionListener)listeners[i+1]).rowSelected(rowSelectionEvent);
            }          
        }
    }    

	public interface TableSelectionListener extends EventListener {
		public abstract void rowSelected(ActionEvent e);
	}
	/*
	 * in AD(swing)Minitable wird definiert 
	 * -	public interface MiniTableSelectionListener extends EventListener
	 * -	public class RowSelectionEvent extends AWTEvent
	 * dann public void addMiniTableSelectionListener(MiniTableSelectionListener l)
	 * 
	 * in java gibt es:
	 * public class ActionEvent extends AWTEvent
	 */
    public void addMiniTableSelectionListener(TableSelectionListener l) {
        listenerList.add(TableSelectionListener.class, l);
    }

}
