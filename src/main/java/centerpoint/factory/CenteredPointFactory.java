package centerpoint.factory;

import centerpoint.object.CenteredPointType;
import centerpoint.object.CentroidPoint;
import centerpoint.object.OrderedCenterPoint;
import centerpoint.object.UnOrderedCenterPoint;
import mainwindow.holder.MainWindowHolder;


public class CenteredPointFactory {
	
	
	public static CentroidPoint getCenteredPoint(CenteredPointType type, MainWindowHolder mainWindowContainer) {
		
		if(CenteredPointType.UN_ORDERED_TEXT_AREA.equals(type)) {
			return new UnOrderedCenterPoint(mainWindowContainer);
		} else {
			return new OrderedCenterPoint(mainWindowContainer);
		}
	}
	
}
