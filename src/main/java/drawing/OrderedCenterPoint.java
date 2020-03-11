package drawing;

import container.MainWindowContainer;


public class OrderedCenterPoint extends CentroidPoint {

	public OrderedCenterPoint(MainWindowContainer mainWindowContainer) {
		super(mainWindowContainer.getOrderedScrollPane());
	}
}
