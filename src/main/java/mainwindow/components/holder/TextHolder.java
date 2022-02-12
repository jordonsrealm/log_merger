package mainwindow.components.holder;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.AbstractDocument;
import mainwindow.components.LineNumberComponent;
import mainwindow.components.LogMergerWindow;
import mainwindow.components.PreviewScrollPane;


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
    private LogMergerWindow logMergerWindow;
    
    
    public TextHolder(LogMergerWindow logMergerWindow) {
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
        
        DocumentFilter filter = new ExpandingDocumentFilter();
        AbstractDocument firstNameDoc = (AbstractDocument) minDateField.getDocument();
        firstNameDoc.setDocumentFilter(filter);
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
	public LogMergerWindow getLogMergerWindow() {
		return logMergerWindow;
	}
	public void setLogMergerWindow(LogMergerWindow logMergerWindow) {
		this.logMergerWindow = logMergerWindow;
	}
	
    class ExpandingDocumentFilter extends DocumentFilter {

        @Override
        public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
            System.out.println("I" + text);
            super.insertString(fb, offset, text, attr);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if ("e".equalsIgnoreCase(text)) {
                text = "example";
            }
            super.replace(fb, offset, length, text, attrs); 
        }

    }
    
    class UppercaseDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr)
            throws BadLocationException {
            fb.insertString(offset, text.toUpperCase(), attr);
        }

        @Override
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {
            fb.replace(offset, length, text.toUpperCase(), attrs);
        }
    }
}
