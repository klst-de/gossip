package com.klst.gossip.render;

import java.awt.Component;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import org.compiere.model.GridField;
import org.compiere.model.Lookup;
import org.compiere.model.MRefList;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.NamePair;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.renderer.ComponentProvider;
import org.jdesktop.swingx.renderer.DefaultTableRenderer;
import org.jdesktop.swingx.renderer.JRendererCheckBox;
import org.jdesktop.swingx.renderer.JRendererLabel;
import org.jdesktop.swingx.renderer.LabelProvider;
import org.jdesktop.swingx.renderer.StringValue;
import org.jdesktop.swingx.table.DefaultTableColumnModelExt;
import org.jdesktop.swingx.table.TableColumnModelExt;

import com.klst.gossip.MXTable;
import com.klst.gossip.wrapper.GridTableModel;
import com.klst.icon.AbstractImageTranscoder;

public class MXTableRenderer extends DefaultTableRenderer { // extends (swingx)AbstractRenderer implements (swing)TableCellRenderer

	private static final long serialVersionUID = -1911708055572460800L;
	private static final Logger LOG = Logger.getLogger(MXTableRenderer.class.getName());

    public MXTableRenderer() {
    	super();
//    	LOG.warning(""+this.toString());
    }
  
/*

in package org.jdesktop.swingx.renderer gibt es folgende renderer
-                 abstract class ComponentProvider<T extends JComponent>
- class LabelProvider    extends ComponentProvider<JLabel> : A component provider which uses a JLabel as rendering component.
- class CheckBoxProvider extends ComponentProvider<AbstractButton>: A component provider which uses a JCheckBox. Nicht nur für Boolean! 
- public class HyperlinkProvider extends ComponentProvider<JXHyperlink> implements RolloverRenderer
... dann gibt es noch die Klassen in package org.jdesktop.swingx.rollover

 */
    private GridTableModel gridTableModel = null;
    public MXTableRenderer(GridTableModel gtm) {
       	this();
       	gridTableModel = gtm;
       	// bsp: GridField field = gridTableModel.getGridField(5);
    }
    public MXTableRenderer(StringValue converter) {
    	super(new LabelProvider(converter));
    }

//        public MXTableRenderer(StringValue converter, int alignment) {
//            super(new MyLabelProvider(converter, alignment)); // org.jdesktop.swingx.renderer.LabelProvider.LabelProvider(StringValue converter, int alignment)
//        }
        
    public MXTableRenderer(ComponentProvider<?> componentProvider) {
        super(componentProvider);
    }

