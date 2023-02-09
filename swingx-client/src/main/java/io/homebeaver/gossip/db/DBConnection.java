package io.homebeaver.gossip.db;

import org.compiere.db.CConnection;

@SuppressWarnings("serial")
public class DBConnection extends CConnection {

	// factory mathods
	public static DBConnection get() {
		return get(null);
	}
	public static DBConnection get(String apps_host) {
		return (DBConnection)CConnection.get(apps_host);
	}

	public DBConnection(String host) {
		super(host);
	}

	// public statt protected
	public void setName() {
		super.setName();
	}
}
