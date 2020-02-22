package com.web.boot;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web.boot.config.JwtTokenUtil;
import com.web.boot.domain.JwtRequest;
import com.web.boot.domain.JwtResponse;
import com.web.boot.domain.User;
import com.web.boot.service.UserService;
import com.web.boot.service.impl.UserDetailsServiceImpl;

@RestController
@RequestMapping("/rest")
public class RestWebController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
    @PostMapping("/login")
	public String createAuthenticationToken (HttpServletRequest request, HttpServletResponse response, @ModelAttribute("userForm") User userForm, RedirectAttributes redirectAttributes) throws Exception {
    	userService.authenticate(userForm.getUsername(), userForm.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(userForm.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		
		Cookie cookie = new Cookie("token", token);		
		response.addCookie(cookie);
				
		return "member/login";
	}
    
	@GetMapping("/logout")
    public String getLogout(HttpServletRequest request, HttpServletResponse response) {
    	Cookie cookie = new Cookie("token", null);
    	cookie.setMaxAge(0);
    	cookie.setPath("/");;
		response.addCookie(cookie);
    	return "member/login";
    }
    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
    	Cookie cookie = new Cookie("token", null);
    	cookie.setMaxAge(0);
    	cookie.setPath("/");;
		response.addCookie(cookie);
    	return "member/login";
    }
    
	@ResponseBody
	@GetMapping("/hello")
	public String firstPage() {
		return "index";
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken (@RequestBody JwtRequest userForm) throws Exception {
		userService.authenticate(userForm.getUsername(), userForm.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(userForm.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}

}
