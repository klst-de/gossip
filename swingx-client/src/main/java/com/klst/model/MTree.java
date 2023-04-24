package com.klst.model;

import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.RowSet;

import org.adempiere.core.domains.models.I_AD_Tree;
import org.adempiere.core.domains.models.X_AD_Menu;
import org.adempiere.core.domains.models.X_AD_Tree;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_Persistent;
import org.compiere.model.MClient;
import org.compiere.model.MRole;
import org.compiere.model.MTable;
import org.compiere.model.PO;
//import org.compiere.model.MTreeNode;
import org.compiere.print.MPrintColor;
import org.compiere.util.CCache;
import org.compiere.util.CLogMgt;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.ResultSetIterable;

import io.vavr.Tuple;
import io.vavr.Tuple4;
import io.vavr.control.Try;

/*
 * ich will für die Darstellung von Bäumen ein neueres jdesktop.swingx nutzen und 
 * kann die Member rootNode, loadNodes, ... aus super nicht überschreiben, da sie private sind.
 * Also überschreibe ich die ganze Klasse.
 * 
 * org.compiere.model.MTree extends X_AD_Tree
 *                            class X_AD_Tree extends PO implements I_AD_Tree, I_Persistent
 */
public class MTree extends org.compiere.model.MTree {

	private static final long serialVersionUID = -4802325261417990860L;
	private static final Logger LOG = Logger.getLogger(MTree.class.getName());
	
	private static CCache<Integer,MTree> s_cache = new CCache<Integer, MTree>("AD_Tree", 10);
	public static MTree get(Properties ctx, int AD_Tree_ID, String trxName) {
//		return org.compiere.model.MTree.get(ctx, AD_Tree_ID, trxName);
		Integer key = Integer.valueOf(AD_Tree_ID);
		MTree retValue = (MTree) s_cache.get (key);
		if (retValue != null)
			return retValue;
		retValue = new MTree(ctx, AD_Tree_ID, trxName);
		if(retValue.get_ID () != 0)
			s_cache.put(key, retValue);
		return retValue;
	}
	// alle Member in super private - Um zu überschreiben, muss ich neu definieren
	boolean isTreeEditable = false;
	
/** Buffer while loading tree 
 
 
 */
	private Map<Integer, MTreeNode> treeNodeMap = new HashMap<Integer, MTreeNode>();
	private ArrayList<MTreeNode> treeNodes = new ArrayList<>();

	private RowSet nodeRowSet; // used in getNodeDetails

	MTreeNode rootNode = null; // in super MTreeNode extends javax.swing.tree.DefaultMutableTreeNode, ich will
	// org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode extends AbstractMutableTreeTableNode nutzen.
	// DefaultMutableTreeTableNode ist nur zum Testen da, "This implementation is designed mainly for testing. It is NOT recommended to usethis implementation.",
	// ich muss daher von AbstractMutableTreeTableNode direkt ableiten

	private HashMap<Integer, ArrayList<Integer>> nodeIdMap;

	// ctor muss nicht public sein
	public MTree(Properties ctx, int AD_Tree_ID, String trxName) {
		super(ctx, AD_Tree_ID, trxName);
		LOG.info("AD_Tree_ID=" + AD_Tree_ID + " trxName:"+trxName + " \nctx:"+ctx);
		if (AD_Tree_ID == 0) {
			setIsAllNodes(true); // complete tree
			setIsDefault(false);
		}
	}

	// ctor used in MenuPanel
	public MTree(Properties ctx, int treeId, boolean editable, boolean allNodes, String whereClause, String trxName) {
		this(ctx, treeId, trxName);
		isTreeEditable = editable;
		int userId;
		if (allNodes)
			userId = -1;
		else
			userId = Env.getContextAsInt(ctx, "AD_User_ID");
		
		LOG.info("AD_Tree_ID=" + treeId
				+ ", AD_User_ID=" + userId 
				+ ", Editable=" + editable);
		//
		//	Yamel Senih [ 9223372036854775807 ]
		//	Add support to where clause
		loadNodes(userId, whereClause);
	}

	public org.compiere.model.MTreeNode getRoot() {
		LOG.warning("DO NOT USE!!! use getRootNode()");
		return null;
	}
	public MTreeNode getRootNode() {
		return rootNode;
	}

