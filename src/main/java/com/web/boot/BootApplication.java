package com.web.boot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import com.web.boot.domain.Aticle;
import com.web.boot.service.ArticleService;

@SpringBootApplication
public class BootApplication implements CommandLineRunner {

	@Autowired
	private ElasticsearchOperations es;
	@Autowired
	private ArticleService articleService;
	
	public static void main(String[] args) {
		SpringApplication.run(BootApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception{
		System.out.println("===Elasticsearch===");
		articleService.save(new Aticle(new Long(1), "first"));
		articleService.save(new Aticle(new Long(2), "second"));
		articleService.save(new Aticle(new Long(3), "second"));
		
		List<Aticle> list = articleService.findByUsername("second");
		
		list.forEach(x->System.out.println(x));
		System.out.println("===end===");
	}

}
