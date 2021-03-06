package date.line.organizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

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
    	
    	boolean descendingOrder = windowHolder.getIsDescendingCheckBox().isSelected();
    	
    	String dateFormat = windowHolder.getTxtHolder().getRegexPatternTextField().getText();
    	
    	organizeUsingFormat(dateFormat);
    	
    	DateHolder.setDescendingOrder(descendingOrder);
    	
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
                DatedLine dateLineFromReadLine = DateHolder.getDatedLineUsingPattern(lineRead,format);

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
    
    public static ArrayList<DateHolder> returnListWithBoundedDates(ArrayList<DateHolder> unboundedList, Date minimumDate, Date maximumDate) {
        for(int ind = unboundedList.size()-1; ind > -1; ind--){
            DateHolder holder = unboundedList.get(ind);

            if(!holder.isDateWithinBounds(minimumDate, maximumDate)){
            	unboundedList.remove(ind);
            }
        }
        
        return unboundedList;
    }
    
    public DateLineOrganizer handleDateBoundariesReturnList() {
    	String date1 = windowHolder.getTxtHolder().getMinDateField().getText();
    	String date2 = windowHolder.getTxtHolder().getMaxDateField().getText();
    	
    	if(!(date1.isEmpty() && date2.isEmpty())) {
    		logger.info("Working on boundary dates - date1: {}, date2: {}", date1, date2);
    		
        	Date minimumDate = DateHolder.getDateFromStringSupplied(date1, "");
            Date maximumDate = DateHolder.getDateFromStringSupplied(date2, "");

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
