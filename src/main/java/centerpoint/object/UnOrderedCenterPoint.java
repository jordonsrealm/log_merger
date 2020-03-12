package centerpoint.object;

import mainwindow.container.MainWindowContainer;


public class UnOrderedCenterPoint extends CentroidPoint{

	public UnOrderedCenterPoint(MainWindowContainer mainWindowContainer) {
		super(mainWindowContainer.getUnOrganizedScrollPane());
	}
}
