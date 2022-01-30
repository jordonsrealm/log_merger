package threads;

import java.awt.Point;
import java.util.concurrent.atomic.AtomicBoolean;

import centerpoint.factory.CenteredPointFactory;
import centerpoint.object.CenteredPointType;
import glasspane.drawing.GlassPaneGraphicsProcessor;
import mainwindow.holder.MainWindowHolder;


public class LoadingIcon extends GlassPaneGraphicsProcessor implements Runnable{

	private static final AtomicBoolean running = new AtomicBoolean(false);
    private Point centerPoint;
    
    
    public LoadingIcon(MainWindowHolder mainWindowContainer, CenteredPointType centerPointType) {
    	super(mainWindowContainer.getGlassPane());
    	this.centerPoint = CenteredPointFactory.getCenteredPoint( centerPointType, mainWindowContainer).getCenteredPoint();
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
        
        String processingString = buildProcessingLogo();
        
        clearProcessingLogoArea( processingString, centerPoint);
        
        while (running.get()) {
        	tick();
        	
        	processingString = buildProcessingLogo();
        	
        	if(getTickCounter() == 0) {
        		clearProcessingLogoArea( processingString, centerPoint);
        	}
        	
        	drawGlassPaneString(processingString, centerPoint);
        }
    }

}
