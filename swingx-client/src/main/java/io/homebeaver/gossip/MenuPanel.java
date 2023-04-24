package io.homebeaver.gossip;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import org.compiere.model.MMenu;
import org.compiere.model.MProcess;
import org.compiere.util.Env;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXList;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTree;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.AbstractHighlighter;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.icon.JXIcon;
import org.jdesktop.swingx.icon.PainterIcon;
import org.jdesktop.swingx.icon.RadianceIcon;
import org.jdesktop.swingx.painter.ImagePainter;
import org.jdesktop.swingx.renderer.DefaultListRenderer;
import org.jdesktop.swingx.renderer.IconValue;
import org.jdesktop.swingx.renderer.StringValue;
import org.jdesktop.swingx.renderer.StringValues;
import org.jdesktop.swingx.treetable.TreeTableModel;

import com.jhlabs.image.InvertFilter;
import com.klst.model.MTree;
import com.klst.model.MTreeNode;

import io.homebeaver.gossip.icon.IconFactory;
import io.homebeaver.gossip.swingx.CTree;

public class MenuPanel extends JXPanel implements ActionListener, TreeSelectionListener {

	private static final long serialVersionUID = 3820339775333768359L;
	static Logger LOG = Logger.getLogger(MenuPanel.class.getName());
	static final String COMPONENT_NAME = "menuTreeTable";

	@Override // implements ActionListener
	public void actionPerformed(ActionEvent e) {
		LOG.config("ActionCommand:"+e.getActionCommand() + " " + e.toString());	
	}

	@Override // implements TreeSelectionListener
	public void valueChanged(TreeSelectionEvent event) {
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
//				MProcess mp = MProcess.get(Env.getCtx(), node.getNode_ID());
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
	}


	public MenuPanel(RootFrame rootFrame) {
        super(new BorderLayout());
        this.rootFrame = rootFrame;
        // Versuch mit XList: 
        //add(createList(), BorderLayout.WEST);
        // -----------------
//        createModelImCtor(); // treeModel + treeTableModel
//        
//        // TODO buttons raus
//		JComponent control = new JXPanel();
//		refreshButton = new JXButton("Refresh");
//		refreshButton.setName("refreshButton");
//
//		expandButton = new JXButton("Expand All Nodes");
//		expandButton.setName("expandButton");
//
//		collapseButton = new JXButton("Collapse All Nodes");
//		collapseButton.setName("collapseButton");
//
//        control.add(refreshButton);
//		control.add(expandButton);
//		control.add(collapseButton);
//		add(control, BorderLayout.PAGE_END);
//        
        add(createTree(), BorderLayout.CENTER);
//        createTreeTable();
//        add(treeTable, BorderLayout.EAST);
//        
//        // nur Test:
//        ActionMap am = treeTable.getActionMap();
//        Object[] actionMapKeys = am.allKeys();
//        for(int i=0; i<actionMapKeys.length; i++) {
//        	LOG.config("key "+i + " : "+ actionMapKeys[i]);
//        }
//    	LOG.config("ctor fertig.\n");
	}
	
	RootFrame rootFrame;
	private List<MTreeNode> list;
	private TreeTableModel treeTableModel;
	private MTree vTree;
	private JXTree tree;
	private JXTreeTable treeTable;
	private JXButton refreshButton;
	private JXButton expandButton;
	private JXButton collapseButton;
    
//    private class JXTreeMenuTable extends JXTreeTable {
//
//	    @Override
//	    protected JTableHeader createDefaultTableHeader() {
//	        return new GenericTableHeader(columnModel);
//	    }
//
//	    @Override
//	    protected void processMouseEvent(MouseEvent e) {
//	    	super.processMouseEvent(e);
//	    }
//
//	    public void setTreeCellRenderer(TreeCellRenderer cellRenderer) {
//	    	super.setTreeCellRenderer(cellRenderer);
////	        if (renderer != null) {
////	            renderer.setCellRenderer(cellRenderer);
////	        }
//	    }
//
//    }

