package centerpoint.object;

import mainwindow.container.MainWindowContainer;


public class OrderedCenterPoint extends CentroidPoint {

	public OrderedCenterPoint(MainWindowContainer mainWindowContainer) {
		super(mainWindowContainer.getOrganizedScrollPane());
	}
}
