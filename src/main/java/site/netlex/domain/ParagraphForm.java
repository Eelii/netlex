package site.netlex.domain;

import javax.validation.constraints.NotEmpty;

//TODO----------------------------------------------------------------------
public class ParagraphForm {

	private Long secDbId;
    private String textContent = "";
    
	public Long getSecDbId() {
		return secDbId;
	}
	public void setSecDbId(Long secDbId) {
		this.secDbId = secDbId;
	}
	public String getTextContent() {
		return textContent;
	}
	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	
}
