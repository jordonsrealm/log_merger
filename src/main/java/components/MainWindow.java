package components;

import listeners.ClearTextAreaListener;
import listeners.MergeButtonListener;
import listeners.OpenFileDialog;
import listeners.SaveFileListener;
import listeners.SelectFileListener;
import runnables.DateLineProcessor;
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
import java.util.concurrent.ExecutorService;


public class MainWindow extends JFrame {

	private static final long serialVersionUID         = 1L;
	private static final Logger logger                 = LoggerFactory.getLogger(MainWindow.class);
    private static final String UN_ORDERED_TEXT        = "UN-ORDERED TEXT";
    private static final String CLEAR_TEXT_AREA        = "Clear";
    private static final String SAVE_TO_FILE           = "Save To File";
    private static final String SELECT_FILE_STR        = "Select File";
    private static final String ORDERED_TEXT           = "ORDERED TEXT";
    private static final String PATTERN_TEXT           = "PATTERN TEXT";
    private static final String MIN_DATE_STR           = "Minimum Date";
    private static final String MAX_DATE_STR           = "Maximum Date";
    private static final String BTN_TITLE              = "Merge Files";
    private static final String USE_FILE_STR           = "Use File";
    private static final String BLANK_STR              = "";
    private JTextField fieldPattern 				   = new JTextField(PATTERN_FIELD_COLUMN_CNT);
    private JTextArea unOrganizedText 				   = new JTextArea(BLANK_STR,TEXT_AREA_ROWS_CNT,TEXT_AREA_COLUMNS_CNT);
    private JButton mergeButton 					   = new JButton(BTN_TITLE);
    private JTextField fileInput 					   = new JTextField(FILENAME_FIELD_COLUMN_CNT);
    private OrderedTextArea organizedText 			   = new OrderedTextArea(BLANK_STR,TEXT_AREA_ROWS_CNT,TEXT_AREA_COLUMNS_CNT);
    private JTextField minDateField 				   = new JTextField(TEXT_FIELD_COLUMNS_CNT);
    private JTextField maxDateField 				   = new JTextField(TEXT_FIELD_COLUMNS_CNT);
    private JButton clearUnorganizedText 			   = new JButton(CLEAR_TEXT_AREA);
    private JButton fileInputButton 				   = new JButton(SELECT_FILE_STR);
    private JButton useFileButton 					   = new JButton(USE_FILE_STR);
    private JButton saveToFileButton 				   = new JButton(SAVE_TO_FILE);
    private MainWindowContainer mainWindowContainer	   = new MainWindowContainer();
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
    private DateLineProcessor processor;
    private Component glassPane = getGlassPane();
    private ExecutorService executor;
    

    public MainWindow(ExecutorService executor) {
    	this.setExecutor(executor);
    }
    
    public void populateFrame(){
        logger.debug("Logger user directory: {}" , System.getProperty("user.dir"));
        
        this.setTitle(configGetter.getApplicationName());
        this.setLayout(new BorderLayout());

        createTopPanel();
        createBottomPanel();
        
        addFinishedPanelsToFrame();

        setTitledBordersAndConfigurePatternTextField();
        
        initializeMainWindowContainer();
        
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
        mainWindowContainer.setUnOrganizedText(unOrganizedText);
        mainWindowContainer.setMergeBtn(mergeButton);
        processor = new DateLineProcessor(mainWindowContainer);
        mainWindowContainer.setOrganizedText(organizedText);
        organizedText.setRunnable(processor);
        mainWindowContainer.setFileNameInputTextField(fileInput);
        mainWindowContainer.setPatternTextField(fieldPattern);
        mainWindowContainer.setMinDateField(minDateField);
        mainWindowContainer.setMaxDateField(maxDateField);
        mainWindowContainer.setClearUnorganizedText(clearUnorganizedText);
        mainWindowContainer.setFileInputButton(fileInputButton);
        mainWindowContainer.getFileInputButton().addActionListener(new OpenFileDialog(fileInput));
        mainWindowContainer.setUseFileBtn(useFileButton);
        mainWindowContainer.setSaveToFile(saveToFileButton);
        mainWindowContainer.getSaveToFile().addActionListener(new SaveFileListener(organizedText));
        mainWindowContainer.setTopPanel(topPanel);
        mainWindowContainer.setBottomSplitPane(bottomSplitPane);
        mainWindowContainer.setGlassPane(glassPane);
        
        useFileButton.addActionListener(new SelectFileListener(mainWindowContainer, executor));
        mergeButton.addActionListener(new MergeButtonListener(mainWindowContainer, executor));
        clearUnorganizedText.addActionListener(new ClearTextAreaListener(mainWindowContainer, executor));
    }

    private void setTitledBordersAndConfigurePatternTextField(){
    	fieldPattern.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), PATTERN_TEXT, TitledBorder.LEFT, TitledBorder.TOP));
        unOrganizedText.setBorder(BorderFactory.createTitledBorder(UN_ORDERED_TEXT));
        organizedText.setBorder(BorderFactory.createTitledBorder(ORDERED_TEXT));
        fieldPattern.setText(DateHolder.DEFAULT_FORMAT);
        fieldPattern.setOpaque(true);
    }
    
    private void addFinishedPanelsToFrame() {
        this.add( bottomSplitPane, BorderLayout.CENTER);
        this.add( topPanel, BorderLayout.NORTH);
    }

    private JPanel createTopPanel() {
        topPanel.setLayout(new BorderLayout());
        topPanel.add( fieldPattern, BorderLayout.CENTER);
        topPanel.add( mergeButton, BorderLayout.EAST);

        JPanel dateSection = new JPanel(new FlowLayout());
        dateSection.add(clearUnorganizedText);
        dateSection.add(fileInputButton);
        dateSection.add(fileInput);
        dateSection.add(useFileButton);
        dateSection.add(createVerticalSeparator());
        
        minDateField.setBorder(BorderFactory.createTitledBorder(MIN_DATE_STR));
        dateSection.add(minDateField);
        
        maxDateField.setBorder(BorderFactory.createTitledBorder(MAX_DATE_STR));
        dateSection.add(maxDateField);
        
        dateSection.add(saveToFileButton);
        dateSection.setBackground(Color.decode("0xffffff"));

        topPanel.add(dateSection, BorderLayout.SOUTH);
        
        return topPanel;
    }

    private JSplitPane createBottomPanel(){
        JScrollPane unOrganizedScrollPane = new JScrollPane(unOrganizedText);
        JScrollPane organizedScrollPane   = new JScrollPane(organizedText);
        
        mainWindowContainer.setUnOrganizedScrollPane(unOrganizedScrollPane);
        mainWindowContainer.setOrganizedScrollPane(organizedScrollPane);

        bottomSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, unOrganizedScrollPane, organizedScrollPane);
        
        bottomSplitPane.setDividerLocation(configGetter.getWindowWidth()/2);

        return  bottomSplitPane;
    }

    private JComponent createVerticalSeparator() {
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setPreferredSize(new Dimension(SEPARATOR_ROW_COUNT,SEPARATOR_COLUMN_COUNT));
        return separator;
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
		return mainWindowContainer;
	}

	public void setMainWindowContainer(MainWindowContainer mainWindowContainer) {
		this.mainWindowContainer = mainWindowContainer;
	}

	public ExecutorService getExecutor() {
		return executor;
	}

	public void setExecutor(ExecutorService executor) {
		this.executor = executor;
	}
	
}