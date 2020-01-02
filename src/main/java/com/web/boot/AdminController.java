package com.web.boot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web.boot.config.UserValidator;
import com.web.boot.domain.Role;
import com.web.boot.domain.User;
import com.web.boot.service.RoleService;
import com.web.boot.service.UserService;

@Controller
@RequestMapping(value="/admin")
public class AdminController {
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
	
    @GetMapping("/account")
    public String account(Model model) {
    	model.addAttribute("userForm", new User());
    	return "member/account";
    }

    @PostMapping("/account")
    public String account(HttpServletRequest request, @ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) 
    {
    	userValidator.setAddValid(true);
    	userValidator.validate(userForm, bindingResult);
    	
        if (bindingResult.hasErrors()) {
            return "member/account";
        }
        
        userService.saveUser(userForm);

        redirectAttributes.addFlashAttribute("userForm", userForm);
        return "redirect:/login";
    }

}
