package com.klst.gossip;

import java.awt.Color;
import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JTable;

import org.compiere.model.PO;
import org.compiere.util.Env;
import org.jdesktop.swingx.renderer.ComponentProvider;
import org.jdesktop.swingx.renderer.DefaultTableRenderer;
import org.jdesktop.swingx.renderer.LabelProvider;
import org.jdesktop.swingx.renderer.StringValue;

// für MuliRowPanel
public class GenericTableRenderer extends DefaultTableRenderer { // extends AbstractRenderer implements TableCellRenderer

	private static final long serialVersionUID = -4219461104323147646L;
	private static final Logger LOG = Logger.getLogger(GenericTableRenderer.class.getName());

    public GenericTableRenderer() {
    	super();
    }

    public GenericTableRenderer(StringValue converter) {
    	super(new LabelProvider(converter));
    }

    public GenericTableRenderer(StringValue converter, int alignment) {
        super(new LabelProvider(converter, alignment));
    }
    
    public GenericTableRenderer(ComponentProvider<?> componentProvider) {
        super(componentProvider);
    }

    Object getObject(String columnName, Object value) { // eine interimsLösung
    	if(value==null || !columnName.endsWith("_ID")) {
    		return value;
    	}
    	String className = "org.compiere.model.X_" + columnName.substring(0, columnName.length()-3);
    	LOG.config(">>>>>>>>>>>>>>>>>className="+className);
    	try {
			Class<?> classClass = ClassLoader.getSystemClassLoader().loadClass(className);
			// oder kurz: Class<?> theClass = Class.forName(theType);
			//Object object = classClass.newInstance();
			// in PO: static public List<?> getInstances(Integer tableId, List<Integer> recordIds, String trxName) throws AdempiereException
			Object object = classClass.getConstructor(Properties.class, int.class, String.class).newInstance(Env.getCtx(), ((Integer)value).intValue(), null);
			
			//Object obj = classClass.cast(object); // cast möglich, aber nicht: classClass.cast(object).getValue();
			PO po = (PO)object;
			Object v = po.get_Value("Name"); // für AD_Org_ID=0 bekomme ich nicht "*" das ist bei AD_Client_ID=0 definiert, 
			                                 // in den props steht aber #AD_Client_ID=11 - als interimsLösung gut genug
			if(v!=null) value = v;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return value;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return value;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return value;
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return value;
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return value;
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return value;
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return value;
		}
		return value;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    	Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    	LOG.config(table 
    			+ "\n"+column+"/"+row + " value:" + value + " of type " + (value==null ? "null" : value.getClass())
    			+ "\ncomponent:"+component);
    	
		if(table instanceof MuliRowPanel) {
//			if(row==-1) { // TableHeader
//				return component;
//			}
			//TableModel dataModel = table.getModel();
			GenericDataModel dataModel = (GenericDataModel)table.getModel();
			if(dataModel.isCellEditable(row, column)) {
				// TODO dann sollten sie einen Editor haben
			} else {
////				LOG.config("ColumnClass aka Storage Class aka DisplayType="+dataModel.getColumnClass(column)); // java.lang.Integer java.lang.String ...
//				if(dataModel.getColumnName(column).endsWith("_ID")) { // zB AD_Org_ID
////					new X_AD_Org(Properties ctx, int AD_Org_ID = value, String trxName)
//					LOG.config(">>>>>>>>>>>>>>>>>ColumnName="+dataModel.getColumnName(column));
//					// value ersetzten durch "select value from XXX -tablename where dataModel.getColumnName(column)=value"
//					// bzw Lookup gridField.getLookup()
//				}
				value = getObject(dataModel.getColumnName(column), value);
				component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				((JComponent)component).setForeground(Color.GRAY);
			}
		}
		
//		if(component instanceof JLabel) {
//			((JLabel)component).setForeground(Color.LIGHT_GRAY);
//		}
		
    	return component;
    }
}
