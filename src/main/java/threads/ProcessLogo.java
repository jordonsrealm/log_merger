package threads;

import java.awt.Point;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import container.MainWindowContainer;
import drawing.GlassPaneGraphicsProcessor;
import factory.CenteredPointFactory;
import factory.CenteredPointType;


public class ProcessLogo extends GlassPaneGraphicsProcessor implements Runnable{

	private static final Logger logger = LoggerFactory.getLogger(ProcessLogo.class);
	private Thread worker;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private Point centerPoint;
    
    
    public ProcessLogo(MainWindowContainer mainWindowContainer, CenteredPointType centerPointType) {
    	super(mainWindowContainer.getGlassPane());
    	this.centerPoint = CenteredPointFactory.getCenteredPoint( centerPointType, mainWindowContainer).getCenteredPoint();;
    }
  
    public void startProcessing() {
        worker = new Thread(this);
        worker.start();
        setTickCounter(0);
        getGlassPane().setVisible(true);
    }
  
    public void stopProcessing() {
        running.set(false);
        getGlassPane().setVisible(false);
    }
 
    @Override
    public void run() { 
        running.set(true);
        
        String processingString = buildProcessingLogo();
        
        clearProcessingLogoArea( processingString, centerPoint);
        
        while (running.get()) {
        	tick();
        	
        	processingString = buildProcessingLogo();
        	
        	if(getTickCounter() == 0) {
        		clearProcessingLogoArea( processingString, centerPoint);
        	}
        	
        	drawGlassPaneString(processingString, centerPoint);
        	
        	try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				logger.error("Unable to pause current thread - tickCount: {}", getTickCounter(), e);
			}
        }
    }

}
