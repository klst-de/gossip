package org.jdesktop.swingx.demos;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Window;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTree;
import org.jdesktop.swingx.decorator.AbstractHighlighter;
import org.jdesktop.swingx.decorator.ComponentAdapter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.demos.TreeDemoIconValues.FilteredIconValue;
import org.jdesktop.swingx.demos.TreeDemoIconValues.LazyLoadingIconValue;
import org.jdesktop.swingx.renderer.DefaultTreeRenderer;
import org.jdesktop.swingx.renderer.IconValue;
import org.jdesktop.swingx.renderer.StringValue;
import org.jdesktop.swingx.renderer.StringValues;
import org.jdesktop.swingx.renderer.WrappingIconPanel;
import org.jdesktop.swingx.treetable.TreeTableModel;

//import com.sun.swingset3.DemoProperties;

/**
 * JXTree Demo
 * 
 * PENDING JW: make editable to demonstrate terminate enhancement. 
 *
 *@author Jeanette Winzenburg, Berlin
 */

/* Beispiel aus doc
 JXTree tree = new JXTree(new FileSystemModel());
 // use system file icons and name to render
 tree.setCellRenderer(new DefaultTreeRenderer(IconValues.FILE_ICON, StringValues.FILE_NAME));
 // highlight condition: file modified after a date     
 HighlightPredicate predicate = new HighlightPredicate() {
    public boolean isHighlighted(Component renderer, ComponentAdapter adapter) {
       File file = getUserObject(adapter.getValue());
       return file != null ? lastWeek < file.lastModified : false;
    }
 };
 // highlight with foreground color 
 tree.addHighlighter(new ColorHighlighter(predicate, null, Color.RED);      
 */
public class XTreeDemo extends JXPanel { // original XTreeDemo extends JPanel
	// ... extended JPanel that provides additional features. 

	private static final long serialVersionUID = 1L;
	static Logger LOG = Logger.getLogger(XTreeDemo.class.getName());

	public XTreeDemo() {
        super(new BorderLayout());
        initComponents();
        configureComponents();
        bind();	
	}

    private void bind() {
        // example model is component hierarchy of demo application
        // bind in addNotify
        tree.setModel(null);
    }

    private JXTree tree;
    private JXButton refreshButton;
    private JXButton expandButton;
    private JXButton collapseButton;

	private void initComponents() {
		tree = new JXTree();
		tree.setName("componentTree");
		tree.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		add(new JScrollPane(tree), BorderLayout.CENTER);

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

    private static void createAndShowGUI2() {
        //Create and set up the window.
    	JXFrame frame = new JXFrame(XTreeDemo.class.getName()); // original: new JFrame(XTreeDemo.class.getAnnotation(DemoProperties.class).value());
    	// JXFrame enhanced JFrame with Additional Features:
    	// Root pane:
    	// Idle: 
    	// Wait (busy) glass pane:
    	
        frame.setDefaultCloseOperation(JXFrame.EXIT_ON_CLOSE);
        JXPanel panel = new XTreeDemo();
        frame.getContentPane().add(panel);
 
        //Display the window.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

//---------------- binding/configure
    
    private void configureComponents() {
        // <snip> JXTree rendering
        // StringValue provides node text: concat several 
        StringValue sv = new StringValue() {
            
            @Override
            public String getString(Object value) {
                if (value instanceof Component) {
                    Component component = (Component) value;
                    String simpleName = component.getClass().getSimpleName();
                    if (simpleName.length() == 0){
                        // anonymous class
                        simpleName = component.getClass().getSuperclass().getSimpleName();
                    }
                    return simpleName + "(" + component.getName() + ")";
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
                String simpleClassName = value.getClass().getSimpleName();
                if (simpleClassName.length() == 0){
                    // anonymous class
                    simpleClassName = value.getClass().getSuperclass().getSimpleName();
                }
                LOG.info("liefert "+simpleClassName +".png");
                return simpleClassName + ".png";
            }
        };
        // <snip> JXTree rendering
        // IconValue provides node icon 
        IconValue iv = new LazyLoadingIconValue(getClass(), keyValue, "fallback.png"); // LazyLoadingIconValue in TreeDemoIconValues versteckt!
        // create and set a tree renderer using the custom Icon-/StringValue
        tree.setCellRenderer(new DefaultTreeRenderer(iv, sv));
        // </snip>
        tree.setRowHeight(-1);
        
        // <snip> JXTree rollover
        // enable and register a highlighter
        tree.setRolloverEnabled(true);
        tree.addHighlighter(createRolloverIconHighlighter(iv));
        // </snip>
        
        refreshButton.addActionListener(event -> {
        	tree.setModel(createTreeModel());
        });

        expandButton.addActionListener(event -> {
        	tree.expandAll();
        });
        collapseButton.addActionListener(event -> {
        	tree.collapseAll();
        });
    }

    /**
     * Overridden to create and install the component tree model.
     */
    @Override // javax.swing.JComponent.addNotify
    public void addNotify() {
        super.addNotify();
        if (tree.getModel() == null) {
            tree.setModel(createTreeModel());
        }
    }

    private TreeTableModel createTreeModel() {
        Window window = SwingUtilities.getWindowAncestor(this);
        return ComponentModels.getTreeTableModel(window != null ? window : this);
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

	public static void main(String[] args) {
		LOG.info("start");

		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI2();
			}
		});
	}

}
