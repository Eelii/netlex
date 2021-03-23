package site.netlex.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import site.netlex.domain.User;
import site.netlex.domain.SignupForm;
import site.netlex.domain.UserRepository;

@Controller
public class UserController {
	
	@Autowired
    private UserRepository repository;
	
	 @RequestMapping(value = "signup", method = RequestMethod.GET)
	    public String addUser(Model model){
	    	model.addAttribute("signupform", new SignupForm());
	        return "rekisteroidy";
	    }	
	 
	 @RequestMapping(value = "login", method = RequestMethod.GET)
	    public String getLogin(Model model){
	        return "kirjaudu";
	    }	
	 
	 @RequestMapping(value = "saveuser", method = RequestMethod.POST)
	    public String save(@Valid @ModelAttribute("signupform") SignupForm signupForm, BindingResult bindingResult) 	{

	    	if (!bindingResult.hasErrors()) {
	    		if (signupForm.getPassword().equals(signupForm.getPasswordCheck())) { // check password match		
	    			
		    		String pwd = signupForm.getPassword();
			    	BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
			    	//Password should not be saved to a database in plain text, which is why we encode a string type password.
			    	String hashPwd = bc.encode(pwd);
			    	User newUser = new User();
			    	newUser.setPasswordHash(hashPwd);
			    	newUser.setUsername(signupForm.getUsername());
			    	newUser.setRole("USER");
			    	newUser.setEmail(signupForm.getEmail());
			    	/*System.out.print(signupForm.getGender());
			    	
			    	//-------------------------------------
			    	newUser.setfName(signupForm.getfName());
			    	newUser.setlName(signupForm.getlName());
			    	newUser.setGender(signupForm.getGender());*/
			    	
			    	if (repository.findByUsername(signupForm.getUsername()) == null) {
			    		repository.save(newUser);
			    	}
			    	else {
		    			bindingResult.rejectValue("username", "err.username", "Username already exists");    	
		    			return "signup";		    		
			    	}
	    		}
	    		else {
	    			bindingResult.rejectValue("passwordCheck", "err.passCheck", "Passwords does not match");    	
	    			return "signup";
	    		}
	    	}
	    	else {
	    		return "signup";
	    	}
	    	return "redirect:/login";    	
	    }    
}
