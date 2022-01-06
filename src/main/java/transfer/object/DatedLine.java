package transfer.object;

import java.util.Date;


public class DatedLine {

    private String originalStringWithDate;
    private Date embeddedDate;


    public DatedLine(){}
    
    public DatedLine(String originalString, Date embeddedDate) {
    	this.originalStringWithDate = originalString;
    	this.embeddedDate 			= embeddedDate;
    }

    public String getOriginalStringWithDate() {
        return originalStringWithDate;
    }

    public void setOriginalStringWithDate(String originalStringWithDate) {
        this.originalStringWithDate = originalStringWithDate;
    }

    public boolean isValidDate(){
        return embeddedDate != null;
    }

    public Date getEmbeddedDate() {
        return embeddedDate;
    }

    public void setEmbeddedDate(Date embeddedDate) {
        this.embeddedDate = embeddedDate;
    }
    
    public void setAppendToOriginalString(String stringToAppend) {
    	this.originalStringWithDate += stringToAppend;
    }

}
