package com.klst.gossip;

import static org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker.StateValue;
import javax.swing.table.JTableHeader;

import org.compiere.grid.VPanel;
import org.compiere.grid.ed.VDate;
import org.compiere.grid.ed.VEditor;
import org.compiere.grid.ed.VLookup;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.Lookup;
import org.compiere.util.DisplayType;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTableHeader;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import gov.nasa.arc.mct.gui.impl.HidableTabbedPane;

public class Tab extends JPanel implements ComponentListener {

	private static final long serialVersionUID = -2597982525624660612L;
	
	private static final Logger LOG = Logger.getLogger(Tab.class.getName());

	private static EnumMap<StateValue, Icon> statusToTrafficlights = new EnumMap<>(StateValue.class);
	
	// AD Komponenten:
	// MTab mit den Metadaten
	// swing Komponenten:
	// JPanel parentTab
	// HidableTabbedPane parentContainer
	// window / bzw JFrame
	// rootFrame

	private WindowFrame frame;
	private GridTab gridTab;
	private List<GridField> fields;
	private GenericTableModel tableModel;
	private GenericDataLoader loader;

	// ui
	JXTable jXTable; // JXTable extends JTable implements TableColumnModelExtListener
	ListSelectionModel listSelectionModel; // the ListSelectionModel that is used to maintain rowselection state
	VPanel vPanel = null; // VPanel extends JTabbedPane TODO
	
	// ctor
	/* super ctors
	 * 	   public JPanel(LayoutManager layout, boolean isDoubleBuffered) 
	 *     public JPanel(LayoutManager layout) 
	 *     public JPanel(boolean isDoubleBuffered) 
	 */
	public Tab(GridTab gridTab, WindowFrame frame) {
		super(new BorderLayout());
		LOG.config("gridTab "+gridTab + ", WindowFrame frame:"+frame);
		this.frame = frame;
		this.gridTab = gridTab;
		this.fields = Arrays.asList(this.gridTab.getFields());
		this.addComponentListener(this);
		this.setName(this.gridTab.getName());
		
		// in GridTab gibt es ein GridTable m_mTable // GridTable extends AbstractTableModel
		// wir wollen unser eigenes Model haben GenericTableModel extends AbstractTableModel
		// und org.jdesktop.swingx.JXTable nutzen, darin 
		// - GenericEditor
		// - NumberEditor
		// - BooleanEditor
		this.jXTable = createXTable();
		this.listSelectionModel = jXTable.getSelectionModel();
		
		if(statusToTrafficlights.isEmpty()) {
			statusToTrafficlights.put(StateValue.PENDING, frame.AIT.getImageIcon(frame.AIT.RLI, WindowFrame.SMALL_ICON_SIZE));
			statusToTrafficlights.put(StateValue.STARTED, frame.AIT.getImageIcon(frame.AIT.YLI, WindowFrame.SMALL_ICON_SIZE));
			statusToTrafficlights.put(StateValue.DONE   , frame.AIT.getImageIcon(frame.AIT.GLI, WindowFrame.SMALL_ICON_SIZE));
		}
	}

	public void setLoadState(StateValue state) {
		LOG.config(this.getName()+" StateValue:"+state);
//		frame.actionStatus.setText(state.name()); // sieht nicht gut aus => Ampel:
		frame.actionStatus.setIcon(statusToTrafficlights.get(state));
		if(state.equals(StateValue.STARTED)) {
			frame.setVisible(true);
			updateStatusBar(0);
		} else if(state.equals(StateValue.DONE)) {
			updateStatusBar(0);
		}
	}

	public GridTab getGridTab() {
		return this.gridTab;
	}
	
	public void refresh() {
		this.loader = initDataLoader();
		this.loader.execute();
	}
	
	private void updateStatusBar(int rowIndex) {
		frame.tableRows.setText(""+(rowIndex+1)+"/"+jXTable.getRowCount());
	}

