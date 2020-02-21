package listeners;

import javax.swing.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import components.MainWindow;
import container.MainWindowContainer;
import date.object.DateOrganizer;
import factory.CenteredPointFactory;
import factory.CenteredPointType;
import threads.GlassPaneProcessingThread;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MergeButtonListener implements ActionListener {
	
	private static final Logger logger = LoggerFactory.getLogger(MergeButtonListener.class);
    private JTextField formatField;
    private JTextField minDateField;
    private JTextField maxDateField;
    private JTextArea unOrganizedTextArea;
    private JTextArea organizedTextArea;
    private JCheckBox descendingOrderCheckBox;
    private JScrollPane organizedPane;
    private MainWindow mainWindow;
    
    
    public MergeButtonListener(MainWindowContainer mainWindowContainer){
        this.formatField         	 = mainWindowContainer.getPatternTextField();
        this.unOrganizedTextArea 	 = mainWindowContainer.getUnOrganizedText();
        this.organizedTextArea   	 = mainWindowContainer.getOrganizedText();
        this.descendingOrderCheckBox = mainWindowContainer.getAscendDescendOrder();
        this.minDateField         	 = mainWindowContainer.getMinDateField();
        this.maxDateField         	 = mainWindowContainer.getMaxDateField();
        this.organizedPane			 = mainWindowContainer.getOrganizedScrollPane();
        this.mainWindow 			 = mainWindowContainer.getMainWindow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	
    	JButton btnPressed = (JButton) e.getSource();
    	
    	btnPressed.setEnabled(false);
    	
    	organizedTextArea.setText("");
    	
    	Point centerPoint = CenteredPointFactory.getCenteredPoint(CenteredPointType.ORDERED, mainWindow).getCenteredPoint();
    	
    	GlassPaneProcessingThread processingThread = new GlassPaneProcessingThread(mainWindow, centerPoint);
    	processingThread.startProcessing();
    	
    	DateOrganizer dateOrganizer = new DateOrganizer(unOrganizedTextArea.getText());
        dateOrganizer.orderDateLines( descendingOrderCheckBox.isSelected(), formatField.getText());
        dateOrganizer.handleDateBoundariesReturnList( minDateField.getText(), maxDateField.getText());
        
        String organizedTextString = dateOrganizer.toString();
        organizedTextArea.setText(organizedTextString);
        organizedTextArea.setCaretPosition(organizedTextString.length());
        organizedPane.getHorizontalScrollBar().setValue(0);
        
        processingThread.stopProcessing();
        
        btnPressed.setEnabled(true);
    }
}
