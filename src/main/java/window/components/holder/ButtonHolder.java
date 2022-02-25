package window.components.holder;

import window.components.ClearUnOrderedTextButton;
import window.components.LogMergerWindow;
import window.components.SaveFileButton;
import window.components.SortFileButton;


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
