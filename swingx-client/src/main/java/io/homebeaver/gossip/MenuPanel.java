package io.homebeaver.gossip;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import javax.swing.ActionMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.table.JTableHeader;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.compiere.model.MMenu;
import org.compiere.model.MProcess;
import org.compiere.util.Env;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTree;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.AbstractHighlighter;
import org.jdesktop.swingx.decorator.ComponentAdapter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.icon.JXIcon;
import org.jdesktop.swingx.icon.PainterIcon;
import org.jdesktop.swingx.painter.ImagePainter;
import org.jdesktop.swingx.renderer.DefaultTreeRenderer;
import org.jdesktop.swingx.renderer.IconValue;
import org.jdesktop.swingx.renderer.StringValue;
import org.jdesktop.swingx.renderer.StringValues;
import org.jdesktop.swingx.renderer.WrappingIconPanel;
import org.jdesktop.swingx.treetable.TreeTableModel;

import com.jhlabs.image.InvertFilter;
import com.klst.gossip.GenericTableHeader;
import com.klst.gossip.MenuTreeTableModel;
//import com.klst.icon.AbstractImageTranscoder;
//import com.klst.icon.TableColumnControlButton;
import com.klst.model.MTree;
import com.klst.model.MTreeNode;

import io.homebeaver.gossip.icon.IconFactory;

public class MenuPanel extends JXPanel implements ActionListener {

	private static final long serialVersionUID = 3820339775333768359L;
	static Logger LOG = Logger.getLogger(MenuPanel.class.getName());
	static final String COMPONENT_NAME = "menuTreeTable";

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
    private JXTreeMenuTable tree;
//    private JXButton refreshButton;
//    private JXButton expandButton;
//    private JXButton collapseButton;
    
    private class JXTreeMenuTable extends JXTreeTable {

	    @Override
	    protected JTableHeader createDefaultTableHeader() {
	        return new GenericTableHeader(columnModel);
	    }

	    @Override
	    protected void processMouseEvent(MouseEvent e) {
	    	super.processMouseEvent(e);
	    }
    }
    private void initComponents() {  	
//		tree = new JXTreeTable(treeTableModel); // gleichwertig zu: new JXTreeTable(); tree.setTreeTableModel(treeTableModel);
//		tree = new JXTreeTable() {
//		    @Override
//		    protected JTableHeader createDefaultTableHeader() {
//		        return new GenericTableHeader(columnModel);
//		    }
//		};
    	tree = new JXTreeMenuTable();
		tree.setTreeTableModel(treeTableModel);
		
		LOG.config("tree SelectionMode="+tree.getSelectionMode()); //  javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION : 2
		tree.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION); // : 1
		
		//tree.setOverwriteRendererIcons(true);
		
        tree.setName(COMPONENT_NAME);
        add(new JScrollPane(tree), BorderLayout.CENTER);

        // TODO buttons raus
//		JComponent control = new JXPanel();
//		refreshButton = new JXButton("Refresh");
//		refreshButton.setName("refreshButton");
//
//		expandButton = new JXButton("Expand All Nodes");
//		expandButton.setName("expandButton");
//
//		collapseButton = new JXButton("Collapse All Nodes");
//		collapseButton.setName("collapseButton");

//        control.add(refreshButton);
//		control.add(expandButton);
//		control.add(collapseButton);
//		add(control, BorderLayout.PAGE_END);
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
		ctx.setProperty("#AD_Client_ID", "11");	// GardenWorld
		ctx.setProperty("#AD_Org_ID", "11");	// HQ
		ctx.setProperty("#AD_User_ID", "100");	// SuperUser
		ctx.setProperty("#AD_Role_ID", "102");	// GardenWorld Admin

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
//        tree.setColumnControl(new TableColumnControlButton(tree));
//        tree.setColumnControlVisible(true);
        
        //tree.setToolTipText("String ToolTipText text");
        	
        // <snip> JXTree rollover
        // enable and register a highlighter
        tree.setRolloverEnabled(true);
        tree.addHighlighter(createRolloverIconHighlighter(iv));
        // </snip>
        	
//        refreshButton.addActionListener(event -> {
//        	LOG.config("event "+event); // TODO initial size merken
//        	rootFrame.refresh();  // =======================> WindowFrame.refreshItem
//        });
//
//        expandButton.addActionListener(event -> {
//        	tree.expandAll();
//        });
//
//        collapseButton.addActionListener(event -> {
//        	tree.collapseAll();
//        });
        
