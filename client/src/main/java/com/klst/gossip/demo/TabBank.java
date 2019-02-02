package com.klst.gossip.demo;

import static org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
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

/*
- TabBank : implementiert den Tab Bank für Window "Bank" als JXTable  
	(AD_Window - AD_Window_ID=158)
	(AD_Tab - AD_Tab_ID=227)
	nutzt C_Bank  (AD_Table - AD_Table_ID=296)

 */
public class TabBank implements WindowListener {
	// vll extends GenericTab

	private static final Logger LOG = Logger.getLogger(WindowCountry.class.getName());

	private static final int AD_Tab_ID=227;
	private static final String NAME="Bank";
	
	private GenericTableModel tableModel;
	private JXTable bankTable;
	
	// TODO:
	// - LaF propagieren
	// - JFrame.DISPOSE_ON_CLOSE, aber frame ist noch in frames!
	public void showIn(Container rootContainer) {
		LOG.config(">>> Component#="+rootContainer.getComponentCount() + ", Name:"+rootContainer.getName());
		if(NAME.equals(rootContainer.getName())) {
			rootContainer.removeAll();
		}
		
		JFrame frame = (JFrame) SwingUtilities.windowForComponent(rootContainer);
		frame.addWindowListener(this);
		
//		if(rootContainer instanceof JRootPane) {
//			JRootPane rootPane = (JRootPane)rootContainer;
//			rootPane.getParent()
//		}
		rootContainer.setName(NAME);
		JProgressBar progressBar = new JProgressBar(0, 100);
		progressBar.setForeground(Color.GREEN);
		progressBar.setStringPainted(true);
		rootContainer.setLayout(new BorderLayout());
		rootContainer.add(progressBar, BorderLayout.SOUTH);
		
		tableModel = new GenericTableModel(AD_Tab_ID);
        bankTable = createXTable(); // statt new JXTable();
        bankTable.setName("bankTable");
        JScrollPane scrollpane = new JScrollPane(bankTable);
        Stacker stacker = new Stacker(scrollpane);
        rootContainer.add(stacker, BorderLayout.CENTER);
        
        // show column control
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

        bankTable.setModel(tableModel);
        
        // filterController = new OscarFiltering(oscarTable);
        // some display properties can be configured only after the model has been set, here:
        // configure the view sequence of columns to be different from the model
//        bankTable.getColumnExt(16).setVisible(false); // rückwärts!
//        bankTable.getColumnExt(15).setVisible(false);
//        bankTable.getColumnExt(8).setVisible(false);
//        bankTable.getColumnExt(7).setVisible(false);
//        bankTable.getColumnExt(6).setVisible(false);
//        bankTable.getColumnExt(1).setVisible(false); // B ausblenden
//        bankTable.getColumnExt(0).setVisible(false); // A ausblenden
//        bankTable.setColumnSequence(new Object[] {bankTable.getColumnName(1), "Bank Name"}); // A wird evtl hinten angehängt
       
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

	// wg. implements WindowListener
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		LOG.config("TODO");
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		LOG.config("TODO");
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
