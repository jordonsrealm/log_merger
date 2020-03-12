package centerpoint.object;

import java.awt.Component;
import java.awt.Point;

import javax.swing.JComponent;
import javax.swing.JFrame;


public class CentroidPoint implements Centroid{

	private JComponent childWindow;
	
	
	public CentroidPoint(JComponent childWindow) {
		this.childWindow = childWindow;
	}

	@Override
	public Point getCenteredPoint() {
    	Point pt = getPointFromOrigin();
    	
		return new Point((pt.x), (pt.y));
	}
	
	private Point getPointFromOrigin() {
		
		Component comp = childWindow;
		Point currentPt;
		int totalX = 0;
		int totalY = 0;
		
		while(!(comp instanceof JFrame)) {
			comp = comp.getParent();
			currentPt = comp.getLocation();
			totalX += currentPt.x;
			totalY += currentPt.y;
		}
		
		return new Point(totalX, totalY);
	}
}
