package com.klst.gossip;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTableHeader;
import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.renderer.CheckBoxProvider;
import org.jdesktop.swingx.renderer.StringValues;
import org.jdesktop.swingx.table.ColumnFactory;
import org.jdesktop.swingx.table.TableColumnModelExt;

import com.klst.icon.TableColumnControlButton;

/* https://stackoverflow.com/questions/18186495/using-tablecelleditor-and-tablecellrenderer-at-the-same-time
 * pseudocode
JTable table = new JTable(model);
DefaultCellEditor editor = new DefaultCellEditor(......); // abstract or custom name 
editor.setClickCountToStart(2); // for Compound JComponents (JComboBox) is more userfriendly invoke Editor on second click
table.getColumnModel().getColumn(1).setCellEditor(editor);
 */
public class MuliRowPanel extends JXTable { // JXTable extends JTable implements TableColumnModelExtListener

	private static final long serialVersionUID = 4527635643876059507L;	
	private static final Logger LOG = Logger.getLogger(MuliRowPanel.class.getName());

	// Layout:
	// - ColumnControlButton - a control to the trailing corner above the scroll pane
	private static final boolean isColumnControlVisible = true;
	// - Grid lines
	private static final boolean showHorizontalLines = true;
	private static final boolean showVerticalLines = true;
	// - Highlighter aka Decorator, die org.jdesktop.swingx.decorator.HighlighterFactory bietet mehrere an
	//   SimpleStriping, AlternateStriping, ... siehe dort
	private static Highlighter highlighter = HighlighterFactory.createAlternateStriping();
			
	// factory method aus org.jdesktop.swingx.demos.table.XTableDemo , erweitert, wird in Tab gebraucht
	// dataModel ist GenericDataModel
	// ohne gridTab - es ist in GenericDataModel gekapselt
	protected static MuliRowPanel createXTable(TableModel dataModel) {
		return new MuliRowPanel(dataModel);
	}

	// ctor use factory method createXTable()
	private MuliRowPanel() {
		super();
	}

	ColumnFactory columnFactory = GridFieldFactory.getInstance(); // a singleton
	private MuliRowPanel(TableModel dataModel) {
		super(); // es gibt noch ctor in super mit TableColumnModel: (TableModel dm, TableColumnModel cm) und andere

		super.setColumnFactory(columnFactory);	
		// A ColumnFactory is used by JXTable to create and configure all columns. It can be set per-application or per-table (before setting the model)
		super.setModel(dataModel); // setting the model
		
		super.createDefaultColumnsFromModel(); // a final method, do getColumnFactory().createAndConfigureTableColumn(getModel(), i);
		LOG.config(">>>>>>>>>>>>>>>>>>>>>> ColumnCount="+dataModel.getColumnCount() + " columnFactory:"+columnFactory);
		//columnFactory.configureTableColumn(dataModel, TableColumnExt columnExt) IN createDefaultColumnsFromModel geht es nicht, da dataModel noch nicht definiert
		// aus JTable: protected TableColumnModel columnModel;
//		((GridFieldFactory)columnFactory).configureTableColumn((GenericDataModel)dataModel, columnModel);

		setColumnControl(new TableColumnControlButton(this)); // TableColumnControlButton tauscht das Icon
		setColumnControlVisible(isColumnControlVisible); // column control to the trailing corner of the scroll pane 

		// replace grid lines with striping 
		setShowGrid(showHorizontalLines, showVerticalLines);
		addHighlighter(highlighter);
		//setVisibleRowCount(10);
		
		// JXTable uses instances of this as per-class default renderers.
		setDefaultRenderer(Object.class, new GenericTableRenderer());
//		setDefaultRenderer(Object.class, new GenericTableRenderer(new GridFieldProvider()));
		//setDefaultRenderer(PO.class, new GenericTableRenderer(StringValues.TO_STRING, JLabel.RIGHT)); // wieso greift das nicht?
		setDefaultRenderer(Number.class, new GenericTableRenderer(StringValues.NUMBER_TO_STRING, JLabel.LEFT));
//		setDefaultRenderer(Date.class, new GenericTableRenderer(StringValues.DATE_TO_STRING));
//		// use the same center aligned default for Image/Icon
//		TableCellRenderer renderer = new GenericTableRenderer(new MappedValue(StringValues.EMPTY, IconValues.ICON), JLabel.CENTER);
//		setDefaultRenderer(Icon.class, renderer);
//		setDefaultRenderer(ImageIcon.class, renderer);
//		// use a CheckBoxProvider for booleans
		setDefaultRenderer(Boolean.class, new GenericTableRenderer(new CheckBoxProvider()));
//		
//		setDefaultRenderer(X_AD_Client.class, new GenericTableRenderer(new GridFieldProvider()));

		this.createDefaultEditors();
//		super.dataModel.isCellEditable(rowIndex, columnIndex)
//		super.dataModel.getColumnCount();
//		super.dataModel.getRowCount();
		
		this.addMouseListener(mouseListener);
		
		LOG.config(dataModel.getColumnCount()+"/"+dataModel.getRowCount()); 
	}
/* zum Nachlesen aus super:
 * ... JXTable registers SwingX default table renderers instead of core defaults (see {@link DefaultTableRenderer}) 
 * The recommended approach for customizing rendered content is to intall a DefaultTableRenderer configured with a custom String- and/or IconValue. 
 * F.i. assuming the cell value is a File and should be rendered by showing its name followed and date of last change:
 * 
 * <pre><code>
 * StringValue sv = new StringValue() {
 *      public String getString(Object value) {
 *        if (!(value instanceof File)) return StringValues.TO_STRING.getString(value);
 *        return StringValues.FILE_NAME.getString(value) + &quot;, &quot; 
 *           + StringValues.DATE_TO_STRING.getString(((File) value).lastModified());
 * }};
 * table.setCellRenderer(File.class, new DefaultTableRenderer(sv));
 * </code></pre>

 */
	GenericDataModel getDataModel() {
		return (GenericDataModel)dataModel;
	}
	
