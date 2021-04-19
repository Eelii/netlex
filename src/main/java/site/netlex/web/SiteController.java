package site.netlex.web;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import site.netlex.domain.SectionForm;
import site.netlex.domain.SignupForm;
import site.netlex.domain.Statute;
import site.netlex.domain.StatuteForm;
import site.netlex.domain.StatuteRepository;
import site.netlex.domain.User;
import site.netlex.domain.UserRepository;

@Controller
public class SiteController {

	@Autowired
    private StatuteRepository statRepo;
	
	@Autowired
    private UserRepository userRepo;
	
	@GetMapping(value="/")
	public String getMain(Model model){
		return "redirect:/saadokset";
	}
	
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	@RequestMapping(value = "uusisaados", method = RequestMethod.GET)
    public String addUser(Model model){
    	model.addAttribute("statuteform", new StatuteForm());
        return "uusisaados";
    }	
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	@GetMapping(value="/saadokset")
	public String getStatuteList(Model model) {
		List<Statute> statutes = (List<Statute>) statRepo.findAll(); 
		model.addAttribute("statutes",statutes);
		return "saadoslista";
	}
	
	
//Login and sign up requests ---------------------------------------------------------
	
	
	@RequestMapping(value = "kirjaudu", method = RequestMethod.GET)
    public String loginGet(){
        return "kirjaudu";
    }
	
	@RequestMapping(value = "rekisteroidy", method = RequestMethod.GET)
    public String signupGet(Model model){
		model.addAttribute("signupform", new SignupForm());
        return "rekisteroidy";
    }	
	
	@RequestMapping(value = "rekisteroidy", method = RequestMethod.POST)
    public String signupPost(@Valid @ModelAttribute("signupform") SignupForm signupForm, BindingResult bindingResult){
		if (!bindingResult.hasErrors()) { // validation errors
    		if (signupForm.getPassword().equals(signupForm.getPasswordCheck())) { // check password match		
    			
	    		String pwd = signupForm.getPassword();
		    	BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		    	//A string type password is encoded by a BCrypt encoder. This way the correct password cant be read from a database even if one has access to it. 
		    	String hashPwd = bc.encode(pwd);
		    	User newUser = new User();
		    	newUser.setPasswordHash(hashPwd);
		    	newUser.setUsername(signupForm.getUsername());
		    	newUser.setRole("USER");
		    	newUser.setEmail(signupForm.getEmail());
		    	
		    	//Checks for optional user information 
		    	if (signupForm.getfName() != null) {newUser.setfName(signupForm.getfName());}
		    	if (signupForm.getlName() != null) {newUser.setlName(signupForm.getlName());}
		    	if (signupForm.getGender() <= 3 && signupForm.getGender() >= 0) {newUser.setGender(signupForm.getGender());}
		    	
		    	//Checks to verify that the username is not already in the database and that the given passwords match.
		    	if (userRepo.findByUsername(signupForm.getUsername()) == null) {
		    		userRepo.save(newUser);
		    	}
		    	else {
	    			bindingResult.rejectValue("username", "err.username", "Username already exists");    	
	    			return "rekisteroidy";		    		
		    	}
    		}	
    		else {
    			bindingResult.rejectValue("passwordCheck", "err.passCheck", "Passwords does not match");    	
    			return "rekisteroidy";
    		}
    	}
    	else {
    		return "rekisteroidy";
    	}
    	return "redirect:/kirjaudu";    	
    }    
	
}
