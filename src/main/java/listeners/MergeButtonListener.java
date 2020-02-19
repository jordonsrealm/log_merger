package listeners;

import javax.swing.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import container.MainWindowContainer;
import date.object.DateOrganizer;

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
    private JScrollPane organizedPane;


    public MergeButtonListener(MainWindowContainer mainWindowContainer){
        this.format         		 = mainWindowContainer.getPatternTextField();
        this.unOrganizedText 		 = mainWindowContainer.getUnOrganizedText();
        this.organizedText   		 = mainWindowContainer.getOrganizedText();
        this.descendingOrderCheckBox = mainWindowContainer.getAscendDescendOrder();
        this.minDate         		 = mainWindowContainer.getMinDateField();
        this.maxDate         		 = mainWindowContainer.getMaxDateField();
        this.organizedPane			 = mainWindowContainer.getOrganizedScrollPane();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	logger.info("Working on sorting of date objects...");
    	
    	DateOrganizer dateOrganizer = new DateOrganizer(unOrganizedText.getText());
    	
        dateOrganizer.orderDateLines( descendingOrderCheckBox.isSelected(), format.getText());
        
        dateOrganizer.handleDateBoundariesReturnList(minDate.getText(), maxDate.getText());
        
        
        String organizedTextString = dateOrganizer.toString();
        organizedText.setText(organizedTextString.toString());
        organizedText.setCaretPosition(organizedTextString.length());
        organizedPane.getHorizontalScrollBar().setValue(0);
    }
}
