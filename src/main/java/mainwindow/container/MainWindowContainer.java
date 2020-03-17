package mainwindow.container;

import javax.swing.*;

import configuration.ConfigurationGetter;
import mainwindow.components.AddFileButton;
import mainwindow.components.ClearUnOrderedTextButton;
import mainwindow.components.DescendingCheckBox;
import mainwindow.components.LogMergerWindow;
import mainwindow.components.MergeFileButton;
import mainwindow.components.SaveFileButton;
import mainwindow.components.SearchButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;


public class MainWindowContainer {

    private ConfigurationGetter configGetter = new ConfigurationGetter();
    
    private JTextField fileNameInputTextField = new JTextField(FILENAME_FIELD_COLUMN_CNT);
    private JScrollPane unOrganizedScrollPane = new JScrollPane(this.unOrganizedText);
    private JScrollPane organizedScrollPane	= new JScrollPane(this.organizedText);
    private JTextField regexPatternTextField = new JTextField(REGEX_PATTERN_COLUMN_CNT);
    private JTextArea unOrganizedText = new JTextArea(BLANK_STR,TEXT_AREA_ROWS_CNT,TEXT_AREA_COLUMNS_CNT);
    private JTextArea organizedText = new JTextArea(BLANK_STR,TEXT_AREA_ROWS_CNT,TEXT_AREA_COLUMNS_CNT);
    private JTextField minDateField = new JTextField(TEXT_FIELD_COLUMNS_CNT);
    private JTextField maxDateField = new JTextField(TEXT_FIELD_COLUMNS_CNT);
    private JCheckBox isDescendingCheckBox = new DescendingCheckBox(this);
    private ClearUnOrderedTextButton clearUnOrderedTextButton;
    private SaveFileButton saveFileButton;
    private AddFileButton addFileButton;
    private MergeFileButton mergeButton;
    private SearchButton searchButton;
    private JSplitPane bottomPanel;
    private Component glassPane;
    private JPanel topPanel;
    
    private static final String DEFAULT_REGEX = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final String DATE_PATTERN = "DATE PATTERN";
    private static final String MIN_DATE_STR = "Min Date";
    private static final String MAX_DATE_STR = "Max Date";
    private static final String BLANK_STR    = "";
    private static final int TEXT_AREA_ROWS_CNT 	   = 5;
    private static final int TEXT_AREA_COLUMNS_CNT     = 70;
    private static final int TEXT_FIELD_COLUMNS_CNT    = 15;
    private static final int REGEX_PATTERN_COLUMN_CNT  = 30;
    private static final int FILENAME_FIELD_COLUMN_CNT = 60;
    
    private LogMergerWindow logMergerWindow;
    
    
    public MainWindowContainer(LogMergerWindow logMergerWindow) {
    	
    	this.logMergerWindow 	 = logMergerWindow;
    	
        clearUnOrderedTextButton = new ClearUnOrderedTextButton(this.logMergerWindow);
        searchButton			 = new SearchButton(this.logMergerWindow);
        addFileButton 			 = new AddFileButton(this.logMergerWindow);
        saveFileButton 			 = new SaveFileButton(this.logMergerWindow);
        mergeButton 		     = new MergeFileButton(this.logMergerWindow);
        glassPane 				 = this.logMergerWindow.getGlassPane();
        
        this.topPanel  = createTopPanel();
        this.bottomPanel = createBottomPanel();
	}
    
    public JSplitPane createBottomPanel(){
        JSplitPane bottomSplitPane;
        
        JPanel rightUpperPanel = new JPanel(new FlowLayout());
        isDescendingCheckBox = new DescendingCheckBox(this);
        rightUpperPanel.add(isDescendingCheckBox);
        rightUpperPanel.add(new JLabel(MIN_DATE_STR));
        rightUpperPanel.add(minDateField);
        rightUpperPanel.add(new JLabel(MAX_DATE_STR));
        rightUpperPanel.add(maxDateField);
        rightUpperPanel.add(saveFileButton);
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(rightUpperPanel, BorderLayout.NORTH);
        rightPanel.add(organizedScrollPane, BorderLayout.CENTER);
        
        JPanel patternAndClearPanel = new JPanel(new FlowLayout());
        patternAndClearPanel.add(this.clearUnOrderedTextButton);
        JLabel dateLabel = new JLabel(DATE_PATTERN);
        patternAndClearPanel.add(dateLabel);
        regexPatternTextField.setText(DEFAULT_REGEX);
        patternAndClearPanel.add(this.regexPatternTextField);
        patternAndClearPanel.add(mergeButton);
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(patternAndClearPanel, BorderLayout.NORTH);
        leftPanel.add(unOrganizedScrollPane, BorderLayout.CENTER);

        bottomSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        bottomSplitPane.setDividerLocation(configGetter.getWindowWidth()/2);

        return bottomSplitPane;
    }
    
