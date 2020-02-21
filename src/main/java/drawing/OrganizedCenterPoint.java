package drawing;

import components.MainWindow;


public class OrganizedCenterPoint extends CentroidPoint {

	public OrganizedCenterPoint(MainWindow mainWindow) {
		super(mainWindow.getMainWindowContainer().getTopPanel(), mainWindow.getMainWindowContainer().getOrganizedScrollPane());
	}
}
