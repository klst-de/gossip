package com.klst.gossip;

import java.awt.Color;
import java.awt.Component;
import java.sql.Timestamp;
import java.util.EventObject;
import java.util.logging.Logger;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;

import org.compiere.model.GridTab;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTableHeader;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.renderer.CheckBoxProvider;
import org.jdesktop.swingx.renderer.ComponentProvider;
import org.jdesktop.swingx.renderer.DefaultTableRenderer;

// https://stackoverflow.com/questions/18186495/using-tablecelleditor-and-tablecellrenderer-at-the-same-time
/*
JTable table = new JTable(model);
DefaultCellEditor editor = new DefaultCellEditor(......); // abstract or custom name 
editor.setClickCountToStart(2); // for Compound JComponents (JComboBox) is more userfriendly invoke Editor on second click
table.getColumnModel().getColumn(1).setCellEditor(editor);
 */
public class MuliRowPanel extends JXTable { // JXTable extends JTable implements TableColumnModelExtListener
//	implements TableCellEditor 

	private static final long serialVersionUID = 4527635643876059507L;
	
	private static final Logger LOG = Logger.getLogger(MuliRowPanel.class.getName());

	private GridTab gridTab = null;
	// in JTable gibt es einige member die protected sind zB. dataModel
	
	// ctor use factory method createXTable()
	private MuliRowPanel() {
		super();
	}
	private MuliRowPanel(TableModel dm, GridTab gridTab) {
		super(dm);
		this.gridTab = gridTab;
		
        setColumnControlVisible(true); // column control to the trailing corner of the scroll pane 
        // replace grid lines with striping 
        setShowGrid(false, false);
//        addHighlighter(HighlighterFactory.createSimpleStriping()); // beeinflußt renderer
        addHighlighter(HighlighterFactory.createAlternateStriping());
        this.setCellSelectionEnabled(true); // simultaneous row and columnselection is allowed
        
        setDefaultRenderer(Object.class, new DefaultTableRenderer());
        // Sets a default cell editor to be used if no editor has been set in a TableColumn.
        setDefaultEditor(Object.class, new GenericEditor()); 
	}
	
	// aus org.jdesktop.swingx.demos.table.XTableDemo , erweitert
	protected static MuliRowPanel createXTable(TableModel dm, GridTab gridTab) {
		MuliRowPanel table = new MuliRowPanel(dm, gridTab);
		return table;
	}
	
