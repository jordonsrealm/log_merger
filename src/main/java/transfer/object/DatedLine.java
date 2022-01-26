package transfer.object;

import java.util.Date;


public class DatedLine implements Comparable<DatedLine> {

    private String originalStringWithDate;
    private Date embeddedDate;
    private static boolean orderDescending;


    public DatedLine(){
    	this(null,null);
    }
    
    public DatedLine(String originalString, Date embeddedDate) {
    	this.originalStringWithDate = originalString;
    	this.embeddedDate 			= embeddedDate;
    }
    
    public static void setOrderDescending(boolean descending) {
    	orderDescending = descending;
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
    
    public void appendToOriginalString(String stringToAppend) {
    	this.originalStringWithDate += stringToAppend;
    }
    
    public boolean isDateWithinBounds(Date minimumDate, Date maximumDate) {
    	boolean withinBounds = true;
    	
    	if(minimumDate!=null && maximumDate!=null) {
    		withinBounds = (getEmbeddedDate().after(minimumDate) || getEmbeddedDate().equals(minimumDate)) && 
    						(getEmbeddedDate().before(maximumDate) || getEmbeddedDate().equals(maximumDate));
    	} else {
        	if(minimumDate != null) {
        		withinBounds = getEmbeddedDate().after(minimumDate) || getEmbeddedDate().equals(minimumDate);
        	} else {
        		if(maximumDate != null) {
        			withinBounds = getEmbeddedDate().before(maximumDate) || getEmbeddedDate().equals(maximumDate);
        		}
        	}
    	}
    	
    	return withinBounds;
    }

    @Override
    public int compareTo(DatedLine datedLine) {
        if(getEmbeddedDate() == null || datedLine.getEmbeddedDate() == null){
            return 0;
        }else{
            return getEmbeddedDate().compareTo(datedLine.getEmbeddedDate()) * (orderDescending?-1:1);
        }
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (obj == null) {
    	    return false;
    	}
    	
    	if (this.getClass() != obj.getClass())
    	    return false;
    	
    	DatedLine datedLine = (DatedLine) obj;
		return getEmbeddedDate().equals(datedLine.getEmbeddedDate()) && 
			   getOriginalStringWithDate().equals(datedLine.getOriginalStringWithDate());
    }
    
    @Override
	public int hashCode() {
		return super.hashCode();
	}

}
