package com.klst.gossip;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.ListSelectionModel;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.TableColumn;

import org.compiere.model.GridField;
import org.jdesktop.swingx.table.DefaultTableColumnModelExt;
import org.jdesktop.swingx.table.TableColumnExt;
import org.jdesktop.swingx.table.TableColumnModelExt;

// GridField aka TableColumn, vll von TableColumn ableiten
// diese Klasse kapselt GridField[] fields
// und hat nur LOG-Funktionalität

// durch das Ableiten von org.jdesktop.swingx.table.DefaultTableColumnModelExt sind alle notwendigen Methoden wg implements TableColumnModelExt vorimplementiert
public class GridFields extends DefaultTableColumnModelExt // extends DefaultTableColumnModel
                        implements TableColumnModelExt {

	private static final long serialVersionUID = 5218395949102734661L;
	
	private static final Logger LOG = Logger.getLogger(GridFields.class.getName());

//	private GridFieldBridge[] fields = null; // gelöscht, denn die Felderliste gibt es schon in der Oberklasse
	
	// in DefaultTableColumnModelExt private List<TableColumn> initialColumns = new ArrayList<TableColumn>();
	// ctor
	//public DefaultTableColumnModelExt()
//	public GridFields(GridFieldBridge[] fields) {
//		super();
//		this.fields = fields;
//	}
	public GridFields(GridField[] gfields) {
		super();
		LOG.config("GridField[].length="+gfields.length); // Land:33
//		this.fields = new GridFieldBridge[gfields.length]; // löschen
		for(int c = 0; c < gfields.length; c++) {
			GridField gfield = gfields[c];
			GridFieldBridge aColumn = new GridFieldBridge(gfield);
//			this.addColumn(aColumn); // befüllt die Feldliste
			if(aColumn.isDisplayed() && aColumn.isDisplayedGrid()) {
				// display column
				this.addColumn(aColumn); 
			} else {
				LOG.config("not visible: "+aColumn);
/*				aColumn.setVisible(false); liefert
Exception in thread "AWT-EventQueue-0" java.lang.ArrayIndexOutOfBoundsException: 6 >= 6
	at java.util.Vector.elementAt(Vector.java:477)
	at javax.swing.table.DefaultTableColumnModel.getColumn(DefaultTableColumnModel.java:294)
	at com.klst.gossip.SingleRowPanel.getSingleRowPanelSize(SingleRowPanel.java:44)
	at com.klst.gossip.Tab.getSingleRowPanelSize(Tab.java:229)
	at com.klst.gossip.Tab.initModelAndTable(Tab.java:244)
	at com.klst.gossip.Tab.getDataLoader(Tab.java:210)
	at com.klst.gossip.RootFrame.openNewFrame(RootFrame.java:156)
	at com.klst.client.MenuPanel.mouseClicked(MenuPanel.java:403)
	at com.klst.client.MenuPanel$VTreePanel_mouseAdapter.mouseClicked(MenuPanel.java:421)
	at java.awt.AWTEventMulticaster.mouseClicked(AWTEventMulticaster.java:270)
	at java.awt.Component.processMouseEvent(Component.java:6542)
	at javax.swing.JComponent.processMouseEvent(JComponent.java:3324)
	at org.jdesktop.swingx.JXTreeTable.processMouseEvent(JXTreeTable.java:460)
	at java.awt.Component.processEvent(Component.java:6304)
	at java.awt.Container.processEvent(Container.java:2239)
	at java.awt.Component.dispatchEventImpl(Component.java:4889)
	at java.awt.Container.dispatchEventImpl(Container.java:2297)
	at java.awt.Component.dispatchEvent(Component.java:4711)
	at java.awt.LightweightDispatcher.retargetMouseEvent(Container.java:4904)
	at java.awt.LightweightDispatcher.processMouseEvent(Container.java:4544)
	at java.awt.LightweightDispatcher.dispatchEvent(Container.java:4476)
	at java.awt.Container.dispatchEventImpl(Container.java:2283)
	at java.awt.Window.dispatchEventImpl(Window.java:2746)
	at java.awt.Component.dispatchEvent(Component.java:4711)
	at java.awt.EventQueue.dispatchEventImpl(EventQueue.java:760)
	at java.awt.EventQueue.access$500(EventQueue.java:97)
	at java.awt.EventQueue$3.run(EventQueue.java:709)
	at java.awt.EventQueue$3.run(EventQueue.java:703)
	at java.security.AccessController.doPrivileged(Native Method)
	at java.security.ProtectionDomain$JavaSecurityAccessImpl.doIntersectionPrivilege(ProtectionDomain.java:74)
	at java.security.ProtectionDomain$JavaSecurityAccessImpl.doIntersectionPrivilege(ProtectionDomain.java:84)
	at java.awt.EventQueue$4.run(EventQueue.java:733)
	at java.awt.EventQueue$4.run(EventQueue.java:731)
	at java.security.AccessController.doPrivileged(Native Method)
	at java.security.ProtectionDomain$JavaSecurityAccessImpl.doIntersectionPrivilege(ProtectionDomain.java:74)
	at java.awt.EventQueue.dispatchEvent(EventQueue.java:730)
	at java.awt.EventDispatchThread.pumpOneEventForFilters(EventDispatchThread.java:205)
	at java.awt.EventDispatchThread.pumpEventsForFilter(EventDispatchThread.java:116)
	at java.awt.EventDispatchThread.pumpEventsForHierarchy(EventDispatchThread.java:105)
	at java.awt.EventDispatchThread.pumpEvents(EventDispatchThread.java:101)
	at java.awt.EventDispatchThread.pumpEvents(EventDispatchThread.java:93)
	at java.awt.EventDispatchThread.run(EventDispatchThread.java:82)
 */
//				this.isRemovedToInvisibleEvent(c); // bewirkt nichts ?????
			}
		}
		// TODO: die Breite ist nicht angepasst!
	}
	
	
// wg. implements TableColumnModelExt
//
//	@Override
//	public int getColumnCount(boolean includeHidden) {
//		LOG.config(""+super.getColumnCount(false)+"/"+super.getColumnCount(true));
//		return super.getColumnCount(includeHidden);
//	}
//
//	@Override
//	public List<TableColumn> getColumns(boolean includeHidden) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public TableColumnExt getColumnExt(Object identifier) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public TableColumnExt getColumnExt(int columnIndex) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//		
// wg. implements TableColumnModel
//	
//	@Override
//	public void addColumn(TableColumn aColumn) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void removeColumn(TableColumn column) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void moveColumn(int columnIndex, int newIndex) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void setColumnMargin(int newMargin) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public int getColumnCount() {
//		return fields.length;
//	}
//
//	@Override
//	public Enumeration<TableColumn> getColumns() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public int getColumnIndex(Object columnIdentifier) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public TableColumn getColumn(int columnIndex) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public int getColumnMargin() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public int getColumnIndexAtX(int xPosition) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public int getTotalColumnWidth() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public void setColumnSelectionAllowed(boolean flag) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public boolean getColumnSelectionAllowed() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public int[] getSelectedColumns() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public int getSelectedColumnCount() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public void setSelectionModel(ListSelectionModel newModel) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public ListSelectionModel getSelectionModel() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void addColumnModelListener(TableColumnModelListener x) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void removeColumnModelListener(TableColumnModelListener x) {
//		// TODO Auto-generated method stub
//		
//	}
}