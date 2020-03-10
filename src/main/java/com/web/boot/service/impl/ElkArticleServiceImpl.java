package com.web.boot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.boot.domain.Aticle;
import com.web.boot.repository.ElkArticleRepository;
import com.web.boot.service.ArticleService;

@Service
public class ElkArticleServiceImpl implements ArticleService{
	@Autowired
	ElkArticleRepository articleRepository;
	
	@Override
	public List<Aticle> findByUsername(String username) {
		return articleRepository.findByUsername(username);
	}
	
	@Override
	public Aticle save(Aticle art) {
		return articleRepository.save(art);
	}
	
}
