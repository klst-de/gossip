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
public class TangoRUtilities_system_monitor implements RadianceIcon {
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
g.transform(new AffineTransform(0.024544989690184593f, 0.0f, 0.0f, 0.02086758054792881f, 45.8936882019043f, 40.09109115600586f));
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
shape = new RoundRectangle2D.Double(1.6199486255645752, 1.6600797176361084, 44.75983810424805, 41.70181655883789, 9.131982803344727, 9.131985664367676);
paint = new LinearGradientPaint(new Point2D.Double(25.86111831665039, 26.133586883544922), new Point2D.Double(18.300277709960938, 19.567596435546875), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(195, 198, 192, 255)) : new Color(195, 198, 192, 255)),((colorFilter != null) ? colorFilter.filter(new Color(232, 234, 230, 255)) : new Color(232, 234, 230, 255))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9523869752883911f, 0.0f, 0.0f, 1.0183390378952026f, 1.1425989866256714f, -1.941627025604248f));
g.setPaint(paint);
g.fill(shape);
paint = new LinearGradientPaint(new Point2D.Double(0.0012142062187194824, 24.012266159057617), new Point2D.Double(47.99876403808594, 24.012266159057617), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(169, 170, 167, 255)) : new Color(169, 170, 167, 255)),((colorFilter != null) ? colorFilter.filter(new Color(103, 105, 100, 255)) : new Color(103, 105, 100, 255))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9523869752883911f, 0.0f, 0.0f, 1.0183390378952026f, 1.1425989866256714f, -1.941627025604248f));
stroke = new BasicStroke(0.9999997f,1,1,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(1.6199486255645752, 1.6600797176361084, 44.75983810424805, 41.70181655883789, 9.131982803344727, 9.131985664367676);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2
shape = new RoundRectangle2D.Double(5.4052019119262695, 5.4815545082092285, 37.17786407470703, 28.954593658447266, 3.389341354370117, 3.3893420696258545);
paint = new RadialGradientPaint(new Point2D.Double(23.99413299560547, 32.266910552978516), 19.088932f, new Point2D.Double(23.99413299560547, 32.266910552978516), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(81, 135, 214, 255)) : new Color(81, 135, 214, 255)),((colorFilter != null) ? colorFilter.filter(new Color(30, 69, 128, 255)) : new Color(30, 69, 128, 255))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.768625020980835f, -1.96125698954616E-23f, 1.5520639517275292E-23f, 1.3871020078659058f, -18.442480087280273f, -15.292679786682129f));
g.setPaint(paint);
g.fill(shape);
paint = (colorFilter != null) ? colorFilter.filter(new Color(23, 53, 98, 255)) : new Color(23, 53, 98, 255);
stroke = new BasicStroke(0.9999998f,1,1,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(5.4052019119262695, 5.4815545082092285, 37.17786407470703, 28.954593658447266, 3.389341354370117, 3.3893420696258545);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_3
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(12.390689f, 20.935247f);
generalPath.lineTo(6.368861f, 20.935247f);
generalPath.lineTo(6.368861f, 22.152252f);
generalPath.lineTo(14.087646f, 22.152252f);
generalPath.lineTo(15.493568f, 16.239132f);
generalPath.lineTo(18.956081f, 29.936651f);
generalPath.lineTo(22.164804f, 19.116953f);
generalPath.lineTo(25.059347f, 25.028755f);
generalPath.lineTo(28.946308f, 21.516787f);
generalPath.lineTo(41.654736f, 21.516787f);
generalPath.lineTo(41.654736f, 19.457142f);
generalPath.lineTo(28.505236f, 19.457142f);
generalPath.lineTo(25.335018f, 22.59674f);
generalPath.lineTo(22.059557f, 15.937588f);
generalPath.lineTo(19.049723f, 24.112486f);
generalPath.lineTo(15.78119f, 11.248712f);
generalPath.lineTo(12.390689f, 20.935247f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(236, 255, 217, 255)) : new Color(236, 255, 217, 255);
g.setPaint(paint);
g.fill(shape);
paint = (colorFilter != null) ? colorFilter.filter(new Color(195, 234, 155, 106)) : new Color(195, 234, 155, 106);
stroke = new BasicStroke(1.0f,0,0,10.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(12.390689f, 20.935247f);
generalPath.lineTo(6.368861f, 20.935247f);
generalPath.lineTo(6.368861f, 22.152252f);
generalPath.lineTo(14.087646f, 22.152252f);
generalPath.lineTo(15.493568f, 16.239132f);
generalPath.lineTo(18.956081f, 29.936651f);
generalPath.lineTo(22.164804f, 19.116953f);
generalPath.lineTo(25.059347f, 25.028755f);
generalPath.lineTo(28.946308f, 21.516787f);
generalPath.lineTo(41.654736f, 21.516787f);
generalPath.lineTo(41.654736f, 19.457142f);
generalPath.lineTo(28.505236f, 19.457142f);
generalPath.lineTo(25.335018f, 22.59674f);
generalPath.lineTo(22.059557f, 15.937588f);
generalPath.lineTo(19.049723f, 24.112486f);
generalPath.lineTo(15.78119f, 11.248712f);
generalPath.lineTo(12.390689f, 20.935247f);
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
paint = new LinearGradientPaint(new Point2D.Double(20.33875846862793, 19.63689422607422), new Point2D.Double(46.092254638671875, 39.70832443237305), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 255)) : new Color(255, 255, 255, 255)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 0)) : new Color(255, 255, 255, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9523869752883911f, 0.0f, 0.0f, 1.0156569480895996f, 1.1425989866256714f, -0.8763250112533569f));
stroke = new BasicStroke(0.9999998f,1,1,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(2.553668975830078, 2.6544337272644043, 42.89474105834961, 39.646549224853516, 8.260666847229004, 8.260668754577637);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 0.38068184f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_5
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(11.515689f, 20.012278f);
generalPath.lineTo(6.368861f, 20.012278f);
generalPath.lineTo(6.368861f, 23.06103f);
generalPath.lineTo(14.962646f, 22.93603f);
generalPath.lineTo(15.618568f, 19.893387f);
generalPath.lineTo(18.963228f, 32.601727f);
generalPath.lineTo(22.539804f, 21.135092f);
generalPath.lineTo(25.059347f, 26.551191f);
generalPath.lineTo(29.321308f, 22.44261f);
generalPath.lineTo(41.654736f, 22.31761f);
generalPath.lineTo(40.904736f, 18.408072f);
generalPath.lineTo(28.505236f, 18.283072f);
generalPath.lineTo(25.460018f, 21.456026f);
generalPath.lineTo(22.059557f, 13.665616f);
generalPath.lineTo(19.424723f, 20.604265f);
generalPath.lineTo(15.90619f, 8.333659f);
generalPath.lineTo(11.515689f, 20.012278f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(236, 255, 217, 255)) : new Color(236, 255, 217, 255);
g.setPaint(paint);
g.fill(shape);
paint = (colorFilter != null) ? colorFilter.filter(new Color(195, 234, 155, 106)) : new Color(195, 234, 155, 106);
stroke = new BasicStroke(1.0000004f,0,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(11.515689f, 20.012278f);
generalPath.lineTo(6.368861f, 20.012278f);
generalPath.lineTo(6.368861f, 23.06103f);
generalPath.lineTo(14.962646f, 22.93603f);
generalPath.lineTo(15.618568f, 19.893387f);
generalPath.lineTo(18.963228f, 32.601727f);
generalPath.lineTo(22.539804f, 21.135092f);
generalPath.lineTo(25.059347f, 26.551191f);
generalPath.lineTo(29.321308f, 22.44261f);
generalPath.lineTo(41.654736f, 22.31761f);
generalPath.lineTo(40.904736f, 18.408072f);
generalPath.lineTo(28.505236f, 18.283072f);
generalPath.lineTo(25.460018f, 21.456026f);
generalPath.lineTo(22.059557f, 13.665616f);
generalPath.lineTo(19.424723f, 20.604265f);
generalPath.lineTo(15.90619f, 8.333659f);
generalPath.lineTo(11.515689f, 20.012278f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 0.43181816f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_6
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(6.84375f, 6.96875f);
generalPath.lineTo(6.84375f, 15.795073f);
generalPath.curveTo(10.513653f, 16.48318f, 14.582567f, 16.875f, 18.875f, 16.875f);
generalPath.curveTo(27.810295f, 16.875f, 35.81226f, 15.21019f, 41.15625f, 12.596829f);
generalPath.lineTo(41.15625f, 6.96875f);
generalPath.lineTo(6.84375f, 6.96875f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(8.820780754089355, 12.537569999694824), new Point2D.Double(12.499242782592773, 24.238262176513672), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 255)) : new Color(255, 255, 255, 255)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 0)) : new Color(255, 255, 255, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(3.809546947479248f, 0.0f, 0.0f, 1.7503249645233154f, -16.0003604888916f, -15.787190437316895f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 0.07954544f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.1875f, 0.6875f));
// _0_0_7
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_7_0
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(39.125f, 37.8125f);
generalPath.lineTo(38.0625f, 37.34375f);
generalPath.curveTo(37.805687f, 37.204185f, 37.500156f, 37.125f, 37.1875f, 37.125f);
generalPath.curveTo(36.187f, 37.125f, 35.375f, 37.937f, 35.375f, 38.9375f);
generalPath.curveTo(35.375f, 39.938f, 36.187f, 40.75f, 37.1875f, 40.75f);
generalPath.curveTo(38.156734f, 40.75f, 38.951427f, 39.98848f, 39.0f, 39.03125f);
generalPath.curveTo(39.000393f, 39.02353f, 38.999706f, 39.007744f, 39.0f, 39.0f);
generalPath.lineTo(39.125f, 37.8125f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255);
g.setPaint(paint);
g.fill(shape);
paint = (colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,10.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(39.125f, 37.8125f);
generalPath.lineTo(38.0625f, 37.34375f);
generalPath.curveTo(37.805687f, 37.204185f, 37.500156f, 37.125f, 37.1875f, 37.125f);
generalPath.curveTo(36.187f, 37.125f, 35.375f, 37.937f, 35.375f, 38.9375f);
generalPath.curveTo(35.375f, 39.938f, 36.187f, 40.75f, 37.1875f, 40.75f);
generalPath.curveTo(38.156734f, 40.75f, 38.951427f, 39.98848f, 39.0f, 39.03125f);
generalPath.curveTo(39.000393f, 39.02353f, 38.999706f, 39.007744f, 39.0f, 39.0f);
generalPath.lineTo(39.125f, 37.8125f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_7_1
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(28.992525f, 37.54466f);
generalPath.lineTo(28.893139f, 38.709835f);
generalPath.curveTo(28.855812f, 38.99973f, 28.892397f, 39.31323f, 29.005384f, 39.604755f);
generalPath.curveTo(29.366953f, 40.537636f, 30.417519f, 41.001312f, 31.350403f, 40.639748f);
generalPath.curveTo(32.283283f, 40.27818f, 32.74696f, 39.22761f, 32.385395f, 38.29473f);
generalPath.curveTo(32.035126f, 37.391003f, 31.037884f, 36.92522f, 30.127792f, 37.225857f);
generalPath.curveTo(30.120453f, 37.228283f, 30.105982f, 37.234627f, 30.098654f, 37.237152f);
generalPath.lineTo(28.992525f, 37.54466f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255);
g.setPaint(paint);
g.fill(shape);
paint = (colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,10.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(28.992525f, 37.54466f);
generalPath.lineTo(28.893139f, 38.709835f);
generalPath.curveTo(28.855812f, 38.99973f, 28.892397f, 39.31323f, 29.005384f, 39.604755f);
generalPath.curveTo(29.366953f, 40.537636f, 30.417519f, 41.001312f, 31.350403f, 40.639748f);
generalPath.curveTo(32.283283f, 40.27818f, 32.74696f, 39.22761f, 32.385395f, 38.29473f);
generalPath.curveTo(32.035126f, 37.391003f, 31.037884f, 36.92522f, 30.127792f, 37.225857f);
generalPath.curveTo(30.120453f, 37.228283f, 30.105982f, 37.234627f, 30.098654f, 37.237152f);
generalPath.lineTo(28.992525f, 37.54466f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_7_2
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(25.5f, 37.8125f);
generalPath.lineTo(24.5625f, 37.34375f);
generalPath.curveTo(24.305689f, 37.204185f, 24.000156f, 37.125f, 23.6875f, 37.125f);
generalPath.curveTo(22.687f, 37.125f, 21.875f, 37.937f, 21.875f, 38.9375f);
generalPath.curveTo(21.875f, 39.938f, 22.687f, 40.75f, 23.6875f, 40.75f);
generalPath.curveTo(24.656734f, 40.75f, 25.451426f, 39.98848f, 25.5f, 39.03125f);
generalPath.curveTo(25.500393f, 39.02353f, 25.499704f, 39.007744f, 25.5f, 39.0f);
generalPath.lineTo(25.5f, 37.8125f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255);
g.setPaint(paint);
g.fill(shape);
paint = (colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,10.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(25.5f, 37.8125f);
generalPath.lineTo(24.5625f, 37.34375f);
generalPath.curveTo(24.305689f, 37.204185f, 24.000156f, 37.125f, 23.6875f, 37.125f);
generalPath.curveTo(22.687f, 37.125f, 21.875f, 37.937f, 21.875f, 38.9375f);
generalPath.curveTo(21.875f, 39.938f, 22.687f, 40.75f, 23.6875f, 40.75f);
generalPath.curveTo(24.656734f, 40.75f, 25.451426f, 39.98848f, 25.5f, 39.03125f);
generalPath.curveTo(25.500393f, 39.02353f, 25.499704f, 39.007744f, 25.5f, 39.0f);
generalPath.lineTo(25.5f, 37.8125f);
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
// _0_0_8
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(39.125f, 37.8125f);
generalPath.lineTo(38.0625f, 37.34375f);
generalPath.curveTo(37.805687f, 37.204185f, 37.500156f, 37.125f, 37.1875f, 37.125f);
generalPath.curveTo(36.187f, 37.125f, 35.375f, 37.937f, 35.375f, 38.9375f);
generalPath.curveTo(35.375f, 39.938f, 36.187f, 40.75f, 37.1875f, 40.75f);
generalPath.curveTo(38.156734f, 40.75f, 38.951427f, 39.98848f, 39.0f, 39.03125f);
generalPath.curveTo(39.000393f, 39.02353f, 38.999706f, 39.007744f, 39.0f, 39.0f);
generalPath.lineTo(39.125f, 37.8125f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(224, 226, 223, 255)) : new Color(224, 226, 223, 255);
g.setPaint(paint);
g.fill(shape);
paint = (colorFilter != null) ? colorFilter.filter(new Color(155, 157, 153, 255)) : new Color(155, 157, 153, 255);
stroke = new BasicStroke(1.0f,0,0,10.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(39.125f, 37.8125f);
generalPath.lineTo(38.0625f, 37.34375f);
generalPath.curveTo(37.805687f, 37.204185f, 37.500156f, 37.125f, 37.1875f, 37.125f);
generalPath.curveTo(36.187f, 37.125f, 35.375f, 37.937f, 35.375f, 38.9375f);
generalPath.curveTo(35.375f, 39.938f, 36.187f, 40.75f, 37.1875f, 40.75f);
generalPath.curveTo(38.156734f, 40.75f, 38.951427f, 39.98848f, 39.0f, 39.03125f);
generalPath.curveTo(39.000393f, 39.02353f, 38.999706f, 39.007744f, 39.0f, 39.0f);
generalPath.lineTo(39.125f, 37.8125f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
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
generalPath.moveTo(28.992525f, 37.54466f);
generalPath.lineTo(28.893139f, 38.709835f);
generalPath.curveTo(28.855812f, 38.99973f, 28.892397f, 39.31323f, 29.005384f, 39.604755f);
generalPath.curveTo(29.366953f, 40.537636f, 30.417519f, 41.001312f, 31.350403f, 40.639748f);
generalPath.curveTo(32.283283f, 40.27818f, 32.74696f, 39.22761f, 32.385395f, 38.29473f);
generalPath.curveTo(32.035126f, 37.391003f, 31.037884f, 36.92522f, 30.127792f, 37.225857f);
generalPath.curveTo(30.120453f, 37.228283f, 30.105982f, 37.234627f, 30.098654f, 37.237152f);
generalPath.lineTo(28.992525f, 37.54466f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(224, 226, 223, 255)) : new Color(224, 226, 223, 255);
g.setPaint(paint);
g.fill(shape);
paint = (colorFilter != null) ? colorFilter.filter(new Color(155, 157, 153, 255)) : new Color(155, 157, 153, 255);
stroke = new BasicStroke(1.0f,0,0,10.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(28.992525f, 37.54466f);
generalPath.lineTo(28.893139f, 38.709835f);
generalPath.curveTo(28.855812f, 38.99973f, 28.892397f, 39.31323f, 29.005384f, 39.604755f);
generalPath.curveTo(29.366953f, 40.537636f, 30.417519f, 41.001312f, 31.350403f, 40.639748f);
generalPath.curveTo(32.283283f, 40.27818f, 32.74696f, 39.22761f, 32.385395f, 38.29473f);
generalPath.curveTo(32.035126f, 37.391003f, 31.037884f, 36.92522f, 30.127792f, 37.225857f);
generalPath.curveTo(30.120453f, 37.228283f, 30.105982f, 37.234627f, 30.098654f, 37.237152f);
generalPath.lineTo(28.992525f, 37.54466f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_10
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(25.5f, 37.8125f);
generalPath.lineTo(24.5625f, 37.34375f);
generalPath.curveTo(24.305689f, 37.204185f, 24.000156f, 37.125f, 23.6875f, 37.125f);
generalPath.curveTo(22.687f, 37.125f, 21.875f, 37.937f, 21.875f, 38.9375f);
generalPath.curveTo(21.875f, 39.938f, 22.687f, 40.75f, 23.6875f, 40.75f);
generalPath.curveTo(24.656734f, 40.75f, 25.451426f, 39.98848f, 25.5f, 39.03125f);
generalPath.curveTo(25.500393f, 39.02353f, 25.499704f, 39.007744f, 25.5f, 39.0f);
generalPath.lineTo(25.5f, 37.8125f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(224, 226, 223, 255)) : new Color(224, 226, 223, 255);
g.setPaint(paint);
g.fill(shape);
paint = (colorFilter != null) ? colorFilter.filter(new Color(155, 157, 153, 255)) : new Color(155, 157, 153, 255);
stroke = new BasicStroke(1.0f,0,0,10.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(25.5f, 37.8125f);
generalPath.lineTo(24.5625f, 37.34375f);
generalPath.curveTo(24.305689f, 37.204185f, 24.000156f, 37.125f, 23.6875f, 37.125f);
generalPath.curveTo(22.687f, 37.125f, 21.875f, 37.937f, 21.875f, 38.9375f);
generalPath.curveTo(21.875f, 39.938f, 22.687f, 40.75f, 23.6875f, 40.75f);
generalPath.curveTo(24.656734f, 40.75f, 25.451426f, 39.98848f, 25.5f, 39.03125f);
generalPath.curveTo(25.500393f, 39.02353f, 25.499704f, 39.007744f, 25.5f, 39.0f);
generalPath.lineTo(25.5f, 37.8125f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 0.5738636f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_11
paint = new LinearGradientPaint(new Point2D.Double(35.69420623779297, 37.333858489990234), new Point2D.Double(15.044075012207031, 5.958856582641602), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 255)) : new Color(255, 255, 255, 255)),((colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 0)) : new Color(255, 255, 255, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(1.0f,1,1,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(4.3192057609558105, 4.635766983032227, 39.34986877441406, 30.64617919921875, 5.13934326171875, 5.13934326171875);
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
        return 0.0;
    }

    /**
     * Returns the Y of the bounding box of the original SVG image.
     * 
     * @return The Y of the bounding box of the original SVG image.
     */
    public static double getOrigY() {
        return 1.160079836845398;
    }

	/**
	 * Returns the width of the bounding box of the original SVG image.
	 * 
	 * @return The width of the bounding box of the original SVG image.
	 */
	public static double getOrigWidth() {
		return 48.0;
	}

	/**
	 * Returns the height of the bounding box of the original SVG image.
	 * 
	 * @return The height of the bounding box of the original SVG image.
	 */
	public static double getOrigHeight() {
		return 45.787071228027344;
	}

	/** The current width of this icon. */
	private int width;

    /** The current height of this icon. */
	private int height;

	/**
	 * Creates a new transcoded SVG image. This is marked as private to indicate that app
	 * code should be using the {@link #of(int, int)} method to obtain a pre-configured instance.
	 */
	private TangoRUtilities_system_monitor() {
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
       TangoRUtilities_system_monitor base = new TangoRUtilities_system_monitor();
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
       TangoRUtilities_system_monitor base = new TangoRUtilities_system_monitor();
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
        return TangoRUtilities_system_monitor::new;
    }
}

