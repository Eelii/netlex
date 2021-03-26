package site.netlex.web;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import site.netlex.domain.Section;
import site.netlex.domain.SectionForm;
import site.netlex.domain.SectionRepository;
import site.netlex.domain.Statute;
import site.netlex.domain.StatuteForm;
import site.netlex.domain.StatuteRepository;


@Controller
public class StatuteController {

	@Autowired
    private StatuteRepository statRepository;
	@Autowired
	private SectionRepository secRepository;
	
	@RequestMapping(value = "savestatute", method = RequestMethod.POST)
	    public String save(@Valid @ModelAttribute("statuteform") StatuteForm statuteForm, BindingResult bindingResult) 	{	
	    			
			    	Statute newStatute = new Statute();
			    	newStatute.setNum(Integer.parseInt(statuteForm.getNum()));
			    	newStatute.setYear(Integer.parseInt(statuteForm.getYear()));
			    	newStatute.setShortName(statuteForm.getShortName());
			    	newStatute.setStatuteType(statuteForm.getStatuteType());
			    	newStatute.setLanguage(statuteForm.getLanguage());
			    	newStatute.initStatute();
			    	
			    	/*System.out.print(signupForm.getGender());
			    	
			    	//-------------------------------------
			    	newUser.setfName(signupForm.getfName());
			    	newUser.setlName(signupForm.getlName());
			    	newUser.setGender(signupForm.getGender());*/
			    	
			    	if (statRepository.findByStatuteId(statuteForm.getStatuteId()) == null) {
			    		statRepository.save(newStatute);
			    	}
			    	else {
		    			bindingResult.rejectValue("statuteId", "err.statuteId", "Säädös on jo tietokannassa");    	
		    			return "uusisaados";		    		
			    	}
	    	return "redirect:/muokkaamain/" + newStatute.getStatDbId();
	    }    
	
	
	@RequestMapping(value = "/statDbId", method = RequestMethod.POST)
	public String postStatDbId(@Valid @ModelAttribute("statDbId") String statDbId) {
		
		return "redirect:/uusipykala?statdbid=" + String.valueOf(statDbId);
	}
	
	@RequestMapping(value = "/uusipykala", method = RequestMethod.GET)
	public String createSection(@Valid @ModelAttribute("sectionform") SectionForm sectionForm, BindingResult bindingResult, @RequestParam("statdbid") long statDbId, Model model){
		System.out.println("STATDBID: " + statDbId);
		SectionForm newSecForm = new SectionForm();
		newSecForm.setStatuteDbId(String.valueOf(statDbId));
		model.addAttribute("sectionform", newSecForm);
		Optional<Statute> op = statRepository.findById(statDbId);
		model.addAttribute("statDbId", String.valueOf(statDbId));
		model.addAttribute("statute", op.get());
		
		return "uusipykala";
	}
	
	
	@RequestMapping(value = "/savesection", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("sectionform") SectionForm sectionForm, @ModelAttribute Section section) 	{	
    			
		Section newSection = new Section();
		String statDbId = sectionForm.getStatuteDbId();
		Statute stat = statRepository.findByStatDbId(Long.parseLong(statDbId));
		newSection.setStatute(stat);
		newSection.setIdentifier(sectionForm.getIdentifier());
		newSection.setHeading(sectionForm.getHeading());
		secRepository.save(newSection);
    	return "redirect:/muokkaamain/"+sectionForm.getStatuteDbId();
    }    
	
	
	@RequestMapping(value = "/muokkaamain/{id}", method = RequestMethod.GET)
	public String editStatute(@PathVariable("id") Long statDbId, Model model) {
		Optional<Statute> op = statRepository.findById(statDbId);
		model.addAttribute("sections", op.get().getSections());
		model.addAttribute("statute", op.get());
		model.addAttribute("statDbId", op.get().getStatDbId());
		return "muokkaamain";
	}
	
	@RequestMapping(value = "/muokkaapykala/{secid}", method = RequestMethod.GET)
	public String editSection(@PathVariable("secid") Long secDbId, Model model) {
		Optional<Section> op = secRepository.findById(secDbId);
		model.addAttribute("section", op.get());
		return "muokkaapykala";
	}
	
	
}
