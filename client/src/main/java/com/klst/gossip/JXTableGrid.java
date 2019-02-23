package com.klst.gossip;

import java.awt.Color;
import java.awt.Component;
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
import org.jdesktop.swingx.renderer.DefaultTableRenderer;

// https://stackoverflow.com/questions/18186495/using-tablecelleditor-and-tablecellrenderer-at-the-same-time
/*
JTable table = new JTable(model);
DefaultCellEditor editor = new DefaultCellEditor(......); // abstract or custom name 
editor.setClickCountToStart(2); // for Compound JComponents (JComboBox) is more userfriendly invoke Editor on second click
table.getColumnModel().getColumn(1).setCellEditor(editor);
 */
public class JXTableGrid extends JXTable { // JXTable extends JTable implements TableColumnModelExtListener
//	implements TableCellEditor 

	private static final long serialVersionUID = 4527635643876059507L;
	
	private static final Logger LOG = Logger.getLogger(JXTableGrid.class.getName());

	private GridTab gridTab = null;
	
	// ctor use factory method createXTable()
	private JXTableGrid() {
		super();
	}
	private JXTableGrid(TableModel dm, GridTab gridTab) {
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
        
//        for(int c = 0; c < dataModel.getColumnCount(); c++) {
//        	Class<?> clazz = dataModel.getColumnClass(c);
//        	// CellEditor sollten null sein, sie werden gleich gesetzt!
//        	LOG.config("col "+c + " Class:"+dataModel.getColumnClass(c)
//    			+ " CellRenderer:"+this.getColumnModel().getColumn(c).getCellRenderer()
//        		+ " CellEditor:"+this.getColumnModel().getColumn(c).getCellEditor());
//        	if(clazz==Boolean.class) {
//        		DefaultCellEditor editor = new BooleanEditor();
//        		editor.setClickCountToStart(1);
////        		this.getColumnModel().getColumn(c).setCellEditor(editor); // funktioniert so nicht! aber so:     		
//        		this.getColumnModel().getColumn(c).setCellEditor(new CellEditorAndRenderer(null, editor));
//        		
//            	} else if(clazz==Integer.class) {
//        		DefaultCellEditor editor = new NumberEditor();
//        		this.getColumnModel().getColumn(c).setCellEditor(new CellEditorAndRenderer(null, editor));
//        		
////        	} else if(clazz==String.class) {
////        		// setzen BooleanEditor für diese Spalte
////        		DefaultCellEditor editor = new DefaultCellEditor(final JTextField textField ??? weches);
////        		editor.setClickCountToStart(1);
//////        		this.getColumnModel().getColumn(c).setCellEditor(editor);
////        		// funktioniert nicht!
////        		
////        		// anders:
////        		this.getColumnModel().getColumn(c).setCellEditor(new CellEditorAndRenderer(editor));
//        	}
//        }
	}
	
	// aus org.jdesktop.swingx.demos.table.XTableDemo , erweitert
	protected static JXTable createXTable(TableModel dm, GridTab gridTab) {
		JXTable table = new JXTableGrid(dm, gridTab);
		return table;
	}
	
	@Override
	protected JTableHeader createDefaultTableHeader() {
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
		LOG.config(name + ", event Rows "+event.getFirstRow()+":"+event.getLastRow() + ", RowCount:"+dataModel.getRowCount());
		super.preprocessModelChange(event);
	}
	protected void postprocessModelChange(TableModelEvent event) {
		String name = this.gridTab==null ? "null" : this.gridTab.getName();
		LOG.config(name + ", event Rows "+event.getFirstRow()+":"+event.getLastRow() + ", RowCount:"+dataModel.getRowCount());
		super.postprocessModelChange(event);
		if(event.getFirstRow()>=0 && !isSetColumnEditorsSet && name!=null) {
			setColumnEditors();
		}
	}
	
	public static class GossipNumberEditor extends NumberEditor {
		public GossipNumberEditor() {
			super();
		}
		
		public void setVisible(boolean b) {
			this.editorComponent.setVisible(b);
		}
		
	}
	public class GossipTableRenderer extends DefaultTableRenderer {
		public GossipTableRenderer() {
			super();
//			this.setBackground(Color.GRAY); // das sieht mit Highlighter nicht gut aus!
			this.setForeground(Color.LIGHT_GRAY);
		}
		
	}

	private boolean isSetColumnEditorsSet = false;
	public void setColumnEditors() {
//		for(int r = 0; r < dataModel.getRowCount(); r++) {

		
        for(int c = 0; c < dataModel.getColumnCount(); c++) {
        	Class<?> clazz = dataModel.getColumnClass(c);
        	// CellEditor sollten null sein, sie werden gleich gesetzt! Sind sie aber nicht!!!
        	LOG.config("col "+c + " Class:"+dataModel.getColumnClass(c)
				+ " ColumnName:"+dataModel.getColumnName(c)
//				+ " CellEditable:"+dataModel.isCellEditable(r, c)
    			+ " CellRenderer:"+this.getColumnModel().getColumn(c).getCellRenderer() + "/" + this.getDefaultRenderer(clazz)
        		+ " CellEditor:"+this.getColumnModel().getColumn(c).getCellEditor() + "/" + this.getDefaultEditor(clazz));
        	if(clazz==Boolean.class) {
        		DefaultCellEditor editor = new BooleanEditor();
        		editor.setClickCountToStart(1);
//        		this.getColumnModel().getColumn(c).setCellEditor(editor); // funktioniert so nicht! aber so:     		
        		this.getColumnModel().getColumn(c).setCellEditor(new CellEditorAndRenderer(null, editor));
        		
            } else if(clazz==Integer.class) {
            	NumberEditor editor = new NumberEditor(); //new GossipNumberEditor();
        		// test
        		if(dataModel.isCellEditable(0, c)) {
        			// OK
        		} else {
//        			Component component = editor.getTableCellEditorComponent(this, Integer.valueOf(0), true, 0, c);
//        			component.setVisible(false);
//        			editor.setVisible(false); // TODO dann ist es ja kein editor, sondern ein Renderer!!!!!!!!!!!!!!!
        			// TODO am renderer soll man erkennen, ob es einen editor gibt!! ==> einen anderen renderer für diese Celle
        			// hat auswirkungen auf Highlighter!!!!
        			this.getColumnModel().getColumn(c).setCellRenderer(new GossipTableRenderer());
        		}
    			
        		this.getColumnModel().getColumn(c).setCellEditor(new CellEditorAndRenderer(null, editor));
        		
//        	} else if(clazz==String.class) {
//        		// setzen BooleanEditor für diese Spalte
//        		DefaultCellEditor editor = new DefaultCellEditor(final JTextField textField ??? weches);
//        		editor.setClickCountToStart(1);
////        		this.getColumnModel().getColumn(c).setCellEditor(editor);
//        		// funktioniert nicht!
//        		
//        		// anders:
//        		this.getColumnModel().getColumn(c).setCellEditor(new CellEditorAndRenderer(editor));
        	}
        }		
//		}
        isSetColumnEditorsSet = true;
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