        LOG.config("isRootVisible:"+tree.isRootVisible());
        tree.setRootVisible(true); // default is false
//        tree.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN); tut nicht
        tree.sizeColumnsToFit(0); // Breite der menu spalten anpassen
        tree.sizeColumnsToFit(2); // tut nicht
        
//        tree.addMouseListener(mouseListener);
        tree.addTreeSelectionListener(event -> { // implements TreeSelectionListener
			if(event.getSource() instanceof JXTree) {
				JXTree tree = (JXTree)event.getSource();
				TreePath treePath = event.getPath(); // first path element
				MTreeNode node = (MTreeNode)treePath.getLastPathComponent();
				
				if(node.isWindow()) {
					MMenu mMenu = MMenu.getFromId(Env.getCtx(), node.getNode_ID());
					rootFrame.openNewFrame(mMenu.getAD_Window_ID());
				} else if(node.isProcess()) {
					MMenu mm1 = new MMenu(Env.getCtx(), node.getNode_ID(), null); // gleichwertig zu MMenu.getFromId(Env.getCtx(), node.getNode_ID());
					MMenu mm2 = MMenu.getFromId(Env.getCtx(), node.getNode_ID());
					// mm.getAD_Window_ID()==0
//					MProcess mp = MProcess.get(Env.getCtx(), node.getNode_ID());
					LOG.config("TODO Process Node_ID="+node.getNode_ID() + " MMenu ID="+mm1.get_ID() + "/"+mm2.get_ID() + " OptionId="+mm1.getOptionId());// TODO Process, siehe AMenuStartItem.startProcess
					MProcess mp = MProcess.get(Env.getCtx(), mm1.getAD_Process_ID());
					WindowFrame pd = rootFrame.openNewFrame(mp);
					LOG.config("WindowFrame pd/aka ProcessDialog:"+pd);
					pd.getContentPane().invalidate();
					pd.getContentPane().validate();
					pd.pack();
					pd.setLocationRelativeTo(null); // oben links würde es sonst angezeigt
					pd.setVisible(true);
/* in (client) org.compiere.apps.AMenuStartItem extends Thread
		private void startProcess (int AD_Process_ID, boolean isSOTrx) {
			SwingUtilities.invokeLater(updateProgressBar);			//	1
			timer.stop();
			ProcessDialog pd = new ProcessDialog (menu.getGraphicsConfiguration(), AD_Process_ID, isSOTrx);
			    // ProcessDialog extends (base)CFrame extends JFrame implements IProcessDialog, ASyncProcess
			if (!pd.init())
				return;
			timer.start();
			menu.getWindowManager().add(pd);

			SwingUtilities.invokeLater(updateProgressBar);			//	2
			pd.getContentPane().invalidate();
			pd.getContentPane().validate();
			pd.pack();
			//	Center the window
			SwingUtilities.invokeLater(updateProgressBar);			//	3
			AEnv.showCenterScreen(pd);
		}
 */
				} else {
					if(tree.getExpandsSelectedPaths()) {
						if(!tree.isExpanded(treePath)) {
							tree.expandPath(treePath);
						} else {
							tree.collapsePath(treePath);
						}
					}
				}
			}
        });
//        tree.addTreeExpansionListener(treeExpansionListener);
//        tree.addTreeWillExpandListener(treeWillExpandListener);
    }
    
    void setInitialTree() {
    	tree.collapseAll();
    	tree.expandRow(0);   	
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

//		AbstractImageTranscoder AIT = AbstractImageTranscoder.getInstance();

        public LazyLoadingIconValue(StringValue sv) {
        	stringValue = sv;
        	iconCache = new HashMap<Object, Icon>(); 
         }

        private Map<Object, Icon> iconCache;
        private StringValue stringValue;
        
		@Override // implements org.jdesktop.swingx.renderer.IconValue
		public Icon getIcon(Object value) {
			String imageIndicator = stringValue.getString(value);
			Icon icon = iconCache.get(imageIndicator);
            if(icon==null) {
            	LOG.config("loadIcon for "+imageIndicator+" value:"+value); // z.B value:53108/0 1002 - Human Resource & Payroll
                // loadIcon 
    			int imageIndex = MTreeNode.getImageIndex(imageIndicator);
                icon = MTreeNode.getImageIcon(imageIndex, JXIcon.SMALL_ICON);
//    			icon = IconFactory.get(imageIndicator, JXIcon.SMALL_ICON);
                iconCache.put(imageIndicator, icon);
            }
            return icon;
		}
    	
    }
    
    public static class FilteredIconValue implements IconValue {

//		AbstractImageTranscoder AIT = AbstractImageTranscoder.getInstance();
    	private Icon icon = manipulatedIcon(MTreeNode.getImageIcon(5, JXIcon.SMALL_ICON));
		
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
	
	// to prevent expanding
	class WillExpandListener implements TreeWillExpandListener {

		@Override
		public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
			LOG.config("TreeExpansionEvent:" +event);
			if (event.getSource() instanceof JXTreeMenuTable) {
				JXTreeMenuTable tree = (JXTreeMenuTable)event.getSource();
				if(tree.getName()==COMPONENT_NAME) {
					TreePath treePath = event.getPath();
					LOG.config("treePath:"+treePath 
					+ "\n isExpanded="+tree.isExpanded(treePath) + " ExpandedDescendants:"+tree.getExpandedDescendants(treePath));
//					isExpanded=true
				}
			}
		}

		@Override
		public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
			// TODO Auto-generated method stub
			
		}
		
	}
	class ExpansionListener implements TreeExpansionListener {

		@Override
		public void treeExpanded(TreeExpansionEvent event) {
			LOG.config("TreeExpansionEvent:" +event);
			if (event.getSource() instanceof JXTreeMenuTable) {
				JXTreeMenuTable tree = (JXTreeMenuTable)event.getSource();
				if(tree.getName()==COMPONENT_NAME) {
					TreePath treePath = event.getPath();
					LOG.config("treePath:"+treePath 
					+ "\n isExpanded="+tree.isExpanded(treePath) + " ExpandedDescendants:"+tree.getExpandedDescendants(treePath));
//					isExpanded=true , dann kommt mousePressed MouseEvent:501 dran
				}
			}
		}

		@Override
		public void treeCollapsed(TreeExpansionEvent event) {
			LOG.config("treeCollapsed:" +event);
			if (event.getSource() instanceof JXTreeMenuTable) {
				JXTreeMenuTable tree = (JXTreeMenuTable)event.getSource();				
				if(tree.getName()==COMPONENT_NAME) {
					TreePath treePath = event.getPath();
					LOG.config("treePath:"+treePath 
					+ "\n isExpanded="+tree.isExpanded(treePath) + " ExpandedDescendants:"+tree.getExpandedDescendants(treePath));
//					isExpanded=false
				}
			}
		}
		
	}
	// alle Methoden in (awt) abstract class MouseAdapter sind leer vorimplementiet
