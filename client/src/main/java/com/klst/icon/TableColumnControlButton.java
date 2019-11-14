package com.klst.icon;

import javax.swing.Action;
import javax.swing.Icon;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.table.ColumnControlButton;

// ColumnControl == der kleine Controler ColumnControlButton rechts bei den Tabellenüberschriften
// org.jdesktop.swingx.icon.ColumnControlIcon implementiert das Icon, es ist ausprogrammiert, also kein gif oder jpg
// das Icon austauschen
public class TableColumnControlButton extends ColumnControlButton { // ColumnControlButton extends JButton

	private static final long serialVersionUID = 8446883147181512624L;
	static final int SMALL_ICON_SIZE = 16;

	static AbstractImageTranscoder AIT = AbstractImageTranscoder.getInstance();
	
	// ctor
	public TableColumnControlButton(JXTable table) {
		super(table);
	}
	
	// das Icon austauschen
	@Override
	protected void updateActionUI() {
        if (getAction() == null) return;
        Icon icon = AIT.getImageIcon(AIT.MENU, SMALL_ICON_SIZE);
        getAction().putValue(Action.SMALL_ICON, icon);
	}
	
}
