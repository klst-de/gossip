package com.klst.gossip;

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

import org.compiere.model.GridField;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Trx;

// aka class Loader implements Serializable, Runnable in GridTable
/*
		public Loader()
		{
		}	//	Loader

		private PreparedStatement   m_pstmt = null;
		private ResultSet 		    m_rs = null;
		private Trx trx = null;

		/ **
		 *	Open ResultSet
		 *	@param maxRows maximum number of rows or 0 for all
		 *	@return number of records
		 * /
		protected int open (int maxRows)
		{
		//	log.config( "MTable Loader.open");
			//	Get Number of Rows
			int rows = 0;
			PreparedStatement pstmt = null;
			ResultSet rs = null;			
			try
			{
				pstmt = DB.prepareStatement(m_SQL_Count, null);
				setParameter (pstmt, true);
				rs = pstmt.executeQuery();
				if (rs.next())
					rows = rs.getInt(1);
			}
			catch (SQLException e0)
			{
				//	Zoom Query may have invalid where clause
				if (DBException.isInvalidIdentifierError(e0))
					log.warning("Count - " + e0.getLocalizedMessage() + "\nSQL=" + m_SQL_Count);
				else
					log.log(Level.SEVERE, "Count SQL=" + m_SQL_Count, e0);
				return 0;
			}
			finally
			{
				DB.close(rs, pstmt);				
			}
			StringBuffer info = new StringBuffer("Rows=");
			info.append(rows);
			if (rows == 0)
				info.append(" - ").append(m_SQL_Count);
						
			//postgresql need trx to use cursor based resultset
			String trxName = m_virtual ? Trx.createTrxName("Loader") : null;
			trx  = trxName != null ? Trx.get(trxName, true) : null;
			//	open Statement (closed by Loader.close)
			try
			{
				m_pstmt = DB.prepareStatement(m_SQL, trxName);
				if (maxRows > 0 && rows > maxRows)
				{
					m_pstmt.setMaxRows(maxRows);
					info.append(" - MaxRows=").append(maxRows);
					rows = maxRows;
				}
				//ensure not all row is fectch into memory for virtual table
				if (m_virtual)
					m_pstmt.setFetchSize(100);
				setParameter (m_pstmt, false);
				m_rs = m_pstmt.executeQuery();
			}
			catch (SQLException e)
			{
				log.log(Level.SEVERE, m_SQL, e);
				return 0;
			}
			log.fine(info.toString());
			return rows;
		}	//	open

		/ **
		 *	Close RS and Statement
		 * /
		private void close()
		{
		//	log.config( "MTable Loader.close");
			DB.close(m_rs, m_pstmt);
			m_rs = null;
			m_pstmt = null;
			if (trx != null)
				trx.close();
		}	//	close

		/ **
		 *	Fill Buffer to include Row
		 * /
		public void run()
		{
			log.info("");
			if (m_rs == null)
				return;

			try
			{
				while (m_rs.next())
				{
					if (Thread.interrupted())
					{
						log.fine("Interrupted");
						close();
						return;
					}
					//  Get Data
					int recordId = 0;
					Object[] rowData = null;
					if (m_virtual)
						recordId = m_rs.getInt(getKeyColumnName());
					else
						rowData = readData(m_rs);
					//	add Data
					MSort sort = m_virtual
						? new MSort(recordId, null)
						: new MSort(m_buffer.size(), null);	//	index
					if (!m_virtual)
					{
						m_buffer.add(rowData);
					}
					m_sort.add(sort);

					//	Statement all 1000 rows & sleep
					if (m_sort.size() % 1000 == 0)
					{
						//	give the other processes a chance
						try
						{
							Thread.yield();
							Thread.sleep(10);		//	.01 second
						}
						catch (InterruptedException ie)
						{
							log.fine("Interrupted while sleeping");
							close();
							return;
						}
						DataStatusEvent evt = createDSE();
						evt.setLoading(m_sort.size());
						fireDataStatusChanged(evt);
					}
				}	//	while(rs.next())
			}
			catch (SQLException e)
			{
				log.log(Level.SEVERE, "run", e);
			}
			finally
			{
				close();
			}
			fireDataStatusIEvent("", "");
		}	//	run

		/ **
		 *	Set Parameter for Query.
		 *		elements must be Integer, BigDecimal, String (default)
		 *  @param pstmt prepared statement
		 *  @param countSQL count
		 * /
		private void setParameter (PreparedStatement pstmt, boolean countSQL)
		{
			if (m_parameterSELECT.size() == 0 && m_parameterWHERE.size() == 0)
				return;
			try
			{
				int pos = 1;	//	position in Statement
				//	Select Clause Parameters
				for (int i = 0; !countSQL && i < m_parameterSELECT.size(); i++)
				{
					Object para = m_parameterSELECT.get(i);
					if (para != null)
						log.fine("Select " + i + "=" + para);
					//
					if (para == null)
						;
					else if (para instanceof Integer)
					{
						Integer ii = (Integer)para;
						pstmt.setInt (pos++, ii.intValue());
					}
					else if (para instanceof BigDecimal)
						pstmt.setBigDecimal (pos++, (BigDecimal)para);
					else
						pstmt.setString(pos++, para.toString());
				}
				//	Where Clause Parameters
				for (int i = 0; i < m_parameterWHERE.size(); i++)
				{
					Object para = m_parameterWHERE.get(i);
					if (para != null)
						log.fine("Where " + i + "=" + para);
					//
					if (para == null)
						;
					else if (para instanceof Integer)
					{
						Integer ii = (Integer)para;
						pstmt.setInt (pos++, ii.intValue());
					}
					else if (para instanceof BigDecimal)
						pstmt.setBigDecimal (pos++, (BigDecimal)para);
					else
						pstmt.setString(pos++, para.toString());
				}
			}
			catch (SQLException e)
			{
				log.log(Level.SEVERE, "parameter", e);
			}
		}	//	setParameter

	}	//	Loader

 */

