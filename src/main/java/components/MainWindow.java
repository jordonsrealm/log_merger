package components;

import listeners.MergeButtonListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import configuration.ConfigurationGetter;
import container.MainWindowContainer;
import date.object.DateHolder;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;


public class MainWindow extends JFrame {

	private static final long serialVersionUID         = 1L;
	private static final Logger logger                 = LoggerFactory.getLogger(MainWindow.class);
    private static final String UN_ORDERED_TEXT        = "UN-ORDERED TEXT";
    private static final String CLEAR_TEXT_AREA        = "Clear Text Area";
    private static final String SAVE_TO_FILE           = "Save To File...";
    private static final String SELECT_FILE_STR        = "Select File...";
    private static final String ORDERED_TEXT           = "ORDERED TEXT";
    private static final String PATTERN_TEXT           = "PATTERN TEXT";
    private static final String MIN_DATE_STR           = "Minimum Date";
    private static final String MAX_DATE_STR           = "Maximum Date";
    private static final String BTN_TITLE              = "Merge Files";
    private static final String DESCENDING_STRING      = "Descending?";
    private static final String USE_FILE_STR           = "Use File...";
    private static final String BLANK_STR              = "";
    private MainWindowContainer mainWindowContainer    = new MainWindowContainer(this);
    private ConfigurationGetter configGetter           = new ConfigurationGetter();
    private JPanel topPanel 						   = new JPanel();
    private static final int TEXT_AREA_ROWS_CNT 	   = 5;
    private static final int TEXT_AREA_COLUMNS_CNT     = 70;
    private static final int TEXT_FIELD_COLUMNS_CNT    = 15;
    private static final int PATTERN_FIELD_COLUMN_CNT  = 50;
    private static final int FILENAME_FIELD_COLUMN_CNT = 60;
    private static final int SEPARATOR_ROW_COUNT	   = 3;
    private static final int SEPARATOR_COLUMN_COUNT	   = 40;
    private JSplitPane bottomSplitPane;
    

    public void populateFrame(){
        logger.debug("Logger user directory: {}" , System.getProperty("user.dir"));
        
        this.setTitle(configGetter.getApplicationName());
        this.setLayout(new BorderLayout());
        
        initializeMainWindowContainer();

        addFinishedPanelsToFrame();

        setTitledBordersAndConfigurePatternTextField();
        
        mainWindowContainer.setMergeButtonListener(new MergeButtonListener(mainWindowContainer));
        
        setImageIconForApplication();
    }
    
    private void setImageIconForApplication() {
		try {
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			InputStream  inputStreamFromPng = classloader.getResourceAsStream(configGetter.getAppIconName());
	        
			ImageIcon icon = new ImageIcon(ImageIO.read(inputStreamFromPng));
			
			setIconImage(icon.getImage());
		} catch (IOException e) {
			logger.error("Unable to set the image icon for application. Resource: {}", configGetter.getAppIconName(), e);
		}
    }

    private void initializeMainWindowContainer(){
        mainWindowContainer.setUnOrganizedText(new JTextArea(BLANK_STR,TEXT_AREA_ROWS_CNT,TEXT_AREA_COLUMNS_CNT));
        mainWindowContainer.setOrganizedText(new JTextArea(BLANK_STR,TEXT_AREA_ROWS_CNT,TEXT_AREA_COLUMNS_CNT));
        mainWindowContainer.setFileNameInputTextField(new JTextField(FILENAME_FIELD_COLUMN_CNT));
        mainWindowContainer.setPatternTextField(new JTextField(PATTERN_FIELD_COLUMN_CNT));
        mainWindowContainer.setAscendDescendOrder( new JCheckBox(DESCENDING_STRING));
        mainWindowContainer.setMinDateField(new JTextField(TEXT_FIELD_COLUMNS_CNT));
        mainWindowContainer.setMaxDateField(new JTextField(TEXT_FIELD_COLUMNS_CNT));
        mainWindowContainer.setClearUnorganizedText(new JButton(CLEAR_TEXT_AREA));
        mainWindowContainer.setFileInputButton(new JButton(SELECT_FILE_STR));
        mainWindowContainer.setSelectFileBtn(new JButton(USE_FILE_STR));
        mainWindowContainer.setSaveToFile(new JButton(SAVE_TO_FILE));
        mainWindowContainer.setBottomSplitPane(createBottomPanel());
        mainWindowContainer.setMergeBtn(new JButton(BTN_TITLE));
        mainWindowContainer.setTopPanel(createTopPanel());
    }

    private void setTitledBordersAndConfigurePatternTextField(){
    	mainWindowContainer.getPatternTextField().setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), PATTERN_TEXT, TitledBorder.LEFT, TitledBorder.TOP));
        mainWindowContainer.getUnOrganizedText().setBorder(BorderFactory.createTitledBorder(UN_ORDERED_TEXT));
        mainWindowContainer.getOrganizedText().setBorder(BorderFactory.createTitledBorder(ORDERED_TEXT));
        mainWindowContainer.getPatternTextField().setText(DateHolder.DEFAULT_FORMAT);
        mainWindowContainer.getPatternTextField().setOpaque(true);
    }
    
    private void addFinishedPanelsToFrame() {
        this.add( bottomSplitPane, BorderLayout.CENTER);
        this.add( topPanel, BorderLayout.NORTH);
    }

    private JPanel createTopPanel() {
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
        separator.setPreferredSize(new Dimension(SEPARATOR_ROW_COUNT,SEPARATOR_COLUMN_COUNT));
        return separator;
    }

    private JSplitPane createBottomPanel(){
        JScrollPane unOrganizedScrollPane = new JScrollPane(mainWindowContainer.getUnOrganizedText());
        JScrollPane organizedScrollPane   = new JScrollPane(mainWindowContainer.getOrganizedText());
        
        mainWindowContainer.setUnOrganizedScrollPane(unOrganizedScrollPane);
        mainWindowContainer.setOrganizedScrollPane(organizedScrollPane);

        bottomSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, unOrganizedScrollPane, organizedScrollPane);

        return  bottomSplitPane;
    }

    public void setFrameConstraints(){
        this.setMinimumSize(new Dimension(configGetter.getWindowWidth()/2,configGetter.getWindowHeight()/2));
        this.setSize(new Dimension(configGetter.getWindowWidth(),configGetter.getWindowHeight()));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setVisible(true);
    }
	
	public MainWindowContainer getMainWindowContainer() {
		return this.mainWindowContainer;
	}
}