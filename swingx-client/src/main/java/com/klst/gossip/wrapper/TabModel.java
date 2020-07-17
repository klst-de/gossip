package org.compiere.model;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.compiere.util.Env;

import com.klst.gossip.wrapper.GridTableModel;

public class TabModel extends GridTab {

	private static final long serialVersionUID = -7644936025409068432L;
	private static final Logger LOG = Logger.getLogger(TabModel.class.getName());

	public TabModel(GridTabVO vo, GridWindow w) {
		this(vo, w, false);
	}
	public TabModel(GridTabVO vo, GridWindow w, boolean virtual) {
		super(vo, w, virtual);
		m_vo = vo;
		//  Create MTable
		m_mTable = new GridTable(vo.ctx, vo.AD_Table_ID, vo.TableName, vo.WindowNo, vo.TabNo, true, virtual);
		m_mTable.setReadOnly(isReadOnly() || vo.IsView);
		m_mTable.setDeleteable(vo.IsDeleteable);
	}
	
	@Override
	public String toString(){
		String retValue = "TabModel aka MTab [TabNo=";
		if (m_vo != null)
			retValue += getTabNo() + ", Name=" + getName() + ", AD_Tab_ID=" + getAD_Tab_ID() + ", GridTable/MTable:" + getMTable() + "]";
		else
			retValue += "???";
		return retValue;
	}
	
	private GridTabVO m_vo;
	private GridTable m_mTable;
	private volatile boolean    m_loadComplete = false;
	public boolean isLoadComplete() {
		return m_loadComplete;
	}
	private String				m_extendedWhere; // wird in loadTab() und in super query() gesetzt
	private String[]	    	m_OrderBys = new String[3];
	private MultiMap<String,GridField>	m_depOnField = new MultiMap<String,GridField>();
	private ArrayList<String>	m_parents = new ArrayList<String>(2);
//	private GridTable.Loader gridTableLoader;
	
	public GridTableModel getGridTableModel() {
		LOG.warning(">>> wrap");
		GridTable gt = getTableModel();
		LOG.warning("<<< wrap done.");
		return new GridTableModel(gt);
	}
	
	// fast alle methoden kann ich überschreiben, ABER super.m_mTable ist private nur mit public GridTable getTableModel() erreichbar
	public GridTable getTableModel() {
		LOG.warning("m_mTable:"+m_mTable + " ColumnCount:"+m_mTable.getColumnCount() + " m_loadComplete="+m_loadComplete);
		// in super:
//		if (!m_loadComplete) initTab(false);
		boolean inited = initTab(false);
		LOG.warning("inited="+inited);
		return m_mTable;
//		GridTable gridTable = super.getTableModel();
//		LOG.config("gridTable:"+gridTable + " ColumnCount:"+gridTable.getColumnCount());
//		return gridTable;
	}
	
	/**
     * {@inheritDoc}
     * 
     */
	@Override
	public int getAD_Tab_ID() {
		return m_vo.AD_Tab_ID;
	}
	
	public String getName() {
		return m_vo.Name;
	}
	public String getDescription() {
		return m_vo.Description;
	}
	public String getHelp() {
		return m_vo.Help;
	}
	public int getTabLevel() {
		return m_vo.TabLevel;
	}
	public String getCommitWarning() {
		return m_vo.CommitWarning;
	}
	public String getWhereClause() {
		return m_vo.WhereClause;
	}
	public String getDisplayLogic() {
		return m_vo.DisplayLogic;
	}
	public boolean isOnlyCurrentRows() {
		return m_vo.onlyCurrentRows;
	}
	public GridTable getMTable() {
		return m_mTable;
	}
	public boolean isPrinted() {
		return m_vo.AD_Process_ID != 0;
	}
	public int getWindowNo() {
		return m_vo.WindowNo;
	}
	public int getTabNo() {
		return m_vo.TabNo;
	}
	public int getAD_Process_ID() {
		return m_vo.AD_Process_ID;
	}
	public boolean isHighVolume() {
		return m_vo.IsHighVolume;
	}
	public boolean isSingleRow() {
		return m_vo.IsSingleRow;
	}
	public boolean isTreeTab() {
		return m_vo.HasTree;
	}
	public int getAD_Table_ID() {
		return m_vo.AD_Table_ID;
	}
	public int getAD_Window_ID() {
		return m_vo.AD_Window_ID;
	}
	public boolean isSortTab() {
		return m_vo.IsSortTab;
	}
	public int getAD_ColumnSortOrder_ID() {
		return m_vo.AD_ColumnSortOrder_ID;
	}
	public int getAD_ColumnSortYesNo_ID() {
		return m_vo.AD_ColumnSortYesNo_ID;
	}
	/* @deprecated with
commit 1e78f25804fe04d3a4939bfb797c38fe06fd7c1e
Author: globalqss <devnull@localhost> 2010-12-19 04:49:52
Committer: globalqss <devnull@localhost> 2010-12-19 04:49:52
	 */
	/**
     * {@inheritDoc}
     * @deprecated
     */
	@Override
	public int getIncluded_Tab_ID() {
		LOG.severe("@deprecated since 2010-12-19 04:49:52");
		return super.getIncluded_Tab_ID();
	}
	/**
     * {@inheritDoc}
     * 
     */
	@Override
	public String getTableName() {
		return m_vo.TableName;
	}

