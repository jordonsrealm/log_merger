package date_object;

import transfer_object.DateResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateHolder implements Comparable<DateHolder>{

    private DateResult dateResult;
    public static String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    public static Boolean invertComparison;


    public DateHolder(String strToConvert, String format){

        this.dateResult = getDateFromEntireString(strToConvert, format);
    }

    public DateResult getDateResult() {
        return dateResult;
    }

    public Date getDate(){
        return dateResult.getConvertedDate();
    }

    public String getOgStr(){
        return dateResult.getOriginalStringWithDate();
    }

    public static DateResult getDateFromEntireString(String strToConvert, String format){
        String[] segmentedDate = strToConvert.split("\\s");

        String assembleDateString;

        if(segmentedDate != null && segmentedDate.length > 1){
            assembleDateString = segmentedDate[0] + " " + segmentedDate[1];
        } else{
            assembleDateString = "";
        }

        return new DateResult( getDateFromString(assembleDateString), strToConvert);
    }

    public static Date getDateFromString(String dateStr){
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_FORMAT);

        Date dateFromString = null;
        try {
            dateFromString = formatter.parse(dateStr);
        } catch (ParseException e) {
        }

        return dateFromString;
    }

    public void appendStrToOrigStr(String appStr){
        String ogStr = this.dateResult.getOriginalStringWithDate();
        this.dateResult.setOriginalStringWithDate(ogStr + appStr);
    }

    @Override
    public String toString() {
        return "DateHolder [dateObject=" + dateResult.toString() + "]";
    }

    @Override
    public int compareTo(DateHolder o) {
        if(this.getDateResult() == null || o.getDateResult() == null){
            return 0;
        }else{
            return this.getDateResult().getConvertedDate().compareTo(o.getDateResult().getConvertedDate()) * (invertComparison?-1:1);
        }

    }
}
