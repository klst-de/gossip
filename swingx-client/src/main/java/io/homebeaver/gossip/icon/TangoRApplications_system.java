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
public class TangoRApplications_system implements RadianceIcon {
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
g.transform(new AffineTransform(1.1863800287246704f, 0.0f, 0.0f, 1.1863800287246704f, -4.539687156677246f, -7.794678211212158f));
// _0_0
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(44.285713f, 38.714287f);
generalPath.curveTo(44.31765f, 42.239277f, 40.526283f, 45.503265f, 34.347244f, 47.27033f);
generalPath.curveTo(28.168203f, 49.037388f, 20.54608f, 49.037388f, 14.36704f, 47.27033f);
generalPath.curveTo(8.187999f, 45.503265f, 4.396634f, 42.239277f, 4.42857f, 38.714287f);
generalPath.curveTo(4.396634f, 35.189297f, 8.187999f, 31.925306f, 14.36704f, 30.158245f);
generalPath.curveTo(20.54608f, 28.391184f, 28.168203f, 28.391184f, 34.347244f, 30.158245f);
generalPath.curveTo(40.526283f, 31.925306f, 44.31765f, 35.189297f, 44.285713f, 38.714287f);
generalPath.closePath();
shape = generalPath;
paint = new RadialGradientPaint(new Point2D.Double(14.287617683410645, 68.87297058105469), 11.68987f, new Point2D.Double(14.287617683410645, 72.56800079345703), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 83)) : new Color(0, 0, 0, 83)),((colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 0)) : new Color(0, 0, 0, 0))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.3992600440979004f, 0.0f, 0.0f, 0.513260006904602f, 4.365074157714844f, 4.839284896850586f));
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_0
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(22.699526f, 0.94746965f);
generalPath.curveTo(22.22635f, 0.9798452f, 21.766438f, 1.0531317f, 21.301674f, 1.1063166f);
generalPath.lineTo(21.269903f, 1.1063166f);
generalPath.lineTo(20.157974f, 7.1742673f);
generalPath.curveTo(18.345621f, 7.5870047f, 16.640562f, 8.287457f, 15.106644f, 9.239277f);
generalPath.lineTo(10.118853f, 5.649338f);
generalPath.curveTo(8.770521f, 6.6961412f, 7.543552f, 7.917005f, 6.465374f, 9.239277f);
generalPath.lineTo(9.928236f, 14.290607f);
generalPath.curveTo(8.876814f, 15.89739f, 8.086153f, 17.732094f, 7.640841f, 19.659634f);
generalPath.curveTo(7.640765f, 19.668745f, 7.640779f, 19.689816f, 7.640841f, 19.691402f);
generalPath.lineTo(1.60466f, 20.644484f);
generalPath.curveTo(1.494303f, 21.545853f, 1.4458131f, 22.477388f, 1.4458131f, 23.40842f);
generalPath.curveTo(1.4458131f, 24.170174f, 1.4668461f, 24.92175f, 1.541121f, 25.664045f);
generalPath.lineTo(7.577303f, 26.744204f);
generalPath.curveTo(8.0066f, 28.840364f, 8.822112f, 30.797989f, 9.960006f, 32.52623f);
generalPath.lineTo(6.3700657f, 37.450485f);
generalPath.curveTo(7.3982005f, 38.726868f, 8.585171f, 39.888966f, 9.864697f, 40.913345f);
generalPath.lineTo(14.947798f, 37.418713f);
generalPath.curveTo(16.724274f, 38.551956f, 18.707342f, 39.346603f, 20.856901f, 39.737877f);
generalPath.lineTo(21.809982f, 45.742287f);
generalPath.curveTo(22.487236f, 45.803932f, 23.181757f, 45.805824f, 23.874992f, 45.805824f);
generalPath.curveTo(24.853678f, 45.805824f, 25.788513f, 45.768734f, 26.734236f, 45.646976f);
generalPath.lineTo(27.877934f, 39.515488f);
generalPath.curveTo(29.918861f, 39.007584f, 31.836113f, 38.126488f, 33.501114f, 36.94217f);
generalPath.lineTo(38.393597f, 40.50034f);
generalPath.curveTo(39.662365f, 39.420895f, 40.822582f, 38.180153f, 41.824688f, 36.84686f);
generalPath.lineTo(38.266518f, 31.700222f);
generalPath.curveTo(39.230125f, 30.036026f, 39.897816f, 28.199856f, 40.236217f, 26.23589f);
generalPath.lineTo(46.24063f, 25.282808f);
generalPath.curveTo(46.29329f, 24.656218f, 46.30417f, 24.048544f, 46.30417f, 23.408415f);
generalPath.curveTo(46.30417f, 22.296015f, 46.174873f, 21.205315f, 46.018246f, 20.13617f);
generalPath.lineTo(39.918526f, 19.024242f);
generalPath.curveTo(39.440517f, 17.259163f, 38.65621f, 15.612362f, 37.6629f, 14.131758f);
generalPath.lineTo(41.25284f, 9.207504f);
generalPath.curveTo(40.14007f, 7.8466496f, 38.870716f, 6.5895233f, 37.472282f, 5.522257f);
generalPath.lineTo(32.293873f, 9.080427f);
generalPath.curveTo(30.805546f, 8.200199f, 29.203894f, 7.5248137f, 27.464928f, 7.142495f);
generalPath.lineTo(26.511847f, 1.1063137f);
generalPath.curveTo(25.644365f, 1.0042701f, 24.769745f, 0.94746685f, 23.874989f, 0.94746685f);
generalPath.curveTo(23.633163f, 0.94746685f, 23.384283f, 0.93985784f, 23.144293f, 0.94746685f);
generalPath.curveTo(23.027298f, 0.9511763f, 22.911522f, 0.94066066f, 22.79483f, 0.94746685f);
generalPath.curveTo(22.763226f, 0.9493102f, 22.731068f, 0.94530845f, 22.699522f, 0.94746685f);
generalPath.closePath();
generalPath.moveTo(23.52553f, 16.387386f);
generalPath.curveTo(23.641592f, 16.381496f, 23.757473f, 16.387386f, 23.874992f, 16.387386f);
generalPath.curveTo(27.6356f, 16.387386f, 30.705408f, 19.457195f, 30.705408f, 23.217802f);
generalPath.curveTo(30.70541f, 26.978407f, 27.635597f, 30.016449f, 23.874992f, 30.016449f);
generalPath.curveTo(20.114388f, 30.01645f, 17.076347f, 26.978409f, 17.076347f, 23.217804f);
generalPath.curveTo(17.07635f, 19.574718f, 19.927559f, 16.569965f, 23.52553f, 16.387388f);
generalPath.closePath();
shape = generalPath;
paint = new LinearGradientPaint(new Point2D.Double(99.77729797363281, 15.423800468444824), new Point2D.Double(153.00050354003906, 248.631103515625), new float[] {0.0f,1.0f}, new Color[] {((colorFilter != null) ? colorFilter.filter(new Color(24, 67, 117, 255)) : new Color(24, 67, 117, 255)),((colorFilter != null) ? colorFilter.filter(new Color(200, 189, 220, 255)) : new Color(200, 189, 220, 255))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.20069000124931335f, 0.0f, 0.0f, 0.20069000124931335f, -54.335758209228516f, -1.0507869720458984f));
g.setPaint(paint);
g.fill(shape);
paint = (colorFilter != null) ? colorFilter.filter(new Color(63, 69, 97, 255)) : new Color(63, 69, 97, 255);
stroke = new BasicStroke(1.0f,1,1,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(22.699526f, 0.94746965f);
generalPath.curveTo(22.22635f, 0.9798452f, 21.766438f, 1.0531317f, 21.301674f, 1.1063166f);
generalPath.lineTo(21.269903f, 1.1063166f);
generalPath.lineTo(20.157974f, 7.1742673f);
generalPath.curveTo(18.345621f, 7.5870047f, 16.640562f, 8.287457f, 15.106644f, 9.239277f);
generalPath.lineTo(10.118853f, 5.649338f);
generalPath.curveTo(8.770521f, 6.6961412f, 7.543552f, 7.917005f, 6.465374f, 9.239277f);
generalPath.lineTo(9.928236f, 14.290607f);
generalPath.curveTo(8.876814f, 15.89739f, 8.086153f, 17.732094f, 7.640841f, 19.659634f);
generalPath.curveTo(7.640765f, 19.668745f, 7.640779f, 19.689816f, 7.640841f, 19.691402f);
generalPath.lineTo(1.60466f, 20.644484f);
generalPath.curveTo(1.494303f, 21.545853f, 1.4458131f, 22.477388f, 1.4458131f, 23.40842f);
generalPath.curveTo(1.4458131f, 24.170174f, 1.4668461f, 24.92175f, 1.541121f, 25.664045f);
generalPath.lineTo(7.577303f, 26.744204f);
generalPath.curveTo(8.0066f, 28.840364f, 8.822112f, 30.797989f, 9.960006f, 32.52623f);
generalPath.lineTo(6.3700657f, 37.450485f);
generalPath.curveTo(7.3982005f, 38.726868f, 8.585171f, 39.888966f, 9.864697f, 40.913345f);
generalPath.lineTo(14.947798f, 37.418713f);
generalPath.curveTo(16.724274f, 38.551956f, 18.707342f, 39.346603f, 20.856901f, 39.737877f);
generalPath.lineTo(21.809982f, 45.742287f);
generalPath.curveTo(22.487236f, 45.803932f, 23.181757f, 45.805824f, 23.874992f, 45.805824f);
generalPath.curveTo(24.853678f, 45.805824f, 25.788513f, 45.768734f, 26.734236f, 45.646976f);
generalPath.lineTo(27.877934f, 39.515488f);
generalPath.curveTo(29.918861f, 39.007584f, 31.836113f, 38.126488f, 33.501114f, 36.94217f);
generalPath.lineTo(38.393597f, 40.50034f);
generalPath.curveTo(39.662365f, 39.420895f, 40.822582f, 38.180153f, 41.824688f, 36.84686f);
generalPath.lineTo(38.266518f, 31.700222f);
generalPath.curveTo(39.230125f, 30.036026f, 39.897816f, 28.199856f, 40.236217f, 26.23589f);
generalPath.lineTo(46.24063f, 25.282808f);
generalPath.curveTo(46.29329f, 24.656218f, 46.30417f, 24.048544f, 46.30417f, 23.408415f);
generalPath.curveTo(46.30417f, 22.296015f, 46.174873f, 21.205315f, 46.018246f, 20.13617f);
generalPath.lineTo(39.918526f, 19.024242f);
generalPath.curveTo(39.440517f, 17.259163f, 38.65621f, 15.612362f, 37.6629f, 14.131758f);
generalPath.lineTo(41.25284f, 9.207504f);
generalPath.curveTo(40.14007f, 7.8466496f, 38.870716f, 6.5895233f, 37.472282f, 5.522257f);
generalPath.lineTo(32.293873f, 9.080427f);
generalPath.curveTo(30.805546f, 8.200199f, 29.203894f, 7.5248137f, 27.464928f, 7.142495f);
generalPath.lineTo(26.511847f, 1.1063137f);
generalPath.curveTo(25.644365f, 1.0042701f, 24.769745f, 0.94746685f, 23.874989f, 0.94746685f);
generalPath.curveTo(23.633163f, 0.94746685f, 23.384283f, 0.93985784f, 23.144293f, 0.94746685f);
generalPath.curveTo(23.027298f, 0.9511763f, 22.911522f, 0.94066066f, 22.79483f, 0.94746685f);
generalPath.curveTo(22.763226f, 0.9493102f, 22.731068f, 0.94530845f, 22.699522f, 0.94746685f);
generalPath.closePath();
generalPath.moveTo(23.52553f, 16.387386f);
generalPath.curveTo(23.641592f, 16.381496f, 23.757473f, 16.387386f, 23.874992f, 16.387386f);
generalPath.curveTo(27.6356f, 16.387386f, 30.705408f, 19.457195f, 30.705408f, 23.217802f);
generalPath.curveTo(30.70541f, 26.978407f, 27.635597f, 30.016449f, 23.874992f, 30.016449f);
generalPath.curveTo(20.114388f, 30.01645f, 17.076347f, 26.978409f, 17.076347f, 23.217804f);
generalPath.curveTo(17.07635f, 19.574718f, 19.927559f, 16.569965f, 23.52553f, 16.387388f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 0.64772725f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(0.616599977016449f, 0.0f, 0.0f, 0.616599977016449f, 9.382019996643066f, 8.539673805236816f));
// _0_1_1
paint = (colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 255)) : new Color(255, 255, 255, 255);
stroke = new BasicStroke(1.6218005f,0,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(36.239223f, 23.781593f);
generalPath.curveTo(36.25962f, 28.342402f, 33.83816f, 32.565517f, 29.89175f, 34.85183f);
generalPath.curveTo(25.94534f, 37.13814f, 21.077263f, 37.13814f, 17.130852f, 34.85183f);
generalPath.curveTo(13.18444f, 32.565517f, 10.762982f, 28.342402f, 10.783379f, 23.781593f);
generalPath.curveTo(10.762982f, 19.220785f, 13.18444f, 14.997669f, 17.130852f, 12.711357f);
generalPath.curveTo(21.077263f, 10.425044f, 25.94534f, 10.425044f, 29.89175f, 12.711357f);
generalPath.curveTo(33.83816f, 14.997669f, 36.25962f, 19.220785f, 36.239223f, 23.781593f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 0.34659088f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_2
paint = (colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 255)) : new Color(255, 255, 255, 255);
stroke = new BasicStroke(0.9999992f,0,0,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(21.995808f, 2.148467f);
generalPath.lineTo(21.103024f, 8.023524f);
generalPath.curveTo(19.404253f, 8.410395f, 16.279442f, 9.593603f, 14.841657f, 10.485771f);
generalPath.lineTo(10.091974f, 6.940627f);
generalPath.curveTo(8.828144f, 7.921826f, 8.741473f, 7.988366f, 7.7308664f, 9.227769f);
generalPath.lineTo(11.165062f, 14.320988f);
generalPath.curveTo(10.179536f, 15.82707f, 8.995795f, 18.510983f, 8.570777f, 20.42893f);
generalPath.lineTo(2.552987f, 21.443356f);
generalPath.curveTo(2.449546f, 22.288235f, 2.499259f, 24.096529f, 2.5688791f, 24.792303f);
generalPath.lineTo(8.317097f, 25.82782f);
generalPath.curveTo(8.71949f, 27.79261f, 10.225324f, 30.95523f, 11.2919035f, 32.57516f);
generalPath.lineTo(7.6569014f, 37.37772f);
generalPath.curveTo(8.620601f, 38.574112f, 8.813474f, 38.68359f, 10.01281f, 39.643772f);
generalPath.lineTo(14.873441f, 36.082733f);
generalPath.curveTo(16.53858f, 37.144955f, 19.84373f, 38.43711f, 21.85857f, 38.80386f);
generalPath.lineTo(22.656298f, 44.60495f);
generalPath.curveTo(23.291107f, 44.662735f, 25.044828f, 44.824825f, 25.931282f, 44.710697f);
generalPath.lineTo(26.824064f, 38.671818f);
generalPath.curveTo(28.737082f, 38.195747f, 32.042538f, 36.838894f, 33.603188f, 35.728794f);
generalPath.lineTo(38.458622f, 39.236954f);
generalPath.curveTo(39.647877f, 38.225163f, 39.65853f, 38.072704f, 40.59783f, 36.822975f);
generalPath.lineTo(36.999813f, 31.708664f);
generalPath.curveTo(37.903027f, 30.148764f, 39.0709f, 27.098064f, 39.388096f, 25.257183f);
generalPath.lineTo(45.279045f, 24.27974f);
generalPath.curveTo(45.3284f, 23.69242f, 45.330803f, 22.054573f, 45.18399f, 21.052435f);
generalPath.lineTo(39.18209f, 20.016918f);
generalPath.curveTo(38.73404f, 18.36246f, 37.196415f, 15.381149f, 36.265358f, 13.993338f);
generalPath.lineTo(40.080074f, 9.190781f);
generalPath.curveTo(39.037052f, 7.915213f, 38.64924f, 7.7401953f, 37.338448f, 6.739816f);
generalPath.lineTo(32.313995f, 10.337833f);
generalPath.curveTo(30.918941f, 9.512773f, 28.137096f, 8.255036f, 26.507114f, 7.896679f);
generalPath.lineTo(25.619528f, 2.1484618f);
generalPath.curveTo(24.806414f, 2.0528135f, 22.460487f, 2.0952868f, 21.995808f, 2.1484618f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(transformsStack.pop());
g.setComposite(AlphaComposite.getInstance(3, 0.5f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_3
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(10.102903f, 6.2970657f);
generalPath.curveTo(8.754569f, 7.3438697f, 8.165647f, 7.971923f, 7.0874686f, 9.294194f);
generalPath.lineTo(10.489927f, 14.259153f);
generalPath.curveTo(9.438507f, 15.857756f, 8.331628f, 18.426115f, 8.142386f, 19.987705f);
generalPath.lineTo(2.0798864f, 21.031898f);
generalPath.curveTo(2.0109134f, 21.595255f, 1.9062505f, 22.884802f, 1.9062505f, 22.884802f);
generalPath.lineTo(2.0830271f, 24.447302f);
generalPath.curveTo(2.5107572f, 24.535637f, 2.923182f, 24.617817f, 3.3642771f, 24.666052f);
generalPath.lineTo(3.8642771f, 23.134802f);
generalPath.curveTo(4.208318f, 23.163279f, 4.54393f, 23.197302f, 4.895527f, 23.197302f);
generalPath.curveTo(5.246735f, 23.197302f, 5.613985f, 23.163471f, 5.958027f, 23.134802f);
generalPath.lineTo(6.426777f, 24.666052f);
generalPath.curveTo(6.868065f, 24.617817f, 7.3115487f, 24.535637f, 7.739277f, 24.447302f);
generalPath.lineTo(7.739277f, 22.884802f);
generalPath.curveTo(8.425034f, 22.72518f, 9.071278f, 22.497044f, 9.708027f, 22.228552f);
generalPath.lineTo(10.645527f, 23.509802f);
generalPath.curveTo(11.047878f, 23.327707f, 11.421123f, 23.133984f, 11.801777f, 22.916052f);
generalPath.lineTo(11.301777f, 21.416052f);
generalPath.curveTo(11.89901f, 21.053802f, 12.463529f, 20.620705f, 12.989277f, 20.166052f);
generalPath.lineTo(14.270527f, 21.103552f);
generalPath.curveTo(14.596162f, 20.806973f, 14.91164f, 20.49169f, 15.208027f, 20.166052f);
generalPath.lineTo(14.270527f, 18.916052f);
generalPath.curveTo(14.725373f, 18.390305f, 15.127027f, 17.82617f, 15.489277f, 17.228552f);
generalPath.lineTo(16.989277f, 17.697302f);
generalPath.curveTo(17.207209f, 17.316454f, 17.432571f, 16.943209f, 17.614277f, 16.541052f);
generalPath.lineTo(16.333027f, 15.603552f);
generalPath.curveTo(16.601517f, 14.966803f, 16.798016f, 14.3205595f, 16.958027f, 13.634802f);
generalPath.lineTo(18.551777f, 13.634802f);
generalPath.curveTo(18.640112f, 13.207075f, 18.691236f, 12.76359f, 18.739277f, 12.322302f);
generalPath.lineTo(17.239277f, 11.853552f);
generalPath.curveTo(17.268139f, 11.509704f, 17.301777f, 11.142455f, 17.301777f, 10.791052f);
generalPath.curveTo(17.301775f, 10.439649f, 17.267754f, 10.104038f, 17.239277f, 9.759802f);
generalPath.lineTo(18.739277f, 9.291052f);
generalPath.curveTo(18.69373f, 8.871164f, 18.633686f, 8.449053f, 18.551777f, 8.041052f);
generalPath.curveTo(17.404348f, 8.440352f, 15.999117f, 9.194171f, 14.983265f, 9.824523f);
generalPath.lineTo(10.102903f, 6.297064f);
generalPath.closePath();
generalPath.moveTo(37.23664f, 17.217754f);
generalPath.curveTo(36.85286f, 17.39913f, 36.49f, 17.60351f, 36.123238f, 17.813295f);
generalPath.lineTo(36.692886f, 19.548136f);
generalPath.curveTo(35.995792f, 19.970436f, 35.338158f, 20.467825f, 34.72501f, 20.99815f);
generalPath.lineTo(33.2491f, 19.910637f);
generalPath.curveTo(32.869015f, 20.256536f, 32.507328f, 20.618221f, 32.16159f, 20.99815f);
generalPath.lineTo(33.2491f, 22.474058f);
generalPath.curveTo(32.718773f, 23.08737f, 32.221546f, 23.745f, 31.799086f, 24.441936f);
generalPath.lineTo(31.25533f, 24.260685f);
generalPath.curveTo(31.207647f, 24.960968f, 31.01895f, 25.62217f, 30.737467f, 26.228563f);
generalPath.lineTo(30.84104f, 26.306242f);
generalPath.curveTo(30.527884f, 27.048922f, 30.276491f, 27.83664f, 30.09014f, 28.636623f);
generalPath.lineTo(28.614231f, 28.636623f);
generalPath.curveTo(28.477947f, 28.722075f, 28.343678f, 28.821684f, 28.19994f, 28.895554f);
generalPath.curveTo(28.12157f, 29.310822f, 28.065027f, 29.71288f, 28.018688f, 30.138424f);
generalPath.lineTo(29.77942f, 30.708073f);
generalPath.curveTo(29.746035f, 31.109348f, 29.727634f, 31.515268f, 29.727634f, 31.92505f);
generalPath.curveTo(29.727633f, 32.33499f, 29.746035f, 32.740753f, 29.77942f, 33.14203f);
generalPath.lineTo(28.018688f, 33.711678f);
generalPath.curveTo(28.074707f, 34.226433f, 28.14868f, 34.74035f, 28.251726f, 35.239372f);
generalPath.lineTo(30.090137f, 35.213478f);
generalPath.curveTo(30.218256f, 35.763466f, 30.393202f, 36.320915f, 30.582108f, 36.844746f);
generalPath.curveTo(31.327024f, 36.557465f, 32.05594f, 36.21456f, 32.731236f, 35.80902f);
generalPath.curveTo(32.31965f, 34.59298f, 32.083908f, 33.279915f, 32.083908f, 31.925053f);
generalPath.curveTo(32.083908f, 26.72712f, 35.37629f, 22.288399f, 39.98131f, 20.583862f);
generalPath.lineTo(38.893803f, 20.402609f);
generalPath.curveTo(38.671013f, 19.579947f, 38.382477f, 18.774017f, 38.013435f, 18.020441f);
generalPath.curveTo(38.002583f, 17.998278f, 37.998512f, 17.96486f, 37.98754f, 17.94276f);
generalPath.lineTo(37.935757f, 17.890974f);
generalPath.lineTo(37.23664f, 17.217752f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 255)) : new Color(255, 255, 255, 255);
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
        return 0.6763917207717896;
    }

    /**
     * Returns the Y of the bounding box of the original SVG image.
     * 
     * @return The Y of the bounding box of the original SVG image.
     */
    public static double getOrigY() {
        return 0.44400766491889954;
    }

	/**
	 * Returns the width of the bounding box of the original SVG image.
	 * 
	 * @return The width of the bounding box of the original SVG image.
	 */
	public static double getOrigWidth() {
		return 47.3236083984375;
	}

	/**
	 * Returns the height of the bounding box of the original SVG image.
	 * 
	 * @return The height of the bounding box of the original SVG image.
	 */
	public static double getOrigHeight() {
		return 47.555992126464844;
	}

	/** The current width of this icon. */
	private int width;

    /** The current height of this icon. */
	private int height;

	/**
	 * Creates a new transcoded SVG image. This is marked as private to indicate that app
	 * code should be using the {@link #of(int, int)} method to obtain a pre-configured instance.
	 */
	private TangoRApplications_system() {
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
       TangoRApplications_system base = new TangoRApplications_system();
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
       TangoRApplications_system base = new TangoRApplications_system();
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
        return TangoRApplications_system::new;
    }
}

