package com.klst.gossip;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.compiere.model.I_AD_Role;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;
import org.compiere.model.MColumn;
import org.compiere.model.MTable;
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
			MTable mTable = new MTable(Env.getCtx(), I_M_Product.Table_ID, null);
			this.setTableName(mTable.getTableName()); // I_M_Product.Table_ID==208

/* Info_Column[] ausprogrammiert in InfoProduct.getTableLayout()

	non CTextField Suchfelder in InfoProduct:
	private VLookup fPriceList_ID;
	private VLookup fProductCategory_ID;
	private VLookup fVendor_ID;
	private VLookup fWarehouse_ID;
	private VLookup fAS_ID;
	private VPAttribute fASI_ID;

		ArrayList<Info_Column> list = new ArrayList<Info_Column>();
		list.add(new Info_Column(" ", "p.M_Product_ID", IDColumn.class, false));
		list.add(new Info_Column(Msg.translate(Env.getCtx(), "Discontinued").substring(0, 1), "p.Discontinued", Boolean.class));
(client)Info_Column extends  (base)org.compiere.model.GridField.ColumnInfo
		list.add(new Info_Column(Msg.translate(Env.getCtx(), "M_Product_Category_ID"), "pc.Name", String.class));
		list.add(new Info_Column(Msg.translate(Env.getCtx(), "Value"), "p.Value", String.class));
		list.add(new Info_Column(Msg.translate(Env.getCtx(), "Name"), "p.Name", String.class));
		list.add(new Info_Column(Msg.translate(Env.getCtx(), "UPC"), "p.UPC", String.class));
		list.add(new Info_Column(Msg.translate(Env.getCtx(), "SKU"), "p.SKU", String.class));
		list.add(new Info_Column(Msg.translate(Env.getCtx(), "C_UOM_ID"), "u.name", String.class));
		if (isValidVObject(fPriceList_ID))
		{ // bomPriceList ist sql-Function: bompricelist(numeric, numeric)
			list.add(new Info_Column(Msg.translate(Env.getCtx(), "PriceList"), "bomPriceList(p.M_Product_ID, pr.M_PriceList_Version_ID) AS PriceList",  BigDecimal.class));
			list.add(new Info_Column(Msg.translate(Env.getCtx(), "PriceStd"), "bomPriceStd(p.M_Product_ID, pr.M_PriceList_Version_ID) AS PriceStd", BigDecimal.class));
			list.add(new Info_Column(Msg.translate(Env.getCtx(), "PriceLimit"), "bomPriceLimit(p.M_Product_ID, pr.M_PriceList_Version_ID) AS PriceLimit", BigDecimal.class));
			list.add(new Info_Column(Msg.translate(Env.getCtx(), "Margin"), "bomPriceStd(p.M_Product_ID, pr.M_PriceList_Version_ID)-bomPriceLimit(p.M_Product_ID, pr.M_PriceList_Version_ID) AS Margin", BigDecimal.class));
		}
		if (isValidVObject(fWarehouse_ID))
		{
			list.add(new Info_Column(Msg.translate(Env.getCtx(), "IsStocked"), "p.isStocked", Boolean.class));
			list.add(new Info_Column(Msg.translate(Env.getCtx(), "QtyAvailable"), "case when p.IsBOM='N' and (p.ProductType!='I' OR p.IsStocked='N') then to_number(get_Sysconfig('QTY_TO_SHOW_FOR_SERVICES', '99999', p.ad_client_id, 0), '99999999999') else bomQtyAvailable(p.M_Product_ID,?,0) end AS QtyAvailable", Double.class, true, true, null));
			list.add(new Info_Column(Msg.translate(Env.getCtx(), "QtyOnHand"), "case when p.IsBOM='N' and (p.ProductType!='I' OR p.IsStocked='N') then to_number(get_Sysconfig('QTY_TO_SHOW_FOR_SERVICES', '99999', p.ad_client_id, 0), '99999999999') else bomQtyOnHand(p.M_Product_ID,?,0) end AS QtyOnHand", Double.class));
			list.add(new Info_Column(Msg.translate(Env.getCtx(), "QtyReserved"), "bomQtyReserved(p.M_Product_ID,?,0) AS QtyReserved", Double.class));
			list.add(new Info_Column(Msg.translate(Env.getCtx(), "QtyOrdered"), "bomQtyOrdered(p.M_Product_ID,?,0) AS QtyOrdered", Double.class));
			if (isUnconfirmed())
			{
				list.add(new Info_Column(Msg.translate(Env.getCtx(), "QtyUnconfirmed"), "(SELECT SUM(c.TargetQty) FROM M_InOutLineConfirm c INNER JOIN M_InOutLine il ON (c.M_InOutLine_ID=il.M_InOutLine_ID) INNER JOIN M_InOut i ON (il.M_InOut_ID=i.M_InOut_ID) WHERE c.Processed='N' AND i.M_Warehouse_ID=? AND il.M_Product_ID=p.M_Product_ID) AS QtyUnconfirmed", Double.class));
				list.add(new Info_Column(Msg.translate(Env.getCtx(), "QtyUnconfirmedMove"), "(SELECT SUM(c.TargetQty) FROM M_MovementLineConfirm c INNER JOIN M_MovementLine ml ON (c.M_MovementLine_ID=ml.M_MovementLine_ID) INNER JOIN M_Locator l ON (ml.M_LocatorTo_ID=l.M_Locator_ID) WHERE c.Processed='N' AND l.M_Warehouse_ID=? AND ml.M_Product_ID=p.M_Product_ID) AS QtyUnconfirmedMove", Double.class));
			}
		}
		list.add(new Info_Column(Msg.translate(Env.getCtx(), "Vendor"), "bp.Name", String.class));
		list.add(new Info_Column(Msg.translate(Env.getCtx(), "IsInstanceAttribute"), "pa.IsInstanceAttribute", Boolean.class));

 */			
			
			List<MColumn> mColumnList = mTable.getColumnsAsList();
			Map<String, MColumn> mColumnMap = new HashMap<>(); 
			mColumnList.forEach( mColumn -> {
				LOG.config("ID:"+mColumn.get_ID() + " : AD_Reference_ID/DisplayType=" + mColumn.getAD_Reference_ID()+ " : ColumnName=" + mColumn.getColumnName());
				mColumnMap.put(mColumn.getColumnName(), mColumn);
			});
			
			addColumn(I_M_Product.COLUMNNAME_M_Product_ID, mColumnMap);
			addColumn(I_M_Product.COLUMNNAME_Discontinued, mColumnMap);
			addColumn(I_M_Product.COLUMNNAME_M_Product_Category_ID, mColumnMap);
			addColumn(I_M_Product.COLUMNNAME_Value, mColumnMap);
			addColumn(I_M_Product.COLUMNNAME_Name, mColumnMap);
			addColumn(I_M_Product.COLUMNNAME_UPC, mColumnMap);
			addColumn(I_M_Product.COLUMNNAME_SKU, mColumnMap);
			addColumn(I_M_Product.COLUMNNAME_C_UOM_ID, mColumnMap);
