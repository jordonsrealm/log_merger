package drawing;

import components.MainWindow;


public class UnOrganizedCenterPoint extends CentroidPoint{

	public UnOrganizedCenterPoint(MainWindow mainWindow) {
		super(mainWindow.getMainWindowContainer().getTopPanel(), mainWindow.getMainWindowContainer().getUnOrganizedScrollPane());
	}
}