	@Override
	public String get_TableName() {
		return this.getTableName();
	}

	public boolean isInsertRecord() {
		if (isReadOnly())
			return false;
		return m_vo.IsInsertRecord;
	}
	public boolean isReadOnly() {
		LOG.warning("NOT implemented! use (super)GridTab");
		return super.isReadOnly();
	}
//		if (m_vo.IsReadOnly)
//			return true;
//		
//		//hengsin, make detail readonly when parent is empty
//		if (m_parentNeedSave) return true; // wird in query gesetzt!!!!!!!!!!!!!!!!!!! TODO
//
//		//  no restrictions
//		if (m_vo.ReadOnlyLogic == null || m_vo.ReadOnlyLogic.equals(""))
//			return m_vo.IsReadOnly;
//
//		//  ** dynamic content **  uses get_ValueAsString
//		boolean retValue = Evaluator.evaluateLogic(this, m_vo.ReadOnlyLogic);
//		log.finest(m_vo.Name
//			+ " (" + m_vo.ReadOnlyLogic + ") => " + retValue);
//		return retValue;
//	}
	
	public String getWhereExtended() {
		return m_extendedWhere;
	}
	public ArrayList<String> getParentColumnNames() {
		return m_parents;
	}
	// wg. LOG
	public boolean initTab(boolean async) {
//		LOG.config("async="+async);
//		return super.initTab(async);
		LOG.config("#" + m_vo.TabNo + " - Async=" + async + " - Where=" + m_vo.WhereClause + " isLoadComplete()="+isLoadComplete());
		if (isLoadComplete()) return true;
		return loadTab();
		
//		if (m_loader != null && m_loader.isAlive())
//		{
//			waitLoadCompete();
//			if (isLoadComplete())
//				return true;
//		}
//
//		if (async)
//		{
//			m_loader = new Loader();
//			m_loader.start();
//			return false;
//		}
//		else
//		{
//			return loadTab();
//		}
	}
	
	// wg. m_mTable.setOrderClause(getOrderByClause(m_vo.onlyCurrentRows)); 
	private String getOrderByClause(boolean onlyCurrentRows)
	{
		//	First Prio: Tab Order By
		if (m_vo.OrderByClause.length() > 0)
		{
			String orderBy = Env.parseContext(m_vo.ctx, m_vo.WindowNo, m_vo.OrderByClause, false, false);
			return orderBy;
		}

		//	Second Prio: Fields (save it)
		m_vo.OrderByClause = "";
		for (int i = 0; i < 3; i++)
		{
			String order = m_OrderBys[i];
			if (order != null && order.length() > 0)
			{
				if (m_vo.OrderByClause.length() > 0)
					m_vo.OrderByClause += ",";
				m_vo.OrderByClause += order;
			}
		}
		if (m_vo.OrderByClause.length() > 0)
			return m_vo.OrderByClause;

		//	Third Prio: onlyCurrentRows
		m_vo.OrderByClause = "Created";
		if (onlyCurrentRows && !isDetail())	//	first tab only
			m_vo.OrderByClause += " DESC";
		return m_vo.OrderByClause;
	}	//	getOrderByClause
	
//	public boolean loadGridTab() { // damit ist loadTab public
//		return loadTab();
//	}
	protected boolean loadTab() {
//		return super.loadTab(); // macht boolean loadFields()
		LOG.config("#" + m_vo.TabNo + " m_mTable.getFieldCount()="+this.m_mTable.getFieldCount()+ " m_mTable.getColumnCount()="+this.m_mTable.getColumnCount());
		m_extendedWhere = m_vo.WhereClause;
		
		//	Get Field Data
		if (!loadFields())
		{
			m_loadComplete = true;
			return false;
		}

		//  Order By
		m_mTable.setOrderClause(getOrderByClause(m_vo.onlyCurrentRows));

		m_loadComplete = true;
		return true;
	}
	
	// wg. m_mTable.close (true);  //  also disposes Fields TODO
	protected void dispose() {
		m_mTable = null;
		super.dispose();
	}
	
