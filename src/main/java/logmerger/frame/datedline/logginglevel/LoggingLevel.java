package logmerger.frame.datedline.logginglevel;

import java.awt.Color;

public enum LoggingLevel {
	ERROR("ERROR", "0xff6f68"),
	INFO("INFO", "0xb6d7a8"),
	DEBUG("DEBUG", "0xabc5fe"),
	TRACE("TRACE", "0xc0c0c0"),
	WARN("WARN", "0xfeff8f"),
	UNKNOWN("UNKNOWN", "0xe9e9e9");
	
	private String level;
	private String color;
	
	LoggingLevel(String level, String color) {
		this.level = level;
		this.color = color;
	}
	
	public String level() {
		return level;
	}
	
	public Color getLevelColor() {
		return Color.decode(this.color);
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