/*

			s_productFrom += " 
LEFT OUTER JOIN (SELECT mpp.M_Product_ID, mpp.M_PriceList_Version_id, mpp.IsActive, mpp.PriceList, mpp.PriceStd, mpp.PriceLimit
                   FROM M_ProductPrice mpp, M_PriceList_Version mplv
                  WHERE mplv.M_PriceList_Version_ID = mpp.M_PriceList_Version_ID AND mplv.IsActive = 'Y') pr
 ON (p.M_Product_ID=pr.M_Product_ID AND pr.IsActive='Y' 


 */
			// 101 ist pr.M_PriceList_Version_ID dh M_ProductPrice.M_PriceList_Version_id für GardenWorld
// die nachfolgende Zeile fürrt zu exception, aber Info Produkt lädt dadurch !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			addColumn("101_as_M_PriceList_Version_ID", Msg.translate(Env.getCtx(), "PriceList_Version"), DisplayType.ID);
//			addColumn("M_PriceList_Version_ID", Msg.translate(Env.getCtx(), "PriceList_Version"), DisplayType.ID);
			// bomPriceList ist sql-Function: bompricelist(numeric, numeric)
			addColumn("bomPriceList(M_Product_ID, 101) AS PriceList", Msg.translate(Env.getCtx(), "PriceList"), DisplayType.Amount);
			
			addColumn("IsStocked", mColumnMap);
			
			// "Quantity to show for services in InfoProduct window"
			// sql Function: bomqtyavailable(numeric, numeric, numeric)
