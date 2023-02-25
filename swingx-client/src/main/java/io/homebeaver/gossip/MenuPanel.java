package io.homebeaver.gossip;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
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
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.Painter;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.compiere.model.MMenu;
import org.compiere.model.MProcess;
import org.compiere.util.Env;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTree;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.AbstractHighlighter;
import org.jdesktop.swingx.decorator.ComponentAdapter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.icon.JXIcon;
import org.jdesktop.swingx.icon.PainterIcon;
import org.jdesktop.swingx.icon.RadianceIcon;
import org.jdesktop.swingx.painter.AbstractAreaPainter;
import org.jdesktop.swingx.painter.ImagePainter;
import org.jdesktop.swingx.renderer.DefaultTreeRenderer;
import org.jdesktop.swingx.renderer.IconValue;
import org.jdesktop.swingx.renderer.StringValue;
import org.jdesktop.swingx.renderer.StringValues;
import org.jdesktop.swingx.renderer.WrappingIconPanel;
import org.jdesktop.swingx.rollover.RolloverProducer;
import org.jdesktop.swingx.treetable.TreeTableModel;

import com.jhlabs.image.BumpFilter;
import com.jhlabs.image.InvertFilter;
import com.klst.gossip.GenericTableHeader;
import com.klst.model.MTree;
import com.klst.model.MTreeNode;

