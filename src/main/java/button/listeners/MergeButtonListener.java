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
    	
    	getWindowHolder().setOrderedText("");
    	
    	ProcessLogo processingLogoThread = new ProcessLogo(getWindowHolder(), CenteredPointType.ORDERED_TEXT_AREA);
    	processingLogoThread.startProcessing();
    	new DateLineProcessor(getWindowHolder()).run();
    	processingLogoThread.stopProcessing();
        
        btnPressed.setEnabled(true);
    }
}