/*
aus MouseEvent:
For example, if the first mouse button is pressed, events are sent in the following order: 
    id              modifiers    button 
    MOUSE_PRESSED:  BUTTON1_MASK BUTTON1
    MOUSE_RELEASED: BUTTON1_MASK BUTTON1
    MOUSE_CLICKED:  BUTTON1_MASK BUTTON1

 */
	class MenuPanelMouseAdapter extends MouseAdapter { // java.awt.event.MouseAdapter implements MouseListener, MouseWheelListener, MouseMotionListener
		
	    /**
	     * {@inheritDoc}
	     */
	    public void mouseClicked(MouseEvent e) {
	    	if (e.getSource() instanceof JXTreeMenuTable) {
	    		JXTreeMenuTable tree = (JXTreeMenuTable)e.getSource();
	    		if(tree.getName()==COMPONENT_NAME && SwingUtilities.isLeftMouseButton(e)) {
					LOG.config("MouseEvent:" +e.getID()
					+ " Button:"+e.getButton() + " ModifiersEx:"+e.getModifiersEx()+ " " + e.getX()+","+e.getY() + " ClickCount:"+ e.getClickCount() + " "+e.toString());
//					adaptee.mouseClicked(e);	 
					TreePath treePath = tree.getPathForLocation(e.getX(), e.getY());
					int selRow = tree.getRowForPath(treePath);
					LOG.config("457 es ist "+COMPONENT_NAME + " row:"+selRow + " treePath:"+treePath
							+ "\n   0.PathComponent:"+treePath.getPathComponent(0) 
							+ "\n LastPathComponent:"+treePath.getLastPathComponent() + " Path.size="+treePath.getPath().length);
					
					// -- wg. https://github.com/klst-de/gossip/issues/5 : der Fehler tritt nicht mehr auf, 
					// das "Menu Expand" icon funktioniert 
					// mit LIMA: MOUSE_PRESSED: aufklappen
					//           MOUSE_RELEASED: wieder zuklappen 
					// mit LIMA: MOUSE_PRESSED: aufklappen, Maus etws bewegen
					//           MOUSE_RELEASED: klappt nicht zu
					// mit REMA: gar nix 
					LOG.config("tree.isExpanded(treePath)="+tree.isExpanded(treePath) + " ExpandsSelectedPaths:"+tree.getExpandsSelectedPaths()
					+ " ExpandedDescendants:"+tree.getExpandedDescendants(treePath));
//					if(tree.getExpandedDescendants(treePath) != null && tree.isExpanded(treePath)) {
//						tree.expandPath(treePath);
//					} else {
//						tree.collapsePath(treePath);
//					}
					if (tree.getExpandsSelectedPaths()) {
						if (tree.isExpanded(treePath))
							tree.collapsePath(treePath);
						else {
							tree.expandPath(treePath);
						}
					} 
//					else {
//						if (tree.getExpandedDescendants(treePath) != null && tree.isExpanded(treePath)) {
//							tree.expandPath(treePath);
//						} else {
//							tree.collapsePath(treePath);
//						}
//					}
					// <--

					MTreeNode node = (MTreeNode)treePath.getLastPathComponent();
					if(node.isWindow()) {
						//MTree_NodeMM mm = MTree_NodeMM.get(vTree, node.getNode_ID());
						MMenu mm = new MMenu(Env.getCtx(), node.getNode_ID(), null);
//						JComponent jc = adaptee.getRootPane();
//						JRootPane rp = adaptee.getRootPane(); // JComponent
//						LOG.config("es ist "+COMPONENT_NAME + " node:"+node + " AD_Window_ID="+mm.getAD_Window_ID() + " RootPane/JComponent:"+rp.getContentPane()); // Bank 158
						rootFrame.openNewFrame(mm.getAD_Window_ID());
					}

	    		}
	    	}
	    }

	    /**
	     * {@inheritDoc}
	     */
	    public void mousePressed(MouseEvent e) {
	    	if (e.getSource() instanceof JXTreeMenuTable) {
	    		JXTreeMenuTable tree = (JXTreeMenuTable)e.getSource();
	    		if(tree.getName()==COMPONENT_NAME && SwingUtilities.isLeftMouseButton(e)) {
					LOG.config("MouseEvent:" +e.getID()
					+ " Button:"+e.getButton() + " ModifiersEx:"+e.getModifiersEx()+ " " + e.getX()+","+e.getY() + " ClickCount:"+ e.getClickCount() + " "+e.toString());
//					adaptee.mousePressed(e);
					TreePath treePath = tree.getPathForLocation(e.getX(), e.getY());
					LOG.config("tree.isExpanded(treePath)="+tree.isExpanded(treePath) + " ExpandedDescendants:"+tree.getExpandedDescendants(treePath));
//					tree.processMouseEvent(e);
	    		}
	    	}
	    }

	    /**
	     * {@inheritDoc}
	     */
	    public void mouseReleased(MouseEvent e) {
	    	if (e.getSource() instanceof JXTreeMenuTable) {
	    		JXTreeMenuTable tree = (JXTreeMenuTable)e.getSource();
	    		if(tree.getName()==COMPONENT_NAME && SwingUtilities.isLeftMouseButton(e)) {
					LOG.config("MouseEvent:" +e.getID()
					+ " Button:"+e.getButton() + " ModifiersEx:"+e.getModifiersEx()+ " " + e.getX()+","+e.getY() + " ClickCount:"+ e.getClickCount() + " "+e.toString());
//					adaptee.mouseReleased(e);
					TreePath treePath = tree.getPathForLocation(e.getX(), e.getY());
					LOG.config("tree.isExpanded(treePath)="+tree.isExpanded(treePath) + " ExpandedDescendants:"+tree.getExpandedDescendants(treePath));
//					tree.processMouseEvent(e);
//					e.consume();
	    		}
	    	}
	    }

	    /**
	     * {@inheritDoc}
	     */
	    public void mouseEntered(MouseEvent e) {}

	    /**
	     * {@inheritDoc}
	     */
	    public void mouseExited(MouseEvent e) {}

	    /**
	     * {@inheritDoc}
	     * @since 1.6
	     */
	    public void mouseWheelMoved(MouseWheelEvent e){}

	    /**
	     * {@inheritDoc}
	     * @since 1.6
	     */
	    public void mouseDragged(MouseEvent e){}

	    /**
	     * {@inheritDoc}
	     * @since 1.6
	     */
	    public void mouseMoved(MouseEvent e){}

	}

}
