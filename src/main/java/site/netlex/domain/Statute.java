package site.netlex.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Statute {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long statDbId;
	
	private int num;
	private int year;
	private String statuteId;
	private String shortName;
	private String statuteType;
	private String documentType;
	private String fullName;
	private String language;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "statute")
	private List<Section> sections;
	
	public long getStatDbId() {
		return statDbId;
	}
	public void setStatDbId(long statDbId) {
		this.statDbId = statDbId;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getStatuteId() {
		return statuteId;
	}
	public void setStatuteId(String statuteId) {
		this.statuteId = statuteId;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
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
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public List<Section> getSections() {
		return sections;
	}
	
	public void setSections(List<Section> sections) {
		this.sections = sections;
	}
	
	public void initStatute() {
		this.fullName = this.statuteType + " " + this.shortName;
		this.statuteId = this.num + "/" + this.year; 
	}
	/*@OneToMany(cascade = CascadeType.ALL, mappedBy = "section")
	private ArrayList<Section> sections;*/
	
	
	
	/*@OneToMany(cascade = CascadeType.ALL, mappedBy = "chapter")
	private ArrayList<Chapter> chapters;
	@OneToOne(cascade=CascadeType.ALL, mappedBy="enactingClause")
	private EnactingClause enactingClause;
	private ArrayList<ReferencePart> referenceParts;
	@OneToOne(cascade=CascadeType.ALL, mappedBy="signaturePart")
	private SignaturePart signaturePart;*/
	
	
}
