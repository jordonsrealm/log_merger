package factory;

import components.MainWindow;
import drawing.CentroidPoint;
import drawing.OrganizedCenterPoint;
import drawing.UnOrganizedCenterPoint;


public class CenteredPointFactory {
	
	
	public static CentroidPoint getCenteredPoint(CenteredPointType type, MainWindow mainWindow) {
		
		CentroidPoint centroidPoint = null;
		
		switch(type) {
			case NOT_ORDERED: 
				centroidPoint = new UnOrganizedCenterPoint(mainWindow);
				break;
			case ORDERED: 
				centroidPoint = new OrganizedCenterPoint(mainWindow);
				break;
		}
		
		return centroidPoint;
	}
	
}
