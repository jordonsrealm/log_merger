package mainwindow.components;

import java.awt.event.ItemListener;

import javax.swing.JCheckBox;

import mainwindow.container.MainWindowContainer;


public abstract class AbstractListeningCheckBox extends JCheckBox implements ItemListener{

	private static final long serialVersionUID = 1L;
	protected MainWindowContainer mainWindowContainer;
	
	
	public AbstractListeningCheckBox(MainWindowContainer mainWindowContainer) {
		this(mainWindowContainer, "");
	}
	
	public AbstractListeningCheckBox(MainWindowContainer mainWindowContainer, String title) {
		super(title);
		this.setMainWindowContainer(mainWindowContainer);
		this.addItemListener(this);
	}

	public MainWindowContainer getMainWindowContainer() {
		return mainWindowContainer;
	}

	public void setMainWindowContainer(MainWindowContainer mainWindowContainer) {
		this.mainWindowContainer = mainWindowContainer;
	}
}