	static final int SMALL_ICON_SIZE = 16;
	static AbstractImageTranscoder AIT = AbstractImageTranscoder.getInstance();

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
    	// Beispiele:
    	// DisplayType.String/10 	any value
    	// DisplayType.Integer/11	any value
    	// DisplayType.Text/14  	any value
    	// DisplayType.DateTime/16 	value==null
    	Component cellRendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    	
    	if(table instanceof MXTable) {
    		TableColumnModelExt tcme = (DefaultTableColumnModelExt)(table.getColumnModel());
    		GridField field = null;
    		if(gridTableModel==null) {
    			field = (GridField)(tcme.getColumn(column).getIdentifier());
    		} else {
    			field = gridTableModel.getGridField(column);
    		}
     		int displayType = field.getDisplayType();
// TEST    		
//    		if(table.getColumnModel() instanceof DefaultTableColumnModelExt) {
//    			// OK
//    		} else {
//    			LOG.warning("NOT DefaultTableColumnModelExt"+table.getColumnModel());
//    		}
//    		TableColumn tc = tcme.getColumn(column);
//    		if(tc.getIdentifier() instanceof GridField) {
//    			// OK
//    		} else {
//    			LOG.warning("NOT GridField"+tc);
//    		}
//    		LOG.config("\nR/C:"+row+"/"+column + " displayType:" + displayType + " >>>>>>>>>>>>>>> value:" + value + " " + (value==null ? "null" : value.getClass())
//            		);
// <<< TEST
        	
			switch(displayType) {
			case DisplayType.String:   // 10 DocumentNo	==> Implementierung in Oberklasse
				// org.jdesktop.swingx.renderer.JRendererLabel extends JLabel implements PainterAware, IconAware
//				JRendererLabel jrl = (JRendererLabel)cellRendererComponent;
//				LOG.config("JRendererLabel Text:"+jrl.getText());
				break;
			case DisplayType.Integer:  // 11 Priority ==> Implementierung in Oberklasse				
				break;
			case DisplayType.Amount:   // 12 Credit Limit AD_Column_ID=2920		
				if(value!=null) {
					cellRendererComponent = getRenderer_Amount(value, field);
				}
				break;
			case DisplayType.ID:       // 13 C_BPartner.C_BPartner_ID ===> Kombination aus Button+TableDir TODO Lookup anders holen
				JRendererCheckBox id = new JRendererCheckBox();	
				if(value instanceof Integer) {
					Integer idkey = (Integer)value; 
					id.setText("#"+idkey.toString()+"#");				
				}
				if(value instanceof String) {
					id.setText((String)value);				
				}
				id.setIcon(null);
				cellRendererComponent = id;
				break;
			case DisplayType.Text:     // 14 Text Message AD_Column_ID=10808 ==> Implementierung in Oberklasse
				break;
			case DisplayType.Date:     // 15 TODO // ohne Time
				break;
			case DisplayType.DateTime: // 16 Created, DateDoc
				if(value!=null) {
//					JRendererLabel dateTime = new JRendererLabel();
//					Timestamp ts = (Timestamp)value;
//					SimpleDateFormat simpleDateFormat = DisplayType.getDateFormat(displayType); // wg. I18N 					
//					dateTime.setText(simpleDateFormat.format(ts)); // TODO Spaltenbreite
//					cellRendererComponent = dateTime;
					cellRendererComponent = getRenderer_DateTime(value, field);
				}
				break;
			case DisplayType.List:     // 17 DocStatus
				if(value!=null) {
					MRefList mRefList = MRefList.get(Env.getCtx(), field.getAD_Reference_Value_ID(), value.toString(), null);
//					LOG.config("mRefList:"+mRefList);
					JRendererLabel label = new JRendererLabel();
					label.setText(mRefList.getName()); // ==.toString()
					cellRendererComponent = label;
				}
				break;
			case DisplayType.Table:    // 18 AD_Language, CreatedBy, UpdatedBy
				if(value!=null) {
					cellRendererComponent = getRenderer_Table(value, field);
				}
			case DisplayType.TableDir: // 19 AD_Client_ID, ...
				if(value!=null) {
					cellRendererComponent = getRenderer_TableDir(value, field);
				}
				break;
			case DisplayType.YesNo:    // 20
				cellRendererComponent = getRenderer_YesNo(value, field);
				break;
			case DisplayType.Location: // 21
//				field.setDisplayType(DisplayType.TableDir); // TODO zoom
				if(value!=null) {
					
					Lookup lookup = field.getLookup(); // MutableComboBoxModel
					Integer key = (Integer) value;
					NamePair np = lookup.getDirect(value, true, true); // nut used in Lookup.getDirect: boolean saveInCache, boolean cacheLocal
					
					Icon icon = AIT.getImageIcon(AIT.ZOOM, SMALL_ICON_SIZE);
					JXButton ic = new JXButton((np == null ? "<" + key + ">" : np.getName()), icon);	

//					ActionListener tut nicht! Untersuchen TODO
					ic.addActionListener(event -> { 
						LOG.config("Location key:"+key+" event:"+event);
					});
					
					cellRendererComponent = ic;
				}
				break;
			case DisplayType.Number:    // 22
				if(value!=null) {
					cellRendererComponent = getRenderer_Number(value, field);
				}
				break;
			case DisplayType.Button:    // 28
				JRendererCheckBox button = new JRendererCheckBox();				
				button.setText((String)value);
				button.setIcon(null);
				cellRendererComponent = button; // TODO Button ist linksbündig, rechts der Text
				break;
			case DisplayType.Quantity:  // 28	
				if(value!=null) {
					cellRendererComponent = getRenderer_Quantity(value, field);
				}
				break;
			case DisplayType.Search:    // 30 Table oder User/Contact AD_Column_ID=10443
//				LOG.config("\nR/C:"+row+"/"+column + " DisplayType.Search value:" + value + " of type " + (value==null ? "null" : value.getClass()) 
//						+ " Header="+field.getHeader() + " AD_Column_ID="+field.getAD_Column_ID() + " Lookup:"+field.getLookup() );
				if (value != null) {
					cellRendererComponent = getRenderer_Search(value, field);
				}
				break;
			case DisplayType.Locator:   // 31
//				field.setDisplayType(DisplayType.TableDir); // TODO zoom
				if(value!=null) {
					
					Lookup lookup = field.getLookup(); // MutableComboBoxModel
					Integer key = (Integer) value;
					NamePair np = lookup.getDirect(value, true, true); // nut used in Lookup.getDirect: boolean saveInCache, boolean cacheLocal
					
					Icon icon = AIT.getImageIcon(AIT.ZOOM, SMALL_ICON_SIZE);
					JXButton ic = new JXButton((np == null ? "<" + key + ">" : np.getName()), icon);	

//					ActionListener tut nicht! Untersuchen TODO
					ic.addActionListener(event -> { 
						LOG.config("Location key:"+key+" event:"+event);
					});
					
					cellRendererComponent = ic;
				}
				break;
			case DisplayType.PAttribute: // 35	
				if(value!=null) {
					cellRendererComponent = getRenderer_PAttribute(value, field); // TODO Überprüfen
				}
				break;
			case DisplayType.CostPrice:  // 37	
				if(value!=null) {
					cellRendererComponent = getRenderer_CostPrice(value, field);
				}
				break;
			case DisplayType.URL:        // 40	
				if(value!=null) {
					cellRendererComponent = getRenderer_URL(value, field);
				}
				break;
			default:
	        	LOG.config(table 
	        			+ "\nR/C:"+row+"/"+column + " Header="+field.getHeader() + " AD_Column_ID="+field.getAD_Column_ID() 
	        			+ " value:" + value + " of type " + (value==null ? "null" : value.getClass()) 
	        			+ " displayType:" + displayType + " Lookup:"+field.getLookup()
//	        			+ "\ncomponent:"+component
	        			);
			}
    	} else {
    		LOG.config(table
        		+ "\nR/C:"+row+"/"+column + " value:" + value
        		);
   	}
    	return cellRendererComponent;
    }

    static final boolean USE_EMOJI = true;
    // for No:
    static final String BALLOT_BOX = "\u2610";
    static final String white_large_square = "\u2B1C";
    static final String black_square_button = "\u1F53";
    // for Yes:    
    static final String BALLOT_BOX_WITH_CHECK = "\u2611";
    static final String CHECK_MARK = "\u2713";
    static final String heavy_check_mark = "\u2714";
    static final String white_check_mark = "\u2705";

    private Component getRenderer_YesNo(Object value, GridField field) {
    	if(value==null) {
    		LOG.warning("null field "+field );
    	}
    	if(USE_EMOJI) {
        	JRendererLabel jrl = new JRendererLabel();
        	if((Boolean)value) {
        		jrl.setText(heavy_check_mark);
        	} else {
        		jrl.setText(white_large_square);
        	}
        	jrl.setHorizontalAlignment(SwingConstants.CENTER);
        	return jrl;
    	}
    	// konventionell, ohne UTF emoji
		JRendererCheckBox checkbox = new JRendererCheckBox();				
		checkbox.setSelected((Boolean)value); // AbstractButton.setSelected(boolean b)
		checkbox.setHorizontalAlignment(SwingConstants.CENTER);
		return checkbox;
    }
    
    /* 
     * Bsp: AD_Column_ID=77930 value:0.0 of type class java.math.BigDecimal displayType:22/Number
     * wie displayType:12/Amount
     */
    private Component getRenderer_Number(Object value, GridField field) {
    	return getRenderer_Amount(value, field);
    }
    private Component getRenderer_Quantity(Object value, GridField field) {
    	return getRenderer_Amount(value, field);
    }
    private Component getRenderer_CostPrice(Object value, GridField field) {
    	return getRenderer_Amount(value, field);
    }
    private Component getRenderer_Amount(Object value, GridField field) {
		JRendererLabel rLabel = new JRendererLabel();
		BigDecimal amount = (BigDecimal)value;
		DecimalFormat decimalFormat = DisplayType.getNumberFormat(field.getDisplayType()); // wg. I18N 					
		rLabel.setText(decimalFormat.format(amount));
		rLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		return rLabel;
    }
    private Component getRenderer_DateTime(Object value, GridField field) {
		JRendererLabel dateTime = new JRendererLabel();
		Timestamp ts = (Timestamp)value;
		SimpleDateFormat simpleDateFormat = DisplayType.getDateFormat(field.getDisplayType()); // wg. I18N 					
		dateTime.setText(simpleDateFormat.format(ts)); // TODO Spaltenbreite
		return dateTime;
    }
    
    private Component getRenderer_URL(Object value, GridField field) {
    	return getRendererLabel(value, field);
    }
    private Component getRenderer_PAttribute(Object value, GridField field) {
    	return getRendererLabel(value, field);
    }
    private Component getRenderer_Table(Object value, GridField field) {
    	return getRendererLabel(value, field);
    }
    private Component getRenderer_TableDir(Object value, GridField field) {
    	return getRendererLabel(value, field);
    }
    private Component getRenderer_Search(Object value, GridField field) {
    	return getRendererLabel(value, field);
    }
    /*
     * Bsp: getRenderer_TableDir GridField field AD_Client_ID , Object=11 
     * rLabel.text = "GardenWorld"
     * 
     * Bsp: getRenderer_Search GridField is Table , Object=318
     * rLabel.text = "C_Invoice_Invoice"
     * 
     * Bsp: getRenderer_Search GridField is User/Contact AD_Column_ID=10443 , Object=100
     * rLabel.text = "<100>" : entspricht der Implemetierung im AD-client
     * 
     * Bsp: getRenderer_Table AD_Language AD_Column_ID=??? , Object=fr_LU
     * rLabel.text = "fr_LU"
     */
    private Component getRendererLabel(Object value, GridField field) {
		JRendererLabel rLabel = new JRendererLabel();
		Lookup lookup = field.getLookup(); // MutableComboBoxModel
		if(value instanceof Integer) {
			Integer key = (Integer)value;
			NamePair np = lookup.getDirect(value, true, true); // nut used in Lookup.getDirect: boolean saveInCache, boolean cacheLocal
			rLabel.setText(np == null ? "<" + key + ">" : np.getName()); // ==.toString()
		}
		if(value instanceof String) {
			String key = (String)value;
			rLabel.setText(key);
		}
		return rLabel; // TODO: Spaltenbreite und editor wenn selektiert 	
    }
}
