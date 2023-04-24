package io.homebeaver.gossip;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;

import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.jdesktop.swingx.JXComboBox;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTree;
import org.jdesktop.swingx.renderer.StringValue;
import org.jdesktop.swingx.renderer.StringValues;

import com.klst.model.MTree;
import com.klst.model.MTreeNode;

import io.homebeaver.gossip.swingx.CTree;

// ersetzt (client)org.compiere.apps.form.VTreeMaintenance
/* extends JXPanel ? in VTreeMaintenance ist member panel ein CPanel extends JPanel
 * panel besteht aus comboBox treeField mit auswahl des baums
 * Verwanschaft mit GenericFormPanel - auch dort: implements FormPanel
 */
public class TreeMaintenancePanel extends TreeMaintenance 
	implements FormPanel, ActionListener, ListSelectionListener, PropertyChangeListener {

	static Logger LOG = Logger.getLogger(TreeMaintenancePanel.class.getName());

	TreeMaintenancePanel() {
		super();
		LOG.info("\nTreeMaintenancePanel extends TreeMaintenance ersetzt org.compiere.apps.form.VTreeMaintenance");
	}
	private int windowNo = 0;
	private FormFrame formFrame;
	private JXPanel panel = new JXPanel();
	private BorderLayout mainLayout = new BorderLayout();
	private JXComboBox treeField; // name wie in VTreeMaintenance
	private JXTree centerTree; // name wie in VTreeMaintenance, dort aber VTreePanel extends CPanel
	private GossipTreeModel treeModel; // GossipTreeModel extends DefaultTreeModel
	private Container statusBar = null; // JXStatusBar
	
	@Override // implements interface FormPanel
	public void init(int WindowNo, FormFrame frame) {
		LOG.info("WindowNo=" + WindowNo + " FormFrame:" + frame);
		setWindowNo(WindowNo);
		formFrame = frame;
		try {
			preInit();
			jbInit();
//			frame.getContentPane().add(panel, BorderLayout.CENTER);
			Container contentPane = frame.getContentPane();
			LOG.config("ContentPane:"+contentPane);
			contentPane.add(treeField, BorderLayout.PAGE_START);
			contentPane.add(panel, BorderLayout.CENTER);
//			contentPane.add(statusBar, BorderLayout.PAGE_END);
			//action_loadTree(104); // treeID=104 ID=104 Name=GardenWorld Organization, das führt zu NPE
			
			formFrame.pack(); // Causes this Window to be sized to fit the preferred size and layouts of its subcomponents
			formFrame.setVisible(true);
		} catch (Exception ex) {
			LOG.log(Level.SEVERE, "TreeMaintenancePanel.init", ex);
		}
	}

	@Override // implements interface FormPanel
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override // wg ActionListener TODO evtl. Lambda
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// für treeField ==> Lambda
	}

	@Override // wg ListSelectionListener TODO evtl. Lambda
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override  // wg PropertyChangeListener TODO evtl. Lambda
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

	void setWindowNo(int windowNo) {
		this.windowNo = windowNo;
	}
	int getWindowNo() {
		return windowNo;
	}

	// name wie in VTreeMaintenance
	private void preInit() {
		panel.setLayout(mainLayout);
		treeField = new JXComboBox(super.getTreeData());
		panel.add(treeField, BorderLayout.NORTH);
		treeField.addActionListener( ae -> {
			Object o = treeField.getSelectedItem();
			KeyNamePair knp = (KeyNamePair)o;
			LOG.info("key/treeID="+knp.getKey()+" ID="+knp.getID()+" Name="+knp.getName());
			action_loadTree(knp.getKey());
		});
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Tree root");
		treeModel = new GossipTreeModel(root, true);
		centerTree = new CTree(treeModel);
		centerTree.setRootVisible(true);
		centerTree.setEditable(true);
		panel.add(new JScrollPane(centerTree), BorderLayout.CENTER); 
	}

	private void jbInit() throws Exception {
//		panel.setLayout(mainLayout);		
	}
	private void action_loadTree(int treeID) {
//		MTree mtree = new MTree(Env.getCtx(), treeID, null); // bei diesem ctor kein loadNodes!!! TODO ctor private
		boolean editable = false;
		String whereClause = null;
		MTree mtree = new MTree(Env.getCtx(), treeID, editable, false, whereClause, null);
		MTreeNode root = mtree.getRootNode();
//		root.setName(Msg.getMsg(Env.getCtx(), m_tree.getName()).replace("&" , "")); // translate name of menu.
		log.info("root=" + root);
//		GossipTreeModel treeModel = new GossipTreeModel(root, true);
//		treeModel.setMTree(mtree);
//		centerTree.setModel(treeModel); // deprecated
/*
Weil in
 StringValue sv = (Object value) -> { ...
auf value==null nicht abgefangen wurde, bekomme ich diesen Tracelog:

Exception in thread "AWT-EventQueue-0" java.lang.NullPointerException: Cannot invoke "Object.getClass()" because "value" is null
	at io.homebeaver.gossip.TreeMaintenancePanel$1.lambda$0(TreeMaintenancePanel.java:134)
	at org.jdesktop.swingx.renderer.ComponentProvider.getValueAsString(ComponentProvider.java:287)
	at org.jdesktop.swingx.renderer.LabelProvider.format(LabelProvider.java:116)
	at org.jdesktop.swingx.renderer.ComponentProvider.configureContent(ComponentProvider.java:332)
	at org.jdesktop.swingx.renderer.ComponentProvider.getRendererComponent(ComponentProvider.java:182)
	at org.jdesktop.swingx.renderer.WrappingProvider.getRendererComponent(WrappingProvider.java:285)
	at org.jdesktop.swingx.renderer.WrappingProvider.getRendererComponent(WrappingProvider.java:48)
	at org.jdesktop.swingx.renderer.DefaultTreeRenderer.getTreeCellRendererComponent(DefaultTreeRenderer.java:138)
	at org.jdesktop.swingx.JXTree$DelegatingRenderer.getTreeCellRendererComponent(JXTree.java:1403)
	at java.desktop/javax.swing.plaf.basic.BasicTreeUI$NodeDimensionsHandler.getNodeDimensions(BasicTreeUI.java:3223)
	at java.desktop/javax.swing.tree.AbstractLayoutCache.getNodeDimensions(AbstractLayoutCache.java:497)
	at java.desktop/javax.swing.tree.VariableHeightLayoutCache$TreeStateNode.updatePreferredSize(VariableHeightLayoutCache.java:1344)
	at java.desktop/javax.swing.tree.VariableHeightLayoutCache.rebuild(VariableHeightLayoutCache.java:723)
	at java.desktop/javax.swing.tree.VariableHeightLayoutCache.setModel(VariableHeightLayoutCache.java:111)
	at java.desktop/javax.swing.plaf.basic.BasicTreeUI.setModel(BasicTreeUI.java:506)
	at java.desktop/javax.swing.plaf.basic.BasicTreeUI$Handler.propertyChange(BasicTreeUI.java:3903)
	at java.desktop/java.beans.PropertyChangeSupport.fire(PropertyChangeSupport.java:343)
	at java.desktop/java.beans.PropertyChangeSupport.firePropertyChange(PropertyChangeSupport.java:335)
	at java.desktop/java.beans.PropertyChangeSupport.firePropertyChange(PropertyChangeSupport.java:268)
	at java.desktop/java.awt.Component.firePropertyChange(Component.java:8722)
	at java.desktop/javax.swing.JTree.setModel(JTree.java:945)
	at org.jdesktop.swingx.JXTree.setModel(JXTree.java:1664)
	at io.homebeaver.gossip.TreeMaintenancePanel.action_loadTree(TreeMaintenancePanel.java:168) <=======

 */
		treeModel.setMTree(mtree);
		treeModel.setRoot(null);
		treeModel.setRoot(root);
//		treeModel.reload();
	}
}
