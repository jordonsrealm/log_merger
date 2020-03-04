package threads;

import java.awt.Component;
import java.awt.Point;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import drawing.GlassPaneGraphicsProcessor;


public class ProcessLogo extends GlassPaneGraphicsProcessor implements Runnable{

	private static final Logger logger = LoggerFactory.getLogger(ProcessLogo.class);
	private Thread worker;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private Point centerPoint;
    
 
    public ProcessLogo(Component glassPane, Point centerPoint) {
    	super(glassPane);
    	this.centerPoint = centerPoint;
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
        
        while (running.get()) {
        	tick();
        	
        	String processingString = buildProcessingLogo();
        	
        	if(getTickCounter() == 1) {
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
