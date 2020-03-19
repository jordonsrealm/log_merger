package mainwindow.components.holder;

import mainwindow.components.AddFileButton;
import mainwindow.components.ClearUnOrderedTextButton;
import mainwindow.components.LogMergerWindow;
import mainwindow.components.MergeFileButton;
import mainwindow.components.SaveFileButton;
import mainwindow.components.SearchButton;


public class ButtonHolder {
	
    private ClearUnOrderedTextButton clearUnOrderedTextButton;
    private SaveFileButton saveFileButton;
    private AddFileButton addFileButton;
    private MergeFileButton mergeButton;
    private SearchButton searchButton;
    
    
    public ButtonHolder(LogMergerWindow logMergerWindow) {
    	clearUnOrderedTextButton = new ClearUnOrderedTextButton(logMergerWindow);
		saveFileButton = new SaveFileButton(logMergerWindow);
		addFileButton = new AddFileButton(logMergerWindow);
		mergeButton = new MergeFileButton(logMergerWindow);
		searchButton = new SearchButton(logMergerWindow);
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
	public AddFileButton getAddFileButton() {
		return addFileButton;
	}
	public void setAddFileButton(AddFileButton addFileButton) {
		this.addFileButton = addFileButton;
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
}
