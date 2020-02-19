package components;

import container_pattern.MainWindowContainer;
import date_object.DateHolder;
import listeners.MergeButtonListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import configuration.ConfigurationGetter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;


public class MainWindow extends JFrame {

	private static final long serialVersionUID      = 1L;
	private static final Logger logger              = LoggerFactory.getLogger(MainWindow.class);
    private static final String BTN_TITLE           = "Merge Files";
    private static final String UN_ORDERED_TEXT     = "UN-ORDERED TEXT";
    private static final String ORDERED_TEXT        = "ORDERED TEXT";
    private static final String PATTERN_TEXT        = "PATTERN TEXT";
    private static final String DESCENDING_STRING   = "Descending?";
    private static final String SELECT_FILE_STR     = "Select File...";
    private static final String USE_FILE_STR        = "Use File...";
    private static final String MIN_DATE_STR        = "Minimum Date";
    private static final String MAX_DATE_STR        = "Maximum Date";
    private static final String CLEAR_TEXT_AREA     = "Clear Text Area";
    private static final String SAVE_TO_FILE        = "Save To File...";
    private static final String BLANK_STR           = "";
    private MainWindowContainer mainWindowContainer = new MainWindowContainer();
    private ConfigurationGetter configGetter        = new ConfigurationGetter();
    

    public void populateFrame(){
        logger.debug("Logger user directory: {}" ,System.getProperty("user.dir"));
        
        this.setTitle(configGetter.getApplicationName());
        this.setLayout(new BorderLayout());

        initializeMainWindowContainer();

        setTitledBordersAndConfigurePatternTextField();

        addFinishedPanelsToFrame();
        
        mainWindowContainer.setMergeButtonListener(new MergeButtonListener(mainWindowContainer));
        // ImageIcon img = new ImageIcon("../../app_icon.png");
        // setIconImage(img.getImage());
    }

    private void initializeMainWindowContainer(){
        mainWindowContainer.setMergeBtn(new JButton(BTN_TITLE));
        mainWindowContainer.setPatternTextField(new JTextField(50));
        mainWindowContainer.setUnOrganizedText(new JTextArea(BLANK_STR,5,55));
        mainWindowContainer.setOrganizedText(new JTextArea(BLANK_STR,5,40));
        mainWindowContainer.setAscendDescendOrder( new JCheckBox(DESCENDING_STRING));
        mainWindowContainer.setMinDateField(new JTextField(15));
        mainWindowContainer.setMaxDateField(new JTextField(15));
        mainWindowContainer.setFileNameInputTextField(new JTextField(15));
        mainWindowContainer.setFileInputButton(new JButton(SELECT_FILE_STR));
        mainWindowContainer.setSelectFileBtn(new JButton(USE_FILE_STR));
        mainWindowContainer.setClearUnorganizedText(new JButton(CLEAR_TEXT_AREA));
        mainWindowContainer.setSaveToFile(new JButton(SAVE_TO_FILE));
    }

    private void setTitledBordersAndConfigurePatternTextField(){
        TitledBorder unOrganizedTitledBorder      = BorderFactory.createTitledBorder(UN_ORDERED_TEXT);
        TitledBorder organizedTextTitledBorder    = BorderFactory.createTitledBorder(ORDERED_TEXT);
        TitledBorder patternTextFieldTitledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),
                                                                                    PATTERN_TEXT,
                                                                                    TitledBorder.LEFT,
                                                                                    TitledBorder.TOP);

        mainWindowContainer.getUnOrganizedText().setBorder(unOrganizedTitledBorder);
        mainWindowContainer.getOrganizedText().setBorder(organizedTextTitledBorder);
        mainWindowContainer.getPatternTextField().setBorder(patternTextFieldTitledBorder);
        
        mainWindowContainer.getPatternTextField().setOpaque(true);
        mainWindowContainer.getPatternTextField().setText(DateHolder.DEFAULT_FORMAT);
    }
    
    private void addFinishedPanelsToFrame() {
        this.add( createTopPanel(), BorderLayout.NORTH);
        this.add( createBottomPanel(), BorderLayout.CENTER);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add( mainWindowContainer.getAscendDescendOrder(), BorderLayout.WEST);
        topPanel.add( mainWindowContainer.getPatternTextField(), BorderLayout.CENTER);
        topPanel.add( mainWindowContainer.getMergeBtn(), BorderLayout.EAST);

        JPanel dateSection = new JPanel(new FlowLayout());
        dateSection.add(mainWindowContainer.getClearUnorganizedText());
        dateSection.add(mainWindowContainer.getFileInputButton());
        dateSection.add(mainWindowContainer.getFileNameInputTextField());
        dateSection.add(mainWindowContainer.getSelectFileBtn());
        dateSection.add(createVerticalSeparator());
        dateSection.add(new JLabel(MIN_DATE_STR));
        dateSection.add(mainWindowContainer.getMinDateField());
        dateSection.add(new JLabel(MAX_DATE_STR));
        dateSection.add(mainWindowContainer.getMaxDateField());
        dateSection.add(mainWindowContainer.getSaveToFile());

        topPanel.add(dateSection, BorderLayout.SOUTH);
        return topPanel;
    }

    private JComponent createVerticalSeparator() {
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setPreferredSize(new Dimension(3,40));
        return separator;
    }

    private JSplitPane createBottomPanel(){
        JScrollPane unOrganizedScrollPane = new JScrollPane(mainWindowContainer.getUnOrganizedText());
        JScrollPane organizedScrollPane   = new JScrollPane(mainWindowContainer.getOrganizedText());

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