package factory;

import container.MainWindowContainer;
import drawing.CentroidPoint;
import drawing.OrderedCenterPoint;
import drawing.UnOrganizedCenterPoint;


public class CenteredPointFactory {
	
	
	public static CentroidPoint getCenteredPoint(CenteredPointType type, MainWindowContainer mainWindowContainer) {
		
		CentroidPoint centroidPoint = null;
		
		switch(type) {
			case UN_ORDERED_TEXT_AREA: 
				centroidPoint = new UnOrganizedCenterPoint(mainWindowContainer);
				break;
			case ORDERED_TEXT_AREA: 
				centroidPoint = new OrderedCenterPoint(mainWindowContainer);
				break;
		}
		
		return centroidPoint;
	}
	
}
