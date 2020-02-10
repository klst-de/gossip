package com.klst.model;

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

import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.SecureEngine;
import org.compiere.util.Trx;

import com.klst.gossip.GridFieldBridge;

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
public class GridTableDataLoader extends SwingWorker<List<Object[]>, Object[]> implements Serializable {

	private static final long serialVersionUID = -1482907759339623751L;

	private static final Logger LOG = Logger.getLogger(GridTableDataLoader.class.getName());
	
	private GridTable dataModel;
	
	private String trxName;
	// trxName ist in GridTable mit setter und getter, hat dort aber nix zu suchen
	private Trx trx = null;
	
	private String sql;
	private PreparedStatement pstmt;
	private ResultSet resultSet;
	private List<Object[]> dbResultRows;

	// ctor
	public GridTableDataLoader(GridTable dm) {
		this.dataModel = dm;
		LOG.config("dataModel "+this.dataModel);
		
//		this.trxName =  Trx.createTrxName(GenericDataLoader.class.getSimpleName());
		trxName = dataModel.m_virtual ? Trx.createTrxName("Loader") : null;
		dataModel.setTrxName(trxName); // in swing dataModel hat trxName nix zu suchen
		trx  = trxName != null ? Trx.get(trxName, true) : null;	
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.SwingWorker#doInBackground()
	 */
	@Override
	protected List<Object[]> doInBackground() throws Exception {
		// zuerst die erwartete Menge ermitteln mit SELECT COUNT(*)
		sql = this.getSelectCountStar();
		LOG.config(sql + "; trxName:"+getTrxName());
		pstmt = DB.prepareStatement(sql, getTrxName());
		resultSet = pstmt.executeQuery();
		resultSet.next();
		int rowsToFind = resultSet.getInt(1);	
//		dataModel.setRowsToLoad(rowsToFind);
		close();
//		LOG.config(sql + "; Query results to "+rowsToFind);
		
		// jetzt die tatsächlichen Daten halen
		sql = getSelectAll();
		LOG.config(rowsToFind + " rows expected, trxName:"+getTrxName() + ", sql query=\n"+sql);
		dbResultRows = new ArrayList<Object[]>(rowsToFind);
		pstmt = DB.prepareStatement(sql, getTrxName());
		resultSet = pstmt.executeQuery();
		while(resultSet.next() && (dbResultRows.size() < rowsToFind) && !super.isCancelled()) {
			Object[] rowData = readData(resultSet, dbResultRows.size());
			dbResultRows.add(rowData);
			super.publish(rowData);
			super.setProgress(100 * dbResultRows.size() / rowsToFind);
		}
		close();
		if(super.isCancelled()) {
			LOG.warning("cancelled "+dbResultRows.size()+"/"+rowsToFind + " "+100 * dbResultRows.size() / rowsToFind);
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
		dataModel.add(chunks);
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
		return dataModel.getTrxName();  // trxName ist in GridTable mit setter und getter, ABER in swing dataModel hat trxName nix zu suchen
//		return trxName;
	}
	
	// SelectCountStar und SelectAll haben gemeinsame where clause, aus GridTable.m_whereClause und RO/RW Access TODO
    private String getSelectCountStar() {
    	return "SELECT COUNT(*) FROM "+dataModel.getTableName();
    }
    private String getSelectAll() {
		StringBuffer select = new StringBuffer("SELECT ");
		for(int f=0; f<dataModel.getColumnCount(); f++) {
			if(f > 0) select.append(",");
			GridFieldBridge field = (GridFieldBridge)dataModel.columnIdentifiers.get(f);
			select.append(field.getColumnSQL()); // ColumnName or Virtual Column withAS
		}
		select.append("\n FROM "); // new line macht sin im log gut
		select.append(dataModel.getTableName());
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
				Object columnInfo = dataModel.columnIdentifiers.get(f);
				GridFieldBridge field = (GridFieldBridge)columnInfo;
// TODO:				GridFieldBridge field = dataModel.getFieldModel(f);
				columnName = field.getColumnName();
				displayType = field.getDisplayType(); // aka AD_Reference_ID
				if(row==0) {
					LOG.config(f+": "+field); //SeqNoGrid="+field.getSeqNoGrid());
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
			}
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
