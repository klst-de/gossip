package com.klst.gossip;

import java.util.List;
import java.util.logging.Logger;

import org.compiere.model.GridField;
import org.compiere.model.Lookup;
import org.compiere.model.MAccountLookup;
import org.compiere.model.MImage;
import org.jdesktop.swingx.table.TableColumnExt;

// Kapselt GridField, das wiederum GridFieldVO enthält
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

		setIdentifier(this.field.getColumnName()); //  ==> Object identifier
		setHeaderValue(this.field.getHeader());
//		setEditable(this.field.isEditable(true)); // always checkContext
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
	
    public boolean isEditable(boolean checkContext) { // in super TableColumnExt gibt es isEditable() ohne para !
        return field.isEditable(true); // always checkContext
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
        return field.getDisplayType();
    }
	
    public int getWindowNo() {
        return field.getWindowNo();
    }
	
    public int getIncluded_Tab_ID() {
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
		stringBuilder.append("Field_IDe=").append(getAD_Field_ID()); 
		stringBuilder.append(", isDisplayed=").append(isDisplayed()); 
		stringBuilder.append(", isDisplayedGrid=").append(isDisplayedGrid()); 
		stringBuilder.append("] ");
		stringBuilder.append(this.field.toStringX());
		return stringBuilder.toString();	
    }

}
