package io.homebeaver.gossip;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.jdesktop.swingx.JXPanel;

/*

in (client) org.compiere.apps.form gibt es VTreeMaintenance :

public class VTreeMaintenance extends org.compiere.apps.form.TreeMaintenance
	implements org.compiere.apps.form.FormPanel
	, ActionListener, ListSelectionListener, PropertyChangeListener
	
im AD Menu ist TreeMaintenance unter
- System Admin
-- General Rules
--- System Rules
    Tree Maintenance, Node_id 465, II/m_imageIndicator=X (ACTION_Form also ausprogrammierte Panel)

Idee: TreePanel soll Oberklasse von MenuPanel werden

 */
public class TreePanel extends JXPanel implements FormPanel, ActionListener, TreeSelectionListener {

	private static final long serialVersionUID = 2248627373354576613L;
	static Logger LOG = Logger.getLogger(TreePanel.class.getName());
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(int WindowNo, FormFrame frame) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
