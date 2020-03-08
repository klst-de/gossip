package com.klst.gossip;

import java.awt.Dimension;
import java.util.logging.Logger;

import javax.swing.JPanel;

import org.compiere.grid.VPanel;
import org.compiere.grid.ed.VDate;
import org.compiere.grid.ed.VEditor;
import org.compiere.grid.ed.VLookup;
//import org.compiere.model.GridField;
//import org.compiere.model.Lookup;
import org.compiere.util.DisplayType;
import org.jdesktop.swingx.table.TableColumnModelExt;

public class SingleRowPanel extends JPanel {

	private static final long serialVersionUID = -422266738393039114L;
	
	private static final Logger LOG = Logger.getLogger(SingleRowPanel.class.getName());
	
	private GenericDataModel tableModel;
	private int currentRow = -1;
	// kapselt
	VPanel vPanel;
	
	// ctor
//	public SingleRowPanel() {
//		this(null);
//	}
	public SingleRowPanel(GenericDataModel tm) {
		this(tm, new VPanel(tm.getName(), tm.getWindowNo()));
	}
	private SingleRowPanel(GenericDataModel tm, VPanel vPanel) {
		super(); // default ist FlowLayout
		this.tableModel = tm;
		this.vPanel = vPanel;
	}
	
	public Dimension getSingleRowPanelSize() {
		TableColumnModelExt fields = tableModel.getColumns();
		for(int f=0; f<fields.getColumnCount(true); f++) {
//			GridField field = fields[f];
			GridFieldBridge field = (GridFieldBridge)fields.getColumn(f);
			LOG.config("field# "+f +"/"+fields.getColumnCount()+"=?="+fields.getColumnCount(true));
			if(field.isDisplayed()) {
				VEditor editor = getEditor(field); // factory TODO
//				field.addPropertyChangeListener(editor); ????????????????
				vPanel.addFieldBufferedXXX(editor, field);
			} else {
				LOG.warning("NOT Displayed field# "+f);
			}
		}
		add(vPanel);
		Dimension dim = getPreferredSize();
		LOG.config("interim return "+dim);
		return dim;
	}
	
	public void showSingleRowPanelSize(int rowIndex) {
		LOG.warning("rowIndex="+rowIndex);
		TableColumnModelExt fields = tableModel.getColumns();
//		GridField[] fields = tableModel.getColumns();
		for(int f=0; f<fields.getColumnCount(true); f++) {
//			GridField field = fields[f];
			GridFieldBridge field = (GridFieldBridge)fields.getColumn(f);
			if(field.isDisplayed()) {
//				Object o = this.tableModel.getValueAt(rowIndex, f);
//				if(o!=null) LOG.info("fieldno:"+f + " value:"+this.tableModel.getValueAt(rowIndex, f).toString());
				VEditor editor = getEditor(field); // factory TODO
//				field.addPropertyChangeListener(editor); ????????????????
				vPanel.addFieldBufferedXXX(editor, field);
			} else {
				LOG.warning("NOT Displayed field# "+f);
			}
		}
		add(vPanel);	
	}

	/*
	public static final int String     = 10;
	public static final int Integer    = 11;
	public static final int Amount     = 12;
	public static final int ID         = 13; Lookup
	public static final int Text       = 14;
	public static final int Date       = 15;
	public static final int DateTime   = 16;
	public static final int List       = 17; Lookup
	public static final int Table      = 18; Lookup
	public static final int TableDir   = 19; Lookup
	public static final int YesNo      = 20;
	public static final int Location   = 21;
	public static final int Number     = 22;
	public static final int Binary     = 23;
	public static final int Time       = 24;
	public static final int Account    = 25;
	public static final int RowID      = 26;
	public static final int Color      = 27;
	public static final int Button	   = 28;
	public static final int Quantity   = 29;
	public static final int Search     = 30; Lookup
	public static final int Locator    = 31;
	public static final int Image      = 32;
	public static final int Assignment = 33;
	public static final int Memo       = 34;
	public static final int PAttribute = 35;
	public static final int TextLong   = 36;
	public static final int CostPrice  = 37;
	public static final int FilePath   = 38;
	public static final int FileName   = 39;
	public static final int URL        = 40;
	public static final int PrinterName= 42;
	public static final int Chart           = 53370;
	public static final int FilePathOrName  = 53670;

 */
	private VEditor getEditor(GridFieldBridge mField) { // TODO mField in field umbenennen
		LOG.config(mField.toString());
		if (mField == null)
			return null; // gut ist das nicht
		
		VEditor editor = null;
		int displayType = mField.getDisplayType();
		String columnName = mField.getColumnName();
		boolean mandatory = mField.isMandatory(false);      //  no context check
		boolean readOnly = mField.isReadOnly();
		boolean updateable = mField.isUpdateable();
		int WindowNo = mField.getWindowNo();
		LOG.config("displayType:"+displayType
				+ " columnName:"+columnName
				+ " mandatory:"+mandatory
				+ " readOnly:"+readOnly
				+ " updateable:"+updateable
				+ " WindowNo:"+WindowNo
				+ " Not a Field:"+mField.isHeading()
				);
		
		//  Not a Field
		if (mField.isHeading())
			return null;

		//	Lookup (displayType == List || displayType == Table || displayType == TableDir || displayType == Search)
		if (DisplayType.isLookup(displayType) || displayType == DisplayType.ID)
		{
//			VLookup vl = new VLookup(columnName, mandatory, readOnly, updateable, mField.getLookup());
			VLookup vl = new VLookup(columnName, mandatory, readOnly, updateable, null);
			vl.setName(columnName);
			vl.setField (mField);
			editor = vl;
		}
		//	YesNo - BooleanEditor
//		else if (displayType == DisplayType.YesNo)
//		{
//			VCheckBox vc = new VCheckBox(columnName, mandatory, readOnly, updateable, mField.getHeader(), mField.getDescription(), tableEditor);
//			vc.setName(columnName);
//			vc.setField (mField);
//			editor = vc;
//		}

		//	Date
		else if (DisplayType.isDate(displayType))
		{
			if (displayType == DisplayType.DateTime)
				readOnly = true;
			VDate vd = new VDate(columnName, mandatory, readOnly, updateable, displayType, (String)mField.getHeaderValue());
			vd.setName(columnName);
			vd.setField (mField);
			editor = vd;
		}

		else {
			LOG.warning(columnName + " - Unknown Type: " + displayType + " ersatzweise VLookup!!!");
//			Lookup getLookup = mField.getLookup();
//			LOG.warning("ignoriert: " + getLookup + " ersatzweise null");
			VLookup vl = new VLookup(columnName, mandatory, readOnly, updateable, null);
			vl.setName(columnName);
			vl.setField (mField);
			editor = vl;

		}

		return editor;
	}

}
