package window.holder;

import javax.swing.*;

import datedline.DatedLine;
import window.components.LogMergerWindow;
import window.components.holder.ButtonHolder;
import window.components.holder.CheckBoxHolder;
import window.components.holder.TextHolder;

import java.awt.Component;
import java.util.List;


public class WindowComponentHolder {
    
    private TextHolder txtHolder;
    private ButtonHolder btnHolder;
    private CheckBoxHolder checkBoxHolder;
    
    private JSplitPane mergingSplitPane;
    private LogMergerWindow logMergerWindow;
    
    private List<DatedLine> datedLines;
    
    
    public WindowComponentHolder(LogMergerWindow logMergerWindow) {
    	this.logMergerWindow = logMergerWindow;
    	this.btnHolder = new ButtonHolder(logMergerWindow);
    	this.checkBoxHolder = new CheckBoxHolder(logMergerWindow);
    	this.txtHolder = new TextHolder(logMergerWindow);
	}

	public Component getGlassPane() {
		return this.logMergerWindow.getGlassPane();
	}

	public JSplitPane getMergingSplitPane() {
		return this.mergingSplitPane;
	}

	public void setMergingSplitPane(JSplitPane bottomPanel) {
		this.mergingSplitPane = bottomPanel;
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
		return this.txtHolder;
	}
	
	public CheckBoxHolder getCheckBoxHolder() {
		return this.checkBoxHolder;
	}
	
	public boolean isDescending() {
		return getCheckBoxHolder().getDescendingCheckBox().isSelected();
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
