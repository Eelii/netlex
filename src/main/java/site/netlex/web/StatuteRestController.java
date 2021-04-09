package site.netlex.web;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import site.netlex.domain.Paragraph;
import site.netlex.domain.ParagraphRepository;
import site.netlex.domain.Section;
import site.netlex.domain.SectionRepository;
import site.netlex.domain.Statute;
import site.netlex.domain.StatuteRepository;
import site.netlex.domain.Subsection;
import site.netlex.domain.SubsectionRepository;

@RestController
@RequestMapping("/api")
public class StatuteRestController {

	@Autowired
	StatuteRepository statRepo;
	
	@Autowired
	SectionRepository secRepo;
	
	@Autowired
	SubsectionRepository subsecRepo;
	
	@Autowired
	ParagraphRepository paraRepo;
	
	@GetMapping("/saados/{statid}")
	public Statute getStatute(@PathVariable long statid) {
		return statRepo.findByStatDbId(statid);
	}
	
	@GetMapping("/saados/{statid}/{secid}")
	public Section getSection(@PathVariable long secid) {
		Section sec = secRepo.findBySecDbId(secid);
		return sec;
	}
	
	@PostMapping("/lisaa/saados")
	public void postStatute(@RequestParam(value="shortname") String shortName, @RequestParam(value="num") int num, @RequestParam(value="year") int year, @RequestParam(value="statutetype") String statuteType, @RequestParam(value="language") String language) 
	{
		Statute newStat = new Statute();
		newStat.setShortName(shortName);
		newStat.setNum(num);
		newStat.setYear(year);
		newStat.setStatuteType(statuteType);
		newStat.setLanguage(language);
		newStat.initStatute();
		statRepo.save(newStat);
	}
	
	//TODO
	@PostMapping("/lisaa/pykala")
	public void postSection(@RequestParam(value="identifier") String identifier, @RequestParam(value="heading", required = false, defaultValue = "") String heading, @RequestParam(value="statid") String statuteId) {
		
		Section newSec = new Section();
		if (heading.isEmpty() && identifier.isEmpty()) {
			newSec.setHeading("Voimaantulosäännös");
			newSec.setIdentifier("hello");
			Statute refStat = statRepo.findByStatuteId(statuteId);
			newSec.setStatute(refStat);
			secRepo.save(newSec);
		}
		/*
		else if (heading.isEmpty() && identifier.i) {
			newSec.setHeading("Voimaan");
		}*/
		else {
		newSec.setIdentifier(identifier);
		newSec.setHeading(heading);
		Statute refStat = statRepo.findByStatuteId(statuteId);
		newSec.setStatute(refStat);
		secRepo.save(newSec);}
	}
	
	@PostMapping("/lisaa/momentti")
	public void postSubsection(@RequestParam(value="text", required = false) String text, @RequestParam(value="secidentifier") String secIdentifier, @RequestParam(value="statid") String statuteId) {
		
		Subsection newSubs = new Subsection();
		newSubs.setText(text);
		Long secDbId = secRepo.findSectionIdByIdentifierAndStatDbId(statRepo.findByStatuteId(statuteId).getStatDbId(), secIdentifier);
		newSubs.setSection(secRepo.findBySecDbId(secDbId));
		
		Collection <Subsection> subsInSection = secRepo.findBySecDbId(secDbId).getSubsections();
		newSubs.setPosition(subsInSection.size()+1);	
		subsecRepo.save(newSubs);
	}
	
	@PostMapping("/lisaa/kohta")
	public void postParagraph(@RequestParam(value="text") String text, @RequestParam(value="secid") String secIdentifier, @RequestParam(value="statid") String statuteId,
			@RequestParam(value="subspos") int subsecPosition, @RequestParam(value="parapos") int paragraphPosition, @RequestParam(value = "ispreamble") boolean isPreamble) {
		
		Paragraph newPara = new Paragraph();
		newPara.setText(text);
		newPara.setPosition(paragraphPosition);
		newPara.setPreamble(isPreamble);
		Long secDbId = secRepo.findSectionIdByIdentifierAndStatDbId(statRepo.findByStatuteId(statuteId).getStatDbId(), secIdentifier);
		Long subsecId = subsecRepo.findBySectionDbIdAndSubsPosition(secDbId, subsecPosition);
		Subsection newParaSubsec = subsecRepo.findById(subsecId).get();
		subsecRepo.save(newParaSubsec);	
		newPara.setSubsection(newParaSubsec);
		paraRepo.save(newPara);
	}
	
}
