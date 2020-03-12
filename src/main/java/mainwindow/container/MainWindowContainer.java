package mainwindow.container;

import javax.swing.*;

import mainwindow.components.AddFileButton;
import mainwindow.components.ClearUnOrderedTextButton;
import mainwindow.components.MergeFileButton;
import mainwindow.components.SaveFileButton;
import mainwindow.components.SearchButton;

import java.awt.Color;
import java.awt.Component;


public class MainWindowContainer {

    private JTextField fileNameInputTextField;
    private JScrollPane unOrganizedScrollPane;
    private JScrollPane organizedScrollPane;
    private JTextField patternTextField;
    private JTextArea unOrganizedText;
    private JTextArea organizedText;
    private JTextField minDateField;
    private JTextField maxDateField;
    private AddFileButton addFileButton;
    private SaveFileButton saveFileButton;
    private MergeFileButton mergeButton;
    private SearchButton searchButton;
    private ClearUnOrderedTextButton clearUnOrderedTextButton;
    private Component glassPane;
    private JCheckBox isDescendingCheckBox;
    

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
}