import io.homebeaver.gossip.icon.IconFactory;
import io.homebeaver.gossip.icon.RolloverIconHighlighter;

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
        createModelImCtor(); // treeModel + treeTableModel
        
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
        
        add(createTree(), BorderLayout.WEST);
        add(createTreeTable(), BorderLayout.CENTER);
        
        // nur Test:
        ActionMap am = treeTable.getActionMap();
        Object[] actionMapKeys = am.allKeys();
        for(int i=0; i<actionMapKeys.length; i++) {
        	LOG.config("key "+i + " : "+ actionMapKeys[i]);
        }
    	LOG.config("ctor fertig.\n");
	}
	
	RootFrame rootFrame;
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

	private JComponent createTree() {
        tree = new JXTree(treeTableModel==null? createModelImCtor() : treeTableModel) {
        	
            @Override // defined in javax.swing.JComponent
            public Insets getInsets() {
                return new Insets(5,5,5,5);
            }

            /**
             * {@inheritDoc} <p>
             * 
             * Overridden to set name and icon.
             */
            @Override // defined in javax.swing.JTree
			public String convertValueToText(Object value, boolean selected, 
					boolean expanded, boolean leaf, int row, boolean hasFocus) {
		    	if(value instanceof MTreeNode mtn) {
		    		String ii = mtn.getImageIndicator();
		    		String text = mtn.getName()+(ii==null?"":" / "+ii);
//		        	LOG.info("-"+(hasFocus?"hasFocus":"--------")+"->>"+text);
		    		if(ii!=null) {
		    			this.setLeafIcon(IconFactory.get(ii, JXIcon.SMALL_ICON));
		    		}
		    		return text;
		    	}
		    	// in super:
//				if (value != null) {
//					String sValue = value.toString();
//					if (sValue != null) {
//						return sValue;
//					}
//				}
//				return "";
		    	return super.convertValueToText(value, selected, expanded, leaf, row, hasFocus);
			}

            protected void updateHighlighterUI() {
            	if(compoundHighlighter == null) {
                	LOG.config("add RolloverIconHighlighter ...");
            		setRolloverEnabled(true);
            		addHighlighter(new RolloverIconHighlighter(HighlightPredicate.ROLLOVER_ROW, null));
            	} else {
                	LOG.info("!!!!!!!!!!!!!!! compoundHighlighter:"+compoundHighlighter);
            	}
            	super.updateHighlighterUI();
                if (compoundHighlighter == null) return;
                compoundHighlighter.updateUI();
            }

        };         
        // rollover support: enabled to show album cover, a "live" rollover behaviour: TODO
        tree.setRolloverEnabled(true);
//        tree.addPropertyChangeListener(RolloverProducer.ROLLOVER_KEY, propertyChangeEvent -> {
//        	JXTree source = (JXTree)propertyChangeEvent.getSource();
//        	source.setToolTipText(null);
//			Point newPoint = (Point)propertyChangeEvent.getNewValue();
//			if(newPoint!=null && newPoint.y>-1) {
//				TreePath treePath = source.getPathForRow(newPoint.y);
//				if(treePath.getPathCount()==4) { // Album / Record / Style 
//					Object o = treePath.getLastPathComponent();
////					LOG.info("PathFor newPoint.y: "+source.getPathForRow(newPoint.y) + " PropertyChangeEvent:"+propertyChangeEvent);
//					// show https://en.wikipedia.org/wiki/File:My_Name_Is_Albert_Ayler.jpg
//					DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode)o;	
//					Album album = (Album)dmtn.getUserObject();
//					source.setToolTipText(album.getHtmlSrc());
//				}
//			}
//        });
        tree.addTreeSelectionListener(this);
        
        tree.setOpaque(true);
        
        // <snip> JXTree rollover
        // enable and register a highlighter
//        tree.setRolloverEnabled(true);
    	tree.addHighlighter(new RolloverIconHighlighter(HighlightPredicate.ROLLOVER_ROW, null)); //TODO funktioniert nicht - warum?
        // auch das tut nicht ????:
//        tree.addPropertyChangeListener(RolloverProducer.ROLLOVER_KEY, propertyChangeEvent -> {
//        	JXTree tree = (JXTree)propertyChangeEvent.getSource();
//        	Point newPoint = (Point)propertyChangeEvent.getNewValue();
//        	if(newPoint!=null && newPoint.y>-1) {
//        		TreePath treePath = tree.getPathForRow(newPoint.y);
//        		LOG.info("TreePath treePath.LastPathComponent() ="+treePath.getLastPathComponent());
//        		if(treePath.getLastPathComponent() instanceof MTreeNode mtn) {
//        			String ii = mtn.getImageIndicator();
//                	TreeCellRenderer tcr = tree.getCellRenderer();
//    ???            	tcr.getTreeCellRendererComponent(tree, mtn, false, false, leaf, row, false);
//        		}
//        	}
//        	// ...
//        });
        // </snip>
        
//        LOG.info("Tree.CellRenderer for menu tree:"+tree.getCellRenderer());
//        DefaultTreeCellRenderer renderer = new MenuTreeCellRenderer();
//        tree.setCellRenderer(renderer);
        
//        tree.setCellRenderer(new DefaultTreeRenderer((IconValue)null, new StringValue() {
//			private static final long serialVersionUID = 1L;
//			public String getString(Object value) {
//				if(value instanceof MTreeNode mtn) {
//		    		String ii = mtn.getImageIndicator();
//		    		value = mtn.getName()+(ii==null?"":" / "+ii);
////		        	LOG.fine("-"+(hasFocus?"hasFocus":"--------")+"->>"+value);
////		    		if(ii!=null) {
////		    			this.ic.setsetLeafIcon(IconFactory.get(ii, JXIcon.SMALL_ICON));
////		    		}
//				} else {
//                	LOG.config("value "+value+" is instance of "+(value==null ? "null" : value.getClass()));
//				}
//                return StringValues.TO_STRING.getString(value);
//            }
//        }, true));
        tree.getSelectionModel().getSelectionMode();
		LOG.config("tree SelectionMode="+tree.getSelectionModel().getSelectionMode());
		// is == DISCONTIGUOUS_TREE_SELECTION = 4
		// set SINGLE_TREE_SELECTION = 1 , TreeSelectionModel is interface!
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

//        tree.setEditable(true);
        return new JScrollPane(tree);
    }

	private JComponent createTreeTable() {
    	treeTable = new JXTreeTable() {
    	    @Override
    	    protected JTableHeader createDefaultTableHeader() {
    	        return new GenericTableHeader(columnModel);
    	    }
    
    	    @Override
    	    protected void processMouseEvent(MouseEvent e) {
    	    	super.processMouseEvent(e);
    	    }
    	    
    	    @Override
    	    protected Component applyRenderer(Component component, ComponentAdapter adapter) {
    	        if (component == null) {
    	            throw new IllegalArgumentException("null component");
    	        }
    	        if (adapter == null) {
    	            throw new IllegalArgumentException("null component data adapter");
    	        }

				if (component instanceof JXTree tree) {
//					LOG.info("------------>\n component:"+component);
//					tree.setRolloverEnabled(true);
//					tree.addHighlighter(new RolloverIconHighlighter(HighlightPredicate.ROLLOVER_ROW, null));
					// TODO funktioniert nicht - warum?
/*
habe die Bemerkung gefunden:

    static class TreeTableCellRenderer extends JXTree implements TableCellRenderer
        // need to implement RolloverRenderer
        // PENDING JW: method name clash rolloverRenderer.isEnabled and
        // component.isEnabled .. don't extend, use? And change
        // the method name in rolloverRenderer? 
        // commented - so doesn't show the rollover cursor.
        // 
//      ,  RolloverRenderer 

 */
				}

    	        if (isHierarchical(adapter.column)) {
    	            // After all decorators have been applied, make sure that relevant
    	            // attributes of the table cell renderer are applied to the
    	            // tree cell renderer before the hierarchical column is rendered!
    	            TreeCellRenderer tcr = super.getTreeCellRenderer();
//    	            LOG.info("------------>\n component:"+component
//    	            		+"\n adapter.row/column="+adapter.row+"/"+adapter.column+" adapter:"+adapter
//    	            		+"\n TreeCellRenderer tcr:"+tcr
//    	            		+"\n ColumnName(2):"+adapter.getColumnName(2) 
//    	            		+ " ColumnIdentifierAt:"+adapter.getColumnIdentifierAt(2) 
//    	            		+ " ValueAt:"+adapter.getValueAt(adapter.row, 2) + " hasFocus="+adapter.hasFocus()
//    	            		);
    	            if (tcr instanceof JXTree.DelegatingRenderer) {
    	                tcr = ((JXTree.DelegatingRenderer) tcr).getDelegateRenderer();
    	            }
    	            if (tcr instanceof DefaultTreeCellRenderer dtcr) {
    	                Object ii = adapter.getValueAt(adapter.row, 2);
    	                if(ii instanceof String imageIndicator) {
    	                	Icon icon = MTreeNode.getImageIcon(MTreeNode.getImageIndex(imageIndicator), JXIcon.SMALL_ICON);
//    	                	LOG.info("Icon:"+icon + (adapter.hasFocus() ? " hasFocus" : ""));
    	                	dtcr.setLeafIcon(icon);
    	                }   	                
    	                // this effectively overwrites the dtcr settings
    	                if (adapter.isSelected()) {
    	                    dtcr.setTextSelectionColor(component.getForeground());
    	                    dtcr.setBackgroundSelectionColor(component.getBackground());
    	                } else {
    	                    dtcr.setTextNonSelectionColor(component.getForeground());
    	                    dtcr.setBackgroundNonSelectionColor(component.getBackground());
    	                }
//    	                if(adapter.hasFocus() && dtcr.getIcon()!=null) {
//    	            		Icon icon = dtcr.getIcon();
//    	                	LOG.info(">>>>>>>>>Icon:"+icon);
//    	            		PainterIcon painterIcon = new PainterIcon(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
//    	                	BufferedImage image = null;
//    	                	if(icon instanceof RadianceIcon ri) {
//    	                		image = ri.toImage(1);
//    	                	} else if(icon instanceof ImageIcon imageIcon) {
//    	                		image = (BufferedImage)imageIcon.getImage();
//    	                	} else {
//    	                		LOG.warning("no highlighting for "+icon);
//    	                	}
//    	                	AbstractAreaPainter<Component> delegate = new ImagePainter(image);
//    	                	delegate.setFilters(new BumpFilter());
//    	                	painterIcon.setPainter((Painter<? extends Component>)delegate);
//    	                	dtcr.setIcon(painterIcon);
//    	                }
    	            }
    	        }
    	        return component;
    	    }
//    	    public Component prepareRenderer(TableCellRenderer renderer, int row,
//    	        int column) {
//    	        Component component = super.prepareRenderer(renderer, row, column);
//    	        return applyRenderer(component, getComponentAdapter(row, column)); 
//    	    }

    	};
		treeTable.setTreeTableModel(treeTableModel);
        treeTable.setName(COMPONENT_NAME);
        treeTable.setColumnControlVisible(true);      
        treeTable.addTreeSelectionListener(this);
//        treeTable.addHighlighter(new RolloverIconHighlighter(HighlightPredicate.ROLLOVER_ROW, null));
        
        // made columns as wide as it needs:
        treeTable.packColumn(treeTable.getHierarchicalColumn(), -1);
        
		LOG.config("treeTable SelectionMode="+treeTable.getSelectionMode()); //  javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION : 2
//		treeTable.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION); // : 1
		
        // <snip> JXTree rendering
        // StringValue provides node text: concat several 
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
        // </snip>
//		DefaultTreeRenderer renderer = new DefaultTreeRenderer(sv);
//		treeTable.setTreeCellRenderer(renderer);
		
        return new JScrollPane(treeTable);
	}
	
    /**
     * Overridden to create and install the component tree model.
     */
    @Override // overrides javax.swing.JComponent.addNotify
    public void addNotify() {
        super.addNotify();
        TreeModel model = tree.getModel();
        LOG.info("tree.Model.Root:"+(model==null?"null":model.getRoot()));
        // der Vergleich mit null ist nicht sinnvoll, denn ein "leeres Modell" liefert nicht null, 
        // sondern DefaultTreeModel mit JTree: colors, sports, food
        if (model == null || model.getRoot() == null|| "JTree".equals(model.getRoot().toString())) {
            tree.setModel(createModelImCtor());
            treeTable.setTreeTableModel(treeTableModel);
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
    private static final int DEFAULT_TREE_ID = 10;
    private TreeModel createModelImCtor() {
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
    	treeTableModel = new MenuTreeTableModel(rootNode);
    	LOG.info("<-------------------MenuTreeTableModel");
    	return treeTableModel;
    }
    
    private void configureComponentsXXX() {
        // <snip> JXTree rendering
        // StringValue provides node text: concat several 
        StringValue sv = new StringValue() {
            
            @Override
            public String getString(Object value) {
            	if(value instanceof MTreeNode mtn) {
            		LOG.config("MTreeNode mtn:"+mtn);
                	// dieser Name wird neben dem Icon angezeigt
                	return StringValues.TO_STRING.getString(mtn.getName());
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
            	if(value instanceof MTreeNode mtn) {
            		String ii = mtn.getImageIndicator(); 
            		LOG.config("MTreeNode mtn:"+mtn);
            		return ii;
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
        treeTable.setTreeCellRenderer(new DefaultTreeRenderer(iv, sv));
        // </snip>
//        tree.setRowHeight(-1);
        
        // ColumnControl == der kleine Controler ColumnControlButton rechts bei den Tabellenüberschriften
        // das Icon austauschen
//        tree.setColumnControl(new TableColumnControlButton(tree));
//        tree.setColumnControlVisible(true);
        
        //tree.setToolTipText("String ToolTipText text");
        	
        // <snip> JXTree rollover
        // enable and register a highlighter
        treeTable.setRolloverEnabled(true);
        treeTable.addHighlighter(new RolloverIconHighlighter(HighlightPredicate.ROLLOVER_ROW, null));
//        treeTable.addHighlighter(createRolloverIconHighlighter(HighlightPredicate.ROLLOVER_CELL));
        // </snip>
        	
        refreshButton.addActionListener(event -> {
        	LOG.config("event "+event); // TODO initial size merken
        	rootFrame.refresh();  // =======================> WindowFrame.refreshItem
        });

//        expandButton.addActionListener(event -> {
//        	tree.expandAll();
//        });
//
//        collapseButton.addActionListener(event -> {
//        	tree.collapseAll();
//        });
        
        LOG.config("isRootVisible:"+treeTable.isRootVisible());
        treeTable.setRootVisible(true); // default is false
//        tree.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN); tut nicht
        treeTable.sizeColumnsToFit(0); // Breite der menu spalten anpassen
        treeTable.sizeColumnsToFit(2); // tut nicht
        
//      treeTable.addMouseListener(mouseListener);
    }
    
    void setInitialTree() {
    	treeTable.collapseAll();
    	treeTable.expandRow(0);   	
    }
    
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
