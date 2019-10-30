package com.klst.gossip;

import java.util.Enumeration;
import java.util.logging.Logger;

import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

import com.klst.model.MTreeNode;

/*
To create a concreate instance of TreeTableModel you need only to provide implementations for thefollowing methods: 
 public int getColumnCount();
 public Object getValueAt(Object node, int column);
 public Object getChild(Object parent, int index);
 public int getChildCount(Object parent);
 public int getIndexOfChild(Object parent, Object child);
 public boolean isLeaf(Object node);
 */
public class MenuTreeTableModel extends AbstractTreeTableModel {

	private static final Logger LOG = Logger.getLogger(MenuTreeTableModel.class.getName());
	
	public MenuTreeTableModel(MTreeNode root) {
		super(root);
		LOG.config("ctor for "+root + ". Root has "+this.getChildCount(this.getRoot()) + " children, #Columns:"+root.getColumnCount());
//		root.getColumnCount();
//		assert(this.getRoot()==root);
	}
	
	@Override
	public int getColumnCount() {
		MTreeNode root = (MTreeNode) getRoot();
		return root.getColumnCount();
	}

	@Override
	public void setValueAt(Object value, Object node, int column) {
		LOG.config("at node "+node + " value '"+value+"+ column:"+column); // nur zum loggen
		// super does nothing
	}
	
	@Override
	public Object getValueAt(Object node, int column) {
		MTreeNode c = (MTreeNode) node;
		Object o = null;
		
//		// derzeit nur eine Spalte, der Name, @see getColumnCount()
//		o = c.getName();
		
      switch (column) {
      case 0:
    	  o = c.getName();
          break;
      case 1:
          o = Integer.valueOf(c.getNode_ID());
          break;
//      case 2:
//          if (c.isShowing()) {
//              o = c.getLocationOnScreen();
//          }
//          break;
//      case 3:
//          o = c.getSize();
//          break;
      default:
          //does nothing
          break;
      }
		return o;
	}

	@Override
	public Object getChild(Object parent, int index) {
		MTreeNode c = (MTreeNode) parent;
		
		// This should not return null if index is a valid index for parent (that is index >= 0 && index < getChildCount(parent)).
//		LOG.config("ChildCount="+c.getChildCount() + " for "+c);
		return c.getChildAt(index);
	}

	@Override
	public int getChildCount(Object parent) {
		MTreeNode c = (MTreeNode) parent;
		return c.getChildCount();
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		// If either parent or child is null, returns -1.
		if(parent==null) return -1;
		if(child==null) return -1;
		
		// If either parent or child don't belong to this tree model, returns -1.
		if(getChildCount(parent) == 0) return -1;		
		MTreeNode p = (MTreeNode) parent;
		
		Enumeration enumCh = p.children();
		for(int i = 0; enumCh.hasMoreElements(); i++) {
			if(child == enumCh.nextElement()) return i;
		}
		return -1;
	}

	// ----------------
	public Class<?> getColumnClass(int column) {
		return String.class;
	}
	
	public boolean isLeaf(Object node) {
		return super.isLeaf(node);	
	}
}
