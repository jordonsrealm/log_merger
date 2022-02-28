package logmerger.frame.component.swingworker.publish.action;

public enum PublishedAction {
	CREATING_DATE_LINES("Working on creating dated lines..."),
	DATE_BOUNDS("Removing any that are not in the date range..."),
	ORDERING("Now ordering dated lines..."),
	UPDATING_DATE_LINES("Now updating the LogMergerFrame with the dated lines..."),
	TO_FULL_TEXT("Now converting dated lines into a full text..."),
	FINISHED("Finished converting dated lines into full text...");
	
	private String action;
	
	PublishedAction(String action) {
		this.action = action;
	}
	
	public String action() {
		return action;
	}
}
