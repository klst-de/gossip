package org.jdesktop.swingx.demos;

//import static org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.RowFilter.Entry;
import javax.swing.SwingWorker.StateValue;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

//import org.jdesktop.application.Application;
//import org.jdesktop.application.ResourceMap.PropertyInjectionException;
import org.jdesktop.beans.AbstractBean;
//import org.jdesktop.beansbinding.BeanProperty;
//import org.jdesktop.beansbinding.BindingGroup;
//import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXStatusBar;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTableHeader;
import org.jdesktop.swingx.decorator.AbstractHighlighter;
import org.jdesktop.swingx.decorator.ComponentAdapter;
import org.jdesktop.swingx.decorator.CompoundHighlighter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.decorator.IconHighlighter;
import org.jdesktop.swingx.decorator.HighlightPredicate.NotHighlightPredicate;
//import org.jdesktop.swingx.demos.table.CustomColumnFactory;
//import org.jdesktop.swingx.demos.table.IMDBLink;
//import org.jdesktop.swingx.demos.table.OscarCandidate;
//import org.jdesktop.swingxset.util.DemoUtils;
//import org.jdesktop.swingx.demos.table.OscarCandidate;
//import org.jdesktop.swingx.demos.table.OscarRendering;
//import org.jdesktop.swingx.demos.table.OscarTableModel;
//import org.jdesktop.swingx.demos.table.XTableDemo;
//import org.jdesktop.swingx.demos.table.OscarRendering.ListStringValue;
//import org.jdesktop.swingx.demos.table.OscarRendering.OscarCandidateLinkAction;
//import org.jdesktop.swingx.demos.table.OscarRendering.ToolTipHighlighter;
import org.jdesktop.swingx.hyperlink.AbstractHyperlinkAction;
import org.jdesktop.swingx.hyperlink.HyperlinkAction;
import org.jdesktop.swingx.renderer.HyperlinkProvider;
import org.jdesktop.swingx.renderer.LabelProvider;
import org.jdesktop.swingx.renderer.StringValue;
import org.jdesktop.swingx.renderer.StringValues;
import org.jdesktop.swingx.util.GraphicsUtilities;
//import org.jdesktop.swingxset.util.DemoUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

//import com.sun.swingset3.DemoProperties;
//import com.sun.swingset3.demos.Stacker;

/**
 * A demo for the {@code JXTable}. This demo displays the same functionality as
 * {@link com.sun.swingset3.demos.table.TableDemo}, using SwingX components and methodologies.
 * <p>
 * It is not possible to extend {@code TableDemo}, since the display components are private. This
 * class replicates contents and behavior in that class and may fall out of sync.
 * 
 * @author Karl George Schaefer
 * @author Jeanette Winzenberg (Devoxx '08 version)
 * @author aim (original TableDemo)
 */
//@DemoProperties(
//        value = "JXTable Demo",
//        category = "Data",
//        description = "Demonstrates JXTable, an enhanced data grid display.",
//        sourceFiles = {
//                "org/jdesktop/swingx/demos/table/XTableDemo.java",
//                "org/jdesktop/swingx/demos/table/OscarRendering.java",
//            "org/jdesktop/swingx/demos/table/CustomColumnFactory.java",
//            "org/jdesktop/swingx/demos/table/OscarFiltering.java",
//            "org/jdesktop/swingx/demos/table/resources/XTableDemo.properties"
//        }
//    )
//    
public class XTableDemo extends JPanel {
	
	private static final long serialVersionUID = 8564262411184935480L;
	static final Logger LOG = Logger.getLogger(XTableDemo.class.getName());

    private OscarTableModel oscarModel;

    private JPanel controlPanel;
    private Stacker dataPanel;
    private JXTable oscarTable;
    private JCheckBox winnersCheckbox;
    private JTextField filterField;
    private JComponent statusBarLeft;
    private JLabel actionStatus;
    private JLabel tableStatus;
    private JLabel tableRows;

    private JProgressBar progressBar;

    private OscarFiltering filterController;

    public XTableDemo() {
        initComponents();
        configureDisplayProperties();
        
        injectResources(this);
        bind();
    }

    /** aus DemoUtils :
     * Injects the resources into the given component instance from
     * the ResourceMap of the component's class.
     */
    public static void injectResources(Component comp) {
    	LOG.warning("ohne injectResources(Component comp:"+comp+")");
//        Application.getInstance().getContext().getResourceMap(comp.getClass()).injectComponents(comp);
        /**
         * Applies {@link #injectComponent} to each Component in the
         * hierarchy with root <tt>root</tt>.
         * 
         * @param root the root of the component hierarchy
         * @throws PropertyInjectionException if a property specified by a resource can't be set
         * @throws IllegalArgumentException if target is null
         * @see #injectComponent
         */
//        public void injectComponents(Component root) {
//            injectComponent(root);
//            if (root instanceof JMenu) {
//                /* Warning: we're bypassing the popupMenu here because
//                 * JMenu#getPopupMenu creates it; doesn't seem right
//                 * to do so at injection time.  Unfortunately, this
//                 * means that attempts to inject the popup menu's
//                 * "label" property will fail.
//                 */
//                JMenu menu = (JMenu) root;
//                for (Component child : menu.getMenuComponents()) {
//                    injectComponents(child);
//                }
//            } else if (root instanceof Container) {
//                Container container = (Container) root;
//                for (Component child : container.getComponents()) {
//                    injectComponents(child);
//                }
//            }
//        }
    }

