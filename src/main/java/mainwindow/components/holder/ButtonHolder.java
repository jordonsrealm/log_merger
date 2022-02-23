package mainwindow.components.holder;

import mainwindow.components.ClearUnOrderedTextButton;
import mainwindow.components.LogMergerWindow;
import mainwindow.components.SortFileButton;
import mainwindow.components.SaveFileButton;


public class ButtonHolder {
	
    private ClearUnOrderedTextButton clearUnOrderedTextButton;
    private SaveFileButton saveFileButton;
    private SortFileButton mergeButton;
    
    
    public ButtonHolder(LogMergerWindow logMergerWindow) {
    	clearUnOrderedTextButton = new ClearUnOrderedTextButton(logMergerWindow);
		saveFileButton = new SaveFileButton(logMergerWindow);
		mergeButton = new SortFileButton(logMergerWindow);
	}
    
	public ClearUnOrderedTextButton getClearUnOrderedTextButton() {
		return clearUnOrderedTextButton;
	}
	public void setClearUnOrderedTextButton(ClearUnOrderedTextButton clearUnOrderedTextButton) {
		this.clearUnOrderedTextButton = clearUnOrderedTextButton;
	}
	public SaveFileButton getSaveFileButton() {
		return saveFileButton;
	}
	public void setSaveFileButton(SaveFileButton saveFileButton) {
		this.saveFileButton = saveFileButton;
	}
	public SortFileButton getMergeButton() {
		return mergeButton;
	}
	public void setMergeButton(SortFileButton mergeButton) {
		this.mergeButton = mergeButton;
	}
}
