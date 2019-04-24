package components;

import container_pattern.MainWindowContainer;
import date_object.DateHolder;
import listeners.MergeBtnListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;


public class MainWindow extends JFrame {

    //private static final Logger logger = LoggerFactory.getLogger(MainWindow.class);
    private MainWindowContainer mainWindowContainer =  new MainWindowContainer();
    private final String MAIN_TITLE      = "FILE MERGER";
    private final String BTN_TITLE       = "Merge Files";
    private final String UN_ORDERED_TEXT = "UN-ORDERED TEXT";
    private final String ORDERED_TEXT    = "ORDERED TEXT";
    private final String PATTERN_TEXT    = "PATTERN TEXT";
    private static Dimension MINIMUM_DIMENSION = new Dimension(600,400);
    private static Dimension PREFERRED_SIZE    = new Dimension(1000,600);


    public void populateFrame(){

        setTitle(MAIN_TITLE);
        setLayout(new BorderLayout());

        // Initializes the mainWindowContainer with the different components
        populateMainWindowContainer();

        // Set the titled borders for JTextFields/JTextAreas
        setTitledBorders();

        // Sets the pattern JTextField to transparent and the default pattern
        configurePatternTextField();

        // Sets the action listener for merge button
        mainWindowContainer.setMergeBtnListener(new MergeBtnListener(mainWindowContainer));

        // Joins the top and bottom panels into the frame
        addFinishedPanelsToFrame();

        // Change the icon image
        // logger.info("Logger user directory: {}" ,System.getProperty("user.dir"));
        // ImageIcon img = new ImageIcon("../../app_icon.png");
        // setIconImage(img.getImage());
    }

    private void addFinishedPanelsToFrame() {
        this.add( createTopPanel(), BorderLayout.NORTH);
        this.add( createBottomPanel(), BorderLayout.CENTER);
    }

    private void configurePatternTextField() {
        // Make sure the background is just transparent
        mainWindowContainer.getPatternTextField().setOpaque(false);

        // Set default pattern
        mainWindowContainer.getPatternTextField().setText(DateHolder.DEFAULT_FORMAT);
    }

    private void populateMainWindowContainer(){
        mainWindowContainer.setMergeBtn(new JButton(BTN_TITLE));
        mainWindowContainer.setPatternTextField(new JTextField(50));
        mainWindowContainer.setUnOrganizedText(new JTextArea("",5,45));
        mainWindowContainer.setOrganizedText(new JTextArea("",5,45));
        mainWindowContainer.setAscendDescendOrder( new JCheckBox("Descending?"));
        mainWindowContainer.setMinDateField(new JTextField(15));
        mainWindowContainer.setMaxDateField(new JTextField(15));
    }

    private void setTitledBorders(){
        TitledBorder unOrganizedTitledBorder   = BorderFactory.createTitledBorder(UN_ORDERED_TEXT);
        TitledBorder organizedTextTitledBorder = BorderFactory.createTitledBorder(ORDERED_TEXT);
        TitledBorder patternTextFieldTitledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), PATTERN_TEXT, TitledBorder.LEFT, TitledBorder.TOP);

        // Set the title border for the two text areas
        mainWindowContainer.getUnOrganizedText().setBorder(unOrganizedTitledBorder);
        mainWindowContainer.getOrganizedText().setBorder(organizedTextTitledBorder);
        mainWindowContainer.getPatternTextField().setBorder(patternTextFieldTitledBorder);
    }

    private JPanel createTopPanel() {
        // Top Panel Construction
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add( mainWindowContainer.getAscendDescendOrder(), BorderLayout.WEST);
        topPanel.add( mainWindowContainer.getPatternTextField(), BorderLayout.CENTER);
        topPanel.add( mainWindowContainer.getMergeBtn(), BorderLayout.EAST);

        JPanel dateSection = new JPanel(new FlowLayout());
        dateSection.add(new JLabel("Minimum Date"));
        dateSection.add(mainWindowContainer.getMinDateField());
        dateSection.add(new JLabel("Maximum Date"));
        dateSection.add(mainWindowContainer.getMaxDateField());

        topPanel.add(dateSection, BorderLayout.SOUTH);
        return topPanel;
    }

    private JSplitPane createBottomPanel(){
        // Bottom Panel Construction
        JScrollPane unOrganizedScrollPane = new JScrollPane(mainWindowContainer.getUnOrganizedText());
        JScrollPane organizedScrollPane   = new JScrollPane(mainWindowContainer.getOrganizedText());

        // Set scrollbars on the scroll panes
        unOrganizedScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        unOrganizedScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        organizedScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        organizedScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        JSplitPane splitPaneBottomPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, unOrganizedScrollPane, organizedScrollPane);

        return  splitPaneBottomPanel;
    }

    public void setFrameConstraints(){
        this.setVisible(true);
        this.setResizable(true);
        this.setMinimumSize(this.MINIMUM_DIMENSION);
        this.setSize(this.PREFERRED_SIZE);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}