	JXTableHeader jXTableHeader;
	protected JTableHeader createDefaultTableHeader() {
		LOG.config("called at javax.swing.JTable.initializeLocalVars(JTable.java:5529) columnModel.getColumnCount()="+columnModel.getColumnCount());
//		this.columnModel.getColumn(0).setHeaderValue("A"); // set first column
		for(int c = 0; c < columnModel.getColumnCount(); c++) {
			columnModel.getColumn(c).setHeaderValue( ((GenericDataModel)dataModel).getHeaderValue(c) );
		}

		jXTableHeader = new GenericTableHeader(columnModel);
		return jXTableHeader;	
	}

	protected void createDefaultEditors() {
		LOG.config("wann kommt diese Methode dran?????????????????");
/* aus super:

        defaultEditorsByColumnClass = new UIDefaults(3, 0.75f);
        defaultEditorsByColumnClass.put(Object.class, new GenericEditor());
        // Numbers
        // JW: fix for 
        // Issue #1183-swingx: NumberEditorExt throws in getCellEditorValue if
        //   Integer (short, byte..) below/above min/max.
        // Issue #1236-swingx: NumberEditorExt cannot be used in columns with Object type
        defaultEditorsByColumnClass.put(Number.class, new NumberEditorExt(true));
        // Booleans
        defaultEditorsByColumnClass.put(Boolean.class, new BooleanEditor());


 */
		super.createDefaultEditors(); // für Object.class Number.class Boolean.class
	}
	// get the rendering component for the given cell
	public Component prepareRenderer(int row, int col) {
		Component stamp = super.prepareRenderer(row, col); // the decorated Component used as a stamp to render the specified cell
		LOG.config(col+"/"+row + " stamp:"+stamp);
		return stamp;		
	}

