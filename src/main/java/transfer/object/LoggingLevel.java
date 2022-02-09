package transfer.object;


public enum LoggingLevel {
	INFO("INFO"),
	DEBUG("DEBUG"),
	ERROR("ERROR"),
	TRACE("TRACE"),
	UNKNOWN("UNKNOWN");
	
	private String level;
	
	LoggingLevel(String level) {
		this.level = level;
	}
	
	public String level() {
		return level;
	}
	
	public static LoggingLevel getLevel(String possibleLevel) {
        LoggingLevel returnLevel;
        
        if(possibleLevel.contains("INFO")) {
        	returnLevel = INFO;
        } else if(possibleLevel.contains("DEBUG")){
        	returnLevel = DEBUG;
        } else if(possibleLevel.contains("ERROR")){
        	returnLevel = ERROR;
        } else if(possibleLevel.contains("TRACE")){
        	returnLevel = TRACE;
        } else {
        	returnLevel = UNKNOWN;
        }
		
		return returnLevel;
	}
}