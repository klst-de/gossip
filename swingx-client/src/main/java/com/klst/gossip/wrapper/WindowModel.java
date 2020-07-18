package com.klst.gossip.wrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import org.compiere.model.GridTabVO;
import org.compiere.model.GridWindow;
import org.compiere.model.GridWindowVO;
import org.compiere.util.Env;

/*

die Tabs sind in super private ArrayList<GridTab>	m_tabs
und mit                public GridTab getTab (int i)
kommt man an sie dran
Aber                   private boolean loadTabData()
GridWindow zuletzt vor 11 Jahren geändert
GridTab            vor 1,5J

 */
/**
 * wrapper subclass for org.compiere.model.GridWindow / Window Model
 * </p>
 * class name WindowModel represents what it is: a Window Model
 */
public class WindowModel extends GridWindow {

	private static final long serialVersionUID = 623538046539276675L;
	private static final Logger LOG = Logger.getLogger(WindowModel.class.getName());
	
	@SuppressWarnings("serial")
	static Map<String,String> WINDOWTYPE = new HashMap<String,String>(){ // TODO besser enum?
		{
			put(GridWindowVO.WINDOWTYPE_QUERY, "WINDOWTYPE_QUERY");
			put(GridWindowVO.WINDOWTYPE_TRX, "WINDOWTYPE_TRX");
			put(GridWindowVO.WINDOWTYPE_MMAINTAIN, "WINDOWTYPE_MMAINTAIN");
		}
	};
	
	// Static factory methods:
	/* rule from Effective Java book by Joshua Bloch:
	 
	 "Consider static factory methods instead of constructors"
	 
	 These are common names for different types of static factory methods:
	 valueOf, of, getInstance, newInstance, getType, newType
	 
	 */
	
	public static WindowModel getInstance(Properties ctx, int WindowNo, int AD_Window_ID) {
		return getInstance(ctx, WindowNo, AD_Window_ID, false);
	}
	
	public static WindowModel getInstance(Properties ctx, int WindowNo, int AD_Window_ID, boolean virtual) {
		LOG.config("Window=" + WindowNo + ", AD_Window_ID=" + AD_Window_ID);
		return new WindowModel(GridWindowVO.create(Env.getCtx(), WindowNo, AD_Window_ID), virtual);
	}
	
	// nur intern in gossip, eigentlich nicht public, kommt weg! TODO
	@Deprecated
	public static WindowModel get(GridWindowVO gridWindowVO, boolean virtual) {
		LOG.warning("internal factory method to create WindowModel, params GridWindowVO:" + gridWindowVO + ", virtual=" + virtual);
		return new WindowModel(gridWindowVO, virtual);
	}
	
	WindowModel(GridWindowVO vo) {
		this(vo, false);
	}

	WindowModel(GridWindowVO vo, boolean virtual) {
		super(vo, virtual);
		this.m_vo = vo;
		LOG.config("vo.Tabs.size="+vo.Tabs.size());
		m_vo.Tabs.forEach((GridTabVO tabVO) -> {
			LOG.config("tabVO:" + (tabVO==null ? "null" : tabVO.Name+"/"+tabVO.AD_Tab_ID));
		});
		this.m_virtual = virtual;
		if (loadTabData()) enableEvents();
		LOG.config("VO:" + (m_vo==null ? "null" : m_vo.AD_Window_ID+"/"+m_vo.AD_Window_ID+"/"+WINDOWTYPE.get(m_vo.WindowType))
				+ ", TabCount:"+getTabCount() + " m_tabs.size="+m_tabs.size() + " initTabs.size="+initTabs.size() + "\n");
	}
	
	GridWindowVO m_vo;
	boolean m_virtual; 	
	List<TabModel> m_tabs = null;
	Set<TabModel> initTabs = new HashSet<TabModel>();
	
	private boolean loadTabData()
	{
		LOG.config("windowVO:" + (m_vo==null ? "null" : m_vo.Name+"/"+m_vo.AD_Window_ID+"/"+WINDOWTYPE.get(m_vo.WindowType))
				+ ", TabCount:"+getTabCount());

		if (m_vo.Tabs == null)
			return false;

		m_tabs = new ArrayList<TabModel>();
		for (int t = 0; t < m_vo.Tabs.size(); t++)
		{
			GridTabVO mTabVO = (GridTabVO)m_vo.Tabs.get(t);
			if (mTabVO != null)
			{
				TabModel mTab = new TabModel(mTabVO, this, m_virtual);
				m_tabs.add(mTab);
			}
		}	//  for all tabs
		return true;
	}

	private void enableEvents() {
		for (int i = 0; i < getTabCount(); i++) {
			TabModel tabModel = getTab(i);
//			getTab(i).enableEvents(); // wg. interface DataStatusListener extends EventListener method dataStatusChanged for MTable.
			LOG.warning("NO DataStatusListener for tabModel "+(i+1)+"/"+getTabCount());
			LOG.config("tabModel:"+tabModel);
		}
			
	}

	@Override
	public int getTabCount() {
		return m_tabs==null ? -1 : m_tabs.size();
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

	@Override
	public boolean isTabInitialized(int index) {
		TabModel mTab = m_tabs.get(index);
		return initTabs.contains(mTab);
	}

    /**
     * {@inheritDoc}
     * 
     */
	@Override
	public TabModel getTab(int i) {
		if (i < 0 || i + 1 > m_tabs.size())
			return null;
		return m_tabs.get(i);
	}

	@Override
	public void initTab(int index)
	{	LOG.config("for tabModel "+(index+1)+"/"+getTabCount());
		TabModel mTab = m_tabs.get(index);
		LOG.config("tabModel:"+mTab);
		if (initTabs.contains(mTab)) return;		
		mTab.initTab(false);		
		//		Set Link Column
		if (mTab.getLinkColumnName().length() == 0)
		{
			ArrayList<String> parents = mTab.getParentColumnNames();
			//	No Parent - no link
			if (parents.size() == 0)
				;
			//	Standard case
			else if (parents.size() == 1)
				mTab.setLinkColumnName((String)parents.get(0));
			else
			{
				//	More than one parent.
				//	Search prior tabs for the "right parent"
				//	for all previous tabs
				for (int i = 0; i < index; i++)
				{
					//	we have a tab
					TabModel tab = (TabModel)m_tabs.get(i);
					String tabKey = tab.getKeyColumnName();		//	may be ""
					//	look, if one of our parents is the key of that tab
					for (int j = 0; j < parents.size(); j++)
					{
						String parent = (String)parents.get(j);
						if (parent.equals(tabKey))
						{
							mTab.setLinkColumnName(parent);
							break;
						}
						//	The tab could have more than one key, look into their parents
						if (tabKey.equals(""))
							for (int k = 0; k < tab.getParentColumnNames().size(); k++)
								if (parent.equals(tab.getParentColumnNames().get(k)))
								{
									mTab.setLinkColumnName(parent);
									break;
								}
					}	//	for all parents
				}	//	for all previous tabs
			}	//	parents.size > 1
		}	//	set Link column
		mTab.setLinkColumnName(null);	//	overwrites, if AD_Column_ID exists
		//
		initTabs.add(mTab);
		LOG.config("DONE tabModel:"+mTab);
	}

}