	/* setRowSelectionInterval(index0, index1)
	 * Selects the rows from <code>index0</code> to <code>index1</code>, inclusive.
	 */
	private void setRowSelection(int rowIndex) {
		// includeSpacing if false, return the true cell bounds -computed by subtracting 
		//  the intercellspacing from the height and widths ofthe column and row models
		jXTable.scrollRectToVisible(jXTable.getCellRect(rowIndex, 0, true)); // includeSpacing:true
		jXTable.setRowSelectionInterval(rowIndex, rowIndex);
		updateStatusBar(rowIndex);
		
		// experiment:
//		if(vPanel!=null) {
//			LOG.warning(this.getName()+"TODO currentRow:"+rowIndex);
//			Component[] comps = vPanel.getComponentsRecursive();
//			LOG.warning("TODO comps.length:"+comps.length);
//			for (int i = 0; i < comps.length; i++) { //... GridControler Zeile 1031
//				Component comp = comps[i];
//				LOG.config(comp.toString());
//				if(comp instanceof VLookup) {
//					VLookup vl = (VLookup)comp;
//					LOG.warning("TODO comps vl.getValue():"+vl.getValue());
//				}
//			}
//		}
	}
	
	public void first() {
		setRowSelection(0);
	}
	
	public void previous() {
		// diese methode hat Sinn im SingleRowModus
		// welches ist die currentRow im MultiRowModus?
		// - die kleinste selektierte!
		int currentRow = -1;
		int[] selected = jXTable.getSelectedRows(); // can be empty
		if(selected.length==0) {
			// und nun? wrap around
			currentRow = jXTable.getRowCount();
		} else {
			currentRow = selected[0];
		}
		LOG.config("currentRow:"+currentRow);
		currentRow--;
		if(currentRow<0) {
			last(); // wrap around
			return; // nix selektiert oder bereits bei first
		}
		setRowSelection(currentRow);
	}
	
	public void next() {
		int[] selected = jXTable.getSelectedRows(); // can be empty
		int currentRow = selected.length==0 ? -1 : selected[selected.length-1];
		LOG.config("currentRow:"+currentRow);
		currentRow++;
		setRowSelection( currentRow<jXTable.getRowCount() ? currentRow : 0 );
	}

	public void last() {
//		LOG.config("VisibleRowCount:"+jXTable.getVisibleRowCount() + " RowCount:"+jXTable.getRowCount());
//		jXTable.scrollRectToVisible(jXTable.getCellRect(jXTable.getRowCount()-1, 0, true));
//		LOG.config("VisibleRowCount:"+jXTable.getVisibleRowCount() + " RowCount:"+jXTable.getRowCount());
//		jXTable.setRowSelectionInterval(jXTable.getRowCount()-1, jXTable.getRowCount()-1);
		setRowSelection(jXTable.getRowCount()-1);
	}
	
	private int getWindowNo() {
		return frame.getWindowNo();
	}
	private List<GridTab> getGridTabs() {
		return frame.getGridTabs();
	}
	private List<Tab> getTabs() {
		return frame.getTabs();
	}
	
	// nur zur Doku
	private GenericDataLoader getDataLoaderBUGGY() {
        frame.tabPane = new HidableTabbedPane(); // BUG. so geht es nicht
        for (int i = 0; i < getGridTabs().size(); i++) { // ohne first
        	GridTab gt = getGridTabs().get(i);
        	Tab t = getTabs().get(i); 
        	frame.tabPane.addTab(gt.getName(), t);
        	t.initModelAndTable();
        	t.initDataLoader();
        }
        frame.jPanel.add(frame.tabPane, BorderLayout.CENTER);
        frame.pack();
        
        return this.loader;
	}
//	public GenericDataLoader getDataLoader(JComponent vPanel) { // TEST TODO
//		frame.jPanel.add(vPanel, BorderLayout.CENTER);
//		
////		GridTab gridTab = getGridTabs().get(0); // first Tab
////		Tab tab = getTabs().get(0); 
//        frame.tabPane = new HidableTabbedPane(gridTab.getName(), vPanel);
////        for (int i = 1; i < getGridTabs().size(); i++) { // ohne first
////        	GridTab gt = getGridTabs().get(i);
////        	Tab t = getTabs().get(i); 
////        	frame.tabPane.addTab(gt.getName(), t);
////        	t.loader = getDataLoader(gt, t);
////        }
//        frame.jPanel.add(frame.tabPane, BorderLayout.CENTER);
//        frame.pack();
////		setLocationRelativeTo(null);; // im caller! oben links würde es sonst angezeigt
//        
//        initModelAndTable();
//        return initDataLoader();
//	}
	public GenericDataLoader getDataLoader() { // TODO nicht nur first ==> this
		GridTab gridTab = getGridTabs().get(0); // first Tab
		Tab tab = getTabs().get(0); 
        frame.tabPane = new HidableTabbedPane(gridTab.getName(), tab);
        for (int i = 1; i < getGridTabs().size(); i++) { // ohne first
        	GridTab gt = getGridTabs().get(i);
        	Tab t = getTabs().get(i); 
        	frame.tabPane.addTab(gt.getName(), t);
        	t.initModelAndTable();
        	t.initDataLoader();
        }
        frame.jPanel.add(frame.tabPane, BorderLayout.CENTER);
        frame.pack();
//		setLocationRelativeTo(null);; // im caller! oben links würde es sonst angezeigt
        
        tab.initModelAndTable();    
        return tab.initDataLoader();
	}

