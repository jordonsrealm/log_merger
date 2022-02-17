package glasspane.drawing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mainwindow.components.AddFileButton;
import mainwindow.components.LogMergerWindow;
import mainwindow.holder.MainWindowHolder;
import threads.LoadingIcon;


public class GlassPaneSwingWorker extends SwingWorker<String, Void> {

	private static final Logger logger= LoggerFactory.getLogger(GlassPaneSwingWorker.class);
	private LogMergerWindow logMergerWindow;
	private LoadingIcon loadingIconThread;
	
	
	public GlassPaneSwingWorker(LogMergerWindow logMergerWindow) {
		this.logMergerWindow = logMergerWindow;
		this.loadingIconThread = new LoadingIcon(logMergerWindow);
	}
	
	@Override
	protected String doInBackground() throws Exception {

		getWindowHolder().getBtnHolder().getAddFileButton().setEnabled(false);
		
		File file = new File(getWindowHolder().getTxtHolder().getFileNameInputTextField().getText());

		loadingIconThread.startLoading();

		String result = "";

		try{
			result =  getWindowHolder().getTxtHolder().getUnOrderedText().getText();
			result += IOUtils.toString(new FileInputStream(file), StandardCharsets.UTF_8);
			result = result.strip();
		} catch(IOException exx){
			logger.error("Unable to read file input:{} and add to text area", file, exx);
		}

		return result;
	}
	
	@Override
	protected void done() {
		AddFileButton selectFileBtn = getWindowHolder().getBtnHolder().getAddFileButton();
		JTextArea unOrganizedText = getWindowHolder().getTxtHolder().getUnOrderedText();
		JScrollPane unOrganizedScrollPane = getWindowHolder().getTxtHolder().getUnOrderedScrollPane();

		try {
			getWindowHolder().getTxtHolder().getUnOrderedText().setText(get());
			getWindowHolder().getTxtHolder().getUnOrderedText().setCaretPosition(0);
			
			unOrganizedScrollPane.getHorizontalScrollBar().setValue(0);

			loadingIconThread.stopLoading();

			selectFileBtn.setEnabled(true);
			unOrganizedText.setEnabled(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private LogMergerWindow getLogMergerWindow() {
		return this.logMergerWindow;
	}
	
	private MainWindowHolder getWindowHolder() {
		return getLogMergerWindow().getWindowHolder();
	}
}
