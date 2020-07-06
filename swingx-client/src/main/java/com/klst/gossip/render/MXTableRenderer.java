package com.klst.gossip.render;

import java.awt.Component;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

import javax.swing.JTable;

import org.compiere.model.GridField;
import org.compiere.model.GridTable;
import org.compiere.model.Lookup;
import org.compiere.model.MRefList;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.NamePair;
import org.jdesktop.swingx.renderer.ComponentProvider;
import org.jdesktop.swingx.renderer.DefaultTableRenderer;
import org.jdesktop.swingx.renderer.JRendererCheckBox;
import org.jdesktop.swingx.renderer.JRendererLabel;
import org.jdesktop.swingx.renderer.LabelProvider;
import org.jdesktop.swingx.renderer.StringValue;
import org.jdesktop.swingx.table.DefaultTableColumnModelExt;

import com.klst.gossip.MXTable;

public class MXTableRenderer extends DefaultTableRenderer { // extends (swingx)AbstractRenderer implements (swing)TableCellRenderer

	private static final long serialVersionUID = -1911708055572460800L;
	private static final Logger LOG = Logger.getLogger(MXTableRenderer.class.getName());

    public MXTableRenderer(GridTable gtModel) {
    	super();
    }
  
/*

in package org.jdesktop.swingx.renderer gibt es folgende renderer
-                 abstract class ComponentProvider<T extends JComponent>
- class LabelProvider    extends ComponentProvider<JLabel> : A component provider which uses a JLabel as rendering component.
- class CheckBoxProvider extends ComponentProvider<AbstractButton>: A component provider which uses a JCheckBox. Nicht nur für Boolean! 
- public class HyperlinkProvider extends ComponentProvider<JXHyperlink> implements RolloverRenderer
... dann gibt es noch die Klassen in package org.jdesktop.swingx.rollover

 */
    public MXTableRenderer(StringValue converter) {
    	super(new LabelProvider(converter));
    }

//        public MXTableRenderer(StringValue converter, int alignment) {
//            super(new MyLabelProvider(converter, alignment)); // org.jdesktop.swingx.renderer.LabelProvider.LabelProvider(StringValue converter, int alignment)
//        }
        
    public MXTableRenderer(ComponentProvider<?> componentProvider) {
        super(componentProvider);
    }

    /**
     * {@inheritDoc}
     */
    // methode ist definiert in interface javax.swing.table.TableCellRenderer
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
/*

column : nur die Displayed, dh 0 == field 2, da die ersten beden nicht angezeigt werden
field.getDisplayType | col WorkflowActivities | value.getClass()=Integer  value.getClass()=String  value.getClass()=Boolean
 /Reference_Value_ID
11                      0N DynPriorityStart       java.lang.Integer                                                          0/0 value:11
20                      1N IsActive                                       /class java.lang.String
19                      2  AD_Client_ID          java.lang.Integer
                        ...
16                      7  Created              /class java.sql.Timestamp                                                    5/13 value:2008-09-22 13:44:58.0 of type class java.sql.Timestamp 

17 /305                 8  WFState                                        java.lang.String                                   6/13 value:OS
                        ...
20                      19 Processed                                      /class java.lang.String                            value:false of type class java.lang.Boolean

 */
    	
    	// (swingx) implementation in org.jdesktop.swingx.renderer.DefaultTableRenderer:
    	Component cellRendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    	
    	if(table instanceof MXTable) {
    		DefaultTableColumnModelExt tcme = (DefaultTableColumnModelExt)(table.getColumnModel());
    		GridField field = (GridField)(tcme.getColumn(column).getIdentifier());
    		int displayType = field.getDisplayType();
        	
			switch(displayType) {
//			case DisplayType.Date: // 15 Date
//				field.setDisplayType(DisplayType.Date); // ohne Time
//				minitable.setColumnClass(f, field);
//				break;
			case DisplayType.DateTime: // 16 Created, DateDoc
				if(value!=null) {
					JRendererLabel dateTime = new JRendererLabel();
					Timestamp ts = (Timestamp)value;
					SimpleDateFormat simpleDateFormat = DisplayType.getDateFormat(displayType); // wg. I18N 					
					dateTime.setText(simpleDateFormat.format(ts)); // TODO Spaltenbreite
					cellRendererComponent = dateTime;
				}
				break;
			case DisplayType.String:   // 10 DocumentNo
				
				break;
//			case DisplayType.ID:       // 13 C_BPartner.C_BPartner_ID
			case DisplayType.List:     // 17 DocStatus
//				boolean optional = false; //field.isMandatory(checkContext); // auch ein leerer Eintrag ist dabei
//				ValueNamePair[] valueName = MRefList.getList(Env.getCtx(), field.getAD_Reference_Value_ID(), optional);
				MRefList mRefList = MRefList.get(Env.getCtx(), field.getAD_Reference_Value_ID(), value.toString(), null);
//				LOG.config("mRefList:"+mRefList);
				JRendererLabel label = new JRendererLabel();
				label.setText(mRefList.getName()); // ==.toString()
				cellRendererComponent = label;
				break;
//			case DisplayType.Table:    // 18 CreatedBy, UpdatedBy
			case DisplayType.TableDir: // 19 AD_Client_ID, ...
				LOG.config("\nTableDir C/R:"+column+"/"+row + " value:" + value + " of type " + (value==null ? "null" : value.getClass()) 
						+ " AD_Column_ID="+field.getAD_Column_ID() + " Lookup:"+field.getLookup() );
				if(value!=null) {
					JRendererLabel tdLabel = new JRendererLabel();
					Integer key = (Integer)value; 
					Lookup lookup = field.getLookup(); // MutableComboBoxModel
					LOG.config("\nTableDir C/R:"+column+"/"+row + " value:" + value + " of type " + (value==null ? "null" : value.getClass()) 
							+ " AD_Column_ID="+field.getAD_Column_ID() + " Lookup:"+lookup );
					NamePair np = lookup.get(key);
					//np.getID(); np.getName()
					tdLabel.setText(np.getName()); // ==.toString()
					cellRendererComponent = tdLabel; // TODO: Spaltenbreite und editor wenn selektiert
				}
				break;
			case DisplayType.YesNo:    // 20
				JRendererCheckBox checkbox = new JRendererCheckBox();				
				checkbox.setSelected((Boolean)value);
				cellRendererComponent = checkbox; // TODO ist noch linksbündig
				break;
//			case DisplayType.Location: // 21 Location TODO
//				field.setDisplayType(DisplayType.TableDir);
//				minitable.setColumnClass(f, field);
//				break;
			default:
	        	LOG.config(table 
	        			+ "\n"+column+"/"+row + " value:" + value + " of type " + (value==null ? "null" : value.getClass()) + " Reference_Value_ID:" + field.getAD_Reference_Value_ID()+ " displayType:" + displayType
//	        			+ "\ncomponent:"+component
	        			);
			}
    	}
    	return cellRendererComponent;
    }

}
