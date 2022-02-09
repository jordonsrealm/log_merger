package transfer.object;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DatedLine implements Comparable<DatedLine> {

	private static final Logger logger = LoggerFactory.getLogger(DatedLine.class);
    private static final String REPLACE_SEQ = "\\d\\d";
    private String originalStringWithDate;
    private String dateFormat;
    private static boolean orderDescending;
    private LoggingLevel logLevel;


    public DatedLine(){
    	this( null, null, LoggingLevel.UNKNOWN);
    }
    
    public DatedLine(String originalString, String dateFormat) {
    	this(originalString, dateFormat, LoggingLevel.UNKNOWN);
    }
    
    public DatedLine(String originalString, String dateFormat, LoggingLevel level) {
    	setOriginalStringWithDate(originalString);
    	setDateFormat(dateFormat);
    	setLogLevel(level);
    }
    
    public static void setOrderDescending(boolean descending) {
    	orderDescending = descending;
    }

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

    public LoggingLevel getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(LoggingLevel logLevel) {
		this.logLevel = logLevel;
	}

	public String getOriginalStringWithDate() {
        return originalStringWithDate;
    }

    public void setOriginalStringWithDate(String originalStringWithDate) {
        this.originalStringWithDate = originalStringWithDate;
    }

    public boolean isValidDate(){
        return getDate() != null;
    }

    public Date getDate() {
    	Date date = null;
    	
    	String formattedDate = getFormattedDate(this.originalStringWithDate, this.dateFormat);
    	
    	if(formattedDate == null) {
    		return date;
    	}
    	
        try {
			date = new SimpleDateFormat(dateFormat).parse(formattedDate);
		} catch (ParseException e) {
			logger.debug("Unable to parse date: {} from format: {}", formattedDate, this.dateFormat);
		}
        
        return date;
    }
	
	public String getDateAsString() {
		 return getFormattedDate(this.originalStringWithDate, this.dateFormat);
	}

	public void appendToOriginalString(String stringToAppend) {
    	this.originalStringWithDate += stringToAppend;
    }
    
	protected String getFormattedDate(String strToConvert, String format) {
    	String formattedString = format.replace("dd", REPLACE_SEQ)
    								  .replace("yyyy", REPLACE_SEQ + REPLACE_SEQ)
    								  .replace("MM", REPLACE_SEQ)
    								  .replace("HH", REPLACE_SEQ)
    								  .replace("mm", REPLACE_SEQ)
    								  .replace("ss", REPLACE_SEQ)
    								  .replace("SSS", "\\d\\d\\d")
    								  .replace(":", "\\:")
    								  .replace(".","\\.");
    	
    	Matcher matcher = Pattern.compile(formattedString).matcher(strToConvert);
    	String dateFromString = null;
    	
    	if(matcher.find()) {
    		dateFromString = strToConvert.substring(matcher.start(), matcher.end());
    	}
    		
        return dateFromString;
    }
    
    public boolean isWithinBounds(Date minimumDate, Date maximumDate) {
    	boolean withinBounds = true;
    	
    	if(minimumDate!=null && maximumDate!=null) {
    		withinBounds = (getDate().after(minimumDate) || getDate().equals(minimumDate)) && 
    						(getDate().before(maximumDate) || getDate().equals(maximumDate));
    	} else {
        	if(minimumDate != null) {
        		withinBounds = getDate().after(minimumDate) || getDate().equals(minimumDate);
        	} else {
        		if(maximumDate != null) {
        			withinBounds = getDate().before(maximumDate) || getDate().equals(maximumDate);
        		}
        	}
    	}
    	
    	return withinBounds;
    }

    @Override
    public int compareTo(DatedLine datedLine) {
        if(getDate() == null || datedLine.getDate() == null){
            return 0;
        }else{
            return getDate().compareTo(datedLine.getDate()) * (orderDescending?-1:1);
        }
    }
    
    @Override
    public boolean equals(Object obj) {
    	
    	if (obj == null) return false;
    	if (obj == this) return true;
    	
    	if (!(obj instanceof DatedLine)) return false;
    	
    	DatedLine datedLine = (DatedLine) obj;
		return getDate().equals(datedLine.getDate()) && 
			   getOriginalStringWithDate().equals(datedLine.getOriginalStringWithDate()) &&
			   getDateFormat().equals(datedLine.getDateFormat());
    }
    
    @Override
	public int hashCode() {
		return super.hashCode();
	}
}
