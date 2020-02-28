package runnables;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import components.OrderedTextArea;
import container.MainWindowContainer;
import date.object.DateOrganizer;


public class OrderingDateLinesProcessor implements Runnable {

	private MainWindowContainer mainWindowContainer;
	private OrderedTextArea organizedTextArea;
	private JTextArea unOrganizedTextArea;
	private JTextField minDateField;
	private JTextField maxDateField;
	private JTextField formatField;
	private JScrollPane organizedPane;
	private Boolean completedOrderingLines = false;
	
	
	public OrderingDateLinesProcessor(MainWindowContainer mainWindowContainer) {
		this.mainWindowContainer = mainWindowContainer;
	}
	
	@Override
	public void run() {
		this.completedOrderingLines = false;
		
		organizedTextArea 	 = mainWindowContainer.getOrganizedText();
		unOrganizedTextArea  = mainWindowContainer.getUnOrganizedText();
		maxDateField 		 = mainWindowContainer.getMaxDateField();
		minDateField 		 = mainWindowContainer.getMinDateField();
		formatField 	  	 = mainWindowContainer.getPatternTextField();
		
		DateOrganizer dateOrganizer = new DateOrganizer(unOrganizedTextArea.getText())
													    .orderDateLines( organizedTextArea.isDescending(), formatField.getText())
														.handleDateBoundariesReturnList( minDateField.getText(), maxDateField.getText());
        
        organizedTextArea.setText(dateOrganizer.toString());
        organizedTextArea.setCaretPosition(0);
        
        if(organizedPane != null && organizedPane.getHorizontalScrollBar()!=null) {
        	organizedPane.getHorizontalScrollBar().setValue(0);
        }
        
        this.completedOrderingLines = true;
	}

	public Boolean getCompletedOrderingLines() {
		return completedOrderingLines;
	}

	public void setCompletedOrderingLines(Boolean completedOrderingLines) {
		this.completedOrderingLines = completedOrderingLines;
	}
}
