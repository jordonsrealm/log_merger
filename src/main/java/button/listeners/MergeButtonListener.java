package button.listeners;

import javax.swing.*;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import centerpoint.object.CenteredPointType;
import mainwindow.container.MainWindowContainer;
import runnables.DateLineProcessor;
import threads.ProcessLogo;

import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutorService;


public class MergeButtonListener extends DrawingComponentListener {
	
	//private static final Logger logger = LoggerFactory.getLogger(MergeButtonListener.class);

    public MergeButtonListener(MainWindowContainer mainWindowContainer, ExecutorService executorService){
    	super(mainWindowContainer, executorService);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	btnPressed = (JButton) e.getSource();
    	
    	DateLineProcessor processor = new DateLineProcessor(mainWindowContainer);
    	
    	btnPressed.setEnabled(false);
    	
    	mainWindowContainer.getOrganizedText().setText("");
    	
    	ProcessLogo processingThread = new ProcessLogo( mainWindowContainer, CenteredPointType.ORDERED_TEXT_AREA);
    	
    	processingThread.startProcessing();
    	
    	processor.run();
    	
    	processingThread.stopProcessing();
        
        btnPressed.setEnabled(true);
    }
}
