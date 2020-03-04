package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import container.MainWindowContainer;
import factory.CenteredPointType;
import threads.ProcessLogo;


public class SelectFileListener implements ActionListener {

	private static final Logger logger= LoggerFactory.getLogger(SelectFileListener.class);
	JButton selectFileBtn;
	JTextArea unOrganizedText;
	JTextField fileNameInputTextField;
	JScrollPane unOrganizedScrollPane;
	MainWindowContainer mainWindowContainer;
	ExecutorService executorService;
	
	
	public SelectFileListener( MainWindowContainer mainWindowContainer, ExecutorService executorService) {
		this.mainWindowContainer = mainWindowContainer;
		this.executorService 	 = executorService;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		selectFileBtn = mainWindowContainer.getUseFileBtn();
		unOrganizedText = mainWindowContainer.getUnOrganizedText();
		fileNameInputTextField = mainWindowContainer.getFileNameInputTextField();
		unOrganizedScrollPane = mainWindowContainer.getUnOrganizedScrollPane();
		
		selectFileBtn.setEnabled(false);
    	unOrganizedText.setEnabled(false);
    	
        File file = new File(fileNameInputTextField.getText());
        
		ProcessLogo glassPaneDrawingThread = new ProcessLogo(mainWindowContainer, CenteredPointType.NOT_ORDERED);
		glassPaneDrawingThread.startProcessing();
    	
		try{
			String result = unOrganizedText.getText();
            result += IOUtils.toString(new FileInputStream(file), StandardCharsets.UTF_8);
            result = result.strip();
            
            unOrganizedText.setText(result);
            unOrganizedText.setCaretPosition(0);
            
            unOrganizedScrollPane.getHorizontalScrollBar().setValue(0);
        } catch(IOException exx){
        	logger.error("Unable to read file input:{} and add to text area", file, exx);
        }
 
    	glassPaneDrawingThread.stopProcessing();
    	
        selectFileBtn.setEnabled(true);
        unOrganizedText.setEnabled(true);
	}

}
