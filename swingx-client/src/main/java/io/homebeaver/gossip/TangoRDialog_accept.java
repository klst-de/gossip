package io.homebeaver.gossip;

import java.awt.*;
import java.awt.geom.*;
import java.util.Stack;
import javax.swing.plaf.UIResource;

import org.jdesktop.swingx.icon.RadianceIcon;
import org.jdesktop.swingx.icon.RadianceIconUIResource;

/**
 * This class has been automatically generated using 
 * <a href="https://jdesktop.wordpress.com/2022/09/25/svg-icons/">Radiance SVG converter</a>.
 */
public class TangoRDialog_accept implements RadianceIcon {
    private Shape shape = null;
    private GeneralPath generalPath = null;
    private Paint paint = null;
    private Stroke stroke = null;
    private RadianceIcon.ColorFilter colorFilter = null;
    private Stack<AffineTransform> transformsStack = new Stack<>();

	// EUG https://github.com/homebeaver (rotation + point/axis reflection)
    private int rsfx = 1, rsfy = 1;
    public void setReflection(boolean horizontal, boolean vertical) {
    	this.rsfx = vertical ? -1 : 1;
    	this.rsfy = horizontal ? -1 : 1;
    }    
    public boolean isReflection() {
		return rsfx==-1 || rsfy==-1;
	}
	
    private double theta = 0;
    public void setRotation(double theta) {
    	this.theta = theta;
    }    
    public double getRotation() {
		return theta;
	}
	// EUG -- END

    

