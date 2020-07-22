package com.klst.gossip;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import org.compiere.model.GridField;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.SecureEngine;
import org.compiere.util.Trx;

import com.klst.gossip.wrapper.GridTableModel;

/* 
SwingWorker Workflow , @see javax.swing.SwingWorker<T, V>

SwingWorker is only designed to be executed once. 
Executing a SwingWorker more than once will not result in invoking the doInBackground method twice.

There are three threads involved in the life cycle of a SwingWorker : 

• Current thread: expl. Tab 
The execute method is called on this thread. 
It schedules SwingWorker for the execution on a worker thread and returns immediately. 
One can wait for the SwingWorker to complete using the get methods. 

• Worker thread: this class
The doInBackground method is called on this thread.
This is where all background activities should happen. 
To notify PropertyChangeListeners about bound properties changes use the firePropertyChange and getPropertyChangeSupport methods. 
By default there are two bound properties available: state and progress.
 
Before the doInBackground method is invoked on a worker thread, 
SwingWorker notifies any PropertyChangeListeners about the state property change to StateValue.STARTED. 
After the doInBackground method is finished the done method isexecuted. 
Then SwingWorker notifies any PropertyChangeListenersabout the state property change to StateValue.DONE. 

• Event Dispatch Thread: 
All Swing related activities occur on this thread. 
SwingWorker invokes the process and done methods and notifies any PropertyChangeListeners on this thread. 


Often, the Current thread is the Event DispatchThread. 

*/
//aka Loader innner class in (base)GridTable implements Serializable, Runnable 
//T - the result type returned by this SwingWorker's doInBackground and get methods
//V - the type used for carrying out intermediate results by this SwingWorker's publish and process methods
public class GenericDataLoader extends SwingWorker<List<Object[]>, Object[]> implements Serializable {

	private static final long serialVersionUID = 7687507315859041009L;

	private static final Logger LOG = Logger.getLogger(GenericDataLoader.class.getName());
	
	private DefaultTableModel dataModel;
	
	private String trxName;
	// trxName ist in GridTable mit setter und getter, hat dort aber nix zu suchen
	private Trx trx = null;
	
	private String sql;
	private PreparedStatement pstmt;
	private ResultSet resultSet;
	private List<Object[]> dbResultRows;

	// ctor
	public GenericDataLoader(GridTableModel gtm) { // GridTableModel extends DefaultTableModel extends AbstractTableModel implements Serializable
		this.dataModel = gtm;
		LOG.config("dataModel "+this.dataModel);
		// TODO virtuelle Tabellen, s. GridTable: trxName = m_virtual ? Trx.createTrxName("Loader") : null;
		this.trxName =  Trx.createTrxName(GenericDataLoader.class.getSimpleName());
		trx  = trxName != null ? Trx.get(trxName, true) : null;	
	}

