package io.homebeaver.gossip;

import java.util.Enumeration;
import java.util.logging.Logger;

import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

import com.klst.model.MTreeNode;

/*
To create a concreate instance of TreeTableModel you need only to provide implementations for the following methods: 
 public int getColumnCount();
 public Object getValueAt(Object node, int column);
 public Object getChild(Object parent, int index);
 public int getChildCount(Object parent);
 public int getIndexOfChild(Object parent, Object child);
 public boolean isLeaf(Object node);
 */
public class MenuTreeTableModel extends AbstractTreeTableModel {

	private static final Logger LOG = Logger.getLogger(MenuTreeTableModel.class.getName());
	
	MTreeNode root;
	
	public MenuTreeTableModel(MTreeNode root) {
		super(root);
		this.root = root;
//		LOG.info(""+this.root + " with "+getChildCount(this.root)+" childs.");
	}
	
    @Override
    public Class<?> getColumnClass(int column) {
        return root.getColumnClass(column);
    }

	/*
	 * per default, in super, sind ColumnName A, B, C, ... 
	 */
    @Override
    public String getColumnName(int column) {
    	return root.getColumnName(column);
    }

    // wg. extends AbstractTreeTableModel:
	@Override
	public int getColumnCount() {
		return root.getColumnCount();
	}

	@Override
	public Object getValueAt(Object node, int column) {
		MTreeNode c = (MTreeNode) node;
		return c.getValueAt(c, column);
	}

	@Override
	public Object getChild(Object parent, int index) {
		MTreeNode c = (MTreeNode) parent;
		
		// This should not return null if index is a valid index for parent (that is index >= 0 && index < getChildCount(parent)).
//		LOG.config("ChildCount="+c.getChildCount() + " for "+c);
//		
//		org.jdesktop.swingx.treetable.TreeTableNode ttn = c.getChildAt(index);
//		LOG.config("Child "+index+" of "+c.getChildCount() + " for "+c + " is "+ttn);
		
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
	
}
