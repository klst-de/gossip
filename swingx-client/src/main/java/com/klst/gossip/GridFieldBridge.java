package com.klst.gossip;

import java.util.List;
import java.util.logging.Logger;

import org.compiere.model.GridField;
import org.compiere.model.Lookup;
import org.compiere.model.MAccountLookup;
import org.compiere.model.MImage;
import org.jdesktop.swingx.table.TableColumnExt;

// Kapselt GridField, das wiederum GridFieldVO mit public membern enthält
public class GridFieldBridge extends TableColumnExt { // TableColumnExt extends TableColumn

	private static final long serialVersionUID = 4112077272210070373L;

	private static final Logger LOG = Logger.getLogger(GridFieldBridge.class.getName());
	
	private GridField field = null;
	private int displayType = 0; //-1;
	
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

		setIdentifier(this.field.getColumnName()); //  ==> Object identifier
		setHeaderValue(this.field.getHeader());
//		setEditable(this.field.isEditable(true)); // always checkContext
	}
	public GridFieldBridge(String columnName, String header, int displayType) {
		super();
		this.field = null;
		this.displayType = displayType;

		setIdentifier(columnName);
		setHeaderValue(header);
	}
	
	Object getValue() {
		return field.getValue();
	}
	
	void setValue(Object newValue, boolean inserting) {
		if(field!=null) field.setValue(newValue, inserting);
	}
	
	public int getAD_Column_ID() {
		return field.getAD_Column_ID();
	}
	
	public int getAD_Window_ID() {
		return field.getAD_Window_ID();
	}
	
	public int getAD_Tab_ID() {
		return field.getAD_Tab_ID();
	}
	
	public int getAD_Field_ID() {
		if(field==null) return -1;
		return field.getAD_Field_ID();
	}
	
	public int getPreferredWidthInListView() { // es gibt in TableColumn.getPreferredWidth() !
		return field.getPreferredWidthInListView(); // field.getPreferredWidth();
	}
	
	public int getDisplayLength() {
		return field.getDisplayLength(); // field.getDisplayLength();
	}
	
	public int getSortNo() {
		return field.getSortNo();
	}

	public int getFieldLength() {
		return field.getFieldLength();
	}

	public int getAD_Process_ID() {
		return field.getAD_Process_ID();
	}

	public int getAD_Image_ID() {
		return field.getAD_Image_ID();
	}

	public int getAD_Chart_ID() {
		return field.getAD_Chart_ID();
	}

	public int getAD_Reference_Value_ID(){
		return field.getAD_Reference_Value_ID();
	}
	
    public String getColumnName() {
    	if(field==null) return (String)getIdentifier();
        return field.getColumnName(); // oder (String)this.getIdentifier();
    }
	
    public String getDescription() {
    	if(field==null) return (String)getIdentifier();
        return field.getDescription();
    }
	
    public String getHeader() { 
        return (String)this.getHeaderValue();
    }
	
    public String getColumnSQL() {
//    	LOG.config(""+field);
    	if(field==null) return (String)getIdentifier();
        return field.getColumnSQL(true); //	ColumnName or Virtual Column // boolean withAS
    }
	
    public boolean isEditable(boolean checkContext) { // in super TableColumnExt gibt es isEditable() ohne para !
    	if(field==null) return false;
        return field.isEditable(true); // always checkContext
    }
	
    public boolean isMandatory(boolean checkContext) {
    	if(field==null) return false;
        return field.isMandatory(true); // checkContext
    }
	
    public boolean isDisplayed() {
    	if(field==null) return true;
        return field.isDisplayed();
    }
	
    public boolean isDisplayedGrid() {
        return field.isDisplayedGrid();
    }
	
    public boolean isFieldOnly() {
        return field.isFieldOnly();
    }
	
    public boolean isHeading() {
    	if(field==null) return false;
        return field.isHeading();
    }
	
    public boolean isReadOnly() {
    	if(field==null) return true;
        return field.isReadOnly();
    }
	
    public boolean isUpdateable() {
    	if(field==null) return false;
        return field.isUpdateable();
    }
	
    public boolean isEncryptedColumn() {
    	if(field==null) return false;
        return field.isEncryptedColumn();
    }
	
    public boolean isSameLine() {
    	if(field==null) return false;
        return field.isSameLine();
    }
	
    public boolean isLongField() {
        return field.isLongField();
    }
	
    public boolean isAutocomplete() {
        return field.isAutocomplete();
    }
	
    public List<String> getEntries() {
    	return field.getEntries();
    }
    
    public int getSeqNoGrid() { // ist immer 0!
        return field.getSeqNoGrid();
    }
	
    public int getDisplayType() {
    	if(field==null) return this.displayType;
        return field.getDisplayType();
    }
	
    public int getWindowNo() {
    	if(field==null) return -1; // TODO interim
        return field.getWindowNo();
    }
	
    public int getIncluded_Tab_ID() {
    	if(field==null) return -1; // TODO interim
    	return field.getIncluded_Tab_ID();
    }

    public String getValueMax() {
        return field.getValueMax();
    }
    public String getValueMin() {
        return field.getValueMin();
    }
    
    public String getVFormat() {
    	return field.getVFormat();
    }
    
    public String getObscureType() {
    	return field.getObscureType();
    }
    
    public String getHelp() {
    	return field.getHelp();
    }
    
    public Lookup getLookup() {
    	return field.getLookup();
    }
    
    public MAccountLookup getAccountLookup() {
    	return new MAccountLookup(field.getVO().ctx, field.getWindowNo());
    }
    
    public MImage getImage() {
    	field.getVFormat();
    	return MImage.get(field.getVO().ctx, field.getAD_Image_ID());
    }
    
    public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("GridFieldBridge[");
//		stringBuilder.append("Value=").append(getValue()); 
		if(field==null) {
			stringBuilder.append("ColumnName=").append(getColumnName()); 
			stringBuilder.append(", DisplayType=").append(getDisplayType()); 
			stringBuilder.append("] ");		
		} else {
			stringBuilder.append("Field_ID=").append(getAD_Field_ID()); 
			stringBuilder.append(", isDisplayed=").append(isDisplayed()); 
			stringBuilder.append(", isDisplayedGrid=").append(isDisplayedGrid()); 
			stringBuilder.append("] ");
			stringBuilder.append(this.field.toStringX());		
		}
		return stringBuilder.toString();	
    }

}
