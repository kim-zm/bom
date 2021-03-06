package com.web.boot.service.impl;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.web.boot.domain.Role;
import com.web.boot.domain.User;
import com.web.boot.repository.RoleRepository;
import com.web.boot.repository.UserRepository;
import com.web.boot.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	private static final String ROLE_PREFIX = "ROLE_";
	
	@Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	    
    @Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
    
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
		String role = "";
		if(user.getRoles() != null) {
			role = user.getRoles().stream()
				.filter(Objects::nonNull)
				.map(Role::getName)
				.filter(Objects::nonNull)
				.map(Objects::toString)
				.collect(Collectors.joining("", ROLE_PREFIX, ""));
		}
		
		user.setRoles(Arrays.asList(roleRepository.findByName(role)));
		
		userRepository.save(user);
	}
	
	@Override
	public void updateUser(User user) {
		User update = findByUsername(user.getUsername());
		user.setId(update.getId());
		user.setRoles(update.getRoles());
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));		
		userRepository.save(user);
	}
	
}
