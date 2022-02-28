package logmerger.frame.datedline.organizer;

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

import logmerger.frame.component.holder.CheckBoxHolder;
import logmerger.frame.component.holder.WindowComponentHolder;
import logmerger.frame.datedline.DatedLine;
import logmerger.frame.datedline.logginglevel.LoggingLevel;


public class DateLineOrganizer {
	
	private static final Logger logger = LoggerFactory.getLogger(DateLineOrganizer.class);
    private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private WindowComponentHolder windowComponentHolder;
	
	
	public DateLineOrganizer(WindowComponentHolder windowComponentHolder) {
		setWindowComponentHolder(windowComponentHolder);
	}
	
	public WindowComponentHolder getWindowComponentHolder() {
		return windowComponentHolder;
	}
	
	public void setWindowComponentHolder(WindowComponentHolder mainWindowContainer) {
		this.windowComponentHolder = mainWindowContainer;
	}
	
    public String orderDateLines(String minDateString, String maxDateString) {
    	
    	List<DatedLine> datedLines = getDatedLinesUsingFormat(getCurrentDateFormat());
        
    	datedLines = handleDateRanges(datedLines, minDateString, maxDateString);
    	
    	datedLines = getOrdereDatedLines(datedLines);
    	
    	updateMainWindowHolderWithDatedLines(datedLines);
    	
    	return returnCompleteTextFromDatedLines(datedLines);
    }
    
    public void updateMainWindowHolderWithDatedLines(List<DatedLine> datedLines) {
    	getWindowComponentHolder().setDatedLines(datedLines);
    }
    
    public List<DatedLine> handleDateRanges(List<DatedLine> datedLines, String minDateString, String maxDateString){
    	datedLines.removeIf(datedLine -> !datedLine.isWithinBounds(getDateFromFormat(minDateString), getDateFromFormat(maxDateString)));
    	return datedLines;
    }
    
    public List<DatedLine> getOrdereDatedLines(List<DatedLine> lines){
    	Collections.sort(lines);
    	return lines;
    }
    
    public List<DatedLine> getDatedLinesUsingFormat(){
    	return this.getDatedLinesUsingFormat(getCurrentDateFormat());
    }
    
    protected List<DatedLine> getDatedLinesUsingFormat(String format) {
    	DatedLine.setDescendingOrder(getWindowComponentHolder().isDescending());
    	CheckBoxHolder holder = getWindowComponentHolder().getCheckBoxHolder();
        ArrayList<DatedLine> datedLineList = new ArrayList<>();
        String lineRead;
        
        try (BufferedReader bufferedReader = new BufferedReader(new StringReader(getNotSortedString()))) {
            while((lineRead = bufferedReader.readLine()) != null){
            	DatedLine givenDatedLine = new DatedLine(lineRead, format, LoggingLevel.getLevel(lineRead));
            	givenDatedLine.handleVisibility(holder);
            	
            	if(givenDatedLine.isValidDate()) {
            		datedLineList.add(givenDatedLine);
            	} else if(!datedLineList.isEmpty()) {
            		DatedLine currLine = datedLineList.get(datedLineList.size()-1);
            		currLine.appendToOriginalString("\n"+lineRead);
            	}
            }
        } catch (IOException ex) {
            logger.error("Unable to read lines of text: ", ex);
        }
        
        return datedLineList;
    }
    
    public String returnCompleteTextFromDatedLines(List<DatedLine> datedLines) {
    	
    	StringBuilder builder = new StringBuilder();
    	
        for(DatedLine datedLine: datedLines){
        	if(datedLine.isVisible()) {
                builder.append(datedLine.getOriginalStringWithDate() + "\n");
        	}
        }
        
        return builder.toString();
    }
    
    protected Date getDateFromFormat(String dateAsString) {
    	
    	if(dateAsString == null || dateAsString.isBlank()) {
    		return null;
    	}
    	
    	String currentDateFormat = getWindowComponentHolder().getRegexPatternText();
        SimpleDateFormat formatter = currentDateFormat.isEmpty() ? new SimpleDateFormat(DEFAULT_FORMAT) : new SimpleDateFormat(currentDateFormat);
        
        Date dateFromString = null;
        try {
            dateFromString = formatter.parse(dateAsString);
        } catch (ParseException e) {
        	logger.error("Unable to parse date from string: {}, format: {}", dateAsString, currentDateFormat);
        }
        
        return dateFromString;
    }
    
    protected String getNotSortedString() {
		return getWindowComponentHolder().getUnorderedText();
	}

    protected String getCurrentDateFormat() {
		return getWindowComponentHolder().getRegexPatternText();
	}
}
