package com.klst.model;

import java.awt.Color;
import java.util.logging.Logger;

import org.compiere.wf.MWFNode;
import org.jdesktop.swingx.treetable.AbstractMutableTreeTableNode;
import org.jdesktop.swingx.treetable.TreeTableNode;

/*
 *  Mutable Tree Node (not a PO).
 *
 *  Ersetzt org.compiere.model.MTreeNode , das von javax.swing.tree.DefaultMutableTreeNode ableitet
 *                                         und MutableTreeNode implementiert : ... implements Cloneable, MutableTreeNode, Serializable
 */
public class MTreeNode extends AbstractMutableTreeTableNode {
//public class MTreeNode extends DefaultMutableTreeNode {

	private static final Logger LOG = Logger.getLogger(MTreeNode.class.getName());
	
	@Override
	public Object getValueAt(int column) {
		return m_name;
	}

	@Override
	public int getColumnCount() {
		return 1;
	}

//	@Override // wg. LOG
//	public void add(MutableTreeTableNode child) {
//		LOG.config("add child "+child + " to "+this);
//		super.add(child);
//	}
	/**
	 *  Construct Model TreeNode
	 *  @param node_ID	node
	 *  @param seqNo sequence
	 *  @param name name
	 *  @param description description
	 *  @param parent_ID parent
	 *  @param isSummary summary                    aka allowsChildren
	 *  @param imageIndicator image indicator
	 *  @param onBar on bar
	 *  @param color color
	 */
	public MTreeNode (int node_ID, int seqNo, String name, String description,
		int parent_ID, boolean isSummary, String imageIndicator, boolean onBar, Color color)
	{
		super(name + " img:"+imageIndicator);
		LOG.config( "MTreeNode Node_ID=" + node_ID + ", seqNo=" + seqNo + ", Parent_ID=" + parent_ID + " - " + name);
		m_node_ID = node_ID;
		m_seqNo = seqNo;
		m_name = name;
		m_description = description;
		if (m_description == null)
			m_description = "";
		m_parent_ID = parent_ID;
		setSummary(isSummary);
		setImageIndicator(imageIndicator);
		m_onBar = onBar;
		m_color = color;
		setUserObject(name + " img:"+imageIndicator); // wie im super ctor
	}   //  MTreeNode

	/** Node ID         */
	private int     	m_node_ID;
	/**	SeqNo			*/
	private int     	m_seqNo;
	/** Name			*/
	private String  	m_name;
	/** Description		*/
	private String  	m_description;
	/**	Parent ID		*/
	private int     	m_parent_ID;
	/**	Summaty			*/
	private boolean 	m_isSummary;
	/** Image Indicator				*/
	private String      m_imageIndicator;
	/** Index to Icon               */
	private int 		m_imageIndex = 0;
	/**	On Bar			*/
	private boolean 	m_onBar;
	/**	Color			*/
	private Color 		m_color;
	private int			m_menu_ID;
	private boolean		m_iscollapsible;

	public String toString() {
		return m_node_ID + "/" + m_parent_ID + " " + m_seqNo + " - " + m_name;
	}

	public int getNode_ID() {
		return m_node_ID;
	}

	public String getName() {
		return m_name;
	}

	public int getParent_ID() {
		return m_parent_ID;
	}

	/**
	 *  Allow children to be added to this node
	 *  @return true if summary node
	 */
	public boolean isSummary() {
		return m_isSummary;
	}

	/**************************************************************************
	 *  Set Summary (allow children)
	 *  @param isSummary summary node
	 */
	public void setSummary (boolean isSummary)
	{
		m_isSummary = isSummary;
		super.setAllowsChildren(isSummary);
	}   //  setSummary

	/**
	 *  Set Image Indicator and Index
	 *  @param imageIndicator image indicator (W/X/R/P/F/T/B) MWFNode.ACTION_
	 */
	public void setImageIndicator (String imageIndicator)
	{
		if (imageIndicator != null)
		{
			m_imageIndicator = imageIndicator;
			m_imageIndex = getImageIndex(m_imageIndicator);
		}
	}   //  setImageIndicator

	/**	Window - 1			*/
	public static int		TYPE_WINDOW = 1;
	/**	Report - 2			*/
	public static int		TYPE_REPORT = 2;
	/**	Process - 3			*/
	public static int		TYPE_PROCESS = 3;
	/**	Workflow - 4		*/
	public static int		TYPE_WORKFLOW = 4;
	/**	Workbench - 5		*/
	public static int		TYPE_WORKBENCH = 5;
	/**	Variable - 6		*/
	public static int		TYPE_SETVARIABLE = 6;
	/**	Choice - 7			*/
	public static int		TYPE_USERCHOICE = 7;
	/**	Action - 8			*/
	public static int		TYPE_DOCACTION = 8;

