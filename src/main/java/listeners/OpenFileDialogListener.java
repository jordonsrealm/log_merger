package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

public class OpenFileDialogListener implements ActionListener {

	JTextField fileNameInputTextField;
	
	
	public OpenFileDialogListener(JTextField fileNameInputTextField) {
		this.fileNameInputTextField = fileNameInputTextField;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

		int returnValue = jfc.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();

			fileNameInputTextField.setText(selectedFile.getAbsolutePath());
		}
	}
}
