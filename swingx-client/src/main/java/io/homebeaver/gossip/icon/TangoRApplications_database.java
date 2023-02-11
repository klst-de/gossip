package io.homebeaver.gossip.icon;

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
public class TangoRApplications_database implements RadianceIcon {
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
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(8.0f, 8.5f);
generalPath.curveTo(3.8578644f, 8.5f, 0.5f, 9.395431f, 0.5f, 10.5f);
generalPath.lineTo(0.5f, 13.5f);
generalPath.curveTo(0.5f, 14.60457f, 3.8578641f, 15.5f, 8.0f, 15.5f);
generalPath.curveTo(12.142136f, 15.5f, 15.5f, 14.60457f, 15.5f, 13.5f);
generalPath.lineTo(15.5f, 10.5f);
generalPath.curveTo(15.5f, 9.395431f, 12.142136f, 8.5f, 8.0f, 8.5f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(0.5, 12.0), new Point2D.Double(15.5, 12.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(114, 159, 207, 255)) : new Color(114, 159, 207, 255)),((colorFilter != null) ? colorFilter.filter(new Color(32, 74, 135, 255)) : new Color(32, 74, 135, 255))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(0.5f, 10.46875f);
generalPath.curveTo(0.49948755f, 10.47835f, 0.5f, 10.49033f, 0.5f, 10.5f);
generalPath.lineTo(0.5f, 13.5f);
generalPath.curveTo(0.5f, 14.60457f, 3.8578641f, 15.5f, 8.0f, 15.5f);
generalPath.curveTo(12.142136f, 15.5f, 15.5f, 14.60457f, 15.5f, 13.5f);
generalPath.lineTo(15.5f, 10.5f);
generalPath.curveTo(15.5f, 10.4903f, 15.500512f, 10.47838f, 15.5f, 10.46875f);
generalPath.curveTo(15.40278f, 10.55198f, 15.299759f, 10.616724f, 15.1875f, 10.6875f);
generalPath.curveTo(14.738464f, 10.970604f, 14.146706f, 11.217128f, 13.4375f, 11.40625f);
generalPath.curveTo(12.019088f, 11.784493f, 10.107097f, 12.0f, 8.0f, 12.0f);
generalPath.curveTo(5.892903f, 12.0f, 3.9809127f, 11.784493f, 2.5625f, 11.40625f);
generalPath.curveTo(1.8532938f, 11.217128f, 1.2615362f, 10.970604f, 0.8125f, 10.6875f);
generalPath.curveTo(0.70024097f, 10.616724f, 0.5972204f, 10.551983f, 0.5f, 10.46875f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(0.49977225065231323, 12.984375), new Point2D.Double(15.500227928161621, 12.984375), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(203, 219, 237, 255)) : new Color(203, 219, 237, 255)),((colorFilter != null) ? colorFilter.filter(new Color(32, 74, 135, 255)) : new Color(32, 74, 135, 255))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -10.0f));
// _0_2
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(8.0f, 19.0f);
generalPath.curveTo(5.964961f, 19.0f, 4.108554f, 19.216887f, 2.8125f, 19.5625f);
generalPath.curveTo(2.164473f, 19.735308f, 1.6537551f, 19.9608f, 1.34375f, 20.15625f);
generalPath.curveTo(1.0337449f, 20.3517f, 1.0f, 20.477188f, 1.0f, 20.5f);
generalPath.lineTo(1.0f, 23.5f);
generalPath.curveTo(1.0f, 23.52281f, 1.033745f, 23.6483f, 1.34375f, 23.84375f);
generalPath.curveTo(1.6537551f, 24.0392f, 2.164473f, 24.264692f, 2.8125f, 24.4375f);
generalPath.curveTo(4.1085534f, 24.783113f, 5.9649615f, 25.0f, 8.0f, 25.0f);
generalPath.curveTo(10.035039f, 25.0f, 11.891446f, 24.783113f, 13.1875f, 24.4375f);
generalPath.curveTo(13.835527f, 24.264692f, 14.346245f, 24.0392f, 14.65625f, 23.84375f);
generalPath.curveTo(14.96626f, 23.6483f, 15.0f, 23.52281f, 15.0f, 23.5f);
generalPath.lineTo(15.0f, 20.5f);
generalPath.curveTo(15.0f, 20.47719f, 14.96626f, 20.3517f, 14.65625f, 20.15625f);
generalPath.curveTo(14.346245f, 19.9608f, 13.835527f, 19.735308f, 13.1875f, 19.5625f);
generalPath.curveTo(11.891446f, 19.216887f, 10.035039f, 19.0f, 8.0f, 19.0f);
generalPath.closePath();
generalPath.moveTo(8.0f, 20.0f);
generalPath.curveTo(9.96298f, 20.0f, 11.732555f, 20.218266f, 12.90625f, 20.53125f);
generalPath.curveTo(13.430581f, 20.671072f, 13.797947f, 20.826551f, 14.0f, 20.9375f);
generalPath.lineTo(14.0f, 23.0625f);
generalPath.curveTo(13.797947f, 23.173449f, 13.430581f, 23.328928f, 12.90625f, 23.46875f);
generalPath.curveTo(11.732555f, 23.781734f, 9.96298f, 24.0f, 8.0f, 24.0f);
generalPath.curveTo(6.03702f, 24.0f, 4.2674446f, 23.781734f, 3.09375f, 23.46875f);
generalPath.curveTo(2.5694191f, 23.328928f, 2.2020533f, 23.173449f, 2.0f, 23.0625f);
generalPath.lineTo(2.0f, 20.9375f);
generalPath.curveTo(2.2020533f, 20.826551f, 2.5694191f, 20.671072f, 3.09375f, 20.53125f);
generalPath.curveTo(4.2674446f, 20.218266f, 6.03702f, 20.0f, 8.0f, 20.0f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(1.0, 22.0), new Point2D.Double(15.0, 22.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 153)) : new Color(255, 255, 255, 153)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 53)) : new Color(255, 255, 255, 53))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_3
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_3_0
paint = (colorFilter != null) ? colorFilter.filter(new Color(24, 56, 102, 255)) : new Color(24, 56, 102, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(8.0f, 8.5f);
generalPath.curveTo(3.8578644f, 8.5f, 0.5f, 9.395431f, 0.5f, 10.5f);
generalPath.lineTo(0.5f, 13.5f);
generalPath.curveTo(0.5f, 14.60457f, 3.8578641f, 15.5f, 8.0f, 15.5f);
generalPath.curveTo(12.142136f, 15.5f, 15.5f, 14.60457f, 15.5f, 13.5f);
generalPath.lineTo(15.5f, 10.5f);
generalPath.curveTo(15.5f, 9.395431f, 12.142136f, 8.5f, 8.0f, 8.5f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -4.0f));
// _0_4
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(8.0f, 8.5f);
generalPath.curveTo(3.8578644f, 8.5f, 0.5f, 9.395431f, 0.5f, 10.5f);
generalPath.lineTo(0.5f, 13.5f);
generalPath.curveTo(0.5f, 14.60457f, 3.8578641f, 15.5f, 8.0f, 15.5f);
generalPath.curveTo(12.142136f, 15.5f, 15.5f, 14.60457f, 15.5f, 13.5f);
generalPath.lineTo(15.5f, 10.5f);
generalPath.curveTo(15.5f, 9.395431f, 12.142136f, 8.5f, 8.0f, 8.5f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(0.5, 12.0), new Point2D.Double(15.5, 12.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(114, 159, 207, 255)) : new Color(114, 159, 207, 255)),((colorFilter != null) ? colorFilter.filter(new Color(32, 74, 135, 255)) : new Color(32, 74, 135, 255))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -4.0f));
// _0_5
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(0.5f, 10.46875f);
generalPath.curveTo(0.49948755f, 10.47835f, 0.5f, 10.49033f, 0.5f, 10.5f);
generalPath.lineTo(0.5f, 13.5f);
generalPath.curveTo(0.5f, 14.60457f, 3.8578641f, 15.5f, 8.0f, 15.5f);
generalPath.curveTo(12.142136f, 15.5f, 15.5f, 14.60457f, 15.5f, 13.5f);
generalPath.lineTo(15.5f, 10.5f);
generalPath.curveTo(15.5f, 10.4903f, 15.500512f, 10.47838f, 15.5f, 10.46875f);
generalPath.curveTo(15.40278f, 10.55198f, 15.299759f, 10.616724f, 15.1875f, 10.6875f);
generalPath.curveTo(14.738464f, 10.970604f, 14.146706f, 11.217128f, 13.4375f, 11.40625f);
generalPath.curveTo(12.019088f, 11.784493f, 10.107097f, 12.0f, 8.0f, 12.0f);
generalPath.curveTo(5.892903f, 12.0f, 3.9809127f, 11.784493f, 2.5625f, 11.40625f);
generalPath.curveTo(1.8532938f, 11.217128f, 1.2615362f, 10.970604f, 0.8125f, 10.6875f);
generalPath.curveTo(0.70024097f, 10.616724f, 0.5972204f, 10.551983f, 0.5f, 10.46875f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(0.49977225065231323, 12.984375), new Point2D.Double(15.500227928161621, 12.984375), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(203, 219, 237, 255)) : new Color(203, 219, 237, 255)),((colorFilter != null) ? colorFilter.filter(new Color(32, 74, 135, 255)) : new Color(32, 74, 135, 255))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -14.0f));
// _0_6
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(8.0f, 19.0f);
generalPath.curveTo(5.964961f, 19.0f, 4.108554f, 19.216887f, 2.8125f, 19.5625f);
generalPath.curveTo(2.164473f, 19.735308f, 1.6537551f, 19.9608f, 1.34375f, 20.15625f);
generalPath.curveTo(1.0337449f, 20.3517f, 1.0f, 20.477188f, 1.0f, 20.5f);
generalPath.lineTo(1.0f, 23.5f);
generalPath.curveTo(1.0f, 23.52281f, 1.033745f, 23.6483f, 1.34375f, 23.84375f);
generalPath.curveTo(1.6537551f, 24.0392f, 2.164473f, 24.264692f, 2.8125f, 24.4375f);
generalPath.curveTo(4.1085534f, 24.783113f, 5.9649615f, 25.0f, 8.0f, 25.0f);
generalPath.curveTo(10.035039f, 25.0f, 11.891446f, 24.783113f, 13.1875f, 24.4375f);
generalPath.curveTo(13.835527f, 24.264692f, 14.346245f, 24.0392f, 14.65625f, 23.84375f);
generalPath.curveTo(14.96626f, 23.6483f, 15.0f, 23.52281f, 15.0f, 23.5f);
generalPath.lineTo(15.0f, 20.5f);
generalPath.curveTo(15.0f, 20.47719f, 14.96626f, 20.3517f, 14.65625f, 20.15625f);
generalPath.curveTo(14.346245f, 19.9608f, 13.835527f, 19.735308f, 13.1875f, 19.5625f);
generalPath.curveTo(11.891446f, 19.216887f, 10.035039f, 19.0f, 8.0f, 19.0f);
generalPath.closePath();
generalPath.moveTo(8.0f, 20.0f);
generalPath.curveTo(9.96298f, 20.0f, 11.732555f, 20.218266f, 12.90625f, 20.53125f);
generalPath.curveTo(13.430581f, 20.671072f, 13.797947f, 20.826551f, 14.0f, 20.9375f);
generalPath.lineTo(14.0f, 23.0625f);
generalPath.curveTo(13.797947f, 23.173449f, 13.430581f, 23.328928f, 12.90625f, 23.46875f);
generalPath.curveTo(11.732555f, 23.781734f, 9.96298f, 24.0f, 8.0f, 24.0f);
generalPath.curveTo(6.03702f, 24.0f, 4.2674446f, 23.781734f, 3.09375f, 23.46875f);
generalPath.curveTo(2.5694191f, 23.328928f, 2.2020533f, 23.173449f, 2.0f, 23.0625f);
generalPath.lineTo(2.0f, 20.9375f);
generalPath.curveTo(2.2020533f, 20.826551f, 2.5694191f, 20.671072f, 3.09375f, 20.53125f);
generalPath.curveTo(4.2674446f, 20.218266f, 6.03702f, 20.0f, 8.0f, 20.0f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(1.0, 22.0), new Point2D.Double(15.0, 22.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 153)) : new Color(255, 255, 255, 153)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 53)) : new Color(255, 255, 255, 53))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_7
paint = (colorFilter != null) ? colorFilter.filter(new Color(24, 56, 102, 255)) : new Color(24, 56, 102, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(8.0f, 4.5f);
generalPath.curveTo(3.8578644f, 4.5f, 0.5f, 5.3954306f, 0.5f, 6.5f);
generalPath.lineTo(0.5f, 9.5f);
generalPath.curveTo(0.5f, 10.60457f, 3.8578641f, 11.5f, 8.0f, 11.5f);
generalPath.curveTo(12.142136f, 11.5f, 15.5f, 10.60457f, 15.5f, 9.5f);
generalPath.lineTo(15.5f, 6.5f);
generalPath.curveTo(15.5f, 5.3954306f, 12.142136f, 4.5f, 8.0f, 4.5f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -8.0f));
// _0_8
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(8.0f, 8.5f);
generalPath.curveTo(3.8578644f, 8.5f, 0.5f, 9.395431f, 0.5f, 10.5f);
generalPath.lineTo(0.5f, 13.5f);
generalPath.curveTo(0.5f, 14.60457f, 3.8578641f, 15.5f, 8.0f, 15.5f);
generalPath.curveTo(12.142136f, 15.5f, 15.5f, 14.60457f, 15.5f, 13.5f);
generalPath.lineTo(15.5f, 10.5f);
generalPath.curveTo(15.5f, 9.395431f, 12.142136f, 8.5f, 8.0f, 8.5f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(0.5, 12.0), new Point2D.Double(15.5, 12.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(114, 159, 207, 255)) : new Color(114, 159, 207, 255)),((colorFilter != null) ? colorFilter.filter(new Color(32, 74, 135, 255)) : new Color(32, 74, 135, 255))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -8.0f));
// _0_9
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(0.5f, 10.46875f);
generalPath.curveTo(0.49948755f, 10.47835f, 0.5f, 10.49033f, 0.5f, 10.5f);
generalPath.lineTo(0.5f, 13.5f);
generalPath.curveTo(0.5f, 14.60457f, 3.8578641f, 15.5f, 8.0f, 15.5f);
generalPath.curveTo(12.142136f, 15.5f, 15.5f, 14.60457f, 15.5f, 13.5f);
generalPath.lineTo(15.5f, 10.5f);
generalPath.curveTo(15.5f, 10.4903f, 15.500512f, 10.47838f, 15.5f, 10.46875f);
generalPath.curveTo(15.40278f, 10.55198f, 15.299759f, 10.616724f, 15.1875f, 10.6875f);
generalPath.curveTo(14.738464f, 10.970604f, 14.146706f, 11.217128f, 13.4375f, 11.40625f);
generalPath.curveTo(12.019088f, 11.784493f, 10.107097f, 12.0f, 8.0f, 12.0f);
generalPath.curveTo(5.892903f, 12.0f, 3.9809127f, 11.784493f, 2.5625f, 11.40625f);
generalPath.curveTo(1.8532938f, 11.217128f, 1.2615362f, 10.970604f, 0.8125f, 10.6875f);
generalPath.curveTo(0.70024097f, 10.616724f, 0.5972204f, 10.551983f, 0.5f, 10.46875f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(0.49977225065231323, 12.984375), new Point2D.Double(15.500227928161621, 12.984375), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(203, 219, 237, 255)) : new Color(203, 219, 237, 255)),((colorFilter != null) ? colorFilter.filter(new Color(32, 74, 135, 255)) : new Color(32, 74, 135, 255))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_10
paint = (colorFilter != null) ? colorFilter.filter(new Color(24, 56, 102, 255)) : new Color(24, 56, 102, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(8.0f, 0.5f);
generalPath.curveTo(3.8578641f, 0.5f, 0.5f, 1.3954304f, 0.5f, 2.5f);
generalPath.lineTo(0.5f, 5.5f);
generalPath.curveTo(0.5f, 6.60457f, 3.8578641f, 7.5f, 8.0f, 7.5f);
generalPath.curveTo(12.142136f, 7.5f, 15.5f, 6.60457f, 15.5f, 5.5f);
generalPath.lineTo(15.5f, 2.5f);
generalPath.curveTo(15.5f, 1.3954304f, 12.142136f, 0.5f, 8.0f, 0.5f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -18.0f));
// _0_11
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(8.0f, 19.0f);
generalPath.curveTo(5.964961f, 19.0f, 4.108554f, 19.216887f, 2.8125f, 19.5625f);
generalPath.curveTo(2.164473f, 19.735308f, 1.6537551f, 19.9608f, 1.34375f, 20.15625f);
generalPath.curveTo(1.0337449f, 20.3517f, 1.0f, 20.477188f, 1.0f, 20.5f);
generalPath.lineTo(1.0f, 23.5f);
generalPath.curveTo(1.0f, 23.52281f, 1.033745f, 23.6483f, 1.34375f, 23.84375f);
generalPath.curveTo(1.6537551f, 24.0392f, 2.164473f, 24.264692f, 2.8125f, 24.4375f);
generalPath.curveTo(4.1085534f, 24.783113f, 5.9649615f, 25.0f, 8.0f, 25.0f);
generalPath.curveTo(10.035039f, 25.0f, 11.891446f, 24.783113f, 13.1875f, 24.4375f);
generalPath.curveTo(13.835527f, 24.264692f, 14.346245f, 24.0392f, 14.65625f, 23.84375f);
generalPath.curveTo(14.96626f, 23.6483f, 15.0f, 23.52281f, 15.0f, 23.5f);
generalPath.lineTo(15.0f, 20.5f);
generalPath.curveTo(15.0f, 20.47719f, 14.96626f, 20.3517f, 14.65625f, 20.15625f);
generalPath.curveTo(14.346245f, 19.9608f, 13.835527f, 19.735308f, 13.1875f, 19.5625f);
generalPath.curveTo(11.891446f, 19.216887f, 10.035039f, 19.0f, 8.0f, 19.0f);
generalPath.closePath();
generalPath.moveTo(8.0f, 20.0f);
generalPath.curveTo(9.96298f, 20.0f, 11.732555f, 20.218266f, 12.90625f, 20.53125f);
generalPath.curveTo(13.430581f, 20.671072f, 13.797947f, 20.826551f, 14.0f, 20.9375f);
generalPath.lineTo(14.0f, 23.0625f);
generalPath.curveTo(13.797947f, 23.173449f, 13.430581f, 23.328928f, 12.90625f, 23.46875f);
generalPath.curveTo(11.732555f, 23.781734f, 9.96298f, 24.0f, 8.0f, 24.0f);
generalPath.curveTo(6.03702f, 24.0f, 4.2674446f, 23.781734f, 3.09375f, 23.46875f);
generalPath.curveTo(2.5694191f, 23.328928f, 2.2020533f, 23.173449f, 2.0f, 23.0625f);
generalPath.lineTo(2.0f, 20.9375f);
generalPath.curveTo(2.2020533f, 20.826551f, 2.5694191f, 20.671072f, 3.09375f, 20.53125f);
generalPath.curveTo(4.2674446f, 20.218266f, 6.03702f, 20.0f, 8.0f, 20.0f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(1.0, 22.0), new Point2D.Double(15.0, 22.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 153)) : new Color(255, 255, 255, 153)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 53)) : new Color(255, 255, 255, 53))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.399999976158142f, 0.0f, 0.0f, 1.2857099771499634f, -30.200000762939453f, -4.428599834442139f));
// _0_12
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(8.0f, 8.5f);
generalPath.curveTo(3.8578644f, 8.5f, 0.5f, 9.395431f, 0.5f, 10.5f);
generalPath.lineTo(0.5f, 13.5f);
generalPath.curveTo(0.5f, 14.60457f, 3.8578641f, 15.5f, 8.0f, 15.5f);
generalPath.curveTo(12.142136f, 15.5f, 15.5f, 14.60457f, 15.5f, 13.5f);
generalPath.lineTo(15.5f, 10.5f);
generalPath.curveTo(15.5f, 9.395431f, 12.142136f, 8.5f, 8.0f, 8.5f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(0.5, 12.0), new Point2D.Double(15.5, 12.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(114, 159, 207, 255)) : new Color(114, 159, 207, 255)),((colorFilter != null) ? colorFilter.filter(new Color(32, 74, 135, 255)) : new Color(32, 74, 135, 255))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -14.0f));
// _0_13
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(-29.0f, 22.28125f);
generalPath.curveTo(-29.327286f, 22.529987f, -29.5f, 22.787104f, -29.5f, 23.0625f);
generalPath.lineTo(-29.5f, 26.9375f);
generalPath.curveTo(-29.5f, 28.35766f, -24.79899f, 29.5f, -19.0f, 29.5f);
generalPath.curveTo(-13.20101f, 29.5f, -8.5f, 28.35766f, -8.5f, 26.9375f);
generalPath.lineTo(-8.5f, 23.0625f);
generalPath.curveTo(-8.5f, 22.787104f, -8.672715f, 22.529987f, -9.0f, 22.28125f);
generalPath.curveTo(-9.026097f, 22.29706f, -9.03593f, 22.32837f, -9.0625f, 22.34375f);
generalPath.curveTo(-9.666368f, 22.693392f, -10.455516f, 22.978266f, -11.4375f, 23.21875f);
generalPath.curveTo(-13.401466f, 23.699722f, -16.062908f, 23.96875f, -19.0f, 23.96875f);
generalPath.curveTo(-21.937092f, 23.96875f, -24.598534f, 23.699722f, -26.5625f, 23.21875f);
generalPath.curveTo(-27.544483f, 22.978266f, -28.333632f, 22.693392f, -28.9375f, 22.34375f);
generalPath.curveTo(-28.96407f, 22.32837f, -28.9739f, 22.29706f, -29.0f, 22.28125f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(-29.5, 25.890625), new Point2D.Double(-8.5, 25.890625), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(203, 219, 237, 255)) : new Color(203, 219, 237, 255)),((colorFilter != null) ? colorFilter.filter(new Color(32, 74, 135, 255)) : new Color(32, 74, 135, 255))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -19.0f));
// _0_14
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(-19.0f, 26.0f);
generalPath.curveTo(-21.861898f, 26.0f, -24.476213f, 26.269047f, -26.3125f, 26.71875f);
generalPath.curveTo(-27.230642f, 26.943602f, -27.94746f, 27.20311f, -28.40625f, 27.46875f);
generalPath.curveTo(-28.86504f, 27.73439f, -29.0f, 27.950232f, -29.0f, 28.0625f);
generalPath.lineTo(-29.0f, 31.9375f);
generalPath.curveTo(-29.0f, 32.04977f, -28.86504f, 32.26561f, -28.40625f, 32.53125f);
generalPath.curveTo(-27.94746f, 32.79689f, -27.230642f, 33.0564f, -26.3125f, 33.28125f);
generalPath.curveTo(-24.476215f, 33.730953f, -21.861898f, 34.0f, -19.0f, 34.0f);
generalPath.curveTo(-16.138102f, 34.0f, -13.523787f, 33.730953f, -11.6875f, 33.28125f);
generalPath.curveTo(-10.769358f, 33.0564f, -10.05254f, 32.79689f, -9.59375f, 32.53125f);
generalPath.curveTo(-9.134956f, 32.26561f, -9.0f, 32.04977f, -9.0f, 31.9375f);
generalPath.lineTo(-9.0f, 28.0625f);
generalPath.curveTo(-9.0f, 27.950232f, -9.13496f, 27.73439f, -9.59375f, 27.46875f);
generalPath.curveTo(-10.05254f, 27.20311f, -10.769358f, 26.943602f, -11.6875f, 26.71875f);
generalPath.curveTo(-13.523786f, 26.269047f, -16.138102f, 26.0f, -19.0f, 26.0f);
generalPath.closePath();
generalPath.moveTo(-19.0f, 27.0f);
generalPath.curveTo(-16.213295f, 27.0f, -13.708605f, 27.206566f, -12.0f, 27.625f);
generalPath.curveTo(-11.306109f, 27.736963f, -10.626757f, 28.064642f, -10.0f, 28.34375f);
generalPath.lineTo(-10.0f, 31.65625f);
generalPath.curveTo(-10.584938f, 32.035007f, -11.344396f, 32.170555f, -12.0f, 32.375f);
generalPath.curveTo(-13.708605f, 32.793434f, -16.213295f, 33.0f, -19.0f, 33.0f);
generalPath.curveTo(-21.786705f, 33.0f, -24.291397f, 32.793434f, -26.0f, 32.375f);
generalPath.curveTo(-26.693892f, 32.263035f, -27.373243f, 31.935358f, -28.0f, 31.65625f);
generalPath.lineTo(-28.0f, 28.34375f);
generalPath.curveTo(-27.415062f, 27.964994f, -26.655603f, 27.829445f, -26.0f, 27.625f);
generalPath.curveTo(-24.291395f, 27.206566f, -21.786705f, 27.0f, -19.0f, 27.0f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(-28.96875, 30.0), new Point2D.Double(-9.03125, 30.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 153)) : new Color(255, 255, 255, 153)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 53)) : new Color(255, 255, 255, 53))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.399999976158142f, 0.0f, 0.0f, 1.2857099771499634f, -30.200000762939453f, -4.428599834442139f));
// _0_15
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_15_0
paint = (colorFilter != null) ? colorFilter.filter(new Color(24, 56, 102, 255)) : new Color(24, 56, 102, 255);
stroke = new BasicStroke(0.74535596f,0,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(8.0f, 8.5f);
generalPath.curveTo(3.8578644f, 8.5f, 0.5f, 9.395431f, 0.5f, 10.5f);
generalPath.lineTo(0.5f, 13.5f);
generalPath.curveTo(0.5f, 14.60457f, 3.8578641f, 15.5f, 8.0f, 15.5f);
generalPath.curveTo(12.142136f, 15.5f, 15.5f, 14.60457f, 15.5f, 13.5f);
generalPath.lineTo(15.5f, 10.5f);
generalPath.curveTo(15.5f, 9.395431f, 12.142136f, 8.5f, 8.0f, 8.5f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -11.0f));
// _0_16
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(-27.28125f, 20.0f);
generalPath.curveTo(-27.535006f, 20.09009f, -27.780647f, 20.201715f, -28.0f, 20.34375f);
generalPath.lineTo(-28.0f, 20.8125f);
generalPath.curveTo(-27.638203f, 20.941624f, -27.259233f, 21.054499f, -26.84375f, 21.15625f);
generalPath.curveTo(-24.752102f, 21.66849f, -22.012285f, 21.9375f, -19.0f, 21.9375f);
generalPath.curveTo(-15.987715f, 21.9375f, -13.247898f, 21.66849f, -11.15625f, 21.15625f);
generalPath.curveTo(-10.740766f, 21.054499f, -10.361797f, 20.941624f, -10.0f, 20.8125f);
generalPath.lineTo(-10.0f, 20.34375f);
generalPath.curveTo(-10.243227f, 20.235435f, -10.493155f, 20.107498f, -10.75f, 20.0f);
generalPath.curveTo(-10.976976f, 20.068f, -11.185191f, 20.15696f, -11.4375f, 20.21875f);
generalPath.curveTo(-13.401466f, 20.699722f, -16.062908f, 20.96875f, -19.0f, 20.96875f);
generalPath.curveTo(-21.937092f, 20.96875f, -24.598534f, 20.699722f, -26.5625f, 20.21875f);
generalPath.curveTo(-26.825272f, 20.1544f, -27.045952f, 20.071098f, -27.28125f, 20.0f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(-28.0, 20.96875), new Point2D.Double(-19.0, 21.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 204)) : new Color(255, 255, 255, 204)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 0)) : new Color(255, 255, 255, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.399999976158142f, 0.0f, 0.0f, 1.2857099771499634f, -30.200000762939453f, -10.428600311279297f));
// _0_17
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(8.0f, 8.5f);
generalPath.curveTo(3.8578644f, 8.5f, 0.5f, 9.395431f, 0.5f, 10.5f);
generalPath.lineTo(0.5f, 13.5f);
generalPath.curveTo(0.5f, 14.60457f, 3.8578641f, 15.5f, 8.0f, 15.5f);
generalPath.curveTo(12.142136f, 15.5f, 15.5f, 14.60457f, 15.5f, 13.5f);
generalPath.lineTo(15.5f, 10.5f);
generalPath.curveTo(15.5f, 9.395431f, 12.142136f, 8.5f, 8.0f, 8.5f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(0.5, 12.0), new Point2D.Double(15.5, 12.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(114, 159, 207, 255)) : new Color(114, 159, 207, 255)),((colorFilter != null) ? colorFilter.filter(new Color(32, 74, 135, 255)) : new Color(32, 74, 135, 255))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -20.0f));
// _0_18
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(-29.0f, 22.28125f);
generalPath.curveTo(-29.327286f, 22.529987f, -29.5f, 22.787104f, -29.5f, 23.0625f);
generalPath.lineTo(-29.5f, 26.9375f);
generalPath.curveTo(-29.5f, 28.35766f, -24.79899f, 29.5f, -19.0f, 29.5f);
generalPath.curveTo(-13.20101f, 29.5f, -8.5f, 28.35766f, -8.5f, 26.9375f);
generalPath.lineTo(-8.5f, 23.0625f);
generalPath.curveTo(-8.5f, 22.787104f, -8.672715f, 22.529987f, -9.0f, 22.28125f);
generalPath.curveTo(-9.026097f, 22.29706f, -9.03593f, 22.32837f, -9.0625f, 22.34375f);
generalPath.curveTo(-9.666368f, 22.693392f, -10.455516f, 22.978266f, -11.4375f, 23.21875f);
generalPath.curveTo(-13.401466f, 23.699722f, -16.062908f, 23.96875f, -19.0f, 23.96875f);
generalPath.curveTo(-21.937092f, 23.96875f, -24.598534f, 23.699722f, -26.5625f, 23.21875f);
generalPath.curveTo(-27.544483f, 22.978266f, -28.333632f, 22.693392f, -28.9375f, 22.34375f);
generalPath.curveTo(-28.96407f, 22.32837f, -28.9739f, 22.29706f, -29.0f, 22.28125f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(-29.5, 25.890625), new Point2D.Double(-8.5, 25.890625), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(203, 219, 237, 255)) : new Color(203, 219, 237, 255)),((colorFilter != null) ? colorFilter.filter(new Color(32, 74, 135, 255)) : new Color(32, 74, 135, 255))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -25.0f));
// _0_19
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(-19.0f, 26.0f);
generalPath.curveTo(-21.861898f, 26.0f, -24.476213f, 26.269047f, -26.3125f, 26.71875f);
generalPath.curveTo(-27.230642f, 26.943602f, -27.94746f, 27.20311f, -28.40625f, 27.46875f);
generalPath.curveTo(-28.86504f, 27.73439f, -29.0f, 27.950232f, -29.0f, 28.0625f);
generalPath.lineTo(-29.0f, 31.9375f);
generalPath.curveTo(-29.0f, 32.04977f, -28.86504f, 32.26561f, -28.40625f, 32.53125f);
generalPath.curveTo(-27.94746f, 32.79689f, -27.230642f, 33.0564f, -26.3125f, 33.28125f);
generalPath.curveTo(-24.476215f, 33.730953f, -21.861898f, 34.0f, -19.0f, 34.0f);
generalPath.curveTo(-16.138102f, 34.0f, -13.523787f, 33.730953f, -11.6875f, 33.28125f);
generalPath.curveTo(-10.769358f, 33.0564f, -10.05254f, 32.79689f, -9.59375f, 32.53125f);
generalPath.curveTo(-9.134956f, 32.26561f, -9.0f, 32.04977f, -9.0f, 31.9375f);
generalPath.lineTo(-9.0f, 28.0625f);
generalPath.curveTo(-9.0f, 27.950232f, -9.13496f, 27.73439f, -9.59375f, 27.46875f);
generalPath.curveTo(-10.05254f, 27.20311f, -10.769358f, 26.943602f, -11.6875f, 26.71875f);
generalPath.curveTo(-13.523786f, 26.269047f, -16.138102f, 26.0f, -19.0f, 26.0f);
generalPath.closePath();
generalPath.moveTo(-19.0f, 27.0f);
generalPath.curveTo(-16.213295f, 27.0f, -13.708605f, 27.206566f, -12.0f, 27.625f);
generalPath.curveTo(-11.306109f, 27.736963f, -10.626757f, 28.064642f, -10.0f, 28.34375f);
generalPath.lineTo(-10.0f, 31.65625f);
generalPath.curveTo(-10.584938f, 32.035007f, -11.344396f, 32.170555f, -12.0f, 32.375f);
generalPath.curveTo(-13.708605f, 32.793434f, -16.213295f, 33.0f, -19.0f, 33.0f);
generalPath.curveTo(-21.786705f, 33.0f, -24.291397f, 32.793434f, -26.0f, 32.375f);
generalPath.curveTo(-26.693892f, 32.263035f, -27.373243f, 31.935358f, -28.0f, 31.65625f);
generalPath.lineTo(-28.0f, 28.34375f);
generalPath.curveTo(-27.415062f, 27.964994f, -26.655603f, 27.829445f, -26.0f, 27.625f);
generalPath.curveTo(-24.291395f, 27.206566f, -21.786705f, 27.0f, -19.0f, 27.0f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(-28.96875, 30.0), new Point2D.Double(-9.03125, 30.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 153)) : new Color(255, 255, 255, 153)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 53)) : new Color(255, 255, 255, 53))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.399999976158142f, 0.0f, 0.0f, 1.2857099771499634f, -30.200000762939453f, -10.428600311279297f));
// _0_20
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_20_0
paint = (colorFilter != null) ? colorFilter.filter(new Color(24, 56, 102, 255)) : new Color(24, 56, 102, 255);
stroke = new BasicStroke(0.74535596f,0,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(8.0f, 8.5f);
generalPath.curveTo(3.8578644f, 8.5f, 0.5f, 9.395431f, 0.5f, 10.5f);
generalPath.lineTo(0.5f, 13.5f);
generalPath.curveTo(0.5f, 14.60457f, 3.8578641f, 15.5f, 8.0f, 15.5f);
generalPath.curveTo(12.142136f, 15.5f, 15.5f, 14.60457f, 15.5f, 13.5f);
generalPath.lineTo(15.5f, 10.5f);
generalPath.curveTo(15.5f, 9.395431f, 12.142136f, 8.5f, 8.0f, 8.5f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -17.0f));
// _0_21
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(-27.28125f, 20.0f);
generalPath.curveTo(-27.535006f, 20.09009f, -27.780647f, 20.201715f, -28.0f, 20.34375f);
generalPath.lineTo(-28.0f, 20.8125f);
generalPath.curveTo(-27.638203f, 20.941624f, -27.259233f, 21.054499f, -26.84375f, 21.15625f);
generalPath.curveTo(-24.752102f, 21.66849f, -22.012285f, 21.9375f, -19.0f, 21.9375f);
generalPath.curveTo(-15.987715f, 21.9375f, -13.247898f, 21.66849f, -11.15625f, 21.15625f);
generalPath.curveTo(-10.740766f, 21.054499f, -10.361797f, 20.941624f, -10.0f, 20.8125f);
generalPath.lineTo(-10.0f, 20.34375f);
generalPath.curveTo(-10.243227f, 20.235435f, -10.493155f, 20.107498f, -10.75f, 20.0f);
generalPath.curveTo(-10.976976f, 20.068f, -11.185191f, 20.15696f, -11.4375f, 20.21875f);
generalPath.curveTo(-13.401466f, 20.699722f, -16.062908f, 20.96875f, -19.0f, 20.96875f);
generalPath.curveTo(-21.937092f, 20.96875f, -24.598534f, 20.699722f, -26.5625f, 20.21875f);
generalPath.curveTo(-26.825272f, 20.1544f, -27.045952f, 20.071098f, -27.28125f, 20.0f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(-28.0, 20.96875), new Point2D.Double(-19.0, 21.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 204)) : new Color(255, 255, 255, 204)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 0)) : new Color(255, 255, 255, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.399999976158142f, 0.0f, 0.0f, 1.2857099771499634f, -30.200000762939453f, -16.428600311279297f));
// _0_22
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(8.0f, 8.5f);
generalPath.curveTo(3.8578644f, 8.5f, 0.5f, 9.395431f, 0.5f, 10.5f);
generalPath.lineTo(0.5f, 13.5f);
generalPath.curveTo(0.5f, 14.60457f, 3.8578641f, 15.5f, 8.0f, 15.5f);
generalPath.curveTo(12.142136f, 15.5f, 15.5f, 14.60457f, 15.5f, 13.5f);
generalPath.lineTo(15.5f, 10.5f);
generalPath.curveTo(15.5f, 9.395431f, 12.142136f, 8.5f, 8.0f, 8.5f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(0.5, 12.0), new Point2D.Double(15.5, 12.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(114, 159, 207, 255)) : new Color(114, 159, 207, 255)),((colorFilter != null) ? colorFilter.filter(new Color(32, 74, 135, 255)) : new Color(32, 74, 135, 255))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -26.0f));
// _0_23
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(-29.0f, 22.28125f);
generalPath.curveTo(-29.327286f, 22.529987f, -29.5f, 22.787104f, -29.5f, 23.0625f);
generalPath.lineTo(-29.5f, 26.9375f);
generalPath.curveTo(-29.5f, 28.35766f, -24.79899f, 29.5f, -19.0f, 29.5f);
generalPath.curveTo(-13.20101f, 29.5f, -8.5f, 28.35766f, -8.5f, 26.9375f);
generalPath.lineTo(-8.5f, 23.0625f);
generalPath.curveTo(-8.5f, 22.787104f, -8.672715f, 22.529987f, -9.0f, 22.28125f);
generalPath.curveTo(-9.026097f, 22.29706f, -9.03593f, 22.32837f, -9.0625f, 22.34375f);
generalPath.curveTo(-9.666368f, 22.693392f, -10.455516f, 22.978266f, -11.4375f, 23.21875f);
generalPath.curveTo(-13.401466f, 23.699722f, -16.062908f, 23.96875f, -19.0f, 23.96875f);
generalPath.curveTo(-21.937092f, 23.96875f, -24.598534f, 23.699722f, -26.5625f, 23.21875f);
generalPath.curveTo(-27.544483f, 22.978266f, -28.333632f, 22.693392f, -28.9375f, 22.34375f);
generalPath.curveTo(-28.96407f, 22.32837f, -28.9739f, 22.29706f, -29.0f, 22.28125f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(-29.5, 25.890625), new Point2D.Double(-8.5, 25.890625), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(203, 219, 237, 255)) : new Color(203, 219, 237, 255)),((colorFilter != null) ? colorFilter.filter(new Color(32, 74, 135, 255)) : new Color(32, 74, 135, 255))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -31.0f));
// _0_24
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(-19.0f, 26.0f);
generalPath.curveTo(-21.861898f, 26.0f, -24.476213f, 26.269047f, -26.3125f, 26.71875f);
generalPath.curveTo(-27.230642f, 26.943602f, -27.94746f, 27.20311f, -28.40625f, 27.46875f);
generalPath.curveTo(-28.86504f, 27.73439f, -29.0f, 27.950232f, -29.0f, 28.0625f);
generalPath.lineTo(-29.0f, 31.9375f);
generalPath.curveTo(-29.0f, 32.04977f, -28.86504f, 32.26561f, -28.40625f, 32.53125f);
generalPath.curveTo(-27.94746f, 32.79689f, -27.230642f, 33.0564f, -26.3125f, 33.28125f);
generalPath.curveTo(-24.476215f, 33.730953f, -21.861898f, 34.0f, -19.0f, 34.0f);
generalPath.curveTo(-16.138102f, 34.0f, -13.523787f, 33.730953f, -11.6875f, 33.28125f);
generalPath.curveTo(-10.769358f, 33.0564f, -10.05254f, 32.79689f, -9.59375f, 32.53125f);
generalPath.curveTo(-9.134956f, 32.26561f, -9.0f, 32.04977f, -9.0f, 31.9375f);
generalPath.lineTo(-9.0f, 28.0625f);
generalPath.curveTo(-9.0f, 27.950232f, -9.13496f, 27.73439f, -9.59375f, 27.46875f);
generalPath.curveTo(-10.05254f, 27.20311f, -10.769358f, 26.943602f, -11.6875f, 26.71875f);
generalPath.curveTo(-13.523786f, 26.269047f, -16.138102f, 26.0f, -19.0f, 26.0f);
generalPath.closePath();
generalPath.moveTo(-19.0f, 27.0f);
generalPath.curveTo(-16.213295f, 27.0f, -13.708605f, 27.206566f, -12.0f, 27.625f);
generalPath.curveTo(-11.306109f, 27.736963f, -10.626757f, 28.064642f, -10.0f, 28.34375f);
generalPath.lineTo(-10.0f, 31.65625f);
generalPath.curveTo(-10.584938f, 32.035007f, -11.344396f, 32.170555f, -12.0f, 32.375f);
generalPath.curveTo(-13.708605f, 32.793434f, -16.213295f, 33.0f, -19.0f, 33.0f);
generalPath.curveTo(-21.786705f, 33.0f, -24.291397f, 32.793434f, -26.0f, 32.375f);
generalPath.curveTo(-26.693892f, 32.263035f, -27.373243f, 31.935358f, -28.0f, 31.65625f);
generalPath.lineTo(-28.0f, 28.34375f);
generalPath.curveTo(-27.415062f, 27.964994f, -26.655603f, 27.829445f, -26.0f, 27.625f);
generalPath.curveTo(-24.291395f, 27.206566f, -21.786705f, 27.0f, -19.0f, 27.0f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(-28.96875, 30.0), new Point2D.Double(-9.03125, 30.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 153)) : new Color(255, 255, 255, 153)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 53)) : new Color(255, 255, 255, 53))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.399999976158142f, 0.0f, 0.0f, 1.2857099771499634f, -30.200000762939453f, -16.428600311279297f));
// _0_25
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_25_0
paint = (colorFilter != null) ? colorFilter.filter(new Color(24, 56, 102, 255)) : new Color(24, 56, 102, 255);
stroke = new BasicStroke(0.74535596f,0,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(8.0f, 8.5f);
generalPath.curveTo(3.8578644f, 8.5f, 0.5f, 9.395431f, 0.5f, 10.5f);
generalPath.lineTo(0.5f, 13.5f);
generalPath.curveTo(0.5f, 14.60457f, 3.8578641f, 15.5f, 8.0f, 15.5f);
generalPath.curveTo(12.142136f, 15.5f, 15.5f, 14.60457f, 15.5f, 13.5f);
generalPath.lineTo(15.5f, 10.5f);
generalPath.curveTo(15.5f, 9.395431f, 12.142136f, 8.5f, 8.0f, 8.5f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -23.0f));
// _0_26
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(-27.28125f, 20.0f);
generalPath.curveTo(-27.535006f, 20.09009f, -27.780647f, 20.201715f, -28.0f, 20.34375f);
generalPath.lineTo(-28.0f, 20.8125f);
generalPath.curveTo(-27.638203f, 20.941624f, -27.259233f, 21.054499f, -26.84375f, 21.15625f);
generalPath.curveTo(-24.752102f, 21.66849f, -22.012285f, 21.9375f, -19.0f, 21.9375f);
generalPath.curveTo(-15.987715f, 21.9375f, -13.247898f, 21.66849f, -11.15625f, 21.15625f);
generalPath.curveTo(-10.740766f, 21.054499f, -10.361797f, 20.941624f, -10.0f, 20.8125f);
generalPath.lineTo(-10.0f, 20.34375f);
generalPath.curveTo(-10.243227f, 20.235435f, -10.493155f, 20.107498f, -10.75f, 20.0f);
generalPath.curveTo(-10.976976f, 20.068f, -11.185191f, 20.15696f, -11.4375f, 20.21875f);
generalPath.curveTo(-13.401466f, 20.699722f, -16.062908f, 20.96875f, -19.0f, 20.96875f);
generalPath.curveTo(-21.937092f, 20.96875f, -24.598534f, 20.699722f, -26.5625f, 20.21875f);
generalPath.curveTo(-26.825272f, 20.1544f, -27.045952f, 20.071098f, -27.28125f, 20.0f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(-28.0, 20.96875), new Point2D.Double(-19.0, 21.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 204)) : new Color(255, 255, 255, 204)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 0)) : new Color(255, 255, 255, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(0.9406300187110901f, 0.0f, 0.0f, 1.496269941329956f, -3.206242084503174f, -9.451600074768066f));
// _0_27
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(-38.0f, 13.0f);
generalPath.curveTo(-37.97436f, 14.074993f, -41.018322f, 15.070391f, -45.979275f, 15.60928f);
generalPath.curveTo(-50.940224f, 16.148169f, -57.059776f, 16.148169f, -62.020725f, 15.60928f);
generalPath.curveTo(-66.981674f, 15.070391f, -70.02564f, 14.074993f, -70.0f, 13.0f);
generalPath.curveTo(-70.02564f, 11.925007f, -66.981674f, 10.929609f, -62.020725f, 10.39072f);
generalPath.curveTo(-57.059776f, 9.851831f, -50.940224f, 9.851831f, -45.979275f, 10.39072f);
generalPath.curveTo(-41.018322f, 10.929609f, -37.97436f, 11.925007f, -38.0f, 13.0f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(46, 52, 54, 255)) : new Color(46, 52, 54, 255);
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.9333300590515137f, 0.0f, 0.0f, 1.8571399450302124f, -69.46666717529297f, -15.285699844360352f));
// _0_28
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(8.0f, 8.5f);
generalPath.curveTo(3.8578644f, 8.5f, 0.5f, 9.395431f, 0.5f, 10.5f);
generalPath.lineTo(0.5f, 13.5f);
generalPath.curveTo(0.5f, 14.60457f, 3.8578641f, 15.5f, 8.0f, 15.5f);
generalPath.curveTo(12.142136f, 15.5f, 15.5f, 14.60457f, 15.5f, 13.5f);
generalPath.lineTo(15.5f, 10.5f);
generalPath.curveTo(15.5f, 9.395431f, 12.142136f, 8.5f, 8.0f, 8.5f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(0.5, 12.0), new Point2D.Double(15.5, 12.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(114, 159, 207, 255)) : new Color(114, 159, 207, 255)),((colorFilter != null) ? colorFilter.filter(new Color(32, 74, 135, 255)) : new Color(32, 74, 135, 255))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -22.0f));
// _0_29
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(-68.09375f, 25.375f);
generalPath.curveTo(-68.34848f, 25.650661f, -68.5f, 25.923143f, -68.5f, 26.21875f);
generalPath.lineTo(-68.5f, 31.78125f);
generalPath.curveTo(-68.5f, 33.832596f, -62.00812f, 35.5f, -54.0f, 35.5f);
generalPath.curveTo(-45.99188f, 35.5f, -39.5f, 33.832596f, -39.5f, 31.78125f);
generalPath.lineTo(-39.5f, 26.21875f);
generalPath.curveTo(-39.5f, 25.923143f, -39.65152f, 25.650661f, -39.90625f, 25.375f);
generalPath.curveTo(-40.03947f, 25.478735f, -40.187702f, 25.5931f, -40.34375f, 25.6875f);
generalPath.curveTo(-41.14931f, 26.174826f, -42.249054f, 26.56218f, -43.59375f, 26.90625f);
generalPath.curveTo(-46.283146f, 27.594395f, -49.95468f, 28.0f, -54.0f, 28.0f);
generalPath.curveTo(-58.04532f, 28.0f, -61.716858f, 27.594395f, -64.40625f, 26.90625f);
generalPath.curveTo(-65.750946f, 26.56218f, -66.850685f, 26.174826f, -67.65625f, 25.6875f);
generalPath.curveTo(-67.812294f, 25.5931f, -67.96053f, 25.478735f, -68.09375f, 25.375f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(0.5, 12.0), new Point2D.Double(15.5, 12.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(203, 219, 237, 255)) : new Color(203, 219, 237, 255)),((colorFilter != null) ? colorFilter.filter(new Color(32, 74, 135, 255)) : new Color(32, 74, 135, 255))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.9333300590515137f, 0.0f, 0.0f, 1.8571399450302124f, -69.46666717529297f, 6.714300155639648f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -17.0f));
// _0_30
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(-54.0f, 18.0f);
generalPath.curveTo(-57.9673f, 18.0f, -61.594692f, 18.406336f, -64.15625f, 19.0625f);
generalPath.curveTo(-65.43703f, 19.390581f, -66.46219f, 19.754833f, -67.125f, 20.15625f);
generalPath.curveTo(-67.78781f, 20.557667f, -68.0f, 20.954205f, -68.0f, 21.21875f);
generalPath.lineTo(-68.0f, 26.78125f);
generalPath.curveTo(-68.0f, 27.045795f, -67.78781f, 27.442333f, -67.125f, 27.84375f);
generalPath.curveTo(-66.46219f, 28.245167f, -65.43703f, 28.609419f, -64.15625f, 28.9375f);
generalPath.curveTo(-61.594692f, 29.593664f, -57.9673f, 30.0f, -54.0f, 30.0f);
generalPath.curveTo(-50.0327f, 30.0f, -46.405308f, 29.593664f, -43.84375f, 28.9375f);
generalPath.curveTo(-42.56297f, 28.609419f, -41.53781f, 28.245167f, -40.875f, 27.84375f);
generalPath.curveTo(-40.21219f, 27.442333f, -40.0f, 27.045795f, -40.0f, 26.78125f);
generalPath.lineTo(-40.0f, 21.21875f);
generalPath.curveTo(-40.0f, 20.954205f, -40.21219f, 20.557667f, -40.875f, 20.15625f);
generalPath.curveTo(-41.53781f, 19.754833f, -42.56297f, 19.390581f, -43.84375f, 19.0625f);
generalPath.curveTo(-46.405308f, 18.406336f, -50.0327f, 18.0f, -54.0f, 18.0f);
generalPath.closePath();
generalPath.moveTo(-54.0f, 19.0f);
generalPath.curveTo(-50.106224f, 19.0f, -46.592964f, 19.375816f, -44.15625f, 20.0f);
generalPath.curveTo(-42.937893f, 20.312094f, -41.958458f, 20.715746f, -41.4375f, 21.03125f);
generalPath.curveTo(-41.046783f, 21.267878f, -40.996845f, 21.401358f, -41.0f, 21.34375f);
generalPath.lineTo(-41.0f, 26.65625f);
generalPath.curveTo(-40.9968f, 26.59864f, -41.04678f, 26.73212f, -41.4375f, 26.96875f);
generalPath.curveTo(-41.958458f, 27.284254f, -42.937893f, 27.687906f, -44.15625f, 28.0f);
generalPath.curveTo(-46.592964f, 28.624184f, -50.106224f, 29.0f, -54.0f, 29.0f);
generalPath.curveTo(-57.893776f, 29.0f, -61.407036f, 28.624184f, -63.84375f, 28.0f);
generalPath.curveTo(-65.06211f, 27.687906f, -66.04154f, 27.284254f, -66.5625f, 26.96875f);
generalPath.curveTo(-66.95322f, 26.732122f, -67.00315f, 26.598642f, -67.0f, 26.65625f);
generalPath.lineTo(-67.0f, 21.34375f);
generalPath.curveTo(-67.0032f, 21.40136f, -66.95322f, 21.26788f, -66.5625f, 21.03125f);
generalPath.curveTo(-66.04154f, 20.715746f, -65.06211f, 20.312094f, -63.84375f, 20.0f);
generalPath.curveTo(-61.407036f, 19.375816f, -57.893776f, 19.0f, -54.0f, 19.0f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(-68.03125, 24.0), new Point2D.Double(-39.96875, 24.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 153)) : new Color(255, 255, 255, 153)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 53)) : new Color(255, 255, 255, 53))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.9333300590515137f, 0.0f, 0.0f, 1.8571399450302124f, -69.46666717529297f, -15.285699844360352f));
// _0_31
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_31_0
paint = (colorFilter != null) ? colorFilter.filter(new Color(24, 56, 102, 255)) : new Color(24, 56, 102, 255);
stroke = new BasicStroke(0.5277452f,0,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(8.0f, 8.5f);
generalPath.curveTo(3.8578644f, 8.5f, 0.5f, 9.395431f, 0.5f, 10.5f);
generalPath.lineTo(0.5f, 13.5f);
generalPath.curveTo(0.5f, 14.60457f, 3.8578641f, 15.5f, 8.0f, 15.5f);
generalPath.curveTo(12.142136f, 15.5f, 15.5f, 14.60457f, 15.5f, 13.5f);
generalPath.lineTo(15.5f, 10.5f);
generalPath.curveTo(15.5f, 9.395431f, 12.142136f, 8.5f, 8.0f, 8.5f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -24.0f));
// _0_32
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(-66.625f, 28.09375f);
generalPath.curveTo(-66.92386f, 28.290142f, -67.0029f, 28.395983f, -67.0f, 28.34375f);
generalPath.lineTo(-67.0f, 29.0f);
generalPath.curveTo(-66.2876f, 29.330662f, -65.41365f, 29.616947f, -64.40625f, 29.875f);
generalPath.curveTo(-61.71985f, 30.563145f, -58.04082f, 30.96875f, -54.0f, 30.96875f);
generalPath.curveTo(-49.95918f, 30.96875f, -46.28015f, 30.563145f, -43.59375f, 29.875f);
generalPath.curveTo(-42.58635f, 29.616947f, -41.712406f, 29.330662f, -41.0f, 29.0f);
generalPath.lineTo(-41.0f, 28.34375f);
generalPath.curveTo(-40.9971f, 28.39598f, -41.07615f, 28.29014f, -41.375f, 28.09375f);
generalPath.curveTo(-42.011677f, 28.393154f, -42.847588f, 28.682325f, -43.84375f, 28.9375f);
generalPath.curveTo(-46.405308f, 29.593664f, -50.0327f, 30.03125f, -54.0f, 30.03125f);
generalPath.curveTo(-57.9673f, 30.03125f, -61.594692f, 29.593664f, -64.15625f, 28.9375f);
generalPath.curveTo(-65.15241f, 28.682325f, -65.98832f, 28.393154f, -66.625f, 28.09375f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(-69.0, 29.0), new Point2D.Double(-54.0, 29.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 204)) : new Color(255, 255, 255, 204)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 0)) : new Color(255, 255, 255, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.9333300590515137f, 0.0f, 0.0f, 1.8571399450302124f, -69.46666717529297f, -23.28569984436035f));
// _0_33
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(8.0f, 8.5f);
generalPath.curveTo(3.8578644f, 8.5f, 0.5f, 9.395431f, 0.5f, 10.5f);
generalPath.lineTo(0.5f, 13.5f);
generalPath.curveTo(0.5f, 14.60457f, 3.8578641f, 15.5f, 8.0f, 15.5f);
generalPath.curveTo(12.142136f, 15.5f, 15.5f, 14.60457f, 15.5f, 13.5f);
generalPath.lineTo(15.5f, 10.5f);
generalPath.curveTo(15.5f, 9.395431f, 12.142136f, 8.5f, 8.0f, 8.5f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(0.5, 12.0), new Point2D.Double(15.5, 12.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(114, 159, 207, 255)) : new Color(114, 159, 207, 255)),((colorFilter != null) ? colorFilter.filter(new Color(32, 74, 135, 255)) : new Color(32, 74, 135, 255))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);

}

