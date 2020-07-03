package org.compiere.model;

public class TabModel extends GridTab {

	private static final long serialVersionUID = -7644936025409068432L;

	public TabModel(GridTabVO vo, GridWindow w) {
		this(vo, w, false);
	}
	public TabModel(GridTabVO vo, GridWindow w, boolean virtual) {
		super(vo, w, virtual);
	}

	void test() {
		// fast alle methoden kann ich überschreiben, ABER m_mTable ist nur mit public GridTable getTableModel() erreichbar 
		// auf Loader kann ich zugreifen
		
//		this.getTableModel();// macht:
//		if (!m_loadComplete) initTab(false);
//		return m_mTable;

	}
}
