package com.web.boot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web.boot.config.UserValidator;
import com.web.boot.domain.Role;
import com.web.boot.domain.User;
import com.web.boot.service.RoleService;
import com.web.boot.service.UserService;

@Controller
public class WebController {
	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
    UserValidator userValidator;
	
	@Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@ModelAttribute("codeRole")
	public List<String> getCodeRole() {
		List<String> role = new ArrayList<>();
    	role.add("ADMIN");
		role.add("USER");		
    	return role;
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
    public String root(Model model) {
		return "redirect:/index";
    }
	
    @RequestMapping(value="/index", method=RequestMethod.GET)
    public String index(Model model) {
        return "index";
    }
    
    @GetMapping("/registration")
    public String registration(Model model) {
    	User user = new User();
    	//Role role = roleService.findByName("ROLE_USER");
    	//user.setRoles(Arrays.asList(role));
    	model.addAttribute("userForm", user);
    	return "member/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
    	
    	userValidator.validate(userForm, bindingResult);
    	
        if (bindingResult.hasErrors()) {
            return "member/registration";
        }
        
        userService.saveUser(userForm);

        redirectAttributes.addFlashAttribute("userForm", userForm);
        return "redirect:/login";
    }
    
    @GetMapping("/mypage")
    public String mypage(Model model) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	User user = userService.findByUsername(auth.getName());
    	model.addAttribute("userForm", user);
    	return "member/mypage";
    }

    @PostMapping("/mypage")
    public String mypage(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
    	
    	userValidator.validate(userForm, bindingResult);
    	
        if (bindingResult.hasErrors()) {
            return "member/registration";
        }
        
        userService.saveUser(userForm);

        redirectAttributes.addFlashAttribute("userForm", userForm);
        return "redirect:/login";
    }    

    @GetMapping("/login")
    public String login(@ModelAttribute("userForm") User userForm, Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "member/login";
    }
    /*
    @GetMapping("/logout")
    public String logout() {
        return "/logout";
    }
    */
}

