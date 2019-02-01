package com.klst.gossip;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.SwingWorker;

import org.compiere.model.I_AD_Column;
import org.compiere.model.MField;
import org.compiere.util.DB;
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
// public class BankDataLoader extends SwingWorker<List<Object[]>, Object[]> {
public class GenericDataLoader extends SwingWorker<List<Object[]>, Object[]> {

	private static final Logger LOG = Logger.getLogger(GenericDataLoader.class.getName());
	
	private GenericTableModel tableModel;

	// ctor
	public GenericDataLoader(GenericTableModel tableModel) {
		this.tableModel = tableModel;
		LOG.config("tableModel "+this.tableModel);
		tableModel.getDbTableName(); // für select * from XXX
		getSelectClause(); // für *
	}

	private String sql;
	private PreparedStatement pstmt;
	private int rowsToFind = -1; // Ergebnis von select count(*)
	private ResultSet resultSet;
	private List<Object[]> dbResultRows;                                    //banks3; // V - also eine SQL 
	private List<MField> fields;
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.SwingWorker#doInBackground()
	 */
	@Override
	// die zentrale Methode
	protected List<Object[]> doInBackground() throws Exception {
		//postgresql need trx to use cursor based resultset
		String trxName =  Trx.createTrxName("Loader");
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
		pstmt = DB.prepareStatement(sql, trxName);
		// m_rowCount = m_loader.open(maxRows);
		fields = tableModel.getColumns();
		resultSet = pstmt.executeQuery();
		while(resultSet.next() && (dbResultRows.size() < rowsToFind) && !isCancelled()) {
//			LOG.config(dbResultRows.size() +"/"+ rowsToFind);
			Object[] rowData = readData(resultSet); //BigInteger number = nextPrimeNumber(); readData liest die cols der DB-row
			
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
//		for (Object[] rowData : chunks) {
//			textArea.append(rowData[1] + ":" + rowData[2] + "\n"); // rowData[0] mit AD_Client_ID lasse ich weg
//		}
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
    
	private Object[] readData(ResultSet rs) throws SQLException {
		int size = this.fields.size();
//		LOG.config("columns.size:"+size + " =="+this.tableModel.getColumnCount());
		Object[] fieldData = new Object[size];
		for (int c = 0; c < size; c++) {
			//MColumn metacolum = columns.get(c); // das nur einmal machen 
			MField field = fields.get(c);
			I_AD_Column column = field.getAD_Column();
//			column.getAD_Element_ID()
//			column.getAD_Table_ID() // muss == tableModel.table_ID sein
//			column.getColumnSQL()
			
//			if(field.getSeqNoGrid()==0) {
//				// nicht im Grid benötigt
//			} else {
				// TODO Display Logic berücksichtigen, zB @IsPostcodeLookup@ = 'Y'
				if(column.getColumnName().endsWith("_ID")) {
					fieldData[c] = new Integer(rs.getInt(column.getColumnName()));	//	Integer	
				} else {
					String value = rs.getString(column.getColumnName()); //	String	
					fieldData[c] = value==null ? "" : new String(value);	
				}
//			}
		}

		return fieldData;
	}

	private void close() {
		LOG.config("");
		DB.close(resultSet, pstmt);
		resultSet = null;
		pstmt = null;
//		if (trx != null)
//			trx.close();
	}

}
