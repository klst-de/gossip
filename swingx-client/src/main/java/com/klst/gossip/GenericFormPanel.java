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
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import org.compiere.apps.form.FormPanel;
import org.compiere.grid.ed.VComboBox;
import org.compiere.grid.ed.VLookup;
import org.compiere.model.GridField;
import org.compiere.model.GridTable;
import org.compiere.model.MQuery;
import org.compiere.model.MRefList;
import org.compiere.model.TabModel;
import org.compiere.model.WindowModel;
import org.compiere.swing.CTextField;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.ValueNamePair;
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
			contentPane.add(statusBar, BorderLayout.PAGE_END);
			
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
		this.listSelectionMode = ListSelectionModel.SINGLE_SELECTION;
	}

	// @see interface ListSelectionModel
    // SINGLE_SELECTION = 0;
    // SINGLE_INTERVAL_SELECTION = 1;
    // MULTIPLE_INTERVAL_SELECTION = 2;	
	private int listSelectionMode = -1; // undefined
	private int windowNo = 0;
	private FormFrame formFrame;
	private int window_ID = -1; // window_ID the miniTable is from

	/* data selectionPanel : dynamisch erstellt
	 * mit controlern für die where clause in selections.
	 * Die controler werden dynamisch für Felder erstellt mit isSelectionColumn()
	 * Jeder UI-controler ist wiederum ein JPanel mit selectionLabel + selectionFld (TextField oder CHeckbox oder ... )
	 */
	private JXPanel selectionPanel = new JXPanel(); // (swingx)JXPanel extends (swing)JPanel implements AlphaPaintable, BackgroundPaintable, Scrollable
	private JXPanel mainPanel = new JXPanel();
	private Container statusBar = null; // JXStatusBar
	// class MiniTable extends CTable implements IMiniTable
	//                   (base)CTable extends JTable
	// TODO aus MiniTable ==> MXTable, später wird aus MXTable ===> MuliRowPanel
	// MuliRowPanel extends JXTable { // JXTable extends JTable implements TableColumnModelExtListener
	protected MXTable miniTable = null; // = MXTable.createXTable(TableModel dataModel);
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

	void setStatusBar(Container statusBar) {
		this.statusBar = statusBar;
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

		LOG.config("data selections.size="+selections.size() + " MXTable.SelectionMode="+miniTable.getSelectionMode() + " / set it to "+listSelectionMode);
		miniTable.setSelectionMode(listSelectionMode);
		
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

	private void initTableModelAndControler() {
		if(getGridTableModel()) {
			setModelAndControler();
		} else {
			// TODO
			LOG.warning("TO BE IMPEMENTED no GridTableModel");
		}
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
	
	protected WindowModel getWindowModel() {
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
		
		if(miniTable==null) {
			miniTable = MXTable.createXTable(gridTableModel);
		}
		
		setSelectWhereClause();

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
		assert(gridTableModel.getRowCount()==miniTable.getRowCount());
//		LOG.config("gridTableModel.getColumnCount()="+gridTableModel.getColumnCount()
//			+ "=?= miniTable.getColumnCount(includeHidden)="+miniTable.getTableColumnModelExt().getColumnCount(true) // boolean includeHidden
//			+ "=?= miniTable.getColumnCount()="+miniTable.getColumnCount());
			
		for(int f = 0; f < fields.length; f++) {
			GridField field = fields[f];
//			LOG.config("field "+f + " isSelectionColumn="+field.isSelectionColumn() + " "+field.getColumnName());
			if(field.isSelectionColumn()) {
				addSelection(field); 
			}								
		}
//		addSelection(fields); // additional selectionFields TODO
		if(gridTableModel.getRowCount()>0) {
			// JTable.changeSelection(rowIndex, columnIndex, toggle, extend);
			miniTable.changeSelection(0, -1, false, false);
//			setStatusDB(1+miniTable.getSelectedRow());
		}
		
		setPreferredSize();
		
	}

//	protected void formatTableFields(GridTable gridTableModel, MXTable minitable) {
//		GridField[] fields = gridTableModel.getFields();
//		boolean readOnly = true; // alle sind RO
//		for (int f = 0; f < minitable.getColumnCount(); f++) {	
//			GridField field = fields[f];
//			boolean isDisplayed = field.isDisplayed() & field.isDisplayedGrid(); // nur fields anzeigen, die isDisplayed UND isDisplayedGrid sind
//			int displayType = field.getDisplayType();
//			String header = field.getHeader();
//			String columnName = field.getColumnName();
//			Class<?> columnClass = gridTableModel.getColumnClass(f);
//			LOG.config("displayType="+displayType + " isKey="+field.isKey() + " isDisplayed="+isDisplayed + " isSelectionColumn="+field.isSelectionColumn() + 
//					": fields["+f+"].ColumnName="+columnName + " =?= " + minitable.getColumnName(f) + 
//					" ColumnClass:"+minitable.getColumnClass(f) + "/" + columnClass);
//			assert(columnName.equals(minitable.getColumnName(f)));
//		}
//	}
	
	protected void addSelection(GridField[] fields) {
		LOG.config("additional selectionFields"); // TODO brauche ich das??????
	}

	void addSelection(GridField field) {
		String header = field.getHeader();
		JXLabel label = new JXLabel(header);
		
		Component selection = null;
		int displayType = field.getDisplayType();
		switch (displayType) {
		case DisplayType.String: // 10
		case DisplayType.Text:   // 14
			LOG.config(header + " DisplayLength=" + field.getDisplayLength() + " FieldLength=" + field.getFieldLength()); // beide60
			selection = makeSelectionTextField(field);
			break;
		case DisplayType.YesNo:  // 20
			selection = makeSelectionComboBox(319, field); // 319 _YesNo / 53365:Yes-No-Unknown
			break;
		case DisplayType.List:   // 17
			selection = makeSelectionComboBox(field.getAD_Reference_Value_ID(), field);
			break;
		case DisplayType.Table:
		case DisplayType.TableDir: // 19
			selection = makeSelectionTableDir(field);
			break;
//		case DisplayType.DateTime:
//			selectionFld = makeSelectionDate(field);
//			break;
		default:
			LOG.warning("kein WHERE für "+header+" displayType=" + displayType);
			return;
		}
		
		addSelection(label, selection);
	}
	
	private Component makeSelectionTextField(GridField field) {
		String header = field.getHeader();
		CTextField selectionFld = new CTextField(MXTable.calculateWidth(field)); // (base)CTextField extends JTextField 
		selectionFld.addActionListener(event -> {
			LOG.config(header+" event:"+event);
			LOG.config(header+" hasChanged="+selectionFld.hasChanged() + " Value:"+selectionFld.getValue() + " oldValue:"+selectionFld.get_oldValue());
			String value = (String)(selectionFld.getValue());
			if(value.isEmpty()) {
				LOG.config(header+" kein where Zusatz");
				removeRestriction(field.getColumnName());
			} else {
//				log.config(header+" where Zusatz: " + field.getColumnName()+" LIKE '"+value+"%'");
				addRestriction(field.getColumnName(), MQuery.LIKE, value+"%");
			}
			if(selectionFld.hasChanged()) {
				selectionFld.set_oldValue();
				setModelAndControler();
			}
		});
		return selectionFld;
	}

	private Component makeSelectionComboBox(int reference_Value_ID, GridField field) {
		String header = field.getHeader();
		boolean optional = true; // auch ein leerer Eintrag ist dabei
		ValueNamePair[] valueName = MRefList.getList(Env.getCtx(), reference_Value_ID, optional);
		for (int i = 0; i < valueName.length; i++) {
			LOG.config("valueName:"+valueName[i].getID()+"/"+valueName[i].getName()+"/"+valueName[i].getValue());
		}
//		JXComboBox selectionFld = new JXComboBox(valueName); // hasChanged() fehlt
		VComboBox selectionFld = new VComboBox(valueName);
		selectionFld.setEditable(false); // keine freie Texteingabe! 1 aus n
		selectionFld.addActionListener(event -> {
			LOG.config(header+" event:"+event);
			LOG.config(header + " SelectedIndex:"+selectionFld.getSelectedIndex() + " SelectedItem:"+selectionFld.getSelectedItem());
			String emptyYorN = valueName[selectionFld.getSelectedIndex()].getID();
			if(emptyYorN.isEmpty()) {
				LOG.config(header+" kein where Zusatz");
				removeRestriction(field.getColumnName());
			} else {
				addRestriction(field.getColumnName(), MQuery.EQUAL, emptyYorN);
			}
			if(selectionFld.hasChanged()) { // hasChanged() ist Eigenschaft von VComboBox extends CComboBox
				selectionFld.set_oldValue();
				setModelAndControler();
			}
		});
		return selectionFld;
	}

	private Component makeSelectionTableDir(GridField field) {
		String header = field.getHeader();
		String columnName = field.getColumnSQL(false); // withAS=false
		// wie in Find.addSelectionColumn
		// VLookup(String columnName, boolean mandatory, boolean isReadOnly, boolean isUpdateable, Lookup lookup)
		VLookup selectionFld = new VLookup(columnName, false, false, true, field.getLookup());
		selectionFld.addActionListener(event -> {
			Object value = selectionFld.getValue();
			LOG.config(header+"makeSelectionTableDir event:"+event);
			LOG.config(header+" hasChanged="+selectionFld.hasChanged() + " Value:"+value + " oldValue:"+selectionFld.get_oldValue());
			if(value==null) {
				LOG.config(header+" kein where Zusatz value:"+value);
				removeRestriction(columnName);
			} else if(value instanceof Integer) {
//				log.config(header+" where Zusatz: " + columnName+" = "+value);
				addRestriction(columnName, MQuery.EQUAL, value);
			} else {
				LOG.config(header+" kann nix anfangen mit "+value);
			}
			if(selectionFld.hasChanged()) {
				selectionFld.set_oldValue();
				setModelAndControler();
			}
		});
		return selectionFld;
	}

//	private void setStatusDB(int current) { // TODO
//		String text = " " + current + " / " + miniTable.getRowCount() + " ";
//		statusBar.setStatusDB(text);
//	}
	
	private GridTable gridTableModel;
	private boolean getGridTableModel() {
		gridTableModel = null;
		WindowModel windowModel = getWindowModel();
		LOG.config("!!!!!!!!!!!!!!!! windowModel.TabCount="+(windowModel==null ? "-1" : windowModel.getTabCount()));
		if(windowModel!=null && windowModel.getTabCount()>=1) {
//			if(windowModel==null) return null; // kann null sein, wenn Berechtigung fehlt, ===========> GridWindowVO.create: No Window - AD_Window_ID=1000001
			// (base)GridTab hat einen Loader
			TabModel tabModel = windowModel.getTab(0); // es gibt mindestens einen Tab
			// (base)GridTable hat einen Loader / GridTable extends AbstractTableModel
			// nur so zur Info: in der DB steht WhereClause="RV_Unprocessed.CreatedBy=@#AD_User_ID@"
			LOG.config("tab "+tabModel + " tab.TableName="+tabModel.get_TableName() + " AD_Tab_ID="+tabModel.getAD_Tab_ID());
			LOG.config("tab.WhereClause:"+tabModel.getWhereClause());
			LOG.config("tab.WhereExtended:"+tabModel.getWhereExtended()); // was ist der Unterschied zu getWhereClause() ?
			
			gridTableModel = tabModel.getTableModel(); // macht boolean initTab(boolean synchron)
			LOG.config("WhereClause:"+gridTableModel.getSelectWhereClause()); // ist leer!? !!!!!!!!!!!!!!!!! tatsächlich wird nicht selektiert
			
			assert(!tabModel.isOnlyCurrentRows());
			int onlyCurrentDays = 0;
			gridTableModel.setSelectWhereClause(tabModel.getWhereClause(), tabModel.isOnlyCurrentRows(), onlyCurrentDays);
			
			whereText.setText(gridTableModel.getSelectWhereClause());
			LOG.config("OrderClause:"+gridTableModel.getOrderClause());
		}
		return (gridTableModel!=null);
	}
	
}
