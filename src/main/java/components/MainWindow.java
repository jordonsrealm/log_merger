package components;

import listeners.ClearTextAreaListener;
import listeners.MergeButtonListener;
import listeners.OpenFileDialogListener;
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
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;


public class MainWindow extends JFrame {

	private static final long serialVersionUID         = 1L;
	private static final Logger logger                 = LoggerFactory.getLogger(MainWindow.class);
	
    private static final String CLEAR_TEXT_AREA        = "Clear";
    private static final String SAVE_TO_FILE           = "Save To File";
    private static final String SELECT_FILE_STR        = "Select File";
    private static final String DATE_PATTERN           = " DATE PATTERN: ";
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
    private static final String HEX_WHITE_COLOR		   = "0xffffff";
    
    private JSplitPane bottomSplitPane;
    private DateLineProcessor processor;
    private Component glassPane = getGlassPane();
    private ExecutorService executor;
    

    public MainWindow(ExecutorService executor) {
    	this.setExecutor(executor);
    }
    
    public void initializeFrame(){
        logger.debug("User directory: {}" , System.getProperty("user.dir"));
        
        this.setTitle(configGetter.getApplicationName());
        
        this.setLayout(new BorderLayout());
        add( createBottomPanel(), BorderLayout.CENTER);
        add( createTopPanel(), BorderLayout.NORTH);
        
        addComponentsToMainWindowContainer();
        setImageIconForApplication();
    }

    private JSplitPane createBottomPanel(){
        mainWindowContainer.setUnOrganizedScrollPane(new JScrollPane(unOrganizedText));
        JPanel jpanel = new JPanel(new BorderLayout());
        jpanel.add(new OrganizedToolsPanel(), BorderLayout.NORTH);
        jpanel.add(organizedText, BorderLayout.CENTER);
        mainWindowContainer.setOrganizedScrollPane(new JScrollPane(jpanel));

        bottomSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mainWindowContainer.getUnOrganizedScrollPane(), mainWindowContainer.getOrganizedScrollPane());
        bottomSplitPane.setDividerLocation(configGetter.getWindowWidth()/2);

        return bottomSplitPane;
    }

    private JPanel createTopPanel() {
        topPanel.setLayout(new BorderLayout());
        
        JLabel dateLabel = new JLabel(DATE_PATTERN);
        dateLabel.setOpaque(true);
        dateLabel.setBackground(Color.decode("0xffffff"));
        topPanel.add(dateLabel, BorderLayout.WEST);
        topPanel.add(fieldPattern, BorderLayout.CENTER);
        topPanel.add(mergeButton, BorderLayout.EAST);

        JPanel dateSection = new JPanel(new FlowLayout());
        dateSection.add(clearUnorganizedText);
        dateSection.add(fileInputButton);
        dateSection.add(fileInput);
        dateSection.add(useFileButton);
        
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setPreferredSize(new Dimension(SEPARATOR_ROW_COUNT,SEPARATOR_COLUMN_COUNT));
        dateSection.add(separator);
        
        minDateField.setBorder(BorderFactory.createTitledBorder(MIN_DATE_STR));
        dateSection.add(minDateField);
        
        maxDateField.setBorder(BorderFactory.createTitledBorder(MAX_DATE_STR));
        dateSection.add(maxDateField);
        
        dateSection.add(saveToFileButton);
        dateSection.setBackground(Color.decode(HEX_WHITE_COLOR));

        topPanel.add(dateSection, BorderLayout.SOUTH);
        
        return topPanel;
    }

    private void addComponentsToMainWindowContainer(){
    	
        mainWindowContainer.setUnOrganizedText(unOrganizedText);
        
        mergeButton.addActionListener(new MergeButtonListener(mainWindowContainer, executor));
        mainWindowContainer.setMergeBtn(mergeButton);
        
        mainWindowContainer.setFileInputButton(fileInputButton);

        mainWindowContainer.getFileInputButton().addActionListener(new OpenFileDialogListener(fileInput));

        fieldPattern.setText(DateHolder.DEFAULT_FORMAT);
        fieldPattern.setOpaque(true);
        mainWindowContainer.setPatternTextField(fieldPattern);
        
        clearUnorganizedText.addActionListener(new ClearTextAreaListener(mainWindowContainer, executor));
        mainWindowContainer.setClearUnorganizedText(clearUnorganizedText);
        
        mainWindowContainer.setFileNameInputTextField(fileInput);
        mainWindowContainer.setMinDateField(minDateField);
        mainWindowContainer.setMaxDateField(maxDateField);

        mainWindowContainer.setUseFileBtn(useFileButton);
        mainWindowContainer.getUseFileBtn().addActionListener(new SelectFileListener(mainWindowContainer, executor, configGetter.getHighlightHexColor()));
        
        mainWindowContainer.setSaveToFile(saveToFileButton);
        
        processor = new DateLineProcessor(mainWindowContainer);
        organizedText.setRunnable(processor);
        mainWindowContainer.setOrganizedText(organizedText);
        mainWindowContainer.getSaveToFile().addActionListener(new SaveFileListener(organizedText));
        
        mainWindowContainer.setTopPanel(topPanel);
        mainWindowContainer.setBottomSplitPane(bottomSplitPane);
        mainWindowContainer.setGlassPane(glassPane);
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

    public void setFrameDimensionsAndBehaviors(){
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