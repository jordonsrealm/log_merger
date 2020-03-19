package mainwindow.components;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SaveFileButton extends AbstractMainWindowContainerButton {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(SaveFileButton.class);
    private static final String BTN_TITLE = "Save...";
	

	public SaveFileButton(LogMergerWindow mainWindow) {
		super(mainWindow, BTN_TITLE);
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
                byte[] bytes = getLogMergerWindow().getWindowHolder().getTxtHolder().getOrganizedText().getText().getBytes();
                outputStream.write(bytes);
            } catch(FileNotFoundException ex){
                logger.error("Unable to find the file to save to...", ex);
            } catch (IOException ioEx){
                logger.error("Uable to create file to save to...", ioEx);
            }

            logger.info("Saved to file: {}", fileToSave.getName());
        }
    
	
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

}
