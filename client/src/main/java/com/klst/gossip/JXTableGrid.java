package com.klst.gossip;

import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.table.JTableHeader;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTableHeader;

public class JXTableGrid extends JXTable { // JXTable extends JTable implements TableColumnModelExtListener
//	implements TableCellEditor 

	private static final long serialVersionUID = 4527635643876059507L;
	
	private static final Logger LOG = Logger.getLogger(JXTableGrid.class.getName());

	// ctor use factory method createXTable()
	private JXTableGrid() {
		super();
	}
	
	// aus org.jdesktop.swingx.demos.table.XTableDemo
	protected static JXTable createXTable() {
		JXTable table = new JXTableGrid();
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

}
