package listeners;

import javax.swing.*;

import container.MainWindowContainer;
import factory.CenteredPointFactory;
import factory.CenteredPointType;
import runnables.DateLineProcessor;
import threads.ProcessLogo;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MergeButtonListener implements ActionListener {
	
	//private static final Logger logger = LoggerFactory.getLogger(MergeButtonListener.class);
    private DateLineProcessor processor;
    private MainWindowContainer mainWindowContainer;
    
    
    public MergeButtonListener(MainWindowContainer mainWindowContainer){
        this.processor = new DateLineProcessor(mainWindowContainer);
        this.mainWindowContainer = mainWindowContainer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	JButton btnPressed = (JButton) e.getSource();
    	Point centerPoint = CenteredPointFactory.getCenteredPoint( CenteredPointType.ORDERED, mainWindowContainer).getCenteredPoint();
    	ProcessLogo processingThread = new ProcessLogo( mainWindowContainer.getGlassPane(), centerPoint);
    	
    	btnPressed.setEnabled(false);
    	
    	this.mainWindowContainer.getOrganizedText().setText("");
    	
    	processingThread.startProcessing();
    	
    	SwingUtilities.invokeLater(processor);
    	
        processingThread.stopProcessing();
        
        btnPressed.setEnabled(true);
    }
}