    /** aus DemoUtils:
     * Injects the resources into the given component instance from
     * the ResourceMap of the parent's class.
     */
    public static void injectResources(Object parent, Component child) {
    	LOG.warning("ohne injectResources(Object parent:"+parent+", Component child:"+child+")");
//        Application.getInstance().getContext().getResourceMap(parent.getClass()).injectComponents(child);
        // aus org.jdesktop.application.injectComponents(Component root) :
        /**
         * Applies {@link #injectComponent} to each Component in the
         * hierarchy with root <tt>root</tt>.
         * 
         * @param root the root of the component hierarchy
         * @throws PropertyInjectionException if a property specified by a resource can't be set
         * @throws IllegalArgumentException if target is null
         * @see #injectComponent
         */

    }


    /**
     * Customizes display properties of contained components.
     * This is data-unrelated.
     */
    private void configureDisplayProperties() {
        //<snip> JXTable display properties
        // show column control
        oscarTable.setColumnControlVisible(true);
        // replace grid lines with striping 
        oscarTable.setShowGrid(false, false);
        oscarTable.addHighlighter(HighlighterFactory.createSimpleStriping());
        // initialize preferred size for table's viewable area
        oscarTable.setVisibleRowCount(10);
//        </snip>
        
        //<snip> JXTable column properties
        // create and configure a custom column factory
        CustomColumnFactory factory = new CustomColumnFactory();
        OscarRendering_configureColumnFactory(factory, getClass());
        // set the factory before setting the table model
        oscarTable.setColumnFactory(factory);
//        </snip>

//        DemoUtils.setSnippet("JXTable display properties", oscarTable);
//        DemoUtils.setSnippet("JXTable column properties", oscarTable.getTableHeader());
//        DemoUtils.setSnippet("Filter control", filterField, winnersCheckbox, tableStatus, tableRows);
//        DemoUtils.setSnippet("Use SwingWorker to asynchronously load the data", statusBarLeft,
//                (JComponent) statusBarLeft.getParent());
    }

    /**
     * Binds components to data and user interaction.
     */
    protected void bind() {
        
        //<snip> JXTable data properties
        oscarModel = new OscarTableModel();
        // set the table model after setting the factory
        oscarTable.setModel(oscarModel);
//        </snip>
        
        // <snip> Filter control
        // create the controller
//        filterController = new OscarFiltering(oscarTable);
//        // bind controller properties to input components
//        BindingGroup filterGroup = new BindingGroup();
//        filterGroup.addBinding(Bindings.createAutoBinding(READ, 
//                winnersCheckbox, BeanProperty.create("selected"),
//                filterController, BeanProperty.create("showOnlyWinners")));
//        filterGroup.addBinding(Bindings.createAutoBinding(READ, 
//                filterField, BeanProperty.create("text"),
//                filterController, BeanProperty.create("filterString")));
//        // PENDING JW: crude hack to update the statusbar - fake property
//        // how-to do cleanly?
//        filterGroup.addBinding(Bindings.createAutoBinding(READ, 
//                filterController, BeanProperty.create("showOnlyWinners"),
//                this, BeanProperty.create("statusContent")));
//        filterGroup.addBinding(Bindings.createAutoBinding(READ, 
//                filterController, BeanProperty.create("filterString"),
//                this, BeanProperty.create("statusContent")));
//        filterGroup.bind();
//        </snip>
        oscarModel.addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                updateStatusBar();
            }
        });

        //<snip> JXTable column properties
        // some display properties can be configured only after the model has been set, here:
        // configure the view sequence of columns to be different from the model
        oscarTable.setColumnSequence(new Object[] {"yearColumn", "categoryColumn", "movieTitleColumn", "nomineesColumn"});
        // </snip>
    }

    /**
     * Binding artefact method: crude hack to update the status bar on state changes
     * from the controller. 
     */
    public void setStatusContent(Object dummy) {
        updateStatusBar();
    }

    /**
     * Updates status labels. Called during loading and on 
     * changes to the filter controller state.
     */
    protected void updateStatusBar() {
        tableStatus.setName(filterController.isFilteringByString() ? "searchCountLabel" : "rowCountLabel");
        injectResources(this, tableStatus);
        tableRows.setText("" + oscarTable.getRowCount());
    }
    
    /**
     * Callback method for demo loader. 
     */
    public void start() {
        if (oscarModel.getRowCount() != 0) return;
        //<snip>Use SwingWorker to asynchronously load the data
        // create SwingWorker which will load the data on a separate thread
        SwingWorker<?, ?> loader = new OscarDataLoader(
                XTableDemo.class.getResource("resources/oscars.xml"), oscarModel);
        
        // display progress bar while data loads
        progressBar = new JProgressBar();
        statusBarLeft.add(progressBar);
        // bind the worker's progress notification to the progressBar
        // and the worker's state notification to this
//        BindingGroup group = new BindingGroup();
//        group.addBinding(Bindings.createAutoBinding(READ, 
//                loader, BeanProperty.create("progress"),
//                progressBar, BeanProperty.create("value")));
//        group.addBinding(Bindings.createAutoBinding(READ, 
//                loader, BeanProperty.create("state"),
//                this, BeanProperty.create("loadState")));
//        group.bind();
        loader.execute();
//        </snip>
    }

    /**
     * Callback for worker's state notification: cleanup if done.
     * @param state
     */
    public void setLoadState(StateValue state) {
        //<snip>Use SwingWorker to asynchronously load the data
        // remove progressbar if done loading
        if (state != StateValue.DONE) return;
        statusBarLeft.remove(progressBar);
        statusBarLeft.remove(actionStatus);
        revalidate();
        repaint();
//        </snip>
    }
    
    //<snip>Use SwingWorker to asynchronously load the data
    // specialized on OscarCandidate
    private class OscarDataLoader extends SwingWorker<List<OscarCandidate>, OscarCandidate> {
        private final URL oscarData;
        private final OscarTableModel oscarModel;
        private final List<OscarCandidate> candidates = new ArrayList<OscarCandidate>();
//        </snip>
        private JLabel credits;
         
        private OscarDataLoader(URL oscarURL, OscarTableModel oscarTableModel) {
            this.oscarData = oscarURL;
            this.oscarModel = oscarTableModel;
        }

        //<snip>Use SwingWorker to asynchronously load the data
        // background task let a parser do its stuff and 
        // update a progress bar
        @Override
        public List<OscarCandidate> doInBackground() {
            OscarDataParser parser = new OscarDataParser() {
                @Override
                protected void addCandidate(OscarCandidate candidate) {
                    candidates.add(candidate);
                    if (candidates.size() % 3 == 0) {
                        try { // slow it down so we can see progress :-)
                            Thread.sleep(1);
                        } catch (Exception ex) {
                        }
                    }
                    publish(candidate);
                    setProgress(100 * candidates.size() / 8545);
                }
            };
            parser.parseDocument(oscarData);
            return candidates;
        }
//        </snip>

        @Override
        protected void process(List<OscarCandidate> moreCandidates) {
            if (credits == null) {
                showCredits();
            }
            oscarModel.add(moreCandidates);
        }

        // For older Java 6 on OS X
        @SuppressWarnings("unused")
        protected void process(OscarCandidate... moreCandidates) {
            for (OscarCandidate candidate : moreCandidates) {
                oscarModel.add(candidate);
            }
        }
        
        //<snip>Use SwingWorker to asynchronously load the data
        // show a transparent overlay on start loading
        private void showCredits() {
            credits = new JLabel(); 
            credits.setName("credits");
            credits.setFont(UIManager.getFont("Table.font").deriveFont(24f));
            credits.setHorizontalAlignment(JLabel.CENTER);
            credits.setBorder(new CompoundBorder(new TitledBorder(""),
                    new EmptyBorder(20,20,20,20)));

            dataPanel.showMessageLayer(credits, .75f);
            injectResources(XTableDemo.this, dataPanel);
        }
//        </snip>
        
        @Override
        //<snip>Use SwingWorker to asynchronously load the data
        // hide transparend overlay on end loading
        protected void done() {
            setProgress(100);
            dataPanel.hideMessageLayer();
        }
//        </snip>
    }
    
