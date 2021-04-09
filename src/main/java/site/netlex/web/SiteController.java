package site.netlex.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import site.netlex.domain.SectionForm;
import site.netlex.domain.Statute;
import site.netlex.domain.StatuteForm;
import site.netlex.domain.StatuteRepository;

@Controller
public class SiteController {

	@Autowired
    private StatuteRepository statRepository;
	
	@GetMapping(value="/")
	public String getMain(Model model){
		return "main";
	}
	
	@RequestMapping(value = "uusisaados", method = RequestMethod.GET)
    public String addUser(Model model){
    	model.addAttribute("statuteform", new StatuteForm());
        return "uusisaados";
    }	
	
	@GetMapping(value="/saadokset")
	//@PreAuthorize("hasAuthority('USER')")
	public String getStatuteList(Model model) {
		List<Statute> statutes = (List<Statute>) statRepository.findAll(); 
		model.addAttribute("statutes",statutes);
		return "saadoslista";
	}
}
