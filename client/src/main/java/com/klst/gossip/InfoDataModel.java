package com.klst.gossip;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.compiere.model.I_AD_Role;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;

public class InfoDataModel extends GenericDataModel {

	private static final long serialVersionUID = 863276666866965431L;
	
	private static final Logger LOG = Logger.getLogger(InfoDataModel.class.getName());

	public InfoDataModel(String name, int infoWindowId) {
		super(null, infoWindowId);
		
		this.setName(name);
		if("Product".equals(name)) {
			this.setTableName("M_PRODUCT_STOCK_V");   // aus InfoProduct : String s_sqlFrom = " M_PRODUCT_STOCK_V "; 
			// TODO hat das auch ein AD_Table_Id ? JA AD_Table_ID=53011 ===> dann Daten von dort holen, auch die Columns
/* aus InfoProduct
        		new ColumnInfo(" ", "M_Warehouse_ID", IDColumn.class),
        		new ColumnInfo(Msg.translate(Env.getCtx(), "WarehouseName"), "WarehouseName", String.class),
        		                                                                                             , boolean readOnly, boolean colorColumn, String keyPairColSQL
        		new ColumnInfo(Msg.translate(Env.getCtx(), "QtyAvailable"), "sum(QtyAvailable)", Double.class, true, true, null),
        		new ColumnInfo(Msg.translate(Env.getCtx(), "QtyOnHand"), "sum(QtyOnHand)", Double.class),
           		new ColumnInfo(Msg.translate(Env.getCtx(), "QtyReserved"), "sum(QtyReserved)", Double.class),
           		new ColumnInfo(Msg.translate(Env.getCtx(), "QtyOrdered"), "sum(QtyOrdered)", Double.class)};
 */
//			fields.addColumn(new GridFieldBridge("M_Warehouse_ID", Msg.translate(Env.getCtx(), "M_Warehouse_ID"), IDColumn.class));
			fields.addColumn(new GridFieldBridge("M_Warehouse_ID", Msg.translate(Env.getCtx(), "Warehouse"), Integer.class, DisplayType.ID));
			fields.addColumn(new GridFieldBridge("WarehouseName", Msg.translate(Env.getCtx(), "WarehouseName"), String.class, DisplayType.String));
			fields.addColumn(new GridFieldBridge("QtyAvailable", Msg.translate(Env.getCtx(), "QtyAvailable"), Double.class, DisplayType.Number));
			fields.addColumn(new GridFieldBridge("QtyOnHand", Msg.translate(Env.getCtx(), "QtyOnHand"), Double.class, DisplayType.Number));
			fields.addColumn(new GridFieldBridge("QtyReserved", Msg.translate(Env.getCtx(), "QtyReserved"), Double.class, DisplayType.Number));
			fields.addColumn(new GridFieldBridge("QtyOrdered", Msg.translate(Env.getCtx(), "QtyOrdered"), Double.class, DisplayType.Number));
		} else if("BPartner".equals(name)) {
			this.setTableName("C_BPartner");
/* aus InfoBPartner
	private static Info_Column[] s_Layout = {
		new Info_Column(" ", "C_BPartner.C_BPartner_ID", IDColumn.class),
		new Info_Column(Msg.translate(Env.getCtx(), "Value"), "C_BPartner.Value", String.class),
		new Info_Column(Msg.translate(Env.getCtx(), "Name"), "C_BPartner.Name", String.class),
		new Info_Column(Msg.translate(Env.getCtx(), "C_BP_Group_ID"), "(SELECT bpg.Name FROM C_BP_Group bpg WHERE bpg.C_BP_Group_ID = C_BPartner.C_BP_Group_ID)", String.class),
		new Info_Column(Msg.translate(Env.getCtx(), "TotalOpenBalance"), "C_BPartner.TotalOpenBalance", BigDecimal.class),
		new Info_Column(Msg.translate(Env.getCtx(), "SO_CreditAvailable"), "C_BPartner.SO_CreditLimit-C_BPartner.SO_CreditUsed AS SO_CreditAvailable", BigDecimal.class, true, true, null),
		new Info_Column(Msg.translate(Env.getCtx(), "SO_CreditUsed"), "C_BPartner.SO_CreditUsed", BigDecimal.class),
		new Info_Column(Msg.translate(Env.getCtx(), "Revenue"), "C_BPartner.ActualLifetimeValue", BigDecimal.class)
	};
 */
			fields.addColumn(new GridFieldBridge("C_BPartner_ID", Msg.translate(Env.getCtx(), "C_BPartner_ID"), Integer.class, DisplayType.ID));
			fields.addColumn(new GridFieldBridge("Value", Msg.translate(Env.getCtx(), "Value"), String.class, DisplayType.String));
			fields.addColumn(new GridFieldBridge("TotalOpenBalance", Msg.translate(Env.getCtx(), "C_BP_Group_ID"), BigDecimal.class, DisplayType.Amount));
			fields.addColumn(new GridFieldBridge("C_BPartner.SO_CreditLimit-C_BPartner.SO_CreditUsed AS SO_CreditAvailable", Msg.translate(Env.getCtx(), "SO_CreditAvailable"), BigDecimal.class, DisplayType.Amount));
			fields.addColumn(new GridFieldBridge("SO_CreditUsed", Msg.translate(Env.getCtx(), "SO_CreditUsed"), BigDecimal.class, DisplayType.Amount));
			fields.addColumn(new GridFieldBridge("ActualLifetimeValue", Msg.translate(Env.getCtx(), "ActualLifetimeValue"), BigDecimal.class, DisplayType.Amount));
			// ...
		} else {
			LOG.warning("TODO nicht implementiert für "+name);
		}
	}

