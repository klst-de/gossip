package com.klst.client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Window;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;

import org.compiere.util.Env;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.AbstractHighlighter;
import org.jdesktop.swingx.decorator.ComponentAdapter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.renderer.DefaultTreeRenderer;
import org.jdesktop.swingx.renderer.IconValue;
import org.jdesktop.swingx.renderer.StringValue;
import org.jdesktop.swingx.renderer.StringValues;
import org.jdesktop.swingx.renderer.WrappingIconPanel;
import org.jdesktop.swingx.table.ColumnFactory;
import org.jdesktop.swingx.table.TableColumnExt;
import org.jdesktop.swingx.treetable.TreeTableModel;

import com.klst.client.TreeDemoIconValues.FilteredIconValue;
import com.klst.gossip.MenuTreeTableModel;
import com.klst.icon.AbstractImageTranscoder;
import com.klst.model.MTree;
import com.klst.model.MTreeNode;

public class MenuPanel extends JXPanel {

	private static final long serialVersionUID = 3820339775333768359L;
	static Logger LOG = Logger.getLogger(MenuPanel.class.getName());

	public MenuPanel() {
        super(new BorderLayout());
        initComponents();
        configureComponents();
        bind();	
	}

    private JXTreeTable tree;
    private JXButton refreshButton;
    private JXButton expandButton;
    private JXButton collapseButton;

    /**
     * Overridden to create and install the component tree model.
     */
    @Override // javax.swing.JComponent.addNotify
    public void addNotify() {
        super.addNotify();
        if (tree.getModel() == null) {
            tree.setTreeTableModel(createTreeModel());
        }
    }

/*
                                             | AD_Tree_ID = per SQL: (default 10)
SELECT COALESCE(r.AD_Tree_Menu_ID, ci.AD_Tree_Menu_ID)
  FROM AD_ClientInfo ci 
  INNER JOIN AD_Role r ON (ci.AD_Client_ID=r.AD_Client_ID) 
 WHERE AD_Role_ID=? -- 102

das sql in (private) MTree.loadNodes
SELECT tn.Node_ID,tn.Parent_ID,tn.SeqNo,tb.IsActive 
FROM AD_TreeNodeMM tn 
LEFT JOIN AD_Menu ON(AD_Menu.AD_Menu_ID = tn.Node_ID)  
LEFT OUTER JOIN AD_TreeBar tb ON (tn.AD_Tree_ID=tb.AD_Tree_ID AND tn.Node_ID=tb.Node_ID  AND tb.AD_User_ID=0 ) 
WHERE tn.AD_Tree_ID=10 AND tn.IsActive='Y' 
ORDER BY COALESCE(tn.Parent_ID, -1), tn.SeqNo
 */
    private TreeTableModel createTreeModel() {
        Window window = SwingUtilities.getWindowAncestor(this);
        //return ComponentModels.getTreeTableModel(window != null ? window : this); // org.jdesktop.swingxset.util.ComponentModels
        return getTreeTableModel(window != null ? window : this);
     }
    // interim
    static TreeTableModel getTreeTableModel(Component root) {
    	int treeId = 10;
    	boolean editable = false;
    	boolean allNodes = false;
    	String whereClause = null, trxName = null;
    	LOG.info("AD_Tree_ID = per SQL: (default 10) ="+treeId);
    	MTree vTree = new MTree(Env.getCtx(), treeId, editable, allNodes, whereClause, trxName);
    	LOG.info(vTree.getName() + " isMenu="+vTree.isMenu() + " root=" // + vTree.getRoot() 
    			+ " rootNode=" + vTree.getRootNode());
//    	MTreeNode (extends DefaultMutableTreeNode) root = -- ich will von AbstractMutableTreeTableNode ableiten, dh
//    			vTree.getRoot(); // kann ich gar nicht nutzen, muss daher MTree subclassen 
    	MTreeNode rootNode = vTree.getRootNode();
/*
		log.config("root=" + root);
		treeModel = new AdempiereTreeModel(root, asksAllowsChildren=true); ====> AdempiereTreeModel extends DefaultTreeModel
 */
    	TreeTableModel treeTableModel = new MenuTreeTableModel(rootNode);
    	return treeTableModel;
    }

	private void initComponents() {
		tree = new JXTreeTable();
		tree.setName("componentTree");
//		tree.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
//		add(new JScrollPane(tree), BorderLayout.CENTER);
        tree.setName("componentTreeTable");
        add(new JScrollPane(tree));

		JComponent control = new JXPanel();
		refreshButton = new JXButton("Refresh");
		refreshButton.setName("refreshButton");

		expandButton = new JXButton("Expand All Nodes");
		expandButton.setName("expandButton");

		collapseButton = new JXButton("Collapse All Nodes");
		collapseButton.setName("collapseButton");

        control.add(refreshButton);
		control.add(expandButton);
		control.add(collapseButton);
		add(control, BorderLayout.SOUTH);
	}

