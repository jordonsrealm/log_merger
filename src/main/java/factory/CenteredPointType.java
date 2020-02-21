package factory;

public enum CenteredPointType {

	ORDERED("ORDERED", 1),
	NOT_ORDERED("NOT_ORDERED",0);
	
	private final String key;
    private final Integer value;

    CenteredPointType(String key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }
    public Integer getValue() {
        return value;
    }
    
    public String getKey(Integer value) {
    	String keyToReturn = null;
    	
    	for(CenteredPointType type: values()) {
    		if(type.value.equals(value)) {
    			keyToReturn = type.key;
    		}
    	}
    	
		return keyToReturn;
    }
}