//------------------ init ui    
    //<snip> JXTable display properties
    // center column header text
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
//                    </snip>
                    
                };
            }
            
        };
        return table;
    }
   
    protected void initComponents() {
        setLayout(new BorderLayout());

        controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.NORTH);
        oscarTable = createXTable();
        oscarTable.setName("oscarTable");
        
        JScrollPane scrollpane = new JScrollPane(oscarTable);
        dataPanel = new Stacker(scrollpane);
        add(dataPanel, BorderLayout.CENTER);

        add(createStatusBar(), BorderLayout.SOUTH);
    }

    protected JPanel createControlPanel() {
        JPanel controlPanel = new JPanel();
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        controlPanel.setLayout(gridbag);

        c.gridx = 0;
        c.gridy = 1;
        c.gridheight = 1;
        c.insets = new Insets(20, 10, 0, 10);
        c.anchor = GridBagConstraints.SOUTHWEST;
        JLabel searchLabel = new JLabel();
        searchLabel.setName("searchLabel");
        controlPanel.add(searchLabel, c);

        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 1.0;
        c.insets.top = 0;
        c.insets.bottom = 12;
        c.anchor = GridBagConstraints.SOUTHWEST;
        //c.fill = GridBagConstraints.HORIZONTAL;
        filterField = new JTextField(24);
        controlPanel.add(filterField, c);

        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = GridBagConstraints.REMAINDER;
        //c.insets.right = 24;
        //c.insets.left = 12;
        c.weightx = 0.0;
        c.anchor = GridBagConstraints.EAST;
        c.fill = GridBagConstraints.NONE;
        winnersCheckbox = new JCheckBox();
        winnersCheckbox.setName("winnersLabel");
        controlPanel.add(winnersCheckbox, c);

        return controlPanel;
    }

    protected Container createStatusBar() {

        JXStatusBar statusBar = new JXStatusBar();
        statusBar.putClientProperty("auto-add-separator", Boolean.FALSE);
        // Left status area
        statusBar.add(Box.createRigidArea(new Dimension(10, 22)));
        statusBarLeft = Box.createHorizontalBox();
        statusBar.add(statusBarLeft, JXStatusBar.Constraint.ResizeBehavior.FILL);
        actionStatus = new JLabel();
        actionStatus.setName("loadingStatusLabel");
        actionStatus.setHorizontalAlignment(JLabel.LEADING);
        statusBarLeft.add(actionStatus);

        // Middle (should stretch)
//        statusBar.add(Box.createHorizontalGlue());
//        statusBar.add(Box.createHorizontalGlue());
        statusBar.add(Box.createVerticalGlue());
        statusBar.add(Box.createRigidArea(new Dimension(50, 0)));

        // Right status area
        tableStatus = new JLabel(); 
        tableStatus.setName("rowCountLabel");
        JComponent bar = Box.createHorizontalBox();
        bar.add(tableStatus);
        tableRows = new JLabel("0");
        bar.add(tableRows);
        
        statusBar.add(bar);
        statusBar.add(Box.createHorizontalStrut(12));
        return statusBar;
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
    	JXFrame frame = new JXFrame(XTableDemo.class.getName()); // original: new JFrame(XTreeDemo.class.getAnnotation(DemoProperties.class).value());
    	// JXFrame enhanced JFrame with Additional Features:
    	// Root pane:
    	// Idle: 
    	// Wait (busy) glass pane:
    	
        frame.setDefaultCloseOperation(JXFrame.EXIT_ON_CLOSE);
        JPanel panel = new XTableDemo();
        frame.getContentPane().add(panel);
 
        LOG.info("Display the window aka frame with panel");
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

//    public static void main(String args[]) {
//
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                JXFrame frame = new JXFrame("JXTable Demo", true);
//                XTableDemo demo = new XTableDemo();
//                frame.add(demo);
//                frame.setSize(700, 400);
//                frame.setVisible(true);
//                demo.start();
//            }
//        });
//    }
	public static void main(String[] args) {
		LOG.info("start");

		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
    
//-----do nothing methods (keep beansbinding happy)
    
    public Object getStatusContent() {
        return null;
    }
    
    public StateValue getLoadState() {
        return null;
    }
// inner --------------------------------------------------
    
    public abstract class OscarDataParser extends DefaultHandler {
        private /*static final*/ String[] CATEGORIES_IN = {
                "actor", "actress", "bestPicture",
                "actorSupporting", "actressSupporting", "artDirection",
                "assistantDirector", "director", "cinematography",
                "costumeDesign", "danceDirection", "docFeature",
                "docShort", "filmEditing", "foreignFilm",
                "makeup", "musicScore", "musicSong",
                "screenplayAdapted", "screenplayOriginal", "shortAnimation",
                "shortLiveAction", "sound", "soundEditing",
                "specialEffects", "visualEffects", "writing",
                "engEffects", "uniqueArtisticPicture"
        };

        private /*static final*/ String[] CATEGORIES_OUT = {
                "Best Actor", "Best Actress", "Best Picture",
                "Best Supporting Actor", "Best Supporting Actress", "Best Art Direction",
                "Best Assistant Director", "Best Director", "Best Cinematography",
                "Best Costume Design", "Best Dance Direction", "Best Feature Documentary",
                "Best Short Documentary", "Best Film Editing", "Best Foreign Film",
                "Best Makeup", "Best Musical Score", "Best Song",
                "Best Adapted Screenplay", "Best Original Screenplay", "Best Animation Short",
                "Best Live Action Short", "Best Sound", "Best Sound Editing",
                "Best Special Effects", "Best Visual Effects", "Best Engineering Effects",
                "Best Writing", "Most Unique Artistic Picture"
        };


        private String tempVal;

        //to maintain context
        private OscarCandidate tempOscarCandidate;
            
        private int count = 0;
        
        public int getCount() {
            return count;
        }

        public void parseDocument(URL oscarURL) {
            //get a factory
            SAXParserFactory spf = SAXParserFactory.newInstance();

            try {
                //get a new instance of parser
                SAXParser sp = spf.newSAXParser();

                //parse the file and also register this class for call backs
                InputStream is = new BufferedInputStream(oscarURL.openStream());
                sp.parse(is, this);
                is.close();

            } catch (SAXException se) {
                se.printStackTrace();
            } catch (ParserConfigurationException pce) {
                pce.printStackTrace();
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }

        //Event Handlers
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            //reset
            tempVal = "";
            for (int i = 0; i < CATEGORIES_IN.length; i++) {
                if (qName.equalsIgnoreCase(CATEGORIES_IN[i])) {
                    tempOscarCandidate = new OscarCandidate(CATEGORIES_OUT[i]);
                    tempOscarCandidate.setYear(Integer.parseInt(attributes.getValue("year")));
                    if (CATEGORIES_IN[i].equals("screenplayOriginal") &&
                         tempOscarCandidate.getYear() == 2007) {
                    }
                    return;
                }
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            tempVal = new String(ch, start, length);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (qName.equalsIgnoreCase("won")) {
                tempOscarCandidate.setWinner(true);
            } else if (qName.equalsIgnoreCase("lost")) {
                tempOscarCandidate.setWinner(false);
            } else if (qName.equalsIgnoreCase("movie")) {
                tempOscarCandidate.setMovieTitle(tempVal);
            } else if (qName.equalsIgnoreCase("person")) {
                tempOscarCandidate.getPersons().add(tempVal);
            } else {
                // find category
                for (String category : CATEGORIES_IN) {
                    if (qName.equalsIgnoreCase(category)) {
                        //add it to the list
                        count++;
                        addCandidate(tempOscarCandidate);
                        break;
                    }
                }
            }
        }

        @Override
        public void error(SAXParseException ex) throws SAXException {
            XTableDemo.LOG.log(Level.SEVERE, "error parsing oscar data ", ex);
        }

        @Override
        public void fatalError(SAXParseException ex) throws SAXException {
            XTableDemo.LOG.log(Level.SEVERE, "fatal error parsing oscar data ", ex);
        }

        @Override
        public void warning(SAXParseException ex) {
            XTableDemo.LOG.log(Level.WARNING, "warning occurred while parsing oscar data ", ex);
        }

        @Override
        public void endDocument() throws SAXException {
            XTableDemo.LOG.log(Level.FINER, "parsed to end of oscar data.");
        }

        protected abstract void addCandidate(OscarCandidate candidate);
    }


    public class OscarTableModel extends AbstractTableModel {
        public static final int CATEGORY_COLUMN = 0;
        public static final int YEAR_COLUMN = 1;
        public static final int WINNER_COLUMN = 2;
        public static final int MOVIE_COLUMN = 3;
        public static final int PERSONS_COLUMN = 4;
        public static final int COLUMN_COUNT = 5;

        private /*static final*/ String[] columnIds = {"categoryColumn", "yearColumn", "winnerColumn", 
            "movieTitleColumn", "nomineesColumn"};

        @Override
        public String getColumnName(int column) {
            return columnIds[column];
        }

        
        private final List<OscarCandidate> candidates = new ArrayList<OscarCandidate>();

        public void add(List<OscarCandidate> newCandidates) {
            int first = candidates.size();
            int last = first + newCandidates.size() - 1;
            candidates.addAll(newCandidates);
            fireTableRowsInserted(first, last);
        }

        public void add(OscarCandidate candidate) {
            int index = candidates.size();
            candidates.add(candidate);
            fireTableRowsInserted(index, index);
        }

        public int getRowCount() {
            return candidates.size();
        }

        public int getColumnCount() {
            return COLUMN_COUNT;
        }

        @Override
        public Class<?> getColumnClass(int column) {
            return getValueAt(0, column).getClass();
        }

        public OscarCandidate getCandidate(int row) {
            return candidates.get(row);
        }

        public Object getValueAt(int row, int column) {
            // PENDING JW: solve in getColumnClass instead of hacking here
            if (row >= getRowCount()) {
                return new Object();
            }
            switch (column) {
                case CATEGORY_COLUMN:
                    return getCandidate(row).getCategory();
                case YEAR_COLUMN:
                    return getCandidate(row).getYear();
                case MOVIE_COLUMN:
                    return getCandidate(row);
                case WINNER_COLUMN:
                    return getCandidate(row).isWinner() ? Boolean.TRUE : Boolean.FALSE;
                case PERSONS_COLUMN:
                    return getCandidate(row).getPersons();
            }
            return null;
        }

        
        
    }
    
    public static class OscarCandidate {

        private String category;
        private Integer year;
        private boolean winner = false;
        private String movie;
        private URI imdbURI;
        private final ArrayList<String> persons = new ArrayList<String>();

        /**
         * Creates a new instance of OscarCandidate
         */
        public OscarCandidate(String category) {
            this.category = category;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public Integer getYear() {
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }

        public boolean isWinner() {
            return winner;
        }

        public void setWinner(boolean winner) {
            this.winner = winner;
        }

        public String getMovieTitle() {
            return movie;
        }

        public void setMovieTitle(String movie) {
            this.movie = movie;
        }

        public URI getIMDBMovieURI() {
            return imdbURI;
        }

        public void setIMDBMovieURI(URI uri) {
            this.imdbURI = uri;
        }

        public List<String> getPersons() {
            return persons;
        }


    }

    public class OscarFiltering extends AbstractBean {
        private RowFilter<Object, Object> winnerFilter;
        private RowFilter<Object, Object> searchFilter;
       
        private String filterString;
        private boolean showOnlyWinners = false;
        private JXTable oscarTable;

        
        public OscarFiltering(JXTable oscarTable) {
            this.oscarTable = oscarTable;
        }

        public boolean isFilteringByString() {
            return !isEmpty(getFilterString());
        }
        
        private boolean isEmpty(String filterString) {
            return filterString == null || filterString.length() == 0;
        }

        /**
         * @param filterString the filterString to set
         */
        public void setFilterString(String filterString) {
            String oldValue = getFilterString();
            // <snip> Filter control
            // set the filter string (bound to the input in the textfield)
            // and update the search RowFilter
            this.filterString = filterString;
            updateSearchFilter();
//            </snip>
            firePropertyChange("filterString", oldValue, getFilterString());
        }

        /**
         * @return the filterString
         */
        public String getFilterString() {
            return filterString;
        }

        /**
         * @param showOnlyWinners the showOnlyWinners to set
         */
        public void setShowOnlyWinners(boolean showOnlyWinners) {
            if (isShowOnlyWinners() == showOnlyWinners) return;
            boolean oldValue = isShowOnlyWinners();
            this.showOnlyWinners = showOnlyWinners;
            updateWinnerFilter();
            firePropertyChange("showOnlyWinners", oldValue, isShowOnlyWinners());
        }

        /**
         * @return the showOnlyWinners
         */
        public boolean isShowOnlyWinners() {
            return showOnlyWinners;
        }


        private void updateWinnerFilter() {
            winnerFilter = showOnlyWinners ? createWinnerFilter() : null;
            updateFilters();
        }

        private void updateSearchFilter() {
            if ((filterString != null) && (filterString.length() > 0)) {
                searchFilter = createSearchFilter(filterString + ".*");
            } else {
                searchFilter = null;
            }
            updateFilters();
        }

        
        private void updateFilters() {
            // <snip> Filter control
            // set the filters to the table 
            if ((searchFilter != null) && (winnerFilter != null)) {
                List<RowFilter<Object, Object>> filters =
                    new ArrayList<RowFilter<Object, Object>>(2);
                filters.add(winnerFilter);
                filters.add(searchFilter);
                RowFilter<Object, Object> comboFilter = RowFilter.andFilter(filters);
                oscarTable.setRowFilter(comboFilter);
            } else if (searchFilter != null) {
                oscarTable.setRowFilter(searchFilter);
            } else {
                oscarTable.setRowFilter(winnerFilter);
            }
//            </snip>
        }


        private RowFilter<Object, Object> createWinnerFilter() {
            return new RowFilter<Object, Object>() {
                @Override
                public boolean include(Entry<? extends Object, ? extends Object> entry) {
                    OscarTableModel oscarModel = (OscarTableModel) entry.getModel();
                    OscarCandidate candidate = oscarModel.getCandidate(((Integer) entry.getIdentifier()).intValue());
                    if (candidate.isWinner()) {
                        // Returning true indicates this row should be shown.
                        return true;
                    }
                    // loser
                    return false;
                }
            };
        }
        
        // <snip> Filter control
        // create and return a custom RowFilter specialized on OscarCandidate
        private RowFilter<Object, Object> createSearchFilter(final String filterString) {
            return new RowFilter<Object, Object>() {
                @Override
                public boolean include(Entry<? extends Object, ? extends Object> entry) {
                    OscarTableModel oscarModel = (OscarTableModel) entry.getModel();
                    OscarCandidate candidate = oscarModel.getCandidate(((Integer) entry.getIdentifier()).intValue());
                    boolean matches = false;
                    Pattern p = Pattern.compile(filterString + ".*", Pattern.CASE_INSENSITIVE);
                    // match against movie title
                    String movie = candidate.getMovieTitle();
                    if (movie != null) {
                        if (movie.startsWith("The ")) {
                            movie = movie.replace("The ", "");
                        } else if (movie.startsWith("A ")) {
                            movie = movie.replace("A ", "");
                        }
                        // Returning true indicates this row should be shown.
                        matches = p.matcher(movie).matches();
                    }
                    List<String> persons = candidate.getPersons();
                    for (String person : persons) {
                        // match against persons as well
                        if (p.matcher(person).matches()) {
                            matches = true;
                        }
                    }
                    return matches;
//                    </snip>
                }
            };
        }

    }

    static OscarCandidate prototype = new OscarCandidate("Special Effects and");
//    public class OscarRendering {
//        @SuppressWarnings("unused")
//        private static final Logger LOG = Logger.getLogger(OscarRendering.class.getName());
        
        //<snip> JXTable column properties
        // Note: the custom column factory is a feature enhanced factory
        // which allows column configuration based on column identifier
        public static void OscarRendering_configureColumnFactory(CustomColumnFactory factory, Class<?> resourceBase) {
            // set location to load resources from
            factory.setBaseClass(resourceBase);
            // mark the isWinner column as hidden
            factory.addHiddenNames("winnerColumn");

            // register a custom comparator
            Comparator<OscarCandidate> comparator = new Comparator<OscarCandidate>() {

                public int compare(OscarCandidate o1, OscarCandidate o2) {
                    String movie1 = o1.getMovieTitle();
                    String movie2 = o2.getMovieTitle();
                    if (movie1 == null) return -1;
                    if (movie2 == null) return 1;
                    return movie1.compareTo(movie2);
                }
                
            };
            factory.addComparator("movieTitleColumn", comparator);

            // add hints for column sizing
//            OscarCandidate prototype = new OscarCandidate("Special Effects and");
            prototype.getPersons().add("some unusually name or");
            prototype.setYear(20000);
            prototype.setMovieTitle("And here we go again ... should ");
            factory.addPrototypeValue("yearColumn", prototype.getYear());
            factory.addPrototypeValue("categoryColumn", prototype.getCategory());
            factory.addPrototypeValue("movieTitleColumn", prototype);
            factory.addPrototypeValue("nomineesColumn", prototype.getPersons());
            
            // register component providers per column identifier
            factory.addComponentProvider("yearColumn", new LabelProvider(JLabel.CENTER));
            factory.addComponentProvider("nomineesColumn", new LabelProvider(new ListStringValue()));
            factory.addComponentProvider("movieTitleColumn", 
                    new HyperlinkProvider(new OscarCandidateLinkAction(), OscarCandidate.class));
            
            // Visual Decorators
            // .... and more 
//            </snip>
            
            // <snip> Highlighter and Renderer
            // ToolTip for movie column
            StringValue toolTip = new StringValue() {
                
                public String getString(Object value) {
                    if (value instanceof OscarCandidate) {
                        return getURIText((OscarCandidate) value);
                    }
                    return "";
                } 
                private String getURIText(OscarCandidate target) {
                    URI uri = target.getIMDBMovieURI();
                    if (uri == null) {
                        return "http://www.imdb.com/" + "\"" + target.getMovieTitle() + "\"";
                    }
                    return target.getIMDBMovieURI().toString();
                }
                
            };
            
            ToolTipHighlighter movieToolTip = new ToolTipHighlighter();
            movieToolTip.addStringValue(toolTip, "movieTitleColumn");
            factory.addHighlighter("movieTitleColumn", movieToolTip);
            
            // ToolTips for nominees column
            Icon winnerIcon = getResourceIcon(resourceBase, "winnerIcon"); // resourceBase == XTableDemo
            Icon nomineeIcon = getResourceIcon(resourceBase, "nomineeIcon"); 
            
            // Icon and tool tip decorator for winners
            IconHighlighter winner = new IconHighlighter(winnerIcon);
            ToolTipHighlighter winnerToolTip = new ToolTipHighlighter();
            winnerToolTip.addStringValue(new ListStringValue(true, "Winner!", "Winners: "), "nomineesColumn");
            // Icon and tool tip decorators for nominees
            IconHighlighter nominee = new IconHighlighter(nomineeIcon);
            ToolTipHighlighter nomineeToolTip = new ToolTipHighlighter();
            nomineeToolTip.addStringValue(new ListStringValue(true, "Nominee", "Nominees: "), "nomineesColumn");
            // the predicate to decide which to use
            HighlightPredicate winnerPredicate = new HighlightPredicate() {
                
                public boolean isHighlighted(Component renderer,
                        ComponentAdapter adapter) {
                    int modelColumn = adapter.getColumnIndex("winnerColumn");
                    return ((Boolean) adapter.getValue(modelColumn)).booleanValue();
                }
                
            };
            // compound per-predicate and add as column highlighter to the factory
            factory.addHighlighter("nomineesColumn", new CompoundHighlighter(
                    new CompoundHighlighter(winnerPredicate, winner, winnerToolTip), 
                    new CompoundHighlighter(new NotHighlightPredicate(winnerPredicate), 
                            nominee, nomineeToolTip)));
//            </snip>

        }
//    }
    
    /** aus DemoUtils:
     * Returns an Icon stored with the given key using the resourceMap of 
     * the baseClass, or null if none is found.
     * 
     */
    public static Icon getResourceIcon(Class<?> baseClass, String key) {
        URL url = baseClass.getResource("resources/images/" + key );
        LOG.config("baseClass:"+baseClass + " key:"+key + " url="+url);
        if (url == null) return null;
        try {
            BufferedImage image = ImageIO.read(url);
            if (image.getHeight() > 30) {
                image = GraphicsUtilities.createThumbnail(image, 16);
            }
            return new ImageIcon(image);
        } catch (IOException e) {
        }
        return null;
//      return Application.getInstance().getContext().getResourceMap(baseClass).getIcon(key);
    }

    //<snip> Highlighter and Renderer
    // a custom link action to drive the hyperlink on the movie column
    /**
     * HyperlinkAction to open the info page related to the OscarCandiate.
     * <p>
     * 
     * The URI is created lazily before browing:
     * <ul>
     * <li>initially, the OscarCandidate only has properties movieTitle and year
     * (of nomination)
     * <li>first time around, this action's performed the uri of the info page
     * is searched online (done in IMBDLink) and set as property to the
     * OscarCandidate
     * <li>if successful, the uri of the info page is set as target to the
     * wrapped HyperlinkAction and its performed is messaged to browse its
     * target
     * </ul>
     */
    public static class OscarCandidateLinkAction extends
            AbstractHyperlinkAction<OscarCandidate> {

        HyperlinkAction browse = HyperlinkAction.createHyperlinkAction(null,
                java.awt.Desktop.Action.BROWSE);

        @Override
        protected void installTarget() {
            setName(target == null ? null : target.getMovieTitle());
            setVisited(target != null ? target.getIMDBMovieURI() != null
                    : false);
        }

        public void actionPerformed(ActionEvent e) {
            if (target == null)
                return;
            try {
                URI imdbURI = target.getIMDBMovieURI();
                if (imdbURI == null) {
                    imdbURI = lookupURI(imdbURI);
                }
                if (imdbURI != null) {
                    // success: browse uri
                    browse.setTarget(imdbURI);
                    browse.actionPerformed(null);
                } else {
                    showLookupFailure();
                }

            } catch (Exception ex) {
                showConnectionError(ex);
            }
        }
//        </snip>

        /**
         * Looks up the URI of the info page.
         */
        private URI lookupURI(URI imdbURI) throws IOException,
                URISyntaxException {
            // lookup uri if not yet set
//        	IMDBLink iMDBLink = new IMDBLink();
            String imdbString = IMDBLink_getMovieURIString(target.getMovieTitle(), target.getYear());
            if (imdbString != null) {
                imdbURI = new URI(imdbString);
                target.setIMDBMovieURI(imdbURI);
            }
            return imdbURI;
        }
        
        private void showLookupFailure() {
            JOptionPane.showMessageDialog(
                    null,
                    // PENDING: localized message
                    // PENDING: source panel/window
                    "Unable to locate IMDB URL for" + "\n"
                    + target.getMovieTitle(), "IMDB Link",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        private void showConnectionError(Exception ex) {
            // PENDING JW: use JXErrorDialog!
            ex.printStackTrace();
        }
        
    }
  
    /**
     * Class used to support converting a movie title string into an IMDB URI
     * corresponding to that movie's IMDB entry.   Since IMDB encodes entries with
     * an alpha-numeric key (rather than title), we have to use Yahoo search on the
     * title and then screenscrape the search results to find the IMDB key.
     *
     * @author aim
     */
//    public class IMDBLink {
//        @SuppressWarnings("unused")
//        private static final Logger LOG = Logger.getLogger(IMDBLink.class.getName());
//        
//        private IMDBLink() {
//        }

        /**
         * @param movieTitle the title of the movie
         * @param year       the year the movie was nominated for the oscar
         * @return String containing URI for movie's IMDB entry or null if URI could not be found
         */
        public static String IMDBLink_getMovieURIString(String movieTitle, int year) throws IOException {
            ArrayList<String> matches = new ArrayList<String>();
            URL url;
            BufferedReader reader;

            // btw, google rejects the request with a 403 return code!
            // URL url = new URL("http://www.google.com/search?q=Dazed+and+confused");
            // Thank you, yahoo, for granting our search request :-)
            try {
                String urlKey = URLEncoder.encode(movieTitle, "UTF-8");
                url = new URL("http://search.yahoo.com/search?ei=utf-8&fr=sfp&p=imdb+" +
                        urlKey + "&iscqry=");
            } catch (Exception ex) {
                System.err.println(ex);
                
                return null;
            }
            URLConnection conn = url.openConnection();
            conn.connect();
            // Get the response from Yahoo search query
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            // Parse response a find each imdb/titleString result
            String line;
            String imdbString = ".imdb.com";
            String titleStrings[] = {"/title", "/Title"};

            while ((line = reader.readLine()) != null) {
                for (String titleString : titleStrings) {
                    String scrapeKey = imdbString + titleString;
                    int index = line.indexOf(scrapeKey);
                    if (index != -1) {
                        // The IMDB key looks something like "tt0032138"
                        // so we look for the 9 characters after the scrape key 
                        // to construct the full IMDB URI.
                        // e.g. http://www.imdb.com/title/tt0032138
                        int len = scrapeKey.length();
                        String imdbURL = "http://www" +
                                line.substring(index, index + len) +
                                line.substring(index + len, index + len + 10);

                        if (!matches.contains(imdbURL)) {
                            matches.add(imdbURL);
                        }
                    }
                }
            }
            reader.close();

            // Since imdb contains entries for multiple movies of the same titleString,
            // use the year to find the right entry
            if (matches.size() > 1) {
                for (String matchURL : matches) {
                    if (IMDBLink_verifyYear(matchURL, year)) {
                        return matchURL;
                    }
                }
            }
            return matches.isEmpty()? null : matches.get(0);
        }


        private static boolean IMDBLink_verifyYear(String imdbURL, int movieYear) throws IOException {
            boolean yearMatches = false;

            URLConnection conn = new URL(imdbURL).openConnection();
            conn.connect();

            // Get the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                int index = line.indexOf("</title>");
                if (index != -1) {
                    // looking for "<title>movie title (YEAR)</title>"                
                    try {
                        int year = Integer.parseInt(line.substring(index - 5, index - 1));
                        // Movie may have been made the year prior to oscar award
                        yearMatches = year == movieYear || year == movieYear - 1;

                    } catch (NumberFormatException ex) {
                        // ignore title lines that have other formatting
                    }
                    break; // only interested in analyzing the one line
                }
            }
            reader.close();

            return yearMatches;
        }
//    }
//----------------- not special to OscarRendering, but still missing in SwingX :-)

    /**
     * 
     */
    public static class ToolTipHighlighter extends AbstractHighlighter {
        
        private List<StringValue> stringValues;
        private List<Object> sourceColumns;
        private String delimiter; 
        
        
        /**
         * Adds a StringValue to use on the given sourceColumn.
         * 
         * @param sv the StringValue to use.
         * @param sourceColumn the column identifier of the column to use. 
         */
        public void addStringValue(StringValue sv, Object sourceColumn) {
           if (stringValues == null) {
               stringValues = new ArrayList<StringValue>();
               sourceColumns = new ArrayList<Object>();
           }
           stringValues.add(sv);
           sourceColumns.add(sourceColumn);
        }

        /**
         * Sets the delimiter to use between StringValues.
         * 
         * @param delimiter the delimiter to use between StringValues, if there are more than one.
         */
        public void setDelimiter(String delimiter) {
            this.delimiter = delimiter;
        }
        
        @Override
        protected Component doHighlight(Component component,
                ComponentAdapter adapter) {
            String toolTip = getToolTipText(component, adapter);
            // PENDING: treetableCellRenderer doesn't reset tooltip!
            if (toolTip != null) {
                ((JComponent) component).setToolTipText(toolTip);
            }
            return component;
        }
        
        private String getToolTipText(Component component,
                ComponentAdapter adapter) {
            if ((stringValues == null) || stringValues.isEmpty()) return null;
            String text = "";
            for (int i = 0; i < stringValues.size(); i++) {
                int modelIndex = adapter.getColumnIndex(sourceColumns.get(i));
                if (modelIndex >= 0) {
                   text += stringValues.get(i).getString(adapter.getValue(modelIndex));
                   if ((i != stringValues.size() - 1) && !isEmpty(text)){
                       text += delimiter;
                   }
                }
            }
            return isEmpty(text) ? null : text;
        }

        private boolean isEmpty(String text) {
            return text.length() == 0;
        }

        /**
         * Overridden to check for JComponent type.
         */
        @Override
        protected boolean canHighlight(Component component,
                ComponentAdapter adapter) {
            return component instanceof JComponent;
        }
        
    }

    public static class ListStringValue implements StringValue {

        boolean isToolTip;
        String singleToolTipPrefix;
        String multipleToolTipPrefix;
        
        public ListStringValue() {
            this(false, null, null);
        }
        
        public ListStringValue(boolean asToolTip, String singleItem, String multipleItems) {
            this.isToolTip = asToolTip;
            this.singleToolTipPrefix = singleItem;
            this.multipleToolTipPrefix = multipleItems;
        }

        @SuppressWarnings("unchecked")
        public String getString(Object value) {
            if (value instanceof List) {
                List<String> persons = (List<String>) value;
                if (isToolTip) {
                    return getStringAsToolTip(persons);
                }
                return getStringAsContent(persons);
            }
            return StringValues.TO_STRING.getString(value);
        }

        private String getStringAsToolTip(List<String> persons) {
            if (persons.size() > 1) {
                StringBuffer winners = new StringBuffer("");
                if (multipleToolTipPrefix != null) {
                    winners.append(multipleToolTipPrefix);
                }
                for (String person : persons) {
                    winners.append(person);
                    winners.append(", ");
                }
                winners = winners.delete(winners.lastIndexOf(","), winners.length());
                return winners.toString();
            }
            return StringValues.TO_STRING.getString(singleToolTipPrefix);
        }

        private String getStringAsContent(List<String> persons) {
            if (persons.isEmpty()) {
                return "unknown";
            }
            if (persons.size() > 1) {
                return persons.get(0) + " + more ...";
            }
            return persons.get(0);
        }

    }
   
}