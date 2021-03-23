package site.netlex.domain;


public class StatuteForm {
	
    private String num = "";
    
    private String year = "";

    private String statuteType = "";

    private String documentType = "";
    
    private String language = "";
    
    private String shortName = "";

    private String statuteId = num + "/" + year;
    
    private String fullName = statuteType + shortName;
    	
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getStatuteType() {
		return statuteType;
	}

	public void setStatuteType(String statuteType) {
		this.statuteType = statuteType;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	
	public String getFullName() {
		return fullName;
	}
	
	public String getStatuteId() {
		return statuteId;
	}
	
    
}
    