	// es gibt zwei exits in (super) public void tableChanged( ...
	// - preprocessModelChange(e)
	// - postprocessModelChange(e); 
	// TableModelEvent is used to notify listeners that a table model has changed.
	protected void preprocessModelChange(TableModelEvent event) {
		LOG.config("Column:"+event.getColumn() + " FirstRow="+event.getFirstRow()+" LastRow="+event.getLastRow() + " Source:"+event.getSource());
		super.preprocessModelChange(event);
	}
	protected void postprocessModelChange(TableModelEvent event) {
		LOG.config("Column:"+event.getColumn() + " FirstRow="+event.getFirstRow()+" LastRow="+event.getLastRow() + " Source:"+event.getSource());
		super.postprocessModelChange(event);
		if(dataModel.getRowCount()>0 && !isSetColumnEditors && event.getLastRow()>-1) {
			setColumnEditors();
		}
	}
	
/*

To add a column to this JTable to display the modelColumn'th column of data in the model with agiven width, cellRenderer,and cellEditor you can use: 

      addColumn(new TableColumn(modelColumn, width, cellRenderer, cellEditor));


 */
	// public void setDefaultEditor(Class<?> columnClass, TableCellEditor editor)
	// public TableCellEditor getDefaultEditor(Class<?> columnClass)
	// active cell editor
	// (JTable) public TableCellEditor getCellEditor(int row, int column)
	//          public TableCellEditor getCellEditor()
	boolean isSetColumnEditors = false;
	public void setColumnEditors() { // TODO das sind noch keine Editoren
		// this.getCellEditor() active cell editor
		isSetColumnEditors = true;
		TableColumnModelExt tableColumnModel = this.getDataModel().getColumns(); // aka TableColumnModel
//		List<TableColumn> colList = tableColumnModel.getColumns(true); // get all columns, includeHidden
//		colList.forEach(aColumn -> {
//			GridFieldBridge gColumn = (GridFieldBridge)aColumn;
//			LOG.config(aColumn.getModelIndex() + ":" + gColumn);
//		});
		Object[] row0 = this.getDataModel().getRow(0);
		for(int c=0; c<tableColumnModel.getColumnCount(); c++) {
			Class<? extends Object> clazz = row0[c]==null ? null : row0[c].getClass();
			Class<? extends Object> columnClass =  this.getDataModel().getColumnClass(c);
			int dt = this.getDataModel().getFieldModel(c).getDisplayType();
			// isCellEditable? ist es korrekt? -  zumindest für Organisation Type stimmt es:
			LOG.config("first row,col "+c+" isCellEditable:"+this.getDataModel().isCellEditable(0, c) + " dt="+dt + " :"+row0[c] + " columnClass:"+columnClass+"/"+clazz);
		}
	}
	
	// nur zum LOGgen
	public void columnPropertyChange(PropertyChangeEvent event) {
		LOG.config("Property:"+event.getPropertyName() + " Object:"+event.getSource());
		super.columnPropertyChange(event);
	}
	public void valueChanged(ListSelectionEvent event) {
		LOG.config("First/Last"+event.getFirstIndex()+"/"+event.getLastIndex() + " Object:"+event.getSource());
		super.valueChanged(event);
	}
	
	private MouseListener mouseListener = new MultiRowPanelMouseAdapter();
	// alle Methoden in (awt) abstract class MouseAdapter sind leer vorimplementiet 
	// siehe auch MenuPanel - dort ersetzt durch tree.addTreeSelectionListener(event -> {
/*
aus MouseEvent:
For example, if the first mouse button is pressed, events are sent in the following order: 
    id              modifiers    button 
    MOUSE_PRESSED:  BUTTON1_MASK BUTTON1
    MOUSE_RELEASED: BUTTON1_MASK BUTTON1
    MOUSE_CLICKED:  BUTTON1_MASK BUTTON1

 */
	class MultiRowPanelMouseAdapter extends MouseAdapter { // java.awt.event.MouseAdapter implements MouseListener, MouseWheelListener, MouseMotionListener

		public void mouseClicked(MouseEvent event) {
			if (event.getSource() instanceof MuliRowPanel) {
				MuliRowPanel jxtable = (MuliRowPanel)event.getSource();
			    int row = jxtable.rowAtPoint(event.getPoint());
			    int col = jxtable.columnAtPoint(event.getPoint());
			    if (row >= 0 && col >= 0) {
			    	LOG.config("c/r:"+col+"/"+row + " ValueAt:"+jxtable.getDataModel().getValueAt(row, col));			    	
			    }
			}
		}
	}
}