	private JComponent createList() {
		initMenuModels(); // init List and treeTableModel for Menu
		DefaultComboBoxModel<MTreeNode> model = new DefaultComboBoxModel<MTreeNode>();
		for (MTreeNode mtn : list) {
			model.addElement(mtn);
		}

		JXList<MTreeNode> xlist = new JXList<MTreeNode>();
		StringValue sv = (Object value) -> {
			if (value instanceof MTreeNode c) {
				return c.getName() + " " + c.getImageIndicator() + " (" + c.getNode_ID() + "/" + c.getParent_ID() + ")";
			}
			return StringValues.TO_STRING.getString(value);
		};
		IconValue iv = (Object value) -> {
			if (value instanceof MTreeNode c) {
				return c.getImageIcon();
			}
			return IconValue.NULL_ICON;
		};		
		xlist.setCellRenderer(new DefaultListRenderer<MTreeNode>(sv, iv));
		xlist.setVisibleRowCount(20);
//		xlist.setAutoCreateRowSorter(true);
		xlist.setModel(model);
		return new JScrollPane(xlist);
	}
	
	private JComponent createTree() {
    	tree = new CTree((TreeTableModel)initMenuModels());
    	
		Highlighter redText = new ColorHighlighter(HighlightPredicate.ROLLOVER_CELL, null, Color.RED);
		tree.addHighlighter(redText);

//        tree.setEditable(true);
		tree.addTreeSelectionListener(this);
        return new JScrollPane(tree);
    }

    // class JXTreeTable.TreeTableCellRenderer extends JXTree implements TableCellRenderer
    class MusicTreeTableCellRenderer extends JXTreeTable.TreeTableCellRenderer {
    	MusicTreeTableCellRenderer(TreeTableModel model) {
    		super(model);  		
    	}
    	public TreeCellRenderer getCellRenderer() { 
            StringValue nameValue = (Object value) -> {
            	//LOG.info("---"+value); // null ???????????????????????
                if(value instanceof MTreeNode c) {
                    return c.getName();
                }
                return StringValues.TO_STRING.getString(value);        	
            };
            return new JXTree.DelegatingRenderer(nameValue); 
    	}
	    @Override // to log
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
        	LOG.info("----- r/c:"+row+"/"+column +" value:"+value + " " + value.getClass());
        	return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
    class MusicTreeTable extends JXTreeTable {
		MusicTreeTable(JXTreeTable.TreeTableCellRenderer renderer) {
			super(renderer);
			assert ((JXTreeTable.TreeTableModelAdapter) getModel()).getTree() == renderer;
			  		
    		// UI-Dependent Striping 
    		Highlighter alternateStriping = HighlighterFactory.createAlternateStriping();
    		if(alternateStriping instanceof AbstractHighlighter ah) {
        		ah.setHighlightPredicate(HighlightPredicate.ALWAYS);
    		}
    		addHighlighter(alternateStriping);
    		
			Highlighter redText = new ColorHighlighter(HighlightPredicate.ROLLOVER_CELL, null, Color.RED);
			addHighlighter(redText);		}
    }
	private JComponent createTreeTable() {
    	JXTreeTable.TreeTableCellRenderer renderer = null; // = new MusicTreeTableCellRenderer(initMenuModels());
    	
//		/*
//		 * use small person icon for Composer (use And Predicate)
//		 */
//		Highlighter personIcon = new IconHighlighter(
//				new HighlightPredicate.AndHighlightPredicate(HighlightPredicate.IS_LEAF, new HighlightPredicate.DepthHighlightPredicate(2)),
//				FeatheRuser.of(SizingConstants.SMALL_ICON, SizingConstants.SMALL_ICON));
//		renderer.addHighlighter(personIcon);
//		
//		/*
//		 * use small disc icon for records/Albums
//		 */
//		Highlighter discIcon = new IconHighlighter(new HighlightPredicate.DepthHighlightPredicate(3), 
//				FeatheRdisc.of(SizingConstants.SMALL_ICON, SizingConstants.SMALL_ICON));
//		renderer.addHighlighter(discIcon);
//		
//		/*
//		 * use very small XS music icon instead the default for songs/compositions
//		 */
//		Highlighter musicIcon = new IconHighlighter(new HighlightPredicate.DepthHighlightPredicate(4),  
//				FeatheRmusic.of(SizingConstants.XS, SizingConstants.XS));
//		renderer.addHighlighter(musicIcon);
//		
//		renderer.addHighlighter(new RolloverIconHighlighter(HighlightPredicate.ROLLOVER_CELL, null));
		
    	treeTable = new MusicTreeTable(renderer);
    	treeTable.setColumnControlVisible(true);
        return new JScrollPane(treeTable);
	}
	
