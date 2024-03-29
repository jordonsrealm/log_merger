package logmerger.frame.component.holder;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import logmerger.frame.LogMergerFrame;
import logmerger.frame.component.impl.LineNumberComponent;
import logmerger.frame.component.impl.PreviewScrollPane;
import logmerger.frame.component.listener.DateFieldListener;


public class TextHolder {
	
    private static final int TEXT_AREA_ROWS_CNT 	   = 5;
    private static final int TEXT_AREA_COLUMNS_CNT     = 70;
    private static final int TEXT_FIELD_COLUMNS_CNT    = 15;
    private static final int REGEX_PATTERN_COLUMN_CNT  = 16;
    private static final int FILENAME_FIELD_COLUMN_CNT = 60;
    private static final String BLANK_STR    		   = "";
    private static final String WHITE_BACKGROUND = "0xffffff";
    private static final String DEFAULT_REGEX_HINT = "yyyy-MM-dd HH:mm:ss.SSS";
    
    private JTextField fileNameInputTextField = new JTextField(FILENAME_FIELD_COLUMN_CNT);
    private JTextArea unOrderedText = new JTextArea(BLANK_STR,TEXT_AREA_ROWS_CNT,TEXT_AREA_COLUMNS_CNT);
    private JTextArea orderedText = new JTextArea(BLANK_STR,TEXT_AREA_ROWS_CNT,TEXT_AREA_COLUMNS_CNT);
    private JTextField regexPatternTextField = new JTextField(REGEX_PATTERN_COLUMN_CNT);
    private PreviewScrollPane unOrderedScrollPane;
    private JScrollPane orderedScrollPane	= new JScrollPane(this.orderedText);
    private JTextField minDateField = new JTextField(TEXT_FIELD_COLUMNS_CNT);
    private JTextField maxDateField = new JTextField(TEXT_FIELD_COLUMNS_CNT);
    private LogMergerFrame logMergerWindow;
    
    
    public TextHolder(LogMergerFrame logMergerWindow) {
    	this.setLogMergerWindow(logMergerWindow);
    	
    	JPanel leftPanel = new JPanel(new BorderLayout());
		leftPanel.add(new LineNumberComponent(logMergerWindow, false), BorderLayout.WEST);
		leftPanel.add(this.unOrderedText, BorderLayout.CENTER);
		
		setUnOrderedScrollPane(new PreviewScrollPane(leftPanel));
    	
    	JPanel rightPanel = new JPanel(new BorderLayout());
    	rightPanel.add(new LineNumberComponent(logMergerWindow, true), BorderLayout.WEST);
    	rightPanel.add(this.orderedText, BorderLayout.CENTER);
    	
    	setOrderedScrollPane(new PreviewScrollPane(rightPanel));
    	
        getRegexPatternTextField().setText(DEFAULT_REGEX_HINT);
        getRegexPatternTextField().setBackground(Color.decode(WHITE_BACKGROUND));
        
        DateFieldListener dateFieldListener = new DateFieldListener(getLogMergerWindow());
        
        minDateField.addActionListener(dateFieldListener);
        maxDateField.addActionListener(dateFieldListener);
	}
    
	public JTextField getFileNameInputTextField() {
		return this.fileNameInputTextField;
	}
	public JTextArea getUnOrderedText() {
		return this.unOrderedText;
	}
	public JTextArea getOrderedTextArea() {
		return this.orderedText;
	}
	public JTextField getRegexPatternTextField() {
		return this.regexPatternTextField;
	}
	public JScrollPane getUnOrderedScrollPane() {
		return this.unOrderedScrollPane;
	}
	public JScrollPane getOrderedScrollPane() {
		return this.orderedScrollPane;
	}
	public void setUnOrderedScrollPane(PreviewScrollPane unOrderedScrollPane) {
		this.unOrderedScrollPane = unOrderedScrollPane;
	}
	public void setOrderedScrollPane(JScrollPane orderedScrollPane) {
		this.orderedScrollPane = orderedScrollPane;
	}
	public JTextField getMinDateField() {
		return this.minDateField;
	}
	public JTextField getMaxDateField() {
		return this.maxDateField;
	}
	public LogMergerFrame getLogMergerWindow() {
		return logMergerWindow;
	}
	public void setLogMergerWindow(LogMergerFrame logMergerWindow) {
		this.logMergerWindow = logMergerWindow;
	}
}
