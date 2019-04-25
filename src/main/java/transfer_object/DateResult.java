package transfer_object;

import java.util.Date;


public class DateResult {

    private String originalStringWithDate;
    private Date convertedDate;


    public DateResult(){}

    public DateResult(Date theDate, String ogStr){
        this.originalStringWithDate = ogStr;
        this.convertedDate = theDate;
    }

    public String getOriginalStringWithDate() {
        return originalStringWithDate;
    }

    public void setOriginalStringWithDate(String originalStringWithDate) {
        this.originalStringWithDate = originalStringWithDate;
    }

    public boolean isValidDate(){
        return convertedDate != null;
    }

    public Date getConvertedDate() {
        return convertedDate;
    }

    public void setConvertedDate(Date convertedDate) {
        this.convertedDate = convertedDate;
    }

}
