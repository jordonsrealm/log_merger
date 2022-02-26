package loadingicon;

import java.awt.event.MouseListener;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import logmerger.frame.LogMergerFrame;
import logmerger.frame.listener.GlassPaneListener;


public class LoadingIcon extends LoadingIconGraphicsHandler implements Runnable{

	private static final Logger logger = LoggerFactory.getLogger(LoadingIcon.class);
	private static final AtomicBoolean running = new AtomicBoolean(false);
	private MouseListener listener = null;
    
    
    public LoadingIcon(LogMergerFrame logMergerWindow) {
    	super(logMergerWindow);
    	this.listener = new GlassPaneListener();
    }
  
    public void initializeLoadingIcon() {
        setTickCounter(0);
        getGlassPane().setVisible(true);
        getGlassPane().addMouseListener(this.listener);
    }
  
    public void terminateLoadingIcon() {
        running.set(false);
        getGlassPane().setVisible(false);
        getGlassPane().removeMouseListener(this.listener);
    }
 
    @Override
    public void run() { 
        running.set(true);
        
        clearGlassPane();
		
        while (running.get()) {
        	drawLoadingIconArea();
        	tick();
        	
        	try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				logger.error("Unable to sleep current LoadingIcon thread", e);
			}
        }
    }
}
