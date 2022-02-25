package loadingicon;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import window.components.LogMergerWindow;


public class LoadingIconGraphicsHandler {

    private static final String DEFAULT_PROCESSING_STRING_LABEL = "Processing";
    public static final int MAX_NUMBER_OF_PERIODS  = 40;
    private LogMergerWindow logMergerWindow;
    private int tickCounter = 0;
    private String processingText = "";
    private Rectangle loadingRectangle = null;
	
	
	public LoadingIconGraphicsHandler(LogMergerWindow logMergerWindow) {
		this.setLogMergerWindow(logMergerWindow);
	}
	
	protected void clearGlassPane() {
		if(getGlassPane()!=null) {
			getGlassPaneGraphics().clearRect(0, 0, getGlassPane().getWidth(), getGlassPane().getHeight());
		}
	}
	
	protected void drawBox() {
    	Graphics glassPaneGraphics = getGlassPaneGraphics();
    	Color currColor = glassPaneGraphics.getColor();
    	
    	Rectangle window = getLoadingRectangle();
		glassPaneGraphics.setColor(Color.RED);
    	glassPaneGraphics.drawRect( window.x, window.y, window.width, window.height);
    	glassPaneGraphics.setColor(currColor);
	}
    
    protected void clearProcessingLogoArea() {
    	Graphics glassPaneGraphics = getGlassPaneGraphics();
    	Color currColor = glassPaneGraphics.getColor();
		
    	Rectangle window = getLoadingRectangle();
    	glassPaneGraphics.setColor(Color.WHITE);
    	glassPaneGraphics.fillRect( window.x, window.y, window.width, window.height);
    	glassPaneGraphics.setColor(currColor);
    }
    
    protected void buildProcessingLogo() {
    	StringBuilder stringBuilder = new StringBuilder(DEFAULT_PROCESSING_STRING_LABEL);
    	
    	for(int t = getTickCounter(); t > 0; t--) {
    		stringBuilder.append(".");
    	}
    	
    	setProcessingText(stringBuilder.toString());
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
	
	protected void drawGlassPaneString() {
		Graphics g = getGlassPaneGraphics();
		
		Rectangle window = getLoadingRectangle();
		if(g != null) {
			FontMetrics fm = g.getFontMetrics();
	    	int textHeight = fm.getHeight();
	    	int textPadding = textHeight;
	    	
	    	Color currColor = g.getColor();
			g.setColor(Color.BLACK);
			g.drawString( getProcessingText(),  window.x + textPadding, window.y + 2 * textHeight);
			g.setColor(currColor);
		}
	}

	public String getProcessingText() {
		return processingText;
	}

	public void setProcessingText(String processingText) {
		this.processingText = processingText;
	}

	public Rectangle getLoadingRectangle() {
		return loadingRectangle;
	}

	public void setLoadingRectangle(Rectangle loadingRectangle) {
		this.loadingRectangle = loadingRectangle;
	}

	public LogMergerWindow getLogMergerWindow() {
		return logMergerWindow;
	}

	public void setLogMergerWindow(LogMergerWindow logMergerWindow) {
		this.logMergerWindow = logMergerWindow;
	}
}
