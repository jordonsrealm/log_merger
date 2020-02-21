package drawing;

import java.awt.Point;

import javax.swing.JComponent;
import javax.swing.JPanel;


public class CentroidPoint implements Centroid{

	protected JPanel topPanel;
	protected JComponent childWindow;
	
	
	public CentroidPoint(JPanel topPanel, JComponent childWindow) {
		this.topPanel    = topPanel;
		this.childWindow = childWindow;
	}

	@Override
	public Point getCenteredPoint() {
    	Point pt   = childWindow.getLocation();
    	int width  = childWindow.getWidth();
    	int height = childWindow.getHeight();
    	
		return new Point((pt.x + width/2), (pt.y + height/2) + topPanel.getHeight());
	}

	public JPanel getTopPanel() {
		return topPanel;
	}

	public void setTopPanel(JPanel topPanel) {
		this.topPanel = topPanel;
	}

	public JComponent getChildWindow() {
		return childWindow;
	}

	public void setChildWindow(JComponent childWindow) {
		this.childWindow = childWindow;
	}
}
