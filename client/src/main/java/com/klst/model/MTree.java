package com.klst.model;

import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.RowSet;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MClient;
import org.compiere.model.MRole;
import org.compiere.model.MTable;
//import org.compiere.model.MTreeNode;
import org.compiere.model.X_AD_Menu;
import org.compiere.print.MPrintColor;
import org.compiere.util.CLogMgt;
import org.compiere.util.DB;
import org.compiere.util.Env;

/*
 * ich will für die Darstellung von Bäumen ein neueres jdesktop.swingx nutzen und 
 * kann die Member rootNode, loadNodes, ... aus super nicht überschreiben, da sie private sind.
 * Also überschreibe ich die ganze Klasse.
 */
public class MTree extends org.compiere.model.MTree {

	private static final long serialVersionUID = -4802325261417990860L;
	private static final Logger LOG = Logger.getLogger(MTree.class.getName());
	
	// alle Member in super private - Um zu überschreiben, muss ich neu definieren
	boolean isTreeEditable = false;
	
/** Buffer while loading tree 
 
 
 */
	private Map<Integer, MTreeNode> treeNodeMap = new HashMap<Integer, MTreeNode>();
	private ArrayList<MTreeNode> treeNodes = new ArrayList<>();

	private RowSet nodeRowSet; // used in getNodeDetails

	MTreeNode rootNode = null; // in super MTreeNode extends javax.swing.tree.DefaultMutableTreeNode, ich will
	// org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode extends AbstractMutableTreeTableNode nutzen.
	// DefaultMutableTreeTableNode ist nur zum Testen da, "Thisimplementation is designed mainly for testing. It is NOT recommended to usethis implementation.",
	// ich muss daher von AbstractMutableTreeTableNode direkt ableiten

	private HashMap<Integer, ArrayList<Integer>> nodeIdMap;

	// ctor muss nicht public sein
	MTree(Properties ctx, int AD_Tree_ID, String trxName) {
		super(ctx, AD_Tree_ID, trxName);
		LOG.config("AD_Tree_ID=" + AD_Tree_ID + " trxName:"+trxName + " \nctx:"+ctx);
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
		String fromClause = getSourceTableName();
		//  SQL for TreeNodes
		StringBuffer sql = new StringBuffer("\nSELECT "
			+ "tn.Node_ID,tn.Parent_ID,tn.SeqNo,tb.IsActive "
			+ "\nFROM ").append(getNodeTableName()).append(" tn ")
			.append("\nLEFT JOIN ")
				.append(fromClause).append(" ON(")
					.append(fromClause).append(".").append(fromClause + "_ID").append(" = tn.Node_ID) ")
			//
			.append(" \nLEFT OUTER JOIN AD_TreeBar tb ON (tn.AD_Tree_ID=tb.AD_Tree_ID"
			+ " AND tn.Node_ID=tb.Node_ID "
			+ (userId != -1 ? " AND tb.AD_User_ID=? ": "") 	//	#1 (conditional)
			+ ") "
			+ "\nWHERE tn.AD_Tree_ID=?");								//	#2
		if (!isTreeEditable)
			sql.append(" AND tn.IsActive='Y'");
		//	Add GridTab Where Class
		if(whereClause != null
				&& whereClause.length() > 0)
			sql.append(" AND ").append(whereClause);
		//	End Yamel Senih
		sql.append(" \nORDER BY COALESCE(tn.Parent_ID, -1), tn.SeqNo");
		LOG.config(sql.toString());
		//  The Node Loop
		try
		{
			// load Node details - addToTree -> getNodeDetail
			getNodeDetails(); 
			//
			PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			int idx = 1;
			if (userId != -1)
				pstmt.setInt(idx++, userId);
			pstmt.setInt(idx++, getAD_Tree_ID());
			//	Get Tree & Bar
			ResultSet rs = pstmt.executeQuery();
			rootNode = new MTreeNode (0, 0, getName(), getDescription(), 0, true, null, false, null);
			while (rs.next())
			{ 
				int node_ID = rs.getInt(1);
				int parent_ID = rs.getInt(2);
				int seqNo = rs.getInt(3);
				boolean onBar = (rs.getString(4) != null); // tb.IsActive 
				//
				if (node_ID == 0 && parent_ID == 0)
					;
				else
					addToTree (node_ID, parent_ID, seqNo, onBar);	//	calls getNodeDetail (nodeId, parentId, seqNo, onBar)
			}
			rs.close();
			pstmt.close();
			//
			//closing the rowset will also close connection for oracle rowset implementation
			//nodeRowSet.close();
			nodeRowSet = null;
			nodeIdMap = null;
		}
		catch (SQLException e)
		{
			LOG.log(Level.SEVERE, sql.toString(), e);
			nodeRowSet = null;
			nodeIdMap = null;
		}
			
		//  Done with loading - add remainder from buffer
		if (treeNodes.size() != 0)
		{
			LOG.config("clearing buffer (expected size=45) size="+treeNodes.size()+"- Adding to: " + rootNode);
			treeNodes.forEach(c -> {
				MTreeNode p = rootNode.findNode(c.getParent_ID());
				if(p!=null) {
					LOG.config(p + " ist parant für "+c);
					p.add(c);
					treeNodeMap.remove(c.getNode_ID());
				}
			});
			LOG.config("nach clearing buffer treeNodeMap.Size="+treeNodeMap.size());
//			for (int i = 0; i < treeNodes.size(); i++)
//			{
//				MTreeNode node = (MTreeNode) treeNodes.get(i);
//				MTreeNode parent = rootNode.findNode(node.getParent_ID());
//				if (parent != null && parent.getAllowsChildren())
//				{
//					parent.add(node);
//					int sizeBeforeCheckBuffer = treeNodes.size();
//					checkBuffer(node);
//					if (sizeBeforeCheckBuffer == treeNodes.size())
//						treeNodes.remove(i);
//					i = -1;		//	start again with i=0
//				} else {
//					LOG.warning("clearing buffer "+( parent==null ? "no parent" : parent.getAllowsChildren() ) + " node:"+node);
//				}
//			}
		}

		//	Nodes w/o parent
		if (treeNodes.size() != 0)
		{ // TODO 
			LOG.severe (""+treeNodes.size()+" nodes w/o parent - NO!!!!!!! adding to root - " + treeNodes);
//			for (int i = 0; i < treeNodes.size(); i++)
//			{
//				MTreeNode node = (MTreeNode) treeNodes.get(i);
//				rootNode.add(node);
//				int sizeBeforeCheckBuffer = treeNodes.size();
//				checkBuffer(node);
//				if (sizeBeforeCheckBuffer == treeNodes.size())
//					treeNodes.remove(i);
//				i = -1;
//			}
//			if (treeNodes.size() != 0)
//				LOG.severe ("Still nodes in Buffer - " + treeNodes);
		}	//	nodes w/o parents

		//  clean up
		if (!isTreeEditable && rootNode.getChildCount() > 0)
			trimTree();
//		diagPrintTree();
		if (CLogMgt.isLevelFinest() || rootNode.getChildCount() == 0)
			LOG.fine("ChildCount=" + rootNode.getChildCount());
	}   //  loadNodes