    public JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        JPanel dateSection = new JPanel(new FlowLayout());
        dateSection.add(searchButton);
        dateSection.add(fileNameInputTextField);
        dateSection.add(addFileButton);
        dateSection.setBackground(Color.WHITE);

        topPanel.add(dateSection, BorderLayout.CENTER);
        
        return topPanel;
    }

    public JTextField getPatternTextField() {
        return regexPatternTextField;
    }

    public void setPatternTextField(JTextField patternTextField) {
    	patternTextField.setBackground(Color.decode("0xffffff"));
        this.regexPatternTextField = patternTextField;
    }

    public JTextArea getUnOrganizedText() {
        return unOrganizedText;
    }

    public void setUnOrganizedText(JTextArea unOrganizedText) {
        this.unOrganizedText = unOrganizedText;
    }

    public JTextArea getOrganizedText() {
        return organizedText;
    }

    public void setOrganizedText(JTextArea organizedText) {
        this.organizedText = organizedText;
    }

    public JTextField getMinDateField() {
        return minDateField;
    }

    public void setMinDateField(JTextField minDateField) {
        this.minDateField = minDateField;
    }

    public JTextField getMaxDateField() {
        return maxDateField;
    }

    public void setMaxDateField(JTextField maxDateField) {
        this.maxDateField = maxDateField;
    }

    public JTextField getFileNameInputTextField() {
    	return fileNameInputTextField;
    }

    public void setFileNameInputTextField(JTextField fileNameInput) {
    	this.fileNameInputTextField = fileNameInput;
    }

    public JScrollPane getUnOrganizedScrollPane() {
		return unOrganizedScrollPane;
	}

	public void setUnOrganizedScrollPane(JScrollPane unOrganizedScrollPane) {
		this.unOrganizedScrollPane = unOrganizedScrollPane;
	}

	public JScrollPane getOrganizedScrollPane() {
		return organizedScrollPane;
	}

	public void setOrganizedScrollPane(JScrollPane organizedScrollPane) {
		this.organizedScrollPane = organizedScrollPane;
	}

	public Component getGlassPane() {
		return glassPane;
	}

	public void setGlassPane(Component glassPane) {
		this.glassPane = glassPane;
	}

	public JCheckBox getIsDescendingCheckBox() {
		return isDescendingCheckBox;
	}

	public void setIsDescendingCheckBox(JCheckBox isDescendingCheckBox) {
		this.isDescendingCheckBox = isDescendingCheckBox;
	}

	public SaveFileButton getSaveFileButton() {
		return saveFileButton;
	}

	public void setSaveFileButton(SaveFileButton saveFileButton) {
		this.saveFileButton = saveFileButton;
	}

	public MergeFileButton getMergeButton() {
		return mergeButton;
	}

	public void setMergeButton(MergeFileButton mergeButton) {
		this.mergeButton = mergeButton;
	}

	public SearchButton getSearchButton() {
		return searchButton;
	}

	public void setSearchButton(SearchButton searchButton) {
		this.searchButton = searchButton;
	}

	public ClearUnOrderedTextButton getClearUnOrderedTextButton() {
		return clearUnOrderedTextButton;
	}

	public void setClearUnOrderedTextButton(ClearUnOrderedTextButton clearUnOrderedTextButton) {
		this.clearUnOrderedTextButton = clearUnOrderedTextButton;
	}

	public AddFileButton getAddFileButton() {
		return addFileButton;
	}

	public void setAddFileButton(AddFileButton addFileButton) {
		this.addFileButton = addFileButton;
	}

	public JSplitPane getBottomPanel() {
		return bottomPanel;
	}

	public void setBottomPanel(JSplitPane bottomPanel) {
		this.bottomPanel = bottomPanel;
	}

	public JPanel getTopPanel() {
		return topPanel;
	}

	public void setTopPanel(JPanel topPanel) {
		this.topPanel = topPanel;
	}
}
