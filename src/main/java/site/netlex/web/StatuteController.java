package site.netlex.web;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import site.netlex.domain.Paragraph;
import site.netlex.domain.ParagraphForm;
import site.netlex.domain.Section;
import site.netlex.domain.SectionForm;
import site.netlex.domain.SectionRepository;
import site.netlex.domain.Statute;
import site.netlex.domain.StatuteForm;
import site.netlex.domain.StatuteRepository;
import site.netlex.domain.Subsection;
import site.netlex.domain.SubsectionForm;
import site.netlex.domain.SubsectionRepository;


@Controller
public class StatuteController {

	@Autowired
    private StatuteRepository statRepository;
	@Autowired
	private SectionRepository secRepository;
	@Autowired
	private SubsectionRepository subsecRepository;
	
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	@RequestMapping(value = "savestatute", method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute("statuteform") StatuteForm statuteForm, BindingResult bindingResult) 	{	
	    			
			    	Statute newStatute = new Statute();
			    	newStatute.setNum(Integer.parseInt(statuteForm.getNum()));
			    	newStatute.setYear(Integer.parseInt(statuteForm.getYear()));
			    	newStatute.setShortName(statuteForm.getShortName());
			    	newStatute.setStatuteType(statuteForm.getStatuteType());
			    	newStatute.setLanguage(statuteForm.getLanguage());
			    	newStatute.initStatute();
			    	
			    	if (statRepository.findByStatuteId(statuteForm.getStatuteId()) == null) {
			    		statRepository.save(newStatute);
			    	}
			    	else {
		    			bindingResult.rejectValue("statuteId", "err.statuteId", "S????d??s on jo tietokannassa");    	
		    			return "uusisaados";		    		
			    	}
	    	return "redirect:/muokkaamain/" + newStatute.getStatDbId();
	    }    
	
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	@RequestMapping(value = "/statDbId", method = RequestMethod.POST)
	public String postStatDbId(@Valid @ModelAttribute("statDbId") String statDbId) {
		
		return "redirect:/uusipykala?statdbid=" + String.valueOf(statDbId);
	}
	
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	@RequestMapping(value = "/uusipykala", method = RequestMethod.GET)
	public String createSection(@Valid @ModelAttribute("sectionform") SectionForm sectionForm, BindingResult bindingResult, @RequestParam("statdbid") long statDbId, Model model){
		
		SectionForm newSecForm = new SectionForm();
		newSecForm.setStatuteDbId(String.valueOf(statDbId));
		model.addAttribute("sectionform", newSecForm);
		Optional<Statute> op = statRepository.findById(statDbId);
		model.addAttribute("statDbId", String.valueOf(statDbId));
		model.addAttribute("statute", op.get());
		
		return "uusipykala";
	}
	
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	@RequestMapping(value = "/savesection", method = RequestMethod.POST)
    public String saveSec(@Valid @ModelAttribute("sectionform") SectionForm sectionForm, @ModelAttribute Section section) 	{	
    			
		Section newSection = new Section();
		String statDbId = sectionForm.getStatuteDbId();
		Statute stat = statRepository.findByStatDbId(Long.parseLong(statDbId));
		newSection.setStatute(stat);
		newSection.setIdentifier(sectionForm.getIdentifier());
		newSection.setHeading(sectionForm.getHeading());
		secRepository.save(newSection);
    	return "redirect:/muokkaamain/"+sectionForm.getStatuteDbId();
    }   
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/delete/statute/{statdbid}", method = RequestMethod.GET)
	public String deleteStatute(@PathVariable("statdbid") Long statDbId) {
		statRepository.deleteById(statDbId);
		return "redirect:/saadokset";
	} 
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/muokkaamain/{statdbid}/deletesection/{secdbid}", method = RequestMethod.GET)
	public String deleteSection(@PathVariable("statdbid") Long statDbId, @PathVariable("secdbid") Long secDbId) {
		secRepository.deleteById(secDbId);
		return "redirect:/muokkaamain/"+String.valueOf(statDbId);
	}
	
	//TODO -- Under construction ??? ---------------------------------------------------------------------
	
	/*@RequestMapping(value = "/saveparagraph", method = RequestMethod.POST)
    public String savePar(@Valid @ModelAttribute("paragraphform") ParagraphForm paragraphForm, @ModelAttribute Paragraph paragraph) 	{	
    			
		Paragraph newParagraph = new Paragraph();
		Long secDbId = paragraphForm.getSecDbId();
		newParagraph.setTextContent(paragraphForm.getTextContent());
		newSection.setIdentifier(sectionForm.getIdentifier());
		newSection.setHeading(sectionForm.getHeading());
		secRepository.save(newSection);
    	return "redirect:/muokkaamain/"+sectionForm.getStatuteDbId();
    }  */
	
	//---------------------------------------------------------------------
	
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	@RequestMapping(value = "/muokkaamain/{id}", method = RequestMethod.GET)
	public String editStatute(@PathVariable("id") Long statDbId, Model model) {
		Optional<Statute> op = statRepository.findById(statDbId);
		model.addAttribute("sections", op.get().getSections());
		model.addAttribute("statute", op.get());
		model.addAttribute("statDbId", op.get().getStatDbId());
		return "muokkaamain";
	}
	
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	@RequestMapping(value = "/muokkaapykala", method = RequestMethod.GET)
	public String editSection(@RequestParam(name="statid", required = true) String statDbId, 
	@RequestParam(name="secid", required = true) String secDbId, @Valid @ModelAttribute("subsectionform") SubsectionForm subsectionForm, Model model) 
	{
		Optional<Section> op = secRepository.findById(Long.parseLong(secDbId));
		SubsectionForm newSubsForm = new SubsectionForm();
		
		newSubsForm.setSecDbId(Long.parseLong(secDbId));
		newSubsForm.setStatDbId(Long.parseLong(statDbId));
		
		model.addAttribute("section", op.get());
		model.addAttribute("subsecform", newSubsForm);
		model.addAttribute("strStatDbId", statDbId);
		model.addAttribute("strSecDbId", secDbId);
		return "/muokkaapykala";
	}
	
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	@RequestMapping(value = "/savesubsection", method = RequestMethod.POST)
    public String saveSubsec(@Valid @ModelAttribute("subsecform") SubsectionForm subsectionForm, @ModelAttribute Subsection subsection) 	{	
    			
		Subsection newSubsection = new Subsection();
		Long secDbId = subsectionForm.getSecDbId();
		newSubsection.setSection(secRepository.findBySecDbId(secDbId));
		newSubsection.setText(subsectionForm.getTextContent());

		Collection <Subsection> subsInSection = newSubsection.getSection().getSubsections();
		newSubsection.setPosition(subsInSection.size()+1);
		subsecRepository.save(newSubsection);
    	return "redirect:/muokkaapykala?statid=" + String.valueOf(secRepository.findBySecDbId(secDbId).getStatute().getStatDbId())+"&secid="+String.valueOf(secDbId);
    }  
	
	
}
