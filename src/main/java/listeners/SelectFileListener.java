package listeners;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
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

import container.MainWindowContainer;
import factory.CenteredPointType;
import highlighter.UnorganizedHighlighter;
import threads.ProcessLogo;


public class SelectFileListener extends DrawingComponentListener{

	private static final Logger logger= LoggerFactory.getLogger(SelectFileListener.class);
	JButton selectFileBtn;
	JTextArea unOrganizedText;
	JTextField fileNameInputTextField;
	JScrollPane unOrganizedScrollPane;
	MainWindowContainer mainWindowContainer;
	ExecutorService executorService;
	static UnorganizedHighlighter myHighlightPainter = new UnorganizedHighlighter(Color.decode("0xB9FF8E"));


	public SelectFileListener( MainWindowContainer mainWindowContainer, ExecutorService executorService) {
		super(mainWindowContainer, executorService);
	}

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
					
					removeHighlights(unOrganizedText);
					highlight(unOrganizedText, mainWindowContainer.getPatternTextField().getText());

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

	public static void highlight(JTextComponent textComp, String pattern) throws Exception {
		removeHighlights(textComp);
		
		String regex = pattern.replace("dd", "\\d\\d")
		  .replace("yyyy", "\\d\\d\\d\\d")
		  .replace("MM", "\\d\\d")
		  .replace("HH", "\\d\\d")
		  .replace("mm", "\\d\\d")
		  .replace("ss", "\\d\\d")
		  .replace("SSS", "\\d\\d\\d")
		  .replace(":", "\\:")
		  .replace(".","\\.");

		System.out.println("Regex Pattern: " + regex);
		Highlighter hilite = textComp.getHighlighter();
		Document doc = textComp.getDocument();
		String text = doc.getText(0, doc.getLength());
		
		final Pattern p = Pattern.compile(regex);
		final Matcher m = p.matcher(text);
		int pos = 0;

		while (m.find()) {
			pos = m.start();
			System.out.println("Position: " + pos);
		    hilite.addHighlight(pos, pos + pattern.length(), myHighlightPainter);
			pos += pattern.length();
		}

	}

	public static void removeHighlights(JTextComponent textComp) {
		Highlighter hilite = textComp.getHighlighter();
		Highlighter.Highlight[] hilites = hilite.getHighlights();

		for (int i = 0; i < hilites.length; i++) {
			if (hilites[i].getPainter() instanceof UnorganizedHighlighter) {
				hilite.removeHighlight(hilites[i]);
			}
		}
	}
}