	// wrapper für dataModel:
	private void setRowsToLoad(int expectedNumberofRows) {
//		if(dataModel instanceof GenericDataModel) {
//			((GenericDataModel)dataModel).setRowsToLoad(expectedNumberofRows);
//		}
		((GridTableModel)dataModel).setRowsToLoad(expectedNumberofRows);
	}
	private void add(List<Object[]> chunks) {
//		if(dataModel instanceof GenericDataModel) {
//			((GenericDataModel)dataModel).add(chunks);
//		}
		((GridTableModel)dataModel).add(chunks);
	}
	private String getTableName() {
		((GridTableModel)dataModel).getSelectWhereClause();		
//		if(dataModel instanceof GenericDataModel) {
//			return ((GenericDataModel)dataModel).getTableName();
//		}
		return ((GridTableModel)dataModel).getTableName();		
	}
	private String getSelectWhereClause() {
		String whereClause = ((GridTableModel)dataModel).getSelectWhereClause();	
		return whereClause==null || whereClause.isEmpty() ? "" : " WHERE "+whereClause;
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.SwingWorker#doInBackground()
	 */
	@Override
	protected List<Object[]> doInBackground() throws Exception {
		// zuerst die erwartete Menge ermitteln mit SELECT COUNT(*)
		sql = this.getSelectCountStar(getSelectWhereClause());
		LOG.config(sql + "; trxName:"+getTrxName());
/* TODO

02:16:15.438   WindowFrame.windowClosing: TODO [26]
[WARNING] com.mchange.v2.resourcepool.BasicResourcePool@2bec854f -- an attempt to checkout a resource was interrupted, and the pool is still live: some other thread must have either interrupted the Thread attempting checkout!java.lang.InterruptedException
	at java.lang.Object.wait(Native Method)
	at com.mchange.v2.resourcepool.BasicResourcePool.awaitAvailable(BasicResourcePool.java:1315)
	at com.mchange.v2.resourcepool.BasicResourcePool.prelimCheckoutResource(BasicResourcePool.java:557)
	at com.mchange.v2.resourcepool.BasicResourcePool.checkoutResource(BasicResourcePool.java:477)
	at com.mchange.v2.c3p0.impl.C3P0PooledConnectionPool.checkoutPooledConnection(C3P0PooledConnectionPool.java:525)
	at com.mchange.v2.c3p0.impl.AbstractPoolBackedDataSource.getConnection(AbstractPoolBackedDataSource.java:128)
	at org.compiere.db.DB_PostgreSQL.getCachedConnection(DB_PostgreSQL.java:526)
	at org.compiere.db.CConnection.getConnection(CConnection.java:1357)
	at org.compiere.util.DB.createConnection(DB.java:455)
	at org.compiere.util.DB.getConnectionRO(DB.java:404)
	at org.compiere.db.PreparedStatementProxy.init(PreparedStatementProxy.java:66)
	at org.compiere.db.PreparedStatementProxy.<init>(PreparedStatementProxy.java:44)
	at org.compiere.db.ProxyFactory.newCPreparedStatement(ProxyFactory.java:56)
	at org.compiere.util.DB.prepareStatement(DB.java:784)
	at org.compiere.util.DB.prepareStatement(DB.java:753)
	at com.klst.gossip.GenericDataLoader.doInBackground(GenericDataLoader.java:115)
	at com.klst.gossip.GenericDataLoader.doInBackground(GenericDataLoader.java:1)
	at javax.swing.SwingWorker$1.call(SwingWorker.java:295)
	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
	at javax.swing.SwingWorker.run(SwingWorker.java:334)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
	at java.lang.Thread.run(Thread.java:748)

02:16:15.468   Tab.setLoadState: Batch StateValue:DONE [26]
02:16:15.483   Tab.setLoadState: Batch StateValue:DONE [26]
02:16:15.507   Tab.setLoadState: Attendance StateValue:DONE [26]
02:16:15.523   Tab.setLoadState: Attendance StateValue:DONE [26]


 */
		pstmt = DB.prepareStatement(sql, getTrxName());
		try {
			resultSet = pstmt.executeQuery();
			resultSet.next();
		} catch (SQLException ex) {
			LOG.log(Level.SEVERE, ex.toString() + " sql:\n"+sql);
//			ex.printStackTrace();
			throw ex;
		}
		int expectedNumberofRows = resultSet.getInt(1);	
		setRowsToLoad(expectedNumberofRows);
		close();
		
		// jetzt die tatsächlichen Daten holen
		sql = getSelectAll(getSelectWhereClause());
		LOG.config(expectedNumberofRows + " rows expected, trxName:"+getTrxName() + ", sql query=\n"+sql);
		dbResultRows = new ArrayList<Object[]>(expectedNumberofRows);
		pstmt = DB.prepareStatement(sql, getTrxName());
		resultSet = pstmt.executeQuery();
		while(resultSet.next() && (dbResultRows.size() < expectedNumberofRows) && !super.isCancelled()) {
			Object[] rowData = readData(resultSet, dbResultRows.size());
			dbResultRows.add(rowData);
//			LOG.warning("vor publish "+dbResultRows.size());
			super.publish(rowData);
			super.setProgress(100 * dbResultRows.size() / expectedNumberofRows);
		}
		close();
		if(super.isCancelled()) {
			LOG.warning("cancelled "+dbResultRows.size()+"/"+expectedNumberofRows + " "+100 * dbResultRows.size() / expectedNumberofRows);
			super.firePropertyChange("cancelled", false, true);
		}
		return dbResultRows;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.SwingWorker#process(java.util.List)
	 */
	@Override
	protected void process(List<Object[]> chunks) {
		LOG.config("chunks#:"+chunks.size());
		add(chunks);
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.SwingWorker#done()
	 */
	@Override
    protected void done() {
    	setProgress(100);
    }

//	------------------- private:
	
	private String getTrxName() {
//		return dataModel.getTrxName();  // trxName ist in GridTable mit setter und getter, ABER in swing dataModel hat trxName nix zu suchen
		return trxName;
	}
	// SelectCountStar und SelectAll haben gemeinsame where clause, aus GridTable.m_whereClause und RO/RW Access TODO
    private String getSelectCountStar(String where) {
    	return "SELECT COUNT(*) FROM "+getTableName() + where;
    }
    private String getSelectAll(String where) {
    	LOG.config("dataModel.ColumnCount="+dataModel.getColumnCount());
		StringBuffer select = new StringBuffer("SELECT ");
		for(int f=0; f<dataModel.getColumnCount(); f++) {
			if(f > 0) select.append(",");
//			GridFieldBridge field = dataModel.getFieldModel(f);
			String columnExt = ((GridTableModel)dataModel).getColumnSQL(f, true);  // ColumnName or Virtual Column withAS
//			GridField field = (GridField)(columnExt.getIdentifier());
			select.append(columnExt);
		}
		select.append("\n FROM "); // new line macht sich im log gut
		select.append(getTableName());
		LOG.config("select="+select);
		return select.toString();
    }
    

//	private Object[] readData (ResultSet rs)
	// parameter row wird nur für LOG (auch exception LOG) benötigt
	private Object[] readData(ResultSet rs, int row) throws SQLException {
		int size = dataModel.getColumnCount();
		Object[] fieldData = new Object[size]; // aka rowData
		String columnName = null;
		int displayType = 0;
		try {
			// get row data field by field
			for (int f = 0; f < size; f++) {
				// field metadata aka Column Info, GridField field 
//				LOG.config(f+"/"+fieldData.length+": row="+row);
//				GridFieldBridge field = dataModel.getFieldModel(f); // GridFieldBridge extends TableColumnExt
				columnName = ((GridTableModel)dataModel).getColumnSQL(f, false);  // ColumnName or Virtual Column withAS
				displayType = ((GridTableModel)dataModel).getDisplayType(f);
				GridField field = ((GridTableModel)dataModel).getGridField(f);
				
//				GridField field = (GridField)(columnExt.getIdentifier());
//				columnName = field.getColumnName(); // ? oder getIdentifier()
//				displayType = field.getDisplayType(); // aka AD_Reference_ID
				if(row==0) {
					LOG.warning(f+"/"+size + " DT="+displayType + ": "+columnName); //SeqNoGrid="+field.getSeqNoGrid());
				}
				//	Integer, ID, Lookup (UpdatedBy is a numeric column)
				if (displayType == DisplayType.Integer
					|| (DisplayType.isID(displayType) 
						&& (columnName.endsWith("_ID") || columnName.endsWith("_Acct") 
							|| columnName.equals("AD_Key") || columnName.equals("AD_Display"))) 
					|| columnName.endsWith("atedBy"))
				{
					fieldData[f] = new Integer(rs.getInt(f+1));	//	Integer
					if (rs.wasNull())
						fieldData[f] = null;
				}
				//	Number
				else if (DisplayType.isNumeric(displayType))
					fieldData[f] = rs.getBigDecimal(f+1);			//	BigDecimal
				//	Date
				else if (DisplayType.isDate(displayType))
					fieldData[f] = rs.getTimestamp(f+1);			//	Timestamp
				//	RowID or Key (and Selection)
				else if (displayType == DisplayType.RowID)
					fieldData[f] = null;
				//	YesNo
				else if (displayType == DisplayType.YesNo)
				{
					String str = rs.getString(f+1);
					if (field.isEncryptedColumn())
						str = (String)decrypt(str);
					fieldData[f] = new Boolean ("Y".equals(str));	//	Boolean
				}
				else if (displayType == DisplayType.Button)
				{
					String str = rs.getString(f+1);
					fieldData[f] = "Button "+str;
				}
				//	LOB
				else if (DisplayType.isLOB(displayType))
				{
					Object value = rs.getObject(f+1);
					if (rs.wasNull())
						fieldData[f] = null;
					else if (value instanceof Clob) 
					{
						Clob lob = (Clob)value;
						long length = lob.length();
						fieldData[f] = lob.getSubString(1, (int)length);
					}
					else if (value instanceof Blob)
					{
						Blob lob = (Blob)value;
						long length = lob.length();
						fieldData[f] = lob.getBytes(1, (int)length);
					}
					else if (value instanceof String)
						fieldData[f] = value;
					else if (value instanceof byte[])
						fieldData[f] = value;
				}
				//	String
				else
					fieldData[f] = rs.getString(f+1);				//	String
				
				//	Encrypted
				if (field.isEncryptedColumn() && displayType != DisplayType.YesNo)
					fieldData[f] = decrypt(fieldData[f]);

				//dataModel.setValueAt(fieldData[f], row, f);
//				field.setValue(fieldData[f], false);
//				LOG.warning("fieldData["+f+"]:"+fieldData[f]);			
			}
//			LOG.warning("DB Zeile "+row+" hat Anzahl Spalten fieldData.length="+fieldData.length);
		}
		catch (SQLException e)
		{
			LOG.log(Level.SEVERE, "row:"+row + ", col:"+columnName + ", displayType=" + displayType, e);
			throw e;
		}
		return fieldData;
	}

	private Object encrypt(Object xx) {
		if (xx == null)
			return null;
		return SecureEngine.encrypt(xx);
	}

	private Object decrypt(Object yy) {
		if (yy == null)
			return null;
		return SecureEngine.decrypt(yy);
	}

	private void close() {
		DB.close(resultSet, pstmt);
		resultSet = null;
		pstmt = null;
		if (trx != null) trx.close();
	}

}
