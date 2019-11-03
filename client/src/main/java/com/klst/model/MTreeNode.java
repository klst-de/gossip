package com.klst.model;

import java.awt.Color;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

import org.compiere.wf.MWFNode;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.TreeTableNode;

import com.klst.icon.AbstractImageTranscoder;

/*
 *  Mutable Tree Node (not a PO).
 *
 *  Ersetzt org.compiere.model.MTreeNode , das von javax.swing.tree.DefaultMutableTreeNode ableitet
 *                                         und MutableTreeNode implementiert : ... implements Cloneable, MutableTreeNode, Serializable
 *                                         
 *  Ich leite ab von jdesktop                                     AbstractMutableTreeTableNode implements MutableTreeTableNode extends TreeTableNode extends TreeNode
 *                            DefaultMutableTreeTableNode extends AbstractMutableTreeTableNode
 */
public class MTreeNode extends DefaultMutableTreeTableNode { // implements NodeModel {

	private static final Logger LOG = Logger.getLogger(MTreeNode.class.getName());

	@Override
	public int getColumnCount() {	
		return 3; // == columnName.length oder kleiner, default ist 1 in super
	}

	// ---- ab hier wg. implements NodeModel mit @Override, ohne @Override, damit es in MenuTreeModel aufrufbar wird
//	@Override
	public Class<?> getColumnClass(int column) {
		return columnClass[column];
	}

//	@Override
	public String getColumnName(int column) {
		return columnName[column];
	}

//	@Override
//	public int getHierarchicalColumn() {
//		return 0;
//	}
//
//	@Override
	public Object getValueAt(Object node, int column) {
		MTreeNode c = (MTreeNode) node;
		Object o = null;
		
		switch (column) {
		case 0:
			o = c.getName();
			break;
		case 1:
			o = Integer.valueOf(c.getNode_ID());
			break;
		case 2:
			o = c.getImageIndicator();
			break;
		case 3:
			o = c.getImageIcon();
			break;
		default:
			// does nothing
			break;
		}
		return o;
	}

//	@Override
//	public boolean isCellEditable(Object node, int column) {
//		return false;
//	}
//
//	@Override
//	public void setValueAt(Object value, Object node, int column) {
//		// noop - not editable	
//	}

	public static final String[] columnName = 
		{ "Name"
		, "Node_Id"
		, "II" // II == m_imageIndicator
		, "Icon"
		};
	
	public static final Class<?>[] columnClass = 
		{ String.class
		, Integer.class
		, String.class
		, ImageIcon.class
		};

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
		super();
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
		setUserObject(this); //(name + " img:"+imageIndicator); // statt im super ctor
	}   //  MTreeNode

	private int     	m_node_ID;
	private int     	m_seqNo;
	private String  	m_name;
	private String  	m_description;
	private int     	m_parent_ID;
	private boolean 	m_isSummary;
	private String      m_imageIndicator; // image indicator (W/X/R/P/F/T/B) MWFNode.ACTION_
	private int 		m_imageIndex = 0; // Index to Icon
	private boolean 	m_onBar;
	private Color 		m_color;
