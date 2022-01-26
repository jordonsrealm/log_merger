package mainwindow.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import centerpoint.object.CenteredPointType;
import configuration.ConfigurationGetter;
import highlighter.UnOrganizedHighlighter;
import mainwindow.holder.MainWindowHolder;
import threads.ProcessLogo;


public class AddFileButton extends AbstractMainWindowContainerButton {
	
	private static final long serialVersionUID = 1L;
	private static final Dimension setDim = new Dimension( 100, 25);
	private static final Logger logger = LoggerFactory.getLogger(AddFileButton.class);
	private static UnOrganizedHighlighter myHighlightPainter = new UnOrganizedHighlighter(Color.decode(ConfigurationGetter.instance().getHighlightHexColor()));
	private static final String ADD_FILE_TOOL_TIP = "Add File";
	private static final String DBL_DIG_STR = "\\d\\d";
	
	
	public AddFileButton(LogMergerWindow logMergerWindow) {
		super(logMergerWindow, ADD_FILE_TOOL_TIP);
		this.setPreferredSize(setDim);
		this.setMinimumSize(setDim);
		this.setMaximumSize(setDim);
		setToolTipText(ADD_FILE_TOOL_TIP);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MainWindowHolder windowHolder = getWindowHolder();
		
		if(!windowHolder.getFileNameInputText().isEmpty()) {
			JTextArea unOrganizedText = windowHolder.getTxtHolder().getUnOrderedText();

			SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
				ProcessLogo glassPaneDrawingThread = new ProcessLogo(windowHolder, CenteredPointType.UN_ORDERED_TEXT_AREA);

				public String doInBackground() throws IOException {
					windowHolder.setSearchBtnEnabled(false);

					File file = new File(windowHolder.getFileNameInputText());

					glassPaneDrawingThread.startProcessing();

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

				public void done() {
					try {
						unOrganizedText.setText(get());
						unOrganizedText.setCaretPosition(0);
						
						removeHighlights(unOrganizedText);
						highlight(unOrganizedText, windowHolder.getTxtHolder().getRegexPatternTextField().getText());

						windowHolder.setUnOrderedHorizontalScrollBar(0);

						glassPaneDrawingThread.stopProcessing();

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


	public static void highlight(JTextComponent textComp, String pattern) throws Exception {
		removeHighlights(textComp);
		
		String regex = pattern.replace("dd", DBL_DIG_STR)
		  .replace("yyyy", DBL_DIG_STR + DBL_DIG_STR)
		  .replace("MM", DBL_DIG_STR)
		  .replace("HH", DBL_DIG_STR)
		  .replace("mm", DBL_DIG_STR)
		  .replace("ss", DBL_DIG_STR)
		  .replace("SSS", "\\d\\d\\d")
		  .replace(":", "\\:")
		  .replace(".","\\.");

		Highlighter hilite = textComp.getHighlighter();
		Document doc = textComp.getDocument();
		String text = doc.getText(0, doc.getLength());
		
		final Pattern p = Pattern.compile(regex);
		final Matcher m = p.matcher(text);
		int pos = 0;

		while (m.find()) {
			pos = m.start();
		    hilite.addHighlight(pos, pos + pattern.length(), myHighlightPainter);
			pos += pattern.length();
		}
	}

	public static void removeHighlights(JTextComponent textComp) {
		Highlighter hilite = textComp.getHighlighter();
		Highlighter.Highlight[] hilites = hilite.getHighlights();

		for (int i = 0; i < hilites.length; i++) {
			if (hilites[i].getPainter() instanceof UnOrganizedHighlighter) {
				hilite.removeHighlight(hilites[i]);
			}
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
}
