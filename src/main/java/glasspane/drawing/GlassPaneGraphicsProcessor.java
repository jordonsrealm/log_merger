package glasspane.drawing;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import mainwindow.components.LogMergerWindow;


public class GlassPaneGraphicsProcessor {

    private static final String DEFAULT_PROCESSING_STRING_LABEL = "Processing";
    public static final int MAX_NUMBER_OF_PERIODS  = 40;
    private LogMergerWindow logMergerWindow;
    private int tickCounter = 0;
	
	
	public GlassPaneGraphicsProcessor(LogMergerWindow logMergerWindow) {
		this.setLogMergerWindow(logMergerWindow);
	}
	
	protected void clearGlassPane() {
		if(getGlassPane()!=null) {
			getGlassPaneGraphics().clearRect(0, 0, getGlassPane().getWidth(), getGlassPane().getHeight());
		}
	}
	
	protected void drawBox(Rectangle window) {
		
    	Graphics glassPaneGraphics = getGlassPaneGraphics();
    	
		glassPaneGraphics.setColor(Color.RED);
    	glassPaneGraphics.drawRect( window.x, window.y, window.width, window.height);
	}
    
    protected void clearProcessingLogoArea(String processingString, Rectangle window) {
    	Graphics glassPaneGraphics = getGlassPaneGraphics();
		
    	glassPaneGraphics.setColor(Color.WHITE);
    	glassPaneGraphics.fillRect( window.x, window.y, window.width, window.height);
    }
    
    protected String buildProcessingLogo() {
    	String processingString = DEFAULT_PROCESSING_STRING_LABEL;
    	
    	for(int t=getTickCounter(); t > 0; t--) {
    		processingString+=".";
    	}
    	
    	return processingString;
    }
    
    public void tick() {
    	if(tickCounter > MAX_NUMBER_OF_PERIODS) {
    		tickCounter = 0;
    	} else{
    		tickCounter++;
    	}
    }

	public int getTickCounter() {
		return tickCounter;
	}

	public void setTickCounter(int tickCounter) {
		this.tickCounter = tickCounter;
	}
	
	public Component getGlassPane() {
		return getLogMergerWindow().getGlassPane();
	}
	
	protected Graphics getGlassPaneGraphics() {
        return getGlassPane().getGraphics();
	}
	
	protected FontMetrics getGlassPaneFontMetrics() {
		Graphics g = getGlassPane().getGraphics();
		if(g != null) {
			return g.getFontMetrics();
		}
		
		return null;
	}
	
	protected void drawGlassPaneString(String processingString, Rectangle window) {
		Graphics g = getGlassPaneGraphics();
		
		if(g != null) {
			FontMetrics fm = g.getFontMetrics();
	    	int textHeight = fm.getHeight();
	    	int textPadding = textHeight;
	    	
			g.setColor(Color.BLACK);
			g.drawString( processingString,  window.x + textPadding, window.y + 2 * textHeight);
		}
	}

	public LogMergerWindow getLogMergerWindow() {
		return logMergerWindow;
	}

	public void setLogMergerWindow(LogMergerWindow logMergerWindow) {
		this.logMergerWindow = logMergerWindow;
	}
}
