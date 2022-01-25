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

import mainwindow.holder.MainWindowHolder;
import transfer.object.DatedLine;


public class DateLineOrganizer {
	
	private static final Logger logger = LoggerFactory.getLogger(DateLineOrganizer.class);
	private List<DatedLine> contentsAsDateLines = new ArrayList<>();
	private MainWindowHolder windowHolder;
    private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final String REPLACE_SEQ = "\\d\\d";
    private boolean descendingOrder = false;
	
	
	public DateLineOrganizer(MainWindowHolder mainWindowContainer) {
		this.windowHolder = mainWindowContainer;
		this.descendingOrder = windowHolder.getCheckBoxHolder().getDescendingCheckBox().isSelected();
	}

    public DateLineOrganizer orderDateLines() {
    	String dateFormat = windowHolder.getRegexPatternText();
    	
    	sortLinesUsingFormat(dateFormat);
    	
    	Collections.sort(this.contentsAsDateLines);
    	
    	return this;
    }
    
    private void sortLinesUsingFormat(String format){
    	logger.info("Ordering Date Lines");
    	
    	BufferedReader bufferedReader = new BufferedReader(new StringReader(windowHolder.getUnorderedText()));
        
        String lineRead;
        ArrayList<DatedLine> datedLineList = new ArrayList<>();
        
        Date validDate = null;
        DatedLine.setOrderDescending(descendingOrder);
        
        try {
            while((lineRead = bufferedReader.readLine()) != null){
            	
            	validDate = validDate( lineRead, format);
            	
                if(validDate != null){
                    datedLineList.add(new DatedLine(lineRead, validDate));
                } else{
                	if(!datedLineList.isEmpty()) {
                		datedLineList.get(datedLineList.size() - 1).appendToOriginalString(lineRead);
                	}
                }
            }
        } catch (IOException ex) {
            logger.error("Unable to read lines of text");
        }
        
        contentsAsDateLines = datedLineList;
    }
	
	public Date validDate(String strToConvert, String format){
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
            } catch (ParseException e) {
            	logger.error("Unable to parse date from string");
            }
    		
    	}
    		
        return dateFromString;
    }
    
    public static List<DatedLine> returnListWithBoundedDates(List<DatedLine> unboundedList, Date minimumDate, Date maximumDate) {
        for(int ind = unboundedList.size()-1; ind > -1; ind--){
        	DatedLine holder = unboundedList.get(ind);

            if(!holder.isDateWithinBounds(minimumDate, maximumDate)){
            	unboundedList.remove(ind);
            }
        }
        
        return unboundedList;
    }
    
    public DateLineOrganizer handleDateBoundariesReturnList() {
    	String minDateStr = windowHolder.getMinDateText();
    	String maxDateStr = windowHolder.getMaxDateText();
    	
    	if(!(minDateStr.isEmpty() && maxDateStr.isEmpty())) {
    		logger.info("Working on boundary dates - date1: {}, date2: {}", minDateStr, maxDateStr);
    		
    		String regexPattern = windowHolder.getRegexPatternText();
        	Date minimumDate = getDateFromStringSupplied(minDateStr, regexPattern);
            Date maximumDate = getDateFromStringSupplied(maxDateStr, regexPattern);

            DatedLine holder;
            
            for(int index = this.contentsAsDateLines.size() - 1; index > -1; index--){

            	holder = this.contentsAsDateLines.get(index);
            	
                if(!holder.isDateWithinBounds(minimumDate, maximumDate)){
                	this.contentsAsDateLines.remove(index);
                }
            }
    	}
    	
    	return this;
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
    
	@Override
	public String toString() {
		
    	StringBuilder builder = new StringBuilder();
    	String appendingStr = "";
    	
        for(DatedLine holder: contentsAsDateLines){
            appendingStr = holder.getOriginalStringWithDate() + "\n";
            builder.append(appendingStr);
        }
        
        return builder.toString();
	}

	public List<DatedLine> getContentsAsDateLines() {
		return contentsAsDateLines;
	}

	public void setContentsAsDateLines(List<DatedLine> contentsAsDateLines) {
		this.contentsAsDateLines = contentsAsDateLines;
	}
}