	private VPanel singleRowPanel() { // TODO test mit VPanel
		LOG.warning("new VPanel("+this.getName()+", "+this.getWindowNo()+")");
		vPanel = new VPanel(this.getName(), this.getWindowNo()); //public VPanel(String Name, int WindowNo) {
		for (Iterator<GridField> iterator = fields.iterator(); iterator.hasNext();) {
			GridField field = iterator.next();
			VEditor editor = getEditor(field); // factory TODO
			if (editor == null) {
				LOG.warning("kein Editor für "+field);
				continue;
			} else {
				LOG.config(field+ " editoe:"+editor);
			}
//			field.addPropertyChangeListener(editor);
			vPanel.addFieldBuffered(editor, field);	
		}
//		add(vPanel, BorderLayout.CENTER);	
		return vPanel;
	}
	
	private void initModelAndTable() {
		this.tableModel = new GenericTableModel(this.gridTab, getWindowNo());
		
		LOG.config(this.getName()+" isSingleRow:"+gridTab.isSingleRow());
		// --- wg. Dimension:
		VPanel vp = singleRowPanel(); // liefert this.vPanel ohne add
		Dimension preferredDim =vPanel.getPreferredSize(); // preferredSize
		LOG.warning("vPanel dimension:"+preferredDim);
		if(gridTab.isSingleRow()) { // isSingleRow aka Single Row Panel in MigLayout für dieses Tab
			add(vPanel, BorderLayout.CENTER);	
		} else {
			this.setPreferredSize(preferredDim);
	        JScrollPane scrollpane = new JScrollPane(this.jXTable);
	        Stacker stacker = new Stacker(scrollpane);
	        jXTable.setName(gridTab.getName());
	        add(stacker, BorderLayout.CENTER);			
		}

        jXTable.setColumnControlVisible(true);
        // replace grid lines with striping 
        jXTable.setShowGrid(false, false);
        jXTable.addHighlighter(HighlighterFactory.createSimpleStriping());
        // initialize preferred size for table's viewable area
//        jXTable.setVisibleRowCount(10); // ergibt sich aus preferredDim zB bei country 13, da es sich aus city berechnet!
//        ! es berrechnet sich aus dem größten SingleRow Tab !?
//        TODO aber es sollte sich aus country SingleRow Panel berechnen!

//        CustomColumnFactory factory = new CustomColumnFactory();

        jXTable.setModel(tableModel);
        listSelectionModel.addListSelectionListener(event -> {
            int firstIndex = event.getFirstIndex();
            //int lastIndex = event.getLastIndex();
            updateStatusBar(firstIndex);
        });
	}
	
