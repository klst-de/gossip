package io.homebeaver.gossip.swingx;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;

import org.jdesktop.swingx.JXTree;
import org.jdesktop.swingx.icon.JXIcon;
import org.jdesktop.swingx.renderer.DefaultTreeRenderer;
import org.jdesktop.swingx.renderer.IconValue;
import org.jdesktop.swingx.renderer.StringValue;
import org.jdesktop.swingx.renderer.StringValues;
import org.jdesktop.swingx.renderer.WrappingProvider;
import org.jdesktop.swingx.rollover.RolloverRenderer;
import org.jdesktop.swingx.tree.DefaultXTreeCellRenderer;

import com.klst.model.MTreeNode;

import io.homebeaver.gossip.icon.IconFactory;

public class CTree extends JXTree {

	private static final long serialVersionUID = -5844191040213531484L;
	private static final Logger LOG = Logger.getLogger(CTree.class.getName());

	public CTree(TreeModel model) {
		super(model);
	}
	
	@Override
	public TreeCellRenderer getCellRenderer() {
		StringValue sv = (Object value) -> {
			if(value==null) return StringValues.TO_STRING.getString(value);
			if(value instanceof MTreeNode mTreeNode) return StringValues.TO_STRING.getString(mTreeNode.getName());
			String simpleName = value.getClass().getSimpleName();
			return simpleName + "(" + value + ")";
		};
		StringValue kv = (Object value) -> {
			if(value==null) return StringValues.TO_STRING.getString(value);
			if(value instanceof MTreeNode mTreeNode) return StringValues.TO_STRING.getString(mTreeNode.getImageIndicator());
			String simpleName = value.getClass().getSimpleName();
			return simpleName + "(" + value + ")";
		};
		IconValue iv = new LazyLoadingIconValue(kv);
		return new TreeDelegatingRenderer(null, iv, sv);
	}

	class TreeDelegatingRenderer extends DefaultTreeRenderer implements TreeCellRenderer, RolloverRenderer {

		// implements TreeCellRenderer:
		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, 
			boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
			return super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
		}
		
		// implements RolloverRenderer:
		@Override
		public boolean isEnabled() {
			if(delegate instanceof RolloverRenderer rolloverRenderer) {
				return rolloverRenderer.isEnabled();
			}
			return false;
		}
		@Override
		public void doClick() {
			if(isEnabled()) {
				((RolloverRenderer) delegate).doClick();
			}
		}
		
		// ctor
		public TreeDelegatingRenderer(TreeCellRenderer delegate, IconValue iv, StringValue delegateStringValue) {
			super(new WrappingProvider(iv, delegateStringValue, false));
			if (delegate instanceof DefaultTreeCellRenderer dtcr) {
				initIcons(dtcr);
			} else {
				initIcons(new DefaultXTreeCellRenderer());
			}
		}

		private Icon closedIcon = null;
		private Icon openIcon = null;
		private Icon leafIcon = null;
		private TreeCellRenderer delegate = null;

		private void initIcons(DefaultTreeCellRenderer renderer) {
			closedIcon = renderer.getDefaultClosedIcon();
			openIcon = renderer.getDefaultOpenIcon();
			leafIcon = renderer.getDefaultLeafIcon();
		}

	}

    public class LazyLoadingIconValue implements IconValue {

		private static final long serialVersionUID = 8601036402183751110L;
		private static Map<Object, Icon> iconCache = new HashMap<Object, Icon>(); 

        public LazyLoadingIconValue(StringValue sv) {
        	stringValue = sv;
         }

        private StringValue stringValue;
        
		@Override // implements org.jdesktop.swingx.renderer.IconValue
		public Icon getIcon(Object value) {
			String imageIndicator = stringValue.getString(value);
			Icon icon = iconCache.get(imageIndicator);
            if(icon==null) {
            	LOG.config("loadIcon for \""+imageIndicator+"\" value:"+value); // z.B value:53108/0 1002 - Human Resource & Payroll
                // loadIcon 
//    			int imageIndex = MTreeNode.getImageIndex(imageIndicator);
//                icon = MTreeNode.getImageIcon(imageIndex, JXIcon.SMALL_ICON);
    			icon = IconFactory.get(imageIndicator, JXIcon.SMALL_ICON);
                iconCache.put(imageIndicator, icon);
            }
            return icon;
		}
    	
    }

}
