package datedline;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import datedline.logginglevel.LoggingLevel;
import window.component.holder.CheckBoxHolder;


public class DatedLine implements Comparable<DatedLine> {

	private static final Logger logger = LoggerFactory.getLogger(DatedLine.class);
	private static final String REPLACE_SEQ = "\\d\\d";
	private String originalStringWithDate;
	private String dateFormat;
	private static boolean descendingOrder;
	private LoggingLevel logLevel;
	private int rowCount = 1;
	private boolean visible = false;


	public DatedLine(){
		this( null, null, LoggingLevel.UNKNOWN);
	}

	public DatedLine(String originalString, String dateFormat) {
		this(originalString, dateFormat, LoggingLevel.UNKNOWN);
	}

	public DatedLine(String originalString, String dateFormat, LoggingLevel level) {
		setOriginalStringWithDate(originalString);
		setDateFormat(dateFormat);
		setLogLevel(level);
	}

	public static void setDescendingOrder(boolean descending) {
		DatedLine.descendingOrder = descending;
	}

	public String getDateFormat() {
		return this.dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public LoggingLevel getLogLevel() {
		return this.logLevel;
	}

	public void setLogLevel(LoggingLevel logLevel) {
		this.logLevel = logLevel;
	}

	public String getOriginalStringWithDate() {
		return this.originalStringWithDate;
	}

	public void setOriginalStringWithDate(String originalStringWithDate) {
		this.originalStringWithDate = originalStringWithDate;
	}

	public boolean isVisible() {
		return this.visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public int getRowCount() {
		return this.rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public void handleVisibility(CheckBoxHolder holder) {
		setVisible((this.logLevel.equals(LoggingLevel.ERROR) && holder.isErrorSelected())||
				(this.logLevel.equals(LoggingLevel.UNKNOWN) && holder.isUnknownSelected())||
				(this.logLevel.equals(LoggingLevel.INFO) && holder.isInfoSelected())||
				(this.logLevel.equals(LoggingLevel.WARN) && holder.isWarnSelected())||
				(this.logLevel.equals(LoggingLevel.DEBUG) && holder.isDebugSelected())||
				(this.logLevel.equals(LoggingLevel.TRACE) && holder.isTraceSelected()));
	}

	public boolean isValidDate(){
		return getDate() != null;
	}

	public Date getDate() {
		Date date = null;

		String formattedDate = getFormattedDate(this.originalStringWithDate, this.dateFormat);

		if(formattedDate == null) {
			return date;
		}

		try {
			date = new SimpleDateFormat(this.dateFormat).parse(formattedDate);
		} catch (ParseException e) {
			logger.debug("Unable to parse date: {} from format: {}, Exception: {}", formattedDate, this.dateFormat, e);
		}

		return date;
	}

	public String getDateAsString() {
		return getFormattedDate(this.originalStringWithDate, this.dateFormat);
	}

	public void appendToOriginalString(String stringToAppend) {
		this.originalStringWithDate += stringToAppend;
		this.rowCount++;
	}

	protected String getFormattedDate(String strToConvert, String format) {
		String formattedString = format.replace("dd", REPLACE_SEQ)
				.replace("yyyy", REPLACE_SEQ + REPLACE_SEQ)
				.replace("MM", REPLACE_SEQ)
				.replace("HH", REPLACE_SEQ)
				.replace("mm", REPLACE_SEQ)
				.replace("ss", REPLACE_SEQ)
				.replace("SSS", "\\d\\d\\d")
				.replace(":", "\\:")
				.replace(".","\\.");

		Matcher matcher = Pattern.compile(formattedString).matcher(strToConvert);
		String dateFromString = null;

		if(matcher.find()) {
			dateFromString = strToConvert.substring(matcher.start(), matcher.end());
		}

		return dateFromString;
	}

	public boolean isWithinBounds(Date minimumDate, Date maximumDate) {
		boolean withinBounds = true;

		if(minimumDate!=null && maximumDate!=null) {
			withinBounds = (getDate().after(minimumDate) || getDate().equals(minimumDate)) && 
					(getDate().before(maximumDate) || getDate().equals(maximumDate));
		} else {
			if(minimumDate != null) {
				withinBounds = getDate().after(minimumDate) || getDate().equals(minimumDate);
			}

			if(maximumDate != null) {
				withinBounds = getDate().before(maximumDate) || getDate().equals(maximumDate);
			}
		}

		return withinBounds;
	}

	@Override
	public int compareTo(DatedLine datedLine) {
		if(getDate() == null || datedLine.getDate() == null){
			return 0;
		}else{
			return getDate().compareTo(datedLine.getDate()) * (descendingOrder?-1:1);
		}
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null) return false;
		if (obj == this) return true;

		if (!(obj instanceof DatedLine)) return false;

		DatedLine datedLine = (DatedLine) obj;
		return getDate().equals(datedLine.getDate()) && 
				this.originalStringWithDate.equals(datedLine.getOriginalStringWithDate()) &&
				this.dateFormat.equals(datedLine.getDateFormat());
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
