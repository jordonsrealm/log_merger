package mainwindow.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import configuration.ConfigurationGetter;
import date.object.DateHolder;
import mainwindow.container.MainWindowContainer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;


public class MainWindow extends JFrame{

	private static final long serialVersionUID         = 1L;
	private static final Logger logger                 = LoggerFactory.getLogger(MainWindow.class);
	
    private static final String DATE_PATTERN           = "DATE PATTERN";
    private static final String MIN_DATE_STR           = "Min Date";
    private static final String MAX_DATE_STR           = "Max Date";
    private static final String BLANK_STR              = "";
    
    private JTextArea organizedText 			   	   = new JTextArea(BLANK_STR,TEXT_AREA_ROWS_CNT,TEXT_AREA_COLUMNS_CNT);
    private JTextArea unOrganizedText 				   = new JTextArea(BLANK_STR,TEXT_AREA_ROWS_CNT,TEXT_AREA_COLUMNS_CNT);
    private JTextField regexPattern 				   = new JTextField(REGEX_PATTERN_COLUMN_CNT);
    private JTextField fileInput 					   = new JTextField(FILENAME_FIELD_COLUMN_CNT);
    private JTextField minDateField 				   = new JTextField(TEXT_FIELD_COLUMNS_CNT);
    private JTextField maxDateField 				   = new JTextField(TEXT_FIELD_COLUMNS_CNT);
    private JScrollPane unOrganizedScrollPane		   = new JScrollPane(unOrganizedText);
    private JScrollPane organizedScrollPane			   = new JScrollPane(organizedText);
    private MainWindowContainer mainWindowContainer	   = new MainWindowContainer();
    private ConfigurationGetter configGetter           = new ConfigurationGetter();
    private JPanel topPanel 						   = new JPanel();
    private static final int TEXT_AREA_ROWS_CNT 	   = 5;
    private static final int TEXT_AREA_COLUMNS_CNT     = 70;
    private static final int TEXT_FIELD_COLUMNS_CNT    = 15;
    private static final int REGEX_PATTERN_COLUMN_CNT  = 30;
    private static final int FILENAME_FIELD_COLUMN_CNT = 60;
    
    private DescendingCheckBox isDescendingCheckBox;
    private ClearUnOrderedTextButton clearUnorganizedTextButton = new ClearUnOrderedTextButton(this);
    private SearchButton searchButton = new SearchButton(this);
    private AddFileButton addFileButton = new AddFileButton(this);
    private SaveFileButton saveFileButton = new SaveFileButton(this);
    private MergeFileButton mergeButton = new MergeFileButton(this);
    private JSplitPane bottomSplitPane;
    private Component glassPane = getGlassPane();
    private ExecutorService executor;
    

    public MainWindow(ExecutorService executor) {
    	this.setExecutor(executor);
    }
    
    public void initializeFrame(){
        logger.debug("User directory: {}" , System.getProperty("user.dir"));
        
        this.setTitle(configGetter.getApplicationName());
        
        this.setLayout(new BorderLayout());
        
        add( createTopPanel(), BorderLayout.NORTH);
        add( createBottomPanel(), BorderLayout.CENTER);
        
        addComponentsToMainWindowContainer();
        
        this.addComponentListener(new ComponentAdapter() {
        	 public void componentResized(ComponentEvent componentEvent) {
        		 bottomSplitPane.setDividerLocation(getWidth()/2);
        	 }
        });
        
        setImageIconForApplication();
    }

    private JSplitPane createBottomPanel(){
        
        JPanel rightUpperPanel = new JPanel(new FlowLayout());
        isDescendingCheckBox = new DescendingCheckBox( mainWindowContainer);
        rightUpperPanel.add(isDescendingCheckBox);
        rightUpperPanel.add(new JLabel(MIN_DATE_STR));
        rightUpperPanel.add(minDateField);
        rightUpperPanel.add(new JLabel(MAX_DATE_STR));
        rightUpperPanel.add(maxDateField);
        rightUpperPanel.add(saveFileButton);
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(rightUpperPanel, BorderLayout.NORTH);
        rightPanel.add(organizedScrollPane, BorderLayout.CENTER);
        
        //LineNumbersView lineNumbers = new LineNumbersView(mainWindowContainer.getUnOrganizedText());
        //JScrollPane scroll = new JScrollPane(mainWindowContainer.getUnOrganizedText());
        //scroll.setRowHeaderView(lineNumbers);
        JPanel patternAndClearPanel = new JPanel(new FlowLayout());
        patternAndClearPanel.add(clearUnorganizedTextButton);
        JLabel dateLabel = new JLabel(DATE_PATTERN);
        patternAndClearPanel.add(dateLabel);
        patternAndClearPanel.add(regexPattern);
        patternAndClearPanel.add(mergeButton);
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(patternAndClearPanel, BorderLayout.NORTH);
        leftPanel.add(unOrganizedScrollPane, BorderLayout.CENTER);

        bottomSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        bottomSplitPane.setDividerLocation(configGetter.getWindowWidth()/2);

        return bottomSplitPane;
    }

    private JPanel createTopPanel() {
        topPanel.setLayout(new BorderLayout());

        JPanel dateSection = new JPanel(new FlowLayout());
        dateSection.add(searchButton);
        dateSection.add(fileInput);
        dateSection.add(addFileButton);
        dateSection.setBackground(Color.WHITE);

        topPanel.add(dateSection, BorderLayout.CENTER);
        
        return topPanel;
    }

    private void addComponentsToMainWindowContainer(){
    	
        mainWindowContainer.setUnOrganizedText(unOrganizedText);
        mainWindowContainer.setUnOrganizedScrollPane(unOrganizedScrollPane);
        
        mainWindowContainer.setOrganizedText(organizedText);
        mainWindowContainer.setOrganizedScrollPane(organizedScrollPane);

        regexPattern.setText(DateHolder.DEFAULT_FORMAT);
        regexPattern.setOpaque(true);
        mainWindowContainer.setPatternTextField(regexPattern);
        
        mainWindowContainer.setFileNameInputTextField(fileInput);
        mainWindowContainer.setMinDateField(minDateField);
        mainWindowContainer.setMaxDateField(maxDateField);

        mainWindowContainer.setClearUnOrderedTextButton(clearUnorganizedTextButton);
        mainWindowContainer.setSearchButton(searchButton);
        mainWindowContainer.setSaveFileButton(saveFileButton);
        mainWindowContainer.setMergeButton(mergeButton);
        mainWindowContainer.setAddFileButton(addFileButton);
        
        mainWindowContainer.setGlassPane(glassPane);
        mainWindowContainer.setIsDescendingCheckBox(isDescendingCheckBox);
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

	public ConfigurationGetter getConfigGetter() {
		return configGetter;
	}
	
}