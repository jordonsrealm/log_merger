package mainwindow.components.holder;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import mainwindow.components.LineNumber;


public class TextHolder {
	
    private static final int TEXT_AREA_ROWS_CNT 	   = 5;
    private static final int TEXT_AREA_COLUMNS_CNT     = 70;
    private static final int TEXT_FIELD_COLUMNS_CNT    = 15;
    private static final int REGEX_PATTERN_COLUMN_CNT  = 30;
    private static final int FILENAME_FIELD_COLUMN_CNT = 60;
    private static final String BLANK_STR    		   = "";
    
    private JTextField fileNameInputTextField = new JTextField(FILENAME_FIELD_COLUMN_CNT);
    private JTextArea unOrderedText = new JTextArea(BLANK_STR,TEXT_AREA_ROWS_CNT,TEXT_AREA_COLUMNS_CNT);
    private JTextArea orderedText = new JTextArea(BLANK_STR,TEXT_AREA_ROWS_CNT,TEXT_AREA_COLUMNS_CNT);
    private JTextField regexPatternTextField = new JTextField(REGEX_PATTERN_COLUMN_CNT);
    private JScrollPane unOrderedScrollPane;
    private JScrollPane orderedScrollPane	= new JScrollPane(this.orderedText);
    private JTextField minDateField = new JTextField(TEXT_FIELD_COLUMNS_CNT);
    private JTextField maxDateField = new JTextField(TEXT_FIELD_COLUMNS_CNT);
    
    
    public TextHolder() {
    	JPanel panel = new JPanel(new BorderLayout());
		panel.add(new LineNumber(), BorderLayout.WEST);
		panel.add(this.unOrderedText, BorderLayout.CENTER);
		
    	unOrderedScrollPane = new JScrollPane(panel);
	}
    
	public JTextField getFileNameInputTextField() {
		return this.fileNameInputTextField;
	}
	public JTextArea getUnOrderedText() {
		return this.unOrderedText;
	}
	public JTextArea getOrderedText() {
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
	public JTextField getMinDateField() {
		return this.minDateField;
	}
	public JTextField getMaxDateField() {
		return this.maxDateField;
	}
}
