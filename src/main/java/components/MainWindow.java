package components;

import container_pattern.MainWindowContainer;
import date_object.DateHolder;
import listeners.MergeBtnListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import configuration.ConfigurationGetter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;


public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(MainWindow.class);
    private MainWindowContainer mainWindow =  new MainWindowContainer();
    private static final String BTN_TITLE           = "Merge Files";
    private static final String UN_ORDERED_TEXT     = "UN-ORDERED TEXT";
    private static final String ORDERED_TEXT        = "ORDERED TEXT";
    private static final String PATTERN_TEXT        = "PATTERN TEXT";
    private static final String DESCENDING_STRING   = "Descending?";
    private static final String SELECT_FILE_STR     = "Select File...";
    private static final String USE_FILE_STR        = "Use File...";
    private static final String MIN_DATE_STR        = "Minimum Date";
    private static final String MAX_DATE_STR        = "Maximum Date";
    private static final String BLANK_STR           = "";
    private ConfigurationGetter configGetter = new ConfigurationGetter();
    

    public void populateFrame(){
        setTitle(configGetter.getApplicationName());
        setLayout(new BorderLayout());

        populateMainWindowContainer();

        setTitledBorders();

        configurePatternTextField();

        mainWindow.setMergeBtnListener(new MergeBtnListener(mainWindow));
        
        addFinishedPanelsToFrame();

        logger.debug("Logger user directory: {}" ,System.getProperty("user.dir"));
        // ImageIcon img = new ImageIcon("../../app_icon.png");
        // setIconImage(img.getImage());
    }

    private void addFinishedPanelsToFrame() {
        this.add( createTopPanel(), BorderLayout.NORTH);
        this.add( createBottomPanel(), BorderLayout.CENTER);
    }

    private void configurePatternTextField() {
        // Make sure the background is just transparent
        mainWindow.getPatternTextField().setOpaque(false);

        // Set default pattern
        mainWindow.getPatternTextField().setText(DateHolder.DEFAULT_FORMAT);
    }

    private void populateMainWindowContainer(){
        mainWindow.setMergeBtn(new JButton(BTN_TITLE));
        mainWindow.setPatternTextField(new JTextField(50));
        mainWindow.setUnOrganizedText(new JTextArea(BLANK_STR,5,55));
        mainWindow.setOrganizedText(new JTextArea(BLANK_STR,5,40));
        mainWindow.setAscendDescendOrder( new JCheckBox(DESCENDING_STRING));
        mainWindow.setMinDateField(new JTextField(15));
        mainWindow.setMaxDateField(new JTextField(15));
        mainWindow.setFileNameInputTextField(new JTextField(15));
        mainWindow.setFileInputButton(new JButton(SELECT_FILE_STR));
        mainWindow.setSelectFileBtn(new JButton(USE_FILE_STR));
        mainWindow.setClearUnorganizedText(new JButton("Clear Text Area"));
        mainWindow.setSaveToFile(new JButton("Save To File..."));
    }

    private void setTitledBorders(){
        TitledBorder unOrganizedTitledBorder   = BorderFactory.createTitledBorder(UN_ORDERED_TEXT);
        TitledBorder organizedTextTitledBorder = BorderFactory.createTitledBorder(ORDERED_TEXT);
        TitledBorder patternTextFieldTitledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),
                                                                                    PATTERN_TEXT,
                                                                                    TitledBorder.LEFT,
                                                                                    TitledBorder.TOP);

        // Set the title border for the two text areas
        mainWindow.getUnOrganizedText().setBorder(unOrganizedTitledBorder);
        mainWindow.getOrganizedText().setBorder(organizedTextTitledBorder);
        mainWindow.getPatternTextField().setBorder(patternTextFieldTitledBorder);
    }

    private JPanel createTopPanel() {
        // Top Panel Construction
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add( mainWindow.getAscendDescendOrder(), BorderLayout.WEST);
        topPanel.add( mainWindow.getPatternTextField(), BorderLayout.CENTER);
        topPanel.add( mainWindow.getMergeBtn(), BorderLayout.EAST);

        JPanel dateSection = new JPanel(new FlowLayout());
        dateSection.add(mainWindow.getClearUnorganizedText());
        dateSection.add(mainWindow.getFileInputButton());
        dateSection.add(mainWindow.getFileNameInputTextField());
        dateSection.add(mainWindow.getSelectFileBtn());
        dateSection.add(createVerticalSeparator());
        dateSection.add(new JLabel(MIN_DATE_STR));
        dateSection.add(mainWindow.getMinDateField());
        dateSection.add(new JLabel(MAX_DATE_STR));
        dateSection.add(mainWindow.getMaxDateField());
        dateSection.add(mainWindow.getSaveToFile());

        topPanel.add(dateSection, BorderLayout.SOUTH);
        return topPanel;
    }

    static JComponent createVerticalSeparator() {
        JSeparator x = new JSeparator(SwingConstants.VERTICAL);
        x.setPreferredSize(new Dimension(3,40));
        return x;
    }

    private JSplitPane createBottomPanel(){
        // Bottom Panel Construction
        JScrollPane unOrganizedScrollPane = new JScrollPane(mainWindow.getUnOrganizedText());
        JScrollPane organizedScrollPane   = new JScrollPane(mainWindow.getOrganizedText());

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
        this.setMinimumSize(new Dimension(configGetter.getWindowWidth()/2,configGetter.getWindowHeight()/2));
        this.setSize(new Dimension(configGetter.getWindowWidth(),configGetter.getWindowHeight()));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}