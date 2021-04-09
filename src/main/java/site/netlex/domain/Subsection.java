package site.netlex.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Subsection {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long subsDbId;
	private int position;
	@Column(length=8000)
	private String text;
	
	@ManyToOne
	@JsonIgnoreProperties ("subsections") 
    @JoinColumn(name = "secDbId")
    private Section section;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "subsection")
	private List<Paragraph> paragraphs;
	
	public long getSubsDbId() {
		return subsDbId;
	}

	public void setSubsDbId(long subsDbId) {
		this.subsDbId = subsDbId;
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

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public List<Paragraph> getParagraphs() {
		return paragraphs;
	}

	public void setParagraphs(List<Paragraph> paragraphs) {
		this.paragraphs = paragraphs;
	}

	
}
