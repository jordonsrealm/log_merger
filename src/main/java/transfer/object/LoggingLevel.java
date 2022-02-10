package transfer.object;

import java.awt.Color;

public enum LoggingLevel {
	INFO("INFO"),
	DEBUG("DEBUG"),
	ERROR("ERROR"),
	TRACE("TRACE"),
	WARN("WARN"),
	UNKNOWN("UNKNOWN");
	
	private String level;
	
	LoggingLevel(String level) {
		this.level = level;
	}
	
	public String level() {
		return level;
	}
	
	public Color getLevelColor() {
		Color color = null;
		switch(this) {
			case ERROR:
				color = Color.decode("0xff6f68");
				break;
			case INFO:
				color = Color.decode("0xb6d7a8");
				break;
			case DEBUG:
				color = Color.decode("0xabc5fe");
				break;
			case TRACE:
				color = Color.decode("0xc0c0c0");
				break;
			case WARN:
				color = Color.decode("0xfeff8f");
				break;
			case UNKNOWN:
				color = Color.decode("0x494949");
				break;
		}
		
		return color;
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
        } else if(possibleLevel.contains("WARN")){
        	returnLevel = WARN;
        } else {
        	returnLevel = UNKNOWN;
        }
		
		return returnLevel;
	}
}