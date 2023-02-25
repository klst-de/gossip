package io.homebeaver.gossip;

import java.awt.Component;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import org.jdesktop.swingx.SwingXUtilities;
import org.jdesktop.swingx.icon.JXIcon;

import com.klst.model.MTreeNode;

import io.homebeaver.gossip.icon.IconFactory;

// copied from org.jdesktop.swingx.tree.DefaultXTreeCellRenderer
@SuppressWarnings("serial")
public class MenuTreeCellRenderer extends DefaultTreeCellRenderer {
//	javax.swing.tree.DefaultTreeCellRenderer extends JLabel implements TreeCellRenderer

    private static final Logger LOG = Logger.getLogger(MenuTreeCellRenderer.class.getName());

    /**
     * {@inheritDoc} <p>
     * 
     * Overridden to update icons and colors.
     */
    @Override // defined in javax.swing.tree.DefaultTreeCellRenderer
    public void updateUI() {
        super.updateUI();
//        LOG.info("------ do updateIcons() + updateColors()");
        updateIcons();
        updateColors();
    }

    /**
     * 
     */
    protected void updateColors() {
    	LOG.fine("Colors:"
    			+"\n Tree.background="+UIManager.getColor("Tree.background")
// Tree.background=DerivedColor(color=255,255,255 parent=nimbusLightBackground offsets=0.0,0.0,0.0,0 pColor=255,255,255
    			+"\n Tree.selectionForeground="+UIManager.getColor("Tree.selectionForeground")
    			+"\n Tree.textForeground="+UIManager.getColor("Tree.textForeground")
    			+"\n Tree.selectionBackground="+UIManager.getColor("Tree.selectionBackground")
// Tree.selectionBackground=DerivedColor(color=57,105,138 parent=nimbusSelectionBackground offsets=0.0,0.0,0.0,0 pColor=57,105,138
    			+"\n Tree.textBackground="+UIManager.getColor("Tree.textBackground")
    			+"\n Tree.selectionBorderColor="+UIManager.getColor("Tree.selectionBorderColor")
    			+"\n"
    			);
        if (SwingXUtilities.isUIInstallable(getTextSelectionColor())) {
            setTextSelectionColor(UIManager.getColor("Tree.selectionForeground"));
        }
        if (SwingXUtilities.isUIInstallable(getTextNonSelectionColor())) {
            setTextNonSelectionColor(UIManager.getColor("Tree.textForeground"));
        }
        if (SwingXUtilities.isUIInstallable(getBackgroundSelectionColor())) {
            setBackgroundSelectionColor(UIManager.getColor("Tree.selectionBackground"));
        }
        if (SwingXUtilities.isUIInstallable(getBackgroundNonSelectionColor())) {
            setBackgroundNonSelectionColor(UIManager.getColor("Tree.textBackground"));
        }
        if (SwingXUtilities.isUIInstallable(getBorderSelectionColor())) {
            setBorderSelectionColor(UIManager.getColor("Tree.selectionBorderColor"));
        }
//        Object value = UIManager.get("Tree.drawsFocusBorderAroundIcon");
//        drawsFocusBorderAroundIcon = (value != null && ((Boolean)value).
//                                      booleanValue());
//        value = UIManager.get("Tree.drawDashedFocusIndicator");
//        drawDashedFocusIndicator = (value != null && ((Boolean)value).
//                                    booleanValue());
    }

    /**
     * 
     */
    protected void updateIcons() {
        if (SwingXUtilities.isUIInstallable(getLeafIcon())) {
        	//  the given property "Tree.leafIcon" should be replacedby the UI's default value
        	LOG.fine("Tree.leafIcon should be replacedby the UI's default value !!!!!!");
            setLeafIcon(UIManager.getIcon("Tree.leafIcon"));
        }
        if (SwingXUtilities.isUIInstallable(getClosedIcon())) {
            setClosedIcon(UIManager.getIcon("Tree.closedIcon"));
        }
        if (SwingXUtilities.isUIInstallable(getOpenIcon())) {
            setOpenIcon(UIManager.getIcon("Tree.openIcon"));
        }

    }

//    public Icon getDefaultLeafIcon() {
//        return DefaultLookup.getIcon(this, ui, "Tree.leafIcon");
//    }

    /**
     * {@inheritDoc} <p>
     * 
     * Overridden to set name and icon.
     */
//    @Override // defined in javax.swing.tree.DefaultTreeCellRenderer
//    public Component getTreeCellRendererComponent(JTree tree, Object value,
//            boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
////    	LOG.config("-"+(leaf?"leaf":"----")+this.getIcon()+"->>"+value);
////    	LOG.config("-"+(leaf?"leaf":"----")+"->>"+this.getIcon());
//    	if(value instanceof MTreeNode mtn) {
//    		String ii = mtn.getImageIndicator();
//    		value = mtn.getName()+(ii==null?"":" / "+ii);
//        	LOG.fine("-"+(hasFocus?"hasFocus":"--------")+"->>"+value);
//    		if(ii!=null) {
//    			this.setLeafIcon(IconFactory.get(ii, JXIcon.SMALL_ICON));
//    		}
//    	}
//    	return super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
//    }

}
