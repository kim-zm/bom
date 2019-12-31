package com.web.boot.service;

import java.util.List;

import com.web.boot.domain.Role;

public interface RoleService {
	public Role findByName(String name);
	public List<Role> findAll();
}
