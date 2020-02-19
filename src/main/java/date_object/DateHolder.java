package date_object;

import transfer_object.DatedLine;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import listeners.MergeButtonListener;


public class DateHolder implements Comparable<DateHolder>{

	private static final Logger logger = LoggerFactory.getLogger(MergeButtonListener.class);
    public static String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private DatedLine dateResult;
    private static Boolean orderDescending;
    

    public DateHolder(String strToConvert, String format){
        this.dateResult = getDatedLineUsingPattern(strToConvert, format);
    }

    @Override
    public String toString() {
        return "DateHolder [dateObject=" + dateResult.toString() + "]";
    }

    @Override
    public int compareTo(DateHolder o) {
        if(this.getDateResult() == null || o.getDateResult() == null){
            return 0;
        }else{
            return this.dateResult.getEmbeddedDate().compareTo(o.getDateResult().getEmbeddedDate()) * (orderDescending?-1:1);
        }
    }

    public static DatedLine getDatedLineUsingPattern(String strToConvert, String format){
    	String replacedString = format.replace("dd", "\\d\\d")
    								  .replace("yyyy", "\\d\\d\\d\\d")
    								  .replace("MM", "\\d\\d")
    								  .replace("HH", "\\d\\d")
    								  .replace("mm", "\\d\\d")
    								  .replace("ss", "\\d\\d")
    								  .replace("SSS", "\\d\\d\\d")
    								  .replace(":", "\\:")
    								  .replace(".","\\.");
    	
    	Pattern pattern = Pattern.compile(replacedString);
    	Matcher matcher = pattern.matcher(strToConvert);
    	DatedLine datedLine = null;
    	
    	if(matcher.find()) {
            SimpleDateFormat formatter = new SimpleDateFormat(format);

            Date dateFromString = null;
            try {
                dateFromString = formatter.parse(strToConvert);
            } catch (ParseException e) {
            	logger.error("Unable to parse date from string");
            }
    		
    		datedLine = new DatedLine( dateFromString, strToConvert);
    	}
    		
        return datedLine;
    }
    
    public static Date getDateFromStringSupplied(String dateAsString, String format) {
    	
        SimpleDateFormat formatter = null;

        if(format.isEmpty()) {
        	formatter = new SimpleDateFormat(DEFAULT_FORMAT);
        } else {
        	formatter = new SimpleDateFormat(dateAsString);
        }
        
        Date dateFromString = null;
        try {
            dateFromString = formatter.parse(dateAsString);
        } catch (ParseException e) {
        	logger.error("Unable to parse date from string: {}", dateAsString);
        }
        
        return dateFromString;
    }

    public void appendToOriginalDateString(String appStr){
        String ogStr = this.dateResult.getOriginalStringWithDate();
        this.dateResult.setOriginalStringWithDate(ogStr + appStr);
    }
    
    public boolean isDateWithinBounds(Date minimumDate, Date maximumDate) {
    	boolean withinBounds = true;
    	
    	if(minimumDate!=null && maximumDate!=null) {
    		withinBounds = (getDate().after(minimumDate)) && (getDate().before(maximumDate));
    	} else {
        	if(minimumDate!=null) {
        		withinBounds = getDate().after(minimumDate);
        	} else {
        		if(maximumDate!=null) {
        			withinBounds = getDate().before(maximumDate);
        		}
        	}
    	}
    	
    	return withinBounds;
    }

    public DatedLine getDateResult() {
        return dateResult;
    }

    public Date getDate(){
        return dateResult.getEmbeddedDate();
    }

    public String getOrginalLine(){
        return dateResult.getOriginalStringWithDate();
    }
    
    public static Boolean isDescending() {
    	return orderDescending;
    }
    
    public static void setDescendingOrder(Boolean setDescending) {
    	DateHolder.orderDescending = setDescending;
    }
}
