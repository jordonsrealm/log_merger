package listeners;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import container.MainWindowContainer;
import factory.CenteredPointFactory;
import factory.CenteredPointType;
import threads.ProcessLogo;


public class SelectFileListener implements ActionListener {

	private static final Logger logger= LoggerFactory.getLogger(SelectFileListener.class);
	JButton selectFileBtn;
	JTextArea unOrganizedText;
	JTextField fileNameInputTextField;
	JScrollPane unOrganizedScrollPane;
	MainWindowContainer mainWindowContainer;
	
	
	public SelectFileListener( MainWindowContainer mainWindowContainer) {
		this.mainWindowContainer = mainWindowContainer;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Point centeredPoint = CenteredPointFactory.getCenteredPoint(CenteredPointType.NOT_ORDERED, mainWindowContainer).getCenteredPoint();
		ProcessLogo glassPaneDrawingThread = new ProcessLogo(mainWindowContainer.getGlassPane(), centeredPoint);
		
		selectFileBtn = mainWindowContainer.getUseFileBtn();
		unOrganizedText = mainWindowContainer.getUnOrganizedText();
		fileNameInputTextField = mainWindowContainer.getFileNameInputTextField();
		unOrganizedScrollPane = mainWindowContainer.getUnOrganizedScrollPane();
		
		selectFileBtn.setEnabled(false);
    	unOrganizedText.setEnabled(false);
    	
        File file = new File(fileNameInputTextField.getText());
        String result = unOrganizedText.getText();
        
    	glassPaneDrawingThread.startProcessing();
    	
    	try{
            result += IOUtils.toString(new FileInputStream(file), StandardCharsets.UTF_8);
            result = result.strip();
        } catch(IOException exx){
        	logger.error("Unable to read file input:{} and add to text area", file, exx);
        }

        unOrganizedText.setText(result);
        unOrganizedText.setCaretPosition(0);
        
        unOrganizedScrollPane.getHorizontalScrollBar().setValue(0);
        
        glassPaneDrawingThread.stopProcessing();
        
        selectFileBtn.setEnabled(true);
        unOrganizedText.setEnabled(true);
	}

}
