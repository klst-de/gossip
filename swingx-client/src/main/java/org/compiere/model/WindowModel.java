package org.compiere.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import org.compiere.apps.form.WorkflowActivities;
import org.compiere.util.Env;

/*

die Tabs sind in super private ArrayList<GridTab>	m_tabs
und mit                public GridTab getTab (int i)
kommt man an sie dran
Aber                   private boolean loadTabData()
GridWindow zuletzt vor 11 Jahren geändert
GridTab            vor 1,5J
 */
public class WindowModel extends GridWindow {

	private static final long serialVersionUID = 623538046539276675L;
	private static final Logger LOG = Logger.getLogger(WindowModel.class.getName());
	
	public static WindowModel get(Properties ctx, int WindowNo, int AD_Window_ID) {
		return get(ctx, WindowNo, AD_Window_ID, false);
	}
	
	public static WindowModel get(Properties ctx, int WindowNo, int AD_Window_ID, boolean virtual) {
//		log.config("Window=" + WindowNo + ", AD_Window_ID=" + AD_Window_ID);
		GridWindowVO mWindowVO = GridWindowVO.create(Env.getCtx(), WindowNo, AD_Window_ID);
		if (mWindowVO == null)
			return null;
		return new WindowModel(mWindowVO, virtual);
	}
	
	public WindowModel(GridWindowVO vo) {
		this(vo, false);
	}

	public WindowModel(GridWindowVO vo, boolean virtual) {
		super(vo, virtual); //macht
//		m_vo = vo;			// private GridWindowVO   	m_vo;
//		m_virtual = virtual;// private boolean m_virtual;
//		if (loadTabData())  // private
//			enableEvents(); // private
// um statt m_tabs  ArrayList<TabModel> zu haben, muss ich beide selber implementieren
// ODER nach dem diesem ctor m_tabs kopieren
		m_virtual = virtual;
		copyGridTab();
// GridTab hat inner class Loader extends Thread
	}
	
	boolean m_virtual; 	
	List<TabModel>	m_tabs = new ArrayList<TabModel>();
	void copyGridTab() {
		LOG.config("WindowNo=" + this.getWindowNo() + ", AD_Window_ID=" + this.getAD_Window_ID() + " has "+getTabCount()+" tabs.");
		for (int i = 0; i < getTabCount(); i++) {
			GridTab gridTab = getTab(i);
			TabModel tm = new TabModel(gridTab.getM_vo(), this, this.m_virtual);
			m_tabs.add(tm);
		}
	}
	
	@Override
	public void dispose() {
		super.dispose();
		m_tabs.forEach(tabModel -> {
			tabModel.dispose();
		});
		m_tabs.clear();
		m_tabs = null;
	}

}
