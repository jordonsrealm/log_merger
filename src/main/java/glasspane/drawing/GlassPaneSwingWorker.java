package glasspane.drawing;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import highlighter.UnOrganizedHighlighter;
import mainwindow.components.AddFileButton;
import mainwindow.components.LogMergerWindow;
import mainwindow.holder.MainWindowHolder;
import threads.LoadingIcon;


public class GlassPaneSwingWorker extends SwingWorker<String, Void> {

	private static final Logger logger= LoggerFactory.getLogger(GlassPaneSwingWorker.class);
	private LogMergerWindow logMergerWindow;
	private LoadingIcon glassPaneDrawingThread;
	static UnOrganizedHighlighter myHighlightPainter;
	private static final String REGEX_NUMERICAL_PATTER = "\\d\\d";
	
	
	public GlassPaneSwingWorker(LogMergerWindow logMergerWindow, String highlightHexColor) {
		this.logMergerWindow = logMergerWindow;
		this.glassPaneDrawingThread = new LoadingIcon(logMergerWindow);
		myHighlightPainter = new UnOrganizedHighlighter(Color.decode(highlightHexColor));
	}
	
	@Override
	protected String doInBackground() throws Exception {

		getWindowHolder().getBtnHolder().getAddFileButton().setEnabled(false);
		
		File file = new File(getWindowHolder().getTxtHolder().getFileNameInputTextField().getText());

		glassPaneDrawingThread.startLoading();

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
	protected void process(List<Void> chunks) {
		super.process(chunks);
	}

	@Override
	protected void done() {
		AddFileButton selectFileBtn = getWindowHolder().getBtnHolder().getAddFileButton();
		JTextArea unOrganizedText = getWindowHolder().getTxtHolder().getUnOrderedText();
		JTextField regexPatternTextField = getWindowHolder().getTxtHolder().getRegexPatternTextField();
		JScrollPane unOrganizedScrollPane = getWindowHolder().getTxtHolder().getUnOrderedScrollPane();

		try {
			getWindowHolder().getTxtHolder().getUnOrderedText().setText(get());
			getWindowHolder().getTxtHolder().getUnOrderedText().setCaretPosition(0);
			
			removeHighlights(unOrganizedText);
			highlight(unOrganizedText, regexPatternTextField.getText());

			unOrganizedScrollPane.getHorizontalScrollBar().setValue(0);

			glassPaneDrawingThread.stopLoading();

			selectFileBtn.setEnabled(true);
			unOrganizedText.setEnabled(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void highlight(JTextComponent textComp, String pattern) throws Exception {
		removeHighlights(textComp);
		
		String regex = pattern.replace("dd", REGEX_NUMERICAL_PATTER)
		  .replace("yyyy", REGEX_NUMERICAL_PATTER + REGEX_NUMERICAL_PATTER)
		  .replace("MM", REGEX_NUMERICAL_PATTER)
		  .replace("HH", REGEX_NUMERICAL_PATTER)
		  .replace("mm", REGEX_NUMERICAL_PATTER)
		  .replace("ss", REGEX_NUMERICAL_PATTER)
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
	
	private LogMergerWindow getLogMergerWindow() {
		return this.logMergerWindow;
	}
	
	private MainWindowHolder getWindowHolder() {
		return getLogMergerWindow().getWindowHolder();
	}
}