//	private int			m_menu_ID;
//	private boolean		m_iscollapsible;

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
	
	public String getImageIndicator() {
		return m_imageIndicator;
	}

	public int getImageIndex() {
		return m_imageIndex;
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
	public void setSummary(boolean isSummary) {
		m_isSummary = isSummary;
		super.setAllowsChildren(isSummary);
	}

	/**
	 *  Set Image Indicator and Index
	 *  @param imageIndicator image indicator (W/X/R/P/F/T/B) MWFNode.ACTION_
	 */
	public void setImageIndicator(String imageIndicator) {
		if (imageIndicator != null) {
			m_imageIndicator = imageIndicator;
			m_imageIndex = getImageIndex(m_imageIndicator);
		}
	}

	public static final int TYPE_WINDOW = 1;
	public static final int TYPE_REPORT = 2;
	public static final int TYPE_PROCESS = 3;
	public static final int TYPE_WORKFLOW = 4;
	public static final int TYPE_WORKBENCH = 5;
	public static final int TYPE_SETVARIABLE = 6;
	public static final int TYPE_USERCHOICE = 7;
	public static final int TYPE_DOCACTION = 8;

	static final int SMALL_ICON_SIZE = 16;
	AbstractImageTranscoder AIT = AbstractImageTranscoder.getInstance();
	public ImageIcon getImageIcon() {
		return getImageIcon(AIT, m_imageIndex, SMALL_ICON_SIZE);
	}
	public static ImageIcon getImageIcon(AbstractImageTranscoder AIT, int index, int iconSize) {
	      switch (index) {
	      case TYPE_WINDOW:
	    	  return AIT.getImageIcon(AIT.MENU_WINDOW, iconSize);
	      case TYPE_REPORT:
	    	  return AIT.getImageIcon(AIT.REPORT, iconSize);
	      case TYPE_PROCESS:
	    	  return AIT.getImageIcon(AIT.PROCESS, iconSize);
	      case TYPE_WORKFLOW:
	    	  return AIT.getImageIcon(AIT.WORKFLOW, iconSize);
	      case TYPE_WORKBENCH:
	    	  return AIT.getImageIcon(AIT.END, iconSize);
	      case TYPE_SETVARIABLE:
	    	  return AIT.getImageIcon(AIT.REPORT, iconSize);
	      case TYPE_USERCHOICE:
	    	  return AIT.getImageIcon(AIT.PROCESS, iconSize);
	      case TYPE_DOCACTION:
	    	  return AIT.getImageIcon(AIT.WORKFLOW, iconSize);
	      default:
	    	  return AIT.getImageIcon(AIT.FOLDER, iconSize);
	      }
	}
	
	/**************************************************************************
	 *  Get Image Indicator/Index
	 *  @param imageIndicator image indicator (W/X/R/P/F/T/B) MWFNode.ACTION_
	 *  @return index of image
	 */
	public static int getImageIndex (String imageIndicator) {
		int imageIndex = 0;
		if (imageIndicator == null)
			;
		else if (imageIndicator.equals(MWFNode.ACTION_UserWindow)		// W	Window 
			|| imageIndicator.equals(MWFNode.ACTION_UserForm))          // X
			imageIndex = TYPE_WINDOW;
		else if (imageIndicator.equals(MWFNode.ACTION_AppsReport))		// R	Report
			imageIndex = TYPE_REPORT;
		else if (imageIndicator.equals(MWFNode.ACTION_AppsProcess)		// P	Process
			|| imageIndicator.equals(MWFNode.ACTION_AppsTask))          // T
			imageIndex = TYPE_PROCESS;
		else if (imageIndicator.equals(MWFNode.ACTION_SubWorkflow))		// F	WorkFlow
			imageIndex = TYPE_WORKFLOW;
		/*
		else if (imageIndicator.equals(MWFNode.ACTION_UserWorkbench))	//	Workbench
			imageIndex = TYPE_WORKBENCH;
		*/
		else if (imageIndicator.equals(MWFNode.ACTION_SetVariable))		// V	Set Variable
			imageIndex = TYPE_SETVARIABLE;
		else if (imageIndicator.equals(MWFNode.ACTION_UserChoice))		// C	User Choice
			imageIndex = TYPE_USERCHOICE;
		else if (imageIndicator.equals(MWFNode.ACTION_DocumentAction))	// D	Document Action
			imageIndex = TYPE_DOCACTION;
		else if (imageIndicator.equals(MWFNode.ACTION_WaitSleep))		// Z	Sleep
			;
		else if (imageIndicator.equals(MWFNode.ACTION_SmartBrowse))		// S	Smart Browser
			imageIndex = TYPE_DOCACTION;
		return imageIndex;
	}

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
		return null;
	}

}
