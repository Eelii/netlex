package site.netlex.domain;

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
	
	private boolean isPreamble;
	private int position;
	private String text;
	
	@ManyToOne
	@JsonIgnoreProperties ("subsections") 
    @JoinColumn(name = "subsDbId")
    private Subsection subsection;
	
}
