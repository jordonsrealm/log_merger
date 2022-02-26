package window.components.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileSystemView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SaveFileBtnListener implements ActionListener {

	private static final Logger logger = LoggerFactory.getLogger(SaveFileBtnListener.class);
	private JTextArea organizedText;
	private static final String DIALOG_TITLE = "Specify a file to save";
	
	
	public SaveFileBtnListener(JTextArea organizedText) {
		this.organizedText = organizedText;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setDialogTitle(DIALOG_TITLE);

        int userSelection = jfc.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = jfc.getSelectedFile();
            logger.debug("Saving file as: {}", fileToSave.getAbsolutePath());

            try(FileOutputStream outputStream = new FileOutputStream(fileToSave)){
                outputStream.write(this.organizedText.getText().getBytes());
                logger.debug("Saved to file: {}", fileToSave.getName());
            } catch(FileNotFoundException ex){
                logger.error("Unable to find the file to save to...", ex);
            } catch (IOException ioEx){
                logger.error("Uable to create file to save to...", ioEx);
            }
        }
    
	}

}
