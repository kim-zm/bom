package com.web.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.boot.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	Role findByName(String name);
}
