package io.homebeaver.gossip.icon;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.Painter;

import org.jdesktop.swingx.decorator.ComponentAdapter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.jdesktop.swingx.decorator.IconHighlighter;
import org.jdesktop.swingx.icon.PainterIcon;
import org.jdesktop.swingx.icon.RadianceIcon;
import org.jdesktop.swingx.painter.AbstractAreaPainter;
import org.jdesktop.swingx.painter.ImagePainter;
import org.jdesktop.swingx.plaf.UIDependent;
import org.jdesktop.swingx.renderer.WrappingIconPanel;

import com.jhlabs.image.BumpFilter;

public class RolloverIconHighlighter extends IconHighlighter implements UIDependent {

	private static final Logger LOG = Logger.getLogger(RolloverIconHighlighter.class.getName());

	private JComponent comp;
//	
//    public RolloverIconHighlighter(JComponent comp) {
//        this((HighlightPredicate) null, comp);
//    }
//    public RolloverIconHighlighter(HighlightPredicate predicate, JComponent comp) {
//        this(predicate, null, comp);
//    }
//    public RolloverIconHighlighter(Icon icon, JComponent comp) {
//        this(null, icon, comp);
//    }
//    public RolloverIconHighlighter(HighlightPredicate predicate, Icon icon, JComponent comp) {
//        super(predicate);
//        setIcon(icon);
//	this.comp = comp;
//    }

    public RolloverIconHighlighter(JComponent comp) {
        this((HighlightPredicate) null, comp);
    }
    public RolloverIconHighlighter(HighlightPredicate predicate, JComponent comp) {
        this(predicate, null, comp);
    }
    public RolloverIconHighlighter(Icon icon, JComponent comp) {
        this(null, icon, comp);
    }
    public RolloverIconHighlighter(HighlightPredicate predicate, Icon icon, JComponent comp) {
        super(predicate);
        setIcon(icon);
    }

    @Override
    protected boolean canHighlight(Component component, ComponentAdapter adapter) {
    	if(component instanceof WrappingIconPanel wip) {
        	comp = wip;
        	return true;
    	}
        return super.canHighlight(component, adapter);
    }

    @Override
    protected Component doHighlight(Component component, ComponentAdapter adapter) {
    	if(component instanceof WrappingIconPanel wip) {
    		Icon icon = wip.getIcon();
    		PainterIcon painterIcon = new PainterIcon(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        	BufferedImage image = null;
        	if(icon instanceof RadianceIcon ri) {
        		image = ri.toImage(1);
        	} else if(icon instanceof ImageIcon ii) {
        		image = (BufferedImage)ii.getImage();
        	} else {
        		LOG.warning("no highlighting for "+icon);
        	}
        	AbstractAreaPainter<Component> delegate = new ImagePainter(image);
        	delegate.setFilters(new BumpFilter());
        	painterIcon.setPainter((Painter<? extends Component>)delegate);
        	wip.setIcon(painterIcon);
//        	wip.updateUI();
        	comp = wip;
        	return wip;
    	}
        return super.doHighlight(component, adapter);
//    	in super:
//        if (getIcon() != null) {
//            if (component instanceof IconAware) {
//                ((IconAware) component).setIcon(getIcon());
//            } else if (component instanceof JLabel) {
//                ((JLabel) component).setIcon(getIcon());
//            }
//        }
//        return component;
    }
    
	@Override
	public void updateUI() {
		if(comp==null) return;
		comp.updateUI();
	}

}
