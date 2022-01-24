package date.line.organizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import date.holder.DateHolder;
import mainwindow.holder.MainWindowHolder;
import transfer.object.DatedLine;


public class DateLineOrganizer {
	
	private static final Logger logger = LoggerFactory.getLogger(DateLineOrganizer.class);
	private ArrayList<DateHolder> contentsAsDateLines = new ArrayList<>();
	private MainWindowHolder windowHolder;
	
	
	public DateLineOrganizer(MainWindowHolder mainWindowContainer) {
		this.windowHolder = mainWindowContainer;
	}

    public DateLineOrganizer orderDateLines() {
    	String dateFormat = windowHolder.getTxtHolder().getRegexPatternTextField().getText();
    	
    	organizeUsingFormat(dateFormat);
    	
    	DateHolder.setDescendingOrder(windowHolder.getCheckBoxHolder().getDescendingCheckBox().isSelected());
    	
    	Collections.sort(this.contentsAsDateLines);
    	
    	return this;
    }
    
    private void organizeUsingFormat(String format){
    	logger.info("Ordering Date Lines");
    	
    	BufferedReader bufferedReader = new BufferedReader(new StringReader(windowHolder.getTxtHolder().getUnOrderedText().getText()));
        
        String lineRead;
        ArrayList<DateHolder> dateHolderList = new ArrayList<>();
        
        try {
            while((lineRead = bufferedReader.readLine()) != null){
                DatedLine dateLineFromReadLine = DateHolder.getDatedLineUsingPattern( lineRead, format);

                if(dateLineFromReadLine.isValidDate()){
                    dateHolderList.add(new DateHolder(lineRead, format));
                } else{
                	if(!dateHolderList.isEmpty()) {
                		dateHolderList.get(dateHolderList.size() - 1).appendToOriginalDateString(lineRead);
                	}
                }
            }
        } catch (IOException ex) {
            logger.error("Unable to read lines of text");
        }
        
        this.contentsAsDateLines = dateHolderList;
    }
    
    public static List<DateHolder> returnListWithBoundedDates(List<DateHolder> unboundedList, Date minimumDate, Date maximumDate) {
        for(int ind = unboundedList.size()-1; ind > -1; ind--){
            DateHolder holder = unboundedList.get(ind);

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
    		
        	Date minimumDate = DateHolder.getDateFromStringSupplied(minDateStr, "");
            Date maximumDate = DateHolder.getDateFromStringSupplied(maxDateStr, "");

            DateHolder holder;
            
            for(int index = this.contentsAsDateLines.size() - 1; index > -1; index--){

            	holder = this.contentsAsDateLines.get(index);
            	
                if(!holder.isDateWithinBounds(minimumDate, maximumDate)){
                	this.contentsAsDateLines.remove(index);
                }
            }
    	}
    	
    	return this;
    }
    
	@Override
	public String toString() {
		
    	StringBuilder builder = new StringBuilder();
    	String appendingStr = "";
    	
        for(DateHolder holder: contentsAsDateLines){
            appendingStr = holder.getOrginalLine() + "\n";
            builder.append(appendingStr);
        }
        
        return builder.toString();
	}

	public ArrayList<DateHolder> getContentsAsDateLines() {
		return contentsAsDateLines;
	}

	public void setContentsAsDateLines(ArrayList<DateHolder> contentsAsDateLines) {
		this.contentsAsDateLines = contentsAsDateLines;
	}
}