	// wg. m_mTable.addField(field); das in protected boolean loadTab() verwendet wird
	private boolean loadFields() {
		LOG.config("#" + m_vo.TabNo + " m_mTable.getFieldCount()="+this.m_mTable.getFieldCount()+ " m_mTable.getColumnCount()="+this.m_mTable.getColumnCount());

		if (m_vo.getFields() == null)
			return false;

		//  Add Fields
		for (int f = 0; f < m_vo.getFields().size(); f++)
		{
			GridFieldVO voF = (GridFieldVO)m_vo.getFields().get(f);
			//	Add Fields to Table
			if (voF != null)
			{
				GridField field = new GridField (voF);
				field.setGridTab(this);
				String columnName = field.getColumnName();
				//FR [ 1757088 ] - this create Bug [ 1866793 ]
				/*
				if(this.isReadOnly()) {
				   voF.IsReadOnly = true;
				}*/
				//	Record Info
				if (field.isKey()) {
					setKeyColumnName(columnName);
				}
				//	Parent Column(s)
				if (field.isParentColumn())
					m_parents.add(columnName);
				//	Order By
				int sortNo = field.getSortNo();
				if (sortNo == 0)
					;
				else if (Math.abs(sortNo) == 1)
				{
					m_OrderBys[0] = columnName;
					if (sortNo < 0)
						m_OrderBys[0] += " DESC";
				}
				else if (Math.abs(sortNo) == 2)
				{
					m_OrderBys[1] = columnName;
					if (sortNo < 0)
						m_OrderBys[1] += " DESC";
				}
				else if (Math.abs(sortNo) == 3)
				{
					m_OrderBys[2] = columnName;
					if (sortNo < 0)
						m_OrderBys[2] += " DESC";
				}
				//  Add field
				m_mTable.addField(field);

				//  List of ColumnNames, this field is dependent on
				ArrayList<String> list = field.getDependentOn();
				for (int i = 0; i < list.size(); i++)
					m_depOnField.put(list.get(i), field);   //  ColumnName, Field
				//  Add fields all fields are dependent on
				if (columnName.equals("IsActive")
					|| columnName.equals("Processed")
					|| columnName.equals("Processing"))
					m_depOnField.put(columnName, null);
			}
		}   //  for all fields

		if (! m_mTable.getTableName().equals(X_AD_PInstance_Log.Table_Name)) { // globalqss, bug 1662433 
			//  Add Standard Fields
			if (m_mTable.getField("Created") == null)
			{
				GridField created = new GridField (GridFieldVO.createStdField(m_vo.ctx,
					m_vo.WindowNo, m_vo.TabNo,
					m_vo.AD_Window_ID, m_vo.AD_Tab_ID, false, true, true));
				m_mTable.addField(created);
			}
			if (m_mTable.getField("CreatedBy") == null)
			{
				GridField createdBy = new GridField (GridFieldVO.createStdField(m_vo.ctx,
					m_vo.WindowNo, m_vo.TabNo,
					m_vo.AD_Window_ID, m_vo.AD_Tab_ID, false, true, false));
				m_mTable.addField(createdBy);
			}
			if (m_mTable.getField("Updated") == null)
			{
				GridField updated = new GridField (GridFieldVO.createStdField(m_vo.ctx,
					m_vo.WindowNo, m_vo.TabNo,
					m_vo.AD_Window_ID, m_vo.AD_Tab_ID, false, false, true));
				m_mTable.addField(updated);
			}
			if (m_mTable.getField("UpdatedBy") == null)
			{
				GridField updatedBy = new GridField (GridFieldVO.createStdField(m_vo.ctx,
					m_vo.WindowNo, m_vo.TabNo,
					m_vo.AD_Window_ID, m_vo.AD_Tab_ID, false, false, false));
				m_mTable.addField(updatedBy);
			}
		}
		return true;
	}
	
	private String 				m_keyColumnName = "";
	private void setKeyColumnName(String keyColumnName) {
		this.m_keyColumnName = keyColumnName;
		Env.setContext(m_vo.ctx, m_vo.WindowNo, m_vo.TabNo, CTX_KeyColumnName, m_keyColumnName);
	}

	// wg. m_mTable.addDataStatusListener(this);
//	public void enableEvents() {
//		super.enableEvents();
//	}
//	
//	// wg. m_mTable.dataRefreshAll(); m_mTable.dataRequery ...
//	public void query (boolean onlyCurrentRows, int onlyCurrentDays, int maxRows) {
//		
//	}
//	
//	// wg. m_mTable.dataRefreshAll(...)
//	public void dataRefreshAll (boolean fireEvent,  boolean retainedCurrentRow) {
//		
//	}
//	
//	// wg. m_mTable.dataRefresh(row, fireEvent);
//	public void dataRefresh (int row, boolean fireEvent) {
//		
//	}
//	
//	// wg. m_mTable.dataSave(manualCmd)
//	public boolean dataSave(boolean manualCmd) {
//		
//	}
//	
//	// ? wg. m_mTable.hasChanged
//	public boolean hasChangedCurrentTabAndParents() {
//		
//	}
}
