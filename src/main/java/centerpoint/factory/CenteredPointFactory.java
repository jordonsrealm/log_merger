package centerpoint.factory;

import centerpoint.object.CenteredPointType;
import centerpoint.object.CentroidPoint;
import centerpoint.object.OrderedCenterPoint;
import centerpoint.object.UnOrderedCenterPoint;
import mainwindow.components.LogMergerWindow;


public class CenteredPointFactory {
	
	
	public static CentroidPoint getCenteredPoint(CenteredPointType type, LogMergerWindow logMergerWindow) {
		
		if(CenteredPointType.UN_ORDERED_TEXT_AREA.equals(type)) {
			return new UnOrderedCenterPoint(logMergerWindow.getWindowHolder());
		} else {
			return new OrderedCenterPoint(logMergerWindow.getWindowHolder());
		}
	}
	
}
