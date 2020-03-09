package listeners;

import javax.swing.*;

import container.MainWindowContainer;
import factory.CenteredPointType;
import runnables.DateLineProcessor;
import threads.ProcessLogo;

import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutorService;


public class MergeButtonListener extends DrawingComponentListener {
	
	//private static final Logger logger = LoggerFactory.getLogger(MergeButtonListener.class);
    private DateLineProcessor processor;
    
    
    public MergeButtonListener(MainWindowContainer mainWindowContainer, ExecutorService executorService){
    	super(mainWindowContainer, executorService);
    	processor = new DateLineProcessor(this.mainWindowContainer);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	JButton btnPressed = (JButton) e.getSource();
    	ProcessLogo processingThread = new ProcessLogo( mainWindowContainer, CenteredPointType.ORDERED_TEXT_AREA);
    	
    	btnPressed.setEnabled(false);
    	
    	mainWindowContainer.getOrganizedText().setText("");
    	
    	processingThread.startProcessing();
    	
    	processor.run();
    	
    	processingThread.stopProcessing();;
        
        btnPressed.setEnabled(true);
    }
}
