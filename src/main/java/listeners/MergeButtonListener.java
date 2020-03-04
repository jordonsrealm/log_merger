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
import java.util.concurrent.ExecutorService;


public class MergeButtonListener implements ActionListener {
	
	//private static final Logger logger = LoggerFactory.getLogger(MergeButtonListener.class);
    private DateLineProcessor processor;
    private MainWindowContainer mainWindowContainer;
    private ExecutorService executorService;
    
    
    public MergeButtonListener(MainWindowContainer mainWindowContainer, ExecutorService executorService){
        this.processor = new DateLineProcessor(mainWindowContainer);
        this.mainWindowContainer = mainWindowContainer;
        this.executorService = executorService;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	JButton btnPressed = (JButton) e.getSource();
    	Point centerPoint = CenteredPointFactory.getCenteredPoint( CenteredPointType.ORDERED, mainWindowContainer).getCenteredPoint();
    	ProcessLogo processingThread = new ProcessLogo( mainWindowContainer.getGlassPane(), centerPoint);
    	
    	btnPressed.setEnabled(false);
    	
    	this.mainWindowContainer.getOrganizedText().setText("");
    	
    	processingThread.startProcessing();
    	
    	processor.run();
    	
    	processingThread.stopProcessing();;
        
        btnPressed.setEnabled(true);
    }
}
