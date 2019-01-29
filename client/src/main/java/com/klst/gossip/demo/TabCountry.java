package com.klst.gossip.demo;

import static org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker.StateValue;
import javax.swing.table.JTableHeader;

import org.compiere.model.I_C_Bank;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTableHeader;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import com.klst.gossip.CustomColumnFactory;
import com.klst.gossip.GenericDataLoader;
import com.klst.gossip.GenericTableModel;
import com.klst.gossip.Stacker;

/*
- TabCountry : implementiert den Tab Country für Winddow "Country Region and City" als JXTable  
	(AD_Window - AD_Window_ID=122)
	(AD_Tab - AD_Tab_ID=135) 
	nutzt C_Country (AD_Table - AD_Table_ID=170)

 */
public class TabCountry {
	// vll extends GenericTab

	private static final Logger LOG = Logger.getLogger(TabCountry.class.getName());

	private static final int AD_Tab_ID=135;
	
	private GenericTableModel tableModel;
	private JXTable countryTable;
	
	public void showIn(Container jPanel) {
		LOG.config(">>>");
		
		JTextArea textArea = new JTextArea();
		textArea.setRows(10);
		JProgressBar progressBar = new JProgressBar(0, 100);
		progressBar.setForeground(Color.GREEN);
		progressBar.setStringPainted(true);
		jPanel.setLayout(new BorderLayout());
//		jPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);
		jPanel.add(progressBar, BorderLayout.SOUTH);
		
        //<snip> JXTable data properties
		// private OscarTableModel oscarModel; ==> private BankTableModel bankTableModel
		// private JXTable oscarTable;         ==> private JXTable bankTable 
		tableModel = new GenericTableModel(AD_Tab_ID);
//        oscarModel = new OscarTableModel();
        // set the table model after setting the factory
//        oscarTable.setModel(oscarModel);
        countryTable = createXTable(); // statt new JXTable();
        countryTable.setName("countryTable");
        JScrollPane scrollpane = new JScrollPane(countryTable);
//        dataPanel = new Stacker(scrollpane);
//        add(dataPanel, BorderLayout.CENTER);
        Stacker stacker = new Stacker(scrollpane);
        jPanel.add(stacker, BorderLayout.CENTER);
        
        //<snip> JXTable display properties
        // show column control
        countryTable.setColumnControlVisible(true);
        // replace grid lines with striping 
        countryTable.setShowGrid(false, false);
        countryTable.addHighlighter(HighlighterFactory.createSimpleStriping());
        // initialize preferred size for table's viewable area
        countryTable.setVisibleRowCount(10);
//        </snip>

        //<snip> JXTable column properties
        // create and configure a custom column factory
        CustomColumnFactory factory = new CustomColumnFactory();
//        OscarRendering.configureColumnFactory(factory, getClass()); // TODO
        // set the factory before setting the table model
//        bankTable.setColumnFactory(factory);
//        </snip>

        countryTable.setModel(tableModel);
        
        // filterController = new OscarFiltering(oscarTable);
        //<snip> JXTable column properties
        // some display properties can be configured only after the model has been set, here:
        // configure the view sequence of columns to be different from the model
        countryTable.getColumnExt(0).setVisible(false); // A ausblenden
        countryTable.setColumnSequence(new Object[] {I_C_Bank.COLUMNNAME_C_Bank_ID, I_C_Bank.COLUMNNAME_Name}); // A wird evtl hinten angehängt
        // </snip>

 		GenericDataLoader task = new GenericDataLoader(tableModel);
        
        // bind the worker's progress notification to the progressBar
        // and the worker's state notification to this
		// ersetzt das den PropertyChangeListener!
		JLabel status = new JLabel();
        BindingGroup group = new BindingGroup();
        group.addBinding(Bindings.createAutoBinding(READ, task, 
        		BeanProperty.create("progress"),
                progressBar, BeanProperty.create("value")));
        group.addBinding(Bindings.createAutoBinding(READ, task, 
        		BeanProperty.create("state"),
        		this, BeanProperty.create("loadState"))); // call setLoadState 
        group.bind();

		task.execute();
		LOG.config("<<<");
	}

	public void setLoadState(StateValue state) {
		LOG.config("StateValue:"+state);
	}

	// aus org.jdesktop.swingx.demos.table.XTableDemo
	// TODO in Oberklasse
	private JXTable createXTable() {
		JXTable table = new JXTable() {

			@Override
			protected JTableHeader createDefaultTableHeader() {
				return new JXTableHeader(columnModel) {

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

}
