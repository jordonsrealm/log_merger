package threads;

import java.awt.Component;
import java.awt.Point;
import java.util.concurrent.atomic.AtomicBoolean;

import components.MainWindow;
import drawing.GlassPaneGraphicsProcessor;


public class GlassPaneProcessingThread extends GlassPaneGraphicsProcessor implements Runnable{

	private Thread worker;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private Point centerPoint;
    
 
    public GlassPaneProcessingThread(MainWindow frame, Point centerPoint) {
    	super(frame.getMainWindowContainer());
    	this.centerPoint = centerPoint;
    	
    }
  
    public void startProcessing() {
        worker = new Thread(this);
        worker.start();
        setTickCounter(0);
    }
  
    public void stopProcessing() {
        running.set(false);
        getGlassPane().setVisible(false);
    }
 
    @Override
    public void run() { 
        running.set(true);
        
        Component glassPane = getGlassPane();

        
        glassPane.setVisible(true);
        
        while (running.get()) {
        	tick();
        	
        	Point unOrganizedCenterPoint = centerPoint;
        	
        	String processingString = buildProcessingLogo();
        	
        	if(getTickCounter() == 1) {
        		clearProcessingLogoArea( processingString, unOrganizedCenterPoint);
        	}
        	
        	drawGlassPaneString(processingString, unOrganizedCenterPoint.x, unOrganizedCenterPoint.y);
        	
        	try {
        		//glassPane.repaint();
				Thread.sleep(20);
			} catch (InterruptedException e) {
			}
        }
    }

}
