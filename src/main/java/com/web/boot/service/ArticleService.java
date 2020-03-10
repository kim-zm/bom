package com.web.boot.service;

import java.util.List;

import com.web.boot.domain.Aticle;

public interface ArticleService {
	List<Aticle> findByUsername(String username);
	Aticle save(Aticle art);
}
