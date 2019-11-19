package com.klst.gossip;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import javax.swing.ActionMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.compiere.model.MMenu;
import org.compiere.util.Env;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.AbstractHighlighter;
import org.jdesktop.swingx.decorator.ComponentAdapter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.icon.PainterIcon;
import org.jdesktop.swingx.painter.ImagePainter;
import org.jdesktop.swingx.renderer.DefaultTreeRenderer;
import org.jdesktop.swingx.renderer.IconValue;
import org.jdesktop.swingx.renderer.StringValue;
import org.jdesktop.swingx.renderer.StringValues;
import org.jdesktop.swingx.renderer.WrappingIconPanel;
import org.jdesktop.swingx.treetable.TreeTableModel;

import com.jhlabs.image.InvertFilter;
import com.klst.icon.AbstractImageTranscoder;
import com.klst.icon.TableColumnControlButton;
import com.klst.model.MTree;
import com.klst.model.MTreeNode;

public class MenuPanel extends JXPanel implements ActionListener {

	private static final long serialVersionUID = 3820339775333768359L;
	static Logger LOG = Logger.getLogger(MenuPanel.class.getName());
	static final String COMPONENT_NAME = "componentTreeTable";

	public MenuPanel(RootFrame rootFrame) {
        super(new BorderLayout());
        this.rootFrame = rootFrame;
        createModelImCtor(); // treeModel + treeTableModel
        initComponents();
        configureComponents();
        
        // nur Test:
        ActionMap am = tree.getActionMap();
        Object[] actionMapKeys = am.allKeys();
        for(int i=0; i<actionMapKeys.length; i++) {
        	LOG.config("key "+i + " : "+ actionMapKeys[i]);
        }
    	LOG.config("ctor fertig.\n");
	}
	
	RootFrame rootFrame;
    private TreeTableModel treeTableModel;
    private MTree vTree;
    private JXTreeTable tree;
    private JXButton refreshButton;
    private JXButton expandButton;
    private JXButton collapseButton;
    
    private MouseListener mouseListener = new MenuPanelMouseAdapter(this);

    private void initComponents() {  	
//		tree = new JXTreeTable(treeTableModel); // gleichwertig zu
		tree = new JXTreeTable();
		tree.setTreeTableModel(treeTableModel);
		
		LOG.config("tree SelectionMode="+tree.getSelectionMode()); //  javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION : 2
		tree.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION); // : 1
		
		//tree.setOverwriteRendererIcons(true);
		
        tree.setName(COMPONENT_NAME);
        add(new JScrollPane(tree), BorderLayout.CENTER);

        // TODO buttons raus
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
		add(control, BorderLayout.PAGE_END);
	}

    /**
     * Overridden to create and install the component tree model.
     */
    // ist für das TreeTableDemo notwendig