private void _paint1(Graphics2D g,float origAlpha) {
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -30.0f));
// _0_34
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(-68.09375f, 25.375f);
generalPath.curveTo(-68.34848f, 25.650661f, -68.5f, 25.923143f, -68.5f, 26.21875f);
generalPath.lineTo(-68.5f, 31.78125f);
generalPath.curveTo(-68.5f, 33.832596f, -62.00812f, 35.5f, -54.0f, 35.5f);
generalPath.curveTo(-45.99188f, 35.5f, -39.5f, 33.832596f, -39.5f, 31.78125f);
generalPath.lineTo(-39.5f, 26.21875f);
generalPath.curveTo(-39.5f, 25.923143f, -39.65152f, 25.650661f, -39.90625f, 25.375f);
generalPath.curveTo(-40.03947f, 25.478735f, -40.187702f, 25.5931f, -40.34375f, 25.6875f);
generalPath.curveTo(-41.14931f, 26.174826f, -42.249054f, 26.56218f, -43.59375f, 26.90625f);
generalPath.curveTo(-46.283146f, 27.594395f, -49.95468f, 28.0f, -54.0f, 28.0f);
generalPath.curveTo(-58.04532f, 28.0f, -61.716858f, 27.594395f, -64.40625f, 26.90625f);
generalPath.curveTo(-65.750946f, 26.56218f, -66.850685f, 26.174826f, -67.65625f, 25.6875f);
generalPath.curveTo(-67.812294f, 25.5931f, -67.96053f, 25.478735f, -68.09375f, 25.375f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(0.5, 12.0), new Point2D.Double(15.5, 12.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(203, 219, 237, 255)) : new Color(203, 219, 237, 255)),((colorFilter != null) ? colorFilter.filter(new Color(32, 74, 135, 255)) : new Color(32, 74, 135, 255))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.9333300590515137f, 0.0f, 0.0f, 1.8571399450302124f, -69.46666717529297f, 6.714300155639648f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -25.0f));
// _0_35
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(-54.0f, 18.0f);
generalPath.curveTo(-57.9673f, 18.0f, -61.594692f, 18.406336f, -64.15625f, 19.0625f);
generalPath.curveTo(-65.43703f, 19.390581f, -66.46219f, 19.754833f, -67.125f, 20.15625f);
generalPath.curveTo(-67.78781f, 20.557667f, -68.0f, 20.954205f, -68.0f, 21.21875f);
generalPath.lineTo(-68.0f, 26.78125f);
generalPath.curveTo(-68.0f, 27.045795f, -67.78781f, 27.442333f, -67.125f, 27.84375f);
generalPath.curveTo(-66.46219f, 28.245167f, -65.43703f, 28.609419f, -64.15625f, 28.9375f);
generalPath.curveTo(-61.594692f, 29.593664f, -57.9673f, 30.0f, -54.0f, 30.0f);
generalPath.curveTo(-50.0327f, 30.0f, -46.405308f, 29.593664f, -43.84375f, 28.9375f);
generalPath.curveTo(-42.56297f, 28.609419f, -41.53781f, 28.245167f, -40.875f, 27.84375f);
generalPath.curveTo(-40.21219f, 27.442333f, -40.0f, 27.045795f, -40.0f, 26.78125f);
generalPath.lineTo(-40.0f, 21.21875f);
generalPath.curveTo(-40.0f, 20.954205f, -40.21219f, 20.557667f, -40.875f, 20.15625f);
generalPath.curveTo(-41.53781f, 19.754833f, -42.56297f, 19.390581f, -43.84375f, 19.0625f);
generalPath.curveTo(-46.405308f, 18.406336f, -50.0327f, 18.0f, -54.0f, 18.0f);
generalPath.closePath();
generalPath.moveTo(-54.0f, 19.0f);
generalPath.curveTo(-50.106224f, 19.0f, -46.592964f, 19.375816f, -44.15625f, 20.0f);
generalPath.curveTo(-42.937893f, 20.312094f, -41.958458f, 20.715746f, -41.4375f, 21.03125f);
generalPath.curveTo(-41.046783f, 21.267878f, -40.996845f, 21.401358f, -41.0f, 21.34375f);
generalPath.lineTo(-41.0f, 26.65625f);
generalPath.curveTo(-40.9968f, 26.59864f, -41.04678f, 26.73212f, -41.4375f, 26.96875f);
generalPath.curveTo(-41.958458f, 27.284254f, -42.937893f, 27.687906f, -44.15625f, 28.0f);
generalPath.curveTo(-46.592964f, 28.624184f, -50.106224f, 29.0f, -54.0f, 29.0f);
generalPath.curveTo(-57.893776f, 29.0f, -61.407036f, 28.624184f, -63.84375f, 28.0f);
generalPath.curveTo(-65.06211f, 27.687906f, -66.04154f, 27.284254f, -66.5625f, 26.96875f);
generalPath.curveTo(-66.95322f, 26.732122f, -67.00315f, 26.598642f, -67.0f, 26.65625f);
generalPath.lineTo(-67.0f, 21.34375f);
generalPath.curveTo(-67.0032f, 21.40136f, -66.95322f, 21.26788f, -66.5625f, 21.03125f);
generalPath.curveTo(-66.04154f, 20.715746f, -65.06211f, 20.312094f, -63.84375f, 20.0f);
generalPath.curveTo(-61.407036f, 19.375816f, -57.893776f, 19.0f, -54.0f, 19.0f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(-68.03125, 24.0), new Point2D.Double(-39.96875, 24.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 153)) : new Color(255, 255, 255, 153)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 53)) : new Color(255, 255, 255, 53))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.9333300590515137f, 0.0f, 0.0f, 1.8571399450302124f, -69.46666717529297f, -23.28569984436035f));
// _0_36
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_36_0
paint = (colorFilter != null) ? colorFilter.filter(new Color(24, 56, 102, 255)) : new Color(24, 56, 102, 255);
stroke = new BasicStroke(0.5277452f,0,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(8.0f, 8.5f);
generalPath.curveTo(3.8578644f, 8.5f, 0.5f, 9.395431f, 0.5f, 10.5f);
generalPath.lineTo(0.5f, 13.5f);
generalPath.curveTo(0.5f, 14.60457f, 3.8578641f, 15.5f, 8.0f, 15.5f);
generalPath.curveTo(12.142136f, 15.5f, 15.5f, 14.60457f, 15.5f, 13.5f);
generalPath.lineTo(15.5f, 10.5f);
generalPath.curveTo(15.5f, 9.395431f, 12.142136f, 8.5f, 8.0f, 8.5f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -32.0f));
// _0_37
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(-66.625f, 28.09375f);
generalPath.curveTo(-66.92386f, 28.290142f, -67.0029f, 28.395983f, -67.0f, 28.34375f);
generalPath.lineTo(-67.0f, 29.0f);
generalPath.curveTo(-66.2876f, 29.330662f, -65.41365f, 29.616947f, -64.40625f, 29.875f);
generalPath.curveTo(-61.71985f, 30.563145f, -58.04082f, 30.96875f, -54.0f, 30.96875f);
generalPath.curveTo(-49.95918f, 30.96875f, -46.28015f, 30.563145f, -43.59375f, 29.875f);
generalPath.curveTo(-42.58635f, 29.616947f, -41.712406f, 29.330662f, -41.0f, 29.0f);
generalPath.lineTo(-41.0f, 28.34375f);
generalPath.curveTo(-40.9971f, 28.39598f, -41.07615f, 28.29014f, -41.375f, 28.09375f);
generalPath.curveTo(-42.011677f, 28.393154f, -42.847588f, 28.682325f, -43.84375f, 28.9375f);
generalPath.curveTo(-46.405308f, 29.593664f, -50.0327f, 30.03125f, -54.0f, 30.03125f);
generalPath.curveTo(-57.9673f, 30.03125f, -61.594692f, 29.593664f, -64.15625f, 28.9375f);
generalPath.curveTo(-65.15241f, 28.682325f, -65.98832f, 28.393154f, -66.625f, 28.09375f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(-69.0, 29.0), new Point2D.Double(-54.0, 29.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 204)) : new Color(255, 255, 255, 204)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 0)) : new Color(255, 255, 255, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.9333300590515137f, 0.0f, 0.0f, 1.8571399450302124f, -69.46666717529297f, -31.28569984436035f));
// _0_38
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(8.0f, 8.5f);
generalPath.curveTo(3.8578644f, 8.5f, 0.5f, 9.395431f, 0.5f, 10.5f);
generalPath.lineTo(0.5f, 13.5f);
generalPath.curveTo(0.5f, 14.60457f, 3.8578641f, 15.5f, 8.0f, 15.5f);
generalPath.curveTo(12.142136f, 15.5f, 15.5f, 14.60457f, 15.5f, 13.5f);
generalPath.lineTo(15.5f, 10.5f);
generalPath.curveTo(15.5f, 9.395431f, 12.142136f, 8.5f, 8.0f, 8.5f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(0.5, 12.0), new Point2D.Double(15.5, 12.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(114, 159, 207, 255)) : new Color(114, 159, 207, 255)),((colorFilter != null) ? colorFilter.filter(new Color(32, 74, 135, 255)) : new Color(32, 74, 135, 255))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -38.0f));
// _0_39
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(-68.09375f, 25.375f);
generalPath.curveTo(-68.34848f, 25.650661f, -68.5f, 25.923143f, -68.5f, 26.21875f);
generalPath.lineTo(-68.5f, 31.78125f);
generalPath.curveTo(-68.5f, 33.832596f, -62.00812f, 35.5f, -54.0f, 35.5f);
generalPath.curveTo(-45.99188f, 35.5f, -39.5f, 33.832596f, -39.5f, 31.78125f);
generalPath.lineTo(-39.5f, 26.21875f);
generalPath.curveTo(-39.5f, 25.923143f, -39.65152f, 25.650661f, -39.90625f, 25.375f);
generalPath.curveTo(-40.03947f, 25.478735f, -40.187702f, 25.5931f, -40.34375f, 25.6875f);
generalPath.curveTo(-41.14931f, 26.174826f, -42.249054f, 26.56218f, -43.59375f, 26.90625f);
generalPath.curveTo(-46.283146f, 27.594395f, -49.95468f, 28.0f, -54.0f, 28.0f);
generalPath.curveTo(-58.04532f, 28.0f, -61.716858f, 27.594395f, -64.40625f, 26.90625f);
generalPath.curveTo(-65.750946f, 26.56218f, -66.850685f, 26.174826f, -67.65625f, 25.6875f);
generalPath.curveTo(-67.812294f, 25.5931f, -67.96053f, 25.478735f, -68.09375f, 25.375f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(0.5, 12.0), new Point2D.Double(15.5, 12.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(203, 219, 237, 255)) : new Color(203, 219, 237, 255)),((colorFilter != null) ? colorFilter.filter(new Color(32, 74, 135, 255)) : new Color(32, 74, 135, 255))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.9333300590515137f, 0.0f, 0.0f, 1.8571399450302124f, -69.46666717529297f, 6.714300155639648f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -33.0f));
// _0_40
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(-54.0f, 18.0f);
generalPath.curveTo(-57.9673f, 18.0f, -61.594692f, 18.406336f, -64.15625f, 19.0625f);
generalPath.curveTo(-65.43703f, 19.390581f, -66.46219f, 19.754833f, -67.125f, 20.15625f);
generalPath.curveTo(-67.78781f, 20.557667f, -68.0f, 20.954205f, -68.0f, 21.21875f);
generalPath.lineTo(-68.0f, 26.78125f);
generalPath.curveTo(-68.0f, 27.045795f, -67.78781f, 27.442333f, -67.125f, 27.84375f);
generalPath.curveTo(-66.46219f, 28.245167f, -65.43703f, 28.609419f, -64.15625f, 28.9375f);
generalPath.curveTo(-61.594692f, 29.593664f, -57.9673f, 30.0f, -54.0f, 30.0f);
generalPath.curveTo(-50.0327f, 30.0f, -46.405308f, 29.593664f, -43.84375f, 28.9375f);
generalPath.curveTo(-42.56297f, 28.609419f, -41.53781f, 28.245167f, -40.875f, 27.84375f);
generalPath.curveTo(-40.21219f, 27.442333f, -40.0f, 27.045795f, -40.0f, 26.78125f);
generalPath.lineTo(-40.0f, 21.21875f);
generalPath.curveTo(-40.0f, 20.954205f, -40.21219f, 20.557667f, -40.875f, 20.15625f);
generalPath.curveTo(-41.53781f, 19.754833f, -42.56297f, 19.390581f, -43.84375f, 19.0625f);
generalPath.curveTo(-46.405308f, 18.406336f, -50.0327f, 18.0f, -54.0f, 18.0f);
generalPath.closePath();
generalPath.moveTo(-54.0f, 19.0f);
generalPath.curveTo(-50.106224f, 19.0f, -46.592964f, 19.375816f, -44.15625f, 20.0f);
generalPath.curveTo(-42.937893f, 20.312094f, -41.958458f, 20.715746f, -41.4375f, 21.03125f);
generalPath.curveTo(-41.046783f, 21.267878f, -40.996845f, 21.401358f, -41.0f, 21.34375f);
generalPath.lineTo(-41.0f, 26.65625f);
generalPath.curveTo(-40.9968f, 26.59864f, -41.04678f, 26.73212f, -41.4375f, 26.96875f);
generalPath.curveTo(-41.958458f, 27.284254f, -42.937893f, 27.687906f, -44.15625f, 28.0f);
generalPath.curveTo(-46.592964f, 28.624184f, -50.106224f, 29.0f, -54.0f, 29.0f);
generalPath.curveTo(-57.893776f, 29.0f, -61.407036f, 28.624184f, -63.84375f, 28.0f);
generalPath.curveTo(-65.06211f, 27.687906f, -66.04154f, 27.284254f, -66.5625f, 26.96875f);
generalPath.curveTo(-66.95322f, 26.732122f, -67.00315f, 26.598642f, -67.0f, 26.65625f);
generalPath.lineTo(-67.0f, 21.34375f);
generalPath.curveTo(-67.0032f, 21.40136f, -66.95322f, 21.26788f, -66.5625f, 21.03125f);
generalPath.curveTo(-66.04154f, 20.715746f, -65.06211f, 20.312094f, -63.84375f, 20.0f);
generalPath.curveTo(-61.407036f, 19.375816f, -57.893776f, 19.0f, -54.0f, 19.0f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(-68.03125, 24.0), new Point2D.Double(-39.96875, 24.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 153)) : new Color(255, 255, 255, 153)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 53)) : new Color(255, 255, 255, 53))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.9333300590515137f, 0.0f, 0.0f, 1.8571399450302124f, -69.46666717529297f, -31.28569984436035f));
// _0_41
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_41_0
paint = (colorFilter != null) ? colorFilter.filter(new Color(24, 56, 102, 255)) : new Color(24, 56, 102, 255);
stroke = new BasicStroke(0.5277452f,0,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(8.0f, 8.5f);
generalPath.curveTo(3.8578644f, 8.5f, 0.5f, 9.395431f, 0.5f, 10.5f);
generalPath.lineTo(0.5f, 13.5f);
generalPath.curveTo(0.5f, 14.60457f, 3.8578641f, 15.5f, 8.0f, 15.5f);
generalPath.curveTo(12.142136f, 15.5f, 15.5f, 14.60457f, 15.5f, 13.5f);
generalPath.lineTo(15.5f, 10.5f);
generalPath.curveTo(15.5f, 9.395431f, 12.142136f, 8.5f, 8.0f, 8.5f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -40.0f));
// _0_42
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(-66.625f, 28.09375f);
generalPath.curveTo(-66.92386f, 28.290142f, -67.0029f, 28.395983f, -67.0f, 28.34375f);
generalPath.lineTo(-67.0f, 29.0f);
generalPath.curveTo(-66.2876f, 29.330662f, -65.41365f, 29.616947f, -64.40625f, 29.875f);
generalPath.curveTo(-61.71985f, 30.563145f, -58.04082f, 30.96875f, -54.0f, 30.96875f);
generalPath.curveTo(-49.95918f, 30.96875f, -46.28015f, 30.563145f, -43.59375f, 29.875f);
generalPath.curveTo(-42.58635f, 29.616947f, -41.712406f, 29.330662f, -41.0f, 29.0f);
generalPath.lineTo(-41.0f, 28.34375f);
generalPath.curveTo(-40.9971f, 28.39598f, -41.07615f, 28.29014f, -41.375f, 28.09375f);
generalPath.curveTo(-42.011677f, 28.393154f, -42.847588f, 28.682325f, -43.84375f, 28.9375f);
generalPath.curveTo(-46.405308f, 29.593664f, -50.0327f, 30.03125f, -54.0f, 30.03125f);
generalPath.curveTo(-57.9673f, 30.03125f, -61.594692f, 29.593664f, -64.15625f, 28.9375f);
generalPath.curveTo(-65.15241f, 28.682325f, -65.98832f, 28.393154f, -66.625f, 28.09375f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(-69.0, 29.0), new Point2D.Double(-54.0, 29.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 204)) : new Color(255, 255, 255, 204)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 0)) : new Color(255, 255, 255, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0476200580596924f, 0.0f, 0.0f, 1.375f, 4.857142925262451f, -1.75f));
// _0_43
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(-81.0f, 6.0f);
generalPath.curveTo(-80.96635f, 7.433324f, -84.96155f, 8.760521f, -91.47279f, 9.479039f);
generalPath.curveTo(-97.98404f, 10.197558f, -106.01596f, 10.197558f, -112.52721f, 9.479039f);
generalPath.curveTo(-119.03845f, 8.760521f, -123.03365f, 7.433324f, -123.0f, 6.0f);
generalPath.curveTo(-123.03365f, 4.566676f, -119.03845f, 3.239479f, -112.52721f, 2.5209603f);
generalPath.curveTo(-106.01596f, 1.8024417f, -97.98404f, 1.8024417f, -91.47279f, 2.5209603f);
generalPath.curveTo(-84.96155f, 3.239479f, -80.96635f, 4.566676f, -81.0f, 6.0f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(46, 52, 54, 204)) : new Color(46, 52, 54, 204);
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(2.733330011367798f, 0.0f, 0.0f, 2.571429967880249f, -123.86778259277344f, -29.357099533081055f));
// _0_44
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(8.0f, 8.5f);
generalPath.curveTo(3.8578644f, 8.5f, 0.5f, 9.395431f, 0.5f, 10.5f);
generalPath.lineTo(0.5f, 13.5f);
generalPath.curveTo(0.5f, 14.60457f, 3.8578641f, 15.5f, 8.0f, 15.5f);
generalPath.curveTo(12.142136f, 15.5f, 15.5f, 14.60457f, 15.5f, 13.5f);
generalPath.lineTo(15.5f, 10.5f);
generalPath.curveTo(15.5f, 9.395431f, 12.142136f, 8.5f, 8.0f, 8.5f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(0.5, 12.0), new Point2D.Double(15.5, 12.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(114, 159, 207, 255)) : new Color(114, 159, 207, 255)),((colorFilter != null) ? colorFilter.filter(new Color(32, 74, 135, 255)) : new Color(32, 74, 135, 255))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -23.0f));
// _0_45
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(-122.34375f, 19.5f);
generalPath.curveTo(-122.4484f, 19.710567f, -122.5f, 19.938572f, -122.5f, 20.15625f);
generalPath.lineTo(-122.5f, 28.34375f);
generalPath.curveTo(-122.5f, 31.184072f, -113.32184f, 33.5f, -102.0f, 33.5f);
generalPath.curveTo(-90.67816f, 33.5f, -81.5f, 31.184072f, -81.5f, 28.34375f);
generalPath.lineTo(-81.5f, 20.15625f);
generalPath.curveTo(-81.5f, 19.938572f, -81.5516f, 19.710567f, -81.65625f, 19.5f);
generalPath.curveTo(-82.91709f, 22.036959f, -91.54585f, 24.0f, -102.0f, 24.0f);
generalPath.curveTo(-112.45415f, 24.0f, -121.08291f, 22.036959f, -122.34375f, 19.5f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(-122.5, 27.0), new Point2D.Double(-81.5, 27.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(203, 219, 237, 255)) : new Color(203, 219, 237, 255)),((colorFilter != null) ? colorFilter.filter(new Color(32, 74, 135, 255)) : new Color(32, 74, 135, 255))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -22.0f));
// _0_46
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(-102.0f, 15.0f);
generalPath.curveTo(-107.62377f, 15.0f, -112.69706f, 15.58515f, -116.34375f, 16.5f);
generalPath.curveTo(-118.1671f, 16.957424f, -119.65931f, 17.458477f, -120.625f, 18.03125f);
generalPath.curveTo(-121.59069f, 18.604023f, -122.0f, 19.191883f, -122.0f, 19.65625f);
generalPath.lineTo(-122.0f, 27.34375f);
generalPath.curveTo(-122.0f, 27.808117f, -121.59069f, 28.395977f, -120.625f, 28.96875f);
generalPath.curveTo(-119.65931f, 29.541523f, -118.1671f, 30.042576f, -116.34375f, 30.5f);
generalPath.curveTo(-112.69706f, 31.41485f, -107.62377f, 32.0f, -102.0f, 32.0f);
generalPath.curveTo(-96.37623f, 32.0f, -91.30294f, 31.41485f, -87.65625f, 30.5f);
generalPath.curveTo(-85.8329f, 30.042576f, -84.34069f, 29.541523f, -83.375f, 28.96875f);
generalPath.curveTo(-82.40931f, 28.395977f, -82.0f, 27.808117f, -82.0f, 27.34375f);
generalPath.lineTo(-82.0f, 19.65625f);
generalPath.curveTo(-82.0f, 19.191883f, -82.40931f, 18.604023f, -83.375f, 18.03125f);
generalPath.curveTo(-84.34069f, 17.458477f, -85.8329f, 16.957424f, -87.65625f, 16.5f);
generalPath.curveTo(-91.30294f, 15.58515f, -96.37623f, 15.0f, -102.0f, 15.0f);
generalPath.closePath();
generalPath.moveTo(-102.0f, 16.0f);
generalPath.curveTo(-96.45996f, 16.0f, -91.45205f, 16.487268f, -87.9375f, 17.375f);
generalPath.curveTo(-86.18022f, 17.818867f, -84.820946f, 18.322245f, -84.0f, 18.8125f);
generalPath.curveTo(-83.179054f, 19.302755f, -83.0f, 19.6522f, -83.0f, 19.625f);
generalPath.lineTo(-83.0f, 27.375f);
generalPath.curveTo(-83.0f, 27.34781f, -83.17905f, 27.697245f, -84.0f, 28.1875f);
generalPath.curveTo(-84.820946f, 28.677755f, -86.18022f, 29.181133f, -87.9375f, 29.625f);
generalPath.curveTo(-91.45205f, 30.51273f, -96.45996f, 31.0f, -102.0f, 31.0f);
generalPath.curveTo(-107.54004f, 31.0f, -112.54795f, 30.51273f, -116.0625f, 29.625f);
generalPath.curveTo(-117.81978f, 29.181133f, -119.17905f, 28.677755f, -120.0f, 28.1875f);
generalPath.curveTo(-120.82095f, 27.697245f, -121.0f, 27.3478f, -121.0f, 27.375f);
generalPath.lineTo(-121.0f, 19.625f);
generalPath.curveTo(-121.0f, 19.65219f, -120.82095f, 19.302755f, -120.0f, 18.8125f);
generalPath.curveTo(-119.17905f, 18.322245f, -117.81978f, 17.818867f, -116.0625f, 17.375f);
generalPath.curveTo(-112.54795f, 16.487268f, -107.54004f, 16.0f, -102.0f, 16.0f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(-122.0, 23.5), new Point2D.Double(-82.0, 23.5), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 153)) : new Color(255, 255, 255, 153)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 53)) : new Color(255, 255, 255, 53))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(2.733330011367798f, 0.0f, 0.0f, 2.571429967880249f, -123.86778259277344f, -29.357099533081055f));
// _0_47
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_47_0
paint = (colorFilter != null) ? colorFilter.filter(new Color(25, 57, 101, 255)) : new Color(25, 57, 101, 255);
stroke = new BasicStroke(0.37719545f,0,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(8.0f, 8.5f);
generalPath.curveTo(3.8578644f, 8.5f, 0.5f, 9.395431f, 0.5f, 10.5f);
generalPath.lineTo(0.5f, 13.5f);
generalPath.curveTo(0.5f, 14.60457f, 3.8578641f, 15.5f, 8.0f, 15.5f);
generalPath.curveTo(12.142136f, 15.5f, 15.5f, 14.60457f, 15.5f, 13.5f);
generalPath.lineTo(15.5f, 10.5f);
generalPath.curveTo(15.5f, 9.395431f, 12.142136f, 8.5f, 8.0f, 8.5f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -30.0f));
// _0_48
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(-121.0f, 27.78125f);
generalPath.lineTo(-121.0f, 28.78125f);
generalPath.curveTo(-117.95001f, 30.661516f, -110.59047f, 32.0f, -102.0f, 32.0f);
generalPath.curveTo(-93.40953f, 32.0f, -86.04999f, 30.661516f, -83.0f, 28.78125f);
generalPath.lineTo(-83.0f, 27.78125f);
generalPath.curveTo(-86.049995f, 29.661516f, -93.40953f, 31.0f, -102.0f, 31.0f);
generalPath.curveTo(-110.59047f, 31.0f, -117.95001f, 29.661516f, -121.0f, 27.78125f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(-121.0, 30.0), new Point2D.Double(-102.0, 30.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 204)) : new Color(255, 255, 255, 204)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 0)) : new Color(255, 255, 255, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(2.733330011367798f, 0.0f, 0.0f, 2.571429967880249f, -123.86778259277344f, -41.35710144042969f));
// _0_49
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(8.0f, 8.5f);
generalPath.curveTo(3.8578644f, 8.5f, 0.5f, 9.395431f, 0.5f, 10.5f);
generalPath.lineTo(0.5f, 13.5f);
generalPath.curveTo(0.5f, 14.60457f, 3.8578641f, 15.5f, 8.0f, 15.5f);
generalPath.curveTo(12.142136f, 15.5f, 15.5f, 14.60457f, 15.5f, 13.5f);
generalPath.lineTo(15.5f, 10.5f);
generalPath.curveTo(15.5f, 9.395431f, 12.142136f, 8.5f, 8.0f, 8.5f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(0.5, 12.0), new Point2D.Double(15.5, 12.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(114, 159, 207, 255)) : new Color(114, 159, 207, 255)),((colorFilter != null) ? colorFilter.filter(new Color(32, 74, 135, 255)) : new Color(32, 74, 135, 255))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -35.0f));
// _0_50
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(-122.34375f, 19.5f);
generalPath.curveTo(-122.4484f, 19.710567f, -122.5f, 19.938572f, -122.5f, 20.15625f);
generalPath.lineTo(-122.5f, 28.34375f);
generalPath.curveTo(-122.5f, 31.184072f, -113.32184f, 33.5f, -102.0f, 33.5f);
generalPath.curveTo(-90.67816f, 33.5f, -81.5f, 31.184072f, -81.5f, 28.34375f);
generalPath.lineTo(-81.5f, 20.15625f);
generalPath.curveTo(-81.5f, 19.938572f, -81.5516f, 19.710567f, -81.65625f, 19.5f);
generalPath.curveTo(-82.91709f, 22.036959f, -91.54585f, 24.0f, -102.0f, 24.0f);
generalPath.curveTo(-112.45415f, 24.0f, -121.08291f, 22.036959f, -122.34375f, 19.5f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(-122.5, 27.0), new Point2D.Double(-81.5, 27.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(203, 219, 237, 255)) : new Color(203, 219, 237, 255)),((colorFilter != null) ? colorFilter.filter(new Color(32, 74, 135, 255)) : new Color(32, 74, 135, 255))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -34.0f));
// _0_51
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(-102.0f, 15.0f);
generalPath.curveTo(-107.62377f, 15.0f, -112.69706f, 15.58515f, -116.34375f, 16.5f);
generalPath.curveTo(-118.1671f, 16.957424f, -119.65931f, 17.458477f, -120.625f, 18.03125f);
generalPath.curveTo(-121.59069f, 18.604023f, -122.0f, 19.191883f, -122.0f, 19.65625f);
generalPath.lineTo(-122.0f, 27.34375f);
generalPath.curveTo(-122.0f, 27.808117f, -121.59069f, 28.395977f, -120.625f, 28.96875f);
generalPath.curveTo(-119.65931f, 29.541523f, -118.1671f, 30.042576f, -116.34375f, 30.5f);
generalPath.curveTo(-112.69706f, 31.41485f, -107.62377f, 32.0f, -102.0f, 32.0f);
generalPath.curveTo(-96.37623f, 32.0f, -91.30294f, 31.41485f, -87.65625f, 30.5f);
generalPath.curveTo(-85.8329f, 30.042576f, -84.34069f, 29.541523f, -83.375f, 28.96875f);
generalPath.curveTo(-82.40931f, 28.395977f, -82.0f, 27.808117f, -82.0f, 27.34375f);
generalPath.lineTo(-82.0f, 19.65625f);
generalPath.curveTo(-82.0f, 19.191883f, -82.40931f, 18.604023f, -83.375f, 18.03125f);
generalPath.curveTo(-84.34069f, 17.458477f, -85.8329f, 16.957424f, -87.65625f, 16.5f);
generalPath.curveTo(-91.30294f, 15.58515f, -96.37623f, 15.0f, -102.0f, 15.0f);
generalPath.closePath();
generalPath.moveTo(-102.0f, 16.0f);
generalPath.curveTo(-96.45996f, 16.0f, -91.45205f, 16.487268f, -87.9375f, 17.375f);
generalPath.curveTo(-86.18022f, 17.818867f, -84.820946f, 18.322245f, -84.0f, 18.8125f);
generalPath.curveTo(-83.179054f, 19.302755f, -83.0f, 19.6522f, -83.0f, 19.625f);
generalPath.lineTo(-83.0f, 27.375f);
generalPath.curveTo(-83.0f, 27.34781f, -83.17905f, 27.697245f, -84.0f, 28.1875f);
generalPath.curveTo(-84.820946f, 28.677755f, -86.18022f, 29.181133f, -87.9375f, 29.625f);
generalPath.curveTo(-91.45205f, 30.51273f, -96.45996f, 31.0f, -102.0f, 31.0f);
generalPath.curveTo(-107.54004f, 31.0f, -112.54795f, 30.51273f, -116.0625f, 29.625f);
generalPath.curveTo(-117.81978f, 29.181133f, -119.17905f, 28.677755f, -120.0f, 28.1875f);
generalPath.curveTo(-120.82095f, 27.697245f, -121.0f, 27.3478f, -121.0f, 27.375f);
generalPath.lineTo(-121.0f, 19.625f);
generalPath.curveTo(-121.0f, 19.65219f, -120.82095f, 19.302755f, -120.0f, 18.8125f);
generalPath.curveTo(-119.17905f, 18.322245f, -117.81978f, 17.818867f, -116.0625f, 17.375f);
generalPath.curveTo(-112.54795f, 16.487268f, -107.54004f, 16.0f, -102.0f, 16.0f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(-122.0, 23.5), new Point2D.Double(-82.0, 23.5), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 153)) : new Color(255, 255, 255, 153)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 53)) : new Color(255, 255, 255, 53))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(2.733330011367798f, 0.0f, 0.0f, 2.571429967880249f, -123.86778259277344f, -41.35710144042969f));
// _0_52
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_52_0
paint = (colorFilter != null) ? colorFilter.filter(new Color(25, 57, 101, 255)) : new Color(25, 57, 101, 255);
stroke = new BasicStroke(0.37719545f,0,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(8.0f, 8.5f);
generalPath.curveTo(3.8578644f, 8.5f, 0.5f, 9.395431f, 0.5f, 10.5f);
generalPath.lineTo(0.5f, 13.5f);
generalPath.curveTo(0.5f, 14.60457f, 3.8578641f, 15.5f, 8.0f, 15.5f);
generalPath.curveTo(12.142136f, 15.5f, 15.5f, 14.60457f, 15.5f, 13.5f);
generalPath.lineTo(15.5f, 10.5f);
generalPath.curveTo(15.5f, 9.395431f, 12.142136f, 8.5f, 8.0f, 8.5f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -42.0f));
// _0_53
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(-121.0f, 27.78125f);
generalPath.lineTo(-121.0f, 28.78125f);
generalPath.curveTo(-117.95001f, 30.661516f, -110.59047f, 32.0f, -102.0f, 32.0f);
generalPath.curveTo(-93.40953f, 32.0f, -86.04999f, 30.661516f, -83.0f, 28.78125f);
generalPath.lineTo(-83.0f, 27.78125f);
generalPath.curveTo(-86.049995f, 29.661516f, -93.40953f, 31.0f, -102.0f, 31.0f);
generalPath.curveTo(-110.59047f, 31.0f, -117.95001f, 29.661516f, -121.0f, 27.78125f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(-121.0, 30.0), new Point2D.Double(-102.0, 30.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 204)) : new Color(255, 255, 255, 204)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 0)) : new Color(255, 255, 255, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(2.733330011367798f, 0.0f, 0.0f, 2.571429967880249f, -123.86778259277344f, -53.35710144042969f));
// _0_54
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(8.0f, 8.5f);
generalPath.curveTo(3.8578644f, 8.5f, 0.5f, 9.395431f, 0.5f, 10.5f);
generalPath.lineTo(0.5f, 13.5f);
generalPath.curveTo(0.5f, 14.60457f, 3.8578641f, 15.5f, 8.0f, 15.5f);
generalPath.curveTo(12.142136f, 15.5f, 15.5f, 14.60457f, 15.5f, 13.5f);
generalPath.lineTo(15.5f, 10.5f);
generalPath.curveTo(15.5f, 9.395431f, 12.142136f, 8.5f, 8.0f, 8.5f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(0.5, 12.0), new Point2D.Double(15.5, 12.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(114, 159, 207, 255)) : new Color(114, 159, 207, 255)),((colorFilter != null) ? colorFilter.filter(new Color(32, 74, 135, 255)) : new Color(32, 74, 135, 255))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -47.0f));
// _0_55
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(-122.34375f, 19.5f);
generalPath.curveTo(-122.4484f, 19.710567f, -122.5f, 19.938572f, -122.5f, 20.15625f);
generalPath.lineTo(-122.5f, 28.34375f);
generalPath.curveTo(-122.5f, 31.184072f, -113.32184f, 33.5f, -102.0f, 33.5f);
generalPath.curveTo(-90.67816f, 33.5f, -81.5f, 31.184072f, -81.5f, 28.34375f);
generalPath.lineTo(-81.5f, 20.15625f);
generalPath.curveTo(-81.5f, 19.938572f, -81.5516f, 19.710567f, -81.65625f, 19.5f);
generalPath.curveTo(-82.91709f, 22.036959f, -91.54585f, 24.0f, -102.0f, 24.0f);
generalPath.curveTo(-112.45415f, 24.0f, -121.08291f, 22.036959f, -122.34375f, 19.5f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(-122.5, 27.0), new Point2D.Double(-81.5, 27.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(203, 219, 237, 255)) : new Color(203, 219, 237, 255)),((colorFilter != null) ? colorFilter.filter(new Color(32, 74, 135, 255)) : new Color(32, 74, 135, 255))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -46.0f));
// _0_56
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(-102.0f, 15.0f);
generalPath.curveTo(-107.62377f, 15.0f, -112.69706f, 15.58515f, -116.34375f, 16.5f);
generalPath.curveTo(-118.1671f, 16.957424f, -119.65931f, 17.458477f, -120.625f, 18.03125f);
generalPath.curveTo(-121.59069f, 18.604023f, -122.0f, 19.191883f, -122.0f, 19.65625f);
generalPath.lineTo(-122.0f, 27.34375f);
generalPath.curveTo(-122.0f, 27.808117f, -121.59069f, 28.395977f, -120.625f, 28.96875f);
generalPath.curveTo(-119.65931f, 29.541523f, -118.1671f, 30.042576f, -116.34375f, 30.5f);
generalPath.curveTo(-112.69706f, 31.41485f, -107.62377f, 32.0f, -102.0f, 32.0f);
generalPath.curveTo(-96.37623f, 32.0f, -91.30294f, 31.41485f, -87.65625f, 30.5f);
generalPath.curveTo(-85.8329f, 30.042576f, -84.34069f, 29.541523f, -83.375f, 28.96875f);
generalPath.curveTo(-82.40931f, 28.395977f, -82.0f, 27.808117f, -82.0f, 27.34375f);
generalPath.lineTo(-82.0f, 19.65625f);
generalPath.curveTo(-82.0f, 19.191883f, -82.40931f, 18.604023f, -83.375f, 18.03125f);
generalPath.curveTo(-84.34069f, 17.458477f, -85.8329f, 16.957424f, -87.65625f, 16.5f);
generalPath.curveTo(-91.30294f, 15.58515f, -96.37623f, 15.0f, -102.0f, 15.0f);
generalPath.closePath();
generalPath.moveTo(-102.0f, 16.0f);
generalPath.curveTo(-96.45996f, 16.0f, -91.45205f, 16.487268f, -87.9375f, 17.375f);
generalPath.curveTo(-86.18022f, 17.818867f, -84.820946f, 18.322245f, -84.0f, 18.8125f);
generalPath.curveTo(-83.179054f, 19.302755f, -83.0f, 19.6522f, -83.0f, 19.625f);
generalPath.lineTo(-83.0f, 27.375f);
generalPath.curveTo(-83.0f, 27.34781f, -83.17905f, 27.697245f, -84.0f, 28.1875f);
generalPath.curveTo(-84.820946f, 28.677755f, -86.18022f, 29.181133f, -87.9375f, 29.625f);
generalPath.curveTo(-91.45205f, 30.51273f, -96.45996f, 31.0f, -102.0f, 31.0f);
generalPath.curveTo(-107.54004f, 31.0f, -112.54795f, 30.51273f, -116.0625f, 29.625f);
generalPath.curveTo(-117.81978f, 29.181133f, -119.17905f, 28.677755f, -120.0f, 28.1875f);
generalPath.curveTo(-120.82095f, 27.697245f, -121.0f, 27.3478f, -121.0f, 27.375f);
generalPath.lineTo(-121.0f, 19.625f);
generalPath.curveTo(-121.0f, 19.65219f, -120.82095f, 19.302755f, -120.0f, 18.8125f);
generalPath.curveTo(-119.17905f, 18.322245f, -117.81978f, 17.818867f, -116.0625f, 17.375f);
generalPath.curveTo(-112.54795f, 16.487268f, -107.54004f, 16.0f, -102.0f, 16.0f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(-122.0, 23.5), new Point2D.Double(-82.0, 23.5), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 153)) : new Color(255, 255, 255, 153)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 53)) : new Color(255, 255, 255, 53))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(2.733330011367798f, 0.0f, 0.0f, 2.571429967880249f, -123.86778259277344f, -53.35710144042969f));
// _0_57
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_57_0
paint = (colorFilter != null) ? colorFilter.filter(new Color(25, 57, 101, 255)) : new Color(25, 57, 101, 255);
stroke = new BasicStroke(0.37719545f,0,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(8.0f, 8.5f);
generalPath.curveTo(3.8578644f, 8.5f, 0.5f, 9.395431f, 0.5f, 10.5f);
generalPath.lineTo(0.5f, 13.5f);
generalPath.curveTo(0.5f, 14.60457f, 3.8578641f, 15.5f, 8.0f, 15.5f);
generalPath.curveTo(12.142136f, 15.5f, 15.5f, 14.60457f, 15.5f, 13.5f);
generalPath.lineTo(15.5f, 10.5f);
generalPath.curveTo(15.5f, 9.395431f, 12.142136f, 8.5f, 8.0f, 8.5f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -54.0f));
// _0_58
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(-121.0f, 27.78125f);
generalPath.lineTo(-121.0f, 28.78125f);
generalPath.curveTo(-117.95001f, 30.661516f, -110.59047f, 32.0f, -102.0f, 32.0f);
generalPath.curveTo(-93.40953f, 32.0f, -86.04999f, 30.661516f, -83.0f, 28.78125f);
generalPath.lineTo(-83.0f, 27.78125f);
generalPath.curveTo(-86.049995f, 29.661516f, -93.40953f, 31.0f, -102.0f, 31.0f);
generalPath.curveTo(-110.59047f, 31.0f, -117.95001f, 29.661516f, -121.0f, 27.78125f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(-121.0, 30.0), new Point2D.Double(-102.0, 30.0), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 204)) : new Color(255, 255, 255, 204)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 0)) : new Color(255, 255, 255, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
g.setPaint(paint);
g.fill(shape);
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
_paint1(g, origAlpha);


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
        return 0.0;
    }

    /**
     * Returns the Y of the bounding box of the original SVG image.
     * 
     * @return The Y of the bounding box of the original SVG image.
     */
    public static double getOrigY() {
        return 0.0;
    }

	/**
	 * Returns the width of the bounding box of the original SVG image.
	 * 
	 * @return The width of the bounding box of the original SVG image.
	 */
	public static double getOrigWidth() {
		return 16.0;
	}

	/**
	 * Returns the height of the bounding box of the original SVG image.
	 * 
	 * @return The height of the bounding box of the original SVG image.
	 */
	public static double getOrigHeight() {
		return 16.0;
	}

	/** The current width of this icon. */
	private int width;

    /** The current height of this icon. */
	private int height;

	/**
	 * Creates a new transcoded SVG image. This is marked as private to indicate that app
	 * code should be using the {@link #of(int, int)} method to obtain a pre-configured instance.
	 */
	private TangoRApplications_database() {
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
       TangoRApplications_database base = new TangoRApplications_database();
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
       TangoRApplications_database base = new TangoRApplications_database();
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
        return TangoRApplications_database::new;
    }
}

