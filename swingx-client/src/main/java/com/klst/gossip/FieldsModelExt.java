package com.klst.gossip;

import java.util.logging.Logger;

import javax.swing.table.TableCellRenderer;

import org.compiere.model.GridField;
import org.compiere.model.GridTable;
import org.jdesktop.swingx.table.DefaultTableColumnModelExt;
import org.jdesktop.swingx.table.TableColumnExt;
import org.jdesktop.swingx.table.TableColumnModelExt;

import com.klst.gossip.render.MXTableRenderer;

public class FieldsModelExt extends DefaultTableColumnModelExt // extends DefaultTableColumnModel implements TableColumnModel, ropertyChangeListener, ListSelectionListener, Serializable
                         implements TableColumnModelExt {

	private static final long serialVersionUID = 5274896514373522577L;
	private static final Logger LOG = Logger.getLogger(FieldsModelExt.class.getName());

	private FieldsModelExt() {
        super();
    }

	public FieldsModelExt(GridTable gridTable) {
		this(gridTable.getFields());
	}
	public FieldsModelExt(GridField[] anArray) {
		this();
		for(int modelIndex = 0; modelIndex < anArray.length; modelIndex++) {
//      for (GridField gf : anArray) {
			GridField field = anArray[modelIndex];
			LOG.config("GridField["+modelIndex+"]="+field);
        	// aus GridField TableColumn bzw TableColumnExt machen
    		// ...
    		// addColumn(TableColumn aColumn)
			
			boolean isVisible = field.isDisplayed() & field.isDisplayedGrid(); // nur fields anzeigen, die isDisplayed UND isDisplayedGrid sind
			
			// @see https://stackoverflow.com/questions/258486/calculate-the-display-width-of-a-string-in-java
			int width = field.getPreferredWidthInListView(); // m_vo.PreferredWidth in chars
			field.getDisplayLength(); // m_vo.DisplayLength in chars
			field.getFieldLength(); // m_vo.FieldLength in chars
			// 75);    // default pixel width of view column taken from javax.swing.table.TableColumn
			// es gibt noch den ctor mit pixel width
			TableColumnExt aColumn = new TableColumnExt(modelIndex);
			
			TableCellRenderer cellRenderer = new MXTableRenderer();
			// und TableColumnExt aColumn = new TableColumnExt(f, width, cellRenderer, cellEditor)
			aColumn.setCellRenderer(cellRenderer);
			
			aColumn.setIdentifier(field); // GridField wird zum Identifier!!!
			addColumn(aColumn);
			
			aColumn.setVisible(isVisible);
        }

	}

	
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//	public TableColumnExt getColumnExt(int columnIndex) {
//        TableColumn column = getColumn(columnIndex);
//        if (column instanceof TableColumnExt) {
//            return (TableColumnExt) column;
//        }
//        return null;
//	}
}
