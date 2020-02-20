package com.web.boot.service.impl;

import java.io.UnsupportedEncodingException;

import com.web.boot.service.JwtService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtServiceImpl implements JwtService{

	private static final String SALT =  "luvookSecret";
	
	@Override
	public <T> String create(String key, T data, String subject){
		String jwt = Jwts.builder()
						 .setHeaderParam("typ", "JWT")
						 .setHeaderParam("regDate", System.currentTimeMillis())
						 .setSubject(subject)
						 .claim(key, data)
						 .signWith(SignatureAlgorithm.HS256, this.generateKey())
						 .compact();
		return jwt;
	}	

	private byte[] generateKey(){
		byte[] key = null;
		try {
			key = SALT.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return key;
	}
}