    /**
     * Overridden to create and install the component tree model.
     */
    @Override // overrides javax.swing.JComponent.addNotify
    public void addNotify() {
        super.addNotify();
//        TreeModel model = tree.getModel();
//        LOG.info("tree.Model.Root:"+(model==null?"null":model.getRoot()));
//        // der Vergleich mit null ist nicht sinnvoll, denn ein "leeres Modell" liefert nicht null, 
//        // sondern DefaultTreeModel mit JTree: colors, sports, food
//        if (model == null || model.getRoot() == null|| "JTree".equals(model.getRoot().toString())) {
//            tree.setModel(createModelImCtor());
////            treeTable.setTreeTableModel(treeTableModel);
//        }
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
    private static final int DEFAULT_TREE_ID = 10;
    private TreeTableModel initMenuModels() {
    	if(treeTableModel!=null) return treeTableModel;
    	int treeId = DEFAULT_TREE_ID;
    	boolean editable = false;
    	boolean allNodes = false;
    	String whereClause = null, trxName = null;
    	LOG.info("AD_Tree_ID = TODO per SQL: (default 10) ="+treeId); // TODO
    	
    	Properties ctx = Env.getCtx(); // props wg. https://github.com/klst-de/gossip/issues/2 :
    	LOG.config(
    			 "\n #AD_Client_ID = "+ctx.getProperty("#AD_Client_ID") + " ==> 11// GardenWorld"
    	    	+"\n #AD_Org_ID = "+ctx.getProperty("#AD_Org_ID") + " ==> 11// HQ"
    	    	+"\n #AD_User_ID = "+ctx.getProperty("#AD_User_ID") + " ==> 100// SuperUser"
    	    	+"\n #AD_Role_ID = "+ctx.getProperty("#AD_Role_ID") + " ==> 102// GardenWorld Admin"
    			);
		ctx.setProperty("#AD_Client_ID", "11");	// GardenWorld
		ctx.setProperty("#AD_Org_ID", "11");	// HQ
		ctx.setProperty("#AD_User_ID", "100");	// SuperUser
		ctx.setProperty("#AD_Role_ID", "102");	// GardenWorld Admin

    	vTree = new MTree(ctx, treeId, editable, allNodes, whereClause, trxName);
    	LOG.info("-------------------");
    	LOG.info(vTree.getName() + " isMenu="+vTree.isMenu()
    			+ " rootNode=" + vTree.getRootNode());
    	MTreeNode rootNode = vTree.getRootNode();
    	LOG.info("------------------->MenuTreeTableModel");
//    	DefaultMutableTreeNode rootDMTN = new DefaultMutableTreeNode(rootNode);
    	treeTableModel = new MenuTreeTableModel(rootNode);
    	Object root = treeTableModel.getRoot();
    	LOG.info("<-------------------MenuTreeTableModel getColumnCount="+treeTableModel.getColumnCount()
    		+ ",\n root:"+treeTableModel.getRoot()
    		+ ",\n root.ValueAt 0:"+treeTableModel.getValueAt(root, 0)
    		+ ",\n root.ValueAt 1:"+treeTableModel.getValueAt(root, 1)
    		+ ",\n root.ValueAt 2:"+treeTableModel.getValueAt(root, 2)
    		+ ",\n root.ValueAt 3:"+treeTableModel.getValueAt(root, 3)
    	);
    	list = new LinkedList<MTreeNode>();
    	list.add(rootNode);
    	for(int c=0; c<treeTableModel.getChildCount(root); c++) {
    		Object ch = treeTableModel.getChild(root, c);
    		if(ch instanceof MTreeNode mtn) {
    			LOG.info("child "+c+" is MTreeNode:"+mtn);
            	list.add(mtn);
            	if(mtn.getNode_ID()==153) { // Application Dictionary
            		for(int d=0; d<treeTableModel.getChildCount(mtn); d++) {
            			Object dch = treeTableModel.getChild(mtn, d);
            			list.add((MTreeNode)dch);
            		}
            	}
    		} else {
            	LOG.info("child "+c+"="+ch);
    		}
    	}
//    	return rootDMTN;
    	return treeTableModel;
    }
    
//    private void configureComponentsXXX() {
//        // <snip> JXTree rendering
//        // StringValue provides node text: concat several 
//        StringValue sv = new StringValue() {
//            
//            @Override
//            public String getString(Object value) {
//            	if(value instanceof MTreeNode mtn) {
//            		LOG.config("MTreeNode mtn:"+mtn);
//                	// dieser Name wird neben dem Icon angezeigt
//                	return StringValues.TO_STRING.getString(mtn.getName());
//                } else {
//                	LOG.config("value "+value+" is instance of "+(value==null ? "null" : value.getClass()));
//                }
//                return StringValues.TO_STRING.getString(value);
//            }
//        };
//        // </snip>
//        
//        // StringValue for lazy icon loading
//        StringValue keyValue = new StringValue() {
//            
//            @Override
//            public String getString(Object value) {
//                if (value == null) return "";
//            	if(value instanceof MTreeNode mtn) {
//            		String ii = mtn.getImageIndicator(); 
//            		LOG.config("MTreeNode mtn:"+mtn);
//            		return ii;
//            	}
//                String simpleClassName = value.getClass().getSimpleName();
//                if (simpleClassName.length() == 0){
//                    // anonymous class
//                    simpleClassName = value.getClass().getSuperclass().getSimpleName();
//                }
//                return simpleClassName + ".png";
//            }
//        };
//        // <snip> JXTree rendering
//        // IconValue provides node icon 
//        IconValue iv = new LazyLoadingIconValue(keyValue);
//        // create and set a tree renderer using the custom Icon-/StringValue
//        // welche Renderer gibt es sonst noch? Wie kann ich meine icons als iv definieren?
//        treeTable.setTreeCellRenderer(new DefaultTreeRenderer(iv, sv));
//        // </snip>
////        tree.setRowHeight(-1);
//        
//        // ColumnControl == der kleine Controler ColumnControlButton rechts bei den Tabellenüberschriften
//        // das Icon austauschen
////        tree.setColumnControl(new TableColumnControlButton(tree));
////        tree.setColumnControlVisible(true);
//        
//        //tree.setToolTipText("String ToolTipText text");
//        	
//        // <snip> JXTree rollover
//        // enable and register a highlighter
//        treeTable.setRolloverEnabled(true);
//        treeTable.addHighlighter(new RolloverIconHighlighter(HighlightPredicate.ROLLOVER_ROW, null));
////        treeTable.addHighlighter(createRolloverIconHighlighter(HighlightPredicate.ROLLOVER_CELL));
//        // </snip>
//        	
//        refreshButton.addActionListener(event -> {
//        	LOG.config("event "+event); // TODO initial size merken
//        	rootFrame.refresh();  // =======================> WindowFrame.refreshItem
//        });
//
////        expandButton.addActionListener(event -> {
////        	tree.expandAll();
////        });
////
////        collapseButton.addActionListener(event -> {
////        	tree.collapseAll();
////        });
//        
//        LOG.config("isRootVisible:"+treeTable.isRootVisible());
//        treeTable.setRootVisible(true); // default is false
////        tree.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN); tut nicht
//        treeTable.sizeColumnsToFit(0); // Breite der menu spalten anpassen
//        treeTable.sizeColumnsToFit(2); // tut nicht
//        
////      treeTable.addMouseListener(mouseListener);
//    }
//    
//    void setInitialTree() {
//    	treeTable.collapseAll();
//    	treeTable.expandRow(0);   	
//    }
    
    // --------------------
    public static class LazyLoadingIconValue implements IconValue {

		private static final long serialVersionUID = 8601036402183751110L;

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
//    			int imageIndex = MTreeNode.getImageIndex(imageIndicator);
//                icon = MTreeNode.getImageIcon(imageIndex, JXIcon.SMALL_ICON);
    			icon = IconFactory.get(imageIndicator, JXIcon.SMALL_ICON);
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
        private Icon manipulatedIcon(JXIcon icon) {
            PainterIcon painterIcon = new PainterIcon(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
            BufferedImage image = ((RadianceIcon)icon).toImage(1);
//            BufferedImage image = (BufferedImage) ((ImageIcon) icon).getImage();
            ImagePainter delegate = new ImagePainter(image);
            delegate.setFilters(new InvertFilter()); // com.jhlabs.image.InvertFilter.InvertFilter() 
            // ==> JH Labs is the alias of Jerry Huxtable .. image processing stuff. @see http://www.jhlabs.com/
            painterIcon.setPainter(delegate);
            return painterIcon;
        }

    }
	
	// to prevent expanding
//	class WillExpandListener implements TreeWillExpandListener {
//
//		@Override
//		public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
//			LOG.config("TreeExpansionEvent:" +event);
//			if (event.getSource() instanceof JXTreeMenuTable) {
//				JXTreeMenuTable tree = (JXTreeMenuTable)event.getSource();
//				if(tree.getName()==COMPONENT_NAME) {
//					TreePath treePath = event.getPath();
//					LOG.config("treePath:"+treePath 
//					+ "\n isExpanded="+tree.isExpanded(treePath) + " ExpandedDescendants:"+tree.getExpandedDescendants(treePath));
////					isExpanded=true
//				}
//			}
//		}
//
//		@Override
//		public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
//			// TODO Auto-generated method stub
//			
//		}
//		
//	}
//	class ExpansionListener implements TreeExpansionListener {
//
//		@Override
//		public void treeExpanded(TreeExpansionEvent event) {
//			LOG.config("TreeExpansionEvent:" +event);
//			if (event.getSource() instanceof JXTreeMenuTable) {
//				JXTreeMenuTable tree = (JXTreeMenuTable)event.getSource();
//				if(tree.getName()==COMPONENT_NAME) {
//					TreePath treePath = event.getPath();
//					LOG.config("treePath:"+treePath 
//					+ "\n isExpanded="+tree.isExpanded(treePath) + " ExpandedDescendants:"+tree.getExpandedDescendants(treePath));
////					isExpanded=true , dann kommt mousePressed MouseEvent:501 dran
//				}
//			}
//		}
//
//		@Override
//		public void treeCollapsed(TreeExpansionEvent event) {
//			LOG.config("treeCollapsed:" +event);
//			if (event.getSource() instanceof JXTreeMenuTable) {
//				JXTreeMenuTable tree = (JXTreeMenuTable)event.getSource();				
//				if(tree.getName()==COMPONENT_NAME) {
//					TreePath treePath = event.getPath();
//					LOG.config("treePath:"+treePath 
//					+ "\n isExpanded="+tree.isExpanded(treePath) + " ExpandedDescendants:"+tree.getExpandedDescendants(treePath));
////					isExpanded=false
//				}
//			}
//		}
//		
//	}
//	// alle Methoden in (awt) abstract class MouseAdapter sind leer vorimplementiet
///*
//aus MouseEvent:
//For example, if the first mouse button is pressed, events are sent in the following order: 
//    id              modifiers    button 
//    MOUSE_PRESSED:  BUTTON1_MASK BUTTON1
//    MOUSE_RELEASED: BUTTON1_MASK BUTTON1
//    MOUSE_CLICKED:  BUTTON1_MASK BUTTON1
//
// */
//	class MenuPanelMouseAdapter extends MouseAdapter { // java.awt.event.MouseAdapter implements MouseListener, MouseWheelListener, MouseMotionListener
//		
//	    /**
//	     * {@inheritDoc}
//	     */
//	    public void mouseClicked(MouseEvent e) {
//	    	if (e.getSource() instanceof JXTreeMenuTable) {
//	    		JXTreeMenuTable tree = (JXTreeMenuTable)e.getSource();
//	    		if(tree.getName()==COMPONENT_NAME && SwingUtilities.isLeftMouseButton(e)) {
//					LOG.config("MouseEvent:" +e.getID()
//					+ " Button:"+e.getButton() + " ModifiersEx:"+e.getModifiersEx()+ " " + e.getX()+","+e.getY() + " ClickCount:"+ e.getClickCount() + " "+e.toString());
////					adaptee.mouseClicked(e);	 
//					TreePath treePath = tree.getPathForLocation(e.getX(), e.getY());
//					int selRow = tree.getRowForPath(treePath);
//					LOG.config("457 es ist "+COMPONENT_NAME + " row:"+selRow + " treePath:"+treePath
//							+ "\n   0.PathComponent:"+treePath.getPathComponent(0) 
//							+ "\n LastPathComponent:"+treePath.getLastPathComponent() + " Path.size="+treePath.getPath().length);
//					
//					// -- wg. https://github.com/klst-de/gossip/issues/5 : der Fehler tritt nicht mehr auf, 
//					// das "Menu Expand" icon funktioniert 
//					// mit LIMA: MOUSE_PRESSED: aufklappen
//					//           MOUSE_RELEASED: wieder zuklappen 
//					// mit LIMA: MOUSE_PRESSED: aufklappen, Maus etws bewegen
//					//           MOUSE_RELEASED: klappt nicht zu
//					// mit REMA: gar nix 
//					LOG.config("tree.isExpanded(treePath)="+tree.isExpanded(treePath) + " ExpandsSelectedPaths:"+tree.getExpandsSelectedPaths()
//					+ " ExpandedDescendants:"+tree.getExpandedDescendants(treePath));
////					if(tree.getExpandedDescendants(treePath) != null && tree.isExpanded(treePath)) {
////						tree.expandPath(treePath);
////					} else {
////						tree.collapsePath(treePath);
////					}
//					if (tree.getExpandsSelectedPaths()) {
//						if (tree.isExpanded(treePath))
//							tree.collapsePath(treePath);
//						else {
//							tree.expandPath(treePath);
//						}
//					} 
////					else {
////						if (tree.getExpandedDescendants(treePath) != null && tree.isExpanded(treePath)) {
////							tree.expandPath(treePath);
////						} else {
////							tree.collapsePath(treePath);
////						}
////					}
//					// <--
//
//					MTreeNode node = (MTreeNode)treePath.getLastPathComponent();
//					if(node.isWindow()) {
//						//MTree_NodeMM mm = MTree_NodeMM.get(vTree, node.getNode_ID());
//						MMenu mm = new MMenu(Env.getCtx(), node.getNode_ID(), null);
////						JComponent jc = adaptee.getRootPane();
////						JRootPane rp = adaptee.getRootPane(); // JComponent
////						LOG.config("es ist "+COMPONENT_NAME + " node:"+node + " AD_Window_ID="+mm.getAD_Window_ID() + " RootPane/JComponent:"+rp.getContentPane()); // Bank 158
//						rootFrame.openNewFrame(mm.getAD_Window_ID());
//					}
//
//	    		}
//	    	}
//	    }
//
//	    /**
//	     * {@inheritDoc}
//	     */
//	    public void mousePressed(MouseEvent e) {
//	    	if (e.getSource() instanceof JXTreeMenuTable) {
//	    		JXTreeMenuTable tree = (JXTreeMenuTable)e.getSource();
//	    		if(tree.getName()==COMPONENT_NAME && SwingUtilities.isLeftMouseButton(e)) {
//					LOG.config("MouseEvent:" +e.getID()
//					+ " Button:"+e.getButton() + " ModifiersEx:"+e.getModifiersEx()+ " " + e.getX()+","+e.getY() + " ClickCount:"+ e.getClickCount() + " "+e.toString());
////					adaptee.mousePressed(e);
//					TreePath treePath = tree.getPathForLocation(e.getX(), e.getY());
//					LOG.config("tree.isExpanded(treePath)="+tree.isExpanded(treePath) + " ExpandedDescendants:"+tree.getExpandedDescendants(treePath));
////					tree.processMouseEvent(e);
//	    		}
//	    	}
//	    }
//
//	    /**
//	     * {@inheritDoc}
//	     */
//	    public void mouseReleased(MouseEvent e) {
//	    	if (e.getSource() instanceof JXTreeMenuTable) {
//	    		JXTreeMenuTable tree = (JXTreeMenuTable)e.getSource();
//	    		if(tree.getName()==COMPONENT_NAME && SwingUtilities.isLeftMouseButton(e)) {
//					LOG.config("MouseEvent:" +e.getID()
//					+ " Button:"+e.getButton() + " ModifiersEx:"+e.getModifiersEx()+ " " + e.getX()+","+e.getY() + " ClickCount:"+ e.getClickCount() + " "+e.toString());
////					adaptee.mouseReleased(e);
//					TreePath treePath = tree.getPathForLocation(e.getX(), e.getY());
//					LOG.config("tree.isExpanded(treePath)="+tree.isExpanded(treePath) + " ExpandedDescendants:"+tree.getExpandedDescendants(treePath));
////					tree.processMouseEvent(e);
////					e.consume();
//	    		}
//	    	}
//	    }
//
//	    /**
//	     * {@inheritDoc}
//	     */
//	    public void mouseEntered(MouseEvent e) {}
//
//	    /**
//	     * {@inheritDoc}
//	     */
//	    public void mouseExited(MouseEvent e) {}
//
//	    /**
//	     * {@inheritDoc}
//	     * @since 1.6
//	     */
//	    public void mouseWheelMoved(MouseWheelEvent e){}
//
//	    /**
//	     * {@inheritDoc}
//	     * @since 1.6
//	     */
//	    public void mouseDragged(MouseEvent e){}
//
//	    /**
//	     * {@inheritDoc}
//	     * @since 1.6
//	     */
//	    public void mouseMoved(MouseEvent e){}
//
//	}

}
