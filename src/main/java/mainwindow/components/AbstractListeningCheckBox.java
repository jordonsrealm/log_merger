package mainwindow.components;

import java.awt.event.ItemListener;

import javax.swing.JCheckBox;

import mainwindow.holder.MainWindowHolder;


public abstract class AbstractListeningCheckBox extends JCheckBox implements ItemListener{

	private static final long serialVersionUID = 1L;
	protected MainWindowHolder windowHolder;
	
	
	public AbstractListeningCheckBox(MainWindowHolder windowHolder) {
		this(windowHolder, "");
	}
	
	public AbstractListeningCheckBox(MainWindowHolder windowHolder, String title) {
		super(title);
		this.setWindowHolder(windowHolder);
		this.addItemListener(this);
	}

	public MainWindowHolder getWindowHolder() {
		return windowHolder;
	}

	public void setWindowHolder(MainWindowHolder windowHolder) {
		this.windowHolder = windowHolder;
	}
}