	private static final Map<String, String> ALLOWANCE = createMap();
    private static Map<String, String> createMap() {
        Map<String, String> result = new HashMap<String, String>();
        result.put("Account", I_AD_Role.COLUMNNAME_Allow_Info_Account);
        result.put("Asset", I_AD_Role.COLUMNNAME_Allow_Info_Asset);
        result.put("BPartner", I_AD_Role.COLUMNNAME_Allow_Info_BPartner);
        result.put("CashJournal", I_AD_Role.COLUMNNAME_Allow_Info_CashJournal);
        result.put("CRP", I_AD_Role.COLUMNNAME_Allow_Info_CRP);
        result.put("InOut", I_AD_Role.COLUMNNAME_Allow_Info_InOut);
        result.put("Invoice", I_AD_Role.COLUMNNAME_Allow_Info_Invoice);
        result.put("MRP", I_AD_Role.COLUMNNAME_Allow_Info_MRP);
        result.put("Order", I_AD_Role.COLUMNNAME_Allow_Info_Order);
        result.put("Payment", I_AD_Role.COLUMNNAME_Allow_Info_Payment);
        result.put("Product", I_AD_Role.COLUMNNAME_Allow_Info_Product);
        result.put("Resource", I_AD_Role.COLUMNNAME_Allow_Info_Resource);
        result.put("Schedule", I_AD_Role.COLUMNNAME_Allow_Info_Schedule);
        return Collections.unmodifiableMap(result);
    }

	// aus (client) AEnv
	/**
	 * 
	 * @param infoWindowName Urspünglich suffix zu ALLOW_INFO_ bildet spalte in AD_ROLE, 
	 *  besser aus AD_ROLE (AD_Table_ID=156) holen
	 *  oder aus I_AD_Role.COLUMNNAME_Allow_Info_Account
	 *                    .COLUMNNAME_Allow_Info_Asset
	 *                    .COLUMNNAME_Allow_Info_BPartner
	 *                    ... statisch
	 * @return
	 */
	public static boolean canAccessInfo(String infoWindowName) {
		boolean result=false;
		int roleid= Env.getAD_Role_ID(Env.getCtx());
		String sqlRolePermission="Select COUNT(AD_ROLE_ID) AS ROWCOUNT FROM AD_ROLE WHERE AD_ROLE_ID=" + roleid  
	                              + " AND " + ALLOWANCE.get(infoWindowName) + "='Y'"; 

		LOG.config(sqlRolePermission); 
		PreparedStatement prolestmt = null; 
		ResultSet rs = null;
		try 
		{ 
			prolestmt = DB.prepareStatement (sqlRolePermission, null); 
	 
			rs = prolestmt.executeQuery ();  
	 
			rs.next(); 
	 
			if (rs.getInt("ROWCOUNT")>0)
			{
				result=true;
			}
			else 
			{
				return false;
			}
		} 
		catch (Exception e) 
		{
				System.out.println(e); 
				LOG.log(Level.SEVERE, "(1)", e); 
		} 
		finally
		{
			DB.close(rs, prolestmt);
			rs = null; prolestmt = null;
		}
	
		return result; 
		
	}


}
