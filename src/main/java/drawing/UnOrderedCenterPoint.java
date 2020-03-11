package drawing;

import container.MainWindowContainer;


public class UnOrderedCenterPoint extends CentroidPoint{

	public UnOrderedCenterPoint(MainWindowContainer mainWindowContainer) {
		super(mainWindowContainer.getUnOrganizedScrollPane());
	}
}