//	    product_id numeric,
//	    warehouse_id numeric, // vorerst 50002 == "Fertilizer"
//	    locator_id numeric)   // 0
			// sql Function: get_Sysconfig('QTY_TO_SHOW_FOR_SERVICES', '99999', ad_client_id, 0)
			addColumn("(case when IsBOM='N' and (ProductType!='I' OR IsStocked='N') then to_number(get_Sysconfig('QTY_TO_SHOW_FOR_SERVICES', '99999', ad_client_id, 0), '99999999999') else bomQtyAvailable(M_Product_ID,50002,0) end) AS QtyAvailable"
					, Msg.translate(Env.getCtx(), "QtyAvailable"), DisplayType.Number);
			
		} else if("BPartner".equals(name)) {
			MTable mTable = new MTable(Env.getCtx(), I_C_BPartner.Table_ID, null);
			this.setTableName(mTable.getTableName()); // 291
			
			List<MColumn> mColumnList = mTable.getColumnsAsList();
			Map<String, MColumn> mColumnMap = new HashMap<>(); 
			mColumnList.forEach( mColumn -> {
				LOG.config("ID:"+mColumn.get_ID() + " : AD_Reference_ID/DisplayType=" + mColumn.getAD_Reference_ID()+ " : ColumnName=" + mColumn.getColumnName());
				mColumnMap.put(mColumn.getColumnName(), mColumn);
			});
			
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

 AD_Column_ID = 2893 , ... ermitteln:
 
select * from ad_column where ad_table_id in(
select ad_table_id from ad_table where tablename='C_BPartner') -- 'C_UOM')
order by 1

 */
			addColumn(I_C_BPartner.COLUMNNAME_C_BPartner_ID, mColumnMap);
			addColumn(I_C_BPartner.COLUMNNAME_Value, mColumnMap);
			addColumn(I_C_BPartner.COLUMNNAME_C_BP_Group_ID, mColumnMap);
			addColumn(I_C_BPartner.COLUMNNAME_TotalOpenBalance, mColumnMap);
			
			// TODO SO_CreditAvailable als virtuell definieren:
			addColumn("C_BPartner.SO_CreditLimit-C_BPartner.SO_CreditUsed AS SO_CreditAvailable", Msg.translate(Env.getCtx(), "SO_CreditAvailable"), DisplayType.Amount);
			
			addColumn(I_C_BPartner.COLUMNNAME_SO_CreditUsed, mColumnMap);
			addColumn(I_C_BPartner.COLUMNNAME_ActualLifeTimeValue, mColumnMap);	
			// ...
		} else {
			LOG.warning("TODO nicht implementiert für "+name);
		}
	}

	private void addColumn(String identifier, String header, int displayType) {
		super.addColumn(new GridFieldBridge(identifier, header, displayType));
	}
	private void addColumn(String columnName, Map<String, MColumn> mColumnMap) {
//		mColumnMap.forEach( (k,v) -> {
//			LOG.config("k:"+k + " : " + v);
//		});
//		assert(columnName.equals(mColumnMap.get(columnName).getColumnName()));
//		MColumn mapVal = mColumnMap.get(columnName);
//		LOG.config(">>>>>>>>>>"+mapVal);
//		mapVal.get_ID();
		int column_ID = mColumnMap.get(columnName).get_ID();
		MColumn mColumn = new MColumn(Env.getCtx(), column_ID, null);
		super.addColumn(
			new GridFieldBridge(/*column_ID,*/ columnName
				, mColumn.get_Translation("Name")
				, mColumnMap.get(columnName).getAD_Reference_ID())
			);
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
