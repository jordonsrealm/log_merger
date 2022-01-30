package mainwindow.components.holder;

import mainwindow.components.DescendingCheckBox;
import mainwindow.components.LogMergerWindow;


public class CheckBoxHolder {

	private DescendingCheckBox descendingCheckBox;
	private LogMergerWindow logMergerWindow;
	
	public CheckBoxHolder(LogMergerWindow logMergerWindow) {
		this.logMergerWindow = logMergerWindow;
	}

	public DescendingCheckBox getDescendingCheckBox() {
		if(descendingCheckBox == null) {
			descendingCheckBox = new DescendingCheckBox(logMergerWindow);
		}
		return descendingCheckBox;
	}

	public void setDescendingCheckBox(DescendingCheckBox descendingCheckBox) {
		this.descendingCheckBox = descendingCheckBox;
	}
}