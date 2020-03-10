package com.web.boot.domain;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "boot", type = "aticle")
public class Aticle {
	@Id
    private Long id;
	private String username;	
		
	public Aticle() {		
	}
	
	public Aticle(Long id, String username) {
		this.id = id;
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public String toString() {
		return "Aticle{" +
				"id=" + id +
				", username='" + username  + '\'' +
				'}';		
	}
}