	/*************************************************************************
	 *  Load Nodes and Bar
	 * 	@param userId user for tree bar
	 * 	@param whereClause
	 */
	// aus super
/*
SELECT tn.Node_ID,tn.Parent_ID,tn.SeqNo,tb.IsActive 
FROM AD_TreeNodeMM tn 
LEFT JOIN AD_Menu ON(AD_Menu.AD_Menu_ID = tn.Node_ID)  
LEFT OUTER JOIN AD_TreeBar tb ON (tn.AD_Tree_ID=tb.AD_Tree_ID AND tn.Node_ID=tb.Node_ID  AND tb.AD_User_ID=0 ) 
WHERE tn.AD_Tree_ID=10 AND tn.IsActive='Y' 
ORDER BY COALESCE(tn.Parent_ID, -1), tn.SeqNo
-- liefert 949 Zeilen
 */
	private void loadNodes (int userId, String whereClause) {
		List<Object> parameters = new ArrayList<>();
		String fromClause = getSourceTableName();
		//  SQL for TreeNodes
		StringBuilder sql = new StringBuilder("\nSELECT tn.Node_ID,tn.Parent_ID,tn.SeqNo,tb.IsActive \nFROM ")
				.append(getNodeTableName())
				.append(" tn ")
				.append("LEFT JOIN ")
				.append(fromClause).append(" ON(")
				.append(fromClause).append(".").append(fromClause).append("_ID").append(" = tn.Node_ID) ")
				.append(" LEFT OUTER JOIN AD_TreeBar tb ON (tn.AD_Tree_ID=tb.AD_Tree_ID AND tn.Node_ID=tb.Node_ID ")
				.append(userId != -1 ? " AND tb.AD_User_ID=? " : "").append(") ").append("\nWHERE tn.AD_Tree_ID=?"); //	#2
		if (userId != -1) {
			parameters.add(userId);
		}
		parameters.add(getAD_Tree_ID());
		if (!isTreeEditable) {
			sql.append(" AND tn.IsActive=?");
			parameters.add(true);
		}
		//	Add GridTab Where Class
		if(whereClause != null
				&& whereClause.length() > 0)
			sql.append(" AND ").append(whereClause);
		//	End Yamel Senih

		//suppress duplicated items
		sql.append(" GROUP BY tn.Node_ID,tn.Parent_ID,tn.SeqNo,tb.IsActive ");
		sql.append(" ORDER BY COALESCE(tn.Parent_ID, -1), tn.SeqNo");
		log.info(sql.toString());
		LOG.info(sql.toString());

		// load Node details - addToTree -> getNodeDetail
		getNodeDetails();
		rootNode = new MTreeNode (0, 0, getName(), getDescription(), 0, true, null, false, null);
		//  The Node Loop
		Try<Void> addToTree = DB.runResultSetFunction.apply(get_TrxName(), sql.toString(), io.vavr.collection.List.ofAll(parameters), resultSet -> {
			ResultSetIterable<Tuple4<Integer, Integer, Integer, String>> records = new ResultSetIterable<>(resultSet, row -> {
				return Tuple.of(
						row.getInt(1),
						row.getInt(2),
						row.getInt(3),
						row.getString(4));
			});
			records.stream()
			// Node_ID != 0 || Parent_ID Id != 0
			.filter(node -> node._1  != 0 || node._2 != 0)
			// addToTree (Node_ID , Parent_ID , SeqNo , IsActive)
			.forEach(node -> addToTree(node._1, node._2, node._3, node._4 != null));
		}).andFinally(() -> {
			nodeRowSet = null;
			nodeIdMap = null;
		}).onFailure(throwable -> {
			log.log(Level.SEVERE, sql.toString(), throwable);
		});

		if (addToTree.isFailure())
			return;

		//  Done with loading - add remainder from buffer
		if (treeNodes.size() != 0)
		{
			log.finest("clearing buffer - Adding to: " + rootNode);
			for (int i = 0; i < treeNodes.size(); i++)
			{
				MTreeNode node = (MTreeNode) treeNodes.get(i);
				MTreeNode parent = rootNode.findNode(node.getParent_ID());
				if (parent != null && parent.getAllowsChildren())
				{
					parent.add(node);
					int sizeBeforeCheckBuffer = treeNodes.size();
					checkBuffer(node);
					if (sizeBeforeCheckBuffer == treeNodes.size())
						treeNodes.remove(i);
					i = -1;		//	start again with i=0
				}
			}
		}

		//	Nodes w/o parent
		if (treeNodes.size() != 0)
		{
			log.severe ("Nodes w/o parent - adding to root - " + treeNodes);
			for (int i = 0; i < treeNodes.size(); i++)
			{
				MTreeNode node = (MTreeNode) treeNodes.get(i);
				rootNode.add(node);
				int sizeBeforeCheckBuffer = treeNodes.size();
				checkBuffer(node);
				if (sizeBeforeCheckBuffer == treeNodes.size())
					treeNodes.remove(i);
				i = -1;
			}
			if (treeNodes.size() != 0)
				log.severe ("Still nodes in Buffer - " + treeNodes);
		}	//	nodes w/o parents

		//  clean up
		if (!isTreeEditable && rootNode.getChildCount() > 0)
			trimTree();
//		diagPrintTree();
		if (CLogMgt.isLevelFinest() || rootNode.getChildCount() == 0)
			log.fine("ChildCount=" + rootNode.getChildCount());
	}   //  loadNodes

