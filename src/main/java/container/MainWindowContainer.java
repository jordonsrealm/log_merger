package container;

import listeners.MergeButtonListener;
import components.OrderedTextArea;
import javax.swing.*;
import java.awt.Color;
import java.awt.Component;


public class MainWindowContainer {

    private JTextField fileNameInputTextField;
    private JScrollPane unOrganizedScrollPane;
    private JScrollPane organizedScrollPane;
    private JButton clearUnorganizedText;
    private JTextField patternTextField;
    private JSplitPane bottomSplitPane;
    private JTextArea unOrganizedText;
    private OrderedTextArea organizedText;
    private JTextField minDateField;
    private JTextField maxDateField;
    private JButton useFileBtn;
    private JButton inputFileBtn;
    private JButton mergeBtn;
    private JButton saveToFile;
    private JPanel topPanel;
    private Component glassPane;
    

    public JButton getMergeBtn() {
        return mergeBtn;
    }

    public void setMergeBtn(JButton mergeBtn) {
        this.mergeBtn = mergeBtn;
    }

    public JTextField getPatternTextField() {
        return patternTextField;
    }

    public void setPatternTextField(JTextField patternTextField) {
    	patternTextField.setBackground(Color.decode("0xffffff"));
        this.patternTextField = patternTextField;
    }

    public JTextArea getUnOrganizedText() {
        return unOrganizedText;
    }

    public void setUnOrganizedText(JTextArea unOrganizedText) {
        this.unOrganizedText = unOrganizedText;
    }

    public OrderedTextArea getOrganizedText() {
        return organizedText;
    }

    public void setOrganizedText(OrderedTextArea organizedText) {
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

    public void setMergeButtonListener(MergeButtonListener listener){
    	this.mergeBtn.addActionListener(listener);
    }

    public JTextField getFileNameInputTextField() {
    	return fileNameInputTextField;
    }

    public void setFileNameInputTextField(JTextField fileNameInput) {
    	this.fileNameInputTextField = fileNameInput;
    }

    public JButton getUseFileBtn() {
    	return useFileBtn;
    }

    public JButton getInputFileBtn() {
    	return inputFileBtn;
    }

    public void setInputFileBtn(JButton inputFileBtn) {
    	this.inputFileBtn = inputFileBtn;
    }

    public JButton getClearUnorganizedText() {
        return clearUnorganizedText;
    }

    public JButton getSaveToFile() {
        return saveToFile;
    }

    public void setSaveToFile(JButton saveToFile) {
        this.saveToFile = saveToFile;
    }

    public void setClearUnorganizedText(JButton clearUnorganizedText) { 
    	this.clearUnorganizedText = clearUnorganizedText; 
    }

    public void setUseFileBtn(JButton selectFileBtn) {
        this.useFileBtn = selectFileBtn;
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

	public JButton getFileInputButton() { 
		return inputFileBtn; 
	}

    public void setFileInputButton(JButton fileInput) {
        this.inputFileBtn = fileInput;
    }

	public void setTopPanel(JPanel topPanel) {
		this.topPanel = topPanel;
	}
	
	public JPanel getTopPanel() {
		return this.topPanel;
	}
	
	public JSplitPane getBottomSplitPane() {
		return this.bottomSplitPane;
	}
	
	public void setBottomSplitPane(JSplitPane splitPane) {
		this.bottomSplitPane = splitPane;
	}

	public Component getGlassPane() {
		return glassPane;
	}

	public void setGlassPane(Component glassPane) {
		this.glassPane = glassPane;
	}
}
