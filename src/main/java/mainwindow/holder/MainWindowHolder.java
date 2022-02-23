package mainwindow.holder;

import javax.swing.*;

import mainwindow.components.LogMergerWindow;
import mainwindow.components.holder.ButtonHolder;
import mainwindow.components.holder.CheckBoxHolder;
import mainwindow.components.holder.TextHolder;
import transfer.object.DatedLine;

import java.awt.Component;
import java.util.List;


public class MainWindowHolder {
    
    private TextHolder txtHolder;
    private ButtonHolder btnHolder;
    private CheckBoxHolder checkBoxHolder;
    
    private Component glassPane;
    private JPanel topPanel;
    private JSplitPane bottomPanel;
    private LogMergerWindow logMergerWindow;
    
    private List<DatedLine> datedLines;
    
    
    public MainWindowHolder(LogMergerWindow logMergerWindow) {
    	this.logMergerWindow = logMergerWindow;
        this.glassPane = logMergerWindow.getGlassPane();
    	this.btnHolder = new ButtonHolder(logMergerWindow);
    	this.checkBoxHolder = new CheckBoxHolder(logMergerWindow);
    	this.txtHolder = new TextHolder(logMergerWindow);
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
	
	public CheckBoxHolder getCheckBoxHolder() {
		return checkBoxHolder;
	}
	
	public boolean isDescending() {
		return checkBoxHolder.getDescendingCheckBox().isSelected();
	}
	
	public String getRegexPatternText() {
		return getTxtHolder().getRegexPatternTextField().getText();
	}

	public void setUnorderedText(String text) {
		getTxtHolder().getUnOrderedText().setText(text);
	}
	
	public String getUnorderedText() {
		return getTxtHolder().getUnOrderedText().getText();
	}
	
	public void setOrderedText(String newText) {
		getTxtHolder().getOrderedTextArea().setText(newText);
	}
	
	public String getOrderedText() {
		return getTxtHolder().getOrderedTextArea().getText();
	}
	
	public void setFileNameInputText(String newText) {
		getTxtHolder().getFileNameInputTextField().setText(newText);
	}
	
	public String getFileNameInputText() {
		return getTxtHolder().getFileNameInputTextField().getText();
	}
	
	public void setUnOrderedHorizontalScrollBar(int newVal) {
		getTxtHolder().getUnOrderedScrollPane().getHorizontalScrollBar().setValue(newVal);
	}
	
	public String getMinDateText() {
		return getTxtHolder().getMinDateField().getText();
	}
	
	public String getMaxDateText() {
		return getTxtHolder().getMaxDateField().getText();
	}

	public List<DatedLine> getDatedLines() {
		return datedLines;
	}

	public void setDatedLines(List<DatedLine> datedLines) {
		this.datedLines = datedLines;
	}
}
