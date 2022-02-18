package mainwindow.components.holder;

import mainwindow.components.ListeningCheckBox;
import mainwindow.components.LogMergerWindow;
import transfer.object.LoggingLevel;


public class CheckBoxHolder {

	private ListeningCheckBox traceCheckBox;
	private ListeningCheckBox errorCheckBox;
	private ListeningCheckBox debugCheckBox;
	private ListeningCheckBox unknownCheckBox;
	private ListeningCheckBox infoCheckBox;
	private ListeningCheckBox warnCheckBox;
	private ListeningCheckBox descendingCheckBox;
	private LogMergerWindow logMergerWindow;
	private static final String DESCENDING_TEXT = "Descending";
	
	
	public CheckBoxHolder(LogMergerWindow logMergerWindow) {
		this.logMergerWindow = logMergerWindow;
	}

	public ListeningCheckBox getDescendingCheckBox() {
		if(descendingCheckBox == null) {
			descendingCheckBox = new ListeningCheckBox(logMergerWindow, DESCENDING_TEXT);
		}
		return descendingCheckBox;
	}

	public ListeningCheckBox getTraceCheckBox() {
		if(traceCheckBox == null) {
			traceCheckBox = new ListeningCheckBox(logMergerWindow, LoggingLevel.TRACE.name());
			traceCheckBox.setSelected(true);
		}
		return traceCheckBox;
	}
	
	public ListeningCheckBox getErrorCheckBox() {
		if(errorCheckBox == null) {
			errorCheckBox = new ListeningCheckBox(logMergerWindow, LoggingLevel.ERROR.name());
			errorCheckBox.setSelected(true);
		}
		return errorCheckBox;
	}

	public ListeningCheckBox getDebugCheckBox() {
		if(debugCheckBox == null) {
			debugCheckBox = new ListeningCheckBox(logMergerWindow, LoggingLevel.DEBUG.name());
			debugCheckBox.setSelected(true);
		}
		return debugCheckBox;
	}

	public ListeningCheckBox getUnknownCheckBox() {
		if(unknownCheckBox == null) {
			unknownCheckBox = new ListeningCheckBox(logMergerWindow, LoggingLevel.UNKNOWN.name());
			unknownCheckBox.setSelected(true);
		}
		return unknownCheckBox;
	}

	public ListeningCheckBox getInfoCheckBox() {
		if(infoCheckBox == null) {
			infoCheckBox = new ListeningCheckBox(logMergerWindow, LoggingLevel.INFO.name());
			infoCheckBox.setSelected(true);
		}
		return infoCheckBox;
	}

	public ListeningCheckBox getWarnCheckBox() {
		if(warnCheckBox == null) {
			warnCheckBox = new ListeningCheckBox(logMergerWindow, LoggingLevel.WARN.name());
			warnCheckBox.setSelected(true);
		}
		return warnCheckBox;
	}
}