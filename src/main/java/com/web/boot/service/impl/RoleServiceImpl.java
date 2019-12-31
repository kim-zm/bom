package com.web.boot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.boot.domain.Role;
import com.web.boot.repository.RoleRepository;
import com.web.boot.repository.UserRepository;
import com.web.boot.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
	
    @Override
	public Role findByName(String name) {
		return roleRepository.findByName(name);
	}
    
    @Override
    public List<Role> findAll() {
		return roleRepository.findAll();
	}

}
