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

import mainwindow.components.holder.CheckBoxHolder;
import mainwindow.holder.MainWindowHolder;
import transfer.object.DatedLine;
import transfer.object.LoggingLevel;


public class DateLineOrganizer {
	
	private static final Logger logger = LoggerFactory.getLogger(DateLineOrganizer.class);
    private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private MainWindowHolder mainWindowContainer;
	
	
	public DateLineOrganizer(MainWindowHolder mainWindowContainer) {
		setMainWindowContainer(mainWindowContainer);
	}

    public String orderDateLines(String minDateString, String maxDateString) {
    	
    	List<DatedLine> datedLines = getDatedLinesUsingFormat(getCurrentDateFormat());
        
    	datedLines.removeIf(datedLine -> !datedLine.isWithinBounds(getDateFromFormat(minDateString), getDateFromFormat(maxDateString)));
    	
    	Collections.sort(datedLines);
    	
    	getMainWindowHolder().setDatedLines(datedLines);
    	
    	return returnCompleteTextFromDatedLines(datedLines);
    }
    
    protected List<DatedLine> getDatedLinesUsingFormat(String format) {
    	DatedLine.setOrderDescending(getMainWindowHolder().isDescending());
    	CheckBoxHolder holder = getMainWindowHolder().getCheckBoxHolder();
    	boolean markErrors = holder.getErrorCheckBox().isSelected();
    	boolean markDebugs = holder.getDebugCheckBox().isSelected();
    	boolean markTraces = holder.getTraceCheckBox().isSelected();
    	boolean markWarns = holder.getWarnCheckBox().isSelected();
    	boolean markInfos = holder.getInfoCheckBox().isSelected();
    	boolean markUnknowns = holder.getUnknownCheckBox().isSelected();
    	
        ArrayList<DatedLine> datedLineList = new ArrayList<>();
        String lineRead;
        
        try (BufferedReader bufferedReader = new BufferedReader(new StringReader(getNotSortedString()))) {
            while((lineRead = bufferedReader.readLine()) != null){
            	DatedLine givenDatedLine = new DatedLine(lineRead, format, LoggingLevel.getLevel(lineRead));
            	givenDatedLine.handleVisibility(markErrors, markUnknowns, markInfos, markWarns, markDebugs, markTraces);
            	
            	if(givenDatedLine.isValidDate()) {
            		datedLineList.add(givenDatedLine);
            	} else {
            		if(!datedLineList.isEmpty()) {
            			DatedLine currLine = datedLineList.get(datedLineList.size()-1);
            			currLine.appendToOriginalString("\n"+lineRead);
            		}
            	}
            }
        } catch (IOException ex) {
            logger.error("Unable to read lines of text: ", ex);
        }
        
        return datedLineList;
    }
    
    protected String returnCompleteTextFromDatedLines(List<DatedLine> datedLines) {
    	
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
    	
    	String currentDateFormat = getMainWindowHolder().getRegexPatternText();
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
		return getMainWindowHolder().getUnorderedText();
	}

    protected String getCurrentDateFormat() {
		return getMainWindowHolder().getRegexPatternText();
	}

	public MainWindowHolder getMainWindowHolder() {
		return mainWindowContainer;
	}

	public void setMainWindowContainer(MainWindowHolder mainWindowContainer) {
		this.mainWindowContainer = mainWindowContainer;
	}
}
