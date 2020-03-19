package mainwindow.components.holder;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TextHolder {
	
    private static final int TEXT_AREA_ROWS_CNT 	   = 5;
    private static final int TEXT_AREA_COLUMNS_CNT     = 70;
    private static final int TEXT_FIELD_COLUMNS_CNT    = 15;
    private static final int REGEX_PATTERN_COLUMN_CNT  = 30;
    private static final int FILENAME_FIELD_COLUMN_CNT = 60;
    private static final String BLANK_STR    = "";
    
    private JTextField fileNameInputTextField = new JTextField(FILENAME_FIELD_COLUMN_CNT);
    private JTextArea unOrganizedText = new JTextArea(BLANK_STR,TEXT_AREA_ROWS_CNT,TEXT_AREA_COLUMNS_CNT);
    private JTextArea organizedText = new JTextArea(BLANK_STR,TEXT_AREA_ROWS_CNT,TEXT_AREA_COLUMNS_CNT);
    private JTextField regexPatternTextField = new JTextField(REGEX_PATTERN_COLUMN_CNT);
    private JScrollPane unOrganizedScrollPane = new JScrollPane(this.unOrganizedText);
    private JScrollPane organizedScrollPane	= new JScrollPane(this.organizedText);
    private JTextField minDateField = new JTextField(TEXT_FIELD_COLUMNS_CNT);
    private JTextField maxDateField = new JTextField(TEXT_FIELD_COLUMNS_CNT);
    
	public JTextField getFileNameInputTextField() {
		return fileNameInputTextField;
	}
	public void setFileNameInputTextField(JTextField fileNameInputTextField) {
		this.fileNameInputTextField = fileNameInputTextField;
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
	public JTextField getRegexPatternTextField() {
		return regexPatternTextField;
	}
	public void setRegexPatternTextField(JTextField regexPatternTextField) {
		this.regexPatternTextField = regexPatternTextField;
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
    
    
}
