package com.web.boot.domain;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Long id;
	
    @Column(nullable = false)
    private String name;

    public Role() {    	
	}
    
	public Role(int id, String name) {
		this.id = (long) id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
