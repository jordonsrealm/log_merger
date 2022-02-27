package window.component.holder;

import logmerger.frame.LogMergerFrame;
import window.component.ClearUnOrderedTextButton;
import window.component.SaveFileButton;
import window.component.SortFileButton;


public class ButtonHolder {
	
    private ClearUnOrderedTextButton clearUnOrderedTextButton;
    private SaveFileButton saveFileButton;
    private SortFileButton mergeButton;
    
    
    public ButtonHolder(LogMergerFrame logMergerWindow) {
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
