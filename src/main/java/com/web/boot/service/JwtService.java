package com.web.boot.service;

public interface JwtService {
	public <T> String create(String key, T data, String subject);

}
