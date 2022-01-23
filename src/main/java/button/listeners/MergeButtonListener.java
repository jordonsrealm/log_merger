package button.listeners;

import javax.swing.*;

import centerpoint.object.CenteredPointType;
import mainwindow.holder.MainWindowHolder;
import runnables.DateLineProcessor;
import threads.ProcessLogo;

import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutorService;


public class MergeButtonListener extends DrawingComponentListener {
	
    public MergeButtonListener(MainWindowHolder mainWindowContainer, ExecutorService executorService){
    	super(mainWindowContainer, executorService);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	btnPressed = (JButton) e.getSource();
    	btnPressed.setEnabled(false);
    	
    	windowHolder.setOrderedText("");
    	
    	ProcessLogo processingThread = new ProcessLogo(windowHolder, CenteredPointType.ORDERED_TEXT_AREA);
    	processingThread.startProcessing();
    	new DateLineProcessor(windowHolder).run();
    	processingThread.stopProcessing();
        
        btnPressed.setEnabled(true);
    }
}
