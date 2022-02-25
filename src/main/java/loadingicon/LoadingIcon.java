package loadingicon;

import java.awt.Component;
import java.awt.Rectangle;
import java.util.concurrent.atomic.AtomicBoolean;

import window.components.LogMergerWindow;


public class LoadingIcon extends LoadingIconGraphicsHandler implements Runnable{

	private static final AtomicBoolean running = new AtomicBoolean(false);
    
    
    public LoadingIcon(LogMergerWindow logMergerWindow) {
    	super(logMergerWindow);
    }
  
    public void initializeLoadingIcon() {
        setTickCounter(0);
        getGlassPane().setVisible(true);
    }
  
    public void terminateLoadingIcon() {
        running.set(false);
        getGlassPane().setVisible(false);
    }
 
    @Override
    public void run() { 
        running.set(true);
        
        clearGlassPane();
        
		Component oldComp = getLogMergerWindow().getWindowComponentHolder().getTxtHolder().getOrderedScrollPane();
		Component newComp = oldComp;
		
		while(newComp.getParent() != null) {
			newComp = newComp.getParent();
		}
		
		setLoadingRectangle(new Rectangle((newComp.getWidth() - 200)/2 + oldComp.getWidth()/2, (newComp.getHeight() - 50)/2, 200, 50));
		
        while (running.get()) {
            
        	buildProcessingLogo();
        	
        	clearProcessingLogoArea();
            drawBox();
        	drawGlassPaneString();
        	tick();
        	
        	try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
    }
}
