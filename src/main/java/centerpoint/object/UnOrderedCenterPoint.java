package centerpoint.object;

import mainwindow.holder.MainWindowHolder;


public class UnOrderedCenterPoint extends CentroidPoint{

	public UnOrderedCenterPoint(MainWindowHolder windowHolder) {
		super(windowHolder.getTxtHolder().getUnOrganizedScrollPane());
	}
}
