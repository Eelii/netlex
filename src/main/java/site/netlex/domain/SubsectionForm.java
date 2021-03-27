package site.netlex.domain;

import javax.validation.constraints.NotEmpty;

public class SubsectionForm {

	private Long statDbId;
	private Long secDbId;
    private String textContent = "";
    private int position;
    
	public Long getStatDbId() {
		return statDbId;
	}
	public void setStatDbId(Long statDbId) {
		this.statDbId = statDbId;
	}
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
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}

	
}