	public String getNodeTableName() {
		return super.getNodeTableName();
	}

	/**************************************************************************
	 *  Get Node Detail.
	 * 	Loads data into RowSet m_nodeRowSet
	 *  Columns:
	 * 	- ID
	 *  - Name
	 *  - Description
	 *  - IsSummary
	 *  - ImageIndicator
	 * 	- additional for Menu
	 *  Parameter:
	 *  - Node_ID
	 *  The SQL contains security/access control
	 */
	private void getNodeDetails () {
		//  SQL for Node Info
		StringBuilder sqlNode = new StringBuilder();
		String sourceTable = "t";
		String fromClause = getSourceTableName();
		String color = getActionColorName();
		if (getTreeType().equals(TREETYPE_Menu)) {
			boolean base = Env.isBaseLanguage(p_ctx, "AD_Menu");
			sourceTable = "m";
			if (base) {
				sqlNode.append("SELECT m.AD_Menu_ID, m.Name,m.Description,m.IsSummary,m.Action, "
					+ "m.AD_Window_ID, m.AD_Process_ID, m.AD_Form_ID, m.AD_Workflow_ID, m.AD_Task_ID, m.AD_Workbench_ID "
					+ ", m.AD_Browse_ID "
					+ "FROM AD_Menu m");
			} else {
				sqlNode.append("SELECT m.AD_Menu_ID,  t.Name,t.Description,m.IsSummary,m.Action, "
					+ "m.AD_Window_ID, m.AD_Process_ID, m.AD_Form_ID, m.AD_Workflow_ID, m.AD_Task_ID, m.AD_Workbench_ID "
					+ ", m.AD_Browse_ID "
					+ "FROM AD_Menu m "
					+ "LEFT JOIN AD_Menu_Trl t ON(t.AD_Menu_ID = m.AD_Menu_ID AND t.AD_Language='").append(Env.getAD_Language(p_ctx)).append("') ");
			}
			//	
			if (!isTreeEditable) {
				boolean hasWhere = sqlNode.indexOf(" WHERE ") != -1;
				sqlNode.append(hasWhere ? " AND " : " WHERE ").append("m.IsActive='Y' ");
			}
			//	Do not show Beta
			if (!MClient.get(getCtx()).isUseBetaFunctions())
			{
				boolean hasWhere = sqlNode.indexOf(" WHERE ") != -1;
				sqlNode.append(hasWhere ? " AND " : " WHERE ");
				sqlNode.append("(m.AD_Window_ID IS NULL OR EXISTS (SELECT 1 FROM AD_Window w WHERE m.AD_Window_ID=w.AD_Window_ID AND w.IsBetaFunctionality='N'))")
					.append(" AND (m.AD_Process_ID IS NULL OR EXISTS (SELECT 1 FROM AD_Process p WHERE m.AD_Process_ID=p.AD_Process_ID AND p.IsBetaFunctionality='N'))")
					.append(" AND (m.AD_Workflow_ID IS NULL OR EXISTS (SELECT 1 FROM AD_Workflow wf WHERE m.AD_Workflow_ID=wf.AD_Workflow_ID AND wf.IsBetaFunctionality='N'))")
					.append(" AND (m.AD_Form_ID IS NULL OR EXISTS (SELECT 1 FROM AD_Form f WHERE m.AD_Form_ID=f.AD_Form_ID AND f.IsBetaFunctionality='N'))")
					.append(" AND (m.AD_Browse_ID IS NULL OR EXISTS (SELECT 1 FROM AD_Browse b WHERE m.AD_Browse_ID=b.AD_Browse_ID AND b.IsBetaFunctionality='N'))");
			}
			//	In R/O Menu - Show only defined Forms
			if (!isTreeEditable) {
				boolean hasWhere = sqlNode.indexOf(" WHERE ") != -1;
				sqlNode.append(hasWhere ? " AND " : " WHERE ");
				sqlNode.append("(m.AD_Form_ID IS NULL OR EXISTS (SELECT 1 FROM AD_Form f WHERE m.AD_Form_ID=f.AD_Form_ID AND (f.Classname IS NOT NULL OR f.JSPURL IS NOT NULL)))");
			}
		} 
		//	Yamel Senih [ 9223372036854775807 ]
		//	Load for Custom Tree
		else if(getTreeType().equals(TREETYPE_CustomTree)) {
			boolean base = Env.isBaseLanguage(p_ctx, fromClause);
			boolean translation = !base && MTable.hasTranslation(fromClause);
			sourceTable = "t";
			String recordClause = "";
			if (!translation){
				sqlNode.append("SELECT t." + fromClause + "_ID, ")
					.append("COALESCE(t.Name, '') AS Name, COALESCE(t.Description, '') AS Description, t.IsSummary, " + color + " AS Action ")
					.append(recordClause.length() > 0 ? ", " + recordClause : "")
					.append(" FROM ").append(fromClause).append(" AS t ")
					;
			} else {
				sqlNode.append("SELECT t." + fromClause + "_ID, ")
					.append("COALESCE(tt.Name, t.Name, '') AS Name, COALESCE(tt.Description, t.Description, '') AS Description, t.IsSummary,  ")
					.append( color + " AS Action ")
					.append(recordClause.length() > 0 ? ", " + recordClause : "")
					.append(" FROM ").append(fromClause).append(" AS t ")
					.append(" LEFT JOIN " + fromClause + "_Trl tt ON (t. " + fromClause + "_ID = t." + fromClause + "_ID AND tt.AD_Language='").append(Env.getAD_Language(p_ctx)).append("'").append(")")
				;
			}
			//	
			if (!isTreeEditable) {
				boolean hasWhere = sqlNode.indexOf(" WHERE ") != -1;
				sqlNode.append(hasWhere ? " AND " : " WHERE ").append("t.IsActive='Y' ");
			}
		}
		//	End Yamel Senih
		else
		{
			if (fromClause == null)
				throw new IllegalArgumentException("Unknown TreeType=" + getTreeType());
			sqlNode.append("SELECT t.").append(fromClause)
				.append("_ID,t.Name,t.Description,t.IsSummary,").append(color);
			if (containsValueColumn(fromClause) ) {
				sqlNode.append(", t.Value"); //@Trifon
			}
			sqlNode.append(" FROM ").append(fromClause).append(" t ");
			if (!isTreeEditable)
				sqlNode.append(" WHERE t.IsActive='Y'");
		}
		String sql = sqlNode.toString();
		if (!isTreeEditable)	//	editable = menu/etc. window
			sql = MRole.getDefault(getCtx(), false).addAccessSQL(sql, 
				sourceTable, MRole.SQL_FULLYQUALIFIED, isTreeEditable);
		log.fine(sql);
		nodeRowSet = DB.getRowSet (sql);
		nodeIdMap = new HashMap<Integer, ArrayList<Integer>>(50);
		try 
		{
			nodeRowSet.beforeFirst();
			int i = 0;
			while (nodeRowSet.next())
			{
				i++;
				int node = nodeRowSet.getInt(1);
				Integer nodeId = Integer.valueOf(node);
				ArrayList<Integer> list = nodeIdMap.get(nodeId);
				if (list == null)
				{
					list = new ArrayList<Integer>(5);
					nodeIdMap.put(nodeId, list);
				}
				list.add(Integer.valueOf(i));
			}
		} catch (SQLException e) 
		{
			log.log(Level.SEVERE, "", e);
		}
	}   //  getNodeDetails

