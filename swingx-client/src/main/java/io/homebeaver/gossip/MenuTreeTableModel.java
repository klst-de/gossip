package io.homebeaver.gossip;

import java.util.Enumeration;
import java.util.logging.Logger;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

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
 
// interface TreeTableModel extends TreeModel
// interface javax.swing.table.TableModel
public class MusicTreeModel extends AbstractTableModel implements TreeTableModel, TableModel {

 */
public class MenuTreeTableModel extends AbstractTreeTableModel {

	private static final Logger LOG = Logger.getLogger(MenuTreeTableModel.class.getName());
	
//	MTreeNode root;
	
	public MenuTreeTableModel(MTreeNode root) {
		super(root);
//		this.root = root;
//		LOG.info(""+this.root + " with "+getChildCount(this.root)+" childs.");
	}
	
    // wg. implements TreeModel:
	@Override
    public Object getRoot() {
    	return super.getRoot();
    }

    // wg. implements TreeTableModel, (TableModel):
	@Override
	public int getColumnCount() {
		return ((MTreeNode)getRoot()).getColumnCount();
	}

    // wg. implements TreeTableModel:
	@Override
	public Object getValueAt(Object node, int column) {
		MTreeNode c = (MTreeNode) node;
		return c.getValueAt(c, column);
	}

    // wg. implements TreeTableModel, TreeModel:
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

    // wg. implements TreeTableModel, TreeModel:
	@Override
	public int getChildCount(Object parent) {
		MTreeNode c = (MTreeNode) parent;
		return c.getChildCount();
	}

    // wg. implements TreeTableModel, TreeModel:
	@Override
	public int getIndexOfChild(Object parent, Object child) {
		// If either parent or child is null, returns -1.
		if(parent==null) return -1;
		if(child==null) return -1;
		
		// If either parent or child don't belong to this tree model, returns -1.
		if(getChildCount(parent) == 0) return -1;		
		MTreeNode p = (MTreeNode) parent;
		
		Enumeration<?> enumCh = p.children();
		for(int i = 0; enumCh.hasMoreElements(); i++) {
			if(child == enumCh.nextElement()) return i;
		}
		return -1;
	}
	
    // wg. implements TreeTableModel, TreeModel:
	@Override
    public boolean isLeaf(Object node) {
		return super.isLeaf(node);
    	
    }

    // wg. implements TreeModel:
	@Override
    public void valueForPathChanged(TreePath path, Object newValue) {
		LOG.info("TreePath "+path+" newValue "+newValue);
    }

    // wg. implements TreeModel:
	@Override
    public void addTreeModelListener(TreeModelListener l) {
    	super.addTreeModelListener(l);
    }
	
    // wg. implements TreeModel:
	@Override
    public void removeTreeModelListener(TreeModelListener l) {
    	super.removeTreeModelListener(l);
    }
	
    // wg. implements (TableModel):
//	@Override
//    public int getRowCount() {
//		return 0;  	
//    }

    // wg. implements TreeTableModel:
    @Override
    public int getHierarchicalColumn() {
		return super.getHierarchicalColumn();	
    }

    // wg. implements TreeTableModel, (TableModel):
    @Override
    public Class<?> getColumnClass(int column) {
        return ((MTreeNode)getRoot()).getColumnClass(column);
    }

	/*
	 * per default, in super, sind ColumnName A, B, C, ... 
	 */
    // wg. implements TreeTableModel, (TableModel):
    @Override
    public String getColumnName(int columnIndex) {
		return ((MTreeNode)getRoot()).getColumnName(columnIndex);
    }

    // wg. implements TreeTableModel, (TableModel):
    @Override
    public boolean isCellEditable(Object node, int column) {
    	return super.isCellEditable(node, column);
    }

    // wg. implements TreeTableModel, (TableModel): value for the {@code node} at {@code columnIndex} to {@code value}
    @Override
    public void setValueAt(Object value, Object node, int column) {
		LOG.info("value "+value+" for "+node + " at "+column);
    }

}