	@Override // aus JXTable
	protected JTableHeader createDefaultTableHeader() { // TODO den Header besser: namen, column rentere und editoren dorthin!!!!
		LOG.warning(this.dataModel.getClass().toString());
		GenericDataModel dm = (GenericDataModel)dataModel;
		GridFields columnModel = dm.getColumns(); // Header sind besser aber TODO die Breiten werden nicht richtig gerendert
		return new JXTableHeader(columnModel) {
			private static final long serialVersionUID = -4124370542563896297L;

			@Override
			public void updateUI() {
				super.updateUI();
				// need to do in updateUI to survive toggling of LAF
				if (getDefaultRenderer() instanceof JLabel) {
					((JLabel) getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

				}
			}

		};
	}

	// es gibt zwei exits bei public void tableChanged( ...
	// - preprocessModelChange(e)
	// - postprocessModelChange(e); 
	protected void preprocessModelChange(TableModelEvent event) {
		String name = this.gridTab==null ? "null" : this.gridTab.getName();
		LOG.config("gridTab.Name=:'"+name + "', event Rows "+event.getFirstRow()+":"+event.getLastRow() + ", RowCount:"+dataModel.getRowCount());
		super.preprocessModelChange(event);
	}
	protected void postprocessModelChange(TableModelEvent event) {
		String name = this.gridTab==null ? "null" : this.gridTab.getName();
		LOG.config("gridTab.Name=:'"+name + "', event Rows "+event.getFirstRow()+":"+event.getLastRow() + ", RowCount:"+dataModel.getRowCount());
		super.postprocessModelChange(event);
		if(dataModel.getRowCount()>0 && !isSetColumnEditors && name!=null) {
			setColumnEditors();
		}
	}
	
	private boolean isSetColumnEditors = false;
	// jeweils eine Instanz der RO-Renderere:
	GossipTableRenderer roRenderer = new GossipTableRenderer();
	GossipBooleanEditor booleanEditor = new GossipBooleanEditor();
	
	public void setColumnEditors() {
//		for(int r = 0; r < dataModel.getRowCount(); r++) {

		
        for(int c = 0; c < dataModel.getColumnCount(); c++) {
        	Class<?> clazz = dataModel.getColumnClass(c);
        	// CellEditor sollten null sein, sie werden gleich gesetzt! Sind sie aber nicht!!!
        	LOG.config("col "+c + " Class:"+dataModel.getColumnClass(c)
				+ " ColumnName:"+dataModel.getColumnName(c)
//				+ " CellEditable:"+dataModel.isCellEditable(r, c)
    			+ " CellRenderer:"+this.getColumnModel().getColumn(c).getCellRenderer() + "/" + this.getDefaultRenderer(clazz).getClass()
        		+ " CellEditor:"+this.getColumnModel().getColumn(c).getCellEditor() + "/" + this.getDefaultEditor(clazz).getClass()
        		);
        	if(clazz==Boolean.class) {
//        		DefaultCellEditor editor = new BooleanEditor();
//        		editor.setClickCountToStart(1);
////        		this.getColumnModel().getColumn(c).setCellEditor(editor); // funktioniert so nicht! aber so:     		
//        		this.getColumnModel().getColumn(c).setCellEditor(new CellEditorAndRenderer(null, editor));
        		
        		this.getColumnModel().getColumn(c).setCellEditor(new CellEditorAndRenderer(null, booleanEditor)); // default überschreiben 
        		if(dataModel.isCellEditable(0, c)) {
        		} else {
        			//booleanEditor.getComponent()
        			CheckBoxProvider checkBoxProvider = new CheckBoxProvider();
        			// Versuch:
        			//checkBoxProvider.setBorderPainted(!checkBoxProvider.isBorderPainted());
        			// TODO ??? dem renderer ist nicht anzusehen, ob er einen Editor hat 
        			this.getColumnModel().getColumn(c).setCellRenderer(new GossipTableRenderer(checkBoxProvider));
        		}
        		
            } else if(clazz==Integer.class) {
//            	NumberEditor editor = new NumberEditor();
        		// test
        		if(dataModel.isCellEditable(0, c)) {
        			// OK - es greift der DefaultRenderer und der DefaultEditor
        		} else {
//        			Component component = editor.getTableCellEditorComponent(this, Integer.valueOf(0), true, 0, c);
//        			component.setVisible(false);
//        			editor.setVisible(false); // TODO dann ist es ja kein editor, sondern ein Renderer!!!!!!!!!!!!!!!
        			// TODO am renderer soll man erkennen, ob es einen editor gibt!! ==> einen anderen renderer für diese Celle
        			// hat auswirkungen auf Highlighter!!!!
        			this.getColumnModel().getColumn(c).setCellRenderer(roRenderer);
        		}		
//        		this.getColumnModel().getColumn(c).setCellEditor(new CellEditorAndRenderer(null, editor));
        		
        	} else if(clazz==Timestamp.class) {
        		if(dataModel.isCellEditable(0, c)) {
        			// OK - es greift der DefaultRenderer und der DefaultEditor
        		} else {
        			this.getColumnModel().getColumn(c).setCellRenderer(roRenderer);
        		}
        		
        	} else if(clazz==String.class) {
        		if(dataModel.isCellEditable(0, c)) {
        			// OK - es greift der DefaultRenderer und der DefaultEditor
        		} else {
        			this.getColumnModel().getColumn(c).setCellRenderer(roRenderer);
        		}
        	}
        }		
//		}
        isSetColumnEditors = true;
	}

	public static class GossipBooleanEditor extends BooleanEditor {

		private static final long serialVersionUID = 781583106895352002L;
		
		public GossipBooleanEditor() {
			super();
			this.setClickCountToStart(1); // number of clicks needed to start editing
		}
		
		@Override
		public boolean shouldSelectCell(EventObject anEvent) {
			LOG.config("user might want to be able to change checkboxes without altering the selection.");
			return true;
		}
////		@Override
//		public boolean shouldSelectCell(EventObject anEvent) {
//			this.getTableCellEditorComponent(table, value, isSelected, row, column)
//			Component component = this.getTableCellEditorComponent(table, value, isSelected, row, column);
////			component.setEnabled(b);
//			return component;
//		}
	}
	
//	public static class GossipNumberEditor extends NumberEditor {
//
//		private static final long serialVersionUID = 8714208867557744019L;
//
//		public GossipNumberEditor() {
//			super();
//		}
//		
//		public void setVisible(boolean b) {
//			this.editorComponent.setVisible(b);
//		}
//		
//	}
	
	// mit diesem Renderer werden Spalten dargestellt die RO sind / keinen Editor haben
	public class GossipTableRenderer extends DefaultTableRenderer {

		private static final long serialVersionUID = 3097989281688245341L;

		public GossipTableRenderer() {
			this((ComponentProvider<?>) null);
//			super();
////			this.setBackground(Color.GRAY); // das sieht mit Highlighter nicht gut aus!
//			this.setForeground(Color.LIGHT_GRAY);
////			this.componentController.getRendererComponent(context???)
		}
		
		// praram CheckBoxProvider()
		GossipTableRenderer(ComponentProvider<?> componentProvider) {
			super(componentProvider);
//			this.setBackground(Color.GRAY); // das sieht mit Highlighter nicht gut aus!
			this.setForeground(Color.LIGHT_GRAY);
		}
		
	}

//	------------- implements TableCellEditor:
//	@Override
//	public Object getCellEditorValue() {
//		return null;
//	}
//
//	@Override
//	public boolean isCellEditable(EventObject anEvent) {
//		//super.isCellEditable(row, column)
//		return false;
//	}
//
//	@Override
//	public boolean shouldSelectCell(EventObject anEvent) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean stopCellEditing() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public void cancelCellEditing() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void addCellEditorListener(CellEditorListener l) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void removeCellEditorListener(CellEditorListener l) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
//		// TODO Auto-generated method stub
//		return null;
//	} 

// inner:
	
	class CellEditorAndRenderer extends AbstractCellEditor implements 
//    TableCellRenderer, 
    TableCellEditor {

		private static final long serialVersionUID = 8976822169724033058L;

//	    private CompCellPanel renderer = new CompCellPanel();
//	    private CompCellPanel editor = ...
		
//		private GenericEditor numberEditor = new NumberEditor(); // NumberEditor extends GenericEditor extends DefaultCellEditor
//		private NumberEditorExt numberEditorExt = new NumberEditorExt(); // extends DefaultCellEditor
//		private DefaultCellEditor editor;
		
		private DefaultTableRenderer renderer;
		private DefaultCellEditor editor;
		
		// ctor
		CellEditorAndRenderer(DefaultTableRenderer renderer, DefaultCellEditor editor) {
			super();
			this.renderer = renderer;
			this.editor = editor;
		}
		
		/*
		 * (non-Javadoc)
		 * @see javax.swing.AbstractCellEditor#isCellEditable(java.util.EventObject)
		 */
	    @Override
	    public boolean isCellEditable(EventObject anEvent) {
	        return true;
	    }

	    /*
	     * (non-Javadoc)
	     * @see javax.swing.AbstractCellEditor#shouldSelectCell(java.util.EventObject)
	     */
	    @Override
	    public boolean shouldSelectCell(EventObject anEvent) {
	    	if(this.editor instanceof BooleanEditor) {
				LOG.config("user might want to be able to change checkboxes without altering the selection.");
	    		return false; // user might want to be able to change checkboxes without altering the selection.
	    	}
	        return true;
	    }

// wg. implements TableCellEditor:
	    
		@Override
		public Object getCellEditorValue() {
			Object value = editor.getCellEditorValue();
			LOG.config("value:"+value);
			return value;
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			LOG.config("table:"+table + "\n value:"+value + " isSelected:"+isSelected + " row:"+row + " column:"+column);
			Component component = editor.getTableCellEditorComponent(table, value, isSelected, row, column);
//			component.setEnabled(b);
			return component;
		}

// wg. implements TableCellRenderer:
		
///*
//    @Override
//    public Component getTableCellRendererComponent(JTable table, Object value,
//            boolean isSelected, boolean hasFocus, int row, int column) {
//
//        setSelected(value==null ? false : (boolean)value);              
//        return this;
//    }
// */
//		@Override
//		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//			LOG.config("table:"+table + " value:"+value + " isSelected:"+isSelected + " hasFocus:"+hasFocus + " row:"+row + " column:"+column);
////	        renderer.setComp((Comp) value);
////	        return renderer;
//			return renderer.get;
//		}
//		
	}

}
