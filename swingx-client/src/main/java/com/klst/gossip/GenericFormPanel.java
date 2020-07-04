package com.klst.gossip;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.compiere.apps.form.FormPanel;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.GridTable;
import org.compiere.model.MQuery;
import org.compiere.model.WindowModel;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;

/**
 *	Generic Form Panel
 *	
 *  The ContentPane in BorderLayout contains
 *  <br> at PAGE_START a selectionPanel
 *  <br> at CENTER     a mainPanel with scrollable miniTable
 *  <br> at PAGE_END   a statusBar
 *  
 *  <p> in subclass the mainPanel can contain additional miniTables, 
 *  expl InfoBP.mainPanel contains bpPanel + subPane with Contacts and Locations
 *	
 *  @author https://github.com/homebeaver
 */
public class GenericFormPanel implements FormPanel {

	private static final Logger LOG = Logger.getLogger(GenericFormPanel.class.getName());
	
	@Override // implements FormPanel
	public void init(int WindowNo, FormFrame frame) {
		LOG.config("WindowNo="+WindowNo + " FormFrame:"+frame);
		setWindowNo(WindowNo);
		formFrame = frame;
		try {
			jbInit();
			
			Container contentPane = formFrame.getContentPane();
			LOG.config("ContentPane:"+contentPane);
			contentPane.add(selectionPanel, BorderLayout.PAGE_START);
			contentPane.add(mainPanel, BorderLayout.CENTER);
//			contentPane.add(statusBar, BorderLayout.PAGE_END);
			
			formFrame.pack(); // Causes this Window to be sized to fit the preferred size and layouts of its subcomponents
			formFrame.setVisible(true);
			//Dimension initialSize = formFrame.getContainer().getBounds().getSize(); 
			Dimension initialSize = formFrame.getContentPane().getBounds().getSize(); 
			LOG.config("initialSize:" + initialSize + " miniTable.Dim:"+this.miniTable.getPreferredSize());
			initialSize.width = this.miniTable.getPreferredSize().width + 100; // TODO die 100
			formFrame.getContentPane().setPreferredSize(initialSize);
			
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "", e);
		}
	}

	@Override // implements FormPanel
	public void dispose() {
		LOG.config("WindowNo="+getWindowNo());
		if (formFrame != null) {
			formFrame.dispose();
		}
		formFrame = null;
	}

	protected GenericFormPanel(int window_ID) {
		this(window_ID, false);
	}
	protected GenericFormPanel(int window_ID, boolean showWhere) {
		LOG.config("ctor window_ID="+window_ID + " showWhere="+showWhere);
		this.window_ID = window_ID;
		this.showWhere = showWhere;
	}

	private int windowNo = 0;
	private FormFrame formFrame;
	private int window_ID = -1; // window_ID the miniTable is from

	/* selectionPanel : dynamisch erstellt
	 * mit controlern für die where clause in selections.
	 * Die controler werden dynamisch für Felder erstellt mit isSelectionColumn()
	 * Jeder UI-controler ist wiederum ein JPanel mit selectionLabel + selectionFld (TextField oder CHeckbox oder ... )
	 */
	private JXPanel selectionPanel = new JXPanel(); // (swingx)JXPanel extends (swing)JPanel implements AlphaPaintable, BackgroundPaintable, Scrollable
	private JXPanel mainPanel = new JXPanel();
	// class MiniTable extends CTable implements IMiniTable
	//                   (base)CTable extends JTable
	// TODO aus MiniTable ==> MXTable, später wird aus MXTable ===> MuliRowPanel
	// MuliRowPanel extends JXTable { // JXTable extends JTable implements TableColumnModelExtListener
	MXTable miniTable; // = MXTable.createXTable(TableModel dataModel);
	protected JTable getTable() {
		return miniTable;
	}
	
	// selectionPanel:
	protected List<JComponent> selections = new ArrayList<JComponent>();
	private boolean showWhere = true;
	private JXLabel whereLabel = new JXLabel();
	private JXTextField whereText = new JXTextField();
	void addSelection(Component label, Component component) {
		JComponent selection = new JXPanel(new BorderLayout());
		selection.add(label, BorderLayout.PAGE_START);
		selection.add(component, BorderLayout.PAGE_END);
		this.selections.add(selection);
	}

	// Restriction Handler macht aus columnName, operator, object eine Stück sql
	private Map<String, MQuery> restrictions = new HashMap<String, MQuery>();
	protected void removeRestriction(String columnName) {
		restrictions.remove(columnName);
	}
	protected void addRestriction(String columnName, String operator, Object code) {
		MQuery mQuery = new MQuery();
		mQuery.addRestriction(columnName, operator, code);
		restrictions.put(columnName, mQuery);
		LOG.config("restriction "+restrictions.size()+" : "+mQuery);
	}

	void setWindowNo(int windowNo) {
		this.windowNo = windowNo;
	}
	int getWindowNo() {
		return windowNo;
	}
	int getWindowId() {
		return window_ID;
	}

	// some kind of "init()" method to initialize the user interface (the main frame, and all it's menus, buttons, textedit boxes, etc).
	// the name comes with Borland's JBuilder happens to call this method "jbInit()" (as in "JBuilder UI initialization").
	// TODO rename
	private void jbInit() throws Exception {
		selectionPanel.setLayout(new GridBagLayout());
		whereLabel.setText(Msg.translate(Env.getCtx(), "where"));
		whereText.setEditable(false);
		if(showWhere) addSelection(whereLabel, whereText);
		
		mainPanel.setLayout(new BorderLayout());
		//miniTable.setMultiSelection(true);  // (default false) Should be performed before the class is set.
		initTableModelAndControler(); // macht getColumnNames + loadData + miniTable.setModel(tablemodel) + selections controler definieren
		
		LOG.config("selections.size="+selections.size());
		int i=0;
		for( Iterator<JComponent> iterator = selections.iterator(); iterator.hasNext(); i++ ) {
			JComponent component = iterator.next();
			/*
Parameters:
    gridx The initial gridx value.
    gridy The initial gridy value.
    gridwidth The initial gridwidth value.
    gridheight The initial gridheight value.
    weightx The initial weightx value.
    weighty The initial weighty value.
    anchor The initial anchor value.
    fill The initial fill value.
    insets The initial insets value.
    ipadx The initial ipadx value.
    ipady The initial ipady value.
			 */
			selectionPanel.add(component, 
					new GridBagConstraints(i, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5,5,5,5), 0, 0)
					);
		}
		
