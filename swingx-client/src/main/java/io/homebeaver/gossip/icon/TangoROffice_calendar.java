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
public class TangoROffice_calendar implements RadianceIcon {
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
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(0.02294846996665001f, 0.0f, 0.0f, 0.022768119350075722f, 44.7558708190918f, 36.74253845214844f));
// _0_0_0
g.setComposite(AlphaComposite.getInstance(3, 0.40206185f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_0
shape = new Rectangle2D.Double(-1559.2523193359375, -150.6968536376953, 1339.633544921875, 478.357177734375);
paint = new LinearGradientPaint(new Point2D.Double(302.8571472167969, 366.64788818359375), new Point2D.Double(302.8571472167969, 609.5050659179688), new float[] {0.0f,0.5f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 0)) : new Color(0, 0, 0, 0)),((colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255)),((colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 0)) : new Color(0, 0, 0, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.7743890285491943f, 0.0f, 0.0f, 1.9697060585021973f, -1892.178955078125f, -872.8853759765625f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 0.40206185f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(-219.61876f, -150.68037f);
generalPath.curveTo(-219.61876f, -150.68037f, -219.61876f, 327.65042f, -219.61876f, 327.65042f);
generalPath.curveTo(-76.74459f, 328.55087f, 125.78146f, 220.48074f, 125.78138f, 88.45424f);
generalPath.curveTo(125.78138f, -43.572304f, -33.655437f, -150.68036f, -219.61876f, -150.68037f);
generalPath.closePath();
shape = generalPath;
paint = new RadialGradientPaint(new Point2D.Double(605.7142944335938, 486.64788818359375), 117.14286f, new Point2D.Double(605.7142944335938, 486.64788818359375), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255)),((colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 0)) : new Color(0, 0, 0, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.7743890285491943f, 0.0f, 0.0f, 1.9697060585021973f, -1891.633056640625f, -872.8853759765625f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 0.40206185f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_2
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(-1559.2523f, -150.68037f);
generalPath.curveTo(-1559.2523f, -150.68037f, -1559.2523f, 327.65042f, -1559.2523f, 327.65042f);
generalPath.curveTo(-1702.1265f, 328.55087f, -1904.6525f, 220.48074f, -1904.6525f, 88.45424f);
generalPath.curveTo(-1904.6525f, -43.572304f, -1745.2157f, -150.68036f, -1559.2523f, -150.68037f);
generalPath.closePath();
shape = generalPath;
paint = new RadialGradientPaint(new Point2D.Double(605.7142944335938, 486.64788818359375), 117.14286f, new Point2D.Double(605.7142944335938, 486.64788818359375), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255)),((colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 0)) : new Color(0, 0, 0, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-2.7743890285491943f, 0.0f, 0.0f, 1.9697060585021973f, 112.76229858398438f, -872.8853759765625f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(4.857143f, 38.42857f);
generalPath.curveTo(4.642857f, 39.42857f, 5.464286f, 40.464287f, 6.821429f, 40.42857f);
generalPath.lineTo(43.0f, 40.42857f);
generalPath.curveTo(44.285713f, 40.392857f, 44.714287f, 39.214287f, 44.428574f, 38.25f);
generalPath.lineTo(36.57143f, 9.428572f);
generalPath.lineTo(10.571429f, 9.428572f);
generalPath.lineTo(4.857143f, 38.42857f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(167, 167, 167, 255)) : new Color(167, 167, 167, 255);
g.setPaint(paint);
g.fill(shape);
paint = (colorFilter != null) ? colorFilter.filter(new Color(101, 101, 101, 255)) : new Color(101, 101, 101, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(4.857143f, 38.42857f);
generalPath.curveTo(4.642857f, 39.42857f, 5.464286f, 40.464287f, 6.821429f, 40.42857f);
generalPath.lineTo(43.0f, 40.42857f);
generalPath.curveTo(44.285713f, 40.392857f, 44.714287f, 39.214287f, 44.428574f, 38.25f);
generalPath.lineTo(36.57143f, 9.428572f);
generalPath.lineTo(10.571429f, 9.428572f);
generalPath.lineTo(4.857143f, 38.42857f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 0.10857142f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(9.162504f, 30.806458f);
generalPath.lineTo(39.395096f, 30.806458f);
generalPath.curveTo(40.252296f, 30.806458f, 40.94239f, 31.478745f, 40.94239f, 32.313824f);
generalPath.lineTo(42.26958f, 37.11476f);
generalPath.curveTo(42.26958f, 37.94984f, 41.579487f, 38.622128f, 40.722282f, 38.622128f);
generalPath.lineTo(8.277712f, 38.622128f);
generalPath.curveTo(7.420509f, 38.622128f, 6.7304144f, 37.94984f, 6.7304144f, 37.11476f);
generalPath.lineTo(7.6152067f, 32.313824f);
generalPath.curveTo(7.6152067f, 31.478745f, 8.305302f, 30.806458f, 9.162504f, 30.806458f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255);
g.setPaint(paint);
g.fill(shape);
paint = (colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255);
stroke = new BasicStroke(0.9999996f,0,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(9.162504f, 30.806458f);
generalPath.lineTo(39.395096f, 30.806458f);
generalPath.curveTo(40.252296f, 30.806458f, 40.94239f, 31.478745f, 40.94239f, 32.313824f);
generalPath.lineTo(42.26958f, 37.11476f);
generalPath.curveTo(42.26958f, 37.94984f, 41.579487f, 38.622128f, 40.722282f, 38.622128f);
generalPath.lineTo(8.277712f, 38.622128f);
generalPath.curveTo(7.420509f, 38.622128f, 6.7304144f, 37.94984f, 6.7304144f, 37.11476f);
generalPath.lineTo(7.6152067f, 32.313824f);
generalPath.curveTo(7.6152067f, 31.478745f, 8.305302f, 30.806458f, 9.162504f, 30.806458f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_3
paint = (colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 88)) : new Color(255, 255, 255, 88);
stroke = new BasicStroke(0.9999997f,0,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(6.0478435f, 37.80557f);
generalPath.curveTo(5.8479824f, 38.738255f, 5.649196f, 39.385227f, 6.522763f, 39.385227f);
generalPath.lineTo(42.83744f, 39.385227f);
generalPath.curveTo(43.753494f, 39.385227f, 43.579185f, 38.64554f, 43.312706f, 37.746162f);
generalPath.lineTo(35.770172f, 10.471961f);
generalPath.lineTo(11.520336f, 10.471961f);
generalPath.lineTo(6.0478435f, 37.80557f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_4
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(9.641802f, 29.928574f);
generalPath.lineTo(38.929626f, 29.928574f);
generalPath.curveTo(39.76004f, 29.928574f, 40.42857f, 30.579851f, 40.42857f, 31.388836f);
generalPath.lineTo(41.714283f, 36.03974f);
generalPath.curveTo(41.714283f, 36.848724f, 41.045753f, 37.500004f, 40.21534f, 37.500004f);
generalPath.lineTo(8.784659f, 37.500004f);
generalPath.curveTo(7.954244f, 37.500004f, 7.2857146f, 36.848724f, 7.2857146f, 36.03974f);
generalPath.lineTo(8.142858f, 31.388836f);
generalPath.curveTo(8.142858f, 30.579851f, 8.811387f, 29.928574f, 9.641802f, 29.928574f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(197, 197, 197, 255)) : new Color(197, 197, 197, 255);
g.setPaint(paint);
g.fill(shape);
paint = (colorFilter != null) ? colorFilter.filter(new Color(105, 105, 105, 255)) : new Color(105, 105, 105, 255);
stroke = new BasicStroke(0.99999964f,0,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(9.641802f, 29.928574f);
generalPath.lineTo(38.929626f, 29.928574f);
generalPath.curveTo(39.76004f, 29.928574f, 40.42857f, 30.579851f, 40.42857f, 31.388836f);
generalPath.lineTo(41.714283f, 36.03974f);
generalPath.curveTo(41.714283f, 36.848724f, 41.045753f, 37.500004f, 40.21534f, 37.500004f);
generalPath.lineTo(8.784659f, 37.500004f);
generalPath.curveTo(7.954244f, 37.500004f, 7.2857146f, 36.848724f, 7.2857146f, 36.03974f);
generalPath.lineTo(8.142858f, 31.388836f);
generalPath.curveTo(8.142858f, 30.579851f, 8.811387f, 29.928574f, 9.641802f, 29.928574f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_5
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(9.641802f, 27.785717f);
generalPath.lineTo(38.929626f, 27.785717f);
generalPath.curveTo(39.76004f, 27.785717f, 40.42857f, 28.436995f, 40.42857f, 29.245977f);
generalPath.lineTo(41.714283f, 33.896885f);
generalPath.curveTo(41.714283f, 34.705868f, 41.045753f, 35.357147f, 40.21534f, 35.357147f);
generalPath.lineTo(8.784659f, 35.357147f);
generalPath.curveTo(7.954244f, 35.357147f, 7.2857146f, 34.705868f, 7.2857146f, 33.896885f);
generalPath.lineTo(8.142858f, 29.245977f);
generalPath.curveTo(8.142858f, 28.436995f, 8.811387f, 27.785717f, 9.641802f, 27.785717f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(6.785715103149414, 30.78571319580078), new Point2D.Double(42.21428298950195, 30.78571319580078), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(226, 226, 226, 255)) : new Color(226, 226, 226, 255)),((colorFilter != null) ? colorFilter.filter(new Color(159, 159, 159, 255)) : new Color(159, 159, 159, 255))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.7857180237770081f));
g.setPaint(paint);
g.fill(shape);
paint = (colorFilter != null) ? colorFilter.filter(new Color(105, 105, 105, 255)) : new Color(105, 105, 105, 255);
stroke = new BasicStroke(0.99999964f,0,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(9.641802f, 27.785717f);
generalPath.lineTo(38.929626f, 27.785717f);
generalPath.curveTo(39.76004f, 27.785717f, 40.42857f, 28.436995f, 40.42857f, 29.245977f);
generalPath.lineTo(41.714283f, 33.896885f);
generalPath.curveTo(41.714283f, 34.705868f, 41.045753f, 35.357147f, 40.21534f, 35.357147f);
generalPath.lineTo(8.784659f, 35.357147f);
generalPath.curveTo(7.954244f, 35.357147f, 7.2857146f, 34.705868f, 7.2857146f, 33.896885f);
generalPath.lineTo(8.142858f, 29.245977f);
generalPath.curveTo(8.142858f, 28.436995f, 8.811387f, 27.785717f, 9.641802f, 27.785717f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(0.9425489902496338f, 0.0f, 0.0f, 0.9425489902496338f, -0.22261899709701538f, 1.8558599948883057f));
// _0_0_6
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_6_0
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(10.891973f, 11.500004f);
generalPath.lineTo(6.5714283f, 33.21429f);
generalPath.curveTo(6.5714283f, 33.21429f, 32.857143f, 33.21429f, 32.857143f, 33.21429f);
generalPath.curveTo(45.441975f, 33.21429f, 48.085304f, 29.21429f, 48.085304f, 29.21429f);
generalPath.curveTo(48.085304f, 29.21429f, 44.728165f, 28.035719f, 43.299595f, 23.071432f);
generalPath.curveTo(43.299595f, 23.071432f, 40.23864f, 11.500004f, 40.23864f, 11.500004f);
generalPath.lineTo(10.891973f, 11.500004f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(13.357142448425293, 14.428570747375488), new Point2D.Double(42.21428298950195, 28.428571701049805), new float[] {0.0f,0.5f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(231, 235, 235, 255)) : new Color(231, 235, 235, 255)),((colorFilter != null) ? colorFilter.filter(new Color(230, 235, 235, 255)) : new Color(230, 235, 235, 255)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 255)) : new Color(255, 255, 255, 255))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 2.0457709148675313E-15f, 0.7857180237770081f));
g.setPaint(paint);
g.fill(shape);
paint = (colorFilter != null) ? colorFilter.filter(new Color(105, 105, 105, 255)) : new Color(105, 105, 105, 255);
stroke = new BasicStroke(1.0609524f,0,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(10.891973f, 11.500004f);
generalPath.lineTo(6.5714283f, 33.21429f);
generalPath.curveTo(6.5714283f, 33.21429f, 32.857143f, 33.21429f, 32.857143f, 33.21429f);
generalPath.curveTo(45.441975f, 33.21429f, 48.085304f, 29.21429f, 48.085304f, 29.21429f);
generalPath.curveTo(48.085304f, 29.21429f, 44.728165f, 28.035719f, 43.299595f, 23.071432f);
generalPath.curveTo(43.299595f, 23.071432f, 40.23864f, 11.500004f, 40.23864f, 11.500004f);
generalPath.lineTo(10.891973f, 11.500004f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_6_1
paint = (colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 255)) : new Color(255, 255, 255, 255);
stroke = new BasicStroke(1.060952f,0,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(11.803734f, 12.474609f);
generalPath.lineTo(7.812257f, 32.23967f);
generalPath.curveTo(7.812257f, 32.23967f, 24.956518f, 32.23967f, 32.23838f, 32.23967f);
generalPath.curveTo(43.46502f, 32.23967f, 46.348812f, 29.388803f, 46.348812f, 29.388803f);
generalPath.curveTo(46.348812f, 29.388803f, 43.35575f, 27.525963f, 42.05542f, 23.007305f);
generalPath.curveTo(42.05542f, 23.007305f, 39.316856f, 12.546038f, 39.316856f, 12.546038f);
generalPath.lineTo(11.803734f, 12.474609f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_7
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(11.315699f, 7.4285707f);
generalPath.lineTo(36.494514f, 7.4285707f);
generalPath.curveTo(37.367634f, 7.4285707f, 38.070538f, 8.097342f, 38.070538f, 8.928057f);
generalPath.lineTo(38.42768f, 11.071942f);
generalPath.curveTo(38.42768f, 11.902657f, 37.724773f, 12.571428f, 36.851658f, 12.571428f);
generalPath.lineTo(10.958556f, 12.571428f);
generalPath.curveTo(10.085439f, 12.571428f, 9.382532f, 11.902657f, 9.382532f, 11.071942f);
generalPath.lineTo(9.7396755f, 8.928057f);
generalPath.curveTo(9.7396755f, 8.097342f, 10.442582f, 7.4285707f, 11.315699f, 7.4285707f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(155, 155, 155, 255)) : new Color(155, 155, 155, 255);
g.setPaint(paint);
g.fill(shape);
paint = (colorFilter != null) ? colorFilter.filter(new Color(105, 105, 105, 255)) : new Color(105, 105, 105, 255);
stroke = new BasicStroke(0.9999995f,0,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(11.315699f, 7.4285707f);
generalPath.lineTo(36.494514f, 7.4285707f);
generalPath.curveTo(37.367634f, 7.4285707f, 38.070538f, 8.097342f, 38.070538f, 8.928057f);
generalPath.lineTo(38.42768f, 11.071942f);
generalPath.curveTo(38.42768f, 11.902657f, 37.724773f, 12.571428f, 36.851658f, 12.571428f);
generalPath.lineTo(10.958556f, 12.571428f);
generalPath.curveTo(10.085439f, 12.571428f, 9.382532f, 11.902657f, 9.382532f, 11.071942f);
generalPath.lineTo(9.7396755f, 8.928057f);
generalPath.curveTo(9.7396755f, 8.097342f, 10.442582f, 7.4285707f, 11.315699f, 7.4285707f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_8
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(20.969158f, 22.39302f);
generalPath.curveTo(21.957575f, 22.486776f, 22.691544f, 22.765423f, 23.171062f, 23.228962f);
generalPath.curveTo(23.65118f, 23.687302f, 23.849054f, 24.27324f, 23.76468f, 24.986778f);
generalPath.curveTo(23.64087f, 26.033659f, 23.075909f, 26.877413f, 22.069796f, 27.518038f);
generalPath.curveTo(21.063667f, 28.158665f, 19.762257f, 28.478977f, 18.16556f, 28.478977f);
generalPath.curveTo(16.939741f, 28.478977f, 15.906445f, 28.286268f, 15.065668f, 27.90085f);
generalPath.lineTo(15.30588f, 25.869595f);
generalPath.curveTo(16.189322f, 26.478973f, 17.193659f, 26.78366f, 18.318897f, 26.78366f);
generalPath.curveTo(19.217825f, 26.78366f, 19.926554f, 26.637829f, 20.445091f, 26.346159f);
generalPath.curveTo(20.964233f, 26.049284f, 21.255527f, 25.632618f, 21.318975f, 25.096153f);
generalPath.curveTo(21.385489f, 24.533655f, 21.17641f, 24.122196f, 20.691744f, 23.861774f);
generalPath.curveTo(20.207064f, 23.601362f, 19.398966f, 23.471153f, 18.26745f, 23.47115f);
generalPath.lineTo(16.994488f, 23.47115f);
generalPath.lineTo(17.20144f, 21.721144f);
generalPath.lineTo(18.559265f, 21.721144f);
generalPath.curveTo(19.451904f, 21.72115f, 20.149975f, 21.585733f, 20.653477f, 21.314892f);
generalPath.curveTo(21.157581f, 21.038857f, 21.439508f, 20.648232f, 21.499262f, 20.143013f);
generalPath.curveTo(21.555304f, 19.669062f, 21.408537f, 19.288853f, 21.05896f, 19.002384f);
generalPath.curveTo(20.70937f, 18.715935f, 20.15426f, 18.572704f, 19.393635f, 18.572697f);
generalPath.curveTo(18.431835f, 18.572704f, 17.490215f, 18.853956f, 16.568771f, 19.416449f);
generalPath.lineTo(16.798822f, 17.47113f);
generalPath.curveTo(17.831207f, 17.03364f, 18.988598f, 16.81489f, 20.270992f, 16.814878f);
generalPath.curveTo(21.509375f, 16.81489f, 22.459536f, 17.072702f, 23.121485f, 17.588318f);
generalPath.curveTo(23.7897f, 18.103954f, 24.076385f, 18.76281f, 23.981543f, 19.564886f);
generalPath.curveTo(23.893456f, 20.309689f, 23.59823f, 20.919065f, 23.09587f, 21.393017f);
generalPath.curveTo(22.594105f, 21.861774f, 21.8852f, 22.195108f, 20.969158f, 22.39302f);
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255);
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_9
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(33.007378f, 28.45554f);
generalPath.lineTo(30.499172f, 28.45554f);
generalPath.lineTo(29.387875f, 19.096134f);
generalPath.lineTo(26.295374f, 19.893011f);
generalPath.lineTo(26.086657f, 18.135195f);
generalPath.lineTo(31.627996f, 16.838314f);
generalPath.lineTo(33.007378f, 28.45554f);
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255);
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 0.5257143f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(0.684211015701294f, 0.0f, 0.0f, 0.684211015701294f, 3.5601539611816406f, 2.2781970500946045f));
// _0_0_10
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(12.857143f, 9.928572f);
generalPath.curveTo(12.859318f, 10.414878f, 12.601125f, 10.865177f, 12.18033f, 11.10896f);
generalPath.curveTo(11.759535f, 11.352744f, 11.240465f, 11.352744f, 10.81967f, 11.10896f);
generalPath.curveTo(10.398875f, 10.865177f, 10.140682f, 10.414878f, 10.142857f, 9.928572f);
generalPath.curveTo(10.140682f, 9.4422655f, 10.398875f, 8.991966f, 10.81967f, 8.748183f);
generalPath.curveTo(11.240465f, 8.504399f, 11.759535f, 8.504399f, 12.18033f, 8.748183f);
generalPath.curveTo(12.601125f, 8.991966f, 12.859318f, 9.4422655f, 12.857143f, 9.928572f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(254, 254, 254, 255)) : new Color(254, 254, 254, 255);
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 0.30285713f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_11
paint = (colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 255)) : new Color(255, 255, 255, 255);
stroke = new BasicStroke(1.0000001f,1,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(13.042053f, 8.601015f);
generalPath.lineTo(36.371307f, 8.601015f);
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
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
        return 1.047010898590088;
    }

    /**
     * Returns the Y of the bounding box of the original SVG image.
     * 
     * @return The Y of the bounding box of the original SVG image.
     */
    public static double getOrigY() {
        return 6.928570747375488;
    }

	/**
	 * Returns the width of the bounding box of the original SVG image.
	 * 
	 * @return The width of the bounding box of the original SVG image.
	 */
	public static double getOrigWidth() {
		return 46.59535217285156;
	}

	/**
	 * Returns the height of the bounding box of the original SVG image.
	 * 
	 * @return The height of the bounding box of the original SVG image.
	 */
	public static double getOrigHeight() {
		return 37.29445266723633;
	}

	/** The current width of this icon. */
	private int width;

    /** The current height of this icon. */
	private int height;

	/**
	 * Creates a new transcoded SVG image. This is marked as private to indicate that app
	 * code should be using the {@link #of(int, int)} method to obtain a pre-configured instance.
	 */
	private TangoROffice_calendar() {
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
       TangoROffice_calendar base = new TangoROffice_calendar();
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
       TangoROffice_calendar base = new TangoROffice_calendar();
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
        return TangoROffice_calendar::new;
    }
}

