package io.homebeaver.gossip;

import java.sql.SQLException;
import java.util.logging.Level;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import org.compiere.model.MColumn;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Trx;

import com.klst.model.MTree;
import com.klst.model.MTreeNode;

/**
 * provides a persistable tree model based on an MTree.
 */
// ersetzt (client)org.compiere.grid.tree.AdempiereTreeModel
public class GossipTreeModel extends DefaultTreeModel {

	private static final long serialVersionUID = -6100763060946741888L;
//	static Logger LOG = Logger.getLogger(GossipTreeModel.class.getName());
	private static CLogger LOG = CLogger.getCLogger(GossipTreeModel.class);

	public GossipTreeModel(TreeNode root) {
		this(root, false);
	}
	public GossipTreeModel(TreeNode root, boolean asksAllowsChildren) {
		super(root, asksAllowsChildren);
	}
	
	private MTree m_MTree;
	
	public void setMTree(MTree tree) {
		m_MTree = tree;
	}
	
	public void saveChangedNodes(MTreeNode from, MTreeNode to) {
		int AD_Tree_ID = m_MTree.getAD_Tree_ID();
		Trx trx = Trx.get (Trx.createTrxName("AdempiereTreeModel"), true);
		try {
			for (int i = 0; i < from.getChildCount(); i++) {
				MTreeNode nd = (MTreeNode)from.getChildAt(i);
				String whereClause = "AD_Tree_ID="+AD_Tree_ID+ " AND Node_ID=" + nd.getNode_ID();
				PO tree = MTable.get(Env.getCtx(), m_MTree.getNodeTableName()).getPO(whereClause, trx.getTrxName());
				if (tree.get_ValueAsInt("Parent_ID") != from.getNode_ID() || tree.get_ValueAsInt("SeqNo") != i) {
					tree.set_CustomColumn("Parent_ID", from.getNode_ID());
					tree.set_CustomColumn("SeqNo", i);
					tree.saveEx();
				}
			}
			if (from != to) {
				// Renumber and set parent ID for the children of the 'to' node.
				int nextSeqNo = 0;
				for (int i = 0; i < to.getChildCount(); i++) {
					MTreeNode nd = (MTreeNode)to.getChildAt(i);
					String whereClause = "AD_Tree_ID="+AD_Tree_ID+ " AND Node_ID=" + nd.getNode_ID();
					PO tree = MTable.get(Env.getCtx(), m_MTree.getNodeTableName()).getPO(whereClause, trx.getTrxName());
					if (tree.get_ValueAsInt("Parent_ID") != to.getNode_ID() || tree.get_ValueAsInt("SeqNo") < nextSeqNo) {
						tree.set_CustomColumn("Parent_ID", to.getNode_ID());
						tree.set_CustomColumn("SeqNo", nextSeqNo++);
						tree.saveEx();
					} else {
						nextSeqNo = tree.get_ValueAsInt("SeqNo") + 1;
					}
				}
				//Update Table if has parent Column
				if (m_MTree.getParent_Column_ID() > 0) {
					MColumn parentColumn = MColumn.get(Env.getCtx(), m_MTree.getParent_Column_ID());
					MTable treeTable = MTable.get(Env.getCtx(),m_MTree.getAD_Table_ID());
					String[] keyColumns = treeTable.getKeyColumns();
					if (keyColumns.length>0) {
						String whereClause = keyColumns[0] + "=" + from.getNode_ID();
						PO table = MTable.get(Env.getCtx(), MTable.getTableName(Env.getCtx(), m_MTree.getAD_Table_ID())).getPO(whereClause, trx.getTrxName());
						if (table.get_ID() > 0) {
							if (to.getNode_ID()>0) {
								table.set_ValueOfColumn(parentColumn.getColumnName(), to.getNode_ID());
							} else {
								table.set_ValueOfColumn(parentColumn.getColumnName(), null);
							}
							table.saveEx();
						}
					}
				}
			}
			trx.commit(true); // throws SQLException
		} catch (SQLException e) {
			trx.rollback();
			LOG.log(Level.SEVERE, "move", e);
		}
		trx.close();
		trx = null;
		LOG.config("complete");
	}
}
