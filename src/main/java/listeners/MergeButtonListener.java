package listeners;

import container_pattern.MainWindowContainer;
import date_object.DateOrganizer;

import javax.swing.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MergeButtonListener implements ActionListener {
	
	private static final Logger logger = LoggerFactory.getLogger(MergeButtonListener.class);
    private JTextField format;
    private JTextField minDate;
    private JTextField maxDate;
    private JTextArea unOrganizedText;
    private JTextArea organizedText;
    private JCheckBox descendingOrderCheckBox;


    public MergeButtonListener(MainWindowContainer mainWindowContainer){
        this.format         		 = mainWindowContainer.getPatternTextField();
        this.unOrganizedText 		 = mainWindowContainer.getUnOrganizedText();
        this.organizedText   		 = mainWindowContainer.getOrganizedText();
        this.descendingOrderCheckBox = mainWindowContainer.getAscendDescendOrder();
        this.minDate         		 = mainWindowContainer.getMinDateField();
        this.maxDate         		 = mainWindowContainer.getMaxDateField();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	logger.info("Working on sorting of date objects...");
        
    	DateOrganizer dateOrganizer = new DateOrganizer(unOrganizedText.getText());
    	
        dateOrganizer.orderDateLines( descendingOrderCheckBox.isSelected(), format.getText());
        
        dateOrganizer.handleDateBoundariesReturnList(minDate.getText(), maxDate.getText());
        
        organizedText.setText(dateOrganizer.toString());
    }
}
