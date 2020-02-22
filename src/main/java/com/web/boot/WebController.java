package com.web.boot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web.boot.config.JwtTokenUtil;
import com.web.boot.config.UserValidator;
import com.web.boot.domain.Role;
import com.web.boot.domain.Search;
import com.web.boot.domain.User;
import com.web.boot.service.BookService;
import com.web.boot.service.UserService;
import com.web.boot.service.impl.UserDetailsServiceImpl;

@Controller
public class WebController {
	@Autowired
	private UserService userService;
	@Autowired
	private BookService bookService;
	@Autowired
	private UserValidator userValidator;
	
	private String app_key = "";
	
	@ModelAttribute("codeRole")
	public List<String> getCodeRole() {
		List<String> role = new ArrayList<>();
    	role.add("ADMIN");
		role.add("USER");		
    	return role;
	}
	
	@ResponseBody
	@GetMapping("/create")
    public User create(Model model) {
		User user = new User();
		user.setUsername("admin");
		user.setPassword("1234");
		user.setEmail("admin@naver.com");
		user.setRoles(Arrays.asList(new Role(1, "ADMIN")));
		userService.saveUser(user);
		return user;
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
    	model.addAttribute("userForm", new User());
    	return "member/registration";
    }

    @PostMapping("/registration")
    public String registration(HttpServletRequest request, @ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, RedirectAttributes redirectAttributes)
    {
    	userValidator.validate(userForm, bindingResult);
    	
    	Map<String, String> error = new HashMap<String, String>();
    	if (bindingResult.hasErrors()) {
            return "member/registration";
        }
        
        userForm.setRoles(Arrays.asList(new Role(2, "USER")));
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
    public String login(HttpServletRequest request, @ModelAttribute("userForm") User userForm, Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        String referrer = request.getHeader("Referer");
        request.getSession().setAttribute("url_referrer", referrer);
        
        return "member/login";
    }
    
    @GetMapping("/search")
    public String search(Search search, Model model) 
    {
    	if(search.getSearchType() != null) {
	    	search = bookService.findBookName(this.app_key, search, search.getSearchType(), search.getSearchValue());
    	}
    	model.addAttribute("search", search);
    	return "book/search";
    }
    
    @PostMapping("/search")
    public String search(@ModelAttribute("search") @Valid Search search, BindingResult bindingResult, Model model) {
    	
    	if (bindingResult.hasErrors()) {
            return "book/search";
        }
    	
    	search.setSearchType(search.getSearchType());
    	search.setSearchValue(search.getSearchValue());
    	
        model.addAttribute("search", bookService.findBookName(this.app_key, search, search.getSearchType(), search.getSearchValue()));
    	return "book/search";
    }
    
    @GetMapping("/detail")
    public String detail(Search search, @RequestParam("title") String title, Model model) 
    {    	
        search = bookService.findBookName(this.app_key, search, "title", title);
        
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date to = null;
		try {
			to = transFormat.parse(search.getList().get(0).get("datetime").toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


        model.addAttribute("search", search);
    	model.addAttribute("search_date", to);
    	return "book/detail";
    }
}

