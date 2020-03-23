package button.listeners;

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


public class SaveFileListener implements ActionListener {

	private static final Logger logger = LoggerFactory.getLogger(SaveFileListener.class);
	private JTextArea organizedText;
	
	
	public SaveFileListener(JTextArea organizedText) {
		this.organizedText = organizedText;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setDialogTitle("Specify a file to save");

        int userSelection = jfc.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = jfc.getSelectedFile();
            logger.debug("Saving file as: " + fileToSave.getAbsolutePath());

            try(FileOutputStream outputStream = new FileOutputStream(fileToSave)){
                byte[] bytes = this.organizedText.getText().getBytes();
                outputStream.write(bytes);
            } catch(FileNotFoundException ex){
                logger.error("Unable to find the file to save to...", ex);
            } catch (IOException ioEx){
                logger.error("Uable to create file to save to...", ioEx);
            }

            logger.info("Saved to file: {}", fileToSave.getName());
        }
    
	}

}
