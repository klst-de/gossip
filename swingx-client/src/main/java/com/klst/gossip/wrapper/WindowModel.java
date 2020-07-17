package com.klst.gossip.wrapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
 *
 */
public class WindowModel extends GridWindow {

	private static final long serialVersionUID = 623538046539276675L;
	private static final Logger LOG = Logger.getLogger(WindowModel.class.getName());
	
	public static WindowModel get(Properties ctx, int WindowNo, int AD_Window_ID) {
		return get(ctx, WindowNo, AD_Window_ID, false);
	}
	
	public static WindowModel get(Properties ctx, int WindowNo, int AD_Window_ID, boolean virtual) {
		LOG.config("Window=" + WindowNo + ", AD_Window_ID=" + AD_Window_ID);
		GridWindowVO mWindowVO = GridWindowVO.create(Env.getCtx(), WindowNo, AD_Window_ID);
		if (mWindowVO == null)
			return null;
		return new WindowModel(mWindowVO, virtual);
	}
	
	public WindowModel(GridWindowVO vo) {
		this(vo, false);
	}

	public WindowModel(GridWindowVO vo, boolean virtual) {
		super(vo, virtual); // exception:
/*

Exception in ctor thread "AWT-EventQueue-0" java.lang.NullPointerException
	at org.compiere.model.WindowModel.getTab(WindowModel.java:85)
	at org.compiere.model.WindowModel.getTab(WindowModel.java:1)
	at org.compiere.model.GridWindow.enableEvents(GridWindow.java:308) 	=====>	getTab(i).enableEvents();
	at org.compiere.model.GridWindow.<init>(GridWindow.java:112)
	at org.compiere.model.WindowModel.<init>(WindowModel.java:43)
	at com.klst.gossip.WindowFrame.getGridWindow(WindowFrame.java:512)

 */
		m_vo = vo;
		LOG.config("m_vo.Tabs.size="+m_vo.Tabs.size());
		m_vo.Tabs.forEach((GridTabVO tab) -> {
			LOG.config(tab.toString());
		});
		m_virtual = virtual;
		if (loadTabData()) enableEvents();
		LOG.config("TabCount:"+getTabCount() + " m_tabs.size="+m_tabs.size() + " initTabs.size="+initTabs.size() + "\n");
	}
	
	GridWindowVO m_vo;
	boolean m_virtual; 	
	List<TabModel> m_tabs = null;
	Set<TabModel> initTabs = new HashSet<TabModel>();
	
	private boolean loadTabData()
	{
		LOG.config("m_vo:"+m_vo + " m_virtual:"+m_virtual + " m_tabs:"+m_tabs);

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
			LOG.warning("NO DataStatusListener for ["+tabModel+"] of "+getTabCount());
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
	{	LOG.config(" initTab for tab ["+index+"] of "+getTabCount());
		TabModel mTab = m_tabs.get(index);
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
		LOG.config("TabCount:"+getTabCount() + " m_tabs.size="+m_tabs.size() + " initTabs.size="+initTabs.size() + "\n");
	}

}