	/**
	 *	Get Node TableName
	 *	@return node table name, e.g. AD_TreeNode
	 */
	public String getNodeTableName() {
		//	Yamel Senih, 2015-11-14
		//	Add support to old version
		String tableName = org.compiere.model.MTree.getNodeTableName(getTreeType());
		if(tableName == null) {
			tableName = org.compiere.model.MTree.getNodeTableName(getAD_Table_ID());
		}
		//	Return
		return tableName;
	}	//	getNodeTableName

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
		StringBuffer sqlNode = new StringBuffer();
		String sourceTable = "t";
		String fromClause = getSourceTableName();
		String color = getActionColorName();
		LOG.config("fromClause aka SourceTableName:"+fromClause + " color aka ActionColorName:"+color);
		if (getTreeType().equals(TREETYPE_Menu)) {
			boolean base = Env.isBaseLanguage(p_ctx, "AD_Menu");
			sourceTable = "m";
			if (base) {
				sqlNode.append("\nSELECT m.AD_Menu_ID, m.Name,m.Description,m.IsSummary,m.Action, "
					+ "m.AD_Window_ID, m.AD_Process_ID, m.AD_Form_ID, m.AD_Workflow_ID, m.AD_Task_ID, m.AD_Workbench_ID "
					+ ", m.AD_Browse_ID "
					+ "\nFROM AD_Menu m\n");
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
				sqlNode.append(hasWhere ? "\n AND " : " WHERE ");
				sqlNode.append("(m.AD_Window_ID IS NULL OR EXISTS (SELECT 1 FROM AD_Window w WHERE m.AD_Window_ID=w.AD_Window_ID AND w.IsBetaFunctionality='N'))")
					.append("\n AND (m.AD_Process_ID IS NULL OR EXISTS (SELECT 1 FROM AD_Process p WHERE m.AD_Process_ID=p.AD_Process_ID AND p.IsBetaFunctionality='N'))")
					.append("\n AND (m.AD_Workflow_ID IS NULL OR EXISTS (SELECT 1 FROM AD_Workflow wf WHERE m.AD_Workflow_ID=wf.AD_Workflow_ID AND wf.IsBetaFunctionality='N'))")
					.append("\n AND (m.AD_Form_ID IS NULL OR EXISTS (SELECT 1 FROM AD_Form f WHERE m.AD_Form_ID=f.AD_Form_ID AND f.IsBetaFunctionality='N'))")
					.append("\n AND (m.AD_Browse_ID IS NULL OR EXISTS (SELECT 1 FROM AD_Browse b WHERE m.AD_Browse_ID=b.AD_Browse_ID AND b.IsBetaFunctionality='N'))");
			}
			//	In R/O Menu - Show only defined Forms
			if (!isTreeEditable) {
				boolean hasWhere = sqlNode.indexOf(" WHERE ") != -1;
				sqlNode.append(hasWhere ? "\n AND " : " WHERE ");
				sqlNode.append("(m.AD_Form_ID IS NULL OR EXISTS (SELECT 1 FROM AD_Form f WHERE m.AD_Form_ID=f.AD_Form_ID AND (f.Classname IS NOT NULL OR f.JSPURL IS NOT NULL)))");
			}
			LOG.config("fertig mit "+getTreeType() + " isBaseLanguage="+base);
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
		sqlNode.append("\n");
		String sql = sqlNode.toString();
		if (!isTreeEditable)	//	editable = menu/etc. window
			sql = MRole.getDefault(getCtx(), false).addAccessSQL(sql, sourceTable, MRole.SQL_FULLYQUALIFIED, isTreeEditable);
		LOG.config(sql);
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
			LOG.config("#nodes aka i="+i + " nodeIdMap#:"+nodeIdMap.size() + " nodeIdMap[218]:"+nodeIdMap.get(Integer.valueOf(218)).size()); // NPE: + " nodeIdMap[0]#:"+nodeIdMap.get(Integer.valueOf(0)).size());
		} catch (SQLException e) 
		{
			LOG.log(Level.SEVERE, "", e);
		}
	}   //  getNodeDetails

	/**
	 * 	Get Source TableName
	 *	@param treeType tree typw
	 *	@return source table name, e.g. AD_Org or null 
	 */
	public String getSourceTableName() {
		if(getTreeType().equals(TREETYPE_CustomTree)) {
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
		//	Get Tree Type
		return org.compiere.model.MTree.getSourceTableName(getTreeType());
	}	//	getSourceTableName

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
	private void addToTree(int nodeId, int parentId, int seqNo, boolean onBar) {
		// Create new Node
		MTreeNode child = getNodeDetail(nodeId, parentId, seqNo, onBar);
		if (child == null)
			return;

		// Add to Tree
		MTreeNode parent = null;
		if (rootNode != null)
			parent = rootNode.findNode(parentId);

		if(parent==null) { // in Liste eintragen, da im Baum parant nicht gefunden!
			LOG.warning("parent with id "+parentId+" not in tree! Adding ("+child+") to treeNodes #:"+treeNodes.size());
			treeNodes.add(child);
			treeNodeMap.put(child.getNode_ID(), child);
		} else {
			// parent ist möglicherweise gar nicht parent von child !!
			if(parent.getAllowsChildren()) {
				parent.add(child);
//				checkBuffer(child);
				if(treeNodeMap.get(child.getNode_ID()) != null) {
					LOG.config("könnte kandidat für parant sein : "+child);
				}
				LOG.config("added "+child + " to "+parent + " - treeNodeMap.Size="+treeNodeMap.size() + " == treeNodes.Size="+treeNodes.size());
				treeNodes.forEach(c -> {
					MTreeNode k = c.findNode(child.getParent_ID());
					if(k!=null) {
						LOG.config("ist parant füt : "+k);
					}
				});
			} else {
				LOG.warning("?????????????? parent does not allow Children "+parentId);
			}
		}		
	}

	/**
	 *  Check the buffer for nodes which have newNode as Parents
	 *  @param newNode new node
	 */
	private void checkBufferX (MTreeNode newNode)
	{ 
		if(treeNodes.isEmpty()) return;
		
		//	Ability to add nodes
		if (!newNode.isSummary() || !newNode.getAllowsChildren())
			return;
		//
		for (int i = 0; i < treeNodes.size(); i++)
		{
			MTreeNode node = (MTreeNode) treeNodes.get(i);
			if (node.getParent_ID() == newNode.getNode_ID())
			{ // da die IDs der Knoten gleich sind, ist ein add unsinnig!!! 
				try
				{
					newNode.add(node); // void org.jdesktop.swingx.treetable.AbstractMutableTreeTableNode.add(MutableTreeTableNode child)
					//                    void javax.swing.tree.DefaultMutableTreeNode.add(MutableTreeNode newChild)
					// Removes <code>newChild</code> from its parent and makes it a child of this node by adding it to the end of this node's child array.
					//node.removeFromParent(); // damit wird Data/157 zum Leaf und Utility/195 kein child von Data ??????
				}
				catch (Exception e)
				{
					LOG.severe("Adding " + node.getNode_ID()+"/"+ node.getName() 
						+ " to " + newNode.getNode_ID()+"/"+newNode.getName() + ": " + e.getMessage());
				}
				treeNodes.remove(i);
				i--;
			}
		}
	}   //  checkBuffer

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
					{ LOG.config("========================= 532 actionColor="+actionColor);
						retValue = new MTreeNode (node_ID, seqNo, name, description, parent_ID, isSummary, actionColor, onBar, null);	//	menu has no color
					}
				} else if(getTreeType().equals(TREETYPE_CustomTree)) { LOG.config("========================= 537");
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
					LOG.config("========================= 558 TreeType:"+getTreeType() + " sSummary:"+sSummary);
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

}
