package site.netlex.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
public class Section {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long secDbId;
	
	
	private String identifier;
	//private String classification; ????
	private String heading;
	
	@ManyToOne
	@JsonIgnoreProperties ("sections") 
    @JoinColumn(name = "statDbId")
    private Statute statute;
	
	//TODO
	@OneToMany (cascade = CascadeType.ALL, mappedBy = "section")
	private List<Subsection> subsections;
	
	public long getSecDbId() {
		return secDbId;
	}

	public void setSecDbId(long secDbId) {
		this.secDbId = secDbId;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public Statute getStatute() {
		return statute;
	}

	public void setStatute(Statute statute) {
		this.statute = statute;
	}

	public List<Subsection> getSubsections() {
		return subsections;
	}

	public void setSubsections(List<Subsection> subsections) {
		this.subsections = subsections;
	}
	
	
	
	/*@OneToMany(cascade = CascadeType.ALL, mappedBy = "subsection")
	private ArrayList<Subsection> subsections;*/
	
	
	
	
	
}
