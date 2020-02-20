package com.web.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.boot.config.JwtTokenUtil;
import com.web.boot.domain.JwtRequest;
import com.web.boot.domain.JwtResponse;
import com.web.boot.service.impl.UserDetailsServiceImpl;

@RestController
public class RestWebController {
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@ResponseBody
	@GetMapping("/hello")
	public String firstPage() {
		return "index";
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken (@RequestBody JwtRequest userForm) throws Exception {
		authenticate(userForm.getUsername(), userForm.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(userForm.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}
    private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