	/**************************************************************************
	 *  Get Image Indicator/Index
	 *  @param imageIndicator image indicator (W/X/R/P/F/T/B) MWFNode.ACTION_
	 *  @return index of image
	 */
	public static int getImageIndex (String imageIndicator)
	{
		int imageIndex = 0;
		if (imageIndicator == null)
			;
		else if (imageIndicator.equals(MWFNode.ACTION_UserWindow)		//	Window 
			|| imageIndicator.equals(MWFNode.ACTION_UserForm))
			imageIndex = TYPE_WINDOW;
		else if (imageIndicator.equals(MWFNode.ACTION_AppsReport))		//	Report
			imageIndex = TYPE_REPORT;
		else if (imageIndicator.equals(MWFNode.ACTION_AppsProcess)		//	Process
			|| imageIndicator.equals(MWFNode.ACTION_AppsTask))
			imageIndex = TYPE_PROCESS;
		else if (imageIndicator.equals(MWFNode.ACTION_SubWorkflow))		//	WorkFlow
			imageIndex = TYPE_WORKFLOW;
		/*
		else if (imageIndicator.equals(MWFNode.ACTION_UserWorkbench))	//	Workbench
			imageIndex = TYPE_WORKBENCH;
		*/
		else if (imageIndicator.equals(MWFNode.ACTION_SetVariable))		//	Set Variable
			imageIndex = TYPE_SETVARIABLE;
		else if (imageIndicator.equals(MWFNode.ACTION_UserChoice))		//	User Choice
			imageIndex = TYPE_USERCHOICE;
		else if (imageIndicator.equals(MWFNode.ACTION_DocumentAction))	//	Document Action
			imageIndex = TYPE_DOCACTION;
		else if (imageIndicator.equals(MWFNode.ACTION_WaitSleep))		//	Sleep
			;
		else if (imageIndicator.equals(MWFNode.ACTION_SmartBrowse))		//	Smart Browser
			imageIndex = TYPE_DOCACTION;
		return imageIndex;
	}   //  getImageIndex

	/*************************************************************************/

	/**	Last found ID				*/
	private int                 m_lastID = -1;
	/** Last found Node				*/
	private MTreeNode           m_lastNode = null;

	/**
	 *	Return the Node with ID in list of children
	 *  @param ID id
	 *  @return VTreeNode with ID or null
	 */
	public MTreeNode findNode(int ID) {
		if (m_node_ID == ID)
			return this;
		//
		if (ID == m_lastID && m_lastNode != null)
			return m_lastNode;
		
		if(getChildCount()==0) return null;
		
		this.getChildCount();
		
		for(int c=0; c<getChildCount(); c++) {
			TreeTableNode n = this.getChildAt(c);
			MTreeNode nd = (MTreeNode)n;
			if (ID == nd.getNode_ID()) {
				m_lastID = ID;
				m_lastNode = nd;
				return nd;
			} else {
				//LOG.config("<>"+nd.getNode_ID() +"=?="+ID + " #"+c);
			}
		}
//		// ist in DefaultMutableTreeNode : private final class PreorderEnumeration implements Enumeration<TreeNode>
//		// wie finde ich es via AbstractMutableTreeTableNode ? gar nicht! dann kopiere ich es
//		Enumeration<MTreeNode> en = preorderEnumeration();
//		int i=0;
//		while (en.hasMoreElements()) {
//			MTreeNode nd = en.nextElement();
//			if (ID == nd.getNode_ID()) {
//				m_lastID = ID;
//				m_lastNode = nd;
//				return nd;
//			} else {
//				LOG.config("<>"+nd.getNode_ID() +"=?="+ID + " preorderEnumeration #"+i);
//			}
//			i++;
//		}
//		LOG.config("preorderEnumeration #"+i);
		return null;
	}

//    public Enumeration<MTreeNode> preorderEnumeration() {
//        return new PreorderEnumeration(this);
//    }
//    // aus javax.swing.tree.DefaultMutableTreeNode.PreorderEnumeration;
//   private final class PreorderEnumeration implements Enumeration<MTreeNode> {
//        private final Stack<Enumeration<MTreeNode>> stack = new Stack<Enumeration<MTreeNode>>();
//
//        public PreorderEnumeration(MTreeNode rootNode) {
//            super();
//            Vector<MTreeNode> v = new Vector<MTreeNode>(1);
//            v.addElement(rootNode);     // PENDING: don't really need a vector
//            stack.push(v.elements());
//        }
//
//        public boolean hasMoreElements() {
//            return (!stack.empty() && stack.peek().hasMoreElements());
//        }
//
//        public MTreeNode nextElement() {
//            Enumeration<MTreeNode> enumer = stack.peek();
//            MTreeNode    node = enumer.nextElement();
//            Enumeration children = node.children();
//
//            if (!enumer.hasMoreElements()) {
//                stack.pop();
//            }
//            if (children.hasMoreElements()) {
//                stack.push(children);
//            }
//            return node;
//        }
//
//    }  // End of class PreorderEnumeration

}
