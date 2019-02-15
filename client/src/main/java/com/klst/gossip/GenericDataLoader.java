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
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.SecureEngine;
import org.compiere.util.Trx;

// aka class Loader implements Serializable, Runnable in GridTable

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
		LOG.config(sql + ";\n rowsToFind:"+rowsToFind + "; trxName:"+trxName);
		//
		pstmt = DB.prepareStatement(sql, trxName);
		// m_rowCount = m_loader.open(maxRows);
		resultSet = pstmt.executeQuery();
		while(resultSet.next() && (dbResultRows.size() < rowsToFind) && !isCancelled()) {
//			LOG.config(dbResultRows.size() +"/"+ rowsToFind);
			Object[] rowData = readData(resultSet, dbResultRows.size()); //BigInteger number = nextPrimeNumber(); readData liest die cols der DB-row
			
			dbResultRows.add(rowData);
			publish(rowData);
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
    
//    private String getSelectAll() {
//    	return "SELECT * FROM "+tableModel.getDbTableName();
//    }
    
    private String getSelectClause() {
    	// TODO
    	return null;
    }
    
	private Object[] readData(ResultSet rs, int row) throws SQLException {
		int size = tableModel.getColumnCount();
		Object[] fieldData = new Object[size]; // renamed from rowData
		String columnName = null;
		int displayType = 0;
		
		MTable mTable = null;
//		List<Integer> recordIds;
		//	Types see also MField.createDefault
		try
		{
			//	get row data
			for (int f = 0; f < size; f++)
			{
				// field metadata
				GridField field = this.fields[f];
				columnName = field.getColumnName();
				displayType = field.getDisplayType(); // aka AD_Reference_ID 
				if(row==0) {
					LOG.config(f+":"+field.toString() + " -- toStringX:"+field.toStringX());
					// 0:GridField[AD_Client_ID=null, IsDisplayed=true] -- toStringX:MField[AD_Client_ID=null,DisplayType=19] 
					
					LOG.config(f+":SeqNoGrid="+field.getSeqNoGrid() + " columnName="+columnName 
					+ " DisplayType="+displayType + " Field_ID="+field.getAD_Field_ID() 
					+ " Reference="+field.getAD_Reference_Value_ID() + " DefaultValue="+field.getDefaultValue() 
					+ " Tab_ID="+field.getAD_Tab_ID() + " Column_ID="+field.getAD_Column_ID()
					);
				}
//				if(field.getSeqNoGrid()==0) {
//					// do not display
//				} else {	
				
				
//	So ist es gut, aber nur für	f==0:		
//				if(displayType==DisplayType.TableDir && f==0) { // AD_Reference_ID=19 : Table Direct
				if(displayType==DisplayType.TableDir && columnName.endsWith("_ID")) { // AD_Reference_ID=19 : Table Direct
					int recordId = rs.getInt(f+1);
//					int tableId = 112;
//					MTable mTable = new MTable(Env.getCtx(), tableId, trxName);
//					LOG.warning("tableName:"+columnName.substring(0, columnName.lastIndexOf("_ID")));
					mTable = MTable.get(Env.getCtx(), columnName.substring(0, columnName.lastIndexOf("_ID")));
					PO po = mTable.getPO(recordId, trxName);
//					LOG.warning(mTable + " recordId:"+recordId + " po:"+po.toString());
					fieldData[f] = po.toString(); // das liefert ja nach Definition von toString()
// 	MClient[0-SYSTEM] --besser--> Name	
//	X_AD_Org[0] --besser--> value
//	MCurrency[102-EUR-€,Euro,Precision=2/4 --besser--> iso_code
					// besser: Name oder Value? TODO
//					fieldData[f] = po.get_Value("Name"); 
				} 
				//	YesNo
				else if(displayType == DisplayType.YesNo) // AD_Reference_ID=20 : CheckBox
				{
					String value = rs.getString(f+1);
					if (field.isEncryptedColumn()) value = (String)decrypt(value);
					fieldData[f] = value.equals("Y") ? Boolean.TRUE : Boolean.FALSE; 
				} else
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
				else if (displayType == DisplayType.RowID) {
					fieldData[f] = null;
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
				if (field.isEncryptedColumn() && displayType != DisplayType.YesNo) {
					fieldData[f] = decrypt(fieldData[f]);
				}
//				LOG.config(f+":"+fieldData[f]);
			}
		}
		catch (SQLException e)
		{
			LOG.log(Level.SEVERE, "row:"+row + ", col:"+columnName + ", displayType=" + displayType, e);
			throw e;
		}
		return fieldData;
	}	//	readData

	/**
	 *	Encrypt
	 *	@param xx clear data 
	 *	@return encrypted value
	 */
	private Object encrypt (Object xx)
	{
		if (xx == null)
			return null;
		return SecureEngine.encrypt(xx);
	}	//	encrypt
	
	/**
	 * 	Decrypt
	 *	@param yy encrypted data
	 *	@return clear data
	 */
	private Object decrypt (Object yy)
	{
		if (yy == null)
			return null;
		return SecureEngine.decrypt(yy);
	}	//	decrypt

	private void close() {
		LOG.config("");
		DB.close(resultSet, pstmt);
		resultSet = null;
		pstmt = null;
//		if (trx != null)
//			trx.close();
	}

}