	/**
	 * 	Get Source TableName
	 *	@param treeType tree type
	 *	@return source table name, e.g. AD_Org or null 
	 */
	public String getSourceTableName() {
		if(getTreeType().equals(TREETYPE_CustomTree)) { // "CU"
			if(getAD_Table_ID() == 0) {
				throw new AdempiereException("@AD_Table_ID@ @NotFound@");
			}
			MTable table = MTable.get(getCtx(), getAD_Table_ID());
			if(table == null) {
				throw new AdempiereException("@AD_Table_ID@ @NotFound@");
			}
			//	Default
			return table.getTableName();
		}
		/*	Get Tree Type
		 * liefert zu String aus Spalte ad_tree.TreeType den TabellenNamen
		 * "MM" ==> "AD_Menu"
		 * "PR" ==> "M_Product"
		 * "BP" ==> "C_BPartner"
		 * "OO" ==> "AD_Org"
		 * ... dh es geht auch mit static map
		 */
		return org.compiere.model.MTree.getSourceTableName(getTreeType());
	}

	/**
	 * 	Get fully qualified Name of Action/Color Column
	 *	@return NULL or Action or Color
	 */
	public String getActionColorName()
	{
		//	Yamel Senih [ 9223372036854775807 ]
		String tableName = getSourceTableName();
		if ("AD_Menu".equals(tableName))
			return "t.Action";
		return "NULL";
	}	//	getSourceTableName

