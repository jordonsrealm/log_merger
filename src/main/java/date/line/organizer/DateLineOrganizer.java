package date.line.organizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import transfer.object.DatedLine;


public class DateLineOrganizer {
	
	private static final Logger logger = LoggerFactory.getLogger(DateLineOrganizer.class);
	private String notSortedString;
	private String currentDateFormat;
    private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final String REPLACE_SEQ = "\\d\\d";
    private boolean descendingOrder = false;
	
	
	public DateLineOrganizer(String strToOrder, String currentDateFormat, boolean descending) {
		setNotSortedString(strToOrder);
		setCurrentDateFormat(currentDateFormat);
		setDescendingOrder(descending);
	}

    public String orderDateLines(String minDateString, String maxDateString) {
    	
    	List<DatedLine> sortedDatedLines = getDatedLinesUsingFormat(getCurrentDateFormat());
    	
    	Collections.sort(sortedDatedLines);
    	
    	List<DatedLine> boundedDatedLines = getBoundedDatedLines(sortedDatedLines, minDateString, maxDateString);
    	
    	return returnCompleteText(boundedDatedLines);
    }
    
    protected String returnCompleteText(List<DatedLine> datedLines) {
    	
    	StringBuilder builder = new StringBuilder();
    	String appendingStr = "";
    	
        for(DatedLine holder: datedLines){
            appendingStr = holder.getOriginalStringWithDate() + "\n";
            builder.append(appendingStr);
        }
        
        return builder.toString();
    }
    
    protected List<DatedLine> getDatedLinesUsingFormat(String format){
    	logger.info("Ordering dated lines {} order", (isDescendingOrder() ? "in descending" : "in ascending"));
    	
        DatedLine.setOrderDescending(isDescendingOrder());
        
    	BufferedReader bufferedReader = new BufferedReader(new StringReader(getNotSortedString()));
        ArrayList<DatedLine> datedLineList = new ArrayList<>();
        Date validDate = null;
        String lineRead;
        
        try {
            while((lineRead = bufferedReader.readLine()) != null){
            	
            	validDate = isValidDate( lineRead, format);
            	
                if(validDate != null){
                    datedLineList.add(new DatedLine(lineRead, validDate));
                } else{
                	if(!datedLineList.isEmpty()) {
                		datedLineList.get(datedLineList.size() - 1).appendToOriginalString(lineRead);
                	}
                }
            }
        } catch (IOException ex) {
            logger.error("Unable to read lines of text: {}", ex.getStackTrace());
        }
        
        return datedLineList;
    }
	
	protected Date isValidDate(String strToConvert, String format){
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
    	Date dateFromString = null;
    	
    	if(matcher.find()) {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            
            try {
                dateFromString = formatter.parse(strToConvert);
            } catch (ParseException ex) {
            	logger.error("Unable to parse date from string: {}", ex.getStackTrace());
            }
    	}
    		
        return dateFromString;
    }
    
    protected List<DatedLine> getBoundedDatedLines(List<DatedLine> dateLines, String minDateString, String maxDateString) {
    	
    	if(!(minDateString.isEmpty() && maxDateString.isEmpty())) {
    		logger.info("Working on boundary dates - date1: {}, date2: {}", minDateString, maxDateString);
    		
        	Date minimumDate = getDateFromStringSupplied(minDateString, currentDateFormat);
            Date maximumDate = getDateFromStringSupplied(maxDateString, currentDateFormat);

            DatedLine holder;
            
            for(int index = dateLines.size() - 1; index > -1; index--){

            	holder = dateLines.get(index);
            	
                if(!holder.isDateWithinBounds(minimumDate, maximumDate)){
                	dateLines.remove(index);
                }
            }
    	}
    	
    	return dateLines;
    }
    
    protected Date getDateFromStringSupplied(String dateAsString, String format) {
    	
    	if(dateAsString == null || dateAsString.isBlank()) {
    		return null;
    	}
    	
        SimpleDateFormat formatter = format.isEmpty() ? new SimpleDateFormat(DEFAULT_FORMAT) : new SimpleDateFormat(format);
        
        Date dateFromString = null;
        try {
            dateFromString = formatter.parse(dateAsString);
            logger.debug("dateFromString: {}, format: {}", dateFromString, format);
        } catch (ParseException e) {
        	logger.error("Unable to parse date from string: {}, format: {}", dateAsString, format);
        }
        
        return dateFromString;
    }

    protected void setNotSortedString(String entireNotSortedString) {
		this.notSortedString = entireNotSortedString;
	}
    
    protected String getNotSortedString() {
		return this.notSortedString;
	}

    protected String getCurrentDateFormat() {
		return this.currentDateFormat;
	}

    protected void setCurrentDateFormat(String currentDateFormat) {
		this.currentDateFormat = currentDateFormat;
	}

    protected boolean isDescendingOrder() {
		return this.descendingOrder;
	}

    protected void setDescendingOrder(boolean descendingOrder) {
		this.descendingOrder = descendingOrder;
	}
}