//		mainPanel.add(new JScrollPane(miniTable), BorderLayout.CENTER);
		initMainPanel(mainPanel, miniTable);
		
		registerTableSelectionListener();
	}

	/*
	 * default:
	 * at CENTER     a mainPanel with scrollable miniTable
	 * 
	 * expl in subclass InfoBP:
	 * additional subPanel with Contacts + Addresses
	 */
	protected void initMainPanel(JPanel mainPanel, Component miniTable) {
		mainPanel.add(new JScrollPane(miniTable), BorderLayout.CENTER);
	}

	// MiniTableSelectionListener muss in Unterklasse implementiert werden
	protected void registerTableSelectionListener() throws Exception {
		LOG.warning("TO BE IMPEMENTED in Subclass");
	}
	
	protected WindowModel getGridWindow() {
		// wrapped (base)org.compiere.model.GridWindow
		return WindowModel.get(Env.getCtx(), this.getWindowNo(), this.getWindowId());
	}

	private final static boolean finalCall = false;
	private final static boolean isOnlyCurrentRows = false;
	private final static int onlyCurrentDays = 0;
	private void setSelectWhereClause() {
		LOG.config("WhereClause:"+gridTableModel.getSelectWhereClause() + "/"+whereText.getText()); 
		
		StringBuffer sql = new StringBuffer(whereText.getText());
		restrictions.forEach((k,v) -> {
			if(sql.length()>0) sql.append(" AND "); // whereText kann leer sein!
			sql.append(v.getWhereClause());
		});
		
		gridTableModel.close(finalCall);
		gridTableModel.setSelectWhereClause(sql.toString(), isOnlyCurrentRows, onlyCurrentDays);
		LOG.config("WhereClause:"+gridTableModel.getSelectWhereClause());
	}

	static public Vector<Vector<Object>> getData(GridTable gridTableModel) {
		// mit boolean GridTable.open (int maxRows=0) wird alles geladen:
		boolean success = gridTableModel.open(0);
		LOG.config("load all data via GridTable.open success="+success + " RowCount="+gridTableModel.getRowCount());
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		for (int row = 0; row < gridTableModel.getRowCount(); row++) {
			Vector<Object> rowVector = new Vector<Object>();
			for (int col = 0; col < gridTableModel.getColumnCount(); col++) {
				Object o = gridTableModel.getValueAt(row, col);
				rowVector.add(o); // bei RowCount=135039 exception
			}
			data.add(rowVector);
		}
		return data;
	}
	
	static public Vector<String> getFieldsNames(GridTable gridTableModel) {
		Vector<String> columnNames = new Vector<String>();
		GridField[] fields = gridTableModel.getFields();
		for (int i = 0; i < fields.length; i++) {
			String columnName = fields[i].getColumnName();
			LOG.config("columnName["+i+"]="+columnName);
			columnNames.add(columnName);
		}
		return columnNames;
	}

	/*
	 * width : calculated in miniTable.getColumnsWidth()
	 * height: 2/3 of ScreenSize
	 */
	protected Dimension getPreferredSize() {
//		int width = miniTable.getColumnsWidth(); // TODO
		Dimension dimension = miniTable.getPreferredSize();
//		dimension.width = width;
		
		dimension.height = Toolkit.getDefaultToolkit().getScreenSize().height*2/3;
		return dimension;
	}
	protected void setPreferredSize(Dimension dimension) {
		mainPanel.setPreferredSize(dimension);
	}
	private void setPreferredSize() {
		setPreferredSize(getPreferredSize());
	}

	protected void setModelAndControler() {
		// TODO umbenennen in dynInit - das ist die dynamische initialisierung
		// setSelectWhereClause bereitet das sql vor für den Loader, wrapper für gridTableModel.setSelectWhereClause
		// getData für DefaultTableModel als Vector mittels Loader in gridTableModel
		// dann miniTable und selections controler definieren
		
		setSelectWhereClause();
		
		miniTable = MXTable.createXTable(gridTableModel);
		// javax.swing.table.DefaultTableModel erwartet raw type Vector data
		Vector<Vector<Object>> data = getData(gridTableModel); // Vector data wird für worker benötigt
		DefaultTableModel dataModel = new DefaultTableModel(data, getFieldsNames(gridTableModel));
		miniTable.setModel(dataModel);

//// ----------------
//			boolean success = gridTableModel.open(0);
//			log.config("load all data via GridTable.open success="+success + " RowCount="+gridTableModel.getRowCount());
//			Vector<List<Object>> data = new Vector<List<Object>>(gridTableModel.getRowCount());
//			
//			GenericDataLoader dataLoader = new GenericDataLoader(gridTableModel, data);
//	        dataLoader.addPropertyChangeListener(event -> {
//	        	if ("state".equals(event.getPropertyName())) {
//	        		//setLoadState((StateValue)event.getNewValue());
//	        		log.config(" StateValue:"+(StateValue)event.getNewValue());
//	        	}
//	        });
//			dataLoader.execute();
	//
//			try {
////				Vector<List<Object>> 
//				data = dataLoader.get(); // Waits if necessary for the computation to complete, and thenretrieves its result. 
//				data = dataLoader.get(500L, TimeUnit.MILLISECONDS);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (ExecutionException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (TimeoutException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			log.config(" gewartet: dataLoader.isDone="+dataLoader.isDone());
//			
//			DefaultTableModel tablemodel = new DefaultTableModel(data, getFieldsNames());
//			
//			statusBar.setStatusLine(gridTableModel.getSelectWhereClause());
	//// <<<<<<<<<<<	
//			miniTable.setModel(tablemodel);

			GridField[] fields = gridTableModel.getFields();
// Umgehung für BUG in MiniTable : an Pos 0 (FIRST) darf kein Boolean feld stehen, indem ich versuche heuristisch eine KeyColumn zu setzten
//			boolean keyFound = false;
//			for(int f = 0; f < miniTable.getColumnCount(); f++) {
//				if(fields[f].isKey() && fields[f].getColumnName().endsWith("_ID")) {
//					miniTable.setKeyColumnIndex(f);
//					keyFound = true;
//					break;
//				}
//			}
//			if(!keyFound) {
//				LOG.warning("Probably no key in miniTable!");
//			}

			assert(gridTableModel.getRowCount()==miniTable.getRowCount());
//			formatTableFields(gridTableModel, miniTable);
			
			for(int f = 0; f < miniTable.getColumnCount(); f++) {	
				GridField field = fields[f];
				if(field.isSelectionColumn()) {
					addSelection(field); 
				}								
			}
			setPreferredSize();
	}

