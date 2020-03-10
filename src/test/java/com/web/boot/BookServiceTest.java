package com.web.boot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.web.boot.domain.Aticle;
import com.web.boot.service.ArticleService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class BookServiceTest {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ElasticsearchTemplate esTemplate;
	
	@Before
	public void before() {
		esTemplate.deleteIndex(Aticle.class);
		esTemplate.createIndex(Aticle.class);
		esTemplate.putMapping(Aticle.class);
		esTemplate.refresh(Aticle.class);
	}
	
	@Test
	public void test() {
		Aticle art = new Aticle(new Long(1), "eee");
		Aticle test = articleService.save(art);
		
		assertEquals(test.getUsername(), art.getUsername());
		
	}
}
