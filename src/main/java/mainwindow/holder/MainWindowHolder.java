package mainwindow.holder;

import javax.swing.*;

import configuration.ConfigurationGetter;
import mainwindow.components.DescendingCheckBox;
import mainwindow.components.LogMergerWindow;
import mainwindow.components.holder.ButtonHolder;
import mainwindow.components.holder.TextHolder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;


public class MainWindowHolder {

    private ConfigurationGetter configGetter = new ConfigurationGetter();
    
    private static final String DEFAULT_REGEX = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final String DATE_PATTERN = "DATE PATTERN";
    private static final String MIN_DATE_STR = "Min Date";
    private static final String MAX_DATE_STR = "Max Date";

    private JCheckBox isDescendingCheckBox = new DescendingCheckBox(this);
    private TextHolder txtHolder = new TextHolder();
    private ButtonHolder btnHolder;
    private Component glassPane;
    private JSplitPane bottomPanel;
    private JPanel topPanel;
    private LogMergerWindow logMergerWindow;
    
    
    public MainWindowHolder(LogMergerWindow logMergerWindow) {
    	
        this.glassPane 		 = logMergerWindow.getGlassPane();
    	this.logMergerWindow = logMergerWindow;
    	
    	this.btnHolder 		 = new ButtonHolder(logMergerWindow);

        this.topPanel        = createTopPanel();
        this.bottomPanel 	 = createBottomPanel();
	}
    
    public JSplitPane createBottomPanel(){
        JSplitPane bottomSplitPane;
        
        JPanel rightUpperPanel = new JPanel(new FlowLayout());
        this.isDescendingCheckBox = new DescendingCheckBox(this);
        rightUpperPanel.add(this.isDescendingCheckBox);
        rightUpperPanel.add(new JLabel(MIN_DATE_STR));
        rightUpperPanel.add(this.txtHolder.getMinDateField());
        rightUpperPanel.add(new JLabel(MAX_DATE_STR));
        rightUpperPanel.add(this.txtHolder.getMaxDateField());
        rightUpperPanel.add(this.btnHolder.getSaveFileButton());
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(rightUpperPanel, BorderLayout.NORTH);
        rightPanel.add(this.txtHolder.getOrganizedScrollPane(), BorderLayout.CENTER);
        
        JPanel patternAndClearPanel = new JPanel(new FlowLayout());
        patternAndClearPanel.add(this.btnHolder.getClearUnOrderedTextButton());
        JLabel dateLabel = new JLabel(DATE_PATTERN);
        patternAndClearPanel.add(dateLabel);
        this.txtHolder.getRegexPatternTextField().setText(DEFAULT_REGEX);
        this.txtHolder.getRegexPatternTextField().setBackground(Color.decode("0xffffff"));
        patternAndClearPanel.add(this.txtHolder.getRegexPatternTextField());
        patternAndClearPanel.add(this.btnHolder.getMergeButton());
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(patternAndClearPanel, BorderLayout.NORTH);
        leftPanel.add(this.txtHolder.getUnOrganizedScrollPane(), BorderLayout.CENTER);

        bottomSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        bottomSplitPane.setDividerLocation(this.configGetter.getWindowWidth()/2);

        return bottomSplitPane;
    }
    
    public JPanel createTopPanel() {
        JPanel topMostPanel = new JPanel();
        topMostPanel.setLayout(new BorderLayout());

        JPanel dateSection = new JPanel(new FlowLayout());
        dateSection.add(this.btnHolder.getSearchButton());
        dateSection.add(this.txtHolder.getFileNameInputTextField());
        dateSection.add(this.btnHolder.getAddFileButton());
        dateSection.setBackground(Color.WHITE);

        topMostPanel.add(dateSection, BorderLayout.CENTER);
        
        return topMostPanel;
    }

	public ConfigurationGetter getConfigGetter() {
		return this.configGetter;
	}

	public void setConfigGetter(ConfigurationGetter configGetter) {
		this.configGetter = configGetter;
	}

	public JCheckBox getIsDescendingCheckBox() {
		return this.isDescendingCheckBox;
	}

	public void setIsDescendingCheckBox(JCheckBox isDescendingCheckBox) {
		this.isDescendingCheckBox = isDescendingCheckBox;
	}

	public Component getGlassPane() {
		return this.glassPane;
	}

	public void setGlassPane(Component glassPane) {
		this.glassPane = glassPane;
	}

	public JSplitPane getBottomPanel() {
		return this.bottomPanel;
	}

	public void setBottomPanel(JSplitPane bottomPanel) {
		this.bottomPanel = bottomPanel;
	}

	public JPanel getTopPanel() {
		return this.topPanel;
	}

	public void setTopPanel(JPanel topPanel) {
		this.topPanel = topPanel;
	}

	public LogMergerWindow getLogMergerWindow() {
		return this.logMergerWindow;
	}

	public void setLogMergerWindow(LogMergerWindow logMergerWindow) {
		this.logMergerWindow = logMergerWindow;
	}

	public ButtonHolder getBtnHolder() {
		return this.btnHolder;
	}

	public TextHolder getTxtHolder() {
		return txtHolder;
	}

}
