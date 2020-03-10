package com.web.boot.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.web.boot.domain.Aticle;

public interface ElkArticleRepository extends ElasticsearchRepository<Aticle, Long>{
	List<Aticle> findByUsername(String username);
}
