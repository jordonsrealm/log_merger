package drawing;

import container.MainWindowContainer;


public class UnOrganizedCenterPoint extends CentroidPoint{

	public UnOrganizedCenterPoint(MainWindowContainer mainWindowContainer) {
		super(mainWindowContainer.getTopPanel(), mainWindowContainer.getUnOrganizedScrollPane());
	}
}
