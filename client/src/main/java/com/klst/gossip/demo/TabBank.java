package com.klst.gossip.demo;

import static org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker.StateValue;
import javax.swing.table.JTableHeader;

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
import com.klst.gossip.Tab;

/*
- TabBank : implementiert den Tab Bank für Window "Bank" als JXTable  
	(AD_Window - AD_Window_ID=158)
	(AD_Tab - AD_Tab_ID=227)
	nutzt C_Bank  (AD_Table - AD_Table_ID=296)

 */
public class TabBank extends Tab {

	private static final long serialVersionUID = -274665901539021577L;

	private static final Logger LOG = Logger.getLogger(WindowCountry.class.getName());

	private static final int AD_Tab_ID=227;
	private static final String NAME="Bank";
	
	private GenericTableModel tableModel;
	private JXTable bankTable;
	
	public Stacker stacker;
	
	// ctor
	public TabBank() {
		super(AD_Tab_ID);
		// Test
//		this.add(new JLabel("BorderLayout.SOUTH == PAGE_END"), BorderLayout.PAGE_END);
//		this.add(new JLabel("BorderLayout.WEST == LINE_START"), BorderLayout.LINE_START);
	}
	
	public GenericDataLoader showIn(Container rootContainer, int windowNo) {
		LOG.config(">>> Component#="+rootContainer.getComponentCount() + ", Name:"+rootContainer.getName());
//		if(NAME.equals(rootContainer.getName())) {
//			rootContainer.removeAll();
//		}
		
//		setWindowListenerFor(rootContainer); // wg. - JFrame.DISPOSE_ON_CLOSE, aber frame ist noch in frames! TODO muss in Window
		
//		LOG.config("TODO setTitle(title) to Name:"+this.mWindow.getName());
//		currentFrame.setTitle(title);
//		currentFrame.setIconImages(icons); // TODO

//		JProgressBar progressBar = new JProgressBar(0, 100); // TODO in Window
//		progressBar.setForeground(Color.GREEN);
//		progressBar.setStringPainted(true);
//		rootContainer.setLayout(new BorderLayout());
//		rootContainer.add(progressBar, BorderLayout.SOUTH);
		
// TODO		// benötige int WindowNo, int TabNo, int AD_Tab_ID
		// AD_Window_ID=158) ==?== WindowNo
		tableModel = new GenericTableModel(AD_Tab_ID, windowNo);
        bankTable = createXTable(); // statt new JXTable() in Oberklasse
        JScrollPane scrollpane = new JScrollPane(bankTable);
        stacker = new Stacker(scrollpane);
        
//        List<MTab> tabs = getTabs(false);
//        tabs.forEach(tab -> {
//        	LOG.config("Tab Name:"+tab.getName() + " SeqNo:"+tab.getSeqNo() + " TabLevel:"+tab.getTabLevel());
//        });
//        LOG.config("Tab Name:"+mTab.getName() + " SeqNo:"+mTab.getSeqNo() + " TabLevel:"+mTab.getTabLevel()); // TODO kann das weg?
        
        
        // TODO:
//		this.hidableTabbedPane = new HidableTabbedPane(tabs.get(0).getName(), stacker); // den Namen in hiddenTabPane, Stacker TODO
//        rootContainer.add(this.hidableTabbedPane, BorderLayout.CENTER);
        this.add(stacker, BorderLayout.CENTER);
        
//        bankTable.setName(mTab.getName()); // TODO kann das weg?
        
        // zur Demo, die Tabs anzeigen
//        Iterator<MTab> itr = tabs.iterator();
//        itr.next(); // den ersten überspringen TODO this/TabLevel 0 muß dorthin
//        boolean p_show_trl = Ini.isPropertyBool(this.P_SHOW_TRL);
//        while (itr.hasNext()) {
//        	MTab tab = itr.next(); 
//        	if(!tab.isTranslationTab() || p_show_trl) {
//            	// TODO Aktion wenn tabPanel (Region) ausgewählt wird / Tab Name:Region SeqNo:30 TabLevel:1 
//        		if(tab.getName().equals("Region")) {
//        			TabRegion tabPanel = new TabRegion(); // extends Tab (generisch) extends JPanel
//                	hidableTabbedPane.addTab(tab.getName(), tabPanel);
//       		} else {
//                	JPanel tabPanel = new JPanel();
//                	hidableTabbedPane.addTab(tab.getName(), tabPanel);
//        		}
//        	} else { // do not show translation tabs
//        		LOG.config(tab.toString()+" not shown "+tab.getName());
//        	}
//        }

       
        bankTable.setColumnControlVisible(true);
        // replace grid lines with striping 
        bankTable.setShowGrid(false, false);
        bankTable.addHighlighter(HighlighterFactory.createSimpleStriping());
        // initialize preferred size for table's viewable area
        bankTable.setVisibleRowCount(10);

        CustomColumnFactory factory = new CustomColumnFactory();
//        OscarRendering.configureColumnFactory(factory, getClass()); // TODO
        // set the factory before setting the table model
//        bankTable.setColumnFactory(factory);
//        </snip>

        bankTable.setModel(tableModel);
        
        // filterController = new OscarFiltering(oscarTable);
        //<snip> JXTable column properties
        // some display properties can be configured only after the model has been set, here:
        // configure the view sequence of columns to be different from the model
//        bankTable.getColumnExt(0).setVisible(false); // A ausblenden
//        bankTable.setColumnSequence(new Object[] {I_C_Bank.COLUMNNAME_C_Bank_ID, I_C_Bank.COLUMNNAME_Name}); // A wird evtl hinten angehängt
        // </snip>

 		GenericDataLoader task = new GenericDataLoader(tableModel);
        
        // bind the worker's progress notification to the progressBar
        // and the worker's state notification to this
		// ersetzt das den PropertyChangeListener!
		JLabel status = new JLabel();
        BindingGroup group = new BindingGroup();
        group.addBinding(Bindings.createAutoBinding(READ, task, 
        		BeanProperty.create("progress"),
                this.progressBar, BeanProperty.create("value")));
        group.addBinding(Bindings.createAutoBinding(READ, task, 
        		BeanProperty.create("state"),
        		this, BeanProperty.create("loadState"))); // call setLoadState 
        group.bind();

//		task.execute();
		LOG.config("<<<");
        return task;
	}

}
