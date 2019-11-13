package com.klst.gossip;

import java.util.logging.Logger;

import org.compiere.model.GridField;
import org.jdesktop.swingx.table.TableColumnExt;

public class GridFieldBridge extends TableColumnExt { // TableColumnExt extends TableColumn

	private static final long serialVersionUID = 4112077272210070373L;

	private static final Logger LOG = Logger.getLogger(GridFieldBridge.class.getName());
	
	private GridField field = null;
	
	// TableColumn ctors:
    //public TableColumn() 
    //public TableColumn(int modelIndex)
	//public TableColumn(int modelIndex, int width)
    //public TableColumn(int modelIndex, int width, TableCellRenderer cellRenderer, TableCellEditor cellEditor)
	
	// TableColumnExt ctors:
	//public TableColumnExt()
	//public TableColumnExt(int modelIndex)
	//public TableColumnExt(int modelIndex, int width)
    //public TableColumnExt(int modelIndex, int width, TableCellRenderer cellRenderer, TableCellEditor cellEditor) 
	//public TableColumnExt(TableColumnExt columnExt)

	public GridFieldBridge(GridField field) {
		super();
		this.field = field;
//		this.field.getAD_Field_ID() // ? ==> modelIndex
//		this.field.getHeader() // ? ==> Object headerValue
		//Object identifier
		//this.field.getDisplayType()

		setIdentifier(this.field.getColumnName()); //  ==> Object identifier
		setHeaderValue(this.field.getHeader());
		setEditable(this.field.isEditable(false)); // checkContext
	}
	
    public String getColumnName() {
        return field.getColumnName(); // oder (String)this.getIdentifier();
    }
	
    public String getDescription() {
        return field.getDescription();
    }
	
    public String getHeader() { 
        return (String)this.getHeaderValue();
    }
	
    public String getColumnSQL() {
        return field.getColumnSQL(true); //	ColumnName or Virtual Column // boolean withAS
    }
	
    public boolean isEditable(boolean checkContext) {
        return field.isEditable(true); // checkContext
    }
	
    public boolean isMandatory(boolean checkContext) {
        return field.isMandatory(true); // checkContext
    }
	
    public boolean isDisplayed() {
        return field.isDisplayed();
    }
	
    public boolean isDisplayedGrid() {
        return field.isDisplayedGrid();
    }
	
    public boolean isFieldOnly() {
        return field.isFieldOnly();
    }
	
    public boolean isHeading() {
        return field.isHeading();
    }
	
    public boolean isReadOnly() {
        return field.isReadOnly();
    }
	
    public boolean isUpdateable() {
        return field.isUpdateable();
    }
	
    public boolean isEncryptedColumn() {
        return field.isEncryptedColumn();
    }
	
    public boolean isSameLine() {
        return field.isSameLine();
    }
	
    public boolean isLongField() {
        return field.isLongField();
    }
	
    public int getSeqNoGrid() { // ist immer 0!
        return field.getSeqNoGrid();
    }
	
    public int getDisplayType() {
        return field.getDisplayType();
    }
	
    public int getWindowNo() {
        return field.getWindowNo();
    }
	
    public int getIncluded_Tab_ID() {
        return field.getIncluded_Tab_ID();
    }

    public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("GridFieldBridge[field="+this.field.toString());
		stringBuilder.append(", DisplayType="+getDisplayType()); 
		stringBuilder.append(", isDisplayed="+isDisplayed()); 
		stringBuilder.append(", isDisplayedGrid="+isDisplayedGrid()); 
		
		stringBuilder.append("]");
		return stringBuilder.toString();	
    }

}
