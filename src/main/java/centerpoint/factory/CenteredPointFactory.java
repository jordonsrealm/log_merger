package centerpoint.factory;

import centerpoint.object.CenteredPointType;
import centerpoint.object.CentroidPoint;
import centerpoint.object.OrderedCenterPoint;
import centerpoint.object.UnOrderedCenterPoint;
import mainwindow.container.MainWindowContainer;


public class CenteredPointFactory {
	
	
	public static CentroidPoint getCenteredPoint(CenteredPointType type, MainWindowContainer mainWindowContainer) {
		
		CentroidPoint centroidPoint = null;
		
		switch(type) {
			case UN_ORDERED_TEXT_AREA: 
				centroidPoint = new UnOrderedCenterPoint(mainWindowContainer);
				break;
			case ORDERED_TEXT_AREA: 
				centroidPoint = new OrderedCenterPoint(mainWindowContainer);
				break;
		}
		
		return centroidPoint;
	}
	
}
