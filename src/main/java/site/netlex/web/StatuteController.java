package site.netlex.web;

import java.util.Optional;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	public String postStatDbId(@Valid @ModelAttribute("statDbId") Long statDbId) {
		
		return "redirect:/uusipykala/" + String.valueOf(statDbId);
	}
	
	@RequestMapping(value = "/uusipykala/{statdbid}", method = RequestMethod.GET)
	public String createSection(@Valid @ModelAttribute("sectionform") SectionForm sectionForm, @PathVariable("statdbid") Long statDbId, Model model){
		model.addAttribute("sectionform", new SectionForm());
		Optional<Statute> op = statRepository.findById(statDbId);
		model.addAttribute("statDbId", op.get().getStatDbId());
		model.addAttribute("statute", op.get());
		
		return "uusipykala";
	}
	
	
	@RequestMapping(value = "/savesection", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("sectionform") SectionForm sectionForm, BindingResult bindingResult) 	{	
    			
		Section newSection = new Section();
		String statDbId = sectionForm.getStatuteDbId();
		Statute stat = statRepository.findByStatDbId(statDbId);
		newSection.setStatute(stat);
		newSection.setIdentifier(sectionForm.getIdentifier());
		newSection.setHeading(sectionForm.getHeading());
    	return "redirect:/muokkaamain/"+sectionForm.getStatuteDbId();
    }    
	
	
	@RequestMapping(value = "/muokkaamain/{id}", method = RequestMethod.GET)
	public String editStatute(@PathVariable("id") Long statDbId, Model model) {
		Optional<Statute> op = statRepository.findById(statDbId);
		model.addAttribute("statute", op.get());
		model.addAttribute("statDbId", op.get().getStatDbId());
		return "muokkaamain";
	}
	
	
	
}
