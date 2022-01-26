package mainwindow.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;


public class SearchButton extends AbstractMainWindowContainerButton {

	private static final long serialVersionUID = 1L;
	private static final Dimension setDim = new Dimension(120,25);
	private static final String SEARCH_TOOL_TIP = "Search Files";
	
	
	public SearchButton(LogMergerWindow mainWindow) {
		super(mainWindow, SEARCH_TOOL_TIP);
		this.setPreferredSize(setDim);
		this.setMinimumSize(setDim);
		this.setMaximumSize(setDim);
		setToolTipText(SEARCH_TOOL_TIP);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

		int returnValue = jfc.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();

			getLogMergerWindow().getWindowHolder().getTxtHolder().getFileNameInputTextField().setText(selectedFile.getAbsolutePath());
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

}
