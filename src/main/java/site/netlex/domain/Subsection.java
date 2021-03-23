package site.netlex.domain;

import java.util.ArrayList;

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
public class Subsection {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String subsDbId;
	private int position;
	private String text;
	
	@ManyToOne
	@JsonIgnoreProperties ("sections") 
    @JoinColumn(name = "secDbId")
    private Section section;
	
	/*@OneToMany(cascade = CascadeType.ALL, mappedBy = "paragraph")
	private ArrayList<Paragraph> paragraphs;*/

}