//    @Override // javax.swing.JComponent.addNotify
//    public void addNotify() {
//        super.addNotify();
//        if (tree.getModel() == null) {
//            tree.setTreeTableModel(createTreeModel());
//        }
//    }

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
    private static final int DEFAULT_TREE_ID = 10;
    private void createModelImCtor() {
    	int treeId = DEFAULT_TREE_ID;
    	boolean editable = false;
    	boolean allNodes = false;
    	String whereClause = null, trxName = null;
    	LOG.info("AD_Tree_ID = TODO per SQL: (default 10) ="+treeId); // TODO
    	
    	Properties ctx = Env.getCtx(); // props wg. https://github.com/klst-de/gossip/issues/2 :
		ctx.setProperty("#AD_Client_ID", "11");
		ctx.setProperty("#AD_Org_ID", "11");
		ctx.setProperty("#AD_User_ID", "100");
		ctx.setProperty("#AD_Role_ID", "102");

    	vTree = new MTree(ctx, treeId, editable, allNodes, whereClause, trxName);
    	LOG.info(vTree.getName() + " isMenu="+vTree.isMenu()
    			+ " rootNode=" + vTree.getRootNode());
    	MTreeNode rootNode = vTree.getRootNode();
    	treeTableModel = new MenuTreeTableModel(rootNode);
    }
    
    private void configureComponents() {

        // <snip> JXTree rendering
        // StringValue provides node text: concat several 
        StringValue sv = new StringValue() {
            
            @Override
            public String getString(Object value) {
                if(value instanceof MTreeNode) {
                	MTreeNode node = (MTreeNode)value;
//                	node.getName();
//                	node.toString();
                	// dieser Name wird neben dem Icon angezeigt
                	return StringValues.TO_STRING.getString(node.getName());
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
        // create and set a tree renderer using the custom Icon-/StringValue
        // welche Renderer gibt es sonst noch? Wie kann ich meine icons als iv definieren?
        tree.setTreeCellRenderer(new DefaultTreeRenderer(iv, sv));
//        tree.setCellRenderer(new DefaultTreeRenderer(iv, sv));
        // </snip>
//        tree.setRowHeight(-1);
        
        // ColumnControl == der kleine Controler ColumnControlButton rechts bei den Tabellenüberschriften
        // das Icon austauschen
        tree.setColumnControl(new TableColumnControlButton(tree));
        tree.setColumnControlVisible(true);
        
        //tree.setToolTipText("String ToolTipText text");
        	
        // <snip> JXTree rollover
        // enable and register a highlighter
        tree.setRolloverEnabled(true);
        tree.addHighlighter(createRolloverIconHighlighter(iv));
        // </snip>
        	
        refreshButton.addActionListener(event -> {
        	LOG.config("event "+event); // TODO initial size merken
        	tree.collapseAll();
        	tree.expandRow(0);
        });

        expandButton.addActionListener(event -> {
        	tree.expandAll();
        });

        collapseButton.addActionListener(event -> {
        	tree.collapseAll();
        });
        
        LOG.config("isRootVisible:"+tree.isRootVisible());
        tree.setRootVisible(true); // default is false
//        tree.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN); tut nicht
        tree.sizeColumnsToFit(0); // Breite der menu spalten anpassen
        tree.sizeColumnsToFit(2); // tut nicht
        
//        tree.addTreeSelectionListener(this); // swingx-unused
        // treePanel.addPropertyChangeListener(VTreePanel.NODE_SELECTION, this);
//        tree.addPropertyChangeListener(listener);
        tree.addMouseListener(mouseListener);
    }
    
    // <snip> JXTree rollover
    // custom implementation of Highlighter which highlights 
    // by changing the node icon on rollover
    private Highlighter createRolloverIconHighlighter(IconValue delegate) {
        // the icon look-up is left to an IconValue
        final IconValue iv = new FilteredIconValue(delegate); // FilteredIconValue versteckt in TreeDemoIconValues
        AbstractHighlighter hl = new AbstractHighlighter(HighlightPredicate.ROLLOVER_CELL) {
        	// geht weder mit ROLLOVER_ROW noch mit ROLLOVER_CELL
            /**
             * {@inheritDoc} <p>
             * 
             * Implemented to highlight by setting the node icon.
             */
            @Override // muss implementiert werden
            // JXTree tree : funktioniert es
            // JXTreetable tree : nicht
            protected Component doHighlight(Component component, ComponentAdapter adapter) {
            	LOG.config("component:"+component + " ComponentAdapter:"+adapter);
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
            protected boolean canHighlight(Component component, ComponentAdapter adapter) {
                return component instanceof WrappingIconPanel;
            }
            
        };
        return hl;
    }

    // --------------------
	static final int SMALL_ICON_SIZE = 16;
    
    public static class LazyLoadingIconValue implements IconValue {

		private static final long serialVersionUID = 8601036402183751110L;

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
    
    public static class FilteredIconValue implements IconValue {

		AbstractImageTranscoder AIT = AbstractImageTranscoder.getInstance();
    	private Icon icon = manipulatedIcon(MTreeNode.getImageIcon(AIT, 5, SMALL_ICON_SIZE));
		
    	public FilteredIconValue(IconValue delegate) {
    		this.delegate = delegate;
    	}
    	
    	private IconValue delegate;
//        private Map<Object, Icon> iconCache; // zu jedem Icon(object) gibt es ein (const)manipulated version.
    	
		@Override
		public Icon getIcon(Object value) {
			// immer konstant:
			return icon;
		}

        // wraps the given icon into an ImagePainter with a filter effect
        private Icon manipulatedIcon(Icon icon) {
            PainterIcon painterIcon = new PainterIcon(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
            BufferedImage image = (BufferedImage) ((ImageIcon) icon).getImage();
            ImagePainter delegate = new ImagePainter(image);
            delegate.setFilters(new InvertFilter()); // com.jhlabs.image.InvertFilter.InvertFilter() 
            // ==> JH Labs is the alias of Jerry Huxtable .. image processing stuff. @see http://www.jhlabs.com/
            painterIcon.setPainter(delegate);
            return painterIcon;
        }

    }

	@Override
	public void actionPerformed(ActionEvent e) {
		LOG.config("ActionCommand:"+e.getActionCommand() + " " + e.toString());	
	}
	
	// call back
	void mouseClicked(MouseEvent e) {
		LOG.finest("MouseEvent:" //+e.getSource()
		+ " Button:"+e.getButton() + " " + e.getX()+","+e.getY() + " ClickCount:"+ e.getClickCount() + " "+e.toString());
		// 1 java.awt.event.MouseEvent[MOUSE_CLICKED,(116,25),absolute(877,397),button=1,modifiers=Button1,clickCount=1] on componentTreeTable [16]
		//MouseEvent.BUTTON1 == LIMA
		//MouseEvent.BUTTON3 == REMA
		if (e.getSource() instanceof JXTreeTable) {
			if(((JXTreeTable)e.getSource()).getName()==COMPONENT_NAME) {
				LOG.finest("es ist "+COMPONENT_NAME); // tree
				if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount()>0) {
					TreePath treePath = tree.getPathForLocation(e.getX(), e.getY());
					int selRow = tree.getRowForPath(treePath);
					LOG.finest("es ist "+COMPONENT_NAME + " row:"+selRow + " "+treePath.getLastPathComponent());
					MTreeNode node = (MTreeNode)treePath.getLastPathComponent();
					if(node.isWindow()) {
						//MTree_NodeMM mm = MTree_NodeMM.get(vTree, node.getNode_ID());
						MMenu mm = new MMenu(Env.getCtx(), node.getNode_ID(), null);
						JComponent jc = this.getRootPane();
						JRootPane rp = this.getRootPane(); // JComponent
						LOG.config("es ist "+COMPONENT_NAME + " node:"+node + " AD_Window_ID="+mm.getAD_Window_ID() + " RootPane/JComponent:"+rp.getContentPane()); // Bank 158
						rootFrame.openNewFrame(mm.getAD_Window_ID());
					}
//					firePropertyChange(NODE_SELECTION, null, node);
				}
			};
		}
	}
	
	class MenuPanelMouseAdapter extends MouseAdapter { // java.awt.event.MouseAdapter implements MouseListener, MouseWheelListener, MouseMotionListener
		
		MenuPanel adaptee;

		MenuPanelMouseAdapter(MenuPanel adaptee) {
			this.adaptee = adaptee;
		}

		public void mouseClicked(MouseEvent e) {
			adaptee.mouseClicked(e);
		}

	    public void mouseReleased(MouseEvent e) {
	    	
	    }
	    
	    public void mouseEntered(MouseEvent e) {
	    	
	    }

	}

}
