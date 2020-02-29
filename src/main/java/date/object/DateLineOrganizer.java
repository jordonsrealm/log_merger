package date.object;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import transfer.object.DatedLine;


public class DateLineOrganizer {
	
	private static final Logger logger = LoggerFactory.getLogger(DateLineOrganizer.class);
	private final String stringContents;
	private ArrayList<DateHolder> contentsAsDateLines = new ArrayList<DateHolder>();
	
	
	public DateLineOrganizer(String startingString) {
		this.stringContents = startingString;
	}

    public DateLineOrganizer orderDateLines(boolean descendingOrder, String dateFormat) {
    	
    	organizeUsingFormat(dateFormat);
    	
    	DateHolder.setDescendingOrder(descendingOrder);
    	
    	Collections.sort(this.contentsAsDateLines);
    	
    	return this;
    }
    
    private void organizeUsingFormat(String format){
    	logger.info("Ordering Date Lines");
    	
    	BufferedReader bufferedReader = new BufferedReader(new StringReader(this.stringContents));
        
        String lineRead;
        ArrayList<DateHolder> dateHolderList = new ArrayList<>();
        
        try {
            while((lineRead = bufferedReader.readLine()) != null){
                DatedLine dateLineFromReadLine = DateHolder.getDatedLineUsingPattern(lineRead,format);

                if(dateLineFromReadLine.isValidDate()){
                    dateHolderList.add(new DateHolder(lineRead, format));
                } else{
                    dateHolderList.get(dateHolderList.size() - 1).appendToOriginalDateString(lineRead);
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
    
    public DateLineOrganizer handleDateBoundariesReturnList(String date1, String date2) {
    	
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
    
    public static String returnAsString(ArrayList<DateHolder> dateHolderListSorted) {
    	
    	StringBuilder builder = new StringBuilder();
    	String appendingStr = "";
    	
        for(DateHolder holder: dateHolderListSorted){
            appendingStr = holder.getOrginalLine() + "\n";
            builder.append(appendingStr);
        }
        
        return builder.toString();
    }
    
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return returnAsString(this.contentsAsDateLines);
	}

	public ArrayList<DateHolder> getContentsAsDateLines() {
		return contentsAsDateLines;
	}

	public void setContentsAsDateLines(ArrayList<DateHolder> contentsAsDateLines) {
		this.contentsAsDateLines = contentsAsDateLines;
	}
}
