package mainwindow.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mainwindow.holder.MainWindowHolder;
import threads.LoadingIcon;


public class AddFileButton extends AbstractMainWindowContainerButton {
	
	private static final long serialVersionUID = 1L;
	private static final Dimension setDim = new Dimension( 100, 25);
	private static final Logger logger = LoggerFactory.getLogger(AddFileButton.class);
	private static final String ADD_FILE_TOOL_TIP = "Add File";
	
	
	public AddFileButton(LogMergerWindow logMergerWindow) {
		super(logMergerWindow, ADD_FILE_TOOL_TIP);
		this.setPreferredSize(setDim);
		this.setMinimumSize(setDim);
		this.setMaximumSize(setDim);
		setToolTipText(ADD_FILE_TOOL_TIP);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MainWindowHolder windowHolder = getLogMergerWindow().getWindowHolder();
		
		if(!windowHolder.getFileNameInputText().isEmpty()) {
			JTextArea unOrganizedText = windowHolder.getTxtHolder().getUnOrderedText();

			LoadingIcon loadinIcon = new LoadingIcon(getLogMergerWindow());
			
			SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {

				public String doInBackground() throws IOException {
					windowHolder.setSearchBtnEnabled(false);

					File file = new File(windowHolder.getFileNameInputText());

					loadinIcon.startLoading();

					String result = "";

					try{
						result =  unOrganizedText.getText();
						result += IOUtils.toString(new FileInputStream(file), StandardCharsets.UTF_8);
						result = result.strip();

					} catch(IOException exx){
						logger.error("Unable to read file input:{} and add to text area", file, exx);
					}

					return result;
				}

				@Override
				public void done() {
					try {
						unOrganizedText.setText(get());
						unOrganizedText.setCaretPosition(0);
						
						windowHolder.setUnOrderedHorizontalScrollBar(0);

						loadinIcon.stopLoading();

						windowHolder.setSearchBtnEnabled(true);
						unOrganizedText.setEnabled(true);
					} catch (Exception ex) {
						logger.error("Exception was caught after calling done on swing worker: {}", ex.getCause().getMessage());
					}
				}
			};
			worker.execute();
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
}