	/**
	 *  Add Node to Tree.
	 *  If not found add to buffer
	 *  @param nodeId Node_ID
	 *  @param parentId Parent_ID
	 *  @param seqNo SeqNo
	 *  @param onBar on bar
	 */
	private void addToTree (int nodeId, int parentId, int seqNo, boolean onBar)
	{
		//  Create new Node
		MTreeNode child = getNodeDetail (nodeId, parentId, seqNo, onBar);
		if (child == null)
			return;

		//  Add to Tree
		MTreeNode parent = null;
		if (rootNode != null)
			parent = rootNode.findNode (parentId);
		//  Parent found
		if (parent != null && parent.getAllowsChildren())
		{
			parent.add(child);
			//  see if we can add nodes from buffer
			if (treeNodes.size() > 0)
				checkBuffer(child);
		}
		else
			treeNodes.add(child);
	}   //  addToTree

	/**
	 *  Get Menu Node Details.
	 *  As SQL contains security access, not all nodes will be found
	 *  @param  node_ID     Key of the record
	 *  @param  parent_ID   Parent ID of the record
	 *  @param  seqNo       Sort index
	 *  @param  onBar       Node also on Shortcut bar
	 *  @return Node
	 */
	private MTreeNode getNodeDetail (int node_ID, int parent_ID, int seqNo, boolean onBar)
	{
		MTreeNode retValue = null;
		try
		{
			//nodeRowSet.beforeFirst();
			ArrayList<Integer> nodeList = nodeIdMap.get(Integer.valueOf(node_ID));
			int size = nodeList != null ? nodeList.size() : 0;
			int i = 0;
			//while (nodeRowSet.next())
			while (i < size)
			{
				Integer nodeId = nodeList.get(i);
				i++;
				nodeRowSet.absolute(nodeId.intValue());
				int node = nodeRowSet.getInt(1);
				if (node_ID != node)	//	search for correct one
					continue;
				//	ID, Name, Description, IsSummary, Action/Color, Value // @Trifon
				int index = 2;				
				String name = nodeRowSet.getString(index++);
				String description = nodeRowSet.getString(index++);
				String sSummary = nodeRowSet.getString(index++);
				boolean isSummary = "Y".equals(sSummary);
				String actionColor = nodeRowSet.getString(index++);
				//	Menu only
				if ((getTreeType().equals(TREETYPE_Menu)) && !isSummary) {
					int AD_Window_ID = nodeRowSet.getInt(index++);
					int AD_Process_ID = nodeRowSet.getInt(index++);
					int AD_Form_ID = nodeRowSet.getInt(index++);
					int AD_Workflow_ID = nodeRowSet.getInt(index++);
					int AD_Task_ID = nodeRowSet.getInt(index++);
					int AD_Workbench_ID = nodeRowSet.getInt(index++);
					int AD_Browse_ID = nodeRowSet.getInt(index++);
					//
					MRole role = MRole.getDefault(getCtx(), false);
					Boolean access = null;
					if (X_AD_Menu.ACTION_Window.equals(actionColor)) // W
						access = role.getWindowAccess(AD_Window_ID);
					else if (X_AD_Menu.ACTION_Process.equals(actionColor) 
						|| X_AD_Menu.ACTION_Report.equals(actionColor))
						access = role.getProcessAccess(AD_Process_ID);
					else if (X_AD_Menu.ACTION_Form.equals(actionColor))
						access = role.getFormAccess(AD_Form_ID);
					else if (X_AD_Menu.ACTION_SmartBrowse.equals(actionColor))
						access = role.getBrowseAccess(AD_Browse_ID);
					else if (X_AD_Menu.ACTION_WorkFlow.equals(actionColor))
						access = role.getWorkflowAccess(AD_Workflow_ID);
					else if (X_AD_Menu.ACTION_Task.equals(actionColor))
						access = role.getTaskAccess(AD_Task_ID);
//					else if (X_AD_Menu.ACTION_Workbench.equals(action))
//						access = role.getWorkbenchAccess(AD_Window_ID);
//					LOG.fine("getNodeDetail - " + name + " - " + actionColor + " - " + access);
					//
					if (access != null		//	rw or ro for Role 
						|| isTreeEditable)		//	Menu Window can see all
					{ LOG.fine("========================= 488 actionColor="+actionColor);
						retValue = new MTreeNode (node_ID, seqNo, name, description, parent_ID, isSummary, actionColor, onBar, null);	//	menu has no color
					}
				} else if(getTreeType().equals(TREETYPE_CustomTree)) { LOG.config("========================= 491");
					retValue = new MTreeNode (node_ID, seqNo,
							name, description, parent_ID, isSummary,
							actionColor, onBar, null);	//	menu has no color
				}
				else	//	always add
				{
					Color color = null;	//	action
					if (actionColor != null && !getTreeType().equals(TREETYPE_Menu))
					{
						MPrintColor printColor = MPrintColor.get(getCtx(), actionColor);
						if (printColor != null)
							color = printColor.getColor();
					}
					// @Trifon-begin
					if (getTreeType().equals(TREETYPE_ElementValue) ) {
//					String sourceTableName = getSourceTableName(true); // Uncomment it if you want to see Value for all Tree types
//					if ( containsValueColumn( sourceTableName ) ) { // Uncomment it if you want to see Value for all Tree types
						String value = nodeRowSet.getString(index++);
						name = value + " - " + name;
					}// @Trifon-end
					LOG.fine("========================= 512 TreeType:"+getTreeType() + " sSummary:"+sSummary);
					retValue = new MTreeNode (node_ID, seqNo, name, description, parent_ID, isSummary, null, onBar, color); //	no action
				}
			}
		}
		catch (SQLException e)
		{
			LOG.log(Level.SEVERE, "", e);
		}
		return retValue;
	}   //  getNodeDetails

	/**
	 *  Check the buffer for nodes which have newNode as Parents
	 *  @param newNode new node
	 */
	private void checkBuffer (MTreeNode newNode)
	{
		//	Ability to add nodes
		if (!newNode.isSummary() || !newNode.getAllowsChildren())
			return;
		//
		for (int i = 0; i < treeNodes.size(); i++)
		{
			MTreeNode node = (MTreeNode) treeNodes.get(i);
			if (node.getParent_ID() == newNode.getNode_ID())
			{
				try
				{
					newNode.add(node);
				}
				catch (Exception e)
				{
					log.severe("Adding " + node.getName() 
						+ " to " + newNode.getName() + ": " + e.getMessage());
				}
				treeNodes.remove(i);
				i--;
			}
		}
	}   //  checkBuffer

}
