package com.klst.gossip;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingWorker;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.SecureEngine;
import org.compiere.util.Trx;

// aka class Loader implements Serializable, Runnable in GridTable

//T - the result type returned by this SwingWorker's doInBackground and get methods
//V - the type used for carrying out intermediate results by this SwingWorker's publish and process methods
public class GenericDataLoader extends SwingWorker<List<Object[]>, Object[]> {

	private static final Logger LOG = Logger.getLogger(GenericDataLoader.class.getName());
	
	private GenericDataModel dataModel;

	// ctor
	public GenericDataLoader(GenericDataModel dm) {
		this.dataModel = dm;
		LOG.config("dataModel "+this.dataModel);
		this.trxName =  Trx.createTrxName(GenericDataLoader.class.getName());
		dm.getDbTableName(); // für select * from XXX
		getSelectClause(); // für *
	}

	private String sql;
	private PreparedStatement pstmt;
	private int rowsToFind = -1; // Ergebnis von select count(*)
	private ResultSet resultSet;
	private List<Object[]> dbResultRows;
	private GridFields fields = null; // GridFields extends DefaultTableColumnModelExt implements TableColumnModelExt
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
		dataModel.setRowsToLoad(rowsToFind);
		close();
		
		dbResultRows = new ArrayList<Object[]>(rowsToFind);
		fields = dataModel.getColumns();
		//	Create SELECT Part
		StringBuffer select = new StringBuffer("SELECT ");
		for(int f=0; f<fields.getColumnCount(true); f++) {
			if(f > 0) select.append(",");
			GridFieldBridge field = (GridFieldBridge)fields.getColumn(f);
			select.append(field.getColumnSQL()); // withAS
		}
//		for (int i = 0; i < fields.length; i++)
//		{
//			if(i > 0) select.append(",");
//			GridField field = fields[i];
//			select.append(field.getColumnSQL(true));	//	ColumnName or Virtual Column // boolean withAS
//		}
		select.append(" FROM ").append(dataModel.getDbTableName());
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
		if(isCancelled()) {
			LOG.warning("cancelled "+dbResultRows.size()+"/"+rowsToFind + " "+100 * dbResultRows.size() / rowsToFind);
			firePropertyChange("cancelled", false, true);
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

    protected void done() {
    	setProgress(100);
    }

    private String getSelectCountStar() {
    	return "SELECT COUNT(*) FROM "+dataModel.getDbTableName();
    }
    
//    private String getSelectAll() {
//    	return "SELECT * FROM "+tableModel.getDbTableName();
//    }
    
    private String getSelectClause() {
    	// TODO
    	return null;
    }
    
    private static Map<Integer,String> DISPLAYTYPE = new HashMap<Integer,String>(); // erleichtert das Testen
    static {
    	DISPLAYTYPE.put(25, "Account");
    	DISPLAYTYPE.put(12, "Amount");
    	DISPLAYTYPE.put(33, "Assignment");
    	DISPLAYTYPE.put(23, "Binary");
    	DISPLAYTYPE.put(28, "Button	");
    	DISPLAYTYPE.put(53370, "Chart");
    	DISPLAYTYPE.put(27, "Color");
    	DISPLAYTYPE.put(37, "CostPrice");
    	DISPLAYTYPE.put(15, "Date");
    	DISPLAYTYPE.put(16, "DateTime");
    	DISPLAYTYPE.put(39, "FileName");
    	DISPLAYTYPE.put(38, "FilePath");
    	DISPLAYTYPE.put(53670, "FilePathOrName");
    	DISPLAYTYPE.put(13, "ID");
    	DISPLAYTYPE.put(32, "Image");
    	DISPLAYTYPE.put(11, "Integer");
    	DISPLAYTYPE.put(17, "List");
    	DISPLAYTYPE.put(21, "Location");
    	DISPLAYTYPE.put(31, "Locator");
    	DISPLAYTYPE.put(34, "Memo");
    	DISPLAYTYPE.put(22, "Number");
    	DISPLAYTYPE.put(35, "PAttribute");
    	DISPLAYTYPE.put(42, "PrinterName");
    	DISPLAYTYPE.put(29, "Quantity");
    	DISPLAYTYPE.put(26, "RowID");
    	DISPLAYTYPE.put(30, "Search");
    	DISPLAYTYPE.put(10, "String");
    	DISPLAYTYPE.put(18, "Table");
    	DISPLAYTYPE.put(19, "TableDir");
    	DISPLAYTYPE.put(14, "Text");
    	DISPLAYTYPE.put(36, "TextLong");
    	DISPLAYTYPE.put(24, "Time");
    	DISPLAYTYPE.put(40, "URL");
    	DISPLAYTYPE.put(20, "YesNo");      
    }
	private Object[] readData(ResultSet rs, int row) throws SQLException {
		int size = dataModel.getColumnCount();
		Object[] fieldData = new Object[size]; // renamed from rowData
		String columnName = null;
		int displayType = 0;
//		try { // slow down for testing
//			Thread.sleep( 10 );
//		} catch (InterruptedException e1) {
//			e1.printStackTrace();
//		}
		try
		{	
			//	get row data
			for (int f = 0; f < size; f++)
			{
				// field metadata
				//GridField field = this.fields[f];
				GridFieldBridge field = (GridFieldBridge)fields.getColumn(f);
				columnName = field.getColumnName();
				displayType = field.getDisplayType(); // aka AD_Reference_ID 
				if(row==0) {
					LOG.config(f+": SeqNoGrid="+field.getSeqNoGrid() + " DisplayedGrid="+field.isDisplayedGrid() + " Displayed="+field.isDisplayed() 
						+ "\t"+DISPLAYTYPE.get(displayType) + "\t Header="+field.getHeader() + " columnName="+columnName 
						+ " DisplayType="+displayType);
//					LOG.config(f+":SeqNoGrid="+field.getSeqNoGrid() + " columnName="+columnName 
//					+ " DisplayType="+displayType + " Field_ID="+field.getAD_Field_ID() 
//					+ " Reference="+field.getAD_Reference_Value_ID() + " DefaultValue="+field.getDefaultValue() 
//					+ " Tab_ID="+field.getAD_Tab_ID() + " Column_ID="+field.getAD_Column_ID()
//					);
				}
				
				if(displayType == DisplayType.String
				|| displayType == DisplayType.PrinterName
				|| displayType == DisplayType.Text
				|| displayType == DisplayType.TextLong
				|| displayType == DisplayType.URL 
				|| displayType == DisplayType.PrinterName
				|| displayType == DisplayType.FilePath 
				|| displayType == DisplayType.FileName 
				|| displayType == DisplayType.FilePathOrName
				|| displayType == DisplayType.Memo
				|| displayType == DisplayType.Button   // TODO testen
				|| displayType == DisplayType.List		// obwohl es in isLookup liegt
				|| columnName.equals("AD_Language") // BUG?: columnName=AD_Language DisplayType=18 , aber character varying(6)
				// BUG: columnName=AD_Language DisplayType=18 Field_ID=5753 Reference=106 DefaultValue= Tab_ID=135 Column_ID=7050
				//      Unzulässiger Wert für den Typ int : es_HN
				) {                                                // ==> String (clear/password)
					fieldData[f] = rs.getString(f+1);
					
//				} else if( DisplayType.isLookup(displayType) // List/17 | Table/18 | TableDir/19 | Search/30 ,
					// ABER columnName=BankType DisplayType=17 Field_ID=83995 Reference=53978 DefaultValue=B Tab_ID=227 Column_ID=85656
					// ist banktype character(1) DEFAULT 'B'::bpchar,
					// das gilt für alle List, daher ==> String
				} else if( displayType == DisplayType.Table
						|| displayType == DisplayType.TableDir
						|| displayType == DisplayType.Search
						|| displayType == DisplayType.ID
						|| displayType == DisplayType.RowID
						|| displayType == DisplayType.Location
						|| displayType == DisplayType.Locator
						|| displayType == DisplayType.Account
						|| displayType == DisplayType.Assignment // TODO testen
						|| displayType == DisplayType.Color // TODO testen
						|| displayType == DisplayType.PAttribute
						|| displayType == DisplayType.Integer		// isNumeric!
						|| displayType == DisplayType.Image // TODO testen
						|| displayType == DisplayType.Chart // TODO testen
				) {                                                // ==> Integer
//					if(field.getAD_Column_ID()==85656) {
//						fieldData[f] = rs.getString(f+1);
//					} else {
						fieldData[f] = new Integer(rs.getInt(f+1));
						if(rs.wasNull()) fieldData[f] = null;
//					}
					
				} else if( DisplayType.isNumeric(displayType)
				) {                                                // Amount, Number!, CostPrice, Integer!, Quantity ==> BigDecimal
					fieldData[f] = rs.getBigDecimal(f+1);
					
				} else if( displayType == DisplayType.YesNo
				) {                                                // YesNo ==> Boolean : CheckBox
					String value = rs.getString(f+1);
					if (field.isEncryptedColumn()) value = (String)decrypt(value);
					fieldData[f] = value.equals("Y") ? Boolean.TRUE : Boolean.FALSE; 
					
				} else if( DisplayType.isDate(displayType)
				) {                                                // Date, DateTime, Time ==> Timestamp
					fieldData[f] = rs.getTimestamp(f+1);
					
				} else if( DisplayType.isLOB(displayType)
				) {                                                // LOB: Binary, TextLong ==> Clob | Blob | String			
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
					
				} else {
					LOG.log(Level.SEVERE, "row:"+row + ", col:"+columnName + ", displayType=" + displayType);
					throw new AdempiereException("cannot readData displayType=" + displayType + " row:"+row + ", col:"+columnName);
				}
				

//				if(displayType==DisplayType.TableDir && columnName.endsWith("_ID")) { // AD_Reference_ID=19 : Table Direct
//					int recordId = rs.getInt(f+1);
//					mTable = MTable.get(Env.getCtx(), columnName.substring(0, columnName.lastIndexOf("_ID")));
//					PO po = mTable.getPO(recordId, trxName);
//					fieldData[f] = po.toString(); // das liefert ja nach Definition von toString()
//				} 
//				//	YesNo
//				else if(displayType == DisplayType.YesNo) // AD_Reference_ID=20 : CheckBox
//				{
//					String value = rs.getString(f+1);
//					if (field.isEncryptedColumn()) value = (String)decrypt(value);
//					fieldData[f] = value.equals("Y") ? Boolean.TRUE : Boolean.FALSE; 
//				} else
//				//	Integer, ID, Lookup (UpdatedBy is a numeric column)
//				if (displayType == DisplayType.Integer
//					|| (DisplayType.isID(displayType) 
//						&& (columnName.endsWith("_ID") || columnName.endsWith("_Acct") 
//							|| columnName.equals("AD_Key") || columnName.equals("AD_Display"))) 
//					|| columnName.endsWith("atedBy"))
//				{
//					fieldData[f] = new Integer(rs.getInt(f+1));	//	Integer
//					if (rs.wasNull())
//						fieldData[f] = null;
//				}
//				//	Number
//				else if (DisplayType.isNumeric(displayType))
//					fieldData[f] = rs.getBigDecimal(f+1);			//	BigDecimal
//				//	Date
//				else if (DisplayType.isDate(displayType))
//					fieldData[f] = rs.getTimestamp(f+1);			//	Timestamp
//				//	RowID or Key (and Selection)
//				else if (displayType == DisplayType.RowID) {
//					fieldData[f] = null;
//				}
//				//	LOB
//				else if (DisplayType.isLOB(displayType))
//				{
//					Object value = rs.getObject(f+1);
//					if (rs.wasNull())
//						fieldData[f] = null;
//					else if (value instanceof Clob) 
//					{
//						Clob lob = (Clob)value;
//						long length = lob.length();
//						fieldData[f] = lob.getSubString(1, (int)length);
//					}
//					else if (value instanceof Blob)
//					{
//						Blob lob = (Blob)value;
//						long length = lob.length();
//						fieldData[f] = lob.getBytes(1, (int)length);
//					}
//					else if (value instanceof String)
//						fieldData[f] = value;
//					else if (value instanceof byte[])
//						fieldData[f] = value;
//				}
//				//	String
//				else
//					fieldData[f] = rs.getString(f+1);				//	String
				
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