	private void _paint0(Graphics2D g,float origAlpha) {
transformsStack.push(g.getTransform());
// 
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0
g.setComposite(AlphaComposite.getInstance(3, 0.6f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0706000328063965f, 0.0f, 0.0f, 0.5249999761581421f, -0.8927599787712097f, 22.5f));
// _0_0_0
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(41.0f, 40.0f);
generalPath.curveTo(41.027473f, 43.0714f, 37.766056f, 45.915382f, 32.450706f, 47.45506f);
generalPath.curveTo(27.13536f, 48.994736f, 20.57864f, 48.994736f, 15.263292f, 47.45506f);
generalPath.curveTo(9.947945f, 45.915382f, 6.6865287f, 43.0714f, 6.7140007f, 40.0f);
generalPath.curveTo(6.6865287f, 36.9286f, 9.947945f, 34.084618f, 15.263292f, 32.54494f);
generalPath.curveTo(20.57864f, 31.005262f, 27.13536f, 31.005262f, 32.450706f, 32.54494f);
generalPath.curveTo(37.766056f, 34.084618f, 41.027473f, 36.9286f, 41.0f, 40.0f);
generalPath.closePath();
shape = generalPath;
paint = new RadialGradientPaint(new Point2D.Double(23.85700035095215, 40.0), 17.143f, new Point2D.Double(23.85700035095215, 40.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255)),((colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 0)) : new Color(0, 0, 0, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 0.5f, 0.0f, 20.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_0
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(0.920490026473999f, 0.0f, 0.0f, 0.920490026473999f, 2.368499994277954f, 0.9740800261497498f));
// _0_1_0_0
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(46.857f, 23.929f);
generalPath.curveTo(46.857f, 36.829002f, 36.399998f, 47.286003f, 23.499998f, 47.286003f);
generalPath.curveTo(10.599998f, 47.286003f, 0.14299774f, 36.829002f, 0.14299774f, 23.929003f);
generalPath.curveTo(0.14299774f, 11.029003f, 10.5999975f, 0.5720024f, 23.499998f, 0.5720024f);
generalPath.curveTo(36.399998f, 0.5720024f, 46.857f, 11.029002f, 46.857f, 23.929003f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(115, 210, 22, 255)) : new Color(115, 210, 22, 255);
g.setPaint(paint);
g.fill(shape);
paint = (colorFilter != null) ? colorFilter.filter(new Color(78, 154, 6, 255)) : new Color(78, 154, 6, 255);
stroke = new BasicStroke(1.0864f,0,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(46.857f, 23.929f);
generalPath.curveTo(46.857f, 36.829002f, 36.399998f, 47.286003f, 23.499998f, 47.286003f);
generalPath.curveTo(10.599998f, 47.286003f, 0.14299774f, 36.829002f, 0.14299774f, 23.929003f);
generalPath.curveTo(0.14299774f, 11.029003f, 10.5999975f, 0.5720024f, 23.499998f, 0.5720024f);
generalPath.curveTo(36.399998f, 0.5720024f, 46.857f, 11.029002f, 46.857f, 23.929003f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 0.34659f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(0.8560900092124939f, 0.0f, 0.0f, 0.8560900092124939f, 1.8183000087738037f, 0.19776999950408936f));
// _0_1_0_1
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(49.902f, 26.635f);
generalPath.curveTo(49.902f, 39.885002f, 39.161f, 50.626f, 25.911001f, 50.626f);
generalPath.curveTo(12.661003f, 50.626f, 1.920002f, 39.885f, 1.920002f, 26.635f);
generalPath.curveTo(1.920002f, 13.385002f, 12.661002f, 2.644001f, 25.911001f, 2.644001f);
generalPath.curveTo(39.161f, 2.644001f, 49.902f, 13.385001f, 49.902f, 26.635f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 0)) : new Color(0, 0, 0, 0);
g.setPaint(paint);
g.fill(shape);
paint = new LinearGradientPaint(new Point2D.Double(43.93600082397461, 53.83599853515625), new Point2D.Double(20.065000534057617, -8.562700271606445), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(255, 230, 155, 255)) : new Color(255, 230, 155, 255)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 255)) : new Color(255, 255, 255, 255))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(1.1681f,0,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(49.902f, 26.635f);
generalPath.curveTo(49.902f, 39.885002f, 39.161f, 50.626f, 25.911001f, 50.626f);
generalPath.curveTo(12.661003f, 50.626f, 1.920002f, 39.885f, 1.920002f, 26.635f);
generalPath.curveTo(1.920002f, 13.385002f, 12.661002f, 2.644001f, 25.911001f, 2.644001f);
generalPath.curveTo(39.161f, 2.644001f, 49.902f, 13.385001f, 49.902f, 26.635f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setTransform(transformsStack.pop());
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_2
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_2_0
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_2_0_0
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(14.707f, 25.178f);
generalPath.curveTo(15.33621f, 25.178015f, 15.8122f, 25.6943f, 16.1348f, 26.7269f);
generalPath.curveTo(16.78014f, 28.663f, 17.24f, 29.631f, 17.5142f, 29.631f);
generalPath.curveTo(17.72393f, 29.631012f, 17.94173f, 29.469671f, 18.16762f, 29.14698f);
generalPath.curveTo(22.70122f, 21.88678f, 26.896019f, 16.01398f, 30.75162f, 11.52898f);
generalPath.curveTo(31.751919f, 10.36738f, 33.341118f, 9.78658f, 35.51922f, 9.78648f);
generalPath.curveTo(36.03547f, 9.786511f, 36.382347f, 9.834913f, 36.55982f, 9.93168f);
generalPath.curveTo(36.73726f, 10.028514f, 36.825996f, 10.14952f, 36.826027f, 10.29469f);
generalPath.curveTo(36.825993f, 10.52059f, 36.559788f, 10.96428f, 36.027397f, 11.62569f);
generalPath.curveTo(29.799698f, 19.11179f, 24.023397f, 27.01769f, 18.699398f, 35.34269f);
generalPath.curveTo(18.328308f, 35.923508f, 17.569998f, 36.21393f, 16.424498f, 36.21392f);
generalPath.curveTo(15.262797f, 36.213924f, 14.577198f, 36.16552f, 14.367397f, 36.06872f);
generalPath.curveTo(13.818837f, 35.82672f, 13.173497f, 34.59252f, 12.431297f, 32.36602f);
generalPath.curveTo(11.592327f, 29.89752f, 11.172797f, 28.34872f, 11.172897f, 27.719421f);
generalPath.curveTo(11.17289f, 27.041811f, 11.737577f, 26.388422f, 12.866998f, 25.75912f);
generalPath.curveTo(13.560748f, 25.371922f, 14.173798f, 25.178322f, 14.706298f, 25.1783f);
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(238, 238, 236, 255)) : new Color(238, 238, 236, 255);
g.setPaint(paint);
g.fill(shape);
paint = (colorFilter != null) ? colorFilter.filter(new Color(238, 238, 236, 255)) : new Color(238, 238, 236, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(14.707f, 25.178f);
generalPath.curveTo(15.33621f, 25.178015f, 15.8122f, 25.6943f, 16.1348f, 26.7269f);
generalPath.curveTo(16.78014f, 28.663f, 17.24f, 29.631f, 17.5142f, 29.631f);
generalPath.curveTo(17.72393f, 29.631012f, 17.94173f, 29.469671f, 18.16762f, 29.14698f);
generalPath.curveTo(22.70122f, 21.88678f, 26.896019f, 16.01398f, 30.75162f, 11.52898f);
generalPath.curveTo(31.751919f, 10.36738f, 33.341118f, 9.78658f, 35.51922f, 9.78648f);
generalPath.curveTo(36.03547f, 9.786511f, 36.382347f, 9.834913f, 36.55982f, 9.93168f);
generalPath.curveTo(36.73726f, 10.028514f, 36.825996f, 10.14952f, 36.826027f, 10.29469f);
generalPath.curveTo(36.825993f, 10.52059f, 36.559788f, 10.96428f, 36.027397f, 11.62569f);
generalPath.curveTo(29.799698f, 19.11179f, 24.023397f, 27.01769f, 18.699398f, 35.34269f);
generalPath.curveTo(18.328308f, 35.923508f, 17.569998f, 36.21393f, 16.424498f, 36.21392f);
generalPath.curveTo(15.262797f, 36.213924f, 14.577198f, 36.16552f, 14.367397f, 36.06872f);
generalPath.curveTo(13.818837f, 35.82672f, 13.173497f, 34.59252f, 12.431297f, 32.36602f);
generalPath.curveTo(11.592327f, 29.89752f, 11.172797f, 28.34872f, 11.172897f, 27.719421f);
generalPath.curveTo(11.17289f, 27.041811f, 11.737577f, 26.388422f, 12.866998f, 25.75912f);
generalPath.curveTo(13.560748f, 25.371922f, 14.173798f, 25.178322f, 14.706298f, 25.1783f);
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setTransform(transformsStack.pop());
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_3
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_3_0
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(43.429f, 21.8f);
generalPath.curveTo(43.429f, 32.663f, 33.043f, 15.514999f, 24.698f, 22.188f);
generalPath.curveTo(16.547f, 28.705f, 4.0389996f, 34.415f, 4.0389996f, 23.552f);
generalPath.curveTo(4.0393996f, 12.434f, 12.761f, 2.1210003f, 23.623999f, 2.1210003f);
generalPath.curveTo(34.487f, 2.1208003f, 43.429f, 10.937f, 43.429f, 21.800001f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(21.993999481201172, 33.95500183105469), new Point2D.Double(20.91699981689453, 15.8149995803833), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(255, 254, 255, 85)) : new Color(255, 254, 255, 85)),((colorFilter != null) ? colorFilter.filter(new Color(255, 254, 255, 55)) : new Color(255, 254, 255, 55))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.003000020980835f, 0.0f, 0.0f, 1.003000020980835f, -0.07185900211334229f, 0.019683999940752983f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setTransform(transformsStack.pop());
g.setTransform(transformsStack.pop());
g.setTransform(transformsStack.pop());

}



	private void innerPaint(Graphics2D g) {
        float origAlpha = 1.0f;
        Composite origComposite = g.getComposite();
        if (origComposite instanceof AlphaComposite) {
            AlphaComposite origAlphaComposite = 
                (AlphaComposite)origComposite;
            if (origAlphaComposite.getRule() == AlphaComposite.SRC_OVER) {
                origAlpha = origAlphaComposite.getAlpha();
            }
        }
        
	    _paint0(g, origAlpha);


	    shape = null;
	    generalPath = null;
	    paint = null;
	    stroke = null;
        transformsStack.clear();
	}

    /**
     * Returns the X of the bounding box of the original SVG image.
     * 
     * @return The X of the bounding box of the original SVG image.
     */
    public static double getOrigX() {
        return 2.000117778778076;
    }

    /**
     * Returns the Y of the bounding box of the original SVG image.
     * 
     * @return The Y of the bounding box of the original SVG image.
     */
    public static double getOrigY() {
        return 1.0005923509597778;
    }

	/**
	 * Returns the width of the bounding box of the original SVG image.
	 * 
	 * @return The width of the bounding box of the original SVG image.
	 */
	public static double getOrigWidth() {
		return 43.99979019165039;
	}

	/**
	 * Returns the height of the bounding box of the original SVG image.
	 * 
	 * @return The height of the bounding box of the original SVG image.
	 */
	public static double getOrigHeight() {
		return 46.99940872192383;
	}

	/** The current width of this icon. */
	private int width;

    /** The current height of this icon. */
	private int height;

	/**
	 * Creates a new transcoded SVG image. This is marked as private to indicate that app
	 * code should be using the {@link #of(int, int)} method to obtain a pre-configured instance.
	 */
	private TangoRDialog_accept() {
        this.width = (int) getOrigWidth();
        this.height = (int) getOrigHeight();
	}

    @Override
	public int getIconHeight() {
		return height;
	}

    @Override
	public int getIconWidth() {
		return width;
	}

	@Override
	public synchronized void setDimension(Dimension newDimension) {
		this.width = newDimension.width;
		this.height = newDimension.height;
	}

    @Override
    public boolean supportsColorFilter() {
        return true;
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        this.colorFilter = colorFilter;
    }

    @Override
	public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        if(getRotation()!=0) {
            g2d.rotate(getRotation(), x+width/2, y+height/2);
        }
        if(isReflection()) {
        	g2d.translate(x+width/2, y+height/2);
        	g2d.scale(this.rsfx, this.rsfy);
        	g2d.translate(-x-width/2, -y-height/2);
        }
		g2d.translate(x, y);

        double coef1 = (double) this.width / getOrigWidth();
        double coef2 = (double) this.height / getOrigHeight();
        double coef = Math.min(coef1, coef2);
        g2d.clipRect(0, 0, this.width, this.height);
        g2d.scale(coef, coef);
        g2d.translate(-getOrigX(), -getOrigY());
        if (coef1 != coef2) {
            if (coef1 < coef2) {
               int extraDy = (int) ((getOrigWidth() - getOrigHeight()) / 2.0);
               g2d.translate(0, extraDy);
            } else {
               int extraDx = (int) ((getOrigHeight() - getOrigWidth()) / 2.0);
               g2d.translate(extraDx, 0);
            }
        }
        Graphics2D g2ForInner = (Graphics2D) g2d.create();
        innerPaint(g2ForInner);
        g2ForInner.dispose();
        g2d.dispose();
	}
    
    /**
     * Returns a new instance of this icon with specified dimensions.
     *
     * @param width Required width of the icon
     * @param height Required height of the icon
     * @return A new instance of this icon with specified dimensions.
     */
    public static RadianceIcon of(int width, int height) {
       TangoRDialog_accept base = new TangoRDialog_accept();
       base.width = width;
       base.height = height;
       return base;
    }

    /**
     * Returns a new {@link UIResource} instance of this icon with specified dimensions.
     *
     * @param width Required width of the icon
     * @param height Required height of the icon
     * @return A new {@link UIResource} instance of this icon with specified dimensions.
     */
    public static RadianceIconUIResource uiResourceOf(int width, int height) {
       TangoRDialog_accept base = new TangoRDialog_accept();
       base.width = width;
       base.height = height;
       return new RadianceIconUIResource(base);
    }

    /**
     * Returns a factory that returns instances of this icon on demand.
     *
     * @return Factory that returns instances of this icon on demand.
     */
    public static Factory factory() {
        return TangoRDialog_accept::new;
    }
}