//T - the result type returned by this SwingWorker's doInBackground and get methods
//V - the type used for carrying out intermediate results by this SwingWorker's publish and process methods
public class GenericDataLoader extends SwingWorker<List<Object[]>, Object[]> {

	private static final Logger LOG = Logger.getLogger(GenericDataLoader.class.getName());
	
	private GenericTableModel tableModel;

	// ctor
	public GenericDataLoader(GenericTableModel tableModel) {
		this.tableModel = tableModel;
		LOG.config("tableModel "+this.tableModel);
		this.trxName =  Trx.createTrxName(GenericDataLoader.class.getName());
		tableModel.getDbTableName(); // für select * from XXX
		getSelectClause(); // für *
	}

	private String sql;
	private PreparedStatement pstmt;
	private int rowsToFind = -1; // Ergebnis von select count(*)
	private ResultSet resultSet;
	private List<Object[]> dbResultRows;
	private GridField[] fields = null;
	private String trxName;
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.SwingWorker#doInBackground()
	 */
	@Override
	// die zentrale Methode
	protected List<Object[]> doInBackground() throws Exception {
		//postgresql need trx to use cursor based resultset
//		String trxName =  Trx.createTrxName("Loader");
		sql = this.getSelectCountStar();
		LOG.config(sql + "; trxName:"+trxName);
		pstmt = DB.prepareStatement(sql, trxName);
		resultSet = pstmt.executeQuery();
		resultSet.next();
		rowsToFind = resultSet.getInt(1);	
		close();
		
		dbResultRows = new ArrayList<Object[]>(rowsToFind);
		sql = this.getSelectAll();
		LOG.config(sql + "; rowsToFind:"+rowsToFind + "; trxName:"+trxName);
		fields = tableModel.getColumns();
		//	Create SELECT Part
		StringBuffer select = new StringBuffer("SELECT ");
		for (int i = 0; i < fields.length; i++)
		{
			if(i > 0) select.append(",");
			GridField field = fields[i];
			select.append(field.getColumnSQL(true));	//	ColumnName or Virtual Column // boolean withAS
		}
		select.append(" FROM ").append(tableModel.getDbTableName());
		sql = select.toString();
		//
		pstmt = DB.prepareStatement(sql, trxName);
		// m_rowCount = m_loader.open(maxRows);
		resultSet = pstmt.executeQuery();
		while(resultSet.next() && (dbResultRows.size() < rowsToFind) && !isCancelled()) {
//			LOG.config(dbResultRows.size() +"/"+ rowsToFind);
			Object[] rowData = readData(resultSet, dbResultRows.size()); //BigInteger number = nextPrimeNumber(); readData liest die cols der DB-row
			
			dbResultRows.add(rowData);
			publish(rowData); // TODO ? publish(banks3);
			setProgress(100 * dbResultRows.size() / rowsToFind);
		}
		close();
		return dbResultRows;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.SwingWorker#process(java.util.List)
	 */
	@Override
	protected void process(List<Object[]> chunks) {
		LOG.config("chunks#:"+chunks.size());
		tableModel.add(chunks);
	}

    protected void done() {
    	setProgress(100);
//    	dataPanel.hideMessageLayer(); // TODO Stacker dataPanel; 
    }

    private String getSelectCountStar() {
    	return "SELECT COUNT(*) FROM "+tableModel.getDbTableName();
    }
    
    private String getSelectAll() {
    	return "SELECT * FROM "+tableModel.getDbTableName();
    }
    
    private String getSelectClause() {
    	// TODO
    	return null;
    }
    
	private Object[] readData(ResultSet rs, int row) throws SQLException {
		int size = tableModel.getColumnCount();
		Object[] fieldData = new Object[size]; // renamed from rowData
		String columnName = null;
		int displayType = 0;

		//	Types see also MField.createDefault
		try
		{
			//	get row data
			for (int f = 0; f < size; f++)
			{
				// field metadata
				GridField field = this.fields[f];
				columnName = field.getColumnName();
				displayType = field.getDisplayType();
				if(row==0) {
					LOG.config(f+":SeqNoGrid="+field.getSeqNoGrid() + " columnName="+columnName 
					+ " DisplayType"+displayType + " Field_ID="+field.getAD_Field_ID() 
					);
				}
//				if(field.getSeqNoGrid()==0) {
//					// do not display
//				} else {	
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
	//					if (field.isEncryptedColumn()) str = (String)decrypt(str); // TODO
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
	//				if (field.isEncryptedColumn() && displayType != DisplayType.YesNo) rowData[j] = decrypt(rowData[j]); // TODO
//				}
					//LOG.config(f+":"+fieldData[f]);
			}
		}
		catch (SQLException e)
		{
			LOG.log(Level.SEVERE, "row:"+row + ", col:"+columnName + ", displayType=" + displayType, e);
			throw e;
		}
		return fieldData;
	}	//	readData

/*    
	private Object[] readDataXX(ResultSet rs, int row) throws SQLException {
		int size = tableModel.getColumnCount();
		Object[] fieldData = new Object[size];
//		Integer tableId = null;
		MTable table = null; // MTable.get(Env.getCtx() , tableId);
		String trxName = Trx.createTrxName("Loader");
//		List<Integer> recordIds; // = new ArrayList<Integer>();
		for (int f = 0; f < size; f++) {
//			MField field = fields.get(f);
			GridField field = this.gridFields[f];
			I_AD_Column column = fields.get(f).getAD_Column();
//			column.getAD_Element_ID()
//			column.getAD_Table_ID() // muss == tableModel.table_ID sein
//			column.getColumnSQL()
			
//			if(row==0) {
////				tableId = column.getAD_Table_ID();
//				table = MTable.get(Env.getCtx() , column.getAD_Table_ID());
//				LOG.config(f+":SeqNoGrid="+field.getSeqNoGrid() + " SeqNo="+field.getSeqNo() 
//				+ " Reference="+column.getAD_Reference_ID()
//				+ " Element="+column.getAD_Element_ID()
//				+ " Table_ID="+column.getAD_Table_ID()
//				+ " ColumnName="+column.getColumnName()
////				+ " ColumnName="+field.getDisplayType // in GridField
//				+ " Name='"+field.getName()+"' "+field.toString());
//			}
			
			if(field.getSeqNo()==0) {
				// do not display
			} else {
				// TODO Display Zeilen Logic berücksichtigen, zB @IsPostcodeLookup@ = 'Y'			
				if(column.getAD_Reference_ID()==20) { // AD_Reference_ID=20 : CheckBox
					String value = rs.getString(column.getColumnName());
					fieldData[f] = new Boolean( value.equals("Y") ? true : false );
				} else if(column.getAD_Reference_ID()==19 && column.getColumnName().endsWith("_ID") // AD_Reference_ID=19 : Table Direct
						|| column.getColumnName().endsWith("_Acct") 
						|| column.getColumnName().equals("AD_Key") 
						|| column.getColumnName().equals("AD_Display") 
						|| column.getColumnName().endsWith("atedBy")) {
					Integer recordId = new Integer(rs.getInt(column.getColumnName()));
//					recordIds = new ArrayList<Integer>();
//					recordIds.add(recordId);
					// PO static public List<?> getInstances(Integer tableId, List<Integer> recordIds, String trxName) throws AdempiereException
//					List<?> po = PO.getInstances(tableId, recordIds, Trx.createTrxName("Loader")); // throws AdempiereException
//					PO po = table.getPO(recordId.intValue(), trxName); // bleibt nach dem ersten Rec stehen!
//					LOG.config(po.toString());
					fieldData[f] = recordId; //po.get(0).toString(); po.toString(); 
					
				} else if(column.getColumnName().endsWith("_ID")) {
					fieldData[f] = new Integer(rs.getInt(column.getColumnName()));
				} else {
					String value = rs.getString(column.getColumnName()); //	String	
					fieldData[f] = value==null ? "" : new String(value);
				}
			}
		}
		return fieldData;
	}
*/
	private void close() {
		LOG.config("");
		DB.close(resultSet, pstmt);
		resultSet = null;
		pstmt = null;
//		if (trx != null)
//			trx.close();
	}

}
