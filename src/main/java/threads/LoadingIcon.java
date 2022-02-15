package threads;

import java.awt.Component;
import java.awt.Rectangle;
import java.util.concurrent.atomic.AtomicBoolean;

import glasspane.drawing.GlassPaneGraphicsProcessor;
import mainwindow.components.LogMergerWindow;


public class LoadingIcon extends GlassPaneGraphicsProcessor implements Runnable{

	private static final AtomicBoolean running = new AtomicBoolean(false);
    
    
    public LoadingIcon(LogMergerWindow logMergerWindow) {
    	super(logMergerWindow);
    }
  
    public void startLoading() {
        setTickCounter(0);
        getGlassPane().setVisible(true);
    }
  
    public void stopLoading() {
        running.set(false);
        getGlassPane().setVisible(false);
    }
 
    @Override
    public void run() { 
        running.set(true);
        
        clearGlassPane();
        
		Component oldComp = getLogMergerWindow().getWindowHolder().getTxtHolder().getOrderedScrollPane();
		Component newComp = oldComp;
		
		while(newComp.getParent() != null) {
			newComp = newComp.getParent();
		}
		
		Rectangle processingWindowRectangle =  new Rectangle((newComp.getWidth() - 200)/2 + oldComp.getWidth()/2, (newComp.getHeight() - 50)/2, 200, 50);
        String processingString;
        
        while (running.get()) {
            
        	processingString = buildProcessingLogo();
        	
        	clearProcessingLogoArea( processingString, processingWindowRectangle);
            drawBox(processingWindowRectangle);
        	drawGlassPaneString(processingString, processingWindowRectangle);
        	tick();
        	
        	try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
    }
}
