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

	// Returns the size in PICA of the string, given space is 200 and 'W' is 1000.
	// see https://p2p.wrox.com/access/32197-calculate-character-widths.html
	// 1 pixel (X) == 0.0625 pica (computer)
	static int picaSize(String s) {
	    // the following characters are sorted by width in Arial font
	    String lookup = " .:,;'^`!|jl/\\i-()JfIt[]?{}sr*a\"ce_gFzLxkP+0123456789<=>~qvy$SbduEphonTBCXY#VRKZN%GUAHD@OQ&wmMW";
	    int result = 0;
	    for (int i = 0; i < s.length(); ++i)
	    {
	        int c = lookup.indexOf(s.charAt(i));
	        result += (c < 0 ? 60 : c) * 7 + 200;
	    }
	    return result;
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
		int headerWidth = (int) (0.0625 * picaSize(header))/3;
		String wDisplayLength = new String(new char[field.getDisplayLength()]).replace('\0', 'W');
		int iDisplayLength = (int) (0.0625 * picaSize(wDisplayLength));
		String wFieldLength = new String(new char[field.getFieldLength()]).replace('\0', 'M');
		int iFieldLength = (int) (0.0625 * picaSize(wFieldLength));
		String wMAXDISPLAY_LENGTH = new String(new char[GridField.MAXDISPLAY_LENGTH]).replace('\0', ' ');
		int iMAXDISPLAY_LENGTH = (int) (0.0625 * picaSize(wMAXDISPLAY_LENGTH));
		LOG.config("field '"+field.getColumnName() + "'/"+header+"/"+headerWidth+": minOf 75, DisplayLength()="+iDisplayLength + ", FieldLength()="+iFieldLength + ", MAXDISPLAY_LENGTH="+iMAXDISPLAY_LENGTH);
		int min = Math.min(Math.min(iDisplayLength, iFieldLength), iMAXDISPLAY_LENGTH)/3;
		
		return Math.max(min, headerWidth);
	}

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
//			int width = field.getPreferredWidthInListView(); // m_vo.PreferredWidth in chars
//			field.getDisplayLength(); // m_vo.DisplayLength in chars
//			field.getFieldLength(); // m_vo.FieldLength in chars
//			// 75);    // default pixel width of view column taken from javax.swing.table.TableColumn
//			// es gibt noch den ctor mit pixel width
			TableColumnExt aColumn = new TableColumnExt(modelIndex);
			
			TableCellRenderer cellRenderer = new MXTableRenderer();
			// und TableColumnExt aColumn = new TableColumnExt(f, width, cellRenderer, cellEditor)
			aColumn.setCellRenderer(cellRenderer);
			
			aColumn.setIdentifier(field); // GridField wird zum Identifier!!!
			addColumn(aColumn);
			
			aColumn.setVisible(isVisible);
			aColumn.setHeaderValue(field.getHeader()); // TODO es gibt TableColumn.sizeWidthToFit()
			
			int width = calculateWidth(field);
			LOG.config("setPreferredWidth to "+width);
			aColumn.setPreferredWidth(width);
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
