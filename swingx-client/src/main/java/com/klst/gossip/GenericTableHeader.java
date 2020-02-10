package com.klst.gossip;

import javax.swing.JLabel;
import javax.swing.table.TableColumnModel;

import org.jdesktop.swingx.JXTableHeader;

public class GenericTableHeader extends JXTableHeader { // JXTableHeader extends JTableHeader implements TableColumnModelExtListener

	private static final long serialVersionUID = -1583919509911123066L;
//	private static final Logger LOG = Logger.getLogger(GenericTableHeader.class.getName());
	
	private static int defaultHorizontalAlignment = JLabel.CENTER;
	
	// ctor
	public GenericTableHeader(TableColumnModel tableColumnModel) {
		super(tableColumnModel);
	}
	
	/* need to do in updateUI to survive toggling of LAF (so steht es in XTableDemo)
	 * ... und tatsächlich, wenn LAF umgeschaltet wird zB. nach Nimbus, dann
	 * geht die (default) Zentrierung aka HorizontalAlignment im TableHeader verloren
	 * 
	 * das gilt für MuliRowPanel, das JXTable als Superklasse hat
	 * und auch für MenuPanel, das JXTreeTable als member nutzt
	 * 
	 * TODO: TableHeader ist im MenuPanel nicht korrekt gerendert, wenn mit Nimbus als default LAF begonnen wird
	 */
	@Override
	public void updateUI() {
		super.updateUI();
		if (getDefaultRenderer() instanceof JLabel) {
			((JLabel) getDefaultRenderer()).setHorizontalAlignment(defaultHorizontalAlignment);
		}
	}

}
