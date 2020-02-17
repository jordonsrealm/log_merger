package listeners;

import container_pattern.MainWindowContainer;
import date_object.DateHolder;
import date_object.DateOrganizer;

import javax.swing.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;


public class MergeBtnListener implements ActionListener {
	
	private static final Logger logger = LoggerFactory.getLogger(MergeBtnListener.class);
    private JTextField format;
    private JTextArea unOrganizedText;
    private JTextArea organizedText;
    private JCheckBox descendingOrderCheckBox;
    private JTextField minDate;
    private JTextField maxDate;


    public MergeBtnListener(MainWindowContainer mainWindowContainer){
        this.format         			= mainWindowContainer.getPatternTextField();
        this.unOrganizedText 			= mainWindowContainer.getUnOrganizedText();
        this.organizedText   			= mainWindowContainer.getOrganizedText();
        this.descendingOrderCheckBox	= mainWindowContainer.getAscendDescendOrder();
        this.minDate         			= mainWindowContainer.getMinDateField();
        this.maxDate         			= mainWindowContainer.getMaxDateField();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	logger.info("Working on sorting of date objects...");
        
        ArrayList<DateHolder> dateHolderListUnsorted = DateOrganizer.createListOfDateHoldersUsingReader(new StringReader(unOrganizedText.getText()), format.getText());
        
        ArrayList<DateHolder> dateHolderListSorted = DateOrganizer.orderList(dateHolderListUnsorted, descendingOrderCheckBox.isSelected());
        
        StringBuilder builder = new StringBuilder();
        
        Date minimumDate = DateHolder.getDateFromStringSupplied(minDate.getText(), "");
        Date maximumDate = DateHolder.getDateFromStringSupplied(maxDate.getText(), "");

        for(DateHolder holder: dateHolderListSorted){
            String appendingStr = "";

            if(holder.isDateWithinBounds(minimumDate, maximumDate)){
            	appendingStr = holder.getOrginalLine() + "\n";
             }

            builder.append(appendingStr);
        }

        organizedText.setText(builder.toString());
    }
}
