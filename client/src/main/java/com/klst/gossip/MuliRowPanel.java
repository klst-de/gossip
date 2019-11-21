package com.klst.gossip;

import java.util.logging.Logger;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import org.compiere.model.GridTab;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTableHeader;
import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.decorator.HighlighterFactory;

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
	// - Grid lines
	private static final boolean showHorizontalLines = true;
	private static final boolean showVerticalLines = true;
	// - Highlighter aka Decorator, die org.jdesktop.swingx.decorator.HighlighterFactory bietet mehrere an
	//   SimpleStriping, AlternateStriping, ... siehe dort
	private static Highlighter highlighter = HighlighterFactory.createAlternateStriping();
			
	// factory method aus org.jdesktop.swingx.demos.table.XTableDemo , erweitert, wird in Tab gebraucht
	// dataModel ist GenericDataModel
	// TODO wieso gridTab - es ist doch in GenericDataModel gekapselt
	protected static MuliRowPanel createXTable(TableModel dataModel, GridTab gridTab) {
		return new MuliRowPanel(dataModel, gridTab);
	}

	// ctor use factory method createXTable()
	private MuliRowPanel() {
		super();
	}

	private MuliRowPanel(TableModel dataModel, GridTab gridTab) {
		super(dataModel); // es gibt noch ctor in super mit TableColumnModel: (TableModel dm, TableColumnModel cm) und andere

		setColumnControl(new TableColumnControlButton(this)); // TableColumnControlButton tauscht das Icon
		setColumnControlVisible(true); // column control to the trailing corner of the scroll pane 

		// replace grid lines with striping 
		setShowGrid(showHorizontalLines, showVerticalLines);
		addHighlighter(highlighter);
		
		setDefaultRenderer(Object.class, new GenericTableRenderer());
//		super.dataModel.isCellEditable(rowIndex, columnIndex)
//		super.dataModel.getColumnCount();
//		super.dataModel.getRowCount();
		LOG.config(dataModel.getColumnCount()+"/"+dataModel.getRowCount()); 
	}

	JXTableHeader jXTableHeader;
	protected JTableHeader createDefaultTableHeader() {
		//this.columnModel.getColumn(0).setHeaderValue("A"); // set first column
		for(int c = 0; c < columnModel.getColumnCount(); c++) {
			columnModel.getColumn(c).setHeaderValue( ((GenericDataModel)dataModel).getHeaderValue(c) );
		}

		jXTableHeader = new GenericTableHeader(columnModel);
		return jXTableHeader;	
	}

	// get the rendering component for the given cell
//	public Component prepareRenderer(int row, int col) {
//		Component stamp = super.prepareRenderer(row, col); // the decorated Component used as a stamp to renderthe specified cell
//		LOG.config(col+"/"+row + " stamp:"+stamp);
//		if(stamp instanceof JLabel) {
//			((JLabel)stamp).setForeground(Color.LIGHT_GRAY);
//		}
//		return stamp;		
//	}
}
