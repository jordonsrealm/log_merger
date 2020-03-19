package centerpoint.object;

import mainwindow.holder.MainWindowHolder;


public class OrderedCenterPoint extends CentroidPoint {

	public OrderedCenterPoint(MainWindowHolder windowHolder) {
		super(windowHolder.getTxtHolder().getOrganizedScrollPane());
	}
}