	/*
	public static final int String     = 10;
	public static final int Integer    = 11;
	public static final int Amount     = 12;
	public static final int ID         = 13; Lookup
	public static final int Text       = 14;
	public static final int Date       = 15;
	public static final int DateTime   = 16;
	public static final int List       = 17; Lookup
	public static final int Table      = 18; Lookup
	public static final int TableDir   = 19; Lookup
	public static final int YesNo      = 20;
	public static final int Location   = 21;
	public static final int Number     = 22;
	public static final int Binary     = 23;
	public static final int Time       = 24;
	public static final int Account    = 25;
	public static final int RowID      = 26;
	public static final int Color      = 27;
	public static final int Button	   = 28;
	public static final int Quantity   = 29;
	public static final int Search     = 30; Lookup
	public static final int Locator    = 31;
	public static final int Image      = 32;
	public static final int Assignment = 33;
	public static final int Memo       = 34;
	public static final int PAttribute = 35;
	public static final int TextLong   = 36;
	public static final int CostPrice  = 37;
	public static final int FilePath   = 38;
	public static final int FileName   = 39;
	public static final int URL        = 40;
	public static final int PrinterName= 42;
	public static final int Chart           = 53370;
	public static final int FilePathOrName  = 53670;

 */
	private VEditor getEditor(GridField mField) { // TODO mField in fied umbenennen
		LOG.config(mField.toString());
		if (mField == null)
			return null; // gut ist das nicht
		
		VEditor editor = null;
		int displayType = mField.getDisplayType();
		String columnName = mField.getColumnName();
		boolean mandatory = mField.isMandatory(false);      //  no context check
		boolean readOnly = mField.isReadOnly();
		boolean updateable = mField.isUpdateable();
		int WindowNo = mField.getWindowNo();
		LOG.config("displayType:"+displayType
				+ " columnName:"+columnName
				+ " mandatory:"+mandatory
				+ " readOnly:"+readOnly
				+ " updateable:"+updateable
				+ " WindowNo:"+WindowNo
				+ " Not a Field:"+mField.isHeading()
				);
		
		//  Not a Field
		if (mField.isHeading())
			return null;

		//	Lookup (displayType == List || displayType == Table || displayType == TableDir || displayType == Search)
		if (DisplayType.isLookup(displayType) || displayType == DisplayType.ID)
		{
			VLookup vl = new VLookup(columnName, mandatory, readOnly, updateable, mField.getLookup());
			vl.setName(columnName);
			vl.setField (mField);
			editor = vl;
		}
		//	YesNo - BooleanEditor
//		else if (displayType == DisplayType.YesNo)
//		{
//			VCheckBox vc = new VCheckBox(columnName, mandatory, readOnly, updateable, mField.getHeader(), mField.getDescription(), tableEditor);
//			vc.setName(columnName);
//			vc.setField (mField);
//			editor = vc;
//		}

		//	Date
		else if (DisplayType.isDate(displayType))
		{
			if (displayType == DisplayType.DateTime)
				readOnly = true;
			VDate vd = new VDate(columnName, mandatory, readOnly, updateable, displayType, mField.getHeader());
			vd.setName(columnName);
			vd.setField (mField);
			editor = vd;
		}

		else {
			LOG.warning(columnName + " - Unknown Type: " + displayType + " ersatzweise VLookup!!!");
			Lookup getLookup = mField.getLookup();
			LOG.warning("ignoriert: " + getLookup + " ersatzweise null");
			VLookup vl = new VLookup(columnName, mandatory, readOnly, updateable, null);
			vl.setName(columnName);
			vl.setField (mField);
			editor = vl;

		}

		return editor;
	}

	private GenericDataLoader initDataLoader() {
 		this.loader = new GenericDataLoader(this.tableModel);
 		
        BindingGroup group = new BindingGroup();
        group.addBinding(Bindings.createAutoBinding(READ, loader, 
        		BeanProperty.create("progress"),
        		frame.progressBar, BeanProperty.create("value")));
        group.addBinding(Bindings.createAutoBinding(READ, loader, 
        		BeanProperty.create("state"),
        		this, BeanProperty.create("loadState"))); // call setLoadState 
        group.bind();

//		setVisible(true); // in setLoadState
		return loader;		
	}

	// aus org.jdesktop.swingx.demos.table.XTableDemo
	protected JXTable createXTable() {
		JXTable table = new JXTable() {

			private static final long serialVersionUID = -2974517519415177299L;

			@Override
			protected JTableHeader createDefaultTableHeader() {
				return new JXTableHeader(columnModel) {
					private static final long serialVersionUID = -4124370542563896297L;

					@Override
					public void updateUI() {
						super.updateUI();
						// need to do in updateUI to survive toggling of LAF
						if (getDefaultRenderer() instanceof JLabel) {
							((JLabel) getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

						}
					}

				};
			}

		};
		return table;
	}

	// wg. ComponentListener
	@Override
	public void componentResized(ComponentEvent e) {
		LOG.warning(""+e.getComponent());
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		LOG.warning(""+e.getComponent());
	}

	@Override
	public void componentShown(ComponentEvent e) {
		LOG.warning(""+e.getComponent());
		if(e.getComponent() instanceof Tab) {
			Tab tab = (Tab)e.getComponent();
			LOG.config("ParentTab:"+gridTab.getParentTab());
			this.loader.execute();
		}
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		LOG.warning(""+e.getComponent());
	}

}
