package threads;

import java.awt.Point;
import java.util.concurrent.atomic.AtomicBoolean;

import centerpoint.factory.CenteredPointFactory;
import centerpoint.object.CenteredPointType;
import glasspane.drawing.GlassPaneGraphicsProcessor;
import mainwindow.components.LogMergerWindow;


public class LoadingIcon extends GlassPaneGraphicsProcessor implements Runnable{

	private static final AtomicBoolean running = new AtomicBoolean(false);
    private Point centerPoint;
    
    
    public LoadingIcon(LogMergerWindow logMergerWindow, CenteredPointType centerPointType) {
    	super(logMergerWindow);
    	this.centerPoint = CenteredPointFactory.getCenteredPoint( centerPointType, logMergerWindow).getCenteredPoint();
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
        
        String processingString;
        
        while (running.get()) {
            
        	processingString = buildProcessingLogo();
        	
        	if(getTickCounter() == 0) {
        		clearProcessingLogoArea( processingString, centerPoint);
        	}
        	
            drawBox(processingString, centerPoint);
        	drawGlassPaneString(processingString, centerPoint);
        	tick();
        	
        	try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
    }

}
