package date_object;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import listeners.MergeBtnListener;
import transfer_object.DatedLine;


public class DateOrganizer {
	
	private static final Logger logger = LoggerFactory.getLogger(MergeBtnListener.class);
    
    public static ArrayList<DateHolder> createListOfDateHoldersUsingReader(Reader inputString, String format){
    	
    	BufferedReader bufferedReader = new BufferedReader(inputString);
        
        String lineRead;
        ArrayList<DateHolder> dateHolderList = new ArrayList<>();

        try {
            while((lineRead = bufferedReader.readLine()) != null){
                DatedLine res = DateHolder.getDatedLineUsingPattern(lineRead,format);

                if(res.isValidDate()){
                    dateHolderList.add(new DateHolder(lineRead, format));
                } else{
                    dateHolderList.get(dateHolderList.size() - 1).appendToOriginalDateString("\n"+lineRead);
                }
            }
        } catch (IOException ex) {
            logger.error("Unable to read lines of text");
        }
        
        return dateHolderList;
    }
    
    public static ArrayList<DateHolder> orderList(ArrayList<DateHolder> dateHolderListUnsorted, boolean descendingOrder) {
    	DateHolder.setDescendingOrder(descendingOrder);
    	Collections.sort(dateHolderListUnsorted);
    	
    	return dateHolderListUnsorted;
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
}
