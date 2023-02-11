package io.homebeaver.gossip.icon;

import org.jdesktop.swingx.demos.svg.FeatheRcheck;
import org.jdesktop.swingx.demos.svg.FeatheRdatabase;
import org.jdesktop.swingx.demos.svg.FeatheRfeather;
import org.jdesktop.swingx.demos.svg.FeatheRserver;
import org.jdesktop.swingx.demos.svg.FeatheRx;
import org.jdesktop.swingx.icon.JXIcon;

/*
	SERVER	("server"),
	DATABASE("database"),
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
		else if("CANCEL".equalsIgnoreCase(name))
			return getCANCEL(size);
		else if("OK".equalsIgnoreCase(name))
			return getOK(size);
		else return FeatheRfeather.of(size, size);
			
	}
	
	public static JXIcon getSERVER(int size) {
		return monochrom ? FeatheRserver.of(size, size) : TangoRNetwork_server.of(size, size);
	}
	public static JXIcon getDATABASE(int size) {
		return monochrom ? FeatheRdatabase.of(size, size) : TangoRApplications_database.of(size, size);
	}
	public static JXIcon getCANCEL(int size) {
		return monochrom ? FeatheRx.of(size, size) : TangoRDialog_error_round.of(size, size);
	}
	public static JXIcon getOK(int size) {
		return monochrom ? FeatheRcheck.of(size, size) : TangoRDialog_accept.of(size, size);
	}
}
