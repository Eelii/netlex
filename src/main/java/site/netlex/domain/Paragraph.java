package site.netlex.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Paragraph {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long parDbId;
	
	//isPreamble boolean isn't really necessary since isPreamble should always be true when position is 0, but could maybe used as a sanity check
	//when processing statutes received via POST request. 
	private boolean isPreamble;
	private int position;
	
	@Column(length=8000)
	private String text;
	
	@ManyToOne
	@JsonIgnoreProperties ("paragraphs") 
    @JoinColumn(name = "subsDbId")
    private Subsection subsection;

	public long getParDbId() {
		return parDbId;
	}

	public void setParDbId(long parDbId) {
		this.parDbId = parDbId;
	}

	public boolean isPreamble() {
		return isPreamble;
	}

	public void setPreamble(boolean isPreamble) {
		this.isPreamble = isPreamble;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Subsection getSubsection() {
		return subsection;
	}

	public void setSubsection(Subsection subsection) {
		this.subsection = subsection;
	}

	public Paragraph() {
		super();
	}
	
}
