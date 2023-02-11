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
public class TangoRNetwork_server implements RadianceIcon {
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
g.setComposite(AlphaComposite.getInstance(3, 0.634f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.2588000297546387f, 0.0f, 0.0f, 0.6902999877929688f, -44.792999267578125f, 2.0829999446868896f));
// _0_0_0
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(36.378f, 30.647f);
generalPath.lineTo(72.921f, 30.647f);
generalPath.lineTo(72.921f, 34.647f);
generalPath.lineTo(36.378f, 34.647f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(1.6419999599456787, 117.8270034790039), new Point2D.Double(15.343000411987305, 117.8270034790039), new float[] {0.0f,0.238f,0.781f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(104, 104, 104, 0)) : new Color(104, 104, 104, 0)),((colorFilter != null) ? colorFilter.filter(new Color(104, 104, 104, 255)) : new Color(104, 104, 104, 255)),((colorFilter != null) ? colorFilter.filter(new Color(104, 104, 104, 255)) : new Color(104, 104, 104, 255)),((colorFilter != null) ? colorFilter.filter(new Color(104, 104, 104, 0)) : new Color(104, 104, 104, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.7401700019836426f, 0.0f, 0.0f, 0.36493998765945435f, 31.378000259399414f, -10.352999687194824f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.2588000297546387f, 0.0f, 0.0f, 0.6902999877929688f, -44.792999267578125f, 2.0829999446868896f));
// _0_0_1
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(36.378f, 33.649f);
generalPath.lineTo(72.921f, 33.649f);
generalPath.lineTo(72.921f, 34.639f);
generalPath.lineTo(36.378f, 34.639f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(0.6119999885559082, 372.5780029296875), new Point2D.Double(5.085999965667725, 372.5780029296875), new float[] {0.0f,0.1f,0.9f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(71, 71, 71, 0)) : new Color(71, 71, 71, 0)),((colorFilter != null) ? colorFilter.filter(new Color(71, 71, 71, 255)) : new Color(71, 71, 71, 255)),((colorFilter != null) ? colorFilter.filter(new Color(71, 71, 71, 255)) : new Color(71, 71, 71, 255)),((colorFilter != null) ? colorFilter.filter(new Color(71, 71, 71, 0)) : new Color(71, 71, 71, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(8.168600082397461f, 0.0f, 0.0f, 0.22121000289916992f, 31.378000259399414f, -48.27399826049805f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.2588000297546387f, 0.0f, 0.0f, 0.6902999877929688f, -44.792999267578125f, 2.0829999446868896f));
// _0_0_2
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(36.378f, 30.301f);
generalPath.lineTo(72.921f, 30.301f);
generalPath.lineTo(72.921f, 31.324f);
generalPath.lineTo(36.378f, 31.324f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(0.6119999885559082, 372.5780029296875), new Point2D.Double(5.085999965667725, 372.5780029296875), new float[] {0.0f,0.1f,0.9f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(71, 71, 71, 0)) : new Color(71, 71, 71, 0)),((colorFilter != null) ? colorFilter.filter(new Color(71, 71, 71, 255)) : new Color(71, 71, 71, 255)),((colorFilter != null) ? colorFilter.filter(new Color(71, 71, 71, 255)) : new Color(71, 71, 71, 255)),((colorFilter != null) ? colorFilter.filter(new Color(71, 71, 71, 0)) : new Color(71, 71, 71, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(8.168600082397461f, 0.0f, 0.0f, 0.2286199927330017f, 31.378000259399414f, -46.266998291015625f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.2588000297546387f, 0.0f, 0.0f, 0.6902999877929688f, -44.792999267578125f, 2.0829999446868896f));
// _0_0_3
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(36.378f, 31.425f);
generalPath.lineTo(72.921f, 31.425f);
generalPath.lineTo(72.921f, 33.044f);
generalPath.lineTo(36.378f, 33.044f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(1.6419999599456787, 117.8270034790039), new Point2D.Double(15.343000411987305, 117.8270034790039), new float[] {0.0f,0.108f,0.921f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 0)) : new Color(255, 255, 255, 0)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 180)) : new Color(255, 255, 255, 180)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 180)) : new Color(255, 255, 255, 180)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 0)) : new Color(255, 255, 255, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.7401700019836426f, 0.0f, 0.0f, 0.14768999814987183f, 31.378000259399414f, 14.833000183105469f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(0.9208800196647644f, 0.0f, 0.0f, 0.9208800196647644f, -26.290000915527344f, 0.3440000116825104f));
// _0_1
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(68.185295f, 26.231213f);
generalPath.curveTo(68.188774f, 27.009447f, 67.77559f, 27.730057f, 67.102196f, 28.120182f);
generalPath.curveTo(66.428795f, 28.510307f, 65.59814f, 28.510307f, 64.924736f, 28.120182f);
generalPath.curveTo(64.25134f, 27.730057f, 63.838158f, 27.009447f, 63.841637f, 26.231213f);
generalPath.curveTo(63.838158f, 25.452978f, 64.25134f, 24.732368f, 64.924736f, 24.342243f);
generalPath.curveTo(65.59814f, 23.952118f, 66.428795f, 23.952118f, 67.102196f, 24.342243f);
generalPath.curveTo(67.77559f, 24.732368f, 68.188774f, 25.452978f, 68.185295f, 26.231213f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(234, 234, 234, 255)) : new Color(234, 234, 234, 255);
g.setPaint(paint);
g.fill(shape);
paint = (colorFilter != null) ? colorFilter.filter(new Color(138, 138, 138, 255)) : new Color(138, 138, 138, 255);
stroke = new BasicStroke(1.086f,0,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(68.185295f, 26.231213f);
generalPath.curveTo(68.188774f, 27.009447f, 67.77559f, 27.730057f, 67.102196f, 28.120182f);
generalPath.curveTo(66.428795f, 28.510307f, 65.59814f, 28.510307f, 64.924736f, 28.120182f);
generalPath.curveTo(64.25134f, 27.730057f, 63.838158f, 27.009447f, 63.841637f, 26.231213f);
generalPath.curveTo(63.838158f, 25.452978f, 64.25134f, 24.732368f, 64.924736f, 24.342243f);
generalPath.curveTo(65.59814f, 23.952118f, 66.428795f, 23.952118f, 67.102196f, 24.342243f);
generalPath.curveTo(67.77559f, 24.732368f, 68.188774f, 25.452978f, 68.185295f, 26.231213f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_2
g.setComposite(AlphaComposite.getInstance(3, 0.402f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(0.0142000000923872f, 0.0f, 0.0f, 0.02087000012397766f, 36.542999267578125f, 39.16299819946289f));
// _0_2_0
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(-1559.252f, -150.697f);
generalPath.lineTo(-219.619f, -150.697f);
generalPath.lineTo(-219.619f, 327.66f);
generalPath.lineTo(-1559.252f, 327.66f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(302.85699462890625, 366.64801025390625), new Point2D.Double(302.85699462890625, 609.5050048828125), new float[] {0.0f,0.5f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 0)) : new Color(0, 0, 0, 0)),((colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255)),((colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 0)) : new Color(0, 0, 0, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.7743899822235107f, 0.0f, 0.0f, 1.9696999788284302f, -1892.178955078125f, -872.885009765625f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 0.402f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(0.0142000000923872f, 0.0f, 0.0f, 0.02087000012397766f, 36.542999267578125f, 39.16299819946289f));
// _0_2_1
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
paint = new RadialGradientPaint(new Point2D.Double(605.7139892578125, 486.64801025390625), 117.143f, new Point2D.Double(605.7139892578125, 486.64801025390625), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255)),((colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 0)) : new Color(0, 0, 0, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.7743899822235107f, 0.0f, 0.0f, 1.9696999788284302f, -1891.633056640625f, -872.885009765625f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 0.402f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(0.0142000000923872f, 0.0f, 0.0f, 0.02087000012397766f, 36.542999267578125f, 39.16299819946289f));
// _0_2_2
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
paint = new RadialGradientPaint(new Point2D.Double(605.7139892578125, 486.64801025390625), 117.143f, new Point2D.Double(605.7139892578125, 486.64801025390625), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255)),((colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 0)) : new Color(0, 0, 0, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-2.7743899822235107f, 0.0f, 0.0f, 1.9696999788284302f, 112.76200103759766f, -872.885009765625f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0017199516296387f, 0.0f, 0.0f, 1.000480055809021f, 0.012000000104308128f, 0.9449999928474426f));
// _0_3
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(12.466718f, 7.997959f);
generalPath.lineTo(12.466718f, 41.535027f);
generalPath.lineTo(34.42887f, 41.535027f);
generalPath.lineTo(34.42887f, 7.795929f);
generalPath.lineTo(30.38826f, 3.553288f);
generalPath.lineTo(16.305298f, 3.553288f);
generalPath.lineTo(12.466718f, 7.997959f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(24.350000381469727, 34.4640007019043), new Point2D.Double(23.233999252319336, 10.017999649047852), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(210, 210, 210, 255)) : new Color(210, 210, 210, 255)),((colorFilter != null) ? colorFilter.filter(new Color(223, 223, 223, 255)) : new Color(223, 223, 223, 255))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.40400001406669617f));
g.setPaint(paint);
g.fill(shape);
paint = (colorFilter != null) ? colorFilter.filter(new Color(94, 94, 94, 255)) : new Color(94, 94, 94, 255);
stroke = new BasicStroke(0.999f,1,1,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(12.466718f, 7.997959f);
generalPath.lineTo(12.466718f, 41.535027f);
generalPath.lineTo(34.42887f, 41.535027f);
generalPath.lineTo(34.42887f, 7.795929f);
generalPath.lineTo(30.38826f, 3.553288f);
generalPath.lineTo(16.305298f, 3.553288f);
generalPath.lineTo(12.466718f, 7.997959f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0352100133895874f, 0.0f, 0.0f, 1.0294100046157837f, -0.4580000042915344f, 0.8820000290870667f));
// _0_4
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(16.428572f, 4.0f);
generalPath.lineTo(13.0f, 8.857143f);
generalPath.lineTo(33.285713f, 8.857143f);
generalPath.lineTo(29.571428f, 4.142857f);
generalPath.lineTo(16.428572f, 4.0f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 167)) : new Color(255, 255, 255, 167);
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 0.349f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.025570034980774f, 0.0f, 0.0f, 0.942799985408783f, -0.871999979019165f, 1.3539999723434448f));
// _0_5
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(16.451f, 11.291f);
generalPath.lineTo(32.052f, 11.291f);
generalPath.lineTo(32.052f, 15.534f);
generalPath.lineTo(16.451f, 15.534f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 255)) : new Color(255, 255, 255, 255);
g.setPaint(paint);
g.fill(shape);
paint = (colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 255)) : new Color(255, 255, 255, 255);
stroke = new BasicStroke(1.017f,0,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(16.451f, 11.291f);
generalPath.lineTo(32.052f, 11.291f);
generalPath.lineTo(32.052f, 15.534f);
generalPath.lineTo(16.451f, 15.534f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0089600086212158f, 0.0f, 0.0f, 1.0050599575042725f, -0.0860000029206276f, 0.8930000066757202f));
// _0_6
paint = (colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 255)) : new Color(255, 255, 255, 255);
stroke = new BasicStroke(0.993f,0,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(13.465157f, 8.6221f);
generalPath.lineTo(13.465157f, 40.403126f);
generalPath.lineTo(33.287598f, 40.403126f);
generalPath.lineTo(33.287598f, 8.43856f);
generalPath.lineTo(29.616774f, 4.5841956f);
generalPath.lineTo(16.952438f, 4.5841956f);
generalPath.lineTo(13.465157f, 8.6221f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 0.526f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0094399452209473f, 0.0f, 0.0f, 0.7933599948883057f, -0.7450000047683716f, 6.309999942779541f));
// _0_7
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(21.542f, 23.559f);
generalPath.lineTo(32.439f, 23.559f);
generalPath.lineTo(32.439f, 28.601f);
generalPath.lineTo(21.542f, 28.601f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(27.325000762939453, 26.88800048828125), new Point2D.Double(22.312000274658203, 26.78700065612793), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 255)) : new Color(255, 255, 255, 255)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 0)) : new Color(255, 255, 255, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9559299945831299f, 0.0f, 0.0f, 1.0f, 0.8700000047683716f, 0.0f));
g.setPaint(paint);
g.fill(shape);
paint = new LinearGradientPaint(new Point2D.Double(27.325000762939453, 26.88800048828125), new Point2D.Double(22.312000274658203, 26.78700065612793), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 255)) : new Color(255, 255, 255, 255)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 0)) : new Color(255, 255, 255, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9559299945831299f, 0.0f, 0.0f, 1.0f, 0.8700000047683716f, 0.0f));
stroke = new BasicStroke(1.117f,0,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(21.542f, 23.559f);
generalPath.lineTo(32.439f, 23.559f);
generalPath.lineTo(32.439f, 28.601f);
generalPath.lineTo(21.542f, 28.601f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0527399778366089f, 0.0f, 0.0f, 0.942799985408783f, -1.3660000562667847f, 2.388000011444092f));
// _0_8
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(20.771f, 23.453f);
generalPath.lineTo(31.22f, 23.453f);
generalPath.lineTo(31.22f, 27.696f);
generalPath.lineTo(20.771f, 27.696f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(200, 200, 200, 255)) : new Color(200, 200, 200, 255);
g.setPaint(paint);
g.fill(shape);
paint = (colorFilter != null) ? colorFilter.filter(new Color(172, 172, 172, 255)) : new Color(172, 172, 172, 255);
stroke = new BasicStroke(1.004f,0,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(20.771f, 23.453f);
generalPath.lineTo(31.22f, 23.453f);
generalPath.lineTo(31.22f, 27.696f);
generalPath.lineTo(20.771f, 27.696f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 0.349f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.025570034980774f, 0.0f, 0.0f, 0.942799985408783f, -0.871999979019165f, 1.6979999542236328f));
// _0_9
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(16.451f, 17.291f);
generalPath.lineTo(32.052f, 17.291f);
generalPath.lineTo(32.052f, 21.534f);
generalPath.lineTo(16.451f, 21.534f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 255)) : new Color(255, 255, 255, 255);
g.setPaint(paint);
g.fill(shape);
paint = (colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 255)) : new Color(255, 255, 255, 255);
stroke = new BasicStroke(1.017f,0,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(16.451f, 17.291f);
generalPath.lineTo(32.052f, 17.291f);
generalPath.lineTo(32.052f, 21.534f);
generalPath.lineTo(16.451f, 21.534f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0132399797439575f, 0.0f, 0.0f, 0.942799985408783f, -0.15199999511241913f, 2.055000066757202f));
// _0_10
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(15.447f, 16.382f);
generalPath.lineTo(31.238f, 16.382f);
generalPath.lineTo(31.238f, 20.625f);
generalPath.lineTo(15.447f, 20.625f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(200, 200, 200, 255)) : new Color(200, 200, 200, 255);
g.setPaint(paint);
g.fill(shape);
paint = (colorFilter != null) ? colorFilter.filter(new Color(172, 172, 172, 255)) : new Color(172, 172, 172, 255);
stroke = new BasicStroke(1.023f,0,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(15.447f, 16.382f);
generalPath.lineTo(31.238f, 16.382f);
generalPath.lineTo(31.238f, 20.625f);
generalPath.lineTo(15.447f, 20.625f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0132399797439575f, 0.0f, 0.0f, 0.942799985408783f, -0.15199999511241913f, 1.7120000123977661f));
// _0_11
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(15.447f, 10.382f);
generalPath.lineTo(31.238f, 10.382f);
generalPath.lineTo(31.238f, 14.625f);
generalPath.lineTo(15.447f, 14.625f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(200, 200, 200, 255)) : new Color(200, 200, 200, 255);
g.setPaint(paint);
g.fill(shape);
paint = (colorFilter != null) ? colorFilter.filter(new Color(172, 172, 172, 255)) : new Color(172, 172, 172, 255);
stroke = new BasicStroke(1.023f,0,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(15.447f, 10.382f);
generalPath.lineTo(31.238f, 10.382f);
generalPath.lineTo(31.238f, 14.625f);
generalPath.lineTo(15.447f, 14.625f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_12
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(0.6907399892807007f, 0.0f, 0.0f, 0.6907299757003784f, -28.597999572753906f, 8.880999565124512f));
// _0_12_0
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(68.185295f, 26.231213f);
generalPath.curveTo(68.188774f, 27.009447f, 67.77559f, 27.730057f, 67.102196f, 28.120182f);
generalPath.curveTo(66.428795f, 28.510307f, 65.59814f, 28.510307f, 64.924736f, 28.120182f);
generalPath.curveTo(64.25134f, 27.730057f, 63.838158f, 27.009447f, 63.841637f, 26.231213f);
generalPath.curveTo(63.838158f, 25.452978f, 64.25134f, 24.732368f, 64.924736f, 24.342243f);
generalPath.curveTo(65.59814f, 23.952118f, 66.428795f, 23.952118f, 67.102196f, 24.342243f);
generalPath.curveTo(67.77559f, 24.732368f, 68.188774f, 25.452978f, 68.185295f, 26.231213f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(200, 200, 200, 255)) : new Color(200, 200, 200, 255);
g.setPaint(paint);
g.fill(shape);
paint = (colorFilter != null) ? colorFilter.filter(new Color(151, 151, 151, 255)) : new Color(151, 151, 151, 255);
stroke = new BasicStroke(1.448f,0,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(68.185295f, 26.231213f);
generalPath.curveTo(68.188774f, 27.009447f, 67.77559f, 27.730057f, 67.102196f, 28.120182f);
generalPath.curveTo(66.428795f, 28.510307f, 65.59814f, 28.510307f, 64.924736f, 28.120182f);
generalPath.curveTo(64.25134f, 27.730057f, 63.838158f, 27.009447f, 63.841637f, 26.231213f);
generalPath.curveTo(63.838158f, 25.452978f, 64.25134f, 24.732368f, 64.924736f, 24.342243f);
generalPath.curveTo(65.59814f, 23.952118f, 66.428795f, 23.952118f, 67.102196f, 24.342243f);
generalPath.curveTo(67.77559f, 24.732368f, 68.188774f, 25.452978f, 68.185295f, 26.231213f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(0.8934000134468079f, 0.0f, 0.0f, 0.8934000134468079f, 2.063999891281128f, 3.8359999656677246f));
// _0_12_1
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(16.667519f, 25.574614f);
generalPath.curveTo(16.668327f, 25.755598f, 16.572239f, 25.923182f, 16.415634f, 26.013908f);
generalPath.curveTo(16.259031f, 26.104635f, 16.065853f, 26.104635f, 15.909249f, 26.013908f);
generalPath.curveTo(15.7526455f, 25.923182f, 15.656555f, 25.755598f, 15.657365f, 25.574614f);
generalPath.curveTo(15.656555f, 25.39363f, 15.7526455f, 25.226046f, 15.909249f, 25.135319f);
generalPath.curveTo(16.065853f, 25.044592f, 16.259031f, 25.044592f, 16.415634f, 25.135319f);
generalPath.curveTo(16.572239f, 25.226046f, 16.668327f, 25.39363f, 16.667519f, 25.574614f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 255)) : new Color(255, 255, 255, 255);
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(0.9899500012397766f, 0.0f, 0.0f, 0.9899500012397766f, 0.11999999731779099f, 1.4819999933242798f));
// _0_13
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(19.072f, 29.817f);
generalPath.lineTo(20.082f, 29.817f);
generalPath.lineTo(20.082f, 39.919f);
generalPath.lineTo(19.072f, 39.919f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(16.36400032043457, 39.91899871826172), new Point2D.Double(16.36400032043457, 30.92799949645996), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 255)) : new Color(255, 255, 255, 255)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 0)) : new Color(255, 255, 255, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 2.7070000171661377f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(0.9899500012397766f, 0.0f, 0.0f, 0.9899500012397766f, 0.14000000059604645f, 1.4819999933242798f));
// _0_14
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(21.072f, 29.817f);
generalPath.lineTo(22.082f, 29.817f);
generalPath.lineTo(22.082f, 39.919f);
generalPath.lineTo(21.072f, 39.919f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(16.36400032043457, 39.91899871826172), new Point2D.Double(16.36400032043457, 30.92799949645996), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 255)) : new Color(255, 255, 255, 255)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 0)) : new Color(255, 255, 255, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 4.706999778747559f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(0.9899500012397766f, 0.0f, 0.0f, 0.9899500012397766f, 0.1599999964237213f, 1.4819999933242798f));
// _0_15
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(23.072f, 29.817f);
generalPath.lineTo(24.082f, 29.817f);
generalPath.lineTo(24.082f, 39.919f);
generalPath.lineTo(23.072f, 39.919f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(16.36400032043457, 39.91899871826172), new Point2D.Double(16.36400032043457, 30.92799949645996), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 255)) : new Color(255, 255, 255, 255)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 0)) : new Color(255, 255, 255, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 6.706999778747559f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(0.9899500012397766f, 0.0f, 0.0f, 0.9899500012397766f, 0.18000000715255737f, 1.4819999933242798f));
// _0_16
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(25.072f, 29.817f);
generalPath.lineTo(26.082f, 29.817f);
generalPath.lineTo(26.082f, 39.919f);
generalPath.lineTo(25.072f, 39.919f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(16.36400032043457, 39.91899871826172), new Point2D.Double(16.36400032043457, 30.92799949645996), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 255)) : new Color(255, 255, 255, 255)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 0)) : new Color(255, 255, 255, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 8.706999778747559f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(0.9899500012397766f, 0.0f, 0.0f, 0.9899500012397766f, 0.20000000298023224f, 1.4819999933242798f));
// _0_17
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(27.072f, 29.817f);
generalPath.lineTo(28.082f, 29.817f);
generalPath.lineTo(28.082f, 39.919f);
generalPath.lineTo(27.072f, 39.919f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(16.36400032043457, 39.91899871826172), new Point2D.Double(16.36400032043457, 30.92799949645996), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 255)) : new Color(255, 255, 255, 255)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 0)) : new Color(255, 255, 255, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 10.706999778747559f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 0.543f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(0.9899500012397766f, 0.0f, 0.0f, 0.9899500012397766f, 0.2199999988079071f, 1.4819999933242798f));
// _0_18
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(29.072f, 29.817f);
generalPath.lineTo(30.082f, 29.817f);
generalPath.lineTo(30.082f, 39.919f);
generalPath.lineTo(29.072f, 39.919f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(16.36400032043457, 39.91899871826172), new Point2D.Double(16.36400032043457, 30.92799949645996), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 255)) : new Color(255, 255, 255, 255)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 0)) : new Color(255, 255, 255, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 12.706999778747559f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 0.16f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(0.9899500012397766f, 0.0f, 0.0f, 0.9899500012397766f, 0.20000000298023224f, 1.4819999933242798f));
// _0_19
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(17.981f, 29.817f);
generalPath.lineTo(18.991f, 29.817f);
generalPath.lineTo(18.991f, 39.919f);
generalPath.lineTo(17.981f, 39.919f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(16.36400032043457, 39.91899871826172), new Point2D.Double(16.36400032043457, 30.92799949645996), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255)),((colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 0)) : new Color(0, 0, 0, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 1.6160000562667847f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 0.291f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(0.9899500012397766f, 0.0f, 0.0f, 0.9899500012397766f, 0.2199999988079071f, 1.4819999933242798f));
// _0_20
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(19.981f, 29.817f);
generalPath.lineTo(20.991f, 29.817f);
generalPath.lineTo(20.991f, 39.919f);
generalPath.lineTo(19.981f, 39.919f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(16.36400032043457, 39.91899871826172), new Point2D.Double(16.36400032043457, 30.92799949645996), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255)),((colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 0)) : new Color(0, 0, 0, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 3.615999937057495f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 0.291f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(0.9899500012397766f, 0.0f, 0.0f, 0.9899500012397766f, 0.23999999463558197f, 1.4819999933242798f));
// _0_21
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(21.981f, 29.817f);
generalPath.lineTo(22.991f, 29.817f);
generalPath.lineTo(22.991f, 39.919f);
generalPath.lineTo(21.981f, 39.919f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(16.36400032043457, 39.91899871826172), new Point2D.Double(16.36400032043457, 30.92799949645996), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255)),((colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 0)) : new Color(0, 0, 0, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 5.616000175476074f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 0.291f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(0.9899500012397766f, 0.0f, 0.0f, 0.9899500012397766f, 0.25999999046325684f, 1.4819999933242798f));
// _0_22
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(23.981f, 29.817f);
generalPath.lineTo(24.991f, 29.817f);
generalPath.lineTo(24.991f, 39.919f);
generalPath.lineTo(23.981f, 39.919f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(16.36400032043457, 39.91899871826172), new Point2D.Double(16.36400032043457, 30.92799949645996), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255)),((colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 0)) : new Color(0, 0, 0, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 7.616000175476074f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 0.291f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(0.9899500012397766f, 0.0f, 0.0f, 0.9899500012397766f, 0.2800000011920929f, 1.4819999933242798f));
// _0_23
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(25.981f, 29.817f);
generalPath.lineTo(26.991f, 29.817f);
generalPath.lineTo(26.991f, 39.919f);
generalPath.lineTo(25.981f, 39.919f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(16.36400032043457, 39.91899871826172), new Point2D.Double(16.36400032043457, 30.92799949645996), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255)),((colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 0)) : new Color(0, 0, 0, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 9.616000175476074f, 0.0f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 0.229f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(0.9899500012397766f, 0.0f, 0.0f, 0.9899500012397766f, 0.30000001192092896f, 1.4819999933242798f));
// _0_24
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(27.981f, 29.817f);
generalPath.lineTo(28.991f, 29.817f);
generalPath.lineTo(28.991f, 39.919f);
generalPath.lineTo(27.981f, 39.919f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(16.36400032043457, 39.91899871826172), new Point2D.Double(16.36400032043457, 30.92799949645996), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255)),((colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 0)) : new Color(0, 0, 0, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 11.616000175476074f, 0.0f));
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
        return 0.9996261596679688;
    }

    /**
     * Returns the Y of the bounding box of the original SVG image.
     * 
     * @return The Y of the bounding box of the original SVG image.
     */
    public static double getOrigY() {
        return 4.000254154205322;
    }

	/**
	 * Returns the width of the bounding box of the original SVG image.
	 * 
	 * @return The width of the bounding box of the original SVG image.
	 */
	public static double getOrigWidth() {
		return 46.000328063964844;
	}

	/**
	 * Returns the height of the bounding box of the original SVG image.
	 * 
	 * @return The height of the bounding box of the original SVG image.
	 */
	public static double getOrigHeight() {
		return 42.01959991455078;
	}

	/** The current width of this icon. */
	private int width;

    /** The current height of this icon. */
	private int height;

	/**
	 * Creates a new transcoded SVG image. This is marked as private to indicate that app
	 * code should be using the {@link #of(int, int)} method to obtain a pre-configured instance.
	 */
	private TangoRNetwork_server() {
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
       TangoRNetwork_server base = new TangoRNetwork_server();
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
       TangoRNetwork_server base = new TangoRNetwork_server();
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
        return TangoRNetwork_server::new;
    }
}

