package factory;

import container.MainWindowContainer;
import drawing.CentroidPoint;
import drawing.OrganizedCenterPoint;
import drawing.UnOrganizedCenterPoint;


public class CenteredPointFactory {
	
	
	public static CentroidPoint getCenteredPoint(CenteredPointType type, MainWindowContainer mainWindowContainer) {
		
		CentroidPoint centroidPoint = null;
		
		switch(type) {
			case NOT_ORDERED: 
				centroidPoint = new UnOrganizedCenterPoint(mainWindowContainer);
				break;
			case ORDERED: 
				centroidPoint = new OrganizedCenterPoint(mainWindowContainer);
				break;
		}
		
		return centroidPoint;
	}
	
}
