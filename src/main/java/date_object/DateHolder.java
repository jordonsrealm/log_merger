package date_object;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateHolder implements Comparable<DateHolder>{

    private Date dateObject;
    private String originalString;
    public static String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss,SSS";


    public Date getDateObject() {
        return dateObject;
    }

    public String getOriginalString() {
        return originalString;
    }

    public DateHolder(String strToConvert, String format){
        String[] parts = strToConvert.split("\\s");

        String assembleDateString;

        if(parts != null && parts.length > 1){
            assembleDateString = parts[0] + " " + parts[1];
        } else{
            assembleDateString = "";
        }

        if(format == null || format.isEmpty()){
            format = DEFAULT_FORMAT;
        }

        SimpleDateFormat formatter = new SimpleDateFormat(format);

        Date dateFromString = null;
        try {
            dateFromString = formatter.parse(assembleDateString);
        } catch (ParseException e) {
        }

        this.dateObject     = dateFromString;
        this.originalString = strToConvert;
    }

    @Override
    public String toString() {
        return "DateHolder [dateObject=" + dateObject.toString() + "]";
    }

    @Override
    public int compareTo(DateHolder o) {
        if(this.getDateObject() == null){
            return 0;
        }else{
            return this.getDateObject().compareTo(o.getDateObject());
        }

    }
}
