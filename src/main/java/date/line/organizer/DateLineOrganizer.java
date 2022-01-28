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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import transfer.object.DatedLine;


public class DateLineOrganizer {
	
	private static final Logger logger = LoggerFactory.getLogger(DateLineOrganizer.class);
	private String notSortedString;
	private String currentDateFormat;
    private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private boolean descendingOrder = false;
	
	
	public DateLineOrganizer(String strToOrder, String currentDateFormat, boolean descending) {
		setNotSortedString(strToOrder);
		setCurrentDateFormat(currentDateFormat);
		setDescendingOrder(descending);
	}

    public String orderDateLines(String minDateString, String maxDateString) {
    	
    	List<DatedLine> sortedDatedLines = getDatedLinesUsingFormat(getCurrentDateFormat());
        
    	sortedDatedLines.removeIf(datedLine -> !datedLine.isWithinBounds(getDateFromFormat(minDateString), getDateFromFormat(maxDateString)));
    	
    	return returnCompleteTextFromDatedLines(sortedDatedLines);
    }
    
    protected List<DatedLine> getDatedLinesUsingFormat(String format) {
    	DatedLine.setOrderDescending(isDescendingOrder());
        
        ArrayList<DatedLine> datedLineList = new ArrayList<>();
        String lineRead;
        
        try (BufferedReader bufferedReader = new BufferedReader(new StringReader(getNotSortedString()))) {
            while((lineRead = bufferedReader.readLine()) != null){
            	DatedLine datedLine = new DatedLine(lineRead, format);
            			
                if(datedLine.isValidDate()){
                    datedLineList.add(datedLine);
                }
            }
        } catch (IOException ex) {
            logger.error("Unable to read lines of text: {}", ex.getStackTrace());
        }
        
        return datedLineList;
    }
    
    protected String returnCompleteTextFromDatedLines(List<DatedLine> datedLines) {
    	Collections.sort(datedLines);
    	StringBuilder builder = new StringBuilder();
    	
        for(DatedLine holder: datedLines){
            builder.append(holder.getOriginalStringWithDate() + "\n");
        }
        
        return builder.toString();
    }
    
    protected Date getDateFromFormat(String dateAsString) {
    	
    	if(dateAsString == null || dateAsString.isBlank()) {
    		return null;
    	}
    	
        SimpleDateFormat formatter = currentDateFormat.isEmpty() ? new SimpleDateFormat(DEFAULT_FORMAT) : new SimpleDateFormat(currentDateFormat);
        
        Date dateFromString = null;
        try {
            dateFromString = formatter.parse(dateAsString);
        } catch (ParseException e) {
        	logger.error("Unable to parse date from string: {}, format: {}", dateAsString, currentDateFormat);
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