//	protected void formatTableFields(GridTable gridTableModel, MiniTable minitable) {
	protected void formatTableFields(GridTable gridTableModel, MXTable minitable) {
		GridField[] fields = gridTableModel.getFields();
		boolean readOnly = true; // alle sind RO
		for (int f = 0; f < minitable.getColumnCount(); f++) {	
			GridField field = fields[f];
			boolean isDisplayed = field.isDisplayed() & field.isDisplayedGrid(); // nur fields anzeigen, die isDisplayed UND isDisplayedGrid sind
			int displayType = field.getDisplayType();
			String header = field.getHeader();
			String columnName = field.getColumnName();
			Class<?> columnClass = gridTableModel.getColumnClass(f);
			LOG.config("displayType="+displayType + " isKey="+field.isKey() + " isDisplayed="+isDisplayed + " isSelectionColumn="+field.isSelectionColumn() + 
					": fields["+f+"].ColumnName="+columnName + " =?= " + minitable.getColumnName(f) + 
					" ColumnClass:"+minitable.getColumnClass(f) + "/" + columnClass);
			assert(columnName.equals(minitable.getColumnName(f)));
// TODO:			
//			switch(displayType) {
//			case DisplayType.Date: // 15 Date
//				field.setDisplayType(DisplayType.Date); // ohne Time
//				minitable.setColumnClass(f, field);
//				break;
//			case DisplayType.DateTime: // 16 DateDoc
//				field.setDisplayType(DisplayType.DateTime);
//				minitable.setColumnClass(f, field);
//				break;
//			case DisplayType.String:   // 10 DocumentNo
//			case DisplayType.ID:       // 13 C_BPartner.C_BPartner_ID
//			case DisplayType.List:     // 17 DocStatus
//			case DisplayType.Table:    // 18 CreatedBy, UpdatedBy
//			case DisplayType.TableDir: // 19 AD_Client_ID, ...
//				minitable.setColumnClass(f, field);
//				break;
//			case DisplayType.Location: // 21 Location TODO
//				field.setDisplayType(DisplayType.TableDir);
//				minitable.setColumnClass(f, field);
//				break;
//			default:
//				minitable.setColumnClass(f, columnClass, displayType, readOnly, header);
//			}
//			
//			if(isDisplayed) {
//				// Both isDisplayed() and isDisplayedGrid() should be true
//			} else {
//				minitable.setColumnVisibility(minitable.getColumn(f), false);
//			}
		}
	}
	
	void addSelection(GridField field) {
		// TODO selections controler definieren			
	}
	
	private GridTable gridTableModel;
	private GridTable getGridTableModel() {
		WindowModel gridWindow = getGridWindow();
		if(gridWindow==null) return null; // kann null sein, wenn Berechtigung fehlt, ===========> GridWindowVO.create: No Window - AD_Window_ID=1000001
//		gridWindow.dispose(); // nur zum Test
		assert(gridWindow.getTabCount()>=1);
		// (base)GridTab hat einen Loader
		GridTab tab = gridWindow.getTab(0); // es gibt mindestens einen Tab
		// (base)GridTable hat einen Loader / GridTable extends AbstractTableModel
		gridTableModel = tab.getTableModel(); // macht boolean initTab(boolean synchron)
		// nur so zur Info: in der DB steht WhereClause="RV_Unprocessed.CreatedBy=@#AD_User_ID@"
		LOG.config("tab "+tab.get_TableName() + " AD_Tab_ID="+tab.getAD_Tab_ID());
		LOG.config("tab.WhereClause:"+tab.getWhereClause());
		LOG.config("tab.WhereExtended:"+tab.getWhereExtended()); // was ist der Unterschied zu getWhereClause() ?
		LOG.config("WhereClause:"+gridTableModel.getSelectWhereClause()); // ist leer!? !!!!!!!!!!!!!!!!! tatsächlich wird nicht selektiert
		
		assert(!tab.isOnlyCurrentRows());
		int onlyCurrentDays = 0;
		gridTableModel.setSelectWhereClause(tab.getWhereClause(), tab.isOnlyCurrentRows(), onlyCurrentDays);
		
		whereText.setText(gridTableModel.getSelectWhereClause());
		LOG.config("OrderClause:"+gridTableModel.getOrderClause());
		
		return gridTableModel;
	}
	
	private void initTableModelAndControler() {
		getGridTableModel();		
//		XetModelAndControler(true); // requery - beim ersten mal ist requery nicht notwendig --------------- DOCH!!!!
		setModelAndControler();
//		setPreferredSize();
	}
}
