package com.klst.gossip.demo;

import static org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker.StateValue;
import javax.swing.table.JTableHeader;

import org.compiere.model.MTab;
import org.compiere.util.Ini;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTableHeader;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import com.klst.gossip.CustomColumnFactory;
import com.klst.gossip.GenericDataLoader;
import com.klst.gossip.GenericTableModel;
import com.klst.gossip.Gossip;
import com.klst.gossip.Stacker;
import com.klst.gossip.Window;

import gov.nasa.arc.mct.gui.impl.HidableTabbedPane;

/*
- WindowCountry : implementiert Window "Country Region and City" als JXTable und dort den ersten Tab 
	(AD_Window - AD_Window_ID=122)
	(AD_Tab - AD_Tab_ID=135) 
	nutzt C_Country (AD_Table - AD_Table_ID=170)

 */
public class WindowCountry extends Window {

	private static final Logger LOG = Logger.getLogger(WindowCountry.class.getName());

	private static final int AD_Window_ID=122;
	private static final int AD_Tab_ID=135;
	
	private GenericTableModel tableModel;
	private JXTable countryTable;
	
	// ctor
	public WindowCountry(Gossip rootFrame) {
		super(rootFrame, AD_Window_ID);
	}
	
	public void showIn(Container rootContainer) {
		LOG.config(">>> Component#="+rootContainer.getComponentCount() + ", Name:"+rootContainer.getName());
//		if(NAME.equals(rootContainer.getName())) {
//			rootContainer.removeAll();
//		}
		
		setWindowListenerFor(rootContainer); // wg. - JFrame.DISPOSE_ON_CLOSE, aber frame ist noch in frames!
		LOG.config("TODO setTitle(title) to Name:"+this.mWindow.getName());
//		currentFrame.setTitle(title);
//		currentFrame.setIconImages(icons); // TODO

		JProgressBar progressBar = new JProgressBar(0, 100);
		progressBar.setForeground(Color.GREEN);
		progressBar.setStringPainted(true);
		rootContainer.setLayout(new BorderLayout());
		rootContainer.add(progressBar, BorderLayout.SOUTH);
		
		tableModel = new GenericTableModel(AD_Tab_ID);
        countryTable = createXTable(); // statt new JXTable();
        JScrollPane scrollpane = new JScrollPane(countryTable);
        Stacker stacker = new Stacker(scrollpane);
        
        List<MTab> tabs = getTabs(false);
        tabs.forEach(tab -> {
        	LOG.config("Tab Name:"+tab.getName() + " SeqNo:"+tab.getSeqNo() + " TabLevel:"+tab.getTabLevel());
        });
		this.tabPane = new HidableTabbedPane(tabs.get(0).getName(), stacker); // den Namen in hiddenTabPane, Stacker TODO
        rootContainer.add(this.tabPane, BorderLayout.CENTER);
        countryTable.setName(tabs.get(0).getName()); 
        // zur Demo, die Tabs anzeigen
        Iterator<MTab> itr = tabs.iterator();
        itr.next(); // den ersten überspringen TODO this/TabLevel 0 muß dorthin
        boolean p_show_trl = Ini.isPropertyBool(this.P_SHOW_TRL);
        while (itr.hasNext()) {
        	MTab tab = itr.next(); 
        	if(!tab.isTranslationTab() || p_show_trl) {
            	// TODO Aktion wenn tabPanel (Region) ausgewählt wird / Tab Name:Region SeqNo:30 TabLevel:1 
        		if(tab.getName().equals("Region")) {
        			TabRegion tabPanel = new TabRegion(); // extends Tab (generisch) extends JPanel
        			this.tabPane.addTab(tab.getName(), tabPanel);
       		} else {
                	JPanel tabPanel = new JPanel();
                	this.tabPane.addTab(tab.getName(), tabPanel);
        		}
        	} else { // do not show translation tabs
        		LOG.config(tab.toString()+" not shown "+tab.getName());
        	}
        }

       
        countryTable.setColumnControlVisible(true);
        // replace grid lines with striping 
        countryTable.setShowGrid(false, false);
        countryTable.addHighlighter(HighlighterFactory.createSimpleStriping());
        // initialize preferred size for table's viewable area
        countryTable.setVisibleRowCount(10);

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
//        countryTable.getColumnExt(0).setVisible(false); // A ausblenden
//        countryTable.setColumnSequence(new Object[] {I_C_Bank.COLUMNNAME_C_Bank_ID, I_C_Bank.COLUMNNAME_Name}); // A wird evtl hinten angehängt
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
