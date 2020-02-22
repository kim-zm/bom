package com.web.boot.service;

import com.web.boot.domain.User;

public interface UserService {
	public User findByUsername(String username);
	public User findUserByEmail(String email);
    public void saveUser(User user);
    public void authenticate(String username, String password) throws Exception;
}
