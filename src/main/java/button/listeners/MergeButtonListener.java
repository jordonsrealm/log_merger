package button.listeners;

import javax.swing.*;

import centerpoint.object.CenteredPointType;
import mainwindow.container.MainWindowContainer;
import runnables.DateLineProcessor;
import threads.ProcessLogo;

import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutorService;


public class MergeButtonListener extends DrawingComponentListener {
	
	//private static final Logger logger = LoggerFactory.getLogger(MergeButtonListener.class);
    private DateLineProcessor processor;
    JButton btnPressed;
    
    
    public MergeButtonListener(MainWindowContainer mainWindowContainer, ExecutorService executorService){
    	super(mainWindowContainer, executorService);
    	
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	btnPressed = (JButton) e.getSource();
    	
    	processor = new DateLineProcessor(mainWindowContainer);
    	
    	btnPressed.setEnabled(false);
    	
    	mainWindowContainer.getOrganizedText().setText("");
    	
    	ProcessLogo processingThread = new ProcessLogo( mainWindowContainer, CenteredPointType.ORDERED_TEXT_AREA);
    	
    	processingThread.startProcessing();
    	
    	processor.run();
    	
    	processingThread.stopProcessing();
        
        btnPressed.setEnabled(true);
    }
}
