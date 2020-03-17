package mainwindow.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;


public class SearchButton extends AbstractMainWindowContainerButton {

	private static final long serialVersionUID = 1L;
	private static final Dimension setDim = new Dimension(25,25);
	private static final String SEARCH_TOOL_TIP = "Search Files";
	
	
	public SearchButton(LogMergerWindow mainWindow) {
		super(mainWindow);
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

			getMainWindowContainer().getFileNameInputTextField().setText(selectedFile.getAbsolutePath());
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int width = getWidth();
		int height = getHeight();
		
		// Set background
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		
		g.setColor(Color.BLACK);
		g.drawArc(5, 5, width-11, height-11, 0, 360);
		g.drawArc(8, 8, width-17, height-17, 0, 360);
		
		g.drawLine(width/2, height-4, width/2, 2);
		g.drawLine(2, height/2, width-4, height/2);
	}

}