    private void configureComponents() {
        // <snip> JXTree rendering
        // StringValue provides node text: concat several 
        StringValue sv = new StringValue() {
            
            @Override
            public String getString(Object value) {
                if(value instanceof MTreeNode) {
                	return StringValues.TO_STRING.getString(((MTreeNode) value).getName());
                } else {
                	LOG.config("value "+value+" is instance of "+(value==null ? "null" : value.getClass()));
                }
                return StringValues.TO_STRING.getString(value);
            }
        };
        // </snip>
        
        // StringValue for lazy icon loading
        StringValue keyValue = new StringValue() {
            
            @Override
            public String getString(Object value) {
                if (value == null) return "";
            	if(value instanceof MTreeNode) {
            		MTreeNode node = (MTreeNode)value;
//            		LOG.info(">>>>>>>>>ICON "+node.getImageIndicator() + node.getImageIndex());
            		return node.getImageIndicator(); //node.getImageIcon();
            	}
                String simpleClassName = value.getClass().getSimpleName();
                if (simpleClassName.length() == 0){
                    // anonymous class
                    simpleClassName = value.getClass().getSuperclass().getSimpleName();
                }
                return simpleClassName + ".png";
            }
        };
        // <snip> JXTree rendering
        // IconValue provides node icon 
        IconValue iv = new LazyLoadingIconValue(keyValue);
//        IconValue iv = new LazyLoadingIconValue(getClass(), keyValue, "fallback.png"); // LazyLoadingIconValue in TreeDemoIconValues versteckt!
/*
		IconValue iv = new LazyLoadingIconValue(XTreeDemo.class, keyValue, "fallback.png");


 */
        // create and set a tree renderer using the custom Icon-/StringValue
        // welche Renderer gibt es sonst noch? Wie kann ich meine icons als iv definieren?
        tree.setTreeCellRenderer(new DefaultTreeRenderer(iv, sv));
//        tree.setTreeCellRenderer(new DefaultTreeRenderer());
        // </snip>
//        tree.setRowHeight(-1);
        
        tree.setColumnControlVisible(true); // ColumnControl == der kleine Controler rechts bei den Tabellenüberschriften
        
        // <snip> JXTree rollover
        // enable and register a highlighter
        tree.setRolloverEnabled(true);
        tree.addHighlighter(createRolloverIconHighlighter(iv));
        // </snip>
        
        refreshButton.addActionListener(event -> {
        	tree.setTreeTableModel(createTreeModel());
        });

        expandButton.addActionListener(event -> {
        	tree.expandAll();
        });

        collapseButton.addActionListener(event -> {
        	tree.collapseAll();
        });
    }
    
    private void bind() {
//        tree.setModel(null); // remove - that is an outdated approach?
        
        // <snip>JXTreeTable column customization
        // configure and install a custom columnFactory, arguably data related ;-)
        ColumnFactory factory = new ColumnFactory() {
            String[] columnNameKeys = { "componentType", "componentName" //, "componentLocation", "componentSize" 
            		}; // wofür ist das?

            @Override
            public void configureTableColumn(TableModel model, TableColumnExt columnExt) {
                super.configureTableColumn(model, columnExt);
                if (columnExt.getModelIndex() < columnNameKeys.length) {
                    columnExt.setTitle(model.getColumnName(columnExt.getModelIndex()));
                }
            }
            
        };
        tree.setColumnFactory(factory);
        // </snip>
    }

    // <snip> JXTree rollover
    // custom implementation of Highlighter which highlights 
    // by changing the node icon on rollover
    private Highlighter createRolloverIconHighlighter(IconValue delegate) {
        // the icon look-up is left to an IconValue
        final IconValue iv = new FilteredIconValue(delegate); // FilteredIconValue versteckt in TreeDemoIconValues
        AbstractHighlighter hl = new AbstractHighlighter(HighlightPredicate.ROLLOVER_ROW) {

            /**
             * {@inheritDoc} <p>
             * 
             * Implemented to highlight by setting the node icon.
             */
            @Override
            protected Component doHighlight(Component component,
                    ComponentAdapter adapter) {
                Icon icon = iv.getIcon(adapter.getValue());
                if (icon != null) {
                    ((WrappingIconPanel) component).setIcon(icon);
                }
                return component;
            }
            // </snip>
            
            /**
             * {@inheritDoc} <p>
             * 
             * Implementated to return true if the component is a WrappingIconPanel,
             * a panel implemenation specialized for rendering tree nodes.
             * 
             */
            @Override
            protected boolean canHighlight(Component component,
                    ComponentAdapter adapter) {
                return component instanceof WrappingIconPanel;
            }
            
        };
        return hl;
    }

    public static class LazyLoadingIconValue implements IconValue {

		private static final long serialVersionUID = 8601036402183751110L;

		static final int SMALL_ICON_SIZE = 16;
		AbstractImageTranscoder AIT = AbstractImageTranscoder.getInstance();

        public LazyLoadingIconValue(StringValue sv) {
        	stringValue = sv;
        	iconCache = new HashMap<Object, Icon>(); 
         }

        private Map<Object, Icon> iconCache;
        private StringValue stringValue;
        
		@Override
		public Icon getIcon(Object value) {
			String imageIndicator = stringValue.getString(value);
			Icon icon = iconCache.get(imageIndicator);
            if(icon==null) {
            	LOG.config("loadIcon for "+imageIndicator+" value:"+value); // z.B value:53108/0 1002 - Human Resource & Payroll
                // loadIcon 
    			int imageIndex = MTreeNode.getImageIndex(imageIndicator);
                icon = MTreeNode.getImageIcon(AIT, imageIndex, SMALL_ICON_SIZE);
                iconCache.put(imageIndicator, icon);
            }
            return icon;
		}
    	
    }
}
