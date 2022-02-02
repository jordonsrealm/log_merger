package threads;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
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
    	setCenterPoint(CenteredPointFactory.getCenteredPoint( centerPointType, logMergerWindow).getCenteredPoint());
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
        
		Component oldComp = getLogMergerWindow().getWindowHolder().getTxtHolder().getOrderedTextArea();
		Component newComp = oldComp;
		
		while(newComp.getParent() != null) {
			newComp = newComp.getParent();
		}
		
		Rectangle processingWindowRectangle =  new Rectangle((newComp.getWidth() + oldComp.getWidth() - 200)/2, (newComp.getHeight() + oldComp.getHeight()/4 - 50)/2, 200, 50);
        
        String processingString;
        
        while (running.get()) {
            
        	processingString = buildProcessingLogo();
        	
        	clearProcessingLogoArea( processingString, processingWindowRectangle);
            drawBox(processingWindowRectangle);
        	drawGlassPaneString(processingString, processingWindowRectangle);
        	tick();
        	
        	try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
    }

	public Point getCenterPoint() {
		return centerPoint;
	}

	public void setCenterPoint(Point centerPoint) {
		this.centerPoint = centerPoint;
	}

}
