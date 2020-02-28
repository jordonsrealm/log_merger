package drawing;

import container.MainWindowContainer;


public class OrganizedCenterPoint extends CentroidPoint {

	public OrganizedCenterPoint(MainWindowContainer mainWindowContainer) {
		super(mainWindowContainer.getTopPanel(), mainWindowContainer.getOrganizedScrollPane());
	}
}
