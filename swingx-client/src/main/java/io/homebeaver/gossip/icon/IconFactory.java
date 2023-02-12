package io.homebeaver.gossip.icon;

import org.jdesktop.swingx.demos.svg.FeatheRcalendar;
import org.jdesktop.swingx.demos.svg.FeatheRcheck;
import org.jdesktop.swingx.demos.svg.FeatheRcrosshair;
import org.jdesktop.swingx.demos.svg.FeatheRdatabase;
import org.jdesktop.swingx.demos.svg.FeatheRfeather;
import org.jdesktop.swingx.demos.svg.FeatheRserver;
import org.jdesktop.swingx.demos.svg.FeatheRx;
import org.jdesktop.swingx.icon.JXIcon;
import org.jdesktop.swingx.icon.TrafficLightGreenIcon;
import org.jdesktop.swingx.icon.TrafficLightRedIcon;
import org.jdesktop.swingx.icon.TrafficLightYellowIcon;

/*
	RLI		("Red_Light_Icon"), TLGREEN
	YLI		("Yellow_Light_Icon"),
	GLI		("Green_Light_Icon"),
	SERVER	("server"),
	DATABASE("database"),
	CALENDAR("calendar"),
	ZOOM	("crosshair"), // ZoomAcross 
	CANCEL	("x"), TangoRDialog_error_round
	OK	("check"), TangoRDialog_accept
	
	FeatheRdatabase.of(JXIcon.SMALL_ICON, JXIcon.SMALL_ICON
	FeatheRserver.of(JXIcon.SMALL_ICON, JXIcon.SMALL_ICON));

		RadianceIcon base = TangoRDialog_accept.factory().createNewIcon();
		base.setDimension(new Dimension(JXIcon.ACTION_ICON, JXIcon.ACTION_ICON));
	oder
		RadianceIcon icon = TangoRDialog_accept.of(JXIcon.ACTION_ICON, JXIcon.ACTION_ICON);

 */
public class IconFactory {

	// vorerst alle nur monochrom feather und tango! TODO mit map
	private static boolean monochrom = false;
	
	public static JXIcon get(String name, int size) {
		if("SERVER".equalsIgnoreCase(name))
			return getSERVER(size);
		else if("DATABASE".equalsIgnoreCase(name))
			return getDATABASE(size);
		else if("CALENDAR".equalsIgnoreCase(name))
			return getCALENDAR(size);
		else if("CANCEL".equalsIgnoreCase(name))
			return getCANCEL(size);
		else if("OK".equalsIgnoreCase(name))
			return getOK(size);
		else return FeatheRfeather.of(size, size);
			
	}
	
	public static JXIcon getTLRED(int size) {
		return TrafficLightRedIcon.of(size, size);
	}
	public static JXIcon getTLYELLOW(int size) {
		return TrafficLightYellowIcon.of(size, size);
	}
	public static JXIcon getTLGREEN(int size) {
		return TrafficLightGreenIcon.of(size, size);
	}

	public static JXIcon getSERVER(int size) {
		return monochrom ? FeatheRserver.of(size, size) : TangoRNetwork_server.of(size, size);
	}
	public static JXIcon getDATABASE(int size) {
		return monochrom ? FeatheRdatabase.of(size, size) : TangoRApplications_database.of(size, size);
	}
	public static JXIcon getCALENDAR(int size) {
		return monochrom ? FeatheRcalendar.of(size, size) : TangoROffice_calendar.of(size, size);
	}
	public static JXIcon getZOOM(int size) {
		return monochrom ? FeatheRcrosshair.of(size, size) : 
			TangoRDialog_error_round.of(size, size); // TODO
	}
	public static JXIcon getCANCEL(int size) {
		return monochrom ? FeatheRx.of(size, size) : TangoRDialog_error_round.of(size, size);
	}
	public static JXIcon getOK(int size) {
		return monochrom ? FeatheRcheck.of(size, size) : TangoRDialog_accept.of(size, size);
	}
}
