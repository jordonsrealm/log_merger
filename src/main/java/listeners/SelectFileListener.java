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
import javax.swing.SwingWorker;

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
        
        SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
        	ProcessLogo glassPaneDrawingThread = new ProcessLogo(mainWindowContainer, CenteredPointType.UN_ORDERED_TEXT_AREA);
    		
    		
            public String doInBackground() throws IOException {
            	selectFileBtn.setEnabled(false);
            	
            	File file = new File(fileNameInputTextField.getText());
                
            	glassPaneDrawingThread.startProcessing();
        		
        		String result = "";
        		
        		try{
        			result = unOrganizedText.getText();
                    result += IOUtils.toString(new FileInputStream(file), StandardCharsets.UTF_8);
                    result = result.strip();

                } catch(IOException exx){
                	logger.error("Unable to read file input:{} and add to text area", file, exx);
                }
            	
                return result; // heavy task
            }
            
            public void done() {
                try {
                    
                    unOrganizedText.setText(get());
                    unOrganizedText.setCaretPosition(0);
                    
                    unOrganizedScrollPane.getHorizontalScrollBar().setValue(0);
                    
                    glassPaneDrawingThread.stopProcessing();
                    
                    selectFileBtn.setEnabled(true);
                    unOrganizedText.setEnabled(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        worker.execute();